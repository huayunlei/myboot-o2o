package com.ihomefnt.o2o.intf.proxy.sales.vo.response;

/**
 * Created by hvk687 on 10/20/15.
 */
public class InviteResponse {
    public static final Integer SUCCESS = 1;
    public static final Integer BOUND = 2;
    public static final Integer FAILED = 3;

    public static final String ERR_BOUND = "该用户已经被添加过";
    public static final String ERR_MORE = "您已超过今日添加限额，请明天再试";
    public static final String ERR_UNREGISTER = "该手机未注册艾佳会员，请核对后再试";

    private Integer status; //1, 邀请成功; 2, 邀请失败;3,操作失败
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
