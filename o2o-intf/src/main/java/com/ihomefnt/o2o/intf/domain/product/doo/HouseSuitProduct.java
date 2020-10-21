package com.ihomefnt.o2o.intf.domain.product.doo;

import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;
@Data
public class HouseSuitProduct {

	private Long houseId;//房屋Id
	private String houseName;//屋型类型
	private String houseSize;//房屋大小
	private Integer count;//件套
	private String style;//件套
	private String name;
	private Long cityCode;
	private Double  price;//价格
	private Double  lowestPrice;//最低价格
	private Double  highestPrice;//最高价格
    private Double deal;//价格折扣
    private Boolean isShowDeal;//是否展示价格折扣
    private Long   compositeProductId;//套装id
    private String pictureUrlHouse;// 户型照片
    private String experienceAddress;//体验地址
    private Double latitude;//维度
    private Double longitude;//经度
    private List<String> pictureUrlOriginal;// 套装图片
    private String summary;//屋型概述
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
    @JsonIgnore(value = true)
    private Integer size;
    @JsonIgnore(value = true)
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
//	        if (kitchen != null && kitchen != 0) {
//	            buffer.append(kitchen).append("厨");
//	        }
//	        if (balcony != null && balcony != 0) {
//	            buffer.append(balcony).append("阳台");
//	        }
	        buffer.append(" | ");
	        if (size!=null && size != 0) {
	            buffer.append(size).append("平米").append(" | ");
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
}
