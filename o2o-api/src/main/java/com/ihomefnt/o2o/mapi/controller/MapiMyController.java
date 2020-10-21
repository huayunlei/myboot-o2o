package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.service.configItem.ConfigItemService;
import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpOrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpUserOrderRequest;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.TWishList;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpUserOrderListResponse120;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpUserOrderResponse120;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserAddWishListRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserAddWishListResponseVo;
import com.ihomefnt.o2o.intf.service.user.MyService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.intf.manager.constant.coupon.VoucherConstant;
import com.ihomefnt.o2o.intf.service.coupon.VoucherService;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.common.config.WebConfig;
import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shirely_geng on 15-2-6.
 */
@Api(value="M站我的设置管理API",description="M站我的设置管理接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/my")
public class MapiMyController {

    private static final Logger LOG = LoggerFactory.getLogger(MapiMyController.class);

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

    @RequestMapping(value = "/wishList/add", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> addWishList(
            UserAddWishListRequestVo userAddWishList, HttpSession httpSession) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (userAddWishList != null) {
            Object tokenObj=httpSession.getAttribute(httpSession.getId());
            String accessToken = tokenObj!=null?tokenObj.toString():"";
            LogDo tLog = userService
                    .isLoggedIn(accessToken);
            if (tLog != null) {
                TWishList wishList = new TWishList();
                wishList.setUserId(tLog.getuId());
                wishList.setWishProductBrand(userAddWishList
                        .getWishProductBrand());
                wishList.setWishProductName(userAddWishList
                        .getWishProductName());
                wishList.setWishProductRequest(userAddWishList
                        .getWishProductRequest());
                wishList.setWishProductUrl(userAddWishList.getWishProductUrl());
                UserAddWishListResponseVo userAddWishListResponse = new UserAddWishListResponseVo();
                userAddWishListResponse.setWishListId(myService
                        .addWishList(wishList));
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(userAddWishListResponse);
                baseResponse.setExt(null);
            } else {
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
            }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String getAllOrder(Model model,
    		 @RequestParam(value = "accessToken", required = false) String accessToken,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();

        String sessionId = httpSession.getId();

        if (!StringUtil.isNullOrEmpty(accessToken)) {
            /**
             * save session id to access token map;
             */
            httpSession.setAttribute(sessionId, accessToken);
        }
        String configName="取消原因";
        if (accessToken != null) {
            //1.查询用户是否登录
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                //2.查询该用户订单信息
            	HttpUserOrderResponse120 userOrderResponse = new HttpUserOrderResponse120();
                userOrderResponse.setUserOrderList(myService.queryAllUserOrder120(tLog.getuId(),null));
                ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
                if(configItem!=null&&configItem.getConfigId()!=null){
                    userOrderResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
                }else{
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
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        model.addAttribute("baseResponse", baseResponse);
        return "order/myOrder.ftl";
    }
    
//    /**
//     * 订单列表
//     * @param accessToken
//     * @param httpSession
//     * @return
//     */
//    @RequestMapping(value = "/order", method = RequestMethod.POST)
//    public ResponseEntity<HttpBaseResponse> getAllOrder(@RequestParam(value = "accessToken", required = false) String accessToken,HttpSession httpSession) {
//        LOG.info("getAllOrder() interface start");
//        LOG.info("accessToken:"+accessToken);
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        HttpMessage message = new HttpMessage();
//
//        String sessionId = httpSession.getId();
//
//        if (!StringUtil.isNullOrEmpty(accessToken)) {
//            /**
//             * save session id to access token map;
//             */
//            httpSession.setAttribute(sessionId, accessToken);
//        }
//        String configName="取消原因";
//        if (accessToken != null) {
//            //1.查询用户是否登录
//            TLog tLog = userService.isLoggedIn(accessToken);
//            if (tLog != null) {
//                //2.查询该用户订单信息
//            	HttpUserOrderResponse120 userOrderResponse = new HttpUserOrderResponse120();
//                userOrderResponse.setUserOrderList(myService.queryAllUserOrder120(tLog.getuId(),null));
//                ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
//                if(configItem!=null&&configItem.getConfigId()!=null){
//                    userOrderResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
//                }else{
//                    userOrderResponse.setItemList(null);
//                }
//                baseResponse.setCode(HttpResponseCode.SUCCESS);
//                baseResponse.setObj(userOrderResponse);
//                baseResponse.setExt(null);
//            } else {
//                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
//                baseResponse.setObj(null);
//                message.setMsg(MessageConstant.USER_NOT_LOGIN);
//                baseResponse.setExt(message);
//            }
//        } else {
//            baseResponse.setCode(HttpResponseCode.FAILED);
//            baseResponse.setObj(null);
//            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
//            baseResponse.setExt(message);
//        }
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.set("Access-Control-Allow-Origin", "*");
//        headers.set("Access-Control-Request-Method", "post");
//        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
//    }
    
    @RequestMapping(value = "/weborder", method = RequestMethod.GET)
    public ModelAndView getWebAllOrder(HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        Map<String, Object> model = new HashMap<String, Object>();
        HttpMessage message = new HttpMessage();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        if(StringUtils.isNotBlank(accessToken)) {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null && null != tLog.getuId()) {
                try{
                    String configName="取消原因";
                    //2.查询该用户订单信息
                	HttpUserOrderResponse120 userOrderResponse = new HttpUserOrderResponse120();
                    userOrderResponse.setUserOrderList(myService.queryAllUserOrder120(tLog.getuId(),null));
                    ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
                    if(configItem!=null&&configItem.getConfigId()!=null){
                        userOrderResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
                    }else{
                        userOrderResponse.setItemList(null);
                    }
                    baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(userOrderResponse);
                    message.setMsg(MessageConstant.SUCCESS);
                    baseResponse.setExt(message);
                } catch(Exception e){
                    baseResponse.setCode(HttpResponseCode.FAILED);
                    message.setMsg(MessageConstant.FAILED);
                    baseResponse.setExt(message);
                    String url = webConfig.HOST + "/RES/error/500.html";
                    return new ModelAndView(new RedirectView(url));
                }
            }else{
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
                model.put("baseResponse", baseResponse);
                String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/my/weborder";
                return new ModelAndView(new RedirectView(url));
            }
        } else {
            baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
            message.setMsg(MessageConstant.USER_NOT_LOGIN);
            baseResponse.setExt(message);
            model.put("baseResponse", baseResponse);
            String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/my/weborder";
            return new ModelAndView(new RedirectView(url));
        }
        model.put("baseResponse", baseResponse);
        return new ModelAndView("order/mywebOrder.ftl",model);
    }
    
    @RequestMapping(value = "/ajaxUserOrder", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> ajaxUserOrder(@Json HttpUserOrderRequest userOrderRequest,HttpSession httpSession) {

		if (userOrderRequest != null) {
			LOG.info("MapiMyController ajaxUserOrder userOrderRequest:{}", JsonUtils.obj2json(userOrderRequest));
		}
	
         HttpBaseResponse baseResponse = new HttpBaseResponse();
         HttpMessage message = new HttpMessage();
         if (userOrderRequest == null||userOrderRequest.getOrderId()==null) {
        	 baseResponse.setCode(HttpResponseCode.FAILED);
             baseResponse.setObj(null);
             message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
             baseResponse.setExt(message);
         }else{
             Object tokenObj=httpSession.getAttribute(httpSession.getId());
        	 String accessToken = tokenObj!=null?tokenObj.toString():"";
        	 LOG.info("accessToken:"+accessToken);
             if (accessToken != null) {
                 //1.查询用户是否登录
                 LogDo tLog = userService.isLoggedIn(accessToken);
                 if (tLog != null) {
                     //2.查询该用户订单信息
                	 HttpUserOrderListResponse120 userOrderResponse = new HttpUserOrderListResponse120();
                     userOrderResponse.setUserOrderList(myService.queryAllUserOrder120(tLog.getuId(),userOrderRequest.getOrderId()));
                     baseResponse.setCode(HttpResponseCode.SUCCESS);
                     baseResponse.setObj(userOrderResponse);
                     baseResponse.setExt(null);
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
         }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,
                HttpStatus.OK);
    }
    
    
//    @RequestMapping(value = "/orderDetails", method = RequestMethod.GET)
//    public String orderDetails(Model model,
//   		 @RequestParam(value = "orderId", required = false) String orderId,
//   		@RequestParam(value = "accessToken", required = false) String accessToken,
//   		 HttpSession httpSession) {
//        LOG.info("getAllOrder() interface start");
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        HttpMessage message = new HttpMessage();
//        String sessionId = httpSession.getId();
//        Object tokenObj=httpSession.getAttribute(sessionId);
//        LOG.info("tokenObj:" + tokenObj);
//        LOG.info("accessToken:" + accessToken);
//        if (accessToken != null) {
//            httpSession.setAttribute(sessionId, accessToken);
//        } else {
//            if (null != tokenObj) {
//                accessToken = tokenObj.toString();
//                httpSession.setAttribute(sessionId, accessToken);
//            }
//        }
//        
//        String configName="取消原因";
//        if (accessToken != null) {
//            //1.查询用户是否登录
//            TLog tLog = userService.isLoggedIn(accessToken);
//            if (tLog != null) {
//                //2.查询该用户订单信息
//            	
//            	UserOrder userOrder = myService.queryUserOrderByOrderId(Long.parseLong(orderId));
//            	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
//            	
//            	Double alreadyPay = orderService.queryPayedMoneyByOrderId(Long.parseLong(orderId));
//				if(alreadyPay == null){
//					alreadyPay = 0d;
//				}
//				orderDetailResponse.setAlreadyPay(alreadyPay);
//				orderDetailResponse.setRemainPay(orderDetailResponse.getOrderPrice()-alreadyPay);
//            	
//            	//查询是否有部分付款
//            	Double payedMoney = orderService.selPayedMoneyByOrderId(Long.parseLong(orderId));
//            	Double totalFee = orderDetailResponse.getOrderPrice();
//            	if(null != payedMoney) {
//            		//totalFee = totalFee - payedMoney;
//            	}
//            	orderDetailResponse.setLeftMoney(totalFee);
//            	
//            	List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(Long.parseLong(orderId));
//            	orderDetailResponse.setOrderDetails(orderDetails);
//            	ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
//            	if(configItem!=null&&configItem.getConfigId()!=null){
//            	    orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
//            	}else{
//            	    orderDetailResponse.setItemList(null);   
//            	}
//            	orderDetailResponse.setTotalPay(orderDetailResponse.getOrderPrice()+orderDetailResponse.getCouponPay());
//                baseResponse.setCode(HttpResponseCode.SUCCESS);
//                baseResponse.setObj(orderDetailResponse);
//                baseResponse.setExt(null);
//            } else {
//                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
//                baseResponse.setObj(null);
//                message.setMsg(MessageConstant.USER_NOT_LOGIN);
//                baseResponse.setExt(message);
//                model.addAttribute("baseResponse", baseResponse);
//            }
//        } else {
//            baseResponse.setCode(HttpResponseCode.FAILED);
//            baseResponse.setObj(null);
//            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
//            baseResponse.setExt(message);
//        }
//        model.addAttribute("fromApp", 1);
//        model.addAttribute("baseResponse", baseResponse);
//        return "order/orderDetails.ftl";
//    }
    
    /**
     * 订单详情
     * @param model
     * @param orderId
     * @param accessToken
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/orderDetails", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> orderDetails(@RequestParam(value = "orderId", required = false) String orderId,
   		@RequestParam(value = "accessToken", required = false) String accessToken,
   		 HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        String sessionId = httpSession.getId();
        Object tokenObj=httpSession.getAttribute(sessionId);
        LOG.info("tokenObj:" + tokenObj);
        LOG.info("accessToken:" + accessToken);
        if (accessToken != null) {
            httpSession.setAttribute(sessionId, accessToken);
        } else {
            if (null != tokenObj) {
                accessToken = tokenObj.toString();
                httpSession.setAttribute(sessionId, accessToken);
            }
        }
        
        String configName="取消原因";
        UserOrder userOrder = myService.queryUserOrderByOrderId(Long.parseLong(orderId));
        if (accessToken != null && userOrder != null) {
            //1.查询用户是否登录
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                //2.查询该用户订单信息
            	
            	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
            	
            	Double alreadyPay = orderService.queryPayedMoneyByOrderId(Long.parseLong(orderId));
				if(alreadyPay == null){
					alreadyPay = 0d;
				}
				orderDetailResponse.setAlreadyPay(alreadyPay);
				orderDetailResponse.setRemainPay(orderDetailResponse.getOrderPrice()-alreadyPay);
				orderDetailResponse.setFromApp("1");
            	
            	//查询是否有部分付款
            	Double payedMoney = orderService.selPayedMoneyByOrderId(Long.parseLong(orderId));
            	Double totalFee = orderDetailResponse.getOrderPrice();
            	if(null != payedMoney) {
            		//totalFee = totalFee - payedMoney;
            	}
            	orderDetailResponse.setLeftMoney(totalFee);
            	
            	List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(Long.parseLong(orderId));
            	orderDetailResponse.setOrderDetails(orderDetails);
            	ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
            	if(configItem!=null&&configItem.getConfigId()!=null){
            	    orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
            	}else{
            	    orderDetailResponse.setItemList(null);   
            	}
            	orderDetailResponse.setTotalPay(orderDetailResponse.getOrderPrice()+orderDetailResponse.getCouponPay());
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(orderDetailResponse);
                baseResponse.setExt(null);
            } else {
            	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                orderDetailResponse.setFromApp("1");
                baseResponse.setObj(orderDetailResponse);
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
            }
        } else {
        	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(new UserOrder());
        	orderDetailResponse.setFromApp("1");
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(orderDetailResponse);
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/orderDetails_version2_2", method = RequestMethod.GET)
    public String orderDetails_version2_2(Model model,
   		 @RequestParam(value = "orderId", required = false) String orderId,
   		@RequestParam(value = "accessToken", required = false) String accessToken,
   		 HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        String sessionId = httpSession.getId();
        Object tokenObj=httpSession.getAttribute(sessionId);
        LOG.info("tokenObj:" + tokenObj);
        LOG.info("accessToken:" + accessToken);
        if (accessToken != null) {
            httpSession.setAttribute(sessionId, accessToken);
        } else {
            if (null != tokenObj) {
                accessToken = tokenObj.toString();
                httpSession.setAttribute(sessionId, accessToken);
            }
        }
        
        String configName="取消原因";
        if (accessToken != null) {
            //1.查询用户是否登录
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                //2.查询该用户订单信息
            	
            	UserOrder userOrder = myService.queryUserOrderByOrderId(Long.parseLong(orderId));
            	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
            	
            	Double alreadyPay = orderService.queryPayedMoneyByOrderId(Long.parseLong(orderId));
				if(alreadyPay == null){
					alreadyPay = 0d;
				}
				orderDetailResponse.setAlreadyPay(alreadyPay);
				orderDetailResponse.setRemainPay(orderDetailResponse.getOrderPrice()-alreadyPay);
            	
            	//查询是否有部分付款
            	Double payedMoney = orderService.selPayedMoneyByOrderId(Long.parseLong(orderId));
            	Double totalFee = orderDetailResponse.getOrderPrice();
            	if(null != payedMoney) {
            		//totalFee = totalFee - payedMoney;
            	}
            	orderDetailResponse.setLeftMoney(totalFee);
            	
            	List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(Long.parseLong(orderId));
            	orderDetailResponse.setOrderDetails(orderDetails);
            	ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
            	if(configItem!=null&&configItem.getConfigId()!=null){
            	    orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
            	}else{
            	    orderDetailResponse.setItemList(null);   
            	}
            	orderDetailResponse.setTotalPay(orderDetailResponse.getOrderPrice()+orderDetailResponse.getCouponPay()+orderDetailResponse.getVoucherPay());
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(orderDetailResponse);
                baseResponse.setExt(null);
            } else {
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                baseResponse.setObj(null);
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
                model.addAttribute("baseResponse", baseResponse);
            }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        model.addAttribute("fromApp", 1);
        model.addAttribute("baseResponse", baseResponse);
        return "order/orderDetails_version2_2.ftl";
    }
    

    @RequestMapping(value = "/weborderDetails", method = RequestMethod.GET)
    public ModelAndView weborderDetails(
   		 @RequestParam(value = "orderId", required = false) String orderId,HttpSession httpSession) {
  	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        Map<String, Object> model = new HashMap<String, Object>();
        HttpMessage message = new HttpMessage();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        if(StringUtils.isNotBlank(accessToken)) {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null && null != tLog.getuId()) {
                try{
                    String configName="取消原因";
                    //2.查询该用户订单信息
                    UserOrder userOrder = myService.queryUserOrderByOrderId(Long.parseLong(orderId));
                	HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);
                	
                	//查询是否有部分付款
                	Double payedMoney = orderService.selPayedMoneyByOrderId(Long.parseLong(orderId));
                	Double totalFee = orderDetailResponse.getOrderPrice();
                	if(null != payedMoney) {
                		totalFee = totalFee - payedMoney;
                	}
                	orderDetailResponse.setLeftMoney(totalFee);
                	
                	List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(Long.parseLong(orderId));
                	orderDetailResponse.setOrderDetails(orderDetails);
                	ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
                	if(configItem!=null&&configItem.getConfigId()!=null){
                	    orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));    
                	}else{
                	    orderDetailResponse.setItemList(null);   
                	}
                    baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(orderDetailResponse);
                    message.setMsg(MessageConstant.SUCCESS);
                    baseResponse.setExt(message);
                } catch(Exception e){
                    baseResponse.setCode(HttpResponseCode.FAILED);
                    message.setMsg(MessageConstant.FAILED);
                    baseResponse.setExt(message);
                    String url = webConfig.HOST + "/RES/error/500.html";
                    return new ModelAndView(new RedirectView(url));
                }
            }else{
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
                model.put("baseResponse", baseResponse);
                String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/my/weborder";
                return new ModelAndView(new RedirectView(url));
            }
        } else {
            baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
            message.setMsg(MessageConstant.USER_NOT_LOGIN);
            baseResponse.setExt(message);
            model.put("baseResponse", baseResponse);
            String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/my/weborder";
            return new ModelAndView(new RedirectView(url));
        }
        model.put("baseResponse", baseResponse);
        return new ModelAndView("order/weborderDetails.ftl",model);
    }
    
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> cancelOrder(
            @Json HttpUserOrderRequest userOrderRequest,HttpSession httpSession) {

		if (userOrderRequest != null) {
			LOG.info("MapiMyController cancelOrder userOrderRequest:{}", JsonUtils.obj2json(userOrderRequest));
		}		

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        if (userOrderRequest != null&&userOrderRequest.getOrderId()!=null) {
            Object tokenObj=httpSession.getAttribute(httpSession.getId());
            String accessToken = tokenObj!=null?tokenObj.toString():"";
            LOG.info("accessToken:"+accessToken);
            LogDo tLog = userService
                    .isLoggedIn(accessToken);
            UserOrder userOrder=myService.queryUserOrderByOrderId(userOrderRequest.getOrderId());
            if (tLog != null&&(userOrder.getUserId()).equals(tLog.getuId())) {
                Boolean isCancel=myService.cancelOrder(userOrderRequest);
                if(isCancel){
                	Long voucherId =userOrder.getVoucherId();
                	if(voucherId!=null){
                		//修改t_voucher_detail
        				//抵用券状态 1:待确认收款2:已生效3:已使用4:已失效5:使用中
        				Voucher temp =new Voucher();
        				temp.setVoucherId(voucherId);
        				temp.setVoucherStatus(VoucherConstant.STATUS_OK);
        				voucherService.updateVoucherById(temp);
        				
        				//新增t_voucher_log
        				VoucherLog log = new VoucherLog();
        				log.setVoucherId(voucherId);
        				log.setVoucherStatus(VoucherConstant.STATUS_OK);
        				//操作类型 1:注册 2:确认收款 3:已使用 ,4取消，5使用中
        				log.setOperateType(VoucherConstant.OPERATE_TYPE_CANCLE);
        				voucherService.insertVoucherLog(log);
                	}
                	baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(MessageConstant.SUCCESS);
                    baseResponse.setExt(null);	
                }else{
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
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,
                HttpStatus.OK);
    }
    
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> deleteOrder(
            @Json HttpUserOrderRequest userOrderRequest,HttpSession httpSession) {

		if (userOrderRequest != null) {
			LOG.info("MapiMyController deleteOrder userOrderRequest:{}", JsonUtils.obj2json(userOrderRequest));
		}		

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        if (userOrderRequest != null&&userOrderRequest.getOrderId()!=null) {
            Object tokenObj=httpSession.getAttribute(httpSession.getId());
            String accessToken = tokenObj!=null?tokenObj.toString():"";
            LOG.info("accessToken:"+accessToken);
            LogDo tLog = userService
                    .isLoggedIn(accessToken);
            UserOrder userOrder=myService.queryUserOrderByOrderId(userOrderRequest.getOrderId());
            if (tLog != null&&(userOrder.getUserId()).equals(tLog.getuId())) {
                Boolean isCancel=myService.deleteOrder(userOrderRequest);
                if(isCancel){
                	baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(MessageConstant.SUCCESS);
                    baseResponse.setExt(null);	
                }else{
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
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/snappingOrderDetails", method = RequestMethod.GET)
    public String snappingOrderDetails(HttpServletRequest request, Model model,
                               @RequestParam(value = "orderId", required = false) String orderId,
                               @RequestParam(value = "accessToken", required = false) String accessToken,
                               HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMessage message = new HttpMessage();
        String sessionId = httpSession.getId();
        Object tokenObj=httpSession.getAttribute(sessionId);
        LOG.info("tokenObj:" + tokenObj);
        LOG.info("accessToken:" + accessToken);
        if (accessToken != null) {
            httpSession.setAttribute(sessionId, accessToken);
        } else {
            if (null != tokenObj) {
                accessToken = tokenObj.toString();
                httpSession.setAttribute(sessionId, accessToken);
            }
        }

        String configName="取消原因";
        if (accessToken != null) {
            //1.查询用户是否登录
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                //2.查询该用户订单信息

                UserOrder userOrder = myService.queryUserOrderByOrderId(Long.parseLong(orderId));
                /**
                 * 抢购,dead line
                 */
                SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
                userOrder.setStrDeadLine(format.format(userOrder.getPayDeadLine()));

                HttpOrderDetailResponse orderDetailResponse = new HttpOrderDetailResponse(userOrder);

                //查询是否有部分付款
                Double payedMoney = orderService.selPayedMoneyByOrderId(Long.parseLong(orderId));
                Double totalFee = orderDetailResponse.getOrderPrice();
                if(null != payedMoney) {
                    totalFee = totalFee - payedMoney;
                }
                orderDetailResponse.setLeftMoney(totalFee);

                List<ProductOrderDetail> orderDetails = myService.queryOrderDetailsByOrderId(Long.parseLong(orderId));
                orderDetailResponse.setOrderDetails(orderDetails);
                ConfigItem configItem=configItemService.findConfigItemByConfigName(configName);
                if(configItem!=null&&configItem.getConfigId()!=null){
                    orderDetailResponse.setItemList(configItemService.queryItemByConfigId(configItem.getConfigId()));
                }else{
                    orderDetailResponse.setItemList(null);
                }
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(orderDetailResponse);
                baseResponse.setExt(null);
            } else {
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                baseResponse.setObj(null);
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
                model.addAttribute("baseResponse", baseResponse);
            }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        model.addAttribute("fromApp", 1);
        model.addAttribute("baseResponse", baseResponse);
        return "order/snappingOrderDetails.ftl";
    }


}
