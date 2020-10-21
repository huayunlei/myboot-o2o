package com.ihomefnt.o2o.intf.domain.homecard.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * APP3.0首页产品版块筛选项实体对象
 * @author ZHAO
 */
@Data
public class ProductFilterInfo {

	/**
	 * 筛选项id
	 */
	private Integer filterId;
	
	/**
	 * 筛选项名称
	 */
	private String filterName;
	
	private String filterImgUrl;//风格效果图
	
	private Integer roomClassifyType;//空间分类类型(1厅2室3厨4卫5阳台6储藏间7衣帽间)
	
	/**
     *户型空间id
     */
    @ApiModelProperty("户型空间id")
    private Long roomId;

	
	public ProductFilterInfo() {
		this.filterId = 0;
		this.filterName = "";
		this.filterImgUrl = "";
		this.roomId = 0L;
	}

	public ProductFilterInfo(Integer filterId, String filterName, String filterImgUrl, Long roomId) {
		this.filterId = filterId;
		this.filterName = filterName;
		this.filterImgUrl = filterImgUrl;
		this.roomId = roomId;
	}

}
