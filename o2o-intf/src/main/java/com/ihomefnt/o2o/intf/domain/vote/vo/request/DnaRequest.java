package com.ihomefnt.o2o.intf.domain.vote.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * dna查询
 *
 * @author liyonggang
 * @create 2019-05-06 19:22
 */
@Data
@ApiModel("dna查询类")
public class DnaRequest extends HttpBaseRequest {

    @ApiModelProperty("总投票数量")
    private Integer dnaId;
}
