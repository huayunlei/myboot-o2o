package com.ihomefnt.o2o.intf.domain.collage.dto;

import com.ihomefnt.o2o.intf.domain.collage.vo.request.ProductConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/17 14:29
 */
@Data
@ApiModel("CreateCollageOrderParam")
public class CreateCollageOrderParam {

    @ApiModelProperty("openId")
    private String openid;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("下单人电话")
    private String mobile;

    @ApiModelProperty("订单来源")
    private String source;

    @ApiModelProperty("ProductConfig 商品详情")
    private List<ProductConfig> detail;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverTel;



    @ApiModelProperty("购物车下单标识 true 购物车下单 false 非购物车下单")
    private boolean shoppingCartPay;

    @ApiModelProperty("团id groupId 新开团为空")
    private Integer groupId;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("用户昵称")
    private String userNick;

    @ApiModelProperty("用户图像")
    private String headImg;

    @ApiModelProperty("收货人地址")
    private String customerAddress;

    @ApiModelProperty("艾佳币数")
    private Integer ajbAmount;

    @ApiModelProperty("实际支付金额")
    private BigDecimal trueAmount;

    @ApiModelProperty("订单类型 1:软装订单 2:硬装订单 3:全品家订单 4:抢购订单 5:艺术品订单 6:文旅商品订单 7:商品退单订单 8:样板间申请订单 9:方案订单 10:自由搭配方案订单 15 小星星订单 16 艺术品拼团")
    private Integer orderType;
}
