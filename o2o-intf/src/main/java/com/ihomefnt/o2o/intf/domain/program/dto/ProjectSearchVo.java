package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2020-02-12 13:53
 */
@Data
@ApiModel(value = "项目方案模糊检索")
public class ProjectSearchVo extends HttpBaseRequest {

    @ApiModelProperty("项目名称")
    private String projectName;

    private Integer companyId;//公司ID
}
