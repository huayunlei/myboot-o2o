package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpProductMoreSingleRequest150;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 专题页面
 */
@Api(value="M站专题API",description="M站专题老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/topic")
public class MapiTopicController {


    @Autowired
	private BundleService bundleService;
    
    @Autowired
    ProductService productService;
    
    @Autowired
    ProductTypeDao productTypeDao;
    
    @Autowired
    TKDMDao tkdmDao;

//    @Value("@{locations.host}")
//    private String host;

    private static final String IPHONE = "iphone";

    private static final String WEIXIN = "micromessenger";

    @RequestMapping(value = "/499/{pvalue}")
    public String ScanningQR(@PathVariable String pvalue,
                             HttpServletRequest request, HttpServletResponse response) {
		
        if ("1".equals(pvalue)) {
            return "topic/thematic.ftl";
        } else if ("2".equals(pvalue)) {
            return "topic/extremeOutput.ftl";
        } else if ("3".equals(pvalue)) {
            return "topic/extremeService.ftl";
        } else if ("4".equals(pvalue)) {
            return "topic/priceGuarantee.ftl";
        }
        return null;
    }

    @RequestMapping(value = "/coreValues")
    public String coreValues(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "topic/core.ftl";
    }

    @RequestMapping(value = "/doILike")
    public String doILike(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "topic/doILike.ftl";
    }

    private String getDownLoadUrl(String ua) {
        String pvalue = "10000";
        if (ua.indexOf(WEIXIN) > 0) {
            if (ua.indexOf(IPHONE) > 0) {
                pvalue = "100";
            }
        }
        AppVersionDto appVersion = bundleService.getLatestApp(pvalue, null);
        return appVersion.getDownload();
    }

    @RequestMapping(value = "/whereTimeGo")
    public String whereTimeGo(Model model, HttpServletRequest request, HttpServletResponse response) {

        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("downloadurl", getDownLoadUrl(ua));
        return "topic/whereTimeGo.ftl";
    }

    /**
     *  郑州样板间开业，圣斯克活动页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/shengsike")
    public String shengsike(Model model) {

        model.addAttribute("id_0", 41L);
        model.addAttribute("id_1", 42L);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp end = Timestamp.valueOf("2015-10-08 23:59:59");

        model.addAttribute("diff", end.getTime() - now.getTime());

        model.addAttribute("enable", true);
        model.addAttribute("title1", "圣斯克家具套装专场10.9起售，买就送罗莱床品！");
        model.addAttribute("icon1", StaticResourceConstants.M_SHENGSIKE_IMG);
        model.addAttribute("desc", "10.9-10.11，圣斯克家具套装起售18888元，加送床垫及罗莱床品，惊喜多多！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/shengsike/share");

        return "topic/shengsike.ftl";
    }

    /**
     * 圣斯克分享页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/shengsike/share")
    public String shengsikeShare(Model model) {

        model.addAttribute("enable", true);
        model.addAttribute("title1", "圣斯克家具套装专场10.9起售，买就送罗莱床品！");
        model.addAttribute("icon1", StaticResourceConstants.M_SHENGSIKE_IMG);
        model.addAttribute("desc", "10.9-10.11，圣斯克家具套装起售18888元，加送床垫及罗莱床品，惊喜多多！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/shengsike/share");

        return "topic/shengsikeshare.ftl";
    }
    
    @RequestMapping(value = "/letv")
    public String letv(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(124l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(124l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 124l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        //String WEIXIN = "micromessenger";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(124l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手乐视TV，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_LETV_IMG);
        model.addAttribute("desc", "乐视TV搭配精美套装，超级放送尽在艾佳生活，速来抢购！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/letv/share");
        return "topic/letv.ftl";
    }
    
    @RequestMapping(value = "/letv/share")
    public String letvShare(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(124l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(124l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 124l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        //String WEIXIN = "micromessenger";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(124l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手乐视TV，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_LETV_IMG);
        model.addAttribute("desc", "乐视TV搭配精美套装，超级放送尽在艾佳生活，速来抢购！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/letv/share");
        return "topic/letvshare.ftl";
    }
    
    @RequestMapping(value = "/geli")
    public String geli(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(127l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(127l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 127l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        //String WEIXIN = "micromessenger";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(127l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手格力电器，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_GELI_IMG);
        model.addAttribute("desc", "格力空调全屋套装，全网低价就在艾佳生活，速来抢购！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/geli/share");
        return "topic/geli.ftl";
    }
    
    @RequestMapping(value = "/geli/share")
    public String geliShare(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(127l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(127l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 127l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        //String WEIXIN = "micromessenger";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(127l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手格力电器，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_GELI_IMG);
        model.addAttribute("desc", "格力空调全屋套装，全网低价就在艾佳生活，速来抢购！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/geli/share");
        return "topic/gelishare.ftl";
    }
    
    @RequestMapping(value = "/dinosaur")
    public String dinosaur(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(134l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(134l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 134l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        //String WEIXIN = "micromessenger";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(134l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手恐龙一号，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_DINOSAUR_SHARE_ICON_URL);
        model.addAttribute("desc", "艾佳生活精美套装，搭配恐龙一号新风机，生活如此精彩，空气如此清新！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/dinosaur/share");
        return "topic/dinosaur.ftl";
    }
    
    @RequestMapping(value = "/dinosaur/share")
    public String dinosaurShare(Model model, HttpServletRequest request) {

    	HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(134l);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(134l);
        //获取排序类型进行排序
        String sortType = request.getParameter("sortType");
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", 134l);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "bannerProductList");
        
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        String ANDROID = "android";
        String IPHONE = "iphone";
        String osType="1";//1:iphone2:android
        if (ua.indexOf(ANDROID) > 0) {
            osType="2";
        }
        if (ua.indexOf(IPHONE) > 0) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        String version = request.getParameter("version");
        model.addAttribute("version", version);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(134l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
    	
        model.addAttribute("enable", true);        
        model.addAttribute("title1", "艾佳生活携手恐龙一号，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_DINOSAUR_SHARE_ICON_URL);
        model.addAttribute("desc", "艾佳生活精美套装，搭配恐龙一号新风机，生活如此精彩，空气如此清新！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/dinosaur/share");
        return "topic/dinosaurshare.ftl";
    }

}
