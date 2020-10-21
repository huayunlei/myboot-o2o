package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

import java.util.Date;

/**
 * 艾积分充值记录返回值
 * @author ZHAO
 */
@Data
public class AjbRecordResponseVo {
	private Integer id;//id
	
	private Integer userId;//用户ID
	
	private String orderNum;//充值回执单号
	
	private String activityCode;//活动代码
	
	private Integer amount;//充值金额
	
	private String remark;//活动文案
	
	private Date createTime;//充值时间
	
	private Integer activeFlag;//是否有效-1无效0有效
}
