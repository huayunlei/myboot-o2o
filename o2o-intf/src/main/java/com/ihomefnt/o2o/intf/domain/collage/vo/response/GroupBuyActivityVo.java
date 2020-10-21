package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * link GroupBuyActivityDto
 * @author jerfan cang
 * @date 2018/10/13 14:28
 */
@Data
@ApiModel("GroupBuyActivityVo")
public class GroupBuyActivityVo {

    @ApiModelProperty("活动id")
    private Integer id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动类型")
    private Integer type;

    @ApiModelProperty("活动开始时间 - 底层用的是 java.sql.Timestamp app用的是long")
    private long beginTime;

    @ApiModelProperty("活动结束时间 - 底层用的是 java.sql.Timestamp; app用的是 long")
    private long endTime;

    @ApiModelProperty("活动规则-终端不用此字段")
    private String activityRules;

    @ApiModelProperty("活动规则 根据 activityRules 分解成list")
    List<String> activityRuleList;

    @ApiModelProperty("创建时间")
    private long createTime;

    @ApiModelProperty("更新时间")
    private long updateTime;

    @ApiModelProperty("活动拼团商品销售总数")
    private Integer joinCount;

    @ApiModelProperty("团成员上限 可以超")
    private Integer groupLimit;


    /**
     * 把activityRules字段 分解
     * 并赋值给activityRuleList
     */
    public void value_activityRules2activityRuleList(){
        if(null != this.activityRules && this.activityRules.length() >0){
            String [] ruleArray = this.activityRules.split("；");
            this.activityRuleList = Arrays.asList(ruleArray);
            this.activityRules=null;
        }
    }

}
