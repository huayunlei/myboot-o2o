package com.ihomefnt.o2o.intf.manager.constant.user;

import java.math.BigDecimal;

public interface UserConstants {

	int SEND_MESSAGE_FLAG = 1;//发送消息标识 1发送消息  0不发送（默认不发送）
    
    int CHARGE_AMOUNT=50;

    String AJB_REGISTER_CODE = "1"; //用户注册活动

    String AJB_COMPLETE_CODE = "2"; //完善资料活动

    String AJB_REGISTER_REMARK = "注册账号奖励艾积分"; //用户注册活动

    String AJB_COMPLETE_REMARK = "资料完善奖励艾积分"; //完善资料活动

    //申请艾佳贷是否验证身份证和姓名字段key
    String CREATE_LOAN_CHECK_USERNAME_AND_IDCARD = "CREATE_LOAN_CHECK_USERNAME_AND_IDCARD";

    BigDecimal CAN_LOAN_AMOUNT = new BigDecimal(300000);
    
}
