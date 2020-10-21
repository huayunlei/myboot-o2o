package com.ihomefnt.o2o.intf.domain.demo.vo.response;

import com.ihomefnt.o2o.intf.domain.demo.vo.DemoButtonInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("演示按钮返回")
public class DemoCommonResponse {

    @ApiModelProperty("演示状态名称")
    private List<String> mainCoreNames;

    @ApiModelProperty("演示按钮列表")
    private DemoButtonInfoVo demoButtonInfo;
}
