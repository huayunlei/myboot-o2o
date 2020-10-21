package com.ihomefnt.o2o.intf.manager.util.common;

/**
 * 水印参数
 * @author ZHAO
 */
public class Watermark {
	//图片地址
	private String imageUrl;

	//水印图片地址
	private String waterImageUrl;

	//透明度，取值范围1-100，默认值为100（完全不透明）
	private int dissolve;

	//水印位置，默认值为SouthEast（右下角）
	private String gravity;

	private int distanceX;//横轴边距，单位:像素(px)，默认值为10
	
	private int distanceY;//纵轴边距，单位:像素(px)，默认值为10
	
	private double watermarkScale;//水印图片自适应原图的短边比例，ws的取值范围为0-1
	
	private String text;//水印文字内容（经过URL安全的Base64编码）
	
	private String font;//水印文字字体（经过URL安全的Base64编码），默认为黑体  注意：中文水印必须指定中文字体
	
	private int fontsize;//水印文字大小，单位: 缇 ，等于1/20磅，默认值是240缇
	
	private String fill;//水印文字颜色，RGB格式，可以是颜色名称（例如 red）或十六进制（例如 #FF0000）默认为黑色。经过URL安全的Base64编码

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getWaterImageUrl() {
		return waterImageUrl;
	}

	public void setWaterImageUrl(String waterImageUrl) {
		this.waterImageUrl = waterImageUrl;
	}

	public int getDissolve() {
		return dissolve;
	}

	public void setDissolve(int dissolve) {
		this.dissolve = dissolve;
	}

	public String getGravity() {
		return gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public int getDistanceX() {
		return distanceX;
	}

	public void setDistanceX(int distanceX) {
		this.distanceX = distanceX;
	}

	public int getDistanceY() {
		return distanceY;
	}

	public void setDistanceY(int distanceY) {
		this.distanceY = distanceY;
	}

	public double getWatermarkScale() {
		return watermarkScale;
	}

	public void setWatermarkScale(double watermarkScale) {
		this.watermarkScale = watermarkScale;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}
	
}
