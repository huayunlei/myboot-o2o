package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-04 10:33
 */
@Data
public class NodeProcessRecordVoV2 {


    private String name;
    private List<NodeProcessRecordVos> nodeProcessRecordVos;

    @Data
    public static class NodeProcessRecordVos {
        private String nodeStatusDesc;
        private String picOperationDesc;
        private Integer recordType;
        private String reformOpinion;
        private String urlDate;
        private Integer userType;
        private List<PicRecordDetailVos> picRecordDetailVos;

        @Data
        public static class PicRecordDetailVos {
            private String itemDesc;
            private Long itemId;
            private String urlTime;
            private List<String> pics;
        }
    }
}
