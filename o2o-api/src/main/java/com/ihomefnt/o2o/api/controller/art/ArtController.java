package com.ihomefnt.o2o.api.controller.art;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.art.dto.Artist;
import com.ihomefnt.o2o.intf.domain.art.dto.Content;
import com.ihomefnt.o2o.intf.domain.art.dto.HttpArtworkListWithFilterRequest;
import com.ihomefnt.o2o.intf.domain.art.dto.HttpShareArtWorkRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.log.vo.request.LogRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubmitOrderResponse;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.art.ArtWordService;
import com.ihomefnt.o2o.intf.service.log.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 艺术品
 *
 * @author Charl
 * @version V1.0
 * @Title: ArtController.java
 * @Description:
 * @date 2016年7月15日 下午12:52:33
 */
@ApiIgnore
@RestController
@Deprecated
@Api(tags = "【APP艺术品】API", hidden = true)
@RequestMapping("/art")
public class ArtController {

    @Autowired
    ArtService artService;
    @Autowired
    LogService LogService;
    @Autowired
    ArtWordService artWordService;

    private static final int ExRate = 100;//汇率

    /**
     * 艺术品列表页
     */
    @ApiOperation(value = "list", notes = "艺术品列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtListResponse> getArtworkList(@Json HttpArtListRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpArtListResponse artworkList = artService.getArtworkList(request);
        return HttpBaseResponse.success(artworkList);
    }

    @ApiOperation(value = "listWithParam", notes = "listWithParam")
    @RequestMapping(value = "/listWithParam", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtListResponse> getArtworkListWithParam(@Json HttpArtListWithParamRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpArtListResponse artworkList = artService.getArtworkListByParam(request);
        return HttpBaseResponse.success(artworkList);
    }

    /**
     * 艺术品详情页
     */
    @ApiOperation(value = "detail", notes = "艺术品详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkDetailResponse> getArtworkDetail(@Json HttpArtworkDetailRequest request) {
        if (null == request || null == request.getArtworkId()) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpArtworkDetailResponse artworkDetail = artService.getArtworkDetail(request);
        if (null == artworkDetail) {
            return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, "该商品已下架");
        }
        return HttpBaseResponse.success(artworkDetail);
    }

    /**
     * 艺术家主页
     */
    @ApiOperation(value = "artistHome", notes = "艺术家主页")
    @RequestMapping(value = "/artistHome", method = RequestMethod.POST)
    public HttpBaseResponse<Artist> artistHome(@Json HttpArtistHomeRequest request) {
        if (null == request || null == request.getArtistId()) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        Artist artistInfo = artService.getArtistInfo(request);
        return HttpBaseResponse.success(artistInfo);
    }

