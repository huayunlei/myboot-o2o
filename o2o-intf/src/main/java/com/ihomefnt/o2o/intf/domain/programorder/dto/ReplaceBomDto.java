package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-07-24 10:14
 */
@Data
public class ReplaceBomDto {

    //草稿使用
    private HardBomGroup bomGroupDefault;
    //草稿使用
    private HardBomGroup bomGroupSelect;
}
