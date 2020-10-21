package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

@Data
public class HttpWpfSuitRequest {
    private Integer wpfSuitId;  //全品家套装ID
    private Integer wpfStyleId;  //全品家风格ID
    private Integer category;   //平台分类    1 pc  2  h5
    private String mobile;  //手机号码
}
