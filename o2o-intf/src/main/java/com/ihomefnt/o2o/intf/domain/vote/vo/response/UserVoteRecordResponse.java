package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteRecordDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-05-07 15:28
 */
@Data
@ApiModel("用户投票记录信息")
@Accessors(chain = true)
public class UserVoteRecordResponse {

    @ApiModelProperty("剩余次数")
    private Integer surplusTime = 0;

    @ApiModelProperty("投票信息")
    private List<DnaVoteRecordDto> recordList;
}
