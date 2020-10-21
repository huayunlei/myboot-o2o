package com.ihomefnt.o2o.service.service.program;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.program.dto.OrderUnConfirmOpinionDto;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionDetailDto;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionPageQueryResponseDto;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionSaveDto;
import com.ihomefnt.o2o.intf.domain.program.vo.request.ProgramOpinionPageQueryReq;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionDetailResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionRecordInfo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserOrderProgramListResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.QueryMasterOrderIdByHouseIdResultDto;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionAffirmByOperationItemReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionAffirmByOperationListReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;
import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderDetailDto;
import com.ihomefnt.o2o.intf.domain.user.dto.AppMasterOrderResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.home.HouseProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProgramOpinionProxy;
import com.ihomefnt.o2o.intf.proxy.sms.SmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.program.ProgramOpinionService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author liyonggang
 * @create 2019-08-08 15:42
 */
@Service
public class ProgramOpinionServiceImpl implements ProgramOpinionService {

    public final static Logger LOG = LoggerFactory.getLogger(ProgramOpinionServiceImpl.class);

    @Autowired
    private ProgramOpinionProxy programOpinionProxy;

    @Autowired
    private HouseProxy houseProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private PersonalCenterProxy personalCenterProxy;

    @Autowired
    SmsProxy smsProxy;

