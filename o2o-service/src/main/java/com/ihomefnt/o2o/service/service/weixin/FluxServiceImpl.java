/**
 * 
 */
package com.ihomefnt.o2o.service.service.weixin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.dao.weixin.FluxDao;
import com.ihomefnt.o2o.intf.manager.constant.weixin.FluxConstant;
import com.ihomefnt.o2o.intf.service.weixin.FluxService;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FLuxAccessToken;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxActivity;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxLogDto;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxUser;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpFluxLogRequest;
import com.ihomefnt.o2o.intf.manager.util.common.http.HttpClientConnectionManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 *
 */
@Service
public class FluxServiceImpl implements FluxService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FluxServiceImpl.class);

	@Autowired
	private FluxDao dao;

	@Override
	public FluxActivity queryActivityByPK(Long activityId) {
		return this.dao.queryActivityByPK(activityId);
	}

	@Override
	public int queryLogByConditon(HttpFluxLogRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobile", request.getMobile());
		//先手机 验证
		int result=this.dao.queryLogByConditon(paramMap);
		if(result==0){
			paramMap = new HashMap<String, Object>();
			paramMap.put("unionId", request.getUnionId());
			//再unionId验证，双重验证
		    result=this.dao.queryLogByConditon(paramMap);
		}
		//0表示没有领取过，大于0 表示已经领取了
		return result;
	}

	@Override
	public int acceptFlux(HttpFluxLogRequest request) {
		FluxLogDto dto = new FluxLogDto(request);
		int mobileType = this.getMobileType(request.getMobile());
		dto.setMobileType(mobileType);
		this.dao.acceptFlux(dto);
		return mobileType;
	}

	/**
	 * 判断号码类型:1 联通 2 非联通<br/>
	 * 	
	 */
	private int getMobileType(String mobile) {
		//联通
		if (mobile.startsWith("130") || mobile.startsWith("131")
				|| mobile.startsWith("132") || mobile.startsWith("155")|| mobile.startsWith("156")
				|| mobile.startsWith("185") || mobile.startsWith("186")
				|| mobile.startsWith("176") || mobile.startsWith("145")|| mobile.startsWith("1709")) {
			return 1;
		} 
		//移动
		else if (mobile.startsWith("1340")|| mobile.startsWith("1341")|| mobile.startsWith("1342")
				||mobile.startsWith("1343")|| mobile.startsWith("1344")|| mobile.startsWith("1345")
				||mobile.startsWith("1346")|| mobile.startsWith("1347")|| mobile.startsWith("1348")
				|| mobile.startsWith("135")|| mobile.startsWith("136") || mobile.startsWith("137")
				|| mobile.startsWith("138") || mobile.startsWith("139")
				|| mobile.startsWith("150") || mobile.startsWith("151")
				|| mobile.startsWith("152") || mobile.startsWith("157")
				|| mobile.startsWith("158") || mobile.startsWith("159")
				|| mobile.startsWith("182")
				|| mobile.startsWith("183")|| mobile.startsWith("184")
				|| mobile.startsWith("187")|| mobile.startsWith("188")
				|| mobile.startsWith("178")|| mobile.startsWith("147")|| mobile.startsWith("1705")
				) {
			return 2;
		} 
		//电信
		else if (mobile.startsWith("133") || mobile.startsWith("1349")
				|| mobile.startsWith("153") || mobile.startsWith("180")
				|| mobile.startsWith("181") || mobile.startsWith("189")
				|| mobile.startsWith("1700") || mobile.startsWith("177")
				 ) {
			return 2;
		} else {
			// 如果出现不能判断号段,默认联通
			return 1;
		}
	}

	@Override
	public FLuxAccessToken getFLuxAccessToken() {
		/**
		 * 这里有个常识需要理解
		 * 微信的token2个小时会失效一次，并且获取token次数是有限制
		 * 根据这个原则，我们需要保存住token
		 */
		FLuxAccessToken fLuxAccessToken = this.dao.getFLuxAccessToken();
		boolean refreashResult = false;//是否需要刷新，true 需要刷新，false不需要刷新
		if (fLuxAccessToken == null) {
			refreashResult = true;
		} else {
			Timestamp createTime = fLuxAccessToken.getCreateTime();
			if (createTime == null) {
				refreashResult = true;
			}
			// 超过1小时50分钟就需要刷新
			else if (System.currentTimeMillis() - createTime.getTime() > 6600*1000L) {
				refreashResult = true;
			}
		}
		if (refreashResult) {
			//如果刷新了，需要将token保存到后台数据库
			String url = FluxConstant.ACCESS_TOKEN_URL.replaceAll("APPID",
					FluxConstant.APP_ID).replaceAll("APPSECRET",
					FluxConstant.APP_SECRET);
			Map<String, Object> map = this.getContentByUrl(url);
			fLuxAccessToken = new FLuxAccessToken();
			fLuxAccessToken.setAccessToken((String) map.get("access_token"));
			this.dao.insertFLuxAccessToken(fLuxAccessToken);
			return fLuxAccessToken;
		} else {
			return fLuxAccessToken;
		}

	}

	/**
	 * 解析微信xml的公共方法
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Map<String, Object> getContentByUrl(String url) {
		String jsonStr = "";
		HttpResponse response = null;
		try (DefaultHttpClient client = new DefaultHttpClient();) {
			client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,true);
			HttpGet httget = HttpClientConnectionManager.getGetMethod(url);
			response = client.execute(httget);
			jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = JsonUtils.json2map(jsonStr);
		return map;
	}

	@Override
	public List<FluxUser> getFluxUserList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", FluxConstant.STATUS_FOCUS);
		List<FluxUser> list = this.dao.getFluxUserList(paramMap);
		//如果没有查询到，表示要通过微信接口去获取所有用户列表
		if(list==null||list.isEmpty()){
			String token = this.getFLuxAccessToken().getAccessToken();
			String url=FluxConstant.GET_USER_OPENID_LIST_FIRST_URL.replaceAll("ACCESS_TOKEN", token);
			Map<String, Object> map = this.getContentByUrl(url);
			JSONObject data =(JSONObject) map.get("data");
			JSONArray openids=(JSONArray) data.get("openid");
			List<FluxUser> userList= new ArrayList<FluxUser>();
			for(int i=0;i<openids.size();i++){
				String openId=openids.getString(i);
				String unionUrl=FluxConstant.GET_USER_UNIONID_URL.replaceAll("ACCESS_TOKEN", token).replaceAll("OPENID", openId);
				FluxUser user =new FluxUser();
				Map<String, Object> unionMap = this.getContentByUrl(unionUrl);
				Integer status =(Integer) unionMap.get("subscribe"); //是否关注：关注类型：1 关注 0 非关注
				String unionId=(String)unionMap.get("unionid");//微信unionid
				String nickName=(String)unionMap.get("nickname");//微信昵称,方便定位
				user.setOpenId(openId);
				user.setUnionId(unionId);
				user.setStatus(status);
				/*
				 * 后台在存储昵称有个常识需要注意：
				 * 昵称 是可以需要非主流哪些特需符号 
				 * 该字段存编码格式存储务必为utf8mb4
				 */
				user.setNickName(nickName);
				userList.add(user);
			}
			//并且将这些 用户都保存到后台数据库
			this.dao.insertFluxUserList(userList);
			return userList;
		}else{
			//如果找到了，就直接返回
			return list;
		}		
	}

	@Override
	public List<FluxUser> queryUserByConditon(HttpFluxLogRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", FluxConstant.STATUS_FOCUS);
		paramMap.put("unionId", request.getUnionId());
		List<FluxUser> list = this.dao.getFluxUserList(paramMap);
		return list;
	}

	@Override
	public void addOrUpdateFluxUser(String openId,int noticeType) {
		//获取unionId
		String token = this.getFLuxAccessToken().getAccessToken();
		String unionUrl=FluxConstant.GET_USER_UNIONID_URL.replaceAll("ACCESS_TOKEN", token).replaceAll("OPENID", openId);
		Map<String, Object> unionMap = this.getContentByUrl(unionUrl);
		String unionId=(String)unionMap.get("unionid");	
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("unionId", unionId);
		//我们用户设计是根据unionId来判断是否唯一的
		List<FluxUser> list = this.dao.getFluxUserList(paramMap);
		//没有找到的话，就新增
		if(CollectionUtils.isEmpty(list)){
			List<FluxUser> userList= new ArrayList<FluxUser>();
			FluxUser user =new FluxUser();
			user.setOpenId(openId);
			user.setUnionId(unionId);
			user.setStatus(noticeType);
			String nickName=(String)unionMap.get("nickname");
			user.setNickName(nickName);
			userList.add(user);
			//关键接口，建议增加日志
			LOG.info("addweixinUser message unionId：{},openId:{},status:{}", unionId, openId, noticeType);
			this.dao.insertFluxUserList(userList);
		}
		//否则就修改
		else{
			//关键接口，建议增加日志
			LOG.info("updateweixinUser message unionId：{},openId:{},status:{}",unionId,openId,noticeType);
			paramMap.put("openId", openId);
			paramMap.put("status", noticeType);
			this.dao.UpdateFluxUser(paramMap);
		}	
	}

}
