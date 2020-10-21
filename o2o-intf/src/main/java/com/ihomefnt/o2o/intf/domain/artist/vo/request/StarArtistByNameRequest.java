package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("根据姓名查询小星星艺术家请求参数")
public class StarArtistByNameRequest extends HttpBaseRequest {

	@ApiModelProperty("小艺术家姓名")
	@NotNull(message="小艺术家姓名不能为空")
	private String artistName; //小艺术家姓名
}
