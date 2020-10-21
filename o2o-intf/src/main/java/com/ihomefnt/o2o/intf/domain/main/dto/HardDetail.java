package com.ihomefnt.o2o.intf.domain.main.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-05-14 15:29
 */
@Data
public class HardDetail {


    private CameraDetailVo cameraDetailVo;
    private HardOrderDetailVo hardOrderDetailVo;
    private List<NodeStatusVos> nodeStatusVos;

    @Data
    public static class CameraDetailVo {
        private String url;
        private int bindStatus;
    }

    @Data
    public static class HardOrderDetailVo {
        private long hardOrderId;
        private int hardStatus;
        private String planBeginDate;
        private String actualBeginDate;
        private String planEndDate;
        private String actualEndDate;
        private int days;
        private String surveyorName;
        private String surveyorPhone;
    }

    @Data
    public static class NodeStatusVos {
        private long nodeId;
        private String nodeName;
        private int status;
    }
}
