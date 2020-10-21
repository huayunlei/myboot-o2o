package com.ihomefnt.o2o.intf.manager.util.common.image;

/**
 * 七牛
 * @author Charl
 */
public enum QiniuImageQuality {
	
	ALL(100), //超高质量
	HIGH(75), //高质量
	MEDIUM(60), //中质量
	LOW(20), //低质量
	LOW_1(40);  //超低质量（针对特大图）
	
	private int quality;

	private QiniuImageQuality(int quality) {
		this.quality = quality;
	}
	
	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
}
