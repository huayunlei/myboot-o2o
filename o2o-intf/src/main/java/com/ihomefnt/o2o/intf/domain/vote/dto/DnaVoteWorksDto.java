package com.ihomefnt.o2o.intf.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author liyonggang
 * @create 2019-05-07 10:57
 */
@Data
@ApiModel("dna投票作品")
@Accessors(chain = true)
public class DnaVoteWorksDto {

    private Integer id;

    @ApiModelProperty("图片")
    private String pictureUrl;

    @ApiModelProperty("设计师名称")
    private String authorName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String detailContent;
    @ApiModelProperty("获得票数")
    private Integer pollNum;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty("是否已经投票")
    private Boolean hasVote = Boolean.FALSE;

    @JsonIgnore
    private String errorMessage;
}
