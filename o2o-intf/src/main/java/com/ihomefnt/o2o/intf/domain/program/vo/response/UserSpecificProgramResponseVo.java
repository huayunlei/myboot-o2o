package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.VideoEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserSpecificProgramResponseVo {
    private List<SeriesProgramListResponse> seriesProgramList;
    private BigDecimal solutionMinPrice;
    private String decorationType;
    private int solutionCount;
    private Integer selectLimitFlag;
    private boolean hardStandLookFlag;
    private List<VideoEntity> videoList;
}
