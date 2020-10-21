package com.ihomefnt.o2o.intf.service.right;

import com.ihomefnt.o2o.intf.domain.right.vo.request.QueryMyOrderRightItemListRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.response.OrderRightsResultVo;

/**
 * @author jerfan cang
 * @date 2018/10/11 10:09
 */
public interface QueryMyOrderRightService {



    OrderRightsResultVo queryMyOrderRightItemList(QueryMyOrderRightItemListRequest param);
}
