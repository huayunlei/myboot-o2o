package com.ihomefnt.o2o.intf.domain.main.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-09-25 17:52
 */
@Data
@Accessors(chain = true)
public class MainPageExtDataResponse {

    @ApiModelProperty("是否有方案记录")
    private Boolean hasProgramRecord = false;
}
