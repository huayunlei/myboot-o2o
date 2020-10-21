package com.ihomefnt.o2o.intf.manager.constant.proxy;


/**
 * @author huayunlei
 * @ClassName: DollyWebServiceNameConstants
 * @Description: dolly-web服务名称常量池
 * @date Feb 14, 2019 1:49:25 PM
 */
public interface DollyWebServiceNameConstants {


    /**
     * 查询软装可替换项
     */
    String SOLUTION_APP_QUERYREPLACESOFTITEMBYROOM = "dolly-web.solution-app.queryReplaceSoftItemByRoom";

    /**
     * 查询硬装可替换项
     */
    String SOLUTION_APP_QUERYREPLACEHARDITEMBYROOM = "dolly-web.solution-app.queryReplaceHardItemByRoom";

    /**
     * Dolly下单预校验
     */
    String DOLLY_ORDER_VALIDATE_PARAM = "dolly-web.order.validate.param";

    /**
     * 查询方案及自由搭配空间详细信息
     */
    String BATCH_QUERY_PRICE_DIFF_BY_ROOM = "dolly-web.solution-app.batchQueryPriceDiffByRoom";

    String CHECK_SOLUTION_SKU_VISIBLE = "dolly-web.solution-app.checkSolutionSkuVisible";

    String QUERY_SKU_VISIBLE_PIC_LIST = "dolly-web.solution-app.querySkuVisiblePicList";

    String QUERY_QUERY_SOLUTION_AND_ROOM_INFO = "dolly-web.solution-app.queryPriceDiff";

    String QUERY_PREPARED_SOLUTION_LIST_WITH_USER_INFO = "dolly-web.solution-app.queryPreparedSolutionListWithUserInfo";

    String QUERY_BASE_PROPERTIES = "dolly-web.property-app.queryBaseProperties";

    String QUERY_BUILDING_DETAIL = "dolly-web.api-project.queryBuildingDetail";

    String BATCH_QUERY_ROOMID_RELATION = "dolly-web.solution.batchQueryRoomIdRelation";

    String QUERY_SOLUTION_LIST = "dolly-web.solution-app.querySolutionList";

    String QUERY_ROOM_LIST = "dolly-web.solution-app.queryRoomList";

    String BATCH_QUERY_SOLUTION_BASEINFO = "dolly-web.solution.batchQuerySolutionBaseInfo";

    String QUERY_SOLUTION_SERVICE = "dolly-web.solution-app.querySolutionService";

    String RANDOM_QUERY_DNA = "dolly-web.design-api.randomQueryDna";

    String QUERY_DNA_ROOM_BY_USAGEID = "dolly-web.design-api.queryDnaRoomByUsageId";

    String QUERY_DNA_SIMPLEINFO_BY_SOLUTIONID = "dolly-web.dna-app.queryDNASimpleInfoBySolutionId";

    /**
     * 根据户型id查询所有空间信息包含户型点位信息标识
     */
    String QUERY_ROOM_DETAIL_LIST_BY_HOUSE_ID = "dolly-web.api-project.queryRoomDetailListByApartmentId";

    /**
     * 根据户型id查询所有空间信息
     */
    String QUERY_ROOM_DETAIL_LIST_WITH_HOUSEID = "dolly-web.api-project.queryRoomDetailListWithHouseId";

    String PROJECT_ADD_CUSTOME_RPROJECT = "dolly-web.api-project.addCustomerProject";

    String QUERY_DNA_PRICE_INFO = "dolly-web.dna-app.queryDNAPriceInfo";

    String CUSTOPER_PROJECR_APARTMENT_QUERY_PATTERN_CONFIG = "dolly-web.customer-project.apartment.queryPatternConfigForAPP";

}
