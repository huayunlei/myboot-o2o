package com.ihomefnt.o2o.service.dao.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.product.ProductDao;
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
 * Created
 * by
 * shirely_geng
 * on
 * 15
 * -
 * 1
 * -
 * 19.
 */
@Repository
public class ProductDaoImpl implements ProductDao {
    private static final Logger LOG = LoggerFactory.getLogger(ProductDaoImpl.class);
    @SuppressWarnings("unused")
    private static String COUNT = "count";
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.product.ProductDao.";

    @Override
    public List<CompositeProduct> queryLatestCompositeProduct(int count) {
        LOG.info("interface queryLatestCompositeProduct() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", Integer.valueOf(count));
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuit", map);
    }

    @Override
    public List<ProductSummary> queryLatestProduct(int count) {
        LOG.info("interface queryLatestProduct() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", Integer.valueOf(count));
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryHomeSingleProduct", map);
    }

    @Override
    public List<ProductSummary> queryProductByPage(int pageSize, int pageNo) {
        LOG.info("interface queryProductByPage() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageSize", pageSize);
        paramMap.put("size", (pageNo - 1) * pageSize);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductByPage", paramMap);
    }

    @Override
    public Long queryProductCount() {
        LOG.info("interface queryProductCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryProductCount");
    }

