package com.ihomefnt.o2o.intf.manager.constant.proxy;

/** 
* @ClassName: WcmWebServiceNameConstants 
* @Description: wcm-web服务名称常量池
* @author huayunlei
* @date Feb 14, 2019 1:40:16 PM 
*  
*/
public interface WcmWebServiceNameConstants {
	
	/**
	 * 根据艾积分活动代码查询活动信息
	 */
	String QUERY_AJB_ACTIVITY_BY_CODE = "wcm-web.ajb.queryAjbActivityByCode";
	
	/**
	 * 新增充值记录
	 */
	String ADD_AJB_RECORD = "wcm-web.ajb.addAjbRecord";

	/**
	 * 根据用户ID和活动代码查询充值记录
	 */
	String QUERY_RECORD_BY_CODE_AND_USER_ID = "wcm-web.ajb.queryRecordByCodeAndUserId";
	
	/**
	 * 添加日志记录
	 */
	String ADD_LOG_FOR_APP = "wcm-web.log.addLogForApp";
	
	/**
	 * 查询日志记录
	 */
	String QUERY_VISIT_LOG_BY_CONDITION = "wcm-web.log.queryVisitLogByCondition";

	/**
	 * 查询监控配置
	 */
	String WCM_QUERY_MONITOR_CONFIG = "wcm-web.dingtalk.queryMonitorConfigList";

	/**
	 * 根据父key值查询字典集合
	 */
	String GET_DIC_LIST_BY_PID_KEY = "wcm-web.dic.getDicListByPidKey";
	
	/**
	 * 根据key值查询字典信息
	 */
	String QUERY_DIC_BY_KEY = "wcm-web.dic.queryDicByKey";
	
	String CHECK_BUNDLE_FOR_APP = "wcm-web.bundle.checkBundleForApp";
	
	String GET_LATEST_BUNDLE_BY_VERSION = "wcm-web.bundle.getLatestBundleByVersion";
	
	String VERSION_CHECK_BUNDLE_VERSION = "wcm-web.version.checkBundleVersion";
	
	String VERSION_ADD_DOWNLOAD = "wcm-web.version.addDownload";
	
	String VERSION_UPDATE_LOCATION = "wcm-web.version.updateLocation";
	
	String ADVERT_QUERY_START_PAGE_LIST = "wcm-web.advert.queryStartPageList";
	
	
	String SERVER_URL_QUERY_ACTIVITY="wcm-web.groupBuy.queryActivityInfo";
	
	String SERVER_URL_QUERY_ACTIVITY_PRODUCT="wcm-web.groupBuy.queryActivityProductInfo";
	
	String SERVER_URL_COLLAGE_RECORD="wcm-web.groupBuy.queryJoinRecordByOpenid";
	
	String SERVER_URL_COLLAGE="wcm-web.groupBuy.queryGroupInfo";
	
	String SERVER_URL_CREATE_COLLAGE_ORDER="wcm-web.groupBuy.createGroupArtOrder";
	
	String LECHANGE_QUERY_ACCESS_TOKEN = "wcm-web.lechange.queryAccessToken";
	
	String QUERY_POPUP_RULE_BY_PARAMS = "wcm-web.popupRule.queryPopupRuleByParams";
	
	String LIFE_GET_PRAISE_COUNT = "wcm-web.life.getPraiseCount";
	
	String LIFE_ADD_LIFE_PRAISE = "wcm-web.life.addLifePraise";
	
	String LIFE_ADD_PRAISE = "wcm-web.life.addPraise";

	String LIFE_ADD_BROWSE = "wcm-web.life.addBrowse";
	
	String LIFE_ADD_FORWARD = "wcm-web.life.addForward";

	String LIFE_GET_ARTICLE_BY_ID = "wcm-web.life.getArticleById";

	String LIFE_GET_LIFE_PRAISE_LIST_BY_USER_ID = "wcm-web.life.getLifePraiseListByUserId";

	String LIFE_GET_CATEGORY_BY_ID = "wcm-web.life.getCategoryById";

	String LIFE_GET_ARTICLE_LIST = "wcm-web.life.getArticleList";

	String LIFE_GET_LIFE_HOME_PAGE = "wcm-web.life.getLifeHomePage";

	String LIFE_GET_LIFE_COMMENT_LIST = "wcm-web.lifeComment.getLifeCommentList";
	
