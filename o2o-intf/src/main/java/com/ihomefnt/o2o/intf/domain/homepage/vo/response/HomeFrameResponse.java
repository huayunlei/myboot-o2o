package com.ihomefnt.o2o.intf.domain.homepage.vo.response;

import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;
import com.ihomefnt.o2o.intf.domain.homepage.dto.OrderNode;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * APP5.0首页返回值
 * Date: 2018年7月20日
 * @author xiamingyu
 */
@Data
@ApiModel(description = "V5首页返回对象")
public class HomeFrameResponse {
	@ApiModelProperty("焦点节点")
	private Integer focusNode;

	@ApiModelProperty("节点")
	private List<OrderNode> orderNodeList;
	
	@ApiModelProperty("特殊用户标识")
	private Boolean userFlag;

	@ApiModelProperty("订单总价")
	private BigDecimal totalPrice;

	@ApiModelProperty("未付金额")
	private BigDecimal unpaidMoney;

	@ApiModelProperty("已付金额")
	private BigDecimal paidMoney;

	@ApiModelProperty("款项类型")
	private String paymentType;

	@ApiModelProperty("预选方案草稿")
	private String selectDesignDraft;

	@ApiModelProperty("工期")
	private Integer constructionPeriod;

	@ApiModelProperty("已开工天数")
	private Integer startedDays;

	@ApiModelProperty("硬装进度")
	private String hardProgress;

	@ApiModelProperty("软装进度")
	private String softProgress;

	@ApiModelProperty("是否需要需求确认")
	private Boolean checkFlag;

	@ApiModelProperty("评价内容")
	private String evaluationContent;

	@ApiModelProperty("评价星数")
	private Integer evaluationStars;

	@ApiModelProperty("是否是老订单")
	private Boolean oldFlag;

	@ApiModelProperty("置家顾问电话")
	private String homeAdviserMobile;

	@ApiModelProperty("需求确认时间")
	private String confirmationTime;

	@ApiModelProperty("订单号")
	private Integer orderId;

	@ApiModelProperty("距离交房时间")
	private Integer leaveRoomDays;

	@ApiModelProperty("方案首图")
	private String solutionImgUrl = "";

	@ApiModelProperty("硬装风格图片")
	private String styleImgUrl = "";

	@ApiModelProperty("调整设计次")
	private Integer adjustCount;

	@ApiModelProperty("方案数")
	private Integer solutionCount;

	@ApiModelProperty("软装数量")
	private Integer softTotal;

	@ApiModelProperty("软装完成数")
	private Integer softFinishNum;

	@ApiModelProperty("售卖类型：0：软装+硬装，1：软装")
	private Integer orderSaleType;

	@ApiModelProperty("家装预算")
	private String budget;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("全品家认购协议书")
	private String subscribeAgeementUrl;

	@ApiModelProperty("置家服务合同")
	private String serviceContractUrl;

	@ApiModelProperty("订单状态")
	private Integer orderStatus;

	@ApiModelProperty("默认订金")
	private BigDecimal depositMoneyDefalut;

	@ApiModelProperty("DNAid")
	private Integer dnaId;

	@ApiModelProperty("DNA名称")
	private String dnaName;

	@ApiModelProperty("DNA风格名称")
	private String dnaStyleName;

	@ApiModelProperty("DNA首图")
	private String dnaHeadImgUrl;
	
	@ApiModelProperty("报修记录次数")
	private Integer maintainCount;
	
	@ApiModelProperty("已选订单草稿")
	private String orderDraft;

	@ApiModelProperty("预选方案草稿ID")
	private String selectDesignDraftId;

	@ApiModelProperty("权益等级id")
	private Integer gradeId;

	@ApiModelProperty("权益等级名称")
	private String gradeName;

	@ApiModelProperty("签约阶段子状态")
	private Integer signSubStatus;

	@ApiModelProperty("任务状态")
	private Integer taskStatus;

	@ApiModelProperty("任务状态描述")
	private String taskStatusStr;
	
	@ApiModelProperty("方案有效标识 0有效，1已下架")
	private Integer solutionFlag;
	
	@ApiModelProperty("已选风格的问题答案集")
	private List<StyleQuestionSelectedResponse> selectedStyleQuestionList;
	
	@ApiModelProperty("艾升级券面值")
	private BigDecimal upGradeCouponAmount;

	@ApiModelProperty("立减权益不减的订单总价")
	private CopyWriterAndValue<String,BigDecimal> finalOrderPrice;

	@ApiModelProperty("方案总价")
	private BigDecimal solutionTotalPrice;

	@ApiModelProperty("款项优化后的剩余应付")
	private BigDecimal newRestAmount;

}
