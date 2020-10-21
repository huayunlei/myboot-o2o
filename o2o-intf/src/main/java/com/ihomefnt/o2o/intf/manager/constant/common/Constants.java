package com.ihomefnt.o2o.intf.manager.constant.common;

public final class Constants {
    public static final Integer PAGE_SIZE = 10;
    public static final Integer PAGE_NO = 1;
    public static final Integer FROM = 0;
    public static final Integer ACTIVITY_ONLINE = 1;//1.上线 0.下线
    public static final Integer ACTIVITY_MY = 1;//MY
    public static final Integer ACTIVITY_APP = 1;
    public static final Float ANDROID_MIDDLE_WIDTH = 720.0f;
    public static final Float IPHONE_MIDDLE_WIDTH = 640.0f;
    public static final Integer AD_TOP_POSITION = 1;
    public static final Integer AD_BOTTOM_POSITION = 2;
    public static final Integer AD_LEFT_POSITION = 3;
    public static final Integer AD_RIGHT_POSITION = 4;
    public static final String DOWNLOAD_URL = "http://m.ihomefnt.com/QR/10000";  //app下载url
    public static final String REDIRECT_URL = "http://h5.eqxiu.com/s/u8FrdDfb"; //跳转关于艾佳url

    public static final Integer DEFAULT_WIDTH = 750;

    /**
     * sku type 6硬装
     */
    public static final int SKU_TYPE_HARD = 6;

    /**
     * sku type 7新定制品
     */
    public static final int SKU_TYPE_CUSTOM = 7;

    /**
     * 方案、空间、skuid状态字段 1下架 2变价 3正常
     */
    public static final int COMPARE_STATUS_OFFLINE = 1;
    public static final int COMPARE_STATUS_PRICE_CHANGE = 2;
    public static final int COMPARE_STATUS_NORMAL = 3;

    /**
     * 草稿SKU状态校验：1异常 2正常 特殊异常场景，sku不在原方案中
     */
    public static final int SPECIAL_EXCEPTION_STATUS_EXCEPTION = 1;
    public static final int SPECIAL_EXCEPTION_STATUS_NORMAL = 2;

    /**
     * 草稿空间校验：1 有下架商品 2正常 3 商品变价
     */
    public static final int INSIDE_SKU_STATUS_OFFLINE = 1;
    public static final int INSIDE_SKU_STATUS_NORMAL = 2;
    public static final int INSIDE_SKU_STATUS_PRICE_CHANGE = 3;

    /**
     * 软硬装选择状态 0：默认项；1：新增；2：删除；3：修改
     */
    public static final int ITEM_STATUS_DEFAULT = 0;
    public static final int ITEM_STATUS_SELECTED = 3;
    public static final int ITEM_STATUS_ADD = 1;
    public static final int ITEM_STATUS_DELETE = 2;

    /**
     * 草稿签约状态：1已签约；2未签约；3历史签约; 4最新草稿
     */
    public static final int DRAFT_SIGN_STATUS_HAS_SIGN = 1;
    public static final int DRAFT_SIGN_STATUS_NO_SIGN = 2;
    public static final int DRAFT_SIGN_STATUS_HISTORY_SIGNED = 3;
    public static final int DRAFT_SIGN_STATUS_LATEST = 4;

    public static final int WHOLE_HARD_ONE_PARENT = 0;

    /**
     * 仅供参考提示 0 不提示 1提示
     */
    public static final int REFERENCE_ONLY_NO_SHOW = 0;
    public static final int REFERENCE_ONLY_SHOW = 1;

    /**
     * 新定制品
     */
    public static final int CUSTOMIZED_ROOT_CATEGORY_ID = 104000;
    public static final String CUSTOMIZED_ROOT_CATEGORY_NAME = "定制家具";//商品中心为'新定制品'，app转成 定制家具 20190705

}
