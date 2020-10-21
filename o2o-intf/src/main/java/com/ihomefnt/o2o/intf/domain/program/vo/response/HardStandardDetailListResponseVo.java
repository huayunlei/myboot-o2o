package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class HardStandardDetailListResponseVo {
    private List<HardStandardSpaceResponse> spaceList;
    private String hardStandardDesc;
}
