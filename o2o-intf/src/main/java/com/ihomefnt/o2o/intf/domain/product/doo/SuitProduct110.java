package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;
@Data
public class SuitProduct110 extends CompositeProduct {

	private String houseName;
	private String houseImgs;//bean array
	private List<String> houseImgList;
	private List<String> pictureUrlOriginalList;
}
