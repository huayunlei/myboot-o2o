package com.ihomefnt.o2o.intf.domain.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class DemoButtonInfoVo {
    private String mainCoreName;

    private List<DemoButtonSingleVo> buttonList;

}
