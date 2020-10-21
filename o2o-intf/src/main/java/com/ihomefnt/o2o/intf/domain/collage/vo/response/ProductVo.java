package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkImage;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/16 12:21
 */
@Data
@ApiModel("ProductVo")
public class ProductVo {

    @ApiModelProperty("活动ID")
    private Integer activityId;

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品头图")
    private String headImage;

    @ApiModelProperty("商品品牌图")
    private String brandIcon;

    @ApiModelProperty("商品详情图 仅查询商品详情时候回返回")
    List<ArtworkImage> imageList;



    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

    @ApiModelProperty("活动价")
    private BigDecimal price;

    @ApiModelProperty("ColorVo 商品颜色集合 和url对应")
    private List<ColorVo> colorList;

    @ApiModelProperty("商品标签-原始的")
    private String productTags;

    @ApiModelProperty("商品标签-给app的")
    private List<String> productTagsList;


    public void markColorList(){
        ColorVo allColor = new ColorVo(1,"4种颜色随机发货",StaticResourceConstants.TUANGOU_GLASS_HEAD_IMG);
        ColorVo green = new ColorVo(2,"抹茶绿", StaticResourceConstants.TUANGOU_GLASS_GREEN_IMG);
        ColorVo black = new ColorVo(3,"可可黑", StaticResourceConstants.TUANGOU_GLASS_BLACK_IMG);
        ColorVo red = new ColorVo(4,"樱桃红", StaticResourceConstants.TUANGOU_GLASS_RED_IMG);
        ColorVo violet = new ColorVo(5,"香芋紫", StaticResourceConstants.TUANGOU_GLASS_VIOLET_IMG);

        colorList = new ArrayList<>();
        colorList.add(allColor);
        colorList.add(green);
        colorList.add(black);
        colorList.add(red);
        colorList.add(violet);


        if(null != productTags && !"".equals(productTags)){
            String [] tagArray = productTags.split(",");
            this.productTagsList = Arrays.asList(tagArray);
        }
    }



}
