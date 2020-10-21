package com.ihomefnt.o2o.intf.domain.main.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.Banner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */
@ApiModel("首页接口返回")
@Data
@Accessors(chain = true)
public class MainPageResponse {

    @ApiModelProperty("节点信息")
    private MainNodeResponse mainNodeResponse;

    @ApiModelProperty("首页banner")
    private List<Banner> banner;

    @ApiModelProperty("节点内容")
    private ContentResponse contentResponse;

}
