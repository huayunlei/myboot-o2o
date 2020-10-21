package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import com.ihomefnt.o2o.intf.domain.promotion.vo.response.MarketingActivityVo;
import lombok.Data;

import java.util.List;
@Data
public class MarketingActivitieListResponse {
    private List<MarketingActivityVo> activities;
}
