package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-06-27 15:21
 */
@Data
@Accessors(chain = true)
public class NodeScheduleListResponse {

    @ApiModelProperty(value = "休假时间列表(yyyy-MM-dd)")
    private List<String> holidays;

    @ApiModelProperty("节点名")
    private String nodeName;

    @ApiModelProperty("节点描述")
    private String nodeDesc;
}
