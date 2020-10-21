package com.ihomefnt.o2o.intf.domain.comment.vo.request;

import lombok.Data;

import java.util.List;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-13 下午1:38
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
public class CommentsAddRequestVo {

    private Integer orderId;// 订单id

    private String comment;// 评论

    private Integer score;// 分数

    private List<Integer> labelIds;// 标签ids

    private List<String> labelNames;//标签名
}
