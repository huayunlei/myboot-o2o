package com.ihomefnt.o2o.intf.proxy.cart;

import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsListResponseVo;

public interface ShoppingCartProxy {

    /**
     * 添加商品到购物车
     *
     * @param
     * @return ResponseVo<Object>
     */
    int addGoods(Object param);

    /**
     * 查询购物车商品列表
     *
     * @param
     * @return ResponseVo<GoodsListResultVo>
     */
    GoodsListResponseVo goodsList(Object param);

    /**
     * 购物车中增加商品数量
     *
     * @param
     * @return ResponseVo<Object>
     */
    int addGoodsAmount(Object param);

    /**
     * 购物车中减少商品数量
     *
     * @param
     * @return ResponseVo<Object>
     */
    int reduceGoodsAmount(Object param);

    /**
     * 删除购物车中商品
     *
     * @param
     * @return ResponseVo<Object>
     */
    int removeGoods(Object param);

    /**
     * 批量删除购物车中商品
     *
     * @param
     * @return ResponseVo<Object>
     */
    int batchRemoveGoods(Object param);

    /**
     * 查询购物车中商品
     *
     * @param
     * @return ResponseVo<GoodsCountResultVo>
     */
    int goodsCount(Object param);

    /**
     * 获取当前用户艾积分信息
     *
     * @param
     * @return ResponseVo<AjbAccountVo>
     */
    AjbAccountDto ajbInfo(Object param);

    /**
     * 购物车结算
     *
     * @param param
     * @return
     */
    boolean settleAccount(Object param);
}
