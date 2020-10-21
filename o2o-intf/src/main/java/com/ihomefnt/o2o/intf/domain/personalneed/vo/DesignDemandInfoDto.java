package com.ihomefnt.o2o.intf.domain.personalneed.vo;

import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("查询未确认设计需求详情返回")
public class DesignDemandInfoDto {

    CommitDesignRequest designDemandInfo;

}
