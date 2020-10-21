package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/4 0004.
 */
@Data
@ApiModel("画集信息")
public class SelectedScreenResponse {

    private Integer resourceId;

    private Integer resourceType;

    private Integer isCollect;
    private SelectedScreenSimpleResponse resourceDetail;
    private Integer browseCount;
    private Integer sendCount;

}
