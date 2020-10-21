package com.ihomefnt.o2o.intf.domain.coupon.doo;

import com.ihomefnt.o2o.intf.domain.coupon.dto.CouponRemarkDto;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CashCouponDo {
    private String couponId;    //
    private String couponName;  //现金券名称
    private double totalMoney;  //现金金额
    private double payMoney;    //实付金额
    
    private String location;    //所属楼盘
    private String remark;      //使用说明
    
    private String startTime;   //开始时间
    private String endTime;     //结束时间
    
	private String createTime;//抵用券领取时间
	
	@JsonIgnore
	private Timestamp createTimestamp;
    
    private String type;        //类型
    private String isRead;      //0:未读1:已读
    private String status;      //-3.暂存-2.删除-1.已失效0.未激活1.已激活2.已充值
    
    private List<CouponRemarkDto> remarkList;
	
	public String getType() {
		if("1".equals(type)){
			return "楼盘现金券";
		}
		return type;
	}
}
