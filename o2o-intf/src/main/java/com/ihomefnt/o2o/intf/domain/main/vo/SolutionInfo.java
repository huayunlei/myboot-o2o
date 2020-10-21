package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("选方案信息")
@Data
@Accessors(chain = true)
public class SolutionInfo {

    @ApiModelProperty("下单类型:1-APP方案下单，2-爱家贷专用方案下单，3-方案代客下单，4-商品代客下单")
    private Integer purchaseType;

    @ApiModelProperty("方案首图")
    private String solutionImgUrl="https://static.ihomefnt.com/1/image/solution_default.png";

    @ApiModelProperty("方案类型:0软+硬，1纯软")
    private Integer solutionType;

    @ApiModelProperty("方案类型字符串")
    private String solutionTypeStr;

    @ApiModelProperty("是否包含爱家贷专用方案空间 0否 1是")
    private Integer containLoan = 0;

    @ApiModelProperty("方案风格名称")
    private String styleName;

    @ApiModelProperty("方案名称")
    private String solutionName;

    @ApiModelProperty("方案id")
    private Integer solutionId;

    @ApiModelProperty("户型版本")
    private Long apartmentVersion;

    @ApiModelProperty("户型ID")
    private Long layoutId;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

    @ApiModelProperty("是否有新的渲染任务完成")
    private boolean hasNewDrawTaskFinish;

    @ApiModelProperty("平面设计图")
    private String solutionGraphicDesignUrl;

    @ApiModelProperty("是否需要展示2018年8月前的设计方案，不支持加入对比 1不展示 0展示")
    private Integer standardHardSolutionFlag = 1;

    @ApiModelProperty("户型图")
    private String apartmentUrl;

    @ApiModelProperty("拆改户型图")
    private String reformApartmentUrl;
}
