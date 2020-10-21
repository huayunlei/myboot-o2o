package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.bundle.dto.AppDownloadLogDto;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppDownloadRequest;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppDownloadResponse;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.util.common.OSType;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AppDownloadController,
 * is designed to expose interface for web browser's request of downloading
 * app with given partner value.
 * Browser includes: android webkit, ios safari, WeiXin;
 */
@Api(value = "M站APP下载API", description = "M站 APP下载接口", tags = "【M-API】")
@Component
@RequestMapping(value = "/mapi/app")
public class MapiAppDownloadController {

    private static final Logger LOG = LoggerFactory.getLogger(MapiAppDownloadController.class);

    private static final String ANDROID = "android";
    private static final String IPHONE = "iphone";
    private static final String WEIXIN = "micromessenger";
    //1:iPhone客户端，2:Android客户端，3:H5网站，4:PC网站

    @Autowired
    private BundleService bundleService;
    @Autowired
    ThreadPoolTaskExecutor executor;

    private static List<String> P_LIST = new ArrayList<String>();

    static {
        P_LIST.add("100");
        P_LIST.add("200");
        P_LIST.add("10000");
        P_LIST.add("20000");
        P_LIST.add("2000");
        P_LIST.add("2001");
        P_LIST.add("2002");
        P_LIST.add("2003");
        P_LIST.add("2004");
        P_LIST.add("2005");
        P_LIST.add("2006");
        P_LIST.add("2007");
        P_LIST.add("2008");
        P_LIST.add("2009");
        P_LIST.add("2010");
        P_LIST.add("2011");
        P_LIST.add("30001");
        P_LIST.add("3001");
        P_LIST.add("40001");
        P_LIST.add("40002");
        P_LIST.add("40003");
    }

    @RequestMapping(value = "/{pvalue}", method = RequestMethod.GET)
    public ModelAndView downLoadApp(@PathVariable String pvalue,
                                    HttpServletRequest request, HttpServletResponse response) {

        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        LOG.info("user agent:{}", ua);
        String origial = pvalue;
        /**
         * 微信浏览器
         */
        if (ua.contains(WEIXIN) && (ua.contains(ANDROID) || ua.contains(IPHONE))) {
            /**
             * tip for user open this download
             */
            return new ModelAndView("download/tip_weixin.ftl");
        }
        pvalue = getPvalueByUa(pvalue, ua);

        //get app version information from database
        AppVersionDto appVersion = null;
        if (P_LIST.contains(pvalue)) {
            appVersion = bundleService.getLatestApp(pvalue, null);
        }
        //error view
        //1:iPhone客户端，2:Android客户端，3:H5网站，4:PC网站
        // 推广活动app下载统计
        Integer osType = null;
        if (ua.contains(ANDROID)) {
            osType = OSType.ANDROID;
        }
        if (ua.contains(IPHONE)) {
            osType = OSType.IOS;
        }
        AppDownloadLogDto log = new AppDownloadLogDto();
        String ip = IpUtils.getIpAddr(request);
        log.setOsType(osType);
        log.setPvalue(origial);
        log.setActCode(request.getParameter("actCode"));
        log.setIp(ip);
        /**
         * add to db;
         */
        Long logId = bundleService.addDownload(log);
        //update location
        addDownloadToDB(logId, ip);

        if (appVersion == null) {

            Map<String, String> model = new HashMap<String, String>();
            AppVersionDto ios = bundleService.getLatestApp("100", null);
            model.put("iosurl", ios.getDownload());
            AppVersionDto android = bundleService.getLatestApp("200", null);
            model.put("androidurl", android.getDownload());


            return new ModelAndView("download/error.ftl", model);
        } else {
            LOG.info("downloadurl:{}", appVersion.getDownload());
            //redirect to download url
            return new ModelAndView(new RedirectView(appVersion.getDownload()));
        }
    }

