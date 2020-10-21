package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/27 13:41
 */
@Data
@ApiModel("权益项")
public class RightItemDto {


    @ApiModelProperty("项id")
    private Integer itemId; //项id

    @ApiModelProperty("项编号")
    private Integer itemNo; //项编号

    @ApiModelProperty("项名称")
    private  String itemName; //项名称

    @ApiModelProperty("别名")
    private  String itemPointName; //别名

    @ApiModelProperty("额度")
    private  String itemRewardQuota; //额度

    @ApiModelProperty("单位")
    private  String itemRewardUnit; //单位

    @ApiModelProperty("单位的值")
    private  String itemRewardUnitStr;// 单位的值

    @ApiModelProperty("奖励方式")
    private  String itemRewardWay; //奖励方式

    @ApiModelProperty("奖励方式的值")
    private  String itemRewardWayStr;//奖励方式的值

    @ApiModelProperty("url")
    private  String url; //url

    @ApiModelProperty("使用说明")
    private  String consumeDesc; //使用说明

    @ApiModelProperty("描述")
    private  String itemContent; //描述

    @ApiModelProperty("请求的订单对应该权益项的等级ID")
    private  Integer gradeId; //请求的订单对应该权益项的等级ID

    @ApiModelProperty("请求的订单对应该权益项的等级名称")
    private  String gradeName; // 请求的订单对应该权益项的等级名称

    @ApiModelProperty("权益项长副标题")
    private  String itemSimpleTitle; //权益项长副标题

    @ApiModelProperty("项编号")
    private Integer isUse; //是否使用 0-否 1-是

    @ApiModelProperty("已使用额度")
    private  String consumed; //已使用额度

    @ApiModelProperty("剩余使用额度")
    private  String surplus; // 剩余使用额度

    @ApiModelProperty("权益确权状态 0：未确权 1：已确权 2：申请中 3：消费中 4：已消费 5：已失效 6：已放弃")
    private  Integer consumeStatus; // 权益确权状态 0：未确权 1：已确权 2：申请中 3：消费中 4：已消费 5：已失效 6：已放弃

    @ApiModelProperty("权益项分类id")
    private Integer classifyId ; //权益项分类id

    @ApiModelProperty("权益分类编号")
    private Integer classifyNo ; //权益分类编号


    @ApiModelProperty("o2o-pai 给APP 拼接的副标题,底层响应没没有该字段 ")
    private String subtitle;
}
