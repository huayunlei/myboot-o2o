package com.ihomefnt.o2o.service.service.cart;

import com.ihomefnt.o2o.intf.dao.cart.ShoppingCartDao;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartDto;
import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartProductDto;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.AddShoppingCartRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.BatchGoodsRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.request.GoodsAmountRequestVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.proxy.cart.ShoppingCartProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.cart.ShoppingCartService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车Service层
 *
 * @author Charl
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private ShoppingCartProxy shopppingCartProxy;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    /**
     * 艾积分汇率
     */
    private static int EX_RATE = 100;

    @Override
    public void addShoppingCart(AddShoppingCartRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("userId", user.getId());
        param.put("goodsId", request.getGoodsId());
        param.put("goodsType", request.getGoodsType());
        param.put("goodsAmount", request.getGoodsAmount());
        int i = shopppingCartProxy.addGoods(param);
        if (i != 1) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    /**
     * 设置用户已选属性
     * @param selectAttr
     */
    private String getSelectAttr(String selectAttr){
        if(org.apache.commons.lang.StringUtils.isNotBlank(selectAttr)){
            if(selectAttr.indexOf(":")<=-1){
                return selectAttr;
            }
            StringBuilder selectAttrRetrun = new StringBuilder();
            if(selectAttr.indexOf("/")>-1){
                selectAttr = selectAttr.trim();
                String[] split = selectAttr.split("/");
                for (String attr : split) {
                    if(attr.indexOf(":")>-1){
                        String[] attrSplit = attr.split(":");
                        if(attrSplit.length>1){
                            selectAttrRetrun.append(attrSplit[1]+"/");
                        }
                    }
                }
            }else{
                if(selectAttr.indexOf(":")>-1){
                    String[] split = selectAttr.split(":");
                    if(split.length>1){
                        selectAttrRetrun.append(split[1]+"/");
                    }
                }
            }
            if(selectAttrRetrun.indexOf("/")>-1){
                return selectAttrRetrun.toString().substring(0,selectAttrRetrun.length()-1);
            }
            return selectAttrRetrun.toString();
        }
        return "";
    }

    @Override
    public GoodsListResponseVo goodsList(HttpBaseRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", user.getId());
        GoodsListResponseVo goodsListResultVo = shopppingCartProxy.goodsList(param);

        if (null == goodsListResultVo) {
            throw new BusinessException(MessageConstant.FAILED);
        }
        List<GoodsItemReponseVo> enableGoodsList = goodsListResultVo.getEnableGoodsList();
        if (CollectionUtils.isNotEmpty(enableGoodsList)) {
            for (GoodsItemReponseVo vo : enableGoodsList) {
                vo.setGoodsDescription(getSelectAttr(vo.getGoodsDescription()));
                if (StringUtils.isNotEmpty(vo.getGoodsImage())) {
                    vo.setGoodsImage(getSinglePicUrl(vo.getGoodsImage(), request.getWidth()));
                }
            }
        }
        List<GoodsItemReponseVo> disableGoodsList = goodsListResultVo.getDisableGoodsList();
        if (CollectionUtils.isNotEmpty(disableGoodsList)) {
            for (GoodsItemReponseVo vo : disableGoodsList) {
                vo.setGoodsDescription(getSelectAttr(vo.getGoodsDescription()));
                if (StringUtils.isNotEmpty(vo.getGoodsImage())) {
                        vo.setGoodsImage(getSinglePicUrl(vo.getGoodsImage(), request.getWidth()));
                }
            }
        }

        return goodsListResultVo;
    }

    /**
     * 获取单张图片并切图
     * @param image
     * @return
     */
    private String getSinglePicUrl(String image,Integer width){
        if(image.indexOf(",")>-1){
            String[] split =image.split(",");
            return AliImageUtil.imageCompress(split[0],
                    2, width, ImageConstant.SIZE_SMALL);
        }else{
            return AliImageUtil.imageCompress(image, 2, width, ImageConstant.SIZE_SMALL);
        }
    }

    @Override
    public void incGoodsAmount(GoodsAmountRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordId", request.getRecordId());
        int i = shopppingCartProxy.addGoodsAmount(param);
        if (i != 1) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public void decGoodsAmount(GoodsAmountRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordId", request.getRecordId());
        int i = shopppingCartProxy.reduceGoodsAmount(param);
        if (i != 1) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public void removeGoods(GoodsAmountRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordId", request.getRecordId());
        int i = shopppingCartProxy.removeGoods(param);
        if (i != 1) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public void batchRemoveGoods(BatchGoodsRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordIds", request.getRecordIds());
        int i = shopppingCartProxy.removeGoods(param);
        if (i != 1) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public GoodsCountResponseVo goodsAmount(HttpBaseRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", user.getId());
        int i = shopppingCartProxy.goodsCount(param);
        if (i < 0) {
            throw new BusinessException(MessageConstant.FAILED);
        }

        GoodsCountResponseVo resultVo = new GoodsCountResponseVo();
        resultVo.setCount(i);
        return resultVo;
    }

    @Override
    public HttpAjbInfoResponseVo ajbInfo(HttpBaseRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        HttpAjbInfoResponseVo data = new HttpAjbInfoResponseVo();
        java.util.Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", user.getId());
        AjbAccountDto ajbInfo = shopppingCartProxy.ajbInfo(param);
        if (ajbInfo != null) {
            data.setAccountId(ajbInfo.getAccountId());
            data.setAjbAccountId(ajbInfo.getAjbAccountId());
            data.setAmount(ajbInfo.getAmount());
            Integer amount = ajbInfo.getAmount();
            BigDecimal ajbMoney = new BigDecimal(amount).setScale(2);
            data.setAmountMoney(ajbMoney.divide(new BigDecimal(EX_RATE)));
            data.setCreateTime(ajbInfo.getCreateTime());
            data.setFrozenAmount(ajbInfo.getFrozenAmount());
            data.setStatus(ajbInfo.getStatus());
            data.setUserId(ajbInfo.getUserId());
            data.setExRate(EX_RATE);
        } else {
            data.setAmount(0);
            data.setAmountMoney(new BigDecimal(0));
            data.setFrozenAmount(0);
            data.setExRate(EX_RATE);
        }
        return data;
    }

    @Override
    public void settleAccount(BatchGoodsRequestVo request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (null == user) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        java.util.Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordIds", request.getRecordIds());
        boolean b = shopppingCartProxy.settleAccount(param);
        if (!b) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public ShoppingCartListResponseVo queryShoppingCart(Long userId) {
        ShoppingCartListResponseVo res = new ShoppingCartListResponseVo();
        List<Room> onSlaveProduct = shoppingCartDao.queryShoppingCartOnSlave(userId);
        for (Room r : onSlaveProduct) {
            int cnt = 0;
            List<ProductSummaryResponse> list = r.getProductSummaryList();
            for (ProductSummaryResponse p : list) {
                cnt = cnt + p.getProductCount();
                p.setPictureUrlOriginal(ImageUtil.removeEmptyStr(p.getImagesUrl()));
            }
            r.setCount(cnt);
        }
        List<ShoppingCartProductDto> offSlaveProduct = shoppingCartDao.queryShoppingCartOffSlave(userId);
        for (ShoppingCartProductDto pf : offSlaveProduct) {
            pf.setPictureUrlOriginal(ImageUtil.removeEmptyStr(pf.getImagesUrl()));
        }
        res.setOnSlaveProduct(onSlaveProduct);
        res.setOffSlaveProduct(offSlaveProduct);
        return res;
    }

    @Override
    public ShoppingCartAddResponseVo addShoppingCartBatch(
            List<ShoppingCartDto> list) {
        ShoppingCartAddResponseVo res = new ShoppingCartAddResponseVo();
        if (null != list && list.size() > 0 && null != list.get(0).getUserId()) {
            shoppingCartDao.addShoppingCartBatch(list);
            int cnt = shoppingCartDao.queryShoppingCartCnt(list.get(0).getUserId());
            res.setShoppingCartCnt(cnt);
        }
        return res;
    }

    @Override
    public int queryShoppingCartCnt(Long userId) {
        return shoppingCartDao.queryShoppingCartCnt(userId);
    }

    @Override
    public int deleteOffProduct(Long userId, Long productId) {
        return shoppingCartDao.deleteOffProduct(userId, productId);
    }

    @Override
    public int deleteOffProduct(Long userId, List<Long> productIds) {
        if (null != productIds) {
            for (Long productId : productIds) {
                shoppingCartDao.deleteOffProduct(userId, productId);
            }
            return productIds.size();
        }
        return 0;
    }

    @Override
    public List<Long> queryShoppingCartProduct(Long userId) {
        return shoppingCartDao.queryShoppingCartProduct(userId);
    }

    @Override
    public List<ProductOrder> queryProductInfo(List<Long> productId) {
        return shoppingCartDao.queryProductInfo(productId);
    }


}
