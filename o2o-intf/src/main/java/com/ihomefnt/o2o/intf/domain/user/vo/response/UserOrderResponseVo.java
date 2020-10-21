package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by piweiwen on 15-1-21.
 */
@Data
@NoArgsConstructor
public class UserOrderResponseVo {
    private Long orderId;
    private String name;
    private Long orderStatus;
    private String createTime;
    private List<String> pictureUrlOriginal;

    public UserOrderResponseVo(UserOrder userOrder) {
        this.orderId = userOrder.getOrderId();
        this.name = userOrder.getName();
        this.orderStatus = userOrder.getOrderStatus();
        if (null == userOrder.getCreateTime()) {
            this.createTime = null;
        } else {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.createTime = sdf.format(userOrder.getCreateTime().clone());
        }
    }
}
