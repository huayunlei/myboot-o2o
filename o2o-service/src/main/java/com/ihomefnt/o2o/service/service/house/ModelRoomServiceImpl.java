/**
 * 
 */
package com.ihomefnt.o2o.service.service.house;

import java.util.List;

import com.ihomefnt.o2o.intf.dao.house.ModelRoomDao;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.house.ModelRoomService;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelHouseDto;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelRoomDto;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelSuitDto;
import com.ihomefnt.o2o.intf.domain.house.vo.request.HttpModeRoomRequest;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageQuality;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;

/**
 * @author weitichao
 * 
 *
 */
@Service
public class ModelRoomServiceImpl implements ModelRoomService {
	
	@Autowired
	ModelRoomDao modelRoomDao;

	@Override
	public ModelRoomDto getModelRoomList(HttpModeRoomRequest request) {
		ModelRoomDto resultList = new ModelRoomDto();
		ModelRoomDto roomList = modelRoomDao.getModelRoomList(request);
		int modelRoomOfflineNum = modelRoomDao.getModelRoomOfflineNum(request);
		if(null == roomList){
			ModelRoomDto modelRoomInfo = modelRoomDao.getModelRoomInfo(request);
			if(null != modelRoomInfo){
				resultList.setExpName(modelRoomInfo.getExpName());
				String expImageStr = setImageUrl(modelRoomInfo.getExpImage());
				if(!StringUtil.isNullOrEmpty(expImageStr)){
//					resultList.setExpImage(expImageStr+"?imageView2/1/w/640/h/320/q/60");
					resultList.setExpImage(QiniuImageUtils.compressImage(expImageStr, QiniuImageQuality.MEDIUM, 640, 320));
				}else{
					resultList.setExpImage(expImageStr);
				}
				resultList.setExpDec(modelRoomInfo.getExpDec());
				resultList.setExpRoom3dNum(0);
				resultList.setExpRoomOfflineNum(modelRoomOfflineNum);
			}else{
				resultList = null;
			}
		}else {
			roomList.setExpRoomOfflineNum(modelRoomOfflineNum);
			List<ModelHouseDto> houstList = roomList.getHoustList();
			String expImageStr1 = setImageUrl(roomList.getExpImage());
			if(!StringUtil.isNullOrEmpty(expImageStr1)){
//				roomList.setExpImage(expImageStr1+"?imageView2/1/w/640/h/320/q/60");
				roomList.setExpImage(QiniuImageUtils.compressImage(expImageStr1, QiniuImageQuality.MEDIUM, 640, 320));
			}else{
				roomList.setExpImage(expImageStr1);
			}
			int count = 0;
			StringBuilder sb = new StringBuilder();
			if(null != houstList && houstList.size()>0){
				for (ModelHouseDto modelHouseDto : houstList) {
					List<ModelSuitDto> suitDtoList = modelHouseDto.getSuitDtoList();
					for (ModelSuitDto modelSuitDto : suitDtoList) {
						modelSuitDto.setSuitStyleName(modelSuitDto.getSuitStyleName()+"风格");
						String suitImageString = setImageUrl(modelSuitDto.getSuitImage());
						if(!StringUtil.isNullOrEmpty(suitImageString)){
//							modelSuitDto.setSuitImage(suitImageString+"?imageView2/1/w/640/h/320/q/60");
							modelSuitDto.setSuitImage(QiniuImageUtils.compressImage(suitImageString, QiniuImageQuality.MEDIUM, 640, 320));
						}else{
							modelSuitDto.setSuitImage(suitImageString);
						}
					}
					StringBuilder houseInfo = new StringBuilder();
					if(modelHouseDto == houstList.get(houstList.size()-1)){
						String houseStr = modelHouseDto.getHouseName();
						sb.append(houseStr);
					}else{
						String houseStr = modelHouseDto.getHouseName();
						sb.append(houseStr);
						sb.append("、");
					}
					count = count + suitDtoList.size();
					String houseImageString = modelHouseDto.getHouseImage();
					modelHouseDto.setHouseImage(houseImageString);
					
					if(!modelHouseDto.getHouseRoom().equals("0")){
						houseInfo.append(modelHouseDto.getHouseRoom()+"室");
					}
					if(!modelHouseDto.getHouseLiving().equals("0")){
						houseInfo.append(modelHouseDto.getHouseLiving()+"厅");
					}
					if(!modelHouseDto.getHouseKitchen().equals("0")){
						houseInfo.append(modelHouseDto.getHouseKitchen()+"厨");
					}
					if(!modelHouseDto.getHouseToilet().equals("0")){
						houseInfo.append(modelHouseDto.getHouseToilet()+"卫");
					}
					if(!modelHouseDto.getHouseBalcony().equals("0")){
						houseInfo.append(modelHouseDto.getHouseBalcony()+"阳台");
					}
					modelHouseDto.setHouseSize(modelHouseDto.getHouseSize().split("\\.")[0].toString()+"㎡");
					modelHouseDto.setHouseInfo(houseInfo.toString());
				}
			}
			roomList.setExpHouses(sb.toString());
			roomList.setExpRoom3dNum(count);
			resultList = roomList;
		}
		return resultList;
	}
	
	private String setImageUrl(String imageUrl){
		if(null != imageUrl && !"".equals(imageUrl.trim())){
			JSONArray jsonArray = JSONArray.fromObject(imageUrl);
	        List<String> imageUrlList = (List<String>) JSONArray.toCollection(jsonArray);
	        if(null != imageUrlList && imageUrlList.size() > 0){
	        	String result = imageUrlList.get(0);
	        	if(null != result && !"".equals(result.trim())){
	        		return result;
	        	}
	        }
		}
		return null;
	}

}
