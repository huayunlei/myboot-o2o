/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Comparator;
import java.util.List;

/**
 * @author zhang<br/>
 *         空间对象<br/>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Room implements Comparator<Room>{
	
	private Long suitId;//套装id

	private Long roomId;// 空间ID
	
	private String roomName;//空间名称
	
	private String suitName;//套装名称
	
	private RoomImage graphicDesign;//平面设计图

	private String firstImage;// 空间的第一张图URL

	private List<String> styleList;// 空间风格集合
	@JsonIgnore
	private String styles; //这个是给数据库用的

	private List<String> storageList;// 储物空间集合
	@JsonIgnore
	private String storages;//这个是给数据库用的
	
	private List<String> materialList;// 材质集合
	@JsonIgnore
	private String materials;//这个是给数据库用的

	private List<String> temperamentList;// 气质集合
	@JsonIgnore
	private String temperaments;//这个是给数据库用的

	private List<String> colorList;// 颜色集合
	@JsonIgnore
	private String colors;//这个是给数据库用的
	
	private List<String> brandList;//品牌集合

	private List<RoomImage> imagelist;//图片集合
	
	private List<RoomImage> imageAlllist;//所有图片集合

	private List<Product> productList;// 商品集合

	private String suitStyle;// 套装风格

	private String roomType;// 空间类型:0:未选择 1：客厅 2：主卧 3：次卧 4：儿童房 5：书房  6：餐厅 7：玄关 8：厨房 9：老人房	
	@JsonIgnore
	private Long roomTypeKey;//这个是给数据库用的

	private Double roomArea;// 空间面积
	
	private double roomPrice;//空间价格
	
	private Long sales;//空间销量
	
	private String roomStr;// 风格面积数量的拼接字段
	
	private Long roomProductCount;// 空间商品数量
	
	private Suit suitInfo;//所属套装

	public String getRoomStr() {
		StringBuffer buff = new StringBuffer();
		if(suitStyle!=null){
			buff.append(suitStyle);
			buff.append(" | ");
		}
		if(roomArea!=null){
			String ss = roomArea.toString().replaceAll("0+?$", "");//去掉后面无用的零
			ss = ss.toString().replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
			if(StringUtils.isNotBlank(ss)&&!ss.equals("0")){
				buff.append(ss);
				buff.append("平方米");
				buff.append(" | ");
			}
		}
		if(roomProductCount!=null){
			buff.append(roomProductCount);
			buff.append("件套");
		}
		return buff.toString();
	}


	public String getRoomName() {
		if(null == roomName){
    		return null;
    	}
    	if(roomName.equals(this.getRoomType())){
    		return roomName;
    	}
		return roomName;
	}

	//0:未选择 1：客厅 2：主卧 3：次卧 4：儿童房 5：书房  6：餐厅 7：玄关 8：厨房 9：老人房
	public String getRoomType() {
		if(roomTypeKey==null){
			return null;
		}else if(roomTypeKey==0L){
			return "未选择";
		}else if(roomTypeKey==1L){
			return "客厅";
		}else if(roomTypeKey==2L){
			return "主卧";
		}else if(roomTypeKey==3L){
			return "次卧";
		}else if(roomTypeKey==4L){
			return "儿童房";
		}else if(roomTypeKey==5L){
			return "书房";
		}else if(roomTypeKey==6L){
			return "餐厅";
		}else if(roomTypeKey==7L){
			return "玄关";
		}else if(roomTypeKey==8L){
			return "厨房";
		}else if(roomTypeKey==9L){
			return "老人房";
		}else if(roomTypeKey==10L){
			return "阳台";
		}else{
			return "未选择";
		}
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Long getRoomTypeKey() {
		if(roomTypeKey==null||roomTypeKey==0L){
			return 9999L;
		}
		return roomTypeKey;
	}


	@Override
	public int compare(Room o1, Room o2) {
		return o1.getRoomTypeKey().compareTo(o2.getRoomTypeKey());
	}
	
	
}
