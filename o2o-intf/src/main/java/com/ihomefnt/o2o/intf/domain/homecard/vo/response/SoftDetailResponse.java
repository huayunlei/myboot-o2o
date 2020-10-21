package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.SoftDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 软装清单返回值
 * @author ZHAO
 */
@Data
@ApiModel(value="SoftDetailResponse",description="软装清单返回值")
public class SoftDetailResponse implements Serializable{
	@ApiModelProperty("空间名称")
	private String room;
	
	@ApiModelProperty("空间软装清单")
	private List<SoftDetail> itemList;

	public SoftDetailResponse() {
		this.room = "";
		this.itemList = new ArrayList<>();
	}

}
