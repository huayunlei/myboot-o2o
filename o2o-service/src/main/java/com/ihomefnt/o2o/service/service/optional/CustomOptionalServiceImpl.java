package com.ihomefnt.o2o.service.service.optional;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuRequestDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CustoOptionalResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.TreeNodeResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.vo.request.CreateCustomSkuRequestVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CreateCustomSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CustoOptionalResponseVo;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.optional.CustomOptionalProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.optional.CustomOptionalService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定制品选配
 *
 * @author liyonggang
 * @create 2018-11-22 19:39
 */
@Service
public class CustomOptionalServiceImpl implements CustomOptionalService {

    @Autowired
    private CustomOptionalProxy customOptionalProxy;
    @Autowired
    private UserProxy userProxy;

    /**
     * 查询订制品属性信息
     *
     * @param skuId
     * @return
     */
    @Override
    public CustoOptionalResponseVo queryCustomAttrs(Integer width, Integer skuId) {
        CustoOptionalResponseDto custoOptionalVO = customOptionalProxy.queryCustomAttrs(skuId);
        if (custoOptionalVO == null) {
        	return null;
        }
        custoOptionalVO.setImageUrl(StringUtils.isNotBlank(custoOptionalVO.getImageUrl()) ? QiniuImageUtils.compressImageAndSamePicTwo(custoOptionalVO.getImageUrl(), width, -1) : "");
        if (CollectionUtils.isNotEmpty(custoOptionalVO.getCustomItemList())) {
        	custoOptionalVO.getCustomItemList().forEach(action -> customImageHandler(action.getAttrs(), width / 3));
        }
        
        CustoOptionalResponseVo response = ModelMapperUtil.strictMap(custoOptionalVO, CustoOptionalResponseVo.class);
        return response;
    }

    void customImageHandler(List<TreeNodeResponseDto> treeNodeRes, Integer width) {
        if (CollectionUtils.isNotEmpty(treeNodeRes)) {
            treeNodeRes.forEach(treeNode -> {
                treeNode.setImageUrl(StringUtils.isNotBlank(treeNode.getImageUrl()) ? QiniuImageUtils.compressImageAndSamePicTwo(treeNode.getImageUrl(), width, -1) : "");
                if (CollectionUtils.isNotEmpty(treeNode.getAttrs())) {
                    customImageHandler(treeNode.getAttrs(), width);
                }
            });
        }
    }

    /**
     * 新建sku
     *
     * @param request
     * @return
     */
    @Override
    public CreateCustomSkuResponseVo createCustomSku(CreateCustomSkuRequestVo request) {
        CreateCustomSkuRequestDto requestDto = ModelMapperUtil.strictMap(request, CreateCustomSkuRequestDto.class);
        CreateCustomSkuResponseDto dto = customOptionalProxy.createCustomSku(requestDto);
        if (null == dto) {
        	return null;
        }
        CreateCustomSkuResponseVo response = ModelMapperUtil.strictMap(dto, CreateCustomSkuResponseVo.class);
        return response;
    }
}
