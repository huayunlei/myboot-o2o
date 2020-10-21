package com.ihomefnt.o2o.service.service.push;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.dao.dic.DictionaryDao;
import com.ihomefnt.o2o.intf.dao.push.PushDao;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.push.dto.*;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.push.PushProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushServiceImpl implements PushService {
	protected static final Logger LOG = LoggerFactory.getLogger(PushServiceImpl.class);

    @Autowired
    private PushDao pushDao;
    @Autowired
    private PushProxy pushProxy;
    @Autowired
    private DictionaryDao dictionaryDao;
    @Autowired
    DictionaryService dictionaryService;
	@Autowired
	UserProxy userProxy;
	
	@Override
	public void updatePushList(JpushUpdateRequestVo request) {
		pushDao.updatePushList(request);				
	}

	@Override
	public List<ExtrasDto> queryPushList(HttpBaseRequest request) {
		HttpUserInfoRequest userDto = request.getUserInfo();
		if(userDto!=null){
			// 通过token获取电话号码,防止请求号码有缓存
			request.setMobileNum(userDto.getMobile());
		}
		return pushDao.queryPushList(request);
	}
	
	@Override
	public void insertPushList(List<PushUserCenterInfoDto> list) {
		LOG.info("insertPushList mongdb data paras = {}", list);
		pushDao.insertPushList(list);		
	}
	
	@Override
	public void insertPushPlatformCenterInfo(PushPlatformCenterInfoDto center) {
		LOG.info("insertPushPlatformCenterInfo mongdb data paras = {}", center);
		pushDao.insertPushPlatformCenterInfo(center);		
	}
	
	
	@Override
	public Integer sendPushPersonalMessage(JpushParamDto jpushRequest) {
		/**
		 * 调度公共服务push推送接口
		 */		
		JSONObject param = convertHttpJpushRequestToJSONObject(jpushRequest,"personal");
		ResponseVo<?> responseVo = pushProxy.sendPushPersonalMessage(param);
		if (responseVo == null) {
			return -1;
		}		
		return 0;
	}
	
	/**
	 * @param jpushRequest
	 * @return
	 */
	private JSONObject convertHttpJpushRequestToJSONObject(JpushParamDto jpushRequest, String type) {
		PushTempletEntityDto templet=this.getTempletContentByType(jpushRequest.getNoticeSubType().intValue());
		JSONObject param = new JSONObject();	
		JSONObject pushExtra = new JSONObject();
		if("platform".equals(type)){
			if(jpushRequest.getPlatform()==null){
				param.put("platform", "all");
			}else{
				String[] platforms = jpushRequest.getPlatform().split(",");
				param.put("platform", platforms);  //推送平台
			}
		}else{
			String[] alias = jpushRequest.getAlias().split(",");
			param.put("mobiles", alias);	
		}
		String tags = jpushRequest.getTags();
		if(StringUtils.isBlank(tags)){
        	//这个是支持版本
			tags=dictionaryDao.getValueByKey("JPUSH_APP_VERSION");
        	if(StringUtils.isBlank(tags)){
        		tags="2.9.8";
        	}
		}
		// 2.9.8 转换为2_9_8
		String[] versions = tags.replace(".", "_").split(",");// app前端特需要求
		param.put("versions", versions);
		if(StringUtils.isBlank(jpushRequest.getNewsTitle())){
			param.put("msgTitle", templet.getTempletTitle());
			pushExtra.put("msgTitle", templet.getTempletTitle());
		}else{
			param.put("msgTitle", jpushRequest.getNewsTitle());
			pushExtra.put("msgTitle", jpushRequest.getNewsTitle());
		}
		param.put("msgContent", jpushRequest.getContent());
		param.put("diyMsg", false);// 是否为自定义消息
		pushExtra.put("msgType", jpushRequest.getNoticeSubType());	
		pushExtra.put("createTime", System.currentTimeMillis());
		if(jpushRequest.getToUrl()==null){
			pushExtra.put("openPage", templet.getToUrl());
		}else{
			String toUrl=jpushRequest.getToUrl();
			toUrl=toUrl.replaceAll("\\[eq\\]", "=");
			pushExtra.put("openPage", toUrl);
		}
		if(jpushRequest.getPhotoUrl()==null){
			pushExtra.put("msgImg", templet.getPhotoUrl());
		}else{
			pushExtra.put("msgImg", jpushRequest.getPhotoUrl());
		}		
		if (jpushRequest.getUnReadCount() == null) {
			if (templet.getUnReadCount() != null) {
				pushExtra.put("unReadCount", templet.getUnReadCount());// 未读数加1
			} else {
				pushExtra.put("unReadCount", 1);// 未读数加1
			}
		} else {
			pushExtra.put("unReadCount", jpushRequest.getUnReadCount());// 未读数加1
		}				
		if (jpushRequest.getJoinBoxStatus() == null) {
			pushExtra.put("saveInMsgCenter", templet.getSaveInMsgCenter());// 是否是消息组
																			// :1是0否
			if (templet.getSaveInMsgCenter() == null) {
				pushExtra.put("saveInMsgCenter", 1);// 是否是消息组 :1是0否
			}
		} else {
			pushExtra.put("saveInMsgCenter", jpushRequest.getJoinBoxStatus());// 是否是消息组
																				// :1是0否
		}	
		pushExtra.put("msgContent", jpushRequest.getContent());
		if (jpushRequest.getMessageGroupStatus() == null) {
			pushExtra.put("messageGroupStatus", templet.getMessageGroupStatus());// 是否需要消息分组
																					// :1是0否
			if (templet.getMessageGroupStatus() == null) {
				pushExtra.put("messageGroupStatus", 0);// 是否需要消息分组 :1是0否
			}
		} else {
			pushExtra.put("messageGroupStatus", jpushRequest.getMessageGroupStatus());// 是否需要消息分组
																						// :1是0否
		}	
		param.put("pushExtra", pushExtra);
		return param;
	}
	
    private PushTempletEntityDto getTempletContentByType(Integer type) {
    	return pushDao.getTempletContentByType(type);
    }

}
