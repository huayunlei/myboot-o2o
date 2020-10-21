package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author jerfan cang
 * @date 2018/9/28 15:56
 */
@Data
@ApiModel("确权请求bean")
public class ConfirmRightRequest extends HttpBaseRequest {

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("权益分类编号")
    private Integer classifyNo;

    @ApiModelProperty("权益分类ID")
    private  Integer classifyId;

    @ApiModelProperty("确权的权益项集合 里面是权益项的id")
    private JSONArray itemIds;

    @ApiModelProperty("权益版本号 1为老权益 2为新权益")
    private Integer version;

    public JSONObject parseJson(){
        JSONObject param = new JSONObject();
        param.put("orderNum",orderNum);
        param.put("classifyNo",classifyNo);
        param.put("itemIds",itemIds);
        // 传入了底层不用
        param.put("classifyId",classifyId);
        return param;
    }

}
