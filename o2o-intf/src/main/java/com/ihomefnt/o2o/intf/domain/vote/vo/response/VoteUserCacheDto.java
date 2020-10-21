package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-05-15 17:30
 */
@Data
@Accessors(chain = true)
public class VoteUserCacheDto {

    private Integer userId;

    private Integer houseId;

    private Integer orderId;
}
