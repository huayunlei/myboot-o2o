package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/5 20:58
 */
@ApiModel("查询订单权益(活动)详情 请求参数")
@Data
public class OrderRightsDetailRequestVo extends HttpBaseRequest{

    @ApiModelProperty("订单编号-必填")
    private Integer orderNum;

    @ApiModelProperty("来源：0/不传：app点击，1：分享H5，2：短信H5")
    private Integer source = 0;

    @ApiModelProperty("用户ID，source为0或1时，无用户ID；source为2时，有用户ID")
    private Integer userId;

    @ApiModelProperty("订单权益版本")
    private Integer rightVersion;

    public OrderRightsDetailRequestVo() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public JSONObject parseJson(){
        JSONObject param = new JSONObject();
        param.put("version",this.getAppVersion());
        return param;
    }
}
