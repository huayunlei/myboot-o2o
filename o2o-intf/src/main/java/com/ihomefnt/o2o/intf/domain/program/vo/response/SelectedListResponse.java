package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesign;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/20
 */
@Data
public class SelectedListResponse {

    private SolutionEffectInfo solutionEffectInfo;

    private List<SpaceDesign> spaceDesignList;

    private String json;
}


