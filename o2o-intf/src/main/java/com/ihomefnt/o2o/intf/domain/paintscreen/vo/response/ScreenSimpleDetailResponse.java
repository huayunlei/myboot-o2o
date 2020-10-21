package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("作品详情页显示信息")
public class ScreenSimpleDetailResponse implements Serializable {


    private static final long serialVersionUID = -6111799985184120571L;

    @ApiModelProperty("作品基本信息")
    private ScreenSimpleResponse resourceDetail;

    @ApiModelProperty("浏览次数")
    private Integer browseCount;
    private Integer resourceId;
    private Integer resourceType;

    @ApiModelProperty("推送次数")
    private Integer sendCount;

    @ApiModelProperty("是否已经收藏: 0:未收藏,1 已收藏")
    private Integer isCollect;

    @ApiModelProperty("是否已购 true已购 false未购")
    private boolean isBuy = false;

    @ApiModelProperty("猜你喜欢")
    private List<ScreenSimpleResponse> guessScreenSimpleList;

    @ApiModelProperty("画集信息")
    private SelectedScreenSimpleResponse selectedScreenSimpleResponse;

}
