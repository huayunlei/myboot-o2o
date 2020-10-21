package com.ihomefnt.o2o.api.controller.art;

import com.ihomefnt.o2o.intf.domain.art.dto.FrontCategoryDto;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.service.art.IhomeMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-06 18:45
 */
@RestController
@Api(tags = "【艾商城API】")
@RequestMapping("/iHomeMall")
public class IhomeMallController {

    @Autowired
    IhomeMallService ihomeMallService;

    /**
     * 艾商城首页接口
     * @param request
     * @return
     */
    @ApiOperation(value = "艾商城首页接口", notes = "艾商城首页查询")
    @PostMapping(value = "/queryArtMall")
    public HttpBaseResponse<ArtMallResponse> queryArtMall(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtMall(request.getWidth(),request.getOsType()));
    }

    /**
     * 定制品列表展示
     * @param request
     * @return
     */
    @ApiOperation(value = "定制品列表展示", notes = "定制品列表展示")
    @PostMapping(value = "/queryFrontCategoryList")
    public HttpBaseResponse<FrontCategoryDto> queryFrontCategoryList(@RequestBody CategoryArtPageRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryFrontCategoryList(request));
    }

    /**
     * 艺术品列表条件检索接口
     * @param request
     * @return
     */
    @ApiOperation(value = "艺术品列表条件检索接口", notes = "艺术品列表条件检索")
    @PostMapping(value = "/queryArtList")
    public HttpBaseResponse<ArtListPageResponse> queryArtList(@RequestBody ArtListRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtList(request));
    }

    /**
     * 艺术品风格及价格筛选条件查询接口
     * @param request
     * @return
     */
    @ApiOperation(value = "艺术品风格及价格筛选条件查询接口", notes = "艺术品风格及价格筛选条件查询")
    @PostMapping(value = "/queryArtTypes")
    public HttpBaseResponse<ScreenTypeResponse> queryArtTypes(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtTypes());
    }

    /**
     * 定制专区-产品列表查询接口
     * @param request
     * @return
     */
    @ApiOperation(value = "定制专区-产品列表查询接口", notes = "定制专区-产品列表查询")
    @PostMapping(value = "/queryProductList")
    public HttpBaseResponse<List<ProductResponse>> queryProductList(@RequestBody ProductCategoryRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryProductList(request));
    }

    /**
     * 产品详情页接口
     * @param request
     * @return
     */
    @ApiOperation(value = "产品详情页接口", notes = "产品详情页")
    @PostMapping(value = "/queryProductInfo")
    public HttpBaseResponse<ProductResponse> queryProductInfo(@RequestBody ProductRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryProductInfo(request));
    }

    /**
     * 根据产品id查询相关可定制图案接口
     * @param request
     * @return
     */
    @ApiOperation(value = "根据产品id或画作id查询相关可定制图案接口", notes = "根据产品id或画作id查询相关可定制图案接口")
    @PostMapping(value = "/queryArtListByProductId")
    public HttpBaseResponse<CustomProductPageResponse> queryArtListByProductId(@RequestBody ArtListProductRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtListByProductId(request));
    }

    /**
     * 定制商品详情页查询接口
     * @param request
     * @return
     */
    @ApiOperation(value = "定制商品详情页查询接口", notes = "定制商品详情页查询")
    @PostMapping(value = "/queryCustomProductInfo")
    public HttpBaseResponse<CustomSkuResponse> queryCustomProductInfo(@RequestBody CustomSkuRequest request) {
        if(request == null || request.getProductId() == null || request.getWorksId() == null){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END,MessageConstant.DATA_TRANSFER_EMPTY);
        }
        return HttpBaseResponse.success(ihomeMallService.queryCustomProductInfo(request));
    }

    /**
     * 根据画作id查询画作详情
     * @param request
     * @return
     */
    @ApiOperation(value = "根据画作id查询画作详情", notes = "根据画作id查询画作详情" )
    @PostMapping(value = "/queryArtInfo")
    public HttpBaseResponse<ArtInfoResponse> queryArtInfo(@RequestBody WorksRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtInfo(request));
    }

    /**
     * 艺术家主页查询
     * @param request
     * @return
     */
    @ApiOperation(value = "艺术家主页查询", notes = "艺术家主页查询" )
    @PostMapping(value = "/queryArtistInfo")
    public HttpBaseResponse<ArtistInfoResponse> queryArtistInfo(@RequestBody HttpArtistRequest request) {
        return HttpBaseResponse.success(ihomeMallService.queryArtistInfo(request));
    }
}
