package com.ihomefnt.o2o.intf.domain.optional.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductSpuPropertyDto extends SkuPropertyDto {

    private String propertyType;

    private List<SkuPropertyDto> templatePropertyValueList;

}
