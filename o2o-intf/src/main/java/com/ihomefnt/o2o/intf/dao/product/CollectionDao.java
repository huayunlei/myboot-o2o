package com.ihomefnt.o2o.intf.dao.product;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;
import com.ihomefnt.o2o.intf.domain.product.doo.UserInspirationFavorites;
import com.ihomefnt.o2o.intf.domain.product.doo.UserProductFavorites;

/**
 * Created by piweiwen on 15-1-20.
 */
public interface CollectionDao {
	
	/**
	 * 收藏某商品
	 */
	Long addCollection(TCollection collection);
	
	/**
	 * 查询某商品用户是否收藏
	 */
	TCollection queryCollection(Long productId,Long userId, Long type);
	
	
	/**
	 * 取消或再次收藏
	 */
	void dynamicMerge(TCollection collection);
	
	/**
     * 查询用户所有收藏
	 * 
	 */
	List<UserProductFavorites> queryAllFavorites(Long userId);
	
    /**
     * 查询用户所有收藏单品
	 * 
	 */
	List<UserProductFavorites> queryFavoritesSingle(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏空间
	 * 
	 */
	List<UserProductFavorites> queryFavoritesRoom(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏套装
	 * 
	 */
	List<UserProductFavorites> queryFavoritesSuit(Map<String, Object> params);
	/**
     * 查询用户所有收藏单品
	 * 
	 */
	int queryFavoritesSingleCount(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏空间
	 * 
	 */
	int queryFavoritesRoomCount(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏套装
	 * 
	 */
	int queryFavoritesSuitCount(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏案例
	 */
	List<UserInspirationFavorites> queryFavoritesCase(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏案例数量
	 */
	int queryFavoritesCaseCount(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏攻略
	 */
	List<UserInspirationFavorites> queryFavoritesStrategy(Map<String, Object> params);
	
	/**
     * 查询用户所有收藏案例数量
	 */
	int queryFavoritesStrategyCount(Map<String, Object> params);

	List<PictureAlbum> queryPictureAlbumList(Map<String, Object> paramMap);

	int queryPictureAlbumCount(Map<String, Object> paramMap);
}
