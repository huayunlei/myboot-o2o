package com.ihomefnt.o2o.service.service.art;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoucherDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.ajb.dto.UserAjbRecordDto;
import com.ihomefnt.o2o.intf.domain.art.dto.*;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarOrderCreateRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarOrderDetailCreateRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.AppArtStarSku;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubmitOrderResponse;
import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.*;
import com.ihomefnt.o2o.intf.proxy.activity.BoeActivityProxy;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.art.*;
import com.ihomefnt.o2o.intf.proxy.cart.ShoppingCartProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.manager.util.art.ArtListFilterUtils;
import com.ihomefnt.o2o.service.service.collage.FilterProductConfig;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 艺术品业务层
 *
 * @author Charl
 * @version V1.0
 * @Title: ArtServiceImpl.java
 * @Description: TODO
 * @date 2016年7月15日 下午1:33:29
 */
@Service
public class ArtServiceImpl implements ArtService {

    @Autowired
    ServiceCaller serviceCaller;

    @Autowired
    ArtProxy artDao;

    @Autowired
    UserService userService;

    @Autowired
    ArtStockProxy artStockProxy;

    @Autowired
    ArtOrderProxy artOrderProxy;

    @Autowired
    DicProxy dicProxy;

    @Autowired
    AddressProxy addressProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private ArtCategoryProxy artCategoryProxy;

    @Autowired
    private ArtInfoProxy artInfoProxy;

    @Value("${host}")
    public String host;

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    ShoppingCartProxy shoppingCartProxy;
    @Autowired
    private AjbProxy ajbRechargeProxy;
    @Autowired
    private BoeActivityProxy boeActivityProxy;
    @Autowired
    private StarProductProxy starProductProxy;

    private static final Logger LOG = LoggerFactory.getLogger(ArtServiceImpl.class);

    private static final HashMap<String, String> itemName = new HashMap<String, String>() {
        {
            put("ARTWORK_LOGO_URL", StaticResourceConstants.ARTWORK_LOGO_URL); //艺术品logo图
            put("ARTWORK_ITEM_NAME1", "艺术家"); //艺术家栏目名称
            put("ARTWORK_ITEM_NAME2", "品牌"); //艺术家栏目名称
            put("ARTWORK_DESC_NAME", "艺术顾问建议"); //艺术顾问栏目名称
            put("ARTWORK_IMAGETEXT_NAME", "图文详情"); //艺术品图文详情
            put("ARTWORK_IMAGETEXT_NAME_NONE", "暂无图文详情"); //暂无图文详情

            put("ARTIST_TITLE1", "艺术家主页"); //艺术家主页title
            put("ARTIST_TITLE2", "品牌主页"); //艺术家主页title

            put("ARTIST_SELF_DESC1", "个人经历"); //艺术家主页个人经历
            put("ARTIST_SELF_DESC2", "关于品牌"); //艺术家主页关于品牌

            put("ARTIST_CREATE_STATEMENT", "创作自述"); //艺术家主页创作自述
        }
    };

    //艺术品价格区间选项
    private static final List<FieldInfo> priceList = new ArrayList<FieldInfo>() {
        {
            add(new FieldInfo(1, "1000元以内", 0, 1000));
            add(new FieldInfo(2, "1000~2000元", 1000, 2000));
            add(new FieldInfo(3, "2000~5000元", 2000, 5000));
            add(new FieldInfo(4, "5000~1万元", 5000, 10000));
            add(new FieldInfo(5, "1万~2万元", 10000, 20000));
            add(new FieldInfo(6, "2万元以上", 20000, null));
        }
    };

    private static final String TYPE_NAME = "类别";
    private static final String PRICE_NAME = "价格范围";
    private static final String ROOM_NAME = "适用空间";

    private static final int ExRate = 100;//汇率

    private static final String CREATION_TIME = "当代";

    @Override
    public HttpArtListResponse getArtworkList(HttpArtListRequest request) {
        HttpArtListResponse result = new HttpArtListResponse();
        //第一次请求返回标签列表
        if (null != request.getIsFirst() && request.getIsFirst()) {
            List<ArtSpace> artSpaceList = artDao.getArtSpaceList();
            ArtSpaceItem allItem = new ArtSpaceItem();
            allItem.setSpaceType(0);
            allItem.setSpaceName("全部");
            for (ArtSpace artSpace : artSpaceList) {
                artSpace.getSpaceItems().add(0, allItem);
            }
            result.setArtNameList(artSpaceList);
        }
        //设置空间id
        if (null == request.getSpaceId() || 0 == request.getSpaceId()) {
            result.setSpaceId(0);
        } else {
            result.setSpaceId(request.getSpaceId());
        }
        //处理分页数据
        int pageSize = request.getPageSize();
        pageSize = (pageSize > 0 ? pageSize : 10);
        request.setPageSize(pageSize);
        int pageNo = request.getPageNo();
        long startIndex = (pageNo > 1 ? (pageNo - 1) * pageSize : 0);
        request.setStartIndex(startIndex);
        Long artworkListCount = artDao.getArtworkListCount(request);
        int pageCount = 0;
        pageCount = (int) (artworkListCount / pageSize);
        if (artworkListCount % pageSize > 0) {
            pageCount++;
        }
        result.setTotalPage(pageCount);
        //请求列表
        List<Artwork> artworkList = artDao.getArtworkList(request);
        if (artworkList != null && artworkList.size() > 0) {
            Iterator iterator = artworkList.iterator();
            while (iterator.hasNext()) {
                Artwork artwork = (Artwork) iterator.next();
                int artistId = (int) artwork.getArtistId();
                if (null != artwork.getHeadImg()) {
                    if (null != request.getOsType() && request.getOsType().equals(1)) {
                        Integer width1 = request.getWidth();

                        artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width1, 0));
                    } else {
                        Map<String, Object> imageSize2 = QiniuImageUtils.getImageSizeByType(artwork.getHeadImg(), "?imageInfo", serviceCaller);
                        int height = 0;
                        if (imageSize2.get("height") != null) {
                            height = (int) imageSize2.get("height");
                        }
                        int width = 0;
                        if (imageSize2.get("width") != null) {
                            width = (int) imageSize2.get("width");
                        }
                        int max = height >= width ? height : width;
                        if (max > 1080) {
                            width = 1080;
                        }

                        if (height >= width) {
                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width, 0));
                        } else {
                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 0, height));
                        }
                    }
                }

                if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
                    artwork.setCreationTime("当代");
                }

//				if(!artwork.getSize().contains("cm")) {
//					artwork.setSize(artwork.getSize()+"cm");
//				}

            }
        }
        /*
         *   加个过滤 把活动商品过滤掉
         */

        artworkList = ArtListFilterUtils.filterActivityProduct(artworkList);
        result.setArtworkList(artworkList);
        return result;
    }

    @Override
    public HttpArtworkDetailResponse getArtworkDetail(HttpArtworkDetailRequest request) {
        HttpArtworkDetailResponse result = new HttpArtworkDetailResponse();
        Long artworkId = request.getArtworkId();
        //获取艺术品信息
        Artwork artwork = artDao.getArtworkById(artworkId);
        if (null == artwork) {
            return null;
        }
        if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
            artwork.setCreationTime("当代");
        }
        BigDecimal price = artwork.getPrice();
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal double1 = price.multiply(new BigDecimal(ExRate));
            BigDecimal double2 = double1.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
            if (price.compareTo(BigDecimal.ONE) > 0) {
                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                String format = decimalFormat.format(double2);
                artwork.setAjbPrice(format + "万");
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                String format = decimalFormat.format(double1);
                artwork.setAjbPrice(format);
            }
        }
        artwork.setHasFavirate(false);
