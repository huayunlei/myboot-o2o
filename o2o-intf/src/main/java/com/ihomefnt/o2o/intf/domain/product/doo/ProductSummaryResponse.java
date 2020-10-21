package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
@NoArgsConstructor
public class ProductSummaryResponse {
	private Long productId;
	private String name;
	private List<String> pictureUrlOriginal;
	private double priceCurrent;
	private double priceMarket;
	private String firstContentsName;
	private String roomType;
	private String secondContentsName;
	private Integer productCount;
	private int shoppingCart;//0表示未加入购物车,1表示已加入购物车
	private Integer status;
	private Integer typeKey;
	private int priceHide;
	private String productAttrJson;
	@JsonIgnore
	private double roomPrice;//房间价格
	
	private Long roomId;
	
	@JsonIgnore
	private String imagesUrl;
	
	public ProductSummaryResponse(ProductSummary productSummary){
		this.productId=productSummary.getProductId();
		this.name=productSummary.getName();
		this.priceCurrent=productSummary.getPriceCurrent();
		this.priceMarket=productSummary.getPriceMarket();
		this.firstContentsName=productSummary.getFirstContentsName();
		this.secondContentsName=productSummary.getSecondContentsName();
        this.productCount = productSummary.getProductCount();
        this.roomPrice = productSummary.getRoomPrice();
        this.status = productSummary.getStatus();
        this.typeKey = productSummary.getTypeKey();
        this.roomId = productSummary.getRoomId();
        this.priceHide = productSummary.getPriceHide();
        this.productAttrJson = productSummary.getProductAttrJson();
        this.roomType = productSummary.getRoomType();
	}
	public List<String> getPictureUrlOriginal() {
		if(pictureUrlOriginal==null){
			return new ArrayList<String>();
		}
		return pictureUrlOriginal;
	}
	public String getRoomType() {
		if("1".equals(roomType)){
    		roomType = "客厅";
    	}
		if("2".equals(roomType)){
    		roomType = "主卧";
    	}
		if("3".equals(roomType)){
    		roomType = "次卧";
    	}
		if("4".equals(roomType)){
    		roomType = "儿童房";
    	}
        if("5".equals(roomType)){
        	roomType = "书房";
        }
        if("6".equals(roomType)){
        	roomType = "餐厅";
        }
        if("7".equals(roomType)){
        	roomType = "玄关";
        }
        if("8".equals(roomType)){
        	roomType = "厨房";
        }
        if("9".equals(roomType)){
    		roomType = "老人房";
    	}
        if("10".equals(roomType)){
    		roomType = "阳台";
    	}
        if("0".equals(roomType)){
        	roomType = firstContentsName;
        }
		return roomType;
	}
}
