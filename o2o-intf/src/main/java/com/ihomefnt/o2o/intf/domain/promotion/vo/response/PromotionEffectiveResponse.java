package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import lombok.Data;

/**
 * 活动是否有效返回对象
 * @author ZHAO
 */
@Data
public class PromotionEffectiveResponse {
	private Integer effectiveType;//有效类型：1 无活动  2 有活动未参加  3有活动已参加
	
	private String shareUrl;//H5分享路径

	public PromotionEffectiveResponse() {
		this.effectiveType = 1;
		this.shareUrl = "";
	}
}
