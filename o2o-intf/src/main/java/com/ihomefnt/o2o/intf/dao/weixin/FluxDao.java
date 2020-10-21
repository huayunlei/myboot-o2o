/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.weixin;

import com.ihomefnt.o2o.intf.domain.weixin.dto.FLuxAccessToken;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxActivity;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxLogDto;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxUser;

import java.util.List;
import java.util.Map;


/**
 * @author zhang
 *
 */
public interface FluxDao {
	
	/**
	 * 获取工作号微信AccessToken
	 * @return
	 */
	FLuxAccessToken getFLuxAccessToken();
	
	/**
	 * 插入微信AccessToken
	 * @param token
	 * @return
	 */
	int insertFLuxAccessToken(FLuxAccessToken token);
	
	/**
	 * 获取微信用户列表
	 * @return
	 */
	List<FluxUser> getFluxUserList(Map<String, Object> paramMap);
	
	int UpdateFluxUser(Map<String, Object> paramMap);
	
	/**
	 * 插入微信用户列表
	 * @param list
	 * @return
	 */
	int insertFluxUserList(List<FluxUser> list);	
	
	/**
	 * 根据活动id查询活动
	 * @param activityId 活动id
	 * @return
	 */
	FluxActivity queryActivityByPK(Long activityId);
	
	/**
	 * 根据条件判断用户是否已经领取过
	 * @param request
	 * @return 0没有领取  >0 表示已经领取
	 */
	int queryLogByConditon(Map<String, Object> paramMap);
	
	/**
	 * 领取流量
	 * 
	 */
	int acceptFlux(FluxLogDto dto);

}
