package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingCityInfo;
import lombok.Data;

import java.util.List;

@Data
public class BuildingInfoListResponseVo {
    private List<BuildingProvinceResponse> buildingProvinceList;

    private List<BuildingCityInfo> buildingCityInfoList;
}
