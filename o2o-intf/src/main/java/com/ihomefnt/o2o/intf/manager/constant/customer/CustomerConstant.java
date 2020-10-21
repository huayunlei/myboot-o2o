package com.ihomefnt.o2o.intf.manager.constant.customer;

public interface CustomerConstant {
	final long FAILURE = 0;//邀请失败
	final long SUCCESS = 1;//邀请成功
	final long INVITED = 2;//该用户已被邀请
	final long EXCEED = 3;//您每日最多邀请20位客户
	
	//返回前台的提示信息
	final String MESSAGE_PARAMETERS_DATA_EMPTY="传入参数为空";	 
	final String MESSAGE_PARAMETERS_DATA_LENGTH="传入参数长度不合法";		
	final String MESSAGE_RESULT_DATA_EMPTY="查询结果为空";
	final String MESSAGE_MOBILE_EMPTY="电话号码为空";
	final String MSG_FAILURE = "邀请失败";
	final String MSG_SUCCESS = "邀请成功";
	final String MSG_INVITED = "该用户已被邀请";
	final String MSG_EXCEED = "您每日最多邀请20位客户";
	final String MSG_SAME = "被邀请人与邀请人不能为同一人";
	
	//预约1、到访2、需求设计3、设计确认4、签约5、施工开始6、验收7
	final int STEP_APPOINT= 1;
	final int STEP_VISIT= 2;
	final int STEP_DEMAND= 3;
	final int STEP_DESIGN= 4;
	final int STEP_SIGN= 5;
	final int STEP_BUILD= 6;
	final int STEP_ACCEPT= 7;
	
	//客户类型 1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期
	final int TYPE_INVITED = 1;
	final int TYPE_ARRIVED = 2;
	final int TYPE_DEALING = 3;
	final int TYPE_COMMISSION_JUNCTION  = 4;
	final int TYPE_ARRIVED_OVERTIME  = 5;
	final int TYPE_DEAL_OVERTIME  = 6;
	
	
	
}
