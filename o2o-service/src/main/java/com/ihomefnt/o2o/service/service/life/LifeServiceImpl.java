package com.ihomefnt.o2o.service.service.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.life.dto.*;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.request.CategoryRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.response.ArticleDetailResponse;
import com.ihomefnt.o2o.intf.domain.life.vo.response.CategoryDetailListResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.StaticResourceDto;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.UserStoryDataResponse;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.life.LifeProxy;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.life.LifeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class LifeServiceImpl implements LifeService {

    @Autowired
    UserProxy userProxy;

    @Autowired
    private LifeProxy lifeProxy;

    @Autowired
    LogProxy logProxy;

    @NacosValue(value = "${USER_STORY_DATA}", autoRefreshed = true)
    private String USER_STORY_DATA;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public LifeHomePage getLifeHomePage(HttpBaseRequest request) {
        LifeHomePage lifeHomePage;
        String homeData = redisTemplate.opsForValue().get(RedisKey.LifePlate.LIFE_HOME_PAGE_DATA_CACHE);
        if (StringUtils.isNotBlank(homeData)) {
            lifeHomePage = JSON.parseObject(homeData, LifeHomePage.class);
        } else {
            lifeHomePage = lifeProxy.getLifeHomePage(request);
            if (lifeHomePage != null) {
                redisTemplate.opsForValue().set(RedisKey.LifePlate.LIFE_HOME_PAGE_DATA_CACHE, JSON.toJSONString(lifeHomePage), 1, TimeUnit.HOURS);
            }
        }
        if (VersionUtil.mustUpdate(request.getAppVersion(), "6.0.0")) {
            lifeHomePage.getCategoryList().removeIf(categoryDto -> categoryDto.getCategoryId() == 6 || categoryDto.getCategoryId() == 7);
        } else {
            lifeHomePage.getCategoryList().removeIf(categoryDto -> categoryDto.getCategoryId() == 4);
            List<CommonListDto> collect = lifeHomePage.getCommonArticleList().stream().filter(commonListDto -> commonListDto.getCategoryId().equals(6)).collect(Collectors.toList());
            lifeHomePage.getCommonArticleList().removeIf(commonListDto -> commonListDto.getCategoryId().equals(4) || commonListDto.getCategoryId().equals(6) || commonListDto.getCategoryId().equals(7));
            if (CollectionUtils.isNotEmpty(collect)) {
                CommonListDto commonListDto = collect.get(0);
                UserStoryDataResponse userStoryDataResponse = JSON.parseObject(USER_STORY_DATA, UserStoryDataResponse.class);
                List<StaticResourceDto> resourceList = userStoryDataResponse.getResourceList();
                List<ArticleDto> articleDtoList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    StaticResourceDto staticResourceDto = resourceList.get(i);
                    ArticleDto articleDto = new ArticleDto();
                    articleDto.setCoverUrl(AliImageUtil.imageCompress(staticResourceDto.getCoverImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    articleDto.setTitle(staticResourceDto.getResourceName());
                    articleDto.setCategoryId(6);
                    articleDto.setType(1);
                    articleDto.setVideoUrl(staticResourceDto.getResourceUrl());
                    articleDtoList.add(articleDto);
                }
                commonListDto.setArticleList(articleDtoList);
                lifeHomePage.getCommonArticleList().add(commonListDto);
            }
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        Integer userId = null;
        String mobile = null;
        if (userDto != null) { //记录日志
            userId = userDto.getId();
            mobile = userDto.getMobile();
            // 增加日志:晒家
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("deviceToken", request.getDeviceToken());
            params.put("mobile", mobile);
            params.put("visitType", LogEnum.LOG_HOME_SHAREORDER.getCode());
            params.put("action", LogEnum.LOG_HOME_SHAREORDER.getMsg());
            params.put("userId", userId);
            params.put("appVersion", request.getAppVersion());
            params.put("osType", request.getOsType());
            params.put("pValue", request.getParterValue());
            params.put("cityCode", request.getCityCode());
            logProxy.addLog(params);
        }
        return lifeHomePage;
    }


    /**
     * 类目详情页
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public CategoryDetailListResponse getCategoryInfoList(CategoryRequestVo request) {
        CategoryDetailListResponse categoryDetail = new CategoryDetailListResponse();
        categoryDetail.setPageSize(request.getLimit());
        categoryDetail.setPageNo(request.getPage());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", request.getCategoryId());
        CategoryDto category = lifeProxy.getCategoryById(params);
        if (category == null) {
            throw new BusinessException("类目获取异常");
        }
        String imageUrl = category.getImageUrl();
        categoryDetail.setCategoryImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(imageUrl, request.getWidth(), -1));//详情页情景图
        PageResponse<ArticleDto> page = lifeProxy.getArticleList(request);
        List<ArticleDto> list = JsonUtils.json2list(JsonUtils.obj2json(page.getList()), ArticleDto.class);
        List<ArticleDto> listRe = new ArrayList<ArticleDto>();
        for (ArticleDto article : list) {
            article.setCoverUrl(QiniuImageUtils.compressImageAndSamePicTwo(article.getCoverUrl(), request.getWidth(), -1));
            article.setHeadUrl(QiniuImageUtils.compressImageAndSamePicTwo(article.getHeadUrl(), 100, -1));
            listRe.add(article);
        }

        categoryDetail.setObj(listRe);
        categoryDetail.setTotalCount(page.getTotalCount());
        categoryDetail.setTotalPage(page.getTotalPage());
        return categoryDetail;
    }

    @Override
    public ArticleDetailResponse getArticleInfo(ArticleRequestVo request) {
        if (request != null && request.getArticleId() != null) {
            ArticleDetailResponse detailResponse = new ArticleDetailResponse();
            Integer articleId = request.getArticleId();
            Integer width = 0;
            if (request.getWidth() != null && request.getWidth() > 0) {
                width = request.getWidth();
            }
            HttpUserInfoRequest userDto = request.getUserInfo();
            ArticleDto article = lifeProxy.getArticleById(request);
            if (article != null) {
                detailResponse.setArticleId(article.getArticleId());
                detailResponse.setSummary(article.getSummary());
                detailResponse.setTitle(article.getTitle());
                detailResponse.setCommentCount(article.getCommentCount());
                AuthorDto author = article.getAuthor();
                detailResponse.setAuthorId(author.getAuthorId());
                detailResponse.setAuthorName(author.getPenName());
                detailResponse.setAuthorIntroduce(author.getIntroduce());

                // 头像处理
                if (StringUtils.isNotBlank(author.getHeadUrl())) {
                    detailResponse.setAuthorHeadUrl(QiniuImageUtils.compressImageAndDiffPic(author.getHeadUrl(),
                            100, 100));
                } else {
                    detailResponse.setAuthorHeadUrl(StaticResourceConstants.USER_DEFAULT_HEAD_IMAGE);
                }

                detailResponse.setCoverUrl(article.getCoverUrl());
                detailResponse.setContentType(article.getContentType());
                // 音频地址
                if (StringUtils.isNotBlank(article.getAudioUrl())) {
                    detailResponse.setAudioUrl(article.getAudioUrl());
                } else {
                    detailResponse.setAudioUrl("");
                }
                detailResponse.setContent(QiniuImageUtils.compressdocumentBodyImage(article.getContent(), width, -1));
                detailResponse.setStatusTime(article.getStatusTime());//发布时间
                detailResponse.setCreateTime(article.getCreateTime());//创建时间
                detailResponse.setPraiseCount(article.getPraiseCount());


                // 用户已登录的情况下，获取用户是否点赞的信息
                Integer userId = null;
                if (null != userDto && null != userDto.getId()) {
                    userId = userDto.getId();
                    List<LifePraiseDto> lifePraiseList = lifeProxy
                            .getLifePraiseListByUserId(userId);
                    Set<Integer> praisedArticleId = new HashSet<>();
                    if (!CollectionUtils.isEmpty(lifePraiseList)) {
                        for (LifePraiseDto lifePraise : lifePraiseList) {
                            praisedArticleId.add(lifePraise.getArticleId());
                        }
                    }
                    // 用户点赞过该记录
                    if (praisedArticleId.contains(articleId)) {
                        detailResponse.setPraised("1");
                    }
                }
            }
            return detailResponse;
        } else {
            return null;
        }
    }

    /**
     * 增加浏览次数
     *
     * @param request
     * @return
     */
    @Override
    public Integer addBrowse(ArticleRequestVo request) {
        return lifeProxy.addBrowse(request);
    }

    /**
     * 增加点赞
     *
     * @param request
     * @return
     */
    @Override
    public Integer addArticlePraise(ArticleRequestVo request) {
        LifePraiseDto lifePraise = new LifePraiseDto();
        BeanUtils.copyProperties(request, lifePraise);
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto || null == userDto.getId()) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        Integer userId = userDto.getId();
        int articleId = request.getArticleId();
        int count = lifeProxy.getPraiseCount(userId, articleId);
        // 用户已点过赞
        if (count > 0) {
            throw new BusinessException(HttpReturnCode.ADD_PRAISE_ALREADY, "已点过赞");
        }
        lifePraise.setUserId(userId);
        lifePraise.setArticleId(articleId);
        // 保存点赞信息
        lifeProxy.addLifePraise(lifePraise);
        return lifeProxy.addPraise(request);
    }

    /**
     * 增加转发数
     *
     * @param request
     * @return
     */
    @Override
    public Integer addForward(ArticleRequestVo request) {
        return lifeProxy.addForward(request);
    }

}
