package com.ihomefnt.o2o.intf.manager.constant.home;

import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0 首页文案说明
 * @author ZHAO
 */
public interface HomeCardPraise {
	//产品版块筛选条件（空间）
	List<String> ROOM_TYPE_LIST = new ArrayList<String>(){
		{
			add("全部");
			add("客厅");
			add("餐厅");
			add("主卧");
			add("次卧");
			add("儿童房");
			add("书房");
			add("老人房");
		}
	};
	
	Integer SPECIFIC_PROGRAM_NUM = 4;//用户特定方案数量

	Integer CARD_DNA_CODE = 1;//DNA卡片
	
	Integer CARD_SUIT_CODE = 2;//套装卡片
	
	Integer CARD_BANNER_CODE = 3;//banner卡片
	
	Integer CARD_ART_CODE = 4;//艺术品卡片
	
	Integer CARD_VIDEO_CODE = 5;//视频卡片

	Integer CARD_PROGRAM_CODE = 6;//特定用户方案卡片

	Integer CARD_GREETING_CODE = 7;//1219定制贺卡卡片
	
	Integer CARD_MAINTAIN = 8;//维修

	String DIC_KEY_TYPE_DNA = "TYPE_DNA"; // DNA

	String DIC_KEY_TYPE_SUIT = "TYPE_SUIT"; // 样板间套餐

	String DIC_KEY_TYPE_BANNER = "TYPE_BANNER"; // banner

	String DIC_KEY_TYPE_ART = "TYPE_ART"; // 艺术品专题

	String DIC_KEY_TYPE_VIDEO = "TYPE_VIDEO"; // 视频
	
	String DIC_KEY_TYPE_GREETING_CARD = "TYPE_GREETING_CARD"; // 1219定制贺卡

	String ALL_GOODS = "硬装+软装=全品家";

	String HARD_SOFT = "硬装+软装";

	String HARD_DETAIL = "硬装清单";
	
	String SOFT_DETAIL = "软装清单";

	String HARD_SKIP_URL_1 = "&seriesId=";//硬装清单跳转地址

	String SERIES_NAME = "套系";
	
	String STYLE_NAME = "风格";
	
	String ROOM_NAME = "空间";
	
	String ROOM_MARK_NAME = "空间标识";

	String ARTISTIC_NAME = "意境";
	
	String INCLUDE_NAME = "包含";

	String COMMENT_NAME = "他们说";
	
	Integer DNA_FAVORITE_NO = 0;//未点赞
	
	Integer DNA_FAVORITE_YES = 1;//点赞
	
	String DNA_COMMENT_LIMIT = "COMMENT_LIMIT";//dna评论权限

	Integer DNA_COMMENT_NOLOGIN = 0;//不需登录评论
	
	Integer DNA_COMMENT_LOGIN = 1;//需要登录评论
	
	Integer COMMENT_TYPE_DNA = 1; //DNA评论
	
	Integer COMMENT_TYPE_ART = 2;//艺术品评论
	
	Integer COMMENT_TYPE_SHARE = 3;//晒家评论
	
	String COMMENT_SUCCESS_PRAISE = "COMMENT_PRAISE";//评论成功文案

	String FAVORITE_SUCCESS_PRAISE = "FAVORITE_PRAISE";//点赞成功文案

	String ANONYMOUS_USER = "匿名用户";//匿名用户
	
	String SHARE_URL = "https://m.ihomefnt.com/dna/detail/";//DNA分享页  线上地址

	//String SHARE_URL = "http://m.sst.ihomefnt.org/dna/detail/";//DNA分享页  SST地址
	
	String DNA_SHARE_SUBTITLE = "来自艾佳生活的全品家套装";
	
	Integer COMMENT_REPLY_ORTHER = 0; //没有官方回复
	
	Integer COMMENT_REPLY_OFFICIAL_FRONT = 1; //官方回复  回复人
	
	Integer COMMENT_REPLY_OFFICIAL_BEHIND = 2;//官方回复  被回复人

	Integer COMMENT_REPLY_OFFICIAL_ALL = 3;//官方回复  全部

	String HOUSE_AREA = "㎡";

	String COMMENT_ADMIN = "COMMENT_ADMIN";//官方回复昵称

	String COMMENT_ADMIN_MOBILE = "wcmSysAdmin";//官方回复手机号码
	
	String NOTICE_NEW_TITLE="艾佳生活";
	
	String NOTICE_CONTENT="[A]评论了您的晒家，TA说：[B]。快去看看吧！";
	
	String NOTICE_TO_URL="native://?shareOrderId=";
	
	String HARD_STANDARD = "HARD_STANDARD";//硬装标准
	
	String SHARE_ORDER_MOBILE = "SHARE_ORDER_MOBILE";//特殊用户号码池

	String HARD_DESC = "硬装";//硬装
	
	String BRAND_MATERIAL = "品牌/材质";//品牌/材质
	
	String HOUSE_TYPE = "户型";

	String HOUSE_TYPE_ORTHER = "其他户型";
	
	Integer VISIT_DNA = 1;//浏览类型  DNA 
	
	Integer VISIT_PROGRAM = 2;//浏览类型  产品方案
	
	Integer FORWARD_DNA = 3;//转发 DNA

	Integer HOUSE_EDIT = 4;//编辑房产次数

	 //1:审批通过, 2:审批不通过
	Integer APPROVE_OK=1;
	
	Integer APPROVE_FAILED=2;

	String FORBIDDEN_NICKNAME = "FORBIDDEN_NICKNAME"; //昵称禁用关键字
	
	String PROGRAM_FLOW_DESC = "PROGRAM_FLOW_DESC";//全品家订单流程说明
	
	String DNA_PRICE_PRAISE = "以100㎡二居室为标准估算";//DNA参考价文案
	
	String STYLE_SERIES = "花好月圆";
	
	String AGENT_RULE = "经纪人规则";//经纪人规则
	
	String WATERMARK_DNA = "WATERMARK_DNA";//DNA水印图片地址

	String WATERMARK_DNA_BIG = "WATERMARK_DNA_BIG";//DNA水印图片地址  大图
	
	Integer WIDTH_BIG = 750;
	
	//查询设计师
	Integer  ARTIST_TAG =1;
	
	String HOME_LETTER_LIMIT = "HOME_LETTER_LIMIT";//1219活动三行家书权限

	Integer HOME_LETTER_ACTIVITY = 1;//1219活动三行家书活动有效

	String LINK_DESC ="售后保障";
	
	Integer HOUSE_EDIT_COUNT = 4;//房产可编辑次数
	
	Integer HOUSE_ADD_COUNT = 4;//房产可新增次数
	
}
