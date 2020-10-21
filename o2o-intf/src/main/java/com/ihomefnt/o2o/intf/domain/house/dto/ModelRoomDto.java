/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.house.dto;

import lombok.Data;

import java.util.List;

/**
 * @author weitichao
 *
 */
@Data
public class ModelRoomDto {
	
	private String expId;  //体验店id
	
	private String expName;  //体验店名字
	
	private String expImage;  //体验店头图
	
	private String expDec;  //体验店描述
	
	private int expRoom3dNum;  //体验店3v家图
	
	private int expRoomOfflineNum;  //体验店线下体验店
	
	private String expHouses;  //体验店包含的户型
	
	private List<ModelHouseDto> houstList;  //体验店下有3v家连接的套装 
}
