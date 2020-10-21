package com.ihomefnt.o2o.intf.domain.experiencestore.dto;

import java.io.Serializable;

import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import lombok.Data;

@Data
public class BuildingExpStore implements Serializable{

	private static final long serialVersionUID = -817427075498929130L;
	private Long expStoreId;
    private String expStoreName;//体验馆名称
    private String expStoreImage;//体验馆头图
    private String businessTime;//营业时间
    private String hqPhone;//总部电话
    private String smPhone;//店长电话
    private String location;//体验馆地址
    private String latitude;//纬度
    private String longitude;//经度
    
    private String[] expStoreImages;//体验馆图片
    
    private String images;//体验馆图片
    
    private Integer suitNum;								//该体验店所包含的样板间的数量
	private Integer productNum;								//该体验店所包含的套装所包含的所有商品的数量
	private Integer suitStyleNum;							//该样板间所包含的所有的套装的样式的数量
	private String experUrl;

    public String getExpStoreImage() {
        if(null != images && !"".equals(images)){
            String[] tempArr = images.split(",");
            if(null != tempArr && tempArr.length > 0){
//                return tempArr[0]+"?imageView2/1/w/458/h/342";
                return QiniuImageUtils.compressImage(tempArr[0], 458, 342);
            }
        }
        return null;
    }
    public String[] getExpStoreImages() {
        if(null != images && !"".equals(images)){
            String[] strArr = images.split(",");
            for(int i = 0; i < strArr.length; i++){
                strArr[i] = strArr[i] + "?imageView2/1/w/988/h/598";
            }
            return strArr;
        }
        return null;
    }
}
