package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 记录列表数据
 *
 * @author liyonggang
 * @create 2019-08-08 16:12
 */
@Data
@Accessors(chain = true)
public class ProgramOpinionRecordInfo {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Date time;

    private Integer status;

    @ApiModelProperty("dolly记录id")
    private Integer reviseOpinionId;

    @ApiModelProperty("dolly记录id")
    private Integer id;

    @ApiModelProperty("wcm记录id")
    private String programOpinionId;
}
