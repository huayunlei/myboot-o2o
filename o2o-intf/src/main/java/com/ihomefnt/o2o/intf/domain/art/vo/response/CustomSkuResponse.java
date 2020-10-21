package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtSkuDto;
import com.ihomefnt.o2o.intf.domain.art.dto.PropertyDto;
import com.ihomefnt.o2o.intf.domain.art.dto.PropertyGroupDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author wanyunxin
 * @create 2019-08-07 12:56
 */
@Data
@ApiModel(value = "定制商品查询返回参数")
public class CustomSkuResponse {

    @ApiModelProperty(value = "定制商品最低价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "定制商品最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "定制商品默认图")
    private List<String> customDefaultPicList;

    @ApiModelProperty(value = "库存")
    private Integer inventoryCount;

    @ApiModelProperty("是否可用艾积分")
    private boolean exAble = true;

    @ApiModelProperty(value = "属性分组信息")
    private List<PropertyGroupDto> propertyGroupList;

    @ApiModelProperty("商品集合 key规则：'属性序号:属性值;'")
    private Map<String, ArtSkuDto> skuList;

    @ApiModelProperty("上下架标记 0上架 1下架")
    private Integer onlineStatus;

}
