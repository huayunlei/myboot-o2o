package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyonggang
 * @create 2018-12-14 15:05
 */
@Data
@ApiModel("设备属性")
public class FacilityProperty implements Serializable {

    private static final long serialVersionUID = -4068349914089324815L;

    @ApiModelProperty("横竖屏  0竖屏，1横屏")
    private String horizontal;
    @ApiModelProperty("图片集合")
    private List<ImageDto> imageList;
}
