package com.ihomefnt.o2o.intf.domain.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-16 下午7:07
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class CheckCommentDto {

    //是否可点评 true:可以 false:不可以
    private boolean canComment;
    //是否已点评 true:已点评 false:未点评
    private boolean commentType;
}
