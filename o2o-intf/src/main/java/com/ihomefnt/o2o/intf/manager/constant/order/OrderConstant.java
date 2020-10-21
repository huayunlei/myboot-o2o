package com.ihomefnt.o2o.intf.manager.constant.order;
import com.ihomefnt.oms.trade.order.enums.OrderState;
/**
 * Created by shirely_geng on 15-2-1.
 */
public interface OrderConstant {
    
//	long CREATE_SUCCESS = 0X00;//提交订单成功
//    long ORDER_PROCESSING = 0X01;//处理中
//    long ORDER_COMPLETE = 0X02;//已完成
//    long ORDER_CANCELED = 0X03;//已取消
//    long ORDER_WAITING_PAY = 0X04;//待付款
//    long ORDER_WAITING_RECEIPT = 0X05;//待收货
//    long ORDER_PART_RAY = 0X06;//部分付款

    long ORDER_TYPE_SINGLE = 0X00;
    long ORDER_TYPE_COMPOSITE = 0X01;
    long ORDER_TYPE_COMPOSITE_PART = 0X02;

    Integer SOURCE_IPHONE = 1;//iPhone客户端 客户自主下单
    Integer SOURCE_ANDROID = 2;//Android客户端 客户自主下单
    Integer SOURCE_H5 = 3;//H5网站 客户自主下单
    Integer SOURCE_PC = 4;//PC网站
    Integer SOURCE_CSH = 5;//客服&销售电话下单
    Integer SOURCE_RECEPTION = 6;//客服&销售现场下单 BOSS 
    Integer SOURCE_RECEPTION_IHOME = 7;//客服&销售APP下单iPhone
    Integer SOURCE_RECEPTION_ANDROID = 8;//客服&销售APP下单Android
    String SOURCE_IPHONE_STR = "iPhone客户端";//iPhone客户端 客户自主下单
    String SOURCE_ANDROID_STR = "Android客户端";//Android客户端 客户自主下单
    String SOURCE_H5_STR = "Android客户端";//H5网站 客户自主下单
    String SOURCE_PC_STR = "PC网站";//PC网站
    String SOURCE_CSH_STR = "客服电话下单";//客服&销售电话下单
    String SOURCE_RECEPTION_STR = "客服BOSS代客下单";//客服&销售现场下单 BOSS 
    String SOURCE_RECEPTION_IHOME_STR = "客服iPhone代客下单";//客服&销售APP下单iPhone
    String SOURCE_RECEPTION_ANDROID_STR = "客服Android代客下单";//客服&销售APP下单Android
    
//    Integer ORDER_STATUS_WAITING_PAY =0;
//    Integer ORDER_STATUS_WAITING_RECEIPT =1;
//    Integer ORDER_STATUS_CANCLE =3;
    
