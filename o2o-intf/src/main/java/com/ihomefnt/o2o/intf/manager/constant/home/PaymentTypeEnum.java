package com.ihomefnt.o2o.intf.manager.constant.home;

/**
 * 全品家订单款项类型
 * Author: ZHAO
 * Date: 2018年7月20日
 */
public enum PaymentTypeEnum {
	EARNEST_MONEY(14, "诚意金"),
    DEPOSIT_MONEY(15, "定金"),
    CONTRACT_MONEY(16, "合同额"),
    INTEREST_MONEY(17, "返息费用"),
    DISCOUNT_MONEY(18, "折扣费用"),
    INTEGRAL_MONEY(19, "积分兑换");

    private Integer code;

    private String description;

    private PaymentTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Integer code) {
        if (null == code) {
            return null;
        }
        PaymentTypeEnum[] values = values();
        for (PaymentTypeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    public static PaymentTypeEnum getEnumByCode(Integer code){
        PaymentTypeEnum[] values = values();
        for (PaymentTypeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
