package com.ihomefnt.o2o.intf.domain.art.vo.request;

import java.math.BigDecimal;
import java.util.List;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author xiamingyu
 */
@ApiModel("艺术品下单参数")
@Data
public class HttpCreateArtworkOrderRequest extends HttpBaseRequest{

	@ApiModelProperty("商品列表")
	private List<HttpOrderProductRequest> productList;

	@ApiModelProperty("订单合同额")
    private BigDecimal softContractgMoney;

	@ApiModelProperty("艾积分数量")
    private Integer ajbAmount;

	@ApiModelProperty("是否全额支付")
    private Boolean allPay;

	@ApiModelProperty("是否购物车内结算")
    private Boolean shoppingCartPay;

	@ApiModelProperty("优惠券券码")
	private String voucherCode;

	@ApiModelProperty("订单类型")
    private Integer orderType;

    @JsonIgnore
	@ApiModelProperty("优惠券金额，前端不传")
    private BigDecimal voucherMoney;

    @ApiModelProperty("售卖类型 0默认 1定制商品")
	private Integer saleType;
    
	@ApiModelProperty("备注")
    private String remark;

}
