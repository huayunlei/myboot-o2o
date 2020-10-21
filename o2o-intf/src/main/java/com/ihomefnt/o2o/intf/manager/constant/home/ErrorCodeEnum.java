package com.ihomefnt.o2o.intf.manager.constant.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanyunxin
 * @create 2019-03-22 20:19
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    ROOM_NOT_EXIST(-1, "空间下架"),
    SOFT_ADD_BAG_SKU_OFF_LIE(-5, "软装增配包sku已下架"),
    SOFT_REPLACE_SKU_OFF_LINE(-8, "软装替换前sku已下架"),
    SOFT_REPLACE_NEW_SKU_OFF_LINE(-10, "软装替换后sku已下架"),

    HARD_ADD_BAG_SKU_OFF_LIE(-6, "硬装增配包sku已下架"),
    HARD_STANDARD_SKU_OFF_LIE(-7, "硬装标准升级项sku已下架"),
    HARD_REPLACE_SKU_NOT_EXIST(-11, "硬装替换前sku下架"),
    HARD_REPLACE_NEW_SKU_OFF_LINE(-14, "硬装替换后sku已下架"),

    HARD_ADD_SKU_OFF_LINE(-16, "新增硬装sku已下架"),
    HARD_ADD_ITEM_CLASS_OFF_LINE(-17, "新增硬装类目下架"),
    WHOLE_HARD_ADD_SKU_OFF_LINE(-20, "全屋新增硬装sku已下架"),
    SOLUTION_OFF_LINE(-22, "方案下架"),
    ;

    private @ApiModelProperty("错误码")
    Integer errorCode;

    @ApiModelProperty("错误信息")
    String errorMsg;
}
