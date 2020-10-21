package com.ihomefnt.o2o.intf.domain.deal.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by hvk687 on 9/29/15.
 */
@Data
public class DealPickHomeResponse {
    private List<DealPickModel> all;//all
    private List<DealPickModel> unPick;
    private List<DealPickModel> picked;
    private List<DealPickModel> unPay;

}
