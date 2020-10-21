/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月7日
 * Description:IProductProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.product;

import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.product.dto.*;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceAbleDto;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomClassIdDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ReplaceAbleDollyResponse;

import java.util.List;
import java.util.Map;

/**
 * 商品服务代理接口
 * 
 * @author zhang
 */
public interface ProductProxy {

	/**
	 * 分页查询已上架带图的sku列表<br/>
	 * 
	 * @param param
	 *            :由page和limit组成<br/>
	 * @param page
	 *            (integer, optional):表示第几页, <br/>
	 * @param limit
	 *            (integer, optional):表示每页多少个 <br/>
	 * @return<br/>
	 */
	ProductListResponseVo queryOnShelveAndWithPicSku(Object param);

	/**
	 * 根据skuId查询SKU简单信息<br/>
	 * 
	 * @param param
	 *            : skuId integer<br/>
	 * @return<br/>
	 */
	ProductResponseVo querySimpleSkuById(Object param);

	/**
	 * 查询sku的品牌信息
	 * 
	 * @param param
	 *            :skuIds array[integer]:数组 集合<br/>
	 * @return
	 */
	List<BrandResponeVo> getSkuBrandInfo(Object param);

	/**
	 * 传入skuIds，返回已上架和未上架的两个skuList
	 * 
	 * @param param
	 *            :skuIds array[integer]:数组 集合<br/>
	 * @return
	 */
	ShelveListResponeVo queryShelveSku(Object param);

	/**
	 * 根据条件分页查询sku列表 <br/>
	 * 
	 * @param param
	 *            :由如下条件组成<br/>
	 * @param id
	 *            (array[integer], optional):skuId,<br/>
	 * @param productId
	 *            (array[integer], optional):skuId, <br/>
	 * @param categoryId
	 *            (array[integer], optional):分类Id,<br/>
	 * @param brandId
	 *            (array[integer], optional):品牌Id, <br/>
	 * @param page
	 *            (integer, optional):表示第几页,<br/>
	 * @param limit
	 *            (integer, optional):表示每页多少个<br/>
	 * @return
	 */
	ProductListResponseVo querySimpleSkuSelective(Object param);

	/**
	 * 根据skuIds集合查询SKU简单信息集合
	 * 
	 * @param param
	 *            :skuIds array[integer]:数组 集合<br/>
	 * @return
	 */
	List<ProductResponseVo> querySimpleSku(Object param);
	
	/**
	 * 根据id查询商品
	 * @param itemId
	 * @return
	 */
	TripProductDto queryProductById(Integer itemId);
	
	/**
	 * 根据文旅商品ID查询商户信息
	 * @param param
	 * @return
	 */
	SellerVo getSellerByProductId(Map<String, Object> param);
	
	/**
	 * 生成文旅商品订单唯一码
	 * @param param
	 * @return
	 */
	boolean generatorOrderCode(GenerateCodeVo param);
	
	/**
	 * 文旅商品订单唯一码是否存在
	 * @param generatOrderCode
	 * @return
	 */
	List<ConsumeCodeVo> getCodeListByOrderId(Map<String, Object> generatOrderCode);
	
	/**
	 * 查询文旅商品商品列表
	 */
	Page<TripProductDto> queryTripProductList(Map<String, Object> paramMap);
	
	/**
	 * 使用唯一码
	 */
	boolean useCode(Map<String, Object> param);

	/**
	 * @param productCategoryIds
	 * @return
	 */
	Map<String, ProductCategoryVo> batchQueryCategoryFullPathByIds(List<Integer> productCategoryIds);

	/**
	 * 通过skuId批量查询roomClassId
	 * @param skuIdList
	 * @return
	 */
	List<RoomClassIdDto> batchQueryClassIdBySkuId(List<Integer> skuIdList);

	/**
	 * 根据id集合查询SKU简单信息
	 * @param skuIdList
	 * @return
	 */
	List<SkuBaseInfoDto> batchQuerySkuBaseInfo(List<Integer> skuIdList);

	/**
	 * 根据id集合查询软装Bom简单信息
	 * @param skuIdList
	 * @return
	 */
	BomCategoryListDto batchQuerySkuBomBaseInfo(List<Integer> skuIdList);

	/**
	 * 一键替换
	 * @param request
	 * @return
	 */
    ReplaceAbleDollyResponse queryUnifiedReplacement(ReplaceAbleDto request);
}
