/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

/**
 * @author weitichao
 *
 */
@Data
public class WpfCaseItem {
	
	private Long wpfCaseId; //全品家案例id
	
	private String wpfCaseTitle; //全品家案例标题
	
	private String wpfCaseImageUrl; //全品家案例头图
	
	private String wpfCaseUrl; //全品家案例文章地址
}
