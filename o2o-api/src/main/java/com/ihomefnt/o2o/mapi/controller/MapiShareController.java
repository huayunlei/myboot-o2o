package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpGetKRequest;
import com.ihomefnt.o2o.intf.manager.util.common.wechat.Weixinutil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(value="M站分享API",description="M站分享老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/share")
public class MapiShareController {
	
	@RequestMapping(value = "/getKey", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> wxConfig(HttpGetKRequest request,
            HttpSession httpSession) {

		HttpBaseResponse baseResponse = new HttpBaseResponse();
		if(null != request 
				&& StringUtils.isNotBlank(request.getShareUrl())){
			Map<String, String> map = Weixinutil.sign(request.getShareUrl(),null,null,"true");
            baseResponse.setCode(HttpResponseCode.SUCCESS);
            baseResponse.setObj(map);
            baseResponse.setExt(MessageConstant.SUCCESS);
		} else{
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            baseResponse.setExt(MessageConstant.FAILED);
		}
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
	}
}
