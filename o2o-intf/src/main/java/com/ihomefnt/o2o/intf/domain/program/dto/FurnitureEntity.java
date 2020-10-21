package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 家具清单明细
 *
 * @author ZHAO
 */
@Data
public class FurnitureEntity implements Serializable {
    //新增8个字段
    private Integer categoryLevelTwoId;

    private String categoryLevelTwoName;

    private Integer categoryLevelThreeId;

    private String categoryLevelThreeName;

    private Integer categoryLevelFourId;

    private String categoryLevelFourName;

    private Integer styleId;

    private String styleName;

    private Integer rootCategoryId;// 根类目ID

    private String rootCategoryName;// 根类目名称

    private Integer lastCategoryId;// 末级类目ID

    private String lastCategoryName;// 末级类目名称

	//新增8个字段
	private Integer skuId;//家具ID

	private String furnitureName;//家具名称

	private String brand;//品牌
	private String seriesName;//系列
	
	private String color;//颜色

	private String material;//材质

    private Integer seriesId;//系列id

    private String itemMaterial;//材质

	private String itemSize;//尺寸

	private Integer itemCount;//数量

	private String itemType;//家具类型

	private String imgUrl;//图片URL

	private String smallImage;//缩略图

	private BigDecimal price;//单价

	private Integer furnitureType;//家具类型：0成品家具  1定制家具  2赠品家具 3新定制品 4bom

	private Integer replaceCount;//可替换家具数量

	private Integer parentSkuId;

	@ApiModelProperty("二级类目id")
	private Integer groupSecondCategoryId;

	@ApiModelProperty("二级类目")
	private String groupSecondCategoryName;

	@ApiModelProperty("组合类型  4 窗帘 9 硬装定制柜 10 软装定制柜")
	private Integer groupType;

	private Integer productStatus;//商品状态

	private String productStatusName;//商品状态:-1待交付 0待采购1采购中2待送货3送货中4送货完成7已取消
	@ApiModelProperty("1:bom组合 0:普通商品 102定制柜")
	private Integer bomFlag;//1:bom组合 0:普通商品
	private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomQueryList;
	private String roomHeadImage;

    private Integer visibleFlag;//是否支持可视化 1支持 0不支持

    private Integer productType;//商品类型

    private boolean appCustomizable;//app 是否可定制

    private boolean hasMore = false;//是否包含更多

    private String moreImageUrl;//图片列表

    private BigDecimal priceDiff;//差价

    @ApiModelProperty("高")
    private Integer height;
    @ApiModelProperty("长")
    private Integer length;
    @ApiModelProperty("宽")
    private Integer width;
    @ApiModelProperty("建议适配床垫最小高")
    private Integer suggestMattressMinHeight;
    @ApiModelProperty("建议适配床垫长")
    private Integer suggestMattressLength;
    @ApiModelProperty("建议适配床垫宽度")
    private Integer suggestMattressWidth;
    @ApiModelProperty("建议适配床垫最大高")
    private Integer suggestMattressMaxHeight;

    @ApiModelProperty("赠品展示标记 0不展示 1可替换为赠品 2免费赠品 4效果图推荐")
    private Integer showFreeFlag = 0;
    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
    private Integer freeFlag = 0;
}

