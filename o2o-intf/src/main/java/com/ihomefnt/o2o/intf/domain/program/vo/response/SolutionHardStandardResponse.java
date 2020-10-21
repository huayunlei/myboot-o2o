package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/22
 */

@ApiModel("方案硬装标准")
public class SolutionHardStandardResponse {

    @ApiModelProperty("空间设计")
    private List<SpaceDesign> spaceDesignList;

    public List<SpaceDesign> getSpaceDesignList() {
        return spaceDesignList;
    }

    public void setSpaceDesignList(List<SpaceDesign> spaceDesignList) {
        this.spaceDesignList = spaceDesignList;
    }
}
