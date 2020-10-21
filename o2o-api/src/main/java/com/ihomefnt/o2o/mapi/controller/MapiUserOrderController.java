/**
 * 
 */
package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashCouponDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CouponRemarkDto;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CashCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CenterCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CashCouponResponseVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CenterCouponResponseVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.VoucherResponseVo;
import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpUserOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpOrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpUserOrderResponse120;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.LoginRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.LoginResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.coupon.VoucherConstant;
import com.ihomefnt.o2o.intf.manager.constant.dic.DicConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.configItem.ConfigItemService;
import com.ihomefnt.o2o.intf.service.coupon.CashCouponService;
import com.ihomefnt.o2o.intf.service.coupon.VoucherService;
import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.service.emchat.EmchatIMUsersService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.user.MyService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang <br/>
 *         http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=4625356 <br/>
 */
@Api(value="M站用户订单API",description="M站用户订单老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/userOrder")
public class MapiUserOrderController extends MapiBaseController {

	private static final Logger LOG = LoggerFactory.getLogger(MapiUserOrderController.class);

	@Autowired
	CashCouponService cashCouponService;

	@Autowired
	MyService myService;

	@Autowired
	UserService userService;

	@Autowired
	ConfigItemService configItemService;

	@Autowired
	private WebConfig webConfig;

	@Autowired
	OrderService orderService;

	@Autowired
	VoucherService voucherService;
	
    @Autowired
    EmchatIMUsersService emchatIMUsersService;
    
	@Autowired
	UserProxy userProxy;

