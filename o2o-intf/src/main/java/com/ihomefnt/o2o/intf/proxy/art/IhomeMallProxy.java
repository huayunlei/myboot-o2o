package com.ihomefnt.o2o.intf.proxy.art;

import com.ihomefnt.o2o.intf.domain.art.dto.*;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * 艾商城
 * @author wanyunxin
 * @create 2019-08-07 17:10
 */
public interface IhomeMallProxy {

    /**
     * 新艾商城艺术品app端分类列表
     * @return
     */
     FrontCategoryDto queryFrontCategoryList(CategoryArtPageRequest request);

    /**
     * 艺术家推荐列表查询
     * @return
     */
     ArtistRecommendDto queryArtistRecommendList(CategoryArtPageRequest request);

    /**
     * 艺术品条件检索
     * @param request
     * @return
     */
     ArtListPageResponse queryArtList(ArtListRequest request);


    /**
     * 艺术品风格查询
     * @return
     */
     List<StyleTypeDto> queryArtTypes(ArtListProductRequest request);

    /**
     * 艺术家主页查询
     * @param request
     * @return
     */
    List<ArtistInfoResponse> queryArtistInfo(HttpArtistRequest request);

    /**
     * 查询艺术品信息
     * @param request
     * @return
     */
    ArtInfoResponse queryArtInfo(WorksRequest request);

    /**
     * 产品列表查询
     * @param artProductListIDto
     * @return
     */
    List<ProductResponse> queryProductList(ArtProductListIDto artProductListIDto);

    /**
     * 定制商品详情页查询接口
     * @param request
     * @return
     */
    ArtProductAllSkuListDto queryCustomProductInfo(CustomSkuRequest request);

    /**
     *  查询相关可定制图案接口
     * @param request
     * @return
     */
    CustomProductPageResponse queryArtListByProductId(ArtListProductRequest request);

    /**
     * 产品详情页
     * @param setProductIdList
     * @return
     */
    List<ProductResponse> queryProductInfo(ArtProductListIDto setProductIdList);

    /**
     * 浏览数+1
     * @param map
     */
    String addViewcount(Map map);


    /**
     * 查询浏览量
     * @param map
     * @return
     */
    VisitResponse queryVisitRecordByProductId(Map map);
}
