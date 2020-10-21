package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import com.ihomefnt.o2o.intf.domain.program.dto.FurnitureEntity;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.programorder.dto.CabinetBomDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 可选软装信息
 *
 * @author ZHAO
 */
@Data
public class OptionalSoftResponse implements Serializable {

    @ApiModelProperty("所属空间设计的Id")
    private Integer roomId;

    @ApiModelProperty("二级类目")
    private String category;

    @ApiModelProperty("唯一标识")
    private String superKey;

    @ApiModelProperty("默认Skuid")
    private Integer defaultSkuId;

    @ApiModelProperty("家具列表")
    private List<FurnitureEntity> furnitureList;

    @ApiModelProperty("已选家具")
    private FurnitureEntity furnitureSelected;

    @ApiModelProperty("是否支持可视化 1支持 0不支持")
    private Integer visibleFlag;//是否支持可视化 1支持 0不支持

    @ApiModelProperty("是否有设计师推荐内容")
    private boolean replaceAble = false;//是否有设计师推荐内容

    @ApiModelProperty("组合信息")
    private List<BomGroupVO> bomGroupList;//软硬装选配使用，全品家清单不用该字段

    @ApiModelProperty("默认家具")
    private FurnitureEntity furnitureDefault;

    @ApiModelProperty("是否有替换项, 成品代表有没有可替换项，bom组合代表是否可定制")
    private Boolean hasReplaceItem;

    @ApiModelProperty("是否支持可视化云渲染")
    private boolean supportDrawCategory = false;

    @ApiModelProperty("二级类目Id")
    private Integer typeTwoId;

    @ApiModelProperty("末级类目ID")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;

    private Integer rootCategoryId;// 根类目ID

    private String rootCategoryName;// 根类目名称

    private Integer itemType = 0;//0其他，1:床,2:床垫

    private String itemImage;

    private String itemName;

    @ApiModelProperty("定制柜数据,全品家清单使用")
    private List<HardBomGroup> guiBomGroupList;

    @ApiModelProperty("查询详情使用参数")
    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomGroupQueryList;

    @ApiModelProperty("定制柜数据 草稿使用")
    private CabinetBomDto cabinetBomGroup;

    private Integer bomFlag = 0;//1bom 0普通 102定制柜

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAbleDolly = 0;

}
