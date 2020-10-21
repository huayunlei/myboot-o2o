package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-06-20 16:19
 */
@Data
@ApiModel("空间方案对应关系")
public class RoomIdRelationDto {

    private List<RoomIdRelationList> roomIdRelationList;

    @Data
    public static class RoomIdRelationList {
        private Integer betaRoomId;
        private Integer betaSolutionId;
        private Integer drRoomId;//为0表示dr空间方案不存在
        private Integer drSolutionId;
    }
}
