package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/19
 */

@Data
@ApiModel("硬装选配包")
@Accessors(chain = true)
public class HardItemSelection implements Serializable {

    @ApiModelProperty("选配包id")
    private Integer hardSelectionId;

    @ApiModelProperty("选配包名称")
    private String hardSelectionName;

    @ApiModelProperty("头图")
    private String headImage;

    @ApiModelProperty("缩略图")
    private String smallImage;

    @ApiModelProperty("选配包描述")
    private String hardSelectionDesc;

    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;

    @ApiModelProperty("是否是默认选项")
    private Boolean defaultSelection = false;

    @ApiModelProperty("可选工艺列表")
    private List<HardProcess> hardProcessList;

    @ApiModelProperty("已选工艺")
    private HardProcess processSelected;

    @ApiModelProperty("唯一标识")
    private String superKey;

    @ApiModelProperty("硬装组合信息")
    private HardBomGroup bomGroup;

    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomQueryList;

    private String roomHeadImage;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;
}