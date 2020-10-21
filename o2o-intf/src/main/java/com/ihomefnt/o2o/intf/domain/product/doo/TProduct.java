package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 
 * 功能描述：Product entity
 * 
 * @author piweiwen@126.com
 */
@Data
public class TProduct {

	/** 主鍵 */
	private Long productId;
	private String name;
	private String pictureUrlOriginal;
	private String thumUrmUrl;
	private double priceCurrent;
	private double priceMarket;
	private Long categoryId;
	private Long status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String productModel;
	private double standardLong;
	private double standardWidth;
	private double standardHigh;
	private String feature;
	private String brand;
	private String madeIn;
	private String deliveryCity;
	private String graphicDescription;
	private Long firstContents;
	private String firstContentsName;
	private Long secondContents;
	private String secondContentsName;
	private double latitude;
	private double longitude;

	public Timestamp getCreateTime() {
		return (null == createTime) ? null : ((Timestamp) createTime.clone());
	}

	public void setCreateTime(Timestamp createTime) {
		if (null == createTime) {
			this.createTime = null;
		} else {
			this.createTime = (Timestamp) createTime.clone();
		}
	}

	public Timestamp getUpdateTime() {
		return (null == updateTime) ? null : ((Timestamp) updateTime.clone());
	}

	public void setUpdateTime(Timestamp updateTime) {
		if (null == updateTime) {
			this.updateTime = null;
		} else {
			this.updateTime = (Timestamp) updateTime.clone();
		}
	}
}