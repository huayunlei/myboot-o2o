package com.ihomefnt.o2o.intf.manager.constant.product;

import java.util.Arrays;
import java.util.List;

public interface ProductCategoryConstant {

    // 沙发-二级分类
    Integer CATEGORY_TWO_ID_SOFA = 5001;

    // 单人沙发-三级分类
    Integer CATEGORY_THREE_ID_SOFA_DANREN = 5002;

    // 双人沙发-三级分类
    Integer CATEGORY_THREE_ID_SOFA_SHUANGREN = 5003;

    // 多人沙发-三级分类
    Integer CATEGORY_THREE_ID_SOFA_DUOREN = 5004;

    // 转角沙发-三级分类
    Integer CATEGORY_THREE_ID_SOFA_ZHUANJIAO = 5005;

    // 贵妃沙发-三级分类
    Integer CATEGORY_THREE_ID_SOFA_GUIFEI = 5006;

    // 脚踏-三级分类
    Integer CATEGORY_THREE_ID_SOFA_JIAOTA = 5007;

    // 床-二级分类
    Integer CATEGORY_TWO_ID_BED = 5008;

    // 成人床-三级分类
    Integer CATEGORY_THREE_ID_BED_CHENGREN = 5009;

    // 儿童床-三级分类
    Integer CATEGORY_THREE_ID_BED_ERTONG = 5010;

    // 高低床-三级分类
    Integer CATEGORY_THREE_ID_BED_GAODI = 5011;

    // 组合床-三级分类
    Integer CATEGORY_THREE_ID_BED_ZUHE = 5012;

    // 床垫-二级分类
    Integer CATEGORY_TWO_ID_MATTRESS = 5058;

    // 弹簧床垫-三级分类
    Integer CATEGORY_THREE_ID_MATTRESS_TANHUANG = 5059;

    // 棕床垫-三级分类
    Integer CATEGORY_THREE_ID_MATTRESS_ZONG = 5060;

    // 乳胶床垫-三级分类
    Integer CATEGORY_THREE_ID_MATTRESS_RUJIAO = 5061;

    // 复合床垫-三级分类
    Integer CATEGORY_THREE_ID_MATTRESS_FUHE = 5062;

    // 茶几-三级分类
    Integer CATEGORY_THREE_ID_CHAJI = 5014;

    // 边几-三级分类
    Integer CATEGORY_THREE_ID_BIANJI = 5015;

    // 餐桌-三级分类
    Integer CATEGORY_THREE_ID_CANZHUO = 5016;

    // 餐椅-三级分类
    Integer CATEGORY_THREE_ID_CANYI = 5022;

    // 床头柜-三级分类
    Integer CATEGORY_THREE_ID_CHUANGTOUGUI = 5032;

    // 电视柜-三级分类
    Integer CATEGORY_THREE_ID_DIANSHIGUI = 5033;

    // 灯具-三级分类
    Integer CATEGORY_THREE_ID_DJ = 5048;

    // 吸顶灯-三级分类
    Integer CATEGORY_THREE_ID_XDD = 5049;

    // 吊灯-三级分类
    Integer CATEGORY_THREE_ID_DD = 5050;

    // 台灯-三级分类
    Integer CATEGORY_THREE_ID_TD = 5051;

    // 落地灯-三级分类
    Integer CATEGORY_THREE_ID_LDD = 5052;

    // 壁灯-三级分类
    Integer CATEGORY_THREE_ID_BD = 5053;



    // 沙发三级分类ID集合
    List<Integer> SOFA_LAST_CATEGORY_ID_LIST = Arrays.asList(
            CATEGORY_THREE_ID_SOFA_DANREN,CATEGORY_THREE_ID_SOFA_SHUANGREN,CATEGORY_THREE_ID_SOFA_DUOREN,
            CATEGORY_THREE_ID_SOFA_ZHUANJIAO,CATEGORY_THREE_ID_SOFA_GUIFEI,CATEGORY_THREE_ID_SOFA_JIAOTA
    );

