package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by onefish on 2016/11/3 0003.
 */
@Data
@ApiModel("新家大晒列表model")
public class HttpShareOrderRequest extends HttpBaseRequest {

    private int page = 1;
    private int limit = 20;
    private String querySource = "";
}
