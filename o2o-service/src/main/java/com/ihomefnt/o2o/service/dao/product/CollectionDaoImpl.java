package com.ihomefnt.o2o.service.dao.product;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;
import com.ihomefnt.o2o.intf.domain.product.doo.UserInspirationFavorites;
import com.ihomefnt.o2o.intf.domain.product.doo.UserProductFavorites;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by piweiwen on 15-1-20.
 */
@Repository
public class CollectionDaoImpl implements CollectionDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.product.CollectionDao.";


    @Override
    public Long addCollection(TCollection collection) {
        sqlSessionTemplate.insert(NAME_SPACE + "addCollection", collection);
        //return primary key id
        return collection.getCollectionId();
    }

    @Override
    public TCollection queryCollection(Long productId, Long userId, Long type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productId", productId);
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryCollection", paramMap);
    }

    @Override
    public void dynamicMerge(TCollection collection) {
        sqlSessionTemplate.update(NAME_SPACE +"updateCollection", collection);
    }
    
    @Override
    public List<UserProductFavorites> queryAllFavorites(Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAllFavorites", paramMap);
    }

    @Override
    public List<UserProductFavorites> queryFavoritesSingle(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryFavoritesSingle", paramMap);
    }
    @Override
    public List<UserProductFavorites> queryFavoritesRoom(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryFavoritesRoom", paramMap);
    }
    @Override
    public List<UserProductFavorites> queryFavoritesSuit(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryFavoritesSuit", paramMap);
    }

	@Override
	public int queryFavoritesSingleCount(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryFavoritesSingleCount", params);
	}

	@Override
	public int queryFavoritesRoomCount(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryFavoritesRoomCount", params);
	}

	@Override
	public int queryFavoritesSuitCount(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryFavoritesSuitCount", params);
	}

	@Override
	public List<UserInspirationFavorites> queryFavoritesCase(
			Map<String, Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryFavoritesCase", params);
	}

	@Override
	public List<UserInspirationFavorites> queryFavoritesStrategy(
			Map<String, Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryFavoritesStrategy", params);
	}
	
	@Override
	public int queryFavoritesCaseCount(Map<String, Object> params) {
		params.put("type", 5);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryFavoritesCaseCount", params);
	}

	@Override
	public int queryFavoritesStrategyCount(Map<String, Object> params) {
		params.put("type", 4);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryFavoritesStrategyCount", params);
	}

	@Override
	public List<PictureAlbum> queryPictureAlbumList(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryPictureAlbumList", paramMap);
	}

	@Override
	public int queryPictureAlbumCount(Map<String, Object> paramMap) {
		paramMap.put("type", 6);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryPictureAlbumCount", paramMap);
	}

}
