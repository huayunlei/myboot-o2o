package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponseN;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.UserComment;
import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import lombok.Data;

@Data
public class HttpCompositeDetailResponseN {

    private CompositeProductReponseN compositeProduct;
    private List<ProductSummaryResponse> singleList;
    private List<HouseImage> houseImageList;
    private List<Room> roomList; //所有 空间
    private String allImageTitle;
    private int collection;
    private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
    private List<UserComment> userCommentList;
    private HttpUserLikeResponse userLikeResponse;
}
