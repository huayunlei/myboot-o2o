package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2020-02-13 14:55
 */
@Data
@ApiModel("项目信息入参")
public class HouseProjectRequest extends HttpBaseRequest {

    private Integer buildingId;//楼盘项目ID
}
