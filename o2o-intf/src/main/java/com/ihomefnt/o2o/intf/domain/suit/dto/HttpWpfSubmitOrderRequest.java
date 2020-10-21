/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * 
 * @author charl
 *
 */
@Data
public class HttpWpfSubmitOrderRequest extends HttpBaseRequest {
	
	private String name;//用户姓名
	
	private String mobile;//用户手机号码
	
	private String submitMobile;//下单手机号码（后端用不用穿）
	
	private String orderPrice;//预估合同额
	
	private String cityName;//城市名称,南京
	
	private String community;//小区名称,中南世纪雅苑
	
	private String size;//户型面积,87m²
	
	private String wpfName;//全品家套餐信息,一生一世 1314元/m²
	
	private String extraPackName;//增配包信息,1万元家电套餐，智慧家增配包1
	
}
