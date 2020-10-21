package com.ihomefnt.o2o.service.service.user;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.dao.cart.ShoppingCartDao;
import com.ihomefnt.o2o.intf.dao.order.OrderDao;
import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.dao.product.WishListDao;
import com.ihomefnt.o2o.intf.domain.address.doo.TReceiveAddressDo;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AccountBookRecordDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.AjbSearchDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.UserAjbRecordDto;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetSurveyorProjectNodeDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.OwnerParamDto;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureInfo;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureListResponse;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListParamVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListResultVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.PagesVo;
import com.ihomefnt.o2o.intf.domain.order.dto.TOrder;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpUserOrderRequest;
import com.ihomefnt.o2o.intf.domain.product.doo.*;
import com.ihomefnt.o2o.intf.domain.user.dto.AjbRemarkType;
import com.ihomefnt.o2o.intf.domain.user.dto.RoleDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.dic.DicConstant;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.manager.constant.user.UserRoleConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.*;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.hbms.OwnerProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.coupon.CashCouponService;
import com.ihomefnt.o2o.intf.service.coupon.VoucherService;
import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.user.MyService;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MyServiceImpl implements MyService {
	
	@Autowired
	private UserProxy userProxy;
	@Autowired
	private CollectionDao collectionDao;
	@Autowired
    ShoppingCartDao shoppingCartDao;
	@Autowired
    WishListDao wishListDao;
	@Autowired
    OrderDao orderDao;
	@Autowired
	HbmsProxy hbmsProxy;
	@Autowired
	AddressProxy addressProxy;
	@Autowired
    DictionaryService dictionaryService;
	@Autowired
    AreaService areaService;
	@Autowired
	LogProxy logProxy;
	@Autowired
	DicProxy dicProxy;
	@Autowired
    private AjbProxy ajbProxy;
	@Autowired
    private VoucherService voucherService;
	@Autowired
	CashCouponService cashCouponService;
    @Autowired
	OrderService orderService;
    @Autowired
    private OwnerProxy ownerProxy;
	
    private static final String DEFAULT_IMAGE = AliImageUtil.imageCompress(StaticResourceConstants.HARD_LIVE_PIC, 2, 1280, ImageConstant.SIZE_MIDDLE);

    public static final String M_HOST="https://m.ihomefnt.com";

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public UserFavoritesResponseVo getAllFavorites(UserFavoritesRequestVo request) {
		if (request == null) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
    	//1.查询用户收藏商品信息
        List<UserProductFavorites> userProductFavoritesList = collectionDao
                .queryAllFavorites(userDto.getId().longValue());
        List<UserProductFavoritesResponse> userProductFavoritesResponseList = new ArrayList<UserProductFavoritesResponse>();
        //2.组装数据返回给客户端
        if (null != userProductFavoritesList
                && userProductFavoritesList.size() > 0) {
            for (UserProductFavorites favorites : userProductFavoritesList) {
                UserProductFavoritesResponse favoritesResponse = new UserProductFavoritesResponse(favorites);
                
                if(null != favorites && StringUtils.isNotBlank(favorites.getPictureUrlOriginal())
                		&&favorites.getPictureUrlOriginal().contains("[")
                		&&favorites.getPictureUrlOriginal().contains("]")){
                    JSONArray jsonArray = JSONArray.fromObject(favorites
                            .getPictureUrlOriginal());
                    List<String> strList = (List<String>) JSONArray.toList(
                            jsonArray, String.class);
                    List<String> strResponseList = new ArrayList<String>();
                    if (null != strList && strList.size() > 0) {
                        for (String str : strList) {
                            if (null != str && !"".equals(str)) {
                            	strResponseList.add(QiniuImageUtils.compressProductImage(str, 100, 100));
                            }
                        }
                    }
                    favoritesResponse.setPictureUrlOriginal(strResponseList);
                }

                userProductFavoritesResponseList.add(favoritesResponse);
            }
        }
        
        UserFavoritesResponseVo userFavortiesResponse = new UserFavoritesResponseVo();
        userFavortiesResponse.setUserProductFavorites(userProductFavoritesResponseList);
		return userFavortiesResponse;
	}

	@Override
	public UserFavoritesAllResponseVo getAllFavorites200(UserFavoritesRequestVo request) {
		if (request == null ||  StringUtil.isNullOrEmpty(request.getAccessToken())) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
    	Long userId = userDto.getId().longValue();
    	int pageSize = request.getPageSize();
    	int pageNo = request.getPageNo();
        UserFavoritesAllResponseVo userFavoritesResponse = new UserFavoritesAllResponseVo();
        request.setPageSize(3);
        request.setPageNo(0);
        if(request.getInspirationFlag() == 1){
            request.setType(4l);
            userFavoritesResponse.setStrategyFavoritesResponse(moreUserFavorites(request,userId));
            
            request.setType(5l);
            userFavoritesResponse.setCaseFavoritesResponse(moreUserFavorites(request,userId));
            
            request.setType(6l);
            request.setPageSize(pageSize);
            request.setPageNo(pageNo);
            userFavoritesResponse.setAlumFavoritesResponse(morePictureAlbumList(request,userId));
        } else {
        	request.setType(1l);
            userFavoritesResponse.setSuitFavoritesResponse(moreUserFavorites(request,userId));
            
            request.setType(2l);
            userFavoritesResponse.setRoomFavoritesResponse(moreUserFavorites(request,userId));
            
            request.setType(3l);
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        int suitCount = collectionDao.queryFavoritesSuitCount(params);
        int roomCount = collectionDao.queryFavoritesRoomCount(params);
        int strategyCount = collectionDao.queryFavoritesStrategyCount(params);
        int caseCount = collectionDao.queryFavoritesCaseCount(params);
        int albumCount = collectionDao.queryPictureAlbumCount(params);
        
        userFavoritesResponse.setProductCount(suitCount+roomCount);
        userFavoritesResponse.setInspirationCount(strategyCount+caseCount+albumCount);
		return userFavoritesResponse;
	}
	
	@Override
	public UserFavoritesResponseVo moreUserFavorites(UserFavoritesRequestVo request,Long userId) {
    	int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize);
        params.put("userId", userId);
        params.put("type", request.getType());
    	
    	List<UserProductFavorites> userFavoritesList =null;
    	List<UserInspirationFavorites> inspirationFavoritesList =null;
    	int totalRecords = 0;
    	
    	if(request.getType()==1){
    		userFavoritesList = collectionDao.queryFavoritesSuit(params);
    		totalRecords = collectionDao.queryFavoritesSuitCount(params);
    	}
    	if(request.getType()==2){
    		userFavoritesList = collectionDao.queryFavoritesRoom(params);
    		totalRecords = collectionDao.queryFavoritesRoomCount(params);
    	}
    	if(request.getType()==3){
    		userFavoritesList = collectionDao.queryFavoritesSingle(params);
    		totalRecords = collectionDao.queryFavoritesSingleCount(params);
    	}
    	if(request.getType()==4){
    		inspirationFavoritesList = collectionDao.queryFavoritesStrategy(params);
    		totalRecords = collectionDao.queryFavoritesStrategyCount(params);
    	}
    	if(request.getType()==5){
    		inspirationFavoritesList = collectionDao.queryFavoritesCase(params);
    		totalRecords = collectionDao.queryFavoritesCaseCount(params);
    	}
    	
    	List<UserProductFavoritesResponse> moreFavoritesResponseList = new ArrayList<UserProductFavoritesResponse>();
    	if (null != userFavoritesList && userFavoritesList.size() > 0) {
    		
			List<Long> productIdList = new ArrayList<Long>();
			for (UserProductFavorites favorites : userFavoritesList) {
				if (favorites != null && null != favorites.getProductId()) {
					if (!productIdList.contains(favorites.getProductId())) {
						productIdList.add(favorites.getProductId());
					}
				}
			}
			Map<Long, String> roomMap = queryProductInRoomByProductIdList(productIdList);
       	
            for (UserProductFavorites favorites : userFavoritesList) {
                UserProductFavoritesResponse favoritesResponse = new UserProductFavoritesResponse(favorites);
                if(null != favorites){
                    List<String> strResponseList = ImageUtil.removeEmptyStr(favorites.getPictureUrlOriginal());
                    favoritesResponse.setPictureUrlOriginal(strResponseList);
                }
                favoritesResponse.setFirstContentsName(roomMap.get(favoritesResponse.getProductId()));
                moreFavoritesResponseList.add(favoritesResponse);
            }
        }
    	
    	List<UserInspirationFavorites> moreInspirationFavoritesList = new ArrayList<UserInspirationFavorites>();
    	if (null != inspirationFavoritesList && inspirationFavoritesList.size() > 0) {
            for (UserInspirationFavorites favorites : inspirationFavoritesList) {
                if(null != favorites){
                    List<String> strResponseList = ImageUtil.removeEmptyStr(favorites.getImages());
                    if(strResponseList.size() > 0){
                    	favorites.setHeadImage(strResponseList.get(0));
                    }
                }
                moreInspirationFavoritesList.add(favorites);
            }
        }
    	
    	UserFavoritesResponseVo userFavortiesResponse = new UserFavoritesResponseVo();
    	userFavortiesResponse.setUserProductFavorites(moreFavoritesResponseList);
    	userFavortiesResponse.setUserInspirationFavorites(moreInspirationFavoritesList);
    	userFavortiesResponse.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        userFavortiesResponse.setTotalPages(totalPages);
        return userFavortiesResponse;
	}
	
	/**
     * 这个只返回满足条件的
     * @param productIdList
     * @return
     */
	private Map<Long, String> queryProductInRoomByProductIdList(List<Long> productIdList) {
        Map<Long, String> map = new HashMap<Long, String>();
        List<ProductSummaryResponse> res = shoppingCartDao.queryProductInRoomByProductIdList(productIdList);
        if (null != res && res.size() > 0) {
            for (ProductSummaryResponse p : res) {
                map.put(p.getProductId(), p.getName());
            }
        }
        return map;
    }	
    
	@Override
	public HttpPictureListResponse morePictureAlbumList(UserFavoritesRequestVo request,Long userId) {
		HttpPictureListResponse response = new HttpPictureListResponse();            
        
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);
        paramMap.put("userId", userId);
        paramMap.put("type", request.getType());
        
        List<PictureAlbum> pictureAlbumList = collectionDao.queryPictureAlbumList(paramMap);
        
        int bigPicWidt = 0;
        int needPicWidt = 0;
        if (request.getNeedPicWidt() != 0){
        	needPicWidt = request.getNeedPicWidt();
        } else {
        	needPicWidt = request.getWidth()/2;
        }
        bigPicWidt = needPicWidt * 3;
        if (null != pictureAlbumList && pictureAlbumList.size() > 0) {
            for (PictureAlbum album : pictureAlbumList) {
            	
            	double pictureWidth = album.getWidth() > 0 ? album.getWidth() : needPicWidt;
                double pictureHeight = album.getHeight() > 0 ? album.getHeight() : needPicWidt;
                double ratio = pictureHeight/pictureWidth;
                int height = (int) (ratio * needPicWidt);
                int bigheight = (int) (ratio * bigPicWidt);
                
                List<PictureInfo> pictureInfoList = new ArrayList<PictureInfo>();
                String url = album.getUrl();
                if(StringUtils.isNotBlank(url)){
                    PictureInfo pictureInfo = new PictureInfo();
                    String bigurl = url + appendImageMethod(bigPicWidt,bigheight);
                    pictureInfo.setPictureId(album.getAlbumId());
                    pictureInfo.setImageUrl(bigurl);
                    pictureInfo.setCurPicHeight(bigheight);
                    pictureInfo.setCurPicWidt(bigPicWidt);
                    pictureInfoList.add(pictureInfo);
                    String smallurl = url+ appendImageMethod(needPicWidt,height);
                    album.setUrl(smallurl);
                    album.setWidth(needPicWidt);
                    album.setHeight(height);
                }
                album.setPictureInfoList(pictureInfoList);
            }
        }
        
        response.setPictureList(pictureAlbumList);
        int totalRecords = collectionDao.queryPictureAlbumCount(paramMap);
        response.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response.setTotalPages(totalPages);
        return response;
	}

	private String appendImageMethod(int width,int height) {
        String methodUrl = "?imageView2/1/w/*/h/*";
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(width));
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(height));
        return methodUrl;
    }

	@Override
	public UserWishListResponseVo getAllWishList(UserWishListRequestVo request) {
		if (request == null) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
    	
        UserWishListResponseVo userWishListResponse = new UserWishListResponseVo();
        userWishListResponse.setWishList(this.queryAllWishList(userDto.getId().longValue()));
		return userWishListResponse;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    public List<WishListResponseVo> queryAllWishList(Long userId) {
        //1.查询用户侃价商品
        List<TWishList> wishList = wishListDao.queryAllWishList(userId);
        //2.组装数据返回给客户端
        List<WishListResponseVo> wishResponseList = new ArrayList<WishListResponseVo>();
        if (null != wishList && wishList.size() > 0) {
            for (TWishList wish : wishList) {
                WishListResponseVo wishResponse = new WishListResponseVo(wish);
                
                if(null != wish && StringUtils.isNotBlank(wish.getWishProductUrl())
                		&& wish.getWishProductUrl().contains("[")
                		&& wish.getWishProductUrl().contains("]")
                		){
                    JSONArray jsonArray = JSONArray.fromObject(wish
                            .getWishProductUrl());
                    List<String> strList = (List<String>) JSONArray.toList(
                            jsonArray, String.class);
                    List<String> strResponseList = new ArrayList<String>();
                    if (null != strList && strList.size() > 0) {
                        for (String str : strList) {
                            if (null != str && !"".equals(str)) {
                                strResponseList.add(str);
                            }
                        }
                    }
                    wishResponse.setWishProductUrl(strResponseList);
                }
                
                
                wishResponseList.add(wishResponse);
            }
            return wishResponseList;
        }
        return wishResponseList;
	}

	@Override
	public UserAddWishListResponseVo addWishList(UserAddWishListRequestVo request) {
		if (request == null) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }
		
        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
    	// 组装侃价信息并入库
        TWishList wishList = new TWishList();
        wishList.setUserId(userDto.getId().longValue());
        wishList.setWishProductBrand(request
                .getWishProductBrand());
        wishList.setWishProductName(request
                .getWishProductName());
        wishList.setWishProductRequest(request
                .getWishProductRequest());
        wishList.setWishProductUrl(request.getWishProductUrl());
        UserAddWishListResponseVo userAddWishListResponse = new UserAddWishListResponseVo();
        userAddWishListResponse.setWishListId(this.addWishList(wishList));
        
		return userAddWishListResponse;
	}

	@Override
	public Long addWishList(TWishList wishList) {
		wishList.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        return wishListDao.addWishList(wishList);
	}

	@Override
	public UserOrderListResponseVo getAllOrder(UserOrderRequestVo request) {
		if (request == null) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }
		
        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
    	// 查询该用户订单信息
        UserOrderListResponseVo userOrderResponse = new UserOrderListResponseVo();
        userOrderResponse.setUserOrderList(this.queryAllUserOrder(userDto.getId().longValue()));
		return userOrderResponse;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
    private List<UserOrderResponseVo> queryAllUserOrder(Long userId) {
        //查询单品订单信息
        List<UserOrder> userSingleOrderList = orderDao
                .queryAllSingleUserOrder(userId);
        //查询套装订单信息
        List<UserOrder> userCompositeOrderList = orderDao
                .queryAllCompositeUserOrder(userId);
       //组装订单信息返回给客户端
        List<UserOrderResponseVo> UserOrderResponseList = new ArrayList<UserOrderResponseVo>();
        if (null != userSingleOrderList && userSingleOrderList.size() > 0) {
            for (UserOrder userOrder : userSingleOrderList) {
                UserOrderResponseVo userOrderResponse = new UserOrderResponseVo(userOrder);
                
                if(null != userOrder 
                		&& StringUtils.isNotBlank(userOrder.getPictureUrlOriginal())
                		&& userOrder.getPictureUrlOriginal().contains("[")
                		&& userOrder.getPictureUrlOriginal().contains("]")
                		){
                    JSONArray jsonArray = JSONArray.fromObject(userOrder.getPictureUrlOriginal());
                    List<String> strList = (List<String>) JSONArray.toList(
                            jsonArray, String.class);
                    List<String> strResponseList = new ArrayList<String>();
                    if (null != strList && strList.size() > 0) {
                        for (String str : strList) {
                            if (null != str && !"".equals(str)) {
                                strResponseList.add(str);
                            }
                        }
                    }
                    userOrderResponse.setPictureUrlOriginal(strResponseList);
                }
                
                UserOrderResponseList.add(userOrderResponse);
            }
        }
        if (null != userCompositeOrderList && userCompositeOrderList.size() > 0) {
            for (UserOrder userOrder : userCompositeOrderList) {
                UserOrderResponseVo userOrderResponse = new UserOrderResponseVo(userOrder);
                
                if(null != userOrder
                		&&userOrder.getPictureUrlOriginal().contains("[")){
                    JSONArray jsonArray = JSONArray.fromObject(userOrder
                            .getPictureUrlOriginal());
                    List<String> strList = (List<String>) JSONArray.toList(
                            jsonArray, String.class);
                    List<String> strResponseList = new ArrayList<String>();
                    if (null != strList && strList.size() > 0) {
                        for (String str : strList) {
                            if (null != str && !"".equals(str)) {
                                strResponseList.add(str);
                            }
                        }
                    }
                    userOrderResponse.setPictureUrlOriginal(strResponseList);
                }
                
                UserOrderResponseList.add(userOrderResponse);
            }
        }
        return UserOrderResponseList;
    }

	@Override
	public MyConfigResponseVo configByOrderId(UserOrderRequestVo request) {
		if (request == null ||  StringUtil.isNullOrEmpty(request.getAccessToken())) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

    	UserDto userDto = userProxy.getUserByToken(request.getAccessToken());
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
    	
        MyConfigResponseVo configResponse = new MyConfigResponseVo();
        String accessToken = request.getAccessToken();
        
 		boolean propertyConsultantFlag = setResponseShow(configResponse, userDto);
        // 设置置业顾问显示
        setInviteUserShow(configResponse, request, propertyConsultantFlag);
        
        Integer orderId = request.getOrderId();
        configResponse.setHardOrderDetail(M_HOST + "/hard/orderDetail" + "?accessToken=" + accessToken + "&orderId=" + orderId+"&orderType=13");
        // 设置项目进度
        setProductProgress(configResponse, orderId);
		
		//实时截取乐橙直播画面
        String liveImage = DEFAULT_IMAGE; //默认图片
        try {
        	if(orderId!=null){
                List<GetDeviceListResultVo> result=hbmsProxy.getDeviceByOrderId(Integer.toString(orderId));
                if (CollectionUtils.isNotEmpty(result)) {
                    for(GetDeviceListResultVo deviceListResultVo : result){
                        if(deviceListResultVo != null && deviceListResultVo.getBrand() == 1
                        && deviceListResultVo.getUrl() != null){
                            liveImage = deviceListResultVo.getUrl();
                            break;
                        }
                    }
                }
                configResponse.setHardLivepic(liveImage);
        	}

        } catch (Exception e) {
            configResponse.setHardLivepic(liveImage);
            e.printStackTrace();
        }
		
		return configResponse;
	}

	private void setInviteUserShow(MyConfigResponseVo configResponse, HttpBaseRequest request,
			boolean propertyConsultantFlag) {
		if (StringUtils.isBlank(request.getAppVersion())) {
			return;
        }
		String appVersion = request.getAppVersion().replace(".", "").toString();
        Long appV = Long.parseLong(appVersion);

        //邀请用户
        if (null != request.getOsType() && request.getOsType() == 2 && appV <= 270) {
            configResponse.setInviteUserShow(0);//hide  
        } else {
            if (propertyConsultantFlag) {
                configResponse.setInviteUserShow(1);//show                                           
                String inviteUserUrl = dictionaryService.getValueByKey("VISIT_USER_URL");
                if (StringUtils.isBlank(inviteUserUrl)) {
                    inviteUserUrl = "http://m.ihomefnt.com/myinvites/homecard";
                }
                inviteUserUrl += "?accessToken=" + request.getAccessToken();
                String cityCode = request.getCityCode();
                if (StringUtils.isBlank(cityCode)) {
                    cityCode = "210000";
                }
                inviteUserUrl += "&cityCode=" + cityCode;
                configResponse.setInviteUserUrl(inviteUserUrl);
            } else {
                configResponse.setInviteUserShow(0);//hide
            }
        }
	}

	private void setProductProgress(MyConfigResponseVo configResponse, Integer orderId) {
        OwnerParamDto ownerParamRequest = new OwnerParamDto();
        ownerParamRequest.setOrderId(orderId.toString());
        List<GetSurveyorProjectNodeDto> dataList =  ownerProxy.getProjectCraft(ownerParamRequest);                                                
		if (CollectionUtils.isNotEmpty(dataList)) {
			GetSurveyorProjectNodeDto data = new GetSurveyorProjectNodeDto();
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i).getStatus() == 1) {
					if (Double.parseDouble(dataList.get(i).getProgress()) < 1) {
						data = dataList.get(i);
						break;
					}
					if (i < (dataList.size() - 1)) {
						if (Double.parseDouble(dataList.get(i).getProgress()) == 1
								&& dataList.get(i + 1).getStatus() == 0
								&& Double.parseDouble(dataList.get(i + 1).getProgress()) == 0) {
							data = dataList.get(i);
							break;
						}
					}
					if (Double.parseDouble(dataList.get(dataList.size() - 1).getProgress()) <= 1) {
						data = dataList.get(i);
						break;
					}
				} else if (dataList.get(i).getStatus() == 0) {
					if (Double.parseDouble(dataList.get(i).getProgress()) > 0
							&& Double.parseDouble(dataList.get(i).getProgress()) < 1) {
						data = dataList.get(i);
						break;
					}
					if (Double.parseDouble(dataList.get(i).getProgress()) == 1) {
						data = dataList.get(i);
						break;
					}
					data = dataList.get(i);
					break;
				} else if (dataList.get(i).getStatus() == 5) {
					if (i == (dataList.size() - 1)) {
						data = dataList.get(i);
						break;
					}
				}
			}
			NumberFormat instance = NumberFormat.getPercentInstance();
			instance.setMaximumFractionDigits(0);
			String format = instance.format(Double.parseDouble(data.getProgress()));
			String substring = data.getNodeName().substring(0, 2) + "阶段";
			configResponse.setProductProgress(substring + " (已完成" + format + ")");
		} else {
			configResponse.setProductProgress("暂未排期");
		} 
	}

	private TReceiveAddressDo getAddress(UserDto userDto) {
    	UserAddressResultDto userAddress = addressProxy.queryDefaultByUserId(userDto.getId());
        if (null != userAddress) {
            int provinceId = userAddress.getProvinceId();
            int cityId = userAddress.getCityId();
            int countryId = userAddress.getCountryId();

            AreaDto provinceArea = areaService.getArea(provinceId);
            AreaDto cityArea = areaService.getArea(cityId);
            AreaDto countryArea = areaService.getArea(countryId);

            StringBuilder sb = new StringBuilder();
            if (null != provinceArea) {
                sb.append(provinceArea.getAreaName());
            }

            if (null != cityArea) {
                sb.append(" ");
                sb.append(cityArea.getAreaName());
            }

            if (null != countryArea) {
                sb.append(" ");
                sb.append(countryArea.getAreaName());
            }

            TReceiveAddressDo res = new TReceiveAddressDo();
            res.setPcdAddress(sb.toString());
            res.setPurchaserName(userAddress.getConsignee());
            res.setPurchaserTel(userAddress.getMobile());
            res.setUserId(userAddress.getUserId().longValue());
            res.setAreaId((long) countryId);
            res.setStreet(userAddress.getAddress());
            res.setDefaultAddress(true);

            if (StringUtils.isNotBlank(res.getPcdAddress())
                    && StringUtils.isNotBlank(res.getPurchaserName())
                    && StringUtils.isNotBlank(res.getPurchaserTel())) {
                return res;
            }
        }

        return null;
    }

	@Override
	public MyConfigResponseVo config(HttpBaseRequest request) {
		if (request == null ||  StringUtil.isNullOrEmpty(request.getAccessToken())) {
        	throw new BusinessException(HttpResponseCode.USER_NAME_PWD_EMPTY,MessageConstant.USER_PASS_EMPTY);
        }

    	UserDto userDto = userProxy.getUserByToken(request.getAccessToken());
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
    	}
        String accessToken = request.getAccessToken();

        MyConfigResponseVo configResponse = new MyConfigResponseVo();
        configResponse.setScreenShow(1);//画屏入口显示1：显示，0：隐藏
        // 关于艾佳生活介绍URL
 		DicDto dicVo = dicProxy.queryDicByKey(DicConstant.ABOUT_IHOME_URL);
 		String abountIhomeUrl = "";
 		if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
 			abountIhomeUrl = dicVo.getValueDesc();
 		}
 		configResponse.setAbountIhomeUrl(abountIhomeUrl);
        
 		addLog(request, userDto.getId(), userDto.getMobile());
 		
 		boolean propertyConsultantFlag = setResponseShow(configResponse, userDto);
 		// 设置置业顾问显示
        setInviteUserShow(configResponse, request, propertyConsultantFlag);
 		
        Integer orderId = setHardLivepic(configResponse, userDto.getMobile());
        // 目前只取订单列表中一条数据	
        if (orderId > 0) {
            //个人中心               
            configResponse.setHardOrderDetail(M_HOST + "/hard/orderDetail" + "?accessToken=" + accessToken + "&orderId=" + orderId+"&orderType=13");
            // 设置项目进度
            setProductProgress(configResponse, orderId);
        }
        
		return configResponse;
	}

	private boolean setResponseShow(MyConfigResponseVo configResponse, UserDto userDto) {
		// load user receipt address
    	TReceiveAddressDo address = getAddress(userDto);
        if (null != address) {
            configResponse.setReceiveAddress(address);
        }
        
        // load user config
        List<RoleDto> roles = userDto.getRoles();
        boolean dealZhongnanFlag = false;
        boolean propertyConsultantFlag = false;
        boolean designerFlag = false;
        if (null != roles) {
            for (RoleDto role : roles) {
                String code = role.getCode();
                if (UserRoleConstant.DEAL_USER_ZHONGNAN.equals(code)) {
                    dealZhongnanFlag = true;
                } else if (UserRoleConstant.PROPERTY_CONSULTANT.equals(code)) {
                    propertyConsultantFlag = true;
                } else if (UserRoleConstant.DESIGNER.equals(code)) {
                    designerFlag = true;
                }
            }
        }
        
        if (dealZhongnanFlag) {
            configResponse.setLittleCofferShow(1);//show
        } else {
            configResponse.setLittleCofferShow(0);//hide
        }
        
        //设计师栏目
        if (designerFlag) {
            configResponse.setDesinerShow(1);
        } else {
            configResponse.setDesinerShow(0);
        }
		return propertyConsultantFlag;
	}

	private Integer setHardLivepic(MyConfigResponseVo configResponse, String mobile) {
		GetDeviceListParamVo param =new GetDeviceListParamVo();
		   
        param.setStatus(0+"");
        Integer orderId = 0;
        //实时截取乐橙直播画面
        String liveImage = DEFAULT_IMAGE; //默认图片
		if (StringUtils.isNotBlank(mobile)) {
			param.setOwnerMobile(mobile);
			//不提供分页
			PagesVo<GetDeviceListResultVo> page = hbmsProxy.getSimpleDeviceList(param);
			if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
				List<GetDeviceListResultVo> list = page.getList();
				for (GetDeviceListResultVo vo : list) {
					if (StringUtils.isNotBlank(vo.getOrderId())) {
						orderId = Integer.parseInt(vo.getOrderId());
						String url = vo.getUrl();
						if (StringUtils.isNotBlank(url)) {
							liveImage = url;
						}
						configResponse.setHardLivepic(liveImage);
						break;
					}
				}
			}
		}          
		return orderId;
	}

	private void addLog(HttpBaseRequest request, Integer userId, String mobile) {
		// 增加日志:我的设置
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceToken", request.getDeviceToken());
		params.put("mobile", mobile);
		params.put("visitType", LogEnum.LOG_HOME_CONFIG.getCode());
		params.put("action", LogEnum.LOG_HOME_CONFIG.getMsg());
		params.put("userId", userId);
		params.put("appVersion", request.getAppVersion());
		params.put("osType", request.getOsType());
		params.put("pValue", request.getParterValue());
		params.put("cityCode", request.getCityCode());
		logProxy.addLog(params);
	}

	@Override
	public AjbInfoResponseVo getAjbInfo(AjbInfoRequestVo httpAjbInfoRequest) {
		if (httpAjbInfoRequest == null) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }
		
        HttpUserInfoRequest userDto = httpAjbInfoRequest.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.ADMIN_ILLEGAL, MessageConstant.ADMIN_ILLEGAL);
    	}
        Integer userId = userDto.getId();
        Integer pageNo = 1;
        Integer pageSize = 10;
        if(httpAjbInfoRequest.getPageNo() != null && httpAjbInfoRequest.getPageNo() > 0){
        	pageNo = httpAjbInfoRequest.getPageNo();
        }
        if(httpAjbInfoRequest.getPageSize() != null && httpAjbInfoRequest.getPageSize() > 0){
        	pageSize = httpAjbInfoRequest.getPageSize();
        }
        
        AjbSearchDto ajbSearch = new AjbSearchDto();
        ajbSearch.setUserId(userId);

        ajbSearch.setAccountBookType(httpAjbInfoRequest.getAccountBookType());
        ajbSearch.setPageNo(pageNo);
        ajbSearch.setPageSize(pageSize);
        //查询账本记录
        Page<AccountBookRecordDto>  recordPage = ajbProxy.queryBookRecords(ajbSearch);
        
        AjbInfoResponseVo ajbInfo = new AjbInfoResponseVo();
        setAjbAccountVoForResponse(ajbInfo, userId);
        setAjbRecordPageForResponse(ajbInfo, recordPage);
        
		return ajbInfo;
	}
	
	private void setAjbRecordPageForResponse(AjbInfoResponseVo ajbInfo, Page<AccountBookRecordDto> recordPageDto) {
		if (null != recordPageDto) {
			Page<AccountBookRecordResponseVo> recordPage = ModelMapperUtil.strictPage(recordPageDto, AccountBookRecordResponseVo.class);
					
			ajbInfo.setAjbRecordPage(recordPage);
            List<AccountBookRecordResponseVo> accountBookRecordVoList = recordPage.getList();
            if (null != accountBookRecordVoList) {
                for (AccountBookRecordResponseVo recordVo : accountBookRecordVoList) {
                    StringBuilder sb = new StringBuilder();
                    //如果是活动则显示活动文案
                    if(recordVo.getActivityCode() != null && recordVo.getActivityCode() > 0){
                    	//根据活动代码从WCM获取活动文案
                    	AjbActivityResponseVo ajbActivityResponseVo = ajbProxy.queryAjbActivityByCode(recordVo.getActivityCode().toString());
                    	if(ajbActivityResponseVo != null){
                    		recordVo.setRemark(ajbActivityResponseVo.getRemark());
                    	}else{
                    		recordVo.setRemark("");
                    	}
                    }else{
                    	String remark = AjbRemarkType.getRemark(recordVo.getStatus(), recordVo.getType());
                        sb.append(remark);
                        String noteStr = "";
                        if(StringUtils.isNotBlank(recordVo.getOrderNum())){
                     	   noteStr += recordVo.getOrderNum();
                        }
                        //备注不显示
                        if (StringUtils.isNotBlank(recordVo.getRemark())) {
                        	noteStr += recordVo.getRemark();
                        }
                        if(StringUtils.isNotBlank(noteStr)){
                        	Integer stauts =recordVo.getStatus() == null ? -1:recordVo.getStatus();
                        	Integer type =recordVo.getType() == null ? -1:recordVo.getType();
                        	//@see AjbRemarkType:0,4 ,1.2,1.4,2.4
							if ((stauts == 0 && type == 4) || (stauts == 1 && type == 2)
									|| (stauts == 1 && type == 4) || (stauts == 2 && type == 4)) {
//								sb.append("(订单号：");
//								sb.append(noteStr);
//								sb.append(")");
							}
                        }
                        recordVo.setRemark(sb.toString());
                    }
                    
                    Date createTime = recordVo.getCreateTime();
                    String createTimeStr = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(createTime.getTime());
                    recordVo.setCreateTimeStr(createTimeStr);
                }
            }
		}
		
	}

	private void setAjbAccountVoForResponse(AjbInfoResponseVo ajbInfo, Integer userId) {
		//查询用户艾积分信息
        UserAjbRecordDto ajbRecordResponseVo = ajbProxy.queryAjbDetailInfoByUserId(userId, 1, 1);
        if(ajbRecordResponseVo != null){
        	AjbAccountDto ajbAccountVo = new AjbAccountDto();
        	if(ajbRecordResponseVo.getDisplayUsableAmount()!=null){
        		ajbAccountVo.setAmount(ajbRecordResponseVo.getDisplayUsableAmount());
        	}else{
        		ajbAccountVo.setAmount(0);
        	}
        	
        	if(ajbRecordResponseVo.getFreezeAmount()!=null){
        		ajbAccountVo.setFrozenAmount(ajbRecordResponseVo.getFreezeAmount());
        	}else{
        		ajbAccountVo.setFrozenAmount(0);
        	}
        	
        	ajbAccountVo.setUserId(ajbRecordResponseVo.getUserId());
        	if(ajbRecordResponseVo.getTotalAmount()!=null){
        		ajbAccountVo.setTotalAmount(ajbRecordResponseVo.getTotalAmount());
        	}else{
        		ajbAccountVo.setTotalAmount(0);
        	}
        	
        	if(ajbRecordResponseVo.getUsedAmount()!=null){
        		ajbAccountVo.setUsedAmount(ajbRecordResponseVo.getUsedAmount());
        	}else{
        		ajbAccountVo.setUsedAmount(0);
        	}
        	
        	if(ajbRecordResponseVo.getExpiredAmount()!=null){
        		ajbAccountVo.setExpiredAmount(ajbRecordResponseVo.getExpiredAmount());
        	}else{
        		ajbAccountVo.setExpiredAmount(0);
        	}
        	
        	ajbAccountVo.setExRate(ajbRecordResponseVo.getExRate());
        	
        	if(ajbRecordResponseVo.getEndTime() != null){
        		Long endTime= new Long(ajbRecordResponseVo.getEndTime());  
        		String endTimeStr = new SimpleDateFormat("yyyy年MM月dd日").format(endTime);
            	ajbAccountVo.setEndTime(endTimeStr);
            	//计算过期时间与当前系统时间的时间差
				DateTime expiredTime = new DateTime(endTime);
				DateTime nowDate = new DateTime();
				Integer dayNum = Days.daysBetween(nowDate, expiredTime).getDays();
				if(dayNum > 9 || dayNum < 0){
					ajbAccountVo.setExpiredDesc("");
				}else{
					if(ajbRecordResponseVo.getDisplayUsableAmount() != null && ajbRecordResponseVo.getDisplayUsableAmount() > 0){
						ajbAccountVo.setExpiredDesc("即将过期");
					}else{
						ajbAccountVo.setExpiredDesc("");
					}
				}
        	}else{
        		ajbAccountVo.setEndTime("");
        		ajbAccountVo.setExpiredDesc("");
        	}
        	ajbInfo.setAjbAccountVo(ajbAccountVo);
        }
	}

	@Override
	public AccountInfoResponseVo getAccountInfo(MyAccountRequestVo request) {
		if (request == null ||  StringUtil.isNullOrEmpty(request.getAccessToken())) {
        	throw new BusinessException(MessageConstant.USER_PASS_EMPTY);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
    	if (userDto == null) {
    		throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
    	}
        Integer userId = userDto.getId();

        AccountInfoResponseVo httpAccountInfo = new AccountInfoResponseVo();
        //查询用户艾积分信息（有效期）
        UserAjbRecordDto ajbRecordResponseVo = ajbProxy.queryAjbDetailInfoByUserId(userId, 1, 1);
        if(ajbRecordResponseVo != null && ajbRecordResponseVo.getDisplayUsableAmount() != null){
        	//有效期内可用艾积分数量
        	httpAccountInfo.setAjbAmount(ajbRecordResponseVo.getDisplayUsableAmount());
        }
        
        if (StringUtils.isNotBlank(userDto.getMobile())) {
            String mobile = userDto.getMobile();
            //算出我的所有抵用券金额
            double amount = voucherService.queryVoucherAmountByMobile(mobile);
            httpAccountInfo.setVoucherTotal(amount);
            Double amountPayable = request.getAmountPayable();
            if (amountPayable != null) {
                //算出我能用的抵用券
                List<Voucher> voucherEnableList = voucherService.getEnableVoucherList(mobile, amountPayable);
                if (voucherEnableList != null) {
                    httpAccountInfo.setVoucherEnableList(voucherEnableList);
                } else {
                    httpAccountInfo.setVoucherEnableList(new ArrayList<Voucher>());
                }
            } else {
                httpAccountInfo.setVoucherEnableList(new ArrayList<Voucher>());
            }
        }
        
		return httpAccountInfo;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
    public List<UserOrderResponse120> queryAllUserOrder120(Long userId,Long orderId) {
        //查询订单信息
        List<UserOrder> userOrderList = orderDao.queryUserOrder(userId,orderId);
        
        //取订单中的每个商品的一张图片start
        String orderIds = "";
        if (userOrderList != null && !userOrderList.isEmpty()) {
            for (UserOrder userOrder : userOrderList) {
                if (null != userOrder && null != userOrder.getOrderId()) {
                    if (StringUtils.isNotBlank(orderIds)) {
                        orderIds = orderIds + "," + userOrder.getOrderId();
                    } else {
                        orderIds = "" + userOrder.getOrderId();
                    }
                }
            }
        }
        Map<Long, String> map = new HashMap<Long, String>();
        if (orderIds != null && orderIds.trim().length() != 0) {
            List<UserOrder> userOrderList2 = orderDao.selOrderProductImages(orderIds);
            for (UserOrder userOrder : userOrderList2) {
                if (null != userOrder && null != userOrder.getOrderId()
                        && StringUtils.isNotBlank(userOrder.getPictureUrlOriginal())) {
                    List<String> imagesList = ImageUtil.removeEmptyStr(userOrder
                            .getPictureUrlOriginal());
                    for (String s : imagesList) {
                        if (StringUtils.isNotBlank(s)) {
                            if (null != map.get(userOrder.getOrderId())) {
                                String temp = map.get(userOrder.getOrderId());
                                temp = temp + "," + s;
                                map.put(userOrder.getOrderId(), temp);
                            } else {
                                map.put(userOrder.getOrderId(), s);
                            }
                            break;
                        }
                    }

                }
            }
        }

        //取订单中的每个商品的一张图片 end 
        
        List<UserOrderResponse120> userOrderResponseList = new ArrayList<UserOrderResponse120>();
        if (null != userOrderList && userOrderList.size() > 0) {
            for (UserOrder userOrder : userOrderList) {
            	UserOrderResponse120 userOrderResponse = new UserOrderResponse120(userOrder);
            	
            	if(null != userOrder 
            			&& null != userOrder.getOrderId()
            			&& StringUtils.isNotBlank(map.get(userOrder.getOrderId()))){
            		List<String> strResponseList = new ArrayList<String>();//去除空
            		String temp = map.get(userOrder.getOrderId());
            		for(String s : temp.split(",")){
            			if(StringUtils.isNotBlank(s)){
            				strResponseList.add(s);
            			}
            		}
            		userOrderResponse.setProductImages(strResponseList);
            	}
            	
            	if(null != userOrder) {
                    String pictureStr = userOrder.getPictureUrlOriginal();
                    if (StringUtils.isNotBlank(pictureStr)
                            && pictureStr.contains("[")  && pictureStr.contains("]")) {
                        List<String> strResponseList = new ArrayList<String>();//去除空
                        JSONArray jsonArray = JSONArray.fromObject(pictureStr);
                        List<String> strList = (List<String>) JSONArray.toList(
                                jsonArray, String.class);
                        if (null != strList && strList.size() > 0) {
                            for (String str : strList) {
                                if (null != str && !"".equals(str)) {
                                    strResponseList.add(str);
                                }
                            }
                        }
                        userOrderResponse.setPictureUrlOriginal(strResponseList);
                    }
            	}

            	Double payedMoney = orderDao.selPayedMoneyByOrderId(userOrderResponse.getOrderId());
            	Double totalFee = userOrderResponse.getOrderPrice();
            	if(null != payedMoney && payedMoney > 0) {
            		totalFee = totalFee - payedMoney;
            	}
            	userOrderResponse.setLeftMoney(totalFee);
                userOrderResponseList.add(userOrderResponse);
            }
        }
        return userOrderResponseList;
    }
	
	@Override
	public Boolean cancelOrder(HttpUserOrderRequest userOrderRequest) {
		UserOrder userOrder =new UserOrder();
		userOrder.setOrderId(userOrderRequest.getOrderId());
		userOrder.setOrderStatus(3l);
		userOrder.setReasonId(userOrderRequest.getReasonId());
		Integer updateCount=orderDao.updateOrder(userOrder);
		
		TOrder tOrder = orderService.queryMyOrderByOrderId(userOrderRequest.getOrderId());
		if(null != tOrder){
			cashCouponService.modifyAccountPay(tOrder.getUserId(), tOrder.getCouponPay(), 2);
		}
		
		return updateCount==1;
	}
	
	@Override
	public Boolean deleteOrder(HttpUserOrderRequest userOrderRequest) {
		UserOrder userOrder =new UserOrder();
		userOrder.setOrderId(userOrderRequest.getOrderId());
		userOrder.setOrderStatus(-1l);
		userOrder.setReasonId(userOrderRequest.getReasonId());
		TOrder tOrder = orderService.queryMyOrderByOrderId(userOrderRequest.getOrderId());
		Integer updateCount = 0;
		if(null != tOrder && tOrder.getOrderStatus()==3){
			updateCount = orderDao.updateOrder(userOrder);
		}
		return updateCount==1;
	}

	@Override
	public UserOrder queryUserOrderByOrderId(Long orderId) {
		return orderDao.queryUserOrderByOrderId(orderId);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ProductOrderDetail> queryOrderDetailsByOrderId(Long orderId) {
		List<ProductOrderDetail> orderDetails=orderDao.queryOrderDetailsByOrderId(orderId);
		if(orderDetails!=null&&!orderDetails.isEmpty()){
			for (int i = 0,l=orderDetails.size(); i < l; i++) {
				    String pictureStr = orderDetails.get(i).getPictureUrls();
	            	if(StringUtils.isNotBlank(pictureStr) 
	                        && pictureStr.contains("[")  && pictureStr.contains("]")) {
	                    JSONArray jsonArray = JSONArray.fromObject(orderDetails.get(i).getPictureUrls());
	                    if (jsonArray != null) {
	                    	orderDetails.get(i).setPictureUrlOriginal(JSONArray.toList(jsonArray, String.class));//转换图片为list
	                    }
	            	}
			}
		}
		return orderDetails;
	}
	
}
