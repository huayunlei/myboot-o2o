package com.ihomefnt.o2o.intf.domain.coupon.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;



/**
 * 选择可用的抵用
 * 
 * @author shenchen
 * @date 2016年3月25日 下午4:36:32
 */
@Data
public class VoucherRequestVo extends HttpBaseRequest{
	
	//应付金额
	private double amountPayable;
	//订单类型
	private List<String> orderTypeList;
	//品类
	private List<String> productTypeList;
}
