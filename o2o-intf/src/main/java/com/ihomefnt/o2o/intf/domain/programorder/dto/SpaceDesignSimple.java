package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.OptionalSoftResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.RoomPictureDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * wanyunxin
 * 20190301
 */
@ApiModel("空间效果,硬装简单信息")
@Data
public class SpaceDesignSimple implements Serializable {

    @ApiModelProperty("空间设计id")
    private Integer spaceDesignId;

    @ApiModelProperty("空间设计头图")
    private String headImage;

    @ApiModelProperty("空间设计头图原图")
    private String headImageOrigin;

    @ApiModelProperty("空间设计用途id")
    private Integer spaceUsageId;

    @ApiModelProperty("空间设计用途名称")
    private String spaceUsageName;

    @ApiModelProperty("空间设计风格")
    private String spaceStyle;

    @ApiModelProperty("所属方案id")
    private Integer solutionId;

    @ApiModelProperty("所属方案名称")
    private String solutionName;

    @ApiModelProperty("空间设计价格")
    private BigDecimal SpaceDesignPrice;

    @ApiModelProperty("空间软装选配项列表")
    private List<OptionalSoftResponse> optionalSoftResponseList;

    @ApiModelProperty("空间硬装选配项列表")
    private List<HardItemSimple> hardItemList;

    @ApiModelProperty("软装高级选配包列表")
    private List<AddBagDetail> softAddBagList;

    private Integer visibleFlag;//是否支持可视化 1支持 0不支持

    @ApiModelProperty("空间图片集合")
    private List<RoomPictureDto> roomPictureDtoList;

}
