package com.ihomefnt.o2o.intf.proxy.program;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingNoResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.QueryPriceDiffDTO;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.BatchQuerySolutionByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HardMoreRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HouseProjectRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.MoreInfoRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesignVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CreateFamilyOrderRequest;

import java.util.List;
import java.util.Map;

/**
 * 产品方案BOSS、WCM端服务代理
 *
 * @author ZHAO
 */
public interface ProductProgramProxy {
    /**
     * 根据用户信息查询可选方案信息
     *
     * @param userId         用户ID
     * @param houseProjectId 楼盘ID
     * @param zoneId         分区ID
     * @param houseTypeId    户型ID
     * @return
     */
    List<SolutionSketchInfoResponseVo> getUserSpecificProgram(Integer userId, Integer houseProjectId, Integer zoneId, Integer houseTypeId);

    /**
     * 根据方案id查询方案详情
     *
     * @param solutionId 方案ID
     * @return
     */
    SolutionDetailResponseVo getProgramDetailById(Integer solutionId);

    /**
     * 根据用户楼盘/户型查询不同的空间类型名称
     *
     * @param userId         用户ID
     * @param houseProjectId 楼盘ID
     * @param zoneId         分区ID
     * @param houseTypeId    户型ID
     * @return
     */
    List<SolutionRoomBaseInfoResponseVo> getRoomListByCondition(Integer userId, Integer houseProjectId, Integer zoneId, Integer houseTypeId);

    /**
     * 根据方案id/空间id查询空间详情
     *
     * @param solutionId     方案id
     * @param solutionRoomId 空间id
     * @return
     */
    SolutionRoomDetailResponseVo getRoomDetailByCondition(Integer solutionId, Integer solutionRoomId);

    /**
     * 查询不同的空间类型方案
     *
     * @param userId         用户ID
     * @param houseProjectId 楼盘ID
     * @param houseTypeId    户型ID
     * @param roomTypeId     空间ID
     * @return
     */
    List<AvailableSolutionRoomResponseVo> getRoomProgramListByCondition(Integer userId, Integer houseProjectId, Integer houseTypeId, Integer roomTypeId);

    /**
     * 根据电话号码查询用户信息
     *
     * @param mobileNum
     * @return
     */
    CustomerDetailInfo getUserInfo(String mobileNum);

    /**
     * 根据方案id集合查询可选增配包信息
     *
     * @param solutionIds
     * @return
     */
    List<SolutionAddBagInfoVo> queryExtraItemWithSolutionIds(List<Integer> solutionIds);


    /**
     * 根据skuid集合查询可选增配包信息
     *
     * @param solutionIds
     * @return
     */
    List<SolutionAddBagInfoVo> queryExtraItemWithSkuIds(List<Integer> skuIdList);

    /**
     * 根据楼盘id查询楼盘下各户型可选方案信息
     *
     * @param buildingId
     * @param zoneId     分区ID
     * @return
     */
    SolutionBuildingProgramResponseVo querySolutionSketchWithBuildingId(Integer buildingId, Integer zoneId,String solutionName,Integer houseTypeId);

    /**
     * 根据方案ID或者空间id集合查询满足条件的所有升级包
     *
     * @param roomIdList
     * @return
     */
    SolutionStandardUpgradesResponseVo querySolutionStandardUpgrades(List<Integer> roomIdList);

    /**
     * 查询空间可替换sku
     *
     * @param roomId
     * @param parentSkuId
     * @return
     */
    List<OptionalSkusResponseVo> queryOptionalSkus(Integer roomId, Integer parentSkuId);

    /**
     * 根据空间ID集合查询可替换sku
     *
     * @param roomIds
     * @return
     */
    List<OptionalSkusResponseVo> queryOptionalSkusByRoomIds(List<Integer> roomIds);

    /**
     * 查询省份、城市、楼盘、小区、户型
     *
     * @return Author: ZHAO
     * Date: 2018年4月12日
     */
    List<ProvinceLocationResponseVo> queryLocationInfo();

    /**
     * 查询所有已上线方案的所属楼盘及公司
     *
     * @return Author: ZHAO
     * Date: 2018年4月12日
     */
    List<UsableCompanyResponseVo> querySolutionBuildingInfo(ProjectSearchVo params);

    /**
     * 查询方案下可选空间设计
     *
     * @param solutionId
     * @return Author: ZHAO
     * Date: 2018年5月2日
     */
    List<OptionalRoomResponseVo> queryRoomDesignWithSolutionId(Integer solutionId);

    /**
     * 根据分区ID查询楼号-单号号-房号-户型
     *
     * @param zoneId
     * @return Author: ZHAO
     * Date: 2018年5月9日
     */
    List<BuildingNoResponseVo> queryBuildingUnitNoByZoneId(Integer zoneId);

