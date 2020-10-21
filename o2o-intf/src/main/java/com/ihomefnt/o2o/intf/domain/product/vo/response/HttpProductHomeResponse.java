package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-17.
 */
@Data
public class HttpProductHomeResponse {
    private List<CompositeProductReponse> compositeList;//1.10之前的版本
    private List<AdvertisementResponseVo> bannerList;
    private List<ProductSummaryResponse> singleList;
}
