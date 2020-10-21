package com.ihomefnt.o2o.api.controller.program;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.BuildingSearchRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.CreateLoanResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.ProjectSearchVo;
import com.ihomefnt.o2o.intf.domain.program.dto.QueryAvailableChildCategoryRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceAbleDto;
import com.ihomefnt.o2o.intf.domain.program.dto.StandardUpgradeResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.*;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AddBagDetail;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardItemSelection;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceEntity;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CreateOrderResponse;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IdCardVerificationUtil;
import com.ihomefnt.o2o.intf.proxy.art.ArtCategoryProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.loan.LoanService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0 产品方案下单
 *
 * @author ZHAO
 */
@RestController
@Api(tags = "【产品方案数据查询API】")
@RequestMapping("/productprogram")
public class ProductProgramController {

    @Autowired
    UserService userService;

    @Autowired
    DicProxy dicProxy;

    @Autowired
    ArtCategoryProxy artCategoryProxy;

    @Autowired
    ProductProgramService productProgramService;

    @Autowired
    HomeV5PageService homeV5PageService;

    @Autowired
    private ProductProgramOrderService orderService;

    @Autowired
    private LoanService loanService;

    @Autowired
    HomeCardService homeCardService;

    @ApiOperation(value = "查询用户可选的方案", notes = "楼盘户型专属方案")
    @PostMapping(value = "/getUserSpecificProgram")
    public HttpBaseResponse<UserSpecificProgramResponseVo> getUserSpecificProgram(@RequestBody HttpProductProgramRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        UserSpecificProgramResponseVo response = productProgramService.getUserSpecificProgram(request, userDto.getId());
        return HttpBaseResponse.success(response);
    }

