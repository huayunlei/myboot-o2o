package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashierRecordDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -418487435571832480L;

    private Long id;

    /**
     *订单ID
     */
    private Long fidOrder;
    
    /**
     *收款人ID
     */
    private Long fidPayee;

    /**
     *收款人姓名
     */
    private String payeeName;

    /**
     *类型
     */
    private Integer type;

    /**
     *金额
     */
    private BigDecimal money;

    /**
     *描述（如：分配方式、付款比例等等）
     */
    private String description;

    /**
     *支付方式
     */
    private Integer paymentMode;

    /**
     *支付号码（pos、银行卡号、支付宝账号、微信号等）
     */
    private String paymentNo;

    /**
     *收据编号
     */
    private String receiptNo;

    /**
     *状态
     */
    private Integer state;

    /**
     *备注
     */
    private String remark;

    private Date createTime;

    /**
     *更新时间
     */
    private Date updateTime;
}
