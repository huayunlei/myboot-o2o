package com.ihomefnt.o2o.intf.proxy.ajb;

import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AccountBookRecordDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AjbBillNoDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AjbSearchDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.OrderNumDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.TradeParamDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.UserAjbRecordDto;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbActivityResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbRecordListResponseVo;

/**
 * 艾积分服务代理
 * @author ZHAO
 */
public interface AjbProxy {
	/**
	 * 艾积分活动充值
	 * @param userId用户ID
	 * @param amount艾积分数额
	 * @param remark备注
	 * @param activityCode 活动代码1.注册账号 2.资料完善 3.发表晒家
	 * @return
	 */
	AjbBillNoDto ajbActivityRecharge(Integer userId, Integer amount, String remark, Integer activityCode);
	
	/**
	 * 查询用户艾积分信息
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	UserAjbRecordDto queryAjbDetailInfoByUserId(Integer userId, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询账本记录
	 * @param param
	 * @return
	 */
	Page<AccountBookRecordDto> queryBookRecords(AjbSearchDto param);
	
	/**
	 * 订单确认支付（线上）
	 * @param param
	 * @return
	 */
	boolean confirmPay(OrderNumDto param);
	
	/**
	 * 订单支付冻结（线上）
	 * @param param
	 * @return
	 */
	boolean freezePay(TradeParamDto param);
	
	/**
	 * 根据艾积分活动代码查询活动信息
	 * @param activityCode
	 * @return
	 */
	AjbActivityResponseVo queryAjbActivityByCode(String activityCode);
	
	/**
	 * 新增充值记录
	 * @param userId
	 * @param orderNum
	 * @param activityCode
	 * @return
	 */
	boolean addAjbRecord(Integer userId, String orderNum, String activityCode);
	
	/**
	 * 根据用户ID和活动代码查询充值记录
	 * @param userId
	 * @param activityCode
	 * @return
	 */
	AjbRecordListResponseVo queryRecordByCodeAndUserId(Integer userId, String activityCode);
}
