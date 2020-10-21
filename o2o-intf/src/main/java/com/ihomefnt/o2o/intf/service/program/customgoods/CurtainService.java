package com.ihomefnt.o2o.intf.service.program.customgoods;


import com.ihomefnt.o2o.intf.domain.program.customgoods.request.*;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.QueryCabinetPropertyListResponseNew;

import java.util.List;

/**
 * 定制窗帘服务
 *
 * @author liyonggang
 * @create 2019-03-18 19:29
 */
public interface CurtainService {

    /**
     * 根据物料id查询物料详情
     *
     * @param request
     * @return
     */
    DetailVO queryMaterialDetail(DetailQueryRequest request);

    /**
     * 根据组合id查询组合详情
     *
     * @param request
     * @return
     */
    DetailVO queryGroupDetail(DetailQueryRequest request);

    /**
     * 根据组合分类id查询物料筛选项
     *
     * @param request
     * @return
     */
    List<QueryMaterialOptionsVO> queryMaterialOptions(DetailQueryRequest request);

    /**
     * 保存组合
     *
     * @param request
     * @return
     */
    GroupSaveVO saveGroup(GroupSaveRequest request);

    /**
     * 分页查询物料信息
     *
     * @param request
     * @return
     */
    MaterialForPageVO querMaterialForPage(QuerMaterialForPageRequest request);

    /**
     * 分页查询物料信息
     *
     * @param request
     * @return
     */
    GroupReplaceDetailVO queryGroupReplaceDetail(QueryGroupReplaceDetailRequest request);

    /**
     * 批量查询组合替换简单信息
     *
     * @param request
     * @return
     */
    List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimple(QueryGroupReplaceDetailSimpleRequest request);

    /**
     * 批量查询组合详情
     *
     * @param request
     * @return
     */
    List<DetailVO> queryGroupDetailList(GroupDetailListRequest request);

    /**
     * 定制柜详情查询
     *
     * @param request
     * @return
     */
    List<CabinetBomGroupDetailResponse.GroupDetailByCabinetType> queryGroupListDetailByGroupListForCabinet(QueryCabinetPropertyListRequest request);

    /**
     * 批量保存组合
     *
     * @param request
     * @return
     */
    BatchSaveGroupResponse batchSaveGroup(QueryCabinetPropertyListResponseNew request);
}
