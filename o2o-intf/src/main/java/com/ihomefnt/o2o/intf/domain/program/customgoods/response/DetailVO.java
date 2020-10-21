package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * sku详情
 *
 * @author liyonggang
 * @create 2019-03-18 16:39
 */
@ApiModel("组合或物料详情")
@Data
public class DetailVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("头图")
    private List<ImageVO> headImages;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("说明")
    private String description;

    @ApiModelProperty("末级类目ID")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;

    @ApiModelProperty("顶级类目ID")
    private Integer topCategoryId;

    @ApiModelProperty("属性集合")
    private List<ElementVO> elementList;
}
