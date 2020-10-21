package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/15 11:21
 */
@Data
@ApiModel("MasterVo 团主信息")
public class MasterVo {

    @ApiModelProperty("CollageMemberVo")
    private CollageMemberVo collageMember;

}
