package com.ihomefnt.o2o.intf.domain.dna.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wanyunxin
 * @create 2019-11-18 14:01
 */
@Data
@ApiModel("DNA浏览记录")
@Accessors(chain = true)
public class DnaBrowseRecordDto {

    @ApiModelProperty("装修报价记录id")
    private Integer quotePriceId;

    @ApiModelProperty("用户id")
    private Integer userId;

    private Integer dnaId;

}
