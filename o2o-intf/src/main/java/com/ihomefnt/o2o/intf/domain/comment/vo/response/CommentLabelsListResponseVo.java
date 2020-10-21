package com.ihomefnt.o2o.intf.domain.comment.vo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentLabelsListResponseVo {

	private List<CommentLabelsResponseVo> labels;
}
