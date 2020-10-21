package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

@Data
public class ClassifyNode {
	private Long key;
	private Long parentKey;
	private String name;
    private Integer status;
    private Integer sort;
}