    @Override
    public ProductInfomation queryProductById(Long productId) {
        LOG.info("interface queryProductById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productId", productId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryProductById", paramMap);
    }

    @Override
    public Room queryRoomById(Long roomId) {
        LOG.info("interface queryRoomById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roomId", roomId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryRoomById", paramMap);
    }

    @Override
    public List<ProductSummary> queryRoomProductById(Long roomId) {
        LOG.info("interface queryRoomProductById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roomId", roomId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRoomSingle", paramMap);
    }

    @Override
    public CompositeProduct queryLocationByBuildingId(Long buildingId) {
        LOG.info("interface queryLocationByBuildingId() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("buildingId", buildingId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryLocationByBuildingId", paramMap);
    }
    
    @Override
    public CompositeProduct queryCompositeProductById(Long compositeProductId) {
        LOG.info("interface queryCompositeProductById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("compositeProductId", compositeProductId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryCompositeProductById", paramMap);
    }

    @Override
    public List<ProductSummary> queryCompositeSingle(Long compositeProductId) {
        LOG.info("interface queryCompositeSingle() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("compositeProductId", compositeProductId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCompositeSingle", paramMap);
    }

    @Override
    public ProductSummary queryProductSummaryById(Long productId) {
        LOG.info("interface queryProductSummaryById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productId", productId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryProductSummary", paramMap);
    }

    /**
     * 给定套装
     * ，
     * 给定房间的产品的列表
     * 
     * @param compositeProductId
     * @param firstContent
     * @return
     */
    @Override
    public List<ProductSummary> queryCompositeSingleDetails(Long compositeProductId,
            String firstContent) {
        LOG.info("interface queryCompositeSingleDetails() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("compositeProductId", compositeProductId);
        paramMap.put("firstContent", firstContent);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCompositeSingleDetails", paramMap);
    }

    @Override
    public List<String> queryFirstContents(Long compositeProductId) {
        LOG.info("interface queryFirstContents() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("compositeProductId", compositeProductId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryFirstContents", paramMap);
    }

    @Override
    public List<CompositeSingleRelation> queryCompositeSingle2(Long compositeProductId) {
        LOG.info("interface queryCompositeSingle2() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("compositeProductId", compositeProductId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCompositeSingle2", paramMap);
    }

    @Override
    public List<HouseSuitProduct> queryHouseSuitProduct(int count) {
        LOG.info("interface queryHouseSuitProduct() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("count", count);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryHouseSuitProduct", paramMap);
    }

    @Override
    public List<SuitProduct> querySuitProductsByHouseId(Long houseId) {

        LOG.info("interface querySuitProductsByHouseId() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitProductsByHouseId", houseId);
    }

    @Override
    public List<SuitProduct110> queryHouseSuitProductByHouseId(
            HttpMultiSuitRequest multiSuitRequest) {
        LOG.info("interface queryHouseSuitProductByHouseId() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("houseId", multiSuitRequest.getHouseId());
        paramMap.put("isExper", multiSuitRequest.getIsExper());
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryHouseSuitProductByHouseId",
                paramMap);
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.intf.product.dao.ProductDao#queryHouseSuitProductFromBuilding(int)
     */
    @Override
    public List<HouseSuitProduct> queryHouseSuitProductFromBuilding(int count, Long buildingId) {
        LOG.info("interface queryHouseSuitProductFromBuilding() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("count", count);
        paramMap.put("buildingId", buildingId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryHouseSuitFromBuilding", paramMap);
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.intf.product.dao.ProductDao#querySuitsByHouseIdExper(java.lang.Long)
     */
    @Override
    public List<SuitProduct> querySuitsByHouseIdExper(Long houseId) {
        LOG.info("interface querySuitsByHouseIdExper() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitsByHouseIdExper", houseId);
    }

    @Override
    public List<ProductSummary> queryProductByCondition(Map<String, Object> params) {
        LOG.info("interface queryProductByCondition() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductByCondition", params);
    }

    @Override
    public Long queryProductCount(Map<String, Object> params) {
        LOG.info("interface queryProductCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryProductCount150", params);
    }

    /**
     * 查询app首页新品推荐
     * 
     * @return
     */
    @Override
    public List<Suit> queryNewSuit(Map<String, Object> paramsMap) {
        LOG.info("interface queryNewSuit() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryNewSuit", paramsMap);
    }

    /**
     * 查询app首页爆款推荐
     * 
     * @return
     */
    @Override
    public List<Suit> querySellCntSuit(Map<String, Object> paramsMap) {
        LOG.info("interface querySellCntSuit() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySellCntSuit", paramsMap);
    }

    @Override
    public List<Suit> queryRecSuit(Map<String, Object> paramsMap) {
        LOG.info("interface queryRecSuit() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRecSuit", paramsMap);
    }

    @Override
    public List<String> queryRecProductCms(Map<String,Object> paramsMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRecSuitCms",paramsMap);
    }
    
    @Override
    public List<Suit> queryRandomRecSuit(Long suitId) {
        LOG.info("interface queryRandomRecSuit() start in ProductDao");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("suitId", suitId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRandomRecSuit", params);
    }

    @Override
    public List<Recommend> queryRecProduct(Map<String, Object> paramsMap) {
        LOG.info("interface queryRecProduct() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRecProduct", paramsMap);
    }

    @Override
    public List<AppButton> queryButtonSort() {
        LOG.info("interface queryButtonSort() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryButtonSort");
    }

    @Override
    public List<AppButton> queryButtonSort170() {
        LOG.info("interface queryButtonSort170() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryButtonSort170");
    }

    @Override
    public List<AppButton> queryButtonSort200() {
        LOG.info("interface queryButtonSort200() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryButtonSort200");
    }

    @Override
    public List<SuitList> querySuitList(Map<String, Object> params) {
        LOG.info("interface querySuitList() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitList", params);
    }

    @Override
    public List<Room> queryRoomList(Map<String, Object> params) {
        LOG.info("interface queryRoomList() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRoomList", params);
    }

    @Override
    public int queryRoomListCount(Map<String, Object> paramMap) {
        LOG.info("interface queryRoomListCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryRoomListCount", paramMap);
    }

    @Override
    public List<SuitList> queryProductCountBySuitId(Map<String, Object> params) {
        LOG.info("interface queryProductCountBySuitId() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductCountBySuitId", params);
    }

    @Override
    public int querySuitListCount(Map<String, Object> paramMap) {
        LOG.info("interface querySuitListCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "querySuitListCount", paramMap);
    }

    @Override
    public List<RoomProduct> queryRoomProductBySuitId(Long suitId) {
        LOG.info("interface queryRoomProductBySuitId() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryRoomProductBySuitId", suitId);
    }

    @Override
    public List<CompositeSingleRelation> querySuitProduct(Long suitId) {
        LOG.info("interface querySuitProduct() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitProduct", suitId);
    }

    @Override
    public List<TProduct> queryProductList(String productIds) {
        LOG.info("interface queryProductList() start in productDaoImpl");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productIds", productIds);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductList", map);
    }

    @Override
    public String querySuitById(Long suitId) {
        LOG.info("interface querySuitById() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suitId", suitId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "querySuitById", map);
    }

    @Override
    public CompositeProductReponseN querySuitRoomProductById(Long suitId) {
        LOG.info("interface querySuitRoomProductById() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("suitId", suitId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "querySuitRoomProductById", paramMap);
    }

    @Override
    public UserLike queryUserLike(Long productId, Long userId) {
        LOG.info("interface queryUserLike() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        map.put("userId", userId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserLike", map);
    }

    @Override
    public Long queryUserLikeCount(Long productId) {
        LOG.info("interface queryUserLikeCount() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserLikeCount", map);
    }

    @Override
    public Long addUserLike(UserLike userLike) {
        LOG.info("interface addUserLike() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userLike", userLike);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "addUserLike", map);
    }

    @Override
    public Long updateUserLike(UserLike userLike) {
        LOG.info("interface updateUserLike() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userLike", userLike);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "updateUserLike", map);
    }

    @Override
    public List<SearchResult> querySameHouseSuit(Long suitId) {
        LOG.info("interface querySameHouseSuit() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suitId", suitId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySameHouseSuit", map);
    }

    @Override
    public List<SearchResult> queryGuessYouLikeSuit(Long suitId) {
        LOG.info("interface queryGuessYouLikeSuit() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suitId", suitId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryGuessYouLikeSuit", map);
    }

    @Override
    public int querySuitSales(Long compositeProductId) {
        LOG.info("interface querySuitSales() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suitId", compositeProductId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "querySuitSales", map);
    }

    @Override
    public List<UserComment> queryUserCommentList(Map<String, Object> params) {
        LOG.info("interface queryUserCommentList() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryUserCommentList", params);
    }

    @Override
    public int queryUserCommentCount(Map<String, Object> params) {
        LOG.info("interface queryUserCommentCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserCommentCount", params);
    }

    @Override
    public List<UserConsult> queryUserConsultList(Map<String, Object> params) {
        LOG.info("interface queryUserConsultList() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryUserConsultList", params);
    }

    @Override
    public int queryUserConsultCount(Map<String, Object> params) {
        LOG.info("interface queryUserConsultCount() start in ProductDao");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserConsultCount", params);
    }

    @Override
    public List<SearchResult> querySameHouseRoom(Long roomId) {
        LOG.info("interface querySameHouseRoom() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roomId", roomId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySameHouseRoom", map);
    }

    @Override
    public List<SearchResult> queryGuessYouLikeRoom(Map<String, Object> map) {
        LOG.info("interface queryGuessYouLikeRoom() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryGuessYouLikeRoom", map);
    }

    @Override
    public List<SearchResult> querySuitByProductId(Long productId) {
        LOG.info("interface querySuitByProductId() start in ProductDao");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitByProductId", map);
    }

    @Override
    public int addUserConsult(Map<String, Object> params) {
        LOG.info("interface addUserConsult() start in ProductDao");
        return sqlSessionTemplate.insert(NAME_SPACE + "addUserConsult", params);
    }

    @Override
    public List<TSuitProduct> getSuitProduct(Long suitId) {
        LOG.info("interface TSuitProduct() start in ProductDao");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("suitId", suitId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitProduct2", paramMap);
    }

    @Override
    public List<SuitHard> getSuitHard(SuitHardRequest suitHardRequest) {
        LOG.info("interface getSuitHard() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitHard", suitHardRequest);
    }

	@Override
	public String queryNameByEsId(Long esId) {
		LOG.info("interface queryNameByEsId() start in ProductDao");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("esId", esId);
		return sqlSessionTemplate
				.selectOne(NAME_SPACE + "queryNameByEsId", map);
	}

    @Override
    public List<AppButton> queryButtonSort260() {
        LOG.info("interface queryButtonSort260() start in ProductDao");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryButtonSort260");
    }

	@Override
	public List<AppButton> queryButtonSort290() {
		LOG.info("interface queryButtonSort290() start in ProductDao");
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryButtonSort290");
	}

    
}
