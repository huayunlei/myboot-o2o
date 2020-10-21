package com.ihomefnt.o2o.intf.domain.culture.vo.response;

import com.ihomefnt.o2o.intf.domain.product.dto.ConsumeCodeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("消费码列表vo")
public class CultureConsumeCodeResponseVo {
	
	@ApiModelProperty("消费码列表")
	private List<ConsumeCodeVo> consumeList;
}
