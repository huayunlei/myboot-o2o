package com.ihomefnt.o2o.service.dao.product;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.product.WishListDao;
import com.ihomefnt.o2o.intf.domain.product.doo.TWishList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by piweiwen on 15-1-21.
 */
@Repository
public class WishListDaoImpl implements WishListDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.product.WishListDao.";

    @Override
    public List<TWishList> queryAllWishList(Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAllWishList", paramMap);
    }

    @Override
    public Long addWishList(TWishList wishList) {
        sqlSessionTemplate.insert(NAME_SPACE + "addWishList", wishList);
        return wishList.getWishListId();
    }
}
