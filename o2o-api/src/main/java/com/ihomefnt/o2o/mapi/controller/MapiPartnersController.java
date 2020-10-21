package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelHousesResponse;
import com.ihomefnt.o2o.intf.domain.partners.dto.Area;
import com.ihomefnt.o2o.intf.domain.partners.dto.TPartners;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.service.partners.PartnersService;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(value="M站合作伙伴API",description="M站合作伙伴老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/partner")
public class MapiPartnersController {

	@Autowired
	PartnersService partnersService;
	
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String activityMain(Model model, 
    		HttpSession httpSession,HttpServletRequest request,
    		HttpServletResponse response) {
		
    	int cnt = 0;
    	List<String> res = new ArrayList<String>();
    	
    	List<String> mobile = partnersService.queryEnrollList();
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
		String cityCode = request.getParameter("cityCode");
		if(StringUtils.isBlank(cityCode)){
			cityCode = "210000";
		}
		
        Cookie cookie = new Cookie("cityCode",cityCode);
        cookie.setPath("/");
        cookie.setMaxAge(1200000);
        response.addCookie(cookie);
        
    	model.addAttribute("mobile", res);
    	model.addAttribute("count", cnt);
    	
		model.addAttribute("enable", true);
		model.addAttribute("title1", "互联网软装合伙人招募邀你轻松赚钱！");
		model.addAttribute("icon1", StaticResourceConstants.M_SOFT_PARTNER_WX_LOGO_IMG);
		model.addAttribute("desc", "15%高额分成，发挥你的实力横扫小区！");
		
    	return "partner/homecard.ftl";
    }
    
    @RequestMapping(value = "/goSignUp", method = RequestMethod.GET)
    public String goEnroll(Model model, 
    		HttpSession httpSession,HttpServletRequest request) {

		String cityCode = "";
		
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("cityCode".equals(cookie.getName())){
            	cityCode = cookie.getValue();
            }
        }
		
		if(StringUtils.isBlank(cityCode)){
			cityCode = "210000";
		}
		List<Area> list = partnersService.queryAreaBuilding(cityCode);
		model.addAttribute("areaListStr", JSONArray.fromObject(list).toString());
		model.addAttribute("areaList", list);
		return "partner/signUp.ftl";
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
     	List<String> mobile = partnersService.queryEnrollList();
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
         ModelHousesResponse modelHousesResponse = new ModelHousesResponse();
         modelHousesResponse.setCount(cnt); 
         modelHousesResponse.setMobile(res);
         
         baseResponse.setCode(HttpResponseCode.SUCCESS);
         baseResponse.setObj(modelHousesResponse);
         baseResponse.setExt(null);
         
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Access-Control-Allow-Origin", "*");
         headers.set("Access-Control-Request-Method", "post");
         return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> enrollModelHouse(TPartners partners
    		, HttpSession httpSession) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
    	if(null == partners || StringUtils.isBlank(partners.getMobile())){
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            baseResponse.setExt(MessageConstant.DATA_TRANSFER_EMPTY);
    	} else {
    		String mobile = partners.getMobile();
    		boolean flag = true;
    		String mobileList = partnersService.queryEnrollByMobile(mobile);
    		if(StringUtils.isNotBlank(mobileList)) {
    			flag = false;
    		}
    		
    		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
     		Matcher m = p.matcher(mobile);
     		if(StringUtils.isNotBlank(mobile) && m.matches()){
     			if(flag) {
     				partnersService.enrollPartners(partners);
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

    	return "partner/signUpSuccess.ftl";
    }
    
    @RequestMapping(value = "/signUpFailure", method = RequestMethod.GET)
    public String enrollFailed(Model model,
    		HttpSession httpSession) {

    	return "partner/signUpFailure.ftl";
    }
    
    @RequestMapping(value = "/haveSigned", method = RequestMethod.GET)
    public String haveSigned(Model model,
    		HttpSession httpSession) {

    	return "partner/haveSigned.ftl";
    }
}
