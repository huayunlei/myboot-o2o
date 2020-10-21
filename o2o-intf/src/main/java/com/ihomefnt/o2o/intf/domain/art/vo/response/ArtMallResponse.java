package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtBannerDto;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtDto;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtistRecommendInfoDto;
import com.ihomefnt.o2o.intf.domain.art.dto.FrontCategoryInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-06 19:35
 */
@ApiModel(value = "新版艾商城返回数据")
@Data
public class ArtMallResponse {

    @ApiModelProperty("banner列表")
    private List<ArtBannerDto> bannerList;

    @ApiModelProperty("新艾商城艺术品app端分类列表")
    private List<FrontCategoryInfoDto> frontCategoryInfoList;

    @ApiModelProperty("艺术家推荐列表")
    private List<ArtistRecommendInfoDto> artistRecommendInfoList;

    @ApiModelProperty("艺术作品列表")
    private List<ArtDto> artList;

    public ArtMallResponse(List<ArtBannerDto> bannerList, List<FrontCategoryInfoDto> frontCategoryInfoList, List<ArtistRecommendInfoDto> artistRecommendInfoList, List<ArtDto> artList) {
        this.bannerList = bannerList;
        this.frontCategoryInfoList = frontCategoryInfoList;
        this.artistRecommendInfoList = artistRecommendInfoList;
        this.artList = artList;
    }
}
