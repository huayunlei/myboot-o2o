package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-24 18:31
 */
@Data
@Accessors(chain = true)
public class QueryCabinetPropertyListRequest extends HttpBaseRequest {

    @ApiModelProperty("组合id集合")
    private List<GroupQueryRequest> queryList;

    @ApiModelProperty("二级分类id")
    private Integer secondCategoryId;

    @ApiModelProperty("空间id")
    private Integer roomId;

    @Data
    @Accessors(chain = true)
    public static class GroupQueryRequest{

        @ApiModelProperty(value = "柜体标签编号")
        private String cabinetType;

        @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
        private String cabinetTypeName;

        @ApiModelProperty(value = "组合id")
        private Integer groupId;

        @ApiModelProperty(value = "默认组合id")
        private Integer defaultGroupId;

        @ApiModelProperty(value = "默认组合数量")
        private Integer defaultGroupNum;

    }
}
