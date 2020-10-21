package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.homepage.vo.request.BomReplaceRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.ReplaceProductRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by lindan on 2018/3/16 0016.
 */
@ApiModel("空间商品调整对象")
@Data
public class RoomReplaceProductDto {

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("替换商品信息")
    private List<ReplaceProductRequest> productDtos;

    @ApiModelProperty("BOM替换信息")
    private List<BomReplaceRequest> replaceBomDtos;

}
