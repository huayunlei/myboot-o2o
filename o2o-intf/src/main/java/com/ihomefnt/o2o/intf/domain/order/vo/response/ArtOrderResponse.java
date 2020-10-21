package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.SolutionDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AllProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderPayStage;
import com.ihomefnt.o2o.intf.domain.programorder.dto.PaymentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("艺术品订单返回信息")
public class ArtOrderResponse {

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("订单备注 团购颜色")
	private String remark;


	/**
	 * 订单id
	 */
	@ApiModelProperty("订单id")
	private Integer orderId;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 订单类型
	 */
	@ApiModelProperty("订单类型 1:软装订单 2:硬装订单 3:全品家订单 4:抢购订单 5:艺术品订单 6:文旅商品订单 7:商品退单订单 8:样板间申请订单 9:方案订单 10:自由搭配方案订单 15 小星星订单 16 艺术品拼团")
	private Integer orderType;

	/**
	 * 静态客户收货地址
	 */
	private String customerAddress;
	/**
	 * 楼栋号
	 */
	private String buildingNo;
	/**
	 * 房号
	 */
	private String houseNo;

	/**
	 * 订单编号全局
	 */
	@ApiModelProperty("订单编号全局")
	private String orderNum;

	/**
	 * 商品总数
	 */
	@ApiModelProperty("商品总数")
	private Integer productCount;

	/**
	 * 订单状态,总状态
	 * ',
	 */
	@ApiModelProperty("订单状态 0:提交订单成功 1:处理中 2:已完成 3:已取消 4:待付款 5:待收货 6:部分付款 7:待施工 8:施工中 9:待退款 10:待发货 11:待结款 12:交易关闭 13：待接单 14：部分发货")
	private Integer state;

	/**
	 * 订单状态,总状态
	 */
	@ApiModelProperty("订单状态")
	private String stateDesc;

	/**
	 * 订单总价
	 */
	@ApiModelProperty("订单总价")
	private BigDecimal totalPrice;
	/**
	 * 实际支付金额
	 */
	@ApiModelProperty("实际支付金额")
	private BigDecimal actualPayMent;

	/**
	 * 客户收货地址
	 */
	@ApiModelProperty("客户收货地址")
	private Integer fidArea;
	/**
	 * 收货人姓名
	 */
	@ApiModelProperty("收货人姓名")
	private String receiverName;
	/**
	 * 收货人电话
	 */
	@ApiModelProperty("收货人电话")
	private String receiverTel;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("楼盘名称")
	private String buildingName; // 楼盘名称


	@ApiModelProperty("详细收货地址")
	private String receiverAddress;

	@ApiModelProperty("下单日期")
	private String createDateStr;

	@ApiModelProperty("未付金额")
	private BigDecimal unpaidMoney;

	@ApiModelProperty("已付金额")
	private BigDecimal paidMoney;

	@ApiModelProperty("艾积分抵用金额")
	private BigDecimal ajbMoney;


	@ApiModelProperty("OrderDetailResp 商品信息")
	private List<OrderDetailResp> orderDetailRespList;

	/**
	 * 只有已完成的艺术品订单才有 MOBILE-356 : 2017-02-08
	 */
	@ApiModelProperty("已经评价的商品数量")
	private int productCommentCount;

	/**
	 * 
	 * 预计发货时间的倒计时[待发货状态的艺术品订单才有的]
	 */
	@ApiModelProperty("预计发货时间的倒计时[待发货状态的艺术品订单才有的]")
	private Integer deliveryTime;

	@ApiModelProperty("物流单号")
	private String logisticNum; // 物流单号

	@ApiModelProperty("物流公司名称")
	private String logisticCompanyName; // 物流公司名称

	@ApiModelProperty("物流公司编码")
	private String logisticCompanyCode; // 物流公司编码

	@ApiModelProperty("剩余支付时间")
	private long lastPayTime; // 剩余支付时间

	/**
	 * 项目方案的订单
	 */
	@ApiModelProperty("项目方案的订单")
	private SolutionDetailResponseVo solutionDetail;
	
	//待发货状态：1待发货 2部分发货
	@ApiModelProperty("待发货状态：-1 没有物流信息 1待发货 2部分发货")
	private Integer subStatus;
	
	//状态描述
	@ApiModelProperty("状态描述")
	private String subStatusDesc;


	//物流跳转类型：0物流详情页、1所有物流页
	@ApiModelProperty("物流跳转类型：0物流详情页、1所有物流页")
	private Integer logisticSkipType;

	@ApiModelProperty("物流商品数")
	private Integer logisticProductNum; //物流商品数

	@ApiModelProperty("物流包裹数")
	private Integer logisticPackageNum; //物流包裹数

}
