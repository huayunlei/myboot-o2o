/**
 * 
 */
package com.ihomefnt.o2o.service.dao.suit;

import com.ihomefnt.o2o.intf.dao.suit.SuitDao;
import com.ihomefnt.o2o.intf.domain.suit.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 *
 */
@Repository
public class SuitDaoImpl implements SuitDao {
	
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.suit.SuitDao.";

	@Override
	public List<RoomImage> getRoomImageListByRoomId(Long roomId,Long designStatus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("status", SuitConstant.IMAGE_STATUS_YES);
		paramMap.put("designStatus", designStatus);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRoomImageListByRoomId",paramMap);
	}
	
	@Override
	public List<RoomImage> getRoomImageListByRoomIdAndDetailPage(Long roomId,Long designStatus,Long detailPage) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("status", SuitConstant.IMAGE_STATUS_YES);
		paramMap.put("designStatus", designStatus);
		paramMap.put("detailPage", detailPage);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRoomImageListByRoomId",paramMap);
	}
	
	@Override
	public List<RoomImage> getRoomImageListBySuitIdAndDetailPage(Long suitId,Long designStatus,Long detailPage){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("suitId", suitId);
		paramMap.put("status", SuitConstant.IMAGE_STATUS_YES);
		paramMap.put("designStatus", designStatus);
		paramMap.put("detailPage", detailPage);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRoomImageListByRoomId",paramMap);
	}
	
	@Override
	public Suit getSuitByPK(Long suitId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("suitId", suitId);
		paramMap.put("status", SuitConstant.SUIT_STATUS_YES);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getSuitByPK",paramMap);
	}
	
	@Override
	public List<Room> getRoomListBySuitId(Long suitId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("suitId", suitId);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRoomListBySuitId",paramMap);
	}
	
	@Override
	public List<Product> getRoomProductListByRoomId(Long roomId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("status", SuitConstant.PRODUCT_STATUS_YES);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getProductListByRoomId",paramMap);
	}
	
	@Override
	public List<Suit> getSugestedSuitListBySuit(Suit mySuit){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("suitId", mySuit.getSuitId());
		paramMap.put("houseId", mySuit.getHouseId());
		paramMap.put("buildingId", mySuit.getBuildingId());
		paramMap.put("districtId", mySuit.getDistrictId());
		paramMap.put("count", SuitConstant.LIMIT_COUNT);
		paramMap.put("status", SuitConstant.SUIT_STATUS_YES);
		//查询同户型的3套
		List<Suit> houseList= sqlSessionTemplate.selectList(NAME_SPACE + "getHouseSuitList",paramMap);
		if(houseList!=null&&houseList.size()== SuitConstant.LIMIT_COUNT){
			return covertSuitList(houseList);
		}		
		List<Long> suitIdList=new ArrayList<Long>();
		List<Suit> suitList=new ArrayList<Suit>();
		if(houseList!=null&&!houseList.isEmpty()){
			for(Suit suit:houseList){
				Long houseSuitId =suit.getSuitId();
				if(!suitIdList.contains(houseSuitId)){
					suitIdList.add(houseSuitId);
					suitList.add(suit);
				}			
			}
		}
		//如果同户型查询不到，查询同小区的3套
		List<Suit> buildingList= sqlSessionTemplate.selectList(NAME_SPACE + "getBuildingSuitList",paramMap);
		if(buildingList!=null&&!buildingList.isEmpty()){
			for(Suit suit:buildingList){
				Long houseSuitId =suit.getSuitId();
				if(!suitIdList.contains(houseSuitId)){
					suitIdList.add(houseSuitId);
					suitList.add(suit);
				}	
				if(suitList.size()== SuitConstant.LIMIT_COUNT){
					return covertSuitList(suitList);
				}
			}
		}		
		//如果同小区也查询不到，查询同区域的3套
		List<Suit> areaList= sqlSessionTemplate.selectList(NAME_SPACE + "getAreaSuitListBySuitId",paramMap);
		if(areaList!=null&&!areaList.isEmpty()){
			for(Suit suit:areaList){
				Long houseSuitId =suit.getSuitId();
				if(!suitIdList.contains(houseSuitId)){
					suitIdList.add(houseSuitId);
					suitList.add(suit);
				}	
				if(suitList.size()== SuitConstant.LIMIT_COUNT){
					return covertSuitList(suitList);
				}
			}
		}		
		return covertSuitList(suitList);
	}
	
	/**
	 * 内部转换
	 * @param list
	 * @return
	 */
	private List<Suit> covertSuitList(List<Suit> list){
		if(list!=null&&!list.isEmpty()){
			for(Suit suit:list) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("suitId", suit.getSuitId());
				Suit tmp=sqlSessionTemplate.selectOne(NAME_SPACE + "getAreasizeBySuitId",paramMap);
				if(tmp!=null){
					suit.setAreaSize(tmp.getAreaSize());
				}				
				tmp=sqlSessionTemplate.selectOne(NAME_SPACE + "getProductCountBySuitId",paramMap);
				if(tmp!=null){
					suit.setProductCount(tmp.getProductCount());
				}				
			}
		}
		return list;
	}

	@Override
	public List<Room> queryProductList(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductList",paramMap);
	}

	@Override
	public List<Room> querySameHouseRoom(Long roomId) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySameHouseRoom", roomId);
	}

	@Override
	public List<Room> querySameStyleRoom(Map<String,Object> paramMap) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "querySameStyleRoom", paramMap);
	}

	@Override
	public Suit getSuitInfoByRoomId(Long roomId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getSuitInfoByRoomId", roomId);
	}

	@Override
	public RoomLabel getRoomLabel(Long roomId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getRoomLabel", roomId);
	}

	@Override
	public List<String> getRoomBrandList(Long roomId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRoomBrandList", roomId);
	}

	public List<String> getStylesByPks(List<String> pks){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pks", pks);
		return sqlSessionTemplate.selectList(NAME_SPACE + "getStylesByPks", paramMap);
	}

}
