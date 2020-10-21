package com.ihomefnt.o2o.service.service.deal;

import com.ihomefnt.o2o.intf.dao.deal.DealDao;
import com.ihomefnt.o2o.intf.domain.deal.dto.*;
import com.ihomefnt.o2o.intf.service.deal.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.manager.constant.order.DealOrderConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hvk687 on 9/29/15.
 */
@Service
public class DealServiceImpl implements DealService {
    @Autowired
    DealDao dealDao;

    /**
     * int ORDER_CREATED = 2;//订单创建成功,等待支付
     * int ORDER_PAYED = 5;//订单已经支付,待领取
     * int PRODUCT_PICKED = 6;//产品已经领取
     *
     * @return
     */
    @Override
    public DealPickHomeResponse loadHomeResponse() {
        DealPickHomeResponse response = new DealPickHomeResponse();
        List<DealPickModel> allList = dealDao.loadAllDealOrder();

        List<DealPickModel> picked = new LinkedList<DealPickModel>();
        List<DealPickModel> unpay = new LinkedList<DealPickModel>();
        List<DealPickModel> paied = new LinkedList<DealPickModel>();

        for (DealPickModel model : allList) {
            switch (model.getStatus()) {
                case DealOrderConstant.ORDER_CREATED:
                    unpay.add(model);
                    break;
                case DealOrderConstant.ORDER_PAYED:
                    paied.add(model);
                    break;
                case DealOrderConstant.PRODUCT_PICKED:
                    picked.add(model);
                    break;
                default:
                    //do nothing
            }
        }
        response.setAll(allList);
        response.setPicked(picked);
        response.setUnPay(unpay);
        response.setUnPick(paied);

        return response;
    }

    @Override
    public OrderDetailResponse loadOrderDetailResponse(String mobile) {
        if (StringUtil.isNullOrEmpty(mobile)) {
            return null;
        }
        TDealOrder dealOrder = null;
        try {
            dealOrder = dealDao.loadDealOrder(mobile);
        } catch (Exception e) {

        }
        if (dealOrder == null) {
            return null;
        }
        OrderDetailResponse response = new OrderDetailResponse();

        TActivityProduct product = dealDao.queryActPrd(dealOrder.getActPrdId());
        if (product != null) {
            ProductDetail detail = new ProductDetail();
            detail.setImage(product.getImage());
            detail.setName(product.getName());
            DecimalFormat format = new DecimalFormat("0.00");
            detail.setOriginalPrice(format.format(product.getOrigPrice()));
            detail.setPrice(format.format(product.getPrice()));
            response.setProduct(detail);
        }

        response.setStatus(dealOrder.getStatus());
        response.setDeadLine("");
        response.setOrderNo(dealOrder.getOrderNo());

        return response;
    }

    @Override
    public boolean pickOrder(String orderNo) {
        return dealDao.pick(orderNo);
    }

    @Override
    public int getOrderCount(int userId) {
        return dealDao.getOrderCount(userId);
    }
    
}
