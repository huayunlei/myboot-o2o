package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import lombok.Data;

/**
 * 定制贺卡信息
 * @author ZHAO
 */
@Data
public class GreetingCardResponse {
	private Integer cardId;//贺卡ID
	
	private String cardImageUrl;//贺卡图片地址
	
	private String blessingWords;//祝福语
	
	private String signature;//署名
}
