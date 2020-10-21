package com.ihomefnt.o2o.service.service.product;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.image.ImageQuality;
import com.ihomefnt.common.util.image.ImageTool;
import com.ihomefnt.common.util.image.ImageType;
import com.ihomefnt.o2o.intf.dao.cart.ShoppingCartDao;
import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.dao.product.ProductDao;
import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.building.doo.Building;
import com.ihomefnt.o2o.intf.domain.common.http.PageModel;
import com.ihomefnt.o2o.intf.domain.house.dto.House;
import com.ihomefnt.o2o.intf.domain.product.doo.*;
import com.ihomefnt.o2o.intf.domain.product.vo.request.*;
import com.ihomefnt.o2o.intf.domain.product.vo.response.*;
import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageQuality;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.art.ArtProxy;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.building.BuildingService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.suit.SuitService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.manager.config.ImageConfig;
import com.ihomefnt.o2o.service.manager.config.ImageProperty;
import com.ihomefnt.o2o.service.manager.config.PromiseUrlConfig;
import com.ihomefnt.zeus.finder.ServiceCaller;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created
 * by
 * shirely_geng
 * on
 * 15
 * -
 * 1
 * -
 * 19.
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    ServiceCaller serviceCaller;
    @Autowired
    ProductDao productDao;
    @Autowired
    CollectionDao collectionDao;
    @Autowired
    ImageConfig imageConfig;
    @Autowired
    AreaService areaService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    HouseService houseService;
    @Autowired
    ProductTypeDao productTypeDao;
    @Autowired
	SuitService suitService;

    @Autowired
    AdService adService;

    @Autowired
    PromiseUrlConfig promiseUrlConfig;

    @Autowired
    UserDao userDao;

    @Autowired
    ShoppingCartDao shoppingCartDao;
    
    @Autowired
    ArtProxy artDao;
    
    @Autowired
    ApiConfig apiconfig;

    @Autowired
    ArtService artService;
    @Autowired
    TKDMDao tkdmDao;
    
    private final int artworkPageSize = 8;
    
    @Override
    public List<ProductSummaryResponse> queryProductSummary(int count) {
        List<ProductSummary> productSummaryList = productDao.queryLatestProduct(count);
        List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();
        if (null != productSummaryList && productSummaryList.size() > 0) {
            if (productSummaryList.size() % 2 > 0 && productSummaryList.size() < 6) {
                productSummaryList.remove(productSummaryList.size() - 1);
            }
            for (ProductSummary product : productSummaryList) {
                ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(product);
                List<String> imgList = ImageUtil.removeEmptyStr(product.getPictureUrlOriginal());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                productSummaryResponse.setPictureUrlOriginal(strResponseList);
                productSummaryResponseList.add(productSummaryResponse);
            }
        }
        return productSummaryResponseList;
    }

    @Override
    public List<CompositeProductReponse> queryCompositeProduct(int count) {
        List<CompositeProduct> compositeProductList = productDao.queryLatestCompositeProduct(count);
        List<CompositeProductReponse> compositeProductResponseList = new ArrayList<CompositeProductReponse>();
        if (null != compositeProductList && compositeProductList.size() > 0) {
            for (CompositeProduct product : compositeProductList) {
                CompositeProductReponse compositeProductReponse = new CompositeProductReponse(
                        product);
                List<String> imgList = ImageUtil.removeEmptyStr(product.getPictureUrlOriginal());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                String saleOff = compositeProductReponse.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                compositeProductReponse.setSaleOff(saleOff);
                compositeProductReponse.setPictureUrlOriginal(strResponseList);
                compositeProductResponseList.add(compositeProductReponse);
            }
        }
        return compositeProductResponseList;
    }

    @Override
    public HttpProductMoreInformationRsponse getProductDetails(Long productId, Long userId) {
        HttpProductMoreInformationRsponse productMoreInformationRsponse = new HttpProductMoreInformationRsponse();
        if (null == productDetails(productId)) {
            return null;
        }
        productMoreInformationRsponse.setProductInfomation(productDetails(productId));
        
        if(null != userId){
        	TCollection collection = collectionDao.queryCollection(productId, userId, 3l);
            if (collection == null || collection.getStatus() == 0) {
                productMoreInformationRsponse.setCollection(0);
            } else {
                productMoreInformationRsponse.setCollection(1);
            }
        } else {
        	productMoreInformationRsponse.setCollection(0);
        }
        
        List<SearchResult> suitList = productDao.querySuitByProductId(productId);
        if (null != suitList && suitList.size() > 0) {
	        for (SearchResult suit : suitList) {
	            List<String> imgList = ImageUtil.removeEmptyStr(suit.getImage());
	            List<String> imageList = new ArrayList<String>();
	            if (null != imgList && imgList.size() > 0) {
	                for (String str : imgList) {
	                    if (null != str && !"".equals(str)) {
	                        str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
	                        imageList.add(str);
	                    }
	                }
	            }
	            suit.setImageList(imageList);
	            suit.setImage(imageList.size()>0?imageList.get(0):null);
	        }
	    }
        productMoreInformationRsponse.setSuitList(suitList);
        
        HttpUserCommentOrConsultRequest userCommendRequest = new HttpUserCommentOrConsultRequest();
        userCommendRequest.setPageNo(0);
        userCommendRequest.setPageSize(1);
        userCommendRequest.setProductId(productId);
        userCommendRequest.setType(3l);
        HttpUserCommendResponse commendResponse = queryUserCommentList(userCommendRequest);
        productMoreInformationRsponse.setUserCommentList(commendResponse.getUserCommentList());
        
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("type", 3);
        int commentCount = productDao.queryUserCommentCount(params);
        int consultCount = productDao.queryUserConsultCount(params);
        productMoreInformationRsponse.setCommentCount(commentCount);
        productMoreInformationRsponse.setConsultCount(consultCount);
        
        return productMoreInformationRsponse;
    }
    
    @Override
    public HttpRoomDetailsResponse getRoomDetails(Long roomId, Long userId) {
    	HttpRoomDetailsResponse roomDetailsResponse = new HttpRoomDetailsResponse();
        
        Room roomDetails = productDao.queryRoomById(roomId);
        if (roomDetails == null) {
            return null;
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", roomId);
		params.put("type", 2);
        int commentCount = productDao.queryUserCommentCount(params);
        int consultCount = productDao.queryUserConsultCount(params);
        roomDetails.setCommentCount(commentCount);
        roomDetails.setConsultCount(consultCount);
        
        List<ProductSummary> productSummaryList = productDao.queryRoomProductById(roomId);
        
        List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();
        if (null != productSummaryList) {
            for (ProductSummary product : productSummaryList) {
            	ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(product);
                
                if (null != product && StringUtils.isNotBlank(product.getPictureUrlOriginal())
                        && product.getPictureUrlOriginal().contains("[")
                        && product.getPictureUrlOriginal().contains("]")) {
                    JSONArray productJsonArray = JSONArray.fromObject(product.getPictureUrlOriginal());
                    List<String> productStrList = (List<String>) JSONArray.toList(productJsonArray, String.class);
                    List<String> productStrResponseList = new ArrayList<String>();

                    if (null != productStrList && productStrList.size() > 0) {
                        for (String str : productStrList) {
                            if (null != str && !"".equals(str)) {
                                str += appendImageMethod(ImageSize.SIZE_MEDIUM);
                                productStrResponseList.add(str);
                            }
                        }
                    }
                    productSummaryResponse.setPictureUrlOriginal(productStrResponseList);
                }

                productSummaryResponseList.add(productSummaryResponse);
            }
        }
        
        roomDetails.setProductSummaryList(productSummaryResponseList);
        
        List<String> imgList = ImageUtil.removeEmptyStr(roomDetails.getImages());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        roomDetails.setImageList(strResponseList);
        
        List<RoomImage> roomImageList = null;
        roomImageList = suitService.getRoomImageListByRoomId(roomId, SuitConstant.DESIGN_STATUS_NO);
        if(roomImageList!=null&&!roomImageList.isEmpty()){
        	for(RoomImage roomImage:roomImageList){
        		String imageUrl=roomImage.getImageUrl();
        		if(StringUtils.isNotBlank(imageUrl)){
        			roomImage.setImageUrl(imageUrl+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
        		}      		
        	}
        	roomDetails.setImageRoll(roomImageList);
        }
        
        
        roomDetailsResponse.setRoomDetails(roomDetails);
        TCollection collection = null;
        if(null != userId){
        	collection = collectionDao.queryCollection(roomId, userId,2l);
        }
        if (collection == null || collection.getStatus() == 0) {
        	roomDetailsResponse.setCollection(0);
        } else {
        	roomDetailsResponse.setCollection(1);
        }
        
        HttpUserCommentOrConsultRequest userCommendRequest = new HttpUserCommentOrConsultRequest();
        userCommendRequest.setPageNo(0);
        userCommendRequest.setPageSize(1);
        userCommendRequest.setProductId(roomId);
        userCommendRequest.setType(2l);
        HttpUserCommendResponse commendResponse = queryUserCommentList(userCommendRequest);
        roomDetailsResponse.setUserCommentList(commendResponse.getUserCommentList());
        
        return roomDetailsResponse;
    }


    @Override
    public CompositeProduct queryCompositeProductById(Long compositeProductId) {
        return productDao.queryCompositeProductById(compositeProductId);
    }
    
    @Override
    public List<ProductSummary> queryCompositeSingle(Long compositeProductId) {
        return productDao.queryCompositeSingle(compositeProductId);
    }


    public ProductInfomationResponse productDetails(Long productId) {
        ProductInfomation productInfomation = productDao.queryProductById(productId);
        if (productInfomation == null) {
            return null;
        }
        ProductInfomationResponse productInfomationResponse = new ProductInfomationResponse(
                productInfomation);
        if (StringUtils.isNotBlank(productInfomation.getProductAttrJson()) && !productInfomation.getProductAttrJson().equals("[]")) {//2.0之前版本
            List<String> detailList = new ArrayList<String>();//first pass string,second pass object => object<key,value>
            JSONObject jsonObj = null;
			try {
				jsonObj = JSONObject.fromObject(productInfomation.getProductAttrJson());
			} catch (Exception e) {
				LOG.info("productDetails ERROR paras :" + JsonUtils.obj2json(productInfomation));
			}          
            if (null != jsonObj) {
                Set<String> set = jsonObj.keySet();
                Iterator<String> it = set.iterator();
                List<ProductDetail> adnexalDatails = new ArrayList<ProductDetail>();
                while (it.hasNext()) {
                    String str = it.next();
                    detailList.add(str + ":" + jsonObj.getString(str));
                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setDetailKey(str);//附件参数
                    productDetail.setDetailValue(jsonObj.getString(str));
                    adnexalDatails.add(productDetail);
                }
                productInfomationResponse.setAdnexalDetails(adnexalDatails);
            }
            productInfomationResponse.setDetailList(detailList);
        }
        if (StringUtils.isNotBlank(productInfomation.getProductAttrJson()) && !productInfomation.getProductAttrJson().equals("[]")) {//2.0以后版本
            List<String> detailList = new ArrayList<String>();//first pass string,second pass object => object<key,value>
            JSONObject jsonObj = null;
			try {
				jsonObj = JSONObject.fromObject(productInfomation.getProductAttrJson());
			} catch (Exception e) {
				LOG.info("productDetails ERROR paras :" + JsonUtils.obj2json(productInfomation));
			}  
            if (null != jsonObj) {
                Set<String> set = jsonObj.keySet();
                Iterator<String> it = set.iterator();
                List<ProductDetailGroup> productDetailGroups = new ArrayList<ProductDetailGroup>();
                while (it.hasNext()) {
                	 String str = it.next();
                	 ProductDetailGroup productDetailGroup=new ProductDetailGroup();
                	 productDetailGroup.setGroupName(str);
                   detailList.add(str + ":" + jsonObj.getString(str));
                   JSONObject jsonObjDetail = null;
                    try {
                        jsonObjDetail = jsonObj.getJSONObject(str);
                    } catch (Exception e) {
                        continue;
                    }

                    if (jsonObjDetail != null && !jsonObjDetail.isNullObject()
                            && !jsonObjDetail.isEmpty()) {
                	   List<ProductDetail> adnexalDatails = new ArrayList<ProductDetail>();
                        Set<String> setDetail = jsonObjDetail.keySet();
                        Iterator<String> itDetail = setDetail.iterator();
                        while (itDetail.hasNext()) {
                            String detailKey = itDetail.next();
                         ProductDetail productDetail = new ProductDetail();
                         productDetail.setDetailKey(detailKey);//附件参数
                         productDetail.setDetailValue(jsonObjDetail.getString(detailKey));
                         adnexalDatails.add(productDetail);
                     }
                     productDetailGroup.setDetails(adnexalDatails); 
                   }
                   productDetailGroups.add(productDetailGroup);
                	}
                productInfomationResponse.setProductDetailGroups(productDetailGroups);
            }
            productInfomationResponse.setDetailList(detailList);
        }

        List<String> imgList = ImageUtil.removeEmptyStr(productInfomation.getPictureUrlOriginal());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        //Map<Long, String> roomMap = queryProductInRoom();
        /**
         * queryProductInRoom 这个会返回所有商品
         * queryProductInRoomByProductId 这个只会返回对应商品
         * 优化理由，你懂的。。。
         */
        Map<Long, String> roomMap = queryProductInRoomByProductId(productInfomationResponse.getProductId());
        productInfomationResponse.setFirstContentsName(roomMap.get(productInfomationResponse.getProductId()));
        
        productInfomationResponse.setPictureUrlOriginal(strResponseList);
        return productInfomationResponse;
    }

    @Override
    public List<CompositeSingleRelation> queryCompositeSingle2(Long compositeProductId) {
        return productDao.queryCompositeSingle2(compositeProductId);
    }

    @Override
    public ProductSummary queryProductSummaryById(Long productId) {
        return productDao.queryProductSummaryById(productId);
    }

    /**
     * generate
     * method
     * url
     *
     * @param mode
     * @return
     */
    @Override
	public String appendImageMethod(int mode) {
		String methodUrl = "?imageView2/1/w/*/h/*";
		if (imageConfig != null && imageConfig.getImageConfigMap() != null) {
			ImageProperty imageProperty = imageConfig.getImageConfigMap().get(mode);
			if (imageProperty != null && imageProperty.getWidth() > 0 && imageProperty.getHeight() > 0) {
				methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getWidth()));
				methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getHeight()));
			} else {
				methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(1080));
				methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(540));
			}
		} else {
			methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(1080));
			methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(540));
		}

		return methodUrl;
	}
    
    @SuppressWarnings({"rawtypes", "deprecation"})
    @Override
    public List<HttpHouseSuitProductResponse> queryHouseSuitFromBuilding(int count, Long buildingId) {
        List<HouseSuitProduct> compositeProductList = productDao.queryHouseSuitProductFromBuilding(
                count, buildingId);
        List<HttpHouseSuitProductResponse> houseSuitProductResponses = new ArrayList<HttpHouseSuitProductResponse>();
        for (int i = 0; i < compositeProductList.size(); i++) {
            compositeProductList.get(i).setPictureUrlOriginal(null);
            List<SuitProduct> suitProducts = productDao
                    .querySuitsByHouseIdExper(compositeProductList.get(i).getHouseId());
            Set<String> set = new HashSet<String>();
            if (suitProducts != null && !suitProducts.isEmpty()) {
                for (int j = 0; j < suitProducts.size(); j++) {
                    set.add(suitProducts.get(j).getStyle());
                    if (suitProducts.get(j).getOffLineExperience() != null
                            && suitProducts.get(j).getOffLineExperience() == 1
                            && compositeProductList.get(i).getPictureUrlOriginal() == null) {
                        String images = suitProducts.get(j).getImages();
                        List imgList = JSONArray.toList(JSONArray.fromObject(images));
                        List<String> newImgList = new ArrayList<String>();
                        for (Object objImg : imgList) {
                            if (objImg != null && !objImg.toString().trim().equals("")) {
                                newImgList.add(objImg.toString());
                            }
                        }
                        compositeProductList.get(i).setPictureUrlOriginal(newImgList);
                        break;
                    }
                }
            }
            if (compositeProductList.get(i).getPictureUrlOriginal() == null && suitProducts != null
                    && !suitProducts.isEmpty()) {
                String images = suitProducts.get(0).getImages();
                if (images != null && images.length() > 1) {
                    List imgList = JSONArray.toList(JSONArray.fromObject(images));
                    List<String> newImgList = new ArrayList<String>();
                    for (Object objImg : imgList) {
                        if (objImg != null && !objImg.toString().trim().equals("")) {
                            newImgList.add(objImg.toString());
                        }
                    }
                    compositeProductList.get(i).setPictureUrlOriginal(newImgList);
                } else {
                    compositeProductList.get(i).setPictureUrlOriginal(null);
                }

            }
            if (suitProducts != null && suitProducts.size() > 1) {
                compositeProductList.get(i).setStyle("{" + suitProducts.size() + "种风格}");
                compositeProductList.get(i).setCompositeProductId(-1l);
                // 组装数据
                AreaDto city = this.areaService.queryCity(compositeProductList.get(i).getCityCode());
                String cityName = city.getAreaName();
                if (city != null && cityName.length() > 2) {
                    cityName = city.getAreaName().substring(0, cityName.length() - 1);
                }
                Long houseId = compositeProductList.get(i).getHouseId();
                House house = this.houseService.queryHouseById(houseId);
                String houseName = "";
                if (house != null) {
                    houseName = house.getName();
                }
                String developerName = "";
                String buildingName = "";
                Building building = this.buildingService.queryBuildingByHouseId(houseId);
                if (building != null) {
                    buildingName = building.getName();
                    developerName = building.getDeveloper();
                }
//              套装标题名称 = 城市 + 开发商名称 + 楼盘名称 + 户型名称 + ”家居套装“
                StringBuffer sb = new StringBuffer();
                sb.append(cityName);
                sb.append(developerName);
                sb.append(buildingName);
                sb.append(houseName);
                sb.append("家居套装");
                if (sb != null && sb.length() > 0) {
                    compositeProductList.get(i).setName(sb.toString());
                } else {
                    compositeProductList.get(i).setName("");
                }
            } else {
                if (set != null && !set.isEmpty()) {
                    compositeProductList.get(i).setStyle(set.iterator().next());
                } else {
                    compositeProductList.get(i).setStyle("");
                }

            }

            HttpHouseSuitProductResponse houseSuitProductResponse = new HttpHouseSuitProductResponse(
                    compositeProductList.get(i));
            houseSuitProductResponses.add(houseSuitProductResponse);
        }
        if (houseSuitProductResponses != null && !houseSuitProductResponses.isEmpty()) {
            for (HttpHouseSuitProductResponse houseSuitProduct : houseSuitProductResponses) {
                List<String> imgList = houseSuitProduct.getPictureUrlOriginal();
                if (imgList != null && imgList.size() > 0) {
                    for (int i = 0, l = imgList.size(); i < l; i++) {
                        String url = imgList.get(i);
                        if (url != null && url.length() > 0) {
                            url += appendImageMethod(ImageConfig.SIZE_LARGE);
                            imgList.set(i, url);
                        }

                    }
                }
                String saleOff = houseSuitProduct.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                houseSuitProduct.setSaleOff(saleOff);
                houseSuitProduct.setPictureUrlOriginal(imgList);
            }
        }
        return houseSuitProductResponses;
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    @Override
    public List<SuitProduct110> queryHouseSuitProductByHouseId(HttpMultiSuitRequest multiSuitRequest) {
        List<SuitProduct110> compositeProductList = productDao
                .queryHouseSuitProductByHouseId(multiSuitRequest);
        if (compositeProductList != null && !compositeProductList.isEmpty()) {
            for (SuitProduct110 suitProduct110 : compositeProductList) {
            	if(StringUtils.isNotBlank(suitProduct110.getHouseImgs())){
                    List imgList = JSONArray
                            .toList(JSONArray.fromObject(suitProduct110.getHouseImgs()));
                    suitProduct110.setHouseImgList(imgList);
                    imgList = JSONArray.toList(JSONArray.fromObject(suitProduct110
                            .getPictureUrlOriginal()));
                    suitProduct110.setPictureUrlOriginalList(imgList);
            	}
            }
        }
        if (compositeProductList != null && !compositeProductList.isEmpty()) {
            for (SuitProduct110 suitProduct110 : compositeProductList) {
                List<String> houseImgList = suitProduct110.getHouseImgList();
                List<String> newHouseImgList = new ArrayList<String>();
                if (houseImgList != null && !houseImgList.isEmpty()) {
                    for (int i = 0, l = houseImgList.size(); i < l; i++) {
                        String url = houseImgList.get(i);
                        if (url != null && !url.trim().equals("")) {
                            url += appendImageMethod(ImageConfig.SIZE_LARGE);
                            newHouseImgList.add(url);
                        }

                    }
                }
                suitProduct110.setHouseImgList(newHouseImgList);

                List<String> urlOriginalList = suitProduct110.getPictureUrlOriginalList();
                List<String> newUrlOriginalList = new ArrayList<String>();
                if (urlOriginalList != null && !urlOriginalList.isEmpty()) {
                    for (int i = 0, l = urlOriginalList.size(); i < l; i++) {
                        String url = urlOriginalList.get(i);
                        if (url != null && !url.trim().equals("")) {
                            url += appendImageMethod(ImageConfig.SIZE_LARGE);
                            newUrlOriginalList.add(url);
                        }

                    }
                }
                String saleOff = suitProduct110.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                suitProduct110.setSaleOff(saleOff);
                suitProduct110.setPictureUrlOriginalList(newUrlOriginalList);
            }
        }
        return compositeProductList;
    }

    /**
     * 这个会返回所有商品
     * @return
     */
    public Map<Long, String> queryProductInRoom() {
        Map<Long, String> map = new HashMap<Long, String>();
        List<ProductSummaryResponse> res = shoppingCartDao.queryProductInRoom();
        if (null != res && res.size() > 0) {
            for (ProductSummaryResponse p : res) {
                map.put(p.getProductId(), p.getName());
            }
        }
        return map;
    }
    
    /**
     * 这个只返回满足条件的
     * @param productId
     * @return
     */
    public Map<Long, String> queryProductInRoomByProductId(Long productId) {
        Map<Long, String> map = new HashMap<Long, String>();
        List<ProductSummaryResponse> res = shoppingCartDao.queryProductInRoomByProductId(productId);
        if (null != res && res.size() > 0) {
            for (ProductSummaryResponse p : res) {
                map.put(p.getProductId(), p.getName());
            }
        }
        return map;
    }
    
    /**
     * 这个只返回满足条件的
     * @param productIdList
     * @return
     */
    public Map<Long, String> queryProductInRoomByProductIdList(List<Long> productIdList) {
        Map<Long, String> map = new HashMap<Long, String>();
        List<ProductSummaryResponse> res = shoppingCartDao.queryProductInRoomByProductIdList(productIdList);
        if (null != res && res.size() > 0) {
            for (ProductSummaryResponse p : res) {
                map.put(p.getProductId(), p.getName());
            }
        }
        return map;
    }

    @Override
    public HttpProductMoreSingleResponse150 getMoreSingle150(HttpProductMoreSingleRequest150 request) {
       // Map<Long, String> roomMap = queryProductInRoom();
        HttpProductMoreSingleResponse150 response150 = new HttpProductMoreSingleResponse150();
        if (request != null
                && null != request.getIsNavigation()
                && request.getIsNavigation()) {
            List<ClassifyNode> classifyNodes = productTypeDao.queryClassifyNodes();
            if (classifyNodes != null && !classifyNodes.isEmpty()) { //分类
                TreeNode rootNode = new TreeNode();
                rootNode.setMenuId(1l);
                createClassifyTree(rootNode, classifyNodes);

                // 首页加的内容start
                if (null != request.getNodeId()) {
                    List<TreeNode> list = rootNode.getChildren();
                    List<TreeNode> resList = new ArrayList<TreeNode>();
                    TreeNode node = new TreeNode();
                    iteratorTree(rootNode, request.getNodeId(), node);
                    
                    if (null != node && null != node.getChildren() && node.getChildren().size() == 0) {
                    	TreeNode fatherNode = new TreeNode();
                    	iteratorFatherOfNode(rootNode, request.getNodeId(), fatherNode);
                    	fatherNode.setName(node.getName());
                    	resList.add(fatherNode);
                    }
                    
                    if (null != node && null != node.getChildren()
                            && node.getChildren().size() > 0) {
                        resList.add(node);
                    }

                    for (TreeNode t : list) {
                        if (null != t && 40 == t.getMenuId()) {
                            resList.add(t);
                        }
                    }
                    response150.setClassifyTreeList(resList);
                } else {
                    response150.setClassifyTreeList(rootNode.getChildren());
                }
                // 首页加的内容end

            }
        }
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        // 1.分页查询单品信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize);
        List<Long> filterIds = request.getFilterIdList();
        if (filterIds != null && !filterIds.isEmpty()) {
            Collections.sort(filterIds);
            for (int i = 0, l = filterIds.size(); i < l; i++) {
                Long filterId = filterIds.get(i);
                ClassifyNode classifyNode = productTypeDao.queryClassifyNodeById(filterId);
                //增加为空保护
				if (classifyNode == null || classifyNode.getStatus() == null) {
					continue;
				}
                else if (classifyNode.getStatus() == 1) {//选全部XX商品
                    ClassifyNode tempNode = productTypeDao.queryClassifyNodeById(classifyNode
                            .getParentKey());
                    List<Long> productIds = productTypeDao.queryProductNodeByClassifyName(tempNode
                            .getName());
                    if (productIds != null && !productIds.isEmpty()) {
                        params.put("productTypeId", productIds.get(0));//大分类
                    }
                } else if (classifyNode.getStatus() == 2) {//具体品类查询
                    params.put("productTypeId", classifyNode.getKey());
                } else if (classifyNode.getStatus() == 3) {//智能排序
                    params.put("productSort", 0);
                } else if (classifyNode.getStatus() == 4) {//价格降序排序
                    params.put("productSort", 1);
                } else if (classifyNode.getStatus() == 5) {//价格升序排序
                    params.put("productSort", 2);
                }
            }
        } else {
            params.put("filterNull", -1);
        }

        List<ProductSummary> productSummaryList = productDao.queryProductByCondition(params);
        List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();

        Map<Long, Long> shoppingProductIds = new HashMap<Long, Long>();
        if (StringUtils.isNotBlank(request.getAccessToken())) {
            //1.判断用户是否登录
            LogDo tLog = userDao.isLoggedIn(request.getAccessToken());
            //2.用户不登录的话查询单品详情信息
            if (tLog != null && null != tLog.getuId()) {
                List<Long> productIds = shoppingCartDao.queryShoppingCartProduct(tLog.getuId());
                if (null != productIds && productIds.size() > 0) {
                    for (Long id : productIds) {
                        shoppingProductIds.put(id, id);
                    }
                }
            }
        }

        // 2.组装返回数据给客户端
        if (null != productSummaryList && productSummaryList.size() > 0) {
        	 // Map<Long, String> roomMap = queryProductInRoom();  这个会查询所有的商品
        	//queryProductInRoomByProductList 只会查询对应的 满足条件的
        	//所有优化原因，你懂的。。。
        	List<Long> productIdList =new ArrayList<Long>();
        	for(ProductSummary product : productSummaryList){
        		if(product!=null&&null != product.getProductId()){
        			if(!productIdList.contains(product.getProductId())){
        				productIdList.add(product.getProductId());
        			}
        		}     		
        	}
        	Map<Long, String> roomMap = queryProductInRoomByProductIdList(productIdList);
            for (ProductSummary product : productSummaryList) {
                ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(product);

                if (null != shoppingProductIds &&
                        null != product &&
                        null != product.getProductId() &&
                        shoppingProductIds.size() > 0 &&
                        null != shoppingProductIds.get(product.getProductId())) {
                    productSummaryResponse.setShoppingCart(1);
                } else {
                    productSummaryResponse.setShoppingCart(0);
                }
                productSummaryResponse.setFirstContentsName(roomMap.get(productSummaryResponse.getProductId()));

                if (null != product) {
                    List<String> strResponseList = new ArrayList<String>();
                    List<String> imgList = ImageUtil.removeEmptyStr(product.getPictureUrlOriginal());
                    if (null != imgList && imgList.size() > 0) {
                        for (String str : imgList) {
                            if (null != str && !"".equals(str)) {
                                str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                                strResponseList.add(str);
                            }
                        }
                    }
                    productSummaryResponse.setPictureUrlOriginal(strResponseList);
                }
                productSummaryResponseList.add(productSummaryResponse);
            }
        }
        response150.setSingleList(productSummaryResponseList);
        Long totalRecords = productDao.queryProductCount(params);
        response150.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response150.setTotalPages(totalPages);
        return response150;
    }

    /**
     * 构建树
     * 递归
     *
     * @param classifyNodes
     */
    public void createClassifyTree(TreeNode node, List<ClassifyNode> classifyNodes) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (int i = 0, l = classifyNodes.size(); i < l; i++) {
            if (node.getMenuId().equals(classifyNodes.get(i).getParentKey())) {
                TreeNode tempNode = new TreeNode(classifyNodes.get(i));
                createClassifyTree(tempNode, classifyNodes);
                children.add(tempNode);
            }
        }
        node.setChildren(children);
    }


    public void iteratorTree(TreeNode manyTreeNode, Long menuId, TreeNode node) {
        if (manyTreeNode != null) {
            for (TreeNode index : manyTreeNode.getChildren()) {
                if (menuId.equals(index.getMenuId())) {
                    node.setMenuId(index.getMenuId());
                    node.setName(index.getName());
                    node.setChildren(index.getChildren());
                }
                if (index.getChildren() != null && index.getChildren().size() > 0) {
                    iteratorTree(index, menuId, node);
                }
            }
        }
    }
    
    /**
     * 求指定叶节点的父节点
     */
    public void iteratorFatherOfNode(TreeNode manyTreeNode, Long menuId, TreeNode node) {
        if (manyTreeNode != null) {
            for (TreeNode index : manyTreeNode.getChildren()) {
            	if (menuId.equals(index.getMenuId())) {
                    node.setMenuId(manyTreeNode.getMenuId());
                    node.setName(manyTreeNode.getName());
                    node.setChildren(manyTreeNode.getChildren());
                    return;
                }
                if (index.getChildren() != null && index.getChildren().size() > 0) {
                	iteratorFatherOfNode(index, menuId, node);
                }
            }
        }
    }


    public List<Suit> queryRandomSuit(Long suitId) {
        List<Suit> list = productDao.queryRandomRecSuit(suitId);

        for (Suit s : list) {
            if (null != s && StringUtils.isNotBlank(s.getImages())) {
                String tempstr = "";
                if (StringUtils.isNotBlank(s.getImages())) {
                    List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                    if (null != imgList && imgList.size() > 0) {
                        tempstr = imgList.get(0);
                    }
                }
                if (StringUtils.isNotBlank(tempstr)) {
                    s.setImagesUrl(tempstr);
                }
            }
        }
        return list;
    }

    /**
     * 1.5
     * .0
     * 版本首页改版
     *
     * @return
     */
    @Override
    public HttpHomeResponse home150(HttpProductHomeRequest request) {
        HttpHomeResponse response = new HttpHomeResponse();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String cityCode = "210000";
        if (null != request && StringUtils.isNotBlank(request.getCityCode())) {
            paramsMap.put("cityCode", request.getCityCode());
            cityCode = request.getCityCode();
        } else {
            paramsMap.put("cityCode", "210000");
        }
        response.setBannerList(adService.queryAdvertisement(10, Constants.AD_TOP_POSITION, cityCode, null));
        response.setButton(productDao.queryButtonSort());

        List<Suit> list = productDao.queryRecSuit(paramsMap);

        if (null != list && list.size() > 1) {
            if (list.size() % 2 != 0 && list.size() > 3) {
                list.remove(list.size() - 1);
                response.setSuitList(list);
            }
            for (Suit s : list) {
                if (null != s && StringUtils.isNotBlank(s.getImages())) {
                    String tempstr = "";
                    if (StringUtils.isNotBlank(s.getImages())) {
                        List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                        if (null != imgList && imgList.size() > 0) {
                            tempstr = imgList.get(0);
                        }
                    }
                    if (StringUtils.isNotBlank(tempstr)) {
                        if (null != request && null != request.getOsType() && null != request.getWidth()) {
                            String ss = getImages(tempstr, request.getOsType(), request.getWidth());
                            s.setImagesUrl(ss);
                        } else {
                            s.setImagesUrl(tempstr);
                        }
                    }
                }
            }
            response.setSuitList(list);
        }

        List<Recommend> recList = productDao.queryRecProduct(paramsMap);
        if (null != recList && recList.size() > 0) {
            for (Recommend r : recList) {
                if (null != r && null != r.getRecProductList() && r.getRecProductList().size() > 0) {
                    for (Product p : r.getRecProductList()) {
                        if (null != p) {
                            String tempstr = p.getImagesUrl();
                            if (StringUtils.isNotBlank(tempstr)) {
                                if (null != request && null != request.getOsType() && null != request.getWidth()) {
                                    String ss = getImages(tempstr, request.getOsType(), request.getWidth());
                                    p.setImagesUrl(ss);
                                } else {
                                    p.setImagesUrl(tempstr);
                                }
                            }
                        }
                    }
                }
            }
        }

        response.setRecList(recList);
        response.setPromiseUrl(promiseUrlConfig.getPromiseUrl());
        return response;
    }


    /**
     * 套装列表页面
     *
     * @param request
     * @return
     */
    public HttpSuitResponse getSuitList150(HttpSuitRequest150 request) {
        HttpSuitResponse response = new HttpSuitResponse();
        if (request == null) {
        	return response;
        }
        if (null != request.getIsNavigation() && request.getIsNavigation()) {
            Map<String, Object> param = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(request.getCityCode())) {
                param.put("cityCode", request.getCityCode());
            } else {
               // param.put("cityCode", "210000");
            }
            if(null != request.getRoomName()){
            	param.put("roomName", request.getRoomName());
            }
            List<ClassifyNode> classifyNodes = new ArrayList<ClassifyNode>();
            
            List<ClassifyNode> buildingClassifyNodes = productTypeDao.queryBuildingClassifyNodes(param);
            for (ClassifyNode classifyNode : buildingClassifyNodes) {
            	classifyNodes.add(classifyNode);
			}
            List<ClassifyNode> styleClassifyNodes = productTypeDao.queryStyleClassifyNodes(param);
            for (ClassifyNode classifyNode : styleClassifyNodes) {
            	classifyNodes.add(classifyNode);
			}
            List<ClassifyNode> sizeClassifyNodes = productTypeDao.querySizeClassifyNodes(param);
            for (ClassifyNode classifyNode : sizeClassifyNodes) {
            	classifyNodes.add(classifyNode);
			}
            
            if (classifyNodes != null && !classifyNodes.isEmpty()) { //分类
                TreeNode rootNode = new TreeNode();
                rootNode.setMenuId(1l);
                createClassifyTree(rootNode, classifyNodes);
                response.setClassifyTreeList(rootNode.getChildren());
            }
        }

        List<Long> filterIds = request.getFilterIdList();
        String filterStrIds = "";
        if (null != filterIds && filterIds.size() > 0) {
            for (int i = 0; i < filterIds.size(); i++) {
                if (StringUtils.isNotBlank(filterStrIds)) {
                    filterStrIds = filterStrIds + "," + filterIds.get(i);
                } else {
                    filterStrIds = "" + filterIds.get(i);
                }
            }
        }

        String conditionS = "";
        String orderSort = "";
        if (StringUtils.isNotBlank(filterStrIds)) {
            List<String> c = productTypeDao.queryCondition(filterStrIds);
            for (int i = 0; i < c.size(); i++) {
                String str = c.get(i);
                if (StringUtils.isNotBlank(str)) {
                    if (str.contains("and") || str.contains("AND")) {
                        if (StringUtils.isNotBlank(conditionS)) {
                            conditionS = "  " + conditionS + "  " + str;
                        } else {
                            conditionS = "  " + str;
                        }
                    } else {
                        if (StringUtils.isNotBlank(orderSort)) {
                            orderSort = "  " + orderSort + "," + str;
                        } else {
                            orderSort = "  " + str;
                        }
                    }
                }
            }
        }

        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageSize", pageSize);
        paramMap.put("size", (pageNo - 1) * pageSize);
        if (StringUtils.isNotBlank(conditionS)) {
            paramMap.put("conditionS", conditionS);
        }

        if (StringUtils.isNotBlank(orderSort)) {
            paramMap.put("orderSort", orderSort);
        }

        if (StringUtils.isNotBlank(request.getCityCode())) {
            paramMap.put("cityCode", request.getCityCode());
        } else {
           // paramMap.put("cityCode", "210000");
        }
        PageModel pagerModel = new PageModel();
        if(null != request.getRoomName() && !"".equals(request.getRoomName())){
        	paramMap.put("roomName", request.getRoomName());
        	String roomType = "";
        	if("客厅".equals(request.getRoomName())){
        		roomType = "1";
        	}
            if("卧室".equals(request.getRoomName())){
            	roomType = "2,3,4,9";
        	}
            if("餐厅".equals(request.getRoomName())){
            	roomType = "6";
            }
            if("书房".equals(request.getRoomName())){
            	roomType = "5";
            }
            paramMap.put("roomType", roomType);
        	if (null != paramMap.get("orderSort")) {
                String orderSorts = paramMap.get("orderSort").toString();
                orderSorts = orderSorts.replaceAll("ts.price", "tr.room_price");
                paramMap.put("orderSort", orderSorts);
            }else{
            	paramMap.put("orderSort", null);
            }
        	
            List<Room> roomList = productDao.queryRoomList(paramMap);
            for(Room room : roomList){
                room.setImageListByStr(room.getImages());
                room.setImages(null);
                if (room.getImageList().size() > 0) {
                    if (null != request.getOsType() && null != request.getWidth()) {
                        String roomFImages = getImages(room.getImageList().get(0), request.getOsType(), request.getWidth());
                        room.setRoomFImages(roomFImages);
                    } else {
                        room.setRoomFImages(room.getImageList().get(0));
                    }
                }
            }
            	
            pagerModel.setRoomList(roomList);
            pagerModel.setList(new ArrayList());
            int totalRecords = productDao.queryRoomListCount(paramMap);
            pagerModel.setTotalRecords(totalRecords);
            pagerModel.setPageNo(pageNo);
            pagerModel.setPageSize(pageSize);
            int totalPages = (totalRecords + pageSize - 1) / pageSize;
            pagerModel.setTotalPages(totalPages);
            response.setDataModel(pagerModel);
            return response;
        }

        List<SuitList> suitList = productDao.querySuitList(paramMap);

        String suitIds = "";
        for (SuitList s : suitList) {
            if (null != s && null != s.getSuitId()) {

                if (StringUtils.isNotBlank(suitIds)) {
                    suitIds = suitIds + "," + s.getSuitId();
                } else {
                    suitIds = "" + s.getSuitId();
                }

                String tempstr = "";
                if (StringUtils.isNotBlank(s.getImages())) {
                    List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                    if (null != imgList && imgList.size() > 0) {
                        tempstr = imgList.get(0);
                    }
                }

                if (StringUtils.isNotBlank(tempstr)) {
                    if (null != request.getOsType() && null != request.getWidth()) {
                        String ss = getImages(tempstr, request.getOsType(), request.getWidth());
                        s.setSuitFImages(ss);
                    } else {
                        s.setSuitFImages(tempstr);
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(suitIds)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("suitIds", suitIds);
            List<SuitList> suitList2 = productDao.queryProductCountBySuitId(params);
            for (SuitList s : suitList) {
                for (SuitList s2 : suitList2) {
                    if (null != s && null != s2
                            && (s.getSuitId()).equals(s2.getSuitId())) {
                        s.setSuitProductCount(s2.getSuitProductCount());
                    }
                }
            }
        }
            
        pagerModel.setList(suitList);
        int totalRecords = productDao.querySuitListCount(paramMap);
        pagerModel.setTotalRecords(totalRecords);
        pagerModel.setPageNo(pageNo);
        pagerModel.setPageSize(pageSize);
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pagerModel.setTotalPages(totalPages);
        response.setDataModel(pagerModel);
        
        return response;
    }


    public SuitList getSuitById(Long suitId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("size", 0);
        paramMap.put("pageSize", 1000);
        paramMap.put("suitId", suitId);
        List<SuitList> suitList = productDao.querySuitList(paramMap);
        String suitIds = "";
        for (SuitList s : suitList) {
            if (null != s && null != s.getSuitId()) {

                if (StringUtils.isNotBlank(suitIds)) {
                    suitIds = suitIds + "," + s.getSuitId();
                } else {
                    suitIds = "" + s.getSuitId();
                }
                String tempstr = "";
                if (StringUtils.isNotBlank(s.getImages())) {
                    List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                    if (null != imgList && imgList.size() > 0) {
                        tempstr = imgList.get(0);
                    }
                }
                if (StringUtils.isNotBlank(tempstr)) {
                    String ss = QiniuImageUtils.compressProductImage(tempstr, 100, 100);//tempstr + "?imageView2/4/w/100/h/100";
                    s.setSuitFImages(ss);
                }
            }
        }
        if (StringUtils.isNotBlank(suitIds)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("suitIds", suitIds);
            List<SuitList> suitList2 = productDao.queryProductCountBySuitId(params);
            for (SuitList s : suitList) {
                for (SuitList s2 : suitList2) {
                    if (null != s && null != s2
                            && (s.getSuitId()).equals(s2.getSuitId())) {
                        s.setSuitProductCount(s2.getSuitProductCount());
                    }
                }
            }
        }
        if (null != suitList && suitList.size() > 0) {
            return suitList.get(0);
        } else {
            return null;
        }
    }

    public String getImages(String imagesStr, int osType, int width) {
        String images1 = imagesStr;
        if (2 == osType && 720 < width) {
            images1 = QiniuImageUtils.compressProductImage(imagesStr, 1080, 540);//imagesStr + "?imageView2/4/w/1080/h/540";
        }
        if (2 == osType && 720 >= width) {
            images1 = QiniuImageUtils.compressProductImage(imagesStr, 540, 300);//imagesStr + "?imageView2/4/w/540/h/300";
        }

        if (1 == osType && 750 < width) {
            images1 = QiniuImageUtils.compressProductImage(imagesStr, 1080, 540);//imagesStr + "?imageView2/4/w/1080/h/540";
        }
        if (1 == osType && 750 >= width) {
            images1 = QiniuImageUtils.compressProductImage(imagesStr, 540, 300);//imagesStr + "?imageView2/4/w/540/h/300";
        }
        return images1;
    }

    @Override
    public List<RoomProduct> queryRoomProductBySuitId(Long suitId) {
        List<RoomProduct> list = productDao.queryRoomProductBySuitId(suitId);
        if (null != list && list.size() > 0) {
            for (RoomProduct p : list) {
                if (null != p) {
                    List<String> imgList = ImageUtil.removeEmptyStr(p.getImages());
                    if (null != imgList && imgList.size() > 0) {
                        p.setFirstImage(imgList.get(0));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<CompositeSingleRelation> querySuitProduct(Long suitId) {
        return productDao.querySuitProduct(suitId);
    }

    @Override
    public List<TProduct> queryProductList(String productIds) {
        return productDao.queryProductList(productIds);
    }


    @Override
    public CompositeProductReponseN querySuitRoomProductById(Long suitId) {
        return productDao.querySuitRoomProductById(suitId);
    }
    

	@Override
	public HttpUserCommendResponse queryUserCommentList(HttpUserCommentOrConsultRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize);
		params.put("productId", request.getProductId());
		params.put("type", request.getType());
		
		List<UserComment> userCommentList = productDao.queryUserCommentList(params);
		
		for (UserComment userComment : userCommentList) {
            List<String> imgList = ImageUtil.removeEmptyStr(userComment.getImages());
            List<String> strResponseList = new ArrayList<String>();
            if (null != imgList && imgList.size() > 0) {
                for (String str : imgList) {
                    if (null != str && !"".equals(str)) {
                        str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                        strResponseList.add(str);
                    }
                }
            }
            userComment.setImageList(strResponseList);
            userComment.setImages(null);
        }
		
		int totalRecords = productDao.queryUserCommentCount(params);
		
		HttpUserCommendResponse userCommendResponse = new HttpUserCommendResponse();
		userCommendResponse.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        userCommendResponse.setTotalPages(totalPages);
		
		userCommendResponse.setUserCommentList(userCommentList);
		
		return userCommendResponse;
	}

	@Override
	public int addUserConsult(HttpAddUserConsultRequest request, Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("content", request.getContent());
		params.put("productId", request.getProductId());
		params.put("productType", request.getType());
		params.put("phoneNumber", request.getPhoneNumber());
		params.put("state", 0);
		params.put("consultType", 1);
        int id = productDao.addUserConsult(params);
        /*if(id > 0){
        	sendOrderMail(feedback);
        }*/
        return id;
	}
	
	
	@Override
    public List<TSuitProduct> getSuitProduct(Long suitId) {
        return productDao.getSuitProduct(suitId);
    }

    @Override
    public List<SuitHard> getSuitHard(SuitHardRequest suitHardRequest) {
        return productDao.getSuitHard(suitHardRequest);
    }
    
    @NacosValue(value = "${product.home260.cache:false}", autoRefreshed = true)
    private boolean productHome260Cache;

    @Override
    public HttpHomeResponse home260(HttpProductHomeRequest productHomeRequest) {
        HttpHomeResponse response = null;
        String cacheKey = null;
        if (productHome260Cache) {
            cacheKey = AppRedisUtil.generateCacheKey("home260", productHomeRequest.getCityCode()
                    , productHomeRequest.getAppVersion(), productHomeRequest.getOsType(), productHomeRequest.getWidth());

            response = JsonUtils.json2obj(AppRedisUtil.get(cacheKey),HttpHomeResponse.class);
            if (null != response) {
                LOG.info("interface home260 get from cache ");
                return response;
            }
        }
        response = new HttpHomeResponse();


        Map<String, Object> cityCodeMap = new HashMap<String, Object>(1);
        String cityCode = productHomeRequest.getCityCode();
        if (StringUtils.isNotBlank(cityCode)) {
            cityCodeMap.put("cityCode", cityCode);
        } else {
            cityCodeMap.put("cityCode", "210000");
            cityCode = "210000";
        }

        String appVersion = productHomeRequest.getAppVersion();
        if (StringUtils.isNotBlank(appVersion)) {
            appVersion = appVersion.replace(".", "");
        } else {
            appVersion = "200";
        }

        Integer osType = productHomeRequest.getOsType();
        Integer width = productHomeRequest.getWidth();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("position", Constants.AD_TOP_POSITION);
        paramMap.put("cityCode", cityCode);
        paramMap.put("appVersion", appVersion);
        if (osType.intValue() != 1) {
            paramMap.put("width", 750);
        } else {
            if (width > 640) {
                paramMap.put("width", 750);
            } else {
                paramMap.put("width", 640);
            }
        }

        paramMap.put("type", 1);
        List<AdvertisementResponseVo> advList = adService.queryAdvertisement(paramMap);
        /**
         * banner 切图
         */
        setAdvList(advList,width);
        
        //进行版本控制
        int intVersion = Integer.parseInt(appVersion);
        List<AppButton> buttonList = null;
        if(intVersion >= 290) {
        	buttonList = productDao.queryButtonSort290(); 
        } else {
        	buttonList = productDao.queryButtonSort260(); 
        }
        
        //ios 2.9.1去掉在线客服
        if(intVersion > 291 && productHomeRequest.getOsType() == 1) {
        	for(int i = 0;i<buttonList.size();i++) {
        		AppButton appButton = buttonList.get(i);
        		if(appButton.getButtonName().equals("在线客服")) {
        			appButton.setButtonName("服务流程");
        			appButton.setIconUrl(apiconfig.getICON_URL());
        			appButton.setImageUrl(apiconfig.getICON_IMG_URL());
        			appButton.setType(7);
        			buttonList.set(i, appButton);
        		}
			}
        }
        
        response.setButton(buttonList);
        response.setBannerList(advList);
        //联系我们图片
        String contactUsUrl = "";
        if (null != buttonList) {   //这里buttonList为套装、空间、全品家相关按钮. 拆分成套装、空间一个List，全品家相关一个List
            LOG.info("interface buttonList Iterator start ");
            List<AppButton> h5ButtonList = new ArrayList<AppButton>();
            Iterator<AppButton> buttonIter = buttonList.iterator();
            while (buttonIter.hasNext()) {
                AppButton appButton = (AppButton) buttonIter.next();
                if (appButton.getType() == 9) { //如果为联系我们
                    contactUsUrl = appButton.getImageUrl();
                    buttonIter.remove();
                }
                int type = appButton.getType();
                if (type == 7 || type == 8) {
                    AppButton h5Button = new AppButton();
                    try {
                        PropertyUtils.copyProperties(h5Button, appButton);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        LOG.error("home260 拷贝AppButton出错 ", e.getMessage());
                    }
                    
                    h5Button.setH5Url(h5Button.getIconUrl());
                    h5Button.setIconUrl("");
                    h5ButtonList.add(h5Button);
                    buttonIter.remove();
                    
//                    String buttonName = h5Button.getButtonName();
//                    Integer filterId = h5Button.getFilterId();
//                    if (null != filterId) {
//                        StringBuilder sb = new StringBuilder("http://h5.ihomefnt.com/family/suitList/");
//                        sb.append(filterId);
//                        sb.append("?wpfSuitName=");
//                        sb.append(buttonName);
//                        h5Button.setH5Url(sb.toString());
//                    } else {
//                        if (type == 7 ) {
//                            h5Button.setH5Url("http://h5.ihomefnt.com/family/suitItem");
//                        }
//                    }
                    
                }
                LOG.info("interface buttonList Iterator end ");
            }
			if (StringUtils.isNotBlank(contactUsUrl)) {
				Integer imageHeight = 682;
				Integer imageWidth = 1242;
				ContactUsImageUrl url = new ContactUsImageUrl();
				if(width==null){
					url.setContactUrl(contactUsUrl);
				}else{
					url.setContactUrl(QiniuImageUtils.compressImageAndSamePic(contactUsUrl, width, -1));
				}
				
				url.setHeight(imageHeight);
				url.setWidth(imageWidth);
				response.setContactUsImageUrl(url);
			}
            
            response.setH5Button(h5ButtonList);
        }
        response.setWpfUrl(apiconfig.getWPF_HOME_URL());
        LOG.info("interface getRecommandSuit start ");
        List<Suit> resList = getRecommandSuit(cityCodeMap, osType, width);
        LOG.info("interface getRecommandSuit end ");
        response.setSuitList(resList);
        response.setPromiseUrl(promiseUrlConfig.getPromiseUrl());
		// 超过 2.10.0版本 需要展示
		if (VersionUtil.mustUpdate("2.10.0", productHomeRequest.getAppVersion())) {
			// 获取艺术品列表
			List<Artwork> artworksRecommend = artService.getArtworksRecommend(artworkPageSize, 1);
			List<Artwork> artworks = new ArrayList<Artwork>();
			if (null != artworksRecommend && artworksRecommend.size() == 8) {
				artworks = artworksRecommend;
			} else {
                if (null != artworksRecommend) {
                    // 推荐艺术品不足8件的时候，补全
                    int size = artworksRecommend.size();
                    List<Artwork> artworksRecommend2 = artService.getArtworksRecommend(artworkPageSize - size, 0);
                    artworks.addAll(artworksRecommend);
                    artworks.addAll(artworksRecommend2);
                }
			}

			for (Artwork artwork : artworks) {
				artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 260,
						180));
			}
			response.setArtworkList(artworks);
		}

        LOG.info("interface tkdmDao.loadTKDM start ");
        TKDMSeo tkdmSeo = tkdmDao.loadTKDM("首页");
        LOG.info("interface tkdmDao.loadTKDM end ");
        if(null == tkdmSeo){
            tkdmSeo = new TKDMSeo();
            tkdmSeo.setTitle("");
            tkdmSeo.setKeyword("");
            tkdmSeo.setDescription("");
        }
        response.setSeo_title(tkdmSeo.getTitle());
        response.setSeo_keyword(tkdmSeo.getKeyword());
        response.setSeo_description(tkdmSeo.getDescription());

        if (productHome260Cache) {
            LOG.info("interface cache start ");
            AppRedisUtil.set(cacheKey, JsonUtils.obj2json(response), 3600);
        }
        LOG.info("interface service home260() end ");

        return response;
    }

	/**
	 * 100% 保证图片完整
	 * 
	 * @param advList
	 */
	private void setAdvList(List<AdvertisementResponseVo> advList, Integer width) {

		if (CollectionUtils.isEmpty(advList) || width == null) {
			return;
		}

		for (AdvertisementResponseVo adv : advList) {
			String bannerUrl = adv.getBannerUrl();
			if (StringUtils.isNotBlank(bannerUrl)) {
				bannerUrl = QiniuImageUtils.compressImageAndSamePic(bannerUrl, width, -1);
				adv.setBannerUrl(bannerUrl);
			}
		}
	}
	

    
    /**
     * 获取推荐套装.
     * @param cityCodeMap
     * @param osType
     * @param width
     * @return
     */
    private List<Suit> getRecommandSuit(Map<String, Object> cityCodeMap, Integer osType, Integer width) {
        List<String> contentList = productDao.queryRecProductCms(cityCodeMap);  //爆款推荐/大牌推荐/新品特卖
        List<Suit> list = new ArrayList<Suit>();
        if(null != contentList&& contentList.size()>0){
            String content = contentList.get(0);
            JSONObject jsonObj = JSONObject.fromObject(content);
            if (null != jsonObj) {
                JSONObject fullHouse = JSONObject.fromObject(jsonObj.get("fullHouse"));
                JSONObject recObjlist = JSONObject.fromObject(fullHouse.get("RecObjlist"));
                JSONArray suitRecList = JSONArray.fromObject(recObjlist.get("suitRecList"));
                JSONObject reclist = JSONObject.fromObject(suitRecList.get(0));
                JSONArray suitList = JSONArray.fromObject(reclist.get("suitList"));
                
                for(int i=0;i<suitList.size();i++){
                    JSONObject suit = JSONObject.fromObject(suitList.get(i));
                    JSONArray suitType = JSONArray.fromObject(suit.get("suitType"));
                    String suitTypeStr = null;
                    Integer suitTypeId = null;
                    for(int j=0;j<suitType.size();j++){
                        JSONObject type = JSONObject.fromObject(suitType.get(j));
                        if((boolean) type.get("checked")){
                            suitTypeStr = type.getString("name");
                            suitTypeId = type.getInt("typeId");
                            if(null == suitTypeId){
                                if("新品特卖".equals(suitTypeStr)){
                                    suitTypeId = 1;
                                }
                                if("低价爆款".equals(suitTypeStr)){
                                    suitTypeId = 2;
                                }
                                if("大牌推荐".equals(suitTypeStr)){
                                    suitTypeId = 3;
                                }
                            }
                        }
                    }
                    String suitId = suit.getString("suitId");
                    String suitName = suit.getString("suitName");
                    String suitImages = suit.getString("suitImages");
                    String styleName = suit.getString("styleName");
                    String feature = suit.getString("feature");
                    
                    if (StringUtils.isNotBlank(styleName) 
                            && styleName.length() < 4 && styleName.indexOf("风格") == -1) {
                        styleName = styleName + "风格";
                    }
                    
                    if ("null".equalsIgnoreCase(feature)) {
                        feature = "";
                    }
                    
                    Suit suitInfo = new Suit();
                    suitInfo.setSuitId(Long.parseLong(suitId));
                    suitInfo.setSuitName(suitName);
                    suitInfo.setTypeName(suitTypeStr);
                    suitInfo.setTypeId(suitTypeId);
                    suitInfo.setImages(suitImages);
                    suitInfo.setStyleName(styleName);
                    suitInfo.setFeature(feature);
                    list.add(suitInfo);
                }
            }
        }
        
        List<Suit> resList = new ArrayList<Suit>();
        resList.addAll(list);

        if (null != resList && resList.size() > 1) {
            for (Suit s : resList) {
                if (null != s && StringUtils.isNotBlank(s.getImages())) {
                    String tempstr = "";
                    if (StringUtils.isNotBlank(s.getImages())) {
                        List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                        if (null != imgList && imgList.size() > 0) {
                            tempstr = imgList.get(0);
                            if (StringUtils.isBlank(tempstr) && imgList.size() > 1) {
                                tempstr = imgList.get(1);
                            }
                        }
                    }
                    
                    if (StringUtils.isNotBlank(tempstr)) {
                        if (null != osType && null != width) {
                        	int smallWidth =width*ImageSize.WIDTH_PER_SIZE_90/ImageSize.WIDTH_PER_SIZE_100;
                            String ss = QiniuImageUtils.compressImageAndDiffPic(tempstr, smallWidth, -1);
                            s.setImagesUrl(ss);
                        } else {
                            s.setImagesUrl(tempstr);
                        }
                    }
                }
            }
        }
        
        return resList;
    }

    @Override
	public HttpUserConsultResponse queryUserConsultList(
			HttpUserCommentOrConsultRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
		
		int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize);
		params.put("productId", request.getProductId());
		params.put("type", request.getType());
		
		List<UserConsult> userConsultList = productDao.queryUserConsultList(params);
		
		int totalRecords = productDao.queryUserConsultCount(params);
		
		HttpUserConsultResponse userConsultResponse = new HttpUserConsultResponse();
		userConsultResponse.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        userConsultResponse.setTotalPages(totalPages);
		
        userConsultResponse.setUserConsultList(userConsultList);
		
		return userConsultResponse;
	}

	@Override
	public CompositeProduct queryLocationByBuildingId(Long buildingId) {
		return productDao.queryLocationByBuildingId( buildingId);
	}

	@Override
	public int queryUserCommentCount(HttpUserCommentOrConsultRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", request.getProductId());
		params.put("type", request.getType());
		return productDao.queryUserCommentCount(params);
	}

	@Override
	public int queryUserConsultCount(HttpUserCommentOrConsultRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", request.getProductId());
		params.put("type", request.getType());
		return productDao.queryUserConsultCount(params);
	}
	
	/**
     * 1.7.0
     * 版本首页改版
     *
     * @return
     */
    @Override
    public HttpHomeResponse home170(HttpProductHomeRequest request) {
        HttpHomeResponse response = new HttpHomeResponse();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String cityCode = "210000";
        if (null != request && StringUtils.isNotBlank(request.getCityCode())) {
            paramsMap.put("cityCode", request.getCityCode());
            cityCode = request.getCityCode();
        } else {
            paramsMap.put("cityCode", "210000");
        }
        List<AdvertisementResponseVo> advList = null;
        Integer osType=request.getOsType();     
        String appVersion="";
        if (StringUtils.isNotBlank(request.getAppVersion())) {
            appVersion=request.getAppVersion().replace(".", "");
        }else{
            appVersion="200";
        }
//        if(appVersion.startsWith("2")){
//        	appVersion="200";
//        }
        Integer width=request.getWidth();
        int position= Constants.AD_TOP_POSITION;
        Map<String, Object> paramMap = new HashMap<String, Object>();

            paramMap.put("position", position);
    	    paramMap.put("cityCode", cityCode);
    	    
    	    paramMap.put("appVersion",appVersion);
    	    
        if (osType.intValue() != 1) {
            paramMap.put("width", 750);
        } else {
            if (width > 640) {
                paramMap.put("width", 750);
            } else {
                paramMap.put("width", 640);
            }

        }

        	paramMap.put("type", 1);
        	if(appVersion.startsWith("2")){
        		advList = adService.queryAdvertisement(paramMap);
        	}else {
        		advList= adService.queryAdvertisement1(paramMap);
        	}
        	

        if (StringUtils.isNotBlank(request.getAppVersion()) 
        		&& Integer.valueOf(request.getAppVersion().replace(".", "")) >= 200){
        	response.setButton(productDao.queryButtonSort200());
        } else {
        	response.setButton(productDao.queryButtonSort170());
        }
        response.setBannerList(advList);
        
        List<AdvertisementResponseVo> modelPatnerList = adService.queryAdvertisement(10, Constants.AD_TOP_POSITION, cityCode, 2);
        List<ModelRoomPartner> modelRoomPartner = new ArrayList<ModelRoomPartner>();

        for (AdvertisementResponseVo r : modelPatnerList) {
            if (null != r) {
                ModelRoomPartner p = new ModelRoomPartner();
                p.setBannerUrl(r.getBannerUrl());
                p.setJumpUrl(r.getJumpUrl());
                modelRoomPartner.add(p);
            }
        }
        response.setModelRoomPartner(modelRoomPartner);
        List<Suit> resList = new ArrayList<Suit>();
        /*List<Suit> newList = productDao.queryNewSuit(paramsMap);//新品推荐
        resList.addAll(newList);

        String suitIds = "";
        for (Suit s : newList) {
            if (StringUtils.isNotBlank(suitIds)) {
                suitIds = suitIds + "," + s.getSuitId();
            } else {
                suitIds = s.getSuitId() + "";
            }
        }*/
//        if(StringUtils.isNotBlank(suitIds)){
//        	param.put("suitIds", suitIds);
//        }

//        List<Suit> sellCntList = productDao.querySellCntSuit(param);//爆款
//        resList.addAll(sellCntList);
//        for(Suit s : sellCntList) {
//        	if(StringUtils.isNotBlank(suitIds)){
//        		suitIds = suitIds + "," + s.getSuitId();
//        	} else {
//        		suitIds = s.getSuitId() + "";
//        	}
//        }

        /*if (StringUtils.isNotBlank(suitIds)) {
            paramsMap.put("suitIds", suitIds);
        }*/
        //List<Suit> list2 = productDao.queryRecSuit(paramsMap);//爆款推荐/大牌推荐/新品特卖
        List<String> contentList = productDao.queryRecProductCms(paramsMap);//爆款推荐/大牌推荐/新品特卖
        List<Suit> list = new ArrayList<Suit>();
        if(null != contentList&& contentList.size()>0){
        	String content = contentList.get(0);
            JSONObject jsonObj = JSONObject.fromObject(content);
            if (null != jsonObj) {
            	
            	JSONObject fullHouse = JSONObject.fromObject(jsonObj.get("fullHouse"));
            	JSONObject recObjlist = JSONObject.fromObject(fullHouse.get("RecObjlist"));
            	JSONArray suitRecList = JSONArray.fromObject(recObjlist.get("suitRecList"));
            	JSONObject reclist = JSONObject.fromObject(suitRecList.get(0));
            	JSONArray suitList = JSONArray.fromObject(reclist.get("suitList"));
            	
            	for(int i=0;i<suitList.size();i++){
            		JSONObject suit = JSONObject.fromObject(suitList.get(i));
            		JSONArray suitType = JSONArray.fromObject(suit.get("suitType"));
            		String suitTypeStr = null;
            		Integer suitTypeId = null;
            		for(int j=0;j<suitType.size();j++){
            			JSONObject type = JSONObject.fromObject(suitType.get(j));
            			if((boolean) type.get("checked")){
            				suitTypeStr = type.get("name").toString();
            				suitTypeId = (Integer) type.get("typeId");
            				if(null == suitTypeId){
            					if("新品特卖".equals(suitTypeStr)){
            						suitTypeId = 1;
            					}
                                if("低价爆款".equals(suitTypeStr)){
                                	suitTypeId = 2;
            					}
                                if("大牌推荐".equals(suitTypeStr)){
                                	suitTypeId = 3;
                                }
            				}
            			}
            		}
            		String suitId = suit.get("suitId").toString();
            		String suitName = suit.get("suitName").toString();
            		String suitImages = suit.get("suitImages").toString();
            		
            		Suit suitInfo = new Suit();
            		suitInfo.setSuitId(Long.parseLong(suitId));
            		suitInfo.setSuitName(suitName);
            		suitInfo.setTypeName(suitTypeStr);
            		suitInfo.setTypeId(suitTypeId);
            		suitInfo.setImages(suitImages);
            		list.add(suitInfo);
            	}
            	
            }
        	
        }
        
        List<String> productContent = productDao.queryRecProductCms(new HashMap<String, Object>());//推荐单品
        List<ProductSummary> productSummaryList = new ArrayList<ProductSummary>();
        if(null != productContent&&StringUtils.isNotBlank(productContent.get(0))){
        	String content = productContent.get(0);
            JSONObject jsonObj = JSONObject.fromObject(content);
            if (null != jsonObj) {
            	
            	JSONObject wholeCountry = JSONObject.fromObject(jsonObj.get("wholeCountry"));
            	JSONObject singleRecommend = JSONObject.fromObject(wholeCountry.get("singleRecommend"));
            	JSONObject recObjlist = JSONObject.fromObject(singleRecommend.get("RecObjlist"));
            	JSONArray productRecList = JSONArray.fromObject(recObjlist.get("productRecList"));
            	
            	for(int i=0;i<productRecList.size();i++){
            		JSONObject product = JSONObject.fromObject(productRecList.get(i));
            		if("App推荐：".equals(product.get("type"))){
            			JSONArray productList = JSONArray.fromObject(product.get("productList"));
            			
            			for(int j=0;j<productList.size();j++){
            				JSONObject productNode = JSONObject.fromObject(productList.get(j));
            				String productId = productNode.get("productId").toString();
                    		String productName = productNode.get("productName").toString();
                    		double price = Double.parseDouble(productNode.get("price").toString());
                    		double priceMarket = Double.parseDouble(productNode.get("priceMarket").toString());
                    		String images = productNode.get("images").toString();
                    		ProductSummary productSummary = new ProductSummary();
                    		productSummary.setProductId(Long.parseLong(productId));
                    		productSummary.setName(productName);
                    		productSummary.setPriceCurrent(price);
                    		productSummary.setPriceMarket(priceMarket);
                    		productSummary.setPictureUrlOriginal(images);
                    		productSummaryList.add(productSummary);
            			}
            			
            		}
            	}
            }
        }
        
        resList.addAll(list);

        if (null != resList && resList.size() > 1) {
            for (Suit s : resList) {
                if (null != s && StringUtils.isNotBlank(s.getImages())) {
                    String tempstr = "";
                    if (StringUtils.isNotBlank(s.getImages())) {
                        List<String> imgList = ImageUtil.removeEmptyStr(s.getImages());
                        if (null != imgList && imgList.size() > 0) {
                            tempstr = imgList.get(0);

                            if (StringUtils.isBlank(tempstr) && imgList.size() > 1) {
                                tempstr = imgList.get(1);
                            }

                        }
                    }
                    if (StringUtils.isNotBlank(tempstr)) {
                        if (null != request && null != request.getOsType() && null != request.getWidth()) {
                            String ss = getImages(tempstr, request.getOsType(), request.getWidth());
                            s.setImagesUrl(ss);
                        } else {
                            s.setImagesUrl(tempstr);
                        }
                    }
                }
            }
            response.setSuitList(resList);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("size", 10);
        params.put("from", 0);
        params.put("ishome", 1);
        //List<ProductSummary> productSummaryList = productDao.queryProductByCondition(params);
        List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();
        
        if (null != productSummaryList && productSummaryList.size() > 0) {
            for (ProductSummary product : productSummaryList) {
                ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(product);
                List<String> imgList = ImageUtil.removeEmptyStr(product.getPictureUrlOriginal());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                productSummaryResponse.setPictureUrlOriginal(strResponseList);
                productSummaryResponseList.add(productSummaryResponse);
            }
        }
        response.setProductSummaryList(productSummaryResponseList);
        response.setPromiseUrl(promiseUrlConfig.getPromiseUrl());
        return response;
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    @Override
    public List<HttpHouseSuitProductResponse> queryHouseSuitProduct(int count) {
        List<HouseSuitProduct> compositeProductList = productDao.queryHouseSuitProduct(count);
        List<HttpHouseSuitProductResponse> houseSuitProductResponses = new ArrayList<HttpHouseSuitProductResponse>();
        for (int i = 0; i < compositeProductList.size(); i++) {
            compositeProductList.get(i).setPictureUrlOriginal(null);
            List<SuitProduct> suitProducts = productDao
                    .querySuitProductsByHouseId(compositeProductList.get(i).getHouseId());
            Set<String> set = new HashSet<String>();
            if (suitProducts != null && !suitProducts.isEmpty()) {
                for (int j = 0; j < suitProducts.size(); j++) {
                    set.add(suitProducts.get(j).getStyle());
                    if (suitProducts.get(j).getOffLineExperience() != null
                            && suitProducts.get(j).getOffLineExperience() == 1
                            && compositeProductList.get(i).getPictureUrlOriginal() == null) {
                        String images = suitProducts.get(j).getImages();
                        List imgList = JSONArray.toList(JSONArray.fromObject(images));
                        List<String> newImgList = new ArrayList<String>();
                        for (Object objImg : imgList) {
                            if (objImg != null && !objImg.toString().trim().equals("")) {
                                newImgList.add(objImg.toString());
                            }
                        }
                        compositeProductList.get(i).setPictureUrlOriginal(newImgList);
                        break;
                    }
                }
            }
            if (compositeProductList.get(i).getPictureUrlOriginal() == null && suitProducts != null
                    && !suitProducts.isEmpty()) {
                String images = suitProducts.get(0).getImages();
                if (images != null && images.length() > 1) {
                    List imgList = JSONArray.toList(JSONArray.fromObject(images));
                    List<String> newImgList = new ArrayList<String>();
                    for (Object objImg : imgList) {
                        if (objImg != null && !objImg.toString().trim().equals("")) {
                            newImgList.add(objImg.toString());
                        }
                    }
                    compositeProductList.get(i).setPictureUrlOriginal(newImgList);
                } else {
                    compositeProductList.get(i).setPictureUrlOriginal(null);
                }

            }
            if (suitProducts != null && suitProducts.size() > 1) {
                compositeProductList.get(i).setIsShowDeal(false);
                compositeProductList.get(i).setStyle("{" + suitProducts.size() + "种风格}");
                compositeProductList.get(i).setCompositeProductId(-1l);
                // 组装数据
                AreaDto city = this.areaService.queryCity(compositeProductList.get(i).getCityCode());
                String cityName = city.getAreaName();
                if (city != null && cityName.length() > 2) {
                    cityName = city.getAreaName().substring(0, cityName.length() - 1);
                }
                Long houseId = compositeProductList.get(i).getHouseId();
                House house = this.houseService.queryHouseById(houseId);
                String houseName = "";
                if (house != null) {
                    houseName = house.getName();
                }
                String developerName = "";
                String buildingName = "";
                Building building = this.buildingService.queryBuildingByHouseId(houseId);
                if (building != null) {
                    buildingName = building.getName();
                    developerName = building.getDeveloper();
                }
                //套装标题名称 = 城市 + 开发商名称 + 楼盘名称 + 户型名称 + ”家居套装“
                StringBuffer sb = new StringBuffer();
                sb.append(cityName);
                sb.append(developerName);
                sb.append(buildingName);
                sb.append(houseName);
                sb.append("家居套装");
                if (sb != null && sb.length() > 0) {
                    compositeProductList.get(i).setName(sb.toString());
                } else {
                    compositeProductList.get(i).setName("");
                }
            } else {
                if (set != null && !set.isEmpty()) {
                    compositeProductList.get(i).setStyle("{" + set.iterator().next() + "}");
                } else {
                    compositeProductList.get(i).setStyle("");
                }
            }

            HttpHouseSuitProductResponse houseSuitProductResponse = new HttpHouseSuitProductResponse(
                    compositeProductList.get(i));
            houseSuitProductResponses.add(houseSuitProductResponse);
        }
        if (houseSuitProductResponses != null && !houseSuitProductResponses.isEmpty()) {
            for (HttpHouseSuitProductResponse houseSuitProduct : houseSuitProductResponses) {
                List<String> imgList = houseSuitProduct.getPictureUrlOriginal();
                if (imgList != null && imgList.size() > 0) {
                    for (int i = 0, l = imgList.size(); i < l; i++) {
                        String url = imgList.get(i);
                        if (url != null && url.length() > 0) {
                            url += appendImageMethod(ImageConfig.SIZE_LARGE);
                            imgList.set(i, url);
                        }

                    }
                }
                String saleOff = houseSuitProduct.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                houseSuitProduct.setSaleOff(saleOff);
                houseSuitProduct.setPictureUrlOriginal(imgList);
            }
        }
        return houseSuitProductResponses;
    }
    

}