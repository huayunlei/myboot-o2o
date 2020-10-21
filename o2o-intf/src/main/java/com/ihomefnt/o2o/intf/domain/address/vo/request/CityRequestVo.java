package com.ihomefnt.o2o.intf.domain.address.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class CityRequestVo extends HttpBaseRequest{
    private Double latitude;
    private Double longitude;
}
