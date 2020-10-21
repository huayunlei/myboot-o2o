package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/16 18:35
 */
@Data
@ApiModel("CollageInfoDto")
public class CollageInfoDto {

    @ApiModelProperty("GroupInfoDto")
    private GroupInfoDto groupInfoDto;

    @ApiModelProperty("JoinGroupRecordDto 列表")
    private List<JoinGroupRecordDto> joinGroupRecordList;
}