    /**
     * @param request
     * @return
     * @deprecated 新版本已废弃
     */
    @ApiOperation(value = "查询方案详情页", notes = "方案详情")
    @PostMapping(value = "/getProgramDetailById")
    @Deprecated
    public HttpBaseResponse<ProgramDetailResponse> getProgramDetailById(@RequestBody HttpProgramDetailRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        ProgramDetailResponse response = productProgramService.getProgramDetailById(request);

        //查询设计师信息
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }
        List<DesignerInfoForProgramResponse> designerList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(response.getSpaceList())) {
            List<Integer> roomIds = new ArrayList<>();
            for (SpaceEntity entity : response.getSpaceList()) {
                roomIds.add(entity.getRoomId());
            }

            if (CollectionUtils.isNotEmpty(roomIds)) {
                designerList = productProgramService.queryDesignerListByRoomIds(roomIds);
            }
        }
        response.setDesignerList(designerList);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询用户信息", notes = "用户信息[已不用]")
    @PostMapping(value = "/getUserInfo")
    public HttpBaseResponse<UserInfoResponse> getUserInfo(@RequestBody AladdinUserInfoRequest request) {
        if (request == null || StringUtils.isBlank(request.getMobileNum())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        UserInfoResponse response = productProgramService.getUserInfo(request);
        return HttpBaseResponse.success(response);
    }


    @ApiOperation(value = "查询空间详情页", notes = "空间详情")
    @PostMapping(value = "/getSpaceDetailById")
    public HttpBaseResponse<SpaceDetailResponse> getSpaceDetailById(@RequestBody HttpSpaceDetailRequest request) {
        if (request == null || request.getSpaceId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        SpaceDetailResponse response = productProgramService.getSpaceDetailById(request);
        //查询设计师信息
        if (response != null) {
            List<Integer> roomIds = new ArrayList<>();
            roomIds.add(request.getSpaceId());

            List<DesignerInfoForProgramResponse> designerList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(roomIds)) {
                designerList = productProgramService.queryDesignerListByRoomIds(roomIds);
            }

            response.setDesignerList(designerList);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询硬装标准对比", notes = "硬装标准对比")
    @PostMapping(value = "/queryHardStandard")
    public HttpBaseResponse<QueryHardStandardListResponseVo> queryHardStandard(@RequestBody HttpBaseRequest request) {
        List<HardStandardResponse> responseList = productProgramService.queryHardStandardGroup();
        return HttpBaseResponse.success(new QueryHardStandardListResponseVo(responseList));
    }

    @ApiOperation(value = "根据套系查询硬装标准", notes = "根据套系查询硬装标准")
    @PostMapping(value = "/queryHardStandardDetail")
    public HttpBaseResponse<HardStandardDetailListResponseVo> queryHardStandardDetail(@RequestBody HardStandardRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HardStandardDetailListResponseVo response = productProgramService.queryHardStandardDetail(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "用户房产信息列表", notes = "用户房产信息列表")
    @PostMapping(value = "/queryUserHouseList")
    public HttpBaseResponse<UserHouseListResponseVo> queryUserHouseList(@RequestBody BuildingSearchRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        List<HouseResponse> responseList = productProgramService.queryUserHouseList(userDto.getId(), request.getType());
        return HttpBaseResponse.success(new UserHouseListResponseVo(responseList));
    }

    @ApiOperation(value = "全品家流程说明", notes = "全品家流程说明")
    @PostMapping(value = "/queryFlowDesc")
    public HttpBaseResponse<QueryFlowDescListResponseVo> queryFlowDesc(@RequestBody HttpBaseRequest request) {
        List<DicResponse> responseList = productProgramService.queryFlowDesc();
        return HttpBaseResponse.success(new QueryFlowDescListResponseVo(responseList));
    }

    @ApiOperation(value = "根据方案ID集合查询可选增配包信息", notes = "根据方案ID集合查询可选增配包信息")
    @PostMapping(value = "/queryAddBagByProgramIds")
    public HttpBaseResponse<AddBagDetailListResponseVo> queryAddBagByProgramIds(@RequestBody AddBagSearchRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        List<AddBagDetail> responseList = productProgramService.queryAddBagByProgramIds(request);
        return HttpBaseResponse.success(new AddBagDetailListResponseVo(responseList));
    }

    @ApiOperation(value = "根据方案ID或者空间id集合查询满足条件的所有升级包", notes = "套装:9,和空间组合:10")
    @PostMapping(value = "/querySolutionStandardUpgrades")
    public HttpBaseResponse<SolutionStandardUpgradeTotalResponse> querySolutionStandardUpgrades(
            @RequestBody SolutionStandardUpgradesSearchRequest request) {
        if (request == null || request.getOrderType() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Integer orderType = request.getOrderType();
        List<Integer> roomIdList = request.getRoomIdList();
        if (orderType.intValue() == OrderConstant.ORDER_TYPE_SUIT) {
            roomIdList = orderService.getRoomIdListBySuitId(request.getProgramId());
        }

        if (CollectionUtils.isEmpty(roomIdList)) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }

        SolutionStandardUpgradeTotalResponse responseList = productProgramService
                .querySolutionStandardUpgrades(roomIdList);
        //选配包去重
        if (responseList != null && CollectionUtils.isNotEmpty(responseList.getUpgradesList())) {
            List<SolutionStandardUpgradesResponse> upgradesResponseList = new ArrayList<>();
            for (SolutionStandardUpgradesResponse upgradeItem : responseList.getUpgradesList()) {
                if (upgradeItem != null && CollectionUtils.isNotEmpty(upgradeItem.getUpgradeItems())) {
                    List<StandardUpgradeResponseVo> standardUpgradeResponseVoList = new ArrayList<>();
                    for (StandardUpgradeResponseVo vo : upgradeItem.getUpgradeItems()) {
                        boolean contain = false;
                        for (StandardUpgradeResponseVo responseVo : standardUpgradeResponseVoList) {
                            if (responseVo.getSpuId().equals(vo.getSpuId())) {
                                contain = true;
                            }
                        }
                        if (!contain) {
                            standardUpgradeResponseVoList.add(vo);
                        }
                    }
                    upgradeItem.setUpgradeItems(standardUpgradeResponseVoList);
                    upgradesResponseList.add(upgradeItem);
                }
            }
            responseList.setUpgradesList(upgradesResponseList);
        }

        return HttpBaseResponse.success(responseList);
    }

    @ApiOperation(value = "查询置家顾问版块基础数据", notes = "查询置家顾问版块基础数据")
    @PostMapping(value = "/queryAdviserBaseInfo")
    public HttpBaseResponse<AdviserBuildingInfoListResponseVo> queryAdviserBaseInfo(@RequestBody ProjectSearchVo request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        List<AdviserBuildingInfoResponse> responseList = productProgramService.queryAdviserBaseInfo(request);
        return HttpBaseResponse.success(new AdviserBuildingInfoListResponseVo(responseList));
    }

    @ApiOperation(value = "查询所有已上线方案的公司", notes = "查询所有已上线方案的公司")
    @PostMapping(value = "/queryAvailableSolutionCompany")
    public HttpBaseResponse<List<AdviserCompanyInfoResponse>> queryAvailableSolutionCompany(@RequestBody HttpBaseRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        List<AdviserCompanyInfoResponse> responseList = productProgramService.queryAvailableSolutionCompany();
        return HttpBaseResponse.success(responseList);
    }

    @ApiOperation(value = "查询项目方案版块资质", notes = "查询项目方案版块资质")
    @PostMapping(value = "/judgeAdviserQualification")
    public HttpBaseResponse<JudgeAdviserQualificationResponseVo> judgeAdviserQualification(@RequestBody HttpBaseRequest request) {
        if (request == null
                || StringUtils.isBlank(request.getMobileNum())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        boolean isAdviserFlag = productProgramService.judgeAdviserQualification(request.getMobileNum());
        return HttpBaseResponse.success(new JudgeAdviserQualificationResponseVo(isAdviserFlag));
    }

    @ApiOperation(value = "根据楼盘ID分区ID查询方案列表", notes = "根据楼盘ID分区查询方案列表")
    @PostMapping(value = "/queryProgramListByBuildingId")
    public HttpBaseResponse<AdviserBuildingProgramResponse> queryProgramListByBuildingId(@RequestBody HttpProductProgramRequest request) {
        if (request == null || request.getHouseProjectId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        AdviserBuildingProgramResponse response = productProgramService.queryProgramListByBuildingId(request, request.getWidth());
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据项目id查询户型信息", notes = "根据项目id查询户型信息")
    @PostMapping(value = "/queryHouseListByProjectId")
    public HttpBaseResponse<List<AdviserBuildingHouseTypeResponse>> queryHouseListByProjectId(@RequestBody HouseProjectRequest request) {
        if (request == null || request.getBuildingId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        List<AdviserBuildingHouseTypeResponse> response = productProgramService.queryHouseListByProjectId(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询各空间可选软装列表", notes = "查询各空间可选软装列表",hidden = true)
    @PostMapping(value = "/queryOptionalSoftList")
    public HttpBaseResponse<OptionalSoftListResponseVo> queryOptionalSoftList(@RequestBody OptionalSoftRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken()) || request.getRoomId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        List<OptionalSoftResponse> optionalSoftResponses = productProgramService.queryOptionalSoftList(request.getRoomId(), request.getSkuId(),
                request.getWidth());
        return HttpBaseResponse.success(new OptionalSoftListResponseVo(optionalSoftResponses));
    }

    @ApiOperation(value = "查询方案软装替换、升级包、增配包", notes = "查询方案软装替换、升级包、增配包")
    @PostMapping(value = "/querySolutionExtraInfo")
    public HttpBaseResponse<SolutionExtraInfoResponse> querySolutionExtraInfo(@RequestBody SolutionExtraInfoRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        List<Integer> roomIdList;
        if (CollectionUtils.isEmpty(request.getRoomIds()) && CollectionUtils.isNotEmpty(request.getProgramIds()) && request.getProgramIds().size() == 1) {
            roomIdList = orderService.getRoomIdListBySuitId(request.getProgramIds().get(0));
        } else {
            roomIdList = request.getRoomIds();
        }

        SolutionExtraInfoResponse extraInfoResponse = productProgramService.querySolutionExtraInfo(roomIdList, request.getProgramIds(), request.getWidth());
        return HttpBaseResponse.success(extraInfoResponse);
    }

    @ApiOperation(value = "查询方案可替换空间信息", notes = "查询方案可替换空间信息",hidden = true)
    @PostMapping(value = "/queryOptionalSpaceList")
    public HttpBaseResponse<OptionalSpaceListResponseVo> queryOptionalSpaceList(@RequestBody HttpProgramDetailRequest request) {
        if (request == null || request.getProgramId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        List<OptionalSpaceResponse> replaceRoomList = productProgramService.queryOptionalSpaceList(request.getProgramId(), request.getWidth());
        return HttpBaseResponse.success(new OptionalSpaceListResponseVo(replaceRoomList));
    }

    @ApiOperation(value = "查询方案对比信息", notes = "查询方案对比信息",hidden = true)
    @PostMapping(value = "/queryContrastInfo")
    public HttpBaseResponse<ContrastInfoListResponseVo> queryContrastInfo(@RequestBody HttpProductProgramRequest request) {
        if (request == null || request.getHouseProjectId() == null
                || request.getHouseTypeId() == null || (request.getHouseId() == null || request.getCustomerHouseId() == null)) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        List<ContrastInfoResponse> contrastInfoList = productProgramService.queryContrastInfo(request, userDto.getId());
        return HttpBaseResponse.success(new ContrastInfoListResponseVo(contrastInfoList));
    }

    /**
     * @param request
     * @return
     * @deprecated 新版本已废弃
     */
    @ApiOperation(value = "根据户型查询所有方案效果信息", notes = "查询方案效果信息",hidden = true)
    @PostMapping(value = "/v5/querySolutionList")
    @Deprecated
    public HttpBaseResponse<SolutionEffectResponse> querySolutionList(@RequestBody SolutionListRequest request) {
        if (request == null || request.getHouseTypeId() == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SolutionEffectResponse response = productProgramService.querySolutionList(request);
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据户型查询所有方案效果信息", notes = "查询方案效果信息",hidden = true)
    @PostMapping(value = "/v529/querySolutionList")
    public HttpBaseResponse<SolutionEffectResponse> querySolutionInfoList(@RequestBody SolutionListRequest request) {
        if (request == null || request.getHouseTypeId() == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SolutionEffectResponse response = productProgramService.querySolutionInfoList(request);
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据户型查询所有方案效果信息", notes = "查询方案效果信息")
    @PostMapping(value = "/v545/querySolutionList")
    public HttpBaseResponse<SolutionEffectResponse> querySolutionDescList(@RequestBody SolutionListRequest request) {
        if (request == null || request.getApartmentId() == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SolutionEffectResponse response = productProgramService.querySolutionDescList(request);
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据户型查询所有方案效果信息", notes = "查询方案效果信息")
    @PostMapping(value = "/v545/queryRoomListBySolutionId")
    public HttpBaseResponse<SolutionEffectResponse> queryRoomListBySolutionId(@RequestBody RoomListRequest request) {
        if (request == null || request.getApartmentId() == null ||
                request.getOrderId() == null || request.getSolutionId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SolutionEffectResponse response = productProgramService.queryRoomListBySolutionId(request);
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.QUERY_FAILED, MessageConstant.QUERY_FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "根据空间设计id列表查询软硬装选配包信息", notes = "查询软硬装选配包信息")
    @PostMapping(value = "/v5/querySpaceDesignSelections")
    public HttpBaseResponse<SelectionResponse> querySpaceDesignSelections(@RequestBody SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SelectionResponse response = productProgramService.querySpaceDesignSelections(request);
        return HttpBaseResponse.success(response);
    }

    /**
     * @param request
     * @return
     * @deprecated
     */
    @ApiOperation(value = "根据空间ID和skuId查询软装更多数据", notes = "查询软装更多数据，已过时，由/v529/querySoftSkuList取代",hidden = true)
    @PostMapping(value = "/v526/querySoftSkuList")
    @Deprecated
    public HttpBaseResponse<OptionalSoftResponse> querySoftSkuList(@RequestBody MoreInfoRequest request) {
        if (request.getSkuId() == null || request.getSpaceId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        OptionalSoftResponse response = productProgramService.querySoftSkuList(request);
        return HttpBaseResponse.success(response);

    }

    /**
     * @param request
     * @return
     * @deprecated 新版本已废弃
     */
    @ApiOperation(value = "根据空间ID和itemId查询硬装更多数据", notes = "查询硬装更多数据",hidden = true)
    @PostMapping(value = "/v526/queryHardSelectionList")
    @Deprecated
    public HttpBaseResponse<List<HardItemSelection>> queryHardSelectionList(@RequestBody HardMoreRequest request) {
        if (request.getHardItemId() == null || request.getSpaceId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        List<HardItemSelection> response = productProgramService.queryHardSelectionList(request);
        return HttpBaseResponse.success(response);

    }

    /**
     * @param request
     * @return
     * @deprecated
     */
    @ApiOperation(value = "根据空间设计id列表查询软硬装选配包信息（v526）", notes = "查询软硬装选配包信息（v526）",hidden = true)
    @PostMapping(value = "/v526/querySpaceDesignList")
    @Deprecated
    public HttpBaseResponse<SelectionSimpleResponse> querySpaceDesignList(@RequestBody SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (homeCardService.getSpaceMarkMustUpdate(request.getAppVersion(),request.getBundleVersions(),request.getOsType())) {
            return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
        }
        SelectionSimpleResponse response = productProgramService.querySpaceDesignList(request);
        return HttpBaseResponse.success(response);

    }


    @ApiOperation(value = "根据空间设计id列表查询软硬装选配包信息（v529）", notes = "查询软硬装选配包信息（v529）")
    @PostMapping(value = "/v529/querySpaceDesignList")
    public HttpBaseResponse<SelectionSimpleResponse> querySpaceDesignList529(@RequestBody SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (homeCardService.getSpaceMarkMustUpdate(request.getAppVersion(),request.getBundleVersions(),request.getOsType())) {
            return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
        }
        SelectionSimpleResponse response = productProgramService.querySpaceDesignListAddBom(request);
        return HttpBaseResponse.success(response);

    }

    @ApiOperation(value = "查询已选方案草稿", notes = "已选方案草稿",hidden = true)
    @PostMapping(value = "/v5/querySolutionDraft")
    public HttpBaseResponse<SolutionDraftResponse> querySolutionDraft(@RequestBody QueryDraftRequest request) {
        if (request.getOrderId() == null && request.getDraftId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        SolutionDraftResponse response = homeV5PageService.querySolutionDraft(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "草稿查询", notes = "草稿查询")
    @PostMapping(value = "/v529/queryDraftInfo")
    public HttpBaseResponse<DraftInfoResponse> queryDraftInfo(@RequestBody QueryDraftRequest request) {
        if (request.getOrderId() == null && request.getDraftProfileNum() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        DraftInfoResponse response = homeV5PageService.queryDraftInfo(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "方案记录列表", notes = "草稿箱列表")
    @PostMapping(value = "/v529/queryDraftList")
    public HttpBaseResponse<DraftSimpleRequestPage> queryDraftList(@RequestBody QueryDraftRequest request) {
        if (request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        request.setPageNo(request.getPageNo() == null ? 1 : request.getPageNo());
        request.setPageSize(request.getPageSize() == null ? 10 : request.getPageSize());
        DraftSimpleRequestPage response = homeV5PageService.queryDraftList(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "签约方案总价和草稿总价格对比", notes = "签约方案总价和草稿总价格对比")
    @PostMapping(value = "/v529/compareOrderToDraft")
    public HttpBaseResponse<OrderDraftCheckResponse> compareOrderToDraft(@RequestBody QueryDraftRequest request) {
        if (request.getOrderId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        boolean response = homeV5PageService.compareOrderToDraft(request);
        return HttpBaseResponse.success(new OrderDraftCheckResponse(response));
    }

    @ApiOperation(value = "下单或更新订单", notes = "下单或更新订单")
    @PostMapping(value = "/v529/createOrUpdateFamilyOrder")
    public HttpBaseResponse<CreateOrderResponse> createOrUpdateFamilyOrder(@RequestBody FamilyOrderRequest request, HttpServletRequest req) {
        CreateOrderResponse createOrderResponse = homeV5PageService.createFamilyOrderByDraft(request, req);
        return HttpBaseResponse.success(createOrderResponse);
    }

    @ApiOperation(value = "下单并保存草稿", notes = "下单并保存草稿")
    @PostMapping(value = "/v529/createDraftAndOrder")
    public HttpBaseResponse<CreateOrderResponse> createDraftAndOrder(@RequestBody CreateOrderAndDraftRequest request) {
        if (request == null || request.getOrderId() == null || request.getDraftContent() == null) {
            return HttpBaseResponse.fail(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        CreateOrderResponse createOrderResponse = homeV5PageService.createDraftAndOrder(request);
        return HttpBaseResponse.success(createOrderResponse);
    }

    @ApiOperation(value = "查询下单参数", notes = "查询下单参数")
    @PostMapping(value = "/v529/queryOrderParam")
    public HttpBaseResponse<CreateFamilyOrderRequest> queryOrderParam(@RequestBody FamilyOrderRequest request) {
        CreateFamilyOrderRequest createOrderResponse = homeV5PageService.queryOrderParam(request);
        return HttpBaseResponse.success(createOrderResponse);
    }

    /**
     * 根据空间集合批量查询各空间下软装信息
     *
     * @param request
     * @return
     * @deprecated 新版本已废弃
     */
    @ApiOperation(value = "根据空间查询空间下软装信息", notes = "根据空间查询空间下软装信息,仅限单个空间查询，传多个也只查一个",hidden = true)
    @PostMapping(value = "/v529/querySpaceSoftList")
    @Deprecated
    public HttpBaseResponse<SelectionResponse> querySoftItemByRoom(@RequestBody SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.querySoftItemByRoom(request));
    }

    /**
     * 根据空间集合批量查询各空间下硬装信息
     *
     * @param request
     * @return
     * @deprecated 新版本已废弃
     */
    @ApiOperation(value = "根据空间查询空间下硬装信息", notes = "根据空间查询空间下硬装信息,仅限单个空间查询，传多个也只查一个",hidden = true)
    @PostMapping(value = "/v529/querySpaceHardList")
    @Deprecated
    public HttpBaseResponse<SelectionResponse> queryHardItemByRoom(@RequestBody SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryHardItemByRoom(request));
    }


    /**
     * 创建艾佳贷订单
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据根据订单号下单，并且申请艾佳贷", notes = "根据订单id创建艾佳贷订单")
    @PostMapping(value = "/v529/createAiJiaLoanOrder")
    public HttpBaseResponse<CreateLoanResponseVo> createAiJiaLoanOrder(@RequestBody CreateOrderAndLoanRequest request, HttpServletRequest req) {
        if (request.getOrderNum() == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userByToken = request.getUserInfo();
        if (userByToken == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        if (StringUtils.isBlank(request.getApplyer())) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, "真实姓名不能为空");
        }
        if (StringUtils.isBlank(request.getApplyerIdNum())) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, "身份证号码不能为空");
        }
        if (!IdCardVerificationUtil.validate(request.getApplyerIdNum())) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, "身份证号码不合法，请输入正确的身份证号码");
        }
        //查询订单信息
        AppOrderBaseInfoResponseVo orderBaseInfoResponseVo = orderService.queryAppOrderBaseInfo(request.getOrderNum());
        if (orderBaseInfoResponseVo != null) {
            BigDecimal contractAmount = orderBaseInfoResponseVo.getContractAmount();
            if (contractAmount != null && contractAmount.compareTo(BigDecimal.ZERO) > 0) {
                //已经签约方案,直接申请艾佳贷，失败不做处理
                CreateLoanRequestVo createLoanRequest = new CreateLoanRequestVo();
                BeanUtils.copyProperties(request, createLoanRequest);
                return HttpBaseResponse.success(new CreateLoanResponseVo().setLoanId(loanService.createLoan(createLoanRequest)));
            } else {
                //没有签约方案，直接签约艾佳贷方案,然后再申请艾佳贷
                FamilyOrderRequest familyOrder = new FamilyOrderRequest();
                BeanUtils.copyProperties(request, familyOrder);
                familyOrder.setOrderId(request.getOrderNum());
                familyOrder.setHouseId(orderBaseInfoResponseVo.getCustomerHouseId());
                //签约艾佳贷方案
                CreateOrderResponse createOrderResponse = homeV5PageService.createFamilyOrder(userByToken, familyOrder, req, 2);
                try {
                    if (createOrderResponse != null) {
                        CreateLoanRequestVo createLoanRequest = new CreateLoanRequestVo();
                        BeanUtils.copyProperties(request, createLoanRequest);
                        //申请艾佳贷
                        return HttpBaseResponse.success(new CreateLoanResponseVo().setLoanId(loanService.createLoan(createLoanRequest)));
                    }
                } catch (Exception e) {
                    //艾佳贷申请失败，回滚艾佳贷方案，订单状态无法回滚
                    orderService.cancelOrderProgram(request.getOrderNum());
                    throw e;//传递异常
                }
            }
        }
        return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.SUBMIT_APPLY_FAILED);
    }

    /**
     * 查询软装可替换项列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询软装可替换项列表", notes = "查询软装可替换项列表")
    @PostMapping(value = "/v529/querySoftSkuList")
    public HttpBaseResponse<OptionalSoftResponse> querySoftSkuListV529(@RequestBody MoreInfoRequest request) {
        return HttpBaseResponse.success(productProgramService.querySoftSkuListV529(request));
    }

    /**
     * 查询硬装可选工艺列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询硬装可替换列表", notes = "查询硬装可选工艺列表")
    @PostMapping(value = "/v529/queryHardSkuList")
    public HttpBaseResponse<List<HardItemSelection>> queryHardSkuListV529(@RequestBody HardMoreRequest request) {
        return HttpBaseResponse.success(productProgramService.queryHardSkuListV529(request));
    }

    /**
     * 根据订单号查询房产信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据订单号查询房产信息", notes = "根据订单号查询房产信息")
    @PostMapping(value = "/queryBuildingHouseInfoByOrderNum")
    public HttpBaseResponse<BuildingHouseInfoResponse> queryBuildingHouseInfoByOrderNum(@RequestBody ProgramOrderDetailRequest request) {
        if (request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryBuildingHouseInfoByOrderNum(request));
    }

    /**
     * 阅读草稿
     */
    @ApiOperation(value = "阅读草稿", notes = "阅读草稿")
    @PostMapping("/readDraft")
    public HttpBaseResponse<Integer> readDraft(@RequestBody DraftSimpleRequest request) {
        productProgramService.readDraft(request.getDraftId());
        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "查询方案详情引导页数据", notes = "查询方案详情引导页数据， programId+orderId")
    @PostMapping("/queryProgramDetailsGuide")
    public HttpBaseResponse<ProgramDetailsGuideResponse> queryProgramDetailsGuide(@RequestBody HttpProgramDetailRequest request) {
        return HttpBaseResponse.success(productProgramService.queryProgramDetailsGuide(request));
    }

    @ApiOperation(value = "查询方案详情引导页V2", notes = "查询方案详情引导页数据V2， programId+orderId")
    @PostMapping("/v2/queryProgramDetailsGuide")
    public HttpBaseResponse<ProgramDetailsGuideNewResponse> queryProgramDetailsGuideV2(@RequestBody HttpProgramDetailRequest request) {
        return HttpBaseResponse.success(productProgramService.queryProgramDetailsGuideV2(request));
    }

    @ApiOperation(value = "查询方案商品清单", notes = "查询方案商品清单")
    @PostMapping("/queryProgramCommodityList")
    public HttpBaseResponse<ProgramCommodityListResponse> queryProgramCommodityList(@RequestBody HttpProgramDetailRequest request) {
        return HttpBaseResponse.success(productProgramService.queryProgramCommodityList(request));
    }

    @ApiOperation(value = "新版方案详情接口")
    @PostMapping("/queryProgramDetailNew")
    public HttpBaseResponse<ProgramDetailNewResponse> queryProgramDetailNew(@RequestBody HttpProgramDetailRequest request) {
        return HttpBaseResponse.success(productProgramService.queryProgramDetailNew(request));
    }

    @ApiOperation(value = "查询定制柜组合属性替换信息")
    @PostMapping("/queryCabinetPropertyList")
    public HttpBaseResponse<QueryCabinetPropertyListResponseNew> queryCabinetPropertyList(@RequestBody QueryCabinetPropertyListRequest request) {
        if (request.getSecondCategoryId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryCabinetPropertyList(request));
    }

    @ApiOperation(value = "订单方案列表（草稿+草稿方案混搭)")
    @PostMapping("/querySolutionAndDraftList")
    public HttpBaseResponse<DraftSimpleRequestPage> querySolutionAndDraftList(@RequestBody SolutionListRequest request) {
        if (request == null || request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.querySolutionAndDraftList(request));
    }

    @ApiOperation(value = "全品家清单（格式转换后的，和软硬装选配格式一致）")
    @PostMapping("/v554/querySelectedDesign")
    public HttpBaseResponse<SelectionSimpleResponse> querySelectedDesign(@RequestBody ProgramOrderDetailRequest request) {
        if (request.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.querySelectedDesign(request));
    }

    @ApiOperation(value = "一键替换功能")
    @PostMapping("/queryUnifiedReplacement")
    public HttpBaseResponse<ReplaceAbleResponse> queryUnifiedReplacement(@RequestBody ReplaceAbleDto request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryUnifiedReplacement(request));
    }

    @ApiOperation("根据父分类ID查询硬装分类晒选项")
    @PostMapping("/queryRelationCategory")
    public HttpBaseResponse<AvailableChildCategoryResponse> queryRelationCategory(@RequestBody QueryAvailableChildCategoryRequest request) {
        if (request.getCategoryId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryRelationCategory(request.getCategoryId()));
    }

    @ApiOperation(value = "是否可进行一键替换")
    @PostMapping("/queryOnceReplaceOrNot")
    public HttpBaseResponse<Boolean> queryOnceReplaceOrNot(@RequestBody SolutionListRequest request) {
        if (request == null || request.getOrderId() == null || request.getSolutionId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        return HttpBaseResponse.success(productProgramService.queryOnceReplaceOrNot(request));
    }

}