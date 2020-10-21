package com.ihomefnt.o2o.intf.proxy.home;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;

import java.util.ArrayList;
import java.util.List;

public interface HouseProxy {

    List<AppHousePropertyResultDto> queryHouseByUserId(Integer userId);

    List<ProjectResponse> queryBuildingListWithIds(ArrayList<Integer> projectIds);

}
