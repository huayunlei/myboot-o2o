package com.ihomefnt.o2o.intf.domain.culture.dto;

import com.ihomefnt.common.util.validation.annotation.Phone;
import com.ihomefnt.common.util.validation.annotation.PhoneMode;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class CultureOrderCreateDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer orderId;
	/**
     *订单类型
     */
	@NotNull(message="订单类型不能为空")
    private Integer orderType;

    /**
     *顾客姓名
     */
    private String customerName;

    /**
     *顾客电话
     */
    @NotBlank(message = "顾客电话不能为空")
    @Phone(value = PhoneMode.MOBILE, message = "顾客电话格式不正确")
    private String customerTel;

    /**
     *订单总价
     */
    @NotNull(message="订单总价不能为空")
    private BigDecimal totalPrice;

    /**
     *实际支付金额
     */
    @NotNull(message="订单实际支付金额不能为空")
    private BigDecimal actualPayMent;

    /**
     *商品总数
     */
    private Integer productCount;

    /**
     *预计收货时间
     */
    private Date expectedReceiptTime;

    /**
     *注册用户id
     */
    private Integer fidUser;

    /**
     *客户收货地址
     */
    private Integer fidArea;

    /**
     *收货人姓名
     */
    private String receiverName;

    /**
     *收货人电话
     */
    private String receiverTel;

    /**
     *静态客户收货地址
     */
    private String customerAddress;

    /**
     *所属公司
     */
    private Integer fidCompany;

    /**
     *订单来源
     */
    private Integer source;

    /**
     *下单时间
     */
    private Date orderTime;

    /**
     *销售id
     */
    private Integer fidSale;

    /**
     *所属项目
     */
    private Integer fidProject;

    /**
     * 订单商品清单
     */
    @NotNull(message="商品清单不能为空")
    private List<CultureOrderDetailDto> detailList;

}
