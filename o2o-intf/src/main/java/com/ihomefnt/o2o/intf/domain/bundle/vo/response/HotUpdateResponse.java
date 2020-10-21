package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/11/22
 */

@ApiModel(description = "热更新返回")
@Data
@Accessors(chain = true)
public class HotUpdateResponse implements Serializable {

    private static final long serialVersionUID = 7028001790004843549L;

    @ApiModelProperty(value = "热更新模块信息列表")
    private List<Module> modules;

}
