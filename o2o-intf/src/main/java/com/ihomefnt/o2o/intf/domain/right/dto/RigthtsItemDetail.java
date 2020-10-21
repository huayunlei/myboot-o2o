package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询我的订单
 * 权益项节点
 * @author jerfan cang
 * @date 2018/10/11 9:40
 */
@Data
@ApiModel("RigthtsItemDetail")
public class RigthtsItemDetail {

    @ApiModelProperty("项id")
    private Integer itemId ; //(integer, optional): 项id,

    @ApiModelProperty("项编号")
    private Integer itemNo ; //(integer, optional): 项编号,

    @ApiModelProperty("项名称")
    private String itemName ; //(string, optional): 项名称,

    @ApiModelProperty("别名")
    private String itemPointName ; // (string, optional): 别名,

    @ApiModelProperty("额度")
    private String itemRewardQuota ; //(string, optional): 额度,

    @ApiModelProperty("单位")
    private Integer itemRewardUnit ; //(integer, optional): 单位,

    @ApiModelProperty("单位的值")
    private String itemRewardUnitStr ; //(string, optional),

    @ApiModelProperty("奖励方式")
    private Integer itemRewardWay ; //(integer, optional): 奖励方式,

    @ApiModelProperty("奖励方式的值")
    private String itemRewardWayStr ; //(string, optional),

    @ApiModelProperty("url")
    private String url ; //(string, optional): url,

    @ApiModelProperty("使用说明")
    private String consumeDesc ; //(string, optional): 使用说明,

    @ApiModelProperty("描述")
    private String itemContent ; //(string, optional): 描述,

    @ApiModelProperty("等级ID")
    private  Integer gradeId ; //(integer, optional): 等级,

    @ApiModelProperty("等级名称")
    private String gradeName ; //(string, optional): 等级名称,

    @ApiModelProperty("权益项长副标题")
    private String itemSimpleTitle ; // (string, optional): 权益项长副标题,

    @ApiModelProperty("是否使用 0-否 1-是")
    private  Integer isUse ; //(integer, optional): 是否使用 0-否 1-是,

    @ApiModelProperty("已使用额度")
    private String consumed ; //(string, optional): 已使用额度,

    @ApiModelProperty("剩余使用额度")
    private String surplus ; //(string, optional): 剩余使用额度,

    @ApiModelProperty("权益确权状态 0：未确权 1：已确权 2：申请中 3：消费中 4：已消费 5：已失效 6：已放弃")
    private Integer consumeStatus ; //(integer, optional): 权益确权状态 0：未确权 1：已确权 2：申请中 3：消费中 4：已消费 5：已失效 6：已放弃,

    @ApiModelProperty("权益项分类id")
    private Integer classifyId ; //(integer, optional): 权益项分类id,

    @ApiModelProperty("权益分类编号")
    private Integer classifyNo ; //(integer, optional): 权益分类编号
}
