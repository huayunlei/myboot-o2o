package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyonggang
 * @create 2018-12-10 19:05
 */
@Data
@ApiModel("推送专用")
public class ArtPushDto extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = -8244241179381116097L;
    @ApiModelProperty("画作id 使用应用提供的画作时传此字段(和userArtId二选一)")
    private Integer artId;
    @ApiModelProperty("资源类型 0:用户上传 1:应用提供")
    private Integer resourceType;
    @ApiModelProperty("图片id 使用自己上传的图片时传此字段(和artId二选一)")
    private Integer userArtId;
    @ApiModelProperty(value = "操作人 ID",hidden = true)
    private Integer operator;
    @ApiModelProperty("macId集合")
    private List<String> macIdList;
    @ApiModelProperty("推送类型：1:推屏，2:移除")
    private Integer pushType;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @ApiModelProperty("移除时传此")
    private String artImage;
}
