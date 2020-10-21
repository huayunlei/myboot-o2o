package com.ihomefnt.o2o.service.service.ishop;

import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureCommodityResponseVo;
import com.ihomefnt.o2o.intf.domain.ishop.vo.request.AijiaShopHomeRequestVo;
import com.ihomefnt.o2o.intf.domain.ishop.vo.response.AijiaShopHomeResponseVo;
import com.ihomefnt.o2o.intf.domain.product.dto.SellerVo;
import com.ihomefnt.o2o.intf.domain.product.dto.SkuVo;
import com.ihomefnt.o2o.intf.domain.product.dto.TripProductDto;
import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.ishop.AijiaShopService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AijiaShopServiceImpl implements AijiaShopService {
	
	private static final int artworkPageSize = 8;
	
	@Autowired ArtService artService;
	
	@Autowired ProductProxy productProxy;

	@Override
	public AijiaShopHomeResponseVo getAijiaShopHome(AijiaShopHomeRequestVo requestVo) {
		AijiaShopHomeResponseVo resultData = new AijiaShopHomeResponseVo();

		String appVersion = "";
		if(null == requestVo || null == requestVo.getOsType() || requestVo.getOsType() == 3) {
			appVersion = "2.9.4";
		} else {
			if(requestVo.getOsType() != 3) {
				appVersion = requestVo.getAppVersion();
			}
		}
		
		//版本控制：293版本以上增加文旅商品version > 293
		if(VersionUtil.mustUpdate("2.9.3", appVersion)) {
			//获取艺术品列表
			List<Artwork> artworksRecommend = artService.getArtworksRecommend(artworkPageSize,1);
			List<Artwork> artworks = new ArrayList<Artwork>();
			if(null != artworksRecommend && artworksRecommend.size() == 8) {
				resultData.setArtworkList(artworksRecommend);
			} else {
				//推荐艺术品不足8件的时候，补全
				int size = artworksRecommend.size();
				List<Artwork> artworksRecommend2 = artService.getArtworksRecommend(artworkPageSize-size,0);
				artworks.addAll(artworksRecommend);
				artworks.addAll(artworksRecommend2);
				resultData.setArtworkList(artworks);
			}
			
			//文旅商品列表
			
			List<CultureCommodityResponseVo> cultureList = new ArrayList<CultureCommodityResponseVo>();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(null != requestVo) {
				paramMap.put("pageNo", requestVo.getPageNo()==0 ? 1:requestVo.getPageNo());
				paramMap.put("pageSize", requestVo.getPageSize()==0 ? 10:requestVo.getPageSize());
				//文旅商品按地区分类展示
				if(StringUtils.isNotBlank(requestVo.getCityCode())){
					paramMap.put("cityCode",Integer.parseInt(requestVo.getCityCode()));
				}else{
					paramMap.put("cityCode",210000);
				}
			} else {
				paramMap.put("pageNo", 1);
				paramMap.put("pageSize", 10);
				paramMap.put("cityCode",210000);
			}
			Page<TripProductDto> productVo =  productProxy.queryTripProductList(paramMap);
			if(null != productVo) {				
				resultData.setCultureTotalPage(productVo.getTotalPage());
				List<TripProductDto> productListVo = productVo.getList();
				for (TripProductDto tripProductVo : productListVo) {
					if(tripProductVo.getStatus() == 1) {
						CultureCommodityResponseVo cultureCommodity = new CultureCommodityResponseVo();
						cultureCommodity.setItemId(tripProductVo.getId());
						cultureCommodity.setItemName(tripProductVo.getProductName());
						//设置商品头图  
						List<SkuVo> skuList = tripProductVo.getSkuList();
						if (CollectionUtils.isNotEmpty(skuList)) {
							SkuVo skuVo = skuList.get(0);
							if (skuVo != null) {
								List<String> images = skuVo.getImages();
								if (CollectionUtils.isNotEmpty(images)) {
									String headImg = images.get(0);
									cultureCommodity.setItemHeadImg(headImg);
								}
							}
						}														
						//根据商品id获取商户信息
					
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("productId", tripProductVo.getId());
						SellerVo sellerVo =productProxy.getSellerByProductId(param);
							if(null != sellerVo) {

							
							cultureCommodity.setSellerId(sellerVo.getId());
							cultureCommodity.setSellerName(sellerVo.getName());
							cultureCommodity.setSellerAddress(sellerVo.getAddr());
						
						}
						cultureList.add(cultureCommodity);
					}
				}
			
			}
			//若没有文旅商品匹配成功，则不展示“文化旅游栏”
			if(CollectionUtils.isNotEmpty(cultureList)){
				resultData.setCultureList(cultureList);
			}
		} else {
			//获取艺术品列表
			List<Artwork> artworksRecommend = artService.getArtworksRecommend(artworkPageSize,1);
			List<Artwork> artworks = new ArrayList<Artwork>();
			if(null != artworksRecommend && artworksRecommend.size() == 8) {
				resultData.setArtworkList(artworksRecommend);
			} else {
				//推荐艺术品不足8件的时候，补全
				int size = artworksRecommend.size();
				List<Artwork> artworksRecommend2 = artService.getArtworksRecommend(artworkPageSize-size,0);
				artworks.addAll(artworksRecommend);
				artworks.addAll(artworksRecommend2);
				resultData.setArtworkList(artworks);
			}
		}
		Integer width = requestVo != null ? requestVo.getWidth() : null;
		this.setImgSize(resultData,width);
		return null;
	}

	/**
	 * 设置切图
	 * @param resultData
	 */
	private void setImgSize(AijiaShopHomeResponseVo resultData, Integer width) {
		if (resultData == null) {
			return;
		}
		List<Artwork> artworks = resultData.getArtworkList();
		/**
		 * 获取艺术品
		 */
		if (CollectionUtils.isNotEmpty(artworks)) {
			for (Artwork art : artworks) {
				String headImg = art.getHeadImg();
				ImageEntity entity = getImgEntity(headImg,width);
				art.setHeadImgObj(entity);
				//首页商品头图  50% 中部截取
				if(StringUtils.isNotBlank(headImg)){
					if(width != null){
						Integer imgWidth = width * ImageSize.WIDTH_PER_SIZE_50 / ImageSize.WIDTH_PER_SIZE_100;
						art.setHeadImg(QiniuImageUtils.compressImageAndDiffPic(headImg, imgWidth, -1));
					}
				}
			}
		}
		
		//获取文旅商品
		List<CultureCommodityResponseVo> cultureCommodities = resultData.getCultureList();
		if (CollectionUtils.isNotEmpty(cultureCommodities)) {
			for (CultureCommodityResponseVo cultureCommodity : cultureCommodities) {
				String headImg = cultureCommodity.getItemHeadImg();
				ImageEntity entity = getImgEntity(headImg,width);
				cultureCommodity.setHeadImgObj(entity);
				//首页商品头图  30% 中部截取
				if(StringUtils.isNotBlank(headImg)){
					if(width != null){
						Integer imgWidth = width * ImageSize.WIDTH_PER_SIZE_30 / ImageSize.WIDTH_PER_SIZE_100;
						cultureCommodity.setItemHeadImg(QiniuImageUtils.compressImageAndDiffPic(headImg, imgWidth, -1));
					}
				}
			}
		}

	}

	/**
	 * 获取切图:大图 90% 中部截取 小图:50% 中部截取
	 * 
	 * @param headImg
	 * @param width
	 * @return
	 */
	private ImageEntity getImgEntity(String headImg, Integer width) {
		ImageEntity entity = new ImageEntity();
		if (StringUtils.isNotBlank(headImg)) {
			if (width == null) {
				entity.setSmallImage(headImg);
				entity.setBigImage(headImg);
			} else {
				Integer smallWidth = width * ImageSize.WIDTH_PER_SIZE_50 / ImageSize.WIDTH_PER_SIZE_100;
				Integer bigWidth = width * ImageSize.WIDTH_PER_SIZE_90 / ImageSize.WIDTH_PER_SIZE_100;
				String smallImage = QiniuImageUtils.compressImageAndDiffPic(headImg, smallWidth, -1);
				String bigImage = QiniuImageUtils.compressImageAndSamePicTwo(headImg, bigWidth, -1);
				entity.setBigImage(bigImage);// 90% 中部截取
				entity.setSmallImage(smallImage);// 50% 中部截取
			}
		}
		return entity;
	}
}
