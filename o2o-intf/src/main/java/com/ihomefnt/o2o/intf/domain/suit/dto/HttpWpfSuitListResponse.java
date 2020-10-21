/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.util.List;

/**
 * @author weitichao
 *
 */
@Data
public class HttpWpfSuitListResponse {
	
	private List<TWpfSuit> wpfSuitList; //全品家项目列表：四个套系加套餐标配清单
	
	private List<WpfCaseItem> wpfCaseList; //全品家近期案例
}
