package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增/更新画屏设备信息
 *
 * @author liyonggang
 * @create 2018-12-05 12:32
 */
@Data
@ApiModel("新增/更新画屏设备信息")
public class AddOrUpdateScreenRequest extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = -9194116103069576358L;

    @ApiModelProperty("设备 ID")
    private Integer facilityId;
    @ApiModelProperty("设备名称")
    private String facilityName;
    @ApiModelProperty(value = "操作人 ID",hidden = true)
    private Integer operator;
    @ApiModelProperty("MAC地址")
    private String macId;
    @ApiModelProperty("产品 ID")
    private Integer productId;
    @ApiModelProperty("绑定用户 ID")
    private Integer userId;
    @ApiModelProperty("绑定用户昵称")
    private String userNikeName;
    @ApiModelProperty(value = "当前用户是否是管理员, 0:否,1:是")
    private Integer bindType;
    @ApiModelProperty(value = "手机号",hidden = true)
    private String mobile;
}
