package com.ihomefnt.o2o.intf.proxy.controlcap;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.controlcap.vo.request.AccessOptionsByBuildingRequestVo;
import com.ihomefnt.o2o.intf.domain.controlcap.vo.response.ProgramListForHouseTypeResponseVo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingInfo;

/**
 * @author liyonggang
 * @create 2018-11-28 10:57
 */
public interface ControlCapService {


    /**
     * 根据楼盘id获取分区,楼栋,单元,房号
     *
     * @return
     */
    BuildingInfo getAccessOptionsByBuildingId(Integer buildingId);

    /**
     * 根据户型id获取当前户型方案列表
     *
     * @return
     */
    ProgramListForHouseTypeResponseVo getProgramListByHouseIdAndBuildingIdAndZoneId(AccessOptionsByBuildingRequestVo request);

    /**
     * 根据户型id获取其他户型方案列表
     *
     * @return
     */
    List<ProgramListForHouseTypeResponseVo> getProgramListByNotHouseTypeAndBuildingIdAndZoneId(AccessOptionsByBuildingRequestVo request);
}
