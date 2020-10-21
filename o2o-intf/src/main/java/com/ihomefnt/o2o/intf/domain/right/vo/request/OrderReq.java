package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/5 22:11
 */
@Data
@ApiModel("查询我的订单权益 请求参数")
public class OrderReq extends HttpBaseRequest{
    /**
     * 订单编号
     */
    private Integer orderNum;

    public JSONObject parseJson(){
        JSONObject param = new JSONObject();
        param.put("orderNum",this.getOrderNum());
        param.put("width",this.getWidth());
        return param;
    }
}
