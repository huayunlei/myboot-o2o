package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import com.ihomefnt.o2o.intf.domain.delivery.dto.SolfSimpleInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 硬装进度
 */
@Data
public class HardDetailVo {

    @ApiModelProperty("摄像头信息")
    private CameraDetailVo cameraDetailVo;

    @ApiModelProperty("工地概览")
    private HardOrderDetailVo hardOrderDetailVo;

    @ApiModelProperty("节点状态信息")
    private List<NodeStatusVo> nodeStatusVos;

    @ApiModelProperty("软装信息")
    private SolfSimpleInfo solfSimpleInfo;

    @ApiModelProperty("公告信息")
    private NoticeBoardDto noticeBoard;
}
