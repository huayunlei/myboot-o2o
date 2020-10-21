package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-05-13 11:45
 */
@Data
public class MasterOrderSimpleInfo {

    private int houseId;
    private int masterOrderNum;
    private int masterOrderStatus;
    private int source;
}
