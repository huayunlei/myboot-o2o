package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdviserBuildingInfoListResponseVo {
    private List<AdviserBuildingInfoResponse> adviserBuildingInfoList;
}
