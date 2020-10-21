package com.ihomefnt.o2o.intf.domain.vote.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 14:59
 */
@NoArgsConstructor
@Data
@ApiModel("提交问卷类")
public class QuestionnaireRequest extends HttpBaseRequest {

    @ApiModelProperty("问题版本号")
    private Integer questionVersion;

    @ApiModelProperty("来源 1app 2 H5 3 微信端")
    private Integer sourceType;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("微信授权用户或app用户昵称")
    private String nickName;

    @ApiModelProperty("微信授权用户性别 0未知 1男 2女")
    private Integer sex;

    @ApiModelProperty("app用户订单号，有全品家订单时传入")//app取当前订单
    private Integer orderNum;

    @ApiModelProperty("DNAId列表 已签约用户提交问卷时有值")
    private List<Integer> dnaIdList;

    @ApiModelProperty("已选问答列表")
    private List<SelectedAnswerListBean> selectedAnswerList;

    @NoArgsConstructor
    @Data
    public static class SelectedAnswerListBean {

        @ApiModelProperty("问题ID")
        private Integer questionId;

        @ApiModelProperty("已选答案列表")
        private List<Integer> answerIdList;
    }
}
