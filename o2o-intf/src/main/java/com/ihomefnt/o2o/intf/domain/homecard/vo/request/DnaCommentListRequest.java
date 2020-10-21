package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户DNA评论查询请求参数
 * @author ZHAO
 */
@Data
@ApiModel("用户评论查询请求参数")
public class DnaCommentListRequest extends HttpBaseRequest{
	@ApiModelProperty("DNAid")
	@NotNull(message = "ID不能为空")
	private Integer dnaId;

	@ApiModelProperty("第几页")
	@NotNull(message = "第几页不能为空")
	@Min(value = 1, message = "第几页最小值1")
	private Integer pageNo;

	@ApiModelProperty("每页大小")
	@NotNull(message = "每页大小不能为空")
	@Min(value = 1, message = "每页大小最小值1")
	private Integer pageSize;
}
