package com.ihomefnt.o2o.intf.domain.right.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询我的订单请求bean 根据权益分类展示
 * @author jerfan cang
 * @date 2018/10/11 9:16
 */
@Data
@ApiModel("查询我的订单请求bean")
public class QueryMyOrderRightItemListRequest  extends HttpBaseRequest {

    @ApiModelProperty("订单编号-必填")
    private Integer orderNum;

    @ApiModelProperty("等级id-可选-暂未用")
    private Integer gradeId;

    @ApiModelProperty("权益分类编号-可选-暂未用")
    private Integer classifyNo;

    @ApiModelProperty(hidden = true,value = "权益版本号 1老版本 2新版本")
    private Integer version = 1;
}
