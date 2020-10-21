package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/27 13:01
 */
@Data
@ApiModel("查询我的订单权益中单个分类的权益请求bean")
public class MyOrderRightByClassifyRequest  extends HttpBaseRequest{


    @ApiModelProperty("订单编号")
    private Integer orderNum;

    @ApiModelProperty("权益分类编号")
    private Integer classifyNo;

    public JSONObject parseJson(){
        JSONObject param = new JSONObject();
        param.put("width",this.getWidth());
        param.put("orderNum",this.getOrderNum());
        param.put("classifyNo",this.getClassifyNo());
        return param;
    }

}
