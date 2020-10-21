package com.ihomefnt.o2o.intf.domain.suit.dto;

import com.ihomefnt.o2o.intf.domain.product.dto.TProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Terrific
 *
 */
@Data
public class TRoom {

    private Long roomId;
    private String roomName;//房间名称
    private Short roomType;//空间类型
    private Long suitId;//套装id
    private String suitName;//套装名称
    private String roomImgs;//空间图片字符串
    private BigDecimal length;//长
    private BigDecimal width;//宽
    private BigDecimal size;//大小
    private Integer productCount;//商品总数
    private String style;//风格
    private BigDecimal marketPrice;//市场价
    private BigDecimal ihomePrice;//艾佳售价
    private Long commentCount;//评论数量
    private List<TProduct> productList;//商品列表
    private String consultMobile;//咨询电话
    private Boolean isOffShelf;
    private String experStore;
    private Long styleId;
    private BigDecimal suitPrice;
    private double roomDiscount;// 空间打折率
    private String firstImage;//头图
}
