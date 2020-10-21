package com.ihomefnt.o2o.intf.proxy.main;

import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.dto.HardDetail;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusRequestVo;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusResponseVo;

import java.util.List;

/**
 * DmsProxy
 * 交付信息模块接口服务代理层
 *
 * @author xiamingyu
 * @date 2019/3/22
 */

public interface DmsProxy {

    /**
     * 获取简要交付信息
     *
     * @param orderId
     * @return
     */
    DeliverySimpleInfoDto getSimpleDeliveryInfo(Integer orderId);

    /**
     * 获取进度详情
     *
     * @param orderId
     * @return
     */
    HardDetail getHardDetail(Integer orderId);

    /**
     * 交付中商品状态查询
     *
     * @param request
     * @return
     */
    List<ProductStatusResponseVo> queryProductStatusByOrderId(ProductStatusRequestVo request);
}
