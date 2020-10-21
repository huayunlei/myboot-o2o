package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 拼团创建订单请求
 * @author jerfan cang
 * @date 2018/10/15 17:20
 */
@Data
@ApiModel("CreateCollageOrderRequest")
public class CreateCollageOrderRequest extends HttpBaseRequest {

    @ApiModelProperty("openId 必传")
    private String openid;

    @ApiModelProperty("下单人电话")
    private String mobile;

    @ApiModelProperty("订单来源 H5")
    private String source;

    @ApiModelProperty("ProductConfig 商品详情")
    private List<ProductConfig> detail;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverTel;

    @ApiModelProperty("收货人地址")
    private String customerAddress;



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


    @ApiModelProperty("艾佳币数量 默认传0")
    private Integer ajbAmount;
    // 上面是下单需要参数

    @ApiModelProperty("支付方式 true 使用艾积分 false 现金")
    private boolean payMark;

    @ApiModelProperty("支付金额 如果艾佳币够 则给0 不拉起支付")
    private BigDecimal trueAmount;

    @ApiModelProperty("订单类型 1:软装订单 2:硬装订单 3:全品家订单 4:抢购订单 5:艺术品订单 6:文旅商品订单 7:商品退单订单 8:样板间申请订单 9:方案订单 10:自由搭配方案订单 15 小星星订单 16 艺术品拼团")
    private Integer orderType;

}
