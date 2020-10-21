/**
 * 
 */
package com.ihomefnt.o2o.api.controller.dic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ihomefnt.o2o.intf.domain.dic.vo.request.HttpKeyRequest;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.AjbHelpDicResponseVo;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.CashCouponDicResponseVo;
import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dic")
@Api(tags = "【字典api】")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
     * 功能描述：查询现金券在线帮助
     */
	@ApiOperation(value="getCashHelpDesc",notes="查询现金券在线帮助")
	@PostMapping(value = "/getCashHelpDesc")
	public HttpBaseResponse<CashCouponDicResponseVo> getCashHelpDesc(@Json HttpBaseRequest baseRequest) {
		if (baseRequest == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		CashCouponDicResponseVo obj = dictionaryService.getCashHelpDesc(baseRequest);
		return HttpBaseResponse.success(obj);
	}
	
	/**
     * 功能描述：艾积分在线帮助说明
     */
	@ApiOperation(value="getAjbHelpDesc",notes="艾积分在线帮助说明")
	@PostMapping(value = "/getAjbHelpDesc")
	public HttpBaseResponse<AjbHelpDicResponseVo> getAjbHelpDesc(@Json HttpBaseRequest baseRequest) {
		if (baseRequest == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		AjbHelpDicResponseVo obj = dictionaryService.getAjbHelpDesc(baseRequest);
		return HttpBaseResponse.success(obj);
	}
	
	/**
     * 功能描述：获取字典
     */
	@ApiOperation(value="getTextDescByKey",notes="获取字典")
	@PostMapping(value = "/getTextDescByKey")
	public HttpBaseResponse<String[]> getTextDescByKey(@RequestBody HttpKeyRequest baseRequest) {
		if (baseRequest == null || baseRequest.getKeyType() == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		String[] obj = dictionaryService.getTextDescByKey(baseRequest);
		return HttpBaseResponse.success(obj);
	}
}
