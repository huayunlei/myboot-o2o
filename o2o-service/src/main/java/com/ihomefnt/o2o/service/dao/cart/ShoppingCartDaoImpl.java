package com.ihomefnt.o2o.service.dao.cart;

import com.ihomefnt.o2o.intf.dao.cart.ShoppingCartDao;
import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartDto;
import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartProductDto;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartDaoImpl.class);
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.cart.ShoppingCartDao.";

    @Override
    public List<Room> queryShoppingCartOnSlave(Long userId) {
        LOG.info("ShoppingCartDao.queryShoppingCartOnSlave() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryShoppingCartOnSlave", userId);
    }

    @Override
    public List<ShoppingCartProductDto> queryShoppingCartOffSlave(Long userId) {
        LOG.info("ShoppingCartDao.queryShoppingCartOffSlave() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryShoppingCartOffSlave", userId);
    }

    @Override
    public void addShoppingCartBatch(List<ShoppingCartDto> list) {
        LOG.info("ShoppingCartDao.addShoppingCartBatch() start");
        sqlSessionTemplate.insert(NAME_SPACE + "addShoppingCartBatch", list);
    }

    @Override
    public int queryShoppingCartCnt(Long userId) {
        LOG.info("ShoppingCartDao.queryShoppingCartCnt() start");
        Object obj = sqlSessionTemplate.selectOne(NAME_SPACE + "queryShoppingCartCnt", userId);
        return obj != null ? (Integer) obj : 0;
    }

    @Override
    public List<Long> queryShoppingCartProduct(Long userId) {
        LOG.info("ShoppingCartDao.queryShoppingCartProduct() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryShoppingCartProduct", userId);
    }

    @Override
    public int deleteOffProduct(Long userId, Long productId) {
        LOG.info("ShoppingCartDao.deleteOffProduct() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        if (null != productId && productId > 0) {
            paramMap.put("productId", productId);
        }
        Object obj = sqlSessionTemplate.delete(NAME_SPACE + "deleteOffProduct", paramMap);
        return obj != null ? (Integer) obj : 0;
    }

    @Override
    public List<ProductSummaryResponse> queryProductInRoom() {
        LOG.info("ShoppingCartDao.queryProductInRoom() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductInRoom");
    }
    
    @Override
    public List<ProductSummaryResponse> queryProductInRoomByProductId(Long productId) {
        LOG.info("ShoppingCartDao.queryProductInRoomByProductId() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null != productId && productId > 0) {
            paramMap.put("productId", productId);
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductInRoomByProductId", paramMap);
    }
    
    @Override
    public List<ProductSummaryResponse> queryProductInRoomByProductIdList(List<Long> productIdList) {
        LOG.info("ShoppingCartDao.queryProductInRoomByproductIdList() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null != productIdList && productIdList.size() > 0) {
            paramMap.put("productIdList", productIdList);
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductInRoomByProductIdList",paramMap);
    }

	@Override
	public List<ProductOrder> queryProductInfo(List<Long> productId) {
		LOG.info("ShoppingCartDao.queryProductInfo() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productId", productId);
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductInfo",paramMap);
	}
	
	
}
