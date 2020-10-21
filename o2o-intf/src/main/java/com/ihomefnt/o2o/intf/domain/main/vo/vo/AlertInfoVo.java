package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("首页核心操作区弹窗信息")
@Data
public class AlertInfoVo {
    private String alertType;
}
