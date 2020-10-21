/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.house.dto;

import lombok.Data;

import java.util.List;

/**
 * @author weitichao
 * h5 3v家户型
 */
@Data
public class ModelHouseDto {
	
	private int houseId;  //户型id
	
	private String houseName;  //户型的名字
	
	private String houseSize;  //户型大小
	
	private String houseImage;  //户型头图
	
	private String houseRoom;  //户型房间
	
	private String houseLiving;  //户型厅数
	
	private String houseToilet;  //户型卫生间
	
	private String houseBalcony;  //户型阳台
	
	private String houseKitchen;  //户型厨房
	
	private String houseInfo;  //户型信息
	
	private List<ModelSuitDto> suitDtoList;  //套装风格列表

}
