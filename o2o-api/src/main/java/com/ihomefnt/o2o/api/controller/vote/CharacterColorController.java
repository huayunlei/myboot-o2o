package com.ihomefnt.o2o.api.controller.vote;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.QuestionnaireRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.AnalysisResultResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.QuestionnaireResponse;
import com.ihomefnt.o2o.intf.service.vote.CharacterColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wanyunxin
 * @create 2019-10-18 14:46
 */
@Api(tags = "【性格色彩分析API】")
@RestController
@RequestMapping("/characterColor")
public class CharacterColorController {

    @Autowired
    private CharacterColorService characterColorService;

    @ApiOperation(value = "问答详情查询", notes = "问答详情查询")
    @PostMapping("/queryQuestionAnswerList")
    public HttpBaseResponse<QuestionnaireResponse> queryQuestionAnswerList(@RequestBody QuestionRequest request) {
        return HttpBaseResponse.success(characterColorService.queryQuestionAnswerList(request));
    }

    @ApiOperation(value = "提交问卷", notes = "提交问卷")
    @PostMapping("/addQuestionnaire")
    public HttpBaseResponse<AnalysisResultResponse> addQuestionnaire(@RequestBody QuestionnaireRequest request) {
        return HttpBaseResponse.success(characterColorService.addQuestionnaire(request));
    }
}
