package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.UserConsult;
import lombok.Data;

import java.util.List;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
public class HttpUserConsultResponse{

	private List<UserConsult> userConsultList;
	
	private int totalRecords;

    private int totalPages;

}