	/**
	 * 
	 * 查询我的所有订单
	 * 
	 */
	@RequestMapping(value = "/getMyOrderList", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getMyOrderList(
			@Json HttpBaseRequest httpBaseRequest) {
		if (httpBaseRequest != null) {
			LOG.info("MapiUserOrderController getMyOrderList httpBaseRequest:{}", JsonUtils.obj2json(httpBaseRequest));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		HttpMessage message = new HttpMessage();
		if (httpBaseRequest == null) {
			baseResponse.setCode(HttpResponseCode.FAILED);
			baseResponse.setObj(null);
			message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
			baseResponse.setExt(message);
		} else {
			// 1.查询用户是否登录
			HttpUserInfoRequest userDto = httpBaseRequest.getUserInfo();
			if (userDto != null) {
				// 2.查询该用户订单信息
				HttpUserOrderResponse120 userOrderResponse = new HttpUserOrderResponse120();
				userOrderResponse.setUserOrderList(myService.queryAllUserOrder120(userDto.getId().longValue(), null));
				String configName = "取消原因";
				ConfigItem configItem = configItemService.findConfigItemByConfigName(configName);
				if (configItem != null && configItem.getConfigId() != null) {
					userOrderResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));
				} else {
					userOrderResponse.setItemList(null);
				}
				baseResponse.setCode(HttpResponseCode.SUCCESS);
				baseResponse.setObj(userOrderResponse);
				baseResponse.setExt(null);
			} else {
				baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
				baseResponse.setObj(null);
				message.setMsg(MessageConstant.USER_NOT_LOGIN);
				baseResponse.setExt(message);
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
	}

	/**
	 * 
	 * 根据订单ID查询订单详情
	 * 
	 */
	@RequestMapping(value = "/getMyOrderDetail", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getMyOrderDetail(
			@Json HttpOrderRequest httpOrderRequest) {
		if (httpOrderRequest != null) {
			LOG.info("MapiUserOrderController getMyOrderDetail httpBaseRequest:{}", JsonUtils.obj2json(httpOrderRequest));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		HttpMessage message = new HttpMessage();
		if (httpOrderRequest == null|| StringUtils.isBlank(httpOrderRequest.getAccessToken())|| httpOrderRequest.getOrderId() == null) {
			baseResponse.setCode(HttpResponseCode.FAILED);
			baseResponse.setObj(null);
			message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
			baseResponse.setExt(message);
		} else {
			String accessToken = httpOrderRequest.getAccessToken();
			LOG.info("accessToken:" + accessToken);
			// 1.查询用户是否登录
			LogDo tLog = userService.isLoggedIn(accessToken);
			if (tLog != null) {
				// 2.查询该用户订单信息
				Long orderId = httpOrderRequest.getOrderId();
				UserOrder userOrder = myService.queryUserOrderByOrderId(orderId);
				HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
				Double alreadyPay = orderService.queryPayedMoneyByOrderId(orderId);
				if (alreadyPay == null) {
					alreadyPay = 0d;
				}
				orderDetailResponse.setAlreadyPay(alreadyPay);
				orderDetailResponse.setRemainPay(orderDetailResponse.getOrderPrice() - alreadyPay);
				// 查询是否有部分付款
				Double payedMoney = orderService.selPayedMoneyByOrderIdAndOrderStatus(orderId,OrderConstant.ORDER_OMSSTATUS_NO_RECEIVE);
				Double totalFee = orderDetailResponse.getOrderPrice();
				if (null != payedMoney) {
					totalFee = totalFee - payedMoney;
				}
				orderDetailResponse.setLeftMoney(totalFee);
				List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(orderId);
				orderDetailResponse.setOrderDetails(orderDetails);
				String configName = "取消原因";
				ConfigItem configItem = configItemService.findConfigItemByConfigName(configName);
				if (configItem != null && configItem.getConfigId() != null) {
					orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));
				} else {
					orderDetailResponse.setItemList(null);
				}
				Double orderPrice=orderDetailResponse.getOrderPrice();
				if(orderPrice==null){
					orderPrice=0d;
				}
				Double couponPay=orderDetailResponse.getCouponPay(); 
				if(couponPay==null){
					couponPay=0d;
				}
				Double voucherPay=orderDetailResponse.getVoucherPay();
				if(voucherPay==null){
					voucherPay=0d;
				}
				orderDetailResponse.setTotalPay(orderPrice+couponPay+voucherPay);
				baseResponse.setCode(HttpResponseCode.SUCCESS);
				baseResponse.setObj(orderDetailResponse);
				baseResponse.setExt(null);
			} else {
				baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
				baseResponse.setObj(null);
				message.setMsg(MessageConstant.USER_NOT_LOGIN);
				baseResponse.setExt(message);
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
	}

	/**
	 * 
	 * 取消订单
	 * 
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> cancelOrder(
			@Json HttpUserOrderRequest userOrderRequest) {
		if (userOrderRequest != null) {
			LOG.info("MapiUserOrderController cancelOrder userOrderRequest:{}", JsonUtils.obj2json(userOrderRequest));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		HttpMessage message = new HttpMessage();
		if (userOrderRequest != null && userOrderRequest.getOrderId() != null&& StringUtils.isNotBlank(userOrderRequest.getAccessToken())) {
			String accessToken = userOrderRequest.getAccessToken();
			LOG.info("accessToken:" + accessToken);
			LogDo tLog = userService.isLoggedIn(accessToken);
			UserOrder userOrder = myService.queryUserOrderByOrderId(userOrderRequest.getOrderId());
			if (tLog != null && (userOrder.getUserId()).equals(tLog.getuId())) {
				Boolean isCancel = myService.cancelOrder(userOrderRequest);
				if (isCancel) {
					Long voucherId = userOrder.getVoucherId();
					if (voucherId != null) {
						// 修改t_voucher_detail
						// 抵用券状态 1:待确认收款2:已生效3:已使用4:已失效5:使用中
						Voucher temp = new Voucher();
						temp.setVoucherId(voucherId);
						temp.setVoucherStatus(VoucherConstant.STATUS_OK);
						voucherService.updateVoucherById(temp);
						// 新增t_voucher_log
						VoucherLog log = new VoucherLog();
						log.setVoucherId(voucherId);
						log.setVoucherStatus(VoucherConstant.STATUS_OK);
						// 操作类型 1:注册 2:确认收款 3:已使用 ,4取消，5使用中
						log.setOperateType(VoucherConstant.OPERATE_TYPE_CANCLE);
						voucherService.insertVoucherLog(log);
					}
					baseResponse.setCode(HttpResponseCode.SUCCESS);
					baseResponse.setObj(MessageConstant.SUCCESS);
					baseResponse.setExt(null);
				} else {
					baseResponse.setCode(HttpResponseCode.FAILED);
					baseResponse.setObj(MessageConstant.FAILED);
					baseResponse.setExt(null);
				}
			} else {
				baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
				baseResponse.setObj(null);
				message.setMsg(MessageConstant.USER_NOT_LOGIN);
				baseResponse.setExt(message);
			}
		} else {
			baseResponse.setCode(HttpResponseCode.FAILED);
			baseResponse.setObj(null);
			message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
			baseResponse.setExt(message);
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Request-Method", "post");
		return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
	}
	
	/**
	 * 
	 * 查询我的所有现金券
	 * 
	 */
	@RequestMapping(value = "/queryCashCouponList", method = RequestMethod.POST)
	public HttpBaseResponse<CashCouponResponseVo> queryCashCouponList(@Json CashCouponRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			return HttpBaseResponse.fail(MessageConstant.USER_NOT_LOGIN);
		}

		return HttpBaseResponse.success(cashCouponService.queryCouponByUserId(userDto.getId().longValue(), request.getIsRead()));
	}
	
	/**
	 * 
	 * 查询我的所有抵用券
	 * 
	 */
	@RequestMapping(value = "/getAllVoucherList", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getAllVoucherList(@Json HttpBaseRequest baseRequest) {
		if (baseRequest != null) {
			LOG.info("MapiUserOrderController getAllVoucherList baseRequest:{}", JsonUtils.obj2json(baseRequest));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		VoucherResponseVo voucherResponseVo = new VoucherResponseVo();
		// 对前台传入参数做保护
		if (baseRequest == null) {
			return super.returnParameterError();
		}
		// 暂不做用户校验提取，后面改为拦截器校验
		HttpUserInfoRequest userDto = baseRequest.getUserInfo();
		if (userDto == null) {
			return super.returnError(voucherResponseVo,MessageConstant.ADMIN_ILLEGAL);
		}
		
		if (StringUtils.isNotBlank(userDto.getMobile())) {
			List<Voucher> list = this.voucherService.queryVoucherByMobile(userDto.getMobile());
			if (list != null && !list.isEmpty()) {
				voucherResponseVo.setVoucherList(list);
				baseResponse.setObj(voucherResponseVo);
			} else {
				return super.returnError(voucherResponseVo,VoucherConstant.QUERY_EMPTY);
			}
		} else {
			return super.returnError(voucherResponseVo,MessageConstant.MOBILE_NOT_EXISTS);
		}
		return super.returnResponse(baseResponse);
	}
	
	/**
	 * 
	 * 查询我所有的券
	 * 
	 */
	 @RequestMapping(value = "/couponList", method = RequestMethod.POST)
	    public HttpBaseResponse<CenterCouponResponseVo> couponList(@Json CenterCouponRequestVo request){
			if (request == null) {
				return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
			}
	    	HttpBaseResponse response = new HttpBaseResponse();
	    	
	    	CenterCouponResponseVo centerCouponResponse = new CenterCouponResponseVo();
	    	
	    	centerCouponResponse.setHeadImage(dictionaryService.getValueByKey("ACTION_TYPE_HEADIMG"));
	    	
	    	HttpUserInfoRequest userDto =  request.getUserInfo();
	    	if(null == userDto){
	    		List<CenterCouponDto> couponList = cashCouponService.getAllCoupons();
	    		if(couponList != null && couponList.size() > 0){
	    			centerCouponResponse.setCouponList(couponList);
	    			response.setCode(HttpResponseCode.SUCCESS);
	    		} else {
	    			response.setCode(HttpResponseCode.SUCCESS);
	    		}
	    		
	    	} else {
	    		String mobile = userDto.getMobile();
	    		List<CenterCouponDto> couponList = cashCouponService.getUseableCoupons(mobile);
	    		if(couponList != null && couponList.size() > 0){
	    			centerCouponResponse.setCouponList(couponList);
	    			response.setCode(HttpResponseCode.SUCCESS);
	    		} else {
	    			response.setCode(HttpResponseCode.SUCCESS);
	    		}
	    	}
	    	
	    	return HttpBaseResponse.success(centerCouponResponse);
	    }
	
	/**
	 * 
	 * 根据抵用券ID，查询对应抵用券说明
	 * 
	 */	
	@RequestMapping(value = "/getVoucherById", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getVoucherById(@Json Voucher voucher) {
		if (voucher != null) {
			LOG.info("MapiUserOrderController getVoucherById voucher:{}", JsonUtils.obj2json(voucher));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		
		// 对前台传入参数做保护
		if (voucher == null ||voucher.getVoucherId()==null) {
			return super.returnParameterError();
		}

		Voucher entity = this.voucherService.queryVoucherByPK(voucher.getVoucherId());
		if (entity != null ) {
		
			baseResponse.setObj(entity);
		} else {
			return super.returnError(entity,VoucherConstant.QUERY_EMPTY);
		}
	
		return super.returnResponse(baseResponse);
	}
	
	/**
	 * 
	 * 查询我的现金券金额和抵用券金额
	 * 
	 */	
    @RequestMapping(value = "/queryAccountMoney", method = RequestMethod.POST)
    public HttpBaseResponse<CashCouponResponseVo> queryAccountMoney(@Json CashCouponRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
        HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		CashCouponResponseVo cashCouponResponse = new CashCouponResponseVo();
		List<CashAccountDo>  accountList =cashCouponService.queryCashAccount(userDto.getId().longValue());
		//tmp 临时修改 解决前台显示问题
		if(accountList==null||accountList.isEmpty()){
			accountList =new ArrayList<CashAccountDo>();
			accountList.add(new CashAccountDo());
			cashCouponResponse.setCashAccount(accountList);
		}else{
			cashCouponResponse.setCashAccount(accountList);
		}

		if (StringUtils.isNotBlank(userDto.getMobile())) {
			String mobile=userDto.getMobile();
			//算出我的所有抵用券金额
			double amount = this.voucherService.queryVoucherAmountByMobile(mobile);
			cashCouponResponse.setVoucherTotal(amount);
			Double amountPayable=request.getAmountPayable();
			if(amountPayable!=null){
				//算出我能用的抵用券
				List<Voucher> voucherEnableList= this.voucherService.getEnableVoucherList(mobile, amountPayable);
				if(voucherEnableList!=null&&!voucherEnableList.isEmpty()){
					cashCouponResponse.setVoucherEnableList(voucherEnableList);
				}
			}
		}
		return HttpBaseResponse.success(cashCouponResponse);

    }
    
	@Autowired
	DictionaryService dictionaryService;
	
	/**
	 * 
	 * 查询现金券在线帮助
	 * 
	 */
	@RequestMapping(value = "/getCashHelpDesc", method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getCashHelpDesc(@Json HttpBaseRequest baseRequest) {
		if (baseRequest != null) {
			LOG.info("MapiUserOrderController getCashHelpDesc baseRequest:{}", JsonUtils.obj2json(baseRequest));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		String  value=dictionaryService.getValueByKey(DicConstant.CASH_HELP_DESC);		
		if(StringUtils.isNotBlank(value)){			
			String[] sets = value.split("<question>");
			List<CouponRemarkDto> remarkList = new ArrayList<CouponRemarkDto>();
			for(int i=1;i<sets.length;i++){
				CouponRemarkDto couponRemark = new CouponRemarkDto();
				String[] set = sets[i].split("<answer>");
				if(set.length>=2){
					couponRemark.setQuestion(set[0]);
					couponRemark.setAnswer(set[1]);
					remarkList.add(couponRemark);
				}				
			}
			if(!remarkList.isEmpty()){
				CashCouponDo cashCoupon = new CashCouponDo();
				cashCoupon.setRemarkList(remarkList);
				baseResponse.setObj(cashCoupon);	
			}
			else{
				LOG.info("getCashHelpDesc empty");
				return super.returnError(DicConstant.QUERY_EMPTY);
			}
		}
		LOG.info("getCashHelpDesc ok");
		return super.returnResponse(baseResponse);
	}
	
	/**
	 * 功能描述：用户登录
	 */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> login(@Json LoginRequestVo loginRequest) {
		if (loginRequest != null) {
			LOG.info("MapiUserOrderController login loginRequest:{}", JsonUtils.obj2json(loginRequest));
		}
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        LoginResponseVo loginResponse = new LoginResponseVo();
        // 1. check whether mobile and password exists or match
        if (loginRequest == null || StringUtil.isNullOrEmpty(loginRequest.getMobile())
                || StringUtil.isNullOrEmpty(loginRequest.getPassword())) {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.USER_PASS_EMPTY);
            baseResponse.setExt(message);
        } else {
            LogDo tLog = userService.auth(loginRequest.getMobile(), loginRequest.getPassword());
            if (tLog == null) {
                // does not exist or match
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_EXISTS);
                baseResponse.setExt(message);
            } else {
                loginResponse.setRefreshToken(tLog.getRefreshToken());
                loginResponse.setAccessToken(tLog.getAccessToken());
                loginResponse.setExpire(tLog.getExpire());// 15天
                UserDo user=userService.queryUserInfo(tLog.getuId());
                if(user!=null&&StringUtils.isNotBlank(user.getNickName())){
                	loginResponse.setNickName(user.getNickName());
                }else{
                	loginResponse.setNickName(null);
                }
                
                if (null != user) {
                    EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(user.getuId()));    //环信IM用户信息
                    loginResponse.setEmchatIMUser(emchatIMUser);
                }

                List<String> tags = userService.refreshUserTag(tLog.getuId(), loginRequest);
                loginResponse.setTag(tags);
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(loginResponse);
                baseResponse.setExt(null);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

}
