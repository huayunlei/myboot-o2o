package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import lombok.Data;

/**
 * 定制贺卡
 * @author ZHAO
 */
@Data
public class MakeGreetingCardResponse {
	private Integer cardId;//贺卡ID
	
	private String cardImageUrl;//贺卡图片URL
}
