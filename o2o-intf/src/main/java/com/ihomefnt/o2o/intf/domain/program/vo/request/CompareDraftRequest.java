package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-25 10:08
 */
@Data
@ApiModel("草稿比对入参")
public class CompareDraftRequest extends HttpBaseRequest {


    @ApiModelProperty("订单号")
    private Integer orderId;

    private List<DraftSimpleListBean> draftSimpleList;

    @Data
    public static class DraftSimpleListBean {

        @ApiModelProperty("草稿编号")
        private String draftProfileNum;

        @ApiModelProperty("方案id")
        private Integer solutionId;

        @ApiModelProperty("草稿状态 1已签约；2未签约；3历史签约 4.最新草稿")
        private Integer draftSignStatus = Constants.DRAFT_SIGN_STATUS_NO_SIGN;;
    }
}
