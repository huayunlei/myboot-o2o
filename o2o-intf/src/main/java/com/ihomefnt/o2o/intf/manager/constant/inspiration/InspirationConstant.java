/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.constant.inspiration;

/**
 * @author zhang
 *
 */
public interface InspirationConstant {

	String RESULT_DATA_EMPTY = "查询结果为空";

	String MSG_DATA_LENGTH_ERROR = "传入参数字段长度不合法";

	String ANONYMOUS_PERSON = "一个热心网友";

	String RESULT_PRAISE_GOOD = "点赞成功";
	String RESULT_PRAISE_WRONG = "点赞失败";
	String RESULT_PRAISED = "已点赞";
	int NUM_PRAISE_GOOD = 0; // 0点赞成功1已点赞2点赞失败
	int NUM_PRAISED = 1;
	int NUM__PRAISE_WRONG = 2;

	String COMMENT_GOOD = "评论成功";
	String COMMENT__WRONG = "评论失败";
	
	String FORWARD_GOOD = "转发成功";
	String FORWARD__WRONG = "转发失败";
	
	String COLLECT_GOOD = "收藏成功";
	String COLLECT__WRONG = "收藏失败";
	//操作类型：阅读1,2点赞,3收藏,4转发
	int ACTION_READ=1;//1阅读
	int ACTION_PRAISE=2;//2点赞
	int ACTION_COLLECT=3;//3收藏
	int ACTION_FORWARD=4;//4转发
	
	int STATUS_PRAISE_YES =1;//已点赞
	int STATUS_PRAISE_NO =0;//未点赞
	
	String QUERY_DATA_GOOD= "查询结果成功";

}
