package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 画屏操作信息
 *
 * @author liyonggang
 * @create 2018-12-04 16:34
 */
@Data
@ApiModel("画屏操作信息")
public class OperatingScreenRequest extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = 4817877081660378237L;

    @ApiModelProperty("设备id,单个操作传此属性")
    private Integer deviceId;
    @ApiModelProperty("作品id 单个作品操作传此字段")
    private Integer productionId;
    @ApiModelProperty("播放类型：循环播放/固定播放等")
    private Integer playType;
    @ApiModelProperty("作品id集合，批量作品操作传此字段")
    private List<Integer> productionIdList;
    @ApiModelProperty("设备id集合,批量操作id传此")
    private List<Integer> deviceIdList;
    @ApiModelProperty("作品图片")
    private String imageId;
    @ApiModelProperty("画屏设备版本号")
    private String deviceVersion;
    @ApiModelProperty("收藏动作：收藏：2,取消收藏：1")
    private Integer collectionAction;
    @ApiModelProperty(value = "用户id", hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "资源类型：0 画作，1画集", hidden = true)
    private Integer resourceType;
    @ApiModelProperty("操作人 ID")
    private Integer operator;
    @ApiModelProperty("宽")
    private Integer imageWidth;
    @ApiModelProperty("高")
    private Integer imageHeight;
    @ApiModelProperty(value = "高",hidden = true)
    private Integer height;

}
