package com.ihomefnt.o2o.intf.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author liyonggang
 * @create 2019-05-07 10:54
 */
@Data
@ApiModel("dna投票记录")
public class DnaVoteRecordDto {

    private Integer id;

    private Integer dnaId;

    private Integer userId;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Integer delFlag;
}
