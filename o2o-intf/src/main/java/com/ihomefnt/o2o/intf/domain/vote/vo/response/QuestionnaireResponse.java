package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 15:10
 */
@NoArgsConstructor
@Data
@ApiModel("问卷详情数据")
public class QuestionnaireResponse {

    @ApiModelProperty("问题列表")
    private List<QuestionListBean> questionList;

    @ApiModelProperty("背景图片")
    private String backgroundImg;

    @NoArgsConstructor
    @Data
    public static class QuestionListBean {

        @ApiModelProperty("问题ID")
        private Integer questionId;

        @ApiModelProperty("问题说明")
        private String questionInfo;

        @ApiModelProperty("问题图片")
        private String questionImg;

        @ApiModelProperty("问题排序")
        private Integer questionSort;

        @ApiModelProperty("答案列表")
        private List<AnswerListBean> answerList;

        @NoArgsConstructor
        @Data
        public static class AnswerListBean {

            @ApiModelProperty("答案ID")
            private Integer answerId;

            @ApiModelProperty("答案说明")
            private String answerInfo;

            @ApiModelProperty("答案图片")
            private String answerImg;

            @ApiModelProperty("答案排序")
            private Integer answerSort;
        }
    }
}
