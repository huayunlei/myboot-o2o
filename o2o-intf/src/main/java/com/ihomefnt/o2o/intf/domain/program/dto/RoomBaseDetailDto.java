package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.RoomPictureDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardProductDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderRoomExtDataDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SoftProductDetailDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lindan on 2018/3/18 0018.
 */
@ApiModel(value = "软/硬装空间", description = "老接口继续从原有接口取值，新接口必须从当前对象取值")
@Data
public class RoomBaseDetailDto {

    @ApiModelProperty("空间ID")
    private Integer roomId;

    @ApiModelProperty("空间名称")
    private String roomName;

    @ApiModelProperty("空间设计用途id")
    private Integer roomUseId;

    @ApiModelProperty("空间设计用途名称")
    private String roomUseName;

    @ApiModelProperty("空间设计风格")
    private String spaceStyle;

    @ApiModelProperty("所属方案id")
    private Integer solutionId;

    @ApiModelProperty("所属方案名称")
    private String solutionName;

    @ApiModelProperty("空间设计价格")
    private BigDecimal solutionTotalSalePrice;

    @ApiModelProperty("空间效果图")
    private String pictureUrl;

    @ApiModelProperty("软装基础商品")
    private List<SoftProductDetailDto> softItemList;

    @ApiModelProperty("硬装基础商品")
    private List<HardProductDetailDto> hardItemList;

    @ApiModelProperty("硬装增配包list（app5.0）")
    private List<HardProductDetailDto> hardAddBagList;

    @ApiModelProperty("软装增配包list（app5.0）")
    private List<SoftProductDetailDto> softAddBagList;

    @ApiModelProperty("空间图片")
    private List<RoomPictureDto> roomPictureDtoList;

    @ApiModelProperty("空间图片任务类型,0：不存在任务，1：正常渲染任务，2：失败渲染任务")
    private Integer taskType = 0;

    @ApiModelProperty("仅供参考提示 0 不提示 1提示 默认0")
    private Integer referenceOnlyFlag = Constants.REFERENCE_ONLY_NO_SHOW;
}
