package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 销售属性
 *
 * @author ZHAO
 */
@Data
public class ArtSaleAttr {
    private Integer saleType;//销售属性CODE

    private String saleTypeStr;//销售属性描述

    private List<String> attrList;
}
