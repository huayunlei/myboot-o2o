package com.ihomefnt.o2o.service.proxy.sku;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.product.dto.SkuBaseInfoDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ProductWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.sku.SkuSearchProxy;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jerfan cang
 * @date 2018/8/31 9:57
 */
@Service
public class SkuSearchProxyImpl implements SkuSearchProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuProxyImpl.class);


    private static String MESSAGE_EXCEPTION_PARAM_EMPTY = "param can not be null . ";

    private static String MESSAGE_EXCEPTION_CALL_REMOTE_SERVER = "call remote server catch an exception .";

    private static Integer IMAGE_ICON_SIZE = 300;


    /**
     * 响应成功
     */
    private static Integer CODE_SUCCESS = 1;

    /**
     * 数据不存在或者远程服务内部异常 返回 null
     */
    private static Integer CODE_FAIL_OR_EXCEPTION = 2;

    /**
     * 参数请求错误
     */
    private static Integer CODE_RESPONSE_EMPTY = -1;

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Autowired
    private ProductProxy productProxy;

    @Autowired
    private ProductProgramService productProgramService;

    @Override
    public JSONObject queryConditionsByCategoryId(Integer levTwo, Integer levFour) throws RuntimeException {
        if (null == levTwo || null == levFour) {
            throw new RuntimeException(MESSAGE_EXCEPTION_PARAM_EMPTY);
        }
        JSONObject obj = null;
        JSONObject param = new JSONObject();
        param.put("categoryLevelTwo", levTwo);
        param.put("categoryLevelFour", levFour);
        JSONObject response = null;
        try {
            response = strongSercviceCaller.post(ProductWebServiceNameConstants.SERVER_NAME_QUERY_CONDITION, param, JSONObject.class);
            if (null == response
                    || null == response.getInteger("code")
                    || null == response.getJSONObject("data")) {
                throw new RuntimeException(ProductWebServiceNameConstants.SERVER_NAME_QUERY_CONDITION + " response is null . ");
            }
        } catch (Exception e) {
            throw new RuntimeException("call " + MESSAGE_EXCEPTION_CALL_REMOTE_SERVER + " exception , more info : " + e);
        }
        try {
            obj = getObjResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("resolving data from " + ProductWebServiceNameConstants.SERVER_NAME_QUERY_CONDITION + " exception , more info :" + e);
        }
        return obj;
    }

    @Override
    public JSONObject queryMoreSkuByCondition(JSONObject reqBean) throws RuntimeException {
        if (!paramByQueryMoreSku(reqBean)) {
            throw new RuntimeException("param is not valid . ");
        }
        JSONObject obj = null;
        JSONObject response = null;
        try {
            response = strongSercviceCaller.post(ProductWebServiceNameConstants.SERVER_NAME_QUERY_MORE_SKU_BY_CONDITION, reqBean, JSONObject.class);
        } catch (Exception e) {
            throw new RuntimeException("call remote server { " + ProductWebServiceNameConstants.SERVER_NAME_QUERY_MORE_SKU_BY_CONDITION + " } catch an exception , more info : " + e);
        }
        try {
            obj = getObjFromData(response, reqBean.getInteger("width"));
        } catch (Exception e) {
            throw e;
        }
        Integer suggestMattressMinHeight = reqBean.getInteger("suggestMattressMinHeight");
        Integer suggestMattressLength = reqBean.getInteger("suggestMattressLength");
        Integer suggestMattressWidth = reqBean.getInteger("suggestMattressWidth");
        Integer suggestMattressMaxHeight = reqBean.getInteger("suggestMattressMaxHeight");
        if (reqBean.get("itemType") != null && reqBean.getInteger("itemType").equals(2) && suggestMattressMinHeight != null && suggestMattressLength != null && suggestMattressWidth != null && suggestMattressMaxHeight != null) {
            JSONArray dataList = obj.getJSONArray("dataList");
            List<Integer> skuIdList = dataList.stream().map(o -> {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                return jsonObject.getInteger("skuId");
            }).collect(Collectors.toList());
            if (dataList != null && dataList.size() > 1) {
                List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(skuIdList);
                Map<Integer, SkuBaseInfoDto> skuBaseInfoDtoMap = skuBaseInfoDtos.stream().collect(Collectors.toMap(o -> o.getSkuId(), o -> o));
                JSONArray okList = new JSONArray();
                JSONArray failList = new JSONArray();
                for (Object o : dataList) {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                    Integer skuId = jsonObject.getInteger("skuId");
                    if (skuBaseInfoDtoMap.get(skuId) != null) {
                        SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoDtoMap.get(skuId);
                        jsonObject.put("height", skuBaseInfoDto.getHeight());
                        jsonObject.put("length", skuBaseInfoDto.getLength());
                        jsonObject.put("width", skuBaseInfoDto.getWidth());
                        jsonObject.put("itemType", 2);
                        if ((skuBaseInfoDto.getLength() != null && !skuBaseInfoDto.getLength().equals(suggestMattressLength))//长
                                || (skuBaseInfoDto.getWidth() != null && skuBaseInfoDto.getWidth().equals(suggestMattressWidth))//宽
                                || ((skuBaseInfoDto.getHeight() != null && (skuBaseInfoDto.getHeight() < suggestMattressMinHeight || skuBaseInfoDto.getHeight() > suggestMattressMaxHeight)))//厚度
                        ) {
                            failList.add(jsonObject);
                        } else {
                            okList.add(jsonObject);
                        }
                    }
                }
                JSONArray dataListForResult = new JSONArray();
                dataListForResult.addAll(okList);
                dataListForResult.addAll(failList);
                obj.put("dataList", dataListForResult);
            }
        } else if (reqBean.get("itemType") != null && reqBean.getInteger("itemType").equals(1)) {
            JSONArray dataList = obj.getJSONArray("dataList");
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<Integer> skuIdList = dataList.stream().map(o -> {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                    return jsonObject.getInteger("skuId");
                }).collect(Collectors.toList());
                Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> integerSkuExtPropertyInfoMap = productProgramService.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(skuIdList, 4);
                if (MapUtils.isNotEmpty(integerSkuExtPropertyInfoMap)) {
                    for (Object o : dataList) {
                        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                        if (integerSkuExtPropertyInfoMap.get(jsonObject.getInteger("skuId")) != null) {
                            SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = integerSkuExtPropertyInfoMap.get(jsonObject.getInteger("skuId"));
                            if (StringUtils.isNotBlank(skuExtPropertyInfo.getPropertyValue())) {
                                String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                                if (split.length >= 4) {
                                    jsonObject.put("suggestMattressLength", Integer.parseInt(split[0]));
                                    jsonObject.put("suggestMattressWidth", Integer.parseInt(split[1]));
                                    jsonObject.put("suggestMattressMinHeight", Integer.parseInt(split[2]));
                                    jsonObject.put("suggestMattressMaxHeight", Integer.parseInt(split[3]));
                                    jsonObject.put("itemType", 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return obj;
    }

    private JSONObject getObjFromData(JSONObject response, Integer width) throws RuntimeException {
        if (null == response || null == response.getJSONObject("data")) {
            throw new RuntimeException("result from remote server is null .");
        }
        JSONObject obj = new JSONObject();
        try {
            JSONObject data = response.getJSONObject("data");
            Integer totalNum = data.getInteger("totalNum");
            Integer pageIndex = data.getInteger("pageIndex");
            // 底层给的数据节点是 skuList
            JSONArray dataList = data.getJSONArray("skuList");
            JSONArray newDataList = new JSONArray();
            for (Object o : dataList) {
                JSONObject json = JSON.parseObject(o.toString());
                // 底层返回的节点是 skuImg
                String image = json.getString("skuImg");
                json.put("imgUrl", null);
                json.put("smallImage", null);
                json.put("itemType", 0);//普通sku
                if (null != image && !"".equals(image)) {
                    String imgUrl = QiniuImageUtils.compressImageAndSamePicTwo(image, width, -1);
                    String smallImage = QiniuImageUtils.compressImage(image, null, IMAGE_ICON_SIZE, IMAGE_ICON_SIZE);
                    json.put("imgUrl", imgUrl);
                    json.put("smallImage", smallImage);
                }
                newDataList.add(json);
            }
            obj.put("totalNum", totalNum);
            obj.put("pageIndex", pageIndex);
            obj.put("dataList", newDataList);
        } catch (Exception e) {
            LOGGER.error("resolving bean catch o2o-exception , more info  : {}", response.toJSONString());
            throw new RuntimeException("resolving bean from data catch an exception .");
        }

        return obj;
    }

    /**
     * 二级类目id 和 价格 是必传的参数
     *
     * @param param
     * @return
     */
    private boolean paramByQueryMoreSku(JSONObject param) {
        if (null == param || null == param.getInteger("categoryLevelTwoId")
                || null == param.getInteger("price")) {
            return false;
        } else {
            LOGGER.info("param is : " + param.toJSONString());
        }
        return true;
    }

    private JSONObject getObjResponse(JSONObject response) throws RuntimeException {
        JSONObject obj = null;
        Integer code = response.getInteger("code");
        /*
         * 1 成功
         * 2 没有数据 或 响应失败
         * -1 请求参数出错
         */
        if (CODE_SUCCESS.equals(code)) {
            obj = response.getJSONObject("data");
            LOGGER.info("response info from data is : " + obj.toString());
        } else if (CODE_FAIL_OR_EXCEPTION.equals(code)) {
            throw new RuntimeException("result from remoting server is null . ");
        } else if (-CODE_RESPONSE_EMPTY == code) {
            throw new RuntimeException("param in request was wrong . ");
        } else {
            throw new RuntimeException("unknown error . ");
        }
        return obj;
    }
}
