package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作品定制记录
 * Author: ZHAO
 * Date: 2018年10月15日
 */
@Data
public class ArtCustomRecordResponse {
	@ApiModelProperty("订单ID")
	private Integer orderId;

	@ApiModelProperty("昵称")
	private String nickName;
	
	@ApiModelProperty("头像")
	private String imgUrl;
	
	@ApiModelProperty("下单时间")
	private String createTimeStr;

	@ApiModelProperty("商品名称")
	private String productName;

	@ApiModelProperty("商品图片")
	private String productImgUrl;

	@ApiModelProperty("评价星级")
	private Integer starNum;

	@ApiModelProperty("评价内容")
	private String comment;
}
