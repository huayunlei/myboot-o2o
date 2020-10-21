package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ExpStoreSuitDto implements Serializable{

	private static final long serialVersionUID = -4687988650282676584L;
	private Long houseId;
    private String houseName;
    private Long suitId;
    private String suitName;
    private String suitImages;
    private String firstImage;
    private Double size;
    private Integer room;
    private Integer living;
    private Integer toilet;
    private Integer productNum;
    private Double suitPrice;
    private Double suitPrductPrice;
    private Double deal;
    private Long styleId;   //风格ID
    private String styleName;
    
    private Integer suitLabelId;
    private String suitLabelName;
    
    private List<TRoom> roomList;//套装所属空间
    
    private Double roomDiscount;//空间折扣
}
