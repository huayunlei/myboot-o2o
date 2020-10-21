package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;

/**
 * Created by shirely_geng on 15-1-21.
 */
@Data
public class UserOrder {
    private Long orderId;
    private String name;
    private String pictureUrlOriginal;
    private Long orderStatus;
    private Timestamp createTime;
    private Double orderPrice;
    private Double couponPay;
    private Long productCount;
    private Long reasonId;
    private String purchaserTel;
    private String purchaserName;
    private String deliveryAddress;
    private String orderNum;
    private Long userId;
    private String receiptName;
    private String receiptTel;
    private String deliveryTime;
    private Integer actCode;//参与活动的订单，需要记住活动代码，订单详情中
    //不同的活动，订单详情展示的不一样
    @JsonIgnore
    private Timestamp payDeadLine;

    private String strDeadLine;
    
    private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键
    
    private Double voucherPay;// 抵用券面值

    public void setCreateTime(Timestamp createTime) {
        if (null == createTime) {
            this.createTime = null;
        } else {
            this.createTime = (Timestamp) createTime.clone();
        }
    }
}
