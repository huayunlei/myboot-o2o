package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2018/12/4 0004.
 */
@ApiModel("画集信息")
public class SelectedScreenSimpleResponse {

    @ApiModelProperty("画集ID")
    private Integer groupId;

    @ApiModelProperty("封面图片")
    private String groupImage;

    @ApiModelProperty("画集分类ID")
    private String groupCategory;

    @ApiModelProperty("画集分类")
    private String groupCategoryStr;

    @ApiModelProperty("画集显示标签 分类名称/标签组合")
    private String groupTagView;

    @ApiModelProperty("画集标签")
    private String groupTag;

    @ApiModelProperty("画集作者")
    private String groupAuthor;

    @ApiModelProperty("画集作者")
    private String groupAuthorStr;

    @ApiModelProperty("标题")
    private String groupName;

    @ApiModelProperty("浏览次数")
    private Integer browseNum;

    @ApiModelProperty("简介")
    private String groupIntro;

    @ApiModelProperty("画集详解")
    private String groupDescription;

    @ApiModelProperty("画作数量总数")//缺失
    private Integer artTotal;

    public String getGroupCategoryStr() {
        return groupCategoryStr;
    }

    public void setGroupCategoryStr(String groupCategoryStr) {
        this.groupCategoryStr = groupCategoryStr;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupCategory() {
        return groupCategory;
    }

    public void setGroupCategory(String groupCategory) {
        this.groupCategory = groupCategory;
    }

    public String getGroupAuthor() {
        return groupAuthor;
    }

    public void setGroupAuthor(String groupAuthor) {
        this.groupAuthor = groupAuthor;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getArtTotal() {
        return artTotal;
    }

    public void setArtTotal(Integer artTotal) {
        this.artTotal = artTotal;
    }

    public String getGroupTagView() {
        return groupTagView;
    }

    public void setGroupTagView(String groupTagView) {
        this.groupTagView = groupTagView;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }


    public String getGroupAuthorStr() {
        return groupAuthorStr;
    }

    public void setGroupAuthorStr(String groupAuthorStr) {
        this.groupAuthorStr = groupAuthorStr;
    }
}
