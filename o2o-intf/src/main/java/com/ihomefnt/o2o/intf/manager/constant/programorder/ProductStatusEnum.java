package com.ihomefnt.o2o.intf.manager.constant.programorder;

/**
 * Created by Administrator on 2017/7/18.
 */
public enum ProductStatusEnum {

    NEED_PAYING(-1, "待交付"),
    NEED_PURCHASE(0, "待采购"),
    IN_PURCHASE(1, "采购中"),
    NEED_DELIVERY(2, "待送货"),
    IN_DELIVERY(3, "送货中"),
    COMPLETE_DELIVERY(4, "送货完成"),
    WAIT_INSTALL(5,"待安装"),
    WAIT_CHECK(6,"待验收"),
    COMPLETE(8,"已完成"),
    CANCELED(7, "已取消"),
    ;

    private Integer code;

    private String description;

    private ProductStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Integer code) {
        if (null == code) {
            return null;
        }
        for (ProductStatusEnum value : ProductStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    public static ProductStatusEnum getEnumByStatus(Integer code){
        ProductStatusEnum[] values = values();
        for (ProductStatusEnum value : values) {
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
