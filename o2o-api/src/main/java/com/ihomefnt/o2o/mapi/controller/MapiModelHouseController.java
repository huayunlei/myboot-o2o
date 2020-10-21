package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelHousesResponse;
import com.ihomefnt.o2o.intf.domain.house.dto.TModelHouses;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpHomeResponse;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import com.ihomefnt.o2o.intf.service.house.ModelHouseService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import io.swagger.annotations.Api;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(value="M站样板间老API",description="M站样板间老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/sampleroom")
public class MapiModelHouseController {
		
    @Autowired
    ProductService productService;
    
    @Autowired
    AdService adService;
    
    @Autowired
	private BundleService bundleService;
    
    @Autowired
    private WebConfig webConfig;

	@Autowired
	ModelHouseService modelHouseService;
	
    @Autowired
    TKDMDao tkdmDao;
	
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String activityMain(Model model,
    		HttpServletRequest request) {
    	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpHomeResponse res = productService.home150(null);
        //首页banner广告只展示两天
        res.setBannerList(adService.queryAdFromProtocol(10, "http", ""));

        //3.组装返回数据
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(res);
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("downloadurl", getDownLoadUrl(ua));

        TKDMSeo tkdmSeo = tkdmDao.loadTKDM("首页");
        model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
        model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
        model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());

        return "product/wapHome.ftl";

    }
       
    private static final String IPHONE = "iphone";
    private static final String WEIXIN = "micromessenger";
    private String getDownLoadUrl(String ua) {
        String pvalue = "10000";
        if (ua.indexOf(IPHONE) > 0) {
            pvalue = "100";
        }
        if (ua.indexOf(WEIXIN) > 0) {
            return webConfig.HOST + "/app/" + pvalue;
        }
        AppVersionDto appVersion = bundleService.getLatestApp(pvalue, null);
        return appVersion.getDownload();
    }
    
    @RequestMapping(value = "/goSignUp", method = RequestMethod.GET)
    public String goEnroll(Model model,
    		HttpSession httpSession) {
  	
		return "sampleroom/signUp.ftl";
		
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
     	List<String> mobile = modelHouseService.queryEnrollList();
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
    public ResponseEntity<HttpBaseResponse> enrollModelHouse(TModelHouses house
    		, HttpSession httpSession) {
		
    	HttpBaseResponse baseResponse = new HttpBaseResponse();
    	if(null == house || StringUtils.isBlank(house.getMobile())){
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            baseResponse.setExt(MessageConstant.DATA_TRANSFER_EMPTY);
    	} else {
    		String mobile = house.getMobile();
    		boolean flag = true;
    		List<String> mobileList = modelHouseService.queryEnrollList();
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
         			modelHouseService.enrollModelHouse(house);
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
   	
    	return "sampleroom/signUpSuccess.ftl";
    }
    
    @RequestMapping(value = "/signUpFailure", method = RequestMethod.GET)
    public String enrollFailed(Model model,
    		HttpSession httpSession) {
   	
    	return "sampleroom/signUpFailure.ftl";
    }
    
    @RequestMapping(value = "/haveSigned", method = RequestMethod.GET)
    public String haveSigned(Model model, 
    		HttpSession httpSession) {
     	
    	return "sampleroom/haveSigned.ftl";
    }
}
