package com.ihomefnt.o2o.intf.service.main;

import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.main.vo.request.MainFrameRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageNewResponse;

public interface MainCoreService {

    /**
     * 6.0首页核心操作区
     *
     * @param request
     * @return
     */
    MainPageNewResponse getMainCore(MainFrameRequest request);

    /**
     * 硬装进度文案转换
     *
     * @param deliveryInfo
     * @return
     */
    String transferDeliverHard(DeliverySimpleInfoDto deliveryInfo);
}
