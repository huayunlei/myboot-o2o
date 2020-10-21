package com.ihomefnt.o2o.intf.domain.right.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huayunlei
 * @created 2018年12月18日 下午7:59:33
 * @desc 权益消费记录
 */
@ApiModel("RightsConsumerRecordVo")
@Data
public class RightsConsumerRecordVo {

    @ApiModelProperty("申请人")
    private Long operatorId;

    @ApiModelProperty("办结时间")
    private String consumerTime;

    @ApiModelProperty("消费单号")
    private String consumerNo;

    @ApiModelProperty("消费公司")
    private String consumerCompany;

    @ApiModelProperty("消费附件")
    private String consumeUrl;
    
    @ApiModelProperty("办结时间")
    private String consumerTimeStr;
    
    @ApiModelProperty("截止时间")
    private String expireTimeStr;
    
    @ApiModelProperty("状态 1:待处理 2：已完成 3:已取消")
    private Integer status;
}
