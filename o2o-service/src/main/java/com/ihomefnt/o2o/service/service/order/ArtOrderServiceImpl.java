package com.ihomefnt.o2o.service.service.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoucherDto;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.art.vo.request.ArtworkOrderRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.request.DeliverOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.*;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HouseResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AllProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardConstructInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SoftDeliveryInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionOrderInfo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.AllProductOrderResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstants;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.right.RightLevelNewEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.order.ArtOrderUtil;
import com.ihomefnt.o2o.intf.proxy.activity.BoeActivityProxy;
import com.ihomefnt.o2o.intf.proxy.art.ArtProxy;
import com.ihomefnt.o2o.intf.proxy.home.HouseProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.artcomment.ArtCommentService;
import com.ihomefnt.o2o.intf.service.culture.CultureService;
import com.ihomefnt.o2o.intf.service.order.ArtOrderService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.ordersnapshot.OrderSnapshotService;
import com.ihomefnt.o2o.intf.service.pay.PayforService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.oms.trade.order.dto.solution.SolutionOrderDto;
import com.ihomefnt.oms.trade.order.enums.OrderState;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtOrderServiceImpl implements ArtOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ArtOrderService.class);

    @Autowired
    private HouseProxy houseProxy;
    @Autowired
    private UserProxy userProxy;
    @Autowired
    OrderProxy orderProxy;
    @Autowired
    OrderService orderService;
    @Autowired
    ArtCommentService artCommentService;
    @Autowired
    ProductProgramOrderService productProgramOrderService;
    @Autowired
    private PersonalCenterProxy personalCenterProxy;
    @Autowired
    ArtProxy artDao;
    @Autowired
    AreaService areaService;
    @Autowired
    OrderSnapshotService orderSnapshotService;
    @Autowired
    BoeActivityProxy boeActivityProxy;
    @Autowired
    private ArtService artService;
    @Autowired
    CultureService cultureService;
    @Autowired
    PayforService payforService;

    /**
     * 支付剩余时间
     */
    private final int WAIT_PAY_TIME = 1 * 60 * 60 * 1000;

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String artworkOrderRedisKey = "o2o:getArtworkOrderInfo:";


    @Override
    public HttpOrderResponse getSubOrderforAlipay(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        Long orderId = orderRequest.getOrderId();
        // 2016年8月1日13:56:26接入oms系统
        OrderDtoVo order = new OrderDtoVo();
        try {
            order = orderService.queryOrderDetail(Integer.parseInt(orderId.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断订单是否存在
        if (order == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.PRODUCT_ORDER_INVALID);
        }
        // 初始化支付参数
        PayInput payInput = new PayInput();
        payInput.setOrderNum(order.getOrderNum());
        if (!order.getOrderType().equals(5)) {
            payInput.setMoney(orderRequest.getSelectSum());
        } else {
            payInput.setMoney(order.getActualPayMent().doubleValue());
        }
        payInput.setActualPayMent(order.getActualPayMent());
        payInput.setTotalPayMent(order.getTotalPrice());
        // 设置支付异步回调
        payInput.setNotifyUrl(orderService.getSubAlipayNotifyUrl290());
        // 订单来源
        payInput.setSource(2);
        payInput.setReturnUrl("m.alipay.com");
        // 请求支付
        payInput.setUserId(userDto.getId());
        payInput.setOrderType(order.getOrderType());

        payInput.setPayStage(getPayStage(order.getOrderNum()));

        AppAlipayOutput data = orderProxy.appAlipay(payInput);

        if (null == data) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.ORDER_PAY_FAILED);
        }

        HttpOrderResponse orderResponse = new HttpOrderResponse();
        orderResponse.setOrderNum(order.getOrderNum());
        orderResponse.setOrderInfo(data.getOrderPayInfo());
        orderResponse.setPrivateKey(data.getPrivateKey());
        return orderResponse;
    }

    private Integer getPayStage(String orderNum) {
        OrderDto orderDto = orderProxy.queryOrderByOrderNum(orderNum);
        if (null != orderDto && (orderDto.getOrderType().equals(OrderConstants.ORDER_SOLUTION)
                || orderDto.getOrderType().equals(OrderConstants.ORDER_SOLUTION_FREEDOM))) {
            SolutionOrderDto solutionOrderDto = orderProxy.querySolutionOrderById(orderDto.getOrderId());
            // 对于线上支付来说，如果此时是定金，返回支付阶段为首付款
            if (solutionOrderDto.getPayStage() == 2) {
                return 3;
            }
            if (solutionOrderDto.getNextOrderPayStage() != null
                    && solutionOrderDto.getNextOrderPayStage().getPayStage() != null) {
                return solutionOrderDto.getNextOrderPayStage().getPayStage();
            }
            return solutionOrderDto.getPayStage();
        }
        return null;
    }

    @Override
    public HttpOrderResponse queryOrderPay(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        OrderDtoVo order = new OrderDtoVo();
        try {
            LOG.info("queryOrderPay queryOrderDetail param:" + JsonUtils.obj2json(orderRequest));
            order = orderService.queryOrderDetail(Integer.parseInt(orderRequest.getOrderId().toString()));
            LOG.info("queryOrderPay queryOrderDetail response:" + JsonUtils.obj2json(order));
        } catch (Exception e) {
            LOG.error("queryOrderPay o2o-exception , more info :{}", e.getMessage());
            e.printStackTrace();
        }

        if (order == null) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.PRODUCT_ORDER_INVALID);
        }

        HttpOrderResponse orderResponse = new HttpOrderResponse();
        //防止已完成订单重复支付
        if (order.getState() != null && (order.getState() == 2 || order.getState() == 5 || order.getState() == 10)) {
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.ART_ORDER_END);
        }
        orderResponse.setOrderNum(order.getOrderNum());
        BigDecimal orderPrice = order.getActualPayMent();
        orderResponse.setOrderPrice(orderPrice.doubleValue());
        List<OrderAttrValueDtoVo> orderAttrValueOutputList = order.getOrderAttrValueOutputList();
        if (null != orderAttrValueOutputList && orderAttrValueOutputList.size() > 0) {
            double couponPay = 0;
            double ajbMouey = 0;
            for (OrderAttrValueDtoVo orderAttrValueDto : orderAttrValueOutputList) {
                if (orderAttrValueDto.getAttrName().equals("cashCoupon")) {
                    couponPay += Double.parseDouble(orderAttrValueDto.getAttrValue());
                } else if (orderAttrValueDto.getAttrName().equals("ajbMoney")) {
                    String value = orderAttrValueDto.getAttrValue();
                    LOG.info("ajbMoney:" + value);
                    if (StringUtils.isNotBlank(value)) {
                        ajbMouey += Double.parseDouble(value);
                    }

                }
            }
            orderResponse.setAjbMoney(ajbMouey);
            orderResponse.setCouponPay(couponPay);
        }

        return orderResponse;
    }

    @Override
    public HttpSubOrderPayResponse createSubOrderPay(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        OrderDtoVo order = new OrderDtoVo();
        try {
            order = orderService.queryOrderDetail(Integer.parseInt(orderRequest.getOrderId().toString()));
        } catch (NumberFormatException e) {
            LOG.error("createSubOrderPay o2o-exception , more info :", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LOG.error("createSubOrderPay o2o-exception , more info :", e.getMessage());
            e.printStackTrace();
        }
        if (order == null) {
            throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_ORDER_INVALID);
        }

        HttpSubOrderPayResponse orderResponse = new HttpSubOrderPayResponse();
        orderResponse.setOrderNum(order.getOrderNum());
        orderResponse.setOrderPrice(order.getActualPayMent().doubleValue());

        OrderPayBalanceVo orderPayBalanceVo = orderService.getOrderPayBalance(order.getOrderNum());

        if (orderPayBalanceVo == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }

        BigDecimal payMoneyB = orderPayBalanceVo.getBalance();
        Double alreadyPay = order.getActualPayMent().subtract(payMoneyB).doubleValue();
        if (alreadyPay == null) {
            alreadyPay = 0d;
        }
        orderResponse.setAlreadyPay(alreadyPay);
        double remainpay = order.getActualPayMent().doubleValue() - alreadyPay;
        long l1 = Math.round(remainpay * 100);
        double ret = l1 / 100.0;

        orderResponse.setRemainPay(ret);
        List<Double> selectSum = orderService.querySelSum(10l);
        List<Double> allSum = new ArrayList<Double>();
        allSum.add(orderResponse.getRemainPay());
        if (null != selectSum) {
            for (Double d : selectSum) {
                if (d < orderResponse.getRemainPay()) {
                    allSum.add(d);
                }
            }
        }
        orderResponse.setSelectSum(allSum);

        return orderResponse;
    }

    @Override
    public HttpSubOrderPayInfoResponse querySubOrderPay(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        OrderDtoVo queryOrderById = orderService.queryOrderDetail(Integer.parseInt(orderRequest.getOrderId().toString()));

        List<PayFinishRecordVo> queryPayFinishedRecordList = orderProxy.queryPayFinishedRecordList(queryOrderById.getOrderNum());

        if (CollectionUtils.isEmpty(queryPayFinishedRecordList)) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }

        HttpSubOrderPayInfoResponse response = new HttpSubOrderPayInfoResponse();
        List<OrderPayRecord> orderPayRecordList = new ArrayList<OrderPayRecord>();
        if (null != queryPayFinishedRecordList && queryPayFinishedRecordList.size() > 0) {
            for (PayFinishRecordVo payFinishedRecord : queryPayFinishedRecordList) {
                OrderPayRecord orderPayRecord = new OrderPayRecord();
                orderPayRecord.setOrderId(Long.parseLong(payFinishedRecord.getId()));
                orderPayRecord.setSubOrderNum(payFinishedRecord.getOrderNum());
                orderPayRecord.setPayType(payFinishedRecord.getPayType());
                orderPayRecord.setPayMoney(payFinishedRecord.getMoney().doubleValue());
                orderPayRecord.setCreateTime(payFinishedRecord.getCreateTime().toString());
            }
        }

        response.setOrderPayRecord(orderPayRecordList);
        response.setTotalRecords(orderPayRecordList.size());
        return response;
    }

    /**
     * 订单列表查询
     *
     * @param orderRequest
     * @return
     */
    @Override
    public PageModel orderList295(HttpOrderListRequest orderRequest) {
        if (null == orderRequest) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        PageModel pageModel = getOrderListLatest(userDto.getId(), orderRequest.getAppVersion());
        return pageModel;
    }


    /**
     * 查询全品家+艺术品订单
     *
     * @param userId
     * @return
     */
    private PageModel getOrderListLatest(Integer userId, String appVersion) {
        Map<String, Object> stringObjectMap = concurrentOrderQueryList(userId, appVersion);
        //全品家订单
        List<OrderResponse> masterOrderList = (List<OrderResponse>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_LIST.name());
        //艺术品订单
        List<OrderResponse> artOrderList = (List<OrderResponse>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_OMS_ORDER_INFO.name());
        List<OrderResponse> orList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(masterOrderList)) {
            orList.addAll(masterOrderList);
        }
        if (CollectionUtils.isNotEmpty(artOrderList)) {
            orList.addAll(artOrderList);
        }
        if (CollectionUtils.isEmpty(orList)) {
            return new PageModel();
        }
        return new PageModel(sortOrderList(orList), orList.size(), orList.size(), 1, 1);
    }


    /**
     * 查询大订单+艺术品订单信息
     *
     * @param userId
     * @return
     */
    private Map<String, Object> concurrentOrderQueryList(Integer userId, String appVersion) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //全品家订单列表
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryMasterOrderList(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MASTER_ORDER_LIST.name();
            }
        });

        //艺术品订单列表
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryArtOrderList(userId, appVersion);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_OMS_ORDER_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 查询全品家订单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderResponse> queryMasterOrderList(Integer userId) {
        Map<String, Object> stringObjectMap = concurrentMasterOrderQueryList(userId);
        //根据用户id查全品家订单列表
        List<AppMasterOrderResultDto> masterOrderList = (List<AppMasterOrderResultDto>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_LIST_BY_ID.name());
        //根据用户id查户型
        List<HouseResponse> houseResponseList = (List<HouseResponse>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_USER_HOUSE_LIST.name());

        if (CollectionUtils.isEmpty(masterOrderList)) {
            return new ArrayList<>();
        }
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (AppMasterOrderResultDto item : masterOrderList) {
            OrderDtoVo orderResultDto = new OrderDtoVo();
            if (null != item.getBuildingId()) {
                orderResultDto.setProjectId(item.getBuildingId().intValue());
                orderResultDto.setFidProject(item.getBuildingId().intValue());
            }
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(item.getMasterOrderId());
            orderResponse.setOrderNum(item.getMasterOrderId().toString());
            orderResponse.setOrderType(OrderConstant.ORDER_TYPE_ALADDIN);
            orderResponse.setState(item.getOrderStatus());
            orderResponse.setStateDesc(item.getOrderStatusName());
            AllProductOrderDetail allProductOrder = new AllProductOrderDetail();
            if (item.getGradeId() != null) {
                allProductOrder.setGradeId(item.getGradeId());
                allProductOrder.setGradeName(RightLevelNewEnum.getName(item.getGradeId()));
            }
            allProductOrder.setRightsVersion(item.getRightsVersion());
            if(item.getRightsVersion()!=null && item.getRightsVersion()==4){//新版权益设置权益id-1
                allProductOrder.setGradeId(-1);
            }
            if (CollectionUtils.isNotEmpty(houseResponseList)) {
                for (HouseResponse houseResponse : houseResponseList) {
                    if(houseResponse.getCustomerHouseId().equals(item.getCustomerHouseId())){
                        HouseResponse houseInfo = new HouseResponse();
                        houseInfo.setHouseType(houseResponse.getHouseType());
                        if (StringUtils.isNotBlank(houseResponse.getBuildingInfo())) {
                            String buildingInfo = houseResponse.getBuildingInfo();
                            JSONObject jsonObject = JSON.parseObject(buildingInfo);
                            String type = jsonObject.getString("type");
                            if ("3".equals(type)) {
                                houseInfo.setBuildingAddress(jsonObject.getString("projectName"));
                                StringBuilder pattern = new StringBuilder("");
                                houseInfo.setPattern(pattern
                                        .append(jsonObject.getString("room") == null ? "" : jsonObject.getString("room") + "室")
                                        .append(jsonObject.getString("livingRoom") == null ? "" : jsonObject.getString("livingRoom") + "厅")
                                        .append(jsonObject.getString("kitchen") == null ? "" : jsonObject.getString("kitchen") + "厨")
                                        .append(jsonObject.getString("bathroom") == null ? "" : jsonObject.getString("bathroom") + "卫")
                                        .append(jsonObject.getString("balcony") == null ? "" : jsonObject.getString("balcony") + "阳台").toString());
                                StringBuilder roomAddress = new StringBuilder("");
                                houseInfo.setRoomAddress(roomAddress
                                        .append(jsonObject.getString("buildingNo") == null ? "" : jsonObject.getString("buildingNo"))
                                        .append(jsonObject.getString("unitNo") == null ? "" : "-" + jsonObject.getString("unitNo"))
                                        .append(jsonObject.getString("roomNo") == null ? "" : "-" + jsonObject.getString("roomNo"))
                                        .toString());
                                houseInfo.setArea(jsonObject.getString("") == null ? "" : jsonObject.getString("") + "㎡");
                                if(StringUtil.isBlank(houseInfo.getArea())){
                                    houseInfo.setArea(houseResponse.getArea());
                                }
                            }
                        } else {
                            houseInfo.setArea(houseResponse.getArea());
                            houseInfo.setBuildingAddress(houseResponse.getBuildingName());
                            houseInfo.setPattern(houseResponse.getPattern());
                            houseInfo.setRoomAddress(houseResponse.getRoomAddress());
                        }
                        allProductOrder.setHouseInfo(houseInfo);
                        orderResponse.setAllProductOrder(allProductOrder);
                    }
                }
                orderResponseList.add(orderResponse);
            }

        }
        return orderResponseList;
    }

    /**
     * 查询大订单+房产信息
     *
     * @param userId
     * @return
     */
    private Map<String, Object> concurrentMasterOrderQueryList(Integer userId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return personalCenterProxy.queryMasterOrderListByUserId(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_MASTER_ORDER_LIST_BY_ID.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return queryUserHouseList(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_USER_HOUSE_LIST.name();
            }
        });


        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 查询房产信息，用于全品家订单列表
     *
     * @param userId
     * @return
     */
    public List<HouseResponse> queryUserHouseList(Integer userId) {
        List<HouseResponse> responses = new ArrayList<>();
        if (userId != null && userId > 0) {
            // 根据用户id查询用户房产列表
            List<AppHousePropertyResultDto> houseResultDtoList = houseProxy.queryHouseByUserId(userId);
            if (CollectionUtils.isEmpty(houseResultDtoList)) {
                return new ArrayList<>();
            }
            for (AppHousePropertyResultDto item : houseResultDtoList) {
                HousePropertyInfoResultDto housePropertyInfoResultDto = item.getHousePropertyInfoResultDto();
                if (null == housePropertyInfoResultDto) {
                    continue;
                }
                HouseInfoResponseVo userHouseResultVo = ModelMapperUtil.strictMap(item, HouseInfoResponseVo.class);
                HouseResponse houseResponse = new HouseResponse();
                houseResponse.setHouseId(housePropertyInfoResultDto.getCustomerHouseId());
                houseResponse.setCustomerHouseId(housePropertyInfoResultDto.getCustomerHouseId());
                //格局
                StringBuffer housePattern = new StringBuffer("");
                if (housePropertyInfoResultDto.getLayoutRoom() != null && housePropertyInfoResultDto.getLayoutRoom() > 0) {
                    housePattern.append(housePropertyInfoResultDto.getLayoutRoom()).append(ProductProgramPraise.CHAMBER);
                }
                if (housePropertyInfoResultDto.getLayoutLiving() != null && housePropertyInfoResultDto.getLayoutLiving() > 0) {
                    housePattern.append(housePropertyInfoResultDto.getLayoutLiving()).append(ProductProgramPraise.HALL);
                }
                if (housePropertyInfoResultDto.getLayoutKitchen() != null && housePropertyInfoResultDto.getLayoutKitchen() > 0) {
                    housePattern.append(housePropertyInfoResultDto.getLayoutKitchen()).append(ProductProgramPraise.KITCHEN);
                }
                if (housePropertyInfoResultDto.getLayoutToilet() != null && housePropertyInfoResultDto.getLayoutToilet() > 0) {
                    housePattern.append(housePropertyInfoResultDto.getLayoutToilet()).append(ProductProgramPraise.TOILET);
                }
                if (housePropertyInfoResultDto.getLayoutBalcony() != null && housePropertyInfoResultDto.getLayoutBalcony() > 0) {
                    housePattern.append(housePropertyInfoResultDto.getLayoutBalcony()).append(ProductProgramPraise.BALCONY);
                }
                houseResponse.setPattern(housePattern.toString());

                userHouseResultVo.setBuildingInfo(housePropertyInfoResultDto.getBuildingInfo());
                userHouseResultVo.setHousingNum(housePropertyInfoResultDto.getHousingNum());
                userHouseResultVo.setPartitionName(housePropertyInfoResultDto.getPartitionName());
                userHouseResultVo.setLayoutCloak(housePropertyInfoResultDto.getLayoutCloak());
                userHouseResultVo.setLayoutStorage(housePropertyInfoResultDto.getLayoutStorage());
                userHouseResultVo.setUnitNum(housePropertyInfoResultDto.getUnitNum());
                userHouseResultVo.setSize(housePropertyInfoResultDto.getArea());
                if (null != housePropertyInfoResultDto.getDeliverTime()) {
                    userHouseResultVo.setDeliverTime(housePropertyInfoResultDto.getDeliverTime());
                }
                userHouseResultVo.setRoomNum(housePropertyInfoResultDto.getRoomNum());

                //toc老带新用户手填信息回显处理
                if (StringUtil.isNotBlank(userHouseResultVo.getBuildingInfo())) {//手填信息不为空
                    String buildingInfo = userHouseResultVo.getBuildingInfo();
                    JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
                    Map map = (Map) jasonObject;
                    String type = String.valueOf(map.get("type"));
                    if ("2".equals(type)) {//type=2表示手填数据
                        userHouseResultVo.setProvinceName((String) map.get("province"));
                        userHouseResultVo.setCityName((String) map.get("city"));
                        userHouseResultVo.setHouseProjectName((String) map.get("community"));
                        userHouseResultVo.setHousingNum(String.valueOf(map.get("buildNo")));
                        userHouseResultVo.setUnitNum(String.valueOf(map.get("unitNo")));
                        userHouseResultVo.setRoomNum(String.valueOf(map.get("roomNo")));
                        if(StringUtil.isBlank(userHouseResultVo.getSize())){
                            userHouseResultVo.setSize(String.valueOf(map.get("area")));
                        }
                    }
                }

                houseResponse.setOrderId(userHouseResultVo.getMasterOrderId());
                if (userHouseResultVo.getSource() != null) {
                    houseResponse.setOrderSource(userHouseResultVo.getSource());
                }
                //楼盘名称
                if (org.apache.commons.lang3.StringUtils.isNotBlank(housePropertyInfoResultDto.getBuildingName())) {
                    houseResponse.setBuildingName(housePropertyInfoResultDto.getBuildingName().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));
                }
                //户型名称
                if (org.apache.commons.lang3.StringUtils.isNotBlank(housePropertyInfoResultDto.getLayoutName())) {
                    if (housePropertyInfoResultDto.getLayoutName().contains(HomeCardPraise.HOUSE_TYPE)) {
                        houseResponse.setHouseType(housePropertyInfoResultDto.getLayoutName());
                    } else {
                        houseResponse.setHouseType(housePropertyInfoResultDto.getLayoutName() + HomeCardPraise.HOUSE_TYPE);
                    }
                } else if (housePropertyInfoResultDto.getLayoutId() != null && housePropertyInfoResultDto.getLayoutId().equals(0)) {
                    houseResponse.setHouseType(HomeCardPraise.HOUSE_TYPE_ORTHER);
                }

                //楼栋单元房号
                StringBuffer houseRoomNum = new StringBuffer("");
                if (org.apache.commons.lang3.StringUtils.isNotBlank(userHouseResultVo.getHousingNum())) {
                    houseRoomNum.append(userHouseResultVo.getHousingNum());
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(userHouseResultVo.getUnitNum())) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(houseRoomNum)) {
                        houseRoomNum.append("-");
                    }
                    houseRoomNum.append(userHouseResultVo.getUnitNum());
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(userHouseResultVo.getRoomNum())) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(houseRoomNum)) {
                        houseRoomNum.append("-");
                    }
                    houseRoomNum.append(userHouseResultVo.getRoomNum());
                }
                houseResponse.setRoomAddress(houseRoomNum.toString());

                //面积
                StringBuffer size = new StringBuffer("");
                if (userHouseResultVo.getSize() != null) {
                    size.append(userHouseResultVo.getSize()).append(ProductProgramPraise.AREA);
                } else {
                    size.append(0).append(ProductProgramPraise.AREA);
                }
                houseResponse.setArea(size.toString());
                houseResponse.setHouseTypeId(userHouseResultVo.getHouseTypeId());
                houseResponse.setBuildingInfo(userHouseResultVo.getBuildingInfo());
                responses.add(houseResponse);
            }
        }
        return responses;
    }

    /**
     * 查询艺术品订单列表
     * 订单状态  前台显示
     * 待付款     待付款
     * 待接单     已付款
     * 待发货     已开始定制
     * 待收货/部分发货 已发货
     * 已完成     已完成
     * 已取消     已取消
     * @param userId
     * @return
     */
    private List<OrderResponse> queryArtOrderList(Integer userId, String appVersion) {
        com.ihomefnt.o2o.intf.domain.agent.dto.PageModel<OrderDtoVo> pageModel = orderProxy.queryOrderInfo(new OrderInfoSearchDto(300, 1, userId, 1));
        if (pageModel == null || pageModel.getList() == null) {
            return new ArrayList<>();
        }

        List<OrderDtoVo> orderDtoVoList = pageModel.getList();
        // 老订单只查艺术品和文旅商品,团购商品和画屏商品
        orderDtoVoList = orderDtoVoList.stream().filter(orderDtoVo -> orderDtoVo.getOrderType() == OrderConstant.ORDER_TYPE_ART
                || orderDtoVo.getOrderType() == OrderConstant.ORDER_TYPE_CLUTRUE
                || orderDtoVo.getOrderType() == OrderConstant.ORDER_TYPE_COLLAGE
                || orderDtoVo.getOrderType() == OrderConstant.ORDER_TYPE_BOE).collect(Collectors.toList());


        if (CollectionUtils.isEmpty(orderDtoVoList)) {
            return new ArrayList<>();
        }

        List<OrderResponse> orList = new ArrayList<>();

        List<Integer> orderIdList = new ArrayList<>();
        for (OrderDtoVo orderDto : orderDtoVoList) {
            if (CollectionUtils.isNotEmpty(orderDto.getOrderDetailDtoList())) {
                for (OrderDetailDtoVo orderDetailDtoVo : orderDto.getOrderDetailDtoList()) {
                    if (StringUtils.isNotBlank(orderDetailDtoVo.getSelectAttr())) {
                        orderDetailDtoVo.setSelectAttr(getSelectAttr(orderDetailDtoVo.getSelectAttr()));
                    } else {
                        orderDetailDtoVo.setSelectAttr(getSelectAttr(orderDetailDtoVo.getPropertyNameValue()));
                    }

                }
            }
            if (orderDto.getOrderId() != null
                    && orderDto.getOrderType() != null
                    && orderDto.getState() != null
                    && (OrderConstant.ORDER_TYPE_ART.equals(orderDto.getOrderType())
                    || OrderConstant.ORDER_TYPE_COLLAGE.equals(orderDto.getOrderType())
                    || OrderConstant.ORDER_TYPE_BOE.equals(orderDto.getOrderType()))
                    && OrderConstant.ORDER_OMSSTATUS_FINISH.equals(orderDto.getState())) {
                //查询已完成的艺术品订单
                orderIdList.add(orderDto.getOrderId());
            }
        }
        List<ArtCommentDto> commentList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderIdList)) {
            commentList = artCommentService.getCommentListByOrderIdList(orderIdList);
        }

        // 循环订单多线程查询处理
        List<TaskAction<OrderResponse>> taskActions = new ArrayList<>();
        for (OrderDtoVo orderDto : orderDtoVoList) {
            taskActions.add(() -> {
                OrderResponse orderResponse = null;
                try {
                    //艺术品订单详情信息
                    orderResponse = queryArtOrderInfo(orderDto, appVersion);
                } catch (Exception e) {
                    LOG.error("queryArtOrderInfo fail o2o-exception , more info :", e);
                }
                return orderResponse;
            });
        }
        List<OrderResponse> orderResponseList = TaskProcessManager.getTaskProcess().executeTask(taskActions);

        if (CollectionUtils.isNotEmpty(orderResponseList)) {
            for (OrderResponse response : orderResponseList) {
                int commentCount = 0;
                if (CollectionUtils.isNotEmpty(commentList)) {
                    if (CollectionUtils.isNotEmpty(response.getOrderDetailRespList())) {
                        for (OrderDetailResp orderDetailResp : response.getOrderDetailRespList()) {
                            for (ArtCommentDto artCommentDto : commentList) {
                                if (artCommentDto.getProductId().equals(orderDetailResp.getFidProduct()) && artCommentDto.getOrderId().equals(response.getOrderId())) {
                                    orderDetailResp.setCommentResultTag(true);
                                    commentCount++;
                                }
                            }
                        }
                    }
                }
                response.setProductCommentCount(commentCount);
                orList.add(response);
            }
        }
        return orList;

    }


    /**
     * 订单列表排序
     *
     * @param orList
     * @return
     */
    @SuppressWarnings("all")
    private List<OrderResponse> sortOrderList(List<OrderResponse> orList) {
        //orList 排序
        List<OrderResponse> deliveryList = new ArrayList<>();//交付中全品家订单
        List<OrderResponse> signList = new ArrayList<>();//交付中全品家订单
        List<OrderResponse> handselList = new ArrayList<>();//交付中全品家订单
        List<OrderResponse> purposeList = new ArrayList<>();//交付中全品家订单
        List<OrderResponse> artOrderList = new ArrayList<>();//艺术品订单
        List<OrderResponse> completeList = new ArrayList<>();//已完成全品家订单
        List<OrderResponse> touchList = new ArrayList<>();//接触中全品家订单
        List<OrderResponse> cancelList = new ArrayList<>();//已取消全品家订单
        List<OrderResponse> otherList = new ArrayList<>();//已取消全品家订单
        List<OrderResponse> artCanceOrderList = new ArrayList<>();//已取消的艺术品订单

        for (OrderResponse order : orList) {
            if (order.getOrderType().equals(OrderConstant.ORDER_TYPE_ALADDIN)) {
                if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_DELIVERY)) {
                    deliveryList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_SIGN)) {
                    signList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_HANDSEL)) {
                    handselList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_PURPOSE)) {
                    purposeList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_FINISH)) {
                    completeList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_TOUCH)) {
                    touchList.add(order);
                } else if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_CANCEL)) {
                    cancelList.add(order);
                } else {
                    otherList.add(order);
                }
            } else if (order.getOrderType().equals(OrderConstant.ORDER_TYPE_ART)
                    || order.getOrderType().equals(OrderConstant.ORDER_TYPE_COLLAGE) || order.getOrderType().equals(OrderConstant.ORDER_TYPE_BOE)) {
                if (order.getState().equals(OrderConstant.ORDER_OMSSTATUS_CANCEL)) {
                    artCanceOrderList.add(order);
                } else {
                    artOrderList.add(order);
                }
            } else {
                otherList.add(order);
            }
        }

        List<OrderResponse> responses = new ArrayList<>();

        //交付中 > 已签约 > 定金阶段 > 意向阶段 > 艺术品订单 > 已完成 > 接触中 > 已取消大订单 > 已取消艺术品订单 > 其他订单
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            responses.addAll(deliveryList);
        }
        if (CollectionUtils.isNotEmpty(signList)) {
            responses.addAll(signList);
        }
        if (CollectionUtils.isNotEmpty(handselList)) {
            responses.addAll(handselList);
        }
        if (CollectionUtils.isNotEmpty(purposeList)) {
            responses.addAll(purposeList);
        }
        if (CollectionUtils.isNotEmpty(completeList)) {
            responses.addAll(completeList);
        }
        if (CollectionUtils.isNotEmpty(artOrderList)) {
            responses.addAll(artOrderList);
        }
        if (CollectionUtils.isNotEmpty(touchList)) {
            responses.addAll(touchList);
        }
        if (CollectionUtils.isNotEmpty(cancelList)) {
            responses.addAll(cancelList);
        }
        if (CollectionUtils.isNotEmpty(artCanceOrderList)) {
            responses.addAll(artCanceOrderList);
        }
        if (CollectionUtils.isNotEmpty(otherList)) {
            responses.addAll(otherList);
        }

        return responses;

    }

    /**
     * 设置全品家订单信息
     *
     * @param orderDto
     * @param orderRequest
     * @param
     */
    private OrderResponse setAllProductOrderInfo(OrderDtoVo orderDto, HttpOrderListRequest orderRequest) {
        if (orderDto != null) {
            AllProductOrderResponse orderResponseVo = null;
            if (orderDto.getSource() != null && ProductProgramPraise.ORDER_SOURCE_VALET.equals(orderDto.getSource())) {
                //代客下单
                orderResponseVo = productProgramOrderService.queryValetOrderDetailById(orderDto.getOrderId(), orderRequest.getWidth());
            } else {
                //方案订单
                orderResponseVo = productProgramOrderService.queryProductOrderDetailForAPPById(orderDto.getOrderId(), orderRequest.getWidth(), "orderList");
            }

            if (orderResponseVo != null) {
                OrderResponse or = new OrderResponse();
                Integer furnitureTotalNum = 0;
                String soultionName = "";
                Integer hardOrderStatus = 0;//硬装订单状态
                Integer softOrderStatus = 0;//软装订单状态
                Integer softProductTotalCount = 0;//代客下单软装家具数量

                or.setOrderId(orderResponseVo.getOrderId());
                or.setOrderType(OrderConstant.ORDER_TYPE_ALADDIN);//全品家订单
                or.setOrderNum(orderResponseVo.getOrderNum());
                //订单状态扭转
                if (orderResponseVo.getState() != null) {
                    if (ProductProgramPraise.ALADDIN_ORDER_STATUS_TOUCH.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_TOUCH);//接触状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_PURPOSE.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_PURPOSE);//意向状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_HANDSEL.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_HANDSEL);//定金状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_SIGN);//签约状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_DELIVERY.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_DELIVERY);//交付中状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_FINISH.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_FINISH);//已完成状态
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_CANCEL.equals(orderResponseVo.getState())) {
                        or.setState(OrderConstant.ORDER_OMSSTATUS_CANCEL);//已取消状态
                    } else {
                        or.setState(-1);//订单状态
                    }
                } else {
                    or.setState(-1);//订单状态
                }
                or.setStateDesc(orderResponseVo.getStateDesc());
                or.setTotalPrice(orderResponseVo.getTotalPrice());
                or.setActualPayMent(orderResponseVo.getActualPayMent());
                or.setUnpaidMoney(orderResponseVo.getUnpaidMoney());
                or.setPaidMoney(orderResponseVo.getPaidMoney());
                or.setApartmentId(orderResponseVo.getUserInfo() == null ? 0 : orderResponseVo.getUserInfo().getLayoutId());
                or.setApartmentVersion(orderResponseVo.getUserInfo().getApartmentVersion());
                or.setReformFlag(orderResponseVo.getUserInfo().getReformFlag());
                AllProductOrderDetail allProductOrder = new AllProductOrderDetail();
                if (orderResponseVo.getGradeId() != null) {
                    allProductOrder.setGradeId(orderResponseVo.getGradeId());
                    allProductOrder.setGradeName(orderResponseVo.getGradeName());
                }
                allProductOrder.setOrderSource(orderDto.getSource());
                if (orderResponseVo.getUserInfo() != null) {
                    UserInfoResponse userInfo = orderResponseVo.getUserInfo();
                    HouseResponse houseInfo = new HouseResponse();
                    houseInfo.setArea(userInfo.getHouseArea());
                    houseInfo.setBuildingAddress(userInfo.getBuildingAddress());
                    houseInfo.setHouseType(userInfo.getHouseName());
                    houseInfo.setPattern(userInfo.getHousePattern());
                    houseInfo.setRoomAddress(userInfo.getHouseFullName());
                    allProductOrder.setHouseInfo(houseInfo);
                    allProductOrder.setHomeAdviserMobile(userInfo.getAdviserMobileNum());
                }
                if (orderResponseVo.getSolutionOrderInfo() != null) {
                    SolutionOrderInfo solutionOrderInfo = orderResponseVo.getSolutionOrderInfo();
                    allProductOrder.setOrderProgramTypeCode(solutionOrderInfo.getOrderProgramTypeCode());
                    allProductOrder.setSolutionId(solutionOrderInfo.getSolutionId());
                    allProductOrder.setSolutionName(solutionOrderInfo.getSolutionName());
                    allProductOrder.setFurnitureTotalNum(solutionOrderInfo.getFurnitureTotalNum());
                    furnitureTotalNum = solutionOrderInfo.getFurnitureTotalNum();
                    soultionName = solutionOrderInfo.getSolutionName();
                }
                //硬装施工信息
                if (orderResponseVo.getHardConstructInfo() != null) {
                    HardConstructInfo hardConstructInfo = orderResponseVo.getHardConstructInfo();
                    if (hardConstructInfo.getHardOrderStatus() != null) {
                        hardOrderStatus = hardConstructInfo.getHardOrderStatus();
                    }
                }
                //软装配送信息
                if (orderResponseVo.getSoftDeliveryInfo() != null) {
                    SoftDeliveryInfo softDeliveryInfo = orderResponseVo.getSoftDeliveryInfo();
                    if (softDeliveryInfo.getSoftOrderStatus() != null) {
                        softOrderStatus = softDeliveryInfo.getSoftOrderStatus();
                    }
                    if (softDeliveryInfo.getProductTotalCount() != null) {
                        softProductTotalCount = softDeliveryInfo.getProductTotalCount();
                    }
                }

                //底部文案
                if (orderResponseVo.getState() != null) {
                    if (ProductProgramPraise.ALADDIN_ORDER_STATUS_PURPOSE.equals(orderResponseVo.getState())) {
                        //意向阶段
                        allProductOrder.setBottomPraise("已交诚意金" + orderResponseVo.getPaidMoney() + "元");
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_HANDSEL.equals(orderResponseVo.getState())) {
                        //定金阶段
                        allProductOrder.setBottomPraise("已交定金" + orderResponseVo.getPaidMoney() + "元");
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN.equals(orderResponseVo.getState())) {
                        if (orderDto.getSource() != null && ProductProgramPraise.ORDER_SOURCE_VALET.equals(orderDto.getSource())) {
                            //代客下单
                            if (softProductTotalCount > 0) {
                                allProductOrder.setBottomPraise("已选定" + softProductTotalCount + "件家具");
                            }
                        } else {
                            //签约阶段
                            if (ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT.equals(orderResponseVo.getOrderType()) || ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT.equals(orderResponseVo.getOrderType())) {
                                //纯软装
                                allProductOrder.setBottomPraise("已选定全品家方案：共" + furnitureTotalNum + "件家具");
                            } else if (ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_HARDSOFT.equals(orderResponseVo.getOrderType())) {
                                //整套
                                allProductOrder.setBottomPraise("已选定全品家方案：" + soultionName);
                            } else if (ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_HARDSOFT.equals(orderResponseVo.getOrderType())) {
                                //自由搭配
                                allProductOrder.setBottomPraise("已选定全品家方案：多空间自由搭配");
                            }
                        }
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_DELIVERY.equals(orderResponseVo.getState())) {
                        //交付中   需要根据软硬装订单状态分析
                        if (ProductProgramPraise.ALADDIN_SOFT_ORDER_STATUS_WAITPURCHASE.equals(softOrderStatus) || ProductProgramPraise.ALADDIN_SOFT_ORDER_STATUS_INPURCHASE.equals(softOrderStatus) || ProductProgramPraise.ALADDIN_SOFT_ORDER_STATUS_WAITDELIVERY.equals(softOrderStatus)) {
                            //软装：待采购 或 采购中 或 待配送
                            if (ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT.equals(orderResponseVo.getOrderType()) || ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT.equals(orderResponseVo.getOrderType())) {
                                //纯软装订单
                                allProductOrder.setBottomPraise("物料已采购，等待交楼进场");
                            } else {
                                //软装+硬装订单
                                if (ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_WAITALLOT.equals(hardOrderStatus) || ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_WAITSCHEDULE.equals(hardOrderStatus) || ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_WAITCONSTRUCT.equals(hardOrderStatus)) {
                                    //硬装：待分配 或 待排期 或 已排期
                                    allProductOrder.setBottomPraise("物料已采购，等待交楼进场");
                                } else if (ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_INCONSTRUCT.equals(hardOrderStatus)) {
                                    //硬装：施工中
                                    allProductOrder.setBottomPraise("硬装已在施工，软装等待进场");
                                } else if (ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_FINISH.equals(hardOrderStatus)) {
                                    //硬装：已完成
                                    allProductOrder.setBottomPraise("硬装已完成，软装等待进场");
                                }
                            }
                        } else if (ProductProgramPraise.ALADDIN_SOFT_ORDER_STATUS_INDELIVERY.equals(softOrderStatus)) {
                            //配送中
                            if (ProductProgramPraise.ALADDIN_ORDER_TYPE_ROOM_SOFT.equals(orderResponseVo.getOrderType()) || ProductProgramPraise.ALADDIN_ORDER_TYPE_SUIT_SOFT.equals(orderResponseVo.getOrderType())) {
                                //纯软装订单
                                allProductOrder.setBottomPraise("软装家具已发货");
                            } else {
                                //软装+硬装订单
                                if (ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_FINISH.equals(hardOrderStatus)) {
                                    //硬装：已完成
                                    allProductOrder.setBottomPraise("硬装已完成，软装家具已发货");
                                }
                            }
                        } else {
                            if (ProductProgramPraise.ALADDIN_HARD_ORDER_STATUS_CANCEL.equals(hardOrderStatus)) {
                                //硬装：已取消
                                allProductOrder.setBottomPraise("硬装已取消，软装服务中");
                            }
                        }
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_FINISH.equals(orderResponseVo.getState())) {
                        //已完成
                        allProductOrder.setBottomPraise("您的新家已完成");
                    } else if (ProductProgramPraise.ALADDIN_ORDER_STATUS_CANCEL.equals(orderResponseVo.getState())) {
                        //已取消
                        allProductOrder.setBottomPraise("您的订单已取消");
                    }
                }
                or.setAllProductOrder(allProductOrder);
                return or;
            }
        }
        return null;
    }

    /**
     * 设置订单信息
     *
     * @param orderDto
     */
    private OrderResponse queryArtOrderInfo(OrderDtoVo orderDto, String appVersion) {
        OrderResponse or = new OrderResponse();
        BeanUtils.copyProperties(orderDto, or);
        or.setStateDesc(OrderStateEnum.getName(orderDto.getState()));
        List<OrderDetailResp> orderDetailRespList = new ArrayList<>();
        List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
        if (VersionUtil.mustUpdate(appVersion, "5.5.0")) {//5.5.0以上版本物流信息单独查queryDeliveryInfoByOrderId接口
            setDeliveryInfoByOrderId(or, orderDto);
        }
        if (null != orderDetailDtoList) {

            List<String> newMallSkuList = new ArrayList<>();
            for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
                OrderDetailResp orderDetailResp = new OrderDetailResp();
                BeanUtils.copyProperties(orderDetailDto, orderDetailResp);
                Integer productId = orderDetailDto.getFidProduct();
                if (null != productId && (orderDetailDto.getSkuType() == null || orderDetailDto.getSkuType() == 0)) {
                    or.setSaleType(0);
                    Artwork artwork = null;
                    String result = AppRedisUtil.get(artworkOrderRedisKey + productId);
                    if (StringUtils.isNotBlank(result)) {
                        artwork = JSON.parseObject(result, Artwork.class);
                    }
                    if (artwork == null) {
                        artwork = artDao.getArtworkOrderInfo(productId.longValue());
                        if (artwork != null) {
                            AppRedisUtil.set(artworkOrderRedisKey + productId, JSON.toJSONString(artwork), 7 * 24 * 3600);
                        }
                    }
                    if (artwork != null) {
                        orderDetailResp.setProductName(artwork.getName());// 商品名称
                        orderDetailResp.setProductPrice(artwork.getPrice());// 商品单价
                        orderDetailResp.setProductCategoty(artwork.getType());// 商品类型
                        orderDetailResp.setHeadImage(QiniuImageUtils.compressImageAndDiffPic(artwork.getHeadImg(), 250, -1));// 商品头图
                    }
                } else {
                    newMallSkuList.add(orderDetailDto.getSkuId());
                }
                orderDetailRespList.add(orderDetailResp);
            }
            if (CollectionUtils.isNotEmpty(newMallSkuList)) {
                List<Artwork> artworkOrderInfo = artDao.getArtworkOmsOrderInfo(new ArtworkOrderRequest(newMallSkuList, true, orderDto.getOrderId(), orderDto.getOrderNum()));
                if (CollectionUtils.isNotEmpty(artworkOrderInfo)) {
                    for (OrderDetailResp orderDetailResp : orderDetailRespList) {
                        if (orderDetailResp.getSkuType() != null && orderDetailResp.getSkuType() != 0) {
                            or.setSaleType(1);
                            for (Artwork artwork : artworkOrderInfo) {
                                if (orderDetailResp.getSkuId().equals(artwork.getProductId())) {
                                    orderDetailResp.setProductName(artwork.getName());// 商品名称
                                    orderDetailResp.setProductPrice(artwork.getPrice());// 商品单价
                                    orderDetailResp.setProductCategoty(artwork.getType());// 商品类型
                                    orderDetailResp.setHeadImage(getSinglePicUrl(artwork.getHeadImg(), 250));// 商品头图
                                }
                            }
                        }
                    }
                }
            }
        }
        or.setOrderDetailRespList(orderDetailRespList);
        return or;
    }


    /**
     * 获取单张图片并切图
     *
     * @param image
     * @return
     */
    private String getSinglePicUrl(String image, Integer width) {
        if (image.indexOf(",") > -1) {
            String[] split = image.split(",");
            return AliImageUtil.imageCompress(split[0],
                    2, width, ImageConstant.SIZE_SMALL);
        } else {
            return AliImageUtil.imageCompress(image, 2, width, ImageConstant.SIZE_SMALL);
        }
    }

    /**
     * 设置物流信息
     */
    @SuppressWarnings("all")
    private void setDeliveryInfoByOrderId(OrderResponse or, OrderDtoVo orderDto) {
        //判断该订单有几条物流信息，若一条则跳转物流详情页，若多条则跳转所有物流页，并添加所有物流说明
        LogisticListResponse logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderDto.getOrderId(), 700);
        if (logisticListResponse != null) {
            // 订单关联的快递信息
            List<LogisticResponse> logisticList = logisticListResponse.getLogisticList();
            if (CollectionUtils.isNotEmpty(logisticList)) {
                if (logisticList.size() == 1 && logisticListResponse.getProductCount().equals(logisticListResponse.getFilledCount())) {
                    LogisticResponse logisticDetail = logisticList.get(0);
                    //物流详情
                    or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_DETAIL); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(OrderConstant.LOGISTIC_SUB_DELIVER);//待发货、部分发货
                    or.setSubStatusDesc(OrderConstant.LOGISTIC_SUB_DELIVER_DESC);//说明
                    or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                    or.setLogisticPackageNum(logisticList.size());//物流包裹数
                    or.setLogisticNum(logisticDetail.getLogisticNum());
                    or.setLogisticCompanyCode(logisticDetail.getLogisticCompanyCode());
                    or.setLogisticCompanyName(logisticDetail.getLogisticCompanyName());
                } else {
                    //所有物流页
                    or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_ALL); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(OrderConstant.LOGISTIC_PART_DELIVER);//待发货、部分发货
                    or.setSubStatusDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);//说明
                    or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                    or.setLogisticPackageNum(logisticList.size());//物流包裹数
                    or.setLogisticNum(logisticList.get(0).getLogisticNum());
                    or.setLogisticCompanyCode(logisticList.get(0).getLogisticCompanyCode());
                    or.setLogisticCompanyName(logisticList.get(0).getLogisticCompanyName());
                    //订单状态
                    if (orderDto.getState() != null && OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(orderDto.getState())) {
                        or.setStateDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);
                    }
                }
            } else {
                //无快递信息
                or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                or.setSubStatus(-1);//待发货、部分发货
                or.setSubStatusDesc("");//说明
                or.setLogisticProductNum(0);//物流商品数
                or.setLogisticPackageNum(0);//物流包裹数
                // 设置快递公司
                or.setLogisticNum(orderDto.getLogisticnum());
                or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
                or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
            }
        } else {
            //无快递信息
            or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
            or.setSubStatus(-1);//待发货、部分发货
            or.setSubStatusDesc("");//说明
            or.setLogisticProductNum(0);//物流商品数
            or.setLogisticPackageNum(0);//物流包裹数
            // 设置快递公司
            or.setLogisticNum(orderDto.getLogisticnum());
            or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
            or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
        }
    }

    /**
     * 查询物流信息
     *
     * @param orderDto
     * @return
     */
    @Override
    public OrderResponse queryDeliveryInfoByOrderId(DeliverOrderRequest orderDto) {
        OrderResponse or = new OrderResponse();
        //判断该订单有几条物流信息，若一条则跳转物流详情页，若多条则跳转所有物流页，并添加所有物流说明
        LogisticListResponse logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderDto.getOrderId(), 700);
        if (logisticListResponse != null) {
            // 订单关联的快递信息
            List<LogisticResponse> logisticList = logisticListResponse.getLogisticList();
            if (CollectionUtils.isNotEmpty(logisticList)) {
                if (logisticList.size() == 1 && logisticListResponse.getProductCount().equals(logisticListResponse.getFilledCount())) {
                    LogisticResponse logisticDetail = logisticList.get(0);
                    //物流详情
                    or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_DETAIL); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(OrderConstant.LOGISTIC_SUB_DELIVER);//待发货、部分发货
                    or.setSubStatusDesc(OrderConstant.LOGISTIC_SUB_DELIVER_DESC);//说明
                    or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                    or.setLogisticPackageNum(logisticList.size());//物流包裹数
                    or.setLogisticNum(logisticDetail.getLogisticNum());
                    or.setLogisticCompanyCode(logisticDetail.getLogisticCompanyCode());
                    or.setLogisticCompanyName(logisticDetail.getLogisticCompanyName());
                } else {
                    //所有物流页
                    or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_ALL); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(OrderConstant.LOGISTIC_PART_DELIVER);//待发货、部分发货
                    or.setSubStatusDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);//说明
                    or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                    or.setLogisticPackageNum(logisticList.size());//物流包裹数
                    or.setLogisticNum(logisticList.get(0).getLogisticNum());
                    or.setLogisticCompanyCode(logisticList.get(0).getLogisticCompanyCode());
                    or.setLogisticCompanyName(logisticList.get(0).getLogisticCompanyName());
                    //订单状态
                    if (orderDto.getState() != null && OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(orderDto.getState())) {
                        or.setStateDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);
                    }
                }
            } else {
                //无快递信息
                or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                or.setSubStatus(-1);//待发货、部分发货
                or.setSubStatusDesc("");//说明
                or.setLogisticProductNum(0);//物流商品数
                or.setLogisticPackageNum(0);//物流包裹数
                // 设置快递公司
                or.setLogisticNum(orderDto.getLogisticnum());
                or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
                or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
            }
        } else {
            //无快递信息
            or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
            or.setSubStatus(-1);//待发货、部分发货
            or.setSubStatusDesc("");//说明
            or.setLogisticProductNum(0);//物流商品数
            or.setLogisticPackageNum(0);//物流包裹数
            // 设置快递公司
            or.setLogisticNum(orderDto.getLogisticnum());
            or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
            or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
        }
        return or;
    }


    @Override
    public OrderResponse getOrderDetail298(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        int orderId = orderRequest.getOrderId().intValue();
        try {
            OrderDtoVo orderDto = orderService.queryOrderDetail(orderId);
            if (null == orderDto) {
                throw new BusinessException(MessageConstant.DDATA_GET_FAILED);
            }

            Integer fidProject = orderDto.getFidProject();
            Integer fidArea = orderDto.getFidArea();
            Date createTime = orderDto.getCreateTime();
            BigDecimal actualPayMent = orderDto.getActualPayMent();
            OrderResponse or = new OrderResponse();

            // 新增 下面 修改艾积分不展示问题 alter by jerfan cang 2018-1024

            List<OrderAttrValueDtoVo> attrList = orderDto.getOrderAttrValueOutputList();
            for (OrderAttrValueDtoVo attr : attrList) {
                if (null != attr.getAttrName() && attr.getAttrName().equals("ajbMoney")) {
                    if (null != attr.getAttrValue() && !"".equals(attr.getAttrValue())) {
                        or.setAjbMoney(new BigDecimal(attr.getAttrValue()));
                    } else {
                        LOG.info(or.getOrderNum() + " 的艾积分响应为空 ...");
                        or.setAjbMoney(new BigDecimal(0));
                    }
                }
            }
            // end
            or.setOrderId(orderDto.getOrderId());
            or.setOrderType(orderDto.getOrderType());
            or.setOrderNum(orderDto.getOrderNum());
            or.setFidProject(fidProject);
            or.setProductCount(orderDto.getProductCount());
            //订单状态
            or.setState(orderDto.getState());
            if(orderDto.getState()!=null){
                or.setStateDesc(OrderStateEnum.getName(orderDto.getState()));
            }
            or.setActualPayMent(actualPayMent);
            or.setFidArea(fidArea);
            or.setProjectId(orderDto.getProjectId());
            or.setReceiverName(orderDto.getReceiverName());
            or.setReceiverTel(orderDto.getReceiverTel());
            or.setTotalPrice(orderDto.getTotalPrice());

            //判断该订单有几条物流信息，若一条且包含所有商品则跳转物流详情页，其他情况均跳转所有物流页，并添加所有物流说明
            LOG.info("298/orderDetail orderService.queryOrderDeliveryInfoByOrderId param:" + JsonUtils.obj2json(orderId));
            LogisticListResponse logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderId, orderRequest.getWidth());
            LOG.info("298/orderDetail orderService.queryOrderDeliveryInfoByOrderId response:" + JsonUtils.obj2json(logisticListResponse));
            if (logisticListResponse != null) {
                // 订单关联的快递信息
                List<LogisticResponse> logisticList = logisticListResponse.getLogisticList();
                if (CollectionUtils.isNotEmpty(logisticList)) {
                    // 仅1件物流 商品总数和已发商品总数相等 则为以发货
                    if (logisticList.size() == 1 && logisticListResponse.getProductCount().equals(logisticListResponse.getFilledCount())) {
                        LOG.info("所有物流信息 : " + JsonUtils.obj2json(logisticList));
                        LogisticResponse logisticDetail = logisticList.get(0);
                        //物流详情
                        or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_DETAIL); //物流跳转类型0物流详情页、1所有物流页

                        if (null != logisticDetail.getState() && null != logisticDetail.getStateDesc()) {
                            or.setSubStatus(logisticDetail.getState());
                            or.setSubStatusDesc(logisticDetail.getStateDesc());
                        } else {
                            or.setSubStatus(OrderConstant.LOGISTIC_SUB_DELIVER);//待发货、部分发货
                            or.setSubStatusDesc(OrderConstant.LOGISTIC_SUB_DELIVER_DESC);//说明
                        }

                        or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                        or.setLogisticPackageNum(logisticList.size());//物流包裹数
                        or.setLogisticNum(logisticDetail.getLogisticNum());
                        or.setLogisticCompanyCode(logisticDetail.getLogisticCompanyCode());
                        or.setLogisticCompanyName(logisticDetail.getLogisticCompanyName());
                    } else {
                        //所有物流页
                        or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_ALL); //物流跳转类型0物流详情页、1所有物流页
                        // begin
                        LOG.info(logisticList.size() + " 个物流信息 : " + JsonUtils.obj2json(logisticList));
                        LogisticResponse logisticResponse = logisticList.get(0);
                        if (null != logisticResponse.getState() && null != logisticResponse.getStateDesc()) {
                            or.setSubStatus(logisticResponse.getState());
                            or.setSubStatusDesc(logisticResponse.getStateDesc());
                        } else {
                            or.setSubStatus(OrderConstant.LOGISTIC_PART_DELIVER);//待发货、部分发货
                            or.setSubStatusDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);//说明
                        }
                        // end
                        or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                        or.setLogisticPackageNum(logisticList.size());//物流包裹数
                        or.setLogisticNum(logisticList.get(0).getLogisticNum());
                        or.setLogisticCompanyCode(logisticList.get(0).getLogisticCompanyCode());
                        or.setLogisticCompanyName(logisticList.get(0).getLogisticCompanyName());
                        //订单状态
                        if (orderDto.getState() != null && OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(orderDto.getState())) {
                            or.setStateDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);
                        }
                    }
                } else {
                    //无快递信息
                    or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(-1);//待发货、部分发货
                    or.setSubStatusDesc("");//说明
                    or.setLogisticProductNum(0);//物流商品数
                    or.setLogisticPackageNum(0);//物流包裹数
                    or.setLogisticNum(orderDto.getLogisticnum());
                    or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
                    or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
                }
            } else {
                //无快递信息
                or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                or.setSubStatus(-1);//待发货、部分发货
                or.setSubStatusDesc("");//说明
                or.setLogisticProductNum(0);//物流商品数
                or.setLogisticPackageNum(0);//物流包裹数
                or.setLogisticNum(orderDto.getLogisticnum());
                or.setLogisticCompanyCode(orderDto.getLogisticcompanycode());
                or.setLogisticCompanyName(orderDto.getLogisticcompanyname());
            }

            if (null != createTime) {
                String createDateStr = dayFormat.format(createTime);
                or.setCreateDateStr(createDateStr);
            }
            // 查询订单未支付金额

            LOG.info("298/orderDetail getOrderPayBalance param:{}", orderDto.getOrderNum());
            OrderPayBalanceVo balanceVo = orderService.getOrderPayBalance(orderDto.getOrderNum());

            if (null == balanceVo) {
                throw new BusinessException(MessageConstant.FAILED);
            }

            BigDecimal balance = balanceVo.getBalance();
            BigDecimal paidMoney = actualPayMent.subtract(balance);
            or.setUnpaidMoney(balance);
            or.setPaidMoney(paidMoney);

            StringBuilder receiverAddress = new StringBuilder();
            String area = areaService.queryFullAddress(fidArea);
            if (StringUtils.isNotBlank(area)) {
                receiverAddress.append(area.replace(" ", ""));
            }
            receiverAddress.append(orderDto.getCustomerAddress());
            if (StringUtils.isNotBlank(orderDto.getBuildingNo())) {
                receiverAddress.append(orderDto.getBuildingNo());
            }
            if (StringUtils.isNotBlank(orderDto.getHouseNo())) {
                if (StringUtils.isNotBlank(orderDto.getBuildingNo())) {
                    receiverAddress.append("-");
                }
                receiverAddress.append(orderDto.getHouseNo());
            }
            or.setReceiverAddress(receiverAddress.toString());
            List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
            Map<Integer, Boolean> commentMap = new HashMap<Integer, Boolean>();//艺术品评审内容
            if (OrderConstant.ORDER_TYPE_SOFT.equals(orderDto.getOrderType())) {
                SoftOrderVo softOrderVo = orderProxy.querySoftOrderDetail(orderId);

                BigDecimal cashCoupon = softOrderVo.getCashCoupon();
                BigDecimal voucher = softOrderVo.getVoucher();
                cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
                voucher = voucher == null ? new BigDecimal("0") : voucher;
                or.setCashCoupon(cashCoupon);
                or.setVoucher(voucher);
            } else if (OrderConstant.ORDER_TYPE_FAMILY.equals(orderDto.getOrderType())) {
                FamilyOrderVo familyOrder = orderProxy.queryFamilyOrderDetail(orderId);

                BigDecimal cashCoupon = familyOrder.getCashCoupon();
                BigDecimal voucher = familyOrder.getVoucher();
                cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
                voucher = voucher == null ? new BigDecimal("0") : voucher;
                or.setCashCoupon(cashCoupon);
                or.setVoucher(voucher);
            } else if (OrderConstant.ORDER_TYPE_HARD.equals(orderDto.getOrderType())) {
                HardOrderVo hardOrderVo = orderProxy.queryHardOrderDetail(orderId);

                BigDecimal cashCoupon = hardOrderVo.getCashCoupon();
                BigDecimal voucher = hardOrderVo.getVoucher();
                cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
                voucher = voucher == null ? new BigDecimal("0") : voucher;
                or.setCashCoupon(cashCoupon);
                or.setVoucher(voucher);
                // alter by jerfan cang  app 艺术品拼商品名称和图片名称 不展示
            } else if (OrderConstant.ORDER_TYPE_ART.equals(orderDto.getOrderType())
                    || OrderConstant.ORDER_TYPE_COLLAGE.equals(orderDto.getOrderType()) || OrderConstant.ORDER_TYPE_BOE.equals(orderDto.getOrderType())) {
                ArtOrderVo artOrderVo = orderProxy.queryArtOrderDetail(orderId);

                BigDecimal cashCoupon = artOrderVo.getCashCoupon();
                BigDecimal voucher = artOrderVo.getVoucher();
                cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
                voucher = voucher == null ? new BigDecimal("0") : voucher;
                or.setCashCoupon(cashCoupon);
                or.setVoucher(voucher);

                //艺术品订单待付款状态增加付款倒计时
                if (orderDto.getState() == 4) {
                    Date createTime2 = orderDto.getCreateTime();
                    long create = createTime2.getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if ((create + WAIT_PAY_TIME) > currentTimeMillis) {
                        long l = currentTimeMillis - create;
                        long l1 = WAIT_PAY_TIME - l;
                        or.setLastPayTime(l1);
                    }
                }
                /**
                 * 判断已完成的艺术品订单
                 */
                if (OrderConstant.ORDER_OMSSTATUS_FINISH.equals(orderDto.getState())) {
                    Integer productCommentCount = artCommentService.getCommentCountByOrderId(orderId);
                    or.setProductCommentCount(productCommentCount);
                    if (null != orderDetailDtoList) {
                        List<Integer> productIdList = new ArrayList<Integer>();
                        for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
                            Integer productId = orderDetailDto.getFidProduct();
                            if (productId != null) {
                                productIdList.add(productId);
                            }
                        }
                        //订单每件商品的评论详情
                        if (CollectionUtils.isNotEmpty(productIdList)) {
                            List<ArtCommentDto> commentList = artCommentService.getCommentListByOrderIdAndProductIdList(orderId, productIdList);
                            if (CollectionUtils.isNotEmpty(commentList)) {
                                for (ArtCommentDto dto : commentList) {
                                    Integer productId = dto.getProductId();
                                    if (productId != null) {
                                        commentMap.put(productId, true);
                                    }
                                }
                            }
                        }
                    }
                }

                BigDecimal ajbMoney = artOrderVo.getAjbMoney();
                or.setAjbMoney(ajbMoney);

            } else if (OrderConstant.ORDER_TYPE_CLUTRUE.equals(orderDto.getOrderType())) {

                TripOrderVo tripOrderVo = orderProxy.queryTripOrderDetail(orderId);

                BigDecimal ajbMoney = tripOrderVo.getAjbMoney();
                or.setAjbMoney(ajbMoney);
            }

            List<OrderDetailResp> orderDetailRespList = new ArrayList<OrderDetailResp>();

            if (null != orderDetailDtoList) {
                for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
                    OrderDetailResp orderDetailResp = new OrderDetailResp();
                    Integer productId = orderDetailDto.getFidProduct();
                    orderDetailResp.setFidProduct(productId);
                    orderDetailResp.setFidSuit(orderDetailDto.getFidSuit());
                    orderDetailResp.setFidSuitRoom(orderDetailDto.getFidSuitRoom());
                    orderDetailResp.setProductAmount(orderDetailDto.getProductAmount());
                    orderDetailResp.setProductAmountPrice(orderDetailDto.getProductAmountPrice());
                    orderDetailResp.setCustomizedContent(orderDetailDto.getCustomizedContent());

                    if (null != productId) {
                        //订单快照
                        String orderNum = orderDto.getOrderNum();
                        Integer orderType = orderDto.getOrderType();
                        OrderSnapshotProductResponse orderSnapshotProductResponse = orderSnapshotService.queryProductInfo(orderNum, orderType, productId);
                        String headImg = "";
                        if (null != orderSnapshotProductResponse) {
                            orderDetailResp.setProductName(orderSnapshotProductResponse.getProductName());//商品名称
                            if (orderSnapshotProductResponse.getProductPrice() != null) {
                                orderDetailResp.setProductPrice(orderSnapshotProductResponse.getProductPrice());//商品单价
                            }
                            if (orderSnapshotProductResponse.getDeliveryTime() != null) {
                                orderDetailResp.setDeliveryTime(orderSnapshotProductResponse.getDeliveryTime());
                            }
                            orderDetailResp.setProductCategoty(orderSnapshotProductResponse.getProductCategoty());//商品类型
                            headImg = orderSnapshotProductResponse.getProductImage();//商品头图
                        } else {
                            Artwork artwork = artDao.getArtworkOrderInfo(productId.longValue());
                            if (artwork != null) {
                                orderDetailResp.setProductName(artwork.getName());//商品名称
                                if (artwork.getPrice() != null) {
                                    orderDetailResp.setProductPrice(artwork.getPrice());//商品单价
                                }
                                orderDetailResp.setProductCategoty(artwork.getType());//商品类型
                                headImg = artwork.getHeadImg();//商品头图
                            }
                        }
                        // alter by jerfan cang app列表拼团商品名称和商品图片 不展示
                        if (OrderConstant.ORDER_TYPE_ART.equals(orderDto.getOrderType())
                                || OrderConstant.ORDER_TYPE_COLLAGE.equals(orderDto.getOrderType()) || OrderConstant.ORDER_TYPE_BOE.equals(orderDto.getOrderType())) {
                            Boolean commentResultTag = commentMap.get(productId);
                            if (commentResultTag == null) {
                                orderDetailResp.setCommentResultTag(false);
                            } else {
                                orderDetailResp.setCommentResultTag(commentResultTag);
                            }
                        }
                        if (StringUtils.isNotBlank(headImg)) {
                            Integer width = orderRequest.getWidth();
                            if (width != null) {
                                width = width * ImageSize.WIDTH_PER_SIZE_33
                                        / ImageSize.WIDTH_PER_SIZE_100;
                                headImg = QiniuImageUtils.compressImageAndDiffPic(headImg,
                                        width, -1);
                            }
                            orderDetailResp.setHeadImage(headImg);
                        }
                    }
                    orderDetailRespList.add(orderDetailResp);
                }

            }
            or.setOrderDetailRespList(orderDetailRespList);
            /**
             * 算出:预计发货时间的倒计时[待发货状态的艺术品订单才有的]
             */
            Integer deliveryTime = ArtOrderUtil.getDeliveryTime(or, orderDto);
            or.setDeliveryTime(deliveryTime);

            if (orderDto.getOrderType().equals(OrderConstant.ORDER_TYPE_BOE)) {
                //画屏订单，查询优惠券使用情况
                JSONObject params = new JSONObject();
                params.put("orderId", orderDto.getOrderId());
                CommonResponseVo commonResponseVo = boeActivityProxy.queryOrderVoucher(params);
                if (commonResponseVo != null && commonResponseVo.getCode() == 1 && commonResponseVo.getObj() != null) {
                    VoucherDto voucherDto = JsonUtils.json2obj(JsonUtils.obj2json(commonResponseVo.getObj()), VoucherDto.class);
                    or.setVoucherMoney(voucherDto == null ? null : voucherDto.getVoucherMoney());
                }

            }

            return or;
        } catch (Exception e) {
            LOG.error("oms order query o2o-exception , more info :", e);
            throw new BusinessException(MessageConstant.FAILED);
        }
    }


    /**
     * 新版艾商城艺术品订单查询
     *
     * @param request
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public OrderResponse queryOrderDetail(HttpOrderRequest request) {

        int orderId = request.getOrderId().intValue();
        try {
            OrderResponse or = orderService.queryArtOrderDetail(orderId);
            if (null == or) {
                throw new BusinessException(MessageConstant.DDATA_GET_FAILED);
            }

            Date createTime = or.getCreateTime();
            if(or.getState()!=null){
                or.setStateDesc(OrderStateEnum.getName(or.getState()));
            }
            LogisticListResponse logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderId, request.getWidth());
            if (logisticListResponse != null) {
                // 订单关联的快递信息
                List<LogisticResponse> logisticList = logisticListResponse.getLogisticList();
                if (CollectionUtils.isNotEmpty(logisticList)) {
                    // 仅1件物流 商品总数和已发商品总数相等 则为以发货
                    if (logisticList.size() == 1 && logisticListResponse.getProductCount().equals(logisticListResponse.getFilledCount())) {
                        LOG.info("所有物流信息 : " + JsonUtils.obj2json(logisticList));
                        LogisticResponse logisticDetail = logisticList.get(0);
                        //物流详情
                        or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_DETAIL); //物流跳转类型0物流详情页、1所有物流页

                        if(null != logisticDetail.getState() && null != logisticDetail.getStateDesc()){
                            or.setSubStatus(OrderConstant.LOGISTIC_PART_DELIVER);
                            or.setSubStatusDesc(logisticDetail.getStateDesc());
                        } else {
                            or.setSubStatus(OrderConstant.LOGISTIC_SUB_DELIVER);//待发货、部分发货
                            or.setSubStatusDesc(OrderConstant.LOGISTIC_SUB_DELIVER_DESC);//说明
                        }

                        or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                        or.setLogisticPackageNum(logisticList.size());//物流包裹数
                        or.setLogisticNum(logisticDetail.getLogisticNum());
                        or.setLogisticCompanyCode(logisticDetail.getLogisticCompanyCode());
                        or.setLogisticCompanyName(logisticDetail.getLogisticCompanyName());
                    } else {
                        //所有物流页
                        or.setLogisticSkipType(OrderConstant.LOGISTIC_SKIP_ALL); //物流跳转类型0物流详情页、1所有物流页
                        // begin
                        LOG.info(logisticList.size() + " 个物流信息 : " + JsonUtils.obj2json(logisticList));
                        LogisticResponse logisticResponse = logisticList.get(0);
                        if(null != logisticResponse.getState() && null!=logisticResponse.getStateDesc()){
                            or.setSubStatus(OrderConstant.LOGISTIC_PART_DELIVER);
                            or.setSubStatusDesc(logisticResponse.getStateDesc());
                        } else{
                            or.setSubStatus(OrderConstant.LOGISTIC_SUB_DELIVER);//待发货、部分发货
                            or.setSubStatusDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);//说明
                        }
                        // end
                        or.setLogisticProductNum(logisticListResponse.getFilledCount());//物流商品数
                        or.setLogisticPackageNum(logisticList.size());//物流包裹数
                        or.setLogisticNum(logisticList.get(0).getLogisticNum());
                        or.setLogisticCompanyCode(logisticList.get(0).getLogisticCompanyCode());
                        or.setLogisticCompanyName(logisticList.get(0).getLogisticCompanyName());
                        //订单状态
                        if (or.getState() != null && OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(or.getState())) {
                            or.setStateDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);
                        }
                    }
                } else {
                    //无快递信息
                    or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                    or.setSubStatus(-1);//待发货、部分发货
                    or.setSubStatusDesc("");//说明
                    or.setLogisticProductNum(0);//物流商品数
                    or.setLogisticPackageNum(0);//物流包裹数
                }
            } else {
                //无快递信息
                or.setLogisticSkipType(-1); //物流跳转类型0物流详情页、1所有物流页
                or.setSubStatus(-1);//待发货、部分发货
                or.setSubStatusDesc("");//说明
                or.setLogisticProductNum(0);//物流商品数
                or.setLogisticPackageNum(0);//物流包裹数
            }

            if (null != createTime) {
                String createDateStr = dayFormat.format(createTime);
                or.setCreateDateStr(createDateStr);
            }
            // 查询订单未支付金额

            LOG.info("queryOrderDetail getOrderPayBalance param:{}", or.getOrderNum());
            OrderPayBalanceVo balanceVo = orderService.getOrderPayBalance(or.getOrderNum());

            if (null == balanceVo) {
                throw new BusinessException(MessageConstant.FAILED);
            }

            StringBuilder receiverAddress = new StringBuilder();
            String area = areaService.queryFullAddress(or.getFidArea());
            if (StringUtils.isNotBlank(area)) {
                receiverAddress.append(area.replace(" ", ""));
            }

            receiverAddress.append(or.getCustomerAddress());
            or.setReceiverAddress(receiverAddress.toString());
            List<OrderDetailResp> orderDetailDtoList = or.getOrderDetailRespList();
            Map<Integer, Boolean> commentMap = new HashMap<Integer, Boolean>();//艺术品评审内容
            if (OrderConstant.ORDER_TYPE_ART.equals(or.getOrderType())) {
                //艺术品订单待付款状态增加付款倒计时
                if (or.getState() == 4) {
                    Date createTime2 = or.getCreateTime();
                    long create = createTime2.getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if ((create + WAIT_PAY_TIME) > currentTimeMillis) {
                        long l = currentTimeMillis - create;
                        long l1 = WAIT_PAY_TIME - l;
                        or.setLastPayTime(l1);
                    }
                }
                /**
                 * 判断已完成的艺术品订单
                 */
                if (OrderConstant.ORDER_OMSSTATUS_FINISH.equals(or.getState())) {
                    Integer productCommentCount = artCommentService.getCommentCountByOrderId(orderId);
                    or.setProductCommentCount(productCommentCount);
                    if (CollectionUtils.isNotEmpty(orderDetailDtoList)) {
                        List<Integer> productIdList = new ArrayList<>();
                        for (OrderDetailResp orderDetailDto : orderDetailDtoList) {
                            Integer productId = orderDetailDto.getFidProduct();
                            if (productId != null) {
                                productIdList.add(productId);
                            }
                        }
                        //订单每件商品的评论详情
                        if (CollectionUtils.isNotEmpty(productIdList)) {
                            List<ArtCommentDto> commentList = artCommentService.getCommentListByOrderIdAndProductIdList(orderId, productIdList);
                            if (CollectionUtils.isNotEmpty(commentList)) {
                                for (ArtCommentDto dto : commentList) {
                                    Integer productId = dto.getProductId();
                                    if (productId != null) {
                                        commentMap.put(productId, true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(orderDetailDtoList)) {
                for (OrderDetailResp orderDetailDto : orderDetailDtoList) {
                    orderDetailDto.setSelectAttr(getSelectAttr(orderDetailDto.getPropertyNameValue()));
                    Integer productId = orderDetailDto.getFidProduct();
                    String skuId = orderDetailDto.getSkuId();
                    if (null != productId || skuId != null) {
                        //订单快照
                        String orderNum = or.getOrderNum();
                        Integer orderType = or.getOrderType();
                        String headImg = orderDetailDto.getHeadImage();
                        if(StringUtils.isNotBlank(headImg)){
                            headImg = headImg.split(",")[0];
                        }
                        // alter by jerfan cang app列表拼团商品名称和商品图片 不展示
                        if (OrderConstant.ORDER_TYPE_ART.equals(or.getOrderType())) {
                            Boolean commentResultTag = commentMap.get(productId == null ? skuId : productId);
                            if (commentResultTag == null) {
                                orderDetailDto.setCommentResultTag(false);
                            } else {
                                orderDetailDto.setCommentResultTag(commentResultTag);
                            }
                        }
                        if (StringUtils.isNotBlank(headImg)) {
                            Integer width = request.getWidth();
                            if (width != null) {
                                width = width * ImageSize.WIDTH_PER_SIZE_33
                                        / ImageSize.WIDTH_PER_SIZE_100;

                            } else {
                                width = 250;
                            }
                            orderDetailDto.setHeadImage(getSinglePicUrl(headImg, width));
                        }
                    }
                }

            }
            /**
             * 算出:预计发货时间的倒计时[待发货状态的艺术品订单才有的]
             */
            Integer deliveryTime = ArtOrderUtil.getDeliveryTime(or);
            or.setDeliveryTime(deliveryTime);
            return or;
        } catch (Exception e) {
            LOG.error("oms order query o2o-exception , more info :", e);
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    /**
     * 设置用户已选属性
     *
     * @param selectAttr
     */
    private String getSelectAttr(String selectAttr) {
        try {
            if (StringUtils.isNotBlank(selectAttr)) {
                StringBuilder selectAttrRetrun = new StringBuilder();
                if (selectAttr.indexOf("/") > -1) {
                    String[] split = selectAttr.split("/");
                    for (String attr : split) {
                        if (attr.indexOf(":") > -1 && attr.split(":").length > 1) {
                            selectAttrRetrun.append(attr.split(":")[1] + "/");
                        }
                    }
                } else {
                    if (selectAttr.indexOf(":") > -1 && selectAttr.split(":").length > 1) {
                        selectAttrRetrun.append(selectAttr.split(":")[1] + "/");
                    }
                }
                if (selectAttrRetrun.indexOf("/") > -1) {
                    return selectAttrRetrun.toString().substring(0, selectAttrRetrun.length() - 1);
                }
                return selectAttrRetrun.toString();
            }

        } catch (Exception e) {
            LOG.error("ArtOrderServiceImpl.getSelectAttr  o2o-exception , more info :", e);
        }
        return "";
    }

    @Override
    public void orderCancel(HttpOrderRequest requestVo) {
        Integer orderType = requestVo.getOrderType();
        if (orderType != null && OrderConstant.ORDER_TYPE_ART.equals(orderType)) {
            orderCancelForArtType(requestVo);
        } else {
            orderCancleCommon(requestVo);
        }
    }

    private void orderCancelForArtType(HttpOrderRequest requestVo) {
        // 判断请求参数是否为空
        if (null == requestVo || null == requestVo.getOrderId() || null == requestVo.getAccessToken()) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String accessToken = requestVo.getAccessToken();
        /**
         * 验证用户是否登陆 <br/>
         * 未登陆,直接返回失败 <br/>
         */
        if (!userProxy.checkLegalUser(accessToken)) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Long orderId = requestVo.getOrderId();

        OrderDto order = new OrderDto();
        try {
            LOG.info("orderCancel queryArtOrderDetailById param:" + JsonUtils.obj2json(orderId));
            order = artService.queryArtOrderDetailById(Integer.parseInt(orderId.toString()));
            LOG.info("orderCancel queryArtOrderDetailById response:" + JsonUtils.obj2json(order));
        } catch (Exception e) {
            LOG.error("orderCancel298 exception from omsOrderService.queryOrderDetail o2o-exception , more info :{}", e.getMessage());
        }
        // 判断订单是否存在
        if (order == null) {
            throw new BusinessException(HttpResponseCode.PRODUCT_ORDER_INVALID, MessageConstant.PRODUCT_ORDER_INVALID);
        }
        Integer state = order.getState();

        /**
         * NO_PAYMENT("待付款", 4), NO_SEND("待发货", 10),
         */
        if (state!=null&&( OrderStateEnum.NO_PAYMENT.getCode() == state ||  OrderStateEnum.WAIT_ORDER.getCode() == state)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderNum", order.getOrderNum());
            // 订单来源PC("PC网站", 1), APP("APP", 2), H5("H5", 3);
            map.put("source", 2);
            boolean result = artService.cancelArtOrder(map);
            if (result) {
                LOG.info("orderCancel298 returnSuccess");
                return;
            } else {
                LOG.info("orderCancel298 returnCancleError:");
                //艺术品订单 13 待接单 14部分发货
//                if( OrderStateEnum.NO_SEND.getCode() == state){
//                    throw new BusinessException(HttpResponseCode.ORDER_CANCLE_FAILED, MessageConstant.ORDER_CANCLE_FAILED_SEND);
//                }else {
                    throw new BusinessException(HttpResponseCode.ORDER_CANCLE_FAILED, MessageConstant.ORDER_CANCLE_FAILED);
//                }
            }
        } else {
            LOG.info("orderCancel298 error state:" + state);
            throw new BusinessException(HttpResponseCode.ORDER_STATUS_FAILED, MessageConstant.ORDER_UNCANCLE_FAILED);
        }
    }

    private void orderCancleCommon(HttpOrderRequest orderRequest) {
        boolean ajbSuccess = false;
        Integer orderType = orderRequest.getOrderType();
        Integer state = null;
        if (orderRequest.getOrderId() != null) {
            OrderDtoVo orderDto = null;
            try {
                orderDto = orderService.queryOrderDetail(orderRequest.getOrderId().intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (orderDto != null) {
                state = orderDto.getState();
            }
        }

        if (orderRequest == null || orderRequest.getOrderId() == null || orderType == null || state == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        int orderId = orderRequest.getOrderId().intValue();
        try {
            int result = 0;
            if ( OrderConstant.ORDER_TYPE_SOFT.equals(orderType)
                    && ( OrderStateEnum.NO_PAYMENT.getCode() == state ||  OrderStateEnum.CREATE.getCode() == state ||  OrderStateEnum.PROCESSING
                    .getCode() == state)) { // 软装

                boolean resultTag = orderProxy.updateOrderState(orderId, OrderStateEnum.CANCEL);
                if (resultTag) {
                    result = 1;
                }
            } else if (( OrderConstant.ORDER_TYPE_ART.equals(orderType) ||  OrderConstant.ORDER_TYPE_BOE.equals(orderType))
                    && ( OrderStateEnum.NO_PAYMENT.getCode() == state ||  OrderStateEnum.CREATE.getCode() == state ||  OrderStateEnum.PROCESSING
                    .getCode() == state)) { // 艺术品
                boolean resultTag = orderProxy.updateOrderState(orderId, OrderStateEnum.CANCEL);
                if (resultTag) {
                    result = 1;
                }
                if (result == 1) {
                    OrderDtoVo orderDto = orderService.queryOrderDetail(orderId);
                    if (null != orderDto) {
                        String orderNum = orderDto.getOrderNum();
                        ajbSuccess = orderProxy.cancelPay(orderNum);
                    }
                }
            } else if ( OrderConstant.ORDER_TYPE_CLUTRUE.equals(orderType)
                    && ( OrderStateEnum.NO_PAYMENT.getCode() == state ||  OrderStateEnum.CREATE.getCode() == state ||  OrderStateEnum.PROCESSING
                    .getCode() == state)) { // 文旅商品
                boolean resultTag = orderProxy.updateOrderState(orderId, OrderStateEnum.CANCEL);
                if (resultTag) {
                    result = 1;
                }
                if (result == 1) {
                    OrderDtoVo orderDto = orderService.queryOrderDetail(orderId);
                    if (null != orderDto) {
                        String orderNum = orderDto.getOrderNum();
                        // 取消订单支付
                        List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
                        for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
                            Integer fidProduct = orderDetailDto.getFidProduct();
                            Integer productAmount = orderDetailDto.getProductAmount();
                            for (int i = 0; i < productAmount; i++) {
                                cultureService.updateUserPurchaseCultureRecord(orderDto.getFidUser(),
                                        fidProduct, 2);
                            }
                        }

                        ajbSuccess = orderProxy.cancelPay(orderNum);
                    }
                }
            } else {
                throw new BusinessException(HttpResponseCode.FAILED, "部分订单及状态不允许取消！");
            }

            if (OrderConstant.ORDER_TYPE_ART.equals(orderType) && OrderConstant.ORDER_OMSSTATUS_NO_PAYMENT.equals(state)
                    && result == 1) {
                if (!ajbSuccess) {
                    return;
                } else {
                    throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
                }
            } else if (OrderConstant.ORDER_TYPE_ART.equals(orderType)
                    && OrderConstant.ORDER_OMSSTATUS_NO_PAYMENT.equals(state) && result != 1) {
                throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
            } else if (result == 1) {
                return;
            } else if (result != -10) {
                throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
            }
        } catch (Exception e) {
            LOG.error("oms order query o2o-exception , more info :", e);
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }
    }

    @Override
    public OrderPayInfoForAlipayResponseVo getSubOrderforAlipayReform(HttpOrderRequest orderRequest) {
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Long orderId = orderRequest.getOrderId();
        // 2016年8月1日13:56:26接入oms系统
        OrderDtoVo order = new OrderDtoVo();
        try {
            LOG.info("OrderH5Controller getSubOrderforAlipayReform orderId:" + JsonUtils.obj2json(orderId));
            order = orderService.queryOrderDetail(Integer.parseInt(orderId.toString()));
            LOG.info("OrderH5Controller getSubOrderforAlipayReform order:" + JsonUtils.obj2json(order));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断订单是否存在
        if (order == null) {
            throw new BusinessException(HttpResponseCode.PRODUCT_ORDER_INVALID, MessageConstant.PRODUCT_ORDER_INVALID);
        }
        // 初始化支付参数
        PayInput payInput = new PayInput();
        payInput.setPlatform(orderRequest.getOsType());
        // 订单编号
        LOG.info("OrderH5Controller getSubOrderforAlipayReform OrderNum:" + order.getOrderNum());
        payInput.setOrderNum(order.getOrderNum());
        // 支付金额
        LOG.info("OrderH5Controller getSubOrderforAlipayReform Money:" + orderRequest.getSelectSum());
//		payInput.setMoney(orderRequest.getSelectSum());
        //		payInput.setMoney(requestVo.getSelectSum());
        if (!order.getOrderType().equals(5)) {
            payInput.setMoney(orderRequest.getSelectSum());
        } else {
            payInput.setMoney(order.getActualPayMent().doubleValue());
        }
        payInput.setActualPayMent(order.getActualPayMent());
        payInput.setTotalPayMent(order.getTotalPrice());
        // 设置支付异步回调
        payInput.setNotifyUrl(orderService.getSubAlipayNotifyUrl290());
        LOG.info("OrderH5Controller getSubOrderforAlipayReform NotifyUrl:" + orderService.getSubAlipayNotifyUrl290());
        // 订单来源
        payInput.setSource(2);
        payInput.setReturnUrl("m.alipay.com");
        // 请求支付
        payInput.setUserId(userDto.getId());
        payInput.setOrderType(order.getOrderType());
//		AppAlipayOutput data = orderProxy.appAlipay(payInput);

        String alipayPayStr = payforService.handlerOldAliPaySignAndRecord(payInput);

        if (null == alipayPayStr) {
            throw new BusinessException(HttpResponseCode.ORDER_PAY_FAILED, MessageConstant.ORDER_PAY_FAILED);
        }

        return new OrderPayInfoForAlipayResponseVo(order.getOrderNum(), alipayPayStr);
    }

}
