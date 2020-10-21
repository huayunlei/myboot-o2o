package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "隐患")
public class TroubleReponseVo {
	
	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;

	@ApiModelProperty(value = "图片")
	private List<DatumReponseVo> datums;
	
	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "整改")
	private ReviseReponse revise;

	@ApiModelProperty(value = "状态")
	private String status;
}
