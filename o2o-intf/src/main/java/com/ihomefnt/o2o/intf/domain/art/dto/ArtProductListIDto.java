package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-08 09:28
 */
@Data
@ApiModel(value = "产品列表详情查询入参")
@Accessors(chain = true)
public class ArtProductListIDto {

    @ApiModelProperty(value = "模糊查询名称")
    private String likeProductName;

    @ApiModelProperty(value = "特色定制 0非 1是")
    private Integer specialFlag;

    @ApiModelProperty(value = "类目ID集合")
    private List<String> categoryIdList;

    @ApiModelProperty(value = "产品ID集合")
    private List<String> productIdList;


    public ArtProductListIDto(List<String> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public ArtProductListIDto() {
    }
}