//		ArtStockCountInfo queryProductStock = artStockService.queryProductStock(Integer.parseInt(artworkId.toString()));
        com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo queryProductStock = artStockProxy.queryInventory(Integer.parseInt(artworkId.toString()));
        if (null != queryProductStock) {
            artwork.setStock(queryProductStock.getEnableInventory());
        } else {
            artwork.setStock(0);
        }
        //判断是否进行场景展示(“装饰艺术”中除了“玻璃钢”类，其他都是可以挂墙上的)
        result.setDisplayScene(true);
        if (artwork.getType() == 28) {
            List<ArtSpaceItem> spaceItems = artwork.getSpaceItems();
            if (null != spaceItems) {
                for (ArtSpaceItem artSpaceItem : spaceItems) {
                    if (artSpaceItem.getSpaceType() == 40) {
                        result.setDisplayScene(false);
                        break;
                    }
                }
            }
        }

        Long u_Id = 0l;
        if (request.getUserInfo() != null && request.getUserInfo().getId() != null) {
            UserDto userByToken = userProxy.getUserByToken(request.getAccessToken());
            if (null != userByToken && null != userByToken.getMember()) {
                Integer id = userByToken.getMember().getUserId();
                u_Id = Long.parseLong(id.toString());
                Long viewArtworkCount = artDao.getViewArtworkCount(artworkId, u_Id, 3);
                if (viewArtworkCount > 0) {
                    artwork.setHasFavirate(true);
                }
            }
        }
        artDao.artworkLog(1, artworkId, u_Id);

        if (null != artwork.getHeadImg()) {
            Picture picture = AppRedisUtil.getRedisImageSize(artwork.getHeadImg(), serviceCaller);
            if (picture != null) {
                artwork.setHeight(picture.getHeight());
                artwork.setWidth(picture.getWidth());
            }
        }
        int screenSize = 750;
        if (null != request.getWidth()) {
            screenSize = request.getWidth();
        }
        //获取艺术品图片信息
        List<ArtworkImage> artworkImages = artDao.getArtworkImages(artworkId);
        if (null != artworkImages && artworkImages.size() > 0) {

            Iterator<ArtworkImage> iterator = artworkImages.iterator();
            while (iterator.hasNext()) {
                ArtworkImage next = iterator.next();
                if (!StringUtils.isBlank(next.getImageUrl())) {
                    //等比压缩
                    String imgUrl = QiniuImageUtils.compressDetailImage(next.getImageUrl(), QiniuImageQuality.MEDIUM, screenSize, 0);
                    Picture picture = AppRedisUtil.getRedisImageSize(next.getImageUrl(), serviceCaller);
                    next.setImageUrl(imgUrl);
                    if (picture != null) {
                        next.setWidth(picture.getWidth());
                        next.setHeight(picture.getHeight());
                    }
                } else {
                    iterator.remove();
                }
            }
            artwork.setImageCount(artworkImages.size());
            result.setArtworkImages(artworkImages);
        } else {
            artwork.setImageCount(0);
            result.setArtworkImages(artworkImages);
        }
        //获取艺术品对应艺术家的信息
        if (artwork.getArtistType() == 1) {
            if (artwork.getArtistId() != 0l) {
                Artist artistInfoList = artDao.getArtworkArtistInfoById(artwork.getArtistId());
                int artistId = (int) artistInfoList.getArtistId();

                UserDto userById = userProxy.getUserById(artistId);
                if (null != userById) {
                    MemberDto member = userById.getMember();
                    if (null != member) {
                        artwork.setArtistName(member.getNickName());
                        String headImgUrl = AliImageUtil.imageCompress(member.getuImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL);
                        artwork.setAvast(headImgUrl);
                    }
                } else {
                    artwork.setArtistId(0l);
                }

                List<Artwork> artworkList = artistInfoList.getArtworkList();
                //艺术家作品数
                Long artistArtworkCount = 1l;
                //艺术家总的作品浏览数
                Long artistArtworkViewCount = 1l;
                if (null != artworkList && artworkList.size() > 0) {
                    //艺术家作品数
                    artistArtworkCount = (long) artworkList.size();
                    List<Long> artIdList = new ArrayList<Long>();
                    for (Artwork artwork2 : artworkList) {
                        artIdList.add(artwork2.getArtWorkId());
                    }
                    //改造为批量查询,提高查询性能,同时数据库t_art_product_log增加索引(fid_product,op)
                    artistArtworkViewCount = artDao.getViewArtworkTotalCount(artIdList, 1);
                }
                artwork.setArtistArtworkCount(artistArtworkCount);
                artwork.setArtistArtworkViewCount(artistArtworkViewCount);
                //艺术品艺术家项目名称
                result.setArtistItemName(itemName.get("ARTWORK_ITEM_NAME1"));
                //新星星背板图  （2.10.2版本不需要）
                //result.setBackgroundImages(backgroundImages);
            }
        } else if (artwork.getArtistType() == 2) {
            if (artwork.getArtistId() != 0l) {
                long artistId = artwork.getArtistId();
                ArtStudio artworkStudio = artDao.getArtworkStudioById(artistId);
                if (null != artworkStudio) {
                    artwork.setArtistName(artworkStudio.getName());
                    artwork.setAvast(artworkStudio.getImage());
                }

                Artist artistInfoList = artDao.getArtworkArtistInfoById(artistId);
                List<Artwork> artworkList = artistInfoList.getArtworkList();
                //艺术家作品数
                Long artistArtworkCount = 1l;
                //艺术家总的作品浏览数
                Long artistArtworkViewCount = 1l;
                if (null != artworkList && artworkList.size() > 0) {
                    //艺术家作品数
                    artistArtworkCount = (long) artworkList.size();
                    List<Long> artIdList = new ArrayList<Long>();
                    for (Artwork artwork2 : artworkList) {
                        artIdList.add(artwork2.getArtWorkId());
                    }
                    //改造为批量查询,提高查询性能,同时数据库t_art_product_log增加索引(fid_product,op)
                    artistArtworkViewCount = artDao.getViewArtworkTotalCount(artIdList, 1);

                }
                artwork.setArtistArtworkCount(artistArtworkCount);
                artwork.setArtistArtworkViewCount(artistArtworkViewCount);
                //艺术品艺术家项目名称
                result.setArtistItemName(itemName.get("ARTWORK_ITEM_NAME2"));
            }
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(artwork.getHeadImg())) {
            artwork.setHeadImg(QiniuImageUtils.compressImageAndSamePicTwo(artwork.getHeadImg(), screenSize, -1));
        } else {
            artwork.setHeadImg("");
        }

        setArtWorkAndScene(result, artwork, artworkId, request.getOsType(), request.getWidth());

        //购买说明配置
        result.setPayDes("https://m.ihomefnt.com/artist/notice");
        //艺术品logo
        result.setLogoUrl(itemName.get("ARTWORK_LOGO_URL"));
        //艺术品描述项目名称
        result.setDescName(itemName.get("ARTWORK_DESC_NAME"));
        //图文详情
        result.setImageText(itemName.get("ARTWORK_IMAGETEXT_NAME"));
        //暂无图文详情
        result.setNoneImageText(itemName.get("ARTWORK_IMAGETEXT_NAME_NONE"));
        return result;
    }

    private void setArtWorkAndScene(HttpArtworkDetailResponse result, Artwork artwork, Long artworkId, Integer osType, Integer width) {
        List<IdentityTaskAction<Object>> identityTaskActionList = new ArrayList<>();
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override public Object doInAction() throws Exception {
                return artDao.getSceneImageById(artworkId);
            }
            @Override public String identity() {
                return "getSceneImageById";
            }
        });

        identityTaskActionList.add(new IdentityTaskAction() {
            @Override public Object doInAction() throws Exception {
                return artDao.getViewArtworkCount(artworkId, null, 3);
            }
            @Override public String identity() {
                return "getViewArtworkCount_"+3;
            }
        });
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override public Object doInAction() throws Exception {
                return artDao.getViewArtworkCount(artworkId, null, 1);
            }
            @Override public String identity() {
                return "getViewArtworkCount_"+1;
            }
        });
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override public Object doInAction() throws Exception {
                return artDao.getViewArtworkCount(artworkId, null, 4);
            }
            @Override public String identity() {
                return "getViewArtworkCount_"+4;
            }
        });
        // 执行
        Map<String, Object> taskResultMap = TaskProcessManager.getTaskProcess()
                .executeIdentityTask(identityTaskActionList);

        //获取场景体验图
        ArtworkImageDto artWorkImage = (ArtworkImageDto) taskResultMap.get("getSceneImageById");
        result.setDisplayScene(artWorkImage != null);
        if (result.isDisplayScene()) {
            String sceneImage = AliImageUtil.imageCompress(artWorkImage.getImageUrl(), osType, width, ImageConstant.SIZE_SMALL);
            result.setSceneImage(sceneImage);
            result.setSceneImageHeight(artWorkImage.getHeight());
            result.setSceneImageWidth(artWorkImage.getWidth());
        }
        //获取艺术品喜欢/收藏的数量
        artwork.setFavirate((Long) taskResultMap.get("getViewArtworkCount_"+3));
        //获取艺术品浏览的数量
        artwork.setScan((Long) taskResultMap.get("getViewArtworkCount_"+1));
        //获取艺术品分享的数量
        artwork.setShare((Long) taskResultMap.get("getViewArtworkCount_"+4));

        result.setArtwork(artwork);
    }

    @Override
    public Artist getArtistInfo(HttpArtistHomeRequest request) {
        Artist result = new Artist();
        Long artistId = request.getArtistId();

        Artist artistInfo = artDao.getArtworkArtistInfoById(artistId);
        if (null != artistInfo) {
            result.setArtistType(artistInfo.getArtistType());
            if (artistInfo.getArtistType() == 1) {

                UserDto userById = userProxy.getUserById(Integer.parseInt(artistId.toString()));
                if (userById != null) {
                    MemberDto member = userById.getMember();
                    result.setArtistId(userById.getId());
                    if (null != member) {
                        String avast = AliImageUtil.imageCompress(member.getuImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL);
                        result.setAvast(avast);
                        result.setBrief(member.getBrief());
                        result.setName(member.getNickName());
                        result.setGendar((null == member.getGender()) ? 0 : member.getGender());
                    }
                    //查询艺术家自述信息
                    List<String> selfDesc = artDao.getArtistSelfDescById(artistId);
                    result.setSelfDesc(selfDesc);
                } else {
                    return null;
                }
                //设置可配信息
                result.setTitle(itemName.get("ARTIST_TITLE1"));
                result.setSelfExp(itemName.get("ARTIST_SELF_DESC1"));
                result.setCreateStatement(itemName.get("ARTIST_CREATE_STATEMENT"));
            } else if (artistInfo.getArtistType() == 2) {
                ArtStudio artworkStudio = artDao.getArtworkStudioById(artistId);
                if (null != artworkStudio) {
                    result.setArtistId(artworkStudio.getStudioId());
                    result.setName(artworkStudio.getName());
                    String avast = AliImageUtil.imageCompress(artworkStudio.getImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL);
                    result.setAvast(avast);
                    result.setBrief(artworkStudio.getDesc());
                } else {
                    return null;
                }
                //设置可配信息
                result.setTitle(itemName.get("ARTIST_TITLE2"));
                result.setSelfExp(itemName.get("ARTIST_SELF_DESC2"));
            }
        }

        //查询艺术家经历
        List<String> artistExperienceById = artDao.getArtistExperienceById(artistId);

        result.setExperience(artistExperienceById);
        //查询艺术家名下艺术品信息
        List<Artwork> artworkByArtistId = artDao.getArtworkByArtistId(artistId);
        if (null != artworkByArtistId && artworkByArtistId.size() > 0) {
            result.setArtworkCount(artworkByArtistId.size());
        } else {
            result.setArtworkCount(0);
        }
        for (Artwork artwork : artworkByArtistId) {

            artwork.setHeadImgObj(getImgEntity(artwork.getHeadImg(), request.getWidth()));
            artwork.setHeadImg(QiniuImageUtils.compressImage_40(artwork.getHeadImg()));
            if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
                artwork.setCreationTime("当代");
            }
        }
        result.setArtworkList(artworkByArtistId);

        return result;
    }

    @Override
    public HttpArtworkShareResponse getArtworkShareInfoById(HttpShareArtWorkRequest request) {
        HttpArtworkShareResponse result = new HttpArtworkShareResponse();
        Content content = new Content();
        int typeId = request.getTypeId();
        Long artworkId = request.getShareId();

        UserDto userByToken = userProxy.getUserByToken(request.getAccessToken());
        if (null != userByToken && null != userByToken.getMember()) {
            Integer id = userByToken.getMember().getUserId();
            if (typeId == 4) {
                Artwork artwork = artDao.getArtworkById(artworkId);
                if (artwork == null) {
                    return null;
                }
                long artistId = artwork.getArtistId();

                UserDto userById = userProxy.getUserById((int) artistId);
                if (null != userById) {
                    artDao.artworkLog(typeId, artworkId, id);
                    content.setTitle(artwork.getName() + "-" + (artwork.getArtistName() == null ? "" : artwork.getArtistName()));
                    if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
                        artwork.setCreationTime("当代");
                    }
                    content.setDesc(artwork.getCategory() + "," + artwork.getSize() + "," + artwork.getCreationTime());

                    content.setUrl(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 150, 150));
                    result.setShareContent(content);
                    result.setmUrl("https://m.ihomefnt.com/artist/listDetail/" + artworkId);
                    return result;
                } else {
                    return null;
                }
            } else if (typeId == 3) {
                Long viewArtworkCount = artDao.getViewArtworkCount(artworkId, (long) id, 3);
                if (viewArtworkCount > 0) {
                    return null;
                } else {
                    artDao.artworkLog(typeId, artworkId, id);
                    return result;
                }

            } else {
                artDao.artworkLog(typeId, artworkId, id);
                return null;
            }
        } else {
            if (typeId == 4) {
                Artwork artwork = artDao.getArtworkById(artworkId);

                if (null != artwork) {

                    content.setTitle(artwork.getName() + "-" + (null == artwork.getArtistName() ? "" : artwork.getArtistName()));
                    if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
                        artwork.setCreationTime("当代");
                    }
                    content.setDesc(artwork.getCategory() + "," + artwork.getSize() + "," + artwork.getCreationTime());
                    content.setUrl(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 150, 150));
                    result.setShareContent(content);
                    result.setmUrl("https://m.ihomefnt.com/artist/listDetail/" + artworkId);
                    return result;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

    }

    @Override
    public long getArtworkStockById(HttpArtworkDetailRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        if (!userProxy.checkLegalUser(request.getAccessToken())) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        Long artworkId = request.getArtworkId();
        int artworkStock = 0;

        com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo queryProductStock = artStockProxy.queryInventory(Integer.parseInt(artworkId.toString()));
        if (null != queryProductStock) {
            artworkStock = queryProductStock.getEnableInventory();
        }
        return artworkStock;
    }

    @Override
    public HttpArtworkOrderResponse getArtworkOrderInfoById(HttpArtworkDetailRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        if (!userProxy.checkLegalUser(request.getAccessToken())) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Long artworkId = request.getArtworkId();
        HttpArtworkOrderResponse result = null;
        Artwork artworkOrderInfo = null;
        com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo queryProductStock =null;
        if(request.getSaleType()!=null && request.getSaleType()==1){
            List<Artwork> artworkOrderList = artDao.getArtworkOmsOrderInfo(new ArtworkOrderRequest(Arrays.asList(request.getSkuId())));
            if(CollectionUtils.isNotEmpty(artworkOrderList)){
                artworkOrderInfo = artworkOrderList.get(0);
                if(artworkOrderInfo == null || artworkOrderInfo.getProductId() == null){
                    throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
                }
            }
            queryProductStock= artStockProxy.queryInventory(Integer.parseInt(artworkId.toString()));
        }else{
            artworkOrderInfo = artDao.getArtworkOrderInfo(artworkId);
            queryProductStock = artStockProxy.queryOmsInventory(artworkId.toString());
        }

        if (null != artworkOrderInfo) {
            int artworkStock = 0;
            if (null != queryProductStock) {
                artworkStock = queryProductStock.getEnableInventory();
            }
            result = new HttpArtworkOrderResponse();
            result.setArtworkId(artworkOrderInfo.getArtWorkId());
            result.setProductId(artworkOrderInfo.getProductId());
            result.setHeadImg(QiniuImageUtils.compressImage_40(artworkOrderInfo.getHeadImg()));
            result.setName(artworkOrderInfo.getName());
            LOG.info("ArtServiceImpl==========artworkOrderInfo.getName():" + artworkOrderInfo.getName());
            result.setPrice(artworkOrderInfo.getPrice());
            result.setStock(artworkStock);
            LOG.info("ArtServiceImpl==========result.getName():" + result.getName());
        }
        return result;
    }

    @Override
    public HttpArtListResponse getArtworkListByParam(HttpArtListWithParamRequest request) {
        HttpArtListResponse result = new HttpArtListResponse();
        if (StringUtil.isNullOrEmpty(request.getSearchParam())) {
            request.setSearchParam(null);
        }
        //处理分页信息
        int pageSize = request.getPageSize();
        pageSize = (pageSize > 0 ? pageSize : 10);
        request.setPageSize(pageSize);
        int pageNo = request.getPageNo();
        long startIndex = (pageNo > 1 ? (pageNo - 1) * pageSize : 0);
        request.setStartIndex(startIndex);
        Long artworkListCount = artDao.getArtWorkCountByParam(request);
        int pageCount = 0;
        pageCount = (int) (artworkListCount / pageSize);
        if (artworkListCount % pageSize > 0) {
            pageCount++;
        }
        result.setTotalPage(pageCount);
        //获取艺术品列表
        List<Artwork> artWorkListByParam = artDao.getArtWorkListByParam(request);
        if (artWorkListByParam != null && artWorkListByParam.size() > 0) {
            Iterator iterator = artWorkListByParam.iterator();
            while (iterator.hasNext()) {
                Artwork artwork = (Artwork) iterator.next();
                if (null != artwork.getHeadImg()) {
                    artwork.setHeadImgObj(getImgEntity(artwork.getHeadImg(), request.getWidth()));
                    if (null != request.getOsType() && request.getOsType().equals(1)) {
                        Integer width1 = request.getWidth();

                        artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width1, 0));
                    } else {
                        Map<String, Object> imageSize2 = QiniuImageUtils.getImageSizeByType(artwork.getHeadImg(), "?imageInfo", serviceCaller);
                        int height = 0;
                        if (imageSize2.get("height") != null) {
                            height = (int) imageSize2.get("height");
                        }
                        int width = 0;
                        if (imageSize2.get("width") != null) {
                            width = (int) imageSize2.get("width");
                        }
                        int max = height >= width ? height : width;
                        if (max > 1080) {
                            width = 1080;
                        }

                        if (height >= width) {

                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width, 0));
                        } else {

                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 0, height));
                        }
                    }
                }
            }
        }
        /*
         * 加过滤活动商品
         * alter by jerfan cang
         */
        artWorkListByParam = ArtListFilterUtils.filterActivityProduct(artWorkListByParam);
        result.setArtworkList(artWorkListByParam);
        return result;
    }

    @Override
    public Map<String, Object> getArtworkFilterInfo() {
        //获取艺术品类型分类信息
        List<ArtworkFilterInfo> artworkTypeInfo = artDao.getArtworkTypeInfo();
        //获取艺术品空间分类信息
        List<ArtworkFilterInfo> artworkRoomInfo = artDao.getArtworkRoomInfo();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("typeList", artworkTypeInfo);
        result.put("typeName", TYPE_NAME);
        Map<String, Object> result1 = new HashMap<String, Object>();
        result1.put("roomList", artworkRoomInfo);
        result1.put("roomName", ROOM_NAME);
        Map<String, Object> result2 = new HashMap<String, Object>();
        result2.put("priceList", priceList);
        result2.put("priceName", PRICE_NAME);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("typeInfo", result);
        response.put("roomInfo", result1);
        response.put("priceInfo", result2);

        return response;
    }

    /**
     * 构建商品价格列表
     *
     * @param priceIds
     * @return
     */
    private List<Map<String, Object>> buildProductPriceList(List<Integer> priceIds) {
        List<Map<String, Object>> result = null;
        if (null != priceIds && priceIds.size() > 0) {
            result = new ArrayList<Map<String, Object>>();
            for (Integer priceId : priceIds) {
                for (FieldInfo fieldInfo : priceList) {
                    if (priceId == fieldInfo.getFieldId()) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("priceStart", fieldInfo.getStart());
                        params.put("priceEnd", fieldInfo.getEnd());
                        result.add(params);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 切图
     *
     * @param artworkList
     * @param osType
     * @param width1
     */
    private void buildImageSize(List<Artwork> artworkList, Integer osType, Integer width1) {
        if (artworkList != null && artworkList.size() > 0) {
            Iterator iterator = artworkList.iterator();
            while (iterator.hasNext()) {
                Artwork artwork = (Artwork) iterator.next();
                int artistId = (int) artwork.getArtistId();
                if (null != artwork.getHeadImg()) {
                    ImageEntity entity = getImgEntity(artwork.getHeadImg(), width1);
                    artwork.setHeadImgObj(entity);
                    if (null != osType && osType.equals(1)) {
                        artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width1, 0));
                    } else {
                        Map<String, Object> imageSize2 = QiniuImageUtils.getImageSizeByType(artwork.getHeadImg(), "?imageInfo", serviceCaller);
                        int height = 0;
                        if (imageSize2.get("height") != null) {
                            height = (int) imageSize2.get("height");
                        }
                        int width = 0;
                        if (imageSize2.get("width") != null) {
                            width = (int) imageSize2.get("width");
                        }
                        int max = height >= width ? height : width;
                        if (max > 1080) {
                            width = 1080;
                        }

                        if (height >= width) {
                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, width, 0));
                        } else {
                            artwork.setHeadImg(QiniuImageUtils.compressImage(artwork.getHeadImg(), QiniuImageQuality.MEDIUM, 0, height));
                        }
                    }
                }

                if (com.ihomefnt.cms.utils.StringUtil.isNullOrEmpty(artwork.getCreationTime())) {
                    artwork.setCreationTime("当代");
                }
            }
        }
    }

    @Override
    public List<Artwork> getArtworksRecommend(int count, int recommend) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("count", count);
        //获取推荐的艺术品列表
        paramMap.put("recommend", recommend);
        return artDao.getArtworksRecommend(paramMap);
    }

    @Override
    public Artwork getArtworkByProductId(Integer productId) {
        return artDao.getArtworkOrderInfo(productId.longValue());
    }

    @Override
    public ResponseVo<?> createArtOrder(HttpCreateArtworkOrderRequest request) {
        JSONObject param = this.getJSONObjectByHttpCreateArtworkOrderRequest(request);
        Integer addressId = param.getInteger("addressId");
        if (addressId == null || addressId.intValue() == 0) {
            ResponseVo vo = new ResponseVo();
            vo.setCode(ArtStatusConstant.ADDRESS_EMPTY.getErrorCode());
            vo.setMsg(ArtStatusConstant.ADDRESS_EMPTY.getErrorMsg());
            vo.setSuccess(false);
            vo.setData(null);
            return vo;
        }
        return artOrderProxy.createArtOrder(param);
    }

    private JSONObject getJSONObjectByHttpCreateArtworkOrderRequest(HttpCreateArtworkOrderRequest request) {
        JSONObject param = new JSONObject();
        HttpUserInfoRequest user = request.getUserInfo();
        Integer userId = user.getId();
        param.put("userId", userId);
        Boolean allPay = request.getAllPay();

        if (allPay != null && allPay) {
            AjbAccountDto obj = shoppingCartProxy.ajbInfo(param);

            if (obj != null) {
                Integer amount = obj.getAmount();
                param.put("ajbAmount", amount);
            }
        } else {
            Integer ajbAmount = request.getAjbAmount();
            if (ajbAmount > 0) {
                AjbAccountDto obj = shoppingCartProxy.ajbInfo(param);
                if (obj != null) {
                    Integer amount = obj.getAmount();
                    param.put("ajbAmount", amount);
                }
            }
        }
        param.put("source", ArtOrderSourceConstant.getMsg(request.getOsType()));

        List<UserAddressResultDto> userAddressList = addressProxy.queryUserAddressList(userId);
        Integer addressId = null;
        if (CollectionUtils.isNotEmpty(userAddressList)) {
            for (UserAddressResultDto userAddress : userAddressList) {
                // 区域id 设置默认地址
                if (userAddress.getIsDefault()) {
                    addressId = userAddress.getId();
                    break;
                }
            }
        }
        if (addressId != null) {
            param.put("addressId", addressId);
        }
        param.put("remark", request.getRemark());
        List<HttpOrderProductRequest> productList = request.getProductList();
        List<JSONObject> list = new ArrayList<>();
        for (HttpOrderProductRequest product : productList) {
            JSONObject detail = new JSONObject();
            detail.put("goodsId", product.getProductId());
            detail.put("skuId",product.getSkuId());
            detail.put("amount", product.getProductCount());
            detail.put("customizedContent",product.getCustomizedContent());
            list.add(detail);
        }
        param.put("detail", list);
        param.put("saleType",request.getSaleType());
        param.put("shoppingCartPay", request.getShoppingCartPay());
        if (request.getVoucherMoney() != null && request.getVoucherMoney().compareTo(BigDecimal.ZERO) > 0) {
            param.put("useCouponAmount", request.getVoucherMoney());
        }
        if (request.getOrderType() != null) {
            param.put("orderType", request.getOrderType());
        }
        return param;
    }

    @Override
    public boolean cancelArtOrder(Map<String, Object> map) {
        return artOrderProxy.artCancel(map);
    }

    @Override
    public OrderDto queryArtOrderDetailById(Integer id) {
        return artOrderProxy.queryArtOrderDetailById(id);
    }

    /**
     * 获取切图:大图 90% 中部截取 小图:50% 中部截取
     *
     * @param headImg
     * @param width
     * @return
     */
    private ImageEntity getImgEntity(String headImg, Integer width) {
        ImageEntity entity = new ImageEntity();
        if (!StringUtils.isEmpty(headImg)) {
            if (width == null) {
                entity.setSmallImage(headImg);
                entity.setBigImage(headImg);
            } else {
                Integer smallWidth = width * ImageSize.WIDTH_PER_SIZE_50 / ImageSize.WIDTH_PER_SIZE_100;
                Integer bigWidth = width * ImageSize.WIDTH_PER_SIZE_90 / ImageSize.WIDTH_PER_SIZE_100;
                String smallImage = QiniuImageUtils.compressImageAndDiffPic(headImg, smallWidth, -1);
                String bigImage = QiniuImageUtils.compressImageAndSamePicTwo(headImg, bigWidth, -1);
                entity.setBigImage(bigImage);// 90% 中部截取
                entity.setSmallImage(smallImage);// 50% 中部截取
            }
        }
        return entity;
    }

    @Override
    public List<CategoryArtListResponse> getCategoryArtListForHome() {
        FrameCategoryResponseVo categoryResponseVo = artCategoryProxy.queryAllFrameList();
        if (categoryResponseVo == null) {
            return null;
        } else {
            List<FrameCategoryVo> frameList = categoryResponseVo.getFrameList();
            if (CollectionUtils.isNotEmpty(frameList)) {
                List<CategoryArtListResponse> artListResponses = new ArrayList<CategoryArtListResponse>();
                List<Integer> frameIdList = this.getCategoryIdList(frameList);
                //查询艺术品品类
                FrameArtResponseVo frameArtResponseVo = artCategoryProxy.queryListByFrameIdList(frameIdList);
                for (FrameCategoryVo frameCategoryVo : frameList) {
                    CategoryArtListResponse response = new CategoryArtListResponse();
                    response.setFrameType(frameCategoryVo.getCategoryNum());

                    if (frameArtResponseVo != null) {
                        List<FrameArtVo> categoryList = this.getCategoryList(frameArtResponseVo.getCategoryList(), frameCategoryVo.getId());
                        if (CollectionUtils.isNotEmpty(categoryList)) {
                            List<CategoryResponse> categoryArtList = new ArrayList<CategoryResponse>();
                            for (FrameArtVo frameArtVo : categoryList) {
                                CategoryResponse artResponse = new CategoryResponse();
                                artResponse.setCategoryType(frameArtVo.getCategoryCode());
                                artResponse.setCategoryTitle(frameArtVo.getCategoryName());
                                artResponse.setJumpTitle(frameArtVo.getArtName());
                                artResponse.setCategorySubTitle(frameArtVo.getCategoryTitle());
                                artResponse.setCategoryImgUrl(frameArtVo.getArtImg());
                                categoryArtList.add(artResponse);
                            }
                            response.setCategoryList(categoryArtList);
                        }
                    }
                    artListResponses.add(response);
                }
                return artListResponses;
            } else {
                return null;
            }
        }
    }

    /**
     * 艺术品品类 id
     *
     * @param frameList
     * @return
     */
    private List<Integer> getCategoryIdList(List<FrameCategoryVo> frameList) {
        List<Integer> list = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(frameList)) {
            for (FrameCategoryVo frameCategoryVo : frameList) {
                if (frameCategoryVo.getId() != null) {
                    list.add(frameCategoryVo.getId());
                }
            }
        }
        return list;
    }

    /**
     * 艺术品品类
     *
     * @param frameList
     * @return
     */
    private List<FrameArtVo> getCategoryList(List<FrameArtVo> frameList, Integer id) {
        List<FrameArtVo> list = new ArrayList<FrameArtVo>();
        if (CollectionUtils.isNotEmpty(frameList)) {
            for (FrameArtVo frameCategoryVo : frameList) {
                if (id.equals(frameCategoryVo.getFrameId())) {
                    list.add(frameCategoryVo);
                }
            }
        }
        return list;
    }

    @Override
    public ArtSubjectPageResponse getArtSubjectList(ArtSubjectPageRequest request) {
        if (request == null) {
            return null;
        }
        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth();
        }
        Integer pageNo = 1;
        Integer pageSize = 10;
        if (request.getPageNo() != null) {
            pageNo = request.getPageNo();
        }
        if (request.getPageSize() != null) {
            pageSize = request.getPageSize();
        }
        ArtSubjectPageResponse pageResponse = new ArtSubjectPageResponse();
        ArtSubjectListResponseVo artSubjectListResponseVo = artCategoryProxy.getArtSubjectList(pageNo, pageSize);
        if (artSubjectListResponseVo != null) {
            List<SubjectInfoVo> list = artSubjectListResponseVo.getList();

            if (CollectionUtils.isNotEmpty(list)) {
                List<ArtSubjectResponse> artSubjectList = new ArrayList<ArtSubjectResponse>();
                for (SubjectInfoVo subjectInfoVo : list) {
                    ArtSubjectResponse subjectResponse = new ArtSubjectResponse();
                    if (StringUtils.isNotBlank(subjectInfoVo.getBannerUrl())) {
                        //切图处理  中部截取 100%
                        subjectResponse.setBannerUrl(QiniuImageUtils.compressImageAndDiffPic(subjectInfoVo.getBannerUrl(), width, -1));
                    } else {
                        subjectResponse.setBannerUrl("");
                    }
                    subjectResponse.setSubjectId(subjectInfoVo.getId());
                    subjectResponse.setSubjectSubTitle(subjectInfoVo.getSubTitle());
                    subjectResponse.setSubjectTitle(subjectInfoVo.getTitle());
                    subjectResponse.setSkipUrl(apiConfig.getSubjectSkipUrl() + subjectInfoVo.getId());
                    artSubjectList.add(subjectResponse);
                }
                pageResponse.setArtSubjectList(artSubjectList);
            } else {
                pageResponse.setArtSubjectList(null);
            }
            pageResponse.setTotalCount(artSubjectListResponseVo.getTotalCount());
            pageResponse.setTotalPage(artSubjectListResponseVo.getTotalPage());
        }
        pageResponse.setPageNo(pageNo);
        pageResponse.setPageSize(pageSize);
        return pageResponse;
    }

    @Override
    public HttpArtListResponse getArtListByCondition(HttpArtworkListWithFilterRequest request) {
        setRequestParamsForsetAjbMoney(request);
        HttpArtListResponse result = new HttpArtListResponse();
        Map<String, Object> params = new HashMap<String, Object>();
        //是否点击用艾积分兑换
        params.put("freeEx", request.getFreeEx());
        params.put("priceSort", request.getPriceSort());
        params.put("typeIds", CollectionUtils.isEmpty(request.getTypeIds()) ? null : request.getTypeIds());//
        params.put("roomIds", CollectionUtils.isEmpty(request.getRoomIds()) ? null : request.getRoomIds());//
        params.put("pageSize", request.getPageSize());
        params.put("pageNo", request.getPageNo());
        params.put("ajbMoney", request.getAjbMoney());
        params.put("recommend", request.getRecommend());
        params.put("priceIds", CollectionUtils.isEmpty(request.getPriceIds()) ? null : request.getPriceIds());//
        List<Map<String, Object>> priceList = buildProductPriceList(request.getPriceIds());
        params.put("priceListFilter", CollectionUtils.isEmpty(priceList) ? null : priceList);//
        //品类标签ID集合
        List<Integer> categoryIds = new ArrayList<Integer>();
        if (request.getCategoryType() != null) {
            String keyDesc = request.getCategoryType().toString();
            DicListDto dicListResponseVo = dicProxy.getDicListByKey(keyDesc);
            if (dicListResponseVo != null && CollectionUtils.isNotEmpty(dicListResponseVo.getDicList())) {
                List<DicDto> dicList = dicListResponseVo.getDicList();
                for (DicDto dicVo : dicList) {
                    categoryIds.add(Integer.parseInt(dicVo.getKeyDesc()));
                }
                params.put("categoryIds", categoryIds);
            }
        }

        ArtListResponseVo artListResponseVo = artInfoProxy.queryArtListByFilters(params);
        if (artListResponseVo != null) {
            result.setTotalPage(artListResponseVo.getTotalPages());
            List<ArtInfoVo> list = artListResponseVo.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Artwork> artworksByFilters = new ArrayList<Artwork>();
                for (ArtInfoVo artInfoVo : list) {
                    Artwork artwork = new Artwork();
                    if (artInfoVo.getIdtArtProduct() != null) {
                        artwork.setArtWorkId(artInfoVo.getIdtArtProduct());
                    }
                    artwork.setName(artInfoVo.getName());
                    artwork.setHeadImg(artInfoVo.getImageUrl());
                    artwork.setCategory(artInfoVo.getCategory());
                    if (StringUtils.isNotBlank(artInfoVo.getProductYear())) {
                        artwork.setCreationTime(artInfoVo.getProductYear());
                    } else {
                        artwork.setCreationTime(CREATION_TIME);
                    }
                    artwork.setPrice(artInfoVo.getProductPrice());
                    artwork.setSize(artInfoVo.getProductSize());
                    if (artInfoVo.getuId() != null) {
                        artwork.setArtistId(artInfoVo.getuId());
                    }
                    artwork.setArtistName(artInfoVo.getNickName());
                    if (artInfoVo.getArtType() != null) {
                        artwork.setArtistType(artInfoVo.getArtType());
                    }
                    artworksByFilters.add(artwork);
                }
                //增加切图参数
                buildImageSize(artworksByFilters, request.getOsType(), request.getWidth());
                /*
                 * add by jerfan cang 加活动商品过滤
                 */
                artworksByFilters = ArtListFilterUtils.filterActivityProduct(artworksByFilters);
                result.setArtworkList(artworksByFilters);
            } else {
                result.setArtworkList(new ArrayList<Artwork>());
            }
        } else {
            result.setTotalPage(1);
            result.setArtworkList(new ArrayList<Artwork>());
        }
        return result;
    }


    private void setRequestParamsForsetAjbMoney(HttpArtworkListWithFilterRequest request) {
        //当用户选定艾积分抵扣时，先判断判断用户是否登录
        if (null != request && request.getFreeEx() == 1) {
            if (null == request.getAccessToken()) {
                throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
            }
            Integer userId = 0;
            Integer amount = 0;
            HttpUserInfoRequest user = request.getUserInfo();
            if (user == null || user.getId() == null) {
                throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
            }
            userId = user.getId();
            //查询用户艾积分信息（有效期）
            UserAjbRecordDto ajbRecordResponseVo = ajbRechargeProxy.queryAjbDetailInfoByUserId(userId, 1, 1);
            if (ajbRecordResponseVo != null && ajbRecordResponseVo.getDisplayUsableAmount() != null) {
                //有效期内可用艾积分数量
                amount = ajbRecordResponseVo.getDisplayUsableAmount();
                if (ajbRecordResponseVo.getExRate() != null) {
                    request.setAjbMoney(new BigDecimal(amount).divide(new BigDecimal(ajbRecordResponseVo.getExRate()), 2, BigDecimal.ROUND_HALF_UP));
                } else {
                    request.setAjbMoney(new BigDecimal(amount).divide(new BigDecimal(ExRate), 2, BigDecimal.ROUND_HALF_UP));
                }
            } else {
                request.setAjbMoney(new BigDecimal(amount).divide(new BigDecimal(ExRate), 2, BigDecimal.ROUND_HALF_UP));
            }
        }
    }

    @Override
    public ArtSubjectDetailResponse querySubjectDetailById(SubjectDetailRequest request) {
        Integer subjectId = request.getSubjectId();
        if (subjectId == null) {
            return null;
        }
        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth();
        }
        ArtSubjectDetailResponse response = new ArtSubjectDetailResponse();
        SubjectProductResponseVo productResponseVo = artCategoryProxy.querySubjectDetailById(subjectId);
        if (productResponseVo != null) {
            if (StringUtils.isNotBlank(productResponseVo.getBannerUrl())) {
                //切图处理  中部截取 100%
                response.setBannerUrl(QiniuImageUtils.compressImageAndDiffPic(productResponseVo.getBannerUrl(), width, -1));
            } else {
                response.setBannerUrl("");
            }
            if (StringUtils.isNotBlank(productResponseVo.getHeadImgUrl())) {
                //切图处理  中部截取 100%
                response.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(productResponseVo.getHeadImgUrl(), width, -1));
            } else {
                response.setHeadImgUrl("");
            }
            if (StringUtils.isNotBlank(productResponseVo.getSubjectImgUrl())) {
                //切图处理  中部截取 100%
                response.setSubjectImgUrl(QiniuImageUtils.compressImageAndDiffPic(productResponseVo.getSubjectImgUrl(), width, -1));
            } else {
                response.setSubjectImgUrl("");
            }
            response.setId(productResponseVo.getId());
            response.setSubTitle(productResponseVo.getSubTitle());
            response.setTitle(productResponseVo.getTitle());
            response.setSubjectDesc(productResponseVo.getSubjectDesc());
            response.setCreateTime(productResponseVo.getCreateTime());
            List<SubjectProductVo> subjectProductList = productResponseVo.getProductList();
            if (CollectionUtils.isNotEmpty(subjectProductList)) {
                List<SubjectProductResponse> productList = new ArrayList<SubjectProductResponse>();
                for (SubjectProductVo subjectProductVo : subjectProductList) {
                    SubjectProductResponse productResponse = new SubjectProductResponse();
                    productResponse.setBrandArtist(subjectProductVo.getBrandArtist());
                    if (StringUtils.isNotBlank(subjectProductVo.getImgUrl())) {
                        //切图处理  中部截取 100%
                        productResponse.setImgUrl(QiniuImageUtils.compressImageAndDiffPic(subjectProductVo.getImgUrl(), width, -1));
                    } else {
                        productResponse.setImgUrl("");
                    }
                    productResponse.setProductDesc(subjectProductVo.getProductDesc());
                    productResponse.setProductId(subjectProductVo.getProductId());
                    productResponse.setProductName(subjectProductVo.getProductName());
                    productResponse.setProductPrice(subjectProductVo.getProductPrice());
                    productResponse.setTitle(subjectProductVo.getTitle());
                    productList.add(productResponse);
                }
                response.setProductList(productList);
            }
        }
        return response;
    }


    @Override
    public Artwork getArtworkByIdAndType(Integer artworkId, Integer artworkType) {
        return artDao.getArtworkByIdAndType(artworkId.longValue(), artworkType);
    }

    @Override
    public HttpSubmitOrderResponse createStarOrder(StarOrderCreateRequest request) {
        String accessToken = request.getAccessToken();
        /**
         * 验证用户是否登陆 <br/>
         * 未登陆,直接返回失败 <br/>
         */
        if (!userProxy.checkLegalUser(accessToken)) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        List<StarOrderDetailCreateRequest> productList = request.getProductList();
        // 计算出来的商品总价格
        BigDecimal totalPrice = new BigDecimal("0").setScale(2);
        for (StarOrderDetailCreateRequest orderProduct : productList) {
            Integer productId = orderProduct.getProductId();
            /**
             * 验证商品ID是否存在 <br/>
             * 不存在,直接返回失败 <br/>
             */
            if (productId == null) {
                throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
            }
            List<Integer> skuIds = new ArrayList<Integer>();
            skuIds.add(productId);
            List<AppArtStarSku> artStarSkuList = starProductProxy.getAllStarSkuListByIds(skuIds);

            if (CollectionUtils.isNotEmpty(artStarSkuList)) {
                AppArtStarSku artStarSku = artStarSkuList.get(0);
                if (null != artStarSku && artStarSku.getPrice() != null && orderProduct.getProductCount() != null) {
                    orderProduct.setProductPrice(artStarSku.getPrice());
                    BigDecimal bdprice = new BigDecimal(String.valueOf(artStarSku.getPrice())).multiply(new BigDecimal(
                            String.valueOf(orderProduct.getProductCount())));
                    LOG.info("StarOrderController createStarOrder artName:" + artStarSku.getProductName() + ",artPrice:"
                            + artStarSku.getPrice() + ",artCount:" + orderProduct.getProductCount());
                    totalPrice = totalPrice.add(bdprice);
                }
            }
        }

        BigDecimal softContractgMoney = request.getSoftContractgMoney();// 校验前端传过来的商品总价格
        /**
         * 验证商品总价格对不对 <br/>
         * 不对,直接返回失败 <br/>
         */
        if (totalPrice.equals(BigDecimal.ZERO) || totalPrice.compareTo(softContractgMoney) != 0) {
            throw new BusinessException(HttpResponseCode.MONEY_ERROR, MessageConstant.MONEY_ERROR);
        }

        JSONObject param = this.getJSONObjectByStarOrderCreateRequest(request);
        Integer addressId = param.getInteger("addressId");
        if (addressId == null || addressId.intValue() == 0) {
            throw new BusinessException(ArtStatusConstant.ADDRESS_EMPTY.getErrorCode(), ArtStatusConstant.ADDRESS_EMPTY.getErrorMsg());
        }
        ResponseVo<?> result = artOrderProxy.createArtOrder(param);
        JSONObject obj = (JSONObject) result.getData();
        HttpSubmitOrderResponse response = null;
        if (obj == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }

        if (null != result.getCode()) {
            String errorMsg = ArtStatusConstant.getErrorMsg(result.getCode());
            if (StringUtil.isNullOrEmpty(errorMsg)) {
                errorMsg = MessageConstant.FAILED;
            }
            throw new BusinessException(HttpResponseCode.FAILED, errorMsg);
        }

        Integer orderId = (Integer) obj.get("orderId");
        LOG.info("orderId :" + orderId);
        response = new HttpSubmitOrderResponse();
        response.setOrderId((long) orderId);
        return response;
    }

    @Override
    public void deleteOrder(QueryCollageOrderDetailRequest request) {
        HttpUserInfoRequest userVo = request.getUserInfo();
        if (userVo == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        //支持批量删除，如果orderId不为空那么就将orderId添加到orderIds集合里
        if (request.getOrderIds() == null) {
            request.setOrderIds(Lists.newArrayList());
        }
        if (request.getOrderId() != null) {
            request.getOrderIds().add(request.getOrderId());
        }
        artOrderProxy.deleteOrder(request);
    }

    private JSONObject getJSONObjectByStarOrderCreateRequest(StarOrderCreateRequest request) {
        JSONObject param = new JSONObject();
        HttpUserInfoRequest user = request.getUserInfo();
        Integer userId = user.getId();
        param.put("userId", userId);
        param.put("source", ArtOrderSourceConstant.getMsg(request.getOsType()));
        param.put("remark", request.getRemark());
        param.put("orderChannel", 2);
        param.put("orderType", 15);

        List<UserAddressResultDto> userAddressList = addressProxy.queryUserAddressList(userId);
        Integer addressId = null;
        if (CollectionUtils.isNotEmpty(userAddressList)) {
            for (UserAddressResultDto userAddress : userAddressList) {
                // 区域id 设置默认地址
                if (userAddress.getIsDefault()) {
                    addressId = userAddress.getId();
                    break;
                }
            }
        }
        if (addressId != null) {
            param.put("addressId", addressId);
        }

        List<StarOrderDetailCreateRequest> productList = request.getProductList();
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (StarOrderDetailCreateRequest product : productList) {
            JSONObject detail = new JSONObject();
            detail.put("goodsId", product.getProductId());
            detail.put("amount", product.getProductCount());
            detail.put("artId", product.getArtId());
            detail.put("artName", product.getArtName());
            String markWord = product.getMarkWord();
            detail.put("markWord", markWord);
            String remark = product.getRemark();
            if (null != markWord) {
                remark = remark + markWord;
            }
            detail.put("remark", remark);
            detail.put("selectAttr", product.getSelectAttr());
            list.add(detail);
        }
        param.put("detail", list);
        param.put("shoppingCartPay", request.getShoppingCartPay());
        return param;
    }

    @Autowired
    FilterProductConfig filterProductConfig;

    @Override
    public HttpArtworkOrderResponse getArtworkOrderInfo290(HttpArtworkDetailRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        if (!userProxy.checkLegalUser(request.getAccessToken())) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Long artworkId = request.getArtworkId();
        HttpArtworkOrderResponse result = null;
        Artwork artworkOrderInfo = artDao.getArtworkOrderInfo(artworkId);

        com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo queryProductStock = artStockProxy.queryInventory(Integer.parseInt(artworkId.toString()));
        if (null != artworkOrderInfo) {
            int artworkStock = 0;
            if (null != queryProductStock) {
                artworkStock = queryProductStock.getEnableInventory();
            }
            result = new HttpArtworkOrderResponse();
            result.setArtworkId(artworkOrderInfo.getArtWorkId());
            result.setHeadImg(QiniuImageUtils.compressImage_40(artworkOrderInfo.getHeadImg()));
            result.setName(artworkOrderInfo.getName());
            LOG.info("ArtServiceImpl==========artworkOrderInfo.getName():" + artworkOrderInfo.getName());
            result.setPrice(artworkOrderInfo.getPrice());
            result.setStock(artworkStock);
            LOG.info("ArtServiceImpl==========result.getName():" + result.getName());
        }
        if (null == result) {
            throw new BusinessException(MessageConstant.ITEM_NOT_SUPPORT);
        }

        Long stock = result.getStock();
        //查询用户艾积分信息（有效期）
        UserAjbRecordDto ajbRecordResponseVo = ajbRechargeProxy.queryAjbDetailInfoByUserId(userDto.getId(), 1, 1);
        if (ajbRecordResponseVo != null && ajbRecordResponseVo.getDisplayUsableAmount() != null) {
            //有效期内可用艾积分数量
            Integer amount = ajbRecordResponseVo.getDisplayUsableAmount();
            result.setAjbAccount(amount);
            BigDecimal ajbMoney = new BigDecimal(0);
            if (ajbRecordResponseVo.getExRate() != null) {
                result.setExRate(ajbRecordResponseVo.getExRate());
                ajbMoney = new BigDecimal(amount).divide(new BigDecimal(ajbRecordResponseVo.getExRate()), 2, BigDecimal.ROUND_HALF_UP);
            } else {
                result.setExRate(ExRate);
                ajbMoney = new BigDecimal(amount).divide(new BigDecimal(ExRate), 2, BigDecimal.ROUND_HALF_UP);
            }
            result.setAjbMoney(ajbMoney);
        } else {
            result.setAjbAccount(0);
            result.setAjbMoney(new BigDecimal(0));
            result.setExRate(ExRate);
        }

        if (stock <= 0) {
            throw new BusinessException(HttpResponseCode.STOCK_IS_ZERO, MessageConstant.STOCK_ZERO);
        }

        return result;
    }

    @Override
    public HttpSubmitOrderResponse createArtworkOrder290(HttpCreateArtworkOrderRequest request) {
        String accessToken = request.getAccessToken();
        /**
         * 验证用户是否登陆 <br/>
         * 未登陆,直接返回失败 <br/>
         */
        if (!userProxy.checkLegalUser(accessToken)) {
            LOG.info("createArtworkOrder298:returnNotLogin");
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        // 获取商品列表信息
        List<HttpOrderProductRequest> productList = request.getProductList();
        // 计算出来的商品总价格
        BigDecimal totalPrice = new BigDecimal("0").setScale(2);
        for (HttpOrderProductRequest orderProduct : productList) {
            Integer productId = orderProduct.getProductId();
            String skuId = orderProduct.getSkuId();
            /**
             * 验证商品ID是否存在 <br/>
             * 不存在,直接返回失败 <br/>
             */
            if (productId == null && StringUtils.isBlank(skuId)) {
                LOG.info("createArtworkOrder298:returnProductEmpty");
                throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
            }
            Artwork artworkOrder = null;
            if (productId != null) {
                 artworkOrder = this.getArtworkByProductId(productId);
            }
            if(StringUtils.isNotBlank(skuId)){
                List<Artwork>  artList = artDao.getArtworkOmsOrderInfo(new ArtworkOrderRequest(Arrays.asList(skuId)));
                if(CollectionUtils.isNotEmpty(artList)){
                    artworkOrder = artList.get(0);
                    if(artworkOrder == null || artworkOrder.getProductId() == null){
                        throw new BusinessException(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_NOT_EXISTS);
                    }
                }
            }
            if (null != artworkOrder && artworkOrder.getPrice() != null && orderProduct.getProductCount() != null) {
                orderProduct.setProductPrice(artworkOrder.getPrice());
                BigDecimal bdprice = new BigDecimal(String.valueOf(artworkOrder.getPrice())).multiply(new BigDecimal(
                        String.valueOf(orderProduct.getProductCount())));
                totalPrice = totalPrice.add(bdprice);
            }
        }
        BigDecimal softContractgMoney = request.getSoftContractgMoney();// 校验前端传过来的商品总价格
        /**
         * 验证商品总价格对不对 <br/>
         * 不对,直接返回失败 <br/>
         */
        if (totalPrice.equals(BigDecimal.ZERO) || totalPrice.compareTo(softContractgMoney) != 0) {
            LOG.info("createArtworkOrder298:returnMoneyError");
            throw new BusinessException(HttpResponseCode.MONEY_ERROR, MessageConstant.MONEY_ERROR);
        }

        ResponseVo<?> result = null;
        //将前端传过来的券金额清空
        request.setVoucherMoney(null);

        /**
         * 夏铭宇2018-11-27新增
         * 如果传了券，校验券是否可用
         */
        if (StringUtils.isNotBlank(request.getVoucherCode())) {
            JSONObject params = new JSONObject();
            params.put("voucherCode", request.getVoucherCode());
            CommonResponseVo<?> commonResponseVo = boeActivityProxy.validVoucher(params);
            if (commonResponseVo != null && commonResponseVo.getCode() != 1) {
                //券不可用
                throw new BusinessException(commonResponseVo.getCode(), commonResponseVo.getMsg());
            } else if (commonResponseVo == null) {
                throw new BusinessException("券查询失败");
            } else if (commonResponseVo.getObj() != null) {
                //查询到有可用券
                try {
                    VoucherDto voucherDto = JsonUtils.json2obj(JsonUtils.obj2json(commonResponseVo.getObj()), VoucherDto.class);
                    request.setVoucherMoney(voucherDto.getVoucherMoney());
                } catch (Exception e) {
                    LOG.error("voucher use o2o-exception , more info : {}" , e.toString());
                }
            }
        }

        /**
         * 调度公共服务接口去创建艺术品订单
         */
        result = this.createArtOrder(request);
        if (result == null || result.getData() == null) {
            throw new BusinessException(MessageConstant.FAILED);
        }
        JSONObject obj = (JSONObject) result.getData();
        HttpSubmitOrderResponse response = null;
        if (obj == null) {
            LOG.info("createArtworkOrder298:result is null");
            throw new BusinessException(MessageConstant.FAILED);
        }
        Integer orderId = (Integer) obj.get("orderId");
        response = new HttpSubmitOrderResponse();
        response.setOrderId((long) orderId);
        LOG.info("createArtworkOrder298 end, resultCode:" + result.getCode());

        //夏铭宇2018-11-27新增
        if (result.getCode() == 1 && StringUtils.isNotBlank(request.getVoucherCode())) {
            //下单成功，若有用券，则券记录订单号
            JSONObject params = new JSONObject();
            params.put("voucherCode", request.getVoucherCode());
            params.put("orderId", response.getOrderId());
            CommonResponseVo<?> commonResponseVo = boeActivityProxy.updateOrderId(params);
            if (commonResponseVo != null && commonResponseVo.getCode() != 1) {
                throw new BusinessException(commonResponseVo.getCode(), commonResponseVo.getMsg());
            }
        }

        String errorMsg = ArtStatusConstant.getErrorMsg(result.getCode());
        if (null != errorMsg) {
            throw new BusinessException(errorMsg);
        }

        return response;
    }

}
