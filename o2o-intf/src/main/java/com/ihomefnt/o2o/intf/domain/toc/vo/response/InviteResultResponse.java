package com.ihomefnt.o2o.intf.domain.toc.vo.response;

import com.ihomefnt.o2o.intf.domain.toc.dto.InviteDto;
import com.ihomefnt.o2o.intf.domain.toc.dto.MoneyDto;
import com.ihomefnt.o2o.intf.domain.toc.dto.PaidDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("邀请结果信息")
public class InviteResultResponse {

    @ApiModelProperty("总邀请人数")
    private Integer inviteTotal;

    @ApiModelProperty("已付款人数")
    private Integer paidTotal;

    @ApiModelProperty("总金额")
    private Integer moneyTotal;

    @ApiModelProperty("邀请人列表")
    private List<InviteDto> inviteList;

    @ApiModelProperty("已付款人列表")
    private List<PaidDto> paidList;

    @ApiModelProperty("抽奖金额列表")
    private List<MoneyDto> moneyList;
}
