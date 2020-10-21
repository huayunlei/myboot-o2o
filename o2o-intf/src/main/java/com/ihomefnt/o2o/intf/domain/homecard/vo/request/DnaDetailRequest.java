package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DNA详情实体对象
 * @author ZHAO
 */
@Data
@ApiModel(value="DnaDetailRequest",description="APP3.0新版DNA详情请求参数")
public class DnaDetailRequest extends HttpBaseRequest {
	@ApiModelProperty("DNA产品ID")
	private Integer dnaId;

	@ApiModelProperty("装修报价记录id")
	private Integer quotePriceId;

}
