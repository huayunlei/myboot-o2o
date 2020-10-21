package com.ihomefnt.o2o.intf.domain.coupon.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author zhang 抵用券实体类
 */
@Data
public class Voucher {

	private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键

	private String voucherName;// 抵用券名称
	@JsonIgnore
	private String remark; //抵用券使用帮助,给后台数据库用
	private List<VoucherRemark> voucherRemarkList;//抵用券使用帮助,给前台用的
	
	@JsonIgnore
	private String orderTypes;// 限制订单类型,给后台用的
	@JsonIgnore
	private List<String> orderTypeList;// 限制订单类型,给前台用的,暂时不用

	@JsonIgnore
	private String productTypes;// 限制商品品类,给后台用的
	@JsonIgnore
	private List<String> productTypeList;// 限制商品品类,给前台用的,暂时不用

	@JsonIgnore
	private Double moneyLimit;// 抵用券最低消费限制,给后台用的
	private String moneyLimitDesc;// 抵用券最低消费限制,给前台用的

	private Double parValue;// 抵用券面值
	
	private Long createTime;//抵用券领取时间
	
	@JsonIgnore
	private Timestamp createTimestamp;

	private Long startTime;// 抵用券开始时间,给前台用的
	@JsonIgnore
	private Timestamp startTimestamp;// 抵用券开始时间,给后台用的

	private Long endTime;// 抵用券结束时间,给前台用的
	@JsonIgnore
	private Timestamp endTimestamp;// 抵用券结束时间,给后台用的
	
	private String timeDesc;//抵用券开始时间到 抵用券结束时间
	
	private Long statusDisplay;//给前台展示用1正常2快过期;3已过期
	
	@JsonIgnore
    private Long voucherStatus ;//1:待确认收款2:已生效3:已使用4:已失效
	
	private String  voucherDesc; //抵用券描述,默认产品不限
	
	private boolean defaultSelect;//是否默认选中  true:最大金额 

}
