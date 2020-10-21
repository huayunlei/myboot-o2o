package com.ihomefnt.o2o.intf.domain.user.dto;

/**
 * 实名认证结果
 * @author ZHAO
 */
public enum CertificationResultEnum {
	SUCCESS(1000, 1L, "恭喜您，实名认证成功"), FAILED(1001, 2L, "认证信息有误，请核对后重新输入"), NOTEXIST(1002, 3L, "请输入正确的身份证号码"),
	SERVICEEXCEPTION(1003, 4L, "第三方服务器异常，请稍后再试"), OUTSERVICE(1004, 5L, "服务器维护中，请稍后再试"), MONEYNOTENOUGH(1005, 6L, "验证接口受限，请稍后再试"),
	PARAMILLEGAL(1006, 7L, "验证参数异常，请稍后再试"), HASAUTHENTICATION(1007, 8L, "已认证"), AUTHENTICATIONLIMIT(1008, 9L, "认证超过次数限制，请明天再试");

	private Integer code;//后端返回结果
	
	private Long result;//返回给前端的code
	
	private String value;//返回给前端的value

	public static CertificationResultEnum getValue(Integer code){
		for(CertificationResultEnum certificationResultEnum : CertificationResultEnum.values()){
			if(certificationResultEnum.getCode().equals(code)){
				return certificationResultEnum;
			}
		}
		return FAILED;
	}
	
	CertificationResultEnum(Integer code, Long result, String value){
		this.code = code;
		this.result = result;
		this.value = value;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	 
}
