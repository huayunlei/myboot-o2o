package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by shirely_geng on 15-1-23.
 */
@Data
public class TOrder {
    private Long orderId;
    private String purchaserName;
    private String purchaserTel;
    private Double orderPrice;
    private Long orderProductAmount;
    private Long orderStatus;

    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp contactTime;

    private Long userId;
    private String deliveryAddress;
    private String invoiceInformation;
    private Long productId;
    private String productName;
    private Long productType;
    private Integer orderSource;
    private String orderNum;
    private String pictureUrlOriginal;
    private String receiptName;
    private String receiptTel;

    private double transportPay;
    private double couponPay;

    private Long areaId;

    private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键

    private Double parValue;// 抵用券面值

}