package com.ihomefnt.o2o.intf.domain.flowmsg.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-20 19:18
 */
@Data
@Accessors(chain = true)
@ApiModel("信息流 list")
public class MessageRecordFlowResponseVo {

    @ApiModelProperty("消息流")
    private List<MessageCardInfoResponseVo> feedMessage;

    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private int totalCount;
}
