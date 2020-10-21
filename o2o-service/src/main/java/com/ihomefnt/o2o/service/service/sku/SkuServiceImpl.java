package com.ihomefnt.o2o.service.service.sku;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.optional.dto.*;
import com.ihomefnt.o2o.intf.domain.product.dto.*;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.QueryConditionByLastIdRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.QueryReplaceSkuByConditionsRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.SkuDetailQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.product.ProductCategoryConstant;
import com.ihomefnt.o2o.intf.manager.constant.product.ProductPropertyConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.sku.SkuProxy;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.sku.SkuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuProxy skuProxy;

    @Autowired
    private ProductProxy productProxy;

    @Autowired
    private ProductProgramService productProgramService;

    private final String MORE_SOFT_MESSAGE = "我们已为您提供几款免费赠品灯具供选择，您也可以自行选择非赠品灯具，但需要支付额外费用。";

    @Override
    public QuerySkuDetailResponseVo querySkuDetail(SkuDetailQueryRequestVo request) {
        return queryNormalSkuDetail(request);
    }

    /**
     * 查询sku详情
     *
     * @param request
     * @return
     */
    private QuerySkuDetailResponseVo queryNormalSkuDetail(SkuDetailQueryRequestVo request) {
        QuerySkuDetailResponseVo response = new QuerySkuDetailResponseVo();
        List<Integer> list = new ArrayList<>();
        list.add(request.getSkuId());
        List<QueryProductDtoBySkuIdsDto> skuResponseList = skuProxy.queryProductDtoBySkuIds(list);
        if (CollectionUtils.isEmpty(skuResponseList)){
            return null;
        }
        QueryProductDtoBySkuIdsDto skuResponse = skuResponseList.get(0);

        // 7新定制品
        if (skuResponse.getType() != null && skuResponse.getType().equals(Constants.SKU_TYPE_CUSTOM)) {
            return assembleCustomerSkuDetail(skuResponse.getAttrTreeRes(), request.getOsType(), request.getWidth());
        }
        response.setDetailContent(skuResponse.getGraphicDetails());

        // 工艺节点封装 仅硬装有工艺节点 且硬装的type值为6
        if (skuResponse.getType() != null && skuResponse.getType().equals(Constants.SKU_TYPE_HARD)) {
            List<SkuCraftVo> craftVoList = skuResponse.getCraftVoList();
            if (CollectionUtils.isNotEmpty(craftVoList)) {
                for (SkuCraftVo craftVo : craftVoList) {
                    if (!StringUtil.isNullOrEmpty(craftVo.getUrl())) {
                        craftVo.setUrl(AliImageUtil.imageCompress(craftVo.getUrl(),
                                request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    }
                }
            }
            response.setCraftVoList(craftVoList);
        }
        Integer skuId = skuResponse.getSkuId();
        response.setSkuId(skuId);
        response.setCategoryName(skuResponse.getCategoryName());
        response.setProductName(skuResponse.getProductName());
        if (StringUtil.isNullOrEmpty(skuResponse.getMaterialDesc())) {
            response.setDesc(null);
        } else {
            response.setDesc(skuResponse.getMaterialDesc());
        }
        response.setLastCategoryId(skuResponse.getLastCategoryId());
        response.setLastCategoryName(skuResponse.getLastCategoryName());

        // 组装返回的属性清单 ---- start
        List<AttributesDto> attributes = new ArrayList<>();
        //把品牌封装到属性里,放首位
        if (!StringUtil.isNullOrEmpty(skuResponse.getBrandName())) {
            attributes.add(0, new AttributesDto("品牌", skuResponse.getBrandName()));
        }

        List<QuerySkuDetailResponseVo.SkuImageDto> images = new ArrayList<>();
        QuerySkuDetailResponseVo skuDetailResponseVoTmp = new QuerySkuDetailResponseVo();
        Boolean localHasAdded = false;
        if (skuId == null) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_DATE_ERROR, MessageConstant.FAILED);
        }
        // 原来返回的尺寸不用了
        List<String> filterKeys = new ArrayList<>();
        filterKeys.add("尺寸");
        // 类目改造，product-web修改返回，软装属性统一放到了propertyList；图片放到外层
        if (CollectionUtils.isNotEmpty(skuResponse.getPropertyList())) {// 新类目修改后sku属性组装
            if (CollectionUtils.isNotEmpty(skuResponse.getImages())) {
                for (String image : skuResponse.getImages()) {
                    images.add(skuDetailResponseVoTmp.new SkuImageDto(
                            AliImageUtil.imageCompress(image, request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)));
                }
            }
            for (SkuPropertyDto property : skuResponse.getPropertyList()) {
                // 仅属性名和属性值不为空时候才封装到属性列表
                if (!StringUtil.isNullOrEmpty(property.getPropertyName()) && !StringUtil.isNullOrEmpty(property.getPropertyValue())) {
                    if(property.getPropertyName().equals("是否免费赠品")||property.getPropertyName().equals("是否默认赠品")){
                        continue;
                    }
                    if ("产地".equals(property.getPropertyName())) {
                        attributes.add(1, new AttributesDto(property.getPropertyName(), property.getPropertyValue()));
                        localHasAdded = true;
                    } else {
                        attributes.add(new AttributesDto(property.getPropertyName(), property.getPropertyValue()));
                    }
                }
            }
        } else {// 新类目修改前sku属性组装
            String[] skuPvsIds = null;
            if (CollectionUtils.isNotEmpty(skuResponse.getSkuList())) {
                for (ProductDtoSkuListDto skuDto : skuResponse.getSkuList()) {
                    if (skuId != null && skuId.equals(skuDto.getId())) {
                        if (skuDto.getSkuPvs() != null && skuDto.getSkuPvsId() != null &&
                                !StringUtil.isNullOrEmpty(skuDto.getSkuPvs()) && !StringUtil.isNullOrEmpty(skuDto.getSkuPvsId())) {
                            skuPvsIds = skuDto.getSkuPvsId().split(";");
                        }
                        if (skuDto.getImages() != null && CollectionUtils.isNotEmpty(skuDto.getImages())) {
                            for (String image : skuDto.getImages()) {
                                images.add(skuDetailResponseVoTmp.new SkuImageDto(
                                        AliImageUtil.imageCompress(image, request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)));
                            }
                        }
                        if (null != skuDto.getSkuExtraFields() && CollectionUtils.isNotEmpty(skuDto.getSkuExtraFields())) {
                            String sizeDesc = skuDto.getLength() == null ? null : "长" + skuDto.getLength() / 1000 + "米";
                            String widthDesc = skuDto.getWidth() == null ? null : "宽" + skuDto.getWidth() / 1000 + "米";
                            if (null == sizeDesc) {
                                sizeDesc = widthDesc;
                            } else if (null != widthDesc) {
                                sizeDesc += widthDesc;
                            }
                            String heightDesc = skuDto.getHeight() == null ? null : "" + skuDto.getHeight() / 1000 + "米";
                            if (null == sizeDesc) {
                                sizeDesc = heightDesc;
                            } else if (null != heightDesc) {
                                sizeDesc += heightDesc;
                            }
                            attributes.add(new AttributesDto("尺寸", sizeDesc));

                            for (ProductDtoSkuListDto.skuExtraFieldDto field : skuDto.getSkuExtraFields()) {
                                if (null != field.getSkuFieldName() && null != field.getSkuFieldValue()) {
                                    String skuFieldName = field.getSkuFieldName();
                                    String skuFieldValue = field.getSkuFieldValue();

                                    // 仅属性名和属性值不为空时候才封装到属性列表
                                    if (StringUtils.isNotBlank(skuFieldName) && StringUtils.isNotBlank(skuFieldValue)) {
                                        if (skuFieldValue.indexOf(";") > -1) {//多个尺寸
                                            if (StringUtils.countMatches(skuFieldValue, ";") == 7) {//儿童床上下层特殊处理
                                                String levelFirValue = skuFieldValue.substring(0, StringUtils.ordinalIndexOf(skuFieldValue, ";", 4));//第一层
                                                String levelSecValue = skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 4) + 1);//第二层
                                                attributes.add(new AttributesDto("床垫尺寸（第一层）", getSkuFieldValue(levelFirValue)));
                                                attributes.add(new AttributesDto("床垫尺寸（第二层）", getSkuFieldValue(levelSecValue)));
                                                continue;
                                            } else if (StringUtils.countMatches(skuFieldValue, ";") == 3
                                                    || StringUtils.countMatches(skuFieldValue, ";") == 2) {//兼容高只有一个的情况
                                                skuFieldName = "床垫尺寸";//过来的内容与需求不一致
                                                skuFieldValue = getSkuFieldValue(skuFieldValue);
                                            }
                                        } else {
                                            skuFieldValue = (parseDouble(skuFieldValue)) / 1000 + "米";
                                        }
                                        attributes.add(new AttributesDto(skuFieldName, skuFieldValue));
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
            // 属性来源有两个 productSkuPropertyList 和 productSpuPropertyList
            // 处理 productSkuPropertyList
            if (CollectionUtils.isNotEmpty(skuResponse.getProductSkuPropertyList())) {
                for (ProductSkuPropertyDto skuPropertyDto : skuResponse.getProductSkuPropertyList()) {
                    if (skuPropertyDto.getPropertyName() != null && !"".equals(skuPropertyDto.getPropertyName()) &&
                            skuPropertyDto.getPropertyValue() != null && !"".equals(skuPropertyDto.getPropertyValue())) {
                        String skuPropertyValue = skuPropertyDto.getPropertyValue();
                        if (skuPropertyDto.getPropertyValue().indexOf(",") > -1) {
                            String[] propertyExt = skuPropertyDto.getPropertyExt().split(",");
                            String[] attrValueSplit = skuPropertyDto.getPropertyValue().split(",");
                            if (skuPvsIds != null) {
                                for (int i = 0; i < propertyExt.length; i++) {
                                    for (int j = 0; j < skuPvsIds.length; j++) {
                                        if (propertyExt[i].equals(skuPvsIds[j])) {
                                            skuPropertyValue = attrValueSplit[i];
                                        }
                                    }
                                }
                            }
                        }
                        if (!filterKeys.contains(skuPropertyDto.getPropertyName())) {
                            attributes.add(new AttributesDto(skuPropertyDto.getPropertyName(), skuPropertyValue));
                        }
                    }
                }
            }
            // 处理productSpuPropertyList
            if (CollectionUtils.isNotEmpty(skuResponse.getProductSpuPropertyList())) {
                for (ProductSpuPropertyDto spuPropertyDto : skuResponse.getProductSpuPropertyList()) {
                    String attrValue = spuPropertyDto.getPropertyValue();
                    String baseAttrValue = spuPropertyDto.getPropertyValue();
                    if (spuPropertyDto.getPropertyType() != null && "check".equals(spuPropertyDto.getPropertyType())) {
                        if (null != spuPropertyDto.getPropertyValue() && null != spuPropertyDto.getTemplatePropertyValueList() &&
                                spuPropertyDto.getTemplatePropertyValueList().size() > 0) {
                            for (SkuPropertyDto skuTempPropertyDto : spuPropertyDto.getTemplatePropertyValueList()) {
                                if (null != skuTempPropertyDto.getId() &&
                                        skuTempPropertyDto.getId().toString().equals(spuPropertyDto.getPropertyValue())) {
                                    attrValue = skuTempPropertyDto.getPropertyValue();
                                }
                            }
                            if (baseAttrValue.equals(attrValue)) {
                                attrValue = "";
                            }
                        }
                    }
                    if (!StringUtil.isNullOrEmpty(attrValue) && null != spuPropertyDto.getPropertyName() &&
                            !"".equals(spuPropertyDto.getPropertyName()) && !filterKeys.contains(spuPropertyDto.getPropertyName())) {
                        attributes.add(new AttributesDto(spuPropertyDto.getPropertyName(), attrValue));
                    }
                }
            }

            if (null != skuResponse.getOriginName() && !"".equals(skuResponse.getOriginName())) {
                attributes.add(1, new AttributesDto("产地", skuResponse.getOriginName()));
                localHasAdded = true;
            }
        }
        response.setImages(images);
        if (skuResponse.getStyleName() != null && !StringUtil.isNullOrEmpty(skuResponse.getStyleName())) {
            int styleKey = 1;
            if (localHasAdded) {
                styleKey = 2;
            }
            attributes.add(styleKey, new AttributesDto("风格", skuResponse.getStyleName()));
        }
        if (skuResponse.getItemSize() != null && !StringUtil.isNullOrEmpty(skuResponse.getItemSize())) {
            attributes.removeIf(attributesDto -> attributesDto.getKey().equals("尺寸"));
            attributes.add(new AttributesDto("尺寸", skuResponse.getItemSize()));
        }

        response.setAttributes(filterAttibutes(attributes));
        // 组装返回的属性清单 ---- end
        return response;
    }

    /**
     * 去重，并控制展示顺序 品牌1，产地2，风格3
     *
     * @param attributes
     * @return
     */
    private List<AttributesDto> filterAttibutes(List<AttributesDto> attributes) {
        List<String> addedAttrs = new ArrayList<>();
        List<AttributesDto> attributesDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(attributes)) {
            return attributesDtos;
        }
        Boolean localHasAddedNew = false;
        // 去除标题过长的这两个属性
        List<String> filterKeys = new ArrayList<>();
        filterKeys.add("硬装是否需要预留电源插座位置");
        filterKeys.add("厂家是否可更换颜色");

        for (AttributesDto attributesTmp : attributes) {
            int key = -1;
            if (!addedAttrs.contains(attributesTmp.getKey()) && !filterKeys.contains(attributesTmp.getKey())) {
                if ("品牌".equals(attributesTmp.getKey())) {
                    key = 0;
                }
                if ("产地".equals(attributesTmp.getKey())) {
                    key = 1;
                    localHasAddedNew = true;
                }
                if ("风格".equals(attributesTmp.getKey())) {
                    if (localHasAddedNew) {
                        key = 2;
                    } else {
                        key = 1;
                    }
                }
                if (key >= 0) {
                    attributesDtos.add(key, attributesTmp);
                } else {
                    attributesDtos.add(attributesTmp);
                }
                addedAttrs.add(attributesTmp.getKey());
            }

        }
        return attributesDtos;
    }

    /**
     * 床品类SkuFieldValue属性重新组装
     *
     * @return
     */
    private String getSkuFieldValue(String skuFieldValue) {
        String length = skuFieldValue.substring(0, skuFieldValue.indexOf(";"));
        String width = skuFieldValue.substring(skuFieldValue.indexOf(";") + 1, StringUtils.ordinalIndexOf(skuFieldValue, ";", 2));
        String heightMin = "";
        if (StringUtils.countMatches(skuFieldValue, ";") == 2) {//高只有一个
            heightMin = skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 2) + 1);
        } else {
            heightMin = skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 2) + 1,
                    StringUtils.ordinalIndexOf(skuFieldValue, ";", 3));
        }

        String heightMax = "";
        Double heightMaxDouble = 0.0;
        if (StringUtils.countMatches(skuFieldValue, ";") == 3) {
            heightMax = skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 3) + 1);
            heightMaxDouble = (parseDouble(heightMax)) / 1000;
        }
        Double lengthDouble = (parseDouble(length)) / 1000;
        Double widthDouble = (parseDouble(width)) / 1000;
        if (heightMin.indexOf("~") > -1) {
            heightMax = heightMin.substring(heightMin.indexOf("~") + 1);
            heightMaxDouble = (parseDouble(heightMax)) / 1000;
            heightMin = heightMin.substring(0, heightMin.indexOf("~"));
        }
        Double heightMinDouble = (parseDouble(heightMin)) / 1000;
        if (StringUtils.countMatches(skuFieldValue, ";") == 2) {//高只有一个
            skuFieldValue = "长" + lengthDouble + "米，宽" + widthDouble + "米，高" + heightMinDouble + "米";
        } else if (heightMax.equals(heightMin)) {
            skuFieldValue = "长" + lengthDouble + "米，宽" + widthDouble + "米，高" + heightMinDouble + "米";
        } else {
            skuFieldValue = "长" + lengthDouble + "米，宽" + widthDouble + "米，高" + heightMinDouble + "米~" + heightMaxDouble + "米";
        }
        return skuFieldValue;
    }

    /**
     * 属性类字段，转double空和null类型兼容，默认为0
     *
     * @param length
     * @return
     */
    private Double parseDouble(String length) {
        if ("".equals(length) || length == null) {
            length = "0";
        }
        return Double.parseDouble(length);

    }

    /**
     * 组装定制品详情
     *
     * @return
     */
    private QuerySkuDetailResponseVo assembleCustomerSkuDetail(CustoOptionalResponseDto custoDto, Integer osType, Integer width) {
        QuerySkuDetailResponseVo response = new QuerySkuDetailResponseVo();
        response.setImageUrl(AliImageUtil.imageCompress(custoDto.getImageUrl(), osType, width, ImageConstant.SIZE_MIDDLE));
        response.setImageUrlList(new ArrayList<>());
        if (CollectionUtils.isNotEmpty(custoDto.getImageUrlList())) {
            for (String imgUrl : custoDto.getImageUrlList()) {
                response.getImageUrlList().add(AliImageUtil.imageCompress(imgUrl, osType, width, ImageConstant.SIZE_MIDDLE));
            }
        }
        response.setSkuId(custoDto.getSkuId());
        response.setSpuName(custoDto.getSpuName());
        response.setLength(custoDto.getLength());
        response.setMinLength(custoDto.getMinLength());
        response.setMaxLength(custoDto.getMaxLength());
        response.setWidth(custoDto.getWidth());
        response.setMinWidth(custoDto.getMinWidth());
        response.setMaxWidth(custoDto.getMaxWidth());
        response.setHeight(custoDto.getHeight());
        response.setMinHeight(custoDto.getMinHeight());
        response.setMaxHeight(custoDto.getMaxHeight());
        response.setMeasure(custoDto.getMeasure());
        response.setPurchasePrice(custoDto.getPurchasePrice());
        response.setAijiaPrice(custoDto.getAijiaPrice());

        List<CustomItemDto> customItemList = new ArrayList<>();


        if (CollectionUtils.isNotEmpty(custoDto.getCustomItemList())) {
            for (CustomItemResponseDto item : custoDto.getCustomItemList()) {
                CustomItemDto customItemDto = new CustomItemDto();
                customItemDto.setTitle(item.getName());
                if (CollectionUtils.isNotEmpty(item.getAttrs())) {
                    for (TreeNodeResponseDto attrs : item.getAttrs()) {
                        jointValue(attrs.getName(), attrs, customItemDto, null, false);
                    }
                }
                customItemList.add(customItemDto);
            }
            response.setCustomItemList(customItemList);
        }
        return response;
    }

    /**
     * 递归拼接sku选配详情的值
     *
     * @param treeNodeRes
     * @param customItemDto
     * @param sb
     * @return
     */
    private CustomItemDto jointValue(String parentName, TreeNodeResponseDto treeNodeRes, CustomItemDto customItemDto,
                                     StringBuilder sb, boolean isJoin) {
        if (treeNodeRes == null || treeNodeRes.getNodeType() == null) {
            throw new BusinessException("jointValue 参数为空 ");
        }
        if (sb == null) {
            sb = new StringBuilder();
        }
        boolean flag = treeNodeRes.getNodeType().equals(2);
        if (!flag) {
            if (isJoin && sb.length() != 0) {
                sb.append("/");
            }
            if (isJoin && !flag) {
                sb.append(treeNodeRes.getName());
            }
        }
        List<CustomItemInfoDto> info = customItemDto.getInfo();
        if (info == null) {
            info = new ArrayList<>();
            customItemDto.setInfo(info);
        }
        if (CollectionUtils.isNotEmpty(treeNodeRes.getAttrs())) {
            if (treeNodeRes.getAttrs().size() == 1) {
                jointValue(parentName, treeNodeRes.getAttrs().get(0), customItemDto, sb, true);
            } else {
                List<CustomItemDto> list = new ArrayList<>();
                CustomItemInfoDto infoTmp = new CustomItemInfoDto();
                infoTmp.setName(parentName);
                infoTmp.setValue(sb.toString());
                infoTmp.setAttrs(list);
                info.add(infoTmp);
                for (TreeNodeResponseDto attr : treeNodeRes.getAttrs()) {
                    CustomItemDto customItemDto1 = new CustomItemDto();
                    list.add(jointValue(attr.getName(), attr, customItemDto1, null, false));
                }
            }
        } else {
            CustomItemInfoDto infoTmp = new CustomItemInfoDto();
            infoTmp.setName(parentName);
            infoTmp.setValue(sb.toString());
            info.add(infoTmp);
        }
        return customItemDto;
    }


    @Override
    public QueryConditionByLastIdResponseVo queryConditionByLastCategoryId(QueryConditionByLastIdRequestVo request) {

        JSONObject param = new JSONObject();
        List<Integer> categoryIdList = new ArrayList<Integer>();
        // 此处传什么三级类目，返回的categoryList就有什么三级类目
        if (ProductCategoryConstant.SOFA_CAN_EXCHANGE_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            categoryIdList.addAll(ProductCategoryConstant.SOFA_CAN_EXCHANGE_LAST_CATEGORY_ID_LIST);
        } else if (ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            categoryIdList.addAll(ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST);
        } else if (ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            categoryIdList.addAll(ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST);
        } else if (ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(request.getLastCategoryId())) {
            categoryIdList.addAll(ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST);
        } else {
            categoryIdList.add(request.getLastCategoryId());
        }

        param.put("categoryIdList", categoryIdList);
        QueryConditionByCategoryDto conditionDto = skuProxy.queryConditionByCategory(param);

        QueryConditionByLastIdResponseVo response = new QueryConditionByLastIdResponseVo();
        // 床、沙发、床垫，用更多+二级分类名作为页面标题；其余使用更多+三级类目名称
        response.setPageTitle("更多" + conditionDto.getSecondCategory().getCategoryName());
        if (!ProductCategoryConstant.SOFA_CAN_EXCHANGE_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId()) &&
                !ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId()) &&
                !ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            for (CategoryDto categoryTmp : conditionDto.getCategoryList()) {
                if (request.getLastCategoryId().equals(categoryTmp.getCategoryId())) {
                    response.setPageTitle("更多" + categoryTmp.getCategoryName());
                    break;
                }
            }
        }

        response.setBrandList(conditionDto.getBrandList());
        response.setCategoryList(conditionDto.getCategoryList());
        response.setSecondCategory(conditionDto.getSecondCategory());
        response.setPropertyList(conditionDto.getPropertyList());

        NeedDisplayPropertyDto needDisplayPropertyDto = new NeedDisplayPropertyDto();
        // 标识前台需要展示的筛选项
        // 赋值顺序决定前台展示的筛选项排序
        List<Long> needDisplayPropertyIds = new ArrayList<>();

        // 所有分类首层筛选都是材质
        needDisplayPropertyDto.setBannerPropertyId(ProductPropertyConstant.PROPERTY_OF_MATERIAL);

        // 二级分类为床/床垫，有三级类目筛选  三级类目为多人沙发、转角沙发、贵妃椅，放开三级类目相互替换
        if (conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_BED) ||
                conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_MATTRESS) ||
                ProductCategoryConstant.SOFA_CAN_EXCHANGE_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_CATEGORY);
        }

        // 二级分类为床/床垫，有尺寸规格筛选
        if (conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_BED) ||
                conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_MATTRESS)) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_BED_SIZE);
        }
        // 二级分类为床垫 有床垫厚度
        if (conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_MATTRESS)) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_MATTRESS_THICKNESS);
        }

        // 三级分类 床头柜、电视柜，有样式属性
        if (ProductCategoryConstant.GUI_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_STYLE);
        }
        // 三级分类 餐桌、茶几、边几，有形状属性
        if (ProductCategoryConstant.ZHUO_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_SHAPE);
        }

        // 二级分类沙发；三级分类床头柜、电视柜；三级分类餐桌、茶几、边几 前台有尺寸输入
        if (conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_TWO_ID_SOFA) ||
                ProductCategoryConstant.ZHUO_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId()) ||
                ProductCategoryConstant.GUI_LAST_CATEGORY_ID_LIST.contains(request.getLastCategoryId())) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_LENGTH);
        }

        // 灯具
        if (conditionDto.getSecondCategory().getCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_DJ) ||
                ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(request.getLastCategoryId())) {
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_STYLE);
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_MATERIAL);
            needDisplayPropertyDto.setBannerPropertyId(ProductPropertyConstant.PROPERTY_OF_CATEGORY);
        } else {
            // 所有分类，都有品牌，且品牌放最后(灯具没有)
            needDisplayPropertyIds.add(ProductPropertyConstant.PROPERTY_OF_BRAND);
        }

        needDisplayPropertyDto.setNeedDisplayPropertyIds(needDisplayPropertyIds);
        response.setNeedDisplayProperties(needDisplayPropertyDto);
        return response;
    }

    @Override
    public QueryReplaceSkuByConditionsResponseVo queryReplaceSkuByConditions(QueryReplaceSkuByConditionsRequestVo request) {
        if (request.getItemType() != null && request.getItemType().equals(2)) {
            if (request.getSearchCondition() == null) {
                request.setSearchCondition(new QueryReplaceSkuByConditionsRequestVo.SearchCondition());
            }
//            if (StringUtils.isNotBlank(request.getItemSize())) {
//                String[] roleSize = request.getItemSize().replace("米", "").split("\\*");
//                Double bedWidth = Double.parseDouble(roleSize[0]);
//                Double bedLength = Double.parseDouble(roleSize[1]);
//                request.getSearchCondition().setWidthRange(new QueryReplaceSkuByConditionsRequestVo.SearchCondition.SizeRange().setMaxValue((int) (bedWidth * 1000)).setMinValue((int) (bedWidth * 1000)));
//                request.getSearchCondition().setLengthRange(new QueryReplaceSkuByConditionsRequestVo.SearchCondition.SizeRange().setMaxValue((int) (bedLength * 1000)).setMinValue((int) (bedLength * 1000)));
//            }
            if (request.getSuggestMattressMinHeight() != null && request.getSuggestMattressMaxHeight() != null) {
                request.getSearchCondition().setHeightRange(new QueryReplaceSkuByConditionsRequestVo.SearchCondition.SizeRange().setMaxValue(request.getSuggestMattressMaxHeight()).setMinValue(0));
            }
        }
        SearchReplaceSkuResponseDto searchReplaceSkuDto = skuProxy.queryReplaceSkuByConditions(request);
        QueryReplaceSkuByConditionsResponseVo response = new QueryReplaceSkuByConditionsResponseVo();
        if (searchReplaceSkuDto.getPageInfo() == null) {
            return response;
        }
        response.setPageNo(searchReplaceSkuDto.getPageInfo().getPageNo());
        response.setPageSize(searchReplaceSkuDto.getPageInfo().getPageSize());
        response.setTotalRecords(searchReplaceSkuDto.getPageInfo().getTotalRecords() == null ?
                0L : searchReplaceSkuDto.getPageInfo().getTotalRecords());
        for (SearchReplaceSkuDto skuDto : searchReplaceSkuDto.getPageInfo().getList()) {
            skuDto.setSmallImage(
                    AliImageUtil.imageCompress(skuDto.getSkuImg(), request.getOsType(),
                            request.getWidth(), ImageConstant.SIZE_SMALL));
            if (skuDto.getProductName() != null) {
                skuDto.setFurnitureName(skuDto.getProductName());
            }
            if (skuDto.getBrandName() != null) {
                skuDto.setBrand(skuDto.getBrandName());
            }
        }

        for (SearchReplaceSkuDto replaceSkuDto : searchReplaceSkuDto.getPageInfo().getList()) {
            try {
                replaceSkuDto.setItemSize((replaceSkuDto.getLength() / 1000.0) + "*" + (replaceSkuDto.getWidth() / 1000.0) + "米");
            } catch (Exception e) {
            }
            if (StringUtils.isNotBlank(replaceSkuDto.getPvs())) {
                String[] split = replaceSkuDto.getPvs().split(";");
                for (String s : split) {
                    if (s.contains("*")) {
                        replaceSkuDto.setItemSize(s);
                        break;
                    }
                }
            }
        }
        if (request.getItemType() != null && request.getItemType().equals(2) && StringUtils.isNotBlank(request.getItemSize()) && request.getSuggestMattressMaxHeight() != null) {
//            List<SearchReplaceSkuDto> resultList = Lists.newArrayList();
            List<SearchReplaceSkuDto> dataList = searchReplaceSkuDto.getPageInfo().getList();
            List<Integer> skuIdList = dataList.stream().map(o -> o.getSkuId().intValue()).collect(Collectors.toList());
            List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(skuIdList);
            Map<Integer, SkuBaseInfoDto> skuBaseInfoDtoMap = skuBaseInfoDtos.stream().collect(Collectors.toMap(o -> o.getSkuId(), o -> o));
//                List<SearchReplaceSkuDto> okList = Lists.newArrayList();
//                List<SearchReplaceSkuDto> failList = Lists.newArrayList();
            for (SearchReplaceSkuDto replaceSkuDto : dataList) {
                SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoDtoMap.get(replaceSkuDto.getSkuId().intValue());
                if (skuBaseInfoDto != null) {
                    replaceSkuDto.setHeight(skuBaseInfoDto.getHeight());
                    replaceSkuDto.setLength(skuBaseInfoDto.getLength());
                    replaceSkuDto.setWidth(skuBaseInfoDto.getWidth());
                    List<String> skuSize = Lists.newArrayList(replaceSkuDto.getItemSize().replace("米", "").split("\\*"));
                    if (StringUtils.isNotBlank(replaceSkuDto.getItemSize())) {
                        String itemSize = replaceSkuDto.getItemSize();
                        String[] roleSize = itemSize.replace("米", "").split("\\*");
                        if (roleSize.length == 2) {
                            try {
                                List<String> bedSize = Lists.newArrayList(request.getItemSize().replace("米", "").split("\\*"));
                                if (!(bedSize.containsAll(skuSize) && replaceSkuDto.getHeight() <= request.getSuggestMattressMaxHeight())) {
                                    replaceSkuDto.setErrorStatus(1);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
//                resultList.addAll(okList);
//                resultList.addAll(failList);
//                searchReplaceSkuDto.getPageInfo().setList(resultList);
        } else if (request.getItemType() != null && request.getItemType().equals(1)) {
            List<SearchReplaceSkuDto> dataList = searchReplaceSkuDto.getPageInfo().getList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<Integer> skuIdList = dataList.stream().map(o -> o.getSkuId().intValue()).collect(Collectors.toList());
                Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> integerSkuExtPropertyInfoMap = productProgramService.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(skuIdList, 4);
                if (MapUtils.isNotEmpty(integerSkuExtPropertyInfoMap)) {
                    for (SearchReplaceSkuDto replaceSkuDto : dataList) {
                        SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = integerSkuExtPropertyInfoMap.get(replaceSkuDto.getSkuId().intValue());
                        if (skuExtPropertyInfo != null) {
                            if (StringUtils.isNotBlank(skuExtPropertyInfo.getPropertyValue())) {
                                String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                                if (split.length >= 4) {
                                    replaceSkuDto.setSuggestMattressLength(Integer.parseInt(split[0]));
                                    replaceSkuDto.setSuggestMattressWidth(Integer.parseInt(split[1]));
                                    replaceSkuDto.setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                                    replaceSkuDto.setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                                }
                            }
                        }
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(searchReplaceSkuDto.getPageInfo().getList())) {
            searchReplaceSkuDto.getPageInfo().getList().forEach(searchReplaceSkuDto1 -> {
                if (request.getSearchCondition() != null && request.getSearchCondition().getFreeAble() != null && searchReplaceSkuDto1.getFurnitureType() != null && searchReplaceSkuDto1.getFurnitureType() == 2 && searchReplaceSkuDto1.getSkuId().equals(request.getSearchCondition().getBaseSkuId().longValue())) {
                    searchReplaceSkuDto1.setShowFreeFlag(4);
                }
                if (searchReplaceSkuDto1.getFreeFlag() == 1) {
                    searchReplaceSkuDto1.setShowFreeFlag(2);
                }
                if (request.getSearchCondition().getFreeAble() != null && request.getSearchCondition().getFreeAble().equals(1) && searchReplaceSkuDto1.getShowFreeFlag().equals(0)) {
                    searchReplaceSkuDto1.setShowFreeFlag(1);
                }
            });
            if (request.getItemType() != null && request.getItemType().equals(0)) {
                searchReplaceSkuDto.getPageInfo().getList().forEach(searchReplaceSkuDto1 -> {
                    List<Integer> pros = Lists.newArrayList(searchReplaceSkuDto1.getLength(), searchReplaceSkuDto1.getWidth(), searchReplaceSkuDto1.getHeight());
                    pros.removeIf(integer -> integer == null);
                    if (CollectionUtils.isNotEmpty(pros)) {
                        searchReplaceSkuDto1.setItemSize(pros.stream().map(integer -> String.valueOf(integer / 1000.0)).collect(Collectors.joining("*", "", "米")));
                    }
                });
            }
        }
        response.setSkuList(searchReplaceSkuDto.getPageInfo().getList());
        response.setFreeMessage(MORE_SOFT_MESSAGE);
        return response;
    }
}
