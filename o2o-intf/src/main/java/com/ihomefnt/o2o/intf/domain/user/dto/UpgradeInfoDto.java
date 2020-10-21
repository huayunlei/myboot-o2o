package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-13 17:48
 */
@NoArgsConstructor
@Data
public class UpgradeInfoDto {


    /**
     * firstFundTime : string
     * orderNum : 0
     * upgradable : true
     * upgradeList : [{"gradeId":0,"gradeName":"string","lastDate":"2019-06-13T09:47:48.348Z","paymentRequiredAmount":0}]
     */

    private String firstFundTime;//首笔款项时间
    private Integer orderNum;//订单编号
    private Boolean upgradable;
    private List<UpgradeListBean> upgradeList;

    @NoArgsConstructor
    @Data
    public static class UpgradeListBean {
        /**
         * gradeId : 0
         * gradeName : string
         * lastDate : 2019-06-13T09:47:48.348Z
         * paymentRequiredAmount : 0
         */

        private Integer gradeId;//权益id
        private String gradeName;//权益名称
        private String lastDate;//截止日
        private BigDecimal paymentRequiredAmount;//升级还需金额
    }
}
