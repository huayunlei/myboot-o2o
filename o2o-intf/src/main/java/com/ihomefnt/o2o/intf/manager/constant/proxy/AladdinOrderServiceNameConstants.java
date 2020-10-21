package com.ihomefnt.o2o.intf.manager.constant.proxy;

/**
 * @author huayunlei
 * @ClassName: AladdinOrderServiceNameConstants
 * @Description: aladdin-order服务名称常量池
 * @date Feb 14, 2019 1:47:10 PM
 */
public interface AladdinOrderServiceNameConstants {


	String CONFIRM_REQURIEMENT = "aladdin-order.masterOrder-app.masterOrder.confirmRequriement";

	String QUERY_BUILDING_SCHEME_RECORD = "aladdin-order.masterOrder-app.queryBuildingSchemeRecord";

    String UPDATE_FAMILY_ORDER = "aladdin-order.aladdinFamilyOrder.order.updateFamilyOrder";

    String QUERY_VALET_ORDER_DETAIL = "aladdin-order.valetOrder.queryValetOrderDetail";

    String QUERY_ORDER_DETAIL = "aladdin-order.masterOrder.queryOrderDetail";

    String QUERY_APP_ORDER_BASE_INFO = "aladdin-order.masterOrder-app.v5_0.queryAppOrderBaseInfo";

    String QUERY_ORDER_SOLUTION_INFO = "aladdin-order.masterOrder-app.queryOrderSolutionInfo";

    String QUERY_APP_SYSTEM_INFO = "aladdin-order.masterOrder-app.v5_0.queryAppSystemInfo";

    String ADD_CUSTOMER_HOUSE_INFO = "aladdin-order.masterOrder-app.addCustomerHouseInfo";

    String QUERY_CONTRACT_LIST = "aladdin-order.app.electronic.contract.queryContractList";

	String CANCEL_SCHEME = "aladdin-order.masterOrder-app.cancelScheme";

	String QUERY_ORDER_LIST_BY_PARAM = "aladdin-order.masterOrder-app.masterOrder.queryOrderListByParam";

	String UPDATE_CUSTOMER_BANKCARK_INFO = "aladdin-order.masterOrder-app.updateCustomerBankCarkInfo";
	
	String QUERY_CUSTOMER_INFO_BY_PARAM = "aladdin-order.aladdinCustomer.queryCustomerInfoByParam";
	
	String LOAN_QUERY_ALL_BANK_LOAN_RATE = "aladdin-order.loanInfo-app.loan.queryAllBankLoanRate";

	String QUERY_BANK_LOAN_RATE_BY_ORDER = "aladdin-order.bank.queryLoanRateByOrderNum";

	String LOAN_APP_CREATE_LOAN_AIJIA = "aladdin-order.loanInfo-app.loan.appCreateLoanAijia";
	
	String LOAN_APP_CANCEL_LOAN_AIJIA = "aladdin-order.loanInfo-app.loan.appCancelLoanAijia";
	
	String LOAN_APP_QUERY_LOAN_INFOS = "aladdin-order.loanInfo-app.loan.appQueryLoanInfos";
	
	String LOAN_APP_QUERY_LOAN_AIJIA_DETAIL = "aladdin-order.loanInfo-app.loan.appQueryLoanAijiaDetail";

	String CREATE_FAMILY_ORDER = "aladdin-order.aladdinFamilyOrder.order.createFamilyOrder";

	String QUERY_FAMILY_ORDER_PRICE = "aladdin-order.aladdinFamilyOrder.order.queryFamilyOrderPrice";

	String QUERY_ORDER_TRANSACTION_LIST = "aladdin-order.masterOrder-app.queryOrderTransactionList";

	String CREATE_LOAN_INFO = "aladdin-order.loanInfo-app.loan.createLoanInfo";

	String QUERY_LOAN_INFO = "aladdin-order.loanInfo-app.loan.queryLoanInfo";

	String QUERY_ROOM_SKU_INFO = "aladdin-order.aladdinOrder.snapshot.queryRoomSkuInfo";

	String QUERY_ORDER_TRANSACTION_DEATIL = "aladdin-order.masterOrder-app.queryOrderTransactionDeatil";

	String PRE_CONFIRM_LIST = "aladdin-order.masterOrder-app.v5_0.preConfirmList";

	String OFFLINE_ORDER_PAYMENT = "aladdin-order.order-rights.OfflineOrderPayment";

	String QUERY_ORDER_SUMMARY_INFO = "aladdin-order.order-rights.queryOrderSummaryInfo";

