package com.ihomefnt.o2o.intf.domain.dna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wanyunxin
 * @create 2019-11-22 18:14
 */
@Data
public class DeliveryTimeDto {

    @ApiModelProperty("需求确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmRequirementDate;

    @ApiModelProperty("实际开工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    @ApiModelProperty("维保开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date guaranteeDate;
}
