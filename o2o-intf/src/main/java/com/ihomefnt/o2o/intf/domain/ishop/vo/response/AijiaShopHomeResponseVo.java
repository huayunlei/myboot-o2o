package com.ihomefnt.o2o.intf.domain.ishop.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureCommodityResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AijiaShopHomeResponseVo",description="艾商城首页返回值vo")
public class AijiaShopHomeResponseVo {
	
	@ApiModelProperty("艺术品列表")
	private List<Artwork> artworkList;
	
	@ApiModelProperty("文旅商品列表")
	private List<CultureCommodityResponseVo> cultureList;
	
	@ApiModelProperty("文旅商品总页数(前面的版本可能不会用到，为了后续可扩展性加的)")
	private int cultureTotalPage;
}
