package com.ihomefnt.o2o.intf.domain.product.vo.response;

import lombok.Data;

@Data
public class AppButton {
    private String buttonName;
    private String iconUrl;
    private String imageUrl;
    private Long nodeId;
    private Integer type;//1:套装,2:单品,3:购物车,4:浏览历史    7:内嵌h5相关   8:在线客服
    private Integer filterId;
    private String h5Url;//内嵌h5链接
}
