package com.ihomefnt.o2o.service.service.program;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.BatchSolutionBaseInfoVo;
import com.ihomefnt.o2o.intf.domain.program.dto.DraftCollectVo;
import com.ihomefnt.o2o.intf.domain.program.dto.RelRoomIdVo;
import com.ihomefnt.o2o.intf.domain.program.dto.SolutionBaseInfoVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.CompareDraftRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardItem;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardItemSelection;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionSelected;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesign;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.constant.programorder.SolutionCompareEnum;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.program.DraftProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanyunxin
 * @create 2019-06-25 11:09
 */
@Service
@SuppressWarnings("all")
public class DraftProgramServiceImpl implements DraftProgramService {

    private static final Logger LOG = LoggerFactory.getLogger(DraftProgramServiceImpl.class);

    @Autowired
    private ProductProgramOrderService orderService;

    @Autowired
    HomeV5PageService homeV5PageService;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;


    private static Integer showDesignUrlFlag = 1;

    //空间类型2表示房间
    private static Integer roomSpaceCode = 2;

    //是否高亮显示
    private static Integer rowHighLight = 1;

    private static String betaChangeMessage = "发生后台商品调整，请以清单为准";

    /**
     * 空间标识展示排序 客厅、餐厅、第三厅、第四厅 主卧、次卧、第三房、第四房、第五房、第六房、第七房 厨房、第二厨房 主卫、客卫、第三卫、第四卫
     * 主阳台、第二阳台、第三阳台、第四阳台 储藏间、第二储藏间 衣帽间、第二衣帽间
     * 20191128空间标识废弃，改为空间用途
     */
    private static List<Integer> ROOM_TYPE_ORDER_LIST = Arrays.asList(RoomUseEnum.ROOM_WHOLE.getCode(), RoomUseEnum.LIVING_ROOM.getCode(),
            RoomUseEnum.RESTAURANT.getCode(),RoomUseEnum.MASTER_ROOM.getCode(),RoomUseEnum.SECOND_ROOM.getCode(),RoomUseEnum.CHILDREN_ROOM.getCode()
            ,RoomUseEnum.STUDY_ROOM.getCode(),RoomUseEnum.ENTRANCE.getCode(),RoomUseEnum.ELDERLY_ROOM.getCode(),RoomUseEnum.TATAMI_ROOM.getCode()
            ,RoomUseEnum.SITTING_ROOM.getCode(),RoomUseEnum.GUEST_ROOM.getCode(),RoomUseEnum.GYM.getCode(),RoomUseEnum.ROOM_MULTIPLE_FUNC.getCode()
            ,RoomUseEnum.KITCHEN.getCode(),RoomUseEnum.BATHROOM.getCode(),RoomUseEnum.MASTER_BATHROOM.getCode(),RoomUseEnum.SECOND_BATHROOM.getCode()
            ,RoomUseEnum.BALCONY.getCode(),RoomUseEnum.REST_BALCONY.getCode(),RoomUseEnum.ROOM_BALCONY.getCode(),RoomUseEnum.STORAGE_ROOM.getCode()
            ,RoomUseEnum.CLOAK_ROOM.getCode(),RoomUseEnum.CORRIDOR.getCode());



    /**
     * 已选方案对比（草稿比对）
     *
     * @param request
     * @return
     */
    @Override
    public CompareDraftResponse compareDraftList(CompareDraftRequest request) {

        CompareDraftRequest.DraftSimpleListBean signDraftSimple = null;//签约方案简单信息
        List<Integer> solutionList = new ArrayList<>();//方案列表
        for (CompareDraftRequest.DraftSimpleListBean draftSimpleListBean : request.getDraftSimpleList()) {
            solutionList.add(draftSimpleListBean.getSolutionId());
            if (draftSimpleListBean.getDraftSignStatus() == Constants.DRAFT_SIGN_STATUS_HAS_SIGN) {
                signDraftSimple = draftSimpleListBean;
            }
        }
        List<CompareDraftRequest.DraftSimpleListBean> draftList = request.getDraftSimpleList().stream().filter(draftSimpleListBean ->
                draftSimpleListBean.getDraftSignStatus() != Constants.DRAFT_SIGN_STATUS_HAS_SIGN).collect(Collectors.toList());//未签约草稿信息

        Map<String, Object> stringObjectMap = concurrentQueryDraftList(draftList, request.getOrderId(), signDraftSimple,solutionList);
        SolutionSelected solutionSelected = null;
        DraftInfoResponse signDraftInfo = null;
        //批量查询方案信息
        BatchSolutionBaseInfoVo batchSolutionBaseInfoVo = (BatchSolutionBaseInfoVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_BATCH_SOLUTION_BASE_INFO.name());
        if (signDraftSimple != null) {
            solutionSelected = (SolutionSelected) stringObjectMap.get(ConcurrentTaskEnum.QUERY_PRODUCT_SOLUTION_SELECT_INFO.name());
            List<RelRoomIdVo> list = (List<RelRoomIdVo>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_ALL_ROOM_CLASS_REL.name());
            setLastCategoryInfo(list, solutionSelected);
            if (solutionSelected != null && batchSolutionBaseInfoVo != null && CollectionUtils.isNotEmpty(batchSolutionBaseInfoVo.getSolutionBaseInfoList())) {
                for (SolutionBaseInfoVo solutionBaseInfoVo : batchSolutionBaseInfoVo.getSolutionBaseInfoList()) {
                    if (solutionBaseInfoVo.getSolutionId() != null && solutionBaseInfoVo.getSolutionId().equals(signDraftSimple.getSolutionId())) {
                        solutionSelected.getSolutionInfo().setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(solutionBaseInfoVo.getSolutionGraphicDesignUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
                    }
                }
            }
            // 获取草稿基础信息
            signDraftInfo = (DraftInfoResponse) stringObjectMap.get(ConcurrentTaskEnum.QUERY_DRAFT_INFO.name());
        }
        List<DraftInfoResponse> draftInfoResponseList = new ArrayList<>();//草稿列表
        for (int i = 0; i < draftList.size(); i++) {
            draftInfoResponseList.add((DraftInfoResponse) stringObjectMap.get(ConcurrentTaskEnum.QUERY_DRAFT_INFO.name() + i));
        }
        if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
            for (DraftInfoResponse draftInfoResponse : draftInfoResponseList) {
                for (SolutionBaseInfoVo solutionBaseInfoVo : batchSolutionBaseInfoVo.getSolutionBaseInfoList()) {
                    if (solutionBaseInfoVo.getSolutionId() != null && solutionBaseInfoVo.getSolutionId().equals(draftInfoResponse.getDraftContent().getSolutionSelected().getSolutionId())) {
                        draftInfoResponse.getDraftContent().getSolutionSelected().setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(solutionBaseInfoVo.getSolutionGraphicDesignUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
                    }
                }
            }
        }
        return getCompareDraftResponse(solutionSelected, draftInfoResponseList, signDraftInfo);
    }

    /**
     * 设置快照类目信息跟草稿一致
     *
     * @param list
     * @param solutionSelected
     */
    private void setLastCategoryInfo(List<RelRoomIdVo> list, SolutionSelected solutionSelected) {
        if (solutionSelected != null && CollectionUtils.isNotEmpty(solutionSelected.getSpaceDesignSelected())) {
            solutionSelected.getSpaceDesignSelected().forEach(spaceDesign -> {
                if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                    spaceDesign.getHardItemList().forEach(hardItem -> {
                        if (CollectionUtils.isNotEmpty(list)) {
                            list.forEach(relRoomIdVo -> {
                                if (hardItem.getHardItemId() != null && relRoomIdVo.getRelRoomClassId() != null && relRoomIdVo.getRelRoomClassId().equals(hardItem.getHardItemId())) {
                                    hardItem.setHardItemId(relRoomIdVo.getRoomClassId());
                                    hardItem.setHardItemName(relRoomIdVo.getName());
                                }
                            });
                        }
                    });
                }
            });
        }
    }


    /**
     * 组装已选方案和草稿列表，输出方案对比信息
     *
     * @param solutionSelected
     * @param draftInfoResponseList
     * @return
     */
    private CompareDraftResponse getCompareDraftResponse(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, DraftInfoResponse signDraftInfo) {
        CompareDraftResponse compareDraftResponse = new CompareDraftResponse();
        setDraftSimpleInfoList(solutionSelected, draftInfoResponseList, compareDraftResponse, signDraftInfo);//组装方案简要信息
        List<DraftCollectVo> spaceColletBean = getSpaceSelectedBean(solutionSelected, draftInfoResponseList, signDraftInfo);//组装空间及软硬装项目合集
        setSolutionBaseInfoList(solutionSelected, draftInfoResponseList, compareDraftResponse, signDraftInfo);//组装基础信息
        setSpaceInfoList(solutionSelected, draftInfoResponseList, compareDraftResponse, signDraftInfo, spaceColletBean);//组装空间详细信息
        return compareDraftResponse;
    }


