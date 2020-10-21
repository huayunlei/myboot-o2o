package com.ihomefnt.o2o.intf.domain.homepage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */

@ApiModel("用户评价")
public class UserComment implements Serializable{

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("加密的电话号码")
    private String mobileHide;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论日期")
    private String commentDate;

    @ApiModelProperty("评价星数")
    private Integer starNum;

    @ApiModelProperty("小区名称")
    private String projectName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileHide() {
        return mobileHide;
    }

    public void setMobileHide(String mobileHide) {
        this.mobileHide = mobileHide;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
