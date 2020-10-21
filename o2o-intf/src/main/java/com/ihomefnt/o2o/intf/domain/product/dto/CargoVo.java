package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("货物vo")
public class CargoVo {
	
	private Integer id; //(integer, optional),
	private String cargoNo; //(string, optional),
	private Integer categoryId; //(integer, optional),
	private String name; //(string, optional),
	private Integer supplierId; //(integer, optional),
	private BigDecimal purchasePrice; //(number, optional),
	private String manufacturerModel; //(string, optional),
	private String specificationNote; //(string, optional),
	private Integer packageSum; //(integer, optional),
	private String image; //(string, optional),
	private List<CargoPackageVo> cargoPackageList; //(array[CargoPackageVo], optional),
	private String createTime; //(string, optional),
	private String updateTime; //(string, optional)
}
