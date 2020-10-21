package com.ihomefnt.o2o.intf.service.program;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.UserHousePropertiesResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.product.dto.SkuBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.program.dto.ProjectSearchVo;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceAbleDto;
import com.ihomefnt.o2o.intf.domain.program.vo.request.*;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AddBagDetail;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardItemSelection;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CreateFamilyOrderRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;

import java.util.List;
import java.util.Map;

/**
 * 产品方案SERVICE层
 *
 * @author ZHAO
 */
public interface ProductProgramService {
    /**
     * 查询用户可选的方案
     *
     * @param request
     * @param userId  用戶ID
     * @return
     */
    UserSpecificProgramResponseVo getUserSpecificProgram(HttpProductProgramRequest request, Integer userId);


    UserHousePropertiesResponseVo getUserSpecificProgram(Integer userId, Integer houseId);

    /**
     * 查询方案详情（整套）
     *
     * @param request
     * @return
     */
    ProgramDetailResponse getProgramDetailById(HttpProgramDetailRequest request);


    /**
     * 查询用户信息
     *
     * @param request
     * @return
     */
    UserInfoResponse getUserInfo(AladdinUserInfoRequest request);

    /**
     * 查询空间详情
     *
     * @param request
     * @return
     */
    SpaceDetailResponse getSpaceDetailById(HttpSpaceDetailRequest request);

    /**
     * 查询硬装标准对比
     *
     * @return
     */
    List<HardStandardResponse> queryHardStandardGroup();

    /**
     * 查询用户房产信息
     *
     * @param userId
     * @param type
     * @return
     */
    List<HouseResponse> queryUserHouseList(Integer userId, Integer type);

    /**
     * 查询全品家订单流程说明
     *
     * @return
     */
    List<DicResponse> queryFlowDesc();

    /**
     * 查询方案、全品家订单方案硬装标准
     *
     * @param request
     * @return
     */
    HardStandardDetailListResponseVo queryHardStandardDetail(HardStandardRequest request);

    /**
     * 根据方案ID集合查询可选增配包信息
     *
     * @param request
     * @return
     */
    List<AddBagDetail> queryAddBagByProgramIds(AddBagSearchRequest request);

    /**
     * 查询置家顾问版块基础数据
     *
     * @return
     */
    List<AdviserBuildingInfoResponse> queryAdviserBaseInfo(ProjectSearchVo request);

    /**
     * 查询所有已上线方案的公司
     * @return
     */
    List<AdviserCompanyInfoResponse> queryAvailableSolutionCompany();

    /**
     * 查询项目方案版块资质
     *
     * @param mobile
     * @return
     */
    boolean judgeAdviserQualification(String mobile);

    /**
     * 根据楼盘ID分区ID查询方案列表
     *
     * @param request
     * @return
     */
    AdviserBuildingProgramResponse queryProgramListByBuildingId(HttpProductProgramRequest request, Integer width);

    /**
     * 根据方案ID或者空间id集合查询满足条件的所有升级包
     *
     * @param roomIdList
     * @return
     */
    SolutionStandardUpgradeTotalResponse querySolutionStandardUpgrades(List<Integer> roomIdList);

    /**
     * 根据空间ID集合查询设计师信息
     *
     * @param roomIds
     * @return
     */
    List<DesignerInfoForProgramResponse> queryDesignerListByRoomIds(List<Integer> roomIds);

    /**
     * queryOptionalSoftList
     *
     * @param roomId
     * @param skuId
     * @param width
     * @return
     */
    List<OptionalSoftResponse> queryOptionalSoftList(Integer roomId, Integer skuId, Integer width);

    /**
     * 查询方案软装替换、升级包、增配包
     *
     * @param roomIds
     * @param programIds
     * @param width
     * @return
     */
    SolutionExtraInfoResponse querySolutionExtraInfo(List<Integer> roomIds, List<Integer> programIds, Integer width);

    /**
     * @param programId
     * @param width
     * @return Author: ZHAO
     * Date: 2018年5月2日
     */
    List<OptionalSpaceResponse> queryOptionalSpaceList(Integer programId, Integer width);

    /**
     * 查询方案对比信息
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年6月22日
     */
    List<ContrastInfoResponse> queryContrastInfo(HttpProductProgramRequest request, Integer userId);


    /**
     * 5.0查询可选方案信息
     *
     * @param request
     * @return
     */
    SolutionEffectResponse querySolutionList(SolutionListRequest request);

    /**
     * 5.0批量查询空间软硬装选配包信息
     *
     * @param request
     * @return
     */
    SelectionResponse querySpaceDesignSelections(SelectionRequest request);


    /**
     * 查询软装更多数据
     *
     * @param request
     * @return
     */
    @Deprecated
    OptionalSoftResponse querySoftSkuList(MoreInfoRequest request);

