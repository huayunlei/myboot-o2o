package com.ihomefnt.o2o.intf.manager.constant.program;

import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;

/**
 * 二级分类类型枚举
 */
public enum SoftItemEnum {

    COFFEE_TABLE(682 ,"茶几", StaticResourceConstants.MORE_CHAJI_IMG),

    SOFA (4 ,"沙发",StaticResourceConstants.MORE_ONE_SHAFA_IMG),

    TV_CABINET (5 ,"电视柜",StaticResourceConstants.MORE_DIANSHIGUI_IMG),

    TABLE (688, "餐桌",StaticResourceConstants.MORE_CANZHUO_IMG),

    DINING_CHAIR (15, "餐椅",StaticResourceConstants.MORE_CANYI_IMG),

    BED (19, "床",StaticResourceConstants.MORE_CHUANG_IMG),

    MATTRESS (21, "床垫",StaticResourceConstants.MORE_CHUANGDIAN_IMG),

    BEDSIDE_CUPBOARD (22, "床头柜",StaticResourceConstants.MORE_CHUANGTOUGUI_IMG),

    DENG_JU (5001, "灯具",null);

    private Integer categoryLevelTwoId;

    private String categoryLevelTwoName;

    private String imageUrl;

    SoftItemEnum(Integer categoryLevelTwoId, String categoryLevelTwoName,String imageUrl) {
        this.categoryLevelTwoId = categoryLevelTwoId;
        this.categoryLevelTwoName = categoryLevelTwoName;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCategoryLevelTwoId() {
        return categoryLevelTwoId;
    }

    public void setCategoryLevelTwoId(Integer categoryLevelTwoId) {
        this.categoryLevelTwoId = categoryLevelTwoId;
    }

    public String getCategoryLevelTwoName() {
        return categoryLevelTwoName;
    }

    public void setCategoryLevelTwoName(String categoryLevelTwoName) {
        this.categoryLevelTwoName = categoryLevelTwoName;
    }}
