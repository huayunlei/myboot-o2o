package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小星星艺术品
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星艺术品请求参数")
public class StarArtistRequest {
	@ApiModelProperty("用户ID")
    private Integer userId;
}
