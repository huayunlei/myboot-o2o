package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;

public class RigthtsItemBaseInfo {


    @ApiModelProperty("项id")
    private Long itemId;
    @ApiModelProperty("项编号")
    private Integer itemNo;
    @ApiModelProperty("项名称")
    private String itemName;
    @ApiModelProperty("别名")
    private String itemPointName;
    @ApiModelProperty("额度")
    private String itemRewardQuota;
    @ApiModelProperty("单位")
    private Integer itemRewardUnit;
    private String itemRewardUnitStr;
    @ApiModelProperty("奖励方式")
    private Integer itemRewardWay;
    private String itemRewardWayStr;
    @ApiModelProperty("url")
    private String url;
    @ApiModelProperty("urlNew")
    private String urlNew;
    @ApiModelProperty("使用说明")
    private String consumeDesc;
    @ApiModelProperty("描述")
    private String itemContent;
    @ApiModelProperty("等级")
    private Integer gradeId;
    @ApiModelProperty("等级名称")
    private String gradeName;
    @ApiModelProperty("权益项长副标题")
    private String itemSimpleTitle;
    @ApiModelProperty("分类编号")
    private Integer classifyNo;

    @ApiModelProperty("副标题前台文案")
    private String itemSimpleTitleCopywriting;
    @ApiModelProperty("浮层标题")
    private String FlotItemName;

    @ApiModelProperty("情义无价副标题")
    private String subtitle;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getItemSimpleTitleCopywriting() {
        return itemSimpleTitleCopywriting;
    }

    public void setItemSimpleTitleCopywriting(String itemSimpleTitleCopywriting) {
        this.itemSimpleTitleCopywriting = itemSimpleTitleCopywriting;
    }

    public String getUrlNew() {
        return urlNew;
    }

    public void setUrlNew(String urlNew) {
        this.urlNew = urlNew;
    }

    public String getFlotItemName() {
        return FlotItemName;
    }

    public void setFlotItemName(String flotItemName) {
        FlotItemName = flotItemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPointName() {
        return itemPointName;
    }

    public void setItemPointName(String itemPointName) {
        this.itemPointName = itemPointName;
    }

    public String getItemRewardQuota() {
        return itemRewardQuota;
    }

    public void setItemRewardQuota(String itemRewardQuota) {
        this.itemRewardQuota = itemRewardQuota;
    }

    public Integer getItemRewardUnit() {
        return itemRewardUnit;
    }

    public void setItemRewardUnit(Integer itemRewardUnit) {
        this.itemRewardUnit = itemRewardUnit;
    }

    public String getItemRewardUnitStr() {
        return itemRewardUnitStr;
    }

    public void setItemRewardUnitStr(String itemRewardUnitStr) {
        this.itemRewardUnitStr = itemRewardUnitStr;
    }

    public Integer getItemRewardWay() {
        return itemRewardWay;
    }

    public void setItemRewardWay(Integer itemRewardWay) {
        this.itemRewardWay = itemRewardWay;
    }

    public String getItemRewardWayStr() {
        return itemRewardWayStr;
    }

    public void setItemRewardWayStr(String itemRewardWayStr) {
        this.itemRewardWayStr = itemRewardWayStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConsumeDesc() {
        return consumeDesc;
    }

    public void setConsumeDesc(String consumeDesc) {
        this.consumeDesc = consumeDesc;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getItemSimpleTitle() {
        return itemSimpleTitle;
    }

    public void setItemSimpleTitle(String itemSimpleTitle) {
        this.itemSimpleTitle = itemSimpleTitle;
    }

    public Integer getClassifyNo() {
        return classifyNo;
    }

    public void setClassifyNo(Integer classifyNo) {
        this.classifyNo = classifyNo;
    }
}
