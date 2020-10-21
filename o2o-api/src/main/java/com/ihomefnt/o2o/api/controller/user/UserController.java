package com.ihomefnt.o2o.api.controller.user;

import com.ihomefnt.o2o.common.config.ApiConfig;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.user.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.*;
import com.ihomefnt.o2o.intf.manager.util.common.SmsUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.service.service.user.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huayunlei
 * @ClassName: UserController
 * @Description: 用户信息模块controller层
 * @date Feb 18, 2019 9:37:22 AM
 */
@RestController
@RequestMapping("/account")
@Api(tags = "【用户信息API】")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApiConfig apiConfig;

    /**
     * 功能描述：用户登录
     */
    @ApiOperation(value = "login", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> login(HttpServletRequest req, @Json LoginRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        LoginResponseVo obj = userService.loginByPassword(req, request);
        return HttpBaseResponse.success(obj);
    }


    /**
     * 功能描述：修改昵称
     */
    @ApiOperation(value = "changeNick", notes = "修改昵称")
    @RequestMapping(value = "/changeNick", method = RequestMethod.POST)
    public HttpBaseResponse<Void> changeNick(@Json ChangeNickRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        userService.changeNick(request);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：用户注册（手机号、密码）
     */
    @ApiOperation(value = "register", notes = "用户注册（手机号、密码）")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpBaseResponse<RegisterResponseVo> register(@Json RegisterRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        RegisterResponseVo obj = userService.registerByMobileAndPassword(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：注册，重新发送短信
     */
    @ApiOperation(value = "register/resendsms", notes = "注册，重新发送短信")
    @RequestMapping(value = "/register/resendsms", method = RequestMethod.POST)
    public HttpBaseResponse<Void> resendsms(@Json ReSendSmsRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        userService.resendsms(request);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：注册验证短信
     */
    @ApiOperation(value = "register/validate", notes = "注册验证短信")
    @RequestMapping(value = "/register/validate", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> registerValidateSms(@Json RegisterValidateRequestVo registerValidateRequest) {
        if (registerValidateRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        LoginResponseVo obj = userService.registerValidateSms(registerValidateRequest);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：初始化密码
     */
    @ApiOperation(value = "retrievepass", notes = "初始化密码")
    @RequestMapping(value = "/retrievepass", method = RequestMethod.POST)
    public HttpBaseResponse<RetrievePassResponseVo> retrievepass(@Json RetrievePassRequestVo retrievePassRequest) {
        if (retrievePassRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        RetrievePassResponseVo obj = userService.retrievepass(retrievePassRequest);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：重置密码验证短信
     */
    @ApiOperation(value = "retrievepass/validatesms", notes = "重置密码验证短信")
    @RequestMapping(value = "/retrievepass/validatesms", method = RequestMethod.POST)
    public HttpBaseResponse<Void> retriveValidate(@Json RetrieveValidateRequestVo retrieveValidateRequest) {
        if (retrieveValidateRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        userService.retriveValidate(retrieveValidateRequest);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：重置修改密码
     */
    @ApiOperation(value = "retrievepass/resetpass", notes = "重置修改密码")
    @RequestMapping(value = "/retrievepass/resetpass", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> resetPass(@Json ResetPassRequestVo resetPassRequest) {
        if (resetPassRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        LoginResponseVo obj = userService.resetPass(resetPassRequest);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：退出登录
     */
    @ApiOperation(value = "logout", notes = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public HttpBaseResponse<Void> logout(@Json LogoutRequestVo logoutRequest) {
        if (logoutRequest == null || StringUtil.isNullOrEmpty(logoutRequest.getRefreshToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        userService.logout(logoutRequest);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：添加用户反馈
     */
    @ApiOperation(value = "feedback/add", notes = "添加用户反馈")
    @RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
    public HttpBaseResponse<FeedbackResponseVo> addFeedback(@Json FeedbackRequestVo feedbackRequest) {
        if (feedbackRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        FeedbackResponseVo obj = userService.addFeedback(feedbackRequest);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：检查用户合法性
     */
    @ApiOperation(value = "checkAccessToken", notes = "检查用户合法性")
    @RequestMapping(value = "/checkAccessToken", method = RequestMethod.POST)
    public HttpBaseResponse<Void> checkAccessToken(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        userService.checkAccessToken(request);
        return HttpBaseResponse.success();
    }

    /**
     * 功能描述：登录发送短信
     */
    @ApiOperation(value = "sendlogsmsV2", notes = "登录发送短信")
    @RequestMapping(value = "/sendlogsmsV2", method = RequestMethod.POST)
    public HttpBaseResponse<Void> sendlogsmsV2(HttpServletRequest req, @Json SendLoginSmsRequestVo request) {
        if (request == null || StringUtil.isNullOrEmpty(request.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS,MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (!UserServiceImpl.checkSign(req, request.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS,MessageConstant.SIGN_VALIDATE_FAIL);
        }
        if (UserServiceImpl.checkMobile(request.getMobile())) {
            HttpBaseResponse.fail(HttpReturnCode.MOBILE_NOT_CORRECT, MessageConstant.MOBILE_NOT_EXISTS);
        }
        return SmsUtils.handlerSmsSendResult(userService.sendlogsmsV2(req, request));
    }

    /**
     * 功能描述：登录发送短信
     */
    @ApiOperation(value = "sendlogsms", notes = "登录发送短信")
    @RequestMapping(value = "/sendlogsms", method = RequestMethod.POST)
    public HttpBaseResponse<Void> sendlogsms(HttpServletRequest req, @Json SendLoginSmsRequestVo request) {
        if (request == null || StringUtil.isNullOrEmpty(request.getMobile())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (UserServiceImpl.checkMobile(request.getMobile())) {
            return HttpBaseResponse.fail(HttpReturnCode.MOBILE_NOT_CORRECT, MessageConstant.MOBILE_NOT_EXISTS);
        }
        return SmsUtils.handlerSmsSendResult(userService.sendlogsms(req, request));
    }

    /**
     * 功能描述：短信注册或登录
     */
    @ApiOperation(value = "newvalidelogsms", notes = "短信注册或登录")
    @RequestMapping(value = "/newvalidelogsms", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> newvalidelogsms(HttpServletRequest req, @Json ValidateLoginSmsRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String openTagShow = apiConfig.getOpenTagShow();
        LoginResponseVo obj = userService.newvalidelogsms(req, request, openTagShow);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：短信注册或登录
     */
    @ApiOperation(value = "validelogsms", notes = "登录发送短信")
    @RequestMapping(value = "/validelogsms", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> validelogsms(HttpServletRequest req, @Json ValidateLoginSmsRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String openTagShow = apiConfig.getOpenTagShow();
        LoginResponseVo obj = userService.validelogsms(req, request, openTagShow);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：设置密码
     */
    @ApiOperation(value = "setsmspass", notes = "设置密码")
    @RequestMapping(value = "/setsmspass", method = RequestMethod.POST)
    public HttpBaseResponse<LoginResponseVo> validelogsms(HttpServletRequest req, @Json SMSSetPassRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String openTagShow = apiConfig.getOpenTagShow();
        LoginResponseVo obj = userService.setsmspass(req, request, openTagShow);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：更改用户头像
     */
    @ApiOperation(value = "changeavatar", notes = "更改用户头像")
    @RequestMapping(value = "/changeavatar", method = RequestMethod.POST)
    public HttpBaseResponse<UserAvatarResponseVo> changeavatar(@Json ChangeAvatarRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserAvatarResponseVo obj = userService.changeavatar(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：获取用户头像
     */
    @ApiOperation(value = "getUserAvatar", notes = "获取用户头像")
    @RequestMapping(value = "/getUserAvatar", method = RequestMethod.POST)
    public HttpBaseResponse<UserAvatarResponseVo> getUserAvatar(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserAvatarResponseVo obj = userService.getUserAvatar(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：检查用户信息
     */
    @ApiOperation(value = "checkUserInfo", notes = "检查用户信息")
    @RequestMapping(value = "/checkUserInfo", method = RequestMethod.POST)
    public HttpBaseResponse<CheckUserInfoResponseVo> checkUserInfo(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CheckUserInfoResponseVo obj = userService.checkUserInfo(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：用户完善信息，充值50艾积分
     */
    @ApiOperation(value = "completeUserInfo", notes = "用户完善信息，充值50艾积分")
    @RequestMapping(value = "/completeUserInfo", method = RequestMethod.POST)
    public HttpBaseResponse<AjbBillNoResponseVo> completeUserInfo(@Json CompleteUserRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        AjbBillNoResponseVo obj = userService.completeUserInfo(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：查询用户是否已注册
     */
    @ApiOperation(value = "judgeUserIsRegister", notes = "查询用户是否已注册")
    @RequestMapping(value = "/judgeUserIsRegister", method = RequestMethod.POST)
    public HttpBaseResponse<UserIsRegisterResponseVo> judgeUserIsRegister(@RequestBody LoginRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        UserIsRegisterResponseVo obj = userService.judgeUserIsRegister(request);
        return HttpBaseResponse.success(obj, obj.getDesc());
    }

    /**
     * 功能描述：用户实名认证
     */
    @ApiOperation(value = "setUserIdCard", notes = "用户实名认证")
    @RequestMapping(value = "/setUserIdCard", method = RequestMethod.POST)
    public HttpBaseResponse<IdCardCertifincationResponseVo> setUserIdCard(@RequestBody UserIdCardRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        IdCardCertifincationResponseVo obj = userService.setUserIdCard(request);
        return HttpBaseResponse.success(obj);
    }

    /**
     * 功能描述：微信H5活动登录
     */
    @ApiOperation(value = "loginInWeChatH5", notes = "微信H5活动登录")
    @RequestMapping(value = "/loginInWeChatH5", method = RequestMethod.POST)
    public HttpBaseResponse<String> loginInWeChatH5(HttpServletRequest req,
                                                    @RequestBody ValidateLoginSmsRequestVo request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String obj = userService.loginInWeChatH5(req, request);
        return HttpBaseResponse.success(obj);
    }

}
