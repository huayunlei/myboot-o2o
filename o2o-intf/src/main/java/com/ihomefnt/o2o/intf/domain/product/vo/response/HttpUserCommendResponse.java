package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.UserComment;
import lombok.Data;

import java.util.List;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
public class HttpUserCommendResponse{

	private List<UserComment> userCommentList;
	
	private int totalRecords;

    private int totalPages;

}
