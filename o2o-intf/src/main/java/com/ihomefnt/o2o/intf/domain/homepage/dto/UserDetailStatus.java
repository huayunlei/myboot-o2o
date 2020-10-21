package com.ihomefnt.o2o.intf.domain.homepage.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午5:16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class UserDetailStatus {

    //楼盘id
    private Integer buildingId;

    //户型id
    private Integer houseTypeId;

    //户型相应上线方案数量
    private Integer solutionBaseNum;

    //用户交付清单是否待确认
    private Boolean checkResult;

    // 售卖类型：0：软装+硬装，1：软装
    private Integer orderSaleType;

    //硬装进度
    private String hardSchedule;

    //软装进度
    private String softSchedule;

    //实际进场开工日期
    private String actualBeginDate;

    //软装进场时间
    private String expectSendDate;

    //订单状态/阶段
    private Integer orderStatus;

    //交付清单是否已经确认(订单大状态 交付中 和 已完成 情况下 就代表交付清单是否已经确认)
    private Integer isConfirmed = 0;

    //订单状态
    private Integer state;

    //订单来源 6:代客下单 其他:方案下单
    private Integer source;

    //代课下单状态下 固定图
    private String proxySourceFixedImg;

    //交房日期
    private String deliverTime;

    // 软装订单状态0：待付款，1：待采购，2：采购中，3：待配送，4：配送中，5：已完成，6：已取消
    private Integer softOrderStatus;

    // 硬装订单状态  0 待付款 1待分配 2待排期 3待施工 4施工中 5已完成 6已取消
    private Integer hardOrderStatus;

    //楼盘名称
    private String buildingName;

    //户型名称
    private String houseName;

    //是否是特定用户：1、没有资质 2、只能看 3、可以下单
    private Integer specific;

}
