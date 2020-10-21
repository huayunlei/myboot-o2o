package com.ihomefnt.o2o.intf.domain.vote.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-10-18 17:10
 */
@Data
public class DnaInfoDto {

    @ApiModelProperty("dnaid")
    private Integer dnaId;

    @ApiModelProperty("dna名称")
    private String dnaName;

}
