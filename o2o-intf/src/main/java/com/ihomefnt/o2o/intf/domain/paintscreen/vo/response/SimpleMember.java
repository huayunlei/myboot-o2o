package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;

/**
 * 成员简单信息
 *
 * @author liyonggang
 * @create 2018-12-05 12:46
 */
@Data
@ApiModel("成员简单信息")
public class SimpleMember implements Serializable {

    private static final long serialVersionUID = -6066919938435355515L;

    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("当前用户的绑定昵称")
    private String userNikeName;
    @ApiModelProperty("绑定状态 1已绑定,2待审核")
    private Integer bindState;
    @ApiModelProperty("绑定Id")
    private Integer bindId;
    @ApiModelProperty("绑定时间")
    private String bindTime;
    @ApiModelProperty("手机号")
    private String mobile;


    public void setBindTime(String bindTime) {
        if (StringUtils.isBlank(bindTime)) {
            this.bindTime = null;
        } else {
            try {
                this.bindTime = DateFormatUtils.format(DateUtils.parseDate(bindTime,new String[]{"yyyy-MM-dd HH:mm:ss"}), "yyyy/MM/dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
