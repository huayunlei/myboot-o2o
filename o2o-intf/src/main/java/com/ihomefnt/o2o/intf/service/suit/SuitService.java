/**
 * 
 */
package com.ihomefnt.o2o.intf.service.suit;

import com.ihomefnt.o2o.intf.domain.suit.dto.Product;
import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import com.ihomefnt.o2o.intf.domain.suit.dto.Suit;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.HttpSuitResponse;

import java.util.List;


/**
 * @author zhang<br/>
 * 套装Service接口<br/>
 */
public interface SuitService {
	
	/**
	 * 通过空间ID和平面设计图状态获取所有空间下所有的图片
	 * @param roomId 空间ID
	 * @param designStatus 平面设计图：0不是平面设计图 ,1是平面设计图,null表示查询所有
	 * @return
	 */
	List<RoomImage> getRoomImageListByRoomId(Long roomId,Long designStatus);
	
	/**
	 * 通过空间ID和平面设计图状态,以及是否图文详情来获取所有空间下所有的图片
	 * @param roomId 空间ID
	 * @param designStatus 平面设计图：0不是平面设计图 ,1是平面设计图,null表示查询所有
	 * @param detailPage 是否用于图文详情页 1：用于图文详情页 
	 * @return
	 */
	List<RoomImage> getRoomImageListByRoomIdAndDetailPage(Long roomId,Long designStatus,Long detailPage);
	
	/**
	 * 通过suitId和平面设计图状态,以及是否图文详情来获取所有空间下所有的图片
	 * @param suitId 套装ID
	 * @param designStatus 平面设计图：0不是平面设计图 ,1是平面设计图,null表示查询所有
	 * @param detailPage 是否用于图文详情页 1：用于图文详情页 
	 * @return
	 */
	List<RoomImage> getRoomImageListBySuitIdAndDetailPage(Long suitId,Long designStatus,Long detailPage);
	
	/**
	 * 根据套装主键来获取套装
	 * @param suitId 套装主键
	 * @return
	 */
	Suit getSuitByPK(Long suitId);
		
	/**
	 * 根据套装来获取所有空间
	 * @param suitId 套装主键
	 * @return
	 */
	List<Product> getRoomProductListByRoomId(Long suitId);
	
	/**
	 * 根据套装来获取所有空间
	 * @param suitId 套装主键
	 * @return
	 */
	List<Room> getRoomListBySuitId(Long suitId);
	
	/**
	 * 通过套装来获取他的推荐套装
	 * @param suit
	 * @return
	 */
	List<Suit> getSugestedSuitListBySuit(Suit suit);

	/**
	 * 根据空间主键来获取空间
	 * @param roomId 空间主键
	 * @return
	 */
	Room getRoomInfoByRoomId(Long roomId);

	/**
	 * 根据空间主键来获取套装
	 * @param roomId 空间主键
	 * @return
	 */
	Suit getSuitInfoByRoomId(Long roomId);

	/**
	 * 根据空间主键来获取推荐空间列表
	 * @param roomId 空间主键
	 * @return
	 */
	List<Room> getRecommendRoomList(Long roomId, String cityCode);

	/**
	 * 根据空间主键来获取单品列表
	 * @param roomId 空间主键
	 * @return
	 */
	Room getProductListByRoomId(Long roomId);
	
	/**
	 * 根据空间主键和套装主键来获取单品列表
	 * @param roomId 空间主键
	 * @return
	 */
	Room getProductListByRoomIdAndSuitId(Long roomId,Long suitId);
	
	/**
	 * 根据样式ID，查询所有的样式
	 * @param pks
	 * @return
	 */
	List<String> getStylesByPks(List<String> pks);

	HttpSuitResponse getSuitInfoBySuitId(Long suitId);
}
