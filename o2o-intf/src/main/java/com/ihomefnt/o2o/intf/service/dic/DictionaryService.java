package com.ihomefnt.o2o.intf.service.dic;

import com.ihomefnt.o2o.intf.domain.dic.vo.request.HttpKeyRequest;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.AjbHelpDicResponseVo;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.CashCouponDicResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface DictionaryService {

	/** 
	* @Title: getCashHelpDesc 
	* @Description: 查询现金券在线帮助
	* @param @param baseRequest
	* @return CashCouponDicResponseVo    返回类型 
	* @throws 
	*/
	CashCouponDicResponseVo getCashHelpDesc(HttpBaseRequest baseRequest);

	/** 
	* @Title: getAjbHelpDesc 
	* @Description: 艾积分在线帮助说明
	* @param @param baseRequest
	* @return AjbHelp    返回类型 
	* @throws 
	*/
	AjbHelpDicResponseVo getAjbHelpDesc(HttpBaseRequest baseRequest);

	/** 
	* @Title: getTextDescByKey 
	* @Description: 获取字典 
	* @param @param baseRequest
	* @return String[]    返回类型 
	* @throws 
	*/
	String[] getTextDescByKey(HttpKeyRequest baseRequest);
	
	/**
	 * 
	 * 通过键来查询对应的值
	 * @return
	 */
	String getValueByKey(String key);

}