    /**
     * @see com.ihomefnt.oms.trade.order.enums.OrderState
     * CREATE("提交订单", 0), 
     * PROCESSING("处理中", 1), 
     * FINISH("已完成", 2), //
     * CANCEL("已取消", 3), 
     * NO_PAYMENT("待付款", 4), 
     * NO_RECEIVE("待收货", 5),//
     * PART_PAYMENT("部分付款", 6), //
     * NO_CONSTRUCT("待施工", 7), //
     * CONSTRUCTING("施工中", 8), //
     * NO_REFUND("待退款", 9), 
     * NO_SEND("待发货", 10), //
     * NO_FOR_PAYMENT("待结款", 11), 
     * CLOSED("交易关闭", 12); -- 已废弃
     * ("待交付",12),
     * ("接触阶段",13)
     * ("意向阶段",14)
     * ("定金阶段",15)
     * ("签约阶段",16)
     * ("交付中",17)
	 *
	 * 0 1 3 4 9 11 12  13
	 * 2 5 6 7 8 10 14 15 16 17
     */
	Integer ORDER_OMSSTATUS_CREATE = OrderStateEnum.CREATE.getCode();// 0
	Integer ORDER_OMSSTATUS_PROCESSING = OrderStateEnum.PROCESSING.getCode();// 1
	Integer ORDER_OMSSTATUS_FINISH = OrderStateEnum.FINISH.getCode();// 2
	Integer ORDER_OMSSTATUS_CANCEL = OrderStateEnum.CANCEL.getCode();// 3
	Integer ORDER_OMSSTATUS_NO_PAYMENT = OrderStateEnum.NO_PAYMENT.getCode();// 4
	Integer ORDER_OMSSTATUS_NO_RECEIVE = OrderStateEnum.NO_RECEIVE.getCode();// 5
	Integer ORDER_OMSSTATUS_PART_PAYMENT = OrderStateEnum.PART_PAYMENT.getCode();// 6
	Integer ORDER_OMSSTATUS_NO_CONSTRUCT = OrderStateEnum.NO_CONSTRUCT.getCode();// 7
	Integer ORDER_OMSSTATUS_CONSTRUCTING = OrderStateEnum.CONSTRUCTING.getCode();// 8
	Integer ORDER_OMSSTATUS_NO_REFUND = OrderStateEnum.NO_REFUND.getCode();// 9
	Integer ORDER_OMSSTATUS_NO_SEND = OrderStateEnum.NO_SEND.getCode();// 10
	Integer ORDER_OMSSTATUS_NO_FOR_PAYMENT = OrderStateEnum.NO_FOR_PAYMENT.getCode();// 11
	Integer ORDER_OMSSTATUS_PRE_DELIVERY = 12; // 目前按照  "签约阶段",16 来处理
	Integer ORDER_OMSSTATUS_TOUCH = 13;
	Integer ORDER_OMSSTATUS_PURPOSE = 14;
	Integer ORDER_OMSSTATUS_HANDSEL = 15;
	Integer ORDER_OMSSTATUS_SIGN = 16;
	Integer ORDER_OMSSTATUS_DELIVERY = 17;

    Integer PAY_TYPE_ALIPAY =1;
    Integer PAY_TYPE_WEIXIN =2;
    
    /**
	 * 订单类型 1 软装类型 2 硬装类型 3 全品家类型 5 艺术品类型 6文旅商品 15小星星艺术类型
	 */
	Integer ORDER_TYPE_SOFT = 1;
	Integer ORDER_TYPE_HARD = 2;
	Integer ORDER_TYPE_FAMILY = 3;
	Integer ORDER_TYPE_ART = 5;
	Integer ORDER_TYPE_CLUTRUE =6;
	Integer ORDER_TYPE_STAR_ART = 15;
	// add by jerfan cang
	Integer ORDER_TYPE_COLLAGE=16;

	//BOE画屏订单
	Integer ORDER_TYPE_BOE = 17;

	//画作订单
	Integer ORDER_TYPE_SCREEN = 18;

	//套装:9,和空间组合:10
	Integer ORDER_TYPE_SUIT=9;
	Integer ORDER_TYPE_ROOM=10;
	
	//全品家订单类型
	Integer ORDER_TYPE_ALADDIN = 13;//全品家订单
	
	Integer ORDER_TYPE_B2B = 8;//b2b 暂时按照 全品家订单来处理
	
	/**
	 * 商品类型: 5 艺术品类型 6文旅商品 15小星星艺术类型
	 */
	Integer PRODUCT_TYPE_ART = 5;
	Integer PRODUCT_TYPE_CLUTRUE =6;
	Integer PRODUCT_TYPE_STAR_ART = 15;

//    int CULTURE_ORDER_CREATE_SUCCESS = 0X01; //订单提交成功，未付款
//    int CULTURE_ORDER_WAIT_SEND = 0X03; //订单支付完成，待发货
//    int CULTURE_ORDER_PAY_COMPLETE = 0X04; //订单支付成功，待收货
//    int CULTURE_ORDER_COMPLETE = 0X05; //订单完成
//    int CULTURE_ORDER_CANCELED = 0X06; //订单取消
	
	Integer LOGISTIC_SKIP_DETAIL = 0;//跳转到物流详情页
	
	Integer LOGISTIC_SKIP_ALL = 1;//跳转到所有物流页
	
	Integer LOGISTIC_SUB_DELIVER = 1;//待发货
	
	String LOGISTIC_SUB_DELIVER_DESC = "已开始定制";
	
	Integer LOGISTIC_PART_DELIVER = 2;//部分发货
	
	String LOGISTIC_PART_DELIVER_DESC = "已发货";

	// 订单来源 6代客下单
	public static Integer VALETORDER_SOURCE = 6;

}