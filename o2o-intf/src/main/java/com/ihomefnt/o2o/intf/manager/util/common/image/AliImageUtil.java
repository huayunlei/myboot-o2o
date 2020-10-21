package com.ihomefnt.o2o.intf.manager.util.common.image;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.Watermark;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.secure.Base64Utils;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author xiamingyu
 * @date 2018/10/15
 *
 * 图片处理工具类
 */

public class AliImageUtil {



    /**
     * @param url 原图url
     * @param size 尺寸大小
     * @return
     */
    public static final String imageCompress(String url,Integer osType,Integer width,Integer size){
        if(StringUtil.isNullOrEmpty(url)) {
            return url;
        } else if (AliImageConstants.SOURCE_QINIU.equals(ImageUtil.getImageSource(url))) {
            if(null == width){
                width = 750;
            }
            return QiniuImageUtils.compressImageAndSamePicTwo(url,width,-1,80);
        }

        String compressUrl = url;
        if(StringUtils.isBlank(url)||url.indexOf("!")>-1){//防止重复切图
            return url;
        }
        if(osType == null || osType == 0){
            osType = 1;
        }
        if(width == null){
            width = 750;
        }
        if(size == null){
            size = ImageConstant.SIZE_SMALL;
        }

        ImageCompressParam param = getParamByWidth(osType,width,size);
        if (null == param) {
            return compressUrl;
        }

        return getCompressString2(param, url);
    }

    private static String getCompressString2(ImageCompressParam param, String url) {
        String compressUrl = url;
        switch (param.getMode()){
            case 1:
            case 2:
                switch (param.getSize()){
                    case 1:
                        switch (param.getResolution()){
                            //等比缩放，小尺寸，小分辨率
                            case 1:
                                compressUrl = url.concat("!L-SMALL");
                                break;
                            //等比缩放，小尺寸，中分辨率
                            case 2:
                                compressUrl = url.concat("!M-SMALL");
                                break;
                            //等比缩放，小尺寸，高分辨率
                            case 3:
                                compressUrl = url.concat("!H-SMALL");
                                break;
                        }
                        break;
                    case 2:
                        switch (param.getResolution()){
                            //等比缩放，中尺寸，小分辨率
                            case 1:
                                compressUrl = url.concat("!L-MIDDLE");
                                break;
                            //等比缩放，中尺寸，中分辨率
                            case 2:
                                compressUrl = url.concat("!M-MIDDLE");
                                break;
                            //等比缩放，中尺寸，高分辨率
                            case 3:
                                compressUrl = url.concat("!H-MIDDLE");
                                break;
                        }
                        break;
                    case 3:
                        switch (param.getResolution()){
                            //等比缩放，大尺寸，小分辨率
                            case 1:
                                compressUrl = url.concat("!L-BIG");
                                break;
                            //等比缩放，大尺寸，中分辨率
                            case 2:
                                compressUrl = url.concat("!M-BIG");
                                break;
                            //等比缩放，大尺寸，高分辨率
                            case 3:
                                compressUrl = url.concat("!H-BIG");
                                break;
                        }
                        break;
                    case 4:
                        compressUrl = url.concat("!thum-100x100");
                        break;
                    default:
                }
                break;
            default:
        }
        return compressUrl;
    }

    /**
     * 获取裁剪级别，画屏根据切图参数获取私有图片
     * @param osType
     * @param width
     * @param size
     * @return
     */
    public static final String getImageCompress(Integer osType,Integer width,Integer size){
        if(osType == null || osType == 0){
            osType = 1;
        }
        if(width == null){
            width = 750;
        }
        if(size == null){
            size = ImageConstant.SIZE_SMALL;
        }

        ImageCompressParam param = getParamByWidth(osType,width,size);

        if (null == param) {
            return null;
        }

        return getCompressString(param);
    }

    private static String getCompressString(ImageCompressParam param) {
        String compressString="";
        switch (param.getMode()){
            case 1:
            case 2:
                switch (param.getSize()){
                    case 1:
                        switch (param.getResolution()){
                            //等比缩放，小尺寸，小分辨率
                            case 1:
                                compressString = "L-SMALL";
                                break;
                            //等比缩放，小尺寸，中分辨率
                            case 2:
                                compressString = "M-SMALL";
                                break;
                            //等比缩放，小尺寸，高分辨率
                            case 3:
                                compressString = "H-SMALL";
                                break;
                        }
                        break;
                    case 2:
                        switch (param.getResolution()){
                            //等比缩放，中尺寸，小分辨率
                            case 1:
                                compressString = "L-MIDDLE";
                                break;
                            //等比缩放，中尺寸，中分辨率
                            case 2:
                                compressString = "M-MIDDLE";
                                break;
                            //等比缩放，中尺寸，高分辨率
                            case 3:
                                compressString = "H-MIDDLE";
                                break;
                        }
                        break;
                    case 3:
                        switch (param.getResolution()){
                            //等比缩放，大尺寸，小分辨率
                            case 1:
                                compressString = "L-BIG";
                                break;
                            //等比缩放，大尺寸，中分辨率
                            case 2:
                                compressString = "M-BIG";
                                break;
                            //等比缩放，大尺寸，高分辨率
                            case 3:
                                compressString = "H-BIG";
                                break;
                        }
                        break;
                    case 4:
                        compressString = "thum-100x100";
                        break;
                }
                break;
            default:
        }
        return compressString;
    }