	String LIFE_PUBLISH_COMMENT = "wcm-web.lifeComment.publishComment";

	String LIFE_TO_PRAISED = "wcm-web.lifeComment.toPraised";

	String LIFE_DELETE_COMMENT = "wcm-web.lifeComment.deleteComment";
	
	String LIFE_ARTICLE_SIMPLE_INFO = "wcm-web.lifeComment.articleSimpleInfo";

	String LIFE_GET_COMMENT_RELIST_BY_COMMENT_IDS = "wcm-web.lifeComment.getCommentReListByCommentIds";
	
	
	String QUERY_LIST_BY_FRAME_ID = "wcm-web.artcategory.queryListByFrameId";
	
	String QUERY_LIST_BY_FRAME_ID_LIST = "wcm-web.artcategory.queryListByFrameIdList";

	String QUERY_ALL_FRAME_LIST = "wcm-web.artcategory.queryAllFrameList";

	String QUERY_SUBJECT_LIST = "wcm-web.artsubject.querySubjectList";
	
	String QUERY_SUBJECT_BY_ID = "wcm-web.artsubject.querySubjectById";

	String QUERY_WECHAT_USER_INFO = "wcm-web.wechat.queryWeChatUserInfo";

	String GET_TRANSFER_DTO_LIST = "wcm-web.transfer.getTransferDtoList";

	String INSERT_SHARE_ORDER = "wcm-web.shareorder.insertShareOrder";

	String GET_SHARE_ORDER_LIST = "wcm-web.shareorder.getShareOrderList";

	String GET_SHARE_ORDER_LIST_BY_COMMON_USER = "wcm-web.shareorder.getShareOrderListByCommonUser";

	String GET_SHARE_ORDER_BY_ID = "wcm-web.shareorder.getShareOrderById";

	String UPDATE_SHARE_ORDER = "wcm-web.shareorder.updateShareOrder";

	String SHARE_ORDER_INC = "wcm-web.shareorder.inc";

	String GET_SHARE_ORDER_COUNT_BY_USER_ID = "wcm-web.shareorder.getShareOrderCountByUserId";

	String GET_SHARE_ORDER_PRAISE_LIST_BY_USER_ID = "wcm-web.shareorder.getShareOrderPraiseListByUserId";

	String INSERT_SHARE_ORDER_PRAISE = "wcm-web.shareorder.insertShareOrderPraise";

	String GET_PRAISE_COUNT = "wcm-web.shareorder.getPraiseCount";

	String INSERT_SHARE_ORDER_COMMENT = "wcm-web.shareorder.insertShareOrderComment";

	String GET_SHARE_ORDER_COMMENT_LIST = "wcm-web.shareorder.getShareOrderCommentList";

	String GET_SHARE_ORDER_COMMENT_LIST_BY_COMMON_USER = "wcm-web.shareorder.getShareOrderCommentListByCommonUser";

	String DELETE_SHARE_ORDER_BY_ID = "wcm-web.shareorder.deleteShareOrderById";

	String QUERY_SHARE_ORDER_COMMENT_BY_ID = "wcm-web.shareorder.queryShareOrderCommentById";

	String QUERY_ALL_COMMENT_LIST = "wcm-web.shareorder.queryAllCommentList";

	String UPDATE_COMMENT_BY_ID = "wcm-web.shareorder.updateCommentById";

	String QUERY_OFFICIAL_COUNT_BY_SHARE_ORDER_ID = "wcm-web.shareorder.queryOfficialCountByShareOrderId";

	String QUERY_ALL_SHARE_ORDER_LIST = "wcm-web.shareorder.queryAllShareOrderList";

	String UPDATE_SHARE_ORDER_BY_ID = "wcm-web.shareorder.updateShareOrderById";

	String GET_SHARE_ORDER_LIST_BY_IDS = "wcm-web.shareorder.getShareOrderListByIds";


	String PRAISE_BUILDING_TOPIC_FOR_APP = "wcm-web.buildingtopic.praiseBuildingtopicForApp";

	String VIEW_BUILDING_TOPIC_FOR_APP = "wcm-web.buildingtopic.viewBuildingtopicForApp";

	String FORWARD_FOR_APP = "wcm-web.buildingtopic.forwardForApp";

	String CREATE_COMMENT_FOR_APP = "wcm-web.buildingtopicComment.createCommentForApp";

