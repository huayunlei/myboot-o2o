package com.ihomefnt.o2o.intf.manager.constant.programorder;

/**
 * 支付宝、微信支付结果
 * @author ZHAO
 */
public enum PaymentStatus {
	antFailed(1,"4000","[支付宝]支付失败"),antCancel(1,"6001","[支付宝]用户中途取消"),antNetwork(1,"6002","[支付宝]网络连接出错"),
	wechartError(2,"-1","[微信]出错"),wechartCancel(2,"-2","[微信]用户取消"),wechartPayFailed(2,"-3","[微信]支付失败"),wechartAccreditFailed(2,"-4","[微信]授权失败"),wechartJNI(2,"-5","[微信]不支持");
	
	private Integer payType;//支付类型：1支付宝  2微信
	
	private String resultStatus;//支付结果编码
	
	private String resultDesc;//支付结果说明
	
	public static String getResultDesc(Integer payType, String resultStatus){
		for (PaymentStatus paymentStatus : PaymentStatus.values()) {
			if(paymentStatus.getPayType().equals(payType) && paymentStatus.getResultStatus().equals(resultStatus)){
				return paymentStatus.getResultDesc();
			}
		}
		return "";
	}
	
	PaymentStatus(Integer payType, String resultStatus, String resultDesc){
		this.payType = payType;
		this.resultDesc = resultDesc;
		this.resultStatus = resultStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
}
