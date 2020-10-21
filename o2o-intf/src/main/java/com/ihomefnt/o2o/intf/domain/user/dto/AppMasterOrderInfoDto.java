package com.ihomefnt.o2o.intf.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息
 *
 * @author liyonggang
 * @create 2019-02-22 19:43
 */
@Data
public class AppMasterOrderInfoDto {


    private Integer customerHouseId;//房产id,

    private Integer userId;//用户id,

    private Integer buildingId;//所属项目id,

    private Integer zoneId;//分区id,

    private Integer companyId;//所属公司id,

    private Integer orderStatus;//订单状态,

    private String orderStatusName;//订单状态,

    private Integer orderType;// 订单类型，取值：13:全品家订单,

    private String orderTypeName;//订单类型，取值：13:全品家订单,

    private Integer orderSaleType;//售卖类型，取值：0-软装+硬装，1-软装,

    private String orderSaleTypeName;// 售卖类型，取值：0-软装+硬装，1-软装,

    private BigDecimal contractAmount;//合同金额,

    private BigDecimal discountAmount;//优惠金额,

    private Date expectTime;//期望送货时间:软装使用 非必填,

    private Date orderTime;//下单时间,

    private String remark;//备注,

    private Integer source;//订单来源,取值：6:boss代客下单 其它：方案下单,

    private String sourceName;//订单来源,取值：6:boss代客下单 其它：方案下单,

    private BigDecimal preferAmount;//全屋价,

    private Date updateTime;//完成时间,

    private Integer gradeId;//等级id,

    private String gradeName;//等级名称

    @ApiModelProperty("权益版本号")//2020版本权益为4
    private Integer rightsVersion = 2;
}
