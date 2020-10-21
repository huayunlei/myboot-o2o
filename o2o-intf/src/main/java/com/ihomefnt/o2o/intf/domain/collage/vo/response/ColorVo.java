package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/15 14:06
 */
@Data
@ApiModel("ColorVo")
public class ColorVo {

    @ApiModelProperty("颜色ID 1 四种颜色随机发 2 抹茶绿 3 可可黑 4 樱桃红 5 香芋紫")
    private Integer colorId;

    @ApiModelProperty("名称")
    private String colorName;

    @ApiModelProperty("url")
    private String url;

    public ColorVo(Integer colorId, String colorName, String url) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.url = url;
    }
}
