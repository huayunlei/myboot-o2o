package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/28 17:01
 */
@Data
@ApiModel("确权响应bean")
public class RightConfirmDto {

    // 提交操作状态结果
    private Boolean success;
}
