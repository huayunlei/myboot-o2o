package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-21 11:51
 */
@NoArgsConstructor
@Data
public class OrderSimpleInfoResponse {

    private SolutionInfoBean solutionInfo;

    @Data
    public static class SolutionInfoBean {
        private Long solutionId;
    }
}
