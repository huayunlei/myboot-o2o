package com.ihomefnt.o2o.intf.service.product;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponse;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponseN;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeSingleRelation;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummary;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.RoomProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitHard;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitProduct110;
import com.ihomefnt.o2o.intf.domain.product.doo.TProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.TSuitProduct;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpAddUserConsultRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpMultiSuitRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpProductHomeRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpProductMoreSingleRequest150;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpSuitRequest150;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpUserCommentOrConsultRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.SuitHardRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpHomeResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpHouseSuitProductResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpProductMoreInformationRsponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpProductMoreSingleResponse150;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpRoomDetailsResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpSuitResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpUserCommendResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpUserConsultResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import com.ihomefnt.o2o.intf.domain.product.vo.response.SuitList;

/**
 * Created by shirely_geng on 15-1-19.
 */
public interface ProductService {

    /**
     * 精致单品
     */
    List<ProductSummaryResponse> queryProductSummary(int count);

    /**
     * 主页套装
     */
    List<CompositeProductReponse> queryCompositeProduct(int count);

    /**
     * 单品详情
     */
    HttpProductMoreInformationRsponse getProductDetails(Long productId, Long userId);

    /**
     * 套装详情
     */
    CompositeProduct queryCompositeProductById(Long compositeProductId);

    /**
     * 套装详情,根据套装产品ID，获取套装中的所有产品
     */
    List<ProductSummary> queryCompositeSingle(Long compositeProductId);

    /**
     * 点单接口调用，返回特定的数据
     *
     * @param compositeProductId
     * @return
     */
    List<CompositeSingleRelation> queryCompositeSingle2(Long compositeProductId);

    ProductSummary queryProductSummaryById(Long productId);

    String appendImageMethod(int mode);

    /**
     * @param count
     * @param buildingId
     * @return
     */
    List<HttpHouseSuitProductResponse> queryHouseSuitFromBuilding(int count, Long buildingId);

    /**
     * @param multiSuitRequest
     * @return
     */
    List<SuitProduct110> queryHouseSuitProductByHouseId(HttpMultiSuitRequest multiSuitRequest);

    /**
     * 添加分类查询的商品列表
     * @param productMoreSingleRequest
     * @return
     */
	HttpProductMoreSingleResponse150 getMoreSingle150(
			HttpProductMoreSingleRequest150 productMoreSingleRequest);
    /**
     * 1.5.0版本首页改版
     * @return
     */
	HttpHomeResponse home150(HttpProductHomeRequest resquest);
	
    /**
     * 套装列表页面
     * @param request
     * @return
     */
    HttpSuitResponse getSuitList150(HttpSuitRequest150 request);
    
    List<RoomProduct> queryRoomProductBySuitId(Long suitId);
    
    String getImages(String imagesStr, int osType, int width);
    
    List<CompositeSingleRelation> querySuitProduct(Long suitId);
    
    List<TProduct> queryProductList(String productIds);
    
    SuitList getSuitById(Long suitId);
    
    CompositeProductReponseN querySuitRoomProductById(Long suitId);
    
    List<Suit> queryRandomSuit(Long compositeProductId);

	HttpRoomDetailsResponse getRoomDetails(Long roomId, Long userId);
	
    HttpUserCommendResponse queryUserCommentList(HttpUserCommentOrConsultRequest request);
    
    int addUserConsult(HttpAddUserConsultRequest request,Long userId);
    
    List<TSuitProduct> getSuitProduct(Long suitId);

    List<SuitHard> getSuitHard(SuitHardRequest suitHardRequest);
    
    /**
     * APP 2.6.0首页改版.
     * @param productHomeRequest
     * @return
     */
    HttpHomeResponse home260(HttpProductHomeRequest productHomeRequest);

	HttpUserConsultResponse queryUserConsultList(HttpUserCommentOrConsultRequest request);

	CompositeProduct queryLocationByBuildingId(Long buildingId);

	int queryUserCommentCount(HttpUserCommentOrConsultRequest commentOrConsultRequest);

	int queryUserConsultCount(HttpUserCommentOrConsultRequest commentOrConsultRequest);

	HttpHomeResponse home170(HttpProductHomeRequest productHomeRequest);

	List<HttpHouseSuitProductResponse> queryHouseSuitProduct(int i);
    
}
