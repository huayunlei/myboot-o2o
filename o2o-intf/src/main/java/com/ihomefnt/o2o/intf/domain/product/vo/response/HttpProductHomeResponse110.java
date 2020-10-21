package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-17.
 */
@Data
public class HttpProductHomeResponse110 {
    private List<HttpHouseSuitProductResponse> houseSuitList;//1.10版本 new add
    private List<AdvertisementResponseVo> bannerList;
    private List<AdvertisementResponseVo> bottomBanners;
    private List<ProductSummaryResponse> singleList;
}
