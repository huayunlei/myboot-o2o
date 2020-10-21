/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2017/3/14
 * Description:RoomUseEnum.java
 */
package com.ihomefnt.o2o.intf.manager.constant.programorder;

/**
 * @author 闫辛未
 */
public enum RoomUseEnum {
    /**
     * 为了mybaties EnumOrdinalTypeHandler 能够自动查出枚举值添加，无实际意义
     */
    COMMON(0, "全部", -1, ""),
    /**
     * 为了mybaties EnumOrdinalTypeHandler 能够自动查出枚举值添加，无实际意义
     */

    LIVING_ROOM(1, "客厅", 1, "厅"),

    MASTER_ROOM(2, "主卧", 2, "室"),

    SECOND_ROOM(3, "次卧", 2, "室"),

    CHILDREN_ROOM(4, "儿童房", 2, "室"),

    STUDY_ROOM(5, "书房", 2, "室"),

    RESTAURANT(6, "餐厅", 1, "厅"),

    ENTRANCE(7, "玄关", 2, "室"),//已停用

    KITCHEN(8, "厨房", 3, "厨"),

    ELDERLY_ROOM(9, "老人房", 2, "室"),

    BALCONY(10, "生活阳台", 5, "阳台"),

    CLOAK_ROOM(11, "衣帽间", 7, "衣帽间"),

    TATAMI_ROOM(12, "榻榻米房", 2, "室"),

    BATHROOM(13, "卫生间", 4, "卫"),

    CORRIDOR(14, "走廊", -1, ""),//已停用

    SITTING_ROOM(15, "起居室", 2, "室"),

    GUEST_ROOM(16, "客人房", 2, "室"),

    GYM(17, "健身房", 2, "室"),

    MASTER_BATHROOM(18, "主卫", 4, "卫"),

    SECOND_BATHROOM(19, "客卫", 4, "卫"),

    REST_BALCONY(20, "休闲阳台", 5, "阳台"),

    ROOM_BALCONY(21, "卧室阳台", 5, "阳台"),

    STORAGE_ROOM(22, "储藏间", 6, "储藏间"),

    ROOM_TEA(23, "茶室", 2, "室"),

    ROOM_MULTIPLE_FUNC(24, "多功能室", 2, "室"),

    ROOM_WHOLE(25,"全屋", -1, "");

    private final static String roomMultipleFunc = "多功能房";//dolly跟app的枚举是多功能室，订单的枚举是多功能房

    private Integer code;

    private String description;

    private Integer type;

    private String typeDescription;

    private RoomUseEnum(Integer code, String description, Integer type, String typeDescription) {
        this.code = code;
        this.description = description;
        this.type = type;
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

    public static String getDescription(Integer code) {
        RoomUseEnum[] values = values();
        for (RoomUseEnum value : values) {
            if (value.code.equals(code)) {
                return value.getDescription();
            }
        }
        return "";
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

    public static Integer getCode(String description) {
        RoomUseEnum[] values = values();
        for (RoomUseEnum value : values) {
            if (value.description.equals(description)) {
                return value.getCode();
            }
            if (roomMultipleFunc.equals(description)) {
                return ROOM_MULTIPLE_FUNC.code;
            }
        }
        return null;
    }

    public static int getType(Integer code) {
        for (RoomUseEnum typeEnum : RoomUseEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getType();
            }
        }
        return -1;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
