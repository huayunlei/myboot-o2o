package com.ihomefnt.o2o.intf.domain.art.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 艺术品信息
 * @author ZHAO
 */
public class ArtInfoVo implements Serializable{
	private Integer idtArtProduct;//artWorkId
	
	private String name;//艺术品名称name
	
	private String imageUrl;//headImg
	
	private String category;//category
	
	private String productYear;//creationTime
	
	private BigDecimal productPrice;//价格   price
	
	private String productSize;//尺寸   size
	
	private Integer uId;//品牌、艺术家ID    artistId
	
	private String nickName;//品牌、艺术家名称   artistName
	
	private Integer artType;//2品牌   1艺术家   artistType
	
	private String categoryId;//标签ID集合

	public Integer getIdtArtProduct() {
		return idtArtProduct;
	}

	public void setIdtArtProduct(Integer idtArtProduct) {
		this.idtArtProduct = idtArtProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductYear() {
		return productYear;
	}

	public void setProductYear(String productYear) {
		this.productYear = productYear;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

}
