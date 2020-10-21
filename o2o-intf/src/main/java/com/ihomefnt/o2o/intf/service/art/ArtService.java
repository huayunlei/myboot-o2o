package com.ihomefnt.o2o.intf.service.art;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.Artist;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.art.dto.HttpArtworkListWithFilterRequest;
import com.ihomefnt.o2o.intf.domain.art.dto.HttpShareArtWorkRequest;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.art.vo.request.ArtSubjectPageRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListWithParamRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtistHomeRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtworkDetailRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpCreateArtworkOrderRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.SubjectDetailRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.response.ArtSubjectDetailResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.ArtSubjectPageResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.CategoryArtListResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.HttpArtListResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.HttpArtworkDetailResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.HttpArtworkOrderResponse;
import com.ihomefnt.o2o.intf.domain.art.vo.response.HttpArtworkShareResponse;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarOrderCreateRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubmitOrderResponse;

import java.util.List;
import java.util.Map;

/**
 * 艺术品Service
* @Title: ArtService.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午1:27:38 
* @version V1.0
 */
public interface ArtService {
	
	/**
	 * 艺术品列表
	* @Title: getArtworkList 
	* @param @param request
	* @param @return
	* @return HttpArtListResponse 
	* @date 2016年7月15日下午1:28:58
	* @author Charl
	 */
	HttpArtListResponse getArtworkList(HttpArtListRequest request);
	
	/**
	 * 艺术品详情
	* @Title: getArtworkDetail 
	* @param @param request
	* @param @return
	* @return HttpArtworkDetailResponse 
	* @date 2016年7月18日下午8:05:05
	* @author Charl
	 */
	HttpArtworkDetailResponse getArtworkDetail(HttpArtworkDetailRequest request);
	
	/**
	 * 获取艺术家信息
	* @Title: getArtistInfo 
	* @param @param request
	* @param @return
	* @return Artist 
	* @date 2016年7月20日下午3:07:09
	* @author Charl
	 */
	Artist getArtistInfo(HttpArtistHomeRequest request);
	
	/**
	 * 获取艺术品分享信息,并往数据库插入喜欢、分享的数据
	* @Title: getArtworkShareInfoById 
	* @param @param request
	* @param @return
	* @return Artist 
	* @date 2016年7月20日下午5:11:43
	* @author Charl
	 */
	HttpArtworkShareResponse getArtworkShareInfoById(HttpShareArtWorkRequest request);
	
	/**
	 * 根据艺术品id校验库存
	* @Title: getArtworkStockById 
	* @param @param request
	* @param @return
	* @return long 
	* @date 2016年7月21日下午4:40:21
	* @author Charl
	 */
	long getArtworkStockById(HttpArtworkDetailRequest request);
	
	/**
	 * 根据艺术品id获取艺术品订单所需信息
	* @Title: getArtworkOrderInfoById 
	* @param @param request
	* @param @return
	* @return HttpArtworkOrderResponse 
	* @date 2016年7月25日上午9:40:02
	* @author Charl
	 */
	HttpArtworkOrderResponse getArtworkOrderInfoById(HttpArtworkDetailRequest request);
	
	/**
	 * 根据搜素条件查询艺术品列表
	 * @param request
	 * @return
	 */
	HttpArtListResponse getArtworkListByParam(HttpArtListWithParamRequest request);
	
	/**
	 * 查询筛选选项
	 * @return
	 */
	Map<String, Object> getArtworkFilterInfo();
	
//	/**
//	 * 根据查询条件获取艺术品列表
//	 * @param request
//	 * @return
//	 */
//	HttpArtListResponse getArtworksByFilters(HttpArtworkListWithFilterRequest request);
	
	/**
	 * 获取count件推荐艺术品
	 * @param count
	 * @return
	 */
	List<Artwork> getArtworksRecommend(int count,int recommend);
	
	/**
	 * 根据productId查询艺术品信息
	 */
	Artwork getArtworkByProductId(Integer productId);

	/**
	 * 创建艺术品订单
	 * @param request
	 * @return
	 */
	ResponseVo<?> createArtOrder(HttpCreateArtworkOrderRequest request);
	
	/**
	 * 取消艺术品订单
	 * @param map
	 * @return
	 */
	boolean cancelArtOrder(Map<String,Object> map);
	
	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	OrderDto queryArtOrderDetailById(Integer id);
	
	/**
	 * 艾商城首页分类艺术品集合
	 * @return
	 */
	List<CategoryArtListResponse> getCategoryArtListForHome();

	/**
	 * 艾商城首页艺术品专题（分页）
	 * @param request
	 * @return
	 */
	ArtSubjectPageResponse getArtSubjectList(ArtSubjectPageRequest request);

	/**
	 * 根据查询条件获取艺术品列表（添加艺术品品类条件）
	 * @param request
	 * @return
	 */
	HttpArtListResponse getArtListByCondition(HttpArtworkListWithFilterRequest request);
	
	/**
	 * 根据专题ID查询专题详情
	 * @param request
	 * @return
	 */
	ArtSubjectDetailResponse querySubjectDetailById(SubjectDetailRequest request);
	
	/**根据id和类型查询艺术品信息
	 * @param artworkId
	 * @param artworkType 1 艺术家 2 品牌 3小星星艺术家
	 * 若artworkType为3时，artworkId代表作品ID
	 * @return
	 */
	Artwork getArtworkByIdAndType(Integer artworkId, Integer artworkType);

	/**创建小星星订单
	 * @param request
	 * @return
	 */
	HttpSubmitOrderResponse createStarOrder(StarOrderCreateRequest request);

	/**
	 * 删除订单
	 * @param request
	 */
    void deleteOrder(QueryCollageOrderDetailRequest request);

	HttpArtworkOrderResponse getArtworkOrderInfo290(HttpArtworkDetailRequest request);

	HttpSubmitOrderResponse createArtworkOrder290(HttpCreateArtworkOrderRequest request);
}
