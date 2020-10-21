package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shirely_geng on 15-3-5.
 */
@Api(value="M站套装API",description="M站套装老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiQRCodeController {
	private static final Logger LOG = LoggerFactory.getLogger(MapiQRCodeController.class);
	
    @Autowired
    private WebConfig webConfig;
    @Autowired
	private BundleService bundleService;

//    private static String AGENT_QQ = "qq";
    private static String AGENT_WEIXIN = "micromessenger";
    private static final String ANDROID = "android";
    private static final String IPHONE = "iphone";

    /**
     * 套装详情扫描
     *
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/print/{suitid:suit-\\d+}")
    public ModelAndView QRSuit(Model model, @PathVariable String suitid, HttpServletRequest request) {


        /**
         * replace suit- to product id
         */
        String productId = suitid.replaceFirst("suit-", "");
        String url = webConfig.HOST + "/suit/" + productId;

//        String ua = request.getHeader("user-agent").toLowerCase();
        String append = "";
        url = url + append;

        return new ModelAndView(new RedirectView(url));
    }
    @RequestMapping(value = "/QR/{pvalue}")
	public ModelAndView ScanningQR(@PathVariable String pvalue, HttpServletRequest request, HttpServletResponse response) {
	  //ger user agent
    String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
    LOG.info("user agent:" + ua);
    LOG.info("pvalue:" + pvalue);

    /**
     * 微信浏览器
     */
    if (ua.indexOf(AGENT_WEIXIN) > 0) {
        if (ua.indexOf(ANDROID) > 0) {
            pvalue = "10000";
            /**
             * tip for user open this download
             */
            return new ModelAndView("download/tip_weixin.ftl");
        } else if (ua.indexOf(IPHONE) > 0) {
            pvalue = "100";
        }
    } else  {
        /**
         * if null, use default, else use
         */
        if (ua.indexOf(ANDROID) > 0) {
                pvalue = "10000";
        } else if (ua.indexOf(IPHONE) > 0) {
            pvalue = "100";
        }
    }
    //get app version information from database
    LOG.info("pvalue:" + pvalue);
    AppVersionDto appVersion = null;
     appVersion = bundleService.getLatestApp(pvalue, null);
    //error view
    if (appVersion == null) {

        Map<String, String> model = new HashMap<String, String>();
        AppVersionDto ios = bundleService.getLatestApp("10000", null);
        model.put("iosurl", ios.getDownload());
        AppVersionDto android = bundleService.getLatestApp("200", null);
        model.put("androidurl",android.getDownload());

        return new ModelAndView("download/error.ftl",model);
    } else {
        LOG.info("downloadurl:" + appVersion.getDownload());
        //redirect to download url
        return new ModelAndView(new RedirectView(appVersion.getDownload()));
    }
}
    
    @RequestMapping("/print/businesscard-1")
    public String QRBusinessCard(Model model) {


        model.addAttribute("downloadurl", Constants.DOWNLOAD_URL);
    	return "product/about.ftl";
    }
}
