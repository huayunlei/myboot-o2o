package com.ihomefnt.o2o.intf.domain.experiencestore.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import lombok.Data;

@Data
public class Suit implements Serializable{

	private static final long serialVersionUID = -4469520384166259667L;
	private Long suitId;//套装id
    private String suitName;//套装名称
    private String suitFImages;//套装头图
    private String styleName;//套装风格
    private BigDecimal price;//套装价格
    private Byte offLineExperience;//是否可以线下体验,0:否,1:是
    private Integer suitProductCount;//商品件数
    private String houseName;//户型名称几室几厅几卫
    private BigDecimal size;//面积
    
    private String suitImages;//套装头图
    
    private Double suitPrductPrice;		//套装原价，即各个商品相加的价格
    
    public String getSuitFImages() {
        String tempstr = "";
        if(null != suitImages && !"".equals(suitImages)){
            if(suitImages.contains("[")){
                tempstr = suitImages.replace("[", "");
            }
            if(null != tempstr && !"".equals(tempstr) && tempstr.contains("]")){
                tempstr = tempstr.replace("]", "");
            }
            if(null != tempstr && !"".equals(tempstr) && tempstr.contains("\"\",")){
                tempstr = tempstr.replace("\"\",", "");
            }
            if(null != tempstr && !"".equals(tempstr) && tempstr.contains("\"")){
                tempstr = tempstr.replace("\"", "");
            }
            if(null != tempstr && !"".equals(tempstr)){
                String[] tArr = tempstr.split(",");
                if(null != tArr && tArr.length > 0){
//                    return tArr[0] + "?imageView2/1/w/308/h/223";
                	return QiniuImageUtils.compressImage(tArr[0], 308, 223);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        } else {
            return null;
        }
    }
}
