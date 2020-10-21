package com.ihomefnt.o2o.intf.domain.popup.vo.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("弹窗-response")
public class PopupResponseVo {

	@ApiModelProperty("是否弹框 0不弹框  1弹框")
	private Integer popup;// 是否弹框 0不弹框  1弹框
	
	private Integer popupMode;// 弹框模式 1通知 2营销
	
	private Integer frequency;// 弹框次数
	
	private Integer frequencyUnit;// 弹框次数单位 0永久  1日  2月  3年
	
	private String mainTitle;// 主标题
	
	private String mainContent;// 主文案
	
	private String subContent;// 副文案
	
	private Integer style;// 适用样式  1通知  2引导升级  3通知+引导升级
	
	private String remark;// 备注
	
	private Date createTime;
	
	private Integer deleteFlag;
	
	private String replaceParams;// 替换参数值
	
	private Integer guideStyle;// 引导样式 1:引导1 2:引导3 3:引导4 4:引导5
	
	@ApiModelProperty("贷款id")
    private Long loanId;
	
}
