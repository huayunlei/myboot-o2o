package com.ihomefnt.o2o.intf.manager.constant.program;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品方案文案说明
 *
 * @author ZHAO
 */
public interface ProductProgramPraise {

    String HARD_SOFT = "硬装+软装";

    String SERIES = "套系";

    String STYLE = "风格";

    String DISCOUNT = "%";

    String BIND_DRAW = "BIND_DRAW"; // 客餐厅阳台

    String BIND_KITCHEN = "BIND_KITCHEN";// 厨房卫生间

    String BIND_DESC = "必须为同一套系";

    String BIND_TEXT = "为了保证装修的品质，";

    String BIND_PRAISE = "为了保证装修的品质，客餐厅必须为同一套系，厨房卫生间必须为同一套系。";

    String CHAMBER = "室";

    String HALL = "厅";

    String TOILET = "卫";

    String KITCHEN = "厨";

    String BALCONY = "阳台";

    String AREA = "㎡";

    String MOBILE_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    String MOBILE_REPLACE = "$1****$2";

    String BANK_CARD_REGEX = "(?<=\\d{4})\\d(?=\\d{4})";

    String ID_CARD_REGEX = "(?<=\\d{3})\\d(?=\\w{4})";

    String ADVISER_MOBILE_DEFAULT = "4008009360";//老号码4009699360

    Integer PAY_STAGE_ADVANCE_CODE = 1;

    Integer PAY_STAGE_INITIAL_CODE = 2;

    Integer PAY_STAGE_INTERIM_CODE = 3;

    Integer PAY_STAGE_FINAL_CODE = 4;

    String PAY_STAGE_INITIAL_DESC = "首付款";

    String PAY_STAGE_INTERIM_DESC = "中期款";

    String PAY_STAGE_FINAL_DESC = "尾款";

    Integer BOSS_STAGE_ADVANCE_CODE = 2; // BOSS支付阶段状态 定金

    Integer BOSS_STAGE_INITIAL_CODE = 3;// BOSS支付阶段状态 首付款

    Integer BOSS_STAGE_INTERIM_CODE = 8;// BOSS支付阶段状态 中期款

    Integer BOSS_STAGE_FINAL_CODE = 4;// BOSS支付阶段状态 尾款

    BigDecimal PAY_STAGE_INITIAL_RATE = BigDecimal.valueOf(0.5);// 首付款支付比例50%

    Integer HARD_STANDARD_ALL = 0;// 硬装+软装

    Integer HARD_STANDARD_SOFT = 1;// 纯软装

    String HARD_STANDARD_SOFT_DESC = "纯软装";// 纯软装

    String HARD_STANDARD_SOFT_PRAISE = "STANDARD_SOFT_PRAISE";// 纯软装 描述文案

    String HARD_STANDARD_SOFT_PRAISE_DESC = "本方案的硬装，是由开发商精装修交付，期待同样是美美的。";// 纯软装
    // 描述文案

    String HOUSE_NAME_BBC = "BBC";// 楼盘名称

    Integer FURNITURE_TYPE_FINISHED = 0;// 家具类型 成品家具

    Integer FURNITURE_TYPE_ORDER = 1;// 家具类型 定制家具

    Integer FURNITURE_TYPE_GIFT = 2;// 家具类型 赠品家具

    Integer FURNITURE_TYPE_NEW = 3;// 家具类型 新定制品

    String FURNITURE_TYPE_ORDER_DESC = "[定制]";// 家具类型 定制

    String FURNITURE_ORDER_DESC_1 = "【艾佳定制】";// 家具类型 定制

    String FURNITURE_ORDER_DESC_2 = "【艾佳标准定制】";// 家具类型 定制

    Integer ALADDIN_ORDER_TYPE_SUIT = 1;// 全品家订单方案类型：1整套方案 2自由搭配

    Integer ALADDIN_ORDER_TYPE_ROOM = 2;// 全品家订单方案类型：1整套方案 2自由搭配

    // 全品家订单类型
    Integer ALADDIN_ORDER_TYPE_SUIT_HARDSOFT = 11;// 套装、硬装+软装

    Integer ALADDIN_ORDER_TYPE_SUIT_SOFT = 12;// 套装、纯软装

    Integer ALADDIN_ORDER_TYPE_ROOM_HARDSOFT = 13;// 自由搭配、硬装+软装

    Integer ALADDIN_ORDER_TYPE_ROOM_SOFT = 14;// 自由搭配、纯软装

    // 全品家订单状态
    Integer ALADDIN_ORDER_STATUS_TOUCH = 1;// 接触阶段

    Integer ALADDIN_ORDER_STATUS_PURPOSE = 2;// 意向阶段

    Integer ALADDIN_ORDER_STATUS_HANDSEL = 3;// 定金阶段

    Integer ALADDIN_ORDER_STATUS_SIGN = 4;// 签约阶段

