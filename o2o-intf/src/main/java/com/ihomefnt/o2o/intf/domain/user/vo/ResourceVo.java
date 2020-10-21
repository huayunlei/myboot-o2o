package com.ihomefnt.o2o.intf.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-02-21 16:14
 */
@ApiModel("资源信息")
@Data
public class ResourceVo {

    @ApiModelProperty("资源id")
    private Integer id;

    @ApiModelProperty("资源图片")
    private String icon;

    @ApiModelProperty("跳转链接")
    private String openUrl;

    @ApiModelProperty("文案")
    private String copyWriter;

    @ApiModelProperty("资源类型 1: banner,2:icon,3:目录")
    private Integer type;

    @ApiModelProperty("子节点")
    private List<ResourceVo> clientNodes;

    //类目
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String category;

    //排序
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer sort;

    @ApiModelProperty("key")
    private String key;
    //role
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String role;

    //角标样式
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String cornerMarkStyle;

    //角标文字
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String cornerMark;

    //是否必须登陆
    @ApiModelProperty("资源是否必须登陆,1:不登陆不显示,2不登陆也可以正常查看,3:不登陆显示,点击跳登陆页")
    private Integer mustLogin;

    /**
     * 显示版本,所有版本显示:ALL,全部不显示:NONE,与hide_versions同时生效,
     * 如果要指定隐藏指定版本就将此字段设置为全部显示,hide_versions设置指定版本号即可,
     * 如果设置发生冲突,则后果自负,如果指定版本号多个用逗号分隔例如5.1.2,5.1.3
     * hide_versions和当前字段必须一个设置为ALL,只能单独设置显示的版本或者隐藏的版本
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String showVersions;

    /**
     * 隐藏版本,所有版本隐藏:ALL,全部不隐藏:NONE,与show_versions同时生效,
     * 如果要指定显示版本就将此字段设置为全部不隐藏,show_versions设置指定版本号即可,
     * 如果设置发生冲突,作为后果自负,如果指定版本号多个用逗号分隔例如5.1.2,5.1.3,
     * show_versions和当前字段必须一个设置为ALL,只能单独设置显示的版本或者隐藏的版本
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String hideVersions;

    @ApiModelProperty("权益特殊标记 1 普通类型 2 跳权益宣传页")//过渡使用
    @Deprecated
    private Integer specialRightFlag = 1;

    @ApiModelProperty("当前用户名下只有一个权益时有值，4跳转新版本我的权益")
    private Integer onlyRightsVersion;



}
