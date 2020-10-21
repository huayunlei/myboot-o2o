package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 方案信息返回值
 *
 * @author ZHAO
 */
@Data
@ApiModel("方案信息")
public class ProgramResponse implements Serializable {
    @ApiModelProperty("方案ID")
    private Integer programId;//方案ID
    @ApiModelProperty("方案名称")
    private String name;//方案名称
    @ApiModelProperty("风格")
    private String style;//风格
    @ApiModelProperty("家具数量")
    private Integer furnitureNum;//家具数量
    @ApiModelProperty("全屋价格")
    private String price;//全屋价格
    @ApiModelProperty("折扣")
    private String discount;//折扣
    @ApiModelProperty("折扣后价格")
    private String discountPrice;//折扣后价格
    @ApiModelProperty("方案头图")
    private String headImgUrl;//方案头图
    @ApiModelProperty("方案的所有图片")
    private List<String> allImages;//方案的所有图片
    @ApiModelProperty("软装+硬装")
    private String category;//装修类别：软装+硬装
    @ApiModelProperty("方案设计描述")
    private String solutionDesignIdea;// 方案设计描述
    @ApiModelProperty("优点")
    private String advantage;//优点
    private String availableCommission;
    private List<String> tagList;//标签集合

    public ProgramResponse() {
        this.programId = -1;
        this.name = "";
        this.style = "";
        this.furnitureNum = 0;
        this.price = "";
        this.discount = "";
        this.discountPrice = "";
        this.headImgUrl = "";
        this.category = "";
        this.solutionDesignIdea = "";
        this.advantage = "";
        this.tagList = new ArrayList<>();
    }
}
