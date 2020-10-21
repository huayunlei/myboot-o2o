package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/8 11:28
 */
@ApiModel("全屋空间信息")
@Data
public class WholeRoomSpaceInfo {

    @ApiModelProperty("全屋空间id")
    private Integer roomId;

    @ApiModelProperty("全屋空间替换列表")
    private List<WholeRoomSpaceDetail> wholeRoomRepalceList;

}
