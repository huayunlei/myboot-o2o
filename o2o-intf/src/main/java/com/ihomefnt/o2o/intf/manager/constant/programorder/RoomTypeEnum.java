package com.ihomefnt.o2o.intf.manager.constant.programorder;

import lombok.Data;

/**
 * 空间种类枚举类
 *
 * @author hongjingchao
 */
@Deprecated
public enum RoomTypeEnum {

    /**
     * 为了mybaties EnumOrdinalTypeHandler 能够自动查出枚举值添加，无实际意义
     */
    COMMON(0, "全部", -1, ""),
    /**
     * 为了mybaties EnumOrdinalTypeHandler 能够自动查出枚举值添加，无实际意义
     */

    LIVING_ROOM(1, "客厅", 1, "厅"),

    RESTAURANT(2, "餐厅", 1, "厅"),

    MASTER_ROOM(3, "主卧", 2, "室"),

    SECOND_ROOM(4, "次卧", 2, "室"),

    THIRD_ROOM(5, "第三房", 2, "室"),

    FOURTH_ROOM(6, "第四房", 2, "室"),

    FIFTH_ROOM(7, "第五房", 2, "室"),

    MASTER_BATHROOM(8, "主卫", 4, "卫"),

    SECOND_BATHROOM(9, "客卫", 4, "卫"),

    KITCHEN(10, "厨房", 3, "厨"),

    BALCONY(11, "主阳台", 5, "阳台"),

    SECOND_BALCONY(12, "第二阳台", 5, "阳台"),

    THIRD_HALL(13, "第三厅", 1, "厅"),

    FOURTH_HALL(14, "第四厅", 1, "厅"),

    SIXTH_ROOM(15, "第六房", 2, "室"),

    SEVENTH_ROOM(16, "第七房", 2, "室"),

    SECOND_KITCHEN(17, "第二厨房", 3, "厨"),

    THIRD_BATHROOM(18, "第三卫", 4, "卫"),

    FOURTH_BATHROOM(19, "第四卫", 4, "卫"),

    THIRD_BALCONY(20, "第三阳台", 5, "阳台"),

    FOURTH_BALCONY(21, "第四阳台", 5, "阳台"),

    STORAGE(22, "储藏间", 6, "储藏间"),

    SECOND_STORAGE(23, "第二储藏间", 6, "储藏间"),

    CLOAK(24, "衣帽间", 7, "衣帽间"),

    SECOND_CLOAK(25, "第二衣帽间", 7, "衣帽间"),

    ROOM_WHOLE(26, "全屋", -1, "");

    private Integer code;

    private String description;

    private Integer type;

    private String typeDescription;

    private RoomTypeEnum(Integer code, String description, Integer type, String typeDescription) {
        this.code = code;
        this.description = description;
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
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

    public static int getCode(String description) {
        for (RoomTypeEnum typeEnum : RoomTypeEnum.values()) {
            if (typeEnum.getDescription().equals(description)) {
                return typeEnum.getCode();
            }
        }
        return -1;
    }

    public static int getType(Integer code) {
        for (RoomTypeEnum typeEnum : RoomTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getType();
            }
        }
        return -1;
    }

    public static String getDescription(Integer code) {
        for (RoomTypeEnum typeEnum : RoomTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getDescription();
            }
        }
        return "";
    }

}
