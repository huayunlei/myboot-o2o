package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/22
 */

@ApiModel("空间设计批量请求参数")
@Data
public class SelectionRequest extends HttpBaseRequest {

    @ApiModelProperty("空间id列表")
    private List<Integer> spaceIdList;

    @ApiModelProperty("方案id")
    private Long solutionId;

}
