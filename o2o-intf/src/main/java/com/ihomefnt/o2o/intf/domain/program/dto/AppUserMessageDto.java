package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-17 19:11
 */
@Data
public class AppUserMessageDto {


    private List<TriggerListBean> triggerList;

    @Data
    public static class TriggerListBean {

        private Integer userId;
        private Integer solutionId;
        private Integer sceneCode;
        private String triggerTime;
    }
}
