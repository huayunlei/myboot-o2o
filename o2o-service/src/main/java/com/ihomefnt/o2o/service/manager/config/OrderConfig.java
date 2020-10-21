package com.ihomefnt.o2o.service.manager.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderAuthWhiteListDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2020/1/13 10:30 上午
 */
@Data
@Component
public class OrderConfig {

    @NacosValue(value = "${order.auth.white.lists}", autoRefreshed = true)
    private String orderAuthWhiteLists;
    // 订单号码鉴权列表
    @NacosValue(value = "${order.num.auth.white.lists}", autoRefreshed = true)
    private String orderNumAuthWhiteLists;

    public List<OrderAuthWhiteListDto> getOrderAuthWhiteLists () {
        if (this.orderAuthWhiteLists != null) {
            return JsonUtils.json2list(this.orderAuthWhiteLists, OrderAuthWhiteListDto.class);
        }
        return null;
    }
    public List<String> getOrderNumAuthWhiteLists () {
        if (this.orderNumAuthWhiteLists != null) {
            return JsonUtils.json2list(this.orderNumAuthWhiteLists, String.class);
        }
        return null;
    }


}
