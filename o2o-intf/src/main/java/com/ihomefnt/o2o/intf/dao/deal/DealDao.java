package com.ihomefnt.o2o.intf.dao.deal;

import com.ihomefnt.o2o.intf.domain.deal.dto.DealPickModel;
import com.ihomefnt.o2o.intf.domain.deal.dto.TActivityProduct;
import com.ihomefnt.o2o.intf.domain.deal.dto.TDealOrder;

import java.util.List;

/**
 * Created by hvk687 on 9/29/15.
 */
public interface DealDao {
    List<DealPickModel> loadAllDealOrder();
    TDealOrder loadDealOrder(String mobile);
    TActivityProduct queryActPrd(Long actPrdKey);
    boolean pick(String orderNo);
    int getOrderCount(int userId);
}
