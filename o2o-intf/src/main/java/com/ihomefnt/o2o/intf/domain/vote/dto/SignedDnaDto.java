package com.ihomefnt.o2o.intf.domain.vote.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-10-18 16:48
 */
@Data
public class SignedDnaDto {

    private Integer userId;
    private Integer orderNum;
    private List<Integer> dnaIdList;

    public SignedDnaDto(Integer userId, Integer orderNum) {
        this.userId = userId;
        this.orderNum = orderNum;
    }

    public SignedDnaDto() {
    }
}
