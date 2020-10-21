package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HouseResponse;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * 全品家订单信息
 * @author ZHAO
 */
@Data
public class AllProductOrderDetail {
	private HouseResponse houseInfo;//房产信息
	
	private String homeAdviserMobile;//置家顾问电话
	
	private String serviceMobile;//客服电话
	
	private Integer orderProgramTypeCode;//订单方案类型：1整套方案  2自由搭配  
	
	private Integer solutionId;// 方案id

	private String solutionName;// 方案名称
	
	private Integer furnitureTotalNum;//家具总件数
	
	private String bottomPraise;//底部文案
	
	private Integer orderSource;//订单来源：6代客下单

	private Integer gradeId;//权益等级id

	private String gradeName;//权益等级名称

	@ApiModelProperty("权益版本号")//2020版本权益为4
	private Integer rightsVersion = 2;

	public AllProductOrderDetail() {
		this.houseInfo = new HouseResponse();
		this.homeAdviserMobile = "";
		this.serviceMobile = "";
		this.orderProgramTypeCode = 1;
		this.solutionId = -1;
		this.solutionName = "";
		this.furnitureTotalNum = 0;
		this.bottomPraise = "";
		this.orderSource = 0;
	}

}
