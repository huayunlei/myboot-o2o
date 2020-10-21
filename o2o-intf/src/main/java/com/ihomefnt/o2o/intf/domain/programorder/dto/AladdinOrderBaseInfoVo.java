package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全品家订单基本信息
 *
 * @author ZHAO
 */
@Data
public class AladdinOrderBaseInfoVo {

    private Integer id;// 大订单id

    private Integer companyId;// 所属公司id

    private Integer buildingId;// 所属项目id

    private Integer orderStatus;// 订单状态/阶段

    private String orderStatusStr;// 订单状态/阶段字符串

    private BigDecimal contractAmount;// 合同金额、需交金额、应交金额

    private BigDecimal preferAmount; //方案原价

    private BigDecimal discountAmount;//订单优惠金额

    private Integer orderType;// 订单类型 13全品家订单

    private String orderTypeStr;// 订单类型字符串

    private Boolean isAutoMatch;// 是否自由搭配

    private Date createTime;// 订单创建时间

    private Integer orderSaleType;// 售卖类型：0：软装+硬装，1：软装

    private String orderSaleTypeStr;// 售卖类型字符串

    private Integer source; //订单来源6：代客下单 其它：方案下单

    private String completeTime; // 竣工日期

    private Date expectTime;//期望交付时间

    private Integer gradeId;//等级id

    private String gradeName;//等级名称

    @ApiModelProperty("权益版本号")//2020版本权益为4
    private Integer rightsVersion = 2;

    private BigDecimal orderTotalAmount;//订单总价

    private BigDecimal otherDisAmount;//其他优惠

    private BigDecimal upItemDeAmount;//艾升级可抵扣

}
