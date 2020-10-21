package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 1219置家狂欢节信息
 * @author ZHAO
 */
@Data
public class HomeCarnivalInfo {
	private String userName;//用户名称
	
	private Integer sort;//报名名次
	
	private BigDecimal preReturnMoneyAmount;//预计现金返现
	
	private Integer preReturnAijiaCoinCount;//预计艾积分收入
	
	private BigDecimal hasReturnedMoneyAmount;//已返现
	
	private Integer hasReturnAijiaCoinCount;//已返艾积分
	
	private Integer loanType;//用户类型0：贷款用户 1：全款用户
	
	private Integer joinActTotalCount;//参加活动用户总数
	
	private String profitRate;//年化收益率

	public HomeCarnivalInfo() {
		this.userName = "";
		this.sort = 0;
		this.preReturnMoneyAmount = BigDecimal.ZERO;
		this.preReturnAijiaCoinCount = 0;
		this.hasReturnedMoneyAmount = BigDecimal.ZERO;
		this.hasReturnAijiaCoinCount = 0;
		this.loanType = 0;
		this.joinActTotalCount = 0;
		this.profitRate = "";
	}
}
