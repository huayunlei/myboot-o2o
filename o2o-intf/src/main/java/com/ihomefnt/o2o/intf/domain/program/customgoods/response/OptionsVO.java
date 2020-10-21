package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 筛选项
 *
 * @author liyonggang
 * @create 2019-03-18 17:16
 */
@ApiModel("筛选项")
@Data
@Accessors(chain = true)
public class OptionsVO {

    @ApiModelProperty("选项id")
    private Integer optionId;

    @ApiModelProperty("选项值")
    private String optionName;

    @ApiModelProperty("选项值集合")
    private List<CopyWriterAndValue<Integer, String>> optionValues;

}