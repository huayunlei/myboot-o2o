package com.ihomefnt.o2o.intf.domain.toc.vo.response;

import com.ihomefnt.o2o.intf.domain.toc.dto.PrizeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/11/16 0016.
 */
@Data
@ApiModel("新用户奖品信息")
public class PrizeListResponse {

    @ApiModelProperty("礼品列表")
    private List<PrizeDto> prizeDtoList;

}
