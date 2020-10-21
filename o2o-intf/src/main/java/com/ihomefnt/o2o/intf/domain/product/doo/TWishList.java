package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Timestamp;


/**
 * Created by piweiwen on 15-1-21.
 */
@Data
public class TWishList {

	/** 主鍵 */
	private Long wishListId;
	private Long userId;
	private String wishProductName;
	private String wishProductBrand;
	private String wishProductUrl;
	private String wishProductRequest;
	private Timestamp submitTime;

	public void setSubmitTime(Timestamp submitTime) {
		if (null == submitTime) {
			this.submitTime = null;
		} else {
			this.submitTime = (Timestamp) submitTime.clone();
		}
	}
}
