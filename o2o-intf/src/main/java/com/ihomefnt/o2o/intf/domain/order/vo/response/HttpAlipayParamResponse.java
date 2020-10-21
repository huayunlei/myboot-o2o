package com.ihomefnt.o2o.intf.domain.order.vo.response;

public class HttpAlipayParamResponse {

	private String partner;//签约合作者身份ID
	private String sellerId;//签约卖家支付宝账号
	private String outTradeNo;//商户网站唯一订单号
	private String subject;//商品名称
	private String body;//商品详情
	private String totalFee;//商品金额
	private String notifyUrl;//服务器异步通知页面路径
	private String service;//服务接口名称， 固定值
	private String paymentType;//支付类型， 固定值
	private String inputCharset;//参数编码， 固定值
	private String itbpay;//未付款交易的超时时间
	private String returnUrl;//支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	
	public HttpAlipayParamResponse(String partner, String sellerId,
			String outTradeNo, String subject, String body, String totalFee,
			String notifyUrl) {
		super();
		this.partner = partner;
		this.sellerId = sellerId;
		this.outTradeNo = outTradeNo;
		this.subject = subject;
		this.body = body;
		this.totalFee = totalFee;
		this.notifyUrl = notifyUrl;
		this.service = "mobile.securitypay.pay";
		this.paymentType = "1";
		this.inputCharset = "utf-8";
		this.itbpay = "7d";
		this.returnUrl = "m.alipay.com";
	}
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	public String getItbpay() {
		return itbpay;
	}
	public void setItbpay(String itbpay) {
		this.itbpay = itbpay;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
}
