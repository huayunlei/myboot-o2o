package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceMark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@Data
@ApiModel("户型方案效果返回")
public class SolutionEffectResponse implements Serializable{

    @ApiModelProperty("空间标识图")//若不存在，取户型图
    private String houseTypeImage;

    @ApiModelProperty("方案列表")
    private List<SolutionEffectInfo> solutionEffectInfoList;

    @ApiModelProperty("户型空间标识")
    private List<SpaceMark> spaceMarkList;

    @ApiModelProperty("是否有待分配、待确认、设计中的任务")
    private Boolean hasTask;
}
