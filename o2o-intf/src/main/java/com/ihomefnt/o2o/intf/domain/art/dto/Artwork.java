package com.ihomefnt.o2o.intf.domain.art.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Data
public class Artwork implements Serializable{

	private static final long serialVersionUID = -5362997345616322397L;
	private long artWorkId; //艺术品id

	private String name; //艺术品名称

	private String headImg; //艺术品头图
	
	private ImageEntity headImgObj; //艺术品头图
	
	private int width;  //头图宽度
	
	private int height;  //头图高度

	private String productId;//新版本艾商城商品id

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private int type; //艺术品大的分类：绘画/家具用品
	
	private String category; //艺术品二级分类,例：油画
	
	private String size; //艺术品大小,例：30x20cm
	
	private BigDecimal price; //艺术品价格
	
	private String creationTime = "当代"; //艺术品创作年代,例：2016
	
	private String artistName; //艺术家姓名,例：二狗
	
	private long artistArtworkCount; //艺术家名下艺术品数量
	
	private long artistArtworkViewCount; //艺术家名下艺术品浏览数量
	
	private String avast; //艺术家头像
	
	private long artistId; //艺术家id
	
	private String style; //艺术品风格：写实、抽象、当代
	
	private String content; //艺术品内容:人物、风景
	
	private long stock; //艺术品库存数量
	
	private String description; //艺术品描述
	
	private int imageCount; //图片数量
	
	private long favirate; //艺术品喜欢的次数
	
	private boolean hasFavirate;  //该用户是否已喜欢改艺术品
	
	private long share; //艺术品分享的次数
	
	private long scan; //艺术品浏览的次数
	
	private List<ArtSpaceItem> spaceItems; //艺术品空间名称列表 
	
	private int artistType;//艺术家类型 1.艺术家  2.机构
	
	private String ajbPrice; //艾积分金额
}
