package com.ihomefnt.o2o.service.service.home;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.DateUtils;
import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.art.dto.Content;
import com.ihomefnt.o2o.intf.domain.artist.dto.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.dna.dto.DnaBrowseRecordDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProductProgramEntity;
import com.ihomefnt.o2o.intf.domain.homecard.dto.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.request.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainUserInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardListResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardSpaceResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardStandardSpaceResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HardStandardItem;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.*;
import com.ihomefnt.o2o.intf.proxy.artist.ArtistProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.dna.DecorationQuotationProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.HardStandardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.maintain.MaintainService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * APP3.0新版首页Service层实现
 *
 * @author ZHAO
 */
@Service
@SuppressWarnings("all")
public class HomeCardServiceImpl implements HomeCardService {

    @Resource
    private ServiceCaller serviceCaller;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    UserDao userDao;

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    private ProductProgramService productProgramService;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private HardStandardWcmProxy hardStandardWcmProxy;

    @Autowired
    private DecorationQuotationProxy decorationQuotationProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private UserService userService;

    @Autowired
    private MaintainService maintainService;
    @Autowired
    ArtistProxy artistProxy;
    @Autowired
    HouseService houseService;

    @NacosValue(value = "${lay.out.web.url}", autoRefreshed = true)
    private String layOutWebUrl;

    @NacosValue(value = "${lay.out.switch}", autoRefreshed = true)
    private boolean layOutSwitch;

    @NacosValue(value = "${spacemark.update.version}", autoRefreshed = true)
    private String SPACEMARK_UPDATE_VERSION;

