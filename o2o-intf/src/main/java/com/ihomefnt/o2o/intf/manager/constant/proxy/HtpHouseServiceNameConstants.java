package com.ihomefnt.o2o.intf.manager.constant.proxy;


/**
 * 业务中台（昊天塔）接口常量
 */
public interface HtpHouseServiceNameConstants {


    /**
     * 根据户型id查询所有空间信息包含户型点位信息标识
     */
    String QUERY_ROOM_DETAIL_LIST_BY_HOUSE_ID = "htp-house.house.queryLatestHouseAtSameOriginalByHouseId";


    /**
     * 根据户型id查询所有空间信息
     */
    String QUERY_ROOM_DETAIL_LIST_WITH_HOUSEID = "htp-house.api-project.queryRoomDetailListWithHouseId";

    /**
     * 根据户型id查询户型扩展信息
     */
    String GET_EXT_BY_HOUSE_ID = "htp-house.house.getExtByHouseId";

    /**
     * 根据项目id查询户型信息
     */
    String LIST_HOUSE_BY_PROJECT_ID = "htp-house.house.listHouseByProjectId";
}
