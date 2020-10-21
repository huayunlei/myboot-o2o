package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;


/**
 * @author xiamingyu
 * @date 2018/9/26
 */
@Data
@ApiModel("未确认的工期变更")
public class TimeChangeResponseVo {

    private Integer addedDate;

    private List<TimeChangeItemResponseVo> itemList;

    private String adviserName;

    private String adviserMobile;

}
