package com.ihomefnt.o2o.service.service.culture;

import com.ihomefnt.cms.utils.ModelMapperUtil;
import com.ihomefnt.common.util.validation.ValidationResult;
import com.ihomefnt.common.util.validation.ValidationUtils;
import com.ihomefnt.o2o.intf.dao.culture.CultureDao;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.OrderNumDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.TradeParamDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.UserAjbRecordDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.culture.dto.CultureOrderCreateDto;
import com.ihomefnt.o2o.intf.domain.culture.dto.CultureOrderDetailDto;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureConsumeCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.OrderCreateRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.OrderProductRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CreateOrderResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureCommodityResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureConsumeCodeResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.OrderConfirmResponseVo;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDetailDtoVo;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.order.dto.TripOrderCreateDto;
import com.ihomefnt.o2o.intf.domain.product.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.culture.CultureService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CultureServiceImpl implements CultureService {
	@Autowired 
	CultureDao cultureDao;
	@Autowired
    OrderProxy orderProxy;
	@Autowired
	OrderService orderService;
    @Autowired
    ProductProxy productProxy;
    @Autowired
	AddressProxy addressProxy;
	@Autowired
	UserProxy userProxy;
	@Autowired
    private AjbProxy ajbRechargeProxy;
	
	private static final int ExRate = 100;//艾积分汇率
	private static final int EXCHANGE_COUNT = 1; //艾积分兑换文旅商品次数
	
	@Override
	public boolean updateUserPurchaseCultureRecord(Integer userId, Integer productId, Integer status) {
		return cultureDao.updateUserPurchaseCultureRecord(userId, productId, status);
	}

	@Override
	public CultureCommodityResponseVo getCultureDetail(CultureDetailRequestVo requestVo) {
		Integer width = requestVo.getWidth();
		Integer itemId = requestVo.getItemId();
		
		TripProductDto productVo =productProxy.queryProductById(itemId);
		CultureCommodityResponseVo cultureCommodity = new CultureCommodityResponseVo();
		if (null == productVo) {
			throw new BusinessException("商品信息获取失败");
		}
			
		cultureCommodity.setItemId(productVo.getId());
		cultureCommodity.setItemName(productVo.getProductName());
		//为空保护
		List<SkuVo> skuList = productVo.getSkuList();
		if (CollectionUtils.isNotEmpty(skuList)) {
			SkuVo skuVo = skuList.get(0);
			if (skuVo != null) {
				List<String> images = skuVo.getImages();
				//图片 90% 中部截取
				if (CollectionUtils.isNotEmpty(images)) {
					String headImg = images.get(0);
					if(width == null){
						cultureCommodity.setItemHeadImg(headImg);
					}else{
						Integer imgWidth = width * ImageSize.WIDTH_PER_SIZE_90 / ImageSize.WIDTH_PER_SIZE_100;
						cultureCommodity.setItemHeadImg(QiniuImageUtils.compressImageAndDiffPic(headImg, imgWidth, -1));
					}
				}
				cultureCommodity.setStock(skuVo.getStock());
				cultureCommodity.setItemSellPrice(skuVo.getAijiaPrice());
			}
		}			
		cultureCommodity.setSellPointContent(productVo.getGraphicDetails());				
		cultureCommodity.setItemTypeId(productVo.getCategoryId());
		
		setItemTypeByCategoryId(cultureCommodity, productVo.getCategoryId());
		
		// 根据商品id获取商户信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", itemId);
		SellerVo sellerVo =productProxy.getSellerByProductId(param);
		if (null == sellerVo) {
			throw new BusinessException("商品信息获取失败");
		}
				
		cultureCommodity.setSellerId(sellerVo.getId());
		cultureCommodity.setSellerName(sellerVo.getName());
		cultureCommodity.setSellerAddress(sellerVo.getAddr());
		return cultureCommodity;
	}

	private void setItemTypeByCategoryId(CultureCommodityResponseVo cultureCommodity, Integer categoryId) {
		switch (categoryId) {
		case 97:
			cultureCommodity.setItemType("文旅商品");
			break;
		case 98:
			cultureCommodity.setItemType("餐饮类");
			break;
		case 99:
			cultureCommodity.setItemType("旅游类");
			break;
		case 100:
			cultureCommodity.setItemType("服务类");
			break;
		default:
			cultureCommodity.setItemType("文旅商品");
			break;
		}
	}

	@Override
	public OrderConfirmResponseVo confirmCultureOrder(CultureDetailRequestVo requestVo) {
		String accessToken = requestVo.getAccessToken();
		if(!userProxy.checkLegalUser(accessToken)){
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
        HttpUserInfoRequest user = requestVo.getUserInfo();
		//查询文旅商品信息
		TripProductDto tripProductVo =productProxy.queryProductById(requestVo.getItemId());
		if (null == tripProductVo ) {
			throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.ITEM_NOT_EXIST);
		}
		Integer stock = tripProductVo.getSkuList().get(0).getStock();
		if(stock <= 0) {
			throw new BusinessException(HttpResponseCode.STOCK_IS_ZERO, MessageConstant.STOCK_ZERO);
		}
		
		OrderConfirmResponseVo orderConfirm = new OrderConfirmResponseVo();
		orderConfirm.setItemId(tripProductVo.getSkuId());
		orderConfirm.setItemName(tripProductVo.getProductName());
		String headImg = tripProductVo.getSkuList().get(0).getImages().get(0);
		orderConfirm.setItemHeadImg(headImg);
		orderConfirm.setStock(stock);
		BigDecimal aijiaPrice = tripProductVo.getSkuList().get(0).getAijiaPrice().setScale(2,BigDecimal.ROUND_HALF_UP);
		orderConfirm.setItemSellPrice(aijiaPrice);						
		Integer userId = 0;
		if(user != null){
			userId = user.getId();
		}
		
		setOrderConfirmVoForAjbInfo(orderConfirm, userId, requestVo.getItemId());
		
		return orderConfirm;
	}

	private void setOrderConfirmVoForAjbInfo(OrderConfirmResponseVo orderConfirm, Integer userId, Integer itemId) {
		//查询用户艾积分信息（有效期）
		Integer amount = 0;
		BigDecimal ajbMoney = new BigDecimal(amount);
        UserAjbRecordDto ajbRecordResponseVo = ajbRechargeProxy.queryAjbDetailInfoByUserId(userId, 1, 1);
        if(ajbRecordResponseVo != null && ajbRecordResponseVo.getDisplayUsableAmount() != null){
        	//有效期内可用艾积分数量
    		amount = ajbRecordResponseVo.getDisplayUsableAmount();
    		orderConfirm.setAjbAccount(amount);
    		if(ajbRecordResponseVo.getExRate() != null){
    			ajbMoney = new BigDecimal(amount).divide(new BigDecimal(ajbRecordResponseVo.getExRate())).setScale(2,BigDecimal.ROUND_HALF_UP);
    		}else{
    			ajbMoney = new BigDecimal(amount).divide(new BigDecimal(ExRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
    		}
    		orderConfirm.setAjbMoney(ajbMoney);
        }else{
        	orderConfirm.setAjbAccount(0);
			orderConfirm.setAjbMoney(BigDecimal.ZERO);
        }
        
		int productCount = cultureDao.queryProductCount(userId, itemId);
		if(productCount > 1) {
			productCount = 1;
		}
		orderConfirm.setAjbTime(EXCHANGE_COUNT - productCount);
				
		if(orderConfirm.getAjbAccount()>0) {
			double price = orderConfirm.getItemSellPrice().doubleValue();
			double ajbMoneyDouble = orderConfirm.getAjbMoney().doubleValue();
			if(productCount == 0) {
				Integer ajbAccount = orderConfirm.getAjbAccount();
				if(ajbMoneyDouble >= (price*EXCHANGE_COUNT)) {
					orderConfirm.setAjbMoney(BigDecimal.valueOf(price*EXCHANGE_COUNT));
					double d = price*EXCHANGE_COUNT*100;
					orderConfirm.setAjbAccount((int) d);
				} else {
					orderConfirm.setAjbMoney(BigDecimal.valueOf(ajbMoneyDouble));
					orderConfirm.setAjbAccount(ajbAccount);
				}
			} else if (productCount>0 && productCount < EXCHANGE_COUNT) {
				if(ajbMoneyDouble >= (price*(EXCHANGE_COUNT-productCount))) {
					orderConfirm.setAjbMoney(BigDecimal.valueOf(price*(EXCHANGE_COUNT-productCount)));
					double d = price*(EXCHANGE_COUNT-productCount)*100;
					orderConfirm.setAjbAccount((int) d);
				} else {
					orderConfirm.setAjbMoney(BigDecimal.valueOf(ajbMoneyDouble));
					orderConfirm.setAjbAccount(Integer.parseInt(ajbMoneyDouble*100+""));
				}
			} else {
				orderConfirm.setAjbAccount(0);
				orderConfirm.setAjbMoney(BigDecimal.ZERO);
			}
		} 
		orderConfirm.setExRate(ExRate);
		orderConfirm.setAjbTotalTime(EXCHANGE_COUNT);
	}

	@Override
	public CreateOrderResponseVo createCultureOrder(OrderCreateRequestVo requestVo) {
		HttpUserInfoRequest user = requestVo.getUserInfo();
        if (user == null) {
        	throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        if(null == requestVo.getAllPay()) {
			requestVo.setAllPay(false);
		}
		CultureOrderCreateDto orderCreate = setOrderCreateVoForBaseInfo(user);
		
		//设置下单商品信息
		List<CultureOrderDetailDto> orderDetailList = new ArrayList<CultureOrderDetailDto>();
		BigDecimal totalPrice = BigDecimal.valueOf(0.00).setScale(2,BigDecimal.ROUND_HALF_UP);//保留2位小数
		int allProductNum = 0;
		StringBuffer sb = new StringBuffer();
		BigDecimal productPrice = BigDecimal.valueOf(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		Integer productId = 0;
		for (OrderProductRequestVo orderProductVo : requestVo.getProductList()) {
			CultureOrderDetailDto orderDetail = new CultureOrderDetailDto();
			productId = orderProductVo.getProductId();
			orderDetail.setFidProduct(productId);
			//查询文旅商品信息
			TripProductDto tripProductVo =productProxy.queryProductById(productId);
			if (null != tripProductVo ) {					
				sb.append(tripProductVo.getProductName());
				productPrice = tripProductVo.getSkuList().get(0).getAijiaPrice();
				orderDetail.setProductPrice(productPrice);
				sb.append(" ");
			} 
			Integer productCount = orderProductVo.getProductCount();
			orderDetail.setProductAmount(productCount);
			BigDecimal productAmountPrice = productPrice.multiply(BigDecimal.valueOf(productCount)).setScale(2,BigDecimal.ROUND_HALF_UP);
			orderDetail.setProductAmountPrice(productAmountPrice);
			orderDetailList.add(orderDetail);
			totalPrice = totalPrice.add(productAmountPrice);
			allProductNum = allProductNum +productCount;
		}
		orderCreate.setProductCount(allProductNum);
		orderCreate.setDetailList(orderDetailList);
				
		BigDecimal ajbMoney = setOrderCreateVoFordMoneyInfo(orderCreate, user.getId(), totalPrice, requestVo);
		ValidationResult result = ValidationUtils.validateEntity(orderCreate);
		if(result.isHasErrors()){
			throw new BusinessException(JsonUtils.obj2json(result.getErrorMsg()));
		} 
		
		CreateOrderResponseVo orderResponseVo = new CreateOrderResponseVo();
		TripOrderCreateDto order = ModelMapperUtil.strictMap(orderCreate, TripOrderCreateDto.class);
		order.setAjbMoney(ajbMoney);
		Integer orderId = orderProxy.createTripOrder(order);
		if (orderId == null || orderId <= 0) {
			throw new BusinessException( MessageConstant.PRODUCT_ORDER_INVALID);
		}
		orderResponseVo.setOrderId(orderId);
		orderProxy.updateOrderState(orderId, OrderStateEnum.NO_PAYMENT);
				
		if(requestVo.getAllPay()) {
			//如果是艾积分全额支付先冻结艾积分，然后确认使用，并更新订单状态
			processCreateCultureOrderForAllPay(orderId, requestVo.getAjbAmount(), user.getId(), sb.toString());
		} else {
			//不是艾积分全额付款的时候
			if (requestVo.getAjbAmount() == 0) {
				return orderResponseVo;
			}
			try {
				// 冻结用户要使用的艾积分	
				Integer userId = user.getId();
				TradeParamDto tradeParamVo = new TradeParamDto();
				OrderDtoVo orderDto=orderService.queryOrderDetail(orderId);
				String orderNum = orderDto.getOrderNum();
				tradeParamVo.setAjbAmount(requestVo.getAjbAmount());
				tradeParamVo.setUserId(userId);
				tradeParamVo.setOrderNum(orderNum);
				tradeParamVo.setRemark(sb.toString().trim());
				
				//插入用户用艾积分兑换商品次数
				BigDecimal price = productPrice.multiply(BigDecimal.valueOf(100));
				double count = requestVo.getAjbAmount()/price.doubleValue();
				double ceil = Math.ceil(count);
				int countProduct = (int) ceil;
				for(int i=0;i<countProduct;i++) {
					cultureDao.addUserPurchaseCultureRecord(userId, productId,1);
				}											

				boolean freezeResult= ajbRechargeProxy.freezePay(tradeParamVo);
				if (!freezeResult) {
					throw new BusinessException( MessageConstant.FROZE_AJB_FAILED);
				}
			} catch (Exception e) {
				throw new BusinessException( MessageConstant.FROZE_AJB_FAILED);
			}
		}
		return null;
	}

	private void processCreateCultureOrderForAllPay(Integer orderId, Integer ajbAmount, Integer userId, String remark) {
		try {
			//冻结用户要使用的艾积分
			TradeParamDto tradeParamVo = new TradeParamDto();
			OrderDtoVo orderDto=orderService.queryOrderDetail(orderId);
			String orderNum = orderDto.getOrderNum();
			tradeParamVo.setAjbAmount(ajbAmount);
			tradeParamVo.setUserId(userId);
			tradeParamVo.setOrderNum(orderNum);
			tradeParamVo.setRemark(remark.trim());

			boolean freezeResult= ajbRechargeProxy.freezePay(tradeParamVo);
			if (!freezeResult) {
				throw new BusinessException( MessageConstant.UPDATE_FAILED);
			}
			//更新订单状态
			orderProxy.updateOrderState(orderId, OrderStateEnum.NO_RECEIVE);
			//确认扣除艾积分
		
			OrderNumDto orderNumVo = new OrderNumDto();
			orderNumVo.setOrderNum(orderNum);
			boolean confirmPayResult= ajbRechargeProxy.confirmPay(orderNumVo);
			if(!confirmPayResult) {
				throw new BusinessException( MessageConstant.UPDATE_FAILED);
			}
			// 支付完成生成消费码（可以在这边判断订单类型文旅商品时才生成）
			Integer fidUser = orderDto.getFidUser();
			GenerateCodeVo generateCodeVo = new GenerateCodeVo();
			generateCodeVo.setUserId(fidUser);
			generateCodeVo.setOrderId(orderDto.getOrderId());
			List<OrderDetailDtoVo> orderDetailDtoList = orderDto.getOrderDetailDtoList();
			List<Integer> productIdList = new ArrayList<Integer>();
			for (OrderDetailDtoVo orderDetailDto : orderDetailDtoList) {
				Integer fidProduct = orderDetailDto.getFidProduct();
				Integer productAmount = orderDetailDto.getProductAmount();
				for (int i = 1; i <= productAmount; i++) {
					productIdList.add(fidProduct);
					//艾积分全额支付向用户表中插入记录
					cultureDao.addUserPurchaseCultureRecord(fidUser, fidProduct,1);
				}
			}
			generateCodeVo.setProductIds(productIdList);
			
			boolean generatorResult=productProxy.generatorOrderCode(generateCodeVo);
			if (!generatorResult) {
				throw new BusinessException( MessageConstant.UPDATE_FAILED);
			}
		} catch (Exception e) {
			throw new BusinessException( MessageConstant.FROZE_AJB_FAILED);
		}
	}

	private BigDecimal setOrderCreateVoFordMoneyInfo(CultureOrderCreateDto orderCreate, Integer userId, BigDecimal totalPrice,
			OrderCreateRequestVo requestVo) {
		//把艾积分换算成人民币
		BigDecimal ajbMoney = BigDecimal.valueOf(0.00).setScale(2,BigDecimal.ROUND_HALF_UP);//保留2位小数
		if(null == requestVo.getAjbAmount()) {
			requestVo.setAjbAmount(0);
		}
		Integer ajbAmount = requestVo.getAjbAmount();
		ajbMoney = BigDecimal.valueOf(ajbAmount).divide(BigDecimal.valueOf(ExRate));
		
		//查询用户艾积分信息（有效期）
		Integer accountAjbAmount = 0;
        UserAjbRecordDto ajbRecordResponseVo = ajbRechargeProxy.queryAjbDetailInfoByUserId(userId, 1, 1);
        if(ajbRecordResponseVo != null && ajbRecordResponseVo.getDisplayUsableAmount() != null){
        	//有效期内可用艾积分数量
        	accountAjbAmount = ajbRecordResponseVo.getDisplayUsableAmount();
        }
		
		//校验合同额是否正确,用户账户艾积分余额与此次要使用的艾积分进行比较
		if (accountAjbAmount < ajbAmount) {
        	throw new BusinessException(HttpResponseCode.AJB_NOT_ENOUTH, MessageConstant.AJB_NOT_EXIST);
		}
		
		//合同额加上艾积分抵扣的现金与商品总金额进行比较
		BigDecimal contractMoney = requestVo.getContractMoney().setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal actualPayMoney = contractMoney.subtract(ajbMoney);
		if(totalPrice.compareTo(contractMoney) != 0 || contractMoney.compareTo(BigDecimal.ZERO) < 1) {
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.MONEY_OVER_FLOW);
		}
		
		
		orderCreate.setTotalPrice(totalPrice);
		if(requestVo.getAllPay()) {
			//艾积分全额支付，创建一个0元订单
			orderCreate.setActualPayMent(BigDecimal.ZERO);
		} else {
			orderCreate.setActualPayMent(actualPayMoney);
		}
		
		return ajbMoney;
	}

	private CultureOrderCreateDto setOrderCreateVoForBaseInfo(HttpUserInfoRequest user) {
		CultureOrderCreateDto orderCreate = new CultureOrderCreateDto();
		//设置下单用户信息
		orderCreate.setFidUser(user.getId());
		orderCreate.setCustomerName(user.getUsername());
		orderCreate.setCustomerTel(user.getMobile());
		orderCreate.setSource(2); //2表示用app下单： 1.pc 2.app 3.m站
		orderCreate.setOrderType(com.ihomefnt.oms.trade.order.constant.OrderConstant.ORDER_TRIP);				
		//设置用户收货信息
		List<UserAddressResultDto> userAddressList = addressProxy.queryUserAddressList(user.getId());
		if(null != userAddressList && userAddressList.size()>0) {
			for (UserAddressResultDto userAddress : userAddressList) {
				//区域id 设置默认地址
				if(userAddress.getIsDefault()) {
					orderCreate.setFidArea(userAddress.getCountryId());
					orderCreate.setCustomerAddress(userAddress.getAddress());
					orderCreate.setReceiverName(userAddress.getConsignee());
					orderCreate.setReceiverTel(userAddress.getMobile());
				}
			}
		}
		//设置下单时间
		orderCreate.setOrderTime(new Date());
		orderCreate.setExpectedReceiptTime(new Date());
		return orderCreate;
	}

	@Override
	public CultureConsumeCodeResponseVo getGenerateCode(CultureConsumeCodeRequestVo requestVo) {
		CultureConsumeCodeResponseVo result = new CultureConsumeCodeResponseVo();
		HttpUserInfoRequest userDto = requestVo.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", requestVo.getOrderId());
		List<ConsumeCodeVo> data =productProxy.getCodeListByOrderId(paramMap);
		if (CollectionUtils.isEmpty(data)) {
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.DDATA_GET_FAILED);
		}

		for (ConsumeCodeVo consumeCodeVo : data) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			Date createDate = new Date(Long.parseLong(consumeCodeVo.getCreateTime()));
			Date update = new Date(Long.parseLong(consumeCodeVo.getUpdateTime()));
			consumeCodeVo.setCreateTime(format.format(createDate));
			consumeCodeVo.setUpdateTime(format.format(update));
		}
		result.setConsumeList(data);
		return result;
	}
	
}
