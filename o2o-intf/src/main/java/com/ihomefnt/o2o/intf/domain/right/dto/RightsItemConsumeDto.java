package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huayunlei
 * @created 2018年12月20日 下午2:22:06
 * @desc 
 */
@Data
@ApiModel("RightsItemConsumeDto")
public class RightsItemConsumeDto extends RigthtsItemDetail {

	@ApiModelProperty("权益消费记录")
    private List<RightsConsumerRecordDto> consumerRecordVos;// 权益消费记录
    
    @ApiModelProperty("艾久久消费明细")
    private AiJiuJiuRewardDetailDto aiJiuJiuRewardDetailVo;// 艾久久消费明细
    
    @ApiModelProperty("全品家立减消费明细")
    private LoanRewardDetailDto loanRewardDetailVo;// 全品家立减消费明细
    
    @ApiModelProperty("艾先住消费明细")
    private AiXianzhuRewardDetailDto aiXianzhuRewardDetailVo;// 艾先住消费明细
}
