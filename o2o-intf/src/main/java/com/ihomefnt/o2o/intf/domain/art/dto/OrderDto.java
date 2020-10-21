package com.ihomefnt.o2o.intf.domain.art.dto;

import com.ihomefnt.oms.trade.order.dto.RefundRecordDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author 作者 wzhang
* @version 创建时间：2016年6月14日 上午9:13:28
* 订单列表类的返回
*/
@Data
public class OrderDto implements Serializable {
	
	private static final long serialVersionUID = -7950292282937878330L;

	/**
     *订单id
     */
    private Integer orderId;

    /**
     *订单类型
     */
    private Integer orderType;

    /**
     *订单编号全局
     */
    private String orderNum;

    /**
     *顾客姓名
     */
    private String customerName;

    /**
     *顾客电话
     */
    private String customerTel;

    /**
     * 所属项目
     */
    private Integer projectId;

    /**
     *订单总价
     */
    private BigDecimal totalPrice;

    /**
     *实际支付金额
     */
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
     *订单状态,总状态
     */
    private Integer state;

    /**
     *卖家状态
     */
    private Integer buyerState;

    /**
     *买家状态
     */
    private Integer sellerState;

    /**
     *订单来源
     */
    private Integer source;

    /**
     *更新时间
     */
    private Date updateTime;

    /**
     *销售id
     */
    private Integer fidSale;

    /**
     *所属项目
     */
    private Integer fidProject;

    /**
     *创建时间
     */
    private Date createTime;
    
    /**
     * 确认时间
     */
    private Date confirmTime;
    
    /**
     * 附加属性
     */
    private List<OrderAttrValueDto> orderAttrValueOutputList;
    
    /**
     * 商品清单
     */
    private List<OrderDetailDto> orderDetailDtoList;
    
    /**
     * 收款记录
     */
    private List<CashierRecordDto> cashierRecordList;
    
    /**
     * 退款记录
     */
    private List<RefundRecordDto> refundRecordDtoList;
    
    /**
     * 操作人id
     */
    private Integer operator;
}