    Integer ALADDIN_ORDER_STATUS_DELIVERY = 5;// 交付中

    Integer ALADDIN_ORDER_STATUS_FINISH = 6;// 已完成

    Integer ALADDIN_ORDER_STATUS_CANCEL = 7;// 已取消

    String ALADDIN_ORDER_STATUS_TOUCH_DESC = "接触阶段";// 接触阶段

    String ALADDIN_ORDER_STATUS_PURPOSE_DESC = "意向阶段";// 意向阶段

    String ALADDIN_ORDER_STATUS_HANDSEL_DESC = "定金阶段";// 定金阶段

    String ALADDIN_ORDER_STATUS_SIGN_DESC = "签约阶段";// 签约阶段

    String ALADDIN_ORDER_STATUS_DELIVERY_DESC = "交付中";// 交付中

    String ALADDIN_ORDER_STATUS_FINISH_DESC = "已完成";// 已完成

    String ALADDIN_ORDER_STATUS_CANCEL_DESC = "已取消";// 已取消

    // 全品家硬装订单状态
    Integer ALADDIN_HARD_ORDER_STATUS_WAITPAY = 0;// 待付款

    Integer ALADDIN_HARD_ORDER_STATUS_WAITALLOT = 1;// 待分配

    Integer ALADDIN_HARD_ORDER_STATUS_WAITSCHEDULE = 2;// 待排期

    Integer ALADDIN_HARD_ORDER_STATUS_WAITCONSTRUCT = 3;// 待施工

    Integer ALADDIN_HARD_ORDER_STATUS_INCONSTRUCT = 4;// 施工中

    Integer ALADDIN_HARD_ORDER_STATUS_FINISH = 5;// 已完成

    Integer ALADDIN_HARD_ORDER_STATUS_CANCEL = 6;// 已取消

    // 全品家软装订单状态
    Integer ALADDIN_SOFT_ORDER_STATUS_WAITPAY = 0;// 待付款

    Integer ALADDIN_SOFT_ORDER_STATUS_WAITPURCHASE = 1;// 待采购

    Integer ALADDIN_SOFT_ORDER_STATUS_INPURCHASE = 2;// 采购中

    Integer ALADDIN_SOFT_ORDER_STATUS_WAITDELIVERY = 3;// 待配送

    Integer ALADDIN_SOFT_ORDER_STATUS_INDELIVERY = 4;// 配送中

    Integer ALADDIN_SOFT_ORDER_STATUS_FINISH = 5;// 已完成

    Integer ALADDIN_SOFT_ORDER_STATUS_CANCEL = 6;// 已取消

    // 空间分类类型(1厅2室3厨4卫5阳台6储藏间7衣帽间)
    // 空间描述是设计描述
    List<Integer> SPACE_CATEGORY_HARDSOFT_LIST = new ArrayList<Integer>() {
        {
            add(1);// 厅
            add(2);// 室
        }
    };

    // 空间描述是硬装描述
    List<Integer> SPACE_CATEGORY_HARD_LIST = new ArrayList<Integer>() {
        {
            add(3);// 厨
            add(4);// 卫
            add(5);// 阳台
            add(6);// 储藏间
            add(7);// 衣帽间
        }
    };

    // 空间描述类型
    Integer SPACE_DESC_TYPE_DESIGN = 1;// 设计描述

    Integer SPACE_DESC_TYPE_HARD = 2;// 硬装描述

    // 产品方案下单权限
    Integer PROGRAM_LIMIT_NO = 1;// 无资质

    Integer PROGRAM_LIMIT_LOOK = 2;// 可以看

    Integer PROGRAM_LIMIT_SELECT = 3;// 可以选择下单

    String PROGRAM_BUTTON_LOOK = "浏览方案";// 可以看

    String PROGRAM_BUTTON_SELECT = "选择方案";// 可以选择下单

    String HARD_STANDARD_SPACE_FULLHOUSE = "全屋";

    String FREE_MATCH_PROGRAM = "自由搭配方案";

    // 客厅
    String SPACE_CATEGORY_TYPE_HALL_DESC = "客厅";

    List<String> SPACE_CATEGORY_TYPE_HALL = new ArrayList<String>() {
        {
            add("起居室");
        }
    };

    // 次卧
    String SPACE_CATEGORY_TYPE_BEDROOM_DESC = "次卧";

    List<String> SPACE_CATEGORY_TYPE_BEDROOM = new ArrayList<String>() {
        {
            add("客人房");
            add("榻榻米房");
            add("健身房");
            add("储藏间");
            add("衣帽间");
        }
    };

    // 卫生间
    String SPACE_CATEGORY_TYPE_TOILET_DESC = "卫生间";

    List<String> SPACE_CATEGORY_TYPE_TOILET = new ArrayList<String>() {
        {
            add("主卫");
            add("客卫");
        }
    };

    // 阳台
    String SPACE_CATEGORY_TYPE_BALCONY_DESC = "阳台";

