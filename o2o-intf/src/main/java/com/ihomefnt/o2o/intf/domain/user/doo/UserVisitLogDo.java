package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.Data;

@Data
public class UserVisitLogDo {

    private Long visitLogId;    //日志记录ID
    private String mobile;  //手机号码
    private Integer visitType;  //用户访问类型 0 打开APP  1 访问首页主模块   2 访问套装、空间、单品详情3 访问搜索页4 搜索内容5 在商品详情的页面点击 到店有礼6 阅读灵感  7立即预定, 8 免费预约设计  9 H5全品家
    private String action;  //用户操作动作
    private Integer targetType; //访问的目标类型  0:未确定 1：客厅 2：主卧 3：次卧 4：儿童房 5：书房  6：餐厅 7：玄关 8：厨房 9：老人房  17:单品 18: 套装  19: 更多套装   20 案例  21 攻略  22美图   30:H5全品家列表 31:H5全品家详情
    private Long targetId;  //操作目标的ID 如：商品ID、美图ID  1:舒适家1314 2:品质家1912 3:尊贵家2399 4:奢享家2999
    private String searchWord;  //搜索词
    private String deviceToken; //访问设备信息
    
    public UserVisitLogDo(String deviceToken, String mobile, Integer visitType, String action) {
        this.deviceToken = deviceToken;
        this.mobile = mobile;
        this.visitType = visitType;
        this.action = action;
    }
    
    public UserVisitLogDo(String deviceToken, String mobile, Integer visitType, String action, String searchWord) {
        this.deviceToken = deviceToken;
        this.mobile = mobile;
        this.visitType = visitType;
        this.action = action;
        this.searchWord = searchWord;
    }
    
    public UserVisitLogDo(String deviceToken, String mobile, Integer visitType, String action, Integer targetType, Long targetId) {
        this.deviceToken = deviceToken;
        this.mobile = mobile;
        this.visitType = visitType;
        this.action = action;
        this.targetType = targetType;
        this.targetId = targetId;
    }
}
