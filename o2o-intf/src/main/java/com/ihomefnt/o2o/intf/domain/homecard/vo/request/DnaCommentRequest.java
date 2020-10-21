package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DNA评论新增
 * @author ZHAO
 */
@Data
@ApiModel("DNA评论新增请求参数")
public class DnaCommentRequest extends HttpBaseRequest{
	@ApiModelProperty("DNAid")
	@NotNull(message = "ID不能为空")
	private Integer dnaId;

	@ApiModelProperty("评论内容")
	@NotNull(message = "评论内容不能为空")
	private String content;

	@ApiModelProperty("评论星数")
	private Integer starNum;

	@ApiModelProperty("评论ID")
	private Integer commentId;

}
