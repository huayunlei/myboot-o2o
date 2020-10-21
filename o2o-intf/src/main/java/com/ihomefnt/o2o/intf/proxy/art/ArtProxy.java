package com.ihomefnt.o2o.intf.proxy.art;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtSpace;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtStudio;
import com.ihomefnt.o2o.intf.domain.art.dto.Artist;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkFilterInfo;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkImage;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkImageDto;
import com.ihomefnt.o2o.intf.domain.art.vo.request.ArtworkOrderRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListWithParamRequest;

/**
 * 艺术品dao层
* @Title: ArtDao.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午1:35:17 
* @version V1.0
 */
public interface ArtProxy {
	

	
	/**
	 * 获取艺术品列表页显示标签
	* @Title: getArtSpaceList 
	* @param @return
	* @return List<ArtSpace> 
	* @date 2016年7月15日下午4:01:54
	* @author Charl
	 */
	List<ArtSpace> getArtSpaceList();
	
	/**
	 * 获取艺术品列表
	* @Title: getArtworkList 
	* @param @param request
	* @param @return
	* @return List<Artwork> 
	* @date 2016年7月15日下午4:03:56
	* @author Charl
	 */
	List<Artwork> getArtworkList(HttpArtListRequest request);
	
	/**
	 * 插入艺术品浏览、收藏、分享
	* @Title: artworkLog 
	* @param @param logId 1.浏览 2.购买 3.收藏 4.分享
	* @param @param productId 产品id
	* @param @param userId 用户id
	* @return void 
	* @date 2016年7月18日下午2:21:56
	* @author Charl
	 */
	Integer artworkLog(int logId,long productId,long userId);
	
	/**
	 * 查询艺术品图片
	* @Title: getArtworkImages 
	* @param @param artworkId
	* @param @return
	* @return List<ArtworkImage> 
	* @date 2016年7月18日下午2:40:51
	* @author Charl
	 */
	List<ArtworkImage> getArtworkImages(Long artworkId);
	
	/**
	 * 获取艺术品查看，分享，收藏的次数
	* @Title: getViewArtworkCount 
	* @param @param artworkId
	* @param @return
	* @return int 
	* @date 2016年7月18日下午3:11:43
	* @author Charl
	 */
	Long getViewArtworkCount(Long productId,Long artistId,int typeId);
	
	/**
	 * 获取艺术品查看，分享，收藏的次数
	 * 
	 * @param artistId
	 * @param op
	 * @return
	 */
	Long getViewArtworkTotalCount(List<Long> artIdList, int op);
	
	/**
	 * 根据艺术家id获取该艺术家信息
	* @Title: getArtworkArtistInfoById 
	* @param @param artistId
	* @param @return
	* @return Artist 
	* @date 2016年7月18日下午4:05:03
	* @author Charl
	 */
	Artist getArtworkArtistInfoById(Long artistId);
	
	/**
	 * 根据id查询艺术品信息
	* @Title: getArtworkById 
	* @param @param artworkId
	* @param @return
	* @return Artwork 
	* @date 2016年7月18日下午4:24:18
	* @author Charl
	 */
	Artwork getArtworkById(Long artworkId);
	
	/**
	 * 艺术品列表分页总页数
	* @Title: getArtworkListCount 
	* @param @param pageSize
	* @param @return
	* @return Long 
	* @date 2016年7月19日下午4:20:51
	* @author Charl
	 */
	Long getArtworkListCount(HttpArtListRequest request);
	
	/**
	 * 获取设计师套装信息
	* @Title: getArtworkByArtistId 
	* @param @param artistId
	* @param @return
	* @return List<Artwork> 
	* @date 2016年7月20日下午2:17:59
	* @author Charl
	 */
	List<Artwork> getArtworkByArtistId(long artistId);
	
	/**
	 * 获取设计师经历
	* @Title: getArtistExperienceById 
	* @param @param artistId
	* @param @return
	* @return List<String> 
	* @date 2016年7月20日下午2:18:52
	* @author Charl
	 */
	List<String> getArtistExperienceById(long artistId);
	
	/**
	 * 获取设计师创作自述
	* @param @param artistId
	* @param @return
	* @return List<String> 
	* @author Charl
	 */
	List<String> getArtistSelfDescById(long artistId);
	
	/**
	 * 根据产品id获取艺术品订单所需信息
	* @Title: getArtworkOrderInfo 
	* @param @param artworkId
	* @param @return
	* @return HttpArtworkOrderResponse 
	* @date 2016年7月25日上午9:43:16
	* @author Charl
	 */
	Artwork getArtworkOrderInfo(Long artworkId);

	/**
	 * 新版艾商城商品信息
	 * @param request
	 * @return
	 */
	List<Artwork> getArtworkOmsOrderInfo(ArtworkOrderRequest request);

	/**
	 * 获取组织信息
	* @param @param artistId
	* @param @return
	* @return ArtStudio 
	* @author Charl
	 */
	ArtStudio getArtworkStudioById(Long artistId);
	
	/**
	 * 带参数时艺术品数量
	 * @param request
	 * @return
	 */
	Long getArtWorkCountByParam(HttpArtListWithParamRequest request);
	
	/**
	 * 根据搜素条件获取艺术品信息
	 * @param request
	 * @return
	 */
	List<Artwork> getArtWorkListByParam(HttpArtListWithParamRequest request);
	
	/**
	 * 获取当前艺术品分类信息
	 * @return
	 */
	List<ArtworkFilterInfo> getArtworkTypeInfo();
	
	/**
	 * 获取当前艺术品空间分类信息
	 * @return
	 */
	List<ArtworkFilterInfo> getArtworkRoomInfo();
	
	/**
	 * 根据筛选条件查询艺术品列表
	 * @param params
	 * @return
	 */
	List<Artwork> getArtworksByFilters(Map<String, Object> params);
	
	/**
	 * 获取区间的艺术品价格
	 * @param fieldInfo
	 * @return
	 */
	List<Double> getArtworkPriceList(Map<String, Object> params);
	
	/**
	 * 获取区间的艺术品价格
	 * @param fieldInfo
	 * @return
	 */
	Long getArtworksByFiltersCount(Map<String, Object> params);
	
	/**
	 * 获取艺术品推荐清单
	 * @param params
	 * @return
	 */
	List<Artwork> getArtworksRecommend(Map<String, Object> params);

	/**根据id和类型查询艺术品信息
	 * @param artworkId
	 * @param artworkType 1 艺术家 2 品牌 3小星星艺术家
	 * @return
	 */
	Artwork getArtworkByIdAndType(Long artworkId, Integer artworkType);

	/**
	 * 根据艺术品id获取艺术品场景体验图信息
	 * @param artworkId
	 * @return
	 */
    ArtworkImageDto getSceneImageById(Long artworkId);


//	void getSceneImageById(Long artworkId);
}
