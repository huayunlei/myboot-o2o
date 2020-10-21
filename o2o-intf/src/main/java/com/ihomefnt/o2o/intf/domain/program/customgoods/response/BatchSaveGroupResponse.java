package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-31 10:17
 */
@Data
public class BatchSaveGroupResponse {


    private List<AppGroupSimpleInfoList> appGroupSimpleInfoList;

    private BigDecimal totalPriceDifferences;
    @ApiModelProperty("颜色")
    private String colour;
    @ApiModelProperty("材质")
    private String texture;

    @Data
    public static class AppGroupSimpleInfoList {
        private Integer batchIndex;
        private Integer groupId;
        private Integer defaultGroupId;
        private String groupImage;
        private String groupName;
        private String cabinetType;
        private BigDecimal priceDifferences;
    }
}
