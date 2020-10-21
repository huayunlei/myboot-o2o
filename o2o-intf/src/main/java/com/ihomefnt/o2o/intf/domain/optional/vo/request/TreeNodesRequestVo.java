package com.ihomefnt.o2o.intf.domain.optional.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 新建订制品入参
 *
 * @author liyonggang
 * @create 2018-11-24 16:43
 */
@Data
@ApiModel("新建订制品树形结构")
public class TreeNodesRequestVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("节点类型 [1: 组, 2: 选项, 3: 可选项],")
    private Integer nodeType;
    @ApiModelProperty("子节点勾选项 id,")
    private Integer optionId;

    private List<TreeNodesRequestVo> attrs;
}
