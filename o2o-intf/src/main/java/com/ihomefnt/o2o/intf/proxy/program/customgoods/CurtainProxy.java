package com.ihomefnt.o2o.intf.proxy.program.customgoods;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.GroupDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialOptionsDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.*;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.*;

import java.util.List;

/**
 * 定制窗帘proxy
 *
 * @author liyonggang
 * @create 2019-03-18 22:07
 */
public interface CurtainProxy {

    /**
     * product-web查询物料详情
     *
     * @param request
     * @return
     */
    MaterialDetailDto queryMaterialDetail(DetailQueryRequest request);

    /**
     * product-web查询组合详情
     *
     * @param request
     * @return
     */
    GroupDetailDto queryGroupDetail(DetailQueryRequest request);

    /**
     * product-web查询物料筛选项
     *
     * @param request
     * @return
     */
    MaterialOptionsDto queryMaterialOptions(DetailQueryRequest request);

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
     * 查询组合替换详情
     *
     * @param request
     * @return
     */
    GroupReplaceDetailVO queryGroupReplaceDetail(QueryGroupReplaceDetailRequest request);


    /**
     * 批量查询组合替换简单信息
     *
     * @param queryList
     * @return
     */
    List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimple(List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> queryList);

    /**
     * 批量查询组合信息
     *
     * @param groupIdList
     * @return
     */
    List<BomGroupVO> queryGroupListDetailByGroupListForCabinet(List<Integer> groupIdList);

    /**
     * 批量保存组合
     *
     * @param request
     * @return
     */
    BatchSaveGroupResponse batchSaveGroup(BatchSaveGroupRequest request);
}
