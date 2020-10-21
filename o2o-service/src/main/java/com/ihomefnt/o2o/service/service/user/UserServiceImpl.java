package com.ihomefnt.o2o.service.service.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.dao.sales.SalesDao;
import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AjbBillNoDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;
import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.user.doo.*;
import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.domain.user.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.*;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.push.PushConstant;
import com.ihomefnt.o2o.intf.manager.constant.user.UserConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.exception.UserNotExistException;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.manager.util.common.SecurityUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.secure.IhomeSignature;
import com.ihomefnt.o2o.intf.manager.util.common.secure.RSAUtils;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IdCardUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.agent.AgentAladdinCommissionProxy;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.push.PushProxy;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.emchat.EmchatIMUsersService;
import com.ihomefnt.o2o.intf.service.feedback.FeedbackService;
import com.ihomefnt.o2o.intf.service.sms.SmsService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AgentAladdinCommissionProxy commissionProxy;
    @Autowired
    private UserProxy userProxy;
    @Autowired
    private DicProxy dicProxy;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SalesDao salesDao;
    @Autowired
    private SmsService smsService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SmsProxy smsProxy;
    @Autowired
    private EmchatIMUsersService emchatIMUsersService;
    @Autowired
    private PushProxy pushProxy;
    @Autowired
    private AddressProxy addressProxy;
    @Autowired
    private AjbProxy ajbProxy;

    @Override
    public LoginResponseVo loginByPassword(HttpServletRequest req, LoginRequestVo loginRequest) {
        if (loginRequest == null || StringUtil.isNullOrEmpty(loginRequest.getMobile())
                || StringUtil.isNullOrEmpty(loginRequest.getPassword())) {
            throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

        LoginParamVo loginUser = new LoginParamVo();
        loginUser.setAccount(loginRequest.getMobile());
        loginUser.setPassword(loginRequest.getPassword());
        loginUser.setDeviceId(loginRequest.getDeviceToken());
        loginUser.setOsType(loginRequest.getOsType());
        Integer osType = loginRequest.getOsType();
        if (osType == null) {
            osType = 0;
        }
        int source = 0;
        if (osType == 1) {
            source = 2;
        } else if (osType == 2) {
            source = 3;
        } else if (osType == 3) {
            source = 4;
        } else if (osType == 4) {
            source = 5;
        }
        loginUser.setSource(source);
        loginUser.setLoginIp(IpUtils.getIpAddr(req));
        String token = userProxy.loginByPassword(loginUser);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(HttpResponseCode.USER_LOGIN_FAILED, MessageConstant.USER_LOGIN_FAILED_PSD);
        }
        UserDto userDto = userProxy.getUserByToken(token);
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.USER_LOGIN_FAILED, MessageConstant.USER_LOGIN_FAILED_PSD);
        }

        LoginResponseVo loginResponse = new LoginResponseVo();
        loginResponse.setAccessToken(token);
        loginResponse.setRefreshToken(token);
        MemberDto member = userDto.getMember();
        if (null != member) {
            loginResponse.setNickName(member.getNickName());
            loginResponse.setAvatar(member.getuImg());
        } else {
            loginResponse.setNickName("");
            loginResponse.setAvatar("");
        }
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        loginResponse.setTag(tags);

        //added by matao  经纪人客户关系即时生效  方式一走mq，方式二走rpc 20180822修改为异步
        List<TaskAction<?>> taskActions = new ArrayList<>();
        // 添加查询的记录任务
        taskActions.add(new TaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                commissionProxy.bindAgentCustomerRelationship(userDto);
                return 1;
            }
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);

        return loginResponse;
    }

    @Override
    public List<String> refreshUserTag(Long userId, LoginRequestVo loginRequest) {
        String cityCode = loginRequest.getCityCode();
        String appVersion = loginRequest.getAppVersion();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (null != cityCode) {
            param.put("tagType", PushConstant.TAG_CITY);
            param.put("tagName", cityCode);
            List<String> tagList = userDao.queryUserTag(param);
            if (null != tagList && tagList.size() > 0) {
                userDao.updateUserTag(param);
            } else {
                userDao.addUserTag(param);
            }
        }
        if (null != appVersion) {
            appVersion = appVersion.replaceAll("\\.", "_");
            param.put("tagType", PushConstant.TAG_VERSION);
            param.put("tagName", appVersion);
            List<String> tagList = userDao.queryUserTag(param);
            if (null != tagList && tagList.size() > 0) {
                userDao.updateUserTag(param);
            } else {
                userDao.addUserTag(param);
            }
        }
        param.put("tagType", null);
        return userDao.queryUserTag(param);
    }

    @Override
    public void changeNick(ChangeNickRequestVo httpChangeNickRequest) {
        if (httpChangeNickRequest == null || StringUtils.isBlank(httpChangeNickRequest.getNewNick())) {
            throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
        }
        String token = httpChangeNickRequest.getAccessToken();
        String newNick = httpChangeNickRequest.getNewNick();
        //要求昵称为英文或汉字、数字、“_”、"-"组成，限4~16个字符（一般一个汉字为2个字符）
        if (!RegexUtil.regexNickName(newNick)) {
            throw new BusinessException(HttpReturnCode.NICK_NAME_ILLEGAL, MessageConstant.NICK_NAME_ILLEGAL);
        }
        UserDto userDto = userProxy.getUserByToken(token);
        if (null == userDto) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        //判断昵称是否是禁用昵称关键字
        boolean isForbiddenNickName = false;
        DicListDto listResponseVo = dicProxy.getDicListByKey(HomeCardPraise.FORBIDDEN_NICKNAME);//查询所有关键字
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getDicList())) {
            List<DicDto> dicList = listResponseVo.getDicList();
            for (DicDto dicVo : dicList) {
                if (newNick.equals(dicVo.getValueDesc())) {
                    isForbiddenNickName = true;
                }
            }
        }
        //昵称属于禁用关键字，提示用户昵称已存在
        if (isForbiddenNickName) {
            throw new BusinessException(HttpResponseCode.USER_NICK_EXISTS, MessageConstant.USER_NICK_EXISTS);
        }

        boolean exist = userProxy.checkNickNameExist(newNick);
        MemberDto member = userDto.getMember();
        if (null == member) {
            if (exist) {
                throw new BusinessException(HttpResponseCode.USER_NICK_EXISTS, MessageConstant.USER_NICK_EXISTS);
            }
            //用户不存在，且输入的昵称不重复，进行插入操作
            MemberAddParamVo meberDto = new MemberAddParamVo();
            meberDto.setUserId(userDto.getId());
            meberDto.setNickName(newNick);
            if (1 != userProxy.addMemberInfo(meberDto)) {
                throw new BusinessException(MessageConstant.ILLEGAL_USER);
            }
        } else {
            if (exist) {
                throw new BusinessException(HttpResponseCode.USER_NICK_EXISTS, MessageConstant.USER_NICK_EXISTS);
            }
            //用户存在，且输入的昵称不重复，进行更新用户昵称的操作
            MemberUpdateParamVo memberDto = new MemberUpdateParamVo();
            memberDto.setNickName(newNick);
            memberDto.setUserId(userDto.getId());
            memberDto.setId(member.getId());
            if (1 != userProxy.updateMemberInfo(memberDto)) {
                throw new BusinessException(MessageConstant.ILLEGAL_USER);
            }
        }
    }

    @Override
    public RegisterResponseVo registerByMobileAndPassword(RegisterRequestVo registerRequest) {
        if (registerRequest == null || StringUtil.isNullOrEmpty(registerRequest.getPassword())
                || StringUtil.isNullOrEmpty(registerRequest.getMobile())) {
            throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }
        String mobile = registerRequest.getMobile();
        UserDo user = userDao.queryUserByMobilePassword(mobile, null);
        if (user != null) {
            throw new BusinessException(MessageConstant.USER_REGISTERED);
        }
        String activateCode = SecurityUtil.generateActivateCode();
        String registerKey = SecurityUtil.encodeByMD5(String.valueOf(System.currentTimeMillis()));
        userDao.addRegistration(mobile, registerRequest.getPassword(), activateCode, registerKey);

        smsService.sendSms(activateCode, mobile);

        RegisterResponseVo registerResponse = new RegisterResponseVo();
        registerResponse.setRegisterKey(registerKey);
        return registerResponse;
    }

    @Override
    public void resendsms(ReSendSmsRequestVo sendSmsRequest) {
        if (sendSmsRequest == null || StringUtil.isNullOrEmpty(sendSmsRequest.getMobile())
                || StringUtil.isNullOrEmpty(sendSmsRequest.getRegisterKey())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        SmsLoginUserParamVo loginUser = new SmsLoginUserParamVo();
        loginUser.setMobile(sendSmsRequest.getMobile());
        loginUser.setSmsCode(sendSmsRequest.getRegisterKey());
        Integer osType = sendSmsRequest.getOsType();
        if (osType == null) {
            osType = 0;
        }
        int source = 0;
        if (osType == 1) {
            source = 2;
        } else if (osType == 2) {
            source = 3;
        } else if (osType == 3) {
            source = 4;
        } else if (osType == 4) {
            source = 5;
        }
        loginUser.setSource(source);
        LoginResultVo loginBySmsCode = userProxy.loginBySmsCode(loginUser);
        if (loginBySmsCode == null || StringUtils.isBlank(loginBySmsCode.getToken())) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public LoginResponseVo registerValidateSms(RegisterValidateRequestVo registerValidateRequest) {
        String mobile = registerValidateRequest.getMobile();
        RegisterDo register = userDao.queryRegistration(mobile, registerValidateRequest.getRegisterKey(), registerValidateRequest.getActivateCode());
        if (null == register) {
            throw new BusinessException(MessageConstant.FAILED);
        }
        UserDo user = new UserDo();
        user.setPassword(register.getPassword());
        user.setDeviceToken(registerValidateRequest.getDeviceToken());
        user.setLocation(registerValidateRequest.getLocation());
        user.setOsType(registerValidateRequest.getOsType());
        user.setpValue(registerValidateRequest.getParterValue());

        user.setMobile(mobile);
        //用户关系绑定 start
        UserRelationDo userRelation = queryInvitedUserByRegMobile(mobile);
        //如果1.有升级关系2.没有置空
        if (userRelation != null) {
            user.setRecMobile(userRelation.getInvitemobile());//添加推荐用户的号码

            UserRelationDo condition = new UserRelationDo();//1.降级用户关系
            condition.setInvitedmobile(mobile);
            condition.setStatus(3);
            userDao.updateUserRelation(condition);//降级的所有的
            userRelation.setStatus(2);
            userDao.updateUserRelation(userRelation);//2.升级绑定关系  升级特定的
        }
        //用户关系绑定 end
        userDao.addUser(user);
        // delete
        userDao.deleteRegister(register.getrId());
        // make user login
        LogDo log = auth(mobile, user.getPassword());

        //update t_customer;
        bindSalesCustomer(mobile);

        LoginResponseVo loginResponse = new LoginResponseVo();
        loginResponse.setRefreshToken(log.getRefreshToken());
        loginResponse.setAccessToken(log.getAccessToken());
        loginResponse.setExpire(log.getExpire());// 15天
        return loginResponse;
    }

    //update t_sales_customer 表，这个是为了中南销售用的，绑定销售和用户
    private void bindSalesCustomer(String mobile) {
        //in case of exception, catch all;
        try {
            salesDao.updateInviteStatus(mobile);
        } catch (Exception e) {

        }
    }

    @Override
    public LogDo auth(String mobile, String password) {
        UserDo user = userDao.queryUserByMobilePassword(mobile, password);
        // match, return access_token, refresh_token, expire
        if (user != null) {
            // 判断用户是否已经登录，如果是，就删除登录记录
            LogDo log = userDao.queryLogByUId(user.getuId());
            if (log != null) {
                // 删除记录
                userDao.deleteLog(log.getRefreshToken());
            }
            // 生成登录记录
            String access_token = SecurityUtil.encodeByMD5(String.valueOf(System.currentTimeMillis()) + user.getuId());
            String refresh_token = SecurityUtil.encodeByMD5(String.valueOf(System.currentTimeMillis())
                    + user.getMobile());
            Long expire = 15L;
            // save to log table;
            LogDo tLog = new LogDo();
            tLog.setAccessToken(access_token);
            tLog.setExpire(expire);
            tLog.setRefreshToken(refresh_token);
            tLog.setuId(user.getuId());
            userDao.addLog(tLog);
            return tLog;
        }
        return null;
    }

    /**
     * 根据注册用户电话号码得到用户的绑定关系
     * 1.有绑定关系
     * 2.或者没有
     *
     * @param mobile
     * @return
     */
    private UserRelationDo queryInvitedUserByRegMobile(String mobile) {
        UserRelationDo userRelation = new UserRelationDo();
        userRelation.setInvitedmobile(mobile);
        userRelation.setStatus(1);
        List<UserRelationDo> userRelations = userDao.queryUserRelation(userRelation);
        if (userRelations != null && !userRelations.isEmpty() && userRelations.size() == 1) {
            userRelation = userRelations.get(0);
        } else {
            userRelation = null;
        }
        return userRelation;
    }

    @Override
    public RetrievePassResponseVo retrievepass(RetrievePassRequestVo retrievePassRequest) {
        if (retrievePassRequest == null || StringUtil.isNullOrEmpty(retrievePassRequest.getMobile())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String mobile = retrievePassRequest.getMobile();
        UserDo user = userDao.queryUserByMobilePassword(mobile, null);
        if (user == null) {
            throw new BusinessException(MessageConstant.USER_NOT_EXISTS);
        }

        String activateCode = SecurityUtil.generateActivateCode();
        String registerKey = SecurityUtil.encodeByMD5(String.valueOf(System.currentTimeMillis()));
        userDao.addRegistration(mobile, null, activateCode, registerKey);
        smsService.sendSms(activateCode, mobile);

        RetrievePassResponseVo retrievePassResponse = new RetrievePassResponseVo();
        retrievePassResponse.setRetieveKey(registerKey);
        return retrievePassResponse;
    }

    @Override
    public void retriveValidate(RetrieveValidateRequestVo retrieveValidateRequest) {
        if (retrieveValidateRequest == null || StringUtil.isNullOrEmpty(retrieveValidateRequest.getMobile())
                || StringUtil.isNullOrEmpty(retrieveValidateRequest.getActivateCode())
                || StringUtil.isNullOrEmpty(retrieveValidateRequest.getRetrieveKey())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        RegisterDo register = userDao.queryRegistration(retrieveValidateRequest.getMobile()
                , retrieveValidateRequest.getRetrieveKey()
                , retrieveValidateRequest.getActivateCode());

        if (null == register) {
            throw new BusinessException(MessageConstant.USER_NOT_EXISTS);
        }
    }

    @Override
    public LoginResponseVo resetPass(ResetPassRequestVo resetPassRequest) {
        if (resetPassRequest == null || StringUtil.isNullOrEmpty(resetPassRequest.getMobile())
                || StringUtil.isNullOrEmpty(resetPassRequest.getRetrieveKey())
                || StringUtil.isNullOrEmpty(resetPassRequest.getPassword())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String mobile = resetPassRequest.getMobile();
        String registerKey = resetPassRequest.getRetrieveKey();
        String password = resetPassRequest.getPassword();
        RegisterDo register = userDao.queryRegistration(mobile, registerKey, null);
        if (null == register) {
            throw new BusinessException(MessageConstant.USER_NOT_EXISTS);
        }
        // update password
        userDao.updatePassword(mobile, password);
        // delete register entry
        userDao.deleteRegister(register.getrId());
        // make user login
        LogDo log = auth(mobile, password);
        LoginResponseVo loginResponse = new LoginResponseVo();
        loginResponse.setRefreshToken(log.getRefreshToken());
        loginResponse.setAccessToken(log.getAccessToken());
        loginResponse.setExpire(log.getExpire());// 15天
        return loginResponse;
    }

    @Override
    public void logout(LogoutRequestVo logoutRequest) {
        LogoutParamVo logout = new LogoutParamVo();
        logout.setToken(logoutRequest.getRefreshToken());
        logout.setDeviceId(logoutRequest.getDeviceToken());
        logout.setOsType(logoutRequest.getOsType());
        userProxy.logout(logout);
    }

    @Override
    public FeedbackResponseVo addFeedback(FeedbackRequestVo feedbackRequest) {
        if (feedbackRequest == null || StringUtil.isNullOrEmpty(feedbackRequest.getPhoneNumber())
                || StringUtil.isNullOrEmpty(feedbackRequest.getAccessToken())
                || StringUtil.isNullOrEmpty(feedbackRequest.getContent())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!RegexUtil.isMobileNew(feedbackRequest.getPhoneNumber())) {
            throw new BusinessException(HttpReturnCode.MOBILE_NOT_CORRECT, MessageConstant.MOBILE_NOT_EXISTS);
        }

        String content = feedbackRequest.getContent();
        if (content != null && content.length() < 500) {
            throw new BusinessException(MessageConstant.CONTENT_OVER_FLOW);
        }

        //1.查询用户是否登录 登录获取uid
        HttpUserInfoRequest userDto = feedbackRequest.getUserInfo();
        FeedbackResponseVo feedbackResponse = new FeedbackResponseVo();
        //2.组装意见反馈或咨询问题信息并入库
        TFeedbackDto feedback = new TFeedbackDto();
        feedback.setContent(feedbackRequest.getContent());
        feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
        feedback.setType(feedbackRequest.getType());
        feedback.setUserId(userDto != null ? userDto.getId().longValue() : null);
        feedbackResponse.setFeedbackId(feedbackService.addFeedback(feedback));

        return feedbackResponse;
    }

    @Override
    public void checkAccessToken(HttpBaseRequest request) {
        if (request == null || StringUtil.isNullOrEmpty(request.getAccessToken())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String token = request.getAccessToken();
        if (!userProxy.checkLegalUser(token)) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(HttpResponseCode.USER_NOT_EXISTS, MessageConstant.USER_NOT_EXISTS);
        }
    }

    @Override
    public Integer sendlogsmsV2(HttpServletRequest req, SendLoginSmsRequestVo request) {
        SendSmsCodeParamVo param = new SendSmsCodeParamVo();
        param.setIp(IpUtils.getIpAddr(req));
        param.setMobile(request.getMobile());
        param.setType(request.getSmsType());
        return smsProxy.sendSmsCode(param);
    }

    @Override
    public Integer sendlogsms(HttpServletRequest req, SendLoginSmsRequestVo request) {
        SendSmsCodeParamVo param = new SendSmsCodeParamVo();
        param.setIp(IpUtils.getIpAddr(req));
        param.setMobile(request.getMobile());
        param.setType(request.getSmsType());
        return smsProxy.sendSmsCode(param);
    }

    public static boolean checkMobile(String mobile) {
        Pattern pattern = Pattern.compile("^-?[0-9]+");
        if (!pattern.matcher(mobile).matches()) {
            //非数字
            return true;
        }
        return false;
    }

    public static boolean checkSign(HttpServletRequest reg, String mobile) {
        String sign = reg.getHeader("sign");
        if (StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(mobile)) {
            try {
                String signOrgin = new StringBuffer(mobile).reverse().toString();

                String res = IhomeSignature.rsaDecrypt(sign, RSAUtils.privateKey, "UTF-8");

                if (StringUtils.isNotBlank(res) && res.equals(signOrgin)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public LoginResponseVo newvalidelogsms(HttpServletRequest req, ValidateLoginSmsRequestVo request, String openTagShow) {
        checkParamsForValidelogsms(request);
        LoginResponseVo response = null;
        int login = request.getLogin();
        if (login == 0) {// 注册
            response = newvalidelogsmsForRegister(request, openTagShow);
        } else if (login == 1) { // 登录
            String loginIp = IpUtils.getIpAddr(req);
            response = newvalidelogsmsForLogin(loginIp, request, openTagShow);
        }
        return response;
    }

    private LoginResponseVo newvalidelogsmsForLogin(String ip, ValidateLoginSmsRequestVo request, String openTagShow) {
        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        String mobile = request.getMobile();
        String smsCode = request.getSms();
        int source = getSourceByOsType(osType);

        LoginResponseVo response = new LoginResponseVo();
        // 如果通过号码查询不到用户,则说明是注册
        UserDto userDto = userProxy.getUserByMobile(mobile);
        // SmsLoginUserDto loginUser = new SmsLoginUserDto();
        SmsLoginUserParamVo loginUser = new SmsLoginUserParamVo();
        loginUser.setMobile(mobile);
        loginUser.setLoginIp(ip);
        loginUser.setDeviceId(request.getDeviceToken());
        loginUser.setOsType(osType);
        loginUser.setSmsCode(smsCode);
        loginUser.setSource(source);

        LoginResultVo loginResultDto = userProxy.loginBySmsCode(loginUser);
        if (null == loginResultDto) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String token = loginResultDto.getToken();
        if (StringUtils.isBlank(token)) {
            Integer resultCode = 0;
            if (loginResultDto.getCode() != null) {
                resultCode = loginResultDto.getCode();
            }
            throw new BusinessException(HttpResponseCode.SMS_CODE_ERROR, getLoginFailMsgByCode(resultCode));
        }

        if (userDto == null) {
            userDto = userProxy.getUserByToken(token);
        }
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.SERVICE_BUSY, "用户接口请求繁忙,请稍后再试");
        }

        response.setAccessToken(token);
        response.setRefreshToken(token);
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName("");
            response.setAvatar("");
        }
        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto
                    .getId())); // 环信IM用户信息
            if (null == emchatIMUser) {
                emchatIMUsersService.registerEmUser(String.valueOf(userDto.getId()), mobile);
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            response.setEmchatIMUser(emchatIMUser);
        }
        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);

        // 20170920实名认证
        String realName = "";
        String idCardNum = "";
        IdCardCertifincationResponseVo certifincationResponseVo = this
                .queryCertifincationResult(userDto.getId());
        if (certifincationResponseVo != null) {
            if (StringUtils.isNotBlank(certifincationResponseVo.getRealName())) {
                realName = certifincationResponseVo.getRealName();
            }
            if (StringUtils.isNotBlank(certifincationResponseVo.getIdCardNum())) {
                idCardNum = certifincationResponseVo.getIdCardNum();
            }
        }
        response.setRealName(realName);
        response.setIdCardNum(idCardNum);

        //added by matao 经纪人客户关系即时生效 2018/07/03
        commissionProxy.bindAgentCustomerRelationship(userDto);
        return response;
    }

    @Override
    public IdCardCertifincationResponseVo queryCertifincationResult(Integer userId) {
        IdCardCertifincationResponseVo response = new IdCardCertifincationResponseVo();
        IdCardCertifincationDto cardCertifincationResponseVo = userProxy.queryCertifincationResult(userId);
        if (cardCertifincationResponseVo != null) {
            if (StringUtils.isNotBlank(cardCertifincationResponseVo.getIdCard())) {
                String idCardNum = cardCertifincationResponseVo.getIdCard();
                if (idCardNum.length() == 18) {
                    response.setIdCardNum(idCardNum.replaceAll("(\\d{3})\\d{11}(\\d{4})", "$1***********$2"));
                } else if (idCardNum.length() == 15) {
                    response.setIdCardNum(idCardNum.replaceAll("(\\d{3})\\d{8}(\\d{4})", "$1********$2"));
                }
            }
            if (cardCertifincationResponseVo.getUserId() != null) {
                response.setUserId(cardCertifincationResponseVo.getUserId());
            }
            if (StringUtils.isNotBlank(cardCertifincationResponseVo.getRealName())) {
                response.setRealName(cardCertifincationResponseVo.getRealName());
            }
            if (cardCertifincationResponseVo.getAuthentication() != null) {
                response.setIdCardCertification(cardCertifincationResponseVo.getAuthentication());
            }
        }
        return response;
    }

    private LoginResponseVo newvalidelogsmsForRegister(ValidateLoginSmsRequestVo request, String openTagShow) {
        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        LoginResponseVo response = new LoginResponseVo();
        SmsCodeRegisterVo smsCodeRegisterDto = new SmsCodeRegisterVo();
        smsCodeRegisterDto.setMobile(request.getMobile());
        smsCodeRegisterDto.setpValue(request.getParterValue());
        smsCodeRegisterDto.setSmsCode(request.getSms());
        smsCodeRegisterDto.setOsType(osType);
        smsCodeRegisterDto.setSource(getSourceByOsType(osType));
        UserIdResultDto registerResultDto = userProxy.registerBySmsCode(smsCodeRegisterDto);
        if (registerResultDto == null || null == registerResultDto.getCode()) {
            throw new BusinessException("注册失败");
        }
        long responseCode = registerResultDto.getCode();
        if (responseCode == 2) {// 注册时用户已存在
            throw new BusinessException(MessageConstant.SHOW_USER_REGISTERED);
        } else if (responseCode != 1) {
            throw new BusinessException(HttpResponseCode.SMS_CODE_ERROR, getRegisterFailMsgByCode(responseCode));
        }

        String token = registerResultDto.getToken();
        UserDto userDto = userProxy.getUserByToken(token);
        if (null == userDto || userDto.getId() == null) {
            throw new BusinessException(HttpResponseCode.PARAMS_ERROR, "用户或者验证码不正确");
        }

        response.setAccessToken(token);
        response.setRefreshToken(token);
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName("");
            response.setAvatar("");
        }
        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = null;
            if (userDto.getId() != null) {
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            if (null == emchatIMUser) {
                emchatIMUsersService.registerEmUser(String.valueOf(userDto.getId()), request.getMobile());
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            response.setEmchatIMUser(emchatIMUser);
        }

        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);
        response.setIdCardNum("");
        response.setRealName("");

        //added by matao 经纪人客户关系即时生效 2018/07/03
        commissionProxy.bindAgentCustomerRelationship(userDto);
        return response;
    }

    private String getLoginFailMsgByCode(Integer code) {
        String message = "登录失败";
        if (code == 0) {
            message = "登录失败，用户名或密码错误";
        } else if (code == 2) {
            message = "登录失败，验证码错误";
        } else if (code == 3) {
            message = "登录失败，验证码错误";
        } else if (code == 4) {
            message = "登录失败，验证码已过期";
        } else if (code == -1) {
            message = "请输入正确的手机号码";
        }
        return message;
    }

    private String getRegisterFailMsgByCode(long code) {
        String message = "注册失败";
        if (code == 3) {
            message = "注册失败，验证码错误";
        } else if (code == 4) {
            message = "注册失败，验证码错误";
        } else if (code == 5) {
            message = "注册失败，验证码已过期";
        }
        return message;
    }

    private int getSourceByOsType(Integer osType) {
        int source = 0;
        if (osType == 1) {
            source = 2;
        } else if (osType == 2) {
            source = 3;
        } else if (osType == 3) {
            source = 4;
        } else if (osType == 4) {
            source = 5;
        }
        return source;
    }

    @Override
    public LoginResponseVo validelogsms(HttpServletRequest req, ValidateLoginSmsRequestVo request, String openTagShow) {
        checkParamsForValidelogsms(request);
        LoginResponseVo response = null;
        int login = request.getLogin();
        if (login == 0) {// 注册
            response = validelogsmsForRegister(request, openTagShow);
        } else if (login == 1) { // 登录
            String loginIp = IpUtils.getIpAddr(req);
            response = validelogsmsForLogin(loginIp, request, openTagShow);
        }
        return response;
    }

    private LoginResponseVo validelogsmsForLogin(String loginIp, ValidateLoginSmsRequestVo request, String openTagShow) {
        String appVersion = request.getAppVersion(); // 2.7.0
        if (StringUtils.isNotBlank(appVersion)) {
            String appVersionStr = appVersion.replaceAll("\\.", "");
            int appVersionNum = Integer.parseInt(appVersionStr);
            if (appVersionNum <= 270) {
                return validelogsmsForLogin270(loginIp, request, openTagShow);
            }
        }

        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        String mobile = request.getMobile();
        String smsCode = request.getSms();
        int source = getSourceByOsType(osType);
        LoginResponseVo response = new LoginResponseVo();

        SmsLoginUserParamVo loginUser = new SmsLoginUserParamVo();
        loginUser.setMobile(mobile);
        loginUser.setLoginIp(loginIp);
        loginUser.setDeviceId(request.getDeviceToken());
        loginUser.setOsType(osType);
        loginUser.setSmsCode(smsCode);
        loginUser.setSource(source);
        LoginResultVo loginResultDto = userProxy.loginBySmsCode(loginUser);
        if (null == loginResultDto) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String token = loginResultDto.getToken();
        if (StringUtils.isBlank(token)) {
            Integer resultCode = 0;
            if (loginResultDto.getCode() != null) {
                resultCode = loginResultDto.getCode();
            }
            throw new BusinessException(HttpResponseCode.SMS_CODE_ERROR, getLoginFailMsgByCode(resultCode));
        }

        UserDto userDto = userProxy.getUserByToken(token);
        if (userDto == null) {
            throw new BusinessException(MessageConstant.USER_NOT_EXISTS);
        }

        response.setAccessToken(token);
        response.setRefreshToken(token);
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName("");
            response.setAvatar("");
        }
        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto
                    .getId())); // 环信IM用户信息
            if (null == emchatIMUser) {
                emchatIMUsersService.registerEmUser(String.valueOf(userDto.getId()), mobile);
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            response.setEmchatIMUser(emchatIMUser);
        }
        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);
        return response;
    }

    private LoginResponseVo validelogsmsForLogin270(String loginIp, ValidateLoginSmsRequestVo request, String openTagShow) {
        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        String mobile = request.getMobile();
        int source = getSourceByOsType(osType);

        LoginResponseVo response = new LoginResponseVo();
        UserDto userDto = userProxy.getUserByMobile(mobile);
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_EXISTS, MessageConstant.USER_NOT_EXISTS);
        }

        boolean b = userProxy.setPassword(userDto.getId(), "");
        if (!b) {
            throw new BusinessException("设置密码失败！");
        }
        LoginParamVo passwordLoginUserDto = new LoginParamVo();
        passwordLoginUserDto.setAccount(mobile);
        passwordLoginUserDto.setPassword("");
        passwordLoginUserDto.setDeviceId(request.getDeviceToken());
        passwordLoginUserDto.setLoginIp(loginIp);
        passwordLoginUserDto.setOsType(osType);
        passwordLoginUserDto.setSource(source);
        String token = userProxy.loginByPassword(passwordLoginUserDto);
        if (StringUtil.isNullOrEmpty(token)) {
            throw new BusinessException("设置密码成功后登录失败！");
        }

        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(String
                    .valueOf(userDto.getId())); // 环信IM用户信息
            if (null == emchatIMUser) {
                emchatIMUsersService
                        .registerEmUser(String.valueOf(userDto.getId()), mobile);
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto
                        .getId())); // 环信IM用户信息
            }
            response.setEmchatIMUser(emchatIMUser);
        }
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName("");
            response.setAvatar("");
        }

        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);
        response.setAccessToken(token);
        response.setRefreshToken(token);
        return response;
    }

    private LoginResponseVo validelogsmsForRegister(ValidateLoginSmsRequestVo request, String openTagShow) {
        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        LoginResponseVo response = new LoginResponseVo();
        SmsCodeRegisterVo smsCodeRegisterDto = new SmsCodeRegisterVo();
        smsCodeRegisterDto.setMobile(request.getMobile());
        smsCodeRegisterDto.setpValue(request.getParterValue());
        smsCodeRegisterDto.setSmsCode(request.getSms());
        smsCodeRegisterDto.setOsType(osType);
        smsCodeRegisterDto.setSource(getSourceByOsType(osType));
        UserIdResultDto registerResultDto = userProxy.registerBySmsCode(smsCodeRegisterDto);
        if (registerResultDto == null || null == registerResultDto.getCode()) {
            throw new BusinessException("注册失败");
        }

        String token = registerResultDto.getToken();
        UserDto userDto = userProxy.getUserByToken(token);
        if (null == userDto || userDto.getId() == null) {
            throw new BusinessException(HttpResponseCode.PARAMS_ERROR, "用户或者验证码不正确");
        }

        response.setAccessToken(token);
        response.setRefreshToken(token);
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName("");
            response.setAvatar("");
        }
        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = null;
            if (userDto.getId() != null) {
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            if (null == emchatIMUser) {
                emchatIMUsersService.registerEmUser(String.valueOf(userDto.getId()), request.getMobile());
                emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId())); // 环信IM用户信息
            }
            response.setEmchatIMUser(emchatIMUser);
        }

        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);
        return response;
    }

    private void checkParamsForValidelogsms(ValidateLoginSmsRequestVo request) {
        if (StringUtils.isBlank(request.getMobile())) {
            throw new BusinessException(HttpResponseCode.PARAMS_ERROR, "用户手机号码参数为空");
        }
        if (StringUtils.isBlank(request.getSms())) {
            throw new BusinessException(HttpResponseCode.PARAMS_ERROR, "验证码参数为空");
        }
    }

    @Override
    public LoginResponseVo setsmspass(HttpServletRequest req, SMSSetPassRequestVo request, String openTagShow) {
        if (request == null || StringUtils.isBlank(request.getMobile())
                || StringUtils.isBlank(request.getPassword())) {
            throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
        }
        String mobile = request.getMobile();
        UserDto userDto = userProxy.getUserByToken(request.getAccessToken());
        if (null == userDto || StringUtils.isBlank(userDto.getMobile())
                || !userDto.getMobile().equals(mobile)) {
            throw new BusinessException(HttpResponseCode.USER_NOT_EXISTS, MessageConstant.USER_NOT_EXISTS);
        }
        String password = request.getPassword();
        boolean b = userProxy.setPassword(userDto.getId(), password);
        if (!b) {
            throw new BusinessException("设置密码失败！");
        }
        //注册时改密码不发送消息，登录修改密码需要发送消息
        if (request.getMessageType() == UserConstants.SEND_MESSAGE_FLAG) {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> smsParamMap = new HashMap<String, Object>();
            smsParamMap.put("createTime", dfs.format(new Date()));
            smsParamMap.put("mobileNum", mobile);
            JSONObject param = new JSONObject();
            param.put("smsParams", JSON.toJSONString(smsParamMap));
            List<String> mobiles = new ArrayList<String>();
            mobiles.add(mobile);
            param.put("mobiles", mobiles);
            param.put("smsKey", PushConstant.SMS_CHANGE_PSD);
            pushProxy.sendSmsMessageByAli(param);
        }
        LoginParamVo passwordLoginUserDto = new LoginParamVo();
        passwordLoginUserDto.setAccount(mobile);
        passwordLoginUserDto.setPassword(password);
        passwordLoginUserDto.setDeviceId(request.getDeviceToken());
        String loginIp = IpUtils.getIpAddr(req);
        passwordLoginUserDto.setLoginIp(loginIp);
        passwordLoginUserDto.setOsType(request.getOsType());
        Integer osType = request.getOsType() == null ? 0 : request.getOsType();
        int source = getSourceByOsType(osType);
        passwordLoginUserDto.setSource(source);
        String token = userProxy.loginByPassword(passwordLoginUserDto);
        if (StringUtil.isNullOrEmpty(token)) {
            throw new BusinessException("设置密码成功，登录失败！");
        }

        LoginResponseVo response = new LoginResponseVo();
        // 只有满足生产环境才为true
        if (openTagShow.equals("true")) {
            EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(String.valueOf(userDto.getId()));    //环信IM用户信息
            response.setEmchatIMUser(emchatIMUser);
        }
        MemberDto member = userDto.getMember();
        if (null != member) {
            response.setNickName(member.getNickName());
            response.setAvatar(member.getuImg());
        } else {
            response.setNickName(null);
            response.setAvatar("");
        }

        LoginRequestVo loginRequest = new LoginRequestVo();
        loginRequest.setCityCode(request.getCityCode());
        loginRequest.setAppVersion(request.getAppVersion());
        List<String> tags = this.refreshUserTag(userDto.getId().longValue(), loginRequest);
        response.setTag(tags);

        //20170920实名认证
        String realName = "";
        String idCardNum = "";
        IdCardCertifincationResponseVo certifincationResponseVo = this.queryCertifincationResult(userDto.getId());
        if (certifincationResponseVo != null) {
            if (StringUtils.isNotBlank(certifincationResponseVo.getRealName())) {
                realName = certifincationResponseVo.getRealName();
            }
            if (StringUtils.isNotBlank(certifincationResponseVo.getIdCardNum())) {
                idCardNum = certifincationResponseVo.getIdCardNum();
            }
        }
        response.setRealName(realName);
        response.setIdCardNum(idCardNum);
        response.setAccessToken(token);
        response.setRefreshToken(token);
        return response;
    }

    @Override
    public UserAvatarResponseVo changeavatar(ChangeAvatarRequestVo request) {
        if (null == request || StringUtils.isBlank(request.getNewAvatar())) {
            throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
        }
        UserDto userDto = userProxy.getUserByToken(request.getAccessToken());
        if (null == userDto) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        String avatar = request.getNewAvatar();
        MemberDto member = userDto.getMember();
        int result = 0;
        if (null == member) {
            MemberAddParamVo meberDto = new MemberAddParamVo();
            meberDto.setUserId(userDto.getId());
            meberDto.setuImg(avatar);
            meberDto.setCreateTime(new Date());
            meberDto.setUpdateTime(new Date());
            result = userProxy.addMemberInfo(meberDto);
        } else {
            MemberUpdateParamVo memberDto = new MemberUpdateParamVo();
            BeanUtils.copyProperties(member, memberDto);
            memberDto.setuImg(avatar);
            memberDto.setUpdateTime(new Date());
            result = userProxy.updateMemberInfo(memberDto);
        }

        if (result != 1) {
            throw new BusinessException(MessageConstant.ILLEGAL_USER);
        }

        UserAvatarResponseVo responseVo = new UserAvatarResponseVo();
        String userImage = AliImageUtil.imageCompress(avatar, request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL);
        responseVo.setUserImage(userImage);
        return responseVo;
    }

    @Override
    public UserAvatarResponseVo getUserAvatar(HttpBaseRequest request) {
        if (null == request || StringUtils.isBlank(request.getAccessToken())) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        String accessToken = request.getAccessToken();
        UserDto user = userProxy.getUserByToken(accessToken);
        if (null == user) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        MemberDto member = user.getMember();
        String userImage = "";
        if (null != member) {
            String uImg = member.getuImg();
            if (StringUtil.isNullOrEmpty(uImg)) {
                userImage = StaticResourceConstants.USER_DEFAULT_IMAGE;
            } else {
                userImage = uImg;
            }
        } else {
            MemberAddParamVo memberDto = new MemberAddParamVo();
            memberDto.setUserId(user.getId());
            memberDto.setuImg(StaticResourceConstants.USER_DEFAULT_IMAGE);
            ;
            userProxy.addMemberInfo(memberDto);
            userImage = StaticResourceConstants.USER_DEFAULT_IMAGE;
        }

        UserAvatarResponseVo response = new UserAvatarResponseVo();
        response.setUserImage(userImage);
        return response;
    }

    @Override
    public CheckUserInfoResponseVo checkUserInfo(HttpBaseRequest request) {
        if (null == request || StringUtils.isBlank(request.getAccessToken())) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        UserDto user = userProxy.getUserByToken(request.getAccessToken());
        if (user == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        CheckUserInfoResponseVo response = new CheckUserInfoResponseVo();
        MemberDto member = user.getMember();
        if (null != member) {
            response.setAvast(!StringUtil.isNullOrEmpty(member.getuImg()));
            response.setNickName(!StringUtil.isNullOrEmpty(member.getNickName()));
        } else {
            response.setAvast(false);
            response.setNickName(false);
        }
        UserAddressResultDto userAddress = addressProxy.queryDefaultByUserId(user.getId());
        if (null != userAddress) {
            if (this.isPositiveNumber(userAddress.getCityId()) && this.isPositiveNumber(userAddress.getCountryId())
                    && this.isPositiveNumber(userAddress.getProvinceId()) && !StringUtils.isEmpty(userAddress.getAddress())
                    && !StringUtils.isEmpty(userAddress.getConsignee()) && !StringUtils.isEmpty(userAddress.getMobile())) {
                response.setUserAddress(true);
            } else {
                response.setUserAddress(false);
            }
        } else {
            response.setUserAddress(false);
        }

        if (response.isAvast() && response.isNickName() && response.isUserAddress()) {
            response.setInfoComplete(true);
        } else {
            response.setInfoComplete(false);
        }
        //20170920实名认证
        response.setIdCardCertification(this.judgeUserIsCertification(user.getId()));
        return response;
    }

    /**
     * 判断数字是否为正整数
     *
     * @param number
     * @return
     */
    private boolean isPositiveNumber(Integer number) {
        if (null != number) {
            if (number > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean judgeUserIsCertification(Integer userId) {
        if (userId == null) {
            return false;
        }

        IdCardCertifincationDto certifincationResponseVo = userProxy.queryCertifincationResult(userId);
        if (certifincationResponseVo != null && certifincationResponseVo.getAuthentication() != null) {
            return certifincationResponseVo.getAuthentication();
        } else {
            return false;
        }
    }

    @Override
    public AjbBillNoResponseVo completeUserInfo(CompleteUserRequestVo request) {
        if (null == request) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        if (!request.isNextState() || request.isPreState()) {
            throw new BusinessException(MessageConstant.FAILED);
        }

        //判断用户是否已经注册充值过艾积分  2为完善资料活动
        AjbRecordListResponseVo ajbRecordListResponseVo = ajbProxy.queryRecordByCodeAndUserId(user.getId(), UserConstants.AJB_COMPLETE_CODE);


        if (ajbRecordListResponseVo != null && CollectionUtils.isNotEmpty(ajbRecordListResponseVo.getRecordList())) {
            return null;//已经充值过艾积分
        }
        //根据活动代码从WCM查询活动信息
        AjbActivityResponseVo ajbActivityResponseVo = ajbProxy.queryAjbActivityByCode(UserConstants.AJB_COMPLETE_CODE);
        AjbBillNoDto ajbBillNo = null;
        if (ajbActivityResponseVo != null) {
            ajbBillNo = ajbProxy.ajbActivityRecharge(user.getId(), ajbActivityResponseVo.getAmount(), ajbActivityResponseVo.getRemark(), Integer.parseInt(UserConstants.AJB_COMPLETE_CODE));
        } else {
            ajbBillNo = ajbProxy.ajbActivityRecharge(user.getId(), UserConstants.CHARGE_AMOUNT, UserConstants.AJB_COMPLETE_REMARK, Integer.parseInt(UserConstants.AJB_COMPLETE_CODE));
        }
        if (ajbBillNo == null) {
            return null;
        }
        //新增充值记录
        ajbProxy.addAjbRecord(user.getId(), ajbBillNo.getOrderNum(), UserConstants.AJB_COMPLETE_CODE);

        AjbBillNoResponseVo response = new AjbBillNoResponseVo();
        response.setOrderNum(ajbBillNo.getOrderNum());
        return response;
    }

    @Override
    public UserIsRegisterResponseVo judgeUserIsRegister(LoginRequestVo request) {
        if (request == null || StringUtils.isBlank(request.getMobile())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        UserDto userDto = userProxy.getUserByMobile(request.getMobile());
        UserIsRegisterResponseVo response = new UserIsRegisterResponseVo();
        if (userDto == null || userDto.getId() == null) {
            response.setRegisterResult(false);
            response.setDesc(MessageConstant.USER_NOT_EXISTS);
            return response;
        }

        response.setRegisterResult(true);
        response.setDesc(MessageConstant.USER_REGISTERED);
        return response;
    }

    @Override
    public IdCardCertifincationResponseVo setUserIdCard(UserIdCardRequestVo request) {
        checkParamsForSetUserIdCard(request);

        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        //实名认证
        UserRealNameCertifincationDto certifincationParams = new UserRealNameCertifincationDto();
        certifincationParams.setUserId(userDto.getId());
        certifincationParams.setRealName(request.getRealName());
        certifincationParams.setIdCard(request.getIdCardNum());
        CertificationResultResponseVo resultResponseVo = userProxy.realNameCertifincation(certifincationParams);
        if (resultResponseVo == null || resultResponseVo.getCode() == null) {
            throw new BusinessException("实名认证失败");
        }

        CertificationResultEnum resultEnum = CertificationResultEnum.getValue(resultResponseVo.getCode());
        if (CertificationResultEnum.SUCCESS != resultEnum) {
            throw new BusinessException(resultEnum.getResult(), resultEnum.getValue());
        }
        //实名认证成功  返回认证信息
        IdCardCertifincationResponseVo certifincationResponse = this.queryCertifincationResult(userDto.getId());
        return certifincationResponse;
    }

    private void checkParamsForSetUserIdCard(UserIdCardRequestVo request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (StringUtils.isBlank(request.getRealName())) {
            throw new BusinessException("真实姓名不能为空");
        }
        if (StringUtils.isBlank(request.getRealName())) {
            throw new BusinessException("身份证号码不能为空");
        }

        //校验身份证号码合法性
        boolean isIdCardFlag = IdCardUtil.judgeIdCard(request.getIdCardNum());
        if (!isIdCardFlag) {
            throw new BusinessException("身份证号码不合法，请输入正确的身份证号码");
        }
    }

    @Override
    public String loginInWeChatH5(HttpServletRequest req, ValidateLoginSmsRequestVo request) {
        checkParamsForLoginInWeChatH5(req, request);

        SmsLoginUserParamVo loginUser = new SmsLoginUserParamVo();
        loginUser.setMobile(request.getMobile());
        String loginIp = IpUtils.getIpAddr(req);
        loginUser.setLoginIp(loginIp);
        loginUser.setDeviceId(request.getDeviceToken());
        loginUser.setOsType(request.getOsType());
        loginUser.setSmsCode(request.getSms());
        loginUser.setSource(100);
        LoginResultVo loginResultDto = userProxy.loginBySmsCode(loginUser);
        Integer resultCode = 0;
        if (loginResultDto != null && loginResultDto.getCode() != null) {
            resultCode = loginResultDto.getCode();
        }
        if (loginResultDto == null || StringUtils.isBlank(loginResultDto.getToken())) {
            throw new BusinessException(HttpResponseCode.SMS_CODE_ERROR, getLoginFailMsgByCode(resultCode));
        }

        return loginResultDto.getToken();
    }

    private void checkParamsForLoginInWeChatH5(HttpServletRequest req, ValidateLoginSmsRequestVo request) {
        if (null == req || request == null || StringUtils.isBlank(request.getMobile()) || StringUtils.isBlank(request.getSms())) {
            throw new BusinessException(HttpResponseCode.SERVICE_BUSY, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!(req.getHeader("user-agent").contains("MicroMessenger") ||
                req.getHeader("user-agent").equals("micromessenger"))) {
            throw new BusinessException(-3, "非微信环境");
        }
    }


    @Override
    public UserDo queryUserInfo(long userId) {
        return userDao.queryUser(userId);
    }

    @Override
    public UserDo queryUserInfoByMobile(String mobile) {
        return userDao.queryUserInfoByMobile(mobile);
    }

    @Override
    public UserDo queryUserByNickName(String nickName) {
        return userDao.queryUserByNickName(nickName);
    }

    @Override
    public LogDo validateSms(String mobile, String registerKey, String activateCode, HttpBaseRequest request) {
        // check from data base, whether mobile, registerKey, activateCode are match
        RegisterDo register = userDao.queryRegistration(mobile, registerKey, activateCode);
        if (register != null) {
            // move data to user table
            UserDo user = new UserDo();
            user.setPassword(register.getPassword());
            /**
             * add statistic to user table
             */
            user.setDeviceToken(request.getDeviceToken());
            user.setLocation(request.getLocation());
            user.setOsType(request.getOsType());
            user.setpValue(request.getParterValue());

            user.setMobile(mobile);
            //用户关系绑定 start
            UserRelationDo userRelation = queryInvitedUserByRegMobile(mobile);
            //如果1.有升级关系2.没有置空
            if (userRelation != null) {
                user.setRecMobile(userRelation.getInvitemobile());//添加推荐用户的号码

                UserRelationDo condition = new UserRelationDo();//1.降级用户关系
                condition.setInvitedmobile(mobile);
                condition.setStatus(3);
                userDao.updateUserRelation(condition);//降级的所有的
//            	userRelation.setId(condition.getId());
                userRelation.setStatus(2);
                userDao.updateUserRelation(userRelation);//2.升级绑定关系  升级特定的
            }
            //用户关系绑定 end
            userDao.addUser(user);
            // delete
            userDao.deleteRegister(register.getrId());
            // make user login
            LogDo log = auth(mobile, user.getPassword());

            //update t_customer;
            bindSalesCustomer(mobile);
            return log;
        }
        return null;
    }


    @Override
    public boolean retrieveValidate(String mobile, String registerKey, String activateCode) {
        RegisterDo register = userDao.queryRegistration(mobile, registerKey, activateCode);
        return register != null;
    }

    @Override
    public boolean resendSms(String mobile, String registerKey) {
        RegisterDo register = userDao.queryRegistration(mobile, registerKey, null);
        if (register != null) {
            String activateCode = SecurityUtil.generateActivateCode();
            userDao.updateRegActivateCode(mobile, registerKey, activateCode);
            smsService.sendSms(activateCode, mobile);
            return true;
        }
        return false;
    }

    @Override
    public String retrievePassword(String mobile) throws UserNotExistException {
        UserDo user = userDao.queryUserByMobilePassword(mobile, null);
        if (user == null) {
            throw new UserNotExistException();
        } else {
            String activateCode = SecurityUtil.generateActivateCode();
            String registerKey = SecurityUtil.encodeByMD5(String.valueOf(System.currentTimeMillis()));
            userDao.addRegistration(mobile, null, activateCode, registerKey);
            // 4. call sms service, here, maybe should use some job queue, change it in the future
            smsService.sendSms(activateCode, mobile);
            return registerKey;
        }
    }


    @Override
    public LogDo resetPassword(String mobile, String registerKey, String password) throws UserNotExistException {
        RegisterDo register = userDao.queryRegistration(mobile, registerKey, null);
        if (register == null) {
            throw new UserNotExistException();
        }
        // update password
        userDao.updatePassword(mobile, password);
        // delete register entry
        userDao.deleteRegister(register.getrId());
        // make user login
        return auth(mobile, password);
    }

    @Override
    public LogDo isLoggedIn(String accessToken) {
        if (!StringUtil.isNullOrEmpty(accessToken)) {
            return userDao.isLoggedIn(accessToken);
        }
        return null;
    }

    @Override
    public int createUserRelation(UserRelationDo userRelation) {
        return userDao.createUserRelation(userRelation);
    }

    @Override
    public int queryUserRelation(UserRelationDo userRelation) {
        List<UserRelationDo> userRelations = userDao.queryUserRelation(userRelation);
        int count = 0;
        if (userRelations != null && !userRelations.isEmpty())
            count = userRelations.size();
        return count;
    }

    @Override
    public int updateUserRelation(UserRelationDo userRelation) {
        return userDao.updateUserRelation(userRelation);
    }

    @Override
    public List<WalletDo> queryMyWallet(Map<String, Object> map) {
        //拼装
        List<WalletDo> myWallets = userDao.queryMyWallet(map);
        for (int i = 0; i < myWallets.size(); i++) {
            myWallets.get(i).setOrderTimeStr(myWallets.get(i).getOrderTime().toString());
        }
        return myWallets;
    }

    @Override
    public List<TUserRelationResponse> queryMyInvitedUsers(
            Map<String, Object> map) {
        List<UserRelationInfoDo> userRelationsDo = userDao.queryMyInvitedUsers(map);

        List<TUserRelationResponse> userRelations = ModelMapperUtil.strictMapList(userRelationsDo, TUserRelationResponse.class);
        if (userRelations != null && userRelations.size() > 0)
            for (int i = 0, l = userRelations.size(); i < l; i++) {
                Timestamp regTime = userRelations.get(i).getRegTime();
                String uImg = userRelations.get(i).getuImg();
                if (uImg == null || uImg.equals("")) {
                    userRelations.get(i).setuImg(StaticResourceConstants.USER_AVATAR);
                }
                if (regTime == null) {
                    userRelations.get(i).setRegTime(userRelations.get(i).getCreateTime());
                }
            }

        return userRelations;
    }

    @Override
    public Double queryMyWalletSum(Map<String, Object> map) {
        Double sum = userDao.queryMyWalletSum(map);
        return sum == null ? 0 : sum;
    }

    @Override
    public int queryMyInvitedUsersCount(Map<String, Object> map) {
        return userDao.queryMyInvitedUsersCount(map);
    }

    /**
     * 绑定用户的业务逻辑
     * 1.首先判断 邀请的用户是否是艾佳用户  不是则不绑定 是则有可能产生绑定关系
     * 2.再判断被邀请对象是否艾佳用户 如果是不产生绑定关系 否则进行绑定
     * 3.在绑定之前 判断他们是否产生绑定关系 如果绑定过了 则不进行绑定 否则进行进行绑定
     * <p/>
     * 代码优化 反向思考
     * 1.只有艾佳用户邀请非艾佳用户 才可以绑定
     * 2.基于上面的情况 绑定过以后 不允许再次绑定
     */
    @Override
    public Long bindingUser(UserRelationDo userRelation) {
        UserDo user = queryUserInfoByMobile(userRelation.getInvitedmobile());
        if (user != null) {//被邀请的用户已经是艾佳用户，直接跳转下载链接；
            return HttpResponseCode.INVITED_IS_AIJIA_USER;
        } else {
            user = queryUserInfoByMobile(userRelation.getInvitemobile());//邀请用户是否是艾佳用户
            if (user != null) {//邀请用户是艾家 用户 邀请用户与被邀请用户建立关键 绑定关系status=1
                UserRelationDo condition = new UserRelationDo();
                condition.setInvitedmobile(userRelation.getInvitedmobile());
                condition.setInvitemobile(userRelation.getInvitemobile());
//	        			 condition.setStatus(1);
                int isBingDing = queryUserRelation(condition);
                if (isBingDing == 0 || isBingDing == 1) {
                    condition = new UserRelationDo();
                    condition.setInvitedmobile(userRelation.getInvitedmobile());
                    int count = queryUserRelation(condition);
                    if (count > 0) {
                        condition.setStatus(3);
                        updateUserRelation(condition);
                    }
                    userRelation.setStatus(1);
                    userRelation.setCreateTime(new Timestamp(new Date().getTime()));
                    if (isBingDing == 0) {
                        createUserRelation(userRelation);
                    } else {
                        updateUserRelation(userRelation);
                    }
                    return HttpResponseCode.BINGDING_SUCCESS;
                } else {
                    return HttpResponseCode.REPEATER_BINGDING;
                }
            } else {
                return HttpResponseCode.INVITE_NOT_AIJIA_USER;
            }
        }
    }

    @Override
    public Long querMyWalletCount(String mobile) {
        return userDao.querMyWalletCount(mobile);
    }

    @Override
    public void unboundRelationShip() {
        userDao.unboundRelationShip();
    }

    @Override
    public boolean logoutByAccessToken(String accessToken) {
        return userDao.deleteLogByAccessToken(accessToken);
    }

    @Override
    public void addAccessLog(Long userId, Integer osType) {
        userDao.addAccessLog(userId, osType);
    }

    @Override
    public Integer saveUserVisitLog(UserVisitLogDo userVisitLog) {
        return userDao.saveUserVisitLog(userVisitLog);
    }

    @Override
    public UserDto getUserByToken(String token) {
        return userProxy.getUserByToken(token);
    }

}
