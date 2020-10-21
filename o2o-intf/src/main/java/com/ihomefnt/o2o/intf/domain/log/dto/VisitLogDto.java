package com.ihomefnt.o2o.intf.domain.log.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 浏览日志
 * @author ZHAO
 */
@Data
public class VisitLogDto {
	private Integer id;// id

	private Date createTime;// 操作时间

	private String deviceToken;

	private String mobile;

	private Integer visitType;

	private String action;

	@ApiModelProperty("用户Id")
	private Integer userId;

	@ApiModelProperty("app版本号")
	private String appVersion;

	private Integer osType;

	private String pValue;

	private String cityCode;

	private String businessCode;
	
	private String commonValue;
}
