package com.ihomefnt.o2o.intf.domain.delivery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 软装简单信息
 *
 * @author liyonggang
 * @create 2018-12-29 11:12
 */
@ApiModel("软装进度信息")
@Accessors(chain = true)
@Data
public class SolfSimpleInfo {

    @ApiModelProperty("软装家具总数")
    private int sum;
    @ApiModelProperty("已进场的家具数，商品状态为送货完成")
    private int finished;

    public SolfSimpleInfo(int sum, int finished) {
        this.sum = sum;
        this.finished = finished;
    }

    public SolfSimpleInfo() {
    }
}
