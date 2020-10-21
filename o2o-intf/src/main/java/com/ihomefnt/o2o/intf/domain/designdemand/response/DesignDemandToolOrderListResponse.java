package com.ihomefnt.o2o.intf.domain.designdemand.response;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-13 16:42
 */
@Data
@ApiModel("列表接口")
@Accessors(chain = true)
public class DesignDemandToolOrderListResponse {

    private Integer houseCount;

    private List<DesignDemandToolOrderSimpleInfo> houseList = Lists.newArrayList();

    @Data
    public static class DesignDemandToolOrderSimpleInfo {
        private Integer orderNum;
        private String houseLayoutName;
        private String buildingName;
        private Integer taskStatus;
        private String taskStatusName = "尚未提交过";
        private Integer orderStatus;
        @ApiModelProperty("0:正常,1:未交定金,2:已经确认方案")
        private Integer errorStatus = 0;
        private Integer houseTypeId;
        private String errorStatusStr = "";
    }

    public Integer getHouseCount() {
        return houseList.size();
    }
}
