/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:StarArtRequest.java
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小星星作品
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星作品请求参数")
public class StarArtRequest {
    @ApiModelProperty("艺术品ID")
    private Long artId;
}
