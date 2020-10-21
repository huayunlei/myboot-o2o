package com.ihomefnt.o2o.intf.manager.util.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jerfan cang
 * @date 2018/10/16 8:58
 */
@ApiModel("WcmExt")
public class WcmExt {

    @ApiModelProperty("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WcmExt() {
    }

    public WcmExt(String msg) {
        this.msg = msg;
    }
}
