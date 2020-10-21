package com.ihomefnt.o2o.intf.domain.homepage.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.Banner;
import com.ihomefnt.oms.trade.util.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@Data
@ApiModel("了解我们数据返回")
public class AboutUsResponse {
	@ApiModelProperty("首页banner")
	private List<Banner> banner;
	
    @ApiModelProperty("省个数")
    private Integer provinceNum;

    @ApiModelProperty("楼盘个数")
    private Integer projectNum;

    @ApiModelProperty("用户数")
    private Integer userNum;

    @ApiModelProperty("评论列表")
    private PageModel commentList;
}
