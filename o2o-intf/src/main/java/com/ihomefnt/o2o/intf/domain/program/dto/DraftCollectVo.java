package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-27 16:44
 */
@Data
@Accessors(chain = true)
public class DraftCollectVo {
        private String spaceUsageName;
        @ApiModelProperty("户型id")
        private Integer houseTypeId;
        private Integer spaceUsageId;
        private List<Integer> softCollectList;
        private List<Integer> hardItemCollectList;
}
