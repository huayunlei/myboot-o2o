package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;
import com.ihomefnt.o2o.intf.domain.tactivity.dto.TTmpActivity;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserRelationDo;
import com.ihomefnt.o2o.intf.domain.user.doo.WalletDo;
import com.ihomefnt.o2o.intf.domain.user.dto.TUserRelationResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.TWalletResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AppAboutResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.FeedbackResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.InvitedUserResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserWishListResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.exception.UserNotExistException;
import com.ihomefnt.o2o.intf.manager.util.common.SecurityUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import com.ihomefnt.o2o.intf.service.feedback.FeedbackService;
import com.ihomefnt.o2o.intf.service.sms.SmsService;
import com.ihomefnt.o2o.intf.service.tactivity.TmpActivityService;
import com.ihomefnt.o2o.intf.service.user.MyService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.ihomefnt.user.dto.UserDto;

/**
 * Created by zhouhai on 2015/2/3.
 */
@Api(value="M站用户API",description="M站用户老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiUserController {
	
    private static final Logger LOG = LoggerFactory.getLogger(MapiUserController.class);
    
    private static final String  url="/app/";

    @Autowired
    MyService myService;

    @Autowired
    UserService userService;
    @Autowired
	private BundleService bundleService;
    
    @Autowired
    FeedbackService feedbackService;
    
    private static String AGENT_WEIXIN = "micromessenger";
    private static final String ANDROID = "android";
    private static final String IPHONE = "iphone";
    
    @Autowired
    private WebConfig config;
    
    private static Map<String, String> mapMobile=new HashMap<String, String>();
    static{
        mapMobile.put("18602537250", "18602537250");
        mapMobile.put("13851680661", "13851680661");
        mapMobile.put("15251822922", "15251822922");
    }
    @Autowired
    TmpActivityService tmpActivityService;
    
    @Autowired
    SmsService smsService;
    
//    @Autowired
//    IUserFacade userFacade;
    	
	@Autowired
	UserProxy userProxy;
    
    @RequestMapping(value = "/tactivity/thome", method = RequestMethod.GET)
    public String thome(
            Model model,HttpServletRequest request) {


        return "tactivity/homecard.ftl";
    }

    /**
     * 青筑活动列表页面
     * @param model
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/tactivity/list", method = RequestMethod.GET)
    public ModelAndView list(
            Model model,HttpServletRequest request, HttpSession httpSession) {

        Map<String, String> model1 = new HashMap<String, String>();
        UserDo user = new UserDo();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        if (accessToken == null || accessToken.equals("")) {
            String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/list";
            return new ModelAndView(new RedirectView(url),model1);
        } else {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                user = userService.queryUserInfo(tLog.getuId());
                if(user != null) {
                    if (user != null && null != user.getMobile() && mapMobile.containsKey(user.getMobile())) {
                        Map<String, Object> recMap = new HashMap<String, Object>();
                        recMap.put("status", 2);
                        model.addAttribute("recMobile", tmpActivityService.queryTActivity(recMap));
                        
                        Map<String, Object> regMap = new HashMap<String, Object>();
                        regMap.put("status", 1);
                        model.addAttribute("regMobile", tmpActivityService.queryTActivity(regMap));
                        
                        Map<String, Object> noRegMap = new HashMap<String, Object>();
                        noRegMap.put("status", "0");
                        model.addAttribute("noRegMobile", tmpActivityService.queryTActivity(noRegMap));
                        return new ModelAndView("tactivity/list.ftl",model1);
                    } else {
                        String url = config.HOST;
                        return new ModelAndView(new RedirectView(url),model1);
                    }
                } else {
                    String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/list";
                    return new ModelAndView(new RedirectView(url),model1);
                }
            } else {
                String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/list";
                return new ModelAndView(new RedirectView(url),model1);
            }
        }
    }
    
    /**
     * 青筑活动送礼页面
     * @param model
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/tactivity/check", method = RequestMethod.GET)
    public ModelAndView check(
            Model model,HttpServletRequest request, HttpSession httpSession) {

        Map<String, String> model1 = new HashMap<String, String>();
        UserDo user = new UserDo();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        if (accessToken == null || accessToken.equals("")) {
            String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/check";
            return new ModelAndView(new RedirectView(url),model1);
        } else {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null) {
                user = userService.queryUserInfo(tLog.getuId());
                if(user != null) {
                    if (user != null && null != user.getMobile() && mapMobile.containsKey(user.getMobile())) {
                        return new ModelAndView("tactivity/check.ftl",model1);
                    } else {
                        String url = config.HOST;
                        return new ModelAndView(new RedirectView(url),model1);
                    }
                } else {
                    String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/check";
                    return new ModelAndView(new RedirectView(url),model1);
                }
            } else {
                String url = config.HOST + "/login?returnUrl=" + config.HOST + "/tactivity/check";
                return new ModelAndView(new RedirectView(url),model1);
            }
        }
    }
    
    /**
     * 点验证按钮进行验证
     */
    @RequestMapping(value = "/tactivity/gocheck", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> gocheck(TTmpActivity activity,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (activity != null && StringUtils.isNotBlank(activity.getActivateCode()) 
                && StringUtils.isNotBlank(activity.getMobile())) {
            UserDo user = new UserDo();
            String sessionId = httpSession.getId();
            String accessToken = httpSession.getAttribute(sessionId).toString();
            if (accessToken == null || accessToken.equals("")) {
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
            } else {
                LogDo tLog = userService.isLoggedIn(accessToken);
                if (tLog != null) {
                    user = userService.queryUserInfo(tLog.getuId());
                    if (user != null && null != user.getMobile() 
                            && mapMobile.containsKey(user.getMobile())) {
                        activity.setStatus(2);
                        activity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        int flag = tmpActivityService.updateTActivityByMobile(activity);
                        if (flag > 0) {
                            LOG.error(user.getMobile() + "验证了业主:" + activity.getMobile());
                            baseResponse.setCode(HttpResponseCode.SUCCESS);
                            baseResponse.setExt(MessageConstant.SUCCESS);
                        } else {
                            baseResponse.setCode(HttpResponseCode.FAILED);
                            baseResponse.setExt(MessageConstant.FAILED);
                        }
                    } else {
                        baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                        baseResponse.setObj(null);
                        HttpMessage message = new HttpMessage();
                        message.setMsg(MessageConstant.USER_NOT_LOGIN);
                        baseResponse.setExt(message);
                    }
                }
            }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    /**
     * 点验证按钮进行验证
     */
    @RequestMapping(value = "/tactivity/ischeck", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> ischeck(TTmpActivity activity,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (activity != null && StringUtils.isNotBlank(activity.getActivateCode()) 
                && StringUtils.isNotBlank(activity.getMobile())) {
            UserDo user = new UserDo();
            String sessionId = httpSession.getId();
            String accessToken = httpSession.getAttribute(sessionId).toString();
            if (accessToken == null || accessToken.equals("")) {
                baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_LOGIN);
                baseResponse.setExt(message);
            } else {
                LogDo tLog = userService.isLoggedIn(accessToken);
                if (tLog != null) {
                    user = userService.queryUserInfo(tLog.getuId());
                    if (user != null && null != user.getMobile() 
                            && mapMobile.containsKey(user.getMobile())) {

                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("status", 1);
                        paramMap.put("mobile", activity.getMobile());
                        paramMap.put("activateCode", activity.getActivateCode());
                        List<TTmpActivity> list = tmpActivityService.queryTActivity(paramMap);
                        if (null !=list && list.size()> 0) {
                            baseResponse.setCode(HttpResponseCode.SUCCESS);
                            baseResponse.setExt(MessageConstant.SUCCESS);
                        } else {
                            baseResponse.setCode(HttpResponseCode.FAILED);
                            baseResponse.setExt(MessageConstant.FAILED);
                        }
                    } else {
                        baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                        baseResponse.setObj(null);
                        HttpMessage message = new HttpMessage();
                        message.setMsg(MessageConstant.USER_NOT_LOGIN);
                        baseResponse.setExt(message);
                    }
                }
            }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            Model model,HttpServletRequest request) {

        if(StringUtils.isNotBlank(request.getParameter("returnUrl"))){
            model.addAttribute("returnUrl", request.getParameter("returnUrl"));
        }
        return "passport/login.ftl";
    }
    
    /**
     * 点登陆按钮进行登录
     */
    @RequestMapping(value = "/gologin", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> gologin(LoginRequestVo loginRequest,HttpSession httpSession) {
		if (loginRequest != null) {
			LOG.info("MapiUserController gologin loginRequest:{}", JsonUtils.obj2json(loginRequest));
		}

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (loginRequest == null || StringUtil.isNullOrEmpty(loginRequest.getMobile())
                || StringUtil.isNullOrEmpty(loginRequest.getPassword())) {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.USER_PASS_EMPTY);
            baseResponse.setExt(message);
        } else {
            LogDo tLog = userService.auth(loginRequest.getMobile(), loginRequest.getPassword());
            if (tLog == null) {
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_NOT_EXISTS);
                baseResponse.setExt(message);
            } else {
            	UserDo user = userService.queryUserInfo(tLog.getuId());
                if (user != null) {
                    baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(null);
                    baseResponse.setExt(MessageConstant.SUCCESS);
                    httpSession.setAttribute(httpSession.getId(), tLog.getAccessToken());
                    user.setPassword(null);
                    httpSession.setAttribute("user", user);
                    
                    //记录访问日志
                    userService.addAccessLog(tLog.getuId(), 3);
                } else {
                    baseResponse.setCode(HttpResponseCode.FAILED);
                    baseResponse.setObj(null);
                    baseResponse.setExt(MessageConstant.USER_NOT_EXISTS);
                }
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------
     * 注册时-输入手机号和密码页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(
            Model model,HttpServletRequest request) {

        return "passport/reg.ftl";
    }
    
    /**
     * register step one, user input mobile and password
     *
     * @param registerRequest
     * @return
     */
    @RequestMapping(value = "/goregister", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> goregister(RegisterRequestVo registerRequest,HttpServletResponse response) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();

        if (registerRequest == null || StringUtil.isNullOrEmpty(registerRequest.getPassword())
                || StringUtil.isNullOrEmpty(registerRequest.getMobile())) {

            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.USER_PASS_EMPTY);
            baseResponse.setExt(message);

        } else {
            // handle data to service
            try {
            	RegisterRequestVo registerParams = new RegisterRequestVo();
            	registerParams.setMobile(registerRequest.getMobile());
            	registerParams.setPassword(registerRequest.getPassword());
            	RegisterResponseVo registerResponse = userService.registerByMobileAndPassword(registerParams);
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(null);
                baseResponse.setExt(null);
                
                Cookie cookie = new Cookie("registerKey",registerResponse.getRegisterKey());
                cookie.setPath("/");
                cookie.setMaxAge(1200000);
                response.addCookie(cookie);
                
                Cookie mobileCookie = new Cookie("mobile",registerRequest.getMobile());
                mobileCookie.setPath("/");
                mobileCookie.setMaxAge(120000);
                response.addCookie(mobileCookie);
            } catch (BusinessException e) {
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.USER_REGISTERED);
                baseResponse.setExt(message);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    /**
     * 注册时-输入验证码页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/register/sendsms", method = RequestMethod.GET)
    public String sendsms(
            Model model,HttpServletRequest request) {

        return "passport/sendsms.ftl";
    }
    
    /**
     * 注册重新发送短信
     *
     * @param sendSmsRequest
     * @return
     */
    @RequestMapping(value = "/register/resendsms", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> resendsms(HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        String registerKey = "";
        String mobile = "";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("registerKey".equals(cookie.getName())){
                registerKey = cookie.getValue();
            }
            if ("mobile".equals(cookie.getName())){
                mobile = cookie.getValue();
            }
        }
        if (StringUtils.isNotBlank(mobile)
                && StringUtils.isNotBlank(registerKey)) {
            boolean success = userService.resendSms(mobile, registerKey);
            if (success) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    /**
     * register, validate sms, and make user in login state
     *
     * @param registerValidateRequest
     * @return
     */
    @RequestMapping(value = "/register/validate", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> registerValidate(RegisterValidateRequestVo registerValidateRequest,HttpServletRequest request,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);

        String registerKey = "";
        String mobile = "";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("registerKey".equals(cookie.getName())){
                registerKey = cookie.getValue();
            }
            if ("mobile".equals(cookie.getName())){
                mobile = cookie.getValue();
            }
        }
        
        // validate and log in
        LogDo log = userService.validateSms(mobile,
                registerKey, registerValidateRequest.getActivateCode(),registerValidateRequest);
        if (log != null) {
        	UserDo user = userService.queryUserInfo(log.getuId());
            if (user != null) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(null);
                baseResponse.setExt(MessageConstant.SUCCESS);
                httpSession.setAttribute(httpSession.getId(), log.getAccessToken());
                user.setPassword(null);
                httpSession.setAttribute("user", user);
                
                //记录访问日志
                userService.addAccessLog(log.getuId(), 3);
            } else {
                baseResponse.setCode(HttpResponseCode.FAILED);
                baseResponse.setObj(null);
                baseResponse.setExt(MessageConstant.USER_NOT_EXISTS);
            }
            
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("mobile", mobile);
            List<TTmpActivity> list = tmpActivityService.queryTActivity(paramMap);
            if(null != list && list.size() > 0){
                String activateCode = SecurityUtil.generateActivateCode();
                smsService.sendSmsKey(activateCode, mobile, "qingzhuCode");
                TTmpActivity tt = list.get(0);
                
                TTmpActivity activity = new TTmpActivity();
                activity.setActivateCode(activateCode);
                activity.setStatus(1);
                activity.setActivityId(tt.getActivityId());
                activity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                tmpActivityService.updateTActivityById(activity);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    
    /**
     * ----------------------------------------------------------------------------------------------------
     * 重置密码-输入手机号
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/goretrievepass", method = RequestMethod.GET)
    public String retrieve(
            Model model,HttpServletRequest request) {

        return "passport/recovery.ftl";
    }

    /**
     * 输入手机号，点下一步
     * @param retrievePassRequest
     * @return
     */
    @RequestMapping(value = "/retrievepass", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> retrievePassword(RetrievePassRequestVo retrievePassRequest,HttpServletResponse response) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        if (retrievePassRequest != null && !StringUtil.isNullOrEmpty(retrievePassRequest.getMobile())) {
            try {
                String retrieveKey = userService.retrievePassword(retrievePassRequest.getMobile());
                
                Cookie cookie = new Cookie("retrieveKey",retrieveKey);
                cookie.setPath("/");
                cookie.setMaxAge(1200000);
                response.addCookie(cookie);
                
                Cookie mobileCookie = new Cookie("retMobile",retrievePassRequest.getMobile());
                mobileCookie.setPath("/");
                mobileCookie.setMaxAge(1200000);
                response.addCookie(mobileCookie);
                
                baseResponse.setObj(null);
                baseResponse.setCode(HttpResponseCode.SUCCESS);
            } catch (UserNotExistException e) {
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    /**
     * 重置密码-输入验证码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/retrievepass/sendsms", method = RequestMethod.GET)
    public String retSendsms(
            Model model,HttpServletRequest request) {

        return "passport/verifyMobile.ftl";
    }
    
    /**
     * 重置密码重新发送短信
     *
     * @param sendSmsRequest
     * @return
     */
    @RequestMapping(value = "/retrievepass/resendsms", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> retResendsms(HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        String retrieveKey = "";
        String retMobile = "";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("retrieveKey".equals(cookie.getName())){
                retrieveKey = cookie.getValue();
            }
            if ("retMobile".equals(cookie.getName())){
                retMobile = cookie.getValue();
            }
        }
        if (StringUtils.isNotBlank(retMobile)
                && StringUtils.isNotBlank(retrieveKey)) {
            boolean success = userService.resendSms(retMobile, retrieveKey);
            if (success) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    /**
     * 输入验证码，点下一步
     * @param retrieveValidateRequest
     * @return
     */
    @RequestMapping(value = "/retrievepass/validatesms", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> retriveValidate(RetrieveValidateRequestVo retrieveValidateRequest,HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);

        String retrieveKey = "";
        String retMobile = "";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("retrieveKey".equals(cookie.getName())){
                retrieveKey = cookie.getValue();
            }
            if ("retMobile".equals(cookie.getName())){
                retMobile = cookie.getValue();
            }
        }
        
        if (retrieveValidateRequest != null && StringUtils.isNotBlank(retrieveValidateRequest.getActivateCode())
                && StringUtils.isNotBlank(retrieveKey) && StringUtils.isNotBlank(retMobile)) {
            boolean success = userService.retrieveValidate(retMobile, retrieveKey, 
                    retrieveValidateRequest.getActivateCode());
            if (success) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    /**
     * 重置密码-输入密码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/retrievepass/gopass", method = RequestMethod.GET)
    public String retGoPass(
            Model model,HttpServletRequest request) {

        return "passport/resetPassword.ftl";
    }
    
    /**
     * 输入密码，点击下一步
     * @param resetPassRequest
     * @return
     */
    @RequestMapping(value = "/retrievepass/resetpass", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> resetPass(ResetPassRequestVo resetPassRequest, HttpServletRequest request,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);

        String retrieveKey = "";
        String retMobile = "";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("retrieveKey".equals(cookie.getName())){
                retrieveKey = cookie.getValue();
            }
            if ("retMobile".equals(cookie.getName())){
                retMobile = cookie.getValue();
            }
        }
        if (resetPassRequest != null && StringUtils.isNotBlank(resetPassRequest.getPassword())
                && StringUtils.isNotBlank(retrieveKey)
                && StringUtils.isNotBlank(retMobile)) {
            try {
                LogDo log = userService.resetPassword(retMobile, retrieveKey,resetPassRequest.getPassword());
                if(null != log && StringUtils.isNotBlank(log.getAccessToken())){
                	UserDo user = userService.queryUserInfo(log.getuId());
                    if (user != null) {
                        baseResponse.setCode(HttpResponseCode.SUCCESS);
                        baseResponse.setObj(null);
                        baseResponse.setExt(MessageConstant.SUCCESS);
                        httpSession.setAttribute(httpSession.getId(), log.getAccessToken());
                        user.setPassword(null);
                        httpSession.setAttribute("user", user);
                        
                        //记录访问日志
                        userService.addAccessLog(log.getuId(), 3);
                    } else {
                        baseResponse.setCode(HttpResponseCode.FAILED);
                        baseResponse.setObj(null);
                        baseResponse.setExt(MessageConstant.USER_NOT_EXISTS);
                    }
                }
            } catch (UserNotExistException e) {
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        String sessionId = httpSession.getId();
        String accessToken = String.valueOf(httpSession.getAttribute(sessionId));
        if (accessToken != null && !StringUtil.isNullOrEmpty(accessToken)) {
        	boolean success = userService.logoutByAccessToken(accessToken);
            if (success) {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
            }
        }
        httpSession.setAttribute(httpSession.getId(), null);
        httpSession.setAttribute("user", null);
        return "passport/login.ftl";
    }

//    /**
//     * 关于我们
//     */
//    @RequestMapping(value = "/app/about", method = RequestMethod.GET)
//    public ModelAndView appAbout(Model model) {
//        LOG.info("appAbout interface start");
//        model.addAttribute("downloadurl", Constants.DOWNLOAD_URL);
//        
//        
//        return new ModelAndView(new RedirectView("http://h5.eqxiu.com/s/u8FrdDfb"));
//        
//        
//      //  return "about.ftl";
////        return "product/about.ftl";
//    }
    
    /**
     * 关于我们
     */
    @RequestMapping(value = "/app/about", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> appAbout() {
    	HttpBaseResponse baseResponse = new HttpBaseResponse();
    	
    	baseResponse.setCode(HttpResponseCode.SUCCESS);
    	AppAboutResponseVo appAboutResponse = new AppAboutResponseVo();
    	appAboutResponse.setDownloadUrl(Constants.DOWNLOAD_URL);
    	appAboutResponse.setRedictUrl(Constants.REDIRECT_URL);
    	
    	baseResponse.setExt(null);
    	baseResponse.setObj(appAboutResponse);
		MultiValueMap<String, String> headers = new HttpHeaders();
	    headers.set("Access-Control-Allow-Origin", "*");
	    headers.set("Access-Control-Request-Method", "post");
	    return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    
    
//    /**
//     * 帮我侃价
//     */
//    @RequestMapping(value = "/topic/bargain", method = RequestMethod.GET)
//    public String topicBargain(Model model, 
//                               @RequestParam(value = "accessToken", required = false) String accessToken,
//                               HttpSession httpSession) {
//
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        baseResponse.setCode(HttpResponseCode.SUCCESS);
//        baseResponse.setExt(null);
//        baseResponse.setObj(null);
//        LOG.info("topicBargain interface start");
//
//        String sessionId = httpSession.getId();
//
//        if (!StringUtil.isNullOrEmpty(accessToken)) {
//            /**
//             * save session id to access token map;
//             */
//            httpSession.setAttribute(sessionId, accessToken);
//        }
//
//        accessToken = (String) httpSession.getAttribute(sessionId);
//        HttpUserWishListResponse wishListResponse = new HttpUserWishListResponse();
//        TLog tLog = userService.isLoggedIn(accessToken);
//        if (tLog != null) {
//            wishListResponse.setWishList(myService.queryAllWishList(tLog.getuId()));
//        }
//        baseResponse.setObj(wishListResponse);
//        model.addAttribute("baseResponse", baseResponse);
//        return "product/bargain.ftl";
//    }
    
    /**
     * 帮我侃价
     */
    @RequestMapping(value = "/topic/bargain", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> topicBargain(@RequestParam(value = "accessToken", required = false) String accessToken,
                               HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setExt(null);
        baseResponse.setObj(null);
        LOG.info("topicBargain interface start");

        String sessionId = httpSession.getId();

        if (!StringUtil.isNullOrEmpty(accessToken)) {
            /**
             * save session id to access token map;
             */
            httpSession.setAttribute(sessionId, accessToken);
        }

        accessToken = (String) httpSession.getAttribute(sessionId);
        UserWishListResponseVo wishListResponse = new UserWishListResponseVo();
      //  UserDto userDto = userFacade.getUserByToken(accessToken);
        UserDto userDto = userProxy.getUserByToken(accessToken);
        if (userDto != null) {
            wishListResponse.setWishList(myService.queryAllWishList(userDto.getId().longValue()));
        }
        baseResponse.setObj(wishListResponse);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    

    /**
     * 帮我侃价
     */
    @RequestMapping(value = "/topic/bargain/add", method = RequestMethod.GET)
    public String addBargain(Model model,
                             HttpSession httpSession) {

        LOG.info("addBargain interface start");
        String sessionId = httpSession.getId();
        String accessToken = (String) httpSession.getAttribute(sessionId);
        System.out.println("sessionId:" + sessionId);
        System.out.println("accesstoken is:" + (accessToken == null ? "null" : accessToken));
        return "product/addBargain.ftl";
    }
    
    /**
     * 全民营销
     * 1.受邀用户
     */
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public String addNationalMarketing(Model model,
    		 @RequestParam(value = "mobile", required = false) String mobile,
    		 @RequestParam(value = "recMobile", required = false) String recMobile,
                             HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setExt(null);
        baseResponse.setObj(null);

            if (recMobile != null) {
            	UserDo user=userService.queryUserInfoByMobile(recMobile);
            	UserDo userTemp=new UserDo();
            	if(user!=null){
            	user.setPassword("");
            	user.setuId(null);
            	userTemp.setRecMobile(user.getMobile());
            	userTemp.setMobile(mobile);
            	}
            	InvitedUserResponseVo invitedUser=new InvitedUserResponseVo(userTemp);
            		  baseResponse.setObj(invitedUser);
            
            }
            model.addAttribute("baseResponse", baseResponse);
        if(mobile==null||mobile.equals("")){
            return "marketing/addInviteUser.ftl";
        }else{
        	return "marketing/addInviteUserByMobile.ftl";
        } 
    }
    /**
     * 全民营销
     * 2.下载
     */
    @RequestMapping(value = "/invite/downLoadAJApp", method = RequestMethod.GET)
    public ModelAndView downLoadAJApp(HttpServletRequest request, HttpServletResponse response) {


        LOG.info("addNationalMarketing interface start");
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        LOG.info("user agent:" + ua);
         String pvalue="";
        /**
         * 微信浏览器
         */
        if (ua.indexOf(AGENT_WEIXIN) > 0) {
            if (ua.indexOf(ANDROID) > 0) {
                pvalue = "200";
                /**
                 * tip for user open this download
                 */
                return new ModelAndView("download/tip_weixin.ftl");
            } else if (ua.indexOf(IPHONE) > 0) {
                pvalue = "100";
                return new ModelAndView("download/tip_weixin.ftl");
            }
        } else  {
            /**
             * if null, use default, else use
             */
            if (ua.indexOf(ANDROID) > 0) {
                    pvalue = "200";
            } else if (ua.indexOf(IPHONE) > 0) {
                pvalue = "100";
            }
        }
        //get app version information from database
        LOG.info("pvalue:" + pvalue);
        AppVersionDto appVersion = null;
         appVersion = bundleService.getLatestApp(pvalue, null);
        //error view
        if (appVersion == null) {

            Map<String, String> model = new HashMap<String, String>();
            AppVersionDto ios = bundleService.getLatestApp("100", null);
            model.put("iosurl", ios.getDownload());
            AppVersionDto android = bundleService.getLatestApp("200", null);
            model.put("androidurl",android.getDownload());

            return new ModelAndView("download/error.ftl",model);
        } else {
            LOG.info("downloadurl:" + appVersion.getDownload());
            //redirect to download url
            return new ModelAndView(new RedirectView(appVersion.getDownload()));
        }
    }
    
    /**
     * 前后交互
     * @param userRelation
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/invite/downLoadAJAppJSON", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> downLoadAJApp(UserRelationDo userRelation,HttpSession httpSession) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("downLoad", url);
        Long isBingding = null;
        if(userRelation!=null)
        	isBingding=userService.bindingUser(userRelation);
        if(isBingding==HttpResponseCode.INVITED_IS_AIJIA_USER){
        	jsonObj.put("msg", MessageConstant.INVITED_IS_AIJIA_USER);
        }else if(isBingding==HttpResponseCode.INVITE_NOT_AIJIA_USER){
        	jsonObj.put("msg", MessageConstant.INVITE_NOT_AIJIA_USER);
        }else if(isBingding==HttpResponseCode.BINGDING_SUCCESS){
        	jsonObj.put("msg", MessageConstant.SUCCESS);
        }else if(isBingding==HttpResponseCode.REPEATER_BINGDING){
        	jsonObj.put("msg", MessageConstant.REPEATER_BINGDING);
        }else{
        	jsonObj.put("msg", MessageConstant.FAILED);
        }
        baseResponse.setExt(MessageConstant.SUCCESS);
        baseResponse.setObj(jsonObj);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers,
                HttpStatus.OK);
    }
    
//    /**
//     * 全民营销
//     * 3.我的钱袋子
//     */
//    @RequestMapping(value = "/invite/myWallet", method = RequestMethod.GET)
//    public String myWallet(Model model,PageModel pageModel, 
//    		@RequestParam(value = "accessToken", required = false) String accessToken,
//                             HttpSession httpSession) {
//        LOG.info("myWallet interface start");
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        Long count=null;
//        if(accessToken==null||accessToken.equals("")){
//        	baseResponse.setCode(HttpResponseCode.MOBILE_NOT_EXISTS);
//            baseResponse.setObj(null);
//            HttpMessage message = new HttpMessage();
//            message.setMsg(MessageConstant.MOBILE_NOT_EXISTS);
//            baseResponse.setExt(message);
//        } 
//        else {
//        // 1 Check whether the user is legitimate
//        	 if (!StringUtil.isNullOrEmpty(accessToken)) {
//
//                 String sessionId = httpSession.getId();
//
//                     /**
//                      * save session id to access token map;
//                      */
//                     httpSession.setAttribute(sessionId, accessToken);
//
//                 accessToken = (String) httpSession.getAttribute(sessionId);
//                 TLog tLog = userService.isLoggedIn(accessToken);
//                 if(tLog!=null){
//                	 TUser user=userService.queryUserInfo(tLog.getuId());
//                	 Map<String, Object> map = new HashMap<String, Object>();
//            		 map.put("mobile", user.getMobile());
//            		 if (pageModel == null || pageModel.getPageNo() == 0
//                             || pageModel.getPageSize() == 0) {
//            			 map.put("from", Constants.FROM);
//                		 map.put("pageSize", Constants.PAGE_SIZE);
//                     }else{
//                    	 map.put("from",  (pageModel.getPageNo()-1)*pageModel.getPageSize());
//                    	 map.put("pageSize", pageModel.getPageSize());
//                     }
//            		
//                	 List<TWallet> myWallets=userService.queryMyWallet(map);
//                	 Double myWalletTotal=userService.queryMyWalletSum(map);
//                	 map.put("status", "0");
//                	 Double myFrozenSum=userService.queryMyWalletSum(map);
//                	  count=userService.querMyWalletCount(user.getMobile());
//                	 Double myWalletSum=myWalletTotal-myFrozenSum;
//                	 TWalletResponse walletResponse = new TWalletResponse();
//                	 walletResponse.setMyWallets(myWallets);;
//                	 walletResponse.setCount(count);
//                	 walletResponse.setMyWalletSum(myWalletSum);
//                	 walletResponse.setMyFrozenSum(myFrozenSum);
//                	 baseResponse.setCode(HttpResponseCode.SUCCESS);
//                     baseResponse.setObj(walletResponse);
//                     HttpMessage message = new HttpMessage();
//                     message.setMsg(MessageConstant.QUERY_SUCCESS);
//                     baseResponse.setExt(message);
//                 }else{
//                	 baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
//                     baseResponse.setObj(null);
//                     HttpMessage message = new HttpMessage();
//                     message.setMsg(MessageConstant.USER_NOT_LOGIN);
//                     baseResponse.setExt(message); 
//                     return "login.ftl";
//                 }
//        } else {
//            baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
//            baseResponse.setObj(null);
//            HttpMessage message = new HttpMessage();
//            message.setMsg(MessageConstant.USER_NOT_LOGIN);
//            baseResponse.setExt(message);
//        }
//        }
//        model.addAttribute("baseResponse", baseResponse);
//        return "marketing/myWallet.ftl";
//    }
    
    /**
     * 全民营销
     * 3.我的钱袋子
     * @param model
     * @param pageModel
     * @param accessToken
     * @param httpSession
     * @return
     */
  @Deprecated
  @RequestMapping(value = "/invite/myWallet", method = RequestMethod.POST)
  public ResponseEntity<HttpBaseResponse> myWallet(PageModel pageModel, 
  		@RequestParam(value = "accessToken", required = false) String accessToken,
                           HttpSession httpSession) {

      HttpBaseResponse baseResponse = new HttpBaseResponse();
      Long count=null;
      if(accessToken==null||accessToken.equals("")){
          baseResponse.setObj(null);
          HttpMessage message = new HttpMessage();
          message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
          baseResponse.setExt(message);
      } 
      else {
      // 1 Check whether the user is legitimate
      	 if (!StringUtil.isNullOrEmpty(accessToken)) {

               String sessionId = httpSession.getId();

                   /**
                    * save session id to access token map;
                    */
                   httpSession.setAttribute(sessionId, accessToken);

               accessToken = (String) httpSession.getAttribute(sessionId);
//               TLog tLog = userService.isLoggedIn(accessToken);
              // UserDto userDto = userFacade.getUserByToken(accessToken);
               UserDto userDto = userProxy.getUserByToken(accessToken);
               if(userDto!=null){
//              	 TUser user=userService.queryUserInfo(tLog.getuId());
              	 Map<String, Object> map = new HashMap<String, Object>();
          		 map.put("mobile", userDto.getMobile());
          		 if (pageModel == null || pageModel.getPageNo() == 0
                           || pageModel.getPageSize() == 0) {
          			 map.put("from", Constants.FROM);
              		 map.put("pageSize", Constants.PAGE_SIZE);
                   }else{
                  	 map.put("from",  (pageModel.getPageNo()-1)*pageModel.getPageSize());
                  	 map.put("pageSize", pageModel.getPageSize());
                   }
          		
              	 List<WalletDo> myWallets=userService.queryMyWallet(map);
              	 Double myWalletTotal=userService.queryMyWalletSum(map);
              	 map.put("status", "0");
              	 Double myFrozenSum=userService.queryMyWalletSum(map);
              	  count=userService.querMyWalletCount(userDto.getMobile());
              	 Double myWalletSum=myWalletTotal-myFrozenSum;
              	 TWalletResponse walletResponse = new TWalletResponse();
              	 walletResponse.setMyWallets(myWallets);
              	 walletResponse.setCount(count);
              	 walletResponse.setMyWalletSum(myWalletSum);
              	 walletResponse.setMyFrozenSum(myFrozenSum);
              	 baseResponse.setCode(HttpResponseCode.SUCCESS);
                   baseResponse.setObj(walletResponse);
                   HttpMessage message = new HttpMessage();
                   message.setMsg(MessageConstant.QUERY_SUCCESS);
                   baseResponse.setExt(message);
               }else{
              	 baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                   baseResponse.setObj(null);
                   HttpMessage message = new HttpMessage();
                   message.setMsg(MessageConstant.USER_NOT_LOGIN);
                   baseResponse.setExt(message); 
               }
      } else {
          baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
          baseResponse.setObj(null);
          HttpMessage message = new HttpMessage();
          message.setMsg(MessageConstant.USER_NOT_LOGIN);
          baseResponse.setExt(message);
      }
      }
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.set("Access-Control-Allow-Origin", "*");
      headers.set("Access-Control-Request-Method", "post");
      return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
  }
    
//    /**
//     * 全民营销
//     * 4.我的邀请
//     */
//    @RequestMapping(value = "/invite/myInvitedUsers", method = RequestMethod.GET)
//    public String myInvitedUsers(Model model,TUser user,PageModel pageModel,
//    		@RequestParam(value = "accessToken", required = false) String accessToken,
//                             HttpSession httpSession) {
//        LOG.info("myInvitedUsers interface start");
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        	 if (!StringUtil.isNullOrEmpty(accessToken)) {
//
//                 String sessionId = httpSession.getId();
//
//                     /**
//                      * save session id to access token map;
//                      */
//                     httpSession.setAttribute(sessionId, accessToken);
//
//                 accessToken = (String) httpSession.getAttribute(sessionId);
//                 TLog tLog = userService.isLoggedIn(accessToken);
//                 if(tLog!=null){
//                	 user=userService.queryUserInfo(tLog.getuId());
//                	 Map<String, Object> map = new HashMap<String, Object>();
//            		 map.put("mobile", user.getMobile());
//            		 if (pageModel == null || pageModel.getPageNo() == 0
//                             || pageModel.getPageSize() == 0) {
//            			 map.put("from", Constants.FROM);
//                		 map.put("pageSize", Constants.PAGE_SIZE);
//                     }else{
//                    	 map.put("from",  (pageModel.getPageNo()-1)*pageModel.getPageSize());
//                    	 map.put("pageSize", pageModel.getPageSize());
//                     }
//            		
//            		 TUserRelationResponse invitedUser = new TUserRelationResponse();
//            		 int invitedUsers=userService.queryMyInvitedUsersCount(map);
//            		 List<TUserRelationResponse> myInvitedUsers=userService.queryMyInvitedUsers(map);
//            		 map.put("status", 2);
//            		 int successInvitedUsers=userService.queryMyInvitedUsersCount(map);
//            		 map.remove("status");
//                	 invitedUser.setInvitedUsers(myInvitedUsers);
//                	 invitedUser.setMyInvitedUsers(invitedUsers);
//                	 invitedUser.setSuccessInvitedUsers(successInvitedUsers);
//                	 baseResponse.setCode(HttpResponseCode.SUCCESS);
//                     baseResponse.setObj(invitedUser);
//                     HttpMessage message = new HttpMessage();
//                     message.setMsg(MessageConstant.QUERY_SUCCESS);
//                     baseResponse.setExt(message);
//                 }else{
//                	 baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
//                     baseResponse.setObj(null);
//                     HttpMessage message = new HttpMessage();
//                     message.setMsg(MessageConstant.USER_NOT_LOGIN);
//                     baseResponse.setExt(message); 
//                     return "login.ftl";
//                 }
//        } else {
//        	baseResponse.setCode(HttpResponseCode.MOBILE_NOT_EXISTS);
//          baseResponse.setObj(null);
//          HttpMessage message = new HttpMessage();
//          message.setMsg(MessageConstant.MOBILE_NOT_EXISTS);
//          baseResponse.setExt(message);
//        }
//        model.addAttribute("baseResponse", baseResponse);
//        return "marketing/myInvitedUsers.ftl";
//    }
    
    @RequestMapping(value = "/invite/myInvitedUsers",method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> myInvitedUsers(UserDo user,PageModel pageModel,
    		@RequestParam(value = "accessToken", required = false) String accessToken,
          HttpSession httpSession) {

      HttpBaseResponse baseResponse = new HttpBaseResponse();
      	 if (!StringUtil.isNullOrEmpty(accessToken)) {

               String sessionId = httpSession.getId();

                   /**
                    * save session id to access token map;
                    */
                   httpSession.setAttribute(sessionId, accessToken);

               accessToken = (String) httpSession.getAttribute(sessionId);
              // UserDto userDto = userFacade.getUserByToken(accessToken);
               UserDto userDto = userProxy.getUserByToken(accessToken);
               if(userDto!=null){
              	 Map<String, Object> map = new HashMap<String, Object>();
          		 map.put("mobile", userDto.getMobile());
          		 if (pageModel == null || pageModel.getPageNo() == 0
                           || pageModel.getPageSize() == 0) {
          			 map.put("from", Constants.FROM);
              		 map.put("pageSize", Constants.PAGE_SIZE);
                   }else{
                  	 map.put("from",  (pageModel.getPageNo()-1)*pageModel.getPageSize());
                  	 map.put("pageSize", pageModel.getPageSize());
                   }
          		
          		 TUserRelationResponse invitedUser = new TUserRelationResponse();
          		 int invitedUsers=userService.queryMyInvitedUsersCount(map);
          		 List<TUserRelationResponse> myInvitedUsers=userService.queryMyInvitedUsers(map);
          		 map.put("status", 2);
          		 int successInvitedUsers=userService.queryMyInvitedUsersCount(map);
          		 map.remove("status");
              	 invitedUser.setInvitedUsers(myInvitedUsers);
              	 invitedUser.setMyInvitedUsers(invitedUsers);
              	 invitedUser.setSuccessInvitedUsers(successInvitedUsers);
              	 baseResponse.setCode(HttpResponseCode.SUCCESS);
                   baseResponse.setObj(invitedUser);
                   HttpMessage message = new HttpMessage();
                   message.setMsg(MessageConstant.QUERY_SUCCESS);
                   baseResponse.setExt(message);
               }else{
              	 baseResponse.setCode(HttpResponseCode.USER_NOT_LOGIN);
                   baseResponse.setObj(null);
                   HttpMessage message = new HttpMessage();
                   message.setMsg(MessageConstant.USER_NOT_LOGIN);
                   baseResponse.setExt(message); 
               }
      } else {
        baseResponse.setObj(null);
        HttpMessage message = new HttpMessage();
        message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
        baseResponse.setExt(message);
      }
      	
  	 MultiValueMap<String, String> headers = new HttpHeaders();
     headers.set("Access-Control-Allow-Origin", "*");
     headers.set("Access-Control-Request-Method", "post");
     return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/goFeedback", method = RequestMethod.GET)
    public String goFeedback(
            Model model,HttpServletRequest request) {

        model.addAttribute("type", request.getParameter("type"));
        return "feedback/feedback.ftl";
    }
    
    @RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> add(@Json FeedbackRequestVo feedbackRequest) {
		if (feedbackRequest != null) {
			LOG.info("MapiUserController add feedbackRequest:{}", JsonUtils.obj2json(feedbackRequest));
		}
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (feedbackRequest != null) {
            String content = feedbackRequest.getContent();
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");  
            Matcher m = p.matcher(feedbackRequest.getPhoneNumber());
            if (content != null && content.length() < 500 && m.matches()) {
                  //1.查询用户是否登录 登录获取uid
                    LogDo tLog = userService.isLoggedIn(feedbackRequest.getAccessToken());
                    FeedbackResponseVo feedbackResponse = new FeedbackResponseVo();
                    //2.组装意见反馈或咨询问题信息并入库
                    TFeedbackDto feedback = new TFeedbackDto();
                    feedback.setContent(feedbackRequest.getContent());
                    feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
                    feedback.setType(feedbackRequest.getType());
                    feedback.setUserId(tLog != null ? tLog.getuId() : null);
                    feedbackResponse.setFeedbackId(feedbackService.addFeedback(feedback));
                    baseResponse.setCode(HttpResponseCode.SUCCESS);
                    baseResponse.setObj(feedbackResponse);
                    baseResponse.setExt(null);
                } else {
                    baseResponse.setCode(HttpResponseCode.FAILED);
                    baseResponse.setObj(null);
                    baseResponse.setExt(MessageConstant.CONTENT_OVER_FLOW);
                }
        } else {
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/feedbackSuccess", method = RequestMethod.GET)
    public String feedbackSuccess(
            Model model,HttpServletRequest request) {

        return "feedback/feedbackSuccess.ftl";
    }
}
