package com.ihomefnt.o2o.service.proxy.toc;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.toc.dto.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.proxy.toc.TocProxy;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Service
public class TocProxyImpl implements TocProxy {

    @Resource
    private ServiceCaller serviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(TocProxyImpl.class);

    /**
     * 获取邀请码
     * @param userId
     * @return
     */
    @Override
    public InviteCodeResponse getInviteCode(Integer userId) {
        if (userId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("userId",userId);
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRegister params:{}",params);
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-commission.appAgentController.agent.personalAgentRegister", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-commission.appAgentController.agent.personalAgentRegister ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRegister times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        InviteCodeResponse result =new InviteCodeResponse();
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRegister responseVo :{}", JsonUtils.obj2json(responseVo));
        result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), InviteCodeResponse.class);
        return result;
    }

    /**
     * 查询老用户抽奖资格
     * @param params
     * @return
     */
    @Override
    public List<RemainTimeDto> getOldUserLuckyDraws(Map<String, Object> params) {
        if (params == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.queryOldUserPrizeRemain params:{}", JsonUtils.obj2json(params));
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-promotion.lottery.queryOldUserPrizeRemain", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-promotion.lottery.queryOldUserPrizeRemain ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-promotion.lottery.queryOldUserPrizeRemain times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        List<RemainTimeDto> result =new ArrayList <RemainTimeDto>();
        LOG.info("aladdin-promotion.lottery.queryOldUserPrizeRemain responseVo :{}", JsonUtils.obj2json(responseVo));
        NewRemainTimeDto newRemainTimeDto = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), NewRemainTimeDto.class);
        if (newRemainTimeDto!=null){
            result=newRemainTimeDto.getNewUserRemainTimes();
        }
        return result;
    }

    /**
     * 老用户抽奖
     * @param params
     * @return
     */
    @Override
    public OldUserDrawPrizeDto doOldUserLuckyDraw(Map<String, Object> params) {
        if (params == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.oldUserDrawPrize params:{}", JsonUtils.obj2json(params));
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-promotion.lottery.oldUserDrawPrize", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-promotion.lottery.oldUserDrawPrize ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-promotion.lottery.oldUserDrawPrize times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.oldUserDrawPrize responseVo :{}", JsonUtils.obj2json(responseVo));
        OldUserDrawPrizeDto oldLuckyDrawResponse = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
                OldUserDrawPrizeDto.class);
        return oldLuckyDrawResponse;
    }


    /**
     * 老用户邀请客户信息查询
     * @param userId
     * @return
     */
    public InviteCustomerResultDto getCustomerList(Integer userId){

        if (userId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("agentUserId",userId);
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentInviteUserList params:{}", params);
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-commission.appAgentController.agent.queryAgentInviteUserList", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-commission.appAgentController.agent.queryAgentInviteUserList ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentInviteUserList times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentInviteUserList responseVo :{}", JsonUtils.obj2json(responseVo));
        InviteCustomerResultDto inviteCustomerResultDto = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
                InviteCustomerResultDto.class);
        return inviteCustomerResultDto;
    }

    /**
     * 邀请码与新用户绑定接口
     * 1是否为老用户 2是否绑定过邀请码 3邀请码是否有效5次限制 4.是否参加抽奖及剩余次数
     * @param params
     * @return
     */
    @Override
    public LuckyDrawTimeResponse bindInviteCodeAndUser(Map <String, Object> params) {
        if (params == null) {
            return null;
        }
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRecommendUser params:{}", JsonUtils.obj2json(params));
        long t1 = System.currentTimeMillis();
        ResponseVo responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-commission.appAgentController.agent.personalAgentRecommendUser", params, ResponseVo.class);
        } catch (Exception e) {
            LOG.error("aladdin-commission.appAgentController.agent.personalAgentRecommendUser ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRecommendUser times :{}ms", (t2 - t1));
        if (responseVo == null || !responseVo.isSuccess()) {
            LuckyDrawTimeResponse luckyDrawTimeResponse = new LuckyDrawTimeResponse();
            luckyDrawTimeResponse.setMsg(responseVo != null ? getMsg(responseVo.getCode()) : MessageConstant.FAILED);
            luckyDrawTimeResponse.setQualified(false);
            luckyDrawTimeResponse.setTimes(0);
            return luckyDrawTimeResponse;
        }
        LOG.info("aladdin-commission.appAgentController.agent.personalAgentRecommendUser responseVo :{}", JsonUtils.obj2json(responseVo));
        RecommendUserResultDto recommendUserResultDto = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),
                RecommendUserResultDto.class);

        LuckyDrawTimeResponse luckyDrawTimeResponse = new LuckyDrawTimeResponse();
        LuckyDrawTimeDto luckyDrawTimeDto = getLuckDrawOrNotInformation(Integer.parseInt(params.get("userId").toString()));
        Integer residueDegree = luckyDrawTimeDto.getRemainTimes();
        luckyDrawTimeResponse.setMsg(getMsg(responseVo.getCode()));
        if(responseVo.getCode()==1){
            luckyDrawTimeResponse.setQualified(true);
            luckyDrawTimeResponse.setMobile(recommendUserResultDto.getMobile());
        }else if(residueDegree==null||residueDegree==0){//抽奖次数为0
            luckyDrawTimeResponse.setQualified(false);
            luckyDrawTimeResponse.setMsg(MessageConstant.NEW_USER_OLNY);
        }else if(responseVo.getCode()==4&&residueDegree==5){//已绑定邀请码，但没抽过奖
            luckyDrawTimeResponse.setQualified(true);
            luckyDrawTimeResponse.setMobile(recommendUserResultDto.getMobile());
        }else{
            luckyDrawTimeResponse.setQualified(false);
        }
        luckyDrawTimeResponse.setTimes(residueDegree);

        return luckyDrawTimeResponse;
    }

    private String getMsg(Integer code){
        String msg;
        switch (code){
            case 1:
                msg="提交成功！快下载APP去领取新人福利吧";
                break;
            case 2:
                msg="查询邀请码失败";
                break;
            case 3:
                msg="此邀请码不存在";//经纪人不存在
                break;
            case 4:
                msg="您已领取过新人福利，不能重复领取哦";//绑定过邀请码
                break;
            case 5:
                msg="您好友的邀请码今天绑定次数已超限";
                break;
            case 6:
                msg="未获取到用户信息";
                break;
            default:
                msg="失败";
        }
        return msg;
    }

    /**
     * 查询是否参加过新人抽奖
     * @param userId
     * @return
     */
    @Override
    public LuckyDrawTimeDto getLuckDrawOrNotInformation(Integer userId) {

        LOG.info("aladdin-promotion.lottery.queryNewUserPrizeRemain params:{}", userId);
        long t1 = System.currentTimeMillis();
        ResponseVo<LuckyDrawTimeDto> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-promotion.lottery.queryNewUserPrizeRemain", userId, new TypeReference<ResponseVo<LuckyDrawTimeDto>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-promotion.lottery.queryNewUserPrizeRemain ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-promotion.lottery.queryNewUserPrizeRemain times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.queryNewUserPrizeRemain responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }

    /**
     * 调用订单接口查询用户是新用户还是老用户
     * 在2018-11-30前交过钱的 --老用户
     * @param userId
     * @return
     */
    @Override
    public Integer judgeNewOrOldCustomer(Integer userId) {
        if (userId == null) {
            return null;
        }
        LOG.info("aladdin-order.masterOrder-app.judgeNewOrOldCustomer params:{}", userId);
        long t1 = System.currentTimeMillis();
        ResponseVo<Integer> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-order.masterOrder-app.judgeNewOrOldCustomer", userId, new TypeReference<ResponseVo<Integer>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-order.masterOrder-app.judgeNewOrOldCustomer ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-order.masterOrder-app.judgeNewOrOldCustomer times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        LOG.info("aladdin-order.masterOrder-app.judgeNewOrOldCustomer responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }

    /**
     * 根据邀请码查询用户id
     * @param inviteCode
     * @return
     */
    @Override
    public UserIdResultDto queryUserInviteCode(String inviteCode) {
        if (inviteCode == null||"".equals(inviteCode)) {
            return null;
        }
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("inviteCode",inviteCode);
        params.put("type",1);

        LOG.info("aladdin-commission.appAgentController.agent.queryUserInviteCode params:{}", params);
        long t1 = System.currentTimeMillis();
        ResponseVo<UserIdResultDto> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-commission.appAgentController.agent.queryUserInviteCode", params, new TypeReference<ResponseVo<UserIdResultDto>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-commission.appAgentController.agent.queryUserInviteCode ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-commission.appAgentController.agent.queryUserInviteCode times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        LOG.info("aladdin-commission.appAgentController.agent.queryUserInviteCode responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }

    /**
     * 查询新用户抽到的奖品列表
     * @param userId
     * @return
     */
    @Override
    public UserBenefitDetailsDto getPrizesInformation(Integer userId) {
        if (userId == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.queryUserPrizeDetail params:{}", userId);
        long t1 = System.currentTimeMillis();
        ResponseVo<UserBenefitDetailsDto> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-promotion.lottery.queryUserPrizeDetail",userId, new TypeReference<ResponseVo<UserBenefitDetailsDto>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-promotion.lottery.queryUserPrizeDetail ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-promotion.lottery.queryUserPrizeDetail times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.queryUserPrizeDetail responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }

    /**
     * 执行新用户抽奖
     * @param userId
     * @return
     */
    @Override
    public NewUserDrawPrizeDto doNewUserLuckyDraw(Integer userId) {
        if (userId == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.newUserDrawPrize params:{}", userId);
        long t1 = System.currentTimeMillis();
        ResponseVo<NewUserDrawPrizeDto> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-promotion.lottery.newUserDrawPrize", userId, new TypeReference<ResponseVo<NewUserDrawPrizeDto>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-promotion.lottery.newUserDrawPrize ERROR:{}", e.getMessage());
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-promotion.lottery.newUserDrawPrize times :{}ms", (t2 - t1));

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        LOG.info("aladdin-promotion.lottery.newUserDrawPrize responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }

    /**
     * 查询用户经纪人列表
     * @param userId
     * @return
     */
    @Override
    public List<AgentAndCustomerDto> queryAgentCustomerList(Integer userId) {
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentCustomerList params:{}", userId);
        long t1 = System.currentTimeMillis();
        ResponseVo<List<AgentAndCustomerDto>> responseVo = null;
        try {
            responseVo = serviceCaller
                    .post("aladdin-commission.appAgentController.agent.queryAgentCustomerList", Maps.newHashMap("userId",userId), new TypeReference<ResponseVo<List<AgentAndCustomerDto>>>(){});
        } catch (Exception e) {
            LOG.error("aladdin-commission.appAgentController.agent.queryAgentCustomerList ERROR:{}", e);
            return null;
        }
        long t2 = System.currentTimeMillis();
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentCustomerList times :{}ms", (t2 - t1));
        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        LOG.info("aladdin-commission.appAgentController.agent.queryAgentCustomerList responseVo :{}", JsonUtils.obj2json(responseVo));
        return responseVo.getData();
    }
}
