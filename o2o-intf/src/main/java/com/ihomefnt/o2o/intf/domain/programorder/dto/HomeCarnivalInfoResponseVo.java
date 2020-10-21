package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.promotion.dto.OrderHouseInfoResultVo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 1219置家狂欢节
 * @author ZHAO
 */
@Data
public class HomeCarnivalInfoResponseVo {
	private String customerName;//用户名称
	
	private Integer sort;//报名名次
	
	private Timestamp joinTime;//报名时间
	
	private String joinTimeStr;//报名时间描述xx月xx日
	
	private BigDecimal preReturnMoneyAmount;//预计现金返现
	
	private Integer preReturnAijiaCoinCount;//预计艾积分收入
	
	private BigDecimal hasReturnedMoneyAmount;//已返现
	
	private Integer hasReturnAijiaCoinCount;//已返艾积分
	
	private Integer loanType;//用户类型0：贷款用户 1：全款用户
	
	private Integer joinActTotalCount;//参加活动用户总数
	
	private String adviserName;//置家顾问姓名
	
	private String adviserMobile;//置家顾问手机号
	
	private List<OrderHouseInfoResultVo> orderHouseList;//订单房产信息

}
