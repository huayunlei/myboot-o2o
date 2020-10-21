package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;
import lombok.Data;

import java.util.List;
@Data
public class HttpInspirationHomeResponse {

	private List<AppButton> pictureButtonList;
	private List<AppButton> strategyButtonList;
	private HttpCaseListResponse moreCaseResponse;
	
}
