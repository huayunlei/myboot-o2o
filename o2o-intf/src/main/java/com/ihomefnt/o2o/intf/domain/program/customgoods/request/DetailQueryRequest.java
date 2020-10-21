package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询类
 *
 * @author liyonggang
 * @create 2019-03-18 16:10
 */
@ApiModel("查询类")
@Data
@Accessors(chain = true)
public class DetailQueryRequest extends HttpBaseRequest {

    @ApiModelProperty("组合id")
    private Integer groupId;

    @ApiModelProperty("物料id")
    private Integer materielId;

    @ApiModelProperty("组件id")
    private Integer componentId;

    @ApiModelProperty("物料分类id")
    private Integer materialClassificationId;

    @ApiModelProperty("组合id")
    private Integer defaultGroupId;

    @ApiModelProperty("物料id")
    private Integer defaultMaterielId;
}
