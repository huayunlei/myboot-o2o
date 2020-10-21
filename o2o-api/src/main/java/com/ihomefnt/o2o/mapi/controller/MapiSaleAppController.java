package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.common.util.HttpRequestUtil;
import com.ihomefnt.o2o.intf.service.token.TokenService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.token.vo.request.HttpSaleGoalFilterRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(value="M站销售API",description="M站销售老接口",tags = "【M-API】")
@Component
@RequestMapping(value = "/mapi/salesapp")
public class MapiSaleAppController {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	WebConfig webConfig;
	
    @RequestMapping(value = "/querySalespersonSales/{token}", method = RequestMethod.GET)
    public String queryMyCustomerInfo(@PathVariable String token,Model model,HttpSession httpSession) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
		Map userIds = tokenService.selSessionIdIsEffective(token);
		if(null != userIds && userIds.size() > 0){
			HttpSaleGoalFilterRequest  request = new HttpSaleGoalFilterRequest();
			request.setSaleId(Long.valueOf(String.valueOf(userIds.get("adminId"))));
			baseResponse = HttpRequestUtil.httpRequestJson(webConfig.cmsUrl + "/sale/querySalespersonSales/"+ userIds.get("adminId") + "/" + userIds.get("sessionId"), request);
		} else {
            baseResponse.setCode(HttpResponseCode.ADMIN_ILLEGAL);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.ADMIN_ILLEGAL);
            baseResponse.setExt(message);
		}
        model.addAttribute("baseResponse", baseResponse);
        return "salesapp/salse.ftl";
    }
}
