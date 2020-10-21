package com.ihomefnt.o2o.service.proxy.main;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.dto.HardDetail;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusRequestVo;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.ProductStatusResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2019/3/22
 */
@Service
public class DmsProxyImpl implements DmsProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public DeliverySimpleInfoDto getSimpleDeliveryInfo(Integer orderId) {
        ResponseVo<DeliverySimpleInfoDto> response = strongSercviceCaller.post("aladdin-dms.back.deliver.queryDeliverSimpleInfoByOrderId", orderId,
                new TypeReference<ResponseVo<DeliverySimpleInfoDto>>() {
                });

        if (null != response && response.isSuccess()) {
            return response.getData();
        }
        return null;
    }

    @Override
    public HardDetail getHardDetail(Integer orderId) {
        return ((ResponseVo<HardDetail>) strongSercviceCaller.post("aladdin-dms.back.deliver.getHardDetail", Maps.newHashMap("orderId", orderId),
                new TypeReference<ResponseVo<HardDetail>>() {
                })).getData();
    }

    @Override
    public List<ProductStatusResponseVo> queryProductStatusByOrderId(ProductStatusRequestVo request) {
        try {
            ResponseVo<List<ProductStatusResponseVo>> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.QUERY_PRODUCT_STATUS_BY_ORDER_ID, request, new TypeReference<ResponseVo<List<ProductStatusResponseVo>>>() {
                    });
            if(responseVo != null && responseVo.getData() != null){
                return responseVo.getData();
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
