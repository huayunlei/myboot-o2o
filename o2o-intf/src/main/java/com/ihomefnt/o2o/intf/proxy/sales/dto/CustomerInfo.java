package com.ihomefnt.o2o.intf.proxy.sales.dto;

import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by hvk687 on 10/20/15.
 */
public class CustomerInfo {
    private String avatar = StaticResourceConstants.USER_AVATAR;
    private String mobile;
    private String date;
    private Integer status;// 1, invited; 2,bound; 3,invalid
    private String desc;

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Timestamp createDate;
    @JsonIgnore
    private Timestamp deadline;

    public String getDate() {
        date = new Date(createDate.getTime()).toString();
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getDesc() {
        return (status == 1 ? ("还有" + ((deadline.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24)) + "天失效") : "");
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