	String QUERY_ORDER_ACT_RECORD_BY_USER = "aladdin-order.masterOrder-app.queryOrderActRecordByUser";

	String CONFIRM_JOIN_ACT = "aladdin-order.masterOrder-app.confirmJoinAct";

	String QUERY_HOUSE_INFO_BY_HOUSE_ID = "aladdin-order.houseProperty-app.queryHouseInfoByHouseId";

	String CHECK_SPECIFIC_USER = "aladdin-order.aladdinFamilyOrder.order.checkSpecificUser";

	String QUERY_HOUSE_INFO_LIST_BY_USER_ID = "aladdin-order.houseProperty-app.queryHouseInfoListByUserId";

	String QUERY_BUILDING_LIST_WITH_IDS = "dolly-web.api-project.queryBuildingListWithIds";

	String QUERY_MASTER_ORDER_IDS_BY_HOUSE_IDS = "aladdin-order.masterOrder-app.queryMasterOrderIdsByHouseIds";


    /**
     * 查询订单权益资质服务名
     */
    String SERVER_NAME_QUERY_ORDER_RIGHT_LICENSE = "aladdin-order.order-rights.checkOrderRights";

    /**
     * 查询订单权益(活动)详情服务名
     */
    String SERVER_NAME_QUERY_ORDER_RIGHT_DETAIL = "aladdin-promotion.promotion.queryOrderRightsActList";

	String ALADDIN_ORDER_MASTERORDER_APP_QUERYORDERVERSION = "aladdin-order.masterOrder-app.queryOrderVersion";

    /**
     * 查询订单权益分类详情
     */
    String SERVER_NAME_QUERY_ORDER_RIGHT_CLASSIFY = "aladdin-promotion.promotion.querySingleRightsDetail";

    /**
     * 查询我的订单权益详情
     */
    String SERVER_NAME_QUERY_ORDER_RIGHT_MIME = "aladdin-promotion.promotion.queryOrderRightsDetail";

    /**
     * 查询订单权益中单个分类下的权益详情
     */
    String SERVER_URL_QUERY_ORDER_RIGHT_SINGLE_CLASSIFY = "aladdin-promotion.promotion.queryOrderSingleRightsDetail";

    /**
     * 订单分类下的权益确权保存操作
     */
    String SERVER_URL_CONFIRM_ORDER_CLASSIFY_RIGHT = "aladdin-promotion.promotion.confirmedRightsInterests";

    String SERVER_URL_QUERY_GRADE_RIGHTS_LIST = "aladdin-promotion.promotion.queryGradeRightsList";

    /**
     * 查询aladdin用户房产信息
     */
    String HOUSEPROPERTY_APP_QUERYHOUSEINFOLISTBYUSERID = "aladdin-order.houseProperty-app.queryHouseInfoListByUserId";

    String ALADDIN_ORDER_MASTERORDER_APP_QUERYMASTERORDERIDSBYHOUSEIDS = "aladdin-order.masterOrder-app.queryMasterOrderIdsByHouseIds";

	String QUERY_ORDER_SIMPLE_INFO = "aladdin-order.home-order.queryOrderSimpleInfo";

	//查询一些时间
	String QUERY_ORDER_MANY_TIME = "aladdin-order.masterOrder-app.queryOrderManyTime";

	/**
	 * 查询是否可点击确认开工
	 */
	String CHECK_IF_CAN_DELIVER_CONFIRM = "aladdin-order.houseProperty-app.checkIfCanDeliverConfirm";

	/**
	 * 生成定金收款单
	 */
	String HANDLE_DEPOSIT_MONEY = "aladdin-order.transaction.handleDepositMoney";

	/**
	 * 生成收款单
	 */
	String HANDLE_MONEY = "aladdin-order.transaction.handleMoney";

	/**
	 * 修改交房日期
	 */
	String UPDATE_DELIVER_TIME = "aladdin-order.houseProperty-app.updateDeliverTime";

	/**
	 * 取消订单
	 */
	String LOGICAL_DELETE_DEMO_ORDER = "aladdin-order.masterOrder-app.logicalDeleteDemoOrder";

	/**
	 * 根据订单编号批量查询简单(订单信息，房产信息）
	 */
	String QUERY_SIMPLE_INFO_BY_ORDER_NUMS = "aladdin-order.aladdinOrder.snapshot.querySimpleInfoByOrderNums";

	String QUERY_ORDER_LIST_BY_USER_ID = "aladdin-order.masterOrder-app.queryOrderListByUserId";

}
