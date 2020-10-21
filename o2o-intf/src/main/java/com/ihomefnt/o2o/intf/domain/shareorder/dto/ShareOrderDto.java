package com.ihomefnt.o2o.intf.domain.shareorder.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by onefish on 2016/11/3 0003.
 */
@Data
@ApiModel("发布晒单model")
public class ShareOrderDto extends HttpBaseRequest {

	@ApiModelProperty("晒单文字内容")
	private String textContent;

	@ApiModelProperty("晒单图片内容")
	private List<String> imgContent;

	@ApiModelProperty("城市名称")
	private String cityName;

	@ApiModelProperty("楼盘地址")
	private String buildingAddress;

	@ApiModelProperty("审批状态:1 审批通过, 2 审批不通过")
	private Integer shareOrderStatus;

	@ApiModelProperty("晒家类型:0 表示老晒家, 1 表示专题")
	private int type;
	
	@ApiModelProperty("晒家专题id")
	private String topicId;

}
