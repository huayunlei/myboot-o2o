package com.ihomefnt.o2o.intf.domain.dna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wanyunxin
 * @create 2019-11-19 11:46
 */
@Data
@Accessors(chain = true)
public class DecorationProcessDto {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("主键消息")
    private String triggerNodeName;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("主节点id")
    private Integer masterNodeId;

    @ApiModelProperty("主节点信息")
    private String masterNode;

    @ApiModelProperty("子节点id")
    private Integer eventNodeId;

    @ApiModelProperty("子节点信息")
    private String eventNode;

    @ApiModelProperty("子节点url")
    private String eventNodeUrl;

    @ApiModelProperty("子节点内容")
    private String eventContent;

    @ApiModelProperty("创建时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
}
