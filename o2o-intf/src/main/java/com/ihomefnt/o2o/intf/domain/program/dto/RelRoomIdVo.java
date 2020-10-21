package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-07-06 14:23
 */
@NoArgsConstructor
@Data
public class RelRoomIdVo {

    private Integer id;
    private Integer roomClassId;
    private String name;
    private Integer relRoomClassId;
    private Long createTime;
    private Integer createUserId;
    private Long updateTime;
    private Integer updateUserId;
    private Integer delFlag;
    private String remark;
}
