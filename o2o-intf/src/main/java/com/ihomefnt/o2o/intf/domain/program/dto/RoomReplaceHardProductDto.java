package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardReplace;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.HardBomDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created on 2018/7/24.
 *
 * @author hongjingchao.
 */
@ApiModel(value = "空间硬装商品调整对象")
@Data
@Accessors(chain = true)
public class RoomReplaceHardProductDto {

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("空间替换硬装商品集合")
    private List<HardReplace> replaceHardProductDtoList;

    @ApiModelProperty("空间新增硬装商品集合")
    private List<HardReplace> addHardProductDtoList;

    @ApiModelProperty("空间待删除硬装商品集合")
    private List<HardReplace> delHardProductDtoList;

    @ApiModelProperty("bom组合集合")
    private List<HardBomDto> addRoomBomDtos;

    @ApiModelProperty("bom组合集合")
    private List<HardBomDto> replaceBomDtos;
}
