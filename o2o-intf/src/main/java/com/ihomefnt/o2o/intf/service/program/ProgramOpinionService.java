package com.ihomefnt.o2o.intf.service.program;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramOpinionDetailDto;
import com.ihomefnt.o2o.intf.domain.program.vo.request.ProgramOpinionPageQueryReq;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionDetailResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserOrderProgramListResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionAffirmByOperationListReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;

/**
 * 方案修改意见
 *
 * @author liyonggang
 * @create 2019-08-08 15:41
 */
public interface ProgramOpinionService {
    /**
     * 订单方案列表
     *
     * @param userId
     * @return
     */
    UserOrderProgramListResponse queryUserProgramOpinionOrderList(Integer userId);

    /**
     * 意见记录列表
     *
     * @param request
     * @return
     */
    ProgramOpinionResponse queryProgramOpinionList(ProgramOpinionRequest request);

    /**
     * 暂存
     *
     * @param request
     * @return
     */
    String addOrUpdateProgramOpinionDraft(ProgramOpinionDetailDto request);

    /**
     * 方案意见详情
     *
     * @param request
     * @return
     */
    ProgramOpinionDetailDto queryProgramOpinionDetail(ProgramOpinionRequest request);

    /**
     * 提交方案意见
     *
     * @param request
     * @return
     */
    ProgramOpinionRequest submitProgramOpinion(ProgramOpinionDetailDto request);

    /**
     * 方案意见确认
     *
     * @param request
     * @return
     */
    void affirmProgramOpinion(ProgramOpinionRequest request);

    /**
     * 运营人员确认方案意见
     *
     * @param request
     * @return
     */
    void affirmProgramOpinionByOperation(ProgramOpinionAffirmByOperationListReq request);

    HttpBasePageResponse<ProgramOpinionDetailResponse> queryProgramOpinionListPage(ProgramOpinionPageQueryReq request);
}
