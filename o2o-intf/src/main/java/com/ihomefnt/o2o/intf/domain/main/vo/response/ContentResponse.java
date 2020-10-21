package com.ihomefnt.o2o.intf.domain.main.vo.response;

import com.ihomefnt.o2o.intf.domain.main.vo.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("首页页面内容")
@Data
@Accessors(chain = true)
public class ContentResponse {

    @ApiModelProperty("订单信息")
    private MasterOrderInfo masterOrderInfo;

    @ApiModelProperty("关于我们返回")
    private AboutUsInfo aboutUsInfo;

    @ApiModelProperty("选方案信息")
    private SolutionInfo solutionInfo;

    @ApiModelProperty("方案草稿信息")
    private SolutionDraftInfo solutionDraftInfo;

    @ApiModelProperty("专属方案信息")
    private SolutionEffectInfo exclusiveSolutionInfo;

    @ApiModelProperty("设计需求信息")
    private DesignDemandInfo designDemandInfo;

    @ApiModelProperty("贷款信息")
    private LoanInfo loanInfo;

    @ApiModelProperty("确认方案信息")
    private ConfirmSolutionInfo confirmSolutionInfo;

    @ApiModelProperty("交付信息")
    private DeliveryInfo deliveryInfo;

    @ApiModelProperty("验收点评信息")
    private CheckInfo checkInfo;

    @ApiModelProperty("维保信息")
    private MaintenanceInfo maintenanceInfo;

    @ApiModelProperty("首页文案提示信息")
    private MainPageTips mainPageTips;

    @ApiModelProperty("扩展数据")
    private MainPageExtDataResponse mainPageExtData;

    @ApiModelProperty("艾师傅是否已上传全品家验收单")
    private Boolean fastCheckApproval;

}
