package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.ihomefnt.o2o.intf.domain.program.vo.response.UserInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionPageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 全品家订单信息
 *
 * @author ZHAO
 */
@ApiModel("全品家订单信息")
@Data
public class AllProductOrderResponse implements Serializable {

    @ApiModelProperty("订单ID")
    private Integer orderId;// 订单ID

    @ApiModelProperty("订单类型 11套装、硬装+软装 12套装、纯软装 13自由搭配、硬装+软装 14自由搭配、纯软装")
    private Integer orderType; // 订单类型 11套装、硬装+软装 12套装、纯软装 13自由搭配、硬装+软装 14自由搭配、纯软装

    @ApiModelProperty("订单编号")
    private String orderNum;// 订单编号

    @ApiModelProperty("订单状态")
    private Integer state;// 订单状态

    @ApiModelProperty("订单状态说明")
    private String stateDesc;// 订单状态说明

    @ApiModelProperty("订单创建时间")
    private String createTime;// 订单创建时间

    @ApiModelProperty("方案原价")
    private BigDecimal originalPrice;// 方案原价

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;// 订单总价

    @ApiModelProperty("订单优惠金额")
    private BigDecimal discountPrice;// 订单优惠金额

    @ApiModelProperty("实际支付金额")
    private BigDecimal actualPayMent;// 实际支付金额

    @ApiModelProperty("未付金额")
    private BigDecimal unpaidMoney; // 未付金额

    @ApiModelProperty("已付金额")
    private BigDecimal paidMoney; // 已付金额

    @ApiModelProperty("交款日期")
    private String paymentTime;// 交款日期

    @ApiModelProperty("用户信息：电话、房产信息、置家顾问信息")
    private UserInfoResponse userInfo;// 用户信息：电话、房产信息、置家顾问信息

    @ApiModelProperty("客服电话")
    private String serviceMobile;// 客服电话

    @ApiModelProperty("可选方案信息")
    private SelectSolutionInfo selectSolutionInfo;// 可选方案信息

    @ApiModelProperty("订单方案信息")
    private SolutionOrderInfo solutionOrderInfo;// 订单方案信息

    @ApiModelProperty("硬装施工信息")
    private HardConstructInfo hardConstructInfo;// 硬装施工信息

    @ApiModelProperty("软装配送信息")
    private SoftDeliveryInfo softDeliveryInfo;// 软装配送信息

    @ApiModelProperty("增配包信息")
    private List<AddBagDetail> addBagInfo;// 增配包信息

    @ApiModelProperty("标准升级包")
    private List<StandardUpgradeInfo> upgradeInfos; // 标准升级包

    @ApiModelProperty("非标准升级包")
    private List<NoStandardUpgradeInfo> noUpgradeInfos; // 非标准升级包

    @ApiModelProperty("订单退款信息")
    private OrderRefundInfo orderRefundInfo;// 订单退款信息

    @ApiModelProperty("增减项信息")
    private IncrementItemInfo incrementItemInfo;// 增减项信息

    @ApiModelProperty("默认订金")
    private BigDecimal depositMoneyDefalut;// 默认订金

    @ApiModelProperty("优惠前原价")
    private BigDecimal originalAmount;// 优惠前原价

    @ApiModelProperty("促销活动列表")
    private PromotionPageResponse promotionPage;// 促销活动列表

    @ApiModelProperty("是否参加1219活动")
    private boolean homeCarnivalFlag;// 是否参加1219活动

    @ApiModelProperty("1219活动报名时间")
    private String homeCarnivalTime;// 1219活动报名时间

    @ApiModelProperty("需求确认标志")
    private boolean confirmationFlag;//需求确认标志

    @ApiModelProperty("电子合同数量")
    private Integer contractNum;//电子合同数量

    @ApiModelProperty(" 订单来源6：代客下单 其它：方案下单")
    private Integer source;// 订单来源6：代客下单 其它：方案下单

    @ApiModelProperty("竣工日期")
    private String completeTime;// 竣工日期

    @ApiModelProperty("交房日期")
    private String deliverTime; //交房日期

    @ApiModelProperty("是否可以点评")
    private boolean checkFlag;//是否可以点评

    @ApiModelProperty("期望交付时间")
    private String exceptTime;//期望交付时间

    @ApiModelProperty("是否是老订单")
    private Boolean oldFlag;

    @ApiModelProperty("等级id")
    private Integer gradeId;//等级id

    @ApiModelProperty("等级名称")
    private String gradeName;//等级名称

    @ApiModelProperty("订单总价信息")
    private CopyWriterAndValue<String, BigDecimal> finalOrderPrice;

    @ApiModelProperty("其他优惠")
    private BigDecimal otherDisAmount;

    @ApiModelProperty("款项优化后的剩余应付")
    private BigDecimal newRestAmount;
}
