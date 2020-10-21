package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 全品家服务流程数据Response
 *
 * @author liyonggang
 * @create 2019-11-15 11:25
 */
@Data
@ApiModel("全品家服务流程数据Response")
public class FamilyOrderServiceProcessDataResponse {

    private List<FamilyOrderServiceProcessNodeVO> nodeList;
    private FamilyOrderServiceProcessNodeVO cover;

    @Data
    public static class FamilyOrderServiceProcessNodeVO {
        private Integer sequenceNumber;
        private String copyWriter;
        private String attachCopyWriter;
        private String nodeName;
        private List<String> images;
        @ApiModelProperty("文字样式:1:左对齐,2:右对齐")
        private Integer textStyle;
    }
}
