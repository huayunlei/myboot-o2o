package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.configItem.vo.response.HttpItemResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
@Data
@NoArgsConstructor
public class HttpOrderDetailResponse {
	private Long orderId;
	private Long orderStatus;
	private Long orderProductAmount;
	private Double orderPrice;
	private Double couponPay;
	private Double totalPay;
	private Double leftMoney;
	
	private String purchaserName;
	private String purchaserTel;
	private String address;
	
	private List<ProductOrderDetail> orderDetails;
	private List<HttpItemResponse> itemList;
	
	private String orderNum;
	private Timestamp createTime;
    private String strDeadLine;
    
    private double alreadyPay;//已分笔支付金额
    private double remainPay;//剩余支付金额
    
    private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键
    
    private Double voucherPay;// 抵用券面值
    
    private String fromApp; //app标识

	public HttpOrderDetailResponse(UserOrder userOrder) {
		this.orderId=userOrder.getOrderId();
		this.orderStatus=userOrder.getOrderStatus();
		this.orderProductAmount=userOrder.getProductCount();
		this.orderPrice=userOrder.getOrderPrice();
		this.couponPay=userOrder.getCouponPay();
		this.address=userOrder.getDeliveryAddress();
		this.purchaserName=userOrder.getReceiptName();
		this.purchaserTel=userOrder.getReceiptTel();
		this.createTime=userOrder.getCreateTime();
		this.orderNum=userOrder.getOrderNum();
        this.strDeadLine = userOrder.getStrDeadLine();
        this.voucherId=userOrder.getVoucherId();
        this.voucherPay=userOrder.getVoucherPay();
	}
}