    /**
     * 查询比较同户型下不同方案信息
     *
     * @param userId
     * @param houseProjectId
     * @param zoneId
     * @param houseTypeId
     * @return Author: ZHAO
     * Date: 2018年6月23日
     */
    List<CompareSolutionResponseVo> compareSolution(Integer userId, Integer houseProjectId, Integer zoneId, Integer houseTypeId);

    /**
     * 5.0查询可选方案信息
     *
     * @param houseTypeId
     * @return
     */
    SolutionEffectResponse querySolutionListByHouseId(Integer houseTypeId, Integer orderId, Long solutionId);

    /**
     * 5.4.4查询可选方案信息
     *
     * @param params
     * @return
     */
    SolutionEffectResponse querySolutionList(Map<String, Object> params);

    /**
     * 5.4.4根据已选方案查询可选空间
     *
     * @param params
     * @return
     */
    SolutionEffectResponse queryRoomListBySolutionId(Map<String, Object> params);

    /**
     * 5.0查询硬装选配项
     *
     * @return
     */
    RoomAndHardItemInfoVo queryHardSelectionList(List<Integer> spaceIds);

    /**
     * 批量查询户型列表(只有户型信息)
     *
     * @param houseIds
     * @return
     */
    List<THouseResponse> batchQueryHouse(List<Integer> houseIds);


    /**
     * 批量查询空间下软装内容（包含类目可视化）
     *
     * @param spaceIdList
     * @return
     */
    List<OptionalSkusResponseVo> batchQuerySoftItemByRoom(List<Integer> spaceIdList);

    /**
     * 批量查询空间下硬装内容
     *
     * @param spaceIdList
     * @return
     */
    List<SpaceDesignVo> batchQueryHardItemByRoom(List<Integer> spaceIdList);


    /**
     * 查询项目户型下可选方案数量
     *
     * @param houseProjectId
     * @param houseTypeId
     * @return Author: ZHAO
     * Date: 2018年7月24日
     */
    Integer queryAvailableSolutionCount(Integer houseProjectId, Integer houseTypeId, Long orderNum);


    /**
     * 查询软装替换项
     *
     * @param request
     * @return
     */
    OptionalSkusResponseVo queryReplaceSoftItemByRoom(MoreInfoRequest request);

    /**
     * 查询硬装可选工艺列表
     *
     * @param request
     * @return
     */
    SpaceDesignVo queryReplaceHardItemByRoom(HardMoreRequest request);

    /**
     * dolly校验下单方案、空间、sku是否下架
     *
     * @param request
     * @return
     */
    ValidateParamResultDto dollyValidateOrderParam(CreateFamilyOrderRequest request);


    /**
     * 查询方案及自由搭配空间详细信息+查询空间下软硬装sku的价格和差价
     *
     * @return
     */
    QueryPriceDiffDTO queryPriceDiff(QueryPriceDiffDTO queryPriceDiffDTO);

    /**
     * 查询aladdin用户房产
     *
     * @param userId
     * @return
     */
    List<AladdinUserHouseInfo> queryAladdinUserHouseInfo(Integer userId);

    /**
     * 查询有效大订单
     */
    List<MasterOrderSimpleInfo> queryMasterOrderIdsByHouseIds(List<Integer> houseIdList);

    /**
     * 批量查询方案信息
     *
     * @param solutionIdList
     * @return
     */
    BatchSolutionBaseInfoVo batchQuerySolutionBaseInfo(List<Integer> solutionIdList);

    /**
     * 根据户型获取可用方案
     *
     * @param request
     * @return
     */
    BatchSolutionBaseInfoVo batchQuerySolutionBaseInfo(BatchQuerySolutionByHouseIdRequestVo request);

    /**
     * roomID 对应关系
     *
     * @return
     */
    List<RelRoomIdVo> listAllRoomClassRel();

    /**
     * 查询定制柜组合替换信息
     *
     * @param collect
     * @return
     */
    QueryCabinetPropertyListResponseDolly queryCabinetPropertyList(List<Integer> collect);

    /**
     * 根据方案id查询服务费信息
     *
     * @param solutionId
     * @return
     */
    ServiceItemResponse querySolutionService(Long solutionId);

    /**
     * 缓存用户方案已读记录
     *
     * @param programId
     * @param userId
     */
    void markUserSolutionReadRecord(Integer programId, Integer userId);

    /**
     * 查询用户方案已读记录
     *
     * @param userId
     * @return
     */
    List<Integer> queryUserSolutionReadListByUserId(Integer userId);

    /**
     * 根据副分类id查询子分类
     *
     * @param categoryId
     * @return
     */
    AvailableChildCategoryResponse queryRelationCategory(Integer categoryId);

    /**
     * 根据手机号批量查询beta用户权限
     *
     * @param mobiles
     * @return
     */
    List<BetaUserDto> batchQueryBetaUserByMobileList(List<String> mobiles);

    /**
     * 查询中台户型信息
     * @param request
     * @return
     */
    List<HtpHouseTypeResponse> queryHouseListByProjectId(HouseProjectRequest request);
}
