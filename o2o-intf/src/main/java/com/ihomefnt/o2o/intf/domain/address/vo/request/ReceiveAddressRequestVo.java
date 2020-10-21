package com.ihomefnt.o2o.intf.domain.address.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("ReceiveAddressRequestVo")
public class ReceiveAddressRequestVo extends HttpBaseRequest {
	@ApiModelProperty("purchaserName")
	private String purchaserName;
	@ApiModelProperty("purchaserTel")
	private String purchaserTel;
	@ApiModelProperty("PCDSNameRequestVo")
	private PCDSNameRequestVo pCDSName;

	public PCDSNameRequestVo getpCDSName() {
		return pCDSName;
	}

	public void setpCDSName(PCDSNameRequestVo pCDSName) {
		this.pCDSName = pCDSName;
	}
}
