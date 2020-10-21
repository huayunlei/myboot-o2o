package com.ihomefnt.o2o.service.service.order;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.dao.configItem.ConfigItemDao;
import com.ihomefnt.o2o.intf.dao.order.OrderDao;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.configItem.dto.Item;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductDeliveryResponseVo;
import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderAuthRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.*;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticListResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderDetailResp;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderDetailDto;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderInfoDto;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.order.NewAndOldOrderInfoRoutingRule;
import com.ihomefnt.o2o.intf.manager.util.unionpay.UnopUtil;
import com.ihomefnt.o2o.intf.proxy.order.OrderHomeProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.kuaidi100.Kuaidi100Service;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.ordersnapshot.OrderSnapshotService;
import com.ihomefnt.o2o.service.manager.config.DictionaryConfig;
import com.ihomefnt.o2o.service.manager.config.OrderConfig;
import com.unionpay.acp.sdk.SDKConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shirely_geng on 15-1-22.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    OrderDao orderDao;

    @Autowired
    DictionaryConfig dictionaryConfig;


    @Autowired
    ConfigItemDao configItemDao;

    @Autowired
    OrderHomeProxy orderHomeProxy;

    @Autowired
    Kuaidi100Service kuaidi100Service;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderSnapshotService orderSnapshotService;
    @Autowired
    UserProxy userProxy;
    @Autowired
    PersonalCenterProxy personalCenterProxy;
    @Autowired
    OrderProxy orderProxy;

    @Autowired
    private OrderConfig orderConfig;

    @Override
    public TOrder queryOrderByOrderId(Long orderId) {
        return orderDao.queryOrderByOrderId(orderId);
    }

    @Override
    public TOrder queryMyOrderByOrderId(Long orderId) {
        return orderDao.queryMyOrderByOrderId(orderId);
    }

    @Override
    public List<Consignee> queryMyConsignee(Long userId) {
        List<Consignee> consigneeList = new ArrayList<Consignee>();
        consigneeList = orderDao.queryMyConsignee(userId);
        return consigneeList;
    }

    /**
     * create order number
     *
     * @return
     */
    public String createOrderNumber(Long orderid) {
        /**
         * create order serial number
         */
        orderDao.createOrderSerial(orderid);
        /**
         * get generated order serial number
         */
        OrderSerialNo serialNo = orderDao.queryOrderSerial(orderid);
        StringBuffer buffer = new StringBuffer("");
        if (serialNo != null) {
            buffer.append(serialNo.getOrderDay());
            DecimalFormat format = new DecimalFormat("000000");
            buffer.append(format.format(serialNo.getSerialNo()));
        } else {
            buffer.append(DateFormatUtils.format(new Date(), orderid + "yyyyMMddHHmmss"));
        }
        return buffer.toString();
    }

    private void updateOrderNumber(Long orderId, String serialNo) {
        orderDao.setOrderNumber(serialNo, orderId);
    }

    @Override
    public Double selPayedMoneyByOrderId(Long orderId) {
        return orderDao.selPayedMoneyByOrderId(orderId);
    }

    @Override
    public Double selPayedMoneyByOrderIdAndOrderStatus(Long orderId, Integer orderStatus) {
        return orderDao.selPayedMoneyByOrderIdAndOrderStatus(orderId, orderStatus);
    }

    @Override
    public List<OrderPayRecord> querySubOrderPay(Long orderId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderId", orderId);
        List<OrderPayRecord> orderPayRecords = orderDao.querySubOrderPay(param);

        if (null != orderPayRecords) {
            for (OrderPayRecord orderPayRecord : orderPayRecords) {
                if (orderPayRecord.getPayType() == 1) {
                    orderPayRecord.setPayTypeName("支付宝");
                    //协调ios前端
                    orderPayRecord.setPayType(0);
                }
                if (orderPayRecord.getPayType() == 2) {
                    orderPayRecord.setPayTypeName("微信");
                    //协调ios前端
                    orderPayRecord.setPayType(1);
                }
                Date date = orderPayRecord.getCreateTimeDate();

                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                c1.setTime(date);
                c2.setTime(new Date());
                String createTime = null;
                String week = null;
                //获取Calendar中各个属性字段的方法
                switch (c1.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        week = " 周日 ";
                        break;
                    case 2:
                        week = " 周一 ";
                        break;
                    case 3:
                        week = " 周二 ";
                        break;
                    case 4:
                        week = " 周三 ";
                        break;
                    case 5:
                        week = " 周四 ";
                        break;
                    case 6:
                        week = " 周五 ";
                        break;
                    case 7:
                        week = " 周六 ";
                        break;
                    default:
                        week = "";
                        break;
                }
                DecimalFormat format = new DecimalFormat("00");
                String month = format.format(c1.get(Calendar.MONTH) + 1L);
                String day = format.format(c1.get(Calendar.DAY_OF_MONTH));
                String hour = format.format(c1.get(Calendar.HOUR_OF_DAY));
                String minute = format.format(c1.get(Calendar.MINUTE));

                createTime = month + "月" + day + "日" + week + hour + ":" + minute;
                if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
                    createTime = c1.get(Calendar.YEAR) + "年" + createTime;
                }
                orderPayRecord.setCreateTime(createTime);
            }
        }

        return orderPayRecords;
    }

    @Override
    public Double queryPayedMoneyByOrderId(Long orderId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderId", orderId);
        return orderDao.queryPayedMoneyByOrderId(param);
    }

    @Override
    public List<Double> querySelSum(Long configId) {
        List<Double> selectSum = new ArrayList<Double>();
        List<Item> items = configItemDao.queryItemByConfigId(configId);
        if (items != null) {
            for (Item item : items) {
                selectSum.add(Double.parseDouble(item.getName()));
            }
        }
        return selectSum;
    }

    @Override
    public String getSubAlipayNotifyUrl290() {
        return dictionaryConfig.subAlipayNotifyUrl290;
    }

    @Override
    @SuppressWarnings("all")
    public LogisticListResponse queryOrderDeliveryInfoByOrderId(Integer orderId, Integer width) {
        if (orderId == null) {
            return null;
        } else {
            LogisticListResponse response = new LogisticListResponse();
            Integer fillCount = 0;//已发货商品件数
            OrderLogisticResultDto logisticResponseVo = orderHomeProxy.queryOrderDeliveryInfoByOrderId(orderId);
            if (logisticResponseVo != null) {
                response.setOrderId(logisticResponseVo.getOrderId());
                List<OrderProductDeliveryInfoDto> infos = logisticResponseVo.getInfos();
                if (CollectionUtils.isNotEmpty(infos)) {
                    List<LogisticResponse> logisticInfoList = new ArrayList<>();
                    Integer type = queryTypeFromInfos(infos);
                    if (type == 1) {//老订单
                        // 根据订单编号+订单类型查询订单商品明细
                        Map<Integer, OrderSnapshotProductResponse> orderProductMap = queryorderProductMapByOrderId(
                                logisticResponseVo.getOrderNum(), logisticResponseVo.getOrderType());
                        response.setOrderProductMap(orderProductMap);

                        List<OrderProductDeliveryInfoDto> infosCollect = new ArrayList<>();
                        Map<String, List<OrderProductDeliveryInfoDto>> infosMap = infos.stream().collect(Collectors.groupingBy(OrderProductDeliveryInfoDto::getDeliveryNumber));
                        infosMap.forEach((key, value) -> {
                            OrderProductDeliveryInfoDto orderProductDeliveryInfoDto1 = value.get(0);
                            List<Integer> productIds = new ArrayList<>();
                            for (OrderProductDeliveryInfoDto orderProductDeliveryInfoDto : value) {
                                productIds.addAll(orderProductDeliveryInfoDto.getProductIds());
                                orderProductDeliveryInfoDto1.setProductIds(productIds);
                            }
                            infosCollect.add(orderProductDeliveryInfoDto1);
                        });

                        for (OrderProductDeliveryInfoDto deliveryInfo : infosCollect) {
                            LogisticResponse logisticInfo = new LogisticResponse();
                            logisticInfo.setOrderId(logisticResponseVo.getOrderId());
                            logisticInfo.setLogisticCompanyCode(deliveryInfo.getDeliveryCode());
                            logisticInfo.setLogisticCompanyName(deliveryInfo.getDeliveryName());
                            logisticInfo.setLogisticNum(deliveryInfo.getDeliveryNumber());
                            //物流商品信息
                            List<Integer> productIds = deliveryInfo.getProductIds();
                            List<OrderDetailResp> orderDetailRespList = new ArrayList<>();
                            Integer goodsCount = 0;
                            if (CollectionUtils.isNotEmpty(productIds)) {
                                for (Integer productId : productIds) {
                                    //查询商品信息 快照信息
                                    OrderDetailResp orderDetailResp = new OrderDetailResp();

                                    OrderSnapshotProductResponse orderSnapshotProductResponse = orderProductMap
                                            .get(productId);

                                    if (orderSnapshotProductResponse != null) {
                                        orderDetailResp.setFidProduct(orderSnapshotProductResponse.getProductId());//商品ID
                                        orderDetailResp.setProductName(orderSnapshotProductResponse.getProductName());//商品名称
                                        if (orderSnapshotProductResponse.getProductPrice() != null) {
                                            orderDetailResp.setProductPrice(orderSnapshotProductResponse.getProductPrice());//商品单价
                                        }
                                        if (orderSnapshotProductResponse.getDeliveryTime() != null) {
                                            orderDetailResp.setDeliveryTime(orderSnapshotProductResponse.getDeliveryTime());
                                        }
                                        orderDetailResp.setProductCategoty(orderSnapshotProductResponse.getProductCategoty());//商品类型
                                        //商品头图
                                        String headImg = "";
                                        if (StringUtils.isNotBlank(orderSnapshotProductResponse.getProductImage())) {
                                            headImg = orderSnapshotProductResponse.getProductImage();
                                            if (width != null) {
                                                Integer imgWidth = width * ImageSize.WIDTH_PER_SIZE_33
                                                        / ImageSize.WIDTH_PER_SIZE_100;
                                                headImg = QiniuImageUtils.compressImageAndDiffPic(headImg,
                                                        imgWidth, -1);
                                            }
                                            orderDetailResp.setHeadImage(headImg);
                                        }
                                        orderDetailResp.setProductAmount(orderSnapshotProductResponse.getAmount());
                                        orderDetailRespList.add(orderDetailResp);
                                        if (orderSnapshotProductResponse.getAmount() != null) {
                                            fillCount = fillCount + orderSnapshotProductResponse.getAmount();
                                            goodsCount = goodsCount + orderSnapshotProductResponse.getAmount();
                                        }
                                    }
                                }
                            }
                            logisticInfo.setOrderDetailRespList(orderDetailRespList);
                            logisticInfo.setGoodsCount(goodsCount);
                            // 查询物流运输记录
                            List<KuaidiProductDeliveryResponseVo> logisticRecordList = new ArrayList<KuaidiProductDeliveryResponseVo>();

                            // 根据物流单号查询物流信息，先查询缓存若无查询快递100, 12*60*60 = 43200秒
                            KuaidiProductInfoResponseVo kuaidiProductInfo = null;
                            String key = "QUERY_KUAIDI_CACHE_" + deliveryInfo.getDeliveryCode() + "_"
                                    + deliveryInfo.getDeliveryNumber();

                            String result = AppRedisUtil.get(key);
                            if (StringUtil.isNotBlank(result)) {
                                kuaidiProductInfo = JSON.parseObject(result, KuaidiProductInfoResponseVo.class);
                            } else {
                                kuaidiProductInfo = kuaidi100Service.getKuaidiInfo(null, orderId, deliveryInfo.getDeliveryCode(),
                                        deliveryInfo.getDeliveryNumber());
                                if (null != kuaidiProductInfo) {
                                    AppRedisUtil.set(key, JSON.toJSONString(kuaidiProductInfo), 43200);
                                }
                            }

                            if (kuaidiProductInfo != null) {
                                List<KuaidiProductDeliveryResponseVo> kuaidiProductDeliveries = kuaidiProductInfo.getData();
                                if (CollectionUtils.isNotEmpty(kuaidiProductDeliveries)) {
                                    logisticRecordList.addAll(kuaidiProductDeliveries);
                                }
                                logisticInfo.setState(kuaidiProductInfo.getState());
                                logisticInfo.setStateDesc(kuaidiProductInfo.getStateDesc());
                            }
                            logisticInfo.setData(logisticRecordList);
                            logisticInfoList.add(logisticInfo);
                        }
                    } else {//新订单
                        OrderResponse or = orderService.queryArtOrderDetail(orderId);
                        List<OrderDetailResp> orderDetailRespList = or.getOrderDetailRespList();
                        List<OrderProductDeliveryInfoDto> infosCollect = new ArrayList<>();

                        Map<String, List<OrderProductDeliveryInfoDto>> infosMap = infos.stream().collect(Collectors.groupingBy(OrderProductDeliveryInfoDto::getDeliveryNumber));
                        infosMap.forEach((key, value) -> {
                            OrderProductDeliveryInfoDto orderProductDeliveryInfoDto1 = value.get(0);
                            List<String> skuIds = new ArrayList<>();
                            for (OrderProductDeliveryInfoDto orderProductDeliveryInfoDto : value) {
                                skuIds.addAll(orderProductDeliveryInfoDto.getSkuIds());
                                orderProductDeliveryInfoDto1.setSkuIds(skuIds);
                            }
                            infosCollect.add(orderProductDeliveryInfoDto1);
                        });

                        for (OrderProductDeliveryInfoDto deliveryInfo : infosCollect) {
                            LogisticResponse logisticInfo = new LogisticResponse();
                            logisticInfo.setOrderId(logisticResponseVo.getOrderId());
                            logisticInfo.setLogisticCompanyCode(deliveryInfo.getDeliveryCode());
                            logisticInfo.setLogisticCompanyName(deliveryInfo.getDeliveryName());
                            logisticInfo.setLogisticNum(deliveryInfo.getDeliveryNumber());
                            //物流商品信息
                            Integer goodsCount = 0;
                            List<OrderDetailResp> orderDetailRespListNew = null;
                            if (CollectionUtils.isNotEmpty(orderDetailRespList)) {
                                orderDetailRespListNew = new ArrayList();
                                for (OrderDetailResp orderDetailResp : orderDetailRespList) {
                                    if (CollectionUtils.isNotEmpty(deliveryInfo.getSkuIds())) {
                                        for (String skuId : deliveryInfo.getSkuIds()) {
                                            if (orderDetailResp.getSkuId().equals(skuId)) {
                                                String headImage = orderDetailResp.getHeadImage();
                                                if (org.apache.commons.lang.StringUtils.isNotBlank(headImage)) {
                                                    headImage = headImage.split(",")[0];
                                                }
                                                orderDetailResp.setHeadImage(AliImageUtil.imageCompress(orderDetailResp.getHeadImage(), 2, width, ImageConstant.SIZE_SMALL));
                                                orderDetailRespListNew.add(orderDetailResp);
                                                fillCount = fillCount + orderDetailResp.getProductAmount();
                                                goodsCount = goodsCount + orderDetailResp.getProductAmount();
                                            }
                                        }
                                    }

                                }
                            }

                            logisticInfo.setOrderDetailRespList(orderDetailRespListNew);
                            logisticInfo.setGoodsCount(goodsCount);
                            // 查询物流运输记录
                            List<KuaidiProductDeliveryResponseVo> logisticRecordList = new ArrayList<KuaidiProductDeliveryResponseVo>();

                            // 根据物流单号查询物流信息，先查询缓存若无查询快递100, 12*60*60 = 43200秒
                            KuaidiProductInfoResponseVo kuaidiProductInfo = null;
                            String key = "QUERY_KUAIDI_CACHE_" + deliveryInfo.getDeliveryCode() + "_"
                                    + deliveryInfo.getDeliveryNumber();

                            String result = AppRedisUtil.get(key);
                            if (StringUtil.isNotBlank(result)) {
                                kuaidiProductInfo = JSON.parseObject(result, KuaidiProductInfoResponseVo.class);
                            } else {
                                kuaidiProductInfo = kuaidi100Service.getKuaidiInfo(null, orderId, deliveryInfo.getDeliveryCode(),
                                        deliveryInfo.getDeliveryNumber());
                                if (null != kuaidiProductInfo) {
                                    AppRedisUtil.set(key, JSON.toJSONString(kuaidiProductInfo), 43200);
                                }
                            }

                            if (kuaidiProductInfo != null) {
                                List<KuaidiProductDeliveryResponseVo> kuaidiProductDeliveries = kuaidiProductInfo.getData();
                                if (CollectionUtils.isNotEmpty(kuaidiProductDeliveries)) {
                                    logisticRecordList.addAll(kuaidiProductDeliveries);
                                }
                                logisticInfo.setState(kuaidiProductInfo.getState());
                                logisticInfo.setStateDesc(kuaidiProductInfo.getStateDesc());
                            }
                            logisticInfo.setData(logisticRecordList);
                            logisticInfoList.add(logisticInfo);
                        }

                    }

                    response.setLogisticList(logisticInfoList);
                }

                //总件数、未发货件数
                if (logisticResponseVo.getProductCount() != null && logisticResponseVo.getProductCount() > 0) {
                    response.setProductCount(logisticResponseVo.getProductCount());
                    response.setFilledCount(fillCount);
                    response.setUnfilledCount(logisticResponseVo.getProductCount() - fillCount);
                } else {
                    response.setProductCount(0);
                    response.setFilledCount(0);
                    response.setUnfilledCount(0);
                }
            }
            return response;
        }
    }

    /**
     * 根据infos区分类型 1老订单 2艾商城新订单
     *
     * @param infos
     * @return
     */
    private Integer queryTypeFromInfos(List<OrderProductDeliveryInfoDto> infos) {
        for (OrderProductDeliveryInfoDto info : infos) {
            if (CollectionUtils.isNotEmpty(info.getSkuIds())) {
                return 2;
            } else {
                return 1;
            }
        }
        return 1;
    }

    private Map<Integer, OrderSnapshotProductResponse> queryorderProductMapByOrderId(String orderNum, Integer orderType) {
        Map<Integer, OrderSnapshotProductResponse> orderProductMap = new HashMap<>();

        OrderSnapshotResponse response = orderSnapshotService.querySnapshotsByOrderNum(orderNum, orderType);

        if (response != null && !CollectionUtils.isEmpty(response.getProductInfos())) {
            List<OrderSnapshotProductResponse> list = response.getProductInfos();
            for (OrderSnapshotProductResponse snapshotResponse : list) {
                orderProductMap.put(snapshotResponse.getProductId(), snapshotResponse);
            }
        }
        return orderProductMap;
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


    @Override
    public String goUnionPay(Long orderId, String accessToken, String host) {
        UserDto userDto = userProxy.getUserByToken(accessToken);
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.USER_NOT_LOGIN);
        }

        TOrder order = this.queryOrderByOrderId(orderId);
        if (order == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.QUERY_FAILED);
        }

        SDKConfig.getConfig().loadPropertiesFromSrc();
        /**
         * 组装请求报文
         */
        Map<String, String> data = new HashMap<String, String>();

        // 查询是否有部分付款
        Double payedMoney = this.selPayedMoneyByOrderId(orderId);
        Double totalFee = order.getOrderPrice();
        if (null != payedMoney) {
            totalFee = totalFee - payedMoney;
        }
        long l1 = Math.round(totalFee * 100);
        String total_fee = l1 + "";// 付款金额

        // 版本号
        data.put("version", "5.0.0");
        // 字符集编码 默认"UTF-8"
        data.put("encoding", "UTF-8");
        // 签名方法 01 RSA
        data.put("signMethod", "01");
        // 交易类型 01-消费
        data.put("txnType", "01");
        // 交易子类型 01:自助消费 02:订购 03:分期付款
        data.put("txnSubType", "01");
        // 业务类型
        data.put("bizType", "000201");
        // 渠道类型，07-PC，08-手机
        data.put("channelType", "08");
        // 前台通知地址 ，控件接入方式无作用
        data.put("frontUrl", host + "/order/goUnionOrderDetails?accessToken=" + accessToken + "&ordId=" + orderId);
        // 后台通知地址
        data.put("backUrl", host + "/order/backUnionNotify");
        // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
        data.put("accessType", "0");
        // 商户号码，请改成自己的商户号700000000000001
