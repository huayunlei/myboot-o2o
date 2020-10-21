package com.ihomefnt.o2o.intf.domain.promotion.dto;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.agent.dto.ZoneVo;
import lombok.Data;

@Data
public class ProjectInfoVo {
    private Integer id;
    private String projectName;
    private Integer projectManagerId;
    private Integer companyId;
    private List<ZoneVo> zoneList;
}
