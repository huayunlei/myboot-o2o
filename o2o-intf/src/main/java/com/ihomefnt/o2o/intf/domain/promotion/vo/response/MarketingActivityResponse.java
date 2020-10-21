package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import lombok.Data;

@Data
public class MarketingActivityResponse extends HttpBaseResponse {
    private MarketingActivityVo activity;

}
