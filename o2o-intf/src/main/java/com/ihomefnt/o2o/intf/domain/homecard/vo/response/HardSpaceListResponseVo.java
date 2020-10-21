package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HardStandardSpaceResponse;
import lombok.Data;

import java.util.List;

@Data
public class HardSpaceListResponseVo {
    private List<HardStandardSpaceResponse> spaceList;
    private String seriesName;
}
