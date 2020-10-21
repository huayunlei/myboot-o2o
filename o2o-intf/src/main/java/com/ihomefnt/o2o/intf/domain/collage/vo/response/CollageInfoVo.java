package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/15 10:14
 */
@Data
@ApiModel("CollageInfoVo 团信息")
public class CollageInfoVo {

    @ApiModelProperty("GroupInfoVo")
    private GroupInfoVo groupInfoDto;

    @ApiModelProperty("JoinGroupRecordDto 列表")
    private List<JoinGroupRecordVo> joinGroupRecordList;
}
