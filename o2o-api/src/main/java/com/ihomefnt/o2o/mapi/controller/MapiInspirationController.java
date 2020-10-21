package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.comment.vo.request.UserCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.UserCommendResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpInspirationRequest;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCaseDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpStrategyDetailResponse;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.inspiration.InspirationService;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Api(value="M站灵感文章API",description="M站灵感文章接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiInspirationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapiInspirationController.class);

	@Autowired
	InspirationService inspirationService;
	
	@Autowired
    CommentService commentService;
	
	@RequestMapping(value = "/caseDetail/{nodeId}", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> caseDetail(@PathVariable Long nodeId,@RequestParam(value = "userAgent", required = true) String userAgent ,HttpServletRequest request) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        
        HttpInspirationRequest inspirationRequest = new HttpInspirationRequest();
        inspirationRequest.setInspirationId(nodeId);
        
        HttpCaseDetailResponse caseDetailResponse = inspirationService.queryCaseDetail(inspirationRequest);
        
        inspirationRequest.setType(3l);
        inspirationService.updateViewCount(inspirationRequest);
        UserCommentRequestVo commentRequest = new UserCommentRequestVo();
        commentRequest.setPageNo(0);
        commentRequest.setPageSize(2);
        commentRequest.setType(5l);
        commentRequest.setProductId(inspirationRequest.getInspirationId());
        UserCommendResponseVo userCommendResponse = commentService.queryUserCommentList(commentRequest);
        caseDetailResponse.setCommentCount(userCommendResponse.getTotalRecords());
        
        caseDetailResponse.setUrlType("caseDetail");
        
        String ua = userAgent;
        caseDetailResponse.setUA(ua);
        
        Case caseInfo = caseDetailResponse.getCaseInfo();
        
        String icon = null;
        if(null != caseInfo.getImageList() && caseInfo.getImageList().size()>0){
        	icon = caseInfo.getImageList().get(0);
        }
        
        String desc = null;
        if (StringUtils.isNotBlank(caseInfo.getDescription())) {
            JSONArray jsonArray = JSONArray.fromObject(caseInfo.getDescription());
            for(int i = 0; i < jsonArray.size(); i++){
            	JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));
            	if("text".equals(jsonObj.getString("type"))){
            		desc = jsonObj.getString("content");
            		break;
            	}
            }
        }
        caseDetailResponse.setEnable(true);
        caseDetailResponse.setTitle1(caseInfo.getCaseName());
        caseDetailResponse.setIcon1(icon);
        caseDetailResponse.setDesc(desc);
        
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(caseDetailResponse);
        baseResponse.setExt(null);
        //model.addAttribute("mUrl", "http://m.ihomefnt.com/caseDetail");
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
//    @RequestMapping(value = "/strategyDetail/{nodeId}", method = RequestMethod.GET)
//    public String strategyDetail(Model model, @PathVariable Long nodeId, HttpServletRequest request) {
//    	LOG.info("strategyDetail() interface start");
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        
//        HttpInspirationRequest inspirationRequest = new HttpInspirationRequest();
//        inspirationRequest.setInspirationId(nodeId);
//        
//        HttpStrategyDetailResponse strategyDetailResponse = inspirationService.queryStrategyDetail(inspirationRequest);
//        
//        inspirationRequest.setType(2l);
//        inspirationService.updateViewCount(inspirationRequest);
//        HttpUserCommentRequest commentRequest = new HttpUserCommentRequest();
//        commentRequest.setPageNo(0);
//        commentRequest.setPageSize(3);
//        commentRequest.setType(4l);
//        commentRequest.setProductId(inspirationRequest.getInspirationId());
//        HttpUserCommendResponse userCommendResponse = commentService.queryUserCommentList(commentRequest);
//        strategyDetailResponse.setCommentCount(userCommendResponse.getTotalRecords());
//        
//        baseResponse.setCode(HttpResponseCode.SUCCESS);
//        baseResponse.setObj(strategyDetailResponse);
//        baseResponse.setExt(null);
//        model.addAttribute("baseResponse", baseResponse);
//        model.addAttribute("urlType", "strategyDetail");
//        
//        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
//        model.addAttribute("UA", ua);
//        
//        Strategy strategy = strategyDetailResponse.getStrategy();
//        
//        String icon = null;
//        if(null != strategy.getImageList() && strategy.getImageList().size()>0){
//        	icon = strategy.getImageList().get(0);
//        }
//        
//        String desc = null;
//        if (StringUtils.isNotBlank(strategy.getDescription())) {
//            JSONArray jsonArray = JSONArray.fromObject(strategy.getDescription());
//            for(int i = 0; i < jsonArray.size(); i++){
//            	JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));
//            	if("text".equals(jsonObj.getString("type"))){
//            		desc = jsonObj.getString("content");
//            		break;
//            	}
//            }
//        }
//        
//        model.addAttribute("enable", true);
//        model.addAttribute("title1", strategy.getStrategyName());
//        model.addAttribute("icon1", icon);
//        model.addAttribute("desc", desc);
//        //model.addAttribute("mUrl", "http://m.ihomefnt.com/strategyDetail");
//        
//        return "design/strategy.ftl";
//    }
    
    @RequestMapping(value = "/strategyDetail/{nodeId}", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> strategyDetail(@PathVariable Long nodeId,@RequestParam(value = "userAgent", required = true) String userAgent, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        
        HttpInspirationRequest inspirationRequest = new HttpInspirationRequest();
        inspirationRequest.setInspirationId(nodeId);
        
        HttpStrategyDetailResponse strategyDetailResponse = inspirationService.queryStrategyDetail(inspirationRequest);
        
        inspirationRequest.setType(2l);
        inspirationService.updateViewCount(inspirationRequest);
        UserCommentRequestVo commentRequest = new UserCommentRequestVo();
        commentRequest.setPageNo(0);
        commentRequest.setPageSize(3);
        commentRequest.setType(4l);
        commentRequest.setProductId(inspirationRequest.getInspirationId());
        UserCommendResponseVo userCommendResponse = commentService.queryUserCommentList(commentRequest);
        strategyDetailResponse.setCommentCount(userCommendResponse.getTotalRecords());
        
        strategyDetailResponse.setUrlType("strategyDetail");
        String ua = userAgent;
        strategyDetailResponse.setUA(ua);
        
        Strategy strategy = strategyDetailResponse.getStrategy();
        
        String icon = null;
        if(null != strategy.getImageList() && strategy.getImageList().size()>0){
        	icon = strategy.getImageList().get(0);
        }
        
        String desc = null;
        if (StringUtils.isNotBlank(strategy.getDescription())) {
            JSONArray jsonArray = JSONArray.fromObject(strategy.getDescription());
            for(int i = 0; i < jsonArray.size(); i++){
            	JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));
            	if("text".equals(jsonObj.getString("type"))){
            		desc = jsonObj.getString("content");
            		break;
            	}
            }
        }
        strategyDetailResponse.setEnable(true);
        strategyDetailResponse.setTitle1(strategy.getStrategyName());
        strategyDetailResponse.setIcon1(icon);
        strategyDetailResponse.setDesc(desc);
        
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(strategyDetailResponse);
        baseResponse.setExt(null);
        //model.addAttribute("mUrl", "http://m.ihomefnt.com/strategyDetail");
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/anniversary/1st", method = RequestMethod.GET)
    public String anniversary(Model model, HttpServletRequest request) {
        
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活");
        model.addAttribute("icon1", "https://pc3-img.ihomefnt.com/aijia-year.png?imageView2/1/w/300/h/300");
        model.addAttribute("desc", "艾佳生活一周年壁纸");
        
        return "topic/anniversary.ftl";
    }
    
    
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model, HttpServletRequest request) {
        
        return "about.ftl";
    }
    
    // happyNewYear
    @RequestMapping(value = "/paycall", method = RequestMethod.GET)
    public String bossInfo(Model model, HttpServletRequest request) {

        model.addAttribute("enable", true);
        model.addAttribute("title1", "中城联盟恭祝您");
        model.addAttribute("icon1", StaticResourceConstants.M_INSPIRATION_ZCLM_IMG);
        model.addAttribute("desc", "逢金猴祥瑞，遇万事吉祥");
        
        return "topic/bossInfo.ftl";
    }
    
    
    @RequestMapping(value = "/bless", method = RequestMethod.GET)
    public String bless(Model model, HttpServletRequest request) {

        String userName = request.getParameter("userName");
        String company =  request.getParameter("company");
        
        try {
            userName = new String(userName.getBytes("iso8859-1"),"utf-8");
            company = new String(company.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        model.addAttribute("userName", userName);
        model.addAttribute("company", company);
        model.addAttribute("enable", true);
        model.addAttribute("title1", "中城联盟" + userName + "恭祝您");
        model.addAttribute("icon1", StaticResourceConstants.M_INSPIRATION_ZCLM_IMG);
        model.addAttribute("desc", "逢金猴祥瑞，遇万事吉祥");
        
        return "topic/blessing.ftl";
    }
    
    
}
