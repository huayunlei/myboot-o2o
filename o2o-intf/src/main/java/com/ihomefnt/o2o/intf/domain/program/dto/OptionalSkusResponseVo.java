package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 空间可替换商品信息
 *
 * @author ZHAO
 */
@Data
public class OptionalSkusResponseVo implements Serializable {
    private Integer roomId;//空间ID

    private String typeTwoName;//二级类目名称

    @ApiModelProperty("二级类目Id")
    private Integer typeTwoId;

    private String spaceName;//空间名称

    private Integer spaceUseId;//空间用途ID

    private String superKey;//唯一标识

    private String spaceUseName;//空间用途名称

    private String roomImage;//空间图片

    private List<SolutionRoomSubItemVo> subItemVos;//可替换sku列表

    private Integer visibleFlag = 0;//空间支持可视化1：是 0：否

    private List<BomGroupVO> bomGroupList;//空间下默认组合信息

    private Boolean hasReplaceItem = Boolean.FALSE;//	是否有替换项, 成品代表有没有可替换项，bom组合代表是否可定制

    private Integer lastCategoryId;// 末级类目ID

    private String lastCategoryName;// 末级类目名称

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;
}
