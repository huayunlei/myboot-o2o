package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.FormaldehydeResponse;
import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.TFormaldehyde;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.formaldehyde.FormaldehydeService;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(value="M站测甲醛活动API",description="M站测甲醛活动接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/formaldehyde")
public class MapiFormaldehydeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapiFormaldehydeController.class);
	
	@Autowired
	FormaldehydeService formaldehydeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String activityMain(Model model,
    		HttpSession httpSession) {		
    	int cnt = 0;
    	List<String> res = new ArrayList<String>();
    	
    	List<String> mobile = formaldehydeService.queryFormaldehyde();
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$"); 
        if(null != mobile && mobile.size() > 0){
        	for(String s : mobile){
        		Matcher m = p.matcher(s);
        		if(StringUtils.isNotBlank(s) && m.matches()){
        			s= s.substring(0,s.length()-(s.substring(3)).length())+"****"+s.substring(7);
        			res.add(s);
        		}
        	}
        }
        if(null != res && res.size() > 0){
        	cnt = res.size();
        }
    	model.addAttribute("mobile", res);
    	model.addAttribute("count", cnt);
    	
		model.addAttribute("enable", true);
		model.addAttribute("title1", "艾佳帮你免费测甲醛，新房入住“醛”程无压力！ ");
		model.addAttribute("icon1", StaticResourceConstants.M_FORMALDEHYDE_ICON_URL);
		model.addAttribute("desc", "30秒快速申请免费上门测量服务，为健康加油，享受入住前的安心生活！");
		
    	return "formaldehyde/homecard.ftl";
    }
    
    @RequestMapping(value = "/goSignUp", method = RequestMethod.GET)
    public String goEnroll(Model model,
    		HttpSession httpSession) {  	
		return "formaldehyde/signUp.ftl";
    }
    
    /**
     * 实时获得报名人数和报名电话列表
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/ajaxGetSignUpInfo", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> ajaxGetInfo(HttpSession httpSession) {
 	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
         
     	int cnt = 0;
     	List<String> res = new ArrayList<String>();
     	List<String> mobile = formaldehydeService.queryFormaldehyde();
         Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$"); 
         if(null != mobile && mobile.size() > 0){
         	for(String s : mobile){
         		Matcher m = p.matcher(s);
         		if(StringUtils.isNotBlank(s) && m.matches()){
         			s= s.substring(0,s.length()-(s.substring(3)).length())+"****"+s.substring(7);
         			res.add(s);
         		}
         	}
         }
         if(null != res && res.size() > 0){
         	cnt = res.size();
         }
         FormaldehydeResponse formaldehydeResponse = new FormaldehydeResponse();
         formaldehydeResponse.setCount(cnt); 
         formaldehydeResponse.setMobile(res);
         
         baseResponse.setCode(HttpResponseCode.SUCCESS);
         baseResponse.setObj(formaldehydeResponse);
         baseResponse.setExt(null);
         
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Access-Control-Allow-Origin", "*");
         headers.set("Access-Control-Request-Method", "post");
         return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> enrollModelHouse(@Json TFormaldehyde formaldehyde
    		, HttpSession httpSession) {
		if (formaldehyde != null) {
			LOG.info("MapiFormaldehydeController enrollModelHouse formaldehyde:{}", JsonUtils.obj2json(formaldehyde));
		} 		
    	HttpBaseResponse baseResponse = new HttpBaseResponse();
    	if(null == formaldehyde || StringUtils.isBlank(formaldehyde.getMobile())){
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            baseResponse.setExt(MessageConstant.DATA_TRANSFER_EMPTY);
    	} else {
    		String mobile = formaldehyde.getMobile();
    		boolean flag = true;
    		List<String> mobileList = formaldehydeService.queryFormaldehyde();
    		if(null != mobileList && mobileList.size() > 0) {
    			for(String s : mobileList){
    				if(StringUtils.isNotBlank(s) && s.equals(mobile)){
    					flag = false;
    					break;
    				}
    			}
    		}
    		
    		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
     		Matcher m = p.matcher(mobile);
     		if(StringUtils.isNotBlank(mobile) && m.matches()){
     			if(flag) {
     				formaldehydeService.enrollFormaldehyde(formaldehyde);
                    baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(null);
                    baseResponse.setExt(MessageConstant.SUCCESS);
     			} else {//已经报过名了，不能再次报名
                    baseResponse.setCode(HttpResponseCode.Data_EXISTS);
                    baseResponse.setObj(null);
                    baseResponse.setExt(MessageConstant.DATA_EXISTS);
     			}
     		} else {
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                baseResponse.setExt(MessageConstant.FAILED);
     		}
    	}
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/signUpSuccess", method = RequestMethod.GET)
    public String enrollSuccess(Model model, 
    		HttpSession httpSession) {
   	
    	return "formaldehyde/signUpSuccess.ftl";
    }
    
    @RequestMapping(value = "/signUpFailure", method = RequestMethod.GET)
    public String enrollFailed(Model model,
    		HttpSession httpSession) {
    	
    	return "formaldehyde/signUpFailure.ftl";
    }
    
    @RequestMapping(value = "/haveSigned", method = RequestMethod.GET)
    public String haveSigned(Model model, 
    		HttpSession httpSession) {
   	
    	return "formaldehyde/haveSigned.ftl";
    }
}
