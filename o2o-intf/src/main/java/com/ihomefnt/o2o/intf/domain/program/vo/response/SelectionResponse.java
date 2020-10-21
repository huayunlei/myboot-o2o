package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/19
 */

@ApiModel("空间设计信息返回")
@Data
public class SelectionResponse implements Serializable {

    @ApiModelProperty("户型图")
    private String headImage;

    @ApiModelProperty("空间列表")
    private List<SpaceDesign> spaceDesignList;

    @ApiModelProperty("空间图片集合")
    private List<RoomPictureDto> roomPictureDtoList;

}
