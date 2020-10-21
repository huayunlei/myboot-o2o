package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户画作添加和推送屏幕
 *
 * @author liyonggang
 * @create 2019-01-17 16:27
 */
@Data
@ApiModel("用户画作添加和推送屏幕")
public class AddPersonalWorkPushRequest extends HttpBaseRequest {

    @ApiModelProperty("macId集合")
    private List<String> macIdList;

    @ApiModelProperty("图片url")
    private String imageUrl;

    @ApiModelProperty(value = "用户id",hidden = true)
    private Integer userId;

    @ApiModelProperty("图片id")
    private String imageId;

}
