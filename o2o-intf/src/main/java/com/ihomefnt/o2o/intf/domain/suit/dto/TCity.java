/**   
* @Title: TCity.java 
* @Description: TODO
* @author Charl 
* @date 2016年9月2日 上午9:26:13 
* @version V1.0   
*/
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**   
 * @Title: TCity.java 
 * @Description: TODO
 * @author Charl 
 * @date 2016年9月2日 上午9:26:13 
 * @version V1.0   
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TCity implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	private Integer cityCode;
	
	private String cityName;
	
	private List<String> buildingList;
	
	public TCity(Integer cityCode, String cityName, List<String> buildingList) {
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.buildingList = buildingList;
	}
}
