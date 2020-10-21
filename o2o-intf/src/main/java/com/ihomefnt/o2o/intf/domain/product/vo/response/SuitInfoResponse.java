package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.SuitHard;
import com.ihomefnt.o2o.intf.domain.product.doo.TSuitProduct;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class SuitInfoResponse {
    private Long suitId;
    private String suitName;
    private Double suitPrice;
    private Map<String, List<TSuitProduct>> roomProductMap;
    private Map<String, List<SuitHard>> roomHardMap;
}
