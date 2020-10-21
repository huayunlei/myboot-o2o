package com.ihomefnt.o2o.intf.domain.vote.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-05-09 11:27
 */
@Data
@ApiModel("获奖名单")
public class WinnerListResponse {

    @ApiModelProperty("活动介绍")
    private String introduce;

    @ApiModelProperty("活动规则")
    private String rule;

    @ApiModelProperty("是否开奖")
    private Boolean openLottery = false;

    @ApiModelProperty("提示文案")
    private String reminder;

    @ApiModelProperty("奖项列表")
    private List<WinnerList> list;

    @Data
    public static class WinnerList {

        @ApiModelProperty("奖项")
        private String awards;

        @ApiModelProperty("获奖列表")
        private List<WinnerUserInfo> list;

        @Data
        public static class WinnerUserInfo {

            @ApiModelProperty("手机号")
            private String mobile;

            @ApiModelProperty("用户姓名")
            private String name;

            @ApiModelProperty("楼盘名称")
            private String houseName;

            public String getMobile() {
                return StringUtils.isNotBlank(mobile) ? mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2") : "";
            }
        }
    }

}
