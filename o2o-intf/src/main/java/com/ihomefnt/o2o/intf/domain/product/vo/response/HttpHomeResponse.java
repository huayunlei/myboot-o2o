package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoresResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

@Data
public class HttpHomeResponse {

    private List<AdvertisementResponseVo> bannerList;//广告列表
    
    private List<AppButton> button;// button顺序
    
    private List<ModelRoomPartner> modelRoomPartner;//样板间、合伙人

    private List<Suit> suitList;//推荐套装列表
    
    private List<ProductSummaryResponse> productSummaryList;

    private String promiseUrl;//艾佳承诺url

    private List<Recommend> recList;
    
    private List<Artwork> artworkList; //艺术品列表
    
    private List<AppButton> h5Button;// 内嵌h5button顺序
    
    private String wpfUrl;  //全品家Url
    
    private String seo_title; //seo文章标题
    
    private String seo_keyword; //seo文章key
    
    private String seo_description; //seo文章描述
    
    private HttpExperienceStoresResponse experienceStores; //体验店列表
    
    private ContactUsImageUrl contactUsImageUrl; //联系我们图片url

}
