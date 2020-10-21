package com.ihomefnt.o2o.intf.domain.designdemand.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-08-13 17:11
 */
@Data
@ApiModel
@Accessors(chain = true)
public class DesignDemandToolQueryRequest {

    private Integer userId;

    private String designDemandId;

    private Integer commitRecordId;

    private String mobile;

    private String authCode;

    private Integer orderId;

    private Integer status;

    private Integer taskId;

    private Integer houseTypeId;

    private String buildingName;

    private String houseNumber;

    @ApiModelProperty("1  - 舒克 2  - betaApp")
    private Integer source = 1;

    @ApiModelProperty("操作类型 用户确认 1 小艾确认 2")
    private Integer opSource = 1;

    @ApiModelProperty("用户ID")
    private Integer createUserId;

    private Integer errorCode;

    private Integer id;

    public String getDesignDemandId() {
        return designDemandId != null ? designDemandId : status != null && status == -2 && id != null ? id.toString() : null;
    }

    public Integer getTaskId() {
        return taskId != null ? taskId : designDemandId != null ? Integer.parseInt(designDemandId) : null;
    }

    public Integer getId() {
        return id != null ? id : taskId != null ? taskId : designDemandId != null ? Integer.parseInt(designDemandId) : null;
    }

    public Integer getCommitRecordId() {
        return commitRecordId == null ? taskId : commitRecordId;
    }
}
