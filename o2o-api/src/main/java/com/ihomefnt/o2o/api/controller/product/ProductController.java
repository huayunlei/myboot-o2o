package com.ihomefnt.o2o.api.controller.product;

import com.ihomefnt.common.util.image.ImageQuality;
import com.ihomefnt.common.util.image.ImageTool;
import com.ihomefnt.common.util.image.ImageType;
import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.product.doo.*;
import com.ihomefnt.o2o.intf.domain.product.vo.request.*;
import com.ihomefnt.o2o.intf.domain.product.vo.response.*;
import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.product.ProductConstant;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import com.ihomefnt.o2o.intf.manager.util.common.RegexUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import com.ihomefnt.o2o.intf.service.cart.ShoppingCartService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.suit.SuitService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shirely_geng on 15-1-17.
 */
@ApiIgnore
@Deprecated
@Api(tags = "商品老api", hidden = true)
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    TKDMDao tkdmDao;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    SuitService suitService;
    @Autowired
    CollectionDao collectionDao;
    @Autowired
    AdService adService;
    @Autowired
    ProductTypeDao productTypeDao;

    /**
     * @param @param request
     * @return HttpBaseResponse<Void>    返回类型
     * @throws
     * @Title: addUserConsult
     * @Description: 前端存在，但未使用
     */
    @ApiOperation(value = "addUserConsult", notes = "addUserConsult")
    @RequestMapping(value = "/addUserConsult", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addUserConsult(@Json HttpAddUserConsultRequest request) {
        if (request == null || request.getProductId() == null || request.getType() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        String content = request.getContent();
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(request.getPhoneNumber());
        if (content == null || content.length() >= 500 && m.matches()) {
            return HttpBaseResponse.fail(MessageConstant.CONTENT_OVER_FLOW);
        }

        //1.查询用户是否登录 登录获取uid
        HttpUserInfoRequest userDto = request.getUserInfo();
        //2.组装意见反馈或咨询问题信息并入库
        if (null != userDto) {
            productService.addUserConsult(request, userDto.getId().longValue());
        } else {
            productService.addUserConsult(request, null);
        }

        return HttpBaseResponse.success();
    }

    /**
     * m站改版
     * 单品列表页
     */
    @RequestMapping(value = "/goAllProductList", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductMoreSingleResponse150> goAllProductList(@Json HttpProductMoreSingleRequest150 productMoreSingleRequest) {
        if (productMoreSingleRequest == null) {
            return HttpBaseResponse.fail(ProductConstant.MESSAGE_PARAMETERS_EMPTY);
        }
        if (productMoreSingleRequest.getIsNavigation() == null) {
            productMoreSingleRequest.setIsNavigation(true);
        }
        if (productMoreSingleRequest.getPageNo() == 0) {
            productMoreSingleRequest.setPageNo(1);
        }
        if (productMoreSingleRequest.getPageSize() == 0) {
            productMoreSingleRequest.setPageSize(10);
        }
        return HttpBaseResponse.success(productService.getMoreSingle150(productMoreSingleRequest));
    }

    /**
     * APP 2.6.0首页改版.
     *
     * @param productHomeRequest
     * @return
     */
    @RequestMapping(value = "/home260", method = RequestMethod.POST)
    public HttpBaseResponse<HttpHomeResponse> home260(@Json HttpProductHomeRequest productHomeRequest) {
        if (null == productHomeRequest) {
            return HttpBaseResponse.fail(ProductConstant.MESSAGE_PARAMETERS_EMPTY);
        }
        UserVisitLogDo userVisitLog = new UserVisitLogDo(productHomeRequest.getDeviceToken(), productHomeRequest.getMobileNum(), 0, "打开APP"); //记录用户行为日志
        userService.saveUserVisitLog(userVisitLog);

        String accessToken = productHomeRequest.getAccessToken();
        Integer osType = productHomeRequest.getOsType();
        if (StringUtils.isNotBlank(accessToken) && null != osType) {
            UserDto userDto = userService.getUserByToken(accessToken);
            if (userDto != null) {
                Long uId = userDto.getId().longValue();
                if (null != uId) {
                    userService.addAccessLog(uId, osType);
                }
            }
        }

        /**
         * MAPI-150:体验店信息(小区入口) 2.9.1版本以上才增加
         */
//            int version = 0;
//            if(null != productHomeRequest.getAppVersion()) {
//            	String replace = productHomeRequest.getAppVersion().replace(".", "");
//            	version = Integer.parseInt(replace);
//            }
//            if(version > 292 || productHomeRequest.getOsType() == 3) {
//            	HttpExperienceStoresResponse expStoreInfo = getExpStoreInfo(productHomeRequest);
//            	httpHomeResponse.setExperienceStores(expStoreInfo);
//            	httpHomeResponse.setArtworkList(null);
//            }

        return HttpBaseResponse.success(productService.home260(productHomeRequest));
    }

    @RequestMapping(value = "/userConsultList", method = RequestMethod.POST)
    public HttpBaseResponse<HttpUserConsultResponse> userConsultList(
            @Json HttpUserCommentOrConsultRequest request) {

        HttpUserConsultResponse userConsultResponse = productService.queryUserConsultList(request);
        return HttpBaseResponse.success(userConsultResponse);
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @RequestMapping(value = "/composite/detail", method = RequestMethod.POST)
    public HttpBaseResponse<HttpCompositeDetailResponse> getCompositeDetail(
            @Json HttpCompositeDetailRequest request) {
        if (request != null) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 2, "查看套装详情", 18, request.getCompositeProductId()); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
            // 1. get composite product
            CompositeProduct compositeProduct = productService
                    .queryCompositeProductById(request.getCompositeProductId());
            if (compositeProduct != null) {
                // 2. get single product list that contained in composite
                // product
                CompositeProductReponse compositeProductResponse = new CompositeProductReponse(
                        compositeProduct);
                JSONArray compositeJsonArray = JSONArray
                        .fromObject(compositeProduct.getPictureUrlOriginal());
                List<String> compositeStrList = (List<String>) JSONArray
                        .toList(compositeJsonArray, String.class);
                List<String> compositeStrResponseList = new ArrayList<String>();
                if (null != compositeStrList && compositeStrList.size() > 0) {
                    for (String str : compositeStrList) {
                        if (null != str && !"".equals(str)) {
                            str += productService.appendImageMethod(ImageSize.SIZE_LARGE);
                            compositeStrResponseList.add(str);
                        }
                    }
                }
                compositeProductResponse
                        .setPictureUrlOriginal(compositeStrResponseList);

                List<ProductSummary> productSummaryList = productService
                        .queryCompositeSingle(request.getCompositeProductId());

                List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();

                if (null != productSummaryList && productSummaryList.size() > 0) {
                    for (ProductSummary product : productSummaryList) {
                        ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(
                                product);
                        JSONArray productJsonArray = JSONArray
                                .fromObject(product.getPictureUrlOriginal());
                        List<String> productStrList = (List<String>) JSONArray
                                .toList(productJsonArray, String.class);
                        List<String> productStrResponseList = new ArrayList<String>();
                        if (null != productStrList && productStrList.size() > 0) {
                            for (String str : productStrList) {
                                if (null != str && !"".equals(str)) {
                                    str += productService.appendImageMethod(ImageSize.SIZE_MEDIUM);
                                    productStrResponseList.add(str);
                                }
                            }
                        }
                        productSummaryResponse
                                .setPictureUrlOriginal(productStrResponseList);
                        productSummaryResponseList.add(productSummaryResponse);
                    }
                }
                HttpCompositeDetailResponse compositeDetailResponse = new HttpCompositeDetailResponse();
                compositeDetailResponse
                        .setCompositeProduct(compositeProductResponse);
                compositeDetailResponse
                        .setSingleList(productSummaryResponseList);
                String saleOff = compositeProductResponse.getSaleOff();
                saleOff = RegexUtil.regExSaleOff(saleOff);
                compositeProductResponse.setSaleOff(saleOff);
                return HttpBaseResponse.success(compositeDetailResponse);
            }
        }
        return HttpBaseResponse.fail(MessageConstant.FAILED);
    }


    @SuppressWarnings({"deprecation", "unchecked"})
    @RequestMapping(value = "/composite/detail110", method = RequestMethod.POST)
    public HttpBaseResponse<HttpCompositeDetailResponseN> getCompositeDetailN(
            @Json HttpCompositeDetailRequest request) {
        if (request == null || request.getCompositeProductId() == null) {
            return HttpBaseResponse.fail(ProductConstant.MESSAGE_PARAMETERS_EMPTY);
        }

        UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 2, "查看套装详情", 18, request.getCompositeProductId()); //记录用户行为日志
        userService.saveUserVisitLog(userVisitLog);
        Map<Long, Long> shoppingProductIds = new HashMap<Long, Long>();
        //1.判断用户是否登录
        HttpUserInfoRequest userDto = request.getUserInfo();
        //2.用户不登录的话查询单品详情信息
        if (userDto != null && null != userDto.getId()) {
            List<Long> productIds = shoppingCartService.queryShoppingCartProduct(userDto.getId().longValue());
            if (null != productIds && productIds.size() > 0) {
                for (Long id : productIds) {
                    shoppingProductIds.put(id, id);
                }
            }
        }
        // 1. get composite product
        CompositeProduct compositeProduct = productService.queryCompositeProductById(request.getCompositeProductId());
        if (compositeProduct == null) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        Long buildingId = compositeProduct.getBuildingId();
        if (buildingId != null) {
            CompositeProduct locationTemp = productService.queryLocationByBuildingId(buildingId);
            if (locationTemp != null && StringUtils.isNotBlank(locationTemp.getExperienceAddress())) {
                compositeProduct.setExperienceAddress(locationTemp.getExperienceAddress());
            }
        }
        compositeProduct.setPrice(compositeProduct.getPrice());
        Long designerId = compositeProduct.getDesignerId();
        //config designer homecard url
        String designerUrl = "http://m.ihomefnt.com/designer/" + designerId;

        // 2. get single product list that contained in composite
        // product
        CompositeProductReponseN compositeProductResponse = new CompositeProductReponseN(compositeProduct);
        String brief = compositeProductResponse.getBrief();
        if (StringUtils.isNotBlank(brief)) {
            compositeProductResponse.setDesignFeatures(brief);
        } else {
            compositeProductResponse.setDesignFeatures(null);
        }

        if (StringUtils.isNotBlank(compositeProduct.getPictureUrlOriginal())
                && compositeProduct.getPictureUrlOriginal().contains("[")
                && compositeProduct.getPictureUrlOriginal().contains("]")) {
            JSONArray compositeJsonArray = JSONArray.fromObject(compositeProduct.getPictureUrlOriginal());
            List<String> compositeStrList = (List<String>) JSONArray.toList(compositeJsonArray, String.class);
            List<String> compositeStrResponseList = new ArrayList<String>();
            if (null != compositeStrList && compositeStrList.size() > 0) {
                for (String str : compositeStrList) {
                    if (null != str && !"".equals(str)) {
                        str += ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW);//productService.appendImageMethod(ImageSize.SIZE_LARGE);
                        compositeStrResponseList.add(str);
                    }
                }
            }
            compositeProductResponse.setPictureUrlOriginal(compositeStrResponseList);
        }

        List<String> pictureUrlOriginal = compositeProductResponse.getPictureUrlOriginal();
        if (pictureUrlOriginal == null || pictureUrlOriginal.isEmpty()) {
            List<RoomImage> roomImageList = suitService.getRoomImageListBySuitIdAndDetailPage(request.getCompositeProductId(), SuitConstant.DESIGN_STATUS_NO, null);
            if (roomImageList != null && !roomImageList.isEmpty()) {
                List<String> urlStringList = new ArrayList<String>();
                for (RoomImage roomImage : roomImageList) {
                    String url = roomImage.getImageUrl();
                    if (!urlStringList.contains(url)) {
                        urlStringList.add(url);
                    }
                }
                compositeProductResponse.setPictureUrlOriginalList(roomImageList);
                compositeProductResponse.setPictureUrlOriginal(urlStringList);
            }
        }
        List<ProductSummary> productSummaryList = productService.queryCompositeSingle(request.getCompositeProductId());

        List<ProductSummaryResponse> productSummaryResponseList = new ArrayList<ProductSummaryResponse>();

        //----------1.6.0版本套装详情start--------------------------------
        Map<String, String> map = new HashMap<String, String>();
        int roomImagesCnt = 0;
        String version = request.getAppVersion();
        Map<String, String> map2 = new HashMap<String, String>();
        //----------1.6.0版本套装详情end--------------------------------

        List<Room> roomList = new ArrayList<Room>();
        Double singlePrice = 0d;
        if (null != productSummaryList && productSummaryList.size() > 0) {
            for (ProductSummary product : productSummaryList) {

                //----------1.6.0版本套装详情start--------------------------------
                if (StringUtils.isNotBlank(version) && Integer.valueOf(version.replace(".", "")) >= 160) {
                    if (null != product && StringUtils.isNotBlank(product.getRoomImages())
                            && product.getRoomImages().contains("[")
                            && product.getRoomImages().contains("]")) {
                        JSONArray roomImagesJsonArray = JSONArray.fromObject(product.getRoomImages());
                        List<String> roomImagesStrList = (List<String>) JSONArray.toList(roomImagesJsonArray, String.class);
                        boolean isExist = false;
                        String roomImage = "";
                        if (null != roomImagesStrList && roomImagesStrList.size() > 0) {
                            if (null != roomImagesStrList && roomImagesStrList.size() > 0) {
                                for (String str : roomImagesStrList) {
                                    if (StringUtils.isNotBlank(str)) {
                                        if (null != map2.get(product.getFirstContentsName() + str)) {

                                        } else {
                                            map2.put(product.getFirstContentsName() + str, str);
                                        }
                                        if (!isExist) {
                                            isExist = true;
                                            roomImage = productService.getImages(str, request.getOsType(), request.getWidth());
                                        }
                                    }
                                }
                            }
                        }
                        if (null != map.get(product.getFirstContentsName())) {

                        } else {
                            map.put(product.getFirstContentsName(), roomImage);
                        }
                    }
                }
                //----------1.6.0版本套装详情end--------------------------------

                boolean flag = true;
                ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse(product);
                if (null != shoppingProductIds &&
                        null != product &&
                        null != product.getProductId() &&
                        shoppingProductIds.size() > 0 &&
                        null != shoppingProductIds.get(product.getProductId())) {
                    productSummaryResponse.setShoppingCart(1);
                } else {
                    productSummaryResponse.setShoppingCart(0);
                }
                if (null != product && StringUtils.isNotBlank(product.getPictureUrlOriginal())
                        && product.getPictureUrlOriginal().contains("[")
                        && product.getPictureUrlOriginal().contains("]")) {
                    JSONArray productJsonArray = JSONArray.fromObject(product.getPictureUrlOriginal());
                    List<String> productStrList = (List<String>) JSONArray.toList(productJsonArray, String.class);
                    List<String> productStrResponseList = new ArrayList<String>();

                    if (null != productStrList && productStrList.size() > 0) {
                        for (String str : productStrList) {
                            if (null != str && !"".equals(str)) {
                                str += productService.appendImageMethod(ImageSize.SIZE_MEDIUM);
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
                        if ((productSummaryResponse.getRoomId()).equals(room.getRoomId())) {
                            flag = false;
                            int productCnt = room.getCount() + product.getProductCount();
                            room.setCount(productCnt);
                            List<ProductSummaryResponse> troomProductList = room.getProductSummaryList();
                            troomProductList.add(productSummaryResponse);
                            room.setProductSummaryList(troomProductList);
                            room.setOriginalPrice(room.getOriginalPrice() + productSummaryResponse.getPriceCurrent() * productSummaryResponse.getProductCount());
                        }
                    }
                }

                if (flag) {
                    Room room = new Room();
                    room.setName(productSummaryResponse.getRoomType());//房间名称
                    room.setCount(productSummaryResponse.getProductCount());//房间里的商品
                    room.setRoomPrice(productSummaryResponse.getRoomPrice());//房间价格
                    room.setRoomId(productSummaryResponse.getRoomId());
                    //处理房间图片
                    if (StringUtils.isNotBlank(product.getRoomImages())
                            && product.getRoomImages().contains("[") && product.getRoomImages().contains("]")) {
                        JSONArray roomImageArray = JSONArray.fromObject(product.getRoomImages());
                        List<String> roomImageStrList = (List<String>) JSONArray.toList(roomImageArray, String.class);
                        List<String> ImageStrResponseList = new ArrayList<String>();
                        if (null != roomImageStrList && (roomImageStrList.size() > 2 || (StringUtils.isNotBlank(version) && Integer.valueOf(version.replace(".", "")) >= 160))) {//3张图片显示
                            for (String str : roomImageStrList) {
                                if (null != str && !"".equals(str)) {
                                    str += productService.appendImageMethod(ImageSize.SIZE_LARGE);
                                    ImageStrResponseList.add(str);
                                }
                            }
                        }
                        if (StringUtils.isNotBlank(version) && Integer.valueOf(version.replace(".", "")) >= 160) {
                            room.setUrls(ImageStrResponseList);
                        } else {
                            if (ImageStrResponseList.size() > 2) {//3张图片显示
                                if (request.getOsType() == null || request.getOsType() == 02) {
                                    room.setUrls(null);
                                } else {
                                    room.setUrls(ImageStrResponseList);
                                }
                            }
                        }
                    }

                    List<ProductSummaryResponse> roomProductList = new ArrayList<ProductSummaryResponse>();
                    roomProductList.add(productSummaryResponse);
                    room.setProductSummaryList(roomProductList);
                    room.setOriginalPrice(room.getOriginalPrice() + productSummaryResponse.getPriceCurrent() * productSummaryResponse.getProductCount());
                    roomList.add(room);
                } //处理房间end
                singlePrice += product.getPriceCurrent() * product.getProductCount();
            }
        }
        /*int sales = productService.querySuitSales(request.getCompositeProductId());
        compositeProductResponse.setSales(sales);*/
        HttpUserCommentOrConsultRequest commentOrConsultRequest = new HttpUserCommentOrConsultRequest();
        commentOrConsultRequest.setProductId(request.getCompositeProductId());
        commentOrConsultRequest.setType(1l);
        int commentCount = productService.queryUserCommentCount(commentOrConsultRequest);
        int consultCount = productService.queryUserConsultCount(commentOrConsultRequest);
        compositeProductResponse.setRoomList(roomList);
        compositeProductResponse.setSinglePrice(singlePrice);

        compositeProductResponse.setDesignerUrl(designerUrl);
        compositeProductResponse.setCommentCount(commentCount);
        compositeProductResponse.setConsultCount(consultCount);


        HttpCompositeDetailResponseN compositeDetailResponse = new HttpCompositeDetailResponseN();
        compositeDetailResponse.setCompositeProduct(compositeProductResponse);
        compositeDetailResponse.setSingleList(productSummaryResponseList);

        HttpUserCommentOrConsultRequest userCommendRequest = new HttpUserCommentOrConsultRequest();
        userCommendRequest.setPageNo(0);
        userCommendRequest.setPageSize(1);
        userCommendRequest.setProductId(request.getCompositeProductId());
        userCommendRequest.setType(1l);
        HttpUserCommendResponse commendResponse = productService.queryUserCommentList(userCommendRequest);
        compositeDetailResponse.setUserCommentList(commendResponse.getUserCommentList());

        String saleOff = compositeProductResponse.getSaleOff();
        /*if (request.getOsType() == null || request.getOsType() == 02) {
            saleOff = "8";
        } else {
            saleOff = RegexUtil.regExSaleOff(saleOff);
        }*/

        //----------1.6.0版本套装详情start--------------------------------
        if (StringUtils.isNotBlank(version) && Integer.valueOf(version.replace(".", "")) >= 160) {
            List<HouseImage> houseImageList = new ArrayList<HouseImage>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                HouseImage houseImage = new HouseImage();
                if (StringUtils.isNotBlank(entry.getKey())
                        && StringUtils.isNotBlank(entry.getValue())) {
                    houseImage.setImgTitle(entry.getKey() + "实景图");
                    houseImage.setImgUrl(entry.getValue());
                    houseImageList.add(houseImage);
                }
                if (houseImageList.size() > 3) {
                    break;
                }
            }
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    roomImagesCnt = roomImagesCnt + 1;
                }
            }

            if (houseImageList.size() > 0) {
                if (houseImageList.size() % 2 == 1) {
                    houseImageList.remove(houseImageList.size() - 1);
                }
                compositeDetailResponse.setHouseImageList(houseImageList);
                compositeDetailResponse.setAllImageTitle("全部实景图片(" + roomImagesCnt + "张)");
            } else {
                String pictureUrl = compositeProduct.getPictureUrlOriginal();
                if (null != pictureUrl && !"".equals(pictureUrl.trim())) {
                    JSONArray jsonArray = JSONArray.fromObject(pictureUrl);
                    List<String> graphs = (List<String>) JSONArray.toCollection(jsonArray);
                    if (graphs != null && graphs.size() > 0) {
                        for (String graph : graphs) {
                            HouseImage houseImage = new HouseImage();
                            if (graph != null && !graph.equals("")) {
                                houseImage.setImgUrl(graph);
                                houseImageList.add(houseImage);
                                break;
                            }
                        }
                    }
                }

                compositeDetailResponse.setHouseImageList(houseImageList);
                compositeDetailResponse.setAllImageTitle("全部实景图片(" + roomImagesCnt + "张)");

            }

            List<com.ihomefnt.o2o.intf.domain.suit.dto.Room> roomSuitList = suitService.getRoomListBySuitId(request.getCompositeProductId());
            //这里只存放有图片的空间
            List<com.ihomefnt.o2o.intf.domain.suit.dto.Room> roomCovertList = new ArrayList<com.ihomefnt.o2o.intf.domain.suit.dto.Room>();
            //对空间做保护
            if (roomSuitList != null && !roomSuitList.isEmpty()) {
                for (com.ihomefnt.o2o.intf.domain.suit.dto.Room room : roomSuitList) {
                    Long roomId = room.getRoomId();
                    String firstImage = room.getFirstImage();
                    List<RoomImage> imageAlllist = suitService.getRoomImageListByRoomIdAndDetailPage(roomId, SuitConstant.DESIGN_STATUS_NO, null);
                    List<String> urlList = new ArrayList<String>();
                    List<RoomImage> roomImageList = new ArrayList<RoomImage>();
                    // 对空间的所有图片做保护
                    if (imageAlllist != null && !imageAlllist.isEmpty()) {
                        room.setFirstImage(firstImage + ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
                        roomCovertList.add(room);
                        room.setImageAlllist(imageAlllist);
                        for (RoomImage roomImage : imageAlllist) {
                            String imageUrl = roomImage.getImageUrl();
                            if (imageUrl != null) {
                                roomImage.setImageUrl(imageUrl + ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
                            }
                            Long detailpage = roomImage.getDetailpage();
                            RoomImage clone = null;
                            try {
                                clone = (RoomImage) roomImage.clone();
                                clone.setImageUrl(imageUrl + ImageTool.appendTypeAndQuality(ImageType.LARGE, ImageQuality.LOW));
                            } catch (CloneNotSupportedException e) {
                                LOG.error("getCompositeDetailN Exception ", e);
                            }
                            if (imageUrl != null && firstImage != null && imageUrl.equals(firstImage)) {
                                if (!urlList.contains(imageUrl)) {
                                    urlList.add(imageUrl);
                                    roomImageList.add(clone);
                                    continue;
                                }

                            }
                            if (detailpage != null && SuitConstant.DETAIL_PAGE_YES.equals(detailpage)) {
                                if (!urlList.contains(imageUrl)) {
                                    urlList.add(imageUrl);
                                    roomImageList.add(clone);
                                }
                            }

                        }
                    }
                    //对空间的图文详情图片做保护
                    if (roomImageList != null && !roomImageList.isEmpty()) {
                        room.setImagelist(roomImageList);
                    }
                }
                if (!roomCovertList.isEmpty()) {
                    compositeDetailResponse.setRoomList(roomCovertList);
                }
            }

        }

        //----------1.6.0版本套装详情end--------------------------------
        //用户不登录的话查询单品详情信息
        if (userDto != null && null != userDto.getId()) {
            int cnt = shoppingCartService.queryShoppingCartCnt(userDto.getId().longValue());
            compositeDetailResponse.setShoppingCartCnt(cnt);
            //HttpUserLikeResponse userLikeResponse = productService.queryUserLike(request.getCompositeProductId(), tLog.getuId());
            //compositeDetailResponse.setUserLikeResponse(userLikeResponse);
            TCollection collection = collectionDao.queryCollection(request.getCompositeProductId(), userDto.getId().longValue(), 1l);
            if (collection == null || collection.getStatus() == 0) {
                compositeDetailResponse.setCollection(0);
            } else {
                compositeDetailResponse.setCollection(1);
            }
        } else {
            compositeDetailResponse.setCollection(0);
        }
        compositeProductResponse.setSaleOff(saleOff);

        return HttpBaseResponse.success(compositeDetailResponse);
    }


    @RequestMapping(value = "/roomDetails", method = RequestMethod.POST)
    public HttpBaseResponse<HttpRoomDetailsResponse> roomDetails(
            @Json HttpRoomDetailsRequest roomDetailsRequest) {
        if (roomDetailsRequest == null
                || roomDetailsRequest.getRoomId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        //1.判断用户是否登录
        UserDto userDto = userService.getUserByToken(roomDetailsRequest.getAccessToken());
        //2.用户不登录的话查询空间详情信息
        if (userDto == null) {
            HttpRoomDetailsResponse roomDetails = productService
                    .getRoomDetails(roomDetailsRequest.getRoomId(), null);
            if (roomDetails == null) {
                return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
            }

            Room roomDetails2 = roomDetails.getRoomDetails();
            roomDetails2.setRoomPrice(roomDetails2.getRoomPrice());
            roomDetails.setRoomDetails(roomDetails2);

            Room room = roomDetails2;
            if (null != room) {
                String roomTypeName = room.getRoomTypeName();
                Integer roomType = room.getRoomType().intValue();
                String content = "查看空间详情";
                if (null != roomTypeName && !"".equals(roomTypeName)) {
                    content = "查看" + roomTypeName + "详情";
                }

                UserVisitLogDo userVisitLog = new UserVisitLogDo(roomDetailsRequest.getDeviceToken(), roomDetailsRequest.getMobileNum(), 2, content, roomType, roomDetailsRequest.getRoomId()); //记录用户行为日志
                userService.saveUserVisitLog(userVisitLog);
            }
            return HttpBaseResponse.success(roomDetails);
        }

        //3.用户存在的话，查询单品详情信息和此单品是否已收藏
        HttpRoomDetailsResponse roomDetails = productService
                .getRoomDetails(roomDetailsRequest.getRoomId(), userDto.getId().longValue());
        if (roomDetails == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
        }

        Room roomDetails2 = roomDetails.getRoomDetails();
        roomDetails2.setRoomPrice(roomDetails2.getRoomPrice());
        roomDetails.setRoomDetails(roomDetails2);
        int cnt = shoppingCartService.queryShoppingCartCnt(userDto.getId().longValue());
        roomDetails.setShoppingCartCnt(cnt);
        Map<Long, Long> shoppingProductIds = new HashMap<Long, Long>();
        List<Long> productIds = shoppingCartService.queryShoppingCartProduct(userDto.getId().longValue());
        if (null != productIds && productIds.size() > 0) {
            for (Long id : productIds) {
                shoppingProductIds.put(id, id);
            }
        }
        Long productId = roomDetails2.getRoomId();
        if (null != shoppingProductIds &&
                null != productId &&
                shoppingProductIds.size() > 0 &&
                null != shoppingProductIds.get(productId)) {
            roomDetails.setShoppingCart(1);
        } else {
            roomDetails.setShoppingCart(0);
        }

        Room room = roomDetails2;
        if (null != room) {
            String roomTypeName = room.getRoomTypeName();
            Integer roomType = room.getRoomType().intValue();
            String content = "查看空间详情";
            if (null != roomTypeName && !"".equals(roomTypeName)) {
                content = "查看" + roomTypeName + "详情";
            }

            UserVisitLogDo userVisitLog = new UserVisitLogDo(roomDetailsRequest.getDeviceToken(), roomDetailsRequest.getMobileNum(), 2, content, roomType, roomDetailsRequest.getRoomId()); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }
        return HttpBaseResponse.success(roomDetails);
    }

    @RequestMapping(value = "/suit150", method = RequestMethod.POST)
    //pc-web使用接口
    public HttpBaseResponse<HttpSuitResponse> suit150(
            @Json HttpSuitRequest150 request) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!StringUtils.isNotBlank(request.getRoomName()) && request.getRoomType() != null) {
            String roomName = "";
            switch (request.getRoomType()) {
                case 0://0.客厅     1.餐厅     2.卧室    3.书房
                    roomName = "客厅";
                    break;
                case 1:
                    roomName = "餐厅";
                    break;
                case 2:
                    roomName = "卧室";
                    break;
                case 3:
                    roomName = "书房";
                    break;
                default:
                    roomName = "卧室";
                    break;
            }
            request.setRoomName(roomName);
        }

        HttpSuitResponse res = productService.getSuitList150(request);
        //1.判断用户是否登录
        HttpUserInfoRequest userDto = request.getUserInfo();
        //2.用户不登录的话查询单品详情信息
        if (userDto != null && null != userDto.getId()) {
            int cnt = shoppingCartService.queryShoppingCartCnt(userDto.getId().longValue());
            res.setShoppingCartCnt(cnt);
        }

        String roomNameStr = request.getRoomName();
        if (!StringUtils.isNotBlank(roomNameStr) && request.getRoomType() == null) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM("套装");
            if (null == tkdmSeo) {
                tkdmSeo = new TKDMSeo();
                tkdmSeo.setTitle("");
                tkdmSeo.setKeyword("");
                tkdmSeo.setDescription("");
            }
            res.setSeo_title(tkdmSeo.getTitle());
            res.setSeo_keyword(tkdmSeo.getKeyword());
            res.setSeo_description(tkdmSeo.getDescription());
            res.setRoomName(request.getRoomName());
        } else if (StringUtils.isNotBlank(roomNameStr)) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(roomNameStr);
            if (null == tkdmSeo) {
                tkdmSeo = new TKDMSeo();
                tkdmSeo.setTitle("");
                tkdmSeo.setKeyword("");
                tkdmSeo.setDescription("");
            }
            res.setSeo_title(tkdmSeo.getTitle());
            res.setSeo_keyword(tkdmSeo.getKeyword());
            res.setSeo_description(tkdmSeo.getDescription());
            res.setRoomName(request.getRoomName());
        }

        int targetType = 0;
        String roomName = request.getRoomName();
        if (null == roomName || "".equals(roomName)) {
            targetType = 19; //更多套装
            roomName = "更多套装";
        } else {
            if ("套装".equals(roomName)) {
                targetType = 18;
            } else if ("客厅".equals(roomName)) {
                targetType = 1;
            } else if ("餐厅".equals(roomName)) {
                targetType = 6;
            } else if ("卧室".equals(roomName)) {
                targetType = 2;
            } else if ("书房".equals(roomName)) {
                targetType = 5;
            }
        }

        UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 1, "打开" + roomName + "模块 ", targetType, null); //记录用户行为日志
        userService.saveUserVisitLog(userVisitLog);

        return HttpBaseResponse.success(res);
    }

    @RequestMapping(value = "/home170", method = RequestMethod.POST)
    public HttpBaseResponse<HttpHomeResponse> home170(
            @Json HttpProductHomeRequest productHomeRequest) {
        if (null != productHomeRequest) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(productHomeRequest.getDeviceToken(), productHomeRequest.getMobileNum(), 0, "打开APP"); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }

        if (null != productHomeRequest
                && StringUtils.isNotBlank(productHomeRequest.getAccessToken())
                && null != productHomeRequest.getOsType()) {
            UserDto userDto = userService.getUserByToken(productHomeRequest.getAccessToken());
            if (userDto != null && null != userDto.getId()) {
                userService.addAccessLog(userDto.getId().longValue(), productHomeRequest.getOsType());
            }
        }

        return HttpBaseResponse.success(productService.home170(productHomeRequest));
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductHomeResponse> home(
            @Json HttpProductHomeRequest productHomeRequest) {
        if (null != productHomeRequest) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(productHomeRequest.getDeviceToken(), productHomeRequest.getMobileNum(), 0, "打开APP"); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }

        HttpProductHomeResponse homeResponse = new HttpProductHomeResponse();
        homeResponse.setCompositeList(productService.queryCompositeProduct(12));
        homeResponse.setSingleList(productService.queryProductSummary(6));
        homeResponse.setBannerList(adService.queryAdvertisement(10, Constants.AD_TOP_POSITION, "", null));//首页banner广告只展示两天

        return HttpBaseResponse.success(homeResponse);
    }

    @RequestMapping(value = "/home110", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductHomeResponse110> home110(
            @Json HttpProductHomeRequest productHomeRequest) {
        if (null != productHomeRequest) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(productHomeRequest.getDeviceToken(), productHomeRequest.getMobileNum(), 0, "打开APP"); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }

        HttpProductHomeResponse110 homeResponse = new HttpProductHomeResponse110();
        //暂时的
        List<HttpHouseSuitProductResponse> houseSuits = productService.queryHouseSuitProduct(12);
        if (productHomeRequest == null || productHomeRequest.getOsType() == null
                || productHomeRequest.getOsType() == 02) {
            if (houseSuits != null && !houseSuits.isEmpty()) {
                for (int i = 0; i < houseSuits.size(); i++) {
                    houseSuits.get(i).setSaleOff("8");
                }
            }
        }
        homeResponse.setHouseSuitList(houseSuits);
        homeResponse.setSingleList(productService.queryProductSummary(6));
        homeResponse.setBannerList(adService.queryAdvertisement(10, Constants.AD_TOP_POSITION, "", null));//首页banner广告只展示两天

        return HttpBaseResponse.success(homeResponse);
    }

    /**
     * 获取单品列表页对应的seo文章
     */
    @RequestMapping(value = "/goProductList/{nodeId}", method = RequestMethod.POST)
    public HttpBaseResponse<HttpProductMoreSingleResponse150> goProductList(@PathVariable Long nodeId, @Json HttpProductMoreSingleRequest150 productMoreSingleRequest) {

        if (productMoreSingleRequest == null) {
            return HttpBaseResponse.fail(ProductConstant.MESSAGE_PARAMETERS_EMPTY);
        }
        if (productMoreSingleRequest.getIsNavigation() == null) {
            productMoreSingleRequest.setIsNavigation(true);
        }
        if (productMoreSingleRequest.getPageNo() == 0) {
            productMoreSingleRequest.setPageNo(1);
        }
        if (productMoreSingleRequest.getPageSize() == 0) {
            productMoreSingleRequest.setPageSize(10);
        }
        productMoreSingleRequest.setNodeId(nodeId);
        if (productMoreSingleRequest.getFilterIdList() == null) {
            List<Long> filterId = new ArrayList<Long>();
            filterId.add(nodeId);
            productMoreSingleRequest.setFilterIdList(filterId);
        } else {
            productMoreSingleRequest.getFilterIdList().add(nodeId);
        }

        HttpProductMoreSingleResponse150 response = productService.getMoreSingle150(productMoreSingleRequest);
        List<String> seoKeyList = productTypeDao.queryClassifyNameByProductNode(nodeId);
        if (response != null && null != seoKeyList && seoKeyList.size() > 0) {
            TKDMSeo tkdmSeo = tkdmDao.loadTKDM(seoKeyList.get(0));
            response.setSeo_title(tkdmSeo == null ? "" : tkdmSeo.getTitle());
            response.setSeo_keyword(tkdmSeo == null ? "" : tkdmSeo.getKeyword());
            response.setSeo_description(tkdmSeo == null ? "" : tkdmSeo.getDescription());
        }
        return HttpBaseResponse.success(response);
    }
}
