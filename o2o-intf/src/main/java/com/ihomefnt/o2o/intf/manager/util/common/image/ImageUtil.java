/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.util.common.image;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
public class ImageUtil {
    @SuppressWarnings({ "unchecked", "deprecation" })
    public static List<String> removeEmptyStr(String sourceImages) {
    	List<String> strResponseList = new ArrayList<String>();
    	if(StringUtils.isNotBlank(sourceImages) 
    			&& sourceImages.contains("]")
    			&& sourceImages.contains("[")){
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
     * 匹配图片来源正则表达式
     */
    private static String REGULAR_EXPRESSION_IMAGE_SOURCE = "(img\\d+.ihomefnt.com)|(aliyuncs)|(static.ihomefnt.com)|(common.ihomefnt.com)";
    
    /**根据url解析图片来源
     * @param url
     * @return
     */
    public static String getImageSource(String url){
    	Pattern p= Pattern.compile(REGULAR_EXPRESSION_IMAGE_SOURCE); 
    	Matcher m=p.matcher(url); 
        //TODO 判断七牛还是阿里云
        return  m.find() ? AliImageConstants.SOURCE_ALI : AliImageConstants.SOURCE_QINIU;

    }
    
//    public static void main(String[] args) {
//		System.out.println(ImageUtil.getImageSource("img.ihomefnt.com"));
//		System.out.println(ImageUtil.getImageSource("img12.ihomefnt.com"));
//		System.out.println(ImageUtil.getImageSource("http://img12.ihomefnt.com"));
//		System.out.println(ImageUtil.getImageSource("http://img12.ihomefnt.com.8237idsjflkl"));
//		System.out.println(ImageUtil.getImageSource("aliyuncs"));
//		System.out.println(ImageUtil.getImageSource("http://aliyuncs.329ofpfsfj"));
//		System.out.println(ImageUtil.getImageSource("img.aliyuncs"));
//
//	}

    /**
     * 最大公约数
     * @param width
     * @param height
     * @return
     */
    public static int getCommonDivisor(int width, int height) {
        int max, min;
        max = (width > height) ? width : height;
        min = (width < height) ? width : height;

        if (max % min != 0) {
            return getCommonDivisor(min, max % min);
        } else
            return min;

    }

    public static void main(String[] args) {
        int commonDivisor=getCommonDivisor(1280,720);
        System.out.println(1280/commonDivisor+":"+720/commonDivisor);
    }
}
