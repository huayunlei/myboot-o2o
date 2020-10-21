package com.ihomefnt.o2o.service.service.main;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.constant.*;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.common.http.CopyWriterConstant;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.delivery.dto.DeliverOrderInfo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.QueryOrderCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.Banner;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanDetailDataResponseVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanListResponseVo;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.vo.*;
import com.ihomefnt.o2o.intf.domain.main.vo.request.MainFrameRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.request.NodeContentRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.ContentResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainNodeResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageExtDataResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageResponse;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.StyleRecordRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.StyleRecordResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.request.SolutionListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.DraftInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppSystemInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.right.dto.GradeVersionDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.user.UserConstants;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.order.FamilyOrderStatus;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.intf.proxy.maintain.MaintainProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.right.RightProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.home.HomeV510PageService;
import com.ihomefnt.o2o.intf.service.loan.LoanService;
import com.ihomefnt.o2o.intf.service.main.MainCoreService;
import com.ihomefnt.o2o.intf.service.main.MainPageService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.vote.DnaVoteService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.oms.trade.util.PageModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiamingyu
 * @date 2019/3/20
 */

@Slf4j
@Service
public class MainPageServiceImpl implements MainPageService {

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    OrderProxy orderProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private ProgramPersonalNeedService personalNeedService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private DmsProxy dmsProxy;

    @Autowired
    OrderProxy orderPayProxy;

    @Autowired
    private MaintainProxy maintainProxy;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DnaVoteService dnaVoteService;

    @Autowired
    HomeV510PageService homeV510PageService;

