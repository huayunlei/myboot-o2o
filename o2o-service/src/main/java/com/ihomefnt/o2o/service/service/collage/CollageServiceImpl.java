package com.ihomefnt.o2o.service.service.collage;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkImage;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.*;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.*;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticListResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderDetailResp;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.order.ArtOrderUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.proxy.art.ArtProxy;
import com.ihomefnt.o2o.intf.proxy.collage.CollageProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.artcomment.ArtCommentService;
import com.ihomefnt.o2o.intf.service.building.BuildingService;
import com.ihomefnt.o2o.intf.service.collage.CollageService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.ordersnapshot.OrderSnapshotService;
import com.ihomefnt.o2o.intf.service.pay.PayforService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.MODULE_CODE_FGW;

/**
 * 艺术品团活动服务类
 * 控制层统一到用此类
 * @author jerfan cang
 * @date 2018/10/16 9:09
 */
@Service
public class CollageServiceImpl implements CollageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollageServiceImpl.class);

    @Autowired
    private CollageProxy collageProxy;
    @Autowired
    private ArtProxy artDao;
    @Autowired
    private UserProxy userProxy;

    @Autowired
    OrderProxy orderProxy;
    @Autowired
    OrderService orderService;
    @Autowired
    AreaService areaService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    ArtCommentService artCommentService;
    @Autowired
    OrderSnapshotService orderSnapshotService;
    @Autowired
    FilterProductConfig filterProductConfig;

    /**
     * 统一支付服务
     */
    @Autowired
    PayforService payforService;

    @Override
    public MainPageVo queryCollageActivity(QueryCollageActivityDetailRequest req) {
        Object aijiaDescInfo = new String("艾佳生活创立于2015年，通过整合地产、设计师、品牌硬装、品牌家居等社会资源，打造大家居互联网生态圈。本着“从房子到家，成就美好生活”的使命，为业主提供包括硬装、软装的设计、施工、验收和维保的全流程装修服务，以及家具、电器、艺术品家饰等全品类产品的采购与配送服务。目前，艾佳生活已获得10亿元B轮融资，服务全国18个省的160余楼盘，服务用户逾2万。");

        //查询活动详情
        GroupBuyActivityVo groupBuyActivityVo =  null;
        GroupBuyActivityDto groupBuyActivityDto = collageProxy.queryCollageActivityDetail(req.getActivityId());
        if (null != groupBuyActivityDto) {
        	groupBuyActivityVo = JsonUtils.json2obj(JsonUtils.obj2json(groupBuyActivityDto),GroupBuyActivityVo.class);
        	// headImage 图片处理  activityId 为空则给700
        	groupBuyActivityVo.value_activityRules2activityRuleList();
        }
        //查询商品详情
        ProductVo productVo = null;
        ProductDto productDto = collageProxy.queryActivityProduct(req.getActivityId());
        if (null != productDto) {
        	productVo = JsonUtils.json2obj(JsonUtils.obj2json(productDto),ProductVo.class);
        	productVo.markColorList();
        }
        //查询用户数据
        Integer width = req.getWidth() == null ? 700 : req.getWidth();
        UserInfoVo userInfoVo  = null;
        UserInfoDto userInfoDto = collageProxy.queryUserInfoByOpenId(req.getOpenid(), width);
        if (null != userInfoDto) {
        	userInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(userInfoDto),UserInfoVo.class);
        	if(null != userInfoVo.getHeadImg()){
        		String headImg  = QiniuImageUtils.compressImageAndSamePicTwo(userInfoVo.getHeadImg(),width,width);
        		userInfoVo.setHeadImg(headImg);
        	}
        }
        MainPageVo mainPageVo= new MainPageVo(groupBuyActivityVo,productVo,aijiaDescInfo,userInfoVo);
        return  mainPageVo;
    }


    @Override
    public CollageMasterResponseVo queryCollage(QueryCollageMasterRequest req)  {
        //活动信息
    	GroupBuyActivityVo groupBuyActivityVo =  null;
        GroupBuyActivityDto groupBuyActivityDto = collageProxy.queryCollageActivityDetail(req.getActivityId());
        if (null != groupBuyActivityDto) {
        	groupBuyActivityVo = JsonUtils.json2obj(JsonUtils.obj2json(groupBuyActivityDto),GroupBuyActivityVo.class);
        	// headImage 图片处理  activityId 为空则给700
        	groupBuyActivityVo.value_activityRules2activityRuleList();
        }
        // 活动商品信息
        ProductVo productVo = null;
        ProductDto productDto = collageProxy.queryActivityProduct(req.getActivityId());
        if (null != productDto) {
        	productVo = JsonUtils.json2obj(JsonUtils.obj2json(productDto),ProductVo.class);
        	productVo.markColorList();
        }
        // 团信息
        CollageInfoVo collageInfoVo = null;
        CollageInfoDto collageInfoDto = collageProxy.queryCollageInfoById(req.getGroupId());
        if (null != collageInfoDto) {
        	collageInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(collageInfoDto), CollageInfoVo.class);
        }
        //查询用户数据
        Integer width = req.getWidth() == null ? 700 : req.getWidth();
        UserInfoVo userInfoVo  = null;
        UserInfoDto userInfoDto = collageProxy.queryUserInfoByOpenId(req.getOpenid(), width);
        if (null != userInfoDto) {
        	userInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(userInfoDto),UserInfoVo.class);
        	if(null != userInfoVo.getHeadImg()){
        		String headImg  = QiniuImageUtils.compressImageAndSamePicTwo(userInfoVo.getHeadImg(),width,width);
        		userInfoVo.setHeadImg(headImg);
        	}
        }
        //返回数据
        CollageMasterResponseVo collageMasterResponseVo =
                new CollageMasterResponseVo(groupBuyActivityVo,productVo,collageInfoVo,userInfoVo);

        return collageMasterResponseVo;
    }

    @Override
    public ProductDetailResponseVo queryProductDetail(QueryProductDetailRequest  req)  {
    	//活动信息
    	GroupBuyActivityVo groupBuyActivityVo =  null;
        GroupBuyActivityDto groupBuyActivityDto = collageProxy.queryCollageActivityDetail(req.getActivityId());
        if (null != groupBuyActivityDto) {
        	groupBuyActivityVo = JsonUtils.json2obj(JsonUtils.obj2json(groupBuyActivityDto),GroupBuyActivityVo.class);
        	// headImage 图片处理  activityId 为空则给700
        	groupBuyActivityVo.value_activityRules2activityRuleList();
        }
        // 活动商品信息
        ProductVo productVo = null;
        ProductDto productDto = collageProxy.queryActivityProduct(req.getActivityId());
        if (null != productDto) {
        	productVo = JsonUtils.json2obj(JsonUtils.obj2json(productDto),ProductVo.class);
        	productVo.markColorList();
            List<ArtworkImage> imageList =  artDao.getArtworkImages(Long.parseLong(productVo.getProductId()+""));
            if(null != productVo && null !=imageList && imageList.size() > 0){
                productVo.setImageList(imageList);
            }
        }

        CollageInfoVo collageInfoVo = null;
        if (null != req.getGroupId()) {
        	// 团信息
            CollageInfoDto collageInfoDto = collageProxy.queryCollageInfoById(req.getGroupId());
            if (null != collageInfoDto) {
            	collageInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(collageInfoDto), CollageInfoVo.class);
            }
        }

        //查询用户数据
        Integer width = req.getWidth() == null ? 700 : req.getWidth();
        UserInfoVo userInfoVo  = null;
        UserInfoDto userInfoDto = collageProxy.queryUserInfoByOpenId(req.getOpenid(), width);
        if (null != userInfoDto) {
        	userInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(userInfoDto),UserInfoVo.class);
        	if(null != userInfoVo.getHeadImg()){
        		String headImg  = QiniuImageUtils.compressImageAndSamePicTwo(userInfoVo.getHeadImg(),width,width);
        		userInfoVo.setHeadImg(headImg);
        	}
        }
        
        ProductDetailResponseVo productDetailResponse = new ProductDetailResponseVo(groupBuyActivityVo,productVo,
                collageInfoVo,userInfoVo);
        return productDetailResponse;
    }

    @Override
    public CollageOrderResponseVo createCollageOrder(CreateCollageOrderRequest req , HttpServletRequest httpServletRequest)  {
        // 如果使用艾佳币支付 需要先校验是否是艾佳用户
        if(req.isPayMark()){
            HttpUserInfoRequest uservo = req.getUserInfo();
            if( !uservo.getMobile().equals(req.getMobile())){
            	throw new BusinessException("您还不是艾佳用户，不能使用艾积分支付");
            }
        }
        CreateCollageOrderParam orderParam = JsonUtils.json2obj(JsonUtils.obj2json(req),CreateCollageOrderParam.class);
        //如果groupId 为空 或者 ==0 表示新开团
        if(null !=orderParam.getGroupId() && 0==orderParam.getGroupId()){
            orderParam.setGroupId(null);
        }
        CreateCollageOrderDto orderResponse = collageProxy.createCollageOrder(orderParam);
        BigDecimal zero = BigDecimal.valueOf(0d);
        WechatPayResponseVo  wechatResponse = null;
        if(req.isPayMark() && zero.equals(req.getTrueAmount())){
            // 全部使用艾积分 支付 不拉起支付，返回订单号 表示下单成功
            // 不拉起支付
        }else{
            // 拉起支付 返回订单号和支付信息 需要客户端去支付
            try{
                Object obj = payforService.pay(configPayforParam(req,orderResponse,httpServletRequest));
                wechatResponse = JsonUtils.json2obj(JsonUtils.obj2json(obj),WechatPayResponseVo.class);
            }catch (Exception e){
                final CancelCollageOrderRequest cancelParam = new CancelCollageOrderRequest(
                        req.getOpenid(),req.getActivityId(),orderResponse.getOrderId(),req.getReceiverTel());
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            cancelCollageOrder(cancelParam);
                        }catch (Exception e){
                            LOGGER.error("oms-web.art-order.artGroupCancel exception , more info is :{} ", e);
                        }
                    }
                }).start();
                throw new BusinessException("微信公众号拉起支付失败");
            }
        }
        CollageOrderResponseVo collageOrderResponseVo = new CollageOrderResponseVo(wechatResponse ,orderResponse.getOrderId());
        return collageOrderResponseVo;
    }

    @Override
    public CancelCollageOrderResponseVo cancelCollageOrder(CancelCollageOrderRequest req)  {
        try{
            boolean cancelMark = collageProxy.cancelCollageOrder(
                    new CancelCollageOrderParam(req.getOrderId(),
                            "拼团支付失败自动取消订单",
                            "7",req.getReceiveTel()
                    ));
            CancelCollageOrderResponseVo orderResponse = new CancelCollageOrderResponseVo(req.getOrderId(),cancelMark);
            return orderResponse;
        }catch (Exception e){
            throw new BusinessException("取消订单失败");
        }
    }

    @Override
    public OrderPayResultResponseVo queryCollageOrder(QueryPayRequest req)  {
    	//活动信息
    	GroupBuyActivityVo groupBuyActivityVo =  null;
        GroupBuyActivityDto groupBuyActivityDto = collageProxy.queryCollageActivityDetail(req.getActivityId());
        if (null != groupBuyActivityDto) {
        	groupBuyActivityVo = JsonUtils.json2obj(JsonUtils.obj2json(groupBuyActivityDto),GroupBuyActivityVo.class);
        	// headImage 图片处理  activityId 为空则给700
        	groupBuyActivityVo.value_activityRules2activityRuleList();
        }
        //查询用户数据
        Integer width = req.getWidth() == null ? 700 : req.getWidth();
        UserInfoVo userInfoVo  = null;
        UserInfoDto userInfoDto = collageProxy.queryUserInfoByOpenId(req.getOpenid(), width);
        if (null != userInfoDto) {
        	userInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(userInfoDto),UserInfoVo.class);
        	if(null != userInfoVo.getHeadImg()){
        		String headImg  = QiniuImageUtils.compressImageAndSamePicTwo(userInfoVo.getHeadImg(),width,width);
        		userInfoVo.setHeadImg(headImg);
        	}
        }
        // 团信息
        CollageInfoVo collageInfoVo = null;
        if (userInfoVo != null) {
            CollageInfoDto collageInfoDto = collageProxy.queryCollageInfoById(userInfoVo.getGroupId());
            if (null != collageInfoDto) {
                collageInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(collageInfoDto), CollageInfoVo.class);
            }
        }

        OrderPayResultResponseVo payResultResponseVo = new OrderPayResultResponseVo(groupBuyActivityVo,collageInfoVo);
        return payResultResponseVo;
    }


    @Override
    public ProductFilterListResponseVo addSkuId2ProductFilterList(List<Integer> ids )  {
        Set<Integer> set =  filterProductConfig.addFilterProduct(ids);
        ProductFilterListResponseVo productFilterListResponseVo = new ProductFilterListResponseVo(set);
        return productFilterListResponseVo;
    }

    @Override
    public ProductFilterListResponseVo moveSkuId2ProductFilterList(List<Integer> ids)  {
        Set<Integer> set = filterProductConfig.removeFilterProduct(ids);
        ProductFilterListResponseVo productFilterListResponseVo = new ProductFilterListResponseVo(set);
        return productFilterListResponseVo;
    }

    @Override
    public ProductFilterListResponseVo loadSkuId2ProductFilterList()  {
        Set<Integer> set =filterProductConfig.getFilterProduct();
        ProductFilterListResponseVo productFilterListResponseVo = new ProductFilterListResponseVo(set);
        return productFilterListResponseVo;
    }

    private JSONObject configPayforParam(CreateCollageOrderRequest req,
    									CreateCollageOrderDto orderVo,
                                         HttpServletRequest httpServletRequest) {
        JSONObject json = new JSONObject();
        json.put("openId",req.getOpenid());
        try{
            OrderDtoVo order = null;
            order = orderService.queryOrderDetail(orderVo.getOrderId());
            if(null != order && null != order.getOrderNum() && !"".equals(order.getOrderNum())){
                json.put("orderNum",order.getOrderNum());
                if(order.getActualPayMent()!=null && order.getActualPayMent().compareTo(BigDecimal.ZERO)>0){
                    json.put("actualPayMent",order.getActualPayMent());
                }
            }
        }catch (Exception e){
            throw new BusinessException("查询orderNum异常");
        }

        json.put("orderType",new Integer(16));
        json.put("userId",orderVo.getUserId());
        //bean.put("actualPayMent",req.getTrueAmount());
        json.put("totalPayMent", BigDecimal.valueOf(59d));

        json.put("source",4);
        json.put("channelSource",new Integer(4)); //微信公众号支付 渠道4
        json.put("ip",IpUtils.getIpAddr(httpServletRequest));
        json.put("showUrl",null);
        json.put("returnUrl",null);

        json.put("notifyUrl",null);
        json.put("outTradeNo",null);
        json.put("orderInfo",null);
        json.put("acctName",null);
        json.put("cardNo",null);

        json.put("idNo",null);
        json.put("riskItem",null);
        json.put("busiPartner",null);
        json.put("orderDt",null);
        json.put("goodsName","大布丁保温杯");

        json.put("signType",null);

        json.put("oidPartner",null);
        json.put("sign",null);
        // 业务编码
        json.put("moduleCode",MODULE_CODE_FGW);
        json.put("platform",req.getOsType());

        return json;
    }


	@Override
	public OrderResponse queryCollageOrderDetail(QueryCollageOrderDetailRequest req) {
		checkUserCollage(req.getOpenid(), req.getWidth(), req.getOrderId());
        
        OrderResponse orderResponse = queryOrderDetail(req.getOrderId(),req.getWidth());
        if(null == orderResponse){
        	throw new BusinessException("未查询到拼团订单详情");
        }
        orderResponse = markProductImage(orderResponse);
		return orderResponse;
	}


	private void checkUserCollage(String openid, Integer width, Integer orderId) {
		if (null == width) {
			width = 700;
		}
		//查询用户数据
        UserInfoVo userInfoVo  = null;
        UserInfoDto userInfoDto = collageProxy.queryUserInfoByOpenId(openid, width);
        if (null != userInfoDto) {
        	userInfoVo = JsonUtils.json2obj(JsonUtils.obj2json(userInfoDto),UserInfoVo.class);
        	if(null != userInfoVo.getHeadImg()){
        		String headImg  = QiniuImageUtils.compressImageAndSamePicTwo(userInfoVo.getHeadImg(),width,width);
        		userInfoVo.setHeadImg(headImg);
        	}
        }
        if(userInfoVo == null){
        	throw new BusinessException("用户未参与活动");
        }else if(userInfoVo.getOrderNum() == null){
        	throw new BusinessException("用户不存在团购订单");
        }else if(!userInfoVo.getOrderNum().equals(orderId)){
        	throw new BusinessException("订单不属于该用户");
        }
	}
	
	private OrderResponse queryOrderDetail(Integer orderId,Integer width) {
        try{
            OrderDtoVo orderDto = orderService.queryOrderDetail(orderId);
            if (null == orderDto) {
            	throw new BusinessException("查询拼团订单详情异常");
            }
            
            Integer fidArea = orderDto.getFidArea();
            BigDecimal actualPayMent = orderDto.getActualPayMent();

            OrderResponse or = setOrderResponseForBaseInfo(orderDto);
            setOrderResponseForLogisticListResponse(or, orderDto, orderId, width);
            setOrderResponseForReceiverAddress(or, orderDto, fidArea);

            // 查询订单未支付金额
            OrderPayBalanceVo balanceVo = orderService.getOrderPayBalance(orderDto.getOrderNum());

            if (null == balanceVo) {
            	return null;
            }
            BigDecimal balance = balanceVo.getBalance();
            BigDecimal paidMoney = actualPayMent.subtract(balance);
            or.setUnpaidMoney(balance);
            or.setPaidMoney(paidMoney);

            Map<Integer,Boolean> commentMap = setOrderResponseForCouponAndVoucher(or, orderDto, orderId);
            setOrderResponseForOrderDetailResp(or, orderDto, commentMap, width);

            /**
             * 算出:预计发货时间的倒计时[待发货状态的艺术品订单才有的]
             */
            Integer deliveryTime = ArtOrderUtil.getDeliveryTime(or, orderDto);
            or.setDeliveryTime(deliveryTime);

            // cangjifeng
            or.setRemark(orderDto.getRemark());
            return or;
        }catch (Exception e){
            throw new BusinessException("查询拼团订单详情异常");
        }
    }

    private void setOrderResponseForOrderDetailResp(OrderResponse or, OrderDtoVo orderDto,
			Map<Integer, Boolean> commentMap, Integer width) {
    	List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
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

                if (null != productId) {
                    //订单快照
                    String orderNum = orderDto.getOrderNum();
                    Integer orderType = orderDto.getOrderType();
                    OrderSnapshotProductResponse orderSnapshotProductResponse = orderSnapshotService.queryProductInfo(orderNum, orderType, productId);
                    String headImg = "";
                    if (null != orderSnapshotProductResponse) {
                        orderDetailResp.setProductName(orderSnapshotProductResponse.getProductName());//商品名称
                        if(orderSnapshotProductResponse.getProductPrice()!=null){
                            orderDetailResp.setProductPrice(orderSnapshotProductResponse.getProductPrice());//商品单价
                        }
                        if(orderSnapshotProductResponse.getDeliveryTime()!=null){
                            orderDetailResp.setDeliveryTime(orderSnapshotProductResponse.getDeliveryTime());
                        }
                        orderDetailResp.setProductCategoty(orderSnapshotProductResponse.getProductCategoty());//商品类型
                        headImg = orderSnapshotProductResponse.getProductImage();//商品头图
                    }else{
                        Artwork artwork = artDao.getArtworkOrderInfo(productId.longValue());
                        if(artwork!=null){
                            orderDetailResp.setProductName(artwork.getName());//商品名称
                            if(artwork.getPrice()!=null){
                                orderDetailResp.setProductPrice(artwork.getPrice());//商品单价
                            }
                            orderDetailResp.setProductCategoty(artwork.getType());//商品类型
                            headImg = artwork.getHeadImg();//商品头图
                        }
                    }
                    if (OrderConstant.ORDER_TYPE_ART.equals(orderDto.getOrderType())) {
                        Boolean commentResultTag=commentMap.get(productId);
                        if(commentResultTag==null){
                            orderDetailResp.setCommentResultTag(false);
                        }else{
                            orderDetailResp.setCommentResultTag(commentResultTag);
                        }
                    }
                    if (StringUtils.isNotBlank(headImg)) {
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
	}


	private Map<Integer, Boolean> setOrderResponseForCouponAndVoucher(OrderResponse or, OrderDtoVo orderDto, Integer orderId) {
    	/**
         * 支付剩余时间
         */
        final int WAIT_PAY_TIME=1*60*60*1000;
    	List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
        Map<Integer,Boolean> commentMap = new HashMap<Integer,Boolean>();//艺术品评审内容
        if (OrderConstant.ORDER_TYPE_SOFT.equals(orderDto.getOrderType())) {
            SoftOrderVo softOrderVo =orderProxy.querySoftOrderDetail(orderId);

            BigDecimal cashCoupon = softOrderVo.getCashCoupon();
            BigDecimal voucher = softOrderVo.getVoucher();
            cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
            voucher = voucher == null ? new BigDecimal("0") : voucher;
            or.setCashCoupon(cashCoupon);
            or.setVoucher(voucher);
        } else if (OrderConstant.ORDER_TYPE_FAMILY.equals(orderDto.getOrderType())) {
            FamilyOrderVo familyOrder=orderProxy.queryFamilyOrderDetail(orderId);

            BigDecimal cashCoupon = familyOrder.getCashCoupon();
            BigDecimal voucher = familyOrder.getVoucher();
            cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
            voucher = voucher == null ? new BigDecimal("0") : voucher;
            or.setCashCoupon(cashCoupon);
            or.setVoucher(voucher);
        } else if (OrderConstant.ORDER_TYPE_HARD.equals(orderDto.getOrderType())) {
            HardOrderVo hardOrderVo =orderProxy.queryHardOrderDetail(orderId);

            BigDecimal cashCoupon = hardOrderVo.getCashCoupon();
            BigDecimal voucher = hardOrderVo.getVoucher();
            cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
            voucher = voucher == null ? new BigDecimal("0") : voucher;
            or.setCashCoupon(cashCoupon);
            or.setVoucher(voucher);
        } else if (OrderConstant.ORDER_TYPE_ART.equals(orderDto.getOrderType()) || OrderConstant.ORDER_TYPE_COLLAGE.equals(orderDto.getOrderType())) {
            ArtOrderVo artOrderVo =orderProxy.queryArtOrderDetail(orderId);

            BigDecimal cashCoupon = artOrderVo.getCashCoupon();
            BigDecimal voucher = artOrderVo.getVoucher();
            cashCoupon = cashCoupon == null ? new BigDecimal("0") : cashCoupon;
            voucher = voucher == null ? new BigDecimal("0") : voucher;
            or.setCashCoupon(cashCoupon);
            or.setVoucher(voucher);

            //艺术品订单待付款状态增加付款倒计时
            if(orderDto.getState() == 4) {
                Date createTime2 = orderDto.getCreateTime();
                long create = createTime2.getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if((create + WAIT_PAY_TIME) > currentTimeMillis) {
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

//            BigDecimal ajbMoney = artOrderVo.getAjbMoney();
            // 注释掉下面的 修改艾积分不展示问题 alter by jerfan cang 2018-1024
            /*or.setAjbMoney(ajbMoney);*/
        } else if (OrderConstant.ORDER_TYPE_CLUTRUE.equals(orderDto.getOrderType())) {
            TripOrderVo tripOrderVo =orderProxy.queryTripOrderDetail(orderId);
            BigDecimal ajbMoney = tripOrderVo.getAjbMoney();
            or.setAjbMoney(ajbMoney);
        }
		return commentMap;
	}


	private void setOrderResponseForReceiverAddress(OrderResponse or, OrderDtoVo orderDto, Integer fidArea) {
    	StringBuilder receiverAddress = new StringBuilder();
        AreaDto area = areaService.getArea(fidArea);
        if (null != area) {
            String countyName = area.getAreaName();
            String cityName = "";
            String provinceName = "";
            area = areaService.getArea(area.getParentId());
            if (null != area) {
                cityName = area.getAreaName();
                area = areaService.getArea(area.getParentId());
                if (null != area) {
                    provinceName = area.getAreaName();
                }
            }
            if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(provinceName)){
                receiverAddress.append(provinceName);
            }
            if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(cityName)){
                receiverAddress.append(cityName);
            }
            if(orderDto.getCustomerAddress()!=null&&!orderDto.getCustomerAddress().contains(countyName)){
                receiverAddress.append(countyName);
            }
        }

        receiverAddress.append(orderDto.getCustomerAddress());
        if(StringUtils.isNotBlank(orderDto.getBuildingNo())){
            receiverAddress.append(orderDto.getBuildingNo());
        }
        if(StringUtils.isNotBlank(orderDto.getHouseNo())){
            if(StringUtils.isNotBlank(orderDto.getBuildingNo())){
                receiverAddress.append("-");
            }
            receiverAddress.append(orderDto.getHouseNo());
        }
        or.setReceiverAddress(receiverAddress.toString());
	}


	private void setOrderResponseForLogisticListResponse(OrderResponse or, OrderDtoVo orderDto, Integer orderId, Integer width) {
    	//判断该订单有几条物流信息，若一条且包含所有商品则跳转物流详情页，其他情况均跳转所有物流页，并添加所有物流说明
        LogisticListResponse logisticListResponse = orderService.queryOrderDeliveryInfoByOrderId(orderId, width);
        if(logisticListResponse != null){
            // 订单关联的快递信息
            List<LogisticResponse> logisticList = logisticListResponse.getLogisticList();
            if(CollectionUtils.isNotEmpty(logisticList)){
                if(logisticList.size() == 1 && logisticListResponse.getProductCount().equals(logisticListResponse.getFilledCount())){
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
                }else{
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
                    if(orderDto.getState() != null && orderDto.getState().equals(OrderConstant.ORDER_OMSSTATUS_NO_SEND)){
                        or.setStateDesc(OrderConstant.LOGISTIC_PART_DELIVER_DESC);
                    }
                }
            }else{
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
        }else{
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
	}


	private OrderResponse setOrderResponseForBaseInfo(OrderDtoVo orderDto) {
    	Integer fidProject = orderDto.getFidProject();
        Integer fidArea = orderDto.getFidArea();
        BigDecimal actualPayMent = orderDto.getActualPayMent();
        
        OrderResponse or = new OrderResponse();
        // 新增 下面 修改艾积分不展示问题 alter by jerfan cang 2018-1024
        setOrderResponseForAjbMoney(or, orderDto.getOrderAttrValueOutputList());

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
        
        final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createTime = orderDto.getCreateTime();
        if (null != createTime) {
            String createDateStr = dayFormat.format(createTime);
            or.setCreateDateStr(createDateStr);
        }
		return or;
	}


	private void setOrderResponseForAjbMoney(OrderResponse or, List<OrderAttrValueDtoVo> attrList) {
        for(OrderAttrValueDtoVo attr : attrList){
            if(null != attr.getAttrName() && attr.getAttrName().equals("ajbMoney")){
                if(null !=attr.getAttrValue()){
                    or.setAjbMoney(new BigDecimal(attr.getAttrValue()));
                }
            }
        }
	}


	/**
     * 拼团活动订单详情的图片和验证 取值
     * @param orderResponse OrderResponse
     * @return orderResponse OrderResponse
     */
    private OrderResponse markProductImage( OrderResponse orderResponse){
        Map<String,String> colorMap = new HashMap<>();
        colorMap.put("4种颜色随机发货","https://static.ihomefnt.com/common-system/staticImage/head_img_2.jpg");
        colorMap.put("抹茶绿", StaticResourceConstants.TUANGOU_GLASS_GREEN_IMG);
        colorMap.put("可可黑", StaticResourceConstants.TUANGOU_GLASS_BLACK_IMG);
        colorMap.put("樱桃红", StaticResourceConstants.TUANGOU_GLASS_RED_IMG);
        colorMap.put("香芋紫", StaticResourceConstants.TUANGOU_GLASS_VIOLET_IMG);
        for(Map.Entry entry: colorMap.entrySet()){
            if(null !=orderResponse.getRemark() && !"".equals(orderResponse.getRemark())
                    && entry.getKey().equals(orderResponse.getRemark().toString())){
                orderResponse.setProductImage(entry.getValue().toString());
                break;
            }
        }
        return orderResponse;
    }



}
