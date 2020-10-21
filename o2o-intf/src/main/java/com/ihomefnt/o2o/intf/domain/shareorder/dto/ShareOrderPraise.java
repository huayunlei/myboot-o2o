package com.ihomefnt.o2o.intf.domain.shareorder.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by onefish on 2016/11/4 0004.
 */
@Data
public class ShareOrderPraise {
    @Id
    private String praiseId;

    /**
     * 用户id
     */
    private long userId;

    /**
     * 设备id
     */
    private String udid;

    /**
     * 被点赞的晒单id
     */
    private String shareOrderId;

}
