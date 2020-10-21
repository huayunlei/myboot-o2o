package com.ihomefnt.o2o.intf.manager.constant.proxy;

public interface OmsWebServiceNameConstants {

	String SHOPPING_CART_ADD_GOODS = "oms-web.shoppingCart.addGoods";
	
	String SHOPPING_CART_GOODS_LIST = "oms-web.shoppingCart.goodsList";
	
	String SHOPPING_CART_ADD_GOODS_AMOUNT = "oms-web.shoppingCart.addGoodsAmount";
	
	String SHOPPING_CART_REDUCE_GOODS_AMOUNT = "oms-web.shoppingCart.reduceGoodsAmount";
	
	String SHOPPING_CART_REMOVE_GOODS = "oms-web.shoppingCart.removeGoods";
	
	String SHOPPING_CART_BATCH_REMOVE_GOODS = "oms-web.shoppingCart.batchRemoveGoods";
	
	String SHOPPING_CART_GOODS_COUNT = "oms-web.shoppingCart.goodsCount";
	
	String SHOPPING_CART_SETTLE_ACCOUNTS = "oms-web.shoppingCart.settleAccounts";
	
	String SERVER_URL_CANCEL_COLLAGE_ORDER="oms-web.art-order.artGroupCancel";
	
	/**
     * 创建艺术品订单
     */
    String SERVER_CREATE_ART_ORDER = "oms-web.art-order.createArtOrder";


    /**
     * 取消艺术品订单
     */
    String SERVER_CANCEL_ART_ORDER = "oms-web.art-order.artCancel";

    /**
     * 查询艺术品订单详情，根据id
     */
    String SERVER_QUERY_ART_ORDER_DETAIL = "oms-web.order.info.queryArtOrderDetail";
    
    String DEL_CANCEL_ART_ORDER = "oms-web.art-order.delCancelArtOrder";

    String QUERY_SOLUTION_ORDER_BY_ID = "oms-web.productorder.querySolutionOrderById";

    String QUERY_ORDER_INFO = "oms-web.order.info.queryOrderInfo";

    String QUERY_ORDER_DETAIL = "oms-web.order.info.queryOrderDetail";

    String QUERY_OMS_ORDER_DETAIL = "oms-web.art-order.newArt.queryOrderDetail";

    String QUERY_TRIP_ORDER_DETAIL = "oms-web.order.info.queryTripOrderDetail";

    String QUERY_HARD_ORDER_DETAIL = "oms-web.order.info.queryHardOrderDetail";

    String QUERY_FAMILY_ORDER_DETAIL = "oms-web.order.info.queryFamilyOrderDetail";

    String QUERY_SOFT_ORDER_DETAIL = "oms-web.order.info.querySoftOrderDetail";

    String QUERY_ORDER_DETAIL_BY_ORDER_NUM = "oms-web.order.info.queryOrderDetailByOrderNum";

    String QUERY_ORDER_DELIVERY_INFO = "oms-web.order.info.queryOrderDeliveryInfo";

    String QUERY_CASHIER_RECORDS_BY_ORDER_ID = "oms-web.cashier.queryCashierRecordsByOrderId";

    String QUERY_ORDER_BY_ORDER_NUM = "oms-web.order.info.queryOrderByOrderNum";




    /**
     * 艾商城接口
     */
    String QUERY_ART_ALL_SKU_LIST = "oms-web.artWork.app.queryArtAllSkuList";

    String QUERY_ART_CUSTOMIZE_LIST = "oms-web.artWork.app.queryArtCustomizeList";

    String QUERY_ART_LIST = "oms-web.artWork.app.queryArtList";

    String QUERY_ART_PRODUCT_LIST = "oms-web.artWork.app.queryArtProductList";

    String QUERY_ART_STYLES = "oms-web.artWork.app.queryArtStyles";

    String QUERY_ART_WORKS_INFO = "oms-web.artWork.app.queryArtWorksInfo";

    String QUERY_ARTIST_INFOS = "oms-web.artWork.app.queryArtistInfos";

    String QUERY_ART_PRODUCT_DETAIL = "oms-web.artWork.app.queryArtProductDetail";

    String QUERY_INVENTORY = "oms-web.artWork.app.queryInventory";

    String QUERY_ART_WORK_ORDER_INFO = "oms-web.artWork.app.getArtworkOrderInfo";

}
