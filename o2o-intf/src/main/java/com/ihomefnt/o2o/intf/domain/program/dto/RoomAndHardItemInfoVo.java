package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesignVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lindan on 2018/7/23.
 */
@Data
public class RoomAndHardItemInfoVo implements Serializable{

    @ApiModelProperty("硬装选配示意图") // 目前先取空间标识图，没有则取户型图
    private String headImage;

    @ApiModelProperty("空间列表")
    private List<SpaceDesignVo> spaceDesignList;
}
