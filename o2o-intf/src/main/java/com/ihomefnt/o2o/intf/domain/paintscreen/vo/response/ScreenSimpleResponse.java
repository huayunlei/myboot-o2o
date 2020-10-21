package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 作品简单信息
 *
 * @author liyonggang
 * @create 2018-12-04 16:03
 */
@Data
@ApiModel("作品简单信息")
public class ScreenSimpleResponse implements Serializable {

    private static final long serialVersionUID = -2939938958406686192L;

    @ApiModelProperty("作品ID")
    private Integer artId;
    @ApiModelProperty("作品名称")
    private String  artName;
    @ApiModelProperty("作品图片")
    private String artImage;
    @ApiModelProperty("作品介绍")
    private String artIntro;
    @ApiModelProperty("分类ID")
    private Integer categoryId;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("艺术品类型：0:免费：1:收费")
    private Integer artType;
    @ApiModelProperty("作品属性 1:横屏, 0竖屏")
    private Integer artProperty;
    @ApiModelProperty("作品年代 0:古代 1:近现代 2:当代")
    private Integer artTime;
    @ApiModelProperty("艺术家名称")
    private String artist;
    @ApiModelProperty("机构名称")
    private String institutionsName;
    @ApiModelProperty("版权类型：0:归属作者 1:归属机构")
    private Integer copyrightType;
    @ApiModelProperty("作品标签")
    private String artTag;
    @ApiModelProperty("作品显示标签 年代/分类名称/标签组合")
    private String artTagView;
    @ApiModelProperty("有效期 0:永久有效 1:一个月 2:三个月 3:半年 4:一年")
    private Integer artDeadline;
    @ApiModelProperty("高")
    private Integer height;
    @ApiModelProperty("宽")
    private Integer width;
    @ApiModelProperty("收费价格（￥），当作品是收费时去该字段价格")
    private BigDecimal artPrice;
    @ApiModelProperty("是否过期  true 已过期，false 未过期")
    private boolean overdue;
    @ApiModelProperty("画作状态 0 下架 1 正常 2是删除")
    private Integer sellState = 1;
    @ApiModelProperty("购买需知")
    private String buyInfo;
    @ApiModelProperty("过期时间")
    private String artExpirationTime;
    @ApiModelProperty("浏览量")
    private Integer browseCount;
    @ApiModelProperty("删除状态 0正常 1删除")
    private Integer deleteFlag= 0;

}
