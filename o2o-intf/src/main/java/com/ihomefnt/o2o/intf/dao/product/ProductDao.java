package com.ihomefnt.o2o.intf.dao.product;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponseN;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeSingleRelation;
import com.ihomefnt.o2o.intf.domain.product.doo.HouseSuitProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductInfomation;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummary;
import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import com.ihomefnt.o2o.intf.domain.product.doo.RoomProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.SearchResult;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitHard;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitProduct110;
import com.ihomefnt.o2o.intf.domain.product.doo.TProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.TSuitProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.UserComment;
import com.ihomefnt.o2o.intf.domain.product.doo.UserConsult;
import com.ihomefnt.o2o.intf.domain.product.doo.UserLike;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpMultiSuitRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.SuitHardRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Recommend;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import com.ihomefnt.o2o.intf.domain.product.vo.response.SuitList;

/**
 * Created by shirely_geng on 15-1-19.
 */
public interface ProductDao {

	/**
	 * 首页套装
	 */
	List<CompositeProduct> queryLatestCompositeProduct(int count);

	/**
	 * 首页单品
	 */
	List<ProductSummary> queryLatestProduct(int count);

	/**
	 * 更多单品
	 */
	List<ProductSummary> queryProductByPage(int pageSize, int pageNo);

	/**
	 * 单品数量
	 */
	Long queryProductCount();

	/**
	 * 单品详情
	 */
	ProductInfomation queryProductById(Long productId);

	/**
	 * 套装详情
	 */
	CompositeProduct queryCompositeProductById(Long compositeProductId);

	/**
	 * 套装详情
	 */
	List<ProductSummary> queryCompositeSingle(Long compositeProductId);

	/**
	 * 单品详情
	 */
	ProductSummary queryProductSummaryById(Long productId);

	/**
	 * 套装详情,根据套装产品ID，获取套装中的所有产品(wap站组装数据)
	 */
	List<ProductSummary> queryCompositeSingleDetails(Long compositeProductId,String firstContent);
	
	/**
	 * 套装详情,根据套装产品ID，获取套装中产品的first_content(wap站组装数据)
	 */
	List<String>  queryFirstContents(Long compositeProductId);


    List<CompositeSingleRelation> queryCompositeSingle2(Long compositeProductId);

	List<HouseSuitProduct> queryHouseSuitProduct(int count);

	List<SuitProduct> querySuitProductsByHouseId(Long houseId);


    /**
     * @param count
     * @param buildingId
     * @return
     */
    List<HouseSuitProduct> queryHouseSuitProductFromBuilding(int count, Long buildingId);

    /**
     * @param houseId
     * @return
     */
    List<SuitProduct> querySuitsByHouseIdExper(Long houseId);

    /**
     * @param multiSuitRequest
     * @return
     */
    List<SuitProduct110> queryHouseSuitProductByHouseId(HttpMultiSuitRequest multiSuitRequest);

    /**
     * 查询商品首页 改版
     * @param params
     * @return
     */
    List<ProductSummary> queryProductByCondition(Map<String, Object> params);

    /**
     * 条件筛选全部
     * @param params
     * @return
     */
    Long queryProductCount(Map<String, Object> params);
    
    /**
     * 查询app首页button顺序
     * @return
     */
    List<AppButton> queryButtonSort();
    
    /**
     * 查询app首页新品推荐
     * @return
     */
    List<Suit> queryNewSuit(Map<String,Object> paramsMap);
    
    /**
     * 查询app首页爆款推荐
     * @return
     */
    List<Suit> querySellCntSuit(Map<String,Object> paramsMap);
    
    /**
     * 查询app首页推荐套装
     * @return
     */
    List<Suit> queryRecSuit(Map<String,Object> paramsMap);
    
    /**
     * 查询app首页推荐套装CMS
     * @return
     */
    List<String> queryRecProductCms(Map<String,Object> paramsMap);
    
    /**
     * 查询app首页推荐商品
     * @return
     */
    List<Recommend> queryRecProduct(Map<String,Object> paramsMap);
    
    
    List<SuitList> querySuitList(Map<String, Object> params);
    
    List<Room> queryRoomList(Map<String, Object> params);
    
    int querySuitListCount(Map<String, Object> paramMap);
    
    int queryRoomListCount(Map<String, Object> paramMap);
    
    List<SuitList> queryProductCountBySuitId(Map<String, Object> params);
    
    List<RoomProduct> queryRoomProductBySuitId(Long suitId);
    
    List<CompositeSingleRelation> querySuitProduct(Long suitId);
    
    List<TProduct> queryProductList(String productIds);
    
    List<AppButton> queryButtonSort170();
    
    List<AppButton> queryButtonSort200();
    
    String querySuitById(Long suitId);
    
    CompositeProductReponseN querySuitRoomProductById(Long suitId);
    
    List<Suit> queryRandomRecSuit(Long suitId);

	Room queryRoomById(Long roomId);
	
	List<ProductSummary> queryRoomProductById(Long roomId);
	
	UserLike queryUserLike(Long productId, Long userId);
	
	int querySuitSales(Long compositeProductId);
	
	Long queryUserLikeCount(Long productId);
	
	Long addUserLike(UserLike userLike);
	
	Long updateUserLike(UserLike userLike);

	List<SearchResult> querySameHouseSuit(Long suitId);

	List<SearchResult> queryGuessYouLikeSuit(Long suitId);
	
	List<SearchResult> querySameHouseRoom(Long roomId);

	List<SearchResult> queryGuessYouLikeRoom(Map<String,Object> map);
	
	List<UserComment> queryUserCommentList(Map<String, Object> params);
	
	int queryUserCommentCount(Map<String, Object> params);
	
    List<UserConsult> queryUserConsultList(Map<String, Object> params);
    
    int addUserConsult(Map<String, Object> params);
	
	int queryUserConsultCount(Map<String, Object> params);
	
	List<SearchResult> querySuitByProductId(Long productId);
	
	
	List<TSuitProduct> getSuitProduct(Long suitId);

    List<SuitHard> getSuitHard(SuitHardRequest suitHardRequest);
    
    /**
     * 根据建筑来查询小区地址
     * @param buildingId
     * @return
     */
    CompositeProduct queryLocationByBuildingId(Long buildingId) ;
    
    String queryNameByEsId(Long esId);

    List<AppButton> queryButtonSort260();
    
    List<AppButton> queryButtonSort290();
}
