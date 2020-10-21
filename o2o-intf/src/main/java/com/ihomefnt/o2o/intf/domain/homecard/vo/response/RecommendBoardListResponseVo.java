package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="RecommendBoardListResponseVo",description="首页推荐版块返回值")
public class RecommendBoardListResponseVo {
    private List<RecommendBoardResponse> recommendList;
}
