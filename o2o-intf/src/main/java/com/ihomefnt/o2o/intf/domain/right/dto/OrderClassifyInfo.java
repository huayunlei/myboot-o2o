package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询我的订单
 * 权益分类节点
 * @author jerfan cang
 * @date 2018/10/11 9:34
 */
@Data
@ApiModel("OrderClassifyInfo")
public class OrderClassifyInfo {

    @ApiModelProperty("等级ID")
    private Integer gradeId ; // (integer, optional): 等级,

    @ApiModelProperty("等级名称")
    private String gradeName ; //(string, optional): 等级名称,

    @ApiModelProperty("权益分类")
    private Integer classifyId ; //(integer, optional): 权益分类,

    @ApiModelProperty("权益编号")
    private Integer classifyNo ; //(integer, optional): 权益编号,

    @ApiModelProperty("版本号")
    private String version ; //(string, optional): 版本号,

    @ApiModelProperty("权益分类名称")
    private String classifyName ; //(string, optional): 分类名称,

    @ApiModelProperty("配置额度")
    private Integer rightsConfigLimit ; //(integer, optional): 配置额度,

    @ApiModelProperty("可选额度")
    private Integer rightsConfirmedLimit ; //(integer, optional): 可选额度,

    @ApiModelProperty("排序")
    private Integer sort ; //(integer, optional): 排序,

    @ApiModelProperty("权益项列表")
    List<RigthtsItemDetail> itemDetailList ; //(array[RigthtsItemDetail], optional): 权益项列表
}
