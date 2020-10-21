package com.ihomefnt.o2o.intf.manager.constant;

/**
 * 个人中心常量
 *
 * @author liyonggang
 * @create 2019-02-22 17:18
 */
public interface PersonalCenterConstants {
    /**
     * 资源key
     */
    class ResouseKey {
        /**
         * 个人中心顶级父菜单
         */
        public static final String TOP = "TOP";
        /**
         * 用户中心
         */
        public static final String USER_CENTER = "USER_CENTER";

        /**
         * 功能列表
         */
        public static final String FUCTION_LIST = "FUCTION_LIST";
        /**
         * 全品家
         */
        public static final String QPJ = "QPJ";
        /**
         * 云画屏
         */
        public static final String YHP = "YHP";
        /**
         * 购物车
         */
        public static final String USER_SHOPPING_CART = "USER_SHOPPING_CART";
        /**
         * 艾积分
         */
        public static final String AJF = "AJF";
        /**
         * 我的福利没有邀请码的资源key
         */
        public static final String MY_WELFARE_FOR_NEW_NO_CODE = "MY_WELFARE_FOR_NEW_NO_CODE";
        /**
         * 我的福利老用户资源key
         */
        public static final String MY_WELFARE_FOR_OLD = "MY_WELFARE_FOR_OLD";
        /**
         * 我的福利有邀请码的资源key
         */
        public static final String MY_WELFARE_FOR_NEW_HAS_CODE = "MY_WELFARE_FOR_NEW_HAS_CODE";
        /**
         * 我的房产
         */
        public static final String MY_HOUSE = "MY_HOUSE";
        /**
         * 我的订单
         */
        public static final String MY_ORDER = "MY_ORDER";
        /**
         * 设计方案
         */
        public static final String DESION_PROGRAM = "DESION_PROGRAM";
        /**
         * 款项记录
         */
        public static final String FUND_RECORD = "FUND_RECORD";
        /**
         * 我的合同
         */
        public static final String MY_CONTRACT = "MY_CONTRACT";
        /**
         * 我的权益
         */
        public static final String MY_RIGHTS = "MY_RIGHTS";
        /**
         * 我的工地
         */
        public static final String MY_SCENE = "MY_SCENE";
        /**
         * 项目方案
         */
        public static final String PROJECT_PROGRAM = "PROJECT_PROGRAM";
        /**
         * 极速报修
         */
        public static final String QUICK_REPAIRS = "QUICK_REPAIRS";
        /**
         * 艺术画廊
         */
        public static final String ART_GALLERY = "ART_GALLERY";
        /**
         * 我的画屏
         */
        public static final String MY_PAINTED_SCREEN = "MY_PAINTED_SCREEN";
        /**
         * 设备管理
         */
        public static final String FACILITY_MANAGE = "FACILITY_MANAGE";
        /**
         * 遥控器
         */
        public static final String REMOTE_CONTROL = "REMOTE_CONTROL";
        /**
         * BRAND
         */
        public static final String BRAND = "BRAND";
        /**
         * 设置
         */
        public static final String SETTING = "SETTING";
        /**
         * 消息中心
         */
        public static final String MESSAGE_CENTER = "MESSAGE_CENTER";
        /**
         * TOC
         */
        public static final String TOC = "TOC";

        /**
         * 顶部菜单
         */
        public static final String TOP_MENU = "TOP_MENU";

        /**
         * 收银台
         */
        public static final String CHECKOUT = "CHECKOUT";
    }

    /**
     * 字典值
     */
    class DicKey {
        /**
         * 置家顾问电话池key前缀(固定)
         */
        public static final String ADVISER_MOBILE_PREFIX = "ADVISER_MOBILE_";

        /**
         * 默认头像地址
         */
        public static final String DEFAULT_AVATAR_IMAGE = "DEFAULT_AVATAR_IMAGE";

    }

    /**
     * 角色值
     */
    class Role {
        /**
         * 所有人
         */
        public static final String ALL = "ALL";

    }

    /**
     * 用户
     */
    class User {
        public static final String DEFAULT_NICKNAME = "完善昵称";
    }

    /**
     * 版本控制相关
     */
    class Version {
        /**
         * 全部
         */
        public static final String ALL = "ALL";

        /**
         * 没有
         */
        public static final String NONE = "NONE";
    }

    /**
     * 资源类型 1: banner,2:icon,3:目录
     */
    class ResouseType {
        /**
         * banner
         */
        public static final Integer BANNER = 1;

        /**
         * icon
         */
        public static final Integer ICON = 2;

        /**
         * 目录
         */
        public static final Integer CATEGORY = 3;

    }
}
