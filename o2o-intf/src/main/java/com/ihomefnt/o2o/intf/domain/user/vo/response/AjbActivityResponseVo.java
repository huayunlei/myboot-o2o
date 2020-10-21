package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

import java.util.Date;

/**
 * 艾积分活动返回值
 * @author ZHAO
 */
@Data
public class AjbActivityResponseVo {
	private Integer id;//id
	
	private String code;//活动代码
	
	private String name;//活动名称
	
	private String remark;//活动文案
	
	private Integer amount;//活动充值金额
	
	private Integer rechargeNum;//活动充值次数
	
	private Integer activeFlag;//活动是否有效-1无效0有效
	
	private Date startTime;//活动开始时间
	
	private Date endTime;//活动结束时间
}
