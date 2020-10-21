package com.ihomefnt.o2o.intf.domain.product.vo.request;

import lombok.Data;

import java.util.List;
@Data
public class SuitHardRequest {
    private Long suitId;
    private List<Long> roomIdList;
    private List<Long> hardIdList;
    private List<Long> roomIdTempList;
    private List<Long> hardIdTempList;
    
}
