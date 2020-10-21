package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SoftDetailListResponseVo implements Serializable {
    private static final long serialVersionUID = -5846219267989616580L;

    private List<SoftDetailResponse> softList;
}