    private String getPvalueByUa(String pvalue, String ua) {
        /**
         * if null, use default, else use
         */
        if (ua.contains(ANDROID)) {
            if (StringUtil.isNullOrEmpty(pvalue)) {
                pvalue = "200";
            }
        } else if (ua.contains(IPHONE)) {
            pvalue = "100";
        }

        return pvalue;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<HttpBaseResponse> downLoadApp(@Json AppDownloadRequest appRequest,
                                                        HttpServletRequest request, HttpServletResponse response) {
        //ger user agent
        String ua = appRequest.getUserAgent().toLowerCase();
        String pvalue = appRequest.getPvalue();
        LOG.info("user agent:{}", ua);
        LOG.info("pvalue:{}", pvalue);
        String origial = pvalue;
        AppDownloadResponse appResponse = new AppDownloadResponse();
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        /**
         * 微信浏览器
         */
        if (ua.contains(WEIXIN)) {
            if (ua.contains(ANDROID) || ua.contains(IPHONE)) {
                /**
                 * tip for user open this download
                 */
                appResponse.setType(1);

                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setExt(null);
                baseResponse.setObj(appResponse);
            }

            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Request-Method", "post");
            return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);

        }

        pvalue = getPvalueByUa(pvalue, ua);
        //get app version information from database
        LOG.info("pvalue:{}", pvalue);
        AppVersionDto appVersion = null;
        if (P_LIST.contains(pvalue)) {
            appVersion = bundleService.getLatestApp(pvalue, null);
        }
        //error view
        //1:iPhone客户端，2:Android客户端，3:H5网站，4:PC网站
        // 推广活动app下载统计
        String actCode = request.getParameter("actCode");
        Integer osType = null;
        if (ua.contains(ANDROID)) {
            osType = OSType.ANDROID;
        }
        if (ua.contains(IPHONE)) {
            osType = OSType.IOS;
        }
        AppDownloadLogDto log = new AppDownloadLogDto();
        String ip = IpUtils.getIpAddr(request);
        log.setOsType(osType);
        log.setPvalue(origial);
        log.setActCode(actCode);
        log.setIp(ip);
        /**
         * add to db;
         */
        Long logId = bundleService.addDownload(log);
        LOG.info("add id:{}", logId);
        //update location
        addDownloadToDB(logId, ip);

        if (appVersion == null) {
            Map<String, String> model = new HashMap<String, String>();
            AppVersionDto ios = bundleService.getLatestApp("100", null);
            model.put("iosurl", ios.getDownload());
            appResponse.setIosurl(ios.getDownload());
            AppVersionDto android = bundleService.getLatestApp("200", null);
            model.put("androidurl", android.getDownload());
            appResponse.setAndroidurl(android.getDownload());

            appResponse.setType(2);
            baseResponse.setCode(HttpResponseCode.SUCCESS);
            baseResponse.setExt(null);
            baseResponse.setObj(appResponse);
        } else {
            LOG.info("downloadurl:{}", appVersion.getDownload());

            appResponse.setAppDownloadUrl(appVersion.getDownload());
            appResponse.setType(3);
            baseResponse.setCode(HttpResponseCode.SUCCESS);
            baseResponse.setExt(null);
            baseResponse.setObj(appResponse);
        }

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    public void addDownloadToDB(Long logId, String ip) {
        executor.execute(new dbTask(logId, ip));
    }

    public class dbTask implements Runnable {
        Long logId;
        String ip;

        public dbTask(Long logId, String ip) {
            this.ip = ip;
            this.logId = logId;
        }

        @Override
        public void run() {
            if (StringUtil.isNullOrEmpty(ip)) {
                return;
            }

            try (CloseableHttpClient httpclient = new DefaultHttpClient();) {
                HttpParams params = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 3000);
                HttpConnectionParams.setSoTimeout(params, 3000);
                HttpPost httpPost = new HttpPost("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
                HttpResponse httpResponse = httpclient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                    String retSrc = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject object = JSONObject.fromObject(retSrc);
                    if (object != null) {
                        int code = object.optInt("code");
                        if (code == 0) {
                            JSONObject data = object.getJSONObject("data");
                            String region = data.getString("region");
                            String city = data.getString("city");
                            String location = region + city;
                            bundleService.updateLocation(logId, location);
                        }
                    }
                }
                httpPost.releaseConnection();
            } catch (Exception e) {
                LOG.info("parse download exception :", e);
            }
        }
    }
}
