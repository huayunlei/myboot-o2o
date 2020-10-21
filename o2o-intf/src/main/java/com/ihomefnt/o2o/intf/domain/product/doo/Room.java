package com.ihomefnt.o2o.intf.domain.product.doo;

import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class Room {

    private String name;

    private List<String> urls;

    private int count;
    
    private int sales;

    private Long roomId;
    
    private Long roomType;
    
    private List<ProductSummaryResponse>  productSummaryList;
    
    private double roomPrice;//房间价格
    
    private double originalPrice;
    
    private String suitId;
    
    private String suitName;
    
    private String styleName;
    
    private int style;//主要风格编号
    
    private String images;
    
    private List<String> imageList;
    
    private String roomFImages;
    
    private double size;
    
    private double width;
    
    private double length;
    
    private int sets;
    
    private int consultCount;//咨询数
    
    private int commentCount;//评论数
    
    private String roomAttr;
    
    private List<RoomImage> imageRoll;//空间图集(新增字段)
    

	public String getRoomAttr() {
    	return String.valueOf(sets) + "件套 | " + styleName;
	}
    

    public String getName() {
    	if(null == name){
    		return null;
    	}
    	if(name.equals(this.getRoomTypeName())){
    		return name;
    	}
        return name;
    }

	public String getRoomTypeName() {
		if(roomType==null){
			return null;
		}else if(roomType==0L){
			return "未选择";
		}else if(roomType==1L){
			return "客厅";
		}else if(roomType==2L){
			return "主卧";
		}else if(roomType==3L){
			return "次卧";
		}else if(roomType==4L){
			return "儿童房";
		}else if(roomType==5L){
			return "书房";
		}else if(roomType==6L){
			return "餐厅";
		}else if(roomType==7L){
			return "玄关";
		}else if(roomType==8L){
			return "厨房";
		}else if(roomType==9L){
			return "老人房";
		}else{
			return "未知";
		}
	}

	public void setImageListByStr(String imageStr) {
		List<String> imgList = ImageUtil.removeEmptyStr(imageStr);
		this.imageList = imgList;
	}
}
