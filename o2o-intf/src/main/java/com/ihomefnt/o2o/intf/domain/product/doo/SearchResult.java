package com.ihomefnt.o2o.intf.domain.product.doo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class SearchResult {
	
	/*
	 * 搜索结果ID
	 */
	private Long sid;
	/*
	 * 搜索结果图片链接
	 */
	private List<String> imageList;
	
	/*
	 * 搜索结果图片链接
	 */
	private String image;
	
	/*
	 * 搜索结果名称
	 */
	private String name;
	
	/**
	 * 搜索商品数量
	 */
	private Long productCount;//
	
	private Double size;//搜索面积
	
	/*
	 * 搜索结果价格
	 */
	private Double price;
	
	private Double priceMarket ;//市场价格
	
	private String suitName;//套装名称
	
	@JsonIgnore
	private Long roomTypeKey;//这个是给数据库用的

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public Double getPriceMarket() {
		return priceMarket;
	}

	public void setPriceMarket(Double priceMarket) {
		this.priceMarket = priceMarket;
	}

	public String getSuitName() {
		return suitName;
	}

	public void setSuitName(String suitName) {
		this.suitName = suitName;
	}

	public Long getRoomTypeKey() {
		return roomTypeKey;
	}

	public void setRoomTypeKey(Long roomTypeKey) {
		this.roomTypeKey = roomTypeKey;
	}
	
	
	
}
