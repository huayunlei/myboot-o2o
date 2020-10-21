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
@ApiModel(description = "验收项")
public class ItemReponseVo {
	
	@ApiModelProperty(value = "id")
	private String id;
	
	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "问题")
	private List<TroubleReponseVo> troubles;

	@ApiModelProperty(value = "时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date updateTime;

}