    @Autowired
    private MainCoreService mainCoreService;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.url}", autoRefreshed = true)
    private String xiSheDnaVoteTopBanner;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.linkUrl}", autoRefreshed = true)
    private String xiSheDnaVoteTopBannerLinkUrl;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.forListIndex}", autoRefreshed = true)
    private int xiSheDnaVoteTopBannerForListIndex;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.startTime}", autoRefreshed = true)
    private String dnaVoteHomePageBannerStartTime;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.endTime}", autoRefreshed = true)
    private String dnaVoteHomePageBannerEndTime;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.minVersion}", autoRefreshed = true)
    private String dnaVoteHomePageBannerMinVersion;

    @Autowired
    private DeliveryProxy deliveryProxy;


    @Autowired
    ProductProgramService productProgramService;

    @Autowired
    private RightProxy rightProxy;

    @NacosValue(value = "${NO_VERTICAL_REDUCTION_RIGHT_VERSION}", autoRefreshed = true)
    private String NO_VERTICAL_REDUCTION_RIGHT_VERSION;


    @Override
    public MainPageResponse getMainFrameData(MainFrameRequest request) {

        MainPageResponse mainPageResponse = new MainPageResponse();

        MainNodeResponse mainNodeResponse = new MainNodeResponse();

        ContentResponse contentResponse = new ContentResponse();

        //校验用户是否登录
        HttpUserInfoRequest userInfo = request.getUserInfo();

        setCommonData(mainPageResponse, contentResponse, request, userInfo == null ? null : userInfo.getId());

        //默认值，节点了解我们（兜底代码）
        setFocusNode(MainNodeEnum.ABOUT_US.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.PAY_DEPOSIT, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);

        if (userInfo == null) {
            //未登录用户=>野生用户 节点焦点：了解我们
            setFocusNode(MainNodeEnum.ABOUT_US.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.PAY_DEPOSIT, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
        } else {
            //已登录用户
            if (request.getOrderId() == null) {
                //用户无订单=>野生用户 节点焦点：了解我们
                setFocusNode(MainNodeEnum.ABOUT_US.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.PAY_DEPOSIT, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
            } else {
                //有订单的用户，先查询大订单信息
                Map<String, Object> resultMap = concurrentQueryPayBaseInfo(request.getOrderId());
                AppOrderBaseInfoResponseVo orderInfo = (AppOrderBaseInfoResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name());
                OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
                FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderDetailDto);
                MasterOrderInfo masterOrderInfo = getMasterOrderInfo(orderInfo, familyOrderPayResponse);
                contentResponse.setMasterOrderInfo(masterOrderInfo);
                if (orderInfo == null || orderInfo.getOrderNum() == null || orderInfo.getOrderStatus() == null || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CANCELED.getStatus())) {
                    //用户无订单或订单在接触中=>野生用户 节点焦点：了解我们
                    setFocusNode(MainNodeEnum.ABOUT_US.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.PAY_DEPOSIT, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);

                } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())) {
                    //订单已完成，已点评=>维保 未点评=>验收
                    setDeliveryAndCheckInfo(orderInfo, request.getOrderId(), contentResponse, userInfo.getId());
                    CheckInfo checkInfo = contentResponse.getCheckInfo();
                    if (checkInfo.getIsCommented()) {
                        if (contentResponse.getMaintenanceInfo() != null && contentResponse.getMaintenanceInfo().getOutWarrantyFlag()) {
                            //已过质保期
                            setFocusNode(MainNodeEnum.MAINTENANCE.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.COMMENT_ALREADY, MaintenanceUiFrameEnum.OUT_MAINTENANCE);
                        } else {
                            //未过质保期
                            setFocusNode(MainNodeEnum.MAINTENANCE.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.COMMENT_ALREADY, MaintenanceUiFrameEnum.IN_MAINTENANCE);
                        }
                    } else {
                        //待客户点评，节点焦点：验收
                        setFocusNode(MainNodeEnum.CHECK.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.WAIT_COMMENT, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                    }

                } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
                    //订单处于交付中 节点焦点：施工
                    setDeliveryAndCheckInfo(orderInfo, request.getOrderId(), contentResponse, userInfo.getId());
                    DeliveryInfo deliveryInfo = contentResponse.getDeliveryInfo();
                    CheckInfo checkInfo = contentResponse.getCheckInfo();
                    if (deliveryInfo != null) {
                        if(deliveryInfo.getDeliveryEnd()){//已竣工
                            if (deliveryInfo.getOwnerCheckStatus().equals(0)) {
                                //验收待客户验收
                                setFocusNode(MainNodeEnum.CHECK.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.WAIT_CHECK, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if(deliveryInfo.getOwnerCheckStatus().equals(1)){
                                //验收不通过
                                setFocusNode(MainNodeEnum.CHECK.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.CHECK_NOT_PASS, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if(deliveryInfo.getOwnerCheckStatus().equals(2) && !checkInfo.getIsCommented()){
                                //验收通过未点评
                                setFocusNode(MainNodeEnum.CHECK.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.WAIT_COMMENT, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if(contentResponse.getFastCheckApproval()&& checkInfo.getIsCommented()){
                                //快速验收渠道，点评完成
                                if (contentResponse.getMaintenanceInfo() != null && contentResponse.getMaintenanceInfo().getOutWarrantyFlag()) {
                                    //已过质保期
                                    setFocusNode(MainNodeEnum.MAINTENANCE.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.COMMENT_ALREADY, MaintenanceUiFrameEnum.OUT_MAINTENANCE);
                                } else {
                                    //未过质保期
                                    setFocusNode(MainNodeEnum.MAINTENANCE.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.COMMENT_ALREADY, MaintenanceUiFrameEnum.IN_MAINTENANCE);
                                }
                            } else if(checkInfo.getIsCommented()){
                                //验收通过且已点评
                                setFocusNode(MainNodeEnum.CHECK.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.COMPLETED, CheckUiFrameEnum.COMMENT_ALREADY, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            }
                        }else if (deliveryInfo.getDeliverStatus() < 3 && StringUtils.isBlank(deliveryInfo.getPlanBeginDate())) {
                            //待排期
                            setFocusNode(MainNodeEnum.CONSTRUCTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.WAIT_PLAN, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                        } else if (StringUtils.isNotBlank(deliveryInfo.getPlanBeginDate()) && deliveryInfo.getDeliverStatus() < 3) {
                            //待开工
                            setFocusNode(MainNodeEnum.CONSTRUCTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.WAIT_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                        } else {
                            //已开工未结束
                            setFocusNode(MainNodeEnum.CONSTRUCTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                        }
                    } else {
                        //交付信息获取异常，默认待排期
                        setFocusNode(MainNodeEnum.CONSTRUCTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.WAIT_PLAN, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                    }

                } else {
                    //定金、意向、签约，需查询设计我家信息(选方案信息、方案草稿信息、设计需求信息、爱家贷信息)
                    ConcurrentQueryParam param = new ConcurrentQueryParam().setOrderId(orderInfo.getOrderNum())
                            .setHouseTypeId(orderInfo.getLayoutId())
                            .setHouseProjectId(orderInfo.getBuildingId())
                            .setUserId(userInfo.getId())
                            .setTaskCount(5);
                    Map<String, Object> contentDataMap = concurrentQueryDesignAndLoanInfo(param);
                    setDesignHomeData(contentDataMap, contentResponse, true, request, request.getOrderId(), orderInfo.getLayoutId(),request.getVersion());

                    //定金、意向
                    if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus())) {
                        //订单在意向阶段或定金阶段 节点焦点：设计我家
                        if (contentResponse.getSolutionDraftInfo() == null) {
                            //没保存过草稿
                            setFocusNode(MainNodeEnum.DESIGN_HOME.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.DESIGN_HOME, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                        } else {
                            //保存过草稿
                            setFocusNode(MainNodeEnum.DESIGN_HOME.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                        }

                    } else if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
                        //用户订单在签约阶段
                        if (contentResponse.getSolutionInfo() == null) {
                            //用户未选方案 或选择了爱家贷专用方案 节点焦点：设计我家
                            if (contentResponse.getSolutionDraftInfo() == null) {
                                //不存在草稿
                                setFocusNode(MainNodeEnum.DESIGN_HOME.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.DESIGN_HOME, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else {
                                //存在草稿
                                setFocusNode(MainNodeEnum.DESIGN_HOME.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.NO_SOLUTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            }
                        } else {
                            //存在有效方案 节点焦点:确认方案
                            DeliverySimpleInfoDto deliverySimpleInfoDto = dmsProxy.getSimpleDeliveryInfo(request.getOrderId());
                            ConfirmSolutionInfo confirmSolutionInfo = getConfirmSolutionData(deliverySimpleInfoDto, orderInfo);
                            contentResponse.setConfirmSolutionInfo(confirmSolutionInfo);

                            if (masterOrderInfo.getUnpaidMoney().compareTo(BigDecimal.ZERO) > 0) {
                                //已确认方案未结清款项
                                setFocusNode(MainNodeEnum.CONFIRM_SOLUTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.WAIT_PAY, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if (confirmSolutionInfo.getConfirmConstructionStatus().equals(0)) {
                                //未达到确认开工条件

                                setFocusNode(MainNodeEnum.CONFIRM_SOLUTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.PAY_ALREADY, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if (confirmSolutionInfo.getConfirmConstructionStatus().equals(1)) {
                                //待确认开工
                                setFocusNode(MainNodeEnum.CONFIRM_SOLUTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.WAIT_CONFIRM_CONSTRUCT, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            } else if (confirmSolutionInfo.getConfirmConstructionStatus().equals(2)) {
                                //已确认开工
                                setFocusNode(MainNodeEnum.CONFIRM_SOLUTION.getNodeId(), mainNodeResponse, DesignHomeUiFrameEnum.SEE_MY_DESIGN, ConfirmSolutionUiFrameEnum.IN_CONSTRUCTION, ConstructionUiFrameEnum.NOT_IN_CONSTRUCTION, CheckUiFrameEnum.NOT_COMPLETE, MaintenanceUiFrameEnum.NOT_IN_MAINTENANCE);
                            }
                        }
                    }
                }
            }
        }
        mainPageResponse.setContentResponse(contentResponse);
        mainPageResponse.setMainNodeResponse(mainNodeResponse);
        setMainPageTips(mainPageResponse);
        DraftInfoResponse orderNum = homeCardWcmProxy.queryOrderDraftTotalStatus(Maps.newHashMap("orderNum", request.getOrderId()));
        if (orderNum != null) {
            ContentResponse contentResponseDto = mainPageResponse.getContentResponse();
            if (contentResponseDto != null && contentResponseDto.getSolutionInfo() != null) {
                SolutionInfo solutionInfo = contentResponseDto.getSolutionInfo();
                solutionInfo.setHasNewDrawTaskFinish(Boolean.TRUE);
            }
            if (contentResponseDto != null && contentResponseDto.getSolutionDraftInfo() != null) {
                SolutionDraftInfo solutionDraftInfo = contentResponseDto.getSolutionDraftInfo();
                solutionDraftInfo.setHasNewDrawTaskFinish(Boolean.TRUE);
            }
        }
        return mainPageResponse;
    }

    private void handlerSolutionAndDraftInfo(ContentResponse contentResponse, Integer orderId, Integer houseTypeId,Integer version) {


        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                SolutionListRequest request = new SolutionListRequest().setOrderId(orderId).setApartmentId(houseTypeId).setQueryType(1).setVersion(version);
                return productProgramService.querySolutionAndDraftList(request);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name() + 1;
            }
        });
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                SolutionListRequest request = new SolutionListRequest().setOrderId(orderId).setApartmentId(houseTypeId).setQueryType(2).setVersion(version);
                return productProgramService.querySolutionAndDraftList(request);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name() + 2;
            }
        });
        Map<String, Object> dataMap = Executor.getInvokeOuterServiceFactory().executeIdentityTask(queryTasks);
        DraftSimpleRequestPage draftSimpleRequestPage2 = (DraftSimpleRequestPage) dataMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name() + 2);
        DraftSimpleRequestPage draftSimpleRequestPage1 = (DraftSimpleRequestPage) dataMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name() + 1);
        boolean hasRecord = (draftSimpleRequestPage1 != null && CollectionUtils.isNotEmpty(draftSimpleRequestPage1.getOrderDraftSimpleList())) || (draftSimpleRequestPage2 != null && CollectionUtils.isNotEmpty(draftSimpleRequestPage2.getOrderDraftSimpleList()));
        if (contentResponse.getMainPageExtData() == null) {
            contentResponse.setMainPageExtData(new MainPageExtDataResponse().setHasProgramRecord(hasRecord));
        } else {
            contentResponse.getMainPageExtData().setHasProgramRecord(hasRecord);
        }
        if (draftSimpleRequestPage1 != null && CollectionUtils.isNotEmpty(draftSimpleRequestPage1.getOrderDraftSimpleList())) {
            List<DraftSimpleRequest> collect = draftSimpleRequestPage1.getOrderDraftSimpleList().stream().filter(draftSimpleRequest -> draftSimpleRequest.getType().equals(1) && draftSimpleRequest.getSolutionType().equals(1)).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(collect)) {
                DraftSimpleRequest draftSimpleRequest = collect.get(0);
                SolutionEffectInfo solutionEffectInfo = new SolutionEffectInfo();
                BeanUtils.copyProperties(draftSimpleRequest, solutionEffectInfo);
                solutionEffectInfo.setSpaceDesignList(Lists.newArrayList());
                contentResponse.setExclusiveSolutionInfo(solutionEffectInfo);
            }
        }
    }

    private void setMainPageTips(MainPageResponse mainPageResponse) {
        String confirmTopTips = "";
        for (MainNode node : mainPageResponse.getMainNodeResponse().getNodeList()) {
            if (node.getNodeId().equals(MainNodeEnum.CONFIRM_SOLUTION.getNodeId())) {
                confirmTopTips = ConfirmSolutionMajorTipsEnum.getEnumByFrameId(node.getUiFrame()).getMajorTips();
                if (confirmTopTips.contains("距离交房") && mainPageResponse.getContentResponse().getConfirmSolutionInfo() != null && mainPageResponse.getContentResponse().getConfirmSolutionInfo().getLeaveRoomDays() != null && mainPageResponse.getContentResponse().getConfirmSolutionInfo().getLeaveRoomDays() <= 0) {
                    confirmTopTips = "已交房";
                }
                break;
            }
        }

        MainPageTips mainPageTips = new MainPageTips().setConfirmTopTips(confirmTopTips);
        mainPageResponse.getContentResponse().setMainPageTips(mainPageTips);
    }

    private ConfirmSolutionInfo getConfirmSolutionData(DeliverySimpleInfoDto deliverySimpleInfoDto, AppOrderBaseInfoResponseVo orderInfo) {

        ConfirmSolutionInfo confirmSolutionInfo = new ConfirmSolutionInfo();
        //查询是否可确认开工标记位
        CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirmVo = orderProxy.checkIfCanDeliveryConfirm(orderInfo.getOrderNum());

        if (checkIfCanDeliveryConfirmVo.getCheckResult()) {
            //可以确认开工
            confirmSolutionInfo.setConfirmConstructionStatus(1);
        } else if (deliverySimpleInfoDto != null && !deliverySimpleInfoDto.getConfirmFlag() && StringUtils.isNotBlank(deliverySimpleInfoDto.getConfirmDateStr())) {
            //已确认开工
            confirmSolutionInfo.setConfirmConstructionStatus(2);
            try {
                confirmSolutionInfo.setConfirmationTime(DateUtil.formatDate(
                        DateUtil.parseDate(deliverySimpleInfoDto.getConfirmDateStr(), Collections.singletonList("yyyy-MM-dd HH:mm:ss")), "yyyy年MM月dd日"));
            } catch (DateParseException e) {
                confirmSolutionInfo.setConfirmationTime("");
            }
        } else {
            //未到确认开工条件
            confirmSolutionInfo.setConfirmConstructionStatus(0);
        }
        if (orderInfo != null) {
            confirmSolutionInfo.setIsConfirmed(orderInfo.getPreConfirmed() != null && (orderInfo.getPreConfirmed() == 1))
                    .setLeaveRoomDays(orderInfo.getDeliverDiff());


        }
        return confirmSolutionInfo;
    }

    @Override
    public ContentResponse getNodeContent(NodeContentRequest request) {

        ContentResponse contentResponse = new ContentResponse();

        int status;
        if (request.getCurrentNodeId().equals(request.getFocusNodeId())) {
            status = 0;
        } else if (request.getCurrentNodeId() < request.getFocusNodeId()) {
            status = -1;
        } else {
            status = 1;
        }

        if (request.getCurrentNodeId().equals(MainNodeEnum.DESIGN_HOME.getNodeId())) {
            Map<String, Object> contentDataMap;
            HttpUserInfoRequest userInfo = request.getUserInfo();
            ConcurrentQueryParam param;
            switch (status) {
                case -1:
                    param = new ConcurrentQueryParam().setUserId(userInfo.getId())
                            .setOrderId(request.getOrderId())
                            .setTaskCount(3);
                    contentDataMap = concurrentQueryDesignAndLoanInfo(param);
                    setDesignHomeData(contentDataMap, contentResponse, false, request, request.getOrderId(), request.getHouseTypeId(),request.getVersion());
                    break;
                case 0:
                    param = new ConcurrentQueryParam().setUserId(userInfo.getId())
                            .setOrderId(request.getOrderId())
                            .setTaskCount(5)
                            .setHouseProjectId(request.getHouseProjectId())
                            .setHouseTypeId(request.getHouseTypeId());
                    contentDataMap = concurrentQueryDesignAndLoanInfo(param);
                    setDesignHomeData(contentDataMap, contentResponse, true, request, request.getOrderId(), request.getHouseTypeId(),request.getVersion());
                    break;
                default:
                    break;
            }
        } else if (request.getCurrentNodeId().equals(MainNodeEnum.CONFIRM_SOLUTION.getNodeId())) {
            Map<String, Object> contentDataMap;
            switch (status) {
                case -1:
                    //选方案信息
                    contentDataMap = concurrentQueryConfirmSolutionInfo(request.getOrderId(), status);
                    setDesignHomeData(contentDataMap, contentResponse, false, request, request.getOrderId(), request.getHouseTypeId(),request.getVersion());
                    break;
                case 0:
                    //确认方案信息+大订单信息+交付信息
                    contentDataMap = concurrentQueryConfirmSolutionInfo(request.getOrderId(), status);
                    setDesignHomeData(contentDataMap, contentResponse, false, request, request.getOrderId(), request.getHouseTypeId(),request.getVersion());
                    AppOrderBaseInfoResponseVo orderInfo = (AppOrderBaseInfoResponseVo) contentDataMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name());
                    DeliverySimpleInfoDto deliverySimpleInfoDto = (DeliverySimpleInfoDto) contentDataMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
                    ConfirmSolutionInfo confirmSolutionInfo = getConfirmSolutionData(deliverySimpleInfoDto, orderInfo);
                    contentResponse.setConfirmSolutionInfo(confirmSolutionInfo);
                    break;
                default:
                    break;
            }
        } else {
            return null;
        }
        DraftInfoResponse orderNum = homeCardWcmProxy.queryOrderDraftTotalStatus(Maps.newHashMap("orderNum", request.getOrderId()));
        if (orderNum != null) {
            if (contentResponse != null && contentResponse.getSolutionInfo() != null) {
                SolutionInfo solutionInfo = contentResponse.getSolutionInfo();
                solutionInfo.setHasNewDrawTaskFinish(Boolean.TRUE);
            }
            if (contentResponse != null && contentResponse.getSolutionDraftInfo() != null) {
                SolutionDraftInfo solutionDraftInfo = contentResponse.getSolutionDraftInfo();
                solutionDraftInfo.setHasNewDrawTaskFinish(Boolean.TRUE);
            }
        }
        return contentResponse;
    }

    /**
     * 设置节点信息
     *
     * @param nodeId                  节点ID
     * @param mainNodeResponse        节点对象
     * @param designHomeUiFrame       设计我家节点展示
     * @param confirmSolutionUiFrame  确认方案节点展示
     * @param constructionUiFrameEnum 施工节点展示
     * @param checkUiFrameEnum        验收节点展示
     * @param maintenanceUiFrameEnum  维保节点展示
     */
    private void setFocusNode(Integer nodeId, MainNodeResponse mainNodeResponse, DesignHomeUiFrameEnum designHomeUiFrame, ConfirmSolutionUiFrameEnum confirmSolutionUiFrame, ConstructionUiFrameEnum constructionUiFrameEnum, CheckUiFrameEnum checkUiFrameEnum, MaintenanceUiFrameEnum maintenanceUiFrameEnum) {
        for (MainNode node : mainNodeResponse.getNodeList()) {
            if (node.getNodeId().equals(nodeId)) {
                mainNodeResponse.setFocusNode(node);
            }
            if (node.getNodeId().equals(MainNodeEnum.DESIGN_HOME.getNodeId())) {
                node.setUiFrame(designHomeUiFrame.getUiFrameId());
                node.setUiFrameName(designHomeUiFrame.getUiFrameName());
            } else if (node.getNodeId().equals(MainNodeEnum.CONFIRM_SOLUTION.getNodeId())) {
                node.setUiFrame(confirmSolutionUiFrame.getUiFrameId());
                node.setUiFrameName(confirmSolutionUiFrame.getUiFrameName());
            } else if (node.getNodeId().equals(MainNodeEnum.CONSTRUCTION.getNodeId())) {
                node.setUiFrame(constructionUiFrameEnum.getUiFrameId());
                node.setUiFrameName(constructionUiFrameEnum.getUiFrameName());
            } else if (node.getNodeId().equals(MainNodeEnum.CHECK.getNodeId())) {
                node.setUiFrame(checkUiFrameEnum.getUiFrameId());
                node.setUiFrameName(checkUiFrameEnum.getUiFrameName());
            } else if (node.getNodeId().equals(MainNodeEnum.MAINTENANCE.getNodeId())) {
                node.setUiFrame(maintenanceUiFrameEnum.getUiFrameId());
                node.setUiFrameName(maintenanceUiFrameEnum.getUiFrameName());
            }
        }
    }

    /**
     * 首页公共信息
     *
     * @param mainPageResponse 接口根对象
     * @param contentResponse  节点内容对象
     * @param request          请求参数
     */
    private void setCommonData(MainPageResponse mainPageResponse, ContentResponse contentResponse, HttpBaseRequest request, Integer userId) {
        Map<String, Object> commonDataMap;
        String redisData = AppRedisUtil.getDataRedis(MainPageConstants.MAIN_COMMON_DATA_REDIS_KEY);
        if (redisData != null) {
            commonDataMap = JsonUtils.json2map(redisData);
        } else {
            commonDataMap = concurrentQueryBannerInfoAndAboutUs();
            AppRedisUtil.setDataRedis(MainPageConstants.MAIN_COMMON_DATA_REDIS_KEY, JsonUtils.obj2json(commonDataMap));
        }
        AboutUsInfo aboutUsInfo = new AboutUsInfo();
        AppSystemInfoResponseVo systemInfoResponseVo = JsonUtils.json2obj(JsonUtils.obj2json(commonDataMap.get(ConcurrentTaskEnum.QUERY_ABOUT_US_INFO.name())), AppSystemInfoResponseVo.class);
        if (systemInfoResponseVo != null) {
            if (systemInfoResponseVo.getProjectCount() != null) {
                aboutUsInfo.setProjectNum(systemInfoResponseVo.getProjectCount());
            }
            if (systemInfoResponseVo.getProvinceCount() != null) {
                aboutUsInfo.setProvinceNum(systemInfoResponseVo.getProvinceCount());
            }
            if (systemInfoResponseVo.getRoomCount() != null) {
                aboutUsInfo.setUserNum(systemInfoResponseVo.getRoomCount());
            }
        }

        PageModel commentPage = JsonUtils.json2obj(JsonUtils.obj2json(commonDataMap.get(ConcurrentTaskEnum.QUERY_USER_COMMENT.name())), PageModel.class);

        if (commentPage != null) {
            aboutUsInfo.setCommentList(commentPage);
        }

        contentResponse.setAboutUsInfo(aboutUsInfo);

        List<Banner> bannerList = new ArrayList<Banner>();
        List<BannerResponseVo> bannerResponseVos = JsonUtils.json2list(JsonUtils.obj2json(commonDataMap.get(ConcurrentTaskEnum.QUERY_BANNER_INFO.name())), BannerResponseVo.class);
        homeV510PageService.reduceBannerByVersion(bannerResponseVos, request.getAppVersion());
        if (CollectionUtils.isNotEmpty(bannerResponseVos)) {
            for (BannerResponseVo bannerResponseVo : bannerResponseVos) {
                Banner banner = new Banner();
                banner.setHeadImage(AliImageUtil.imageCompress(bannerResponseVo.getImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                banner.setLinkUrl(bannerResponseVo.getUrl());
                bannerList.add(banner);
            }
        }
        try {
            Date startDate = DateUtils.parseDate(dnaVoteHomePageBannerStartTime, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtils.parseDate(dnaVoteHomePageBannerEndTime, "yyyy-MM-dd HH:mm:ss");
            if (!VersionUtil.mustUpdate(request.getAppVersion(), dnaVoteHomePageBannerMinVersion) && System.currentTimeMillis() > startDate.getTime() && System.currentTimeMillis() < endTime.getTime() && userId != null && dnaVoteService.handlerUserOpenRule(userId)) {
                bannerList.add(xiSheDnaVoteTopBannerForListIndex, new Banner().setHeadImage(AliImageUtil.imageCompress(xiSheDnaVoteTopBanner, request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).setLinkUrl(xiSheDnaVoteTopBannerLinkUrl));
            }
        } catch (Exception e) {
            log.error("banner o2o-exception , more info :{}", e.getMessage());
        }
        mainPageResponse.setBanner(bannerList);
    }

    private void setDesignHomeData(Map<String, Object> contentDataMap, ContentResponse contentResponse, boolean needDraft, HttpBaseRequest request, Integer orderId, Integer houseTypeId,Integer version) {
        //选方案信息
        SolutionInfo solutionInfo = (SolutionInfo) contentDataMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
        if (solutionInfo != null && !solutionInfo.getPurchaseType().equals(2)) {
            //选了爱家贷专用方案视为未选方案
            contentResponse.setSolutionInfo(solutionInfo.setSolutionImgUrl(AliImageUtil.imageCompress(solutionInfo.getSolutionImgUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE))
                    .setSolutionTypeStr(solutionInfo.getSolutionType() == 1 ? "纯软装" : "软装+硬装"));

        }

        //爱家贷信息
        LoanListResponseVo loanMainInfoVo = (LoanListResponseVo) contentDataMap.get(ConcurrentTaskEnum.QUERY_LOAN_INFO.name());
        if (loanMainInfoVo != null && CollectionUtils.isNotEmpty(loanMainInfoVo.getLoanList())) {
            LoanDetailDataResponseVo infoVo = loanMainInfoVo.getLoanList().get(0);
            LoanInfo loanInfo = new LoanInfo();
            loanInfo.setLoanId(infoVo.getLoanId())
                    .setLoanStatus(infoVo.getLoanStatus())
                    .setLoanStatusStr(infoVo.getLoanStatusStr())
                    .setLoanCount(loanMainInfoVo.getLoanList().size());
            contentResponse.setLoanInfo(loanInfo);
        }
        if (orderId != null && houseTypeId != null) {
            this.handlerSolutionAndDraftInfo(contentResponse, orderId, houseTypeId,version);
        }
        //选风格信息
        StyleRecordResponse recordResponse = (StyleRecordResponse) contentDataMap.get(ConcurrentTaskEnum.QUERY_DESIGN_TASK_INFO.name());
        if (recordResponse != null && CollectionUtils.isNotEmpty(recordResponse.getStyleRecordList())) {
            PersonalDesignResponse designResponseVo = recordResponse.getStyleRecordList().get(0);
            if (designResponseVo != null) {
                DesignDemandInfo designDemandInfo = new DesignDemandInfo();
                designDemandInfo.setTaskStatus(designResponseVo.getTaskStatus())
                        .setTaskStatusStr(designResponseVo.getTaskStatusStr())
                        .setDesignDemandCount(recordResponse.getStyleRecordList().size())
                        .setCommitRecordId(designResponseVo.getStyleCommitRecordId());
                if (designDemandInfo.getCommitRecordId() == null) {
                    designDemandInfo.setDesignResponse(designResponseVo);
                }
                contentResponse.setDesignDemandInfo(designDemandInfo);
            }
        }

        if (needDraft) {
            //草稿信息
            DraftSimpleRequestPage draftSimpleRequestPage = (DraftSimpleRequestPage) contentDataMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_DRAFT_INFO.name());


            if (draftSimpleRequestPage != null && CollectionUtils.isNotEmpty(draftSimpleRequestPage.getOrderDraftSimpleList())) {
                DraftSimpleRequest draftDto = draftSimpleRequestPage.getOrderDraftSimpleList().get(0);
                if (!draftDto.getDraftSignStatus().equals(1)) {
                    draftSimpleRequestPage.getOrderDraftSimpleList().stream().sorted(Comparator.comparingLong(value -> -value.timeNum()));
                    draftDto = draftSimpleRequestPage.getOrderDraftSimpleList().get(0);
                }
                SolutionDraftInfo solutionDraftInfo = new SolutionDraftInfo().setDraftId(draftDto.getDraftId())
                        .setDraftProfileNum(draftDto.getDraftProfileNum())
                        .setDraftCount(draftSimpleRequestPage.getTotalRecords())
                        .setSolutionPrice(draftDto.getTotalPrice())
                        .setSolutionType(draftDto.getDecorationType())
                        .setDraftSignStatus(draftDto.getDraftSignStatus())
                        .setHeadImage(AliImageUtil.imageCompress(draftDto.getUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE))
                        .setStyleName(draftDto.getStyleName());
                if (draftDto.getDecorationType() != null) {
                    solutionDraftInfo.setSolutionTypeStr(draftDto.getDecorationType() == 1 ? "纯软装" : "软装+硬装");
                }
                contentResponse.setSolutionDraftInfo(solutionDraftInfo);
            }
            //可选方案数量
            Integer availableSolutionCount = (Integer) contentDataMap.get(ConcurrentTaskEnum.QUERY_AVAILABLE_SOLUTION_COUNT.name());
            MasterOrderInfo masterOrderInfo = contentResponse.getMasterOrderInfo();
            if (masterOrderInfo != null) {
                masterOrderInfo.setAvailableSolutionCount(availableSolutionCount == null ? 0 : availableSolutionCount);
            }
        }
    }

    /**
     * 组装大订单信息
     *
     * @param orderInfo
     * @return 大订单信息对象
     */
    private MasterOrderInfo getMasterOrderInfo(AppOrderBaseInfoResponseVo orderInfo, FamilyOrderPayResponse familyOrderPayResponse) {
        MasterOrderInfo masterOrderInfo = new MasterOrderInfo();
        List<Integer> versionList = Lists.newArrayList(NO_VERTICAL_REDUCTION_RIGHT_VERSION.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        if (orderInfo != null) {
            OrderDetailDto orderDetailDto = orderProxy.queryOrderSummaryInfo(orderInfo.getOrderNum());
            GradeVersionDto gradeVersionDto = rightProxy.queryCurrentVersion(orderInfo.getOrderNum());
            if (orderDetailDto != null) {
                masterOrderInfo.setOrderSubStatus(orderDetailDto.getOrderSubStatus());
            }
            if (gradeVersionDto != null) {
                masterOrderInfo.setIsCustomRightVersion(versionList.contains(gradeVersionDto.getVersion()));
            }
            masterOrderInfo.setOrderId(orderInfo.getOrderNum())
                    .setGradeId(orderInfo.getGradeId())
                    .setRightsVersion(orderInfo.getRightsVersion())
                    .setGradeName(orderInfo.getGradeName())
                    .setOldFlag(orderInfo.getOldOrder() != 1)
                    .setOrderStatus(orderInfo.getOrderStatus())
                    .setTotalPrice(orderInfo.getOrderTotalAmount() == null ? BigDecimal.ZERO : orderInfo.getOrderTotalAmount())
                    .setPaidMoney(orderInfo.getFundAmount() == null ? BigDecimal.ZERO : orderInfo.getFundAmount())
                    .setSolutionTotalPrice(orderInfo.getSolutionAmount() == null ? BigDecimal.ZERO : orderInfo.getSolutionAmount().subtract(orderInfo.getPriceDisAmount() == null ? BigDecimal.ZERO : orderInfo.getPriceDisAmount()))
                    .setUpgradeDisAmount(orderInfo.getUpgradeDisAmount() == null ? BigDecimal.ZERO : orderInfo.getUpgradeDisAmount())
                    .setUpGradeCouponAmount(orderInfo.getUpGradeCouponAmount() == null ? BigDecimal.ZERO : orderInfo.getUpGradeCouponAmount())
                    .setHomeAdviserMobile(orderInfo.getAdviserPhone())
                    .setHouseProjectId(orderInfo.getBuildingId())
                    .setHouseTypeId(orderInfo.getLayoutId())
                    .setSolutionType(orderInfo.getOrderSaleType())
                    .setUpItemDeAmount(orderInfo.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderInfo.getUpItemDeAmount())
                    .setLockPriceFlag(orderInfo.getLockPriceFlag() == null ? -1 : orderInfo.getLockPriceFlag())
                    .setLockPriceExpireTime(orderInfo.getLockPriceExpireTime())
                    .setPriceLockCount(getPriceLockCount(orderInfo.getLockPriceExpireTime()));
            if(orderInfo.getRightsVersion()!=null && orderInfo.getRightsVersion()==4){
                masterOrderInfo.setGradeId(-1);
            }
            if (isFinalAndPaidAmountEmpty(familyOrderPayResponse)) {
                if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) == 0) {
                    masterOrderInfo.setAllMoney(1);
                } else if (familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) == -1) {
                    masterOrderInfo.setAllMoney(2);
                }
            }

            if (IntegerUtil.isNullOrZero(orderInfo.getPreConfirmed())) {
                //方案未确认，展示的订单总价要减掉艾升级权益可抵扣的金额
                BigDecimal upItemDeAmount = orderInfo.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderInfo.getUpItemDeAmount();
                BigDecimal totalAmount = (masterOrderInfo.getTotalPrice().subtract(upItemDeAmount));
                masterOrderInfo.setTotalPrice(totalAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : totalAmount);
            }
            masterOrderInfo.setUnpaidMoney(masterOrderInfo.getTotalPrice().subtract(masterOrderInfo.getPaidMoney()));
        }
        return masterOrderInfo;
    }

    /**
     * 判断订单金额和已付金额是否为空
     *
     * @param familyOrderPayResponse
     * @return
     */
    private boolean isFinalAndPaidAmountEmpty(FamilyOrderPayResponse familyOrderPayResponse) {
        return familyOrderPayResponse != null && familyOrderPayResponse.getFinalOrderPrice() != null && familyOrderPayResponse.getFinalOrderPrice().getValue() != null
                && familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(BigDecimal.ZERO) != 0 && familyOrderPayResponse.getPaidAmount() != null;
    }


    /**
     * 获取锁价倒计时（秒）
     *
     * @param lockPriceExpireTime
     * @return
     */
    private Integer getPriceLockCount(Date lockPriceExpireTime) {
        if (lockPriceExpireTime != null) {
            return (int) ((new Date().getTime() - lockPriceExpireTime.getTime()) / 1000);
        } else {
            return null;
        }

    }

    public DmsProxy getDmsProxy() {
        return dmsProxy;
    }


    /**
     * 组装交付信息
     *
     * @param orderInfo 大订单信息
     * @param orderId   订单号
     * @return 交付信息对象
     */
    private void setDeliveryAndCheckInfo(AppOrderBaseInfoResponseVo orderInfo, Integer orderId, ContentResponse contentResponse, Integer userId) {
        Map<String, Object> dataMap = concurrentQueryMaintenanceAndDeliveryInfo(orderId, userId);
        DeliverySimpleInfoDto deliverySimpleInfoDto = (DeliverySimpleInfoDto) dataMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setConstructionPeriod(deliverySimpleInfoDto.getConstructionPeriod() == null ? 75 : deliverySimpleInfoDto.getConstructionPeriod());
        deliveryInfo.setSoftTotal(orderInfo.getTotalProductCount());
        deliveryInfo.setSoftFinishNum(orderInfo.getCompleteDelivery());
        List<DeliverOrderInfo> deliverOrderInfos = (List<DeliverOrderInfo>) dataMap.get(ConcurrentTaskEnum.QUERY_DELIVER_ORDERINFO_LIST_BY_ORDER_ID_LIST.name());
        //硬装进度
        String hardProgress = mainCoreService.transferDeliverHard(deliverySimpleInfoDto);

        deliveryInfo.setHardProgress(hardProgress)
                .setStartedDays(deliverySimpleInfoDto.getStartedDays() == null ? 0 : deliverySimpleInfoDto.getStartedDays())
                .setPlanBeginDate(deliverySimpleInfoDto.getPlanBeginDate())
                .setStartedDaysUnit(deliverySimpleInfoDto.getStartedDaysUnit())
                .setPlanEndDate(deliverySimpleInfoDto.getPlanEndDate())
                .setOwnerCheckStatus(deliverySimpleInfoDto.getOwnerCheckStatus())
                .setMasterCheckStatus(deliverySimpleInfoDto.getFastCheckApproval()!=null && deliverySimpleInfoDto.getFastCheckApproval()?2:deliverySimpleInfoDto.getMasterCheckStatus())
                .setDeliveryEnd(deliverySimpleInfoDto.getFastCheckApproval()!=null && deliverySimpleInfoDto.getFastCheckApproval()?true:deliverySimpleInfoDto.getDeliverStatus()>5? true : false)//已竣工
                .setDeliverStatus(deliverySimpleInfoDto.getDeliverStatus());

        if (CollectionUtils.isNotEmpty(deliverOrderInfos) && deliverOrderInfos.get(0) != null) {
            DeliverOrderInfo deliverOrderInfo = deliverOrderInfos.get(0);
            deliveryInfo.setSurveyorName(deliverOrderInfo.getManagerName());
            deliveryInfo.setSurveyorPhone(deliverOrderInfo.getMobile());
        }


        //软装到货进度
        if (deliveryInfo.getSoftFinishNum() != null && deliveryInfo.getSoftFinishNum() > 0) {
            if (deliveryInfo.getSoftFinishNum().equals(deliveryInfo.getSoftTotal())) {
                deliveryInfo.setSoftProgress("已全部到货");
            } else {
                deliveryInfo.setSoftProgress("已到货" + deliveryInfo.getSoftFinishNum() + "件");
            }
        } else {
            deliveryInfo.setSoftProgress("软装备货中");
        }

        CommentsDto commentsDto = (CommentsDto) dataMap.get(ConcurrentTaskEnum.QUERY_ORDER_COMMENT_INFO.name());
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setOwnerCheckStatus(deliverySimpleInfoDto.getOwnerCheckStatus())
                .setIsCommented((commentsDto == null || commentsDto.getOrderId() == null) ? false : true);
        if (commentsDto != null) {
            checkInfo.setEvaluationStars(commentsDto.getScore());
            checkInfo.setEvaluationContent(commentsDto.getComment());
        }

        if (commentsDto != null && commentsDto.getOrderId() != null) {
            //查询报修记录
            List<TaskDetailDto> detailResponseVos = (List<TaskDetailDto>) dataMap.get(ConcurrentTaskEnum.QUERY_MAINTENANCE_INFO.name());
            Integer maintainCount = detailResponseVos == null ? 0 : detailResponseVos.size();
            //质保期
            MaintenanceInfo maintenanceInfo = new MaintenanceInfo().setMaintainCount(maintainCount).setOutWarrantyFlag(deliverySimpleInfoDto != null && deliverySimpleInfoDto.getOutWarrantyFlag());
            contentResponse.setMaintenanceInfo(maintenanceInfo);
        }
        contentResponse.setFastCheckApproval(deliverySimpleInfoDto.getFastCheckApproval()==null?false:deliverySimpleInfoDto.getFastCheckApproval());
        contentResponse.setDeliveryInfo(deliveryInfo);
        contentResponse.setCheckInfo(checkInfo);
    }

    /**
     * 收银台内容组装
     *
     * @param orderDetailDto
     * @return
     */
    private FamilyOrderPayResponse queryPayBaseInfo(OrderDetailDto orderDetailDto) {
        if (orderDetailDto != null) {
            FamilyOrderPayResponse response = new FamilyOrderPayResponse();
            response.setUpItemAmount(orderDetailDto.getUpItemAmount());//选配升级项金额
            response.setOrderStatus(FamilyOrderStatus.getOrderStatus(orderDetailDto.getOrderStatus()));
            response.setAdviserName(StringUtils.isBlank(orderDetailDto.getAdviserName()) ? null : orderDetailDto.getAdviserName());
            response.setAdviserMobile(StringUtils.isBlank(orderDetailDto.getAdviserMobile()) ? null : orderDetailDto.getAdviserMobile());
            response.setOrderSource(orderDetailDto.getSource());
            response.setGradeId(orderDetailDto.getGradeId());
            response.setGradeName(orderDetailDto.getGradeName());
            //其他优惠
            response.setOtherDisAmount(orderDetailDto.getNewOtherDisAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewOtherDisAmount());

            // 艾升级券面值
            response.setUpGradeCouponAmount(orderDetailDto.getUpGradeCouponAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpGradeCouponAmount());
            // 全品家立减金额
            response.setRightsDiscountAmount(orderDetailDto.getRightsDiscountAmount() == null ? BigDecimal.ZERO : orderDetailDto.getRightsDiscountAmount());

            //权益可抵扣金额，若已确认方案，则为已抵扣金额
            response.setRightAmount(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount());

            //用户已付金额
            response.setPaidAmount(orderDetailDto.getFundAmount() == null ? BigDecimal.ZERO : orderDetailDto.getFundAmount());

            //实际合同额
            BigDecimal contractAmount = orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount();

            //已确认收款金额
            response.setConfirmReapAmount(orderDetailDto.getConfirmedAmount() == null ? BigDecimal.ZERO : orderDetailDto.getConfirmedAmount());

            //剩余应付
            BigDecimal restPay = BigDecimal.ZERO;

            //方案总价
            BigDecimal solutionPrice = BigDecimal.ZERO;

            if (IntegerUtil.isNullOrZero(orderDetailDto.getPreConfirmed())) {
                //方案未确认
                //app订单总价  = beta订单总价- 艾升级权益
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount().subtract(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount())));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额
                    solutionPrice = contractAmount;
                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端显示的抵扣后合同额为：实际合同额-权益可抵扣金额
                BigDecimal restContract = contractAmount.subtract(response.getRightAmount());
                response.setContractAmount(restContract);

                //用户剩余应付，确认方案前为：合同额-已付金额-权益抵扣，若<=0 则为0
                restPay = contractAmount.subtract(response.getPaidAmount()).subtract(response.getRightAmount());
            } else if (orderDetailDto.getPreConfirmed().equals(1)) {
                //app订单总价  = beta订单总价
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount()));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                //方案已确认
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额+权益已抵扣金额
                    solutionPrice = contractAmount.add(response.getRightAmount());

                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端展示的抵扣后合同额为：实际合同额
                response.setContractAmount(orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount());

                //用户剩余应付，确认方案后为：合同额-已支付金额
                restPay = contractAmount.subtract(response.getPaidAmount());

            }
            //3月款项优化后的剩余应付
            response.setNewRestAmount(response.getFinalOrderPrice().getValue().subtract(response.getPaidAmount()));
            if (response.getNewRestAmount().compareTo(BigDecimal.ZERO) < 0) {
                response.setNewRestAmount(BigDecimal.ZERO);
            }
            response.setContractAmount(response.getFinalOrderPrice().getValue());
            //原始方案价，若为空，则取合同金额
            response.setSolutionTotalPrice(solutionPrice);
            //方案总价
            response.setSolutionAmount(orderDetailDto.getNewSolutionAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewSolutionAmount());
            response.setRestAmount(restPay.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : restPay);

            response.setCanLoanAmount(response.getNewRestAmount() != null && response.getNewRestAmount().compareTo(BigDecimal.ZERO) > 0 ? response.getNewRestAmount() : UserConstants.CAN_LOAN_AMOUNT.subtract(response.getPaidAmount() == null ? BigDecimal.ZERO : response.getPaidAmount()));
            response.setCanLoanAmount(response.getCanLoanAmount().compareTo(BigDecimal.ZERO) >= 0 ? response.getCanLoanAmount() : BigDecimal.ZERO);
            return response;
        }
        return null;
    }

    /**
     * 大订单信息
     *
     * @param orderId
     * @return
     */
    private Map<String, Object> concurrentQueryPayBaseInfo(Integer orderId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }


    private Map<String, Object> concurrentQueryBannerInfoAndAboutUs() {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);
        //查询系统中的省份、楼盘、用户数
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramOrderProxy.queryAppSystemInfo();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ABOUT_US_INFO.name();
            }
        });
        //查询首页banner信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return homeCardWcmProxy.queryBannerByType();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_BANNER_INFO.name();
            }
        });

        //查询第一条用户评论
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                QueryOrderCommentRequestVo params = new QueryOrderCommentRequestVo();
                params.setPageNo(1);
                params.setPageSize(1);
                return homeCardWcmProxy.queryOrderCommentListByCondition(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_USER_COMMENT.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private Map<String, Object> concurrentQueryDesignAndLoanInfo(ConcurrentQueryParam param) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(param.getTaskCount());

        //查询已选方案信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo(param.getOrderId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });
        //查询爱家贷信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return loanService.queryLoanInfoList(param.getOrderId().longValue());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_LOAN_INFO.name();
            }
        });
        //查询设计需求信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                StyleRecordRequest recordRequest = new StyleRecordRequest();
                recordRequest.setOrderId(param.getOrderId());
                return personalNeedService.queryStyleRecord(recordRequest, param.getUserId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DESIGN_TASK_INFO.name();
            }
        });

        if (param.getTaskCount() > 3) {
            //查询方案草稿信息
            queryTasks.add(new IdentityTaskAction<Object>() {

                @Override
                public Object doInAction() {
                    Map<String, Object> params = new HashMap<>(2);
                    params.put("orderNum", param.getOrderId());
                    params.put("pageNo", 1);
                    params.put("pageSize", Integer.MAX_VALUE);
                    return homeCardWcmProxy.queryDraftList(params);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_SOLUTION_DRAFT_INFO.name();
                }
            });

            //查询可选方案套数
            queryTasks.add(new IdentityTaskAction<Object>() {

                @Override
                public Object doInAction() {
                    return productProgramProxy.queryAvailableSolutionCount(param.getHouseProjectId(), param.getHouseTypeId(), param.getOrderId().longValue());
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_AVAILABLE_SOLUTION_COUNT.name();
                }
            });
        }

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private Map<String, Object> concurrentQueryConfirmSolutionInfo(Integer orderId, int status) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(status == 0 ? 3 : 1);

        //查询已选方案信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });

        if (status == 0) {
            //查询大订单基础信息
            queryTasks.add(new IdentityTaskAction<Object>() {

                @Override
                public Object doInAction() {
                    return productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_MASTER_ORDER_BASE_INFO.name();
                }
            });
            //查询交付信息
            queryTasks.add(new IdentityTaskAction<Object>() {

                @Override
                public Object doInAction() {
                    return dmsProxy.getSimpleDeliveryInfo(orderId);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
                }
            });
        }

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private Map<String, Object> concurrentQueryMaintenanceAndDeliveryInfo(Integer orderId, Integer userId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);

        //查询维保次数信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return maintainProxy.queryList(userId, null, orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MAINTENANCE_INFO.name();
            }
        });
        //查询交付信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return dmsProxy.getSimpleDeliveryInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
            }
        });
        //查询点评信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return commentService.getComment(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_COMMENT_INFO.name();
            }
        });
        //查询节点状态信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return deliveryProxy.queryDeliverOrderInfoListByOrderIdList(Lists.newArrayList(orderId));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DELIVER_ORDERINFO_LIST_BY_ORDER_ID_LIST.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

}
