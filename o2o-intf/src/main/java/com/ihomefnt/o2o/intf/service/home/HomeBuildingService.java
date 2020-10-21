package com.ihomefnt.o2o.intf.service.home;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.*;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.BuildingAddUpdateRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homecard.dto.HouseInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.THouseResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.BuildingHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.toc.dto.AgentAndCustomerDto;

import java.util.List;

/**
 * APP4.0新版首页Service层
 * Author: ZHAO
 * Date: 2018年4月12日
 */
public interface HomeBuildingService {
    /**
     * 查询风格信息
     *
     * @return Author: ZHAO
     * Date: 2018年4月12日
     */
    BasePropertyListResponseVo getStyleInfo();

    /**
     * 查询省份、城市、楼盘、户型
     *
     * @return Author: ZHAO
     * Date: 2018年4月12日
     */
    List<BuildingProvinceResponse> getBuildingInfo();

    /**
     * 新增、编辑房产户型信息
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年4月13日
     */
    BuildingHouseInfoResponse addOrUpdateHouseInfo(BuildingAddUpdateRequest request);

    /**
     * 查询房产户型信息
     *
     * @param houseId
     * @return Author: ZHAO
     * Date: 2018年4月13日
     */
    BuildingHouseInfoResponse queryHouseInfoById(Integer houseId);

    /**
     * 查询房产可新增编辑次数
     *
     * @param houseId
     * @return Author: ZHAO
     * Date: 2018年4月13日
     */
    HouseAddEditCountResponse queryHouseAddEditCount(Integer houseId);

    /**
     * 订单状态扭转
     *
     * @param orginOrderStatus
     * @return Author: ZHAO
     * Date: 2018年4月17日
     */
    Integer getOrderStatus(Integer orginOrderStatus);

    /**
     * 设置房产相关信息
     *
     * @param houseInfoResponseVo
     * @return Author: ZHAO
     * Date: 2018年4月18日
     */
    HouseInfo setHouseInfoStandard(HouseInfoResponseVo houseInfoResponseVo);

    /**
     * 判断是否能查询测试楼盘
     *
     * @return Author: ZHAO
     * Date: 2018年4月26日
     */
    boolean isCanSearchTest();

    /**
     * 根据分区查询楼栋号、单元号、房号、户型
     *
     * @param zoneId
     * @param width
     * @return Author: ZHAO
     * Date: 2018年5月9日
     */
    BuildingNoListResponse queryBuildingUnitNoByZoneId(Integer zoneId, Integer width);

    THouseResponse queryBuildingByLayoutId(Integer layoutId);

    /**
     * 查询用户经纪人列表
     *
     * @param userId
     * @return
     */
    List<AgentAndCustomerDto> queryAgentCustomerList(Integer userId);

    //封装用户区分有无邀请码，封装经纪人房产信息
    UserIsHasCodeNewAndAgentHouseInfoResponse queryUserIsHasCodeNewAndAgentHouseInfo(HttpBaseRequest request);

    BuildingSchemeRecord buildingSchemeRecord(Integer buildingId);

    /**
     * 查询所有的城市楼盘信息
     *
     * @return
     */
    List<BuildingCityInfo> getBuildingInfoStartForCity();

    /**
     * 查询户型格局配置项
     *
     * @return
     */
    HousePatternConfigResponse queryHousePatternConfig();

    /**
     * 根据分区查楼栋单元
     *
     * @param zoneId
     * @return
     */
    List<BuildingNoInfo> queryBuildingUnitListByZoneId(Integer zoneId);

    /**
     * 根据分区，楼栋，单元查房间信息
     *
     * @param zoneId
     * @return
     */
    List<BuildingRoomInfo> queryRoomListByZoneIdAndBuildingIdAndUnitId(Integer zoneId,Integer buildingId,String unitNo);
}
