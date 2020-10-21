package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.junit.experimental.theories.DataPoints;

/**
 * 硬装、软装清单实体对象
 * @author ZHAO
 */
@Data
@ApiModel(value="HardSoftRequest",description="硬装软装清单请求参数")
public class HardSoftRequest extends HttpBaseRequest {
	@ApiModelProperty("DNA产品ID")
	private Integer dnaId;

	@ApiModelProperty("空间用途id")
	private Integer dnaRoomUsageId;
}
