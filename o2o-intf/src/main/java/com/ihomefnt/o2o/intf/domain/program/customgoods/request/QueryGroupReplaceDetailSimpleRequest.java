package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-21 10:04
 */
@Data
@ApiModel("组合替换批量查询")
@Accessors(chain = true)
public class QueryGroupReplaceDetailSimpleRequest extends HttpBaseRequest {

    @ApiModelProperty("组合替换查询信息")
    private List<QueryInfo> queryList;

    @Data
    @ApiModel("替换查询信息")
    public static class QueryInfo {
        @ApiModelProperty("默认组合id")
        private Integer defaultGroupId;

        @ApiModelProperty("组合数量")
        private Integer defaultGroupNum;

        @ApiModelProperty("替换后组合id")
        private Integer replaceGroupId;

        @ApiModelProperty("空间id")
        private Integer roomId;
    }
}
