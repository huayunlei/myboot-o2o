package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 物料筛选项
 *
 * @author liyonggang
 * @create 2019-03-18 17:49
 */
@ApiModel("物料筛选项")
@Data
@Accessors(chain = true)
public class QueryMaterialOptionsVO {

    @ApiModelProperty("选项id")
    private Integer optionId;

    @ApiModelProperty("选项名称")
    private String optionValue;

    @ApiModelProperty("选项值")
    private List<OptionsBean> options;

    /**
     * 选项值信息
     */
    @ApiModel("选项值")
    @Data
    @Accessors(chain = true)
    public static class OptionsBean {
        @ApiModelProperty("id")
        private Integer attrId;
        @ApiModelProperty("名称")
        private String attrValue;

        public static OptionsBean build(Integer attrId, String attrValue) {
            return new OptionsBean().setAttrValue(attrValue).setAttrId(attrId);
        }
    }
}
