package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * DNA空间图片
 * @author ZHAO
 */
@Data
public class DNARoomPictureVo {
	private Integer roomUsageId;//DNA空间用途id
	
	private String roomUsageName;//DNA空间用途名称
	
	private String pictureURL;//DNA空间图片地址
	
	private Integer isFirst;//是否是头图 0不是 1是

}
