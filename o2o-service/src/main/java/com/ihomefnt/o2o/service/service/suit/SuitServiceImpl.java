/**
 * 
 */
package com.ihomefnt.o2o.service.service.suit;

import com.ihomefnt.common.util.image.ImageQuality;
import com.ihomefnt.common.util.image.ImageTool;
import com.ihomefnt.common.util.image.ImageType;
import com.ihomefnt.o2o.intf.dao.product.ProductDao;
import com.ihomefnt.o2o.intf.dao.suit.SuitDao;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.suit.dto.*;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.HttpSuitResponse;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.service.suit.SuitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 *
 */
@Service
public class SuitServiceImpl implements SuitService {

	@Autowired
	SuitDao suitDao;
	@Autowired
	ProductDao productDao;
	
	@Override
	public List<RoomImage> getRoomImageListByRoomId(Long roomId, Long designStatus) {
		return suitDao.getRoomImageListByRoomId(roomId,designStatus);
	}
	
	@Override
	public List<RoomImage> getRoomImageListByRoomIdAndDetailPage(Long roomId,Long designStatus,Long detailPage) {
		return suitDao.getRoomImageListByRoomIdAndDetailPage(roomId,designStatus,detailPage);
	}
	
	@Override
	public List<RoomImage> getRoomImageListBySuitIdAndDetailPage(Long suitId,Long designStatus,Long detailPage){
		return suitDao.getRoomImageListBySuitIdAndDetailPage(suitId,designStatus,detailPage);
	}
	
	@Override
	public Suit getSuitByPK(Long suitId){
		return suitDao.getSuitByPK(suitId);
	}
	
	@Override
	public List<Room> getRoomListBySuitId(Long suitId){
		return suitDao.getRoomListBySuitId(suitId);
	}
	
	@Override
	public List<Product> getRoomProductListByRoomId(Long roomId){
		return suitDao.getRoomProductListByRoomId(roomId);
	}
	
	@Override
	public List<Suit> getSugestedSuitListBySuit(Suit suit){
		return suitDao.getSugestedSuitListBySuit(suit);
	}
	
