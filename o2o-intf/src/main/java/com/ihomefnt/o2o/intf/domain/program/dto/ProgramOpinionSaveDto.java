package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 方案意见详情数据
 *
 * @author liyonggang
 * @create 2019-08-08 16:20
 */
@ApiModel("方案意见详情数据")
@Data
public class ProgramOpinionSaveDto {


    private Integer userId;
    private String id;
    private String userName;
    private String mobile;
    private Integer orderNum;
    private Integer solutionId;
    private List<ReviseOpinionListForDolly> reviseOpinionList;
    private Date addTime;
    private Integer source; // dolly涞源枚举 1 客户确认 ，2 运营确认，3 betaApp用户确认，4 betaApp运营确认
    private Integer submitterUserId;

    @Data
    @Accessors(chain = true)
    public static class ReviseOpinionListForDolly {
        private Integer roomId;
        private String roomUsageName;
        private String remarks;
        private List<TagInfo> tagList;
    }
}
