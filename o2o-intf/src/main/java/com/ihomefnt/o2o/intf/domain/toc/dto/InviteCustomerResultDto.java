package com.ihomefnt.o2o.intf.domain.toc.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/11/23 0023.
 */
@Data
public class InviteCustomerResultDto {
    private List<CustomerBaseInfoDto> bindCustomerList;//注册好友列表,
    private List<CustomerBaseInfoDto> paymentCustomerList;//付款好友列表
}
