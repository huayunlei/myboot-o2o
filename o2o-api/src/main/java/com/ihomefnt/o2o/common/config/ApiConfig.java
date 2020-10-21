package com.ihomefnt.o2o.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

	@Value("${pcHost}")
	public String pcHost;
	@Value("${ihome.api}")
	private String ihomeApi;
	@Value("${ihome.api.account.queryAjbAccountByUserId}")
	private String ihomeApiAjbAccount;

	@Value("${ihome.api.accountBook.queryBookRecords}")
	private String ihomeApiBookRecords;
	@Value("${ihome.api.settlement.cancelPay}")
	private String ihomeApiCancelPay;

	// 冻结艾积分
	@Value("${ihome.api.settlement.freezePay}")
	private String ihomeApiSettlementFreezePay;
	// 确认收到艾积分
	@Value("${ihome.api.settlement.confirmPay}")
	private String ihomeApiSettlementConfirmPay;

	// 艾积分充值(已废弃)
	@Value("${ihome.api.settlement.recharge}")
	private String ihomeApiSettlementRecharge;

	// 乐橙url
	@Value("${lechange.api.base}")
	private String lechangeBaseUrl;

	// 硬装订单信息接口
	@Value("${hbms.api.order.detail}")
	private String hbmsOrderDetail;

	// 文旅商品列表接口
	@Value("${ihome.api.trip.productlist}")
	private String tripProductList;

	// 根据商品id获取商户信息
	@Value("${ihome.api.seller.sellerByProductId}")
	private String sellerByProductId;

	@Value("${ihome.api.trip.product}")
	private String tripProduct;

	// app支付宝支付
	@Value("${account.api.app.alipay}")
	private String appAlipay;

	// app支付宝支付回调
	@Value("${account.api.app.alipay.receive}")
	private String appAlipayReceive;

	// app微信支付
	@Value("${account.api.app.wechatpay}")
	private String appWechatpay;

	// app微信回调
	@Value("${account.api.app.wechatpay.receive}")
	private String appWechatpayReceive;

	// 文旅商品生成消费码
	@Value("${account.api.trip.generatordercode}")
	private String tripGeneratOrderCode;

	// 文旅商品消费一维码
	@Value("${account.api.trip.usergeneratordercode}")
	private String tripUseGeneratOrderCode;

	@Value("${account.api.trip.getgeneratorderlist}")
	private String tripGeneratOrderList;

	// 根据子订单id获取父订单
	@Value("${ihome.api.pay.beforerecord}")
	private String payBeforeRecord;

	// 查询订单账目信息
	@Value("${ihome.api.pay.orderpaybalance}")
	private String orderPayBalance;

	// 查询支付明细
	@Value("${ihome.api.pay.finishrecord}")
	private String payFinishRecord;

	@Value("${account.api.h5.alipay}")
	private String h5Alipay;

	@Value("${account.api.h5.wechatpay}")
	private String h5Wechatpay;

	// 乐橙根据订单id获取设备列表
	@Value("${lechange.api.device.list.orderid}")
	private String lechangeDeviceListByOrderId;

	// 获取boss端设备列表
	@Value("${lechange.api.device.list}")
	private String lechangeDeviceList;
	
	@Value("${env.open.tag.show}")
	private String openTagShow;

	public String getPcHost() {
		return pcHost;
	}

	public void setPcHost(String pcHost) {
		this.pcHost = pcHost;
	}

	public String getIhomeApi() {
		return ihomeApi;
	}

	public void setIhomeApi(String ihomeApi) {
		this.ihomeApi = ihomeApi;
	}

	public String getIhomeApiAjbAccount() {
		return ihomeApiAjbAccount;
	}

	public void setIhomeApiAjbAccount(String ihomeApiAjbAccount) {
		this.ihomeApiAjbAccount = ihomeApiAjbAccount;
	}

	public String getIhomeApiBookRecords() {
		return ihomeApiBookRecords;
	}

	public void setIhomeApiBookRecords(String ihomeApiBookRecords) {
		this.ihomeApiBookRecords = ihomeApiBookRecords;
	}

	public String getIhomeApiCancelPay() {
		return ihomeApiCancelPay;
	}

	public void setIhomeApiCancelPay(String ihomeApiCancelPay) {
		this.ihomeApiCancelPay = ihomeApiCancelPay;
	}

	public String getIhomeApiSettlementFreezePay() {
		return ihomeApiSettlementFreezePay;
	}

	public void setIhomeApiSettlementFreezePay(String ihomeApiSettlementFreezePay) {
		this.ihomeApiSettlementFreezePay = ihomeApiSettlementFreezePay;
	}

	public String getIhomeApiSettlementConfirmPay() {
		return ihomeApiSettlementConfirmPay;
	}

	public void setIhomeApiSettlementConfirmPay(String ihomeApiSettlementConfirmPay) {
		this.ihomeApiSettlementConfirmPay = ihomeApiSettlementConfirmPay;
	}

	public String getLechangeBaseUrl() {
		return lechangeBaseUrl;
	}

	public void setLechangeBaseUrl(String lechangeBaseUrl) {
		this.lechangeBaseUrl = lechangeBaseUrl;
	}

	public String getHbmsOrderDetail() {
		return hbmsOrderDetail;
	}

	public void setHbmsOrderDetail(String hbmsOrderDetail) {
		this.hbmsOrderDetail = hbmsOrderDetail;
	}

	public String getTripProductList() {
		return tripProductList;
	}

	public void setTripProductList(String tripProductList) {
		this.tripProductList = tripProductList;
	}

	public String getSellerByProductId() {
		return sellerByProductId;
	}

	public void setSellerByProductId(String sellerByProductId) {
		this.sellerByProductId = sellerByProductId;
	}

	public String getTripProduct() {
		return tripProduct;
	}

	public void setTripProduct(String tripProduct) {
		this.tripProduct = tripProduct;
	}

	public String getAppAlipay() {
		return appAlipay;
	}

	public void setAppAlipay(String appAlipay) {
		this.appAlipay = appAlipay;
	}

	public String getAppAlipayReceive() {
		return appAlipayReceive;
	}

	public void setAppAlipayReceive(String appAlipayReceive) {
		this.appAlipayReceive = appAlipayReceive;
	}

	public String getAppWechatpay() {
		return appWechatpay;
	}

	public void setAppWechatpay(String appWechatpay) {
		this.appWechatpay = appWechatpay;
	}

	public String getAppWechatpayReceive() {
		return appWechatpayReceive;
	}

	public void setAppWechatpayReceive(String appWechatpayReceive) {
		this.appWechatpayReceive = appWechatpayReceive;
	}

	public String getTripGeneratOrderCode() {
		return tripGeneratOrderCode;
	}

	public void setTripGeneratOrderCode(String tripGeneratOrderCode) {
		this.tripGeneratOrderCode = tripGeneratOrderCode;
	}

	public String getTripUseGeneratOrderCode() {
		return tripUseGeneratOrderCode;
	}

	public void setTripUseGeneratOrderCode(String tripUseGeneratOrderCode) {
		this.tripUseGeneratOrderCode = tripUseGeneratOrderCode;
	}

	public String getTripGeneratOrderList() {
		return tripGeneratOrderList;
	}

	public void setTripGeneratOrderList(String tripGeneratOrderList) {
		this.tripGeneratOrderList = tripGeneratOrderList;
	}

	public String getIhomeApiSettlementRecharge() {
		return ihomeApiSettlementRecharge;
	}

	public void setIhomeApiSettlementRecharge(String ihomeApiSettlementRecharge) {
		this.ihomeApiSettlementRecharge = ihomeApiSettlementRecharge;
	}

	public String getPayBeforeRecord() {
		return payBeforeRecord;
	}

	public void setPayBeforeRecord(String payBeforeRecord) {
		this.payBeforeRecord = payBeforeRecord;
	}

	public String getOrderPayBalance() {
		return orderPayBalance;
	}

	public void setOrderPayBalance(String orderPayBalance) {
		this.orderPayBalance = orderPayBalance;
	}

	public String getPayFinishRecord() {
		return payFinishRecord;
	}

	public void setPayFinishRecord(String payFinishRecord) {
		this.payFinishRecord = payFinishRecord;
	}

	public String getH5Alipay() {
		return h5Alipay;
	}

	public void setH5Alipay(String h5Alipay) {
		this.h5Alipay = h5Alipay;
	}

	public String getH5Wechatpay() {
		return h5Wechatpay;
	}

	public void setH5Wechatpay(String h5Wechatpay) {
		this.h5Wechatpay = h5Wechatpay;
	}

	public String getLechangeDeviceListByOrderId() {
		return lechangeDeviceListByOrderId;
	}

	public void setLechangeDeviceListByOrderId(String lechangeDeviceListByOrderId) {
		this.lechangeDeviceListByOrderId = lechangeDeviceListByOrderId;
	}

	public String getLechangeDeviceList() {
		return lechangeDeviceList;
	}

	public void setLechangeDeviceList(String lechangeDeviceList) {
		this.lechangeDeviceList = lechangeDeviceList;
	}

	public String getOpenTagShow() {
		return openTagShow;
	}

	public void setOpenTagShow(String openTagShow) {
		this.openTagShow = openTagShow;
	}
	
}
