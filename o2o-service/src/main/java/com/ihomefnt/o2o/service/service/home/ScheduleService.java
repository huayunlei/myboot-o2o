package com.ihomefnt.o2o.service.service.home;


import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.o2o.intf.manager.util.common.ServiceCallerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 施工进度
 *
 * @author jiangjun
 * @version 2.0, 2018-04-12 下午2:27
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class ScheduleService {
    //默认软装进度描述语
    private static final String DEFAULT_SOFT = "软装家具饰品会在硬装结束后进场";

    @Autowired
    ServiceCallerUtil serviceCallerUtil;

    public HardScheduleVo getSchedule(Integer orderId, Integer orderType){
        return getSchedule(null, null, orderId, orderType);
    }

    public HardScheduleVo getSchedule(Integer operatorId, String sessionId, Integer orderId, Integer orderType){
        Map param = new HashMap();
        param.put("operatorId", operatorId);
        param.put("sessionId", sessionId);
        param.put("orderId", orderId);

        HardScheduleVo hardScheduleVo = (HardScheduleVo) serviceCallerUtil
                .requestApiData(AladdinDmsServiceNameConstants.API_GETSCHEDULE, HardScheduleVo.class, param);

        //当订单类型是软加硬 并且 软装进度没有数据的情况下 给出默认进度描述语
        if(hardScheduleVo!= null && orderType != null && (orderType == HomePageService.ORDER_TYPE_TZ_ALL
                || orderType == HomePageService.ORDER_TYPE_ZY_ALL) && StringUtils.isBlank(hardScheduleVo.getSoft())){

            hardScheduleVo.setSoft(DEFAULT_SOFT);
        }

        return hardScheduleVo;
    }

}
