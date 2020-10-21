package com.ihomefnt.o2o.intf.domain.comment.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 服务点评的标签
 *
 * @author jiangjun
 * @version 2.0, 2018-04-13 上午11:08
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class CommentLabelsResponseVo {

    private Integer id;

    private String name;

    //1:1-3星  2:4-5星
    private Integer kind;
}
