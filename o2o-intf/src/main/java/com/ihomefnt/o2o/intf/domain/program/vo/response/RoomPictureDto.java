package com.ihomefnt.o2o.intf.domain.program.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间效果图信息
 *
 * @author liyonggang
 * @create 2019-04-17 16:45
 */
@Data
@ApiModel("空间效果图信息")
public class RoomPictureDto {

    @ApiModelProperty("是否首图 0不是 1是")
    private Integer isFirst;

    @ApiModelProperty("是否首图 0老图 1新图")
    private Integer picFlag = 0;

    @ApiModelProperty("0：空间图 1:dr效果图")
    private Integer type = 0;

    @ApiModelProperty("空间图片")
    private String pictureUrl;

    @ApiModelProperty("空间图片")
    private String pictureUrlOrigin;

    @ApiModelProperty("离线渲染任务id")
    private Integer taskId = 0;

}
