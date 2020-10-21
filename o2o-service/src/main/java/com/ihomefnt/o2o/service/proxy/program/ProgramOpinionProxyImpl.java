package com.ihomefnt.o2o.service.proxy.program;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.ProgramOpinionPageQueryReq;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserOrderProgramListResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDdcServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.program.ProgramOpinionProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-08 15:43
 */
@Repository
public class ProgramOpinionProxyImpl implements ProgramOpinionProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public List<UserOrderProgramListResponse.ProgramOrderInfo> queryProgramOrderInfoList(List<Integer> orderIdList) {
        return ((ResponseVo<List<UserOrderProgramListResponse.ProgramOrderInfo>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.SOLUTION_REVISEOPINION_APP_LIST, Maps.newHashMap("orderNumList", orderIdList), new TypeReference<ResponseVo<List<UserOrderProgramListResponse.ProgramOrderInfo>>>() {
        })).getData();
    }

    @Override
    public ProgramOpinionResponse queryProgramOpinionListForPage(ProgramOpinionRequest request) {
        return ((ResponseVo<ProgramOpinionResponse>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.SOLUTION_REVISEOPINION_APP_PAGE, request, new TypeReference<ResponseVo<ProgramOpinionResponse>>() {
        })).getData();
    }

    /**
     * 查询wcm订单方案未确认的数量
     *
     * @param orderIdList
     * @return
     */
    @Override
    public List<OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList> queryUnConfirmOpinionCount(List<Integer> orderIdList) {
        return ((ResponseVo<List<OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.PROGRAM_OPINION_QUERYUN_CONFIRM_OPINION_COUNT, Maps.newHashMap("orderNumList", orderIdList), new TypeReference<ResponseVo<List<OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList>>>() {
        })).getData();
    }

    /**
     * 新增或更新方案意见
     *
     * @param request
     * @return
     */
    @Override
    public String addOrUpdateProgramOpinionDraft(ProgramOpinionDetailDto request) {
        return ((ResponseVo<ProgramOpinionRequest>) strongSercviceCaller
                .post(AladdinDdcServiceNameConstants.PROGRAM_OPINION_ADD_OR_UPDATE_OPINION,
                        Maps.newHashMap("id", request.getProgramOpinionId(),
                                "orderNum", request.getOrderNum(),
                                "reviseOpinionContent", JSON.toJSONString(request.getReviseOpinionList()),
                                "solutionId", request.getSolutionId(),
                                "source", request.getSource(),
                                "submitSource", request.getSource(),
                                "submitterUserId", request.getCreateUserId(),
                                "userId", request.getUserId()), new TypeReference<ResponseVo<ProgramOpinionRequest>>() {
                        })).getData().getId();
    }

    /**
     * 更新wcm意见状态
     *
     * @param request
     * @return
     */
    @Override
    public ProgramOpinionRequest updateOpinionSendStatus(ProgramOpinionRequest request) {
        return ((ResponseVo<ProgramOpinionRequest>)strongSercviceCaller.post(AladdinDdcServiceNameConstants.PROGRAM_OPINION_UPDATE_OPINION_STATUS, request, new TypeReference<ResponseVo<ProgramOpinionRequest>>() {
        })).getData();
    }

    /**
     * 查询订单方案修改意见列表
     *
     * @param request
     * @return
     */
    @Override
    public List<ProgramOpinionDetailDto> queryUnConfirmOpinionList(ProgramOpinionRequest request) {
        return ((ResponseVo<List<ProgramOpinionDetailDto>>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.PROGRAM_OPINION_QUERY_UNCONFIRM_OPINION_LIST, Maps.newHashMap("orderNumList", Lists.newArrayList(request.getOrderNum()),"solutionId",request.getSolutionId()), new TypeReference<ResponseVo<List<ProgramOpinionDetailDto>>>() {
        })).getData();
    }

    /**
     * 查询方案意见详情信息
     *
     * @param id
     * @param solutionId
     * @return
     */
    @Override
    public ProgramOpinionDetailDto queryProgramOpinionDetailForDolly(String id, Integer solutionId) {
        return ((ResponseVo<ProgramOpinionDetailDto>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.SOLUTION_REVISE_OPINION_APP_DETAIL, Maps.newHashMap("reviseOpinionId", id, "solutionId", solutionId), new TypeReference<ResponseVo<ProgramOpinionDetailDto>>() {
        })).getData();
    }

    /**
     * 提交到dolly
     *
     * @param programOpinionSaveDto
     */
    @Override
    public ProgramOpinionRequest affirmProgramOpinionComment(ProgramOpinionSaveDto programOpinionSaveDto) {
        ResponseVo<ProgramOpinionRequest> responseVo = strongSercviceCaller.post(AladdinDdcServiceNameConstants.SOLUTION_REVISE_OPINION_SAVE,
                Maps.newHashMap("userId", programOpinionSaveDto.getUserId(), "orderNum", programOpinionSaveDto.getOrderNum(),
                        "solutionId", programOpinionSaveDto.getSolutionId(),
                        "reviseOpinionContent", JSON.toJSONString(Maps.newHashMap("reviseOpinionList", programOpinionSaveDto.getReviseOpinionList())),
                        "submissionTime", programOpinionSaveDto.getAddTime(),
                        "source", programOpinionSaveDto.getSource(),
                        "submitterUserId", programOpinionSaveDto.getSubmitterUserId(),
                        "id", programOpinionSaveDto.getId()),
                new TypeReference<ResponseVo<ProgramOpinionRequest>>() {
                });
        if (!responseVo.isSuccess()) {
            throw new BusinessException(HttpResponseCode.ABNORMAL_OPERATION, MessageConstant.SUBMIT_APPLY_FAILED);
        }
        return responseVo.getData();
    }

    @Override
    public ProgramOpinionPageQueryResponseDto queryProgramOpinionListPage(ProgramOpinionPageQueryReq request) {
        return ((ResponseVo<ProgramOpinionPageQueryResponseDto>) strongSercviceCaller.post(AladdinDdcServiceNameConstants.PROGRAM_OPINION_QUERY_LIST_PAGE, request, new TypeReference<ResponseVo<ProgramOpinionPageQueryResponseDto>>() {
        })).getData();
    }
}
