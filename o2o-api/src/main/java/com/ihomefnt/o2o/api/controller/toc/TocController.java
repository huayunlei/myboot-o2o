package com.ihomefnt.o2o.api.controller.toc;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.toc.dto.*;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.InviteResultResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.NewLuckyDrawResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.OldLuckyDrawResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.PrizeListResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.SmsCodeRegisterVo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.toc.TocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Api(tags = "【TOC老带新API】")
@RestController
@RequestMapping("/toc")
public class TocController {

    @Autowired
    private TocService tocService;
    @Autowired
    UserProxy userProxy;

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "查询邀请码", notes = "根据用户信息获取邀请码")
    @RequestMapping(value = "/getInviteCode", method = RequestMethod.POST)
    public HttpBaseResponse<InviteCodeResponse> getInviteCode(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();

        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }

        InviteCodeResponse inviteCodeResponse = tocService.getInviteCode(user);
        return HttpBaseResponse.success(inviteCodeResponse);
    }

    
    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "老用户抽奖", notes = "老用户抽现金")
    @RequestMapping(value = "/doOldUserLuckyDraw", method = RequestMethod.POST)
    public HttpBaseResponse<OldLuckyDrawResponse> doOldUserLuckyDraw(@RequestBody LuckyDrawRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        if(request.getInviterUserId()==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.INVITERUSER_IS_NULL);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("oldUserId", user.getId());
        params.put("newUserId", request.getInviterUserId());

        OldLuckyDrawResponse oldLuckyDrawResponse = tocService.doOldUserLuckyDraw(params);
        return HttpBaseResponse.success(oldLuckyDrawResponse);
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "查询老用户累计邀请信息", notes = "老用户查询累计邀请注册人数+累计付款好友+累计获得现金")
    @RequestMapping(value = "/getInviteInformation", method = RequestMethod.POST)
    public HttpBaseResponse<InviteResultResponse> getInviteInformation(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        InviteResultResponse inviteResultResponse = tocService.getInviteInformation(user.getId());
        return HttpBaseResponse.success(inviteResultResponse);
    }


    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "邀请码与新用户绑定接口", notes = "邀请码与新用户手机号绑定接口")
    @RequestMapping(value = "/bindInviteCodeAndUser", method = RequestMethod.POST)
    public HttpBaseResponse<LuckyDrawTimeResponse> bindInviteCodeAndUser(@RequestBody TocRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }

        String mobile = request.getInviterMobile();
        Integer userId;
        if (StringUtils.isNotBlank(request.getSmsCode())) {//H5绑定验证码页面
            //先验证验证码进行注册
            SmsCodeRegisterVo smsCodeRegisterDto = new SmsCodeRegisterVo();
            smsCodeRegisterDto.setMobile(request.getInviterMobile());
            smsCodeRegisterDto.setSmsCode(request.getSmsCode());
            smsCodeRegisterDto.setSource(0);
            UserIdResultDto registerResultDto = userProxy.registerBySmsCode(smsCodeRegisterDto);

            long responseCode = registerResultDto.getCode();
            if (responseCode == 1) { // 注册成功
                userId=registerResultDto.getUserId();
            }else if( responseCode == 2){// 已注册查询是不是老用户
                userId=registerResultDto.getUserId();
                boolean isOld=tocService.judgeNewOrOldCustomer(registerResultDto.getUserId());
                if(isOld){//老用户
                    return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR,  "福利仅限新用户领取哦");
                }
                HasOrderDto hasOrderOrNot = tocService.getHasOrderOrNot(registerResultDto.getUserId());
                if(hasOrderOrNot!=null && hasOrderOrNot.isHasOrder()){//已经有订单了
                    return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR,  "福利仅限新用户领取哦");
                }
            } else{
                String message;
                Long code = HttpReturnCode.TO_C_KNOWN_ERROR;
                if (responseCode == 3) {
                    message = "注册失败，验证码错误";
                } else if (responseCode == 4) {
                    message = "注册失败，验证码错误";
                } else if (responseCode == 5) {
                    message = "注册失败，验证码已过期";
                } else {
                    message = "注册失败";
                    code = HttpResponseCode.FAILED;
                }
                return HttpBaseResponse.fail(code, message);
            }

        } else {//App内绑定页面
            HttpUserInfoRequest user = request.getUserInfo();
            if (user == null) {
                return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
            }
            mobile = user.getMobile();
            userId=user.getId();
            UserIdResultDto userIdResultVo = tocService.queryUserInviteCode(request.getInviteCode());
            if (userIdResultVo==null||userIdResultVo.getUserId()==null){
                return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR, "您输入的邀请码不存在");
            }

            if(userId.equals(userIdResultVo.getUserId())){
                return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR, "您不能输入自己的邀请码哦");
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("userId", userId);
        params.put("inviteCode", request.getInviteCode());
        LuckyDrawTimeResponse luckyDrawTimeResponse = tocService.bindInviteCodeAndUser(params);
        if(luckyDrawTimeResponse==null){
            return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR,  MessageConstant.FAILED);
        }
        if(luckyDrawTimeResponse.getQualified()==false){
            return HttpBaseResponse.fail(HttpReturnCode.TO_C_KNOWN_ERROR,  luckyDrawTimeResponse.getMsg());
        }
        return HttpBaseResponse.success(luckyDrawTimeResponse);
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "查询是否参加过新人抽奖", notes = "根据用户ID查询是否参加过新人抽奖")
    @RequestMapping(value = "/getLuckDrawOrNotInformation", method = RequestMethod.POST)
    public HttpBaseResponse<LuckyDrawTimeResponse> getLuckDrawOrNotInformation(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        LuckyDrawTimeResponse luckyDrawTimeResponse = tocService.getLuckDrawOrNotInformation(user.getId());
        return HttpBaseResponse.success(luckyDrawTimeResponse);
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "新用户抽奖接口", notes = "新用户抽奖接口")
    @RequestMapping(value = "/doNewUserLuckyDraw", method = RequestMethod.POST)
    public HttpBaseResponse<NewLuckyDrawResponse> doNewUserLuckyDraw(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, "未登录用户");
        }
        return HttpBaseResponse.success(tocService.doNewUserLuckyDraw(user.getId()));
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "查询新人抽到的福利列表", notes = "查询新人抽到的福利列表")
    @RequestMapping(value = "/getPrizesInformation", method = RequestMethod.POST)
    public HttpBaseResponse<PrizeListResponse> getPrizesInformation(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, "未登录用户");
        }
        return HttpBaseResponse.success(tocService.getPrizesInformation(user.getId()));
    }

    /**
     * 根据上线时间判断新人福利入口是否展示
     * @param request
     * @return
     */
    @ApiOperation(value = "区分新老用户", notes = "区分新老用户")
    @RequestMapping(value = "/queryCurrentUserIsOldOrNew", method = RequestMethod.POST)
    public HttpBaseResponse<UserDistinguishDto> queryCurrentUserIsOldOrNew(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        return user == null ? HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, "未登录用户!") : HttpBaseResponse.success(tocService.queryCurrentUserIsOldOrNew(user.getId()));
    }

    /**
     * 收银台、房产信息使用
     * @param request
     * @return
     */
    @ApiOperation(value = "判断用户是否是有邀请码的用户", notes = "判断用户是否是有邀请码的用户 date是boolean类型,true表示有邀请码的用户")
    @RequestMapping(value = "/queryCurrentUserIsNewUserWithInvitationCode", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> queryCurrentUserIsNewUserWithInvitationCode(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        return user == null ? HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, "未登录用户!") : HttpBaseResponse.success(tocService.queryCurrentUserIsUserWithInvitationCode(user.getId()));
    }

    /**
     * 老用户邀请入口使用
     * @param request
     * @return
     */
    @ApiOperation(value = "判断用户是否有订单", notes = "判断用户是否有订单")
    @RequestMapping(value = "/getHasOrderOrNot", method = RequestMethod.POST)
    public HttpBaseResponse<HasOrderDto> getHasOrderOrNot(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        HasOrderDto hasOrderDto  = tocService.getHasOrderOrNot(user.getId());
        return  HttpBaseResponse.success(hasOrderDto);

    }
}
