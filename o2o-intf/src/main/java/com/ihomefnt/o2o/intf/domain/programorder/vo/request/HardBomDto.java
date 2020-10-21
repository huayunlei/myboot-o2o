package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-05-20 16:11
 */
@Data
@Accessors(chain = true)
public class HardBomDto {

    private int furnitureType;
    private int groupId;
    private int newGroupId;
    @ApiModelProperty("柜体标签编号")
    private String cabinetType;

    @ApiModelProperty("柜体标签编号")
    private String cabinetTypeName;

    @ApiModelProperty("位置索引")
    private String positionIndex;
}
