package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.deal.dto.DealPickHomeResponse;
import com.ihomefnt.o2o.intf.domain.deal.dto.OrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.user.vo.request.LoginRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.service.deal.DealService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
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

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Created by hvk687 on 9/29/15.
 */
@Api(value="M站一元购API",description="M站一元购接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/onedeal")
public class MapiOneDealController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapiOneDealController.class);
			
    @Autowired
    UserService userService;
    @Autowired
    DealService dealService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String dealLogin() {
        return "salessearch/login.ftl";
    }


    /**
     * 点登陆按钮进行登录
     */
    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> logon(LoginRequestVo loginRequest, HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (loginRequest == null || StringUtil.isNullOrEmpty(loginRequest.getMobile())
                || StringUtil.isNullOrEmpty(loginRequest.getPassword())) {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.USER_PASS_EMPTY);
            baseResponse.setExt(message);
        } else {
            String mobile = loginRequest.getMobile();
            String userPd = loginRequest.getPassword();
            if ("18888888888".equals(mobile) && "888888".equals(userPd)) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(null);
                baseResponse.setExt(MessageConstant.SUCCESS);

                httpSession.setAttribute(httpSession.getId(), "abckd987123hoke");

            } else {
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_EXISTS);
                baseResponse.setExt(message);
            }
            
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/homecard", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
 	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);

        String sessionId = session.getId();
        Object attr = session.getAttribute(sessionId);
        if (attr == null) {
            return "salessearch/login.ftl";
        } else {
            String accessToken = (String) attr;
            if (StringUtil.isNullOrEmpty(accessToken)) {
                return "salessearch/login.ftl";
            } else {
                DealPickHomeResponse response = dealService.loadHomeResponse();
                baseResponse.setObj(response);
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                model.addAttribute("baseResponse", baseResponse);
            }
        }
        return "salessearch/homecard.ftl";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "mobile", required = false) String mobile, HttpSession session, Model model) {
    	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);

        String sessionId = session.getId();
        Object attr = session.getAttribute(sessionId);
        if (attr == null) {
            model.addAttribute("baseResponse", baseResponse);
            return "salessearch/login.ftl";
        } else {
            String accessToken = (String) attr;
            if (StringUtil.isNullOrEmpty(accessToken)) {
                return "salessearch/login.ftl";
            } else {
                if (!StringUtil.isNullOrEmpty(mobile)) {
                    OrderDetailResponse response = dealService.loadOrderDetailResponse(mobile);
                    if (response != null) {
                        response.setMobile(mobile);
                        baseResponse.setObj(response);
                        baseResponse.setCode(HttpResponseCode.SUCCESS);
                    }
                }
            }
        }
        model.addAttribute("baseResponse", baseResponse);
        return "salessearch/searchResult.ftl";
    }

    @RequestMapping(value = "/pick/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<HttpBaseResponse> ajaxPick(@PathVariable String orderNo, HttpSession session, Model model) {
    	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        boolean ret = dealService.pickOrder(orderNo);
        if (ret) {
            baseResponse.setCode(HttpResponseCode.SUCCESS);
        }
        return new ResponseEntity<HttpBaseResponse>(baseResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "share/{code}", method = RequestMethod.GET)
    public String dealShare(@PathVariable Integer code, Model model) {

        String ftl = "";
        if (code == null) {
            return "";
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp end1 = Timestamp.valueOf("2015-10-09 10:30:00");
        Timestamp end2 = Timestamp.valueOf("2015-10-11 23:59:59");
        switch (code) {
            case 1: //郑州开业活动
                end1 = Timestamp.valueOf("2015-10-09 10:30:00");
                end2 = Timestamp.valueOf("2015-10-11 23:59:59");

                model.addAttribute("diff", end1.getTime() - now.getTime());
                model.addAttribute("diff2", end2.getTime() - now.getTime());

                model.addAttribute("enable", true);
                model.addAttribute("title1", "1元限时抢懒人沙发，手快有，手慢无！");
                model.addAttribute("icon1", "http://pc2.img.ihomefnt.com/henanpanic.png");
                model.addAttribute("desc", "10.9-10.11，懒人沙发1元抢，小伙伴们快来参与河南艾佳线上抢购，到店领取活动吧！");
                ftl = "topic/henanqianggou.ftl";
                break;
            case 2:
                end1 = Timestamp.valueOf("2015-11-08 14:25:00");
                end2 = Timestamp.valueOf("2015-11-08 23:59:59");

                model.addAttribute("diff", end1.getTime() - now.getTime());
                model.addAttribute("diff2", end2.getTime() - now.getTime());

                model.addAttribute("enable", true);
                model.addAttribute("title1", "1元限时抢卡通靠垫，手快有，手慢无！");
                model.addAttribute("icon1", StaticResourceConstants.M_WX_ZHONGNAN_IMG);
                model.addAttribute("desc", "11月8日下午14：25卡通靠垫1元开抢，小伙伴们快来参与南京中南世纪雅苑线上抢购，到店领取活动吧！");
                ftl = "topic/zhongnanqianggou.ftl";
                break;
            default://do nothing
        }
        return ftl;
    }
}
