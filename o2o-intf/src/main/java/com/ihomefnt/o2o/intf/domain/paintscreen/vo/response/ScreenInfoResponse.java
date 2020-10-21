package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * 画屏设备信息
 *
 * @author liyonggang
 * @create 2018-12-05 12:37
 */
@Data
@ApiModel("画屏设备信息")
public class ScreenInfoResponse implements Serializable {

    private static final long serialVersionUID = -6894172712956641284L;

    @ApiModelProperty(value = "设备 ID")
    private Integer facilityId;

    @ApiModelProperty(value = "设备名称")
    private String facilityName;

    @ApiModelProperty(value = "设备macId")
    private String macId;

    @ApiModelProperty(value = "产品ID")
    private Integer productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "当前用户是否是管理员, 0:否,1:是")
    private Integer bindType = 0;

    @ApiModelProperty(value = "绑定时间")
    private String bindTime;

    @ApiModelProperty("绑定状态 1：绑定成功，2，审核中")
    private Integer bindState;

    @ApiModelProperty("设备状态 0 下线 1在线")
    private Integer facilityState;

    @ApiModelProperty(value = "设备绑定的用户列表")
    private List<SimpleMember> bindUserVoList;

    @ApiModelProperty("当前用户的绑定昵称")
    private String userNikeName;

    @ApiModelProperty("设备属性")
    private JSONObject facilityProperty;


    public void setBindTime(String bindTime) {
        if (StringUtils.isBlank(bindTime)) {
            this.bindTime = null;
        } else {
            try {
                this.bindTime = DateFormatUtils.format(DateUtils.parseDate(bindTime, new String[]{"yyyy-MM-dd HH:mm:ss"}), "yyyy/MM/dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
