package com.ihomefnt.o2o.intf.domain.dna.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-11-19 09:37
 */
@Data
@ApiModel(value = "装修报价返回信息")
@Accessors(chain = true)
public class DecorationProcessResponse {


    private List<MasterNodeListBean> masterNodeList;

    @Data
    public static class MasterNodeListBean {

        private Integer masterNodeId;
        private String masterNode;
        private Integer masterStatus;//1已完成 2进行中 3待进行
        private List<EventNodeListBean> eventNodeList;

        @Data
        public static class EventNodeListBean {

            private Integer eventNodeId;
            private String eventNode;
            private String eventContent;
            private String createTime;
        }
    }
}
