package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 小星星sku
 *
 * @author ZHAO
 */
@Data
public class ArtStarSku {
    private Long id;//skuid

    private Long artId;//艺术品ID

    private String skuName;//名称

    private BigDecimal price;//价格

    private String attrStr;//销售属性  以，分隔
}
