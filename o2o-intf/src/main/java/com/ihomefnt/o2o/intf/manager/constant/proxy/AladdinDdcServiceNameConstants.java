package com.ihomefnt.o2o.intf.manager.constant.proxy;

/**
 * aladdin-ddc 服务名常量池
 *
 * @author liyonggang
 * @create 2019-12-23 3:31 下午
 */
public interface AladdinDdcServiceNameConstants {

    String BATCH_QUERY_DESIGN_TASKSTATUS_BYORDERNUM_LIST = "aladdin-ddc.design-api.batchQueryDesignTaskStatusByOrderNumList";

    String QUERY_DESIGN_DEMOND_HISTORY = "aladdin-ddc.design-api.queryDesignTaskHistory";

    String COMMIT_DESIGN_DEMAND_BYCUSTOMER_SERVICE = "aladdin-ddc.design-api.commitDesignTaskByCustomerService";

    /**
     * 个性化需求
     */

    String CHECK_USER_DEMOND_NEW = "aladdin-ddc.design-api.checkUserTaskNew";

    String COMMIT_DESIGN_DEMAND = "aladdin-ddc.design-api.commitDesignTask";

    String SOLUTION_REVISEOPINION_APP_LIST = "aladdin-ddc.solutionReviseOpinion.app.list";

    String SOLUTION_REVISEOPINION_APP_PAGE = "aladdin-ddc.solutionReviseOpinion.app.page";

    String SOLUTION_REVISE_OPINION_APP_DETAIL = "aladdin-ddc.solutionReviseOpinion.app.detail";

    String SOLUTION_REVISE_OPINION_SAVE = "aladdin-ddc.solutionReviseOpinion.save";

    /**
     * 一键完成设计任务
     */
    String DIRECT_COMPLETE_SOLUTION_TASK = "aladdin-ddc.design-api.directCompleteSolutionTask";


    String QUERY_STYLE_COMMIT_RECORD_LIST = "aladdin-ddc.style.queryStyleCommitRecordList";

    String QUERY_QUESTION_ANWSER_DETAIL = "aladdin-ddc.style.queryQuestionAnwserDetail";

    String QUERY_QUESTION_ANWSER_DETAIL_LATEST = "aladdin-ddc.style.queryQuestionAnwserDetailLatest";

    String QUERY_ALL_QUESTION_ANWSER_MAP = "aladdin-ddc.style.queryAllQuestionAnwserMap";

    String COMMIT_STYLE_QUESTION_ANWSER = "aladdin-ddc.style.commitStyleQuestionAnwser";

    String QUERY_ALL_QUESTION_ANWSER_LIST = "aladdin-ddc.style.queryAllQuestionAnwserList";

    String QUERY_ALL_ANWSER_LIST = "aladdin-ddc.style.queryAllAnwserList";

    String PROGRAM_OPINION_QUERYUN_CONFIRM_OPINION_COUNT = "aladdin-ddc.solutionReviseOpinion.queryUnConfirmOpinionCount";

    String PROGRAM_OPINION_UPDATE_OPINION_STATUS = "aladdin-ddc.solutionReviseOpinion.updateOpinionSendStatus";

    String PROGRAM_OPINION_QUERY_UNCONFIRM_OPINION_LIST = "aladdin-ddc.solutionReviseOpinion.queryUnConfirmedList";

    String PROGRAM_OPINION_QUERY_LIST_PAGE = "aladdin-ddc.solutionReviseOpinion.queryByPage";

    String ADD_OR_UPDATE_DESIGN_DEMAND = "aladdin-ddc.design-api.createOrderDesignTask";

    String QUERY_DESIGN_DEMAND_INFO = "aladdin-ddc.design-api.queryDesignTask";

    String PROGRAM_OPINION_ADD_OR_UPDATE_OPINION = "aladdin-ddc.solutionReviseOpinion.saveDraft";
}
