package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 11:48
 */
@ApiModel(value = "艺术品查询入参")
@Data
public class WorksRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "艺术品id")
    private String worksId;
}