    List<String> SPACE_CATEGORY_TYPE_BALCONY = new ArrayList<String>() {
        {
            add("生活阳台");
            add("休闲阳台");
            add("卧室阳台");
        }
    };

    String FURNITURE_SIZE = "1*1*1";// 家具尺寸

    String FURNITURE_SIZE_DESC = "依据现场尺寸定制";// 家具尺寸定制说明

    String FURNITURE_SIZE_TEXT = "-";

    List<String> KEYWORD_ART_LIST = new ArrayList<String>() {
        {
            add("艺术品");
            add("艺术家");
        }
    };

    String KEYWORD_REFUND_DESC = "退款说明";// 退款说明

    String KEYWORD_PRESIGN_DESC = "提交签约说明";

    String KEYWORD_ALL_MONEY_DESC = "全款签约说明";

    String KEYWORD_UNALL_MONEY_DESC = "非全款签约说明";

    // 全品家订单退款信息退款状态
    Integer ALADDIN_ORDER_REFUND_STATUS_WAIT_APPROVAL = 10;// 待审批退款

    Integer ALADDIN_ORDER_REFUND_STATUS_WAIT_REFUND = 11;// 已审批待退款

    Integer ALADDIN_ORDER_REFUND_STATUS_REFUNDED = 12;// 已审批已退款

    // 退款状态扭转
    Integer ALADDIN_ORDER_REFUND_STATUS_REFUND_WAIT = 1;// 已审批待退款

    Integer ALADDIN_ORDER_REFUND_STATUS_REFUND_FINISH = 2;// 已审批已退款

    Integer ORDER_EXIST_REFUND_CODE = 10;// 存在退款申请

    // 支付类型：0待确认收款 1已确认收款 2已驳回收款 12已退款
    Integer ORDER_TYPE_REFUND = 12;// 退款

    Integer PAYMENT_TYPE_PAY = 1;// 付款

    Integer PAYMENT_TYPE_REFUND = 2;// 退款

    String PAYMENT_FAILED_DESC = "支付失败";

    // 置家顾问版块
    String ADVISER_BUILDING_COMPANY = "分公司";

    String ADVISER_MOBILE_LIST = "ADVISER_MOBILE";// 置家顾问号码池

    // 商品物流状态
    Integer SKU_STATUS_WAITPAY = -1;// 待付款

    Integer SKU_STATUS_WAITPURCHASE = 0;// 待采购

    Integer SKU_STATUS_ONPURCHASE = 1;// 采购中

    Integer SKU_STATUS_WAITDELIVERY = 2;// 待送货

    Integer SKU_STATUS_ONDELIVERY = 3;// 送货中

    Integer SKU_STATUS_FINISHED = 4;// 送货完成

    Integer SKU_STATUS_CANCEL = 7;// 已取消

    // 代课下单 订单来源 6
    Integer ORDER_SOURCE_VALET = 6;

    // 全局订金默认值
    String GLOBAL_DEPOSIT_MONEY_DEFAULT = "GLOBAL_DEPOSIT_MONEY_DEFAULT";

    Integer ORDER_SOURCE_APP = 1;// 订单来源:2:BOSS,1:APP

    String HOME_CARNIVAL = "HOME_CARNIVAL";//1219置家狂欢节活动

    Integer ACTIVITY_FLAG_UNJOIN = 0;//未参加活动

    Integer ACTIVITY_FLAG_JOIN = 1;//参加活动

    String PROFIT_RATE = "8%";//年化收益率

    String SPECIAL_HOUSETYPE = "SPECIAL_HOUSETYPE"; // 特殊户型

    String NEW_USER_HA_INVITATION_CODE_SUM_RESTRICT = "NEW_USER_HA_INVITATION_CODE_SUM_RESTRICT"; // 有邀请码的新用户支付金额限制

    Integer NODE_STATUS_UN_START = 1 ;//节点状态 未开始

    Integer NODE_STATUS_ONTHE_WAY = 2 ;//节点状态 进行中

    Integer NODE_STATUS_COMPLETED = 3 ;//节点状态 已完成

    //交付状态 0待交付 1需求确认 2交付准备 3施工中/采购中 5安装 6竣工 7质保 8完结
    Integer IN_DELIVERY = 3;//施工中/采购中

    Integer DELIVERY_COMPLETED = 6;//竣工

    //交付验收状态
    Integer CHECK_PASS = 2;//验收通过

    Integer CONST_RUCTIONPERIOD_DEFAULT = 75;// 默认工期


    String CONSTRUCTION_PROCESS_WORDS = "施工排期";
    String LAY_PROCESS_WORDS = "制定计划";
    String HARD_PROCESS_WORDS = "硬装进度";
    String FOST_PROCESS_WORDS ="软装进度";
    String COMPLETED_WORDS = "竣工验收";

    //窗帘纱组件id
    Integer COMPONENT_CATEGORY_ID = 105003;



}
