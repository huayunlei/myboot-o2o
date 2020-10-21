package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**空间图片
 * @author liyonggang
 * @create 2019-04-22 18:23
 */
@Data
@Accessors(chain = true)
public class RoomImageDto {

    private Integer taskId;//dr任务id

    private List<String> pictureList;//图片集合

    @ApiModelProperty("老图片")
    private List<String> oldPictureList;

    @ApiModelProperty("空间任务状态,供app用户查看   0，无任务 1：未开始，2：进行中，3：已完成，4：失败;")
    private Integer taskStatus;

    @ApiModelProperty("空间图片任务类型,0：不存在任务，1：正常渲染任务，2：失败渲染任务")
    private Integer type = 0;

    @ApiModelProperty("图片本次是否操作过，(可视化替换) 0：没做修改，1：修改过")
    private Integer imageHasUpdate = 0;

    private Integer referenceOnlyFlag = Constants.REFERENCE_ONLY_NO_SHOW;//仅供参考提示 0 不提示 1提示
}
