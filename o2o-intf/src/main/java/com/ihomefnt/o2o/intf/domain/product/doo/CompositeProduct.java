package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;

/**
 * Created by shirely_geng on 15-1-17.
 */
@Data
public class CompositeProduct {

    private Long compositeProductId;// product id
    private String name;
    private Double price;
    private String saleOff;
    private Boolean isShowDeal;//是否展示价格折扣
    private String designFeatures;
    private String brief;
    private String experienceAddress;
    @JsonIgnore
    private Long buildingId;//小区ID
    private String pictureUrlOriginal;// bean array
    
    

    private String summary;

    private String summary2;
    private double latitude;
    private double longitude;

    private String fullView3d;
    
    private String designerImg;//设计师头像

    private String designerName;
    private Long designerId;//设计师Id

    @JsonIgnore(value = true)
    private Integer room;
    @JsonIgnore(value = true)
    private Integer living;
    @JsonIgnore(value = true)
    private Integer balcony;
    @JsonIgnore(value = true)
    private Integer toilet;
    @JsonIgnore(value = true)
    private Integer kitchen;

    private Integer size;

    private String style;

    private Integer count;
    
    private Long saleCount;


    public String getSummary() {

        StringBuffer buffer = new StringBuffer("");
        if (room != null && room != 0){
            buffer.append(room).append("室");
        }
        if (living != null && living != 0){
            buffer.append(living).append("厅");
        }
        if (toilet != null && toilet != 0){
            buffer.append(toilet).append("卫");
        }
        buffer.append(" | ");
        if (size!=null && size != 0) {
            buffer.append(size).append("平米").append(" | ");
        }
        if (count != null && count != 0) {
            buffer.append(count).append("件套").append(" | ");
        }
        if(!StringUtil.isNullOrEmpty(style)){
            buffer.append(style);
        }
        
       
        summary = buffer.toString();
        if (summary.endsWith(" | ")) {
            summary = summary.substring(0, summary.length() - 3);
        }

        return summary;
    }

    public Integer getCount() {
        if(null == count){
        	return 0;
        }
    	return count;
    }

	public String getDesignerImg() {
		if(null != designerImg && designerImg.indexOf("default.png")>-1){
			return null;
		}
		return designerImg;
	}

	public String getSummary2() {
        StringBuffer buffer = new StringBuffer("");
        if (room != null && room != 0){
            buffer.append(room).append("室");
        }
        if (living != null && living != 0){
            buffer.append(living).append("厅");
        }
        if (toilet != null && toilet != 0){
            buffer.append(toilet).append("卫");
        }
        summary2 = buffer.toString();
        return summary2;
	}
}
