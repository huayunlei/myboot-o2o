package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import lombok.Data;

/**
 * 1219贺卡信息
 * @author ZHAO
 */
@Data
public class CardInfoResponse {
	private Integer picId;//主键ID
	
	private String imageName;//图片名称
	
	private String imageUrl;//图片地址
	
	private String yearStr;//年份
	
	private String material;//材质
	
	private String author;//作者
}
