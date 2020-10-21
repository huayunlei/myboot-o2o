package com.ihomefnt.o2o.service.service.home;

import com.google.gson.JsonObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetSurveyorProjectNodeDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.OwnerParamDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingSchemeRecord;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.UserHousePropertiesResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.RecommendBoardResponse;
import com.ihomefnt.o2o.intf.domain.homepage.dto.ConventionalActionsVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.UserDetailStatus;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.HomePageRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.HomePageDataVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SelectSolutionInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionOrderInfo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.AllProductOrderResponse;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.hbms.OwnerProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * App4.0 首页接口业务
 *
 * @author jiangjun
 * @version 2.0, 2018-04-12 下午2:56
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class HomePageService {

    private final int STATE_TOUCH = 1;//接触中
    private final int STATE_TREND = 2;//意向
    private final int STATE_PAYMENT = 3;//定金
    private final int STATE_SIGN = 4;//签约
    private final int STATE_PAY = 5;//交付
    private final int STATE_SALE = 6;//已完成

    private final int HARD_STATUS_FINISH = 5;


    public static final int ORDER_TYPE_TZ_SOFT = 12;//套装、纯软
    public static final int ORDER_TYPE_ZY_SOFT = 14;//自由搭配、纯软
    public static final int ORDER_TYPE_TZ_ALL = 11;//套装、软+硬
    public static final int ORDER_TYPE_ZY_ALL = 13;//自由搭配、纯+硬


    private static final int ORDERSALE_TYPE_SOFT = 1;//订单类型 纯软
    private static final int ORDERSALE_TYPE_ALL = 0;//订单类型 软加硬

    private final int SOURCE_VALETORDER = 6;//6 表示 来自 代客下单


    private static int CONVENTIONAL_ACTION_GROUPID = 100;
    private static String CONVENTIONAL_ACTION_QPJ = "rn://sale_service/AboutPage";
    //chatSkillGroup:0  在线客服 （本次使⽤用） 1：艾商城客服
    private static String CONVENTIONAL_ACTION_ONLINESERVICE = "native://customer_service?chatSkillGroup=0";
    private static String CONVENTIONAL_ACTION_PAY = "rn://dna_case/OptPayOrderPage?jsonObj={0}";
    private static String CONVENTIONAL_ACTION_LOAN = "rn://dna_case/LoanPage?orderId={0}&adviserMobileNum={1}";
    private static String CONVENTIONAL_ACTION_ORDERDETAIL = "rn://order/WholeProjectOrderPage?from=server&orderId={0}";
    private static String CONVENTIONAL_ACTION_ORDERDETAIL_VALET = "rn://order/ValetWholeProjectOrderPage?orderId={0}";
    private static String CONVENTIONAL_ACTION_CONTRACT = "rn://sale_service/EContractPage?hasMenu=true&orderId={0}";
    private static String CONVENTIONAL_ACTION_FINISH_HOUSEINFO = "rn://house/SubmitApartmentPage?from=pay";

    private static String CONVENTIONAL_ACTION_NEW_LOAN = "rn://loan/AboutLoanPage?orderId={0}&adviserMobileNum={1}&fromNative=true";

    private static String CONVENTIONAL_ACTION_SHARE_ICON = "https://static.ihomefnt.com/common-system/staticImage/fenxiang2x_n.png";
    private static String CONVENTIONAL_ACTION_QPJ_ICON = "https://static.ihomefnt.com/common-system/staticImage/aijia2x_n.png";
    private static String CONVENTIONAL_ACTION_LOAN_ICON = "https://static.ihomefnt.com/common-system/staticImage/daikuan2x_n.png";
    private static String CONVENTIONAL_ACTION_ORDERDETAIL_ICON = "https://static.ihomefnt.com/common-system/staticImage/dindan2x_n.png";
    private static String CONVENTIONAL_ACTION_PAY_ICON = "https://static.ihomefnt.com/common-system/staticImage/pay2x_n.png";
    private static String CONVENTIONAL_ACTION_PAY_ICON_WITH_LOAN = "https://static.ihomefnt.com/common-system/staticImage/pay2x_with_loan.png";
    private static String CONVENTIONAL_ACTION_CONTRACT_ICON = "https://static.ihomefnt.com/common-system/staticImage/hetong2x_n.png";
    private static String CONVENTIONAL_ACTION_ONLINESERVICE_ICON = "https://static.ihomefnt.com/common-system/staticImage/onlineservice2x_n.png";

    private final Integer RECOMMEND_BOARD_TYPE_DNA = 1;//首页卡片类型为DNA

    private final String DEFAULT_GREET = "欢迎回来";

    private final String HARD_FINISH = "已完成";

    private final String VALETORDER_DEFAULT_IMG = "https://static.ihomefnt.com/common-system/staticImage/dkxd3x.png";//代课下单默认示意图

    @Autowired
    ProductProgramOrderService orderService;

    @Autowired
    HomeCardService homeCardService;

    @Autowired
    UserProxy userProxy;

    @Autowired
    HomeBuildingService homeBuildingService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CommentService commentService;

    @Autowired
    OwnerProxy ownerProxy;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
	private HomeCardWcmProxy homeCardWcmProxy;
	
    public HomePageDataVo getHomePageData(HomePageRequest request) {

        HttpUserInfoRequest userDto = request.getUserInfo();

        String mobileNum = request.getMobileNum();
        String orderIdStr = request.getOrderId();

        //首页卡片信息
        List<RecommendBoardResponse> recommendBoards = homeCardService.getRecommendBoard(request, userDto);

        //需求 要求 暂时只展示DNA 屏蔽其他类型卡片
        if (!CollectionUtils.isEmpty(recommendBoards)) {
            recommendBoards = recommendBoards.stream().filter(
                    r -> (r.getType() != null && RECOMMEND_BOARD_TYPE_DNA.equals(r.getType()))
            ).collect(Collectors.toList());
        }

        //基本操作按钮组
        List<ConventionalActionsVo> conventionalActionsVos = initConventionalActionsVo(orderIdStr, mobileNum, request);

        //全国服务订单数信息
        BuildingSchemeRecord bindingSchemeRecord = homeBuildingService.buildingSchemeRecord(null);

        HomePageDataVo homePageData = new HomePageDataVo()
                .setRecommendBoards(recommendBoards)
                .setConventionalActionsList(conventionalActionsVos)
                .setBuildingSchemeRecord(bindingSchemeRecord)
                .setAppVersion(request.getAppVersion());

        if (StringUtils.isNotBlank(orderIdStr)) {
            homePageDataWithOrder(homePageData, Integer.parseInt(orderIdStr), request.getWidth());

            //是否提交过个性化需求
            Map<String, Object> params = new HashMap<>();
            params.put("orderNum", Integer.parseInt(orderIdStr));
            params.put("mobile", request.getMobileNum());
            AppSolutionDesignResponseVo designResponseVo = personalNeedProxy.queryDesignDemond(params);
            if (designResponseVo != null && StringUtils.isNotBlank(designResponseVo.getUserTags())) {
                homePageData.setPersonalDemandFlag(true);
            }
        }

        return homePageData;
    }

    /**
     * 有订单用户细分状态
     *
     * @return
     */
    public void homePageDataWithOrder(HomePageDataVo homePageData, Integer orderId, Integer width) {

        AllProductOrderResponse order = orderService.queryAllProductOrderDetailById(orderId, width, "homecard");

        if (order == null) {
            return;
        }

        Integer state = order.getState();

        String jsonObj = setPayParam(homePageData, order);

        if (VersionUtil.mustUpdate(homePageData.getAppVersion(), "4.0.6") || !order.getState().equals(STATE_SIGN)) {
            homePageData.getConventionalActionsList().get(2).setIcon(CONVENTIONAL_ACTION_PAY_ICON)
                    .setSrc(CONVENTIONAL_ACTION_PAY.replace("{0}", jsonObj) + "&fromNative=true").setTitle("付款");
        } else {
            homePageData.getConventionalActionsList().get(2).setIcon(CONVENTIONAL_ACTION_PAY_ICON_WITH_LOAN)
                    .setSrc(CONVENTIONAL_ACTION_PAY.replace("{0}", jsonObj) + "&fromNative=true").setTitle("付款");
        }

        switch (state) {
            case STATE_TOUCH:
                stateTouch(homePageData, order, width);
                break;
            case STATE_TREND:
                stateTrend(homePageData, order, width);
                break;
            case STATE_PAYMENT:
                statePayment(homePageData, order, width);
                break;
            case STATE_SIGN:
                stateSign(homePageData, order, width);
                break;
            case STATE_PAY:
                statePay(homePageData, order, width);
                break;
            case STATE_SALE:
                stateSale(homePageData, order);
                break;
        }

        homePageData.setExceptTime(order.getExceptTime());

        homePageData.setState(state);

        setGreet(homePageData, order);

        setActionLoan(homePageData, order);
    }

    public void setActionLoan(HomePageDataVo homePageData, AllProductOrderResponse order) {
        List<ConventionalActionsVo> list = homePageData.getConventionalActionsList();

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(c -> {
                if (c.getTitle().equals("装修贷款") && null != order) {
                    if (VersionUtil.mustUpdate(homePageData.getAppVersion(), "4.0.6")) {
                        c.setSrc(c.getSrc() + "&state=" + order.getState());
                    } else {
                        c.setSrc(c.getSrc() + "&state=" + order.getState() + "&loanAmount=" + order.getUnpaidMoney());
                    }
                }
            });
        }
    }

    /**
     * 设置默认欢迎图
     *
     * @param homePageData
     * @param order
     */
    public void setGreet(HomePageDataVo homePageData, AllProductOrderResponse order) {
        if (null != homePageData && null != order) {
            if (order.getUserInfo() != null) {
                String username = order.getUserInfo().getUserName();

                homePageData.setGreet(DEFAULT_GREET);

                if (StringUtils.isNotBlank(username)) {
                    homePageData.setGreet(username + ", " + DEFAULT_GREET);
                }

                setAdviserMobileNum(homePageData, order);
            }
        }
    }

    public void setAdviserMobileNum(HomePageDataVo homePageData, AllProductOrderResponse order) {
        String adviserMobileNum = (order != null && order.getUserInfo() != null) ?
                order.getUserInfo().getAdviserMobileNum() : "";
        if (homePageData != null) {
            homePageData.setAdviserMobileNum(adviserMobileNum);
        }
    }


    /**
     * 接触中用户
     */
    public void stateTouch(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        touchAndTrendAndPaymentAndSign(homePageData, order, width);
    }

    /**
     * 意向用户
     *
     * @param homePageData
     */
    public void stateTrend(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        TrendAndPaymentAndSign(homePageData, order, width);
    }

    /**
     * 定金用户
     *
     * @param homePageData
     * @param order
     */
    public void statePayment(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        TrendAndPaymentAndSign(homePageData, order, width);
    }

    /**
     * 签约用户
     *
     * @param homePageData
     * @param order
     */
    public void stateSign(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        TrendAndPaymentAndSign(homePageData, order, width);

        signAndPay(homePageData, order);
    }

    /**
     * 交付
     *
     * @param homePageData
     * @param order
     */
    public void statePay(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        TrendAndPaymentAndSign(homePageData, order, width);

        signAndPay(homePageData, order);
    }

    /**
     * 已完成
     *
     * @param homePageData
     * @param order
     */
    public void stateSale(HomePageDataVo homePageData, AllProductOrderResponse order) {

        Integer source = order.getSource();

        if (null != source && source == SOURCE_VALETORDER) {
            //代课下单状态下 完成阶段展示默认图 用 代客下单的默认示意图
            homePageData.setInformServiceInfo(VALETORDER_DEFAULT_IMG);
        }

        String orderDetailSrc = CONVENTIONAL_ACTION_ORDERDETAIL.replace("{0}", order.getOrderId() + "");
        if (order.getSource() != null && order.getSource() == SOURCE_VALETORDER) {
            orderDetailSrc = CONVENTIONAL_ACTION_ORDERDETAIL_VALET.replace("{0}", order.getOrderId() + "");
        }

        homePageData.getConventionalActionsList().get(0)
                .setIcon(CONVENTIONAL_ACTION_ORDERDETAIL_ICON)
                .setTitle("我的订单")
                .setSrc(orderDetailSrc);

        homePageData.getConventionalActionsList().get(2)
                .setIcon(CONVENTIONAL_ACTION_SHARE_ICON)
                .setTitle("分享艾佳")
                .setSrc(CONVENTIONAL_ACTION_QPJ);

        homePageData.getConventionalActionsList().get(3)
                .setIcon(CONVENTIONAL_ACTION_CONTRACT_ICON)
                .setTitle("电子合同")
                .setSrc(CONVENTIONAL_ACTION_CONTRACT.replace("{0}", order.getOrderId() + ""));

        //交付数据
        HardScheduleVo hardSchedule = scheduleService.getSchedule(order.getOrderId(), order.getOrderType());

        //如果已完成时间没有 则取大订单完成时间
        if (hardSchedule != null && StringUtils.isEmpty(hardSchedule.getActualEndDate())) {
            hardSchedule.setActualEndDate(order.getCompleteTime());
        }

        formatDateForHardSchedule(hardSchedule);

        homePageData.setHardSchedule(hardSchedule);

        SolutionOrderInfo solutionOrderInfo = order.getSolutionOrderInfo();

        if (null != solutionOrderInfo) {
            String headImg = solutionOrderInfo.getHeadImgUrl();

            //维修知会信息
            homePageData.setInformServiceInfo(headImg);
        }

        homePageData.setAlreadyComment(commentService.isAlreadyComment(order.getOrderId()));

        UserDetailStatus detailStatus = new UserDetailStatus();

        UserInfoResponse userInfo = order.getUserInfo();

        //设置楼盘和户型信息
        setHouseInfo(homePageData, userInfo, detailStatus);

        homePageData.setUserDetailStatus(detailStatus);

        setOrderSaleType(order, detailStatus);
    }

    /**
     * 初始化 默认的首页基本操作按钮
     *
     * @return
     */
    public List<ConventionalActionsVo> initConventionalActionsVo(String orderId, String mobileNum, HomePageRequest request) {
//        initDic();

        List<ConventionalActionsVo> conventionalActionsList = new ArrayList<>();

        conventionalActionsList.add(new ConventionalActionsVo()
                .setIcon(CONVENTIONAL_ACTION_QPJ_ICON)
                .setSrc(CONVENTIONAL_ACTION_QPJ)
                .setTitle("全品家"));

        conventionalActionsList.add(new ConventionalActionsVo()
                .setIcon(CONVENTIONAL_ACTION_ONLINESERVICE_ICON)
                .setSrc(CONVENTIONAL_ACTION_ONLINESERVICE)
                .setTitle("在线客服")
                .setNeedLogin(true));

        conventionalActionsList.add(new ConventionalActionsVo()
                .setIcon(CONVENTIONAL_ACTION_PAY_ICON)
                .setSrc(CONVENTIONAL_ACTION_FINISH_HOUSEINFO)
                .setTitle("付款")
                .setNeedLogin(true));

        ConventionalActionsVo loanAction = new ConventionalActionsVo()
                .setIcon(CONVENTIONAL_ACTION_LOAN_ICON)
                .setTitle("装修贷款");

        if (VersionUtil.mustUpdate(request.getAppVersion(), "4.0.6")) {
            loanAction = loanAction.setSrc(CONVENTIONAL_ACTION_LOAN.replace("{0}", orderId).replace("{1}",
                    StringUtils.isBlank(mobileNum) ? "" : mobileNum));
        } else {
            loanAction = loanAction.setSrc(CONVENTIONAL_ACTION_NEW_LOAN.replace("{0}", orderId).replace("{1}",
                    StringUtils.isBlank(mobileNum) ? "" : mobileNum));
        }
        conventionalActionsList.add(loanAction);

        return conventionalActionsList;
    }

    @Autowired
    ProductProgramService productProgramService;

    /**
     * 接触 意向 定金 签约 用户通用处理
     *
     * @param homePageData
     * @param order
     */
    public void touchAndTrendAndPaymentAndSign(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        SelectSolutionInfo selectSolutionInfo = order.getSelectSolutionInfo();

        homePageData.setSelectSolutionInfo(selectSolutionInfo);

        UserDetailStatus detailStatus = new UserDetailStatus();

        UserInfoResponse userInfo = order.getUserInfo();

        //设置楼盘和户型信息
        setHouseInfo(homePageData, userInfo, detailStatus);

        //可选方案套数
        if (null != selectSolutionInfo) {
            Integer selectSolutionNum = selectSolutionInfo.getSelectSolutionNum();

            detailStatus.setSolutionBaseNum(selectSolutionNum);
            
            //小艾课堂视频
            homePageData.setVideoList(getVideoList(width));
        }

        //硬装 详细状态
        if (order.getHardConstructInfo() != null) {
            detailStatus.setHardOrderStatus(order.getHardConstructInfo().getHardOrderStatus());
        }

        //软装详细状态
        if (order.getSoftDeliveryInfo() != null) {
            detailStatus.setSoftOrderStatus(order.getSoftDeliveryInfo().getSoftOrderStatus());
        }

        // 订单来源
        detailStatus.setSource(order.getSource());

        //设置交房时间
        setDeliverTime(order, detailStatus);

        Integer buildingId = userInfo == null ? null : userInfo.getBuildingId();

        //该小区订单数
        BuildingSchemeRecord buildingSchemeRecord = homeBuildingService.buildingSchemeRecord(buildingId);

        if (buildingSchemeRecord != null && buildingSchemeRecord.getOrderedCount() != null
                && buildingSchemeRecord.getOrderedCount() >= 10) {
            homePageData.setBuildingSchemeRecord(buildingSchemeRecord);
        }

        setOrderSaleType(order, detailStatus);

        detailStatus.setCheckResult(order.isConfirmationFlag());

        homePageData.setUserDetailStatus(detailStatus);

        if (userInfo != null) {
            //判断用户是否是特定用户
            UserHousePropertiesResponseVo userHousePropertiesResponse = productProgramService
                    .getUserSpecificProgram(userInfo.getUserId(), userInfo.getHouseId());

            if (userHousePropertiesResponse != null) {
                detailStatus.setSpecific(userHousePropertiesResponse.getSpecific());
            }
        }
    }

    public void setOrderSaleType(AllProductOrderResponse order, UserDetailStatus detailStatus) {
        Integer orderType = order.getOrderType();

        Integer orderSaleType = ORDERSALE_TYPE_ALL;

        if (orderType != null && (orderType == ORDER_TYPE_TZ_SOFT || orderType == ORDER_TYPE_ZY_SOFT)) {
            orderSaleType = ORDERSALE_TYPE_SOFT;
        }

        detailStatus.setOrderSaleType(orderSaleType);
    }

    public void setDeliverTime(AllProductOrderResponse order, UserDetailStatus detailStatus) {
        if (StringUtils.isNotBlank(order.getDeliverTime())) {
            try {
                String deliverTime = DateUtil.formatDate(
                        DateUtil.parseDate(order.getDeliverTime(), Arrays.asList("yyyy-MM-dd HH:mm:ss")), "yyyy.MM.dd");
                detailStatus.setDeliverTime(deliverTime);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 设置楼盘和户型信息
     *
     * @param homePageData
     * @param userInfo
     */
    public void setHouseInfo(HomePageDataVo homePageData, UserInfoResponse userInfo, UserDetailStatus detailStatus) {
        String buildingName = userInfo.getBuildingName();

        String houseName = userInfo.getHouseName();

        Integer buildingId = userInfo.getBuildingId();

        boolean building = StringUtils.isEmpty(buildingName) ? false : true;

        boolean house = StringUtils.isEmpty(houseName) ? false : true;

        //拼接 楼盘和户型信息
        if (building && house) {
            String houseArea = "";

            if (StringUtils.isNotBlank(userInfo.getHouseArea())) {
                userInfo.setHouseArea(userInfo.getHouseArea().replace("平米", ""));
            }
            if (userInfo.getHouseArea() != null && !userInfo.getHouseArea().trim().equals("0")) {
                houseArea = " " + userInfo.getHouseArea() + "m²";
            }

            String houseInfo = buildingName + " | " + houseName + houseArea;

            homePageData.setHouseInfo(houseInfo);
        }

        if (StringUtils.isBlank(houseName) && StringUtils.isNotBlank(buildingName)) {
            homePageData.setHouseInfo(buildingName);
        }

        detailStatus.setBuildingId(buildingId)
                .setHouseTypeId(userInfo.getHouseTypeId())
                .setBuildingName(buildingName)
                .setHouseName(houseName);
    }

    /**
     * 意向 定金 签约 用户通用处理
     *
     * @param homePageData
     * @param order
     */
    public void TrendAndPaymentAndSign(HomePageDataVo homePageData, AllProductOrderResponse order, Integer width){
        String orderDetailSrc = CONVENTIONAL_ACTION_ORDERDETAIL.replace("{0}", order.getOrderId() + "");
        if (order.getSource() != null && order.getSource() == SOURCE_VALETORDER) {
            orderDetailSrc = CONVENTIONAL_ACTION_ORDERDETAIL_VALET.replace("{0}", order.getOrderId() + "");
        }

        homePageData.getConventionalActionsList().get(0)
                .setTitle("我的订单")
                .setIcon(CONVENTIONAL_ACTION_ORDERDETAIL_ICON)
                .setSrc(orderDetailSrc);

        touchAndTrendAndPaymentAndSign(homePageData, order, width);
    }

    /**
     * 签约 交付 用户通用处理
     *
     * @param homePageData
     * @param order
     */
    public void signAndPay(HomePageDataVo homePageData, AllProductOrderResponse order) {
        Integer source = order.getSource();

        if (null != source && source == SOURCE_VALETORDER) {
            //代课下单状态下 固定图
            homePageData.setValetOrdersDefaultImg(VALETORDER_DEFAULT_IMG);
        }

        //施工阶段: 开工交底 水电验收 瓦木验收 竣工验收
        HardScheduleVo hardSchedule = scheduleService.getSchedule(order.getOrderId(), order.getOrderType());

        formatDateForHardSchedule(hardSchedule);

        //判断是否可以开始验收
        setAcceptance(homePageData, order);

        if (hardSchedule != null) {
            Integer hardStatus = homePageData.getUserDetailStatus().getHardOrderStatus();
            if (hardStatus != null && hardStatus == HARD_STATUS_FINISH &&
                    (order.getOrderType() == ORDER_TYPE_TZ_ALL || order.getOrderType() == ORDER_TYPE_ZY_ALL)) {
                hardSchedule.setHard(HARD_FINISH);
            }
            homePageData.setHardSchedule(hardSchedule);

            homePageData.getUserDetailStatus().setHardSchedule(hardSchedule.getHard());

            homePageData.getUserDetailStatus().setSoftSchedule(hardSchedule.getSoft());
        }

        //已选方案信息
        homePageData.setSolutionOrderInfo(order.getSolutionOrderInfo());

        homePageData.getConventionalActionsList().get(3)
                .setTitle("电子合同")
                .setIcon(CONVENTIONAL_ACTION_CONTRACT_ICON)
                .setSrc(CONVENTIONAL_ACTION_CONTRACT.replace("{0}", order.getOrderId() + ""));
    }

    /**
     * 设置验收状态(是否可以开始验收)
     *
     * @param order
     */
    public void setAcceptance(HomePageDataVo homePageData, AllProductOrderResponse order) {
        OwnerParamDto ownerParamRequest = new OwnerParamDto();

        ownerParamRequest.setOrderId(order.getOrderId() + "");

        List<GetSurveyorProjectNodeDto> getSurveyorProjectNode = ownerProxy.getProjectCraft(ownerParamRequest);

        if (CollectionUtils.isEmpty(getSurveyorProjectNode)) {
            return;
        }

        for (GetSurveyorProjectNodeDto n : getSurveyorProjectNode) {
            String conform = n.getConfirm();

            Integer status = n.getStatus();

            //付款是否满足
            String payAlready = n.getPay();

            //未验收或者 验收不满意 就去验收
            if (StringUtils.isNotBlank(conform) && status != null
                    && (conform.equals("2") || conform.equals("0")) && status == 5 && payAlready.equals("1")) {
                homePageData.setCanBeAccept(true);
            }
        }
    }

    /**
     * 设置付款链接跳转所需的参数
     *
     * @param order
     * @return
     */
    public String setPayParam(HomePageDataVo homePageData, AllProductOrderResponse order) {

        JsonObject jo = new JsonObject();
        JsonObject paymentInfo = new JsonObject();
        JsonObject data = new JsonObject();

        String adviserMobileNum = order.getUserInfo() == null ? "" : order.getUserInfo().getAdviserMobileNum();

        paymentInfo.addProperty("unpaidMoney", order.getUnpaidMoney());

        paymentInfo.addProperty("paidMoney", order.getPaidMoney());

        paymentInfo.addProperty("totalPrice", order.getTotalPrice());

        data.addProperty("depositMoneyDefalut", order.getDepositMoneyDefalut());

        jo.add("paymentInfo", paymentInfo);

        jo.add("data", data);

        jo.addProperty("orderNum", order.getOrderNum());

        jo.addProperty("orderId", order.getOrderId());

        jo.addProperty("adviserMobileNum", adviserMobileNum);

        jo.addProperty("type", order.getOrderType());

        jo.addProperty("orderState", order.getState());

        homePageData.setPayParam(jo.toString());

        return jo.toString();
    }

    public void formatDateForHardSchedule(HardScheduleVo hardSchedule) {
        try {
            String actualBeginDate = hardSchedule.getActualBeginDate();
            String actualEndDate = hardSchedule.getActualEndDate();
            String expectSendDate = hardSchedule.getExpectSendDate();
            String houseDeliver = hardSchedule.getHouseDeliverDate();
            String planBeginDate = hardSchedule.getPlanBeginDate();


            if (StringUtils.isNotBlank(actualBeginDate)) {
                hardSchedule.setActualBeginDate(format(actualBeginDate));
            }
            if (StringUtils.isNotBlank(actualEndDate)) {
                hardSchedule.setActualEndDate(format(actualEndDate));
            }
            if (StringUtils.isNotBlank(expectSendDate)) {
                hardSchedule.setExpectSendDate(format(expectSendDate));
            }
            if (StringUtils.isNotBlank(houseDeliver)) {
                hardSchedule.setHouseDeliverDate(format(houseDeliver));
            }
            if (StringUtils.isNotBlank(planBeginDate)) {
                hardSchedule.setPlanBeginDate(format(planBeginDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String format(String str) throws Exception {
        return DateUtil.formatDate(
                DateUtil.parseDate(str, Arrays.asList("yyyy-MM-dd")), "yyyy.MM.dd");

    }
    
    public List<VideoEntity> getVideoList(Integer width){
    	if(width != null && width > 0){
    		width = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
    	}else{
    		width = 0;
    	}
    	List<VideoEntity> videoList = new ArrayList<VideoEntity>();
    	//小艾课堂视频
    	Integer videoTypeId= 0;
    	//查询视频分类
		VideoTypeListResponseVo videoTypeListResponse = homeCardWcmProxy.getVideoTypeList();
		if(videoTypeListResponse != null){
			List<VideoTypeEntity> videoTypeEntities = videoTypeListResponse.getTypeList();
			if(!CollectionUtils.isEmpty(videoTypeEntities)){
				for (VideoTypeEntity videoTypeEntity : videoTypeEntities) {
					//视频版块过滤小艾课堂
					if(StringUtils.isNotBlank(videoTypeEntity.getCode()) && videoTypeEntity.getCode().equals("小艾课堂")){
						videoTypeId = videoTypeEntity.getId();
					}
				}
			}
		}
		if(videoTypeId == 0){
			return videoList;
		}
		//根据视频类型查询所有视频列表
		VideoListResponseVo responseVo = homeCardWcmProxy.getVideoList(videoTypeId, null, null);
		if(responseVo != null){
			List<VideoResponseVo> videoListResponse = responseVo.getList();
			if(!CollectionUtils.isEmpty(videoListResponse)){
				for (VideoResponseVo videoResponseVo : videoListResponse) {
					VideoEntity videoEntity = new VideoEntity();
					videoEntity.setVideoId(videoResponseVo.getId().toString());
					videoEntity.setName(videoResponseVo.getTitle());
					//图片宽度100%  中部截取
					if (StringUtils.isNotBlank(videoResponseVo.getFrontImg())) {
						if(width != null){
							videoEntity.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(videoResponseVo.getFrontImg(),width, -1));
						}else{
							videoEntity.setHeadImgUrl(videoResponseVo.getFrontImg());
						}
					}else{
						videoEntity.setHeadImgUrl("");
					}
					videoEntity.setType(videoResponseVo.getType().toString());//视频类别
					videoEntity.setVideoUrl(videoResponseVo.getLink());
					videoEntity.setPraise(videoResponseVo.getIntroduction());
					videoList.add(videoEntity);
				}
			}
		}
    	return videoList;
    }
}
