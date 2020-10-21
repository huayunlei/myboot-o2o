package com.ihomefnt.o2o.intf.domain.vote.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-10-18 14:58
 */
@Data
@ApiModel("问卷查询类")
public class QuestionRequest extends HttpBaseRequest {

    @ApiModelProperty("问题版本号")
    private Integer questionVersion;


}
