package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huayunlei
 * @created 2018年12月20日 下午2:22:06
 * @desc 
 */
@ApiModel("RightsItemConsumeVo")
@Data
public class RightsItemConsumeVo extends RigthtsItemDetailVo {

	@ApiModelProperty("权益消费记录")
    private List<RightsConsumerRecordVo> consumerRecordVos;// 权益消费记录
    
    @ApiModelProperty("艾久久消费明细")
    private AiJiuJiuRewardDetailVo aiJiuJiuRewardDetailVo;// 艾久久消费明细
    
    @ApiModelProperty("全品家立减消费明细")
    private LoanRewardDetailVo loanRewardDetailVo;// 全品家立减消费明细
    
    @ApiModelProperty("艾先住消费明细")
    private AiXianzhuRewardDetailVo aiXianzhuRewardDetailVo;// 艾先住消费明细

}
