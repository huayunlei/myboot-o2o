package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.RoomHardItemClass;
import com.ihomefnt.o2o.intf.domain.program.vo.response.RoomPictureDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lindan on 2018/7/23.
 */
@Data
public class SpaceDesignVo implements Serializable {

    @ApiModelProperty("空间设计id,空间id")
    private Integer spaceDesignId;

    @ApiModelProperty("空间设计头图")
    private String headImage;

    @ApiModelProperty("空间设计用途id【空间名称】")
    private Integer spaceUsageId;

    @ApiModelProperty("空间设计用途名称")
    private String spaceUsageName;

    @ApiModelProperty("空间设计-方案设计描述")
    private String spaceStyle;

    @ApiModelProperty("所属方案id")
    private Long solutionId;

    @ApiModelProperty("所属方案名称")
    private String solutionName;

    @ApiModelProperty("空间设计价格")
    private BigDecimal spaceDesignPrice;

    @ApiModelProperty("空间硬装选配项列表")
    private List<RoomHardItemClass> hardItemList;

    @ApiModelProperty("空间图片集合")
    private List<RoomPictureDto> roomPictureDtoList;
}