    /**
     * 订单方案列表
     *
     * @param userId
     * @return
     */
    @Override
    public UserOrderProgramListResponse queryUserProgramOpinionOrderList(Integer userId) {
        List<AppHousePropertyResultDto> housePropertyResultDtoList = houseProxy.queryHouseByUserId(userId);
        if (CollectionUtils.isNotEmpty(housePropertyResultDtoList)) {
            List<QueryMasterOrderIdByHouseIdResultDto> queryMasterOrderIdByHouseIdResultDtos = productProgramOrderProxy.queryMasterOrderIdsByHouseIds(housePropertyResultDtoList.stream().map(appHousePropertyResultDto -> appHousePropertyResultDto.getHousePropertyInfoResultDto().getCustomerHouseId()).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(queryMasterOrderIdByHouseIdResultDtos)) {
                List<Integer> orderIdList = queryMasterOrderIdByHouseIdResultDtos.stream().map(queryMasterOrderIdByHouseIdResultDto -> queryMasterOrderIdByHouseIdResultDto.getMasterOrderNum()).collect(Collectors.toList());
                List<HousePropertyInfoResultDto> housePropertyInfoResultDtoList = housePropertyResultDtoList.stream().map(AppHousePropertyResultDto::getHousePropertyInfoResultDto).collect(Collectors.toList());
                Map<Integer, HousePropertyInfoResultDto> houseOrderMap = Maps.newHashMap();

                for (QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto : queryMasterOrderIdByHouseIdResultDtos) {
                    for (HousePropertyInfoResultDto housePropertyInfoResultDto : housePropertyInfoResultDtoList) {
                        if (queryMasterOrderIdByHouseIdResultDto.getHouseId().equals(housePropertyInfoResultDto.getCustomerHouseId().longValue())) {
                            houseOrderMap.put(queryMasterOrderIdByHouseIdResultDto.getMasterOrderNum(), housePropertyInfoResultDto);
                            break;
                        }
                    }
                }
                List<UserOrderProgramListResponse.ProgramOrderInfo> programOrderInfos = programOpinionProxy.queryProgramOrderInfoList(orderIdList);
                List<OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList> orderUnConfirmOpinionList = programOpinionProxy.queryUnConfirmOpinionCount(orderIdList);
                for (UserOrderProgramListResponse.ProgramOrderInfo programOrderInfo : programOrderInfos) {
                    HousePropertyInfoResultDto housePropertyInfoResultDto = houseOrderMap.get(programOrderInfo.getOrderNum());
                    if (housePropertyInfoResultDto != null) {
                        programOrderInfo.setBuildingName(housePropertyInfoResultDto.getBuildingName());
                        programOrderInfo.setHouseLayoutName(Lists.newArrayList(housePropertyInfoResultDto.getHousingNum(), housePropertyInfoResultDto.getUnitNum(), housePropertyInfoResultDto.getRoomNum()).stream().filter(s -> StringUtils.isNotBlank(s)).collect(Collectors.joining("-")));
                        if (CollectionUtils.isNotEmpty(programOrderInfo.getSolutionInfoList())) {
                            for (UserOrderProgramListResponse.ProgramOrderInfo.SolutionInfo solutionInfo : programOrderInfo.getSolutionInfoList()) {
                                if (CollectionUtils.isNotEmpty(orderUnConfirmOpinionList)) {
                                    for (OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList orderUnConfirmOpinion : orderUnConfirmOpinionList) {
                                        if (orderUnConfirmOpinion.getOrderNum().equals(programOrderInfo.getOrderNum())) {
                                            for (OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList.UnConfirmOptionList unConfirmOptionList : orderUnConfirmOpinion.getUnConfirmOptionList()) {
                                                if (unConfirmOptionList.getSolutionId().equals(solutionInfo.getSolutionId())) {
                                                    solutionInfo.setReviseOpinionNum(unConfirmOptionList.getCount() + solutionInfo.getReviseOpinionNum());
                                                }
                                            }
                                        }
                                    }
                                }
                                solutionInfo.setSolutionImage(AliImageUtil.imageCompress(solutionInfo.getSolutionImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                            }
                        }
                    }
                }
                int solutionCount = 0;
                if (CollectionUtils.isNotEmpty(programOrderInfos)) {
                    programOrderInfos.removeIf(programOrderInfo -> CollectionUtils.isEmpty(programOrderInfo.getSolutionInfoList()));
                    for (UserOrderProgramListResponse.ProgramOrderInfo programOrderInfo : programOrderInfos) {
                        solutionCount = solutionCount + programOrderInfo.getSolutionInfoList().size();
                    }
                }
                return new UserOrderProgramListResponse().setHouseCount(programOrderInfos.size()).setSolutionCount(solutionCount).setOrderList(programOrderInfos);
            }
        }
        return null;
    }

    /**
     * 意见记录列表
     *
     * @param request
     * @return
     */
    @Override
    public ProgramOpinionResponse queryProgramOpinionList(ProgramOpinionRequest request) {
        boolean addDraftRecord = request.getPageNo() != null && request.getPageNo().equals(1);
        ProgramOpinionResponse programOpinionResponse = programOpinionProxy.queryProgramOpinionListForPage(request);
        if (programOpinionResponse != null && CollectionUtils.isNotEmpty(programOpinionResponse.getRecords())) {
            for (ProgramOpinionRecordInfo record : programOpinionResponse.getRecords()) {
                if (record.getStatus().equals(1) || record.getStatus().equals(2)) {
                    record.setProgramOpinionId(record.getId().toString());
                } else {
                    record.setReviseOpinionId(record.getId());
                }
            }
            programOpinionResponse.setTotalRecordCount(CollectionUtils.isNotEmpty(programOpinionResponse.getRecords()) ? programOpinionResponse.getRecords().size() : 0);
        }
        return programOpinionResponse;
    }

    /**
     * 新增或更新
     *
     * @param request
     * @return
     */
    @Override
    public String addOrUpdateProgramOpinionDraft(ProgramOpinionDetailDto request) {
        return programOpinionProxy.addOrUpdateProgramOpinionDraft(request);
    }


    /**
     * 方案意见详情
     *
     * @param request
     * @return
     */
    @Override
    public ProgramOpinionDetailDto queryProgramOpinionDetail(ProgramOpinionRequest request) {
        ProgramOpinionDetailDto programOpinionDetailDto = null;
        if ((request.getReviseOpinionId() == null || request.getReviseOpinionId().equals(0)) && StringUtils.isBlank(request.getProgramOpinionId())) {
            List<ProgramOpinionDetailDto> programOpinionDetailList = programOpinionProxy.queryUnConfirmOpinionList(request);
            if (CollectionUtils.isNotEmpty(programOpinionDetailList)) {
                programOpinionDetailDto = programOpinionDetailList.get(0);
            }
        }
        if (programOpinionDetailDto == null) {
            programOpinionDetailDto = programOpinionProxy.queryProgramOpinionDetailForDolly(request.getId(), request.getSolutionId());
        }
        if (programOpinionDetailDto == null) {
            return null;
        }
        request.setOrderNum(programOpinionDetailDto.getOrderNum() == null ? request.getOrderNum() : programOpinionDetailDto.getOrderNum());
        if (request.getReviseOpinionId() == null && request.getProgramOpinionId() == null) {
            programOpinionDetailDto.setStatus(1);
        }
        List<UserOrderProgramListResponse.ProgramOrderInfo> programOrderInfos = programOpinionProxy.queryProgramOrderInfoList(Lists.newArrayList(request.getOrderNum()));
        programOpinionDetailDto.setOrderNum(request.getOrderNum());
        programOpinionDetailDto.setUserId(request.getUserId());
        programOpinionDetailDto.setSolutionId(request.getSolutionId());
        programOpinionDetailDto.setId(request.getId());
        if (request.getOrderNum() != null) {
            AppMasterOrderDetailDto appMasterOrderDetailDto = personalCenterProxy.queryMasterOrderDetail(request.getOrderNum());
            if (appMasterOrderDetailDto != null && appMasterOrderDetailDto.getCustomerInfo() != null) {
                programOpinionDetailDto.setMobile(appMasterOrderDetailDto.getCustomerInfo().getUserMobile());
            }
        }
        if (programOpinionDetailDto != null) {
            if (CollectionUtils.isNotEmpty(programOrderInfos)) {
                UserOrderProgramListResponse.ProgramOrderInfo programOrderInfo = programOrderInfos.get(0);
                if (CollectionUtils.isNotEmpty(programOrderInfo.getSolutionInfoList())) {
                    for (UserOrderProgramListResponse.ProgramOrderInfo.SolutionInfo solutionInfo : programOrderInfo.getSolutionInfoList()) {
                        if (request.getSolutionId().equals(solutionInfo.getSolutionId())) {
                            programOpinionDetailDto.setSolutionImage(AliImageUtil.imageCompress(solutionInfo.getSolutionImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                            programOpinionDetailDto.setSolutionName(solutionInfo.getSolutionName());
                            programOpinionDetailDto.setSolutionStyleName(solutionInfo.getSolutionStyleName());
                            programOpinionDetailDto.setSolutionTypeName(solutionInfo.getSolutionTypeName());
                            break;
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(programOpinionDetailDto.getReviseOpinionList())) {
            for (ProgramOpinionDetailDto.ReviseOpinionList reviseOpinionList : programOpinionDetailDto.getReviseOpinionList()) {
                if (CollectionUtils.isNotEmpty(reviseOpinionList.getHardTagList())) {
                    reviseOpinionList.getSoftTagList().addAll(reviseOpinionList.getHardTagList().stream().filter(tagInfo -> tagInfo.getTagId().equals(8)).collect(Collectors.toList()));
                    reviseOpinionList.getHardTagList().removeIf(tagInfo -> tagInfo.getTagId().equals(8));
                }
            }
        }
        return programOpinionDetailDto;
    }

    /**
     * 提交方案意见
     *
     * @param request
     * @return
     */
    @Override
    public ProgramOpinionRequest submitProgramOpinion(ProgramOpinionDetailDto request) {
        request.setId(request.getProgramOpinionId());
        return programOpinionProxy.updateOpinionSendStatus(request);
    }

    /**
     * 用户确认
     *
     * @param request
     * @return
     */
    @Override
    public void affirmProgramOpinion(ProgramOpinionRequest request) {
        CheckSmsCodeParamVo checkSmsCodeParamVo = new CheckSmsCodeParamVo();
        checkSmsCodeParamVo.setMobile(request.getMobile());
        checkSmsCodeParamVo.setSmsCode(request.getAuthCode());
        checkSmsCodeParamVo.setType(2);
        boolean result = smsProxy.checkSmsCode(checkSmsCodeParamVo);
        if (!result) {
            throw new BusinessException(HttpReturnCode.FAILED_VERIFY_FOR_MSG_CODE, MessageConstant.CODE_ERROR);
        }

        commonAffirmffirmProgramOpinion(request, true);
    }

    @Override
    public void affirmProgramOpinionByOperation(ProgramOpinionAffirmByOperationListReq request) {
        List<ProgramOpinionAffirmByOperationItemReq> items = request.getItems();

        List<ProgramOpinionRequest> programOpinionList = new ArrayList<>(items.size());
        items.forEach(item -> {
            ProgramOpinionRequest programOpinion = new ProgramOpinionRequest();
            programOpinion.setOrderNum(item.getOrderNum())
                    .setProgramOpinionId(item.getProgramOpinionId())
                    .setMobile(item.getMobile());
            programOpinionList.add(programOpinion);

        });
        programOpinionList.parallelStream().forEach(programOpinion -> commonAffirmffirmProgramOpinion(programOpinion, false));
    }

    class AffirmProgramOpinionTask implements Runnable {

        private List<ProgramOpinionRequest> programOpinionList;
        private CountDownLatch countDownLatch;

        AffirmProgramOpinionTask(List<ProgramOpinionRequest> programOpinionList, CountDownLatch countDownLatch) {
            this.programOpinionList = programOpinionList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                programOpinionList.forEach(programOpinion -> {
                    commonAffirmffirmProgramOpinion(programOpinion, false);
                });
            } catch (Exception e) {
                LOG.error("commonAffirmffirmProgramOpinion Exception ", e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    private static final ExecutorService threadPool = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(30, 100, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200)));


    private void commonAffirmffirmProgramOpinion(ProgramOpinionRequest request, boolean isUserAffirm) {
        boolean canAffirm = false;
        UserDto userByMobile = userProxy.getUserByMobile(request.getMobile());
        List<AppMasterOrderResultDto> appMasterOrderResultDtos = personalCenterProxy.queryMasterOrderListByUserId(userByMobile.getId());
        List<Integer> orderIdList = appMasterOrderResultDtos.stream().map(dto -> dto.getMasterOrderId()).collect(Collectors.toList());
        List<UserOrderProgramListResponse.ProgramOrderInfo> programOrderInfos = programOpinionProxy.queryProgramOrderInfoList(orderIdList);
        ProgramOpinionDetailDto programOpinionDetailDto1 = programOpinionProxy.queryProgramOpinionDetailForDolly(request.getProgramOpinionId(), request.getSolutionId());
        if (programOpinionDetailDto1 != null) {
            request.setOrderNum(programOpinionDetailDto1.getOrderId());
        }
        List<ProgramOpinionDetailDto> programOpinionDetailList = programOpinionProxy.queryUnConfirmOpinionList(request);
        if (CollectionUtils.isNotEmpty(programOrderInfos) && CollectionUtils.isNotEmpty(programOpinionDetailList)) {
            ProgramOpinionDetailDto programOpinionDetailDto = programOpinionDetailList.get(0);
            for (UserOrderProgramListResponse.ProgramOrderInfo programOrderInfo : programOrderInfos) {
                if (programOpinionDetailDto.getOrderNum().equals(programOrderInfo.getOrderNum())) {
                    for (UserOrderProgramListResponse.ProgramOrderInfo.SolutionInfo solutionInfo : programOrderInfo.getSolutionInfoList()) {
                        if (solutionInfo.getSolutionId().equals(programOpinionDetailDto.getSolutionId())) {
                            canAffirm = true;
                            break;
                        }
                    }
                }
                if (canAffirm) {
                    break;
                }
            }
        }
        if (!canAffirm) {
            throw new BusinessException(HttpResponseCode.PROGRAM_OPINION_ERROR, MessageConstant.NO_MATCH_SOLUTION);
        }
        //wcm 查询
        if (CollectionUtils.isNotEmpty(programOpinionDetailList)) {
            ProgramOpinionDetailDto programOpinionDetailDto = programOpinionDetailList.get(0);
            //提交数据到dolly
            ProgramOpinionSaveDto programOpinionSaveDto = new ProgramOpinionSaveDto();
            programOpinionSaveDto.setId(programOpinionDetailDto.getProgramOpinionId());
            programOpinionSaveDto.setOrderNum(programOpinionDetailDto.getOrderNum());
            programOpinionSaveDto.setSolutionId(programOpinionDetailDto.getSolutionId());
            programOpinionSaveDto.setUserId(programOpinionDetailDto.getUserId());
            programOpinionSaveDto.setReviseOpinionList(new ArrayList<>());
            programOpinionSaveDto.setAddTime(programOpinionDetailDto.getAddTime());
            for (ProgramOpinionDetailDto.ReviseOpinionList reviseOpinionList : programOpinionDetailDto.getReviseOpinionList()) {
                ProgramOpinionSaveDto.ReviseOpinionListForDolly reviseOpinionListForDolly = new ProgramOpinionSaveDto.ReviseOpinionListForDolly();
                reviseOpinionListForDolly.setRemarks(reviseOpinionList.getRemarks());
                reviseOpinionListForDolly.setRoomId(reviseOpinionList.getRoomId());
                reviseOpinionListForDolly.setRoomUsageName(reviseOpinionList.getRoomUsageName());
                reviseOpinionListForDolly.setTagList(reviseOpinionList.getSelectedTagList());
                programOpinionSaveDto.getReviseOpinionList().add(reviseOpinionListForDolly);
            }
            programOpinionSaveDto.setSubmitterUserId(programOpinionDetailDto.getCreateUserId());
            Integer source = getprogramOpinion(isUserAffirm, request.getSource(), programOpinionDetailDto.getSource());
            programOpinionSaveDto.setSource(source);

            programOpinionProxy.affirmProgramOpinionComment(programOpinionSaveDto);
        }
    }

    private Integer getprogramOpinion(boolean isUserAffirm, Integer requestSource, Integer programOpinionSource) {
        Integer source = 1;
        if (isUserAffirm) {
            if (requestSource != null && (1 == requestSource || 2 == requestSource)) {
                source = 1 == requestSource ? 1 : 3;
            } else if (programOpinionSource != null) {
                source = 1 == programOpinionSource ? 1 : 3;
            }
        } else {
            if (requestSource != null && (1 == requestSource || 2 == requestSource)) {
                source = 1 == requestSource ? 2 : 4;
            } else if (programOpinionSource != null) {
                source = 1 == programOpinionSource ? 2 : 4;
            }
        }
        return source;
    }

    @Override
    public HttpBasePageResponse<ProgramOpinionDetailResponse> queryProgramOpinionListPage(ProgramOpinionPageQueryReq request) {
        HttpBasePageResponse<ProgramOpinionDetailResponse> basePageResponse = new HttpBasePageResponse<ProgramOpinionDetailResponse>();
        basePageResponse.setPageSize(request.getPageSize());
        basePageResponse.setPageNo(request.getPageNo());

        if (request.getPageSize() == null || null == request.getPageNo()) {
            basePageResponse.setExt(MessageConstant.PARAMS_NOT_EXISTS);
            basePageResponse.setCode(HttpResponseCode.PARAMS_NOT_EXISTS);
            return basePageResponse;
        }

        ProgramOpinionPageQueryResponseDto responseDto = programOpinionProxy.queryProgramOpinionListPage(request);
        if (null != responseDto) {
            basePageResponse.setObj(responseDto.getRecords());
            basePageResponse.setTotalCount(responseDto.getTotalCount());
        }

        basePageResponse.setExt("查询成功");
        basePageResponse.setCode(HttpResponseCode.SUCCESS);
        return basePageResponse;
    }

}
