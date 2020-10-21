package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-16 17:39
 */
@Data
@ApiModel("方案详情图片对象")
public class ProgramPicDto {
    private String url;
    @ApiModelProperty("免费赠品名称列表")
    private List<String> freeFurnitureNameList;
}
