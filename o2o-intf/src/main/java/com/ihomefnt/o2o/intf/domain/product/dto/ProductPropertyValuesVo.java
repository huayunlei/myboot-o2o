package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;
@Data
public class ProductPropertyValuesVo {
	
	private Integer id;// (integer, optional),
	private Integer productId;// (integer, optional),
	private Integer propertyId;// (integer, optional),
	private Integer parentPropertyId; // (integer, optional),
	private String propertyKey; // (string, optional),
	private String propertyName; // (string, optional),
	private String propertyValue; // (string, optional),
	private String propertyExt; // (string, optional),
	private String propertyDesc; // (string, optional),
	private String pagePropertyValue; // (string, optional),
	private String propertyType; // (string, optional),
	private Integer isDefaultValues; // (integer, optional),
	private Integer isVariantTheme; // (integer, optional),
	private Integer isShow; // (integer, optional),
	private Integer isCheck; // (integer, optional),
	private String regex; // (string, optional),
	private Integer isRequired; // (integer, optional),
	private Integer isPreset; // (integer, optional),
	private String tips; // (string, optional),
	private String createTime; // (string, optional),
	private String updateTime; // (string, optional),
}
