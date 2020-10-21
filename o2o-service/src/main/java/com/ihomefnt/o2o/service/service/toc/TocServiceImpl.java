package com.ihomefnt.o2o.service.service.toc;

import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCardDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.customer.dto.CommissionRuleDto;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.toc.dto.*;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.InviteResultResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.NewLuckyDrawResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.OldLuckyDrawResponse;
import com.ihomefnt.o2o.intf.domain.toc.vo.response.PrizeListResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.toc.NewOrOld;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.agent.AgentAladdinCommissionProxy;
import com.ihomefnt.o2o.intf.proxy.bankcard.BankCardProxy;
import com.ihomefnt.o2o.intf.proxy.toc.TocProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.toc.TocService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.collections.Lists;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TocServiceImpl implements TocService {

    private final static Logger logger = LoggerFactory.getLogger(TocServiceImpl.class);

    @Autowired
    private TocProxy tocProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    OrderService orderService;

    @Autowired
    private AgentAladdinCommissionProxy commissionProxy;

    @Autowired
    private BankCardProxy bankCardDao;

    private static final String inviteCodeMessage="该活动仅限个人经纪人参与，如有疑问请联系客服。";



    /**
     * 获取邀请码
     *
     * @param user
     * @return
     */
    @Override
    public InviteCodeResponse getInviteCode(HttpUserInfoRequest user) {
        Map<String, Object> stringObjectMap = concurrentQueryInviteInfo(user.getId());
        Boolean commissionRule = (Boolean) stringObjectMap.get(ConcurrentTaskEnum.CHECK_PERSONAL_AGENT.name());
        InviteCodeResponse inviteCode = (InviteCodeResponse) stringObjectMap.get(ConcurrentTaskEnum.GET_INVITE_CODE.name());;
        List<BankCardDto> bankCardDtoList = (List<BankCardDto>) stringObjectMap.get(ConcurrentTaskEnum.GET_BANK_CARD_DETAIL.name());
        if(CollectionUtils.isNotEmpty(bankCardDtoList) && StringUtils.isNotBlank(bankCardDtoList.get(0).getName())){
            inviteCode.setName(bankCardDtoList.get(0).getName());
        }
        if(StringUtils.isBlank(inviteCode.getName())){
            inviteCode.setName(user.getMobile());
        }
        if(!commissionRule){//不是个人经纪人
            if(inviteCode == null){
                inviteCode =  new InviteCodeResponse();
            }
            inviteCode.setIsPersonalAgent(false);
            inviteCode.setInviteCodeMessage(inviteCodeMessage);
        }
        return inviteCode;
    }


    /**
     * 签约动作后续查询
     *
     * @param id
     * @return
     */
    private Map<String, Object> concurrentQueryInviteInfo(Integer id) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return commissionProxy.checkPersonalAgent(id);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.CHECK_PERSONAL_AGENT.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return tocProxy.getInviteCode(id);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.GET_INVITE_CODE.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return  bankCardDao.getBankCardDetail(id);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.GET_BANK_CARD_DETAIL.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }



    /**
     * 老用户抽奖
     *
     * @param params
     * @return
     */
    @Override
    public OldLuckyDrawResponse doOldUserLuckyDraw(Map<String, Object> params) {
        OldUserDrawPrizeDto newUserDrawPrizeDto = tocProxy.doOldUserLuckyDraw(params);
        return newUserDrawPrizeDto == null ? null : new OldLuckyDrawResponse(newUserDrawPrizeDto.getRemainTimes(), newUserDrawPrizeDto.getPrizeAmount(),newUserDrawPrizeDto.getTotalAmount());
    }

    /**
     * 查询老用户累计邀请信息
     *
     * @param id
     * @return
     */
    @Override
    public InviteResultResponse getInviteInformation(Integer id) {

        InviteResultResponse inviteResultResponse = new InviteResultResponse();
        InviteCustomerResultDto customerResult = tocProxy.getCustomerList(id);
        List<InviteDto> inviteList = new ArrayList<InviteDto>();
        List<PaidDto> paidListSec = new ArrayList <PaidDto>();
        List<MoneyDto> moneyListRe = new ArrayList<MoneyDto>();
        if(customerResult==null){
            inviteResultResponse.setInviteList(inviteList);
            inviteResultResponse.setInviteTotal(inviteList.size());
            inviteResultResponse.setPaidList(paidListSec);
            inviteResultResponse.setPaidTotal(paidListSec.size());
            inviteResultResponse.setMoneyList(moneyListRe);
            inviteResultResponse.setMoneyTotal(moneyListRe.size());
            return inviteResultResponse;
        }
        List<CustomerBaseInfoDto> bindCustomerList = customerResult.getBindCustomerList();
        bindCustomerList.sort((o1,o2)->o1.getUserId()-o2.getUserId());
        List<Integer> userIdList = new ArrayList <Integer>();
        List<Integer> paidUserIdList = new ArrayList <Integer>();
        if(CollectionUtils.isEmpty(bindCustomerList)){
            inviteResultResponse.setInviteList(inviteList);
            inviteResultResponse.setInviteTotal(inviteList.size());
            inviteResultResponse.setPaidList(paidListSec);
            inviteResultResponse.setPaidTotal(paidListSec.size());
            inviteResultResponse.setMoneyList(moneyListRe);
            inviteResultResponse.setMoneyTotal(moneyListRe.size());
            return inviteResultResponse;
        }

        List<PaidDto> paidList = new ArrayList<PaidDto>();
        List<MoneyDto> moneyList = new ArrayList<MoneyDto>();
        for (int i = 0; i < bindCustomerList.size(); i++) {
            userIdList.add(bindCustomerList.get(i).getUserId());
        }

        List<UserDto> userList = userProxy.batchQueryUserInfo(userIdList);
        userList.sort((o1,o2)->o1.getId()-o2.getId());
        List<UserDto> paidUserList = new ArrayList <UserDto>();
        for (int i = 0; i < userList.size(); i++) {
            InviteDto inviteDto = new InviteDto();
            PaidDto paidDto = new PaidDto();
            inviteDto.setRegisterTime(bindCustomerList.get(i).getBindTimeStr());
            inviteDto.setMobileNum(bindCustomerList.get(i).getMobile());
            if(userList.get(i).getMember()!=null){
                inviteDto.setHeadImgUrl(userList.get(i).getMember().getPhotoImg());
            }
            inviteDto.setUserId(bindCustomerList.get(i).getUserId());
            if (StringUtils.isNotBlank(bindCustomerList.get(i).getDepositTimeStr())) {
                if(bindCustomerList.get(i).getState()==1){
                    inviteDto.setValid(true);
                }
                paidUserIdList.add(bindCustomerList.get(i).getUserId());
                paidUserList.add(userList.get(i));
                paidDto.setName(bindCustomerList.get(i).getCustomerName());
                paidDto.setUserId(bindCustomerList.get(i).getUserId());
                paidDto.setMobileNum(bindCustomerList.get(i).getMobile());
                paidDto.setState(bindCustomerList.get(i).getState());
                paidDto.setPaidTime(bindCustomerList.get(i).getDepositTimeStr());
                if(userList.get(i).getMember()!=null) {
                    paidDto.setHeadImgUrl(userList.get(i).getMember().getPhotoImg());
                }
                paidList.add(paidDto);
            }
            inviteList.add(inviteDto);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",id);
        params.put("inviterUserId",paidUserIdList);
        Map<String, Object> concurrentMap = concurrentQueryList(params);
        List<RemainTimeDto> remainTimeList = (List<RemainTimeDto>) concurrentMap.get(ConcurrentTaskEnum.QUERY_OLDUSER_LUCKYDRAWS.name());
        if(remainTimeList!=null&&remainTimeList.size()>0){
            Map <Integer, RemainTimeDto> collect = remainTimeList.stream().collect(Collectors.toMap(obj->obj.getNewUserId(), action -> action));
            for (int i = 0; i < paidList.size(); i++) {
                PaidDto paidDto= paidList.get(i);
                RemainTimeDto remainTimeDto =collect.get(paidDto.getUserId());
                if(remainTimeDto!=null){
                    paidDto.setTimes(remainTimeDto.getRemainTimes());
                }
                paidListSec.add(paidDto);
            }
        }
        if(paidListSec.size()==0&&paidList.size()!=0){//防止剩余次数接口返回null
            paidListSec=paidList;
        }
        Integer totalMoney = 0;
        //调用抽奖模块接口给moneyList塞值
        UserBenefitDetailsDto prizesInformation = (UserBenefitDetailsDto) concurrentMap.get(ConcurrentTaskEnum.QUERY_PRIZES_INFORMATION.name());
        if (prizesInformation != null && CollectionUtils.isNotEmpty(prizesInformation.getOldUserPrizeDetailDtoList())) {//取出老用户福利
            prizesInformation.getOldUserPrizeDetailDtoList().forEach(action -> moneyList.add(new MoneyDto(action.getUserId(), action.getReceiveTime(), action.getMoney(), action.getState(),action.getType())));
        }
        if(moneyList.size()!=0){
            Map <Integer, UserDto> collect = paidUserList.stream().collect(Collectors.toMap(obj->obj.getId(), action -> action));
            for(MoneyDto moneyDto:moneyList){
                UserDto userVo = collect.get(moneyDto.getUserId());
                if(userVo!=null){
                    if(userVo.getMember()!=null) {
                        moneyDto.setHeadImgUrl(userVo.getMember().getPhotoImg());
                    }
                    moneyDto.setMobileNum(userVo.getMobile());
                    moneyDto.setName(userVo.getUsername());
                    moneyDto.setMsg(getMsg(moneyDto.getState()));
                }
                moneyListRe.add(moneyDto);
                totalMoney = totalMoney +moneyDto.getMoney();
            }
        }

        inviteResultResponse.setInviteList(inviteList);
        inviteResultResponse.setInviteTotal(inviteList.size());
        inviteResultResponse.setPaidList(paidListSec);
        inviteResultResponse.setPaidTotal(paidListSec.size());
        inviteResultResponse.setMoneyList(moneyListRe);
        inviteResultResponse.setMoneyTotal(totalMoney);
        return inviteResultResponse;
    }

    /**
     * 查询抽奖机会和奖品列表
     * @param params
     * @return
     */
    private Map<String, Object> concurrentQueryList(Map<String,Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //查询抽奖机会列表
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return tocProxy.getOldUserLuckyDraws(params);
            }
            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_OLDUSER_LUCKYDRAWS.name();
            }
        });

        // 查询抽到的奖项
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return tocProxy.getPrizesInformation((Integer) params.get("userId"));
            }
            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PRIZES_INFORMATION.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 邀请码与新用户绑定接口
     *
     * @param params
     * @return
     */
    @Override
    public LuckyDrawTimeResponse bindInviteCodeAndUser(Map<String, Object> params) {
        return tocProxy.bindInviteCodeAndUser(params);
    }

    /**
     * 查询是否参加过新人抽奖
     *
     * @param userId
     * @return
     */
    @Override
    public LuckyDrawTimeResponse getLuckDrawOrNotInformation(Integer userId) {
        LuckyDrawTimeDto luckDrawOrNotInformation = tocProxy.getLuckDrawOrNotInformation(userId);
        boolean hasPrizeQuality = false;
        Integer remainTimes = 0;
        if(null != luckDrawOrNotInformation){
            remainTimes = luckDrawOrNotInformation.getRemainTimes();
            hasPrizeQuality =luckDrawOrNotInformation.getHasPrizeQuality();
        }
        return new LuckyDrawTimeResponse(hasPrizeQuality, remainTimes);
    }

    /**
     * 区分新老用户,新用户返回剩余抽奖次数,是否已绑定,经纪人手机号
     *
     * @param userId
     * @return
     */
    @Override
    public UserDistinguishDto queryCurrentUserIsOldOrNew(Integer userId) {
        Integer integer = tocProxy.judgeNewOrOldCustomer(userId);
        if (integer == null) {
            return null;
        }
        UserDistinguishDto userDistinguishDto = new UserDistinguishDto();
        userDistinguishDto.setOld(NewOrOld.OLD.getCode().equals(integer));//设置新老用户
        LuckyDrawTimeDto luckDrawOrNotInformation = tocProxy.getLuckDrawOrNotInformation(userId);//抽奖资格
        if(null != luckDrawOrNotInformation) {
            userDistinguishDto.setResidueNum(luckDrawOrNotInformation.getRemainTimes());//剩余新人抽奖次数
            if (luckDrawOrNotInformation.getRemainTimes() != null && luckDrawOrNotInformation.getRemainTimes() > 0) {//抽奖次数不为0，设置为新用户，用于展示新人福利入口
                userDistinguishDto.setOld(false);//设置为新用户
            }
        }else{
            userDistinguishDto.setResidueNum(0);

        }
        List<AgentAndCustomerDto> agentAndCustomers = tocProxy.queryAgentCustomerList(userId);//经纪人列表
        if (CollectionUtils.isNotEmpty(agentAndCustomers)) {
            for(AgentAndCustomerDto agentAndCustomerDto:agentAndCustomers){
                if (agentAndCustomerDto.getType()==1){//个人经纪人才算已绑定经纪人
                    userDistinguishDto.setBinding(true);//已经绑定经纪人
                    userDistinguishDto.setMobile(agentAndCustomerDto.getAgentMobile());//经纪人手机号
                }
            }
        }
        return userDistinguishDto;
    }

    /**
     * 查询新用户抽到的奖品列表
     *
     * @param userId
     * @return
     */
    @Override
    public PrizeListResponse getPrizesInformation(Integer userId) {
        UserBenefitDetailsDto prizesInformation = tocProxy.getPrizesInformation(userId);
        PrizeListResponse prizeListResponse = new PrizeListResponse();
        prizeListResponse.setPrizeDtoList(Lists.newArrayList());
        if (prizesInformation != null && CollectionUtils.isNotEmpty(prizesInformation.getNewUserPrizeDetailDtoList())) {
            prizesInformation.getNewUserPrizeDetailDtoList().forEach(action -> prizeListResponse.getPrizeDtoList().add(new PrizeDto(action.getPrizeInfoId(), action.getReceiveTime(), action.getState(), action.getRuleDesc(), action.getPrizeName(),action.getType())));
        }
        return prizeListResponse;
    }

    /**
     * 新用户抽奖,返回抽到的奖品
     *
     * @param userId
     * @return
     */
    @Override
    public NewLuckyDrawResponse doNewUserLuckyDraw(Integer userId) {
        NewUserDrawPrizeDto newUserDrawPrizeDto = tocProxy.doNewUserLuckyDraw(userId);
        return newUserDrawPrizeDto == null ? null : new NewLuckyDrawResponse(newUserDrawPrizeDto.getRemainTimes(), newUserDrawPrizeDto.getPrizeInfoId(), newUserDrawPrizeDto.getPrizeType(), newUserDrawPrizeDto.getPrizeName(), newUserDrawPrizeDto.getRuleDesc());
    }

    /**
     * 判断当前用户是否是有邀请码的用户
     *
     * @param userId
     * @return
     */
    @Override
    public boolean queryCurrentUserIsUserWithInvitationCode(Integer userId) {
        List<AgentAndCustomerDto> list= tocProxy.queryAgentCustomerList(userId);
        logger.info("TocController queryCurrentUserIsUserWithInvitationCode.list :{}", JsonUtils.obj2json(list));
        if(CollectionUtils.isNotEmpty(list)){
            for (AgentAndCustomerDto agentAndCustomerDto:list) {
                logger.info("TocController agentAndCustomerDto.type :{}", agentAndCustomerDto.getType());
                if(agentAndCustomerDto.getType()==1){
                    logger.info("TocController agentAndCustomerDto.type result :true");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前用户是否是有邀请码的新用户
     *
     * @param userId
     * @return
     */
    @Override
    public boolean queryCurrentUserIsNewUserWithInvitationCode(Integer userId) {
        Integer code = tocProxy.judgeNewOrOldCustomer(userId);
        if (NewOrOld.NEW.getCode().equals(code)) {
            boolean a= queryCurrentUserIsUserWithInvitationCode(userId);
            logger.info("TocController queryCurrentUserIsNewUserWithInvitationCode :{}",a);
            return a;
        }
        return false;
    }
    /**
     * 判断当前用户是不是老用户
     *
     * @param userId
     * @return
     */
    @Override
    public boolean judgeNewOrOldCustomer(Integer userId) {
        Integer code = tocProxy.judgeNewOrOldCustomer(userId);
        if (NewOrOld.NEW.getCode().equals(code)) {
            return false;
        }
        return true;
    }

    /**
     * 根据邀请码取用户id
     * @param inviteCode
     * @return
     */
    @Override
    public UserIdResultDto queryUserInviteCode(String inviteCode){
        return tocProxy.queryUserInviteCode(inviteCode);
    }

    /**
     * 判断当前用户是否有有效订单
     * @param userId
     * @return
     */
    @Override
    public HasOrderDto getHasOrderOrNot(Integer userId) {
        HasOrderDto hasOrderDto = new HasOrderDto();
        boolean hasOrder=false;
        List<OrderDtoVo> orderDtoList =orderService.queryAlladdinOrderList(userId);
        if (CollectionUtils.isNotEmpty(orderDtoList) ) {
            for(OrderDtoVo orderDtoVo:orderDtoList){
                if(orderDtoVo.getOrderType()!=null && orderDtoVo.getOrderType()>=17){
                    continue;
                }
                Integer[] array = {2 ,3,5 ,6,7,8,10,14,15,16,17 };//交了钱的状态
                int id = Arrays.binarySearch(array,orderDtoVo.getState());
                if(id>-1)
                {
                    hasOrder=true;
                    break;
                }
            }
        }
        hasOrderDto.setHasOrder(hasOrder);
//        checkPersonalAgent(userId,hasOrderDto);
        return hasOrderDto;
    }

    /**
     * 校验是否个人经纪人
     * @param userId
     * @param hasOrderDto
     */
    public void checkPersonalAgent(Integer userId,HasOrderDto hasOrderDto){
        try {
            Boolean commissionRule = commissionProxy.checkPersonalAgent(userId);
            if(!commissionRule){//不是个人经纪人
                hasOrderDto.setHasOrder(false);
            }
        }catch (Exception e){
            logger.error("checkPersonalAgent error",e);
        }
    }

    public String getMsg(Integer state){
        String msg;
        switch (state){
            case 0:
                msg="您邀请的好友成功交定金";
                break;
            case 1:
                msg="您邀请的好友成功交定金";
                break;
            default:
                msg="您邀请的好友退定金";
                break;

        }
        return msg;

    }


}
