package com.ihomefnt.o2o.intf.domain.comment.vo.response;

import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
@Accessors(chain = true)
public class UserCommendResponseVo{

	private List<UserCommentDto> userCommentList;
	private int totalRecords;
    private int totalPages;
}