    //临时方案: 当首页方案数量不满4条时 补齐
    private static List<String> TEMP_PUSH_ICON = new ArrayList() {
        {
            add(StaticResourceConstants.HOMECARD_TEMP_ICON1);
            add(StaticResourceConstants.HOMECARD_TEMP_ICON2);
            add(StaticResourceConstants.HOMECARD_TEMP_ICON3);
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(HomeCardServiceImpl.class);

    @Override
    public List<RecommendBoardResponse> getRecommendBoard(HttpBaseRequest request, HttpUserInfoRequest userDto) {
        LOG.info("HomeCardServiceImpl getRecommendBoard() interface start");
        Integer userId = 0;
        if (userDto != null) {
            userId = userDto.getId();
        }
        List<RecommendBoardResponse> responseList = new ArrayList<RecommendBoardResponse>();
        Integer width = request.getWidth();
        String watermark_type = HomeCardPraise.WATERMARK_DNA;
        //图片宽度90%
        if (width != null) {
            if (width.compareTo(HomeCardPraise.WIDTH_BIG) > 0) {
                watermark_type = HomeCardPraise.WATERMARK_DNA_BIG;
            }
            width = width * ImageSize.WIDTH_PER_SIZE_90
                    / ImageSize.WIDTH_PER_SIZE_100;
        }

        //20171204  版本控制 appVersion>3.2.0的版本才有type=7的卡片
        boolean appVersionFlag = false;
        String appVersion = "";
        if (null == request || null == request.getOsType() || request.getOsType() == 3) {
            appVersion = "3.2.0";
        } else {
            if (request.getOsType() != 3) {
                appVersion = request.getAppVersion();
            }
        }

        if (VersionUtil.mustUpdate("3.2.0", appVersion)) {
            appVersionFlag = true;
        }

        //只有3.2.3以上版本,支持报修,才有type 8
        if (VersionUtil.mustUpdate("3.2.3", appVersion) && userId > 0) {
            MaintainUserInfoResponseVo maintainUserInfo = maintainService.getMaintainUserInfo(request);
            if (maintainUserInfo != null && CollectionUtils.isNotEmpty(maintainUserInfo.getMaintainList())) {
                maintainUserInfo.setLinkDesc(HomeCardPraise.LINK_DESC);
                RecommendBoardResponse recommendBoardResponse = new RecommendBoardResponse();
                recommendBoardResponse.setMaintainUserInfo(maintainUserInfo);
                recommendBoardResponse.setType(HomeCardPraise.CARD_MAINTAIN);

                responseList.add(recommendBoardResponse);
            }
        }

        //用户是否登录
        if (StringUtils.isNotBlank(request.getAccessToken()) && userId > 0) {
            //硬装标准数量
            Integer hardStandardCount = 0;
            DicListDto hardStandardList = dicProxy.getDicListByKey(HomeCardPraise.HARD_STANDARD);
            if (hardStandardList != null && CollectionUtils.isNotEmpty(hardStandardList.getDicList())) {
                hardStandardCount = hardStandardList.getDicList().size();
            }

            //根据用户房产列表信息，判断该套房产是否有权限可展示特定产品方案卡片
            List<HouseInfoResponseVo> houseInfoList = houseService.queryUserHouseList(userId);
            for (HouseInfoResponseVo houseInfoResponseVo : houseInfoList) {
                if (houseInfoResponseVo.getIsAvail() != null && (ProductProgramPraise.PROGRAM_LIMIT_LOOK.equals(houseInfoResponseVo.getIsAvail()) || ProductProgramPraise.PROGRAM_LIMIT_SELECT.equals(houseInfoResponseVo.getIsAvail()))) {
                    RecommendBoardResponse programCard = new RecommendBoardResponse();
                    SolutionInfoResponseVo userProgramResponse = homeCardBossProxy.getUserSpecificProgram(userId, houseInfoResponseVo.getHouseProjectId(), houseInfoResponseVo.getHouseTypeId(), HomeCardPraise.SPECIFIC_PROGRAM_NUM);
                    if (userProgramResponse != null) {
                        programCard.setType(HomeCardPraise.CARD_PROGRAM_CODE);
                        programCard.setCardId(0);
                        //前4套产品首图
                        List<SolutionIdAndHeadImgVo> userProgramList = userProgramResponse.getSolutionIdAndHeadImgList();
                        if (CollectionUtils.isNotEmpty(userProgramList)) {
                            List<ProductProgramEntity> programLists = new ArrayList<ProductProgramEntity>();
                            for (SolutionIdAndHeadImgVo solutionProjectInfoVo : userProgramList) {
                                ProductProgramEntity productProgramEntity = new ProductProgramEntity();
                                productProgramEntity.setProgramId(solutionProjectInfoVo.getSolutionId());
                                //图片宽度90%  中部截取
                                if (StringUtils.isNotBlank(solutionProjectInfoVo.getSolutionHeadImgURL())) {
                                    if (width != null) {
                                        productProgramEntity.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(solutionProjectInfoVo.getSolutionHeadImgURL(), width, -1));
                                    } else {
                                        productProgramEntity.setHeadImgUrl(solutionProjectInfoVo.getSolutionHeadImgURL());
                                    }
                                } else {
                                    productProgramEntity.setHeadImgUrl("");
                                }
                                productProgramEntity.setName("");
                                programLists.add(productProgramEntity);
                            }
                            programCard.setProgramList(programLists);

                            fixSolutionIdAndHeadImgVo(programCard.getProgramList());
                        }

                        if (StringUtils.isNotBlank(userProgramResponse.getHouseProjectName())) {
                            programCard.setBuilding(userProgramResponse.getHouseProjectName().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));//楼盘名称去掉BBC
                        } else {
                            programCard.setBuilding("");
                        }
                        if (StringUtils.isNotBlank(userProgramResponse.getHouseTypeName())) {
                            programCard.setHouse(userProgramResponse.getHouseTypeName().replace(HomeCardPraise.HOUSE_TYPE, ""));//户型名称去掉户型文字
                        } else {
                            programCard.setHouse("");
                        }
                        programCard.setBuildingId(userProgramResponse.getHouseProjectId());//楼盘ID
                        programCard.setHouseId(houseInfoResponseVo.getId());//房产ID
                        programCard.setBuildingInfo(houseInfoResponseVo.getBuildingInfo());//房产信息
                        programCard.setHouseTypeId(userProgramResponse.getHouseTypeId());//户型ID
                        programCard.setHardNum(hardStandardCount);
                        programCard.setSoftNum(userProgramResponse.getSoftDecorationStandardCount());
                        programCard.setStyleNum(userProgramResponse.getStyleCount());
                        programCard.setPriceStart(userProgramResponse.getMinSolutionSalePrice());
                        programCard.setPriceEnd(userProgramResponse.getMaxSolutionSalePrice());
                        if (userProgramResponse.getHouseTypeArea() != null) {
                            programCard.setHouseArea(userProgramResponse.getHouseTypeArea() + HomeCardPraise.HOUSE_AREA);
                        } else {
                            programCard.setHouseArea("");
                        }
                        //卡片按钮名称
                        if (ProductProgramPraise.PROGRAM_LIMIT_LOOK.equals(houseInfoResponseVo.getIsAvail())) {
                            programCard.setButtonText(ProductProgramPraise.PROGRAM_BUTTON_LOOK);
                        } else {
                            programCard.setButtonText(ProductProgramPraise.PROGRAM_BUTTON_SELECT);
                        }
                        responseList.add(programCard);
                    }
                }
            }
        }

        //推荐页卡片配置数据
        //7DNA 8样板间套装 9banner 10艺术品  11视频 12定制贺卡
        CardListResponseVo cardListResponse = homeCardWcmProxy.getCardConfigList();
        if (cardListResponse != null) {
            List<CardResponseVo> cardList = cardListResponse.getList();
            if (CollectionUtils.isNotEmpty(cardList)) {
                //水印图片地址
                String waterImageUrl = "";
                DicDto dicVo = dicProxy.queryDicByKey(watermark_type);
                if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                    waterImageUrl = dicVo.getValueDesc();
                }

                List<Integer> dnaIdList = this.getDnaIdList(cardList);
                Map<Integer, Integer> dnaMap = this.getDnaMapByDnaIdList(dnaIdList);
                Map<Integer, Integer> commentMap = this.getCommentMapByDnaIdList(dnaIdList, HomeCardPraise.COMMENT_TYPE_DNA, userDto);
                for (CardResponseVo cardResponseVo : cardList) {
                    RecommendBoardResponse card = new RecommendBoardResponse();
                    switch (cardResponseVo.getTypeDesc()) {
                        case HomeCardPraise.DIC_KEY_TYPE_DNA:
                            card.setType(HomeCardPraise.CARD_DNA_CODE);
                            card.setStyle(cardResponseVo.getStyle());
                            card.setPraise(cardResponseVo.getPraise());
                            card.setIdea(cardResponseVo.getIdea());
                            card.setFavoriteNum(dnaMap.get(cardResponseVo.getFk()));
                            card.setCommentNum(commentMap.get(cardResponseVo.getFk()));
                            break;
                        case HomeCardPraise.DIC_KEY_TYPE_SUIT:
                            card.setType(HomeCardPraise.CARD_SUIT_CODE);
                            card.setStyle(cardResponseVo.getStyle());
                            break;
                        case HomeCardPraise.DIC_KEY_TYPE_BANNER:
                            card.setType(HomeCardPraise.CARD_BANNER_CODE);
                            card.setSkipUrl(cardResponseVo.getUrl());
                            break;
                        case HomeCardPraise.DIC_KEY_TYPE_ART:
                            //艺术品此版本不做
                            card.setType(HomeCardPraise.CARD_ART_CODE);
                            break;
                        case HomeCardPraise.DIC_KEY_TYPE_VIDEO:
                            card.setType(HomeCardPraise.CARD_VIDEO_CODE);
                            card.setVideoUrl(cardResponseVo.getUrl());
                            card.setIdea(cardResponseVo.getIdea());
                            break;
                        case HomeCardPraise.DIC_KEY_TYPE_GREETING_CARD:
                            card.setType(HomeCardPraise.CARD_GREETING_CODE);
                            card.setSkipUrl(cardResponseVo.getUrl());
                            break;
                        default:
                            break;

                    }

                    card.setCardId(cardResponseVo.getFk());
                    card.setName(cardResponseVo.getTitle());
                    //图片宽度90%  中部截取
                    //DNA图片添加水印
                    if (cardResponseVo.getType() != null && cardResponseVo.getType().equals(7)) {
                        String headImageUrl = "";
                        int headImgHeight = 0;
                        int headImgWidth = 0;
                        if (request.getWidth() != null) {
                            headImageUrl = QiniuImageUtils.compressImageAndDiffPic(cardResponseVo.getHeadImgUrl(), request.getWidth(), -1);
                            Map<String, Object> imageSize = QiniuImageUtils.getImageSizeByType(headImageUrl, "|imageInfo", serviceCaller);
                            if (imageSize.get("height") != null) {
                                headImgHeight = (int) imageSize.get("height");
                            }
                            if (imageSize.get("width") != null) {
                                headImgWidth = (int) imageSize.get("width");
                            }
                        } else {
                            headImageUrl = cardResponseVo.getHeadImgUrl();
                            Map<String, Object> imageSize = QiniuImageUtils.getImageSizeByType(headImageUrl, "?imageInfo", serviceCaller);
                            if (imageSize.get("height") != null) {
                                headImgHeight = (int) imageSize.get("height");
                            }
                            if (imageSize.get("width") != null) {
                                headImgWidth = (int) imageSize.get("width");
                            }
                        }
                        card.setHeadImgUrl(watermarkImage(headImageUrl, waterImageUrl, headImgWidth, headImgHeight));
                    } else {
                        if (StringUtils.isNotBlank(cardResponseVo.getHeadImgUrl())) {
                            if (width != null) {
                                card.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(cardResponseVo.getHeadImgUrl(), width, -1));
                            } else {
                                card.setHeadImgUrl(cardResponseVo.getHeadImgUrl());
                            }
                        } else {
                            card.setHeadImgUrl("");
                        }
                    }
                    if (card.getType().equals(HomeCardPraise.CARD_GREETING_CODE)) {
                        //版本>3.2.0有type=7的1219定制贺卡卡片
                        if (appVersionFlag) {
                            responseList.add(card);
                        }
                    } else if (card.getType().equals(HomeCardPraise.CARD_BANNER_CODE)) {
                        //banner  需判断版本号
                        if (VersionUtil.mustUpdate(cardResponseVo.getAppVersion(), appVersion)) {
                            responseList.add(card);
                        }
                    } else {
                        responseList.add(card);
                    }
                }
            }
        }

        LOG.info("HomeCardServiceImpl getRecommendBoard() interface end");
        return responseList;
    }

    /**
     * 临时方案: 特定产品方案数量小于4时 补全,大于4时 取四个
     *
     * @param list
     */
    public void fixSolutionIdAndHeadImgVo(List<ProductProgramEntity> list) {
        list = list.subList(0, list.size() > 4 ? 4 : list.size());

        int len = 4 - list.size();

        for (int i = 0; i < len; i++) {
            String icon = TEMP_PUSH_ICON.get(i);

            ProductProgramEntity productProgramEntity = new ProductProgramEntity();

            productProgramEntity.setHeadImgUrl(icon);

            //c端 不建议参数存在 null
            productProgramEntity.setProgramId(0);
            productProgramEntity.setName("");

            list.add(productProgramEntity);
        }
    }

    /**
     * 获取dna集合
     *
     * @param cardList
     * @return
     */
    private List<Integer> getDnaIdList(List<CardResponseVo> cardList) {
        List<Integer> list = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(cardList)) {
            for (CardResponseVo cardResponseVo : cardList) {
                Integer type = cardResponseVo.getType();// 7
                if (type != null && type.intValue() == 7) {
                    Integer dnaId = cardResponseVo.getFk();
                    list.add(dnaId);
                }
            }
        }
        return list;
    }

    /**
     * 通过产品列表 dolly-web 获取dna集合
     *
     * @param dnaBaseInfoList
     * @return
     */
    private List<Integer> getDnaIdListByDollyWeb(List<DNABaseInfoVo> dnaBaseInfoList) {
        List<Integer> list = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(dnaBaseInfoList)) {
            for (DNABaseInfoVo dnaBaseInfoResponseVo : dnaBaseInfoList) {
                Integer dnaId = dnaBaseInfoResponseVo.getId();
                list.add(dnaId);
            }
        }
        return list;
    }

    /**
     * 将评论dna集合封装成map
     *
     * @param dnaIdList
     * @return
     */
    public Map<Integer, Integer> getCommentMapByDnaIdList(List<Integer> dnaIdList, int type, HttpUserInfoRequest userDto) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        if (CollectionUtils.isNotEmpty(dnaIdList)) {
            DnaFavoriteCountListResponseVo page = homeCardWcmProxy.queryCommentCountListByDnaIdList(dnaIdList, type, this.getUserLevel(userDto), userDto == null ? null : userDto.getId());
            if (page != null) {
                List<DnaFavoriteCountResponseVo> list = page.getList();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (DnaFavoriteCountResponseVo vo : list) {
                        map.put(vo.getDnaId(), vo.getCount());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 将喜欢dna集合封装成map
     *
     * @param dnaIdList
     * @return
     */
    public Map<Integer, Integer> getDnaMapByDnaIdList(List<Integer> dnaIdList) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        if (CollectionUtils.isNotEmpty(dnaIdList)) {
            DnaFavoriteCountListResponseVo page = homeCardWcmProxy.queryFavoriteCountListByDnaIdList(dnaIdList);
            if (page != null) {
                List<DnaFavoriteCountResponseVo> list = page.getList();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (DnaFavoriteCountResponseVo vo : list) {
                        map.put(vo.getDnaId(), vo.getCount());
                    }
                }
            }
        }
        return map;
    }

    @Override
    public ProductBoardListResponse getProductBoard(ProductBoardRequest request) {
        LOG.info("HomeCardServiceImpl getProductBoard() interface start");
        List<ProductBoardResponse> responseList = new ArrayList<ProductBoardResponse>();
        List<ProductBoardResponse> otherList = new ArrayList<ProductBoardResponse>();

        String watermark_type = HomeCardPraise.WATERMARK_DNA;
        //图片宽度90%
        if (request.getWidth() != null && request.getWidth().compareTo(HomeCardPraise.WIDTH_BIG) > 0) {
            watermark_type = HomeCardPraise.WATERMARK_DNA_BIG;
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
        // 根据筛选条件查询产品列表（分页）
        DNABaseInfoResponseVo dnaBaseInfoListResponse = new DNABaseInfoResponseVo();
        if (request.getArtistTag() != null && request.getArtistTag().intValue() == HomeCardPraise.ARTIST_TAG) {
            if (userDto == null || userDto.getId() == null) {
                return null;
            }
            Integer userId = userDto.getId();
            dnaBaseInfoListResponse = homeCardBossProxy.getProductByCondition(userId, request.getStyle(), request.getStyleIdList(),
                    request.getRoom(), request.getPrice(), request.getCurPageNo(), request.getPageSize());
        } else {
            dnaBaseInfoListResponse = homeCardBossProxy.getProductByCondition(null, request.getStyle(), request.getStyleIdList(),
                    request.getRoom(), request.getPrice(), request.getCurPageNo(), request.getPageSize());

        }
        if (dnaBaseInfoListResponse != null) {
            // 水印图片地址
            String waterImageUrl = "";
            DicDto dicVo = dicProxy.queryDicByKey(watermark_type);
            if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                waterImageUrl = dicVo.getValueDesc();
            }

            List<DNABaseInfoVo> dnaBaseInfoList = dnaBaseInfoListResponse.getSearchResultList();
            if (CollectionUtils.isNotEmpty(dnaBaseInfoList)) {
                List<Integer> dnaIdList = this.getDnaIdListByDollyWeb(dnaBaseInfoList);
                // 点赞
                Map<Integer, Integer> dnaMap = this.getDnaMapByDnaIdList(dnaIdList);
                // 评论
                Map<Integer, Integer> commentMap = this.getCommentMapByDnaIdList(dnaIdList,
                        HomeCardPraise.COMMENT_TYPE_DNA, userDto);
                // 浏览
                Map<Integer, Integer> visitMap = this.getForwardMapByDnaIdList(dnaIdList, HomeCardPraise.VISIT_DNA);

                // 图片处理性能提升 add by zhangbin 2018.8.21
                // 1. 多线程批量处理图片属性查询逻辑
                Integer width = (request.getWidth() == null || request.getWidth() == 0) ? null : request.getWidth();
                Map<String, Picture> picturePropertyMap = this.queryPicturePropertyMap(dnaBaseInfoList,
                        width, -1);

//				Picture picture = null;
//				String pictureUrl ="";
                for (DNABaseInfoVo dnaBaseInfoResponseVo : dnaBaseInfoList) {
                    ProductBoardResponse product = new ProductBoardResponse();
                    product.setProductId(dnaBaseInfoResponseVo.getId());

//					// 获取图片URL
//					pictureUrl = dnaBaseInfoResponseVo.getHeadImgURL();
//					if (request.getWidth() != null) {
//
//						pictureUrl = AliImageUtil.imageCompress(dnaBaseInfoResponseVo.getHeadImgURL(),
//								ImageConstant.MODE_COMPRESS, request.getWidth(), ImageConstant.SIZE_MIDDLE);
////						pictureUrl = QiniuImageUtils.compressImageAndDiffPic(dnaBaseInfoResponseVo.getHeadImgURL(),
////								request.getWidth(), -1);
//
//					}
//					picture = picturePropertyMap.get(pictureUrl);
//
//					// DNA图片添加水印
//					if (picture != null) {
//						product.setHeadImgUrl(watermarkImage(pictureUrl, waterImageUrl, picture.getWidth(),
//								picture.getHeight()));
//					} else {
//						product.setHeadImgUrl("");
//					}
//
//                    if(StringUtils.isNotBlank(pictureUrl)){
//						pictureUrl = AliImageUtil.imageCompress(dnaBaseInfoResponseVo.getHeadImgURL(),
//								request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE);
//                        product.setHeadImgUrl(pictureUrl);
//                    }
                    product.setHeadImgUrl(AliImageUtil.imageCompress(dnaBaseInfoResponseVo.getHeadImgUrl(),
                            request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    product.setName(dnaBaseInfoResponseVo.getName());
                    product.setPraise(dnaBaseInfoResponseVo.getIdea());// 设计理念
                    product.setStyle(dnaBaseInfoResponseVo.getStyle());
                    // 喜欢的人数统计
                    product.setFavoriteNum(dnaMap.get(dnaBaseInfoResponseVo.getId()));
                    // 评论数
                    product.setCommentNum(commentMap.get(dnaBaseInfoResponseVo.getId()));
                    // 浏览数
                    product.setVisitNum(visitMap.get(dnaBaseInfoResponseVo.getId()));
                    //有无视频
                    product.setHasVideo(dnaBaseInfoResponseVo.isHasVideo());
                    //有无全景巡游
                    product.setSupport3D(dnaBaseInfoResponseVo.isSupport3D());
                    responseList.add(product);
                }
            }
        }

        ProductBoardListResponse productList = new ProductBoardListResponse();
        productList.setProductList(responseList);
        if (request.getCurPageNo() != null) {
            productList.setPageNo(request.getCurPageNo());
        }
        if (request.getPageSize() != null) {
            productList.setPageSize(request.getPageSize());
        }
        if (dnaBaseInfoListResponse != null) {
            if (dnaBaseInfoListResponse.getTotalCount() != null) {
                productList.setTotalCount(dnaBaseInfoListResponse.getTotalCount());
            }
            if (dnaBaseInfoListResponse.getTotalPageNum() != null) {
                productList.setTotalPage(dnaBaseInfoListResponse.getTotalPageNum());
            }
        }

        LOG.info("HomeCardServiceImpl getProductBoard() interface end");
        return productList;
    }

    /**
     * 图片属性查询
     *
     * @param dnaBaseInfoList
     * @param width
     * @param height
     * @return
     */
    public Map<String, Picture> queryPicturePropertyMap(List<DNABaseInfoVo> dnaBaseInfoList, Integer width, int height) {

        List<TaskAction<Picture>> taskActions = new ArrayList<>();
        for (DNABaseInfoVo vo : dnaBaseInfoList) {

            if (StringUtils.isBlank(vo.getHeadImgUrl())) {
                continue;
            }

            taskActions.add(new TaskAction<Picture>() {
                @Override
                public Picture doInAction() {

                    return getPictureProperty(vo.getHeadImgUrl(), width, height);
                }

                private Picture getPictureProperty(String headImgURL, Integer width, int height) {

                    // 图片宽度100% 中部截取
                    // DNA图片添加水印
                    String headImageUrl = "";
                    String type = "";
                    String pictureTrueUrl = "";
                    Picture picture = null;

//					if (width != null) {
//						headImageUrl = QiniuImageUtils.compressImageAndDiffPic(vo.getHeadImgURL(), width, -1);
//						type = "|imageInfo";
//					} else {
//						headImageUrl = vo.getHeadImgURL();
//						type = "?imageInfo";
//					}
//					if (width == null) {
//						width = 1;
//					}
                    headImageUrl = AliImageUtil.imageCompress(vo.getHeadImgUrl(), ImageConstant.MODE_COMPRESS,
                            width == null ? 750 : width,
                            ImageConstant.SIZE_MIDDLE);
                    picture = new Picture();
                    picture.setUrl(headImageUrl);
//					if (!StringUtil.isNullOrEmpty(headImageUrl)) {
//						pictureTrueUrl = headImageUrl + type;
//					}
                    // 先查询缓存缓存无则直接查询七牛反写换缓存
//					Picture pictureTemp = AppRedisUtil.getRedisImageSize(vo.getHeadImgURL(), serviceCaller);
//					if(null != pictureTemp){
//						picture = pictureTemp;
//						picture.setUrl(headImageUrl);
//					}
                    return picture;
                }
            });
        }

        Map<String, Picture> picturePropertyMap = new HashMap<>();

        for (Picture picture : TaskProcessManager.getTaskProcess().executeTask(taskActions)) {
            if (picture != null) {
                picturePropertyMap.put(picture.getUrl(), picture);
            }
        }
        return picturePropertyMap;
    }

    private Map<Integer, Integer> getForwardMapByDnaIdList(List<Integer> dnaIdList, Integer forwardDna) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        if (CollectionUtils.isNotEmpty(dnaIdList)) {
            DnaVisitListResponse page = homeCardWcmProxy.queryVisitCountListByDnaIds(dnaIdList, forwardDna);
            if (page != null) {
                List<DnaVisitResponse> list = page.getList();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (DnaVisitResponse vo : list) {
                        map.put(vo.getDnaId(), vo.getVisitNum());
                    }
                }
            }
        }
        return map;

    }

    @Override
    public VideoBoardResponse getVideoBoard(VideoBoardRequest request) {
        LOG.info("HomeCardServiceImpl getVideoBoard() interface start");
        VideoBoardResponse response = new VideoBoardResponse();
        Integer videoTypeId = 0;

        //置顶视频列表
        List<VideoEntity> bannerList = new ArrayList<VideoEntity>();
        //所有视频列表
        List<VideoEntity> videoList = new ArrayList<VideoEntity>();
        //视频类型
        List<VideoTypeResponse> typeList = new ArrayList<VideoTypeResponse>();

        if (request.getTypeId() > 0) {
            videoTypeId = request.getTypeId();
        } else {
            //查询置顶视频列表
            VideoListResponseVo videoConfigListResponseVo = homeCardWcmProxy.getVideoConfigList();
            if (videoConfigListResponseVo != null) {
                List<VideoResponseVo> videoConfigListResponse = videoConfigListResponseVo.getList();
                if (CollectionUtils.isNotEmpty(videoConfigListResponse)) {
                    for (VideoResponseVo videoResponseVo : videoConfigListResponse) {
                        VideoEntity videoEntity = new VideoEntity();
                        videoEntity.setVideoId(videoResponseVo.getId().toString());
                        videoEntity.setName(videoResponseVo.getTitle());
                        //图片宽度100%  中部截取
                        if (StringUtils.isNotBlank(videoResponseVo.getFrontImg())) {
                            if (request.getWidth() != null) {
                                videoEntity.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(videoResponseVo.getFrontImg(), request.getWidth(), -1));
                                //设置小图，宽度小于700
                                videoEntity.setHeadImgSmallUrl(QiniuImageUtils.compressImageAndDiffPic(videoResponseVo.getFrontImg(), 700, -1));
                            } else {
                                videoEntity.setHeadImgUrl(videoResponseVo.getFrontImg());
                                videoEntity.setHeadImgSmallUrl(videoResponseVo.getFrontImg());
                            }
                        } else {
                            videoEntity.setHeadImgUrl("");
                        }
                        videoEntity.setType(videoResponseVo.getType().toString());//视频类别
                        videoEntity.setVideoUrl(videoResponseVo.getLink());
                        videoEntity.setPraise(videoResponseVo.getIntroduction());
                        bannerList.add(videoEntity);
                    }
                }
            }

            //查询视频分类
            VideoTypeListResponseVo videoTypeListResponse = homeCardWcmProxy.getVideoTypeList();
            if (videoTypeListResponse != null) {
                List<VideoTypeEntity> videoTypeEntities = videoTypeListResponse.getTypeList();
                if (CollectionUtils.isNotEmpty(videoTypeEntities)) {
                    for (VideoTypeEntity videoTypeEntity : videoTypeEntities) {
                        //视频版块过滤小艾课堂
                        if (StringUtils.isNotBlank(videoTypeEntity.getCode()) && !videoTypeEntity.getCode().equals("小艾课堂")) {
                            VideoTypeResponse videoTypeResponse = new VideoTypeResponse();
                            videoTypeResponse.setTypeId(videoTypeEntity.getId());
                            videoTypeResponse.setTypeName(videoTypeEntity.getCode());
                            typeList.add(videoTypeResponse);
                        }
                    }
                    videoTypeId = typeList.get(0).getTypeId();
                }
            }
        }

        //根据视频类型查询所有视频列表
        VideoListResponseVo responseVo = homeCardWcmProxy.getVideoList(videoTypeId, request.getPageNo(), request.getPageSize());
        if (responseVo != null) {
            List<VideoResponseVo> videoListResponse = responseVo.getList();
            if (CollectionUtils.isNotEmpty(videoListResponse)) {
                for (VideoResponseVo videoResponseVo : videoListResponse) {
                    VideoEntity videoEntity = new VideoEntity();
                    videoEntity.setVideoId(videoResponseVo.getId().toString());
                    videoEntity.setName(videoResponseVo.getTitle());
                    //图片宽度100%  中部截取
                    if (StringUtils.isNotBlank(videoResponseVo.getFrontImg())) {
                        if (request.getWidth() != null) {
                            videoEntity.setHeadImgUrl(QiniuImageUtils.compressImageAndDiffPic(videoResponseVo.getFrontImg(), request.getWidth(), -1));
                            //设置小图，宽度小于700
                            videoEntity.setHeadImgSmallUrl(QiniuImageUtils.compressImageAndDiffPic(videoResponseVo.getFrontImg(), 700, -1));
                        } else {
                            videoEntity.setHeadImgUrl(videoResponseVo.getFrontImg());
                            videoEntity.setHeadImgSmallUrl(videoResponseVo.getFrontImg());
                        }
                    } else {
                        videoEntity.setHeadImgUrl("");
                    }
                    videoEntity.setType(videoResponseVo.getType().toString());//视频类别
                    videoEntity.setVideoUrl(videoResponseVo.getLink());
                    videoEntity.setPraise(videoResponseVo.getIntroduction());
                    videoList.add(videoEntity);
                }
            }

            if (responseVo.getPageNo() != null) {
                response.setPageNo(responseVo.getPageNo());
            }
            if (responseVo.getPageSize() != null) {
                response.setPageSize(responseVo.getPageSize());
            }
            if (responseVo.getTotalCount() != null) {
                response.setTotalCount(responseVo.getTotalCount());
            }
            if (responseVo.getTotalPage() != null) {
                response.setTotalPage(responseVo.getTotalPage());
            }
        }

        response.setBannerList(bannerList);
        response.setVideoList(videoList);
        response.setTypeList(typeList);

        LOG.info("HomeCardServiceImpl getVideoBoard() interface end");
        return response;
    }

    @Override
    public Map getProductFilterInfo(ProductFilterInfoRequest request) {
        //产品版块筛选条件
        List<BasePropertyResponseVo> basePropertyResponse = homeCardBossProxy.getProductFilterInfo();

        List<ProductFilterInfo> styleList = new ArrayList<ProductFilterInfo>();
        List<ProductFilterInfo> roomList = new ArrayList<ProductFilterInfo>();
        List<ProductFilterInfo> priceList = new ArrayList<ProductFilterInfo>();
        List<ProductFilterInfo> roomMarkList = new ArrayList<ProductFilterInfo>();
        for (BasePropertyResponseVo basePropertyResponseVo : basePropertyResponse) {
            ProductFilterInfo filterInfo = new ProductFilterInfo();
            filterInfo.setFilterId(basePropertyResponseVo.getPropertyId());
            filterInfo.setFilterName(basePropertyResponseVo.getPropertyName());
            //属性类型:1系列 2风格 3空间标识 4空间用途
            if (basePropertyResponseVo.getPropertyType() == 1) {
                if (!basePropertyResponseVo.getPropertyName().equals(HomeCardPraise.STYLE_SERIES)) {
                    priceList.add(filterInfo);
                }
            } else if (basePropertyResponseVo.getPropertyType() == 2) {
                styleList.add(filterInfo);
            } else if (basePropertyResponseVo.getPropertyType() == 4) {
                if (null != request && request.getIsRoomUsedAll() != null && request.getIsRoomUsedAll() == 1) {
                    // 返回全部空间用途
                    roomList.add(filterInfo);
                } else {
                    if (HomeCardPraise.ROOM_TYPE_LIST.contains(filterInfo.getFilterName())) {
                        roomList.add(filterInfo);
                    }
                }
            } else if (basePropertyResponseVo.getPropertyType() == 3) {
                filterInfo.setRoomClassifyType(basePropertyResponseVo.getRoomClassifyType());
                roomMarkList.add(filterInfo);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("styleList", styleList);
        result.put("styleName", HomeCardPraise.STYLE_NAME);
        Map<String, Object> result1 = new HashMap<String, Object>();
        result1.put("roomList", roomList);
        result1.put("roomName", HomeCardPraise.ROOM_NAME);
        Map<String, Object> result2 = new HashMap<String, Object>();
        result2.put("priceList", priceList);
        result2.put("priceName", HomeCardPraise.SERIES_NAME);
        Map<String, Object> result3 = new HashMap<String, Object>();
        result3.put("roomMarkList", roomMarkList);
        result3.put("roomMarkName", HomeCardPraise.ROOM_MARK_NAME);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("styleInfo", result);
        response.put("roomInfo", result1);
        response.put("priceInfo", result2);
        response.put("roomMarkInfo", result3);
        return response;
    }

    @Override
    public DnaDetailResponse getDnaDetailById(DnaDetailRequest request) {
        if (IntegerUtil.isEmpty(request.getWidth())) {
            request.setWidth(750);
        }
        DnaDetailResponse response = new DnaDetailResponse();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        String watermark_type = HomeCardPraise.WATERMARK_DNA;
        //图片宽度90%
        if (request.getWidth() != null && request.getWidth().compareTo(HomeCardPraise.WIDTH_BIG) > 0) {
            watermark_type = HomeCardPraise.WATERMARK_DNA_BIG;
        }

        //根据DNA  ID查询DNA详情信息
        DNAInfoResponseVo dnaInfoResponse = homeCardBossProxy.getDnaDetailById(request.getDnaId());

        if (dnaInfoResponse != null) {
            //水印图片地址
            String waterImageUrl = "";

            DicDto dicVo = dicProxy.queryDicByKey(watermark_type);
            if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                waterImageUrl = dicVo.getValueDesc();
            }

            // 异步写入DNA浏览记录（失败与否不影响主流程）
            asnycAddVisitRecord(request.getDnaId(), HomeCardPraise.VISIT_DNA);

            response.setDnaId(dnaInfoResponse.getDnaId());
            //图片宽度固定，高度自适应
            if (StringUtils.isNotBlank(dnaInfoResponse.getHeadImgUrl())) {
                String headImageUrl = "";
                headImageUrl = AliImageUtil.imageCompress(dnaInfoResponse.getHeadImgUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE);
                Picture picture = AppRedisUtil.getRedisImageSize(dnaInfoResponse.getHeadImgUrl(), serviceCaller);
                response.setHeadImgWidth(picture.getWidth());
                response.setHeadImgHeight(picture.getHeight());
                response.setHeadImgUrl(headImageUrl);
            } else {
                response.setHeadImgUrl("");
            }
            //DNA空间列表
            List<DnaRoomImageEntity> imgList = new ArrayList<DnaRoomImageEntity>();
            List<DNARoomVo> dnaRoomList = dnaInfoResponse.getDnaRoomList();
            List<String> imageList = new ArrayList<>();
            Map<String, Picture> pictureMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(dnaRoomList)) {
                for (DNARoomVo dnaRoomVo : dnaRoomList) {
                    if (CollectionUtils.isNotEmpty(dnaRoomVo.getRoomPictureList())) {
                        for (DNARoomPictureVo dnaRoomPictureVo : dnaRoomVo.getRoomPictureList()) {
                            if (!StringUtil.isNullOrEmpty(dnaRoomPictureVo.getPictureURL())) {
                                imageList.add(dnaRoomPictureVo.getPictureURL());
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(imageList)) {
                    pictureMap = AppRedisUtil.getRedisImageSizeMap(imageList, serviceCaller);
                }
                for (DNARoomVo dnaRoomVo : dnaRoomList) {
                    //各空间图片头图在各空间的第一个显示
                    List<DnaRoomImageEntity> roomOtherImgList = new ArrayList<DnaRoomImageEntity>();//空间其他图片

                    //DNA各空间所有图片    各空间图片头图在各空间的第一个显示
                    List<DNARoomPictureVo> dnaRoomPictureVos = dnaRoomVo.getRoomPictureList();
                    if (CollectionUtils.isNotEmpty(dnaRoomPictureVos)) {
                        for (DNARoomPictureVo dnaRoomPictureVo : dnaRoomPictureVos) {
                            DnaRoomImageEntity dnaRoomImageEntity = new DnaRoomImageEntity();
                            dnaRoomImageEntity.setUsedCount(dnaRoomVo.getUsedCount());
                            dnaRoomImageEntity.setRoomName(dnaRoomVo.getRoomType());
                            List<String> roomItemBrandList = dnaRoomVo.getRoomItemBrandList();
                            String brandPraise = "";
                            if (CollectionUtils.isNotEmpty(roomItemBrandList)) {
                                for (String string : roomItemBrandList) {
                                    brandPraise = brandPraise + string + " ";
                                }
                            }
                            dnaRoomImageEntity.setBrandPraise(brandPraise);//空间品牌文案列表
                            dnaRoomImageEntity.setRoomPraise(dnaRoomVo.getRoomDescription());
                            //图片宽度固定，高度自适应
                            if (StringUtils.isNotBlank(dnaRoomPictureVo.getPictureURL())) {
                                String roomImageUrl = "";
                                roomImageUrl = AliImageUtil.imageCompress(dnaRoomPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE);
                                Picture picture = pictureMap.get(dnaRoomPictureVo.getPictureURL());
                                dnaRoomImageEntity.setImgWidth(picture.getWidth());
                                dnaRoomImageEntity.setImgHeight(picture.getHeight());
                                dnaRoomImageEntity.setImgUrl(roomImageUrl);
                                dnaRoomImageEntity.setBigImgUrl(AliImageUtil.imageCompress(dnaRoomPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_LARGE));
                            } else {
                                dnaRoomImageEntity.setImgUrl("");
                            }

                            if (dnaRoomPictureVo.getIsFirst() == 1) {
                                imgList.add(dnaRoomImageEntity);
                            } else {
                                roomOtherImgList.add(dnaRoomImageEntity);
                            }
                        }
                    }

                    if (CollectionUtils.isNotEmpty(roomOtherImgList)) {
                        imgList.addAll(roomOtherImgList);
                    }
                }
            }
            response.setImgList(imgList);
            if (StringUtils.isNotBlank(dnaInfoResponse.getSeriesName())) {
                response.setPriceType(dnaInfoResponse.getSeriesName() + HomeCardPraise.SERIES_NAME);
            } else {
                response.setPriceType(dnaInfoResponse.getSeriesName());
            }
            response.setName(dnaInfoResponse.getDnaName());
            response.setPraise(dnaInfoResponse.getShortDesc());
            response.setStyle(dnaInfoResponse.getStyleName());
            response.setTagList(dnaInfoResponse.getTagList());
            response.setIdea(dnaInfoResponse.getDesignIdea());
            //意境倡导
            ArtisticListEntity artistic = new ArtisticListEntity();
            artistic.setArtisticTitle(HomeCardPraise.ARTISTIC_NAME);
            List<ArtisticEntity> artisticList = new ArrayList<ArtisticEntity>();
            List<DNAProspectPictureVo> prospectPictureList = dnaInfoResponse.getProspectPictureList();
            if (CollectionUtils.isNotEmpty(prospectPictureList)) {
                for (DNAProspectPictureVo dnaProspectPictureVo : prospectPictureList) {
                    ArtisticEntity artisticEntity = new ArtisticEntity();
                    artisticEntity.setArtistic(dnaProspectPictureVo.getPictureTag());
                    //图片宽度50%  中部截取
                    if (StringUtils.isNotBlank(dnaProspectPictureVo.getPictureURL())) {
                        if (request.getWidth() != null) {
                            artisticEntity.setArtisticImgUrl(AliImageUtil.imageCompress(dnaProspectPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                        } else {
                            artisticEntity.setArtisticImgUrl(dnaProspectPictureVo.getPictureURL());
                        }
                    } else {
                        artisticEntity.setArtisticImgUrl("");
                    }
                    artisticList.add(artisticEntity);
                }
            }
            artistic.setArtisticList(artisticList);
            response.setArtistic(artistic);
            //用户评论  默认5条
            DnaCommentList commentListResponse = new DnaCommentList();
            commentListResponse.setCommentTitle(HomeCardPraise.COMMENT_NAME);
            /**
             * 加入用户等级判断逻辑,并将判断结果作为查询参数
             *
             * 修改内容：1、增加用户等级判断逻辑，2、CommentListResponseVo commentListResponseVo = homeCardWcmProxy.queryCommentListByDnaId;接口增加用户等级、用户id参数
             * added by matao 2018/5/14
             */
            HttpUserInfoRequest userDto = request.getUserInfo();
            CommentListResponseVo commentListResponseVo = homeCardWcmProxy.queryCommentListByDnaId(dnaInfoResponse.getDnaId(), HomeCardPraise.COMMENT_TYPE_DNA, 1, 5, this.getUserLevel(userDto), userDto == null ? null : userDto.getId());

            if (commentListResponseVo != null) {
                List<DnaComment> commentResponse = new ArrayList<DnaComment>();
                List<CommentResponseVo> commentList = commentListResponseVo.getList();
                if (CollectionUtils.isNotEmpty(commentList)) {
                    //官方回复名称
                    String officialName = this.getCommentAdminName();
                    //官方手机号码
                    List<String> adminMobileList = this.getAdminMobileList();
                    // 评论id集合
                    List<String> commentIds = this.getCommentIds(commentList);
                    //评论回复
                    CommentReplyListResponseVo replyListResponseVo = homeCardWcmProxy.queryReplyCommentListByPids(commentIds, HomeCardPraise.COMMENT_TYPE_DNA);

                    for (CommentResponseVo commentResponseVo : commentList) {
                        DnaComment dnaComment = new DnaComment();
                        if (commentResponseVo.getId() != null) {
                            dnaComment.setCommentId(Integer.parseInt(commentResponseVo.getId()));
                        }
                        dnaComment.setContent(commentResponseVo.getContent());
                        if (commentResponseVo.getCreateTime() != null) {
                            dnaComment.setCreateTime(commentResponseVo.getCreateTime());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                            dnaComment.setCreateTimeDesc(dateFormat.format(commentResponseVo.getCreateTime()));
                        }
                        if (StringUtils.isNotBlank(commentResponseVo.getMobile())) {
                            dnaComment.setMobileHide(commentResponseVo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                        }
                        dnaComment.setUserId(commentResponseVo.getUserId());
                        if (StringUtils.isNotBlank(commentResponseVo.getNickName())) {
                            dnaComment.setUserNickName(commentResponseVo.getNickName());
                        } else {
                            if (StringUtils.isNotBlank(dnaComment.getMobileHide())) {
                                dnaComment.setUserNickName(dnaComment.getMobileHide());
                            } else {
                                dnaComment.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
                            }
                        }
                        dnaComment.setStarNum(commentResponseVo.getStarNum());
                        if (StringUtils.isNotBlank(commentResponseVo.getHeadImg())) {
                            Integer width = 0;
                            if (request.getWidth() != null) {
                                width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_20
                                        / ImageSize.WIDTH_PER_SIZE_100;
                            }
                            dnaComment.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo.getHeadImg(), width, -1));
                        } else {
                            dnaComment.setUserHeadImgUrl("");
                        }
                        if (replyListResponseVo != null) {

                            List<CommentResponseVo> replyList = this.getReplyList(replyListResponseVo.getReplyCommentList(), commentResponseVo.getId());

                            if (CollectionUtils.isNotEmpty(replyList)) {
                                List<CommentReply> commentReplyList = new ArrayList<CommentReply>();
                                for (CommentResponseVo commentResponseVo2 : replyList) {
                                    CommentReply commentReply = new CommentReply();
                                    commentReply.setCommentId(commentResponseVo2.getId());
                                    commentReply.setContent(commentResponseVo2.getContent());
                                    if (commentResponseVo2.getCreateTime() != null) {
                                        commentReply.setCreateTime(commentResponseVo2.getCreateTime());
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                                        commentReply.setCreateTimeDesc(dateFormat.format(commentResponseVo2.getCreateTime()));
                                    }
                                    //官方回复标志
                                    if (adminMobileList.contains(commentResponseVo2.getMobile())) {
                                        commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT);
                                        commentReply.setUserNickName(officialName);
                                    } else {
                                        commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_ORTHER);
                                        if (StringUtils.isNotBlank(commentResponseVo2.getNickName())) {
                                            commentReply.setUserNickName(commentResponseVo2.getNickName());
                                        } else {
                                            if (StringUtils.isNotBlank(commentResponseVo2.getMobile())) {
                                                commentReply.setUserNickName(commentResponseVo2.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                                            } else {
                                                commentReply.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
                                            }
                                        }
                                    }
                                    commentReply.setReplyUserId(commentResponseVo2.getReplyUserId());
                                    commentReply.setStarNum(commentResponseVo2.getStarNum());
                                    if (StringUtils.isNotBlank(commentResponseVo2.getHeadImg())) {
                                        Integer width = 0;
                                        if (request.getWidth() != null) {
                                            width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_20
                                                    / ImageSize.WIDTH_PER_SIZE_100;
                                        }
                                        commentReply.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo2.getHeadImg(), width, -1));
                                    } else {
                                        commentReply.setUserHeadImgUrl("");
                                    }
                                    if (StringUtils.isNotBlank(commentResponseVo2.getReplyNickName())) {
                                        commentReply.setReplyNickName(commentResponseVo2.getReplyNickName());
                                    } else {
                                        commentReply.setReplyNickName(HomeCardPraise.ANONYMOUS_USER);
                                    }
                                    commentReply.setReplyUserId(commentResponseVo2.getReplyUserId());
                                    commentReplyList.add(commentReply);
                                }
                                dnaComment.setCommentReplyList(commentReplyList);
                            }
                        }

                        commentResponse.add(dnaComment);
                    }
                    commentListResponse.setCommentNum(commentListResponseVo.getTotalCount());
                } else {
                    commentListResponse.setCommentNum(0);
                }
                commentListResponse.setCommentList(commentResponse);
                if (CollectionUtils.isNotEmpty(commentListResponseVo.getList())) {
                    List<Integer> userIdList = Lists.newArrayList();
                    userIdList.addAll(commentListResponseVo.getList().stream().map(CommentResponseVo::getReplyUserId).collect(Collectors.toList()));
                    userIdList.addAll(commentListResponseVo.getList().stream().map(CommentResponseVo::getUserId).collect(Collectors.toList()));
                    userIdList.stream().distinct();
                    userIdList.removeIf(id -> id == null);
                    List<UserDto> userDtos = userProxy.batchQueryUserInfo(userIdList.stream().distinct().collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(commentListResponse.getCommentList()) && CollectionUtils.isNotEmpty(userDtos)) {
                        Map<Integer, UserDto> userMap = userDtos.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
                        commentListResponse.getCommentList().forEach(dnaComment -> {
                            UserDto userDtoTemp = userMap.get(dnaComment.getUserId());
                            if (userDtoTemp != null && userDtoTemp.getMember() != null && StringUtils.isNotBlank(userDtoTemp.getMember().getuImg())) {
                                dnaComment.setUserNickName(userDtoTemp.getMember().getNickName());
                                dnaComment.setUserHeadImgUrl(AliImageUtil.imageCompress(userDtoTemp.getMember().getuImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                            } else {
                                dnaComment.setUserHeadImgUrl(StaticResourceConstants.USER_DEFAULT_HEAD_IMAGE);
                            }
                            if (CollectionUtils.isNotEmpty(dnaComment.getCommentReplyList())) {
                                dnaComment.getCommentReplyList().forEach(commentReply -> {
                                    UserDto userDto1 = userMap.get(commentReply.getReplyUserId());
                                    if (userDto1 != null && userDto1.getMember() != null) {
                                        commentReply.setUserNickName(userDto1.getMember().getNickName());
                                        commentReply.setUserHeadImgUrl(AliImageUtil.imageCompress(userDto1.getMember().getuImg(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                                    } else {
                                        commentReply.setUserHeadImgUrl(StaticResourceConstants.USER_DEFAULT_HEAD_IMAGE);
                                    }
                                });
                            }
                        });
                    }
                }
            }
            response.setComment(commentListResponse);

            //评论是否需要登录
            CommentLimitResponseVo commentLimitResponseVo = homeCardWcmProxy.queryCommentLimitByCode(HomeCardPraise.DNA_COMMENT_LIMIT);
            if (commentLimitResponseVo != null && commentLimitResponseVo.getActiveFlag() != null && HomeCardPraise.DNA_COMMENT_LOGIN.equals(commentLimitResponseVo.getActiveFlag())) {
                response.setCommentLimitFlag(HomeCardPraise.DNA_COMMENT_LOGIN);
            } else {
                response.setCommentLimitFlag(HomeCardPraise.DNA_COMMENT_NOLOGIN);
            }

            //软硬装亮点
            LightPointList lightPoint = new LightPointList();
            lightPoint.setTitle(HomeCardPraise.INCLUDE_NAME);
            lightPoint.setSubTitle(HomeCardPraise.ALL_GOODS);
            List<LightPointEntity> pointList = new ArrayList<LightPointEntity>();
            //软装亮点
            LightPointEntity softPoint = new LightPointEntity();
            softPoint.setType(2);
            softPoint.setLightPointTitle(HomeCardPraise.SOFT_DETAIL);
            softPoint.setSkipUrl(apiConfig.getSoftSkipUrl() + request.getDnaId());//软装清单跳转路径
            List<String> softList = new ArrayList<String>();
            List<DNASoftDecorationFeature> softDecorationFeatureList = dnaInfoResponse.getSoftDecorationFeatureList();
            if (CollectionUtils.isNotEmpty(softDecorationFeatureList)) {
                int index = 1;
                for (DNASoftDecorationFeature dnaSoftDecorationFeature : softDecorationFeatureList) {
                    softList.add(index + "." + dnaSoftDecorationFeature.getFeatureDesc());
                    index++;
                }
            }
            softPoint.setPoint(softList);
            pointList.add(softPoint);
            lightPoint.setPointList(pointList);
            response.setLightPoint(lightPoint);
            if (StringUtils.isNotBlank(dnaInfoResponse.getVrLinkUrl())) {
                response.setVrLinkUrl(dnaInfoResponse.getVrLinkUrl());//VR链接
            } else {
                response.setVrLinkUrl("");//VR链接
            }
            //DNA分享页
            response.setShareUrl(HomeCardPraise.SHARE_URL + dnaInfoResponse.getDnaId());
            //点赞的人数
            response.setFavoriteNum(homeCardWcmProxy.queryFavoriteCountByDnaId(dnaInfoResponse.getDnaId()));
            //DNA参考价  20170818要求不展示
			/*if(dnaInfoResponse.getDnaTotalPrice() != null && dnaInfoResponse.getDnaTotalPrice().compareTo(BigDecimal.ZERO) > 0){
				response.setDnaTotalPrice(nf.format(dnaInfoResponse.getDnaTotalPrice()).toString());
			}*/
            //DNA参考文案
            //response.setDnaPricePraise(HomeCardPraise.DNA_PRICE_PRAISE);
        }

        if (null != response) {
            setDesignerSimpleInfoResponse(response, request.getDnaId(), request.getOsType(), request.getWidth());
            addDnaBrowseRecord(request);
        }
        return response;
    }

    private void addDnaBrowseRecord(DnaDetailRequest request) {
        try {
            //增加装修报价dna浏览记录
            if (request.getQuotePriceId() != null && request.getQuotePriceId() != 0) {
                decorationQuotationProxy.addDnaBrowseRecord(new DnaBrowseRecordDto().setDnaId(request.getDnaId()).setQuotePriceId(request.getQuotePriceId()).setUserId(request.getUserInfo().getId()));
            }
        } catch (Exception e) {
            LOG.error("addDnaBrowseRecord error", e);
        }
    }


    private void setDesignerSimpleInfoResponse(DnaDetailResponse response, Integer dnaId, Integer osType, Integer width) {
        DesignerSimpleInfoResponse designerSimpleInfoResponse = artistProxy
                .querySimpleDesingerInfoByDnaId(dnaId);
        if (designerSimpleInfoResponse != null) {
            DesignerResponse designer = new DesignerResponse();
            if (designerSimpleInfoResponse.getUserId() != null) {
                designer.setDesignerId(designerSimpleInfoResponse.getUserId());
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDesignerName())) {
                designer.setDesignerName(designerSimpleInfoResponse.getDesignerName());
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDesignerTag())) {
                String designerTag = designerSimpleInfoResponse.getDesignerTag();
                String designerLables = designerTag.replaceAll(",", "、");
                designer.setDesignerLables(designerLables);
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDesignerImage())) {
                String headImgUrl = AliImageUtil.imageCompress(designerSimpleInfoResponse.getDesignerImage(), osType, width, ImageConstant.SIZE_SMALL);
                designer.setHeadImgUrl(headImgUrl);
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDesingerBackImage())) {
                designer.setDesingerBackImage(designerSimpleInfoResponse.getDesingerBackImage());
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDnaAudio())) {
                designer.setAudioUrl(designerSimpleInfoResponse.getDnaAudio());
            }
            if (StringUtils.isNotBlank(designerSimpleInfoResponse.getDnaVideo())) {
                designer.setVideoUrl(designerSimpleInfoResponse.getDnaVideo());
            }
            response.setDesigner(designer);
        }
    }

    private void asnycAddVisitRecord(Integer dnaId, Integer visitDna) {
        TaskAction<Integer> task = () -> {
            try {
                homeCardWcmProxy.addVisitRecord(dnaId, visitDna);
            } catch (Exception e) {
                LOG.error("asnycAddVisitRecord error:{}", e);
            }
            return 1;
        };
        TaskProcessManager.getTaskProcess().asyncExecuteTask(task);
    }

    /**
     * 获取官方回复
     *
     * @return
     */
    private String getCommentAdminName() {
        // 官方回复名称
        String officialName = "";
        DicListDto dicListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN);
        if (dicListResponseVo != null) {
            List<DicDto> dicList = dicListResponseVo.getDicList();
            if (!CollectionUtils.isEmpty(dicList)) {
                officialName = dicList.get(0).getValueDesc();
            }
        }
        return officialName;
    }

    /**
     * 获取官方号码
     *
     * @return
     */
    private List<String> getAdminMobileList() {
        List<String> list = new ArrayList<String>();
        DicListDto dicListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN_MOBILE);
        if (dicListResponseVo != null) {
            List<DicDto> dicList = dicListResponseVo.getDicList();
            if (!CollectionUtils.isEmpty(dicList)) {
                for (DicDto dic : dicList) {
                    list.add(dic.getValueDesc());
                }
            }
        }
        return list;
    }

    @Override
    public HardSpaceListResponseVo getHardListByCondition(HardSoftRequest request) {
        HardSpaceListResponseVo result = new HardSpaceListResponseVo();
        if (request != null && request.getDnaId() != null) {
            DNAInfoResponseVo dnaInfoResponseVo = homeCardBossProxy.getDnaDetailById(request.getDnaId());
            if (dnaInfoResponseVo != null) {
                String seriesName = "";//套系名称
                //根据价格系查询硬装清单
                List<HardStandardSpaceResponse> standardSpaceResponses = new ArrayList<HardStandardSpaceResponse>();
                if (StringUtils.isNotBlank(dnaInfoResponseVo.getSeriesName())) {
                    seriesName = dnaInfoResponseVo.getSeriesName() + HomeCardPraise.SERIES_NAME;
                    List<String> seriesNameList = new ArrayList<String>();
                    seriesNameList.add(dnaInfoResponseVo.getSeriesName());
                    HardStandardListResponseVo hardStandardListResponseVo = hardStandardWcmProxy.queryHardStandByCondition(seriesNameList);
                    if (hardStandardListResponseVo != null && CollectionUtils.isNotEmpty(hardStandardListResponseVo.getHardStandardList())) {
                        HardStandardResponseVo responseVo = hardStandardListResponseVo.getHardStandardList().get(0);//硬装标准
                        if (responseVo != null && CollectionUtils.isNotEmpty(responseVo.getSpaceList())) {
                            List<HardStandardSpaceResponseVo> spaceList = responseVo.getSpaceList();
                            HardStandardItem title = new HardStandardItem();
                            title.setSubjectName(HomeCardPraise.HARD_DESC);
                            title.setMaterial(HomeCardPraise.BRAND_MATERIAL);
                            for (HardStandardSpaceResponseVo hardStandardSpaceResponseVo : spaceList) {
                                HardStandardSpaceResponse spaceResponse = new HardStandardSpaceResponse();
                                List<HardStandardItem> itemListResponse = new ArrayList<HardStandardItem>();
                                itemListResponse.add(title);
                                spaceResponse.setSpaceName(hardStandardSpaceResponseVo.getSpaceName());//空间
                                List<HardStandardDetailVo> itemList = hardStandardSpaceResponseVo.getItemList();//项目材质
                                for (HardStandardDetailVo hardStandardDetailVo : itemList) {
                                    HardStandardItem item = new HardStandardItem();
                                    item.setMaterial(hardStandardDetailVo.getMaterial());//材质
                                    item.setSubjectName(hardStandardDetailVo.getSubjectName());//项目
                                    itemListResponse.add(item);
                                }
                                spaceResponse.setItemList(itemListResponse);
                                standardSpaceResponses.add(spaceResponse);
                            }
                        }
                    }
                }
                result.setSpaceList(standardSpaceResponses);
                result.setSeriesName(seriesName);
            }
        }
        return result;
    }

    @Override
    @Cacheable(cacheNames = "o2o-api", keyGenerator = "springCacheKeyGenerator")
    public SoftDetailListResponseVo getSoftListByCondition(HardSoftRequest request) {
        List<SoftDetailResponse> softList = new ArrayList<SoftDetailResponse>();
        //根据DNA  ID查询软装清单
        List<DNARoomAndItemVo> dNARoomAndItemResponse = homeCardBossProxy.getSoftListByCondition(request.getDnaId());
        if (CollectionUtils.isNotEmpty(dNARoomAndItemResponse)) {
            for (DNARoomAndItemVo dnaRoomAndItemVo : dNARoomAndItemResponse) {
                SoftDetailResponse softDetailResponse = new SoftDetailResponse();
                if (request.getDnaRoomUsageId() != null && !RoomUseEnum.getDescription(request.getDnaRoomUsageId()).equals(dnaRoomAndItemVo.getRoomUseName())) {
                    continue;
                }
                softDetailResponse.setRoom(dnaRoomAndItemVo.getRoomUseName());
                List<DNARoomItemVo> dnaRoomItemList = dnaRoomAndItemVo.getDnaRoomItemList();
                List<SoftDetail> softDetailList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(dnaRoomItemList)) {
                    for (DNARoomItemVo dNARoomItemVo : dnaRoomItemList) {
                        SoftDetail softDetail = new SoftDetail();
                        if (StringUtils.isNotBlank(dNARoomItemVo.getItemBrand())) {
                            softDetail.setBrand(dNARoomItemVo.getItemBrand());
                        }
                        if (StringUtils.isNotBlank(dNARoomItemVo.getItemColor())) {
                            softDetail.setColor(dNARoomItemVo.getItemColor());
                        }
                        softDetail.setItemCount(dNARoomItemVo.getItemCount());
                        if (StringUtils.isNotBlank(dNARoomItemVo.getItemMaterial())) {
                            softDetail.setMaterial(dNARoomItemVo.getItemMaterial());
                        }
                        if (StringUtils.isNotBlank(dNARoomItemVo.getItemSize())) {
                            softDetail.setMatterSize(dNARoomItemVo.getItemSize());
                        }
                        if (StringUtils.isNotBlank(dNARoomItemVo.getItemName())) {
                            softDetail.setName(dNARoomItemVo.getItemName());
                        }
                        softDetail.setSkuId(dNARoomItemVo.getSkuId());
                        softDetailList.add(softDetail);
                    }
                }
                softDetailResponse.setItemList(softDetailList);
                softList.add(softDetailResponse);
            }
        }

        SoftDetailListResponseVo result = new SoftDetailListResponseVo();
        result.setSoftList(softList);
        return result;
    }

    private Integer getCityId(String cityCode) {
        CityHardListReaponseVo cityHardList = homeCardWcmProxy.getCityHardList();
        Integer cityId = 0;
        if (cityHardList != null) {
            List<CityHardVo> cityList = cityHardList.getCityList();
            if (CollectionUtils.isNotEmpty(cityList)) {
                for (CityHardVo cityHardVo : cityList) {
                    if (cityCode.equals(cityHardVo.getCode())) {
                        cityId = cityHardVo.getId();
                    }
                }
            }
        }

        return cityId;
    }

    private Integer getHardId(String hardName) {
        CityHardListReaponseVo cityHardList = homeCardWcmProxy.getCityHardList();
        Integer hardId = 0;
        if (cityHardList != null) {
            List<CityHardVo> hardList = cityHardList.getHardList();
            if (CollectionUtils.isNotEmpty(hardList)) {
                for (CityHardVo cityHardVo : hardList) {
                    if (hardName.equals(cityHardVo.getCode())) {
                        hardId = cityHardVo.getId();
                    }
                }
            }
        }

        return hardId;
    }

    @Override
    public DnaFavoriteResultResponse setDnaFavorite(Integer userId, Integer dnaId) {
        boolean result = false;
        Integer favoriteNum = 0;
        String favoritePraise = "";

        DnaFavoriteResultResponse dnaFavoriteResultResponse = new DnaFavoriteResultResponse();
        result = homeCardWcmProxy.addFavoriteRecord(userId, dnaId, HomeCardPraise.DNA_FAVORITE_YES);
        dnaFavoriteResultResponse.setFavoriteResult(result);
        if (result) {
            //点赞总数
            favoriteNum = homeCardWcmProxy.queryFavoriteCountByDnaId(dnaId);
            //随机文案
            favoritePraise = selectRandomPraise(HomeCardPraise.FAVORITE_SUCCESS_PRAISE);
        }
        dnaFavoriteResultResponse.setFavoriteNum(favoriteNum);
        dnaFavoriteResultResponse.setFavoritePraise(favoritePraise);

        return dnaFavoriteResultResponse;
    }

    @Override
    public DnaCommentResultResponse addDnaComment(DnaCommentRequest request) {
        Integer result = -1;
        String accessToken = request.getAccessToken();
        Integer osType = request.getOsType();
        DnaCommentResultResponse commentResultResponse = new DnaCommentResultResponse();

        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        String mobile = userDto.getMobile();
        Long uId = userDto.getId().longValue();
        if (null != uId) {
            userDao.addAccessLog(uId, osType);
        }

        //新增评论
        result = homeCardWcmProxy.addComment(request.getDnaId(), mobile, request.getContent(), 0, HomeCardPraise.COMMENT_TYPE_DNA, request.getCommentId());
        if (result <= 0) {
            throw new BusinessException("啊哦，发表失败了，请稍后重试");
        }

        //查询评论总数
        Integer commentCount = homeCardWcmProxy.queryCommentCountByDnaId(request.getDnaId(), HomeCardPraise.COMMENT_TYPE_DNA, this.getUserLevel(userDto), userDto.getId());
        commentResultResponse.setCommentNum(commentCount);
        //发表成功文案
        commentResultResponse.setCommentPraise(selectRandomPraise(HomeCardPraise.COMMENT_SUCCESS_PRAISE));
        //查询当前评论内容
        CommentResponseVo commentResponseVo = homeCardWcmProxy.queryCommentById(result);
        if (commentResponseVo != null) {
            commentResultResponse.setContent(commentResponseVo.getContent());
            if (commentResponseVo.getCreateTime() != null) {
                commentResultResponse.setCreateTime(commentResponseVo.getCreateTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                commentResultResponse.setCreateTimeDesc(dateFormat.format(commentResponseVo.getCreateTime()));
            } else {
                commentResultResponse.setCreateTime(null);
                commentResultResponse.setCreateTimeDesc("");
            }
            if (StringUtils.isNotBlank(commentResponseVo.getMobile())) {
                commentResultResponse.setMobileHide(commentResponseVo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
            } else {
                commentResultResponse.setMobileHide("");
            }
            if (StringUtils.isNotBlank(commentResponseVo.getNickName())) {
                commentResultResponse.setUserNickName(commentResponseVo.getNickName());
            } else {
                if (StringUtils.isNotBlank(commentResultResponse.getMobileHide())) {
                    commentResultResponse.setUserNickName(commentResultResponse.getMobileHide());
                } else {
                    commentResultResponse.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
                }
            }
            commentResultResponse.setStarNum(commentResponseVo.getStarNum());
            if (StringUtils.isNotBlank(commentResponseVo.getHeadImg())) {
                Integer width = 0;
                if (request.getWidth() != null) {
                    width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_20
                            / ImageSize.WIDTH_PER_SIZE_100;
                }
                commentResultResponse.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo.getHeadImg(), width, -1));
            } else {
                commentResultResponse.setUserHeadImgUrl("");
            }
        }

        return commentResultResponse;
    }

    private String selectRandomPraise(String keyDesc) {
        //查询所有文案
        DicListDto dicListResponseVo = dicProxy.getDicListByKey(keyDesc);
        if (dicListResponseVo == null) {
            return "";
        } else {
            //文案随机显示
            List<DicDto> dicList = dicListResponseVo.getDicList();
            if (CollectionUtils.isNotEmpty(dicList)) {
                int selectNum = createRandom(dicList.size()); //生成随机数
                if (selectNum < 0) {
                    return "";
                } else {
                    if (StringUtils.isNotBlank(dicList.get(selectNum).getValueDesc())) {
                        return dicList.get(selectNum).getValueDesc();
                    } else {
                        return "";
                    }
                }
            } else {
                return "";
            }
        }
    }

    Random random = new Random();

    /**
     * 生成随机数
     *
     * @return
     */
    private int createRandom(int count) {
        if (count > 0) {
            int num = random.nextInt(count);
            return num;
        } else {
            return -1;
        }
    }

    @Override
    public DnaCommentListResponse queryDnaCommentListByDnaId(DnaCommentListRequest request) {
        if (request.getDnaId() == null) {
            return null;
        } else {
            DnaCommentListResponse commentListResponse = new DnaCommentListResponse();
            Integer pageNo = 1;
            Integer pageSize = 10;
            if (request.getPageNo() != null && request.getPageNo() > 0) {
                pageNo = request.getPageNo();
            }
            if (request.getPageSize() != null && request.getPageSize() > 0) {
                pageSize = request.getPageSize();
            }
            HttpUserInfoRequest userDto = request.getUserInfo();

            CommentListResponseVo commentListResponseVo = homeCardWcmProxy.queryCommentListByDnaId(request.getDnaId(), HomeCardPraise.COMMENT_TYPE_DNA, pageNo, pageSize, this.getUserLevel(userDto), userDto == null ? null : userDto.getId());
            if (commentListResponseVo != null) {
                List<CommentResponseVo> list = commentListResponseVo.getList();
                List<DnaComment> commentList = new ArrayList<DnaComment>();

                if (CollectionUtils.isNotEmpty(list)) {
                    //官方回复名称
                    String officialName = this.getCommentAdminName();
                    //官方手机号码
                    List<String> adminMobileList = this.getAdminMobileList();
                    // 评论id集合
                    List<String> commentIds = this.getCommentIds(list);
                    //评论回复
                    CommentReplyListResponseVo replyListResponseVo = homeCardWcmProxy.queryReplyCommentListByPids(commentIds, HomeCardPraise.COMMENT_TYPE_DNA);

                    for (CommentResponseVo commentResponseVo : list) {
                        DnaComment dnaComment = new DnaComment();
                        dnaComment.setContent(commentResponseVo.getContent());
                        if (commentResponseVo.getCreateTime() != null) {
                            dnaComment.setCreateTime(commentResponseVo.getCreateTime());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                            dnaComment.setCreateTimeDesc(dateFormat.format(commentResponseVo.getCreateTime()));
                        }
                        if (StringUtils.isNotBlank(commentResponseVo.getMobile())) {
                            dnaComment.setMobileHide(commentResponseVo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                        }
                        if (StringUtils.isNotBlank(commentResponseVo.getNickName())) {
                            dnaComment.setUserNickName(commentResponseVo.getNickName());
                        } else {
                            if (StringUtils.isNotBlank(dnaComment.getMobileHide())) {
                                dnaComment.setUserNickName(dnaComment.getMobileHide());
                            } else {
                                dnaComment.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
                            }
                        }
                        dnaComment.setStarNum(commentResponseVo.getStarNum());
                        if (StringUtils.isNotBlank(commentResponseVo.getHeadImg())) {
                            Integer width = 0;
                            if (request.getWidth() != null) {
                                width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_20
                                        / ImageSize.WIDTH_PER_SIZE_100;
                            }
                            dnaComment.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo.getHeadImg(), width, -1));
                        } else {
                            dnaComment.setUserHeadImgUrl("");
                        }
                        //评论回复
                        if (replyListResponseVo != null) {
                            List<CommentResponseVo> replyList = this.getReplyList(replyListResponseVo.getReplyCommentList(), commentResponseVo.getId());
                            if (CollectionUtils.isNotEmpty(replyList)) {


                                List<CommentReply> commentReplyList = new ArrayList<CommentReply>();
                                for (CommentResponseVo commentResponseVo2 : replyList) {
                                    CommentReply commentReply = new CommentReply();
                                    commentReply.setCommentId(commentResponseVo2.getId());
                                    commentReply.setContent(commentResponseVo2.getContent());
                                    if (commentResponseVo2.getCreateTime() != null) {
                                        commentReply.setCreateTime(commentResponseVo2.getCreateTime());
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                                        commentReply.setCreateTimeDesc(dateFormat.format(commentResponseVo2.getCreateTime()));
                                    }
                                    //官方回复标志
                                    if (adminMobileList.contains(commentResponseVo2.getMobile())) {
                                        commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT);
                                        commentReply.setUserNickName(officialName);
                                    } else {
                                        commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_ORTHER);
                                        if (StringUtils.isNotBlank(commentResponseVo2.getNickName())) {
                                            commentReply.setUserNickName(commentResponseVo2.getNickName());
                                        } else {
                                            if (StringUtils.isNotBlank(commentResponseVo2.getMobile())) {
                                                commentReply.setUserNickName(commentResponseVo2.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                                            } else {
                                                commentReply.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
                                            }
                                        }
                                    }
                                    commentReply.setStarNum(commentResponseVo2.getStarNum());
                                    if (StringUtils.isNotBlank(commentResponseVo2.getHeadImg())) {
                                        Integer width = 0;
                                        if (request.getWidth() != null) {
                                            width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_20
                                                    / ImageSize.WIDTH_PER_SIZE_100;
                                        }
                                        commentReply.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo2.getHeadImg(), width, -1));
                                    } else {
                                        commentReply.setUserHeadImgUrl("");
                                    }
                                    if (StringUtils.isNotBlank(commentResponseVo2.getReplyNickName())) {
                                        commentReply.setReplyNickName(commentResponseVo2.getReplyNickName());
                                    } else {
                                        commentReply.setReplyNickName(HomeCardPraise.ANONYMOUS_USER);
                                    }
                                    commentReply.setReplyUserId(commentResponseVo2.getReplyUserId());
                                    commentReplyList.add(commentReply);
                                }
                                dnaComment.setCommentReplyList(commentReplyList);
                            }
                        }

                        commentList.add(dnaComment);
                    }
                }
                commentListResponse.setCommentList(commentList);
                commentListResponse.setTotalCount(commentListResponseVo.getTotalCount());
                commentListResponse.setTotalPage(commentListResponseVo.getTotalPage());
            } else {
                commentListResponse.setTotalCount(0);
                commentListResponse.setTotalPage(0);
            }
            commentListResponse.setPageNo(pageNo);
            commentListResponse.setPageSize(pageSize);
            return commentListResponse;
        }
    }

    /**
     * 该评论的回复集合
     */
    private List<CommentResponseVo> getReplyList(List<CommentResponseVo> replyCommentList, String commentId) {
        if (CollectionUtils.isEmpty(replyCommentList) || StringUtils.isBlank(commentId)) {
            return null;
        }
        List<CommentResponseVo> list = new ArrayList<CommentResponseVo>();
        for (CommentResponseVo vo : replyCommentList) {
            String id = vo.getpId();
            if (StringUtils.isNotBlank(id) && id.equals(commentId)) {
                list.add(vo);
            }
        }
        return list;
    }

    /**
     * 评论id集合
     *
     * @param list
     * @return
     */
    private List<String> getCommentIds(List<CommentResponseVo> list) {
        List<String> commentIds = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (CommentResponseVo vo : list) {
                commentIds.add(vo.getId());
            }
        }
        return commentIds;
    }

    @Override
    public UserFavoriteFlagResponseVo queryUserFavoriteFlagByDnaId(Integer userId, Integer dnaId) {
        Integer result = HomeCardPraise.DNA_FAVORITE_NO;

        DnaFavoriteResponseVo dnaFavoriteResponseVo = homeCardWcmProxy.queryFavoriteRecordByUserIdAndDnaId(userId, dnaId);
        if (dnaFavoriteResponseVo != null && dnaFavoriteResponseVo.getFavoriteFlag() != null && HomeCardPraise.DNA_FAVORITE_YES.equals(dnaFavoriteResponseVo.getFavoriteFlag())) {
            result = HomeCardPraise.DNA_FAVORITE_YES;
        }
        UserFavoriteFlagResponseVo resultObj = new UserFavoriteFlagResponseVo();
        resultObj.setFavoriteFlag(result);
        return resultObj;
    }

    @Override
    public DnaShareResponse shareDna(Integer dnaId) {
        //根据DNA  ID查询DNA详情信息
        DnaShareResponse dnaShareResponse = new DnaShareResponse();
        DNAInfoResponseVo dnaInfoResponse = homeCardBossProxy.getDnaDetailById(dnaId);
        if (dnaInfoResponse != null && dnaInfoResponse.getDnaId() != null) {
            dnaShareResponse.setmUrl(apiConfig.getDnaShareUrl() + dnaInfoResponse.getDnaId());
            Content content = new Content();
            String title = "";
            String styleName = "";
            String dnaName = "";
            if (StringUtils.isNotBlank(dnaInfoResponse.getStyleName())) {
                styleName = dnaInfoResponse.getStyleName() + HomeCardPraise.STYLE_NAME;
            }
            if (StringUtils.isNotBlank(dnaInfoResponse.getDnaName())) {
                dnaName = dnaInfoResponse.getDnaName();
            }
            if (StringUtils.isNotBlank(styleName) && StringUtils.isNotBlank(dnaName)) {
                title = styleName + "-" + dnaName;
            } else {
                title = styleName + dnaName;
            }
            content.setTitle(title);
            content.setDesc(HomeCardPraise.DNA_SHARE_SUBTITLE);
            if (StringUtils.isNotBlank(dnaInfoResponse.getHeadImgUrl())) {
                content.setUrl(AliImageUtil.imageCompress(dnaInfoResponse.getHeadImgUrl(), 1, 750, ImageConstant.SIZE_SMALL));
            } else {
                content.setUrl("");
            }
            dnaShareResponse.setShareContent(content);
            return dnaShareResponse;
        } else {
            return null;
        }
    }

    /**
     * 图片添加水印
     *
     * @param imageUrl
     * @return
     */
    private String watermarkImage(String imageUrl, String waterImageUrl, int imageWidth, int imageHeight) {
        if (StringUtils.isNotBlank(waterImageUrl)) {
            int distanceY = 30;
            if (imageHeight > 0) {
                distanceY = imageHeight * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
                if (distanceY > 47) {
                    distanceY = distanceY - 47;
                }
            }
            imageUrl = QiniuImageUtils.watermark(imageUrl, waterImageUrl, 100, "South", -1, distanceY, 0);
        }

        return imageUrl;
    }


    @Override
    public void setDnaForward(Integer dnaId) {
        //转发 DNA
        homeCardWcmProxy.addVisitRecord(dnaId, HomeCardPraise.FORWARD_DNA);
    }

    /**
     * step 1 :判断是不是特需用户
     *
     * @param mobile
     * @return
     */
    private boolean getSuperUserTag(String mobile) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(mobile)) {
            DicListDto dicList = dicProxy.getDicListByKey(HomeCardPraise.SHARE_ORDER_MOBILE);
            if (dicList != null && CollectionUtils.isNotEmpty(dicList.getDicList())) {
                List<DicDto> list = dicList.getDicList();
                for (DicDto dic : list) {
                    String value = dic.getValueDesc();
                    // step 1 :判断是不是特需用户
                    if (org.apache.commons.lang.StringUtils.isNotBlank(value) && value.equals(mobile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * added by matao 2018/5/14
     *
     * @param userDto
     * @return 1、未登录 2、特需用户 3、普通用户
     */
    private Integer getUserLevel(HttpUserInfoRequest userDto) {
        if (userDto == null) return 1;
        if (this.getSuperUserTag(userDto.getMobile())) return 2;
        return 3;
    }

	@Override
	public HouseInfoResponse getHouseInfoByLayoutId(HouseInfoQueryRequest request) {
        HouseInfoResponse response = new HouseInfoResponse();
		//产品版块筛选条件
		List<BasePropertyResponseVo> basePropertyResponse = homeCardBossProxy.getProductFilterInfo();
		if (CollectionUtils.isEmpty(basePropertyResponse)) {
			return new HouseInfoResponse();
		}
		
		List<ProductFilterInfo> roomList = new ArrayList<ProductFilterInfo>();
		Integer layoutId = request.getLayoutId();
		boolean flag = false;
		Integer hasHouseExt = 0;
		if (null != layoutId && layoutId > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("houseId", layoutId);
			ApartmentInfoVo apartmentInfoVo = homeCardBossProxy.getHouseInfoByApartmentIdNew(map);
			if(apartmentInfoVo == null){
                return new HouseInfoResponse();
            }
            layoutId = apartmentInfoVo.getHouseId();
            if(layOutSwitch){
                hasHouseExt = apartmentInfoVo.getCommitted() == null ? 0 : apartmentInfoVo.getCommitted() ?1:0;
            }
            List<HouseRoomVo> apartmentRoomVoList = apartmentInfoVo.getHouseRoomVos();
			if (CollectionUtils.isNotEmpty(apartmentRoomVoList)) {
				for (HouseRoomVo vo : apartmentRoomVoList) {
					ProductFilterInfo filterInfo = new ProductFilterInfo();
					if(vo.getUsageId()!=null){
                        flag = true;
                        filterInfo.setFilterId(vo.getUsageId());
                        filterInfo.setFilterName(vo.getUsageName());
                        filterInfo.setRoomId(vo.getRoomId());
                        roomList.add(filterInfo);
                    }
				}
			}
		}
		
        for (BasePropertyResponseVo basePropertyResponseVo : basePropertyResponse) {
            ProductFilterInfo filterInfo = new ProductFilterInfo();
            filterInfo.setFilterId(basePropertyResponseVo.getPropertyId());
            filterInfo.setFilterName(basePropertyResponseVo.getPropertyName());
            //属性类型:1系列 2风格 3空间标识 4空间用途
            if (basePropertyResponseVo.getPropertyType() == 4 && !flag) {
        		roomList.add(filterInfo);
            }
        }

        HouseInfoResponse.RoomInfoVo roomInfoVo = new HouseInfoResponse.RoomInfoVo();
        if(CollectionUtils.isNotEmpty(roomList)){
            //目前前端识别同用途空间所选dna依赖与list中的顺序
            Collections.sort(roomList, Comparator.comparing(ProductFilterInfo::getFilterId));
        }
        roomInfoVo.setRoomList(roomList);
        roomInfoVo.setRoomName(HomeCardPraise.ROOM_NAME);

        List<Integer> roomUsageFilterIdList = new ArrayList<>();
        List<HouseInfoResponse.RoomUsageName> roomUsageNameList = new ArrayList<>();
        roomUsageFilterIdList.add(RoomUseEnum.getCode("主卧"));
        roomUsageFilterIdList.add(RoomUseEnum.getCode("次卧"));
        roomUsageFilterIdList.add(RoomUseEnum.getCode("儿童房"));
        roomUsageFilterIdList.add(RoomUseEnum.getCode("书房"));
        roomUsageFilterIdList.add(RoomUseEnum.getCode("老人房"));
        roomUsageFilterIdList.add(RoomUseEnum.getCode("榻榻米房"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(2,"主卧"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(3,"次卧"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(4,"儿童房"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(5,"书房"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(9,"老人房"));
        roomUsageNameList.add(new HouseInfoResponse.RoomUsageName(12,"榻榻米房"));
        HouseInfoResponse.RoomLayOutVo roomLayout = new HouseInfoResponse.RoomLayOutVo();
        roomLayout.setLayOutUrl(layOutWebUrl);
        Integer layoutRoom = 0;
        if(CollectionUtils.isNotEmpty(roomList)){
            for (ProductFilterInfo productFilterInfo : roomList) {
                if(roomUsageFilterIdList.contains(productFilterInfo.getFilterId())){
                    layoutRoom++;
                }
            }
        }

        response.setLayoutRoom(layoutRoom);
        response.setRoomInfo(roomInfoVo);
        response.setRoomUsageFilterIdList(roomUsageFilterIdList);
        response.setHasHouseExt(hasHouseExt);
        response.setRoomUsageNameList(roomUsageNameList);
        response.setNewLayoutId(layoutId);
        response.setRoomLayOut(roomLayout);
        return response;
    }

    @Override
    public DesignerMoreDetailResponse getDesignerDetailById(DesignerMoreDetailRequest request) {
        DesignerRequestVo param = new DesignerRequestVo();
        param.setUserId(request.getDesignerId());
        Integer pageSize = request.getPageSize();
        if (pageSize == null) {
            pageSize = 1;
        }
        param.setPageSize(pageSize);
        Integer pageNo = request.getPageNo();

        if (pageNo == null) {
            pageNo = 10;
        }
        param.setPageNo(pageNo);
        DesignerMoreInfoByDnaResponseVo designerVo = artistProxy.queryDegignerDetailByUserId(param);
        if (null == designerVo) {
            throw new BusinessException(MessageConstant.DDATA_GET_FAILED);
        }

        HttpUserInfoRequest userDto = request.getUserInfo();
        DesignerMoreDetailResponse designer = new DesignerMoreDetailResponse();

        if (designerVo.getUserId() != null) {
            designer.setDesignerId(designerVo.getUserId());
        }

        if (StringUtils.isNotBlank(designerVo.getDesignerName())) {
            designer.setDesignerName(designerVo.getDesignerName());
        }
        if (StringUtils.isNotBlank(designerVo.getDesignerTag())) {
            String designerTag = designerVo.getDesignerTag();
            String designerLables = designerTag.replaceAll(",", "、").replaceAll("，", "、");
            designer.setDesignerLables(designerLables);
        }
        if (StringUtils.isNotBlank(designerVo.getDesignerImage())) {
            designer.setHeadImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(designerVo.getDesignerImage(), 200, 0));
        }
        if (StringUtils.isNotBlank(designerVo.getDesignerBackImage())) {
            designer.setDesingerBackImage(QiniuImageUtils.compressImageAndSamePicTwo(designerVo.getDesignerBackImage(), request.getWidth(), 0));
        }

        if (StringUtils.isNotBlank(designerVo.getDesignerProfile())) {
            List<String> designerDescsList = new ArrayList<>();
            String tags = designerVo.getDesignerProfile();
            String[] designerDescs = tags.split("\n");
            for (String str : designerDescs) {
                if (StringUtils.isNotBlank(str)) {
                    designerDescsList.add(str);
                }
            }
            designer.setDesignerDescs(designerDescsList);
        }

        DesignDealNumsResponseVo num = artistProxy.queryDealNums(param);
        if (num != null && num.getDealNums() != null) {
            designer.setTradeCountSize(num.getDealNums());
        }
        if (designerVo.getTotalSize() != null) {
            designer.setDnaCountSize(designerVo.getTotalSize());
        } else {
            designer.setDnaCountSize(0);
        }

        List<DnaInfoResponse> dnaInfos = designerVo.getDnaInfos();

        if (request.getPageNo() != null && request.getPageSize() != null && CollectionUtils.isNotEmpty(dnaInfos)) {

            List<DnaDetailByDesignerResponse> dnaList = new ArrayList<DnaDetailByDesignerResponse>();
            designer.setDnaList(dnaList);
            List<Integer> dnaIdList = new ArrayList<Integer>();

            for (DnaInfoResponse info : dnaInfos) {
                dnaIdList.add(info.getDnaId());
            }

            // 点赞
            Map<Integer, Integer> dnaMap = this.getDnaMapByDnaIdList(dnaIdList);

            // 评论
            Map<Integer, Integer> commentMap = this.getCommentMapByDnaIdList(dnaIdList,
                    HomeCardPraise.COMMENT_TYPE_DNA, userDto);
            Integer width = request.getWidth();
            if (width == null) {
                width = 750;
            }

            for (DnaInfoResponse info : dnaInfos) {
                DnaDetailByDesignerResponse dna = new DnaDetailByDesignerResponse();

                if (info.getDnaId() != null) {
                    dna.setDnaId(info.getDnaId());
                    dnaList.add(dna);
                }
                if (StringUtils.isNotBlank(info.getStyleName())) {
                    dna.setStyle(info.getStyleName());
                }
                if (StringUtils.isNotBlank(info.getDnaName())) {
                    dna.setName(info.getDnaName());
                }
                if (StringUtils.isNotBlank(info.getDnaHeadImage())) {
                    String headImage = AliImageUtil.imageCompress(info.getDnaHeadImage(), request.getOsType(), width, ImageConstant.SIZE_MIDDLE);
                    dna.setHeadImgUrl(headImage);
                }
                if (StringUtils.isNotBlank(info.getDesignIdea())) {
                    dna.setPraise(info.getDesignIdea());
                }

                // 喜欢的人数统计
                if (dnaMap.get(info.getDnaId()) != null) {
                    dna.setFavoriteNum(dnaMap.get(info.getDnaId()));
                } else {
                    dna.setFavoriteNum(0);
                }

                // 评论数
                if (commentMap.get(info.getDnaId()) != null) {
                    dna.setCommentNum(commentMap.get(info.getDnaId()));
                } else {
                    dna.setCommentNum(0);
                }
            }
        }
        return designer;
    }

    /**
     *
     * 查询是否可以提交设计需求
     * @param request
     * @return
     */
    @Override
    public SubmitDesignResponse querySubmitDesignRequirement(HouseInfoQueryRequest request) {
        SubmitDesignResponse submitDesignResponse = new SubmitDesignResponse();
        submitDesignResponse.setHasPermission(false);
        Integer layoutId = request.getLayoutId();
        if (null != layoutId && layoutId > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("houseId", layoutId);
            ApartmentInfoVo apartmentInfoVo = homeCardBossProxy.getHouseInfoByApartmentIdNew(map);
            if(apartmentInfoVo!=null){
                if(apartmentInfoVo.getCommitted() != null && apartmentInfoVo.getCommitted()){
                    submitDesignResponse.setHasPermission(true);
                    return submitDesignResponse;
                }
//                List<ApartmentRoomVo> apartmentRoomVoList = apartmentInfoVo.getApartmentRoomVos();
//                if (CollectionUtils.isNotEmpty(apartmentRoomVoList)) {
//                    for (ApartmentRoomVo vo : apartmentRoomVoList) {
//                        if(vo.getRoomUsageId()!=null){
//                            submitDesignResponse.setHasPermission(true);
//                            return submitDesignResponse;
//                        }
//                    }
//                }
            }
        }
        return submitDesignResponse;

    }

    /**
     * 空间标识需求提示是否需要更新
     * @param appVersion app版本
     * @param bundleVersions bundle版本号
     * @param osType // 设备类型 1:iPhone客户端，2:Android客户端
     * @return true 需要更新 false不需要更新
     */
    @Override
    public boolean getSpaceMarkMustUpdate(String appVersion,Object bundleVersions,Integer osType){
        if(appVersion == null){
            return false;//app版本号为空 不需要更新
        }
        if(VersionUtil.mustUpdate(appVersion, SPACEMARK_UPDATE_VERSION)){
            return true;//app版本号小于设置版本 需要更新
        }
        if(osType!=null){
            if(osType==1 && !VersionUtil.bundleMustUpdate(bundleVersions,"decoration",99)){//ios端
                return true;
            }
            if(osType==2 && !VersionUtil.bundleMustUpdate(bundleVersions,"decoration",91)){//android端
                return true;
            }
        }
        return false;
    }

}
