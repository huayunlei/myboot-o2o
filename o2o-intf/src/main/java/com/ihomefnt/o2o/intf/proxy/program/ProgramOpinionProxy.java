package com.ihomefnt.o2o.intf.proxy.program;

import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.ProgramOpinionPageQueryReq;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserOrderProgramListResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-08 15:43
 */
public interface ProgramOpinionProxy {
    //查dolly方案列表
    List<UserOrderProgramListResponse.ProgramOrderInfo> queryProgramOrderInfoList(List<Integer> orderIdList);

    ProgramOpinionResponse queryProgramOpinionListForPage(ProgramOpinionRequest request);

    /**
     * 查询wcm订单方案未确认的数量
     *
     * @param orderIdList
     * @return
     */
    List<OrderUnConfirmOpinionDto.OrderUnConfirmOpinionList> queryUnConfirmOpinionCount(List<Integer> orderIdList);

    /**
     * 新增或更新方案意见
     *
     * @param request
     * @return
     */
    String addOrUpdateProgramOpinionDraft(ProgramOpinionDetailDto request);

    /**
     * 更新wcm意见状态
     *
     * @param request
     * @return
     */
    ProgramOpinionRequest updateOpinionSendStatus(ProgramOpinionRequest request);


    /**
     * 查询订单方案修改意见列表
     *
     * @param request
     * @return
     */
    List<ProgramOpinionDetailDto> queryUnConfirmOpinionList(ProgramOpinionRequest request);

    /**
     * 查询方案意见详情信息
     *
     * @param id
     * @param solutionId
     * @return
     */
    ProgramOpinionDetailDto queryProgramOpinionDetailForDolly(String id, Integer solutionId);

    /**
     * 提交到dolly
     *
     * @param programOpinionSaveDto
     */
    ProgramOpinionRequest affirmProgramOpinionComment(ProgramOpinionSaveDto programOpinionSaveDto);

    ProgramOpinionPageQueryResponseDto queryProgramOpinionListPage(ProgramOpinionPageQueryReq request);
}
