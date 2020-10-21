package com.ihomefnt.o2o.service.proxy.dms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DmsProjectAppStatusEnums {

    /**
     * 准备开工
     */
    INIT(0, "准备开工"),
    /**
     * 开工交底
     */
    START_CHECK(1, "开工阶段"),
    /**
     * 水电验收
     */
    HYDROPOWER_CHECK(2, "水电阶段"),

    /**
     * 客户自施工项目阶段
     */
    CUSTOM_ITEM_CHECK(3,"自施工阶段"),
    /**
     * 瓦木验收
     */
    BUILDING_CHECK(4, "瓦木阶段"),
    /**
     * 竣工验收
     */
    FINISH_CHECK(5, "竣工阶段"),
    /**
     * 施工完成
     */
    CLOSED(6, "已竣工");

    private Integer code;

    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    DmsProjectAppStatusEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getStatusDescription(Integer code) {
        if (code != null) {
            for (DmsProjectAppStatusEnums element : values()) {
                if (element.getCode() == code) {
                    return element.getDescription();
                }
            }
        }
        return null;
    }
}
