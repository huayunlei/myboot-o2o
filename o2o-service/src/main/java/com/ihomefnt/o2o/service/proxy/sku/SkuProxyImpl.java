package com.ihomefnt.o2o.service.proxy.sku;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.optional.dto.CustoOptionalResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CustomItemResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.QueryProductDtoBySkuIdsDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.TreeNodeResponseDto;
import com.ihomefnt.o2o.intf.domain.product.dto.QueryConditionByCategoryDto;
import com.ihomefnt.o2o.intf.domain.product.dto.SearchReplaceSkuResponseDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ProductWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.sku.SkuProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cang
 */
@Service
public class SkuProxyImpl implements SkuProxy {
    /**
     * 调用服务端成功的错误码 {1} 表示成功
     */
    private static final Integer CODE_SUCCESS = 1;

    /**
     * 硬装标识 {6} 标识硬装，其它为软装
     */
    private static final Integer MARK_HARD_SKU = 6;

    /**
     * 空值 常量
     */
    private static final String VALUE_EMPTY = "";

    private static final Integer IMAGE_SMALL_SIZE = 100;

    @Autowired
	private StrongSercviceCaller strongSercviceCaller;

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuProxyImpl.class);

    public SkuProxyImpl() {
        super();
    }


    @Override
    public JSONObject loadSkuDetail(Integer skuId, Integer width) throws Exception {
        JSONObject obj;
        // 参数非空校验 参数异常抛出一个运行时异常
        if (null == skuId) {
            throw new RuntimeException("ServiceCaller.loadSkuDetail  param is null . ");
        }
        try {
            List<Integer> list = new ArrayList<>();
            list.add(skuId);
            //TODO 先检索redis ，redis 无此缓存 则调用服务
            ResponseVo responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.URL_SKU_DETAIL, list, ResponseVo.class);
            JSONObject response = new JSONObject();
            response.put("code", responseVo.getCode());
            response.put("msg", responseVo.getMsg());
            response.put("data", responseVo.getData());
            // 封装数据
            obj = getObj(this, response, width);
            if (null == obj) {
                throw new RuntimeException(" data from " + ProductWebServiceNameConstants.URL_SKU_DETAIL + " is null .");
            } else {
                //TODO 对服务端响应数据做缓存 redis 过期时间为24H
            }
        } catch (Exception e) {
            throw new Exception("call " + ProductWebServiceNameConstants.URL_SKU_DETAIL + " exception , more info : ", e);
        }
        return obj;
    }

    @Override
    public List<QueryProductDtoBySkuIdsDto> queryProductDtoBySkuIds(Object param) {
        ResponseVo<List<QueryProductDtoBySkuIdsDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.URL_SKU_DETAIL, param,
                    new TypeReference<ResponseVo<List<QueryProductDtoBySkuIdsDto>>>() {
                    });
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        return responseVo.getData();
    }

    private JSONObject convert(CustoOptionalResponseDto data, Integer width) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuId", data.getSkuId());
        jsonObject.put("imageUrl", StringUtils.isNotBlank(data.getImageUrl()) ? QiniuImageUtils.compressImageAndSamePicTwo(data.getImageUrl(), width, -1) : "");
        if(CollectionUtils.isNotEmpty(data.getImageUrlList())){
            jsonObject.put("imageUrlList",data.getImageUrlList().stream().map(imageUrl->
                    StringUtils.isNotBlank(imageUrl) ? QiniuImageUtils.compressImageAndSamePicTwo(imageUrl, width, -1) : "").collect(Collectors.toList()));
        }
        jsonObject.put("spuName", data.getSpuName());
        jsonObject.put("length", data.getLength());
        jsonObject.put("width", data.getWidth());
        jsonObject.put("height", data.getHeight());
        jsonObject.put("maxLength", data.getMaxLength());
        jsonObject.put("maxWidth", data.getMaxWidth());
        jsonObject.put("maxHeight", data.getMaxHeight());
        jsonObject.put("minLength", data.getMinLength());
        jsonObject.put("minWidth", data.getMinWidth());
        jsonObject.put("minHeight", data.getMinHeight());
        jsonObject.put("aijiaPrice", data.getAijiaPrice());
        jsonObject.put("purchasePrice", data.getPurchasePrice());
        JSONArray customItemList = new JSONArray();
        jsonObject.put("customItemList", customItemList);
        jsonObject.put("measure", data.getMeasure());
        List<CustomItemResponseDto> customItems = data.getCustomItemList();
        if (CollectionUtils.isNotEmpty(customItems)) {
            for (CustomItemResponseDto item : customItems) {
                JSONObject jsonObjectTemp = new JSONObject();
                jsonObjectTemp.put("title", item.getName());
//                jsonObjectTemp.put("images", searchImages(item.getAttrs(), Lists.newArrayList(), width));//不查询图片
                if (CollectionUtils.isNotEmpty(item.getAttrs())) {
                    for (TreeNodeResponseDto attrs : item.getAttrs()) {
                        jointValue(attrs.getName(), attrs, jsonObjectTemp, null, false);
                    }
                }
                customItemList.add(jsonObjectTemp);
            }
        }
        return jsonObject;
    }

    /**
     * 递归拼接sku选配详情的值
     *
     * @param treeNodeRes
     * @param jsonObject
     * @param sb
     * @return
     */
    private JSONObject jointValue(String parentName, TreeNodeResponseDto treeNodeRes, JSONObject jsonObject, StringBuilder sb, boolean isJoin) {
        if (treeNodeRes == null || treeNodeRes.getNodeType() == null || jsonObject == null) {
            LOGGER.error("jointValue params is null ");
            throw new BusinessException("参数为空");
        }
        boolean flag = treeNodeRes.getNodeType().equals(2);
        if (sb == null) {
            sb = new StringBuilder();
        }
        if (!flag) {
            if (isJoin && sb.length() != 0) {
                sb.append("/");
            }
            if (isJoin && !flag) {
                sb.append(treeNodeRes.getName());
            }
        }
        JSONArray info = jsonObject.getJSONArray("info");
        if (info == null) {
            info = new JSONArray();
            jsonObject.put("info", info);
        }
        if (CollectionUtils.isNotEmpty(treeNodeRes.getAttrs())) {
            if (treeNodeRes.getAttrs().size() == 1) {
                jointValue(parentName, treeNodeRes.getAttrs().get(0), jsonObject, sb, true);
            } else {
                List<JSONObject> list = Lists.newArrayList();
                info.add(Maps.newHashMap("name", parentName, "value", sb.toString(), "attrs", list));
                for (TreeNodeResponseDto attr : treeNodeRes.getAttrs()) {
                    list.add(jointValue(attr.getName(), attr, new JSONObject(), null, false));
                }
            }
        } else {
            info.add(Maps.newHashMap("name", parentName, "value", sb.toString()));
        }
        return jsonObject;
    }

    /**
     * @param skuServer
     * @param info      底层服务的响应json报文
     * @return obj obj是从info中抽取出来的客户端需要展示的数据节点
     * @throws RuntimeException exception
     */
    public static JSONObject getObj(SkuProxyImpl skuServer, JSONObject info, Integer width) throws RuntimeException {
        JSONObject obj = new JSONObject();
        if (null == info) {
            throw new RuntimeException(ProductWebServiceNameConstants.URL_SKU_DETAIL + "response is null .");
        }
        // success = true  and code = 1  and msg =成功
        if (info.getString("code").equals(CODE_SUCCESS + "")) {
            // get data note info , and it's a bean object in array ,index =0
            JSONArray data = info.getJSONArray("data");
            if (null == data || data.size() <= 0) {
                throw new RuntimeException(ProductWebServiceNameConstants.URL_SKU_DETAIL + "response {data note }is null .");
            }
            // get the first value in data array
            JSONObject content = data.getJSONObject(0);
            /*
             * 获取 skuId categoryName productName materialDesc brandName
             * 说明 brandName 需要封装到属性里
             */
            Integer skuId = content.getInteger("skuId");
            String categoryName = content.getString("categoryName");
            String productName = content.getString("productName");
            Integer lastCategoryId = content.getInteger("lastCategoryId");
            String lastCategoryName = content.getString("lastCategoryName");


            /*
             * 工艺节点封装 仅硬装有工艺节点 且硬装的type值为6
             */
            if (content.getString("type").equals(MARK_HARD_SKU + "")) {
                JSONArray craftVoList = content.getJSONArray("craftVoList");
                JSONArray HandleCraftVoList = new JSONArray();
                //json的key保持和底层一致
                if (null != craftVoList && craftVoList.size() > 0) {
                    for (int i = 0; i < craftVoList.size(); i++) {
                        JSONObject craftJson = JSON.parseObject(craftVoList.get(i).toString());
                        String url = craftJson.getString("url");
                        if (null != url && !VALUE_EMPTY.equals(url)) {
                            url = QiniuImageUtils.compressImageAndSamePicTwo(url, IMAGE_SMALL_SIZE, IMAGE_SMALL_SIZE);
                        }
                        craftJson.put("url", url);
                        HandleCraftVoList.add(craftJson);
                    }
                    obj.put("craftVoList", HandleCraftVoList);
                }
            }

            JSONArray attributes = new JSONArray();
            JSONArray propertyList = content.getJSONArray("propertyList");
            //把品牌封装到属性里
            JSONObject brandJson = new JSONObject();
            brandJson.put("key", "品牌");
            String brandName = content.getString("brandName");
            brandJson.put("value", brandName);
            /**
             *  把品牌放到属性列表的第一位
             */
            attributes.add(0, brandJson);


            // 类目改造，product-web修改返回，软装属性统一放到了propertyList；图片放到外层

            JSONArray images = null;
            if(propertyList != null ){
                for (Object property : propertyList) {
                    JSONObject json = JSON.parseObject(property.toString());
                    String attrName = json.getString("propertyName");
                    String attrValue = json.getString("propertyValue");
                    // 仅属性名和属性值不为空时候才封装到属性列表
                    if (null != attrName && !"".equals(attrName)
                            && null != attrValue && !"".equals(attrValue)) {
                        JSONObject attribute = new JSONObject();
                        attribute.put("key", attrName);
                        attribute.put("value", attrValue);
                        // 产地放到第二位，新版接口，产地从外层移到了propertyList
                        if("产地".equals(attrName)){
                            attributes.add(1, attribute);
                        }else {
                            attributes.add(attribute);
                        }
                    }
                }
                attributes=filterSizeStr(attributes);//去除原有的尺寸（其实是规格）
                attributes=filterUnDisplayStr(attributes);//去除不需要的属性
                // 图片放到外层
                images = skuServer.getImages(content.getJSONArray("images"), width);

            }else{
                /*
                 *  获取 images
                 *  说明 底层数据images 非标准的jsonArray格式
                 */
                String[] skuPvsIds = null;
                if (content.containsKey("skuList")) {
                    JSONArray skuList = content.getJSONArray("skuList");
                    if(skuList!=null&&skuList.size()>0){
                        for (int i = 0; i < skuList.size(); i++) {
                            JSONObject skuList_content = JSONObject.parseObject(content.getJSONArray("skuList").get(i).toString());
                            if(skuId.equals(skuList_content.getInteger("id"))){
                                if (skuList_content.containsKey("skuPvsId")&&skuList_content.containsKey("skuPvs")) {
                                    skuPvsIds = skuList_content.getString("skuPvsId").split(";");
                                }
                                if (skuList_content.containsKey("images")) {
                                    JSONArray imageArray = skuList_content.getJSONArray("images");
                                    images = skuServer.getImages(imageArray, width);
                                }
                                break;
                            }
                        }
                    }
                }

                // 属性来源有两个 productSkuPropertyList 和 productSpuPropertyList
                JSONArray skuProperties = content.getJSONArray("productSkuPropertyList");
                JSONArray skuAttributes = skuServer.getSkuAttributes(skuProperties,skuPvsIds);
                JSONArray spuProperties = content.getJSONArray("productSpuPropertyList");
                JSONArray spuAttributes = skuServer.getAttributes(spuProperties);
                // attributes 作为相应信息属性节点返回
                JSONArray skuSpuattributes = skuServer.jsonArrayMerge(skuAttributes, spuAttributes);
                skuSpuattributes=filterSizeStr(skuSpuattributes);//去除原有的尺寸（其实是规格）
                JSONArray skuList = content.getJSONArray("skuList");
                JSONArray skuExtraList = skuServer.getExtraAttributes(skuList,skuId);
                attributes = skuServer.jsonArrayMerge(skuSpuattributes, skuExtraList);
                attributes = filterUnDisplayStr(attributes);//去除不需要的属性
                if(content.get("originName")!=null){
                    //地区属性
                    JSONObject origin = new JSONObject();
                    origin.put("key", "产地");
                    origin.put("value", content.get("originName"));
                    attributes.add(1, origin);
                }


            }

            if(content.get("styleName")!=null){
                //地区属性
                JSONObject origin = new JSONObject();
                origin.put("key", "风格");
                origin.put("value", content.get("styleName"));
                if(content.get("originName")!=null){
                    attributes.add(2, origin);
                }else{
                    attributes.add(1, origin);
                }
            }
            String itemSize = content.getString("itemSize");
            if(null != itemSize && !VALUE_EMPTY.equals(itemSize)){
                JSONObject origin = new JSONObject();
                origin.put("key", "尺寸");
                origin.put("value", itemSize);
                attributes.add(origin);
            }

            //sku的节点封装到obj对象里
            obj.put("attributes", attributes);
            if (null != skuId) {
                obj.put("skuId", skuId);
            } else {
                throw new RuntimeException("id of sku can not be null . ");
            }
            if (null != categoryName && !VALUE_EMPTY.equals(categoryName)) {
                obj.put("categoryName", categoryName);
            } else {
                obj.put("categoryName", null);
            }
            if (null != productName && !VALUE_EMPTY.equals(productName)) {
                obj.put("productName", productName);
            } else {
                obj.put("productName", null);
            }
            String materialDesc = content.getString("materialDesc");
            if (null != materialDesc && !VALUE_EMPTY.equals(materialDesc)) {
                obj.put("desc", materialDesc);
            } else {
                obj.put("desc", null);
            }
            if (null != images && images.size() > 0) {
                obj.put("images", images);
            } else {
                obj.put("images", null);
            }

            // 增加末级类目
            if (null != lastCategoryId && lastCategoryId > 0) {
                obj.put("lastCategoryId", lastCategoryId);
            } else {
                obj.put("lastCategoryId", 0);
            }
            if (null != lastCategoryName && !VALUE_EMPTY.equals(lastCategoryName)) {
                obj.put("lastCategoryName", lastCategoryName);
            } else {
                obj.put("lastCategoryName", null);
            }


        } else {
            throw new RuntimeException("response from underlying server is wrong ... ");
        }
        return obj;
    }

    /**
     * 封装 image
     *
     * @param imageArray jsonArray
     * @return images jsonArray
     */
    private JSONArray getImages(final JSONArray imageArray, Integer width) {
        JSONArray images = new JSONArray();
        if (null != imageArray && imageArray.size() > 0) {
            for (Object imageObj : imageArray) {
                JSONObject image = new JSONObject();
                String url = imageObj.toString();
                if (null != url && !"".equals(url)) {
                    url = QiniuImageUtils.compressImageAndSamePicTwo(url, width, -1);
                    image.put("url", url);
                    images.add(image);
                }
            }
        } else {
            return null;
        }
        return images;
    }

    /**
     * 封装sku属性
     * @param properties
     * @param skuPvsIds
     * @return
     */
    private JSONArray getSkuAttributes(JSONArray properties, String[] skuPvsIds) {
        JSONArray attributes = new JSONArray();
        if(CollectionUtils.isEmpty(properties)){
            return attributes;
        }
        for (Object property : properties) {
            JSONObject json = JSON.parseObject(property.toString());
            String attrName = json.getString("propertyName");
            String attrValue = json.getString("propertyValue");
            JSONObject attribute = new JSONObject();
            /*
             * 仅属性名和属性值不为空时候才封装到属性列表
             */
            if (null != attrName && !"".equals(attrName)
                    && null != attrValue && !"".equals(attrValue)) {
                attribute.put("key", attrName);
                if (attrValue.indexOf(",")>-1){//多个skuid属性，取出正确的
                    String[] propertyExt = json.getString("propertyExt").split(",");
                    String[] attrValueSplit = attrValue.split(",");
                    if(skuPvsIds!=null){
                        for (int i = 0; i < propertyExt.length; i++) {
                            for (int j = 0; j < skuPvsIds.length; j++) {
                                if(propertyExt[i].equals(skuPvsIds[j])){
                                    attribute.put("value", attrValueSplit[i]);
                                }
                            }
                        }
                    }

                }else{
                    attribute.put("value", attrValue);
                }
                attributes.add(attribute);
            }

        }
        return attributes;
    }


    /**
     * 封装sku属性
     * @param properties
     * @return
     */
    private JSONArray getExtraAttributes(JSONArray properties,Integer skuId ) {
        JSONArray attributes = new JSONArray();
        if(CollectionUtils.isEmpty(properties)){
            return attributes;
        }
        for (Object property : properties) {
            JSONObject json = JSON.parseObject(property.toString());
            if(skuId.equals(json.get("id"))){
            JSONArray skuExtraFields = json.getJSONArray("skuExtraFields");

            if (CollectionUtils.isNotEmpty(skuExtraFields)) {
                JSONObject sizeJson = new JSONObject();
                sizeJson.put("key", "尺寸");
                double length = json.getDouble("length");

                double width = json.getDouble("width");

                double height = json.getDouble("height");

                sizeJson.put("value", "长"+String.valueOf((length) / 1000)+"米，宽"+String.valueOf((width) / 1000)+"米，高"+String.valueOf((height) / 1000)+"米");
                attributes.add(sizeJson);

                for (Object skuExtraField : skuExtraFields) {
                    JSONObject field = JSON.parseObject(skuExtraField.toString());
                    String skuFieldName = field.getString("skuFieldName");
                    String skuFieldValue = field.getString("skuFieldValue");
                    JSONObject attribute = new JSONObject();
                    /*
                     * 仅属性名和属性值不为空时候才封装到属性列表
                     */
                    if (StringUtils.isNotBlank(skuFieldName) && StringUtils.isNotBlank(skuFieldValue)) {
                        if (skuFieldValue.indexOf(";") > -1) {//多个尺寸
                            if (StringUtils.countMatches(skuFieldValue, ";") == 7) {//儿童床上下层特殊处理
                                JSONObject attributeSec = new JSONObject();
                                String levelfirValue = skuFieldValue.substring(0, StringUtils.ordinalIndexOf(skuFieldValue, ";", 4));//第一层
                                levelfirValue = getSkuFieldValue(levelfirValue);
                                String levelSecValue = skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 4) + 1);//第二层
                                levelSecValue = getSkuFieldValue(levelSecValue);
                                attribute.put("key", "床垫尺寸（第一层）");
                                attribute.put("value", levelfirValue);
                                attributes.add(attribute);
                                attributeSec.put("key", "床垫尺寸（第二层）");
                                attributeSec.put("value", levelSecValue);
                                attributes.add(attributeSec);
                                continue;
                            } else if (StringUtils.countMatches(skuFieldValue, ";") == 3
                                    || StringUtils.countMatches(skuFieldValue, ";") == 2) {//兼容高只有一个的情况
                                skuFieldName = "床垫尺寸";//过来的内容与需求不一致
                                skuFieldValue = getSkuFieldValue(skuFieldValue);
                            }
                        } else {
                            skuFieldValue = (parseDouble(skuFieldValue)) / 1000 + "米";
                        }

                        attribute.put("key", skuFieldName);
                        attribute.put("value", skuFieldValue);
                        attributes.add(attribute);
                    }

                }


            }
        }
        }
        return attributes;
    }


    /**
     * 床品类SkuFieldValue属性重新组装
     * @return
     */
    private String getSkuFieldValue(String skuFieldValue){
        String length=skuFieldValue.substring(0,skuFieldValue.indexOf(";"));
        String width=skuFieldValue.substring(skuFieldValue.indexOf(";")+1,StringUtils.ordinalIndexOf(skuFieldValue, ";", 2));
        String heightMin= "";
        if(StringUtils.countMatches(skuFieldValue, ";")==2) {//高只有一个
            heightMin=skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 2)+1);
        }else {
            heightMin=skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 2)+1,
                    StringUtils.ordinalIndexOf(skuFieldValue, ";", 3));
        }

        String heightMax = "";
        Double heightMaxDouble = 0.0;
        if(StringUtils.countMatches(skuFieldValue, ";")==3){
             heightMax=skuFieldValue.substring(StringUtils.ordinalIndexOf(skuFieldValue, ";", 3)+1);
             heightMaxDouble=(parseDouble(heightMax))/1000;
        }
        Double lengthDouble=(parseDouble(length))/1000;
        Double widthDouble=(parseDouble(width))/1000;
        if(heightMin.indexOf("~")>-1){
            heightMax=heightMin.substring(heightMin.indexOf("~")+1);
            heightMaxDouble=(parseDouble(heightMax))/1000;
            heightMin=heightMin.substring(0,heightMin.indexOf("~"));
        }
        Double heightMinDouble=(parseDouble(heightMin))/1000;
        if(StringUtils.countMatches(skuFieldValue, ";")==2){//高只有一个
            skuFieldValue="长"+lengthDouble+"米，宽"+widthDouble+"米，高"+heightMinDouble+"米";
        }else if(heightMax.equals(heightMin)){
            skuFieldValue="长"+lengthDouble+"米，宽"+widthDouble+"米，高"+heightMinDouble+"米";
        }else{
            skuFieldValue="长"+lengthDouble+"米，宽"+widthDouble+"米，高"+heightMinDouble+"米~"+heightMaxDouble+"米";
        }
        return skuFieldValue;
    }

    /**
     * 属性类字段，转double空和null类型兼容，默认为0
     * @param length
     * @return
     */
    private Double parseDouble(String length){
        if("".equals(length)||length==null){
            length="0";
        }
        return Double.parseDouble(length);

    }
    /**
     * 封装属性
     *
     * @param properties attributes jsonArray
     * @return attribute jsonArray
     */
    private JSONArray getAttributes(JSONArray properties) {
        JSONArray attributes = new JSONArray();
        if(CollectionUtils.isEmpty(properties)){
            return attributes;
        }
        for (Object property : properties) {
            JSONObject json = JSON.parseObject(property.toString());
            String attrName = json.getString("propertyName");
            String attrValue = json.getString("propertyValue");
            String baseAttrValue = json.getString("propertyValue");
            String propertyType = json.getString("propertyType");
            if(!"check".equals(propertyType)){
                JSONArray templatePropertyValueList = json.getJSONArray("templatePropertyValueList");
                if (null != baseAttrValue && null != templatePropertyValueList
                        && templatePropertyValueList.size() > 0) {
                    for (Object template : templatePropertyValueList) {
                        JSONObject templateJson = JSON.parseObject(template.toString());
                        String value = templateJson.getString("id");
                        if (value.equals(attrValue)) {
                            attrValue = templateJson.getString("propertyValue");
                        }
                    }
                    // 线上bug 修改 by jerfan cang
                    /*
                     * 如果 attrValue 没有匹配到值 则设置
                     */
                    if (baseAttrValue.equals(attrValue)) {
                        attrValue = "";
                    }
                }
            }
            JSONObject attribute = new JSONObject();
            /*
             * 仅属性名和属性值不为空时候才封装到属性列表
             */
            if (null != attrName && !"".equals(attrName)
                    && null != attrValue && !"".equals(attrValue)) {
                attribute.put("key", attrName);

                attribute.put("value", attrValue);
                attributes.add(attribute);
            } else {
                continue;
            }

        }
        return attributes;
    }


    /**
     * 两个 jsonArray 合并
     *
     * @param a jsonArray
     * @param b jsonArray
     * @return new jsonArray form param a and param b
     */
    private JSONArray jsonArrayMerge(final JSONArray a, final JSONArray b) {
        JSONArray result = a;
        if (null == result) {
            result = new JSONArray();
        }
        if (null != b && b.size() > 0) {
            for (int i = 0; i < b.size(); i++) {
                JSONObject json = b.getJSONObject(i);
                result.add(json);
            }
        }
        return result;
    }

    /**
     * 规格中'尺寸'去掉
     * @param attributes
     * @return
     */
    private static JSONArray filterSizeStr(JSONArray attributes){

        for (int i = 0; i < attributes.size(); i++) {
            JSONObject json = JSON.parseObject(attributes.get(i).toString());
            if("尺寸".equals(json.get("key"))){//规格中'尺寸'去掉
                attributes.remove(attributes.get(i));
                i--;
            }
        }
        return attributes;
    }

    /**
     * 过滤一些不需要的属性
     * @param attributes
     * @return
     */
    private static JSONArray filterUnDisplayStr(JSONArray attributes){
        if(CollectionUtils.isEmpty(attributes)){
            return attributes;
        }
        for (int i = 0; i < attributes.size(); i++) {
            JSONObject json = JSON.parseObject(attributes.get(i).toString());
            if("硬装是否需要预留电源插座位置".equals(json.get("key"))||"厂家是否可更换颜色".equals(json.get("key"))){
                attributes.remove(attributes.get(i));
                i--;
            }
        }
        return attributes;
    }

    @Override
    public QueryConditionByCategoryDto queryConditionByCategory(Object param) {
        ResponseVo<QueryConditionByCategoryDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.SEARCH_QUERY_CONDITION_BY_CATEGORY, param,
                    new TypeReference<ResponseVo<QueryConditionByCategoryDto>>() {
                    });
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        if(responseVo.getData().getSecondCategory() == null || CollectionUtils.isEmpty(responseVo.getData().getCategoryList()) ||
                CollectionUtils.isEmpty(responseVo.getData().getPropertyList())){
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_DATE_EMPTY, MessageConstant.DATE_EMPTY);
        }
        return responseVo.getData();
    }

    @Override
    public SearchReplaceSkuResponseDto queryReplaceSkuByConditions(Object param) {
        System.out.println(JsonUtils.obj2json(param));
        ResponseVo<SearchReplaceSkuResponseDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.SEARCH_REPLACE_SKU, param,
                    new TypeReference<ResponseVo<SearchReplaceSkuResponseDto>>() {
                    });
        } catch (Exception e) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        return responseVo.getData();
    }
}
