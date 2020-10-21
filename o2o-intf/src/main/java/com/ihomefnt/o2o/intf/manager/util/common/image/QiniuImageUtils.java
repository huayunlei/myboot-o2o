package com.ihomefnt.o2o.intf.manager.util.common.image;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.Watermark;
import com.ihomefnt.zeus.finder.ServiceCaller;
import com.qiniu.api.net.EncodeUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 七牛图片处理工具
 *
 * @author Charl
 */
public class QiniuImageUtils {

    /**
     * 压缩图片，进行等比缩放，不裁剪
     * mode 4
     */
    public static String compressImageAndSamePic(String imageUrl, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            if (height == 100 && width == 100) {
                return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_100);
            }
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/4");
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 压缩图片，进行等比缩放，不裁剪
     * mode 2
     */
    public static String compressImageAndSamePicTwo(String imageUrl, Integer width, Integer height) {
        if (imageUrl == null || imageUrl.trim().length() < 1) {
            return null;
        }
        if(width == null){
            width=750;
        }
        if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            if( height==100 && width==100 ){
                return AliImageUtil.imageCompress(imageUrl,2,width,ImageConstant.SIZE_100);
            }
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        if (imageUrl.indexOf("?") > -1) {//切过图
            return sb.toString();
        }
        sb.append("?imageView2/2");
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 压缩图片，进行等比缩放，不裁剪
     * mode 2
     */
    public static String compressImageAndSamePicTwo(String imageUrl, int width, int height, int quality) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        if (imageUrl.indexOf("?") > -1) {
            //防止重复切图
            return imageUrl;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/2");
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        if (quality > 0) {
            sb.append("/q/" + quality);
        }
        return sb.toString();
    }

    /**
     * 压缩图片， 进行等比缩放，居中裁剪
     * mode 1
     */
    public static String compressImageAndDiffPic(String imageUrl, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/1");
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 压缩图片（压缩质量为20），居中裁剪
     *
     * @param imageUrl 七牛图片url
     * @return
     */
    public static String compressImage(String imageUrl) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, null, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/1/q/" + QiniuImageQuality.LOW.getQuality());
        return sb.toString();
    }

    /**
     * 压缩图片（压缩质量为40），居中裁剪
     *
     * @param imageUrl 七牛图片url
     * @return
     */
    public static String compressImage_40(String imageUrl) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, null, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/1/q/" + QiniuImageQuality.LOW_1.getQuality());
        return sb.toString();
    }

