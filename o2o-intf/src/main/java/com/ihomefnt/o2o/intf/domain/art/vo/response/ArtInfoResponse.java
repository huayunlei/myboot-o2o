package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author wanyunxin
 * @create 2019-08-07 12:43
 */
@ApiModel("艺术品列表")
@Data
public class ArtInfoResponse {

    @ApiModelProperty("艺术品id")
    private String worksId;

    @ApiModelProperty("艺术品名称")
    private String worksName;

    @ApiModelProperty("艺术品图片")
    private String worksPicUrl;

    @ApiModelProperty("艺术品图片多张")
    private List<String> worksPicList;

    @ApiModelProperty("作品介绍")
    private String introductionHtml;

    @ApiModelProperty("风格编号")
    private String styleId;

    @ApiModelProperty(value = "风格名称")
    private String styleName;

    @ApiModelProperty("是否可用艾积分")
    private boolean exAble = true;

    @ApiModelProperty("库存")
    private Integer inventoryCount;

    @ApiModelProperty("作者信息")
    private ArtistInfoResponse artistInfo;

    @ApiModelProperty("属性信息")
    private List<SpecificationExtendDto> specificationList;

    @ApiModelProperty(value = "关联sku最低价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "关联sku最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "作品最低价")
    private BigDecimal minWorksPrice;

    @ApiModelProperty("是否支持特色定制 0 非 1是")
    private Integer specialFlag = 0;

    @ApiModelProperty(value = "属性分组信息")
    private List<PropertyGroupDto> propertyGroupList;

    @ApiModelProperty("画作集合 key规则：'属性序号:属性值;'")
    private Map<String, ArtSpecificationDto> skuList;

    private Integer visitNum = 0;//浏览量

    @ApiModelProperty("上下架标记 0上架 1下架")
    private Integer onlineStatus;

    @ApiModelProperty(value = "关联商品数量")
    private Integer skuCount = 0;

    @ApiModelProperty(value = "作品尺寸")
    private String worksSize;


}
