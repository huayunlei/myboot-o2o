package com.ihomefnt.o2o.intf.manager.util.unionpay;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hvk687 on 9/15/15.
 */
public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        /**
         * get real ip address
         * request.getHeader("X-Real-IP"); // 获取真实地址
         * request.getHeader("x-forwarded-for").split(","); //
         * by jerfan cang
         * on 2018-08-16
         */
        String ip = null;
        if(null != request){
            // 获取真实地址
            ip= request.getHeader("X-Real-IP");
            if(null == ip){
                /**
                 * 获取一串地址，如果经过代理则会在源地址后面追加代理地址，以英文半角逗号分割
                 *  第一位是最原始的地址，默认情况下第一个和X-Real-IP是一样的
                 */
                String ipStr = request.getHeader("x-forwarded-for");
                if(null != ipStr){
                    String [] ips = ipStr.split(",");
                    if(null != ips && ips.length>0){
                        ip = ips[0];
                    }
                }
            }
        }
        /**
         * end 2018-08-16
         */

        /**
         * 注释如下代码
         * by jerfan cang
         * on 2018-08-16
         */
        /*String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }*/

        /**
         * request 隐藏太深了 默认返回 unknown
         * by jerfan cang
         * on 2018-08-16
         */
        if(null == ip){
            ip = "127.0.0.0";
        }
        /**
         * end 2018-08-16
         */
        return ip;
    }
    
    
    /**
    *
    */
   public static String getLocationCityBySina(String ip) {
       String city = "";
       
       if (null == ip || "".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
           city = "南京";
       } else {
           HttpClient httpclient = new DefaultHttpClient();
           HttpParams params = httpclient.getParams();
           HttpConnectionParams.setConnectionTimeout(params, 1000);
           HttpConnectionParams.setSoTimeout(params, 1000);
           HttpPost httpPost = new HttpPost("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=JSON&ip="+ip);
           try {
               HttpResponse httpResponse = httpclient.execute(httpPost);
               if (httpResponse.getStatusLine().getStatusCode() == 200) {
                   String retSrc = EntityUtils.toString(httpResponse.getEntity()); // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                   JSONObject object = JSONObject.fromObject(retSrc);
                   city =(String) object.get("city");
                 
                   if (city == null) {
                        city = "南京";    //parse error, set default value
                   } 
               }
           } catch (Exception e) {
               city = "南京";
               e.printStackTrace();
           } finally {
               httpPost.releaseConnection();
               httpclient.getConnectionManager().shutdown();
           }
       }
       
       return city;
   }
    
    
}
