package com.ihomefnt.o2o.intf.domain.ajb.dto;

import com.ihomefnt.o2o.intf.domain.user.vo.response.AccountBookRecordResponseVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户个人艾积分详情
 * @author ZHAO
 */
@Data
public class UserAjbRecordDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2555066591160035598L;

	private Integer userId;// 用户Id
	
	private Integer totalAmount;// 艾积分发放总数
	
	private Integer usedAmount;// 已使用艾积分数量
	
	private Integer freezeAmount;// 已冻结数量
	
//	private Integer usableAmount;// 可用艾积分数量
	
	/**
	 * 展示可用艾积分
	 */
	private  Integer displayUsableAmount  ;
	
	private Integer expiredAmount;// 过期的数量
	
	private String endTime;// 艾积分过期时间
	
	private Integer exRate;// 艾积分汇率
	
	private List<AccountBookRecordResponseVo> accountBookRecordVos;// 艾积分明细记录
}