    /**
     * 查询硬装更多数据
     *
     * @param request
     * @return
     */
    @Deprecated
    List<HardItemSelection> queryHardSelectionList(HardMoreRequest request);

    /**
     * 查询软硬装选配包信息新
     *
     * @param request
     * @return
     */
    SelectionSimpleResponse querySpaceDesignList(SelectionRequest request);

    /**
     * 查询软硬装选配包信息新
     *
     * @param request
     * @return
     */
    SelectionSimpleResponse querySpaceDesignListAddBom(SelectionRequest request);

    /**
     * 方案列表查询（不包含爱家贷）
     *
     * @param request
     * @return
     */
    SolutionEffectResponse querySolutionInfoList(SolutionListRequest request);

    /**
     * 方案列表查询（不包含爱家贷）
     *
     * @param request
     * @return
     */
    SolutionEffectResponse querySolutionDescList(SolutionListRequest request);

    /**
     * 根据已选方案查询可选空间
     *
     * @param request
     * @return
     */
    SolutionEffectResponse queryRoomListBySolutionId(RoomListRequest request);

    /**
     * 根据空间集合批量查询各空间下软装信息
     *
     * @param request
     * @return
     */
    SelectionResponse querySoftItemByRoom(SelectionRequest request);

    /**
     * 根据空间集合批量查询各空间下硬装信息
     *
     * @param request
     * @return
     */
    SelectionResponse queryHardItemByRoom(SelectionRequest request);

    /**
     * 创建艾佳贷订单
     *
     * @param orderId
     * @return
     */
    CreateFamilyOrderRequest createAiJiaLoanOrder(Integer orderId, Integer userId, Integer houseId);

    /**
     * 查询软装替换项信息
     *
     * @param request
     * @return
     */
    OptionalSoftResponse querySoftSkuListV529(MoreInfoRequest request);

    /**
     * 查询硬装可选工艺列表
     *
     * @param request
     * @return
     */
    List<HardItemSelection> queryHardSkuListV529(HardMoreRequest request);

    /**
     * 根据订单号查询房产信息
     *
     * @param request
     * @return
     */
    BuildingHouseInfoResponse queryBuildingHouseInfoByOrderNum(ProgramOrderDetailRequest request);

    /**
     * 阅读草稿
     *
     * @param draftId
     * @return
     */
    void readDraft(String draftId);

    /**
     * 查询新版方案详情引导页数据
     *
     * @param request
     * @return
     */
    ProgramDetailsGuideResponse queryProgramDetailsGuide(HttpProgramDetailRequest request);

    /**
     * 方案商品清单
     *
     * @param request
     * @return
     */
    ProgramCommodityListResponse queryProgramCommodityList(HttpProgramDetailRequest request);

    /**
     * 新版方案详情
     *
     * @param request
     * @return
     */
    ProgramDetailNewResponse queryProgramDetailNew(HttpProgramDetailRequest request);


    /**
     * 批量查询sku指定扩展属性
     *
     * @param skuId
     * @param propertyType
     * @return
     */
    Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> batchQuerySkuExtPropertyBySkuIdListAndPropertyType(List<Integer> skuId, Integer propertyType);

    /**
     * 定制柜替换信息查询
     *
     * @param request
     * @return
     */
    QueryCabinetPropertyListResponseNew queryCabinetPropertyList(QueryCabinetPropertyListRequest request);

    /**
     * 查询组合颜色材质（定制柜使用）
     *
     * @param groupIdList
     * @return
     */
    Map<Integer, Map<String, String>> getColourAndTextureByGroupIdList(List<Integer> groupIdList);

    List<ServiceItemDto> mergeServiceItemList(List<ServiceItemDto> serviceItemDtoList);


    /**
     * 订单方案列表（草稿+草稿方案混搭）
     *
     * @param request
     * @return
     */
    DraftSimpleRequestPage querySolutionAndDraftList(SolutionListRequest request);

    /**
     * 全品家清单格式调整
     *
     * @param request
     * @return
     */
    SelectionSimpleResponse querySelectedDesign(ProgramOrderDetailRequest request);

    /**
     * 一键替换接口
     * @param request
     * @return
     */
    ReplaceAbleResponse queryUnifiedReplacement(ReplaceAbleDto request) ;

    /**
     * 查询自分类id
     * @param categoryId
     * @return
     */
    AvailableChildCategoryResponse queryRelationCategory(Integer categoryId);

    /**
     * 是否可进行一键替换
     * @param request
     * @return
     */
    Boolean queryOnceReplaceOrNot(SolutionListRequest request);

    /**
     * 根据项目id查询户型信息
     * @param request
     * @return
     */
    List<AdviserBuildingHouseTypeResponse> queryHouseListByProjectId(HouseProjectRequest request);

    ProgramDetailsGuideNewResponse queryProgramDetailsGuideV2(HttpProgramDetailRequest request);
}
