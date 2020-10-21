package com.ihomefnt.o2o.intf.domain.toc.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("老用户抽奖请求参数")
public class LuckyDrawRequest extends HttpBaseRequest {

    @ApiModelProperty("被邀请人用户ID")
    private Integer inviterUserId;
}