package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.OptionalSoftResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.RoomPictureDto;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@ApiModel("空间效果")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SpaceDesign implements Serializable {

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

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("所属方案名称")
    private String solutionName;

    @ApiModelProperty("空间设计价格")
    private Integer SpaceDesignPrice;

    @ApiModelProperty("空间软装选配项列表")
    private List<OptionalSoftResponse> optionalSoftResponseList;

    @ApiModelProperty("空间硬装选配项列表")
    private List<HardItem> hardItemList;

    @ApiModelProperty("软装高级选配包列表")
    private List<AddBagDetail> softAddBagList;

    private Integer visibleFlag;//是否支持可视化 1支持 0不支持

    @ApiModelProperty("空间组合信息")
    private List<BomGroupVO> bomGroupList;

    @ApiModelProperty("空间图片")
    private List<RoomPictureDto> roomPictureDtoList;

    @ApiModelProperty("离线渲染进度 0 无渲染任务，1：渲染中，2。已完成")
    private Integer drawProgress = 0;

    @ApiModelProperty
    private OrderRoomExtDataDto extData;

    @ApiModelProperty("dr是否存在此方案 0:否 1:是")
    private Integer drExistFlag;

    public SpaceDesign(Integer spaceDesignId) {
        this.spaceDesignId = spaceDesignId;
    }
}
