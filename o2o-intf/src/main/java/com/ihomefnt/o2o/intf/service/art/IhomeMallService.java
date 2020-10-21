package com.ihomefnt.o2o.intf.service.art;

import com.ihomefnt.o2o.intf.domain.art.dto.FrontCategoryDto;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-06 19:24
 */
public interface IhomeMallService {

    /**
     * 艾商城首页接口
     * @param width
     * @return
     */
    ArtMallResponse queryArtMall(Integer width,Integer osType);

    FrontCategoryDto queryFrontCategoryList(CategoryArtPageRequest width);

    /**
     * 艺术品列表条件检索接口
     * @param request
     * @return
     */
    ArtListPageResponse queryArtList(ArtListRequest request);



    /**
     * 艺术品风格查询接口
     * @return
     */
    ScreenTypeResponse queryArtTypes();

    /**
     * 定制专区-产品列表查询接口
     * @param request
     * @return
     */
    List<ProductResponse> queryProductList(ProductCategoryRequest request);

    /**
     * 产品详情页接口
     * @param request
     * @return
     */
    ProductResponse queryProductInfo(ProductRequest request);

    /**
     * 根据产品id查询相关可定制图案接口
     * @param request
     * @return
     */
    CustomProductPageResponse queryArtListByProductId(ArtListProductRequest request);

    /**
     * 定制商品详情页查询接口
     * @param request
     * @return
     */
    CustomSkuResponse queryCustomProductInfo(CustomSkuRequest request);

    /**
     * 根据画作id查询画作详情
     * @param request
     * @return
     */
    ArtInfoResponse queryArtInfo(WorksRequest request);

    /**
     * 艺术家主页查询
     * @param request
     * @return
     */
    ArtistInfoResponse queryArtistInfo(HttpArtistRequest request);

}
