package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WalletDo {
	 /**主鍵 */
    private Long id;//
    private Long uid;//用户id
    private String userMobile;//用户号码
    private String recommendMobile;//关联的推荐用户号码
    private Double userMoney;//好友本次的消费总额
    private String  rebateRules;//返点规则/用户的提成系数、
    private Double myCommission;//本次提成 userMoney*rebateRules
    private Double  frozenMoney;//被冻结的资金
    private Integer  status;//是否冻结
    private Timestamp orderTime;//订购时间/支付时间
    private String orderTimeStr;//订购时间/支付时间
    private String orderNum;//订单编号


	private Timestamp createTime;//记录创建时间
    private Integer changeType;//操作类型，0为充值，1为提现，2为管理员调节，99为其他类型,
}
