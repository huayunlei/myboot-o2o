package com.ihomefnt.o2o.intf.domain.right.vo.request;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/5 19:58
 */
@ApiModel("根据订单查询权益资质请求参数")
@Data
public class OrderRightsLicenseRequestVo extends HttpBaseRequest{

    /**
     * 订单id
     */
    private Long orderNum;

}
