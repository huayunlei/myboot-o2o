package com.ihomefnt.o2o.intf.manager.constant.home;

import org.apache.commons.lang3.StringUtils;

/**
 * 电子合同类型
 * Author: ZHAO
 * Date: 2018年4月16日
 */
public enum ElectronicContractTypeEnum {
	SUBSCRIBE_AGREEMENT(0, "全品家定金协议书", "SUBSCRIBE_AGREEMENT"),
	INCREASE_DEPOSIT(1, "增缴定金申请", "INCREASE_DEPOSIT"),
	SERVICE_CONTRACT(2, "置家服务合同", "SERVICE_CONTRACT"),
    QUOTATION(3, "附件一：报检单", "CHECK_LIST"),
    SOFT_LIST(4, "附件二：软装清单", "SOFT_LIST"),
    HARD_LIST(5, "附件二：硬装清单", "HARD_LIST"),
    SERVICE_DESCRIPTION(6, "附件三：服务说明", "SERVICE_DESCRIPTION");

	private Integer code;

	private String description;
	
	private String key;
	
	public static ElectronicContractTypeEnum getElectronicContractTypeEnumByKey(String key){
        if (StringUtils.isBlank(key)) {
            return null;
        }
        ElectronicContractTypeEnum[] values = values();
        for (ElectronicContractTypeEnum contractTypeEnum : values) {
            if (contractTypeEnum.getKey().equals(key)) {
                return contractTypeEnum;
            }
        }
        return null;
    }
	
    private ElectronicContractTypeEnum(Integer code, String description, String key) {
		this.code = code;
		this.description = description;
		this.key = key;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
