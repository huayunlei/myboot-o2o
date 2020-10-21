package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by onefish on 2016/11/3 0003.
 */
@Data
@ApiModel("新家大晒点赞model")
public class HttpShareOrderPraiseRequest extends HttpBaseRequest {

	@ApiModelProperty("晒家id")
	private String shareOrderId;

	@ApiModelProperty("晒家类型:0 表示老晒家, 1 表示专题")
	private int type;
}
