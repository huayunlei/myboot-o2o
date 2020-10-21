package com.ihomefnt.o2o.intf.manager.constant.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/17
 *  5.0首页节点常量
 */
public class HomeOrderNode {

    //了解我们
    public static final Integer ABOUT_US_ID = 0;

    public static final String ABOUT_US_NAME = "了解我们";

    //交定金
    public static final Integer PAY_DEPOSIT_ID = 1;

    public static final String PAY_DEPOSIT_NAME = "交定金";

    //预选设计
    public static final Integer SELECT_DESIGN_ID = 2;

    public static final String SELECT_DESIGN_NAME = "预选设计";

    //付全款
    public static final Integer PAY_ALL_ID = 3;

    public static final String PAY_ALL_NAME = "付全款";

    //调整设计
    public static final Integer ADJUST_DESIGN_ID = 4;

    public static final String ADJUST_DESIGN_NAME = "调整设计";

    //确认方案
    public static final Integer CONFIRM_SOLUTION_ID = 5;

    public static final String CONFIRM_SOLUTION_NAME = "确认方案";

    //施工
    public static final Integer CONSTRUCTION_ID = 6;

    public static final String CONSTRUCTION_NAME = "施工";

    //验收
    public static final Integer CHECK_ID  = 7;

    public static final String SCHECK_NAME = "验收";

    //维保
    public static final Integer MAINTENANCE_ID  = 8;

    public static final String MAINTENANCE_NAME = "维保";

    public static final List<String> NODE_NAME_LIST = new ArrayList<String>(){
        {
            add("了解我们");
            add("交定金");
            add("预选设计");
            add("付全款");
            add("调整设计");
            add("确认方案");
            add("施工");
            add("验收");
            add("维保");
        }
    };

    /**
     * 节点时态
     */
    //过去时
    public static final Integer STATUS_PAST = 1;

    //现在时
    public static final Integer STATUS_NOW = 2;

    //将来时
    public static final Integer STATUS_FUTURE = 3;

    public static final BigDecimal COMPLETE_ZERO = BigDecimal.ZERO;

    public static final BigDecimal COMPLETE_FINISHED = new BigDecimal(1);
    
}