    /**
     * 详情，不裁剪
     *
     * @param imageUrl
     * @return
     */
    public static String compressDetailImage(String imageUrl) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, null, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/2/q/" + QiniuImageQuality.LOW.getQuality());
        return sb.toString();
    }

    /**
     * 根据指定压缩质量进行压缩，居中裁剪
     *
     * @param imageUrl
     * @param quality
     * @return
     */
    public static String compressImage(String imageUrl, QiniuImageQuality quality) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, null, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/1/q/" + quality.getQuality());
        return sb.toString();
    }

    /**
     * 指定宽、高进行切图
     *
     * @param imageUrl
     * @param width
     * @param height
     * @return
     */
    public static String compressImage(String imageUrl, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 居中裁剪
     *
     * @param imageUrl 七牛图片url
     * @param quality  压缩质量
     * @param width    压缩宽度
     * @param height   压缩高度
     * @return
     */
    public static String compressImage(String imageUrl, QiniuImageQuality quality, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        if (null != quality) {
            sb.append("?imageView2/1/q/" + quality.getQuality());
        } else {
            sb.append("?imageView2/1/q/" + QiniuImageQuality.LOW.getQuality());
        }
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 详情 ，不裁剪
     *
     * @param imageUrl
     * @param quality
     * @param width
     * @param height
     * @return
     */
    public static String compressDetailImage(String imageUrl, QiniuImageQuality quality, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        if (null != quality) {
            sb.append("?imageView2/2/q/" + quality.getQuality());
        } else {
            sb.append("?imageView2/2/q/" + QiniuImageQuality.LOW.getQuality());
        }
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * ，不裁剪
     *
     * @param imageUrl
     * @param width
     * @param height
     * @return
     */
    public static String compressProductImage(String imageUrl, int width, int height) {
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return null;
        } else if (AliImageConstants.SOURCE_ALI.equals(ImageUtil.getImageSource(imageUrl))) {
            return AliImageUtil.imageCompress(imageUrl, 2, width, ImageConstant.SIZE_MIDDLE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        sb.append("?imageView2/4");
        if (width > 0) {
            sb.append("/w/" + width);
        }
        if (height > 0) {
            sb.append("/h/" + height);
        }
        return sb.toString();
    }

    /**
     * 获取图片高度
     *
     * @param imageUrl 七牛图片url
     * @return
     */
    public static long getImageHeight(String imageUrl) {
        Map<String, Object> imageSize = getImageSize(imageUrl);
        int height = 0;
        if (imageSize.get("height") != null) {
            height = (int) imageSize.get("height");
        }
        return height;
    }

    /**
     * 获取图片宽度
     *
     * @param imageUrl 七牛图片url
     * @return
     */
    public static long getImageWidth(String imageUrl) {
        Map<String, Object> imageSize = getImageSize(imageUrl);
        long width = 0;
        if (imageSize.get("width") != null) {
            width = (long) imageSize.get("width");
        }
        return width;
    }

    /**
     * 获取图片大小
     *
     * @param url
     * @return
     */
    public static Map<String, Object> getImageSize(String url) {
        return getImageSizeByType(url, "?imageInfo", null);
    }

    /**
     * 获取图片大小
     *
     * @param url
     * @return
     */
    public static Picture getImageSize(String url, ServiceCaller serviceCaller) {
        Picture picture = null;
        Map<String, Object> picMap = null;
        try {
            picMap = (Map<String, Object>) serviceCaller.get("", url.concat("?imageInfo"), null,
                    Map.class);
        } catch (Exception e) {
            return null;
        }

        if (picMap == null) {
            return null;
        }
        String value = (String) picMap.get("orientation");
        if (StringUtils.isNotBlank(value)) {
            // 图片发生旋转90度,宽高需要交换
            try {
                Integer width = (Integer) picMap.get("width");
                Integer height = (Integer) picMap.get("height");
                if (value.equals("Left-top") || value.equals("Left-bottom") || value.equals("Right-top")
                        || value.equals("Right-bottom")) {
                    // {"width":3125,"height":4607,"orientation":"Left-bottom"}
                    picture = new Picture(url, height, width);
                } else {
                    picture = new Picture(url, width, height);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return picture;
    }

    /**
     * 获取图片大小
     *
     * @param url
     * @return
     */
    public static Map<String, Object> getImageSizeByType(String url, String type, ServiceCaller serviceCaller) {
        Map<String, Object> json2map = new HashMap<String, Object>();
        if (serviceCaller == null) {
            StringBuilder json = new StringBuilder();
            try {
                URL imageUrl = new URL(url + type);
                URLConnection yc = imageUrl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "utf-8"));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
                json2map = JsonUtils.json2map(json.toString());
                String value = (String) json2map.get("orientation");
                if (StringUtils.isNotBlank(value)) {
                    // 图片发生旋转90度,宽高需要交换
                    if (value.equals("Left-top") || value.equals("Left-bottom") || value.equals("Right-top")
                            || value.equals("Right-bottom")) {
                        // {"width":3125,"height":4607,"orientation":"Left-bottom"}
                        Integer width = (Integer) json2map.get("width");
                        Integer height = (Integer) json2map.get("height");
                        json2map.put("width", height);
                        json2map.put("height", width);
                    }
                }
            } catch (MalformedURLException e) {

            } catch (IOException e) {

            } catch (Exception e) {

            }
        } else {
            Picture picture = AppRedisUtil.getRedisImageSize(url, serviceCaller);
            if (picture != null) {
                json2map.put("width", picture.getWidth());
                json2map.put("height", picture.getHeight());
            }
        }

        return json2map;
    }

    /**
     * 去掉图片列表中空图片url
     *
     * @param sourceImages 图片json
     * @return
     */
    public static List<String> removeEmptyStr(String sourceImages) {
        List<String> strResponseList = new ArrayList<String>();
        if (StringUtils.isNotBlank(sourceImages)
                && sourceImages.contains("]")
                && sourceImages.contains("[")) {
            JSONArray jsonArray = JSONArray.fromObject(sourceImages);
            List<String> strList = (List<String>) JSONArray.toList(jsonArray, String.class);
            if (null != strList && strList.size() > 0) {
                for (String str : strList) {
                    if (null != str && !"".equals(str)) {
                        strResponseList.add(str);
                    }
                }
            }
        }
        return strResponseList;
    }

    /**
     * 图片添加水印
     *
     * @param imageUrl       图片地址
     * @param waterImageUrl  水印图片地址
     * @param dissolve       透明度，取值范围1-100，默认值为100（完全不透明）
     * @param gravity        水印位置，默认值为SouthEast（右下角）
     * @param distanceX      横轴边距，单位:像素(px)，默认值为10
     * @param distanceY      纵轴边距，单位:像素(px)，默认值为10
     * @param watermarkScale 水印图片自适应原图的短边比例，ws的取值范围为0-1
     * @return 参考文档：https://developer.qiniu.com/dora/manual/1316/image-watermarking-processing-watermark
     */
    public static String watermark(String imageUrl, String waterImageUrl, int dissolve, String gravity, int distanceX, int distanceY, double watermarkScale) {
        StringBuilder sb = new StringBuilder();
        sb.append(imageUrl);
        //水印图片
        if (StringUtils.isNotBlank(waterImageUrl)) {
            //判断是否添加过切图参数
            if (imageUrl.contains("?imageView")) {
                sb.append("|watermark/1/image/" + EncodeUtils.urlsafeEncode(waterImageUrl));
            } else {
                sb.append("?watermark/1/image/" + EncodeUtils.urlsafeEncode(waterImageUrl));
            }

            //透明度，取值范围1-100，默认值为100（完全不透明）
            sb.append("/dissolve/" + dissolve);

            //水印位置，默认值为SouthEast（右下角）
            if (StringUtils.isNotBlank(gravity)) {
                sb.append("/gravity/" + gravity);
            }

            //横轴边距，单位:像素(px)，默认值为10
            if (distanceX > 0) {
                sb.append("/dx/" + distanceX);
            }

            //纵轴边距，单位:像素(px)，默认值为10
            if (distanceY > 0) {
                sb.append("/dy/" + distanceY);
            }

            //水印图片自适应原图的短边比例，ws的取值范围为0-1
            if (watermarkScale > 0) {
                sb.append("/ws/" + watermarkScale);
            }
        }

        return sb.toString();
    }

    /**
     * 获取图片大小
     *
     * @param url
     * @return
     */
    public static Picture getImageSizeNew(String url, ServiceCaller serviceCaller) {
        Picture picture = null;
        Map<String, Object> picMap = (Map<String, Object>) serviceCaller.get("", url, null, Map.class);

        if (picMap == null) {
            return null;
        }

        String value = (String) picMap.get("orientation");
        Integer width = (Integer) picMap.get("width");
        Integer height = (Integer) picMap.get("height");

        // 图片发生旋转90度,宽高需要交换
        if (StringUtils.isNotBlank(value) && (value.equals("Left-top") || value.equals("Left-bottom") || value.equals("Right-top")
                || value.equals("Right-bottom"))) {
            picture = new Picture(url, height, width);
        } else {
            picture = new Picture(url, width, height);
        }

        return picture;
    }


    /**
     * 富文本中图片压缩
     * mode 2
     */
    public static String compressdocumentBodyImage(String strBody, int width, int height) {

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
            sb.append(imgUrl);
            sb.append("?imageView2/2");
            if (width > 0) {
                sb.append("/w/" + width);
            }
            if (height > 0) {
                sb.append("/h/" + height);
            }
            element.attr("src", sb.toString());
        }
        return strBody = bodyDoc.html();


    }

    /**
     * 设置文字水印参数
     *
     * @param text      水印文字内容（经过URL安全的Base64编码）
     * @param font      水印文字字体（经过URL安全的Base64编码），默认为黑体  注意：中文水印必须指定中文字体
     * @param fontsize  水印文字大小，单位: 缇 ，等于1/20磅，默认值是240缇
     * @param fill      水印文字颜色，RGB格式，可以是颜色名称（例如 red）或十六进制（例如 #FF0000）默认为黑色。经过URL安全的Base64编码
     * @param dissolve  透明度，取值范围1-100，默认值100（完全不透明）
     * @param gravity   水印位置，参考水印位置参数表，默认值为SouthEast（右下角）
     * @param distanceX 横轴边距，单位:像素(px)，默认值为10
     * @param distanceY 纵轴边距，单位:像素(px)，默认值为10
     * @return
     */
    private static String setTextWaterMarkParams(String text, String font, int fontsize, String fill, int dissolve, String gravity, int distanceX, int distanceY) {
        StringBuilder sb = new StringBuilder();

        //水印文字内容
        if (StringUtils.isNotBlank(text)) {
            sb.append(EncodeUtils.urlsafeEncode(text));
        }

        //文字字体
        if (StringUtils.isNotBlank(font)) {
            sb.append("/font/" + EncodeUtils.urlsafeEncode(font));
        }

        //文字大小
        if (fontsize > 0) {
            sb.append("/fontsize/" + fontsize);
        }

        //文字颜色
        if (StringUtils.isNotBlank(fill)) {
            sb.append("/fill/" + EncodeUtils.urlsafeEncode(fill));
        }

        //透明度，取值范围1-100，默认值为100（完全不透明）
        if (dissolve > 0) {
            sb.append("/dissolve/" + dissolve);
        }

        //水印位置，默认值为SouthEast（右下角）
        if (StringUtils.isNotBlank(gravity)) {
            sb.append("/gravity/" + gravity);
        }

        //横轴边距，单位:像素(px)，默认值为10
        if (distanceX > 0) {
            sb.append("/dx/" + distanceX);
        }

        //纵轴边距，单位:像素(px)，默认值为10
        if (distanceY > 0) {
            sb.append("/dy/" + distanceY);
        }

        return sb.toString();
    }

    public static String addTextToImage(Watermark watermark) {
        return setTextWaterMarkParams(watermark.getText(), watermark.getFont(), watermark.getFontsize(), watermark.getFill(), watermark.getDissolve(), watermark.getGravity(), watermark.getDistanceX(), watermark.getDistanceY());
    }


    /**
     * 去除原有切图参数（请使用AliImageUtil.getImageDeCompress）
     *
     * @param url
     * @return
     */
    @Deprecated
    public static String rmArgs(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        } else if (url.indexOf("!") != -1) {
            url = url.substring(0, url.indexOf("!"));
        }
        return url;
    }

    /**
     * 去除原有切图参数重新切图（请使用AliImageUtil.getImageDeCompress）
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static String rmArgsAndCompressImage(String url, int width, int height) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String imageUrl = rmArgs(url);
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        }
        return compressImageAndSamePicTwo(imageUrl, width, height);
    }

}
