package com.ihomefnt.o2o.service.dao.deal;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.deal.DealDao;
import com.ihomefnt.o2o.intf.domain.deal.dto.DealPickModel;
import com.ihomefnt.o2o.intf.domain.deal.dto.TActivityProduct;
import com.ihomefnt.o2o.intf.domain.deal.dto.TDealOrder;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hvk687 on 9/29/15.
 */
@Repository
public class DealDaoImpl implements DealDao {
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.deal.DealDao.";

    @Autowired
    SqlSessionTemplate sessionTemplate;

    @Override
    public List<DealPickModel> loadAllDealOrder() {
        return sessionTemplate.selectList(NAME_SPACE + "queryAllDealOrder");
    }

    @Override
    public TDealOrder loadDealOrder(String mobile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        return sessionTemplate.selectOne(NAME_SPACE + "queryDealOrderByMobile", map);
    }

    @Override
    public TActivityProduct queryActPrd(Long actPrdKey) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", actPrdKey);
        return sessionTemplate.selectOne(NAME_SPACE + "queryActPrdById", map);
    }

    @Override
    public boolean pick(String orderNo) {
        if (StringUtil.isNullOrEmpty(orderNo)) {
            return false;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_no", orderNo);
        /**
         * Must ensure that order is payed!
         */
        boolean ret = (1 == sessionTemplate.update(NAME_SPACE + "updateToPicked", map));
        ret = ret & (1 == sessionTemplate.update(NAME_SPACE + "updateToComplete", map));
        return ret;
    }

    @Override
    public int getOrderCount(int userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return sessionTemplate.selectOne(NAME_SPACE + "queryOrderCount", map);
    }
    
}