	@Override
	public Room getRoomInfoByRoomId(Long roomId) {
		Room roomInfo = new Room();
		List<RoomImage> graphicDesign = suitDao.getRoomImageListByRoomId(roomId,SuitConstant.DESIGN_STATUS_YES);
		//取首张平面图
		if(CollectionUtils.isNotEmpty(graphicDesign)){
			roomInfo.setGraphicDesign(graphicDesign.get(0));
		}		
		Room roomTemp = getProductListByRoomId(roomId);
		String firstImage = null;
		// 增加为空保护
		if (roomTemp != null) {
			firstImage = roomTemp.getFirstImage();
		}
		
		List<RoomImage> imageAlllist = suitDao.getRoomImageListByRoomIdAndDetailPage(roomId,SuitConstant.DESIGN_STATUS_NO,null);	
		List<String> urlList= new ArrayList<String>();
		List<RoomImage> roomImageList= new ArrayList<RoomImage>();
		// 对空间的所有图片做保护
		if (CollectionUtils.isNotEmpty(imageAlllist)) {			
			for(RoomImage roomImage:imageAlllist){
				String imageUrl=roomImage.getImageUrl();
				if(StringUtils.isNotBlank(imageUrl)){
					roomImage.setImageUrl(imageUrl+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
				}
				Long detailpage=roomImage.getDetailpage();
				RoomImage clone = null;
				try {
					 clone =(RoomImage) roomImage.clone();
					 if(StringUtils.isNotBlank(imageUrl)){
						 clone.setImageUrl(imageUrl+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
					 }				
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotBlank(imageUrl)&&StringUtils.isNotBlank(firstImage)&&imageUrl.equals(firstImage)){
					if(!urlList.contains(imageUrl)){
						urlList.add(imageUrl);
						roomImageList.add(clone);
						continue;
					}

				}
				if(detailpage!=null&&detailpage==SuitConstant.DETAIL_PAGE_YES){
					if(!urlList.contains(imageUrl)){
						urlList.add(imageUrl);
						roomImageList.add(clone);
					}					
				}
			}
			//对空间的图文详情图片做保护
			if(CollectionUtils.isNotEmpty(roomImageList)){
				roomInfo.setImagelist(roomImageList);
			}
		}	
		if (roomTemp != null) {
			roomInfo.setRoomProductCount(roomTemp.getRoomProductCount());
			roomInfo.setProductList(roomTemp.getProductList());
		}
		RoomLabel roomLabel = suitDao.getRoomLabel(roomId);
		List<String> brandList = suitDao.getRoomBrandList(roomId);
		roomInfo.setBrandList(brandList);
		if (roomLabel != null) {
			roomInfo.setColors(roomLabel.getColorJson());
			roomInfo.setMaterials(roomLabel.getMaterialJson());
			roomInfo.setStorages(roomLabel.getStorageJson());
			roomInfo.setStyles(roomLabel.getStyleJson());
			//id 转换成value值
			List<String> styles=ImageUtil.removeEmptyStr(roomLabel.getStyleJson());
			if(CollectionUtils.isNotEmpty(styles)){
				List<String> styleList=suitDao.getStylesByPks(styles);
				if(CollectionUtils.isNotEmpty(styleList)){
					roomInfo.setStyleList(styleList);
				}else{
					roomInfo.setStyleList(null);
				}
			}else{
				roomInfo.setStyleList(null);
			}		
			roomInfo.setTemperaments(roomLabel.getTemperamentJson());
		}
		return roomInfo;
	}

	@Override
	public Suit getSuitInfoByRoomId(Long roomId) {
		return suitDao.getSuitInfoByRoomId(roomId);
	}

	@Override
	public List<Room> getRecommendRoomList(Long roomId,String cityCode) {
		//推荐空间列表业 = 同楼盘的同户型的同类型空间 *2 + 库中同风格的的同类型空间*1
		//为防止同楼盘和同风格中存在重复的楼盘，同楼盘空间查询3条，同风格查询1条，合并且去重处理
		com.ihomefnt.o2o.intf.domain.product.doo.Room room = this.productDao.queryRoomById(roomId);
		
		//1.同楼盘的同户型的同类型空间
		List<Room> sameHouse = suitDao.querySameHouseRoom(roomId);

		//2.库中同风格的的同类型空间
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("cityCode", cityCode);
		List<Room> sameStyle = new ArrayList<Room>();
		if (room != null) {
			paramMap.put("style", room.getStyle());
			paramMap.put("roomType", room.getRoomType());
			sameStyle = suitDao.querySameStyleRoom(paramMap);
		}
		
		
		//去重
		int existCount=0;
		for (int i = 0; i < sameHouse.size();) {
			boolean exist=false;
			for (int j = 0; j < sameStyle.size(); j++) {
				if(sameHouse.get(i).getRoomId()==sameStyle.get(j).getRoomId()){
					exist=true;
					existCount++;
					continue;
				}
			}
			if(exist){
				sameHouse.remove(i);
			}else{
				i++;
			}
		}
		if(sameHouse.size()>2 && sameStyle.size()>0 && existCount==0){
			int sameHouseSize=sameHouse.size();
			for (int i = sameHouseSize; i > sameHouseSize-sameStyle.size()-existCount; i--) {
				sameHouse.remove(i-1);
			}
		}
		
		//合并
		List<Room> recommendList=new ArrayList<Room>();
		recommendList.addAll(sameHouse);
		if(CollectionUtils.isNotEmpty(sameStyle)){
			recommendList.addAll(sameStyle);
		}		
		return recommendList;
		
	}

	@Override
	public Room getProductListByRoomId(Long roomId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		List<Room> roomList = suitDao.queryProductList(paramMap);
		Room roomInfo = null;
		if (null != roomList && roomList.size() > 0) {
			roomInfo = roomList.get(0);
		}
		if(null != roomInfo && null != roomInfo.getProductList()){
            for (Product product : roomInfo.getProductList()) {
                List<String> imageList = ImageUtil.removeEmptyStr(product.getProductImage());
                if(null != imageList && imageList.size() > 0){
                	product.setProductImage(imageList.get(0)+ImageTool.appendTypeAndQuality(ImageType.SMALL, ImageQuality.HIGH));
                }
            }
		}
		return roomInfo;
	}
	
	@Override
	public Room getProductListByRoomIdAndSuitId(Long roomId,Long suitId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("suitId", suitId);
		List<Room> roomList = suitDao.queryProductList(paramMap);
		Room roomInfo = null;
		if (null != roomList && roomList.size() > 0) {
			roomInfo = roomList.get(0);
		}
		if(null != roomInfo && null != roomInfo.getProductList()){
            for (Product product : roomInfo.getProductList()) {
                List<String> imageList = ImageUtil.removeEmptyStr(product.getProductImage());
                if(null != imageList && imageList.size() > 0){
                	product.setProductImage(imageList.get(0)+ImageTool.appendTypeAndQuality(ImageType.SMALL, ImageQuality.HIGH));
                }
            }
		}
		return roomInfo;
	}

	public List<String> getStylesByPks(List<String> pks){

		return suitDao.getStylesByPks(pks);
	}

	@Override
	public HttpSuitResponse getSuitInfoBySuitId(Long suitId) {
		Suit suit =this.getSuitByPK(suitId);
		//对后台查询结果做保护
		if (suit == null) {
			throw new BusinessException(HttpResponseCode.FAILED, SuitConstant.MESSAGE_RESULT_DATA_EMPTY);
		}
		List<Room> roomList= this.getRoomListBySuitId(suitId);
		List<Room> roomListExceptNullProduct = new ArrayList<Room>();
		//对空间做保护
		if(roomList!=null&&!roomList.isEmpty()){
			for(Room room:roomList){
				Long roomId =room.getRoomId();
				room.setSuitStyle(suit.getSuitStyle());
				List<String> styles=ImageUtil.removeEmptyStr(room.getStyles());
				if(styles!=null&&!styles.isEmpty()){
					List<String> styleList=this.getStylesByPks(styles);
					if(styleList!=null&&!styleList.isEmpty()){
						room.setStyleList(styleList);
					}else{
						room.setStyleList(null);
					}
				}else{
					room.setStyleList(null);
				}
				List<RoomImage> roomDesignImageList=this.getRoomImageListByRoomIdAndDetailPage(roomId,SuitConstant.DESIGN_STATUS_YES,null);
				//对平面设计图做保护
				if(roomDesignImageList!=null&&!roomDesignImageList.isEmpty()){
					RoomImage graphicDesign =roomDesignImageList.get(0);//平面图只会一张
					String imageUrl=graphicDesign.getImageUrl();
					if(org.apache.commons.lang.StringUtils.isNotBlank(imageUrl)){
						room.setGraphicDesign(graphicDesign);
					}else{
						room.setGraphicDesign(null);
					}
				}else{
					room.setGraphicDesign(null);
				}
				String firstImage=room.getFirstImage();
				List<RoomImage> imageAlllist = this.getRoomImageListByRoomIdAndDetailPage(roomId,SuitConstant.DESIGN_STATUS_NO,null);
				List<String> urlList= new ArrayList<String>();
				List<RoomImage> roomImageList= new ArrayList<RoomImage>();
				// 对空间的所有图片做保护
				if (imageAlllist != null && !imageAlllist.isEmpty()) {
					room.setImageAlllist(imageAlllist);
					for(RoomImage roomImage:imageAlllist){
						String imageUrl=roomImage.getImageUrl();
						if(imageUrl!=null){
							roomImage.setImageUrl(imageUrl+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
						}
						RoomImage clone = null;
						try {
							clone =(RoomImage) roomImage.clone();
							clone.setImageUrl(imageUrl+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
						Long detailpage=roomImage.getDetailpage();
						if(imageUrl!=null&&firstImage!=null&&imageUrl.equals(firstImage)){
							if(!urlList.contains(imageUrl)){
								urlList.add(imageUrl);
								roomImageList.add(clone);
								continue;
							}

						}
						if(detailpage!=null&&detailpage==SuitConstant.DETAIL_PAGE_YES){
							if(!urlList.contains(imageUrl)){
								urlList.add(imageUrl);
								roomImageList.add(clone);
							}
						}
					}
					//对空间的图文详情图片做保护
					if(roomImageList!=null&&!roomImageList.isEmpty()){
						room.setImagelist(roomImageList);
					}
				}
				List<Product> productList=this.getRoomProductListByRoomId(roomId);
				List<String> brandList =new ArrayList<String>();
				//对空间商品做保护
				if(productList!=null&&!productList.isEmpty()){
					room.setProductList(productList);
					Long roomProductCount=0L;
					for(Product product:productList){
						roomProductCount+=product.getProductCount();
						String brand =product.getBrand();
						if(org.apache.commons.lang.StringUtils.isNotBlank(brand)&&!brandList.contains(brand)){
							brandList.add(brand);
						}
					}
					room.setRoomProductCount(roomProductCount);
					roomListExceptNullProduct.add(room);
				}
				//品牌做保护
				if(!brandList.isEmpty()){
					room.setBrandList(brandList);
				}
			}
		}
		//获取套装的推荐套装
		List<Suit> suitList=this.getSugestedSuitListBySuit(suit);
		HttpSuitResponse httpSuitResponse = new HttpSuitResponse();
		if(suitList!=null&&!suitList.isEmpty()){
			httpSuitResponse.setSuitList(suitList);
		}
		httpSuitResponse.setGraphicDesignDesc(suit.getGraphicDesignDesc());
		if(org.apache.commons.lang.StringUtils.isNotBlank(suit.getGraphicDesignUrl())){
			httpSuitResponse.setGraphicDesignUrl(suit.getGraphicDesignUrl()+ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
		}

		httpSuitResponse.setServiceUrl(AliImageUtil.imageCompress(StaticResourceConstants.SERVICE_PROMISE_URL,null, ImageType.LARGE.getWidth(), ImageConstant.SIZE_LARGE));
		httpSuitResponse.setServicePicRatio(SuitConstant.SERVICE_PIC_RATIO);
		if(roomListExceptNullProduct!=null&&!roomListExceptNullProduct.isEmpty()){
			httpSuitResponse.setRoomList(roomListExceptNullProduct);
		}else{
			httpSuitResponse.setRoomList(null);
		}
		return httpSuitResponse;
	}
}
