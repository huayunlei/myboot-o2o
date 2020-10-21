package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/5 22:02
 */
@Data
@ApiModel("查询单个模块(订单权益分类)详情 请求参数")
public class ClassificationReq extends HttpBaseRequest{

    /**
     * 订单权益分类ID
     */
    private Integer classifyId;

    /**
     * 订单权益分类编号
     */
    private Integer classifyNo;

    @ApiModelProperty("要传权益等级")
    private Integer gradeId;

    @ApiModelProperty("订单编号-必填")
    private Integer orderNum;

    @ApiModelProperty("权益版本号 1为老权益 2为新权益")
    private Integer version;
}
