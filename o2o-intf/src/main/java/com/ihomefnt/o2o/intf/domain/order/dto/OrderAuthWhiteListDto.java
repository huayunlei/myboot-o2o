package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderAuthWhiteListDto {
    private String url;

    private List<String> ids;
}
