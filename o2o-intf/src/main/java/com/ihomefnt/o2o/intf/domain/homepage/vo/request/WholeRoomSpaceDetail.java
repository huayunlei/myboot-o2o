package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardReplace;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomReplaceHardProductDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/8 13:00
 */
@ApiModel("全屋空间替换详情")
@Data
public class WholeRoomSpaceDetail {

    @ApiModelProperty("分类id")
    private  Integer roomClassId;

    @ApiModelProperty("全屋空间商品组替换列表")
    List<WholeRoomGroupReplace> wholeRoomGroupReplaceList;

    @ApiModelProperty("全屋空间sku替换列表")
    List<RoomReplaceHardProductDto> replaceHardProductDtoList;

    @ApiModelProperty("全屋空间sku新增列表")
    private List<HardReplace> addHardProductDtoList;

}
