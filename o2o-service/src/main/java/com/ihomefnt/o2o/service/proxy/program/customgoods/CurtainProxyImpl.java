package com.ihomefnt.o2o.service.proxy.program.customgoods;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.GroupDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialOptionsDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.*;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.*;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ProductWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.program.customgoods.CurtainProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-18 22:14
 */
@Repository
public class CurtainProxyImpl implements CurtainProxy {

    @Autowired
    private StrongSercviceCaller serviceCaller;


    /**
     * product-web查询物料详情
     *
     * @param request
     * @return
     */
    @Override
    public MaterialDetailDto queryMaterialDetail(DetailQueryRequest request) {
        ResponseVo<MaterialDetailDto> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_MATERIAL_DETAIL, Maps.newHashMap("componentId", request.getComponentId(), "groupId", request.getGroupId(), "materialId", request.getMaterielId()), new TypeReference<ResponseVo<MaterialDetailDto>>() {
        });
        return responseVo.getData();
    }

    /**
     * product-web查询组合详情
     *
     * @param request
     * @return
     */
    @Override
    public GroupDetailDto queryGroupDetail(DetailQueryRequest request) {
        ResponseVo<GroupDetailDto> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_GROUP_DETAIL, request.getGroupId(), new TypeReference<ResponseVo<GroupDetailDto>>() {
        });
        return responseVo.getData();
    }

    /**
     * product-web查询物料筛选项
     *
     * @param request
     * @return
     */
    @Override
    public MaterialOptionsDto queryMaterialOptions(DetailQueryRequest request) {
        ResponseVo<MaterialOptionsDto> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_MATERIAL_OPTIONS, request.getMaterialClassificationId(), new TypeReference<ResponseVo<MaterialOptionsDto>>() {
        });
        return responseVo.getData();
    }

    /**
     * 保存组合
     *
     * @param request
     * @return
     */
    @Override
    public GroupSaveVO saveGroup(GroupSaveRequest request) {
        ResponseVo<GroupSaveVO> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_GROUP_SAVE, request, new TypeReference<ResponseVo<GroupSaveVO>>() {
        });
        return responseVo.getData();
    }

    /**
     * 分页查询物料信息
     *
     * @param request
     * @return
     */
    @Override
    public MaterialForPageVO querMaterialForPage(QuerMaterialForPageRequest request) {
        ResponseVo<MaterialForPageVO> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_MATERIAL_PAGE, request, new TypeReference<ResponseVo<MaterialForPageVO>>() {
        });
        return responseVo.getData();
    }

    /**
     * 查询组合替换详情
     *
     * @param request
     * @return
     */
    @Override
    public GroupReplaceDetailVO queryGroupReplaceDetail(QueryGroupReplaceDetailRequest request) {
        ResponseVo<GroupReplaceDetailVO> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_GROUP_REPLACE_DETAIL, request, new TypeReference<ResponseVo<GroupReplaceDetailVO>>() {
        });
        return responseVo.getData();
    }


    /**
     * 批量查询组合替换简单信息
     *
     * @param queryList
     * @return
     */
    @Override
    public List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimple(List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> queryList) {
        ResponseVo<List<QueryGroupReplaceDetailSimpleVO>> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_GROUP_REPLACE_SIMPLEINFOLIST, queryList, new TypeReference<ResponseVo<List<QueryGroupReplaceDetailSimpleVO>>>() {
        });
        return responseVo.getData();
    }

    /**
     * 批量查询组合信息
     *
     * @param groupIdList
     * @return
     */
    @Override
    public List<BomGroupVO> queryGroupListDetailByGroupListForCabinet(List<Integer> groupIdList) {
        ResponseVo<List<BomGroupVO>> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_GROUP_DETAIL_LIST, groupIdList, new TypeReference<ResponseVo<List<BomGroupVO>>>() {
        });
        return responseVo.getData();
    }

    /**
     * 批量保存组合
     *
     * @param request
     * @return
     */
    @Override
    public BatchSaveGroupResponse batchSaveGroup(BatchSaveGroupRequest request) {
        ResponseVo<BatchSaveGroupResponse> responseVo = serviceCaller.post(ProductWebServiceNameConstants.BOM_APP_BATCH_SAVE_GROUP, request, new TypeReference<ResponseVo<BatchSaveGroupResponse>>() {
        });
        if (!responseVo.isSuccess()){
            throw new BusinessException(HttpResponseCode.SUBMIT_FAILURE, MessageConstant.SUBMIT_FAILURE);
        }
        return responseVo.getData();
    }
}
