package com.ihomefnt.o2o.intf.domain.ajb.dto;

import lombok.Data;

@Data
public class AjbSearchDto {

    private Integer userId; //用户ID
    private Integer accountBookType;    //账本类型  现金券 1, 诚意金 2, 定金 3, 佣金 4, 提现 5,艾积分 6
    private Integer pageNo;
    private Integer pageSize;
}
