package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DeviceInfoDetailMapResponseVo {

    private List<Map<String, Object>> devices;

    private int count;

    private int mokanPageNo = 0;

    private int lechengPageNo = 0;
}
