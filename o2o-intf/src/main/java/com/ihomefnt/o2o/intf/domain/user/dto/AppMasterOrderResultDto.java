package com.ihomefnt.o2o.intf.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息
 *
 * @author liyonggang
 * @create 2019-02-22 18:59
 */
@Data
public class AppMasterOrderResultDto {

    private Integer masterOrderId;//主订单ID,

    private Integer customerHouseId;// 房产id,

    private Integer userId;//用户id,

    private Integer buildingId;//所属项目ID,

    private String buildingName;//所属项目名称,

    private Integer companyId;// 所属公司ID,

    private String companyName;// 所属公司名称,

    private Integer orderStatus;//订单状态,

    private String orderStatusName;// 订单状态名称,

    private Integer orderType;//订单类型,

    private String orderTypeName;//订单类型名称,

    private BigDecimal contractAmount;// 合同金额,

    private BigDecimal hardenedAmount;// 硬装金额,

    private BigDecimal softAmount;//软装金额,

    private BigDecimal serviceAmount;// 服务费金额,

    private BigDecimal discountAmount;// 优惠金额,

    private String expectTime;// 期望收货时间,

    private String orderTime;// 下单时间,

    private String remark;// 备注,

    private Integer source;// 订单来源,

    private String sourceName;//订单来源名称,

    private Date createTime;//创建时间,

    private Date updateTime;// 更新时间,

    private Integer suitType;//套装类型 ：0：自由搭配 1：整套,

    private String suitTypeName;// 套装类型 ：0：自由搭配 1：整套

    private Integer gradeId;//权益等级 0-普通 1-黄金 2-铂金 3-钻石 4-白银 5-青铜

    @ApiModelProperty("权益版本号")//2020版本权益为4
    private Integer rightsVersion = 2;
}
