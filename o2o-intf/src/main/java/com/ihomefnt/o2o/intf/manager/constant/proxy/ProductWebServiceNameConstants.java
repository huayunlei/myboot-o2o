package com.ihomefnt.o2o.intf.manager.constant.proxy;

/**
 * @author huayunlei
 * @ClassName: ProductWebServiceNameConstants
 * @Description: product-web服务名称常量池
 * @date Feb 14, 2019 1:42:27 PM
 */
public interface ProductWebServiceNameConstants {

	String PRODUCT_APP_QUERY_CUSTOM_ATTR_TREE = "product-web.product.app.queryCustomAttrTree";
	
	String PRODUCT_APP_CREATE_CUSTOM_SKU = "product-web.product.app.createCustomSku";
	
	/**
     * 根据二级类目 四级类目的id查询过滤条件的服务名
     */
    String SERVER_NAME_QUERY_CONDITION ="product-web.sku.replace.initSearchCondition";
    
    /**
     * 条件化查询软装更多可替换sku
     */
    String SERVER_NAME_QUERY_MORE_SKU_BY_CONDITION ="product-web.sku.replace.searchReplaceSku";

    // 根据类目查筛选条件
    String SEARCH_QUERY_CONDITION_BY_CATEGORY = "product-web.search.queryConditionByCategory";

    // 搜索同类sku
    String SEARCH_REPLACE_SKU = "product-web.search.searchReplaceSku";

    /**
     * 底层sku详情url
     */
    String URL_SKU_DETAIL = "product-web.product.queryProductDtoBySkuIds";
    
    //物料详情
    String BOM_APP_MATERIAL_DETAIL = "product-web.bom.app.material.detail";

    //组合详情
    String BOM_APP_GROUP_DETAIL = "product-web.bom.app.group.detail";

    //分页查询物料信息
    String BOM_APP_MATERIAL_PAGE = "product-web.bom.app.material.page";

    //保存组合
    String BOM_APP_GROUP_SAVE = "product-web.bom.app.group.save";

    //查询物料筛选项
    String BOM_APP_MATERIAL_OPTIONS = "product-web.bom.app.material.options";

    //product-web查询组合替换详情
    String BOM_APP_GROUP_REPLACE_DETAIL = "product-web.bom.app.group.replace.detail";

    //product-web查询组合替换简单信息
    String BOM_APP_GROUP_REPLACE_SIMPLEINFOLIST = "product-web.bom.app.group.replace.simpleInfoList";

    //product-web查询组合替换简单信息
    String BOM_APP_GROUP_DETAIL_LIST= "product-web.bom.external.group.detailList";

    String QUERY_ON_SHELVE_AND_WITH_PIC_SKU = "product-web.sku.queryOnShelveAndWithPicSku";

    String QUERY_SIMPLE_SKU_BY_ID = "product-web.sku.querySimpleSkuById";

    String GET_SKU_BRAND_INFO = "product-web.sku.getSkuBrandInfo";

    String QUERY_SHELVE_SKU = "product-web.sku.queryShelveSku";

    String QUERY_SIMPLE_SKU_SELECTIVE = "product-web.sku.querySimpleSkuSelective";

    String QUERY_SIMPLE_SKU = "product-web.sku.querySimpleSku";

    // 根据id集合查询SKU简单信息集合
    String QUERY_SKUS_BASE_INFO = "product-web.sku.querySkuBaseInfo";

    // 根据id集合查询SKU简单信息集合
    String QUERY_BOM_BASE_INFO = "product-web.bom.app.group.category";

    String QUERY_PRODUCT_BY_ID = "product-web.product.queryProductById";

    String GET_SELLER_BY_PRODUCT_ID = "product-web.seller.getSellerByProductId";

    String GENERATOR_ORDER_CODE = "product-web.tripProductCode.generatorOrderCode";

    String QUERY_TRIP_PRODUCT_LIST = "product-web.product.queryTripProductList";

    String GET_CODE_LIST_BY_ORDER_ID = "product-web.tripProductCode.getCodeListByOrderId";

    String TRIP_PRODUCT_CODE_USE_CODE = "product-web.tripProductCode.useCode";

    String BATCH_QUERY_CATEGORY_FULL_PATH_BY_IDS = "product-web.category.batchQueryCategoryFullPathByIds";

    String BATCH_QUERY_CLASSID_BY_SKU_ID = "product-web.hardStandard.o2o.batchQueryClassIdBySkuId";

    String QUERY_ALL_ROOM_CLASS_REL="product-web.hardStandard.dolly.listAllRoomClassRel";

    String QUERY_BOM_GROUP_COMPONENT_DETAIL = "product-web.bom.external.group.component.detail";

    //product-web查询组合替换简单信息
    String BOM_APP_BATCH_SAVE_GROUP= "product-web.bom.app.group.batch.save";

    String ONE_KEY_REPLACE = "dolly-web.solution-app.oneKeyReplace";

    String QUERY_RELATION_CATEGORY = "product-web.category.app.queryRelationCategory";
}
