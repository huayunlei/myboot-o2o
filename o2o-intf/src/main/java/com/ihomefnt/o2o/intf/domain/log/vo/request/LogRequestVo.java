package com.ihomefnt.o2o.intf.domain.log.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("记录操作日志请求参数")
public class LogRequestVo extends HttpBaseRequest{

	@ApiModelProperty("日志类型(0其他 1首页推荐板块 2艾商城 3晒家 4我的设置 5广告页下载 6广告页使用 "
			+ "7广告页点击 8三行家书活动页 9三行家书个人主页 10定制贺卡 11魔镜每日一句点赞12跨年管家首页13跨年管家本地攻略"
			+ "14跨年管家留言墙15跨年管家照片墙16跨年管家专属行程17跨年管家最美笑脸)")
    private Integer visitType;

	@ApiModelProperty("业务Code")
    private String businessCode;

	@ApiModelProperty("业务commonValue")
    private String commonValue;
}
