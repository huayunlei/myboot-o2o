package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class HouseSummary implements Serializable {

    private static final long serialVersionUID = 887161309974194177L;
    private Long houseId;   //户型ID
    private String houseName;   //户型名
    private List<SuitSummary> suitSummaryList;  //户型包含套装信息
}