    /**
     * 分享、喜欢艺术品
     */
    @ApiOperation(value = "shareArtwork", notes = "分享、喜欢艺术品")
    @RequestMapping(value = "/shareArtwork", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkShareResponse> shareArtwork(@Json HttpShareArtWorkRequest request) {
        if (null == request || request.getTypeId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpArtworkShareResponse artworkShareInfoById = artService.getArtworkShareInfoById(request);
        if (request.getTypeId() == 4 && null != artworkShareInfoById) {
            return HttpBaseResponse.success(artworkShareInfoById);
        } else if (request.getTypeId() == 3 && null != artworkShareInfoById) {
            return HttpBaseResponse.success(null);
        }
        return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.ITEM_NOT_SUPPORT);
    }

    /**
     * 艺术家首页分享
     */
    @ApiOperation(value = "shareArtist", notes = "艺术家首页分享")
    @RequestMapping(value = "/shareArtist", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkShareResponse> shareArtist(@Json HttpShareArtWorkRequest request) {
        if (null == request || request.getShareId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpArtistHomeRequest artistHomeRequest = ModelMapperUtil.strictMap(request, HttpArtistHomeRequest.class);
        artistHomeRequest.setArtistId(request.getShareId());
        Artist artistInfo = artService.getArtistInfo(artistHomeRequest);
        if (null == artistInfo) {
            return HttpBaseResponse.fail(MessageConstant.PRODUCT_NOT_EXISTS);
        }
        HttpArtworkShareResponse response = new HttpArtworkShareResponse();
        Content content = new Content();
        content.setTitle("艺术家的个人主页-" + (null == artistInfo.getName() ? "" : artistInfo.getName()));
        content.setUrl(null == artistInfo.getAvast() ? "" : artistInfo.getAvast());
        content.setDesc("一段精彩的人生，一些有故事的作品，艾佳生活为您讲述这段传奇经历。");
        response.setShareContent(content);
        response.setmUrl("https://m.ihomefnt.com/artist/artisthome?artistId=" + artistInfo.getArtistId());
        return HttpBaseResponse.success(response);
    }

    /**
     * 艺术品库存校验
     */
    @ApiOperation(value = "stockCheck", notes = "艺术品库存校验")
    @RequestMapping(value = "/stockCheck", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkOrderResponse> stockCheck(@Json HttpArtworkDetailRequest request) {
        if (null == request || request.getArtworkId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpArtworkOrderResponse response = new HttpArtworkOrderResponse();
        long stock = artService.getArtworkStockById(request);
        response.setStock(stock);

        if (stock <= 0) {
            return HttpBaseResponse.fail(HttpResponseCode.STOCK_IS_ZERO, MessageConstant.STOCK_ZERO);
        }
        return HttpBaseResponse.success(response);
    }

    /**
     * 确认订单页面艺术品信息
     */
    @ApiOperation(value = "artworkOrder", notes = "确认订单页面艺术品信息")
    @RequestMapping(value = "/artworkOrder", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkOrderResponse> artworkOrder(@Json HttpArtworkDetailRequest request) {
        if (null == request || request.getArtworkId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpArtworkOrderResponse artworkInfo = artService.getArtworkOrderInfoById(request);
        if (null == artworkInfo) {
            return HttpBaseResponse.fail(MessageConstant.ITEM_NOT_SUPPORT);
        }
        Long stock = artworkInfo.getStock();
        artworkInfo.setExRate(ExRate);
        if (stock <= 0) {
            return HttpBaseResponse.fail(HttpResponseCode.STOCK_IS_ZERO, MessageConstant.STOCK_ZERO);
        }
        return HttpBaseResponse.success(artworkInfo);
    }

    /**
     * 艺术品筛选接口
     */
    @ApiOperation(value = "艺术品筛选项接口", notes = "filterInfo")
    @RequestMapping(value = "/filterInfo", method = RequestMethod.POST)
    public HttpBaseResponse<Map<String, Object>> getFilterInfo(@Json HttpBaseRequest request) {
        Map<String, Object> response = artService.getArtworkFilterInfo();
        return HttpBaseResponse.success(response);
    }

    /**
     * 艺术品筛选列表
     */
    @ApiOperation(value = "艺术品列表（筛选）", notes = "getArtworkListWithParam")
    @RequestMapping(value = "/getArtworkListWithFilter", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtListResponse> getArtworkListWithParam(@Json HttpArtworkListWithFilterRequest request) {
        HttpArtListResponse artworksByFilters = new HttpArtListResponse();
        //app强制更新去除版本号区分代码
        artworksByFilters = artService.getArtListByCondition(request);
        return HttpBaseResponse.success(artworksByFilters);
    }

    /**
     * 确认订单页面艺术品信息
     */
    @RequestMapping(value = "/artworkOrder290", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArtworkOrderResponse> artworkOrder290(@Json HttpArtworkDetailRequest request) {
        if (null == request || request.getArtworkId() <= 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (null == request.getAccessToken()) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        HttpArtworkOrderResponse artworkInfo = artService.getArtworkOrderInfo290(request);
        return HttpBaseResponse.success(artworkInfo);
    }


    /**
     * 艺术品订单创建
     */
    @ApiOperation(value = "createArtworkOrder290", notes = "艺术品创建订单接口")
    @RequestMapping(value = "/createArtworkOrder290", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSubmitOrderResponse> createArtworkOrder290(@Json HttpCreateArtworkOrderRequest request) {
        /**
         * 验证关键参数是否为空 <br/>
         * 为空,直接返回失败<
         */
        if (request == null || StringUtils.isBlank(request.getAccessToken())
                || CollectionUtils.isEmpty(request.getProductList()) || request.getSoftContractgMoney() == null
                || request.getOsType() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpSubmitOrderResponse response = artService.createArtworkOrder290(request);
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取艺术品搜索页面推荐关键词
     */
    @ApiOperation(value = "获取艺术品搜索页面推荐关键词", notes = "")
    @RequestMapping(value = "/getArtKeywordList", method = RequestMethod.POST)
    public HttpBaseResponse<ArtWordListResponseVo> getArtKeywordList(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        ArtWordListResponseVo response = artWordService.getArtWordList();
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "获取艾商城首页各品类艺术品信息", notes = "获取艾商城首页各品类艺术品信息")
    @RequestMapping(value = "/getCategoryArtListForHome", method = RequestMethod.POST)
    public HttpBaseResponse<CategoryArtListMapResponseVo> getCategoryArtListForHome(@RequestBody HttpBaseRequest request) {
        if (request != null) {
            LogRequestVo logRequestVo = ModelMapperUtil.strictMap(request, LogRequestVo.class);
            logRequestVo.setVisitType(LogEnum.LOG_HOME_ART.getCode());
            LogService.addLog(logRequestVo);
        }

        List<CategoryArtListResponse> artListResponses = artService.getCategoryArtListForHome();
        return HttpBaseResponse.success(new CategoryArtListMapResponseVo(artListResponses));
    }

    @ApiOperation(value = "获取艾商城艺术品专题列表（分页）", notes = "获取艾商城艺术品专题列表（分页）")
    @RequestMapping(value = "/getArtSubjectList", method = RequestMethod.POST)
    public HttpBaseResponse<ArtSubjectPageResponse> getArtSubjectList(@RequestBody ArtSubjectPageRequest request) {
        ArtSubjectPageResponse response = artService.getArtSubjectList(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "查询艺术品专题详情", notes = "查询艺术品专题详情")
    @RequestMapping(value = "/querySubjectDetailById", method = RequestMethod.POST)
    public HttpBaseResponse<ArtSubjectDetailResponse> querySubjectDetailById(@RequestBody SubjectDetailRequest request) {
        if (request == null || request.getSubjectId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        ArtSubjectDetailResponse artListResponses = artService.querySubjectDetailById(request);
        return HttpBaseResponse.success(artListResponses);
    }

    @ApiOperation(value = "批量删除订单", notes = "批量删除已经取消的订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public HttpBaseResponse<Void> deleteOrder(@RequestBody @Valid QueryCollageOrderDetailRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken()) || (request.getOrderId() == null && CollectionUtils.isEmpty(request.getOrderIds()))) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }

        artService.deleteOrder(request);
        return HttpBaseResponse.success(MessageConstant.DELETE_SUCCESS);
    }
}
