package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TSuit {
	 private Long suitId;//套装id
	 private String suitName;//套装名称
	 private String styleName;//套装风格
	 private BigDecimal price;//套装价格
	 private Byte offLineExperience;//是否可以线下体验,0:否,1:是
	 private String suitImages;//套装头图
}