//					data.put("merId", "777290058118484");
        data.put("merId", "700000000000001");
        // 商户订单号，8-40位数字字母
        data.put("orderId", order.getOrderNum());
        // 订单发送时间，取系统时间
        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        // 交易金额，单位分
        data.put("txnAmt", total_fee);
        // 交易币种
        data.put("currencyCode", "156");
        //订单支付过程的超时时间为6分钟
//					data.put("orderTimeout", "360000");

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
        String html = UnopUtil.createHtml(requestFrontUrl, UnopUtil.signData(data));
        return html;
    }

    @Override
    public OrderDtoVo queryOrderDetail(Integer orderId) {
        int orderSystemSource = NewAndOldOrderInfoRoutingRule.orderSystemRoutingRule(orderId);
        if (OrderConstants.ORDER_SYSTEM_SOURCE_ALADDIN == orderSystemSource) {
            AppMasterOrderDetailDto dto = personalCenterProxy.queryMasterOrderDetail(orderId);
            if (null == dto) {
                return null;
            }

            OrderDtoVo orderDtoVo = new OrderDtoVo();
            orderDtoVo.setOrderNum(dto.getOrderNum().toString());
            orderDtoVo.setOrderId(dto.getOrderNum());
            AppMasterOrderInfoDto masterOrderInfoDto = dto.getMasterOrderInfo();
            if (null != masterOrderInfoDto) {
                orderDtoVo.setFidUser(masterOrderInfoDto.getUserId());
                orderDtoVo.setOrderType(masterOrderInfoDto.getOrderType());
                orderDtoVo.setActualPayMent(masterOrderInfoDto.getContractAmount());
            }
            return orderDtoVo;
        } else if (OrderConstants.ORDER_SYSTEM_SOURCE_OMS == orderSystemSource) {
            return orderProxy.queryOmsOrderDetail(orderId);
        }
        return null;
    }

    /**
     * 艾商城新版订单详情
     *
     * @param orderId 订单Id
     * @return
     */
    @Override
    public OrderResponse queryArtOrderDetail(Integer orderId) {
        return orderProxy.queryOmsArtOrderDetail(orderId);
    }

    /**
     * 查询订单列表信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderDtoVo> queryAlladdinOrderList(Integer userId) {

        Map<String, Object> stringObjectMap = concurrentOrderQueryList(userId);
        List<AppMasterOrderResultDto> masterOrderList = (List<AppMasterOrderResultDto>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_MASTER_ORDER_LIST_BY_ID.name());
        List<OrderDtoVo> orderDtoVos = (List<OrderDtoVo>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_OMS_ORDER_INFO.name());

        if (null == masterOrderList) {
            masterOrderList = new ArrayList<>();
        }

        List<OrderDtoVo> orderVoList = new ArrayList<>();

        Set<Integer> masterOrderIdSet = new HashSet<>();
        for (AppMasterOrderResultDto item : masterOrderList) {
            if (null == item || null == item.getCustomerHouseId()) {
                continue;
            }
            masterOrderIdSet.add(item.getMasterOrderId());
        }

        // 查询每个大订单是否自由搭配
        for (AppMasterOrderResultDto item : masterOrderList) {
            OrderDtoVo orderResultDto = new OrderDtoVo();
            orderResultDto.setOrderId(item.getMasterOrderId());
            orderResultDto.setOrderType(item.getOrderType());
            orderResultDto.setState(item.getOrderStatus());
            orderResultDto.setActualPayMent(item.getContractAmount());
            orderResultDto.setCreateTime(item.getCreateTime());
            orderResultDto.setSource(item.getSource());
            if (null != item.getUserId()) {
                orderResultDto.setFidUser(item.getUserId());
            }
            if (null != item.getBuildingId()) {
                orderResultDto.setProjectId(item.getBuildingId());
                orderResultDto.setFidProject(item.getBuildingId());
            }

            // 查询大订单是否是自由搭配，等待批量查询接口
            if (null != item.getSuitType()) {
                orderResultDto.setAutoMatch(0 == item.getSuitType());
            }
            orderVoList.add(orderResultDto);
        }
        if (null == orderDtoVos) {
            orderDtoVos = new ArrayList<>();
        }
        orderDtoVos.forEach(item -> {
            // 老订单只查艺术品和文旅商品,团购商品和画屏商品
            if (!item.getState().equals(3) && (item.getOrderType() == 5 || item.getOrderType() == 6 || item.getOrderType() == 16
                    || item.getOrderType() == 17)) {
                orderVoList.add(item);
            }
        });

        return orderVoList;
    }


    /**
     * 查询大订单+艺术品订单信息
     *
     * @param userId
     * @return
     */
    private Map<String, Object> concurrentOrderQueryList(Integer userId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

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
                return queryOrderInfo(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_OMS_ORDER_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    @Override
    public OrderPayBalanceVo getOrderPayBalance(String orderNum) {
        OrderPaidInfoDto orderPaidInfoDto = orderProxy.queryOrderPaidInfo(orderNum);
        if (null == orderPaidInfoDto) {
            return null;
        }
        OrderDtoVo orderDtoVo = orderProxy.queryOrderDetailByOrderNum(orderNum);

        OrderPayBalanceVo orderPayBalanceVo = new OrderPayBalanceVo();
        orderPayBalanceVo.setBalance(orderDtoVo == null ? BigDecimal.ZERO : orderDtoVo.getActualPayMent().subtract(orderPaidInfoDto.getPaidSum()));
        return orderPayBalanceVo;
    }

    @Override
    public OrderAuthResponseVo orderAuth(OrderAuthRequestVo orderAuthRequestVo) {
        if (isNeedOrderAuth(orderAuthRequestVo)) {
            String key = AppRedisUtil.generateCacheKey("order_auth", orderAuthRequestVo.getRequestURI(), orderAuthRequestVo.getOrderId(), orderAuthRequestVo.getUserId());
            Boolean orderAuthResult = JsonUtils.json2obj(AppRedisUtil.get(key), Boolean.class);

            if (null == orderAuthResult) {
                orderAuthResult = getOrderAuthResult(orderAuthRequestVo.getUserId(), orderAuthRequestVo.getOrderId());
                AppRedisUtil.set(key, String.valueOf(orderAuthResult), 3600);
            }

            if (!orderAuthResult) {
                // 订单鉴权不通过
                return OrderAuthResponseVo.builder().build().setResult(false);
            }
        }
        return OrderAuthResponseVo.builder().build().setResult(true);
    }

    private boolean getOrderAuthResult(Integer userId, String orderId) {
        if (null != userId && userId > 0) {
            List<OrderBaseInfoDto> orderDtoList = orderProxy.queryOrderListByUserId(userId);
            if (org.springframework.util.CollectionUtils.isEmpty(orderDtoList)) {
                return false;
            }
            for (OrderBaseInfoDto orderDto : orderDtoList) {
                String orderDtoId = orderDto.getOrderNo();
                String orderDtoNum = orderDto.getOrderNum().toString();
                if (null != orderId) {
                    if (orderId.equals(orderDtoId) || orderId.equals(orderDtoNum)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isNeedOrderAuth(OrderAuthRequestVo orderAuthRequestVo) {
        // 没有有效订单号
        if (!this.isOrderNum(orderAuthRequestVo.getOrderId())) {
            return false;
        }
        // 白名单
        List<String> orderNumLists = orderConfig.getOrderNumAuthWhiteLists();
        if (!org.springframework.util.CollectionUtils.isEmpty(orderNumLists)) {
            if (orderNumLists.contains(orderAuthRequestVo.getOrderId())) {
                return false;
            }
        }

        List<OrderAuthWhiteListDto> list = orderConfig.getOrderAuthWhiteLists();
        if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
            for (OrderAuthWhiteListDto dto : list) {
                if (StringUtils.isNotBlank(dto.getUrl()) && orderAuthRequestVo.getRequestURI().equals(dto.getUrl())) {
                    if (!org.springframework.util.CollectionUtils.isEmpty(dto.getIds()) && !dto.getIds().contains(orderAuthRequestVo.getOrderId())) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOrderNum(String orderNum) {
        if (null == orderNum) {
            return false;
        }
        int orderId = 0;
        try {
            orderId = Integer.parseInt(orderNum);
        } catch (Exception e) {
            return false;
        }
        return orderId > 0;
    }

    /**
     * 查询oms订单
     *
     * @param userId
     * @return
     */
    public List<OrderDtoVo> queryOrderInfo(Integer userId) {
        OrderInfoSearchDto condition = new OrderInfoSearchDto();
        condition.setUserId(userId);
        condition.setPageSize(300);
        condition.setPageNo(1);
        condition.setMark(1);
        PageModel<OrderDtoVo> pageModel = orderProxy.queryOrderInfo(condition);
        if (null == pageModel) {
            return new ArrayList<>();
        }

        List<OrderDtoVo> orderDtoVoList = pageModel.getList();
        if (null == orderDtoVoList) {
            orderDtoVoList = new ArrayList<>();
        }
        return orderDtoVoList;
    }

}