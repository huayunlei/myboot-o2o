package com.ihomefnt.o2o.service.proxy.art;

import com.ihomefnt.o2o.intf.domain.art.dto.*;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.art.IhomeMallProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 艾商城
 * @author wanyunxin
 * @create 2019-08-07 17:10
 */
@Service
public class IhomeMallProxyImpl implements IhomeMallProxy {

    @Autowired
    private StrongSercviceCaller sercviceCaller;

    /**
     * 艺术品列表条件检索接口
     * @return
     */
    @Override
    public ArtListPageResponse queryArtList(ArtListRequest request) {
        return ((ResponseVo<ArtListPageResponse>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_LIST, request, new TypeReference<ResponseVo<ArtListPageResponse>>() {
        })).getData();
    }


    /**
     * 类目列表查询
     * @return
     */
    @Override
    public FrontCategoryDto queryFrontCategoryList(CategoryArtPageRequest request) {
        return ((HttpBaseResponse<FrontCategoryDto>) sercviceCaller.post(WcmWebServiceNameConstants.QUERY_FRONT_CATEGORY_LIST, request, new TypeReference<HttpBaseResponse<FrontCategoryDto>>() {
        })).getObj();
    }

    /**
     * 艺术家推荐列表查询
     * @return
     */
    @Override
    public ArtistRecommendDto queryArtistRecommendList(CategoryArtPageRequest request) {
        return ((HttpBaseResponse<ArtistRecommendDto>) sercviceCaller.post(WcmWebServiceNameConstants.QUERY_ARTIST_RECOMMEND_LIST, request, new TypeReference<HttpBaseResponse<ArtistRecommendDto>>() {
        })).getObj();
    }

    /**
     * 艺术品风格查询
     * @return
     */
    @Override
    public List<StyleTypeDto> queryArtTypes(ArtListProductRequest request) {
        return ((ResponseVo<List<StyleTypeDto>>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_STYLES,request, new TypeReference<ResponseVo<List<StyleTypeDto>>>() {
        })).getData();
    }

    /**
     * 查询艺术家信息
     * @param request
     * @return
     */
    @Override
    public List<ArtistInfoResponse> queryArtistInfo(HttpArtistRequest request) {
        return ((ResponseVo<List<ArtistInfoResponse>>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ARTIST_INFOS, request, new TypeReference<ResponseVo<List<ArtistInfoResponse>>>() {
        })).getData();
    }

    /**
     * 查询艺术品信息
     * @param request
     * @return
     */
    @Override
    public ArtInfoResponse queryArtInfo(WorksRequest request) {
        return ((ResponseVo<ArtInfoResponse>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_WORKS_INFO, request.getWorksId(), new TypeReference<ResponseVo<ArtInfoResponse>>() {
        })).getData();
    }

    /**
     * 产品列表查询
     * @param artProductListIDto
     * @return
     */
    @Override
    public List<ProductResponse> queryProductList(ArtProductListIDto artProductListIDto) {
        return ((ResponseVo<List<ProductResponse>>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_PRODUCT_LIST, artProductListIDto, new TypeReference<ResponseVo<List<ProductResponse>>>() {
        })).getData();
    }

    /**
     * 定制商品详情页查询接口
     * @param request
     * @return
     */
    @Override
    public ArtProductAllSkuListDto queryCustomProductInfo(CustomSkuRequest request) {
        return ((ResponseVo<ArtProductAllSkuListDto>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_ALL_SKU_LIST, request, new TypeReference<ResponseVo<ArtProductAllSkuListDto>>() {
        })).getData();
    }

    /**
     * 查询可定制商品信息
     * @param request
     * @return
     */
    @Override
    public CustomProductPageResponse queryArtListByProductId(ArtListProductRequest request) {
        return ((ResponseVo<CustomProductPageResponse>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_CUSTOMIZE_LIST, request, new TypeReference<ResponseVo<CustomProductPageResponse>>() {
        })).getData();
    }

    /**
     * 产品详情页
     * @param request
     * @return
     */
    @Override
    public List<ProductResponse> queryProductInfo(ArtProductListIDto request) {
        return ((ResponseVo<List<ProductResponse>>) sercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_PRODUCT_DETAIL, request, new TypeReference<ResponseVo<List<ProductResponse>>>() {
        })).getData();
    }

    /**
     * 浏览数+1
     * @param map
     */
    @Override
    public String addViewcount(Map map) {
        return ((HttpBaseResponse<String>) sercviceCaller.post(WcmWebServiceNameConstants.ADD_VISIT_RECORD, map, new TypeReference<HttpBaseResponse<String>>() {
        })).getObj();
    }

    /**
     * 查询点赞数量
     * @param map
     */
    @Override
    public VisitResponse queryVisitRecordByProductId(Map map) {
        return ((HttpBaseResponse<VisitResponse>) sercviceCaller.post(WcmWebServiceNameConstants.QUERY_VISIT_RECORD_BY_PRODUCTID, map, new TypeReference<HttpBaseResponse<VisitResponse>>() {
        })).getObj();
    }

}
