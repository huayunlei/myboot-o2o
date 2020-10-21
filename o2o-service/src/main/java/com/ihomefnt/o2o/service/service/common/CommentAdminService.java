package com.ihomefnt.o2o.service.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;

@Service
public class CommentAdminService {
	
	@Autowired
    private DicProxy dicProxy;
    
	/**
	 * 根据手机号判断是否是官方回复账号
	 * @param userMobile
	 * @return
	 */
	public boolean judgeCommentAdminMobile(String userMobile){
		boolean result = false;
		//官方回复手机号
        List<String> replyMobileList = new ArrayList<String>();
        DicListDto mobileListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN_MOBILE);
        if(mobileListResponseVo != null){
        	List<DicDto> mobileList = mobileListResponseVo.getDicList();
        	if(!CollectionUtils.isEmpty(mobileList)){
        		for (DicDto mobile : mobileList) {
        			if(org.apache.commons.lang3.StringUtils.isNotBlank(mobile.getValueDesc())){
        				replyMobileList.add(mobile.getValueDesc());
        			}
				}
        	}
        }
        if(!CollectionUtils.isEmpty(replyMobileList) && StringUtils.isNotBlank(userMobile)){
        	result = replyMobileList.contains(userMobile);
        }
        
		return result;
	}
	
	/**
	 * 查询官方回复昵称
	 * @return
	 */
	public String queryOfficialName(){
		String officialName = "";
		DicListDto dicListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN);
        if(dicListResponseVo != null){
        	List<DicDto> dicList = dicListResponseVo.getDicList();
        	if(!CollectionUtils.isEmpty(dicList)){
        		officialName = dicList.get(0).getValueDesc();
        	}
        }
        return officialName;
	}
	
	/**
	 * 官方回复手机号
	 * @return
	 */
	public List<String> queryReplyMobileList(){
		List<String> replyMobileList = new ArrayList<String>();
        DicListDto mobileListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN_MOBILE);
        if(mobileListResponseVo != null){
        	List<DicDto> mobileList = mobileListResponseVo.getDicList();
        	if(!CollectionUtils.isEmpty(mobileList)){
        		for (DicDto mobile : mobileList) {
        			if(org.apache.commons.lang3.StringUtils.isNotBlank(mobile.getValueDesc())){
        				replyMobileList.add(mobile.getValueDesc());
        			}
				}
        	}
        }
        
        return replyMobileList;
	}
}