    /**
     * 组装方案简要信息
     *
     * @param solutionSelected
     * @param draftInfoResponseList
     * @param compareDraftResponse
     */
    private void setDraftSimpleInfoList(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, CompareDraftResponse compareDraftResponse, DraftInfoResponse signDraftInfo) {
        List<CompareDraftResponse.DraftSimpleInfoListBean> draftSimpleInfoList = new ArrayList<>();
        if (solutionSelected != null) {
            CompareDraftResponse.DraftSimpleInfoListBean draftSimpleInfoListBean = new CompareDraftResponse.DraftSimpleInfoListBean();
            draftSimpleInfoListBean.setDraftSignStatus(Constants.DRAFT_SIGN_STATUS_HAS_SIGN)
                    .setDraftName(signDraftInfo.getDraftName())
                    .setDesignUrl(solutionSelected.getSolutionInfo().getSolutionGraphicDesignUrl())
                    .setTotalPrice(solutionSelected.getTotalPrice());
            draftSimpleInfoList.add(draftSimpleInfoListBean);
        }
        if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
            draftInfoResponseList.forEach(draftInfoResponse -> {
                CompareDraftResponse.DraftSimpleInfoListBean draftSimpleInfoListBean = new CompareDraftResponse.DraftSimpleInfoListBean();
                draftSimpleInfoListBean.setDesignUrl(draftInfoResponse.getDraftContent().getSolutionSelected().getSolutionGraphicDesignUrl())
                        .setDraftName(draftInfoResponse.getDraftName())
                        .setDraftSignStatus(draftInfoResponse.getDraftSignStatus())
                        .setTotalPrice(new BigDecimal(draftInfoResponse.getDraftContent().getTotalPrice()))
                        .setDraftId(draftInfoResponse.getDraftId());
                draftSimpleInfoList.add(draftSimpleInfoListBean);
            });
        }
        compareDraftResponse.setDraftSimpleInfoList(draftSimpleInfoList);
    }

    /**
     * 组装基础信息
     *
     * @param solutionSelected
     * @param draftInfoResponseList
     * @param compareDraftResponse
     */
    private void setSolutionBaseInfoList(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, CompareDraftResponse compareDraftResponse, DraftInfoResponse signDraftInfo) {
        List<CompareDraftResponse.BaseInfoListBean> baseInfoList = new ArrayList<>();
        for (int i = 0; i < SolutionCompareEnum.values().length; i++) {
            CompareDraftResponse.BaseInfoListBean basePriceBean = new CompareDraftResponse.BaseInfoListBean();
            List<CompareDraftResponse.BaseInfoListBean.RowListBean> rowList = new ArrayList<>();
            //已签约方案订单快照基础信息
            if (solutionSelected != null && signDraftInfo != null) {
                CompareDraftResponse.BaseInfoListBean.RowListBean rowListBean = new CompareDraftResponse.BaseInfoListBean.RowListBean();
                List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = new ArrayList<>();
                if (i == 0) {
                    CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean basePriceRowInnerBean = new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean();
                    if (solutionSelected.getBetaChangeFlag() == 1) {//beta端发生订单变动
                        basePriceRowInnerBean.setRowInnerName(betaChangeMessage);
                    } else {
                        basePriceRowInnerBean.setRowInnerName(RegexUtil.parseNumber(new BigDecimal(signDraftInfo.getDraftContent().getSolutionSelected().getSolutionPrice())) + "元");
                    }
                    rowInnerList.add(basePriceRowInnerBean);
                }
                if (i == 1) {
                    if (solutionSelected.getBetaChangeFlag() == 1) {//beta端发生订单变动
                        CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean roomSelectRowInnerBean = new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean();
                        roomSelectRowInnerBean.setRowInnerName(betaChangeMessage);
                        rowInnerList.add(roomSelectRowInnerBean);
                    } else {
                        rowInnerList = getRoomSelectInfo(signDraftInfo, solutionSelected.getBetaChangeFlag());
                    }

                }
                if (i == 2) {
                    rowInnerList = getSolutionSelectInfo(signDraftInfo);
                }
                if (i == 3) {
                    rowInnerList = getSpaceUseInfo(signDraftInfo);
                    rowListBean.setDesignUrlFlag(showDesignUrlFlag);
                }

                rowListBean.setRowInnerList(rowInnerList);
                rowList.add(rowListBean);

            }
            //草稿基础信息
            if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
                for (DraftInfoResponse draftInfoResponse : draftInfoResponseList) {
                    CompareDraftResponse.BaseInfoListBean.RowListBean rowListBean = new CompareDraftResponse.BaseInfoListBean.RowListBean();
                    List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = new ArrayList<>();
                    if (i == 0) {
                        CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean basePriceRowInnerBean = new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean();
                        basePriceRowInnerBean.setRowInnerName(RegexUtil.parseNumber(new BigDecimal(draftInfoResponse.getDraftContent().getSolutionSelected().getSolutionPrice())) + "元");
                        rowInnerList.add(basePriceRowInnerBean);
                    }
                    if (i == 1) {
                        rowInnerList = getRoomSelectInfo(draftInfoResponse, null);
                    }
                    if (i == 2) {
                        rowInnerList = getSolutionSelectInfo(draftInfoResponse);
                    }
                    if (i == 3) {
                        rowInnerList = getSpaceUseInfo(draftInfoResponse);
                        rowListBean.setDesignUrlFlag(showDesignUrlFlag);
                    }

                    rowListBean.setRowInnerList(rowInnerList);
                    rowList.add(rowListBean);

                }
            }
            basePriceBean.setColumnName(SolutionCompareEnum.getDescription(i)).setRowList(rowList).setRowHighLight(compareRowListInfo(rowList));
            baseInfoList.add(basePriceBean);
        }
        compareDraftResponse.setBaseInfoList(baseInfoList);
    }

    /**
     * 基础信息比较行内数据是否需要高亮显示 0 不高亮 1高亮
     *
     * @return
     */
    private Integer compareRowListInfo(List<CompareDraftResponse.BaseInfoListBean.RowListBean> rowList) {
        if (CollectionUtils.isNotEmpty(rowList) && rowList.size() >= 2) {
            for (int i = 0; i < rowList.size() - 1; i++) {
                List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = rowList.get(i).getRowInnerList();
                List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerSecList = rowList.get(i + 1).getRowInnerList();
                if (rowInnerList.size() != rowInnerSecList.size()) {
                    return rowHighLight;
                }
                for (int j = 0; j < rowInnerList.size(); j++) {
                    CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean rowInnerListBean = rowInnerList.get(j);
                    CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean rowInnerSecListBean = rowInnerSecList.get(j);
                    if ((!rowInnerListBean.getRowInnerName().equals(rowInnerSecListBean.getRowInnerName()))
                            || ((rowInnerListBean.getReplaceAmountTotal() == null && rowInnerSecListBean.getReplaceAmountTotal() != null)
                            || (rowInnerListBean.getReplaceAmountTotal() != null && rowInnerSecListBean.getReplaceAmountTotal() == null)
                            || (rowInnerListBean.getReplaceAmountTotal() != null && rowInnerSecListBean.getReplaceAmountTotal() != null && !rowInnerListBean.getReplaceAmountTotal().equals(rowInnerSecListBean.getReplaceAmountTotal())))

                            || ((rowInnerListBean.getReplaceCount() == null && rowInnerSecListBean.getReplaceCount() != null)
                            || (rowInnerListBean.getReplaceCount() != null && rowInnerSecListBean.getReplaceCount() == null)
                            || (rowInnerListBean.getReplaceCount() != null && rowInnerSecListBean.getReplaceCount() != null && !rowInnerListBean.getReplaceCount().equals(rowInnerSecListBean.getReplaceCount())))) {
                        return rowHighLight;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 组装空间详细信息
     *
     * @param solutionSelected
     * @param draftInfoResponseList
     * @param compareDraftResponse
     */
    private void setSpaceInfoList(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, CompareDraftResponse compareDraftResponse, DraftInfoResponse signDraftInfo, List<DraftCollectVo> spaceColletBean) {
        List<CompareDraftResponse.SpaceInfoListBean> spaceInfoList = new ArrayList<>();
        int draftCount = 0;//草稿总数量
        int currentDraft = 0;//当前是第几分草稿
        if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
            draftCount = draftCount + draftInfoResponseList.size();
        }
        if (solutionSelected != null) {
            draftCount++;
            currentDraft++;
        }

        for (DraftCollectVo expSpaceDesignSelectedBean : spaceColletBean) {
            CompareDraftResponse.SpaceInfoListBean spaceInfoListBean = new CompareDraftResponse.SpaceInfoListBean();

            List<CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean> spaceSelected = new ArrayList<>();
            List<CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean> spaceImageList = new ArrayList<>();
            List<CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean> spaceUsageList = new ArrayList<>();
            List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean> hardItemList = new ArrayList<>();
            List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList = new ArrayList<>();
            Map<String, List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean>> softMap = new HashMap<>();//软装列表map集合
            Map<String, List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean>> hardMap = new HashMap<>();//硬装列表map集合

            spaceInfoListBean
                    .setSpaceUsageId(expSpaceDesignSelectedBean.getSpaceUsageId())
                    .setSpaceUsageName(expSpaceDesignSelectedBean.getSpaceUsageName());

            if (solutionSelected != null) {//全品家订单快照数据
                int spaceMatchCount = 0;//空间匹配计数
                for (SpaceDesign spaceDesign : solutionSelected.getSpaceDesignSelected()) {
                    if (spaceDesign.getSpaceUsageId().equals(expSpaceDesignSelectedBean.getSpaceUsageId())) {
                        spaceMatchCount++;
                        CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedBean = new CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean();
                        CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean spaceImageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean();
                        CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean();

                        if (expSpaceDesignSelectedBean.getSpaceUsageId() != RoomUseEnum.ROOM_WHOLE.getCode()) {//全屋
                            signDraftInfo.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                                if (spaceDesignSelectedBean.getSelected() != null && spaceDesignSelectedBean.getSpaceUsageId().equals(expSpaceDesignSelectedBean.getSpaceUsageId()) && !spaceDesignSelectedBean.getSelected().getSolutionId().equals(spaceDesignSelectedBean.getDefaultSpace().getSolutionId())) {
                                    spaceSelectedBean.setSolutionName("替换为:" + spaceDesignSelectedBean.getSelected().getSolutionName() + "-" + spaceDesignSelectedBean.getSelected().getSpaceStyle())
                                            .setSpaceStyle(spaceDesignSelectedBean.getSelected().getSpaceStyle())
                                            .setSpaceDesignPriceDiff(spaceDesignSelectedBean.getSelected().getSpaceDesignPrice() - spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignPrice());
                                }
                            });
                            spaceSelected.add(spaceSelectedBean);
                            //组装空间效果图
                            spaceImageListBean.setSpaceImage(getSpaceOrderHeadImage(spaceDesign));
                            spaceImageList.add(spaceImageListBean);
                            //组装空间用途信息
                            spaceUsageListBean.setSpaceUsageName(spaceDesign.getSpaceUsageName());
                            spaceUsageList.add(spaceUsageListBean);
                        }

                        //组装软装数据
                        if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getSoftCollectList())) {
                            setOrderSoftResponseList(softResponseList, expSpaceDesignSelectedBean, softMap, spaceDesign);
                        }

                        //组装硬装数据
                        if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getHardItemCollectList())) {//规避纯软装
                            setOrderHardItemList(hardItemList, expSpaceDesignSelectedBean, hardMap, spaceDesign);
                        }
                    }
                }
                if (spaceMatchCount == 0) {//缺省空间数据补全
                    CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedBean = new CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean();
                    CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean spaceImageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean();
                    CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean();

                    if (expSpaceDesignSelectedBean.getSpaceUsageId() != RoomUseEnum.ROOM_WHOLE.getCode()) {//全屋
                        spaceSelected.add(spaceSelectedBean);
                        //组装空间效果图
                        spaceImageListBean.setSpaceImage(null);
                        spaceImageList.add(spaceImageListBean);
                        //组装空间用途信息
                        spaceUsageListBean.setSpaceUsageName(null);
                        spaceUsageList.add(spaceUsageListBean);
                    }
                    SpaceDesign spaceDesign = new SpaceDesign();
                    spaceDesign.setSpaceUsageId(expSpaceDesignSelectedBean.getSpaceUsageId());
                    //组装软装数据
                    if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getSoftCollectList())) {
                        setOrderSoftResponseList(softResponseList, expSpaceDesignSelectedBean, softMap, spaceDesign);
                    }

                    //组装硬装数据
                    if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getHardItemCollectList())) {//规避纯软装
                        setOrderHardItemList(hardItemList, expSpaceDesignSelectedBean, hardMap, spaceDesign);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {//草稿数据
                for (int i = 0; i < draftInfoResponseList.size(); i++) {
                    int spaceMatchCount = 0;//空间匹配计数
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean currentSpaceDesignSelectedBean : draftInfoResponseList.get(i).getDraftContent().getSpaceDesignSelected()) {
                        if (currentSpaceDesignSelectedBean.getSpaceUsageId().equals(expSpaceDesignSelectedBean.getSpaceUsageId())) {//多份草稿匹配到相同空间
                            spaceMatchCount++;
                            CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedBean = new CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean();
                            CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean spaceImageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean();
                            CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean();

                            if (expSpaceDesignSelectedBean.getSpaceUsageId() != RoomUseEnum.ROOM_WHOLE.getCode()) {//全屋
                                if (currentSpaceDesignSelectedBean.getSelected() != null && !currentSpaceDesignSelectedBean.getSelected().getSolutionId().equals(currentSpaceDesignSelectedBean.getDefaultSpace().getSolutionId())) {
                                    spaceSelectedBean.setSolutionName("替换为:" + currentSpaceDesignSelectedBean.getSelected().getSolutionName() + "-" + currentSpaceDesignSelectedBean.getSelected().getSpaceStyle())
                                            .setSpaceStyle(currentSpaceDesignSelectedBean.getSelected().getSpaceStyle())
                                            .setSpaceDesignPriceDiff(currentSpaceDesignSelectedBean.getSelected().getSpaceDesignPrice() - currentSpaceDesignSelectedBean.getDefaultSpace().getSpaceDesignPrice());
                                }
                                spaceSelected.add(spaceSelectedBean);
                                //组装空间效果图
                                spaceImageListBean.setSpaceImage(currentSpaceDesignSelectedBean.getSelected() == null ? null : getSpaceHeadImage(currentSpaceDesignSelectedBean.getSelected()));
                                spaceImageList.add(spaceImageListBean);
                                //组装空间用途信息
                                spaceUsageListBean.setSpaceUsageName(currentSpaceDesignSelectedBean.getSelected() == null ? null : currentSpaceDesignSelectedBean.getSelected().getSpaceUsageName());
                                spaceUsageList.add(spaceUsageListBean);
                            }
                            //组装软装数据
                            if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getSoftCollectList())) {
                                setDraftSoftResponseList(softResponseList, expSpaceDesignSelectedBean, softMap, currentSpaceDesignSelectedBean, draftCount, currentDraft + i + 1);
                            }
                            //组装硬装数据
                            if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getHardItemCollectList())) {//规避纯软装
                                setDraftHardItemList(hardItemList, expSpaceDesignSelectedBean, hardMap, currentSpaceDesignSelectedBean, draftCount, currentDraft + i + 1);
                            }
                        }
                    }
                    if (spaceMatchCount == 0) {//缺省空间数据补全
                        spaceMatchCount++;
                        CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedBean = new CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean();
                        CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean spaceImageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean();
                        CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListBean = new CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean();

                        if (expSpaceDesignSelectedBean.getSpaceUsageId() != RoomUseEnum.ROOM_WHOLE.getCode()) {//全屋
                            spaceSelected.add(spaceSelectedBean);
                            //组装空间效果图
                            spaceImageListBean.setSpaceImage(null);
                            spaceImageList.add(spaceImageListBean);
                            //组装空间用途信息
                            spaceUsageListBean.setSpaceUsageName(null);
                            spaceUsageList.add(spaceUsageListBean);
                        }
                        DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean currentSpaceDesignSelectedBean = new DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean();
                        currentSpaceDesignSelectedBean.setSpaceUsageId(expSpaceDesignSelectedBean.getSpaceUsageId());
                        //组装软装数据
                        if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getSoftCollectList())) {
                            setDraftSoftResponseList(softResponseList, expSpaceDesignSelectedBean, softMap, currentSpaceDesignSelectedBean, draftCount, currentDraft + i + 1);
                        }
                        //组装硬装数据
                        if (CollectionUtils.isNotEmpty(expSpaceDesignSelectedBean.getHardItemCollectList())) {//规避纯软装
                            setDraftHardItemList(hardItemList, expSpaceDesignSelectedBean, hardMap, currentSpaceDesignSelectedBean, draftCount, currentDraft + i + 1);
                        }
                    }
                }
            }

            softResponseList.sort(Comparator.comparingInt(CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean::getFurnitureType));
            compareSoftHardInfo(softResponseList, hardItemList);
            spaceInfoListBean.setSoftResponseList(softResponseList);
            spaceInfoListBean.setHardItemList(hardItemList);
            if (compareSpaceSelectedtInfo(spaceSelected)) {//处理是否高亮显示,不需要高亮则整行不显示
                spaceInfoListBean.setSpaceSelected(spaceSelected);
            }
            spaceInfoListBean.setSpaceImageList(spaceImageList);
            if (compareSpaceUsageList(spaceUsageList)) {//处理是否高亮显示,不需要高亮则整行不显示
                spaceInfoListBean.setSpaceUsageList(spaceUsageList);
            }
            spaceInfoList.add(spaceInfoListBean);
        }
        //空间排序
        spaceInfoList.sort(
                Comparator.comparingInt(o -> ROOM_TYPE_ORDER_LIST.indexOf(o.getSpaceUsageId())));
        compareDraftResponse.setSpaceInfoList(spaceInfoList);
    }


    /**
     * 根据订单快照获取硬装列表
     *
     * @param hardItemList
     * @param expSpaceDesignSelectedBean
     * @param hardMap
     * @param spaceDesign
     */
    private void setOrderHardItemList(List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean> hardItemList,
                                      DraftCollectVo expSpaceDesignSelectedBean,
                                      Map<String, List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean>> hardMap,
                                      SpaceDesign spaceDesign) {
        expSpaceDesignSelectedBean.getHardItemCollectList().forEach(hardItemId -> {
            CompareDraftResponse.SpaceInfoListBean.HardItemListBean hardItemListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean();

            List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList =
                    hardMap.get(spaceDesign.getSpaceUsageId() + ":" + hardItemId);
            if (CollectionUtils.isEmpty(hardItemClassGroupList)) {
                hardItemClassGroupList = initHardItemClassGroupList();
                hardMap.put(spaceDesign.getSpaceUsageId() + ":" + hardItemId, hardItemClassGroupList);
            }

            int matchCount = 0;
            if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                for (HardItem hardItem : spaceDesign.getHardItemList()) {
                    if (hardItemId.equals(hardItem.getHardItemId()) || (CollectionUtils.isNotEmpty(hardItem.getGuiBomGroupList()) && hardItem.getGuiBomGroupList().get(0).getSecondCategoryId().equals(hardItemId))) {//expHardItemListBean中不能有同空间下同类目
                        matchCount++;
                        List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean> hardItemClassList;
                        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean;

                        if (hardItemClassGroupList.size() >= matchCount) {
                            hardItemClassList = hardItemClassGroupList.get(matchCount - 1).getHardItemClassList();
                            hardItemClassGroupListBean = hardItemClassGroupList.get(matchCount - 1);
                        } else {
                            hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
                            hardItemClassList = new ArrayList<>();
                            hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
                            hardItemClassGroupList.add(hardItemClassGroupListBean);
                        }

                        //设置硬装类目具体信息
                        hardItemListBean.setHardItemName(CollectionUtils.isNotEmpty(hardItem.getGuiBomGroupList()) ? "定制家具" : hardItem.getHardItemName());
                        if (hardItem.getCabinetBomGroup() != null) {
                            hardItemListBean.setHardItemId(hardItem.getCabinetBomGroup().getSecondCategoryId());
                        } else {
                            hardItemListBean.setHardItemId(hardItem.getHardItemId());
                        }
                        //硬装基础项（兼容同空间同类目问题）
                        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassListBean =
                                new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean();
                        if (CollectionUtils.isNotEmpty(hardItem.getGuiBomGroupList())) {
                            HardBomGroup hardBomGroup = hardItem.getGuiBomGroupList().get(0);
                            hardItemClassListBean.setGuiBomList(hardItem.getGuiBomGroupList().stream().map(mapper -> {
                                return new QueryCabinetPropertyListRequest.GroupQueryRequest().setCabinetType(mapper.getCabinetType())
                                        .setCabinetTypeName(mapper.getCabinetTypeName()).setGroupId(mapper.getGroupId()).setDefaultGroupId(mapper.getGroupId());
                            }).collect(Collectors.toList()))
                                    .setHardSelectionName(hardBomGroup.getSecondCategoryName())
                                    .setBomFlag(102);
                        } else {
                            hardItemClassListBean.setHardSelectionId(hardItem.getHardItemSelected().getHardSelectionId())
                                    .setHardSelectionName(hardItem.getHardItemSelected().getHardSelectionName())
                                    .setBomFlag(hardItem.getBomFlag());
                        }
                        hardItemClassList.add(hardItemClassListBean);

                        hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
                        if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getProcessSelected() != null && hardItem.getHardItemSelected().getProcessSelected().getHardItemSelection() != null &&
                                CollectionUtils.isNotEmpty(hardItem.getHardItemSelected().getProcessSelected().getHardItemSelection())) {
                            for (HardItemSelection hardItemSelection : hardItem.getHardItemSelected().getProcessSelected().getHardItemSelection()) {
                                //全屋子集
                                matchCount = setOrderSonHardItemSelection(matchCount, hardItemClassGroupList, hardItemSelection, hardItem.getBomFlag());
                            }

                        }
                    }
                }
            }

            if ((matchCount == 0 || hardItemClassGroupList.size() > matchCount) && CollectionUtils.isNotEmpty(hardItemClassGroupList)) {//列表项补全
                //获取硬装group组，依次补全
                hardItemClassGroupList.forEach(hardItemClassGroupListBean -> hardItemClassGroupListBean.getHardItemClassList().add(new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean()));
            }
            hardItemListBean.setHardItemClassGroupList(hardItemClassGroupList);
            if (hardItemListBean.getHardItemName() != null && !hardItemList.contains(hardItemListBean)) {
                hardItemList.add(hardItemListBean);
            }

        });
    }

    private int setOrderSonHardItemSelection(int matchCount, List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList,
                                             HardItemSelection hardItemSelection, Integer bomFlag) {
        matchCount++;
        List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean> hardItemClassList;
        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean;

        if (hardItemClassGroupList.size() >= matchCount) {
            hardItemClassList = hardItemClassGroupList.get(matchCount - 1).getHardItemClassList();
            hardItemClassGroupListBean = hardItemClassGroupList.get(matchCount - 1);
        } else {
            hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
            hardItemClassList = new ArrayList<>();
            hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
            hardItemClassGroupList.add(hardItemClassGroupListBean);
        }

        //硬装基础项（兼容同空间同类目问题）
        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassListBean =
                new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean();
        hardItemClassListBean.setHardSelectionId(hardItemSelection.getHardSelectionId())
                .setHardSelectionName(hardItemSelection.getHardSelectionName())
                .setBomFlag(bomFlag);

        hardItemClassList.add(hardItemClassListBean);

        hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
        return matchCount;
    }

    /**
     * 根据订单快照获取软装列表
     *
     * @param softResponseList
     * @param expSpaceDesignSelectedBean
     * @param softMap
     * @param spaceDesign
     */
    private void setOrderSoftResponseList(List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList,
                                          DraftCollectVo expSpaceDesignSelectedBean,
                                          Map<String, List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean>> softMap,
                                          SpaceDesign spaceDesign) {
        expSpaceDesignSelectedBean.getSoftCollectList().forEach(lastCategoryId -> {
            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean softResponseListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean();

            List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean> softResponseClassGroupList =
                    softMap.get(spaceDesign.getSpaceUsageId() + ":" + lastCategoryId);
            if (CollectionUtils.isEmpty(softResponseClassGroupList)) {
                softResponseClassGroupList = new ArrayList<>();
                CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean softResponseClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean();
                softResponseClassGroupListBean.setSoftResponseClassList(new ArrayList<>());
                softResponseClassGroupList.add(softResponseClassGroupListBean);
                softMap.put(spaceDesign.getSpaceUsageId() + ":" + lastCategoryId, softResponseClassGroupList);
            }

            int matchCount = 0;
            if (CollectionUtils.isNotEmpty(spaceDesign.getOptionalSoftResponseList())) {
                spaceDesign.getOptionalSoftResponseList().sort((o1, o2) -> {
                    if (o1.getFurnitureSelected() != null && o2.getFurnitureSelected() != null && o1.getFurnitureSelected().getSkuId() != null && o2.getFurnitureSelected().getSkuId() != null) {
                        return o1.getFurnitureSelected().getSkuId() - o2.getFurnitureSelected().getSkuId();
                    }
                    return 0;
                });

                for (OptionalSoftResponse optionalSoftResponse : spaceDesign.getOptionalSoftResponseList()) {
                    if (lastCategoryId.equals(optionalSoftResponse.getLastCategoryId()) || (CollectionUtils.isNotEmpty(optionalSoftResponse.getGuiBomGroupList()) && optionalSoftResponse.getGuiBomGroupList().get(0) != null && optionalSoftResponse.getGuiBomGroupList().get(0).getGroupType().equals(10) && optionalSoftResponse.getGuiBomGroupList().get(0).getSecondCategoryId().equals(lastCategoryId)) || (optionalSoftResponse.getRootCategoryId() != null && lastCategoryId.equals(optionalSoftResponse.getRootCategoryId()) && optionalSoftResponse.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID))) {
                        matchCount++;
                        List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean> softResponseClassListBeanList;
                        CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean softResponseClassGroupListBean;
                        if (softResponseClassGroupList.size() >= matchCount) {
                            softResponseClassListBeanList = softResponseClassGroupList.get(matchCount - 1).getSoftResponseClassList();
                            softResponseClassGroupListBean = softResponseClassGroupList.get(matchCount - 1);
                        } else {
                            softResponseClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean();
                            softResponseClassListBeanList = new ArrayList<>();
                            softResponseClassGroupListBean.setSoftResponseClassList(softResponseClassListBeanList);
                            softResponseClassGroupList.add(softResponseClassGroupListBean);
                        }


                        //设置软装具体信息
                        if (optionalSoftResponse.getRootCategoryId() != null && optionalSoftResponse.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                            softResponseListBean.setLastCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                            softResponseListBean.setLastCategoryId(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                        } else if (CollectionUtils.isNotEmpty(optionalSoftResponse.getGuiBomGroupList())) {
                            softResponseListBean.setLastCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                            softResponseListBean.setLastCategoryId(optionalSoftResponse.getGuiBomGroupList().get(0).getSecondCategoryId());
                        } else {
                            softResponseListBean.setLastCategoryName(optionalSoftResponse.getLastCategoryName());
                            softResponseListBean.setLastCategoryId(optionalSoftResponse.getLastCategoryId());
                        }

                        if (CollectionUtils.isEmpty(optionalSoftResponse.getGuiBomGroupList())) {
                            softResponseListBean.setFurnitureType(optionalSoftResponse.getFurnitureSelected().getFurnitureType());
                            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean softResponseClassListBean
                                    = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean();
                            softResponseClassListBean.setSkuId(optionalSoftResponse.getFurnitureSelected().getSkuId())
                                    .setFurnitureName(optionalSoftResponse.getFurnitureSelected().getFurnitureName())
                                    .setFurnitureType(optionalSoftResponse.getFurnitureSelected().getFurnitureType())
                                    .setBrand(optionalSoftResponse.getFurnitureSelected().getBrand())
                                    .setMaterial(optionalSoftResponse.getFurnitureSelected().getMaterial())
                                    .setColor(optionalSoftResponse.getFurnitureSelected().getColor())
                                    .setBomFlag(optionalSoftResponse.getFurnitureSelected().getBomFlag());
                            softResponseClassListBeanList.add(softResponseClassListBean);
                            softResponseClassGroupListBean.setSoftResponseClassList(softResponseClassListBeanList);
                        } else {
                            softResponseListBean.setFurnitureType(optionalSoftResponse.getGuiBomGroupList().get(0).getFurnitureType());
                            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean softResponseClassListBean
                                    = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean();
                            softResponseClassListBean
                                    .setFurnitureName(optionalSoftResponse.getGuiBomGroupList().get(0).getSecondCategoryName())
                                    .setFurnitureType(optionalSoftResponse.getGuiBomGroupList().get(0).getFurnitureType())
                                    .setBomFlag(102);
                            softResponseClassListBeanList.add(softResponseClassListBean);
                            softResponseClassGroupListBean.setSoftResponseClassList(softResponseClassListBeanList);
                        }
                    }
                }
            }
            if ((matchCount == 0 || softResponseClassGroupList.size() > matchCount) && CollectionUtils.isNotEmpty(softResponseClassGroupList)) {//列表项补全
                //获取硬装group组，依次补全
                softResponseClassGroupList.forEach(hardItemClassGroupListBean -> hardItemClassGroupListBean.getSoftResponseClassList().add(new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean()));
            }
            softResponseListBean.setSoftResponseClassGroupList(softResponseClassGroupList);
            if (softResponseListBean.getLastCategoryName() != null && !softResponseList.contains(softResponseListBean)) {
                softResponseList.add(softResponseListBean);
            }
        });
    }


    /**
     * 根据草稿获取硬装列表
     *
     * @param hardItemList
     * @param expSpaceDesignSelectedBean
     * @param hardMap
     * @param currentSpaceDesignSelectedBean
     * @return
     */
    private void setDraftHardItemList(List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean> hardItemList,
                                      DraftCollectVo expSpaceDesignSelectedBean,
                                      Map<String, List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean>> hardMap,
                                      DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean currentSpaceDesignSelectedBean,
                                      int draftCount, int currentDraft) {
        expSpaceDesignSelectedBean.getHardItemCollectList().forEach(hardItemId -> {
            CompareDraftResponse.SpaceInfoListBean.HardItemListBean hardItemListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean();

            List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList =
                    hardMap.get(currentSpaceDesignSelectedBean.getSpaceUsageId() + ":" + hardItemId);
            if (CollectionUtils.isEmpty(hardItemClassGroupList)) {
                hardItemClassGroupList = new ArrayList<>();
                CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
                hardItemClassGroupListBean.setHardItemClassList(new ArrayList<>());
                hardItemClassGroupList.add(hardItemClassGroupListBean);
                hardMap.put(currentSpaceDesignSelectedBean.getSpaceUsageId() + ":" + hardItemId, hardItemClassGroupList);
            }

            int matchCount = 0;
            if (CollectionUtils.isNotEmpty(currentSpaceDesignSelectedBean.getHardItemList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean currentHardItemListBean : currentSpaceDesignSelectedBean.getHardItemList()) {
                    if (hardItemId.equals(currentHardItemListBean.getHardItemId()) || (currentHardItemListBean.getCabinetBomGroup() != null && hardItemId.equals(currentHardItemListBean.getCabinetBomGroup().getSecondCategoryId())) || (currentHardItemListBean.getHardBomGroupSelect() != null &&
                            hardItemId.equals(currentHardItemListBean.getHardBomGroupSelect().getCategoryId()))) {//expHardItemListBean中不能有同空间下同类目
                        matchCount++;
                        List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean> hardItemClassList;
                        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean;

                        if (hardItemClassGroupList.size() >= matchCount) {
                            hardItemClassList = hardItemClassGroupList.get(matchCount - 1).getHardItemClassList();
                            hardItemClassGroupListBean = hardItemClassGroupList.get(matchCount - 1);
                        } else {
                            hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
                            hardItemClassList = new ArrayList<>();
                            for (int i = 0; i < currentDraft - 1; i++) {//同空间、同类目前位hardItemClassList记录补全
                                hardItemClassList.add(new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean());
                            }
                            hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
                            hardItemClassGroupList.add(hardItemClassGroupListBean);
                        }

                        //设置硬装类目具体信息
                        if (hardItemId.equals(currentHardItemListBean.getHardItemId())) {
                            hardItemListBean.setHardItemName(currentHardItemListBean.getHardItemName());
                            hardItemListBean.setHardItemId(currentHardItemListBean.getHardItemId());
                        } else if (currentHardItemListBean.getCabinetBomGroup() != null && hardItemId.equals(currentHardItemListBean.getCabinetBomGroup().getSecondCategoryId())) {
                            hardItemListBean.setHardItemName("定制家具");
                            hardItemListBean.setHardItemId(currentHardItemListBean.getCabinetBomGroup().getSecondCategoryId());
                        } else {
                            hardItemListBean.setHardItemName(currentHardItemListBean.getHardBomGroupSelect().getCategoryName());
                            hardItemListBean.setHardItemId(currentHardItemListBean.getHardBomGroupSelect().getCategoryId());
                        }

                        //硬装基础项（兼容同空间同类目问题）
                        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassListBean =
                                new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean();

                        if (currentHardItemListBean.getStatus() == Constants.ITEM_STATUS_DELETE) {//删除
                            hardItemClassListBean.setHardSelectionId(currentHardItemListBean.getHardItemDefault().getHardSelectionId())
                                    .setHardSelectionName("去除 " + currentHardItemListBean.getHardItemDefault().getHardSelectionName())
                                    .setPriceDiff(BigDecimal.ZERO.subtract(currentHardItemListBean.getHardItemDefault().getProcessSelected().getPrice()).intValue())
                                    .setSkuCompareStatus(currentHardItemListBean.getHardItemDefault().getSkuCompareStatus());

                        } else if (currentHardItemListBean.getStatus() == Constants.ITEM_STATUS_ADD && currentHardItemListBean.getHardItemSelected() != null) {//普通新增
                            hardItemClassListBean.setHardSelectionId(currentHardItemListBean.getHardItemSelected().getHardSelectionId())
                                    .setHardSelectionName(currentHardItemListBean.getHardItemSelected().getHardSelectionName())
                                    .setPriceDiff(currentHardItemListBean.getHardItemSelected().getProcessSelected().getPrice().intValue())
                                    .setSkuCompareStatus(currentHardItemListBean.getHardItemSelected().getSkuCompareStatus());
                        } else if (currentHardItemListBean.getHardBomGroupSelect() != null) {//bom硬装（bom目前无删除项）
                            hardItemClassListBean.setHardSelectionId(currentHardItemListBean.getHardBomGroupSelect().getGroupId())
                                    .setHardSelectionName(currentHardItemListBean.getHardBomGroupSelect().getGroupName())
                                    .setPriceDiff(currentHardItemListBean.getHardBomGroupSelect().getPriceDiff().intValue())
                                    .setSkuCompareStatus(currentHardItemListBean.getHardBomGroupSelect().getCompareStatus());
                        } else if (currentHardItemListBean.getCabinetBomGroup() != null) {
                            hardItemClassListBean.setGuiBomList(currentHardItemListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> {
                                return new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                        .setDefaultGroupId(replaceBomDto.getBomGroupDefault().getGroupId())
                                        .setGroupId(replaceBomDto.getBomGroupSelect().getGroupId())
                                        .setCabinetTypeName(replaceBomDto.getBomGroupSelect().getCabinetTypeName())
                                        .setCabinetType(replaceBomDto.getBomGroupSelect().getCabinetType());
                            }).collect(Collectors.toList()))
                                    .setHardSelectionName(currentHardItemListBean.getCabinetBomGroup().getSecondCategoryName())
                                    .setPriceDiff(currentHardItemListBean.getCabinetBomGroup().getPriceDiff().intValue())
                                    .setSkuCompareStatus(currentHardItemListBean.getCabinetBomGroup().getCompareStatus())
                                    .setBomFlag(102);
                        } else {//普通硬装
                            hardItemClassListBean.setHardSelectionId(currentHardItemListBean.getHardItemSelected().getHardSelectionId())
                                    .setHardSelectionName(currentHardItemListBean.getHardItemSelected().getHardSelectionName())
                                    .setSkuCompareStatus(currentHardItemListBean.getHardItemSelected().getSkuCompareStatus());
                            if(currentHardItemListBean.getHardItemSelected().getProcessSelected().getPriceDiff()!=null){
                                hardItemClassListBean.setPriceDiff(currentHardItemListBean.getHardItemSelected().getProcessSelected().getPriceDiff().intValue());
                            }
                        }
                        hardItemClassList.add(hardItemClassListBean);

                        hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
                        if (currentHardItemListBean.getStatus() == Constants.ITEM_STATUS_ADD && currentHardItemListBean.getHardItemSelected() != null &&
                                currentHardItemListBean.getHardItemSelected().getProcessSelected() != null
                                && currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null
                                && currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId() > 0) {//无子集前端会塞一个负的id
                            matchCount = setSelectChildHardItem(hardItemClassGroupList, currentHardItemListBean, matchCount, currentDraft);
                        }
                    }
                }
            }

            if ((matchCount == 0 || hardItemClassGroupList.size() > matchCount) && CollectionUtils.isNotEmpty(hardItemClassGroupList)) {//多份草稿无该item分类
                //获取硬装group组，依次补全
                hardItemClassGroupList.forEach(hardItemClassGroupListBean -> {
                    if (hardItemClassGroupListBean.getHardItemClassList().size() < draftCount) {//后位hardItemClassList记录补全
                        hardItemClassGroupListBean.getHardItemClassList().add(new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean());

                    }
                });
            }
            hardItemListBean.setHardItemClassGroupList(hardItemClassGroupList);
            if (hardItemListBean.getHardItemName() != null && !hardItemList.contains(hardItemListBean)) {
                hardItemList.add(hardItemListBean);
            }

        });
    }

    /**
     * 设置全屋硬装子集信息
     *
     * @param hardItemClassGroupList
     * @param currentHardItemListBean
     * @param matchCount
     * @param currentDraft
     */
    private int setSelectChildHardItem(List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList,
                                       DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean currentHardItemListBean, int matchCount, int currentDraft) {
        matchCount++;
        List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean> hardItemClassList;
        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean;

        if (hardItemClassGroupList.size() >= matchCount) {
            hardItemClassList = hardItemClassGroupList.get(matchCount - 1).getHardItemClassList();
            hardItemClassGroupListBean = hardItemClassGroupList.get(matchCount - 1);
        } else {
            hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
            hardItemClassList = new ArrayList<>();
            for (int i = 0; i < currentDraft - 1; i++) {//同空间、同类目前位hardItemClassList记录补全
                hardItemClassList.add(new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean());
            }
            hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
            hardItemClassGroupList.add(hardItemClassGroupListBean);
        }
        //硬装基础项（兼容同空间同类目问题）
        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassListBean =
                new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean();
        hardItemClassListBean.setHardSelectionId(currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId())
                .setHardSelectionName(currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionName())
                .setPriceDiff(currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getPrice().intValue())
                .setSkuCompareStatus(currentHardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getSkuCompareStatus());

        hardItemClassList.add(hardItemClassListBean);

        hardItemClassGroupListBean.setHardItemClassList(hardItemClassList);
        return matchCount;
    }

    /**
     * 初始化一个hardItemClassGroup
     *
     * @return
     */
    private List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> initHardItemClassGroupList() {
        List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList = new ArrayList<>();
        CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean hardItemClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean();
        hardItemClassGroupListBean.setHardItemClassList(new ArrayList<>());
        hardItemClassGroupList.add(hardItemClassGroupListBean);
        return hardItemClassGroupList;
    }


    /**
     * 初始化一个softResponseClassGroupList
     *
     * @return
     */
    private List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean> initSoftResponseClassGroupList() {
        List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean> softResponseClassGroupList = new ArrayList<>();
        CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean softResponseClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean();
        softResponseClassGroupListBean.setSoftResponseClassList(new ArrayList<>());
        softResponseClassGroupList.add(softResponseClassGroupListBean);
        return softResponseClassGroupList;
    }


    /**
     * 根据草稿获取软装列表
     *
     * @param softResponseList
     * @param expSpaceDesignSelectedBean
     * @param softMap
     * @param currentSpaceDesignSelectedBean
     * @return
     */
    private void setDraftSoftResponseList(List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList,
                                          DraftCollectVo expSpaceDesignSelectedBean,
                                          Map<String, List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean>> softMap,
                                          DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean currentSpaceDesignSelectedBean,
                                          int draftCount, int currentDraft) {
        expSpaceDesignSelectedBean.getSoftCollectList().forEach(lastCategoryId -> {
            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean softResponseListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean();

            List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean> softResponseClassGroupList =
                    softMap.get(currentSpaceDesignSelectedBean.getSpaceUsageId() + ":" + lastCategoryId);
            if (CollectionUtils.isEmpty(softResponseClassGroupList)) {
                softResponseClassGroupList = initSoftResponseClassGroupList();
                softMap.put(currentSpaceDesignSelectedBean.getSpaceUsageId() + ":" + lastCategoryId, softResponseClassGroupList);
            }

            int matchCount = 0;//类目相同数量统计
            if (CollectionUtils.isNotEmpty(currentSpaceDesignSelectedBean.getSoftResponseList())) {

                currentSpaceDesignSelectedBean.getSoftResponseList().sort((o1, o2) -> {
                    if (o1.getFurnitureSelected() != null && o2.getFurnitureSelected() != null && o1.getFurnitureSelected().getSkuId() != null && o2.getFurnitureSelected().getSkuId() != null) {
                        return o1.getFurnitureSelected().getSkuId() - o2.getFurnitureSelected().getSkuId();
                    } else if (o1.getBomGroupSelected() != null && o2.getBomGroupSelected() != null && o1.getBomGroupSelected().getGroupId() != null && o2.getBomGroupSelected().getGroupId() != null) {
                        return o1.getBomGroupSelected().getGroupId() - o2.getBomGroupSelected().getGroupId();
                    }
                    return 0;
                });


                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean currentSoftResponseListBean : currentSpaceDesignSelectedBean.getSoftResponseList()) {
                    if (lastCategoryId.equals(currentSoftResponseListBean.getLastCategoryId()) || (currentSoftResponseListBean.getCabinetBomGroup() != null && lastCategoryId.equals(currentSoftResponseListBean.getCabinetBomGroup().getSecondCategoryId())) || (currentSoftResponseListBean.getRootCategoryId() != null && lastCategoryId.equals(currentSoftResponseListBean.getRootCategoryId()) && currentSoftResponseListBean.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID))) {
                        matchCount++;
                        List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean> softResponseClassList;
                        CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean softResponseClassGroupListBean;


                        if (softResponseClassGroupList.size() >= matchCount) {
                            softResponseClassList = softResponseClassGroupList.get(matchCount - 1).getSoftResponseClassList();
                            softResponseClassGroupListBean = softResponseClassGroupList.get(matchCount - 1);
                        } else {
                            softResponseClassGroupListBean = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean();
                            softResponseClassList = new ArrayList<>();
                            for (int i = 0; i < currentDraft - 1; i++) {//同空间、同类目前位hardItemClassList记录补全
                                softResponseClassList.add(new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean());
                            }
                            softResponseClassGroupListBean.setSoftResponseClassList(softResponseClassList);
                            softResponseClassGroupList.add(softResponseClassGroupListBean);
                        }


                        //设置软装类目具体信息
                        if ((currentSoftResponseListBean.getRootCategoryId() != null && currentSoftResponseListBean.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID))) {
                            softResponseListBean.setLastCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                            softResponseListBean.setLastCategoryId(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                        } else if (currentSoftResponseListBean.getCabinetBomGroup() != null) {
                            softResponseListBean.setLastCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                            softResponseListBean.setLastCategoryId(currentSoftResponseListBean.getCabinetBomGroup().getSecondCategoryId());
                        } else {
                            softResponseListBean.setLastCategoryName(currentSoftResponseListBean.getLastCategoryName());
                            softResponseListBean.setLastCategoryId(currentSoftResponseListBean.getLastCategoryId());
                        }

                        //软装基础项（兼容同空间同类目问题）
                        CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean softResponseClassListBean
                                = new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean();
                        if (currentSoftResponseListBean.getBomGroupSelected() != null) {//bom软装
                            softResponseClassListBean.setSkuId(currentSoftResponseListBean.getBomGroupSelected().getGroupId())
                                    .setFurnitureName(currentSoftResponseListBean.getBomGroupSelected().getGroupDesc())
                                    .setFurnitureType(currentSoftResponseListBean.getBomGroupSelected().getFurnitureType())
                                    .setSkuCompareStatus(currentSoftResponseListBean.getBomGroupSelected().getSkuCompareStatus())
                                    .setPriceDiff(currentSoftResponseListBean.getBomGroupSelected().getPriceDiff()==null?0:currentSoftResponseListBean.getBomGroupSelected().getPriceDiff().intValue())
                                    .setBomFlag(1);
                            softResponseListBean.setFurnitureType(currentSoftResponseListBean.getBomGroupSelected().getFurnitureType());
                        } else if (currentSoftResponseListBean.getCabinetBomGroup() != null) {
                            softResponseClassListBean.setGuiBomList(currentSoftResponseListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> {
                                return new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                        .setGroupId(replaceBomDto.getBomGroupSelect().getGroupId())
                                        .setCabinetTypeName(replaceBomDto.getBomGroupSelect().getCabinetTypeName())
                                        .setCabinetType(replaceBomDto.getBomGroupDefault().getCabinetType())
                                        .setDefaultGroupId(replaceBomDto.getBomGroupDefault().getGroupId());
                            }).collect(Collectors.toList()))
                                    .setFurnitureName(currentSoftResponseListBean.getCabinetBomGroup().getSecondCategoryName())
                                    .setFurnitureType(currentSoftResponseListBean.getCabinetBomGroup().getFurnitureType())
                                    .setPriceDiff(currentSoftResponseListBean.getCabinetBomGroup().getPriceDiff()==null?0:currentSoftResponseListBean.getCabinetBomGroup().getPriceDiff().intValue())
                                    .setBomFlag(102);
                            softResponseListBean.setFurnitureType(currentSoftResponseListBean.getCabinetBomGroup().getFurnitureType());
                        } else {//普通软装
                            softResponseClassListBean.setSkuId(currentSoftResponseListBean.getFurnitureSelected().getSkuId())
                                    .setSkuCompareStatus(currentSoftResponseListBean.getFurnitureSelected().getSkuCompareStatus())
                                    .setFurnitureName(currentSoftResponseListBean.getFurnitureSelected().getFurnitureName())
                                    .setBrand(currentSoftResponseListBean.getFurnitureSelected().getBrand())
                                    .setMaterial(currentSoftResponseListBean.getFurnitureSelected().getMaterial())
                                    .setColor(currentSoftResponseListBean.getFurnitureSelected().getColor())
                                    .setFurnitureType(currentSoftResponseListBean.getFurnitureSelected().getFurnitureType())
                                    .setPriceDiff(currentSoftResponseListBean.getFurnitureSelected().getPriceDiff()==null?0:currentSoftResponseListBean.getFurnitureSelected().getPriceDiff().intValue());
                            softResponseListBean.setFurnitureType(currentSoftResponseListBean.getFurnitureSelected().getFurnitureType());

                        }
                        softResponseClassList.add(softResponseClassListBean);

                        softResponseClassGroupListBean.setSoftResponseClassList(softResponseClassList);

                    }
                }
            }
            if ((matchCount == 0 || softResponseClassGroupList.size() > matchCount) && CollectionUtils.isNotEmpty(softResponseClassGroupList)) {//列表项补全
                //获取软装group组，依次补全
                softResponseClassGroupList.forEach(softResponseClassGroupListBean -> {
                    if (softResponseClassGroupListBean.getSoftResponseClassList().size() < draftCount) {//后位hardItemClassList记录补全
                        softResponseClassGroupListBean.getSoftResponseClassList().add(new CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean());
                    }
                });
            }
            softResponseListBean.setSoftResponseClassGroupList(softResponseClassGroupList);
            if (softResponseListBean.getLastCategoryName() != null && !compareSoftConstains(softResponseList, softResponseListBean)) {
                softResponseList.add(softResponseListBean);
            }
        });
    }

    /**
     * 比较软装是否包含
     *
     * @param softResponseList
     * @param softResponseListBean
     * @return
     */
    private boolean compareSoftConstains(List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList,
                                         CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean softResponseListBean) {
        if (CollectionUtils.isNotEmpty(softResponseList)) {
            for (CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean softResponseBean : softResponseList) {
                if (softResponseBean.getLastCategoryId() != null && softResponseListBean != null &&
                        softResponseListBean.getLastCategoryId() != null &&
                        softResponseBean.getLastCategoryId().equals(softResponseListBean.getLastCategoryId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 软硬装选配是否高亮显示
     *
     * @param softResponseList
     * @param hardItemList
     */
    private void compareSoftHardInfo(List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList, List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean> hardItemList) {
        try {
            if (CollectionUtils.isNotEmpty(softResponseList)) {
                softResponseList.forEach(softResponseListBean -> softResponseListBean.getSoftResponseClassGroupList().forEach(softResponseClassGroupListBean -> {
                    if (CollectionUtils.isNotEmpty(softResponseClassGroupListBean.getSoftResponseClassList()) && softResponseClassGroupListBean.getSoftResponseClassList().size() >= 2) {
                        for (int i = 0; i < softResponseClassGroupListBean.getSoftResponseClassList().size() - 1; i++) {
                            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean softResponseClassListBean = softResponseClassGroupListBean.getSoftResponseClassList().get(i);
                            CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean softResponseClassBean = softResponseClassGroupListBean.getSoftResponseClassList().get(i + 1);
                            if ((softResponseClassListBean.getSkuId() != null && softResponseClassBean.getSkuId() == null)
                                    || (softResponseClassListBean.getSkuId() == null && softResponseClassBean.getSkuId() != null)
                                    || (softResponseClassListBean.getSkuId() != null && softResponseClassBean.getSkuId() != null
                                    && !softResponseClassListBean.getSkuId().equals(softResponseClassBean.getSkuId()))) {
                                softResponseListBean.setRowHighLight(rowHighLight);
                            } else if (
                                    (CollectionUtils.isEmpty(softResponseClassListBean.getGuiBomList()) && CollectionUtils.isNotEmpty(softResponseClassBean.getGuiBomList()))
                                            || CollectionUtils.isNotEmpty(softResponseClassListBean.getGuiBomList()) && CollectionUtils.isEmpty(softResponseClassBean.getGuiBomList())
                                            || (CollectionUtils.isNotEmpty(softResponseClassBean.getGuiBomList()) && CollectionUtils.isNotEmpty(softResponseClassListBean.getGuiBomList()))) {
                                if (CollectionUtils.isNotEmpty(softResponseClassBean.getGuiBomList()) && CollectionUtils.isNotEmpty(softResponseClassListBean.getGuiBomList())) {
                                    if (softResponseClassBean.getGuiBomList().size() != softResponseClassListBean.getGuiBomList().size()) {
                                        softResponseListBean.setRowHighLight(rowHighLight);
                                    } else {
                                        List<Integer> collect = softResponseClassBean.getGuiBomList().stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList());
                                        List<Integer> collect1 = softResponseClassListBean.getGuiBomList().stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList());
                                        if (!collect1.containsAll(collect)) {
                                            softResponseListBean.setRowHighLight(rowHighLight);
                                        }
                                    }
                                } else {
                                    softResponseListBean.setRowHighLight(rowHighLight);
                                }
                            }

                        }
                    }
                }));
            }
            if (CollectionUtils.isNotEmpty(hardItemList)) {
                hardItemList.forEach(hardItemListBean -> hardItemListBean.getHardItemClassGroupList().forEach(hardItemClassGroupListBean -> {
                    if (CollectionUtils.isNotEmpty(hardItemClassGroupListBean.getHardItemClassList()) && hardItemClassGroupListBean.getHardItemClassList().size() >= 2) {
                        for (int i = 0; i < hardItemClassGroupListBean.getHardItemClassList().size() - 1; i++) {
                            CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassListBean = hardItemClassGroupListBean.getHardItemClassList().get(i);
                            CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean hardItemClassBean = hardItemClassGroupListBean.getHardItemClassList().get(i + 1);

                            if ((hardItemClassListBean.getHardSelectionId() != null && hardItemClassBean.getHardSelectionId() == null)
                                    || (hardItemClassListBean.getHardSelectionId() == null && hardItemClassBean.getHardSelectionId() != null)
                                    || (hardItemClassListBean.getHardSelectionId() != null && hardItemClassBean.getHardSelectionId() != null
                                    && !hardItemClassListBean.getHardSelectionId().equals(hardItemClassBean.getHardSelectionId()))) {
                                hardItemListBean.setRowHighLight(rowHighLight);
                            } else if (
                                    (CollectionUtils.isEmpty(hardItemClassListBean.getGuiBomList()) && CollectionUtils.isNotEmpty(hardItemClassBean.getGuiBomList()))
                                            || CollectionUtils.isNotEmpty(hardItemClassListBean.getGuiBomList()) && CollectionUtils.isEmpty(hardItemClassBean.getGuiBomList())
                                            || (CollectionUtils.isNotEmpty(hardItemClassListBean.getGuiBomList()) && CollectionUtils.isNotEmpty(hardItemClassBean.getGuiBomList()))) {
                                if (CollectionUtils.isNotEmpty(hardItemClassListBean.getGuiBomList()) && CollectionUtils.isNotEmpty(hardItemClassBean.getGuiBomList())) {
                                    if (hardItemClassListBean.getGuiBomList().size() != hardItemClassBean.getGuiBomList().size()) {
                                        hardItemListBean.setRowHighLight(rowHighLight);
                                    } else {
                                        List<Integer> collect = hardItemClassListBean.getGuiBomList().stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList());
                                        List<Integer> collect1 = hardItemClassBean.getGuiBomList().stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList());
                                        if (!collect1.containsAll(collect)) {
                                            hardItemListBean.setRowHighLight(rowHighLight);
                                        }
                                    }
                                } else {
                                    hardItemListBean.setRowHighLight(rowHighLight);
                                }
                            }
                        }
                    }
                }));
            }
        } catch (Exception e) {
            LOG.error("compare soft hard info o2o-exception , more info :", e);
        }
    }

    /**
     * 空间选配是否高亮显示
     *
     * @param spaceSelected
     */
    private boolean compareSpaceSelectedtInfo(List<CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean> spaceSelected) {
        if (CollectionUtils.isNotEmpty(spaceSelected) && spaceSelected.size() >= 2) {
            for (int i = 0; i < spaceSelected.size() - 1; i++) {
                CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedBean = spaceSelected.get(i);
                CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean spaceSelectedSecBean = spaceSelected.get(i + 1);
                if ((spaceSelectedBean.getSolutionName() == null && spaceSelectedSecBean.getSolutionName() != null)
                        || (spaceSelectedBean.getSolutionName() != null && spaceSelectedSecBean.getSolutionName() == null)
                        || (spaceSelectedBean.getSolutionName() != null && spaceSelectedSecBean.getSolutionName() != null
                        && !spaceSelectedBean.getSolutionName().equals(spaceSelectedSecBean.getSolutionName()))) {
                    spaceSelected.forEach(spaceBean -> spaceBean.setRowHighLight(rowHighLight));
                    return true;
                }

            }
        }
        return false;
    }


    /**
     * 比较空间用途功能，判断整行是否需要显示 true 显示 false 不显示
     *
     * @param spaceUsageList
     * @return
     */
    private boolean compareSpaceUsageList(List<CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean> spaceUsageList) {
        if (CollectionUtils.isNotEmpty(spaceUsageList) && spaceUsageList.size() >= 2) {
            for (int i = 0; i < spaceUsageList.size() - 1; i++) {
                CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListBean = spaceUsageList.get(i);
                CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean spaceUsageListSecBean = spaceUsageList.get(i + 1);
                if ((spaceUsageListBean.getSpaceUsageName() == null && spaceUsageListSecBean.getSpaceUsageName() != null)
                        || (spaceUsageListBean.getSpaceUsageName() != null && spaceUsageListSecBean.getSpaceUsageName() == null)
                        || (spaceUsageListBean.getSpaceUsageName() != null && spaceUsageListSecBean.getSpaceUsageName() != null
                        && !spaceUsageListBean.getSpaceUsageName().equals(spaceUsageListSecBean.getSpaceUsageName()))) {
                    spaceUsageList.forEach(spaceUsageBean -> spaceUsageBean.setRowHighLight(rowHighLight));
                    return true;
                }


            }
        }
        return false;
    }

    /**
     * 组装空间及软硬装项目合集（同空间下不包含相同sku）
     *
     * @return
     */
    private List<DraftCollectVo> getSpaceSelectedBean(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, DraftInfoResponse signDraftInfo) {

        List<DraftCollectVo> spaceDesignSelected = new ArrayList<>();
        if (solutionSelected != null && signDraftInfo != null) {
            //组装空间信息
            solutionSelected.getSpaceDesignSelected().forEach(spaceDesign -> signDraftInfo.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                if (RoomUseEnum.getCode(spaceDesignSelectedBean.getSelected().getSpaceUsageName()).equals(spaceDesign.getSpaceUsageId())) {
                    DraftCollectVo draftCollectVo = new DraftCollectVo();
                    draftCollectVo.setSpaceUsageId(spaceDesignSelectedBean.getSpaceUsageId());
                    draftCollectVo.setHouseTypeId(signDraftInfo.getDraftContent().getHouseTypeId());
                    draftCollectVo.setSpaceUsageName(spaceDesignSelectedBean.getSelected().getSpaceUsageName());
                    spaceDesign
                            .setSpaceUsageId(RoomUseEnum.getCode(spaceDesignSelectedBean.getSelected().getSpaceUsageName()))//取spaceUsageName转为SpaceUsageId，为了兼容之前selected没有存SpaceUsageId的老草稿
                            .setSpaceUsageName(spaceDesignSelectedBean.getSelected().getSpaceUsageName());
                    if (CollectionUtils.isNotEmpty(spaceDesign.getOptionalSoftResponseList())) {
                        List<Integer> softCollectList = new ArrayList<>();
                        spaceDesign.getOptionalSoftResponseList().forEach(optionalSoftResponse -> {
                            //商品中心为'新定制品'，app转成 定制家具 20190705
                            if (optionalSoftResponse.getRootCategoryId() != null && optionalSoftResponse.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                                if (!softCollectList.contains(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                                    softCollectList.add(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                                }
                            } else if (CollectionUtils.isNotEmpty(optionalSoftResponse.getGuiBomGroupList()) && optionalSoftResponse.getGuiBomGroupList().get(0).getGroupType().equals(10) && !softCollectList.contains(optionalSoftResponse.getGuiBomGroupList().get(0).getSecondCategoryId())) {
                                softCollectList.add(optionalSoftResponse.getGuiBomGroupList().get(0).getSecondCategoryId());
                            } else if (!softCollectList.contains(optionalSoftResponse.getLastCategoryId())) {
                                softCollectList.add(optionalSoftResponse.getLastCategoryId());
                            }
                        });
                        draftCollectVo.setSoftCollectList(softCollectList);
                    }
                    if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                        List<Integer> hardItemColletList = new ArrayList<>();
                        spaceDesign.getHardItemList().forEach(hardItem -> {
                            if (CollectionUtils.isNotEmpty(hardItem.getGuiBomGroupList()) && hardItem.getGuiBomGroupList().get(0) != null && hardItem.getGuiBomGroupList().get(0).getGroupType() != null && hardItem.getGuiBomGroupList().get(0).getGroupType().equals(9)) {
                                if (!hardItemColletList.contains(hardItem.getGuiBomGroupList().get(0).getSecondCategoryId())) {
                                    hardItemColletList.add(hardItem.getGuiBomGroupList().get(0).getSecondCategoryId());
                                }
                            } else {
                                if (!hardItemColletList.contains(hardItem.getHardItemId())) {
                                    hardItemColletList.add(hardItem.getHardItemId());
                                }
                            }
                        });
                        draftCollectVo.setHardItemCollectList(hardItemColletList);
                    }
                    spaceDesignSelected.add(draftCollectVo);
                }
            }));
        }
        if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
            setSpaceDesignSelectedByDraft(draftInfoResponseList, spaceDesignSelected);
        }
        //不同户型之间的对比
        setUnMactchHouseType(solutionSelected, draftInfoResponseList, signDraftInfo, spaceDesignSelected);
        return spaceDesignSelected;
    }

    /**
     * 不同户型之间的对比，处理 户型空间补全
     *
     * @param draftInfoResponseList
     * @param signDraftInfo
     * @param spaceDesignSelected
     */
    private void setUnMactchHouseType(SolutionSelected solutionSelected, List<DraftInfoResponse> draftInfoResponseList, DraftInfoResponse signDraftInfo, List<DraftCollectVo> spaceDesignSelected) {
        if (!isUnMactchHouseType(spaceDesignSelected)) {
            return;
        }
        if (signDraftInfo != null) {
            setDeaftSpaceMarkId(signDraftInfo, spaceDesignSelected, solutionSelected);
        }
        if (CollectionUtils.isNotEmpty(draftInfoResponseList)) {
            for (DraftInfoResponse draftInfoResponse : draftInfoResponseList) {
                setDeaftSpaceMarkId(draftInfoResponse, spaceDesignSelected, null);
            }
        }

    }

    /**
     * 户型缺省补全
     */
    private void setDeaftSpaceMarkId(DraftInfoResponse draftInfo, List<DraftCollectVo> spaceDesignSelected, SolutionSelected solutionSelected) {
        for (DraftCollectVo draftCollectVo : spaceDesignSelected) {
            int i = 0;
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelectedBean : draftInfo.getDraftContent().getSpaceDesignSelected()) {
                if (draftCollectVo.getSpaceUsageId().equals(spaceDesignSelectedBean.getSpaceUsageId())) {
                    i++;
                }
            }
            if (i == 0) {//该户型图无该户型则进行补全
                draftInfo.getDraftContent().getSpaceDesignSelected().add(new DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean(draftCollectVo.getSpaceUsageId()));
                if (solutionSelected != null) {
                    SpaceDesign spaceDesign = new SpaceDesign();
                    solutionSelected.getSpaceDesignSelected().add(spaceDesign.setSpaceUsageId(draftCollectVo.getSpaceUsageId()));
                }
            }
        }

    }

    /**
     * 判断是否不同户型之间的对比
     *
     * @param spaceDesignSelected
     * @return
     */
    private boolean isUnMactchHouseType(List<DraftCollectVo> spaceDesignSelected) {
        if (CollectionUtils.isNotEmpty(spaceDesignSelected)) {
            for (int i = 0; i < spaceDesignSelected.size() - 1; i++) {
                if (!spaceDesignSelected.get(i).getHouseTypeId().equals(spaceDesignSelected.get(i + 1).getHouseTypeId())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 根据草稿组装空间及软硬装项目合集
     *
     * @param draftInfoResponseList
     * @param spaceDesignSelected
     */
    private void setSpaceDesignSelectedByDraft(List<DraftInfoResponse> draftInfoResponseList, List<DraftCollectVo> spaceDesignSelected) {
        draftInfoResponseList.forEach(draftInfoResponse -> draftInfoResponse.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            DraftCollectVo draftCollectVo = null;
            if (CollectionUtils.isNotEmpty(spaceDesignSelected)) {
                for (DraftCollectVo draftCollectVo1 : spaceDesignSelected) {
                    if (draftCollectVo1.getSpaceUsageId().equals(spaceDesignSelectedBean.getSpaceUsageId())) {
                        draftCollectVo = draftCollectVo1;
                    }
                }
            }
            if (draftCollectVo == null) {
                draftCollectVo = new DraftCollectVo();
                draftCollectVo
                        .setSpaceUsageId(RoomUseEnum.getCode(spaceDesignSelectedBean.getSelected().getSpaceUsageName()))
                        .setHouseTypeId(draftInfoResponse.getDraftContent().getHouseTypeId())
                        .setSpaceUsageName(spaceDesignSelectedBean.getSelected().getSpaceUsageName())
                        .setSoftCollectList(new ArrayList<>())
                        .setHardItemCollectList(new ArrayList<>());
                spaceDesignSelected.add(draftCollectVo);
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean : spaceDesignSelectedBean.getSoftResponseList()) {
                    if (draftCollectVo.getSoftCollectList() == null) {
                        draftCollectVo.setSoftCollectList(new ArrayList<>());
                    }
                    //商品中心为'新定制品'，app转成 定制家具 20190705
                    if (softResponseListBean.getRootCategoryId() != null && softResponseListBean.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                        if (!draftCollectVo.getSoftCollectList().contains(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                            draftCollectVo.getSoftCollectList().add(Constants.CUSTOMIZED_ROOT_CATEGORY_ID);
                        }
                    } else if (softResponseListBean.getCabinetBomGroup() != null && !draftCollectVo.getSoftCollectList().contains(softResponseListBean.getCabinetBomGroup().getSecondCategoryId())) {
                        draftCollectVo.getSoftCollectList().add(softResponseListBean.getCabinetBomGroup().getSecondCategoryId());
                    } else if (!draftCollectVo.getSoftCollectList().contains(softResponseListBean.getLastCategoryId())) {
                        draftCollectVo.getSoftCollectList().add(softResponseListBean.getLastCategoryId());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelectedBean.getHardItemList()) {
                    if (draftCollectVo.getHardItemCollectList() == null) {
                        draftCollectVo.setHardItemCollectList(new ArrayList<>());
                    }
                    if (!draftCollectVo.getHardItemCollectList().contains(hardItemListBean.getHardItemId()) && !hardItemListBean.getHardItemId().equals(0) && hardItemListBean.getCabinetBomGroup() == null) {
                        draftCollectVo.getHardItemCollectList().add(hardItemListBean.getHardItemId());
                    }
                    if (hardItemListBean.getCabinetBomGroup() != null && !draftCollectVo.getHardItemCollectList().contains(hardItemListBean.getCabinetBomGroup().getSecondCategoryId())) {
                        draftCollectVo.getHardItemCollectList().add(hardItemListBean.getCabinetBomGroup().getSecondCategoryId());
                    }
                    if (hardItemListBean.getHardBomGroupSelect() != null &&
                            !draftCollectVo.getHardItemCollectList().contains(hardItemListBean.getHardBomGroupSelect().getCategoryId())) {
                        draftCollectVo.getHardItemCollectList().add(hardItemListBean.getHardBomGroupSelect().getCategoryId());
                    }
                }
            }
        }));
    }


    /**
     * 获取全品家图片
     *
     * @param spaceDesign
     * @return
     */
    private String getSpaceOrderHeadImage(SpaceDesign spaceDesign) {
        List<RoomPictureDto> pictureList = spaceDesign.getRoomPictureDtoList();
        if (CollectionUtils.isNotEmpty(pictureList)) {
            return pictureList.get(0).getPictureUrl();
        }
        return spaceDesign.getHeadImage();
    }


    /**
     * 获取图片
     *
     * @param spaceBean
     * @return
     */
    private String getSpaceHeadImage(DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SpaceBean spaceBean) {
        if (spaceBean.getRoomImage() != null) {
            List<String> pictureList = spaceBean.getRoomImage().getPictureList();
            if (CollectionUtils.isNotEmpty(pictureList)) {
                return pictureList.get(0);
            }
            List<String> oldPictureList = spaceBean.getRoomImage().getOldPictureList();
            if (CollectionUtils.isNotEmpty(oldPictureList)) {
                return oldPictureList.get(0);
            }
        }
        return spaceBean.getHeadImage();
    }

    /**
     * 获取空间选配信息
     *
     * @param draftInfo
     * @return
     */
    private List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> getRoomSelectInfo(DraftInfoResponse draftInfo, Integer betaChangeFlag) {
        List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = new ArrayList<>();
        Integer spaceReplaceCount = draftInfo.getDraftContent().getSpaceReplaceCount();//空间替换总计
        Integer hardReplaceCount = draftInfo.getDraftContent().getHardReplaceCount();//硬装替换总计
        Integer softReplaceCount = draftInfo.getDraftContent().getSoftReplaceCount();//软装替换总计
        if (spaceReplaceCount != null && spaceReplaceCount != 0) {
            Integer spacePriceDiff = 0;
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelectedBean : draftInfo.getDraftContent().getSpaceDesignSelected()) {
                if (spaceDesignSelectedBean.getSelected() != null && !spaceDesignSelectedBean.getSelected().getSolutionId().equals(spaceDesignSelectedBean.getDefaultSpace().getSolutionId())) {
                    spacePriceDiff = spacePriceDiff + (spaceDesignSelectedBean.getSelected().getSpaceDesignPrice() - spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignPrice());
                }
            }
            if (draftInfo.getDraftSignStatus() == 1 && betaChangeFlag != null && betaChangeFlag == 1) {//已签约并发生变动
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean(betaChangeMessage, null, null));
            } else {
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean("空间选配", spaceReplaceCount, spacePriceDiff));
            }
        }
        if (hardReplaceCount != null && hardReplaceCount != 0) {
            Integer hardPriceDiff = getHardPriceDiff(draftInfo);
            if (draftInfo.getDraftSignStatus() == 1 && betaChangeFlag != null && betaChangeFlag == 1) {//已签约并发生变动
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean(betaChangeMessage, null, null));
            } else {
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean("硬装选配", hardReplaceCount, hardPriceDiff));
            }

        }
        if (softReplaceCount != null && softReplaceCount != 0) {
            Integer softPriceDiff = getSoftPriceDiff(draftInfo);
            if (draftInfo.getDraftSignStatus() == 1 && betaChangeFlag != null && betaChangeFlag == 1) {//已签约并发生变动
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean(betaChangeMessage, null, null));
            } else {
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean("软装选配", softReplaceCount, softPriceDiff));
            }

        }


        return rowInnerList;
    }

    /**
     * 计算草稿硬装替换项总价
     *
     * @param draftInfo
     * @return
     */
    private Integer getHardPriceDiff(DraftInfoResponse draftInfo) {
        Integer hardPriceDiff = 0;
        for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelectedBean : draftInfo.getDraftContent().getSpaceDesignSelected()) {
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelectedBean.getHardItemList()) {
                    if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_SELECTED) {
                        if (hardItemListBean.getHardItemSelected() != null) {
                            hardPriceDiff = hardPriceDiff + hardItemListBean.getHardItemSelected().getProcessSelected().getPriceDiff().intValue();
                        }
                        if (hardItemListBean.getHardBomGroupSelect() != null) {
                            hardPriceDiff = hardPriceDiff + hardItemListBean.getHardBomGroupSelect().getPriceDiff().intValue();
                        }

                        if (hardItemListBean.getCabinetBomGroup() != null && hardItemListBean.getCabinetBomGroup().getPriceDiff() != null) {
                            hardPriceDiff = hardPriceDiff + hardItemListBean.getCabinetBomGroup().getPriceDiff().intValue();
                        }
                    }
                    if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_ADD) {
                        if (hardItemListBean.getHardItemSelected() != null) {
                            hardPriceDiff = hardPriceDiff + hardItemListBean.getHardItemSelected().getProcessSelected().getPrice().intValue();
                            if (hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null &&
                                    hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getPrice() != null) {//全屋增项子集
                                hardPriceDiff = hardPriceDiff + hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getPrice().intValue();
                            }
                        }
                        if (hardItemListBean.getHardBomGroupSelect() != null) {
                            hardPriceDiff = hardPriceDiff + hardItemListBean.getHardBomGroupSelect().getPriceDiff().intValue();
                        }
                    }
                    if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_DELETE && hardItemListBean.getHardItemDefault() != null) {
                        hardPriceDiff = hardPriceDiff - hardItemListBean.getHardItemDefault().getProcessSelected().getPrice().intValue();
                    }
                }
            }
        }
        return hardPriceDiff;
    }

    /**
     * 计算草稿软装替换项总价
     *
     * @param draftInfo
     * @return
     */
    private Integer getSoftPriceDiff(DraftInfoResponse draftInfo) {
        Integer softPriceDiff = 0;
        for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelectedBean : draftInfo.getDraftContent().getSpaceDesignSelected()) {
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean : spaceDesignSelectedBean.getSoftResponseList()) {
                    if (softResponseListBean.getStatus() == Constants.ITEM_STATUS_SELECTED) {
                        if (softResponseListBean.getFurnitureSelected() != null) {
                            softPriceDiff = softPriceDiff + softResponseListBean.getFurnitureSelected().getPriceDiff().intValue();
                        } else if (softResponseListBean.getBomGroupSelected() != null) {
                            softPriceDiff = softPriceDiff + softResponseListBean.getBomGroupSelected().getPriceDiff().intValue();
                        } else if (softResponseListBean.getCabinetBomGroup() != null) {
                            softPriceDiff = softPriceDiff + softResponseListBean.getCabinetBomGroup().getPriceDiff().intValue();
                        }

                    }
                }
            }
        }
        return softPriceDiff;
    }

    /**
     * 获取选配风格信息
     *
     * @param draftInfo
     * @return
     */
    private List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> getSolutionSelectInfo(DraftInfoResponse draftInfo) {
        List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        draftInfo.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            if (spaceDesignSelectedBean.getSelected() != null && !list.contains(spaceDesignSelectedBean.getSelected().getSpaceStyle())) {
                list.add(spaceDesignSelectedBean.getSelected().getSpaceStyle());
            }
        });
        rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean(list.stream().collect(Collectors.joining("、"))));
        return rowInnerList;
    }


    /**
     * 获取空间用途信息
     *
     * @param draftInfo
     * @return
     */
    private List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> getSpaceUseInfo(DraftInfoResponse draftInfo) {
        List<CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean> rowInnerList = new ArrayList<>();
        draftInfo.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            if (spaceDesignSelectedBean.getSpaceUsageId() != null && RoomUseEnum.getType(spaceDesignSelectedBean.getSpaceUsageId()) == roomSpaceCode && spaceDesignSelectedBean.getSelected()!=null) {
                rowInnerList.add(new CompareDraftResponse.BaseInfoListBean.RowListBean.RowInnerListBean(RoomUseEnum.getDescription(spaceDesignSelectedBean.getSpaceUsageId()) + "-" + spaceDesignSelectedBean.getSelected().getSpaceUsageName()));
            }
        });
        return rowInnerList;
    }


    /**
     * 查询多份草稿
     *
     * @param draftList
     * @param serverSpan
     * @return
     */
    private Map<String, Object> concurrentQueryDraftList(List<CompareDraftRequest.DraftSimpleListBean> draftList, Integer orderId, CompareDraftRequest.DraftSimpleListBean signDraftSimple,List<Integer> solutionList) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>();
        if (signDraftSimple != null) {

            //查询全品家清单
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() {
                    return orderService.queryOrderSolutionSelectedList(orderId, 750);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_PRODUCT_SOLUTION_SELECT_INFO.name();
                }
            });

            //查roomid对应关系
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() {
                    return getListAllRoomClassRel();
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_ALL_ROOM_CLASS_REL.name();
                }
            });


            //查询已选方案的原始草稿信息
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() {
                    Map<String, Object> params = new HashMap<>();
                    params.put("orderNum", orderId);
                    params.put("draftProfileNum", signDraftSimple.getDraftProfileNum());
                    return homeCardWcmProxy.queryDraftInfo(params);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_DRAFT_INFO.name();
                }
            });

        }

        //查询已选方案的户型图信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramProxy.batchQuerySolutionBaseInfo(solutionList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_BATCH_SOLUTION_BASE_INFO.name();
            }
        });

        //查询草稿信息
        if (CollectionUtils.isNotEmpty(draftList)) {
            for (int i = 0; i < draftList.size(); i++) {
                int finalI = i;
                queryTasks.add(new IdentityTaskAction<Object>() {
                    @Override
                    public Object doInAction() {
                        return homeV5PageService.queryDraftInfo(new QueryDraftRequest(orderId, draftList.get(finalI).getDraftProfileNum()));
                    }

                    @Override
                    public String identity() {
                        return ConcurrentTaskEnum.QUERY_DRAFT_INFO.name() + finalI;
                    }
                });
            }
        }
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 查硬装类目id对应关系
     *
     * @return
     */
    private List<RelRoomIdVo> getListAllRoomClassRel() {
        String redisKey = "o2o:GetListAllRoomClassRel";
        String result = AppRedisUtil.get(redisKey);
        if (!StringUtil.isNullOrEmpty(result)) {
            return JSON.parseArray(result, RelRoomIdVo.class);
        } else {
            List<RelRoomIdVo> relRoomIdVos = productProgramProxy.listAllRoomClassRel();
            if (CollectionUtils.isNotEmpty(relRoomIdVos)) {
                AppRedisUtil.set(redisKey, JSON.toJSONString(relRoomIdVos), 7 * 24 * 3600);
                return relRoomIdVos;
            }
            return null;
        }
    }

}
