package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 保存组合类
 *
 * @author liyonggang
 * @create 2019-03-19 14:23
 */
@Data
@ApiModel("保存组合信息")
public class GroupSaveRequest extends HttpBaseRequest {

    @ApiModelProperty("默认组合id")
    private Integer defaultGroupId;

    @ApiModelProperty("物料替换信息")
    private List<MaterialReplaceListBean> materialReplaceList;

    @ApiModelProperty("默认组合数量")
    private Integer defaultGroupNum;

    @Data
    @ApiModel("物料替换信息")
    public static class MaterialReplaceListBean {

        @ApiModelProperty("组件id")
        private Integer componentId;

        @ApiModelProperty("替换后的物料id")
        private Integer replaceMaterialId;
    }
}
