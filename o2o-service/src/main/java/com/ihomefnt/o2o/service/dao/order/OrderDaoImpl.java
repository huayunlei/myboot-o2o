package com.ihomefnt.o2o.service.dao.order;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import com.ihomefnt.o2o.intf.dao.order.OrderDao;
import com.ihomefnt.o2o.intf.domain.order.dto.Consignee;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderPayRecord;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderSerialNo;
import com.ihomefnt.o2o.intf.domain.order.dto.TOrder;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created
 * by
 * shirely_geng
 * on
 * 15
 * -
 * 1
 * -
 * 23.
 */
@Repository
public class OrderDaoImpl implements OrderDao {
    private static final Logger LOG = LoggerFactory.getLogger(OrderDaoImpl.class);
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.order.OrderDao.";

    @Override
    public List<UserOrder> queryAllSingleUserOrder(Long userId) {
        LOG.info("OrderDao.queryAllSingleUserOrder() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAllSingleUserOrder", paramMap);
    }

    @Override
    public List<UserOrder> queryAllCompositeUserOrder(Long userId) {
        LOG.info("OrderDao.queryAllCompositeUserOrder() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAllCompositeUserOrder", paramMap);
    }

    @Override
    public Integer updateOrder(UserOrder userOrder) {
        LOG.info("OrderDao.updateOrder() start");
        return sqlSessionTemplate.update(NAME_SPACE + "updateOrder", userOrder);
    }

    @Override
    public UserOrder queryUserOrderByOrderId(Long orderId) {
        LOG.info("OrderDao.queryUserOrderByOrderId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserOrderByOrderId", orderId);
    }

    @Override
    public List<ProductOrderDetail> queryOrderDetailsByOrderId(Long orderId) {
        LOG.info("OrderDao.queryOrderDetailsByOrderId() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryOrderDetailsByOrderId", orderId);
    }

    @Override
    public TOrder queryOrderByOrderId(Long orderId) {
        LOG.info("OrderDao.queryOrderByOrderId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryOrderByOrderId", orderId);
    }

    @Override
    public TOrder queryMyOrderByOrderId(Long orderId) {
        LOG.info("OrderDao.queryMyOrderByOrderId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryMyOrderByOrderId", orderId);
    }

    @Override
    public List<UserOrder> queryUserOrder(Long userId, Long orderId) {
        LOG.info("OrderDao.queryUserOrder() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("count", 10);
        paramMap.put("orderId", orderId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryUserOrder", paramMap);
    }

    @Override
    public List<Consignee> queryMyConsignee(Long userId) {
        LOG.info("OrderDao.queryMyConsignee() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryMyConsignee", userId);
    }

    @Override
    public void createOrderSerial(Long orderId) {
        LOG.info("OrderDao.createOrderSerial() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        sqlSessionTemplate.insert(NAME_SPACE + "generateOrderSerial", paramMap);
    }

    @Override
    public OrderSerialNo queryOrderSerial(Long orderId) {
        LOG.info("OrderDao.queryOrderSerial() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "getOrderSerial", paramMap);
    }

    @Override
    public void setOrderNumber(String serialNo, Long orderId) {
        LOG.info("OrderDao.setOrderNumber() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("orderNum", serialNo);
        sqlSessionTemplate.update(NAME_SPACE + "updateOrderNumber", paramMap);
    }

    @Override
    public Double selPayedMoneyByOrderId(Long orderId) {
        LOG.info("OrderDao.selPayedMoneyByOrderId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "selPayedMoneyByOrderId", orderId);
    }
    
    @Override
    public Double selPayedMoneyByOrderIdAndOrderStatus(Long orderId,Integer orderStauts) {
        LOG.info("OrderDao.selPayedMoneyByOrderId() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("orderStauts", orderStauts);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "selPayedMoneyByOrderIdAndOrderStatus", paramMap);
    }

    @Override
    public List<UserOrder> selOrderProductImages(String orderIds) {
        LOG.info("OrderDao.selOrderProductImages() start");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderIds", orderIds);
        return sqlSessionTemplate.selectList(NAME_SPACE + "selOrderProductImages", paramMap);
    }

	@Override
	public List<OrderPayRecord> querySubOrderPay(Map<String, Object> param) {
		LOG.info("OrderDao.querySubOrderPay() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySubOrderPay", param);
	}

	@Override
	public Double queryPayedMoneyByOrderId(Map<String, Object> param) {
		LOG.info("OrderDao.queryPayedMoneyByOrderId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryPayedMoneyByOrderId", param);
	}
}
