package com.ihomefnt.o2o.intf.domain.user.doo;


import lombok.Data;

/**
 * Created by shirely_geng on 15-1-14.
 */
@Data
public class LogDo {
    private Long lId;
    private Long uId;
    private String accessToken;
    private String refreshToken;
    private Long expire;
    private Long logTime;

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }
}
