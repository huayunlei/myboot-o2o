package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.ProgramPicDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-03 15:06
 */
@Data
@ApiModel("新版方案详情")
public class ProgramDetailNewResponse {

    @ApiModelProperty("方案图片列表")
    private List<String> programImageList;

    @ApiModelProperty("方案图片对象")
    private List<ProgramPicDto> programImageDtoList;

    @ApiModelProperty("方案名称")
    private String solutionName;

    @ApiModelProperty("方案ID")
    private Integer solutionId;

    @ApiModelProperty("户型名称")
    private String houseTypeName;

    @ApiModelProperty("户型id")
    private Long apartmentId;

    @ApiModelProperty("户型版本")
    private Long apartmentVersion;

    @ApiModelProperty("户型格局")
    private String apartmentPattern;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

    @ApiModelProperty("面积")
    private String size;

    @ApiModelProperty("风格名称")
    private String solutionStyleName;

    @ApiModelProperty("方案标签")
    private List<String> tagList;

    @ApiModelProperty("方案价格名称")
    private String solutionPrice;

    @ApiModelProperty("设计理念")
    private String solutionDesignIdea;

    @ApiModelProperty("平面设计图")
    private String solutionGraphicDesignUrl;//平面设计图

    @ApiModelProperty("布局文案")
    private String layoutDesc;

    @ApiModelProperty("空间集合")
    List<ProgramCommodityListResponse.RoomInfo> roomList;

    @ApiModelProperty("户型布局图列表")
    private List<String> apartmentLayoutList;

    @ApiModelProperty("方案总价")
    private BigDecimal solutionPriceNum;

    @ApiModelProperty("免费赠品名称列表")
    private List<String> freeFurnitureNameList;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;

    @ApiModelProperty("是否是艾佳贷")
    private Boolean isLoan = Boolean.FALSE;

    @ApiModelProperty("方案全景图")
    private String solutionGlobalViewURL;

    @ApiModelProperty("方案视频介绍")
    private String solutionVideo;
}
