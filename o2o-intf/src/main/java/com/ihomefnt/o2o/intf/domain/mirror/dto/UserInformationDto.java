package com.ihomefnt.o2o.intf.domain.mirror.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 何佳文
 * @date: 2019-11-08 16:26
 */
@Data
public class UserInformationDto {

    @ApiModelProperty(value = "头像", required = true)
    private String headImg;

    @ApiModelProperty(value = "macId", required = true)
    private String macId;

    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "性别 0:女 1:男", required = true)
    private Integer sex;

    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @ApiModelProperty(value = "身高", required = true)
    private Integer height;
}
