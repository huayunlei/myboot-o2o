package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * C端项目创建项目户型等信息的dolly请求类
 *
 * @author liyonggang
 * @create 2019-11-19 09:50
 */
@Data
public class AddCustomerProjectDto {

    @ApiModelProperty("省")
    private Integer provinceId;
    @ApiModelProperty("市")
    private Integer cityId;
    @ApiModelProperty("项目名称")
    private String projectName;
    private String apartmentName;
    @ApiModelProperty("楼栋号")
    private Integer buildingNo;
    @ApiModelProperty("单元号")
    private Integer unitNo;
    @ApiModelProperty("房间号")
    private Integer roomNo;
    private Integer location;
    private String locationName;
    @ApiModelProperty("面积")
    private Integer area;
    @ApiModelProperty("室")
    private Integer room;
    @ApiModelProperty("厅")
    private Integer livingRoom;
    @ApiModelProperty("厨")
    private Integer kitchen;
    private Integer balcony;
    @ApiModelProperty("卫")
    private Integer bathroom;
    @ApiModelProperty("储藏间")
    private Integer storageRoom;
    @ApiModelProperty("衣帽间")
    private Integer cloakroom;
    @ApiModelProperty("用户id")
    private Integer userId;
}
