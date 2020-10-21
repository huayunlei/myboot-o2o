package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-09 16:48
 */
@Data
public class OrderUnConfirmOpinionDto {

    private List<OrderUnConfirmOpinionList> orderUnConfirmOpinionList;

    @Data
    public static class OrderUnConfirmOpinionList {
        private Integer orderNum;
        private List<UnConfirmOptionList> unConfirmOptionList;

        @Data
        public static class UnConfirmOptionList {
            private Integer count = 0;
            private Integer solutionId;
        }
    }
}
