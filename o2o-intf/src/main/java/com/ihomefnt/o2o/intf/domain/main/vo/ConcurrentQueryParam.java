package com.ihomefnt.o2o.intf.domain.main.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/24
 */

@Data
@Accessors(chain = true)
public class ConcurrentQueryParam {

    private Integer taskCount;

    private Integer orderId;

    private Integer userId;

    private Integer houseProjectId;

    private Integer houseTypeId;

}
