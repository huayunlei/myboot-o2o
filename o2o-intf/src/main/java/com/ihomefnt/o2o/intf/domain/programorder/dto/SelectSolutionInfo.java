package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 可选方案信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("可选方案信息")
public class SelectSolutionInfo implements Serializable {
    @ApiModelProperty("楼盘户型数量")
    private Integer houseTypeNum;//楼盘户型数量
    @ApiModelProperty("可选方案数量")
    private Integer selectSolutionNum;//可选方案数量
    @ApiModelProperty("可选方案集合")
    private List<ProgramResponse> solutionList;//可选方案集合

    public SelectSolutionInfo() {
        this.houseTypeNum = 0;
        this.selectSolutionNum = 0;
        this.solutionList = new ArrayList<>();
    }
}
