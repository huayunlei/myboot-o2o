package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import lombok.Data;

/**
 * 房产编辑添加次数
 * Author: ZHAO
 * Date: 2018年4月13日
 */
@Data
public class HouseAddEditCountResponse {
	
	private Integer editCount = 0;//已编辑次数
	
	private Integer remainEditCount = 0;//剩余编辑次数

}
