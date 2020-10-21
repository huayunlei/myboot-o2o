package com.ihomefnt.o2o.api.controller.sku;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.common.util.ApiResult;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.*;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QueryConditionByLastIdResponseVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QueryReplaceSkuByConditionsResponseVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QuerySkuDetailResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.proxy.sku.SkuProxy;
import com.ihomefnt.o2o.intf.proxy.sku.SkuSearchProxy;
import com.ihomefnt.o2o.intf.service.sku.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jerfan cang
 * @date 2018/8/30 14:12
 */
@RestController
@RequestMapping(value = "/sku")
@Api(tags = "【软装查看更多API】")
public class SkuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkuController.class);

    @Resource
    SkuSearchProxy skuSearchProxy;

    @Resource
    SkuProxy skuProxy;

    @Autowired
    SkuService skuService;

    /**
     * 根据二级类目和四级类目的id查询过滤条件
     *
     * @param req 请求bean
     * @return all the condition for search filter interface
     */
    @ApiOperation(value = "查询过滤条件", notes = "查询过滤条件")
    @RequestMapping(value = "/queryConditionsById", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> queryConditionsById(@RequestBody SkuQueryConditionRequestVo req) {
        if (null != req && null != req.getCategoryLevelTwo()
                && null != req.getCategoryLevelFour()) {
            try {
                JSONObject obj = skuSearchProxy.queryConditionsByCategoryId(req.getCategoryLevelTwo(), req.getCategoryLevelFour());
                return new ApiResult().success(obj, MessageConstant.SUCCESS);
            } catch (Exception e) {
                LOGGER.error("SkuController.queryConditionsByCategoryId o2o-exception , more info : ", e);
                return new ApiResult().fail(MessageConstant.FAILED);
            }
        } else {
            return new ApiResult().fail(MessageConstant.FAILED);
        }
    }

    /**
     * 条件查询更多可替换sku 二级类目和价格必传
     *
     * @param req request bean
     * @return bean object
     */
    @ApiOperation(value = "过滤查询更多的可替换sku", notes = "过滤查询更多的可替换sku")
    @RequestMapping(value = "/queryMoreByConditions", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> queryMoreSkuByConditions(@RequestBody SkuFilterConditionRequestVo req) {

        if (null == req || null == req.getCategoryLevelTwoId()
                || null == req.getPrice()) {
            return new ApiResult().fail(MessageConstant.FAILED);
        } else {
            JSONObject obj = null;
            // 调用服务
            try {
                JSONObject param = req.parseJson();
                obj = skuSearchProxy.queryMoreSkuByCondition(param);
                if (null == obj) {
                    LOGGER.error("SkuController.queryMoreByConditions response is null  ");
                    return new ApiResult().fail(MessageConstant.FAILED);
                }
            } catch (Exception e) {
                LOGGER.error("SkuController.queryMoreByConditions o2o-exception ,more info : ", e);
                return new ApiResult().fail(MessageConstant.FAILED);
            }
            return new ApiResult().success(obj, MessageConstant.SUCCESS);
        }
    }

//    /**
//     * @param entity req
//     * @return ResponseEntity
//     */
//    @ApiOperation(value = "查询硬装或软装SKU详情", notes = "查询硬装或软装SKU详情")
//    @RequestMapping(value = "/detail", method = RequestMethod.POST)
//    public ResponseEntity<HttpBaseResponse> loadSkuDetail(@RequestBody SkuDetailQueryRequestVo entity) {
//        if (null == entity || null == entity.getSkuId() || "".equals(entity.getSkuId().toString())) {
//            return new ApiResult().fail(MessageConstant.FAILED);
//        }
//        try {
//            JSONObject obj = null;
//            if (7 == entity.getProductType()) {
//                //可订制
//                obj = skuProxy.loadSkuDetail4Customer(entity.getSkuId(), entity.getWidth());
//            } else {
//                obj = skuProxy.loadSkuDetail(entity.getSkuId(), entity.getWidth());
//            }
//            return new ApiResult().success(obj, MessageConstant.SUCCESS);
//        } catch (Exception e) {
//            LOGGER.error("SkuController.loadSkuDetail exception , more info :  ", e);
//            return new ApiResult().fail(MessageConstant.FAILED);
//        }
//    }

    /**
     * 查询硬装或软装SKU详情
     *
     * @param request
     * @return ResponseEntity
     */
    @ApiOperation(value = "查询硬装或软装SKU详情", notes = "查询硬装或软装SKU详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public HttpBaseResponse<QuerySkuDetailResponseVo> loadSkuDetail2(@RequestBody SkuDetailQueryRequestVo request) {
        if (null == request || null == request.getSkuId() || "".equals(request.getSkuId().toString())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (request.getWidth() == null) {
            request.setWidth(Constants.DEFAULT_WIDTH);
        }
        QuerySkuDetailResponseVo response = skuService.querySkuDetail(request);
        if (response == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS,MessageConstant.DDATA_GET_FAILED);
        }
        return HttpBaseResponse.success(response);
    }


    /**
     * 根据末级类目查询过滤条件
     *
     * @param request
     * @return all the condition for search filter interface
     */
    @ApiOperation(value = "根据末级类目查询过滤条件", notes = "根据末级类目查询过滤条件")
    @RequestMapping(value = "/v540/queryConditionByLastCategoryId", method = RequestMethod.POST)
    public HttpBaseResponse<QueryConditionByLastIdResponseVo> queryConditionByLastCategoryId(
            @RequestBody QueryConditionByLastIdRequestVo request) {
        if (null == request || null == request.getLastCategoryId()) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        QueryConditionByLastIdResponseVo response = skuService.queryConditionByLastCategoryId(request);
        if (response == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }

    /**
     * 条件查询更多可替换sku
     *
     * @param request
     * @return bean object
     */
    @ApiOperation(value = "过滤查询更多的可替换sku", notes = "过滤查询更多的可替换sku")
    @RequestMapping(value = "/v540/queryReplaceSkuByConditions", method = RequestMethod.POST)
    public HttpBaseResponse<QueryReplaceSkuByConditionsResponseVo> queryReplaceSkuByConditions(
            @RequestBody QueryReplaceSkuByConditionsRequestVo request) {
        if (null == request || request.getSearchCondition() == null ||
                request.getSearchCondition().getBaseSkuCount() == null ||
                request.getSearchCondition().getBaseSkuId() == null ||
                request.getSearchCondition().getBaseSkuTotalPrice() == null ||
                request.getSearchCondition().getFurnitureType() == null ||
                CollectionUtils.isEmpty(request.getSearchCondition().getCategoryIdList()) ||
                CollectionUtils.isEmpty(request.getSearchCondition().getExcludeSkuIdList())
        ) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        QueryReplaceSkuByConditionsResponseVo response = skuService.queryReplaceSkuByConditions(request);
        if (response == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(response);
    }


//    /**
//     * 查询硬装或软装SKU详情
//     * @param request
//     * @return HttpBaseResponse
//     */
//    @ApiOperation(value = "批量查询sku详情", notes = "批量查询sku详情")
//    @PostMapping(value = "/detailList")
//    public HttpBaseResponse<List<QuerySkuDetailResponseVo>> detailList(@RequestBody SkuDetailQueryRequestVo request) {
//        List<QuerySkuDetailResponseVo> response =skuService.skuDetailList(request);
//        return HttpBaseResponse.success(response);
//    }

}
