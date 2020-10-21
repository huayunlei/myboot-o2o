package com.ihomefnt.o2o.service.proxy.home;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.home.HouseProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseProxyImpl implements HouseProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public List<AppHousePropertyResultDto> queryHouseByUserId(Integer userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        ResponseVo<List<AppHousePropertyResultDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_HOUSE_INFO_LIST_BY_USER_ID, paramMap,
                    new TypeReference<ResponseVo<List<AppHousePropertyResultDto>>>() {});
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }

    @Override
    public List<ProjectResponse> queryBuildingListWithIds(ArrayList<Integer> projectIds) {
        ResponseVo<List<ProjectResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_BUILDING_LIST_WITH_IDS, projectIds,
                    new TypeReference<ResponseVo<List<ProjectResponse>>>() {});
        } catch (Exception e) {
            return null;
        }

        if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
            return responseVo.getData();
        }
        return null;
    }
}
