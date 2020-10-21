package com.ihomefnt.o2o.api.controller.coupon;

import com.ihomefnt.o2o.api.controller.BaseController;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CashCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CenterCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.ReceiveCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CashCouponResponseVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CenterCouponResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.coupon.CashCouponService;
import com.ihomefnt.o2o.intf.service.coupon.VoucherService;
import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "【现金券抵用券API】")
@RestController
@RequestMapping("/cashCoupon")
public class CashCouponController extends BaseController{
	
	@Autowired
	CashCouponService cashCouponService;
	
    @Autowired
    UserService userService;
    
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	DictionaryService dictionaryService;
	
    @RequestMapping(value = "/queryCashCoupon", method = RequestMethod.POST)
	@Deprecated
    public HttpBaseResponse<CashCouponResponseVo> queryCashCoupon(@Json CashCouponRequestVo request) {
		return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
//    	if (request == null) {
//			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
//		}
//		HttpUserInfoRequest user = request.getUserInfo();
//        if (user == null) {
//        	return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
//        }
    	
//    	CashCouponResponseVo obj = cashCouponService.queryCouponByUserId(user.getId().longValue(), request.getIsRead());
//    	return HttpBaseResponse.success(null);
    }
    
    @RequestMapping(value = "/queryAccountMoney", method = RequestMethod.POST)
    public HttpBaseResponse<CashCouponResponseVo> queryAccountMoney(@Json CashCouponRequestVo request) {
    	if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
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
    
    @RequestMapping(value = "/updateIsRead", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> updateIsRead(@Json CashCouponRequestVo request) {
    	if (request == null || request.getCouponId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
        	return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        Integer obj = cashCouponService.updateStatus(request);
        return HttpBaseResponse.success(obj);
    }
    
    @RequestMapping(value = "/couponList", method = RequestMethod.POST)
    public HttpBaseResponse<CenterCouponResponseVo> couponList(@Json CenterCouponRequestVo request){
    	if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
    	
    	CenterCouponResponseVo centerCouponResponse = new CenterCouponResponseVo();
    	centerCouponResponse.setHeadImage(dictionaryService.getValueByKey("ACTION_TYPE_HEADIMG"));
		HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
        	List<CenterCouponDto> couponList = cashCouponService.getAllCoupons();
    		if(couponList != null && couponList.size() > 0){
    			centerCouponResponse.setCouponList(couponList);
    		}
        } else {
        	String mobile = userDto.getMobile();
    		List<CenterCouponDto> couponList = cashCouponService.getUseableCoupons(mobile);
    		if(couponList != null && couponList.size() > 0){
    			centerCouponResponse.setCouponList(couponList);
    		}
        }
        return HttpBaseResponse.success(centerCouponResponse);
    }
    
    @RequestMapping(value = "/receiveCoupon", method = RequestMethod.POST)
    public HttpBaseResponse<Void> receiveCoupon(@Json ReceiveCouponRequestVo request){
    	if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
        	return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        
		String mobile = userDto.getMobile();
		int couponType = request.getCouponType();
		Long couponId = request.getCouponId();
		int resultCode = cashCouponService.updateVoucher(mobile,couponType,couponId);
		String msg = getUpdateVoucherMsgByCode(resultCode);
		if (null != msg) {
			return HttpBaseResponse.fail(msg);
		}
		return HttpBaseResponse.success();
    }

	private String getUpdateVoucherMsgByCode(int resultCode) {
		switch (resultCode) {
		case 1:
			return null;
		case 3:
			return MessageConstant.NO_VOUCHER_EXIST;
		case 4:
			return MessageConstant.VOUCHER_OVERTIME;
		default:
			return MessageConstant.FAILED;
		}
	}
    
}
