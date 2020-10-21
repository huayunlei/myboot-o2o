package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单方案信息
 *
 * @author ZHAO
 */
@Data
public class SolutionOrderInfo implements Serializable {

    @ApiModelProperty("订单方案类型：1整套方案  2自由搭配")
    private Integer orderProgramTypeCode;//订单方案类型：1整套方案  2自由搭配  

    @ApiModelProperty("风格集合")
    private List<String> styleNameList;//风格集合

    @ApiModelProperty("客厅方案套系")
    private String seriesName;//客厅方案套系

    @ApiModelProperty("方案id")
    private Integer solutionId;// 方案id

    @ApiModelProperty("方案名称")
    private String solutionName;// 方案名称

    @ApiModelProperty("方案头图")
    private String headImgUrl;//方案头图

    @ApiModelProperty("所有空间的图")
    private List<String> roomAllUrls; //所有空间的图

    @ApiModelProperty("家具总件数")
    private Integer furnitureTotalNum;//家具总件数

    @ApiModelProperty("软硬装类别")
    private String category;//软硬装类别

    @ApiModelProperty("开工日期")
    private String commenceTime;//开工日期

    @ApiModelProperty("签约日期")
    private String contractTime;//签约日期

    @ApiModelProperty("所有空间的图和空间名")
    private List<Map> roomAllUrlsAndNames; //所有空间的图和空间名

    public SolutionOrderInfo() {
        this.orderProgramTypeCode = 1;
        this.styleNameList = new ArrayList<>();
        this.seriesName = "";
        this.solutionId = -1;
        this.solutionName = "";
        this.headImgUrl = "";
        this.furnitureTotalNum = 0;
        this.category = "";
        this.commenceTime = "";
        this.contractTime = "";
    }
}
