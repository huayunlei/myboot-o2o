package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.ArrayList;
import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponse110;
import com.ihomefnt.o2o.intf.domain.product.doo.SuitProduct110;
import lombok.Data;

@Data
public class HttpMultiSuitReponse {
	private List<String> pictureUrlHouse;// 户型照片
	private String experienceAddress;//体验地址
	private Double latitude;//维度
	private Double longitude;//经度
	private List<CompositeProductReponse110>compositeProductList ;//此户型下对应的套装
	public HttpMultiSuitReponse(List<SuitProduct110> compositeProducts) {
		this.pictureUrlHouse=compositeProducts.get(0).getHouseImgList();
		this.experienceAddress=compositeProducts.get(0).getExperienceAddress();
		this.latitude=compositeProducts.get(0).getLatitude();
		this.longitude=compositeProducts.get(0).getLongitude();
		compositeProductList=new ArrayList<CompositeProductReponse110>();
		for (int i = 0; i < compositeProducts.size(); i++) {
			CompositeProductReponse110 compositeProductReponse110=new CompositeProductReponse110(compositeProducts.get(i));
			compositeProductList.add(compositeProductReponse110);
		}
	}
}
