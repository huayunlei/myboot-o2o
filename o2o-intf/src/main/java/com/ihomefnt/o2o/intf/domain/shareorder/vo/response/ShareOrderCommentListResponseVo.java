package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrderComment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShareOrderCommentListResponseVo {
    private List<ShareOrderComment> comments;
}
