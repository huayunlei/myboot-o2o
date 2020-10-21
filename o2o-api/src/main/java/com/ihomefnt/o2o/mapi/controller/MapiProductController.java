package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.common.config.WebConfig;
import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.order.dto.Consignee;
import com.ihomefnt.o2o.intf.domain.product.doo.*;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpMultiSuitRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpProductMoreSingleRequest150;
import com.ihomefnt.o2o.intf.domain.product.vo.request.HttpSuitRequest150;
import com.ihomefnt.o2o.intf.domain.product.vo.request.SuitHardRequest;
import com.ihomefnt.o2o.intf.domain.product.vo.response.*;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.secure.Base64Utils;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by piweiwen on 15-1-26.
 */
@Api(value="M站商品API",description="M站商品老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiProductController {

    private static final Logger LOG = LoggerFactory.getLogger(MapiProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
	private BundleService bundleService;

    @Autowired
    AdService adService;

    @Autowired
    private WebConfig webConfig;

    @Autowired
    OrderService orderService;

    @Autowired
    TKDMDao tkdmDao;

    @Autowired
    ProductTypeDao productTypeDao;

    /**
     * 首页M站
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String wapHome(Model model, HttpServletRequest request, ConfigItem configItem) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpHomeResponse res = productService.home150(null);
        //首页banner广告只展示两天
        res.setBannerList(adService.queryAdFromProtocol(10, "http", ""));

        //3.组装返回数据
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(res);
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("downloadurl", getDownLoadUrl(ua));

        TKDMSeo tkdmSeo = tkdmDao.loadTKDM("首页");
        model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
        model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
        model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());

        return "product/wapHome.ftl";
    }

    @RequestMapping(value = "/goSuitList", method = RequestMethod.GET)
    public String goSuitList(Model model, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpSuitRequest150 req = new HttpSuitRequest150();
        req.setIsNavigation(true);
        req.setPageNo(1);
        req.setPageSize(10);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getSuitList150(req));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        return "product/suitList.ftl";
    }

    @RequestMapping(value = "/suit", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSuitResponse> suit(@Json HttpSuitRequest150 request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        return HttpBaseResponse.success(productService.getSuitList150(request));
    }

    @RequestMapping(value = "/goProductList/{nodeId}", method = RequestMethod.GET)
    public String goProductList(Model model, @PathVariable Long nodeId, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(nodeId);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(nodeId);
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
        model.addAttribute("nodeId", nodeId);
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "goProductList");

        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);

        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(nodeId);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
        return "product/productList.ftl";
    }

    @RequestMapping(value = "/goProductListNew", method = RequestMethod.POST)
    public String goProductListNew(Model model, HttpProductNodeSize productMore, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(productMore.getNodeId());
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(productMore.getNodeId());
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
        model.addAttribute("nodeId", productMore.getNodeId());
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "goProductList");

        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);

        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(productMore.getNodeId());
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
        return "product/productList.ftl";
    }

    @RequestMapping(value = "/bannerProductList", method = RequestMethod.GET)
    public String bannerProductList(Model model, HttpServletRequest request) {

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
        
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳生活携手乐视TV，新春巨献");
        model.addAttribute("icon1", StaticResourceConstants.M_LETV_IMG);
        model.addAttribute("desc", "乐视TV搭配精美套装，超级放送尽在艾佳生活，速来抢购！");
        model.addAttribute("mUrl", "http://m.ihomefnt.com/topic/letv/share");

        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);

        String osType="1";//1:iphone2:android
        if (ua.contains(ANDROID)) {
            osType="2";
        }
        if (ua.contains(IPHONE)) {
            osType="1";
        }
        model.addAttribute("osType", osType);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(124l);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
        return "topic/letv.ftl";
    }

    @RequestMapping(value = "/goProductList/{nodeId}/{pageNo}", method = RequestMethod.GET)
    public String goProductList(Model model, @PathVariable Long nodeId, @PathVariable Long pageNo,
            HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        if (pageNo != null && pageNo.intValue() > 1) {
            productMoreSingleRequest.setPageNo(pageNo.intValue());
        }
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(nodeId);
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(nodeId);
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
        model.addAttribute("nodeId", nodeId);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("urlType", "goProductList");

        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(nodeId);
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }

        return "product/productList.ftl";
    }

    @RequestMapping(value = "/goProductListPageNew", method = RequestMethod.POST)
    public String goProductListPageNew(Model model, HttpProductNodeSize productMoreSingle,
                                HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        if (productMoreSingle.getPageNo() != null && productMoreSingle.getPageNo().intValue() > 1) {
            productMoreSingleRequest.setPageNo(productMoreSingle.getPageNo().intValue());
        }
        productMoreSingleRequest.setPageSize(10);
        productMoreSingleRequest.setNodeId(productMoreSingle.getNodeId());
        List<Long> filterId = new ArrayList<Long>();
        filterId.add(productMoreSingle.getNodeId());
        //获取排序类型进行排序
        String sortType = productMoreSingle.getSortType();
        if (null != sortType && !"".equals(sortType)) {
            filterId.add(Long.parseLong(sortType));
        }
        productMoreSingleRequest.setFilterIdList(filterId);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(productService.getMoreSingle150(productMoreSingleRequest));
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        model.addAttribute("nodeId", productMoreSingle.getNodeId());
        model.addAttribute("currentPage", productMoreSingle.getPageNo());
        model.addAttribute("urlType", "goProductList");

        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(productMoreSingle.getNodeId());
        if (null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            model.addAttribute("seo_title", tkdmSeo == null ? "" : tkdmSeo.getTitle());
            model.addAttribute("seo_keyword", tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            model.addAttribute("seo_description", tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }

        return "product/productList.ftl";
    }

    @RequestMapping(value = "/goAllProductList", method = RequestMethod.GET)
    public String goAllProductList(Model model, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        productMoreSingleRequest.setPageSize(10);
        List<Long> filterId = new ArrayList<Long>();
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
        model.addAttribute("currentPage", "1");
        model.addAttribute("urlType", "goAllProductList");
        return "product/productList.ftl";
    }

    @RequestMapping(value = "/goAllProductList/{pageNo}", method = RequestMethod.GET)
    public String goAllProductList(Model model, @PathVariable Long pageNo,
            HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductMoreSingleRequest150 productMoreSingleRequest = new HttpProductMoreSingleRequest150();
        productMoreSingleRequest.setIsNavigation(true);
        productMoreSingleRequest.setPageNo(1);
        if (pageNo != null && pageNo.intValue() > 1) {
            productMoreSingleRequest.setPageNo(pageNo.intValue());
        }
        productMoreSingleRequest.setPageSize(10);
        List<Long> filterId = new ArrayList<Long>();
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
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("urlType", "goAllProductList");
        return "product/productList.ftl";
    }

    @RequestMapping(value = "/moreSingle", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductMoreSingleResponse150> moreSingle(
            @Json HttpProductMoreSingleRequest150 productMoreSingleRequest) {
        if (productMoreSingleRequest == null || productMoreSingleRequest.getPageNo() < 1) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (productMoreSingleRequest.getPageSize() <= 0) {
            productMoreSingleRequest.setPageSize(10);
        }

        List<Long> filterId = new ArrayList<Long>();
        if (null != productMoreSingleRequest.getNodeId()) {
            filterId.add(productMoreSingleRequest.getNodeId());
            productMoreSingleRequest.setFilterIdList(filterId);
        }
        //分页查询单品信息
        HttpProductMoreSingleResponse150 singleResponse = productService
                .getMoreSingle150(productMoreSingleRequest);
        return HttpBaseResponse.success(singleResponse);
    }

    /**
     * 多套装
     * @return
     */
    @RequestMapping(value = "/house/{houseId}", method = RequestMethod.GET)
    public String multiSuit(Model model, @PathVariable Long houseId, HttpServletRequest request) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpMultiSuitReponse multiSuitReponse = null;
        List<SuitProduct110> compositeProductList = new ArrayList<SuitProduct110>();
        if (houseId != null) {
            HttpMultiSuitRequest multiSuitRequest = new HttpMultiSuitRequest();
            multiSuitRequest.setHouseId(houseId);
            compositeProductList = productService.queryHouseSuitProductByHouseId(multiSuitRequest);
        }
        multiSuitReponse = new HttpMultiSuitReponse(compositeProductList);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(multiSuitReponse);
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("downloadurl", getDownLoadUrl(ua));
        return "product/multiSuit.ftl";
    }

    /**
     * 首页PC站
     */
    @RequestMapping(value = "/web/home", method = RequestMethod.GET)
    public String webHome(Model model) {
	
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        HttpProductHomeResponse homeResponse = new HttpProductHomeResponse();
        //1.获取套装数据
        homeResponse.setCompositeList(productService.queryCompositeProduct(4));
        //2.获取单品数据
        homeResponse.setSingleList(productService.queryProductSummary(6));
        //3.组装返回数据
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(homeResponse);
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        return "product/webHome.ftl";
    }

    /**
     * 套装详情页
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @RequestMapping(value = "/suit/{compositeProductId}", method = RequestMethod.GET)
    public String compositeDetail(Model model, @PathVariable Long compositeProductId,
            HttpServletRequest request) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (request != null) {
            // 1. get composite product
            CompositeProduct compositeProduct = productService
                    .queryCompositeProductById(compositeProductId);
            if (compositeProduct != null) {
                // 2. get single product list that contained in composite
                // product
                CompositeProductReponseN compositeProductResponse = new CompositeProductReponseN(
                        compositeProduct);

                if (StringUtils.isNotBlank(compositeProduct.getPictureUrlOriginal())
                        && compositeProduct.getPictureUrlOriginal().contains("[")
                        && compositeProduct.getPictureUrlOriginal().contains("]")) {
                    JSONArray compositeJsonArray = JSONArray.fromObject(compositeProduct
                            .getPictureUrlOriginal());
                    List<String> compositeStrList = (List<String>) JSONArray.toList(
                            compositeJsonArray, String.class);
                    List<String> compositeStrResponseList = new ArrayList<String>();
                    if (null != compositeStrList && compositeStrList.size() > 0) {
                        for (String str : compositeStrList) {
                            if (null != str && !"".equals(str)) {
                                str += productService.appendImageMethod(ImageSize.SIZE_LARGE);
                                compositeStrResponseList.add(str);
                            }
                        }
                    }
                    compositeProductResponse.setPictureUrlOriginal(compositeStrResponseList);
                }

                List<ProductSummary> productSummaryList = productService
                        .queryCompositeSingle(compositeProductId);

                List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();

                List<Room> roomList = new ArrayList<Room>();
                if (null != productSummaryList && productSummaryList.size() > 0) {
                    for (ProductSummary product : productSummaryList) {
                        boolean flag = true;
                        ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(
                                product);

                        if (null != product
                                && StringUtils.isNotBlank(product.getPictureUrlOriginal())
                                && product.getPictureUrlOriginal().contains("[")
                                && product.getPictureUrlOriginal().contains("]")) {
                            JSONArray productJsonArray = JSONArray.fromObject(product
                                    .getPictureUrlOriginal());
                            List<String> productStrList = (List<String>) JSONArray.toList(
                                    productJsonArray, String.class);
                            List<String> productStrResponseList = new ArrayList<String>();

                            if (null != productStrList && productStrList.size() > 0) {
                                for (String str : productStrList) {
                                    if (null != str && !"".equals(str)) {
                                        str += productService
                                                .appendImageMethod(ImageSize.SIZE_MEDIUM);
                                        productStrResponseList.add(str);
                                    }
                                }
                            }
                            productSummaryResponse.setPictureUrlOriginal(productStrResponseList);
                        }

                        productSummaryResponseList.add(productSummaryResponse);

                        //处理房间start
                        if (null != roomList && roomList.size() > 0) {
                            for (Room room : roomList) {
                                if ((productSummaryResponse.getFirstContentsName()).equals(room
                                        .getName())) {
                                    flag = false;
                                    int productCnt = room.getCount() + product.getProductCount();
                                    room.setCount(productCnt);
                                    List<ProductSummaryResponse> troomProductList = room
                                            .getProductSummaryList();
                                    troomProductList.add(productSummaryResponse);
                                    room.setProductSummaryList(troomProductList);
                                }
                            }
                        }

                        if (flag) {
                            Room room = new Room();
                            room.setName(productSummaryResponse.getFirstContentsName());//房间名称
                            room.setCount(productSummaryResponse.getProductCount());//房间里的商品
                            //处理房间图片
                            if (null != product
                                    && StringUtils.isNotBlank(product.getRoomImages())
                                    && product.getRoomImages().contains("[")
                                    && product.getRoomImages().contains("]")) {
                                JSONArray roomImageArray = JSONArray.fromObject(product
                                        .getRoomImages());
                                List<String> roomImageStrList = (List<String>) JSONArray.toList(
                                        roomImageArray, String.class);
                                List<String> ImageStrResponseList = new ArrayList<String>();
                                if (null != roomImageStrList && roomImageStrList.size() > 2) {//3张图片显示
                                    for (String str : roomImageStrList) {
                                        if (null != str && !"".equals(str)) {
                                            str += productService
                                                    .appendImageMethod(ImageSize.SIZE_LARGE);
                                            ImageStrResponseList.add(str);
                                        }
                                    }
                                }

                                if (ImageStrResponseList.size() > 2) {//3张图片显示
                                    room.setUrls(ImageStrResponseList);
                                }

                            }

                            List<ProductSummaryResponse> roomProductList = new ArrayList<ProductSummaryResponse>();
                            roomProductList.add(productSummaryResponse);
                            room.setProductSummaryList(roomProductList);
                            roomList.add(room);
                        } //处理房间end

                    }
                }

                compositeProductResponse.setRoomList(roomList);
                HttpCompositeDetailResponseN compositeDetailResponse = new HttpCompositeDetailResponseN();
                compositeDetailResponse.setCompositeProduct(compositeProductResponse);
                compositeDetailResponse.setSingleList(productSummaryResponseList);
                String saleOff = compositeProductResponse.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                compositeProductResponse.setSaleOff(saleOff);
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(compositeDetailResponse);
                baseResponse.setExt(null);
            }
        }
        model.addAttribute("baseResponse", baseResponse);
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("UA", ua);
        model.addAttribute("downloadurl", getDownLoadUrl(ua));
        model.addAttribute("suitList", productService.queryRandomSuit(compositeProductId));
        return "product/compositeDetail.ftl";
    }

    /**
     * 单品详情页
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String singleDetails(Model model, @PathVariable Long productId,
            HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (productId == null || productId == 0) {
            LOG.info("Transfer data has null value");
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        } else {
            // 1.获取单品详情数据
            HttpProductMoreInformationRsponse productMoreInformationRsponse = productService
                    .getProductDetails(productId, null);
            //2.组装返回数据
            if (null == productMoreInformationRsponse) {
                baseResponse.setCode(HttpResponseCode.PRODUCT_NOT_EXISTS);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.PRODUCT_NOT_EXISTS);
                baseResponse.setExt(message);

            } else {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(productMoreInformationRsponse);
                baseResponse.setExt(null);
            }
        }
        model.addAttribute("baseResponse", baseResponse);
        //ger user agent
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        model.addAttribute("downloadurl", getDownLoadUrl(ua));
        model.addAttribute("UA", ua);
        return "product/singleDetails.ftl";
    }
    
    /**
     * 商品详情页的图文（新建）
     */
    @RequestMapping(value = "/productTextgraph/{productId}", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductMoreInformationRsponse> productTextgraph(@PathVariable Long productId,@RequestParam(value = "userAgent", required = true) String userAgent,
            HttpServletRequest request) {

        if (productId == null || productId == 0) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        // 1.获取单品详情数据
        HttpProductMoreInformationRsponse productMoreInformationRsponse = productService
                .getProductDetails(productId, null);
        //2.组装返回数据
        if (null == productMoreInformationRsponse) {
            return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
        }

        productMoreInformationRsponse.setDownloadUrl(getDownLoadUrl(userAgent));
        return HttpBaseResponse.success(productMoreInformationRsponse);
    }
    
    /**
     * 商品详情页的参数
     */
    @RequestMapping(value = "/productParam/{productId}", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductMoreInformationRsponse> productParam(@PathVariable Long productId,@RequestParam(value = "userAgent", required = true) String userAgent,
            HttpServletRequest request) {

        String downLoadUrl = getDownLoadUrl(userAgent);
        if (productId == null || productId == 0) {
            HttpProductMoreInformationRsponse productMoreInformationRsponse = new HttpProductMoreInformationRsponse();
            productMoreInformationRsponse.setDownloadUrl(downLoadUrl);
            return HttpBaseResponse.buildResponse(HttpResponseCode.FAILED, productMoreInformationRsponse, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        // 1.获取单品详情数据
        HttpProductMoreInformationRsponse productMoreInformationRsponse = productService.getProductDetails(productId, null);
        //2.组装返回数据
        if (null == productMoreInformationRsponse) {
            productMoreInformationRsponse = new HttpProductMoreInformationRsponse();
            productMoreInformationRsponse.setDownloadUrl(downLoadUrl);
            return HttpBaseResponse.buildResponse(HttpResponseCode.PRODUCT_NOT_EXISTS, productMoreInformationRsponse, MessageConstant.PRODUCT_NOT_EXISTS);
        }

        productMoreInformationRsponse.setDownloadUrl(downLoadUrl);
        return HttpBaseResponse.success(productMoreInformationRsponse);
    }

    /**
     * 商品详情页的图文
     */
    @RequestMapping(value = "/product/textgraph/{productId}", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> graphicDescription(@PathVariable Long productId) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        if (productId == null || productId == 0) {
            LOG.info("Transfer data has null value");
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
            baseResponse.setExt(message);
        } else {
            //1.获取单品详情
            HttpProductMoreInformationRsponse productMoreInformationRsponse = productService
                    .getProductDetails(productId, null);
            //2.组装返回数据
            if (null == productMoreInformationRsponse) {
                baseResponse.setCode(HttpResponseCode.PRODUCT_NOT_EXISTS);
                baseResponse.setObj(null);
                HttpMessage message = new HttpMessage();
                message.setMsg(MessageConstant.PRODUCT_NOT_EXISTS);
                baseResponse.setExt(message);

            } else {
                baseResponse.setCode(HttpResponseCode.SUCCESS);
                baseResponse.setObj(productMoreInformationRsponse);
                baseResponse.setExt(null);
            }
        }
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }

    private static final String IPHONE = "iphone";
    private static final String ANDROID = "android";
    private static final String WEIXIN = "micromessenger";

    private String getDownLoadUrl(String ua) {
        String pvalue = "10000";
        if (ua.contains(IPHONE)) {
            pvalue = "100";
        }
        if (ua.contains(WEIXIN)) {
            return webConfig.HOST + "/app/" + pvalue;
        }
        AppVersionDto appVersion = bundleService.getLatestApp(pvalue, null);
        return appVersion.getDownload();
    }

    @RequestMapping(value = "/confirmSuit/{suitId}", method = RequestMethod.GET)
    public ModelAndView confirmSuit(@PathVariable Long suitId, HttpServletRequest request,
            HttpSession httpSession) {
		
        Map<String, Object> model = new HashMap<String, Object>();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/confirmSuit/"
                + suitId;
        if (StringUtils.isNotBlank(accessToken)) {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null && null != tLog.getuId()) {
                model.put("roomProduct", productService.queryRoomProductBySuitId(suitId));
                Long uId = tLog.getuId();//用户id
                UserDo userr = userService.queryUserInfo(uId);
                List<Consignee> consigneeList = orderService.queryMyConsignee(tLog.getuId());
                if (null != consigneeList && consigneeList.size() > 0
                        && StringUtils.isNotBlank(consigneeList.get(0).getPurchaserName())
                        && StringUtils.isNotBlank(consigneeList.get(0).getPurchaserTel())) {
                    model.put("purchaserName", consigneeList.get(0).getPurchaserName());
                    model.put("purchaserTel", consigneeList.get(0).getPurchaserTel());
                } else if (null != userr && StringUtils.isNotBlank(userr.getMobile())) {
                    model.put("purchaserTel", userr.getMobile());
                }
            } else {
                return new ModelAndView(new RedirectView(url));
            }
        } else {
            return new ModelAndView(new RedirectView(url));
        }
        return new ModelAndView("product/suitBooking.ftl", model);
    }

    /**
     * 单品预定确认页
     */
    @RequestMapping(value = "/confirmProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView confirmProduct(@PathVariable Long productId, HttpServletRequest request,
            HttpSession httpSession) {

        Map<String, Object> model = new HashMap<String, Object>();
        Object token = httpSession.getAttribute(httpSession.getId());
        String accessToken = token != null ? token.toString() : "";
        String url = webConfig.HOST + "/login?returnUrl=" + webConfig.HOST + "/confirmProduct/"
                + productId;
        if (StringUtils.isNotBlank(accessToken)) {
            LogDo tLog = userService.isLoggedIn(accessToken);
            if (tLog != null && null != tLog.getuId()) {
                // 1.获取单品详情数据
                HttpProductMoreInformationRsponse productMoreInformationRsponse = productService
                        .getProductDetails(productId, null);
                model.put("product", productMoreInformationRsponse);
                Long uId = tLog.getuId();//用户id
                UserDo userr = userService.queryUserInfo(uId);
                List<Consignee> consigneeList = orderService.queryMyConsignee(tLog.getuId());
                if (null != consigneeList && consigneeList.size() > 0
                        && StringUtils.isNotBlank(consigneeList.get(0).getPurchaserName())
                        && StringUtils.isNotBlank(consigneeList.get(0).getPurchaserTel())) {
                    model.put("purchaserName", consigneeList.get(0).getPurchaserName());
                    model.put("purchaserTel", consigneeList.get(0).getPurchaserTel());
                } else if (null != userr && StringUtils.isNotBlank(userr.getMobile())) {
                    model.put("purchaserTel", userr.getMobile());
                }
            } else {
                return new ModelAndView(new RedirectView(url));
            }
        } else {
            return new ModelAndView(new RedirectView(url));
        }
        return new ModelAndView("product/productBooking.ftl", model);
    }
    
    
    
    @RequestMapping(value = "/newsuit", method = RequestMethod.GET)
    public String getSuitProduct(Model model, HttpServletRequest request) {

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        SuitInfoResponse suitInfoResponse = new SuitInfoResponse();
        
        String suitPara =  request.getParameter("suitpara");
        JSONObject jsonObject = JSONObject.fromObject(suitPara);
        Long suitId = Long.parseLong(jsonObject.get("suitId").toString());
        
        JSONArray roomIdArr = JSONArray.fromObject(jsonObject.get("roomIdList"));
        List<Long> roomIdList = (List<Long>) JSONArray.toCollection(roomIdArr);
        
        JSONArray hardIdArr = JSONArray.fromObject(jsonObject.get("hardIdList"));
        List<List<Long>> hardIdList = (List<List<Long>>) JSONArray.toCollection(hardIdArr);
        
        JSONArray roomIdTempArr = JSONArray.fromObject(jsonObject.get("roomIdTempList"));

        JSONArray hardIdTempArr = JSONArray.fromObject(jsonObject.get("hardIdTempList"));
        List<List<Long>> hardIdTempList = (List<List<Long>>) JSONArray.toCollection(hardIdTempArr);

        List<TSuitProduct> suitProductList = productService.getSuitProduct(suitId);
        Map<String, List<TSuitProduct>> roomProductMap = new HashMap<String, List<TSuitProduct>>();
        
        if (null != suitProductList) {
            suitInfoResponse.setSuitId(suitId);
            suitInfoResponse.setSuitName(suitProductList.get(0).getSuitName());
            suitInfoResponse.setSuitPrice(suitProductList.get(0).getSuitPrice());
            for (TSuitProduct suitProduct : suitProductList) {
                String roomName = suitProduct.getRoomName();
                List<TSuitProduct> suitProductResponse = roomProductMap.get(roomName);
                if (null == suitProductResponse) {
                    suitProductResponse = new ArrayList<TSuitProduct>();
                    roomProductMap.put(roomName, suitProductResponse);
                }
                
                Long productId = suitProduct.getProductId();
                boolean flag = false;
                for (TSuitProduct suitProductRes : suitProductResponse) {
                    Long pid = suitProductRes.getProductId();
                    if (Long.compare(pid, productId) == 0) {
                        suitProduct.setProductNum(suitProduct.getProductNum()+1);    //相同的商品数据量加1
                        flag = true;
                        break;
                    }
                }
                
                if (!flag) { //统计重复的商品数量 不加入重复的商品
                    suitProduct.setProductNum(1);
                    JSONArray jsonArray = JSONArray.fromObject(suitProduct.getProductImages());
                    List<String> graphs = (List<String>) JSONArray.toCollection(jsonArray);
                    if (graphs != null && graphs.size() > 0) {
                        for (String graph : graphs) {
                            if (graph !=null && !graph.equals("")) {
                                suitProduct.setProductFirstImage(graph);
                                break;
                            }
                        }
                    }
                    
                    suitProductResponse.add(suitProduct);
                }
                
            }
        }
        
        Map<String, List<SuitHard>> roomHardMap = new HashMap<String, List<SuitHard>>();
        for (int i=0; i<roomIdList.size(); i++) {
            SuitHardRequest suitHardRequest = new SuitHardRequest(); 
            suitHardRequest.setSuitId(suitId);
            List<Long> roomIdList2 = new ArrayList<Long>();
            roomIdList2.add(Long.valueOf(roomIdArr.get(i).toString()));
            suitHardRequest.setRoomIdList(roomIdList2);
            suitHardRequest.setHardIdList(hardIdList.get(i));
            List<Long> roomIdTempList2 = new ArrayList<Long>();
            roomIdTempList2.add(Long.valueOf(roomIdTempArr.get(i).toString()));
            suitHardRequest.setRoomIdTempList(roomIdTempList2);
            suitHardRequest.setHardIdTempList(hardIdTempList.get(i));
            
            List<SuitHard> suitHardList = productService.getSuitHard(suitHardRequest);
            
            if (null != suitHardList) {
                for (SuitHard suitHard : suitHardList) {
                    String roomName = suitHard.getRoomName();
                    List<SuitHard> suitHardResponse = roomHardMap.get(roomName);
                    if (null == suitHardResponse) {
                        suitHardResponse = new ArrayList<SuitHard>();
                        roomHardMap.put(roomName, suitHardResponse);
                    }
                    
                    suitHardResponse.add(suitHard);
                }
            }
            
        }

        suitInfoResponse.setRoomProductMap(roomProductMap);
        suitInfoResponse.setRoomHardMap(roomHardMap);

        baseResponse.setObj(suitInfoResponse);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "get");
        try {
            suitPara = Base64Utils.encode(suitPara.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("suitpara", suitPara);
        model.addAttribute("baseResponse", baseResponse);
        return "product/suitNewBooking.ftl";
    }
    
    
    
    @RequestMapping(value = "/confirmnewsuit", method = RequestMethod.GET)
    public ModelAndView confirmNewSuit(HttpServletRequest request, HttpSession httpSession) {

        String suitpara = request.getParameter("suitpara");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("suitpara", suitpara);

        return new ModelAndView("product/suitAddressBooking.ftl", model);
    }
    
    
}
