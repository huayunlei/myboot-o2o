package com.ihomefnt.o2o.intf.domain.main.vo.response;

import com.ihomefnt.o2o.constant.MainNodeEnum;
import com.ihomefnt.o2o.intf.domain.main.vo.MainNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("节点返回")
@Data
@Accessors(chain = true)
public class MainNodeResponse {

    @ApiModelProperty("节点列表")
    private List<MainNode> nodeList=new ArrayList<MainNode>(){{
        add(new MainNode().setNodeId(MainNodeEnum.ABOUT_US.getNodeId()).setNodeName(MainNodeEnum.ABOUT_US.getNodeName()));
        add(new MainNode().setNodeId(MainNodeEnum.DESIGN_HOME.getNodeId()).setNodeName(MainNodeEnum.DESIGN_HOME.getNodeName()));
        add(new MainNode().setNodeId(MainNodeEnum.CONFIRM_SOLUTION.getNodeId()).setNodeName(MainNodeEnum.CONFIRM_SOLUTION.getNodeName()));
        add(new MainNode().setNodeId(MainNodeEnum.CONSTRUCTION.getNodeId()).setNodeName(MainNodeEnum.CONSTRUCTION.getNodeName()));
        add(new MainNode().setNodeId(MainNodeEnum.CHECK.getNodeId()).setNodeName(MainNodeEnum.CHECK.getNodeName()));
        add(new MainNode().setNodeId(MainNodeEnum.MAINTENANCE.getNodeId()).setNodeName(MainNodeEnum.MAINTENANCE.getNodeName()));
    }};

    @ApiModelProperty("焦点节点")
    private MainNode focusNode;
}