    // 多人沙发5004、转角沙发5005、贵妃椅5006，放开相互替换
    // 190613，修改为所有沙发可以互查
    List<Integer> SOFA_CAN_EXCHANGE_LAST_CATEGORY_ID_LIST = SOFA_LAST_CATEGORY_ID_LIST;

    // 床三级分类ID集合
    // 只要替换前的二级类目是床，则放开所有的床，不限三级类目:5009成人床;5010儿童床;5011高低床;5012组合床
    List<Integer> BED_LAST_CATEGORY_ID_LIST = Arrays.asList(
            CATEGORY_THREE_ID_BED_CHENGREN,CATEGORY_THREE_ID_BED_ERTONG,CATEGORY_THREE_ID_BED_GAODI,CATEGORY_THREE_ID_BED_ZUHE);

    // 床三级分类ID集合
    // 只要替换前的二级类目是床，则放开所有的床，不限三级类目:5009成人床;5010儿童床;5012组合床
    List<Integer> BED_LAST_CATEGORY_ID_LIST_NO_BUNK_BED = Arrays.asList(
            CATEGORY_THREE_ID_BED_CHENGREN,CATEGORY_THREE_ID_BED_ERTONG,CATEGORY_THREE_ID_BED_ZUHE);

    // 5058	床垫-二级;[5059	弹簧床垫;5060	棕床垫;5061	乳胶床垫;5062	复合床垫]
    List<Integer> MATTRESS_LAST_CATEGORY_ID_LIST = Arrays.asList(
            CATEGORY_THREE_ID_MATTRESS_TANHUANG,CATEGORY_THREE_ID_MATTRESS_ZONG,CATEGORY_THREE_ID_MATTRESS_RUJIAO,CATEGORY_THREE_ID_MATTRESS_FUHE);

    // 床头柜5032;电视柜5033
    List<Integer> GUI_LAST_CATEGORY_ID_LIST = Arrays.asList(CATEGORY_THREE_ID_CHUANGTOUGUI,CATEGORY_THREE_ID_DIANSHIGUI);

    // 茶几5014,边几5015;餐桌5016    有形状属性
    List<Integer> ZHUO_LAST_CATEGORY_ID_LIST = Arrays.asList(CATEGORY_THREE_ID_CHAJI,CATEGORY_THREE_ID_BIANJI,CATEGORY_THREE_ID_CANZHUO);

    // 软装八大类的末级分类ID（可替换）
    List<Integer> EIGHT_BIG_CATEGORY_ID_LIST = Arrays.asList(CATEGORY_THREE_ID_CHAJI,CATEGORY_THREE_ID_BIANJI,// 茶几边几
            CATEGORY_THREE_ID_SOFA_DANREN,CATEGORY_THREE_ID_SOFA_SHUANGREN,CATEGORY_THREE_ID_SOFA_DUOREN,// 沙发
            CATEGORY_THREE_ID_SOFA_ZHUANJIAO,CATEGORY_THREE_ID_SOFA_GUIFEI,CATEGORY_THREE_ID_SOFA_JIAOTA,
            CATEGORY_THREE_ID_DIANSHIGUI,// 电视柜
            CATEGORY_THREE_ID_CANZHUO,// 餐桌
            CATEGORY_THREE_ID_CANYI,// 餐椅
            CATEGORY_THREE_ID_BED_CHENGREN,CATEGORY_THREE_ID_BED_ERTONG,CATEGORY_THREE_ID_BED_GAODI,CATEGORY_THREE_ID_BED_ZUHE,// 床
            CATEGORY_THREE_ID_CHUANGTOUGUI//床头柜
    );

    //灯具集合
    List<Integer> CATEGORY_THREE_ID_D_LIST = Arrays.asList(
            CATEGORY_THREE_ID_XDD,CATEGORY_THREE_ID_DD,CATEGORY_THREE_ID_TD,CATEGORY_THREE_ID_LDD,CATEGORY_THREE_ID_BD
    );


}
