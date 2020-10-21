package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseCommonVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAProspectPictureVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNARoomVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 方案详情引导页数据
 */
@Data
@ApiModel("方案详情引导页新")
@Accessors(chain = true)
public class ProgramDetailsGuideNewResponse  extends DNAInfoResponseCommonVo {

    @ApiModelProperty("客户姓名")
    private String name;
    private String designIdea;
    private List<DNAProspectPictureVo> prospectPictureList;
    private List<DNARoomVo> dnaRoomList;

    @ApiModelProperty("材质示意图")
    private String materialDiagramUrl;

}
