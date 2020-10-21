/**
 * 
 */
package com.ihomefnt.o2o.intf.service.weixin;

import com.ihomefnt.o2o.intf.domain.weixin.dto.FLuxAccessToken;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxActivity;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxUser;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpFluxLogRequest;

import java.util.List;

/**
 * @author zhang
 *
 */
public interface FluxService {
	
	/**
	 * 获取工作号微信AccessToken
	 * @return
	 */
	FLuxAccessToken getFLuxAccessToken();
	
	/**
	 * 获取微信用户列表
	 * @return
	 */
	List<FluxUser> getFluxUserList();
	
	/**
	 * 新增或者修改获取用户信息
	 * @param openId
	 * @param noticeType 关注类型：1 关注 0 非关注
	 * 
	 */
	void addOrUpdateFluxUser(String openId,int noticeType);
	
	/**
	 * 根据条件来查询
	 * @param request
	 * @return
	 */
	List<FluxUser> queryUserByConditon(HttpFluxLogRequest request);
	
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
	int queryLogByConditon(HttpFluxLogRequest request);
	
	/**
	 * 领取流量，并且返回 号码类型
	 * @param request
	 * @return 1 联通 2 非联通
	 */
	int acceptFlux(HttpFluxLogRequest request);

}
