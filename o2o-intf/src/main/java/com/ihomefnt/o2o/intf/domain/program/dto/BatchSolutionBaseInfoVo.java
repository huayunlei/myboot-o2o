package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-25 18:00
 */
@Data
public class BatchSolutionBaseInfoVo {

    @ApiModelProperty("方案基本信息集合")
    private List<SolutionBaseInfoVo> solutionBaseInfoList;
}
