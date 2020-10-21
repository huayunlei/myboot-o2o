package com.ihomefnt.o2o.intf.service.cart;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartDto;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.AddShoppingCartRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.BatchGoodsRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.GoodsAmountRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsCountResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsListResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.HttpAjbInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.ShoppingCartAddResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.ShoppingCartListResponseVo;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface ShoppingCartService {

    /**
     * 用户加入商品到购物车
     *
     * @param
     */
    void addShoppingCart(AddShoppingCartRequestVo request);

    /**
     * 查询购物车商品
     *
     * @param
     */
    GoodsListResponseVo goodsList(HttpBaseRequest request);

    /**
     * 购物车中商品+1
     *
     * @param
     */
    void incGoodsAmount(GoodsAmountRequestVo request);

    /**
     * 购物车中商品-1
     *
     * @param
     */
    void decGoodsAmount(GoodsAmountRequestVo request);

    /**
     * 删除购物车中一条记录
     *
     * @param
     */
    void removeGoods(GoodsAmountRequestVo request);

    /**
     * 批量删除购物车中记录
     *
     * @param
     */
    void batchRemoveGoods(BatchGoodsRequestVo request);

    /**
     * 购物车中商品数量
     *
     * @param
     */
    GoodsCountResponseVo goodsAmount(HttpBaseRequest request);

    /**
     * 当前用户艾积分信息
     *
     * @param
     */
    HttpAjbInfoResponseVo ajbInfo(HttpBaseRequest request);

    /**
     * 购物车结算
     *
     * @param request
     */
    void settleAccount(BatchGoodsRequestVo request);
    
    ShoppingCartListResponseVo queryShoppingCart(Long userId);
	
	ShoppingCartAddResponseVo addShoppingCartBatch(List<ShoppingCartDto> list);
	
	int queryShoppingCartCnt(Long userId);
	
	int deleteOffProduct(Long userId,Long productId);
	
	int deleteOffProduct(Long userId,List<Long> productIds);
	
	List<Long> queryShoppingCartProduct(Long userId);
	
	List<ProductOrder> queryProductInfo(List<Long> productId);
}