	String CREATE_ARTICLE_FOR_APP = "wcm-web.buildingtopicArticle.createArticleForApp";

	String QUERY_COMMENT_LIST_FOR_APP = "wcm-web.buildingtopicComment.queryCommentListForApp";


	String MEETING_FAMILY_SAVE_MEMBER = "wcm-web.meetingFamily.saveMember";

	String MEETING_FAMILY_UPDATE_MEMBER = "wcm-web.meetingFamily.updateMember";


	String MEETING_PIC_SEARCH_HOUSE_MANAGER = "wcm-web.meetingPic.searchHouseManager";

	String MEETING_PIC_PUBLISH_PIC = "wcm-web.meetingPic.publishPic";

	String MEETING_PIC_SEARCH_PIC_WALL = "wcm-web.meetingPic.searchPicWall";

	String MEETING_PIC_SEARCH_FAMILY_GROUP = "wcm-web.meetingPic.searchFamilyGroup";

	String MEETING_PIC_UPLOAD_IMAGE = "wcm-web.meetingPic.uploadImage";

	String MEETING_PIC_UPLOAD_IMAGE_BASE64 = "wcm-web.meetingPic.uploadImageBase64";


	String MEETING_MSG_PUBLISH_MSG = "wcm-web.meetingMsg.publishMsg";

	String MEETING_MSG_QUERY_MSG = "wcm-web.meetingMsg.queryMsg";


	String HOME_LETTER_PUBLISH_ARTICLE = "wcm-web.homeLetter.publishArticle";

	String HOME_LETTER_VOTE = "wcm-web.homeLetter.homeLetterVote";

	String HOME_LETTER_QUERY_VOTE_RECORD_LIST = "wcm-web.homeLetter.queryVoteRecordList";

	String HOME_LETTER_QUERY_ARTICLE_INFO = "wcm-web.homeLetter.queryArticleInfo";

	String HOME_LETTER_QUERY_ARTICLE_LIST = "wcm-web.homeLetter.queryArticleList";

	String HOME_LETTER_QUERY_WINNING_RESULT = "wcm-web.homeLetter.queryWinningResult";


	String GET_KEYWORD_LIST = "wcm-web.keyword.getKeywordList";

	String ADD_DING_TALK_RECORD = "wcm-web.dingtalk.addDingTalkRecord";

	String UPDATE_DESIGN_DEMAND_STATUS = "wcm-web.designDemand.updateDesignDemandStatus";

	/**
	 * 艾商城相关
	 */
	String QUERY_FRONT_CATEGORY_LIST ="wcm-web.ihomeMall.queryFrontCategoryList";

	String QUERY_ARTIST_RECOMMEND_LIST ="wcm-web.ihomeMall.queryArtistRecommendList";


	String ADD_VISIT_RECORD = "wcm-web.ihomeMall.addVisitRecord";

	String QUERY_VISIT_RECORD_BY_PRODUCTID = "wcm-web.ihomeMall.queryVisitRecordByProductId";


	String MARK_USER_SOLUTION_READ_RECORD = "wcm-web.program.markUserSolutionReadRecord";

	String QUERY_USER_SOLUTION_READ_LIST_BY_USERID = "wcm-web.program.queryUserSolutionReadListByUserId";

	String APP_PUSH = "wcm-web.push.appPush";
	/**
	 * 装修报价及预约全品家
	 */
	String QUERY_QUOTE_PRICE_COUNT = "wcm-web.decorationQuotation.queryQuotePriceCount";

	String ADD_QUOTE_PRICE_COUNT = "wcm-web.decorationQuotation.addQuotePriceCount";

	String ADD_DNA_BROWSE_RECORD = "wcm-web.decorationQuotation.addDnaBrowseRecord";

	String ADD_QUOTE_PRICE_RECORD = "wcm-web.decorationQuotation.addQuotePriceRecord";

	/**
	 * 装修历程
	 */
	String QUERY_DECORATION_PROCESS = "wcm-web.decorationQuotation.queryDecorationProcess";

	/**
	 * 查询问卷信息
	 */
	String QUERY_INVESTIGATE_INFO = "wcm-web.investigate.queryInvestigateInfo";

	/**
	 * 提交问卷
	 */
	String COMMIT_INVESTIGATE = "wcm-web.investigate.commitInvestigate";

}
