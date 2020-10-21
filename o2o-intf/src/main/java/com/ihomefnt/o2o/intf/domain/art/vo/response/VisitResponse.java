package com.ihomefnt.o2o.intf.domain.art.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-14 15:42
 */
@Data
@ApiModel("艾商城浏览量返回数据")
public class VisitResponse {

        private List<VisitRecordDto> visitRecords;

        @Data
        @ApiModel("艾商城浏览量")
        public static class VisitRecordDto {

            private Integer id;

            private Integer productType;//DNA id

            private String productId;//DNA名称

            private Integer visitNum;//浏览量


        }
}
