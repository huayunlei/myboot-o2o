package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import com.ihomefnt.o2o.intf.domain.paintscreen.dto.PushArtResultDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 推屏返回数据
 */
@ApiModel("推屏返回数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestIdResponse {

    @ApiModelProperty("推屏返回数据-老")
    private List<Long> requestIdList;

    @ApiModelProperty("推屏返回时数据-新（增加requestResult）")
    private List<PushArtResultDto> resultDateList;
}