    /**
     * 富文本中图片压缩
     * mode 2
     */
    public static String compressdocumentBodyImage(String strBody, Integer width) {

        Document doc = Jsoup.parse(strBody);
        doc.outputSettings().prettyPrint(false);
        Element bodyDoc = doc.body();

        Elements pngs = doc.select("img[src]");
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            if (StringUtil.isNullOrEmpty(imgUrl)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(AliImageUtil.imageCompress(imgUrl,2,width, ImageConstant.SIZE_MIDDLE));
            element.attr("src", sb.toString());
        }
        return bodyDoc.html();


    }


    /**
     * 根据系统类型、屏幕宽度和所需尺寸定义切图参数
     * @param osType 系统类型1-ios，2-android
     * @param width 屏幕宽度
     * @param size 所需尺寸
     * @return
     */
    public static final ImageCompressParam getParamByWidth(Integer osType,Integer width,Integer size){
        if(width==null || width<0){
            return null;
        }
        ImageCompressParam param = new ImageCompressParam();
        param.setMode(ImageConstant.MODE_COMPRESS);
        param.setSize(size);
        switch (osType){
            case 1:
                if(width<=750){
                    param.setResolution(ImageConstant.RESOLUTION_MIDDLE);
                }else {
                    param.setResolution(ImageConstant.RESOLUTION_HIGH);
                }
                break;
            case 2:
                if(width<=720){
                    param.setResolution(ImageConstant.RESOLUTION_LOW);
                }else if(width<=1080){
                    param.setResolution(ImageConstant.RESOLUTION_MIDDLE);
                }else {
                    param.setResolution(ImageConstant.RESOLUTION_HIGH);
                }
                break;
            default:
        }
        return param;
    }

    public static final Picture getImageParam(String url, ServiceCaller serviceCaller){
        Picture picture = null;
        if (AliImageConstants.SOURCE_QINIU.equals(ImageUtil.getImageSource(url))) {
            picture = QiniuImageUtils.getImageSize(url, serviceCaller);
        } else {
            try {
                ResponseVo responseVo = serviceCaller.post("unifyfile.file.queryFileInfo", url, ResponseVo.class);
                if(responseVo !=null && responseVo.getCode().equals(200)){
                    PictureDto pictureDto = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),PictureDto.class);
                    if(pictureDto!=null){
                        picture = new Picture();
                        picture.setUrl(url);
                        picture.setWidth(pictureDto.getWidth());
                        picture.setHeight(pictureDto.getHeight());
                        picture.setAspectRatio(picture.getRatio());
                    }
                }
            } catch (Exception e) {
                picture = null;
            }
        }
        return picture;
    }

    /**
     * 去除切图参数
     * @param picUrl
     * @return
     */
    public static final String getImageDeCompress(String picUrl){
        if (StringUtils.isBlank(picUrl)) {
            return null;
        }
        if (picUrl.indexOf("?") != -1) {
            picUrl = picUrl.substring(0, picUrl.indexOf("?"));
        } else if (picUrl.indexOf("!") != -1) {
            picUrl = picUrl.substring(0, picUrl.indexOf("!"));
        }
        return picUrl;
    }

    /**
     * 设置文字水印参数
     *
     * @param text      水印文字内容（经过URL安全的Base64编码）
     * @param font      水印文字字体（经过URL安全的Base64编码），默认为黑体  注意：中文水印必须指定中文字体
     * @param fontsize  水印文字大小，取值范围：(0，1000]，默认值是40
     * @param fill      水印文字颜色，RGB格式，可以是颜色名称（例如 red）或十六进制（例如 FF0000）默认为黑色
     * @param dissolve  透明度，取值范围0-100，默认值100（完全不透明）
     * @param gravity   水印位置，参考水印位置参数表，默认值为SouthEast（右下角）
     * @param distanceX 横轴边距，单位:像素(px)，默认值为10
     * @param distanceY 纵轴边距，单位:像素(px)，默认值为10
     * @return
     */
    private static String setTextWaterMarkParams(String text, String font, int fontsize, String fill, int dissolve, String gravity, int distanceX, int distanceY) {
        StringBuilder sb = new StringBuilder("?x-oss-process=image/watermark");

        //水印文字内容
        if (StringUtils.isNotBlank(text)) {
            try {
                sb.append(",text_" + Base64Utils.encode(text.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //文字字体
        if (StringUtils.isNotBlank(font)) {
            try {
                sb.append(",type_" + Base64Utils.encode(font.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //文字大小
        if (fontsize > 0) {
            sb.append(",size_" + fontsize);
        }

        //文字颜色
        if (StringUtils.isNotBlank(fill)) {
            sb.append(",color_" + (fill.startsWith("#") ? fill.substring(1) : fill));
        }

        //透明度，取值范围0-100，默认值为100（完全不透明）
        if (dissolve > 0) {
            sb.append(",shadow_" + dissolve);
        }

        //水印位置，默认值为SouthEast（右下角）
        if (StringUtils.isNotBlank(gravity)) {
            sb.append(",g_" + gravity);
        }

        //横轴边距，单位:像素(px)，默认值为10
        if (distanceX > 0) {
            sb.append(",x_" + distanceX);
        }

        //纵轴边距，单位:像素(px)，默认值为10
        if (distanceY > 0) {
            sb.append(",y_" + distanceY);
        }

        return sb.toString();
    }

    public static String addTextToImage(Watermark watermark) {
        return setTextWaterMarkParams(watermark.getText(), watermark.getFont(), watermark.getFontsize(), watermark.getFill(), watermark.getDissolve(), watermark.getGravity(), watermark.getDistanceX(), watermark.getDistanceY());
    }

}
