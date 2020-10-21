package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分享贺卡
 * @author ZHAO
 */
@Data
@ApiModel("分享贺卡请求参数")
public class ShareGreetingCardRequest extends HttpBaseRequest{

	@ApiModelProperty("图片ID")
    private Integer picId;

	@ApiModelProperty("祝福语")
    private String blessingWords;

	@ApiModelProperty("署名")
    private String signature;

	@ApiModelProperty("记录类型: 1保存图片到本地 2微信转发 3分享朋友圈")
    private Integer recordType;

	@ApiModelProperty("分享结果:0 成功  1 失败")
    private Integer shareResult;
}
