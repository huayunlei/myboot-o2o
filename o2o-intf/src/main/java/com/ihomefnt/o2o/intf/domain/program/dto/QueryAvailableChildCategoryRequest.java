package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-11-05 11:16
 */
@Data
public class QueryAvailableChildCategoryRequest extends HttpBaseRequest {

    @ApiModelProperty("父分类id")
    private Integer categoryId;
}
