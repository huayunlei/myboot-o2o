package com.ihomefnt.o2o.service.proxy.life;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.life.dto.ArticleDto;
import com.ihomefnt.o2o.intf.domain.life.dto.BannerDto;
import com.ihomefnt.o2o.intf.domain.life.dto.CategoryDto;
import com.ihomefnt.o2o.intf.domain.life.dto.CommonListDto;
import com.ihomefnt.o2o.intf.domain.life.dto.LifeHomePage;
import com.ihomefnt.o2o.intf.domain.life.dto.LifePraiseDto;
import com.ihomefnt.o2o.intf.domain.life.vo.request.CategoryRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.proxy.life.LifeProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Service
public class LifeProxyImpl implements LifeProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(LifeProxyImpl.class);

    /**
     * 获取点赞情况
     * @param userId
     * @param articleId
     * @return
     */
    @Override
    public Integer getPraiseCount(Integer userId, Integer articleId) {
        if (userId == null || articleId == null) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("articleId", articleId);
        
        HttpBaseResponse<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_PRAISE_COUNT, params, 
            		new TypeReference<HttpBaseResponse<Integer>>() {
					});
        } catch (Exception e) {
            return 0;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return 0;
        }
        return responseVo.getObj();
    }

    /**
     * 新增点赞
     * @param lifePraise
     */
    @Override
    public void addLifePraise(LifePraiseDto lifePraise) {
        if (lifePraise == null) {
            return;
        }
        HttpBaseResponse<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(WcmWebServiceNameConstants.LIFE_ADD_LIFE_PRAISE, lifePraise, HttpBaseResponse.class);
        } catch (Exception e) {
            return;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return;
        }
    }

    /**
     * 点赞数+1，并返回目前点赞数
     * @param request
     * @return
     */
    @Override
    public Integer addPraise(ArticleRequestVo request) {
        if (request == null) {
            return -1;
        }
        HttpBaseResponse<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_ADD_PRAISE, request, 
            		new TypeReference<HttpBaseResponse<Integer>>() {
			});
        } catch (Exception e) {
            return -2;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return -3;
        }
        return responseVo.getObj();
    }

    /**
     * 浏览次数+1，并返回当前浏览数
     * @param request
     * @return
     */
    @Override
    public Integer addBrowse(ArticleRequestVo request) {
        if (request == null) {
            return -1;
        }
        HttpBaseResponse<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_ADD_BROWSE, request, 
            		new TypeReference<HttpBaseResponse<Integer>>() {
			});
        } catch (Exception e) {
            return -2;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return -3;
        }
        return responseVo.getObj();
    }

    /**
     * 转发次数+1，并返回当前转发数
     * @param request
     * @return
     */
    @Override
    public Integer addForward(ArticleRequestVo request) {
        if (request == null) {
            return -1;
        }
        HttpBaseResponse<Integer> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_ADD_FORWARD, request, 
            		new TypeReference<HttpBaseResponse<Integer>>() {
			});
        } catch (Exception e) {
            LOG.error("wcm-web.life.addForward ERROR:{}", e.getMessage());
            return -2;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return -3;
        }
        return responseVo.getObj();
    }

    @Override
    public ArticleDto getArticleById(ArticleRequestVo request) {
        if (request == null) {
            return null;
        }
        HttpBaseResponse<ArticleDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_ARTICLE_BY_ID, request, 
            		new TypeReference<HttpBaseResponse<ArticleDto>>() {
			});
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null || responseVo.getObj() == null) {
            return null;
        }
        ArticleDto result = responseVo.getObj();
        result.setCoverUrl(QiniuImageUtils.compressImageAndSamePicTwo(result.getCoverUrl(), request.getWidth(), -1));
        result.setHeadUrl(QiniuImageUtils.compressImageAndSamePicTwo(result.getHeadUrl(), 100, -1));
        return result;
    }

    @Override
    public List<LifePraiseDto> getLifePraiseListByUserId(Integer userId) {
        if (userId <= 0) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        HttpBaseResponse<List<LifePraiseDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_LIFE_PRAISE_LIST_BY_USER_ID, params,
            		new TypeReference<HttpBaseResponse<List<LifePraiseDto>>>() {
			});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return null;
        }
        return responseVo.getObj();
    }

    @Override
    public CategoryDto getCategoryById(Map <String, Object> params) {
        if (params == null) {
            return null;
        }
        HttpBaseResponse<CategoryDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_CATEGORY_BY_ID, params, 
            		new TypeReference<HttpBaseResponse<CategoryDto>>() {
			});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return null;
        }
        return responseVo.getObj();
    }

    @Override
    public PageResponse<ArticleDto> getArticleList(CategoryRequestVo request) {
        if (request == null) {
            return null;
        }
        HttpBaseResponse<PageResponse<ArticleDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_ARTICLE_LIST, request, 
            		new TypeReference<HttpBaseResponse<PageResponse<ArticleDto>>>() {
			});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return null;
        }
        return responseVo.getObj();
    }

    @Override
    @Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
    public LifeHomePage getLifeHomePage(HttpBaseRequest params) {
        if (params ==null) {
            return null;
        }
        HttpBaseResponse<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_LIFE_HOME_PAGE, params,
                    HttpBaseResponse.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || responseVo.getObj() == null) {
            return null;
        }
        Map<String, Object> map=JsonUtils.json2map(JsonUtils.obj2json(responseVo.getObj()));
        Integer width = params.getWidth();//phone width
        return compressHomePageImage(map,width);
    }

    public LifeHomePage compressHomePageImage(Map<String, Object> map,Integer width){
        List<CategoryDto> category = JsonUtils.json2list(JsonUtils.obj2json(map.get("categoryList")),CategoryDto.class);
        List<BannerDto> banner = JsonUtils.json2list(JsonUtils.obj2json(map.get("bannerList")),BannerDto.class);
        List<ArticleDto> recommend = JsonUtils.json2list(JsonUtils.obj2json(map.get("recommendList")),ArticleDto.class);
        List<CommonListDto> commonArticle = JsonUtils.json2list(JsonUtils.obj2json(map.get("commonArticleList")),CommonListDto.class);
        List<CategoryDto> categoryRe=new ArrayList <CategoryDto>();
        List<BannerDto> bannerRe=new ArrayList <BannerDto>();
        List<ArticleDto> recommendRe=new ArrayList <ArticleDto>();
        List<CommonListDto> commonArticleRe=new ArrayList <CommonListDto>();
        if(category!=null){
            for(CategoryDto categoryDetail:category){//图片处理
                categoryDetail.setIconUrl(QiniuImageUtils.compressImageAndSamePicTwo(categoryDetail.getIconUrl(), 90, -1));
                categoryRe.add(categoryDetail);
            }
        }
        if(banner!=null){
            for(BannerDto bannerDetail:banner){
                bannerDetail.setCoverUrl(QiniuImageUtils.compressImageAndSamePicTwo(bannerDetail.getCoverUrl(), width, -1));
                bannerRe.add(bannerDetail);
            }
        }
        if(recommend!=null){
            for(ArticleDto recommendDetail:recommend){
                recommendDetail.setCoverUrl(QiniuImageUtils.compressImageAndSamePicTwo(recommendDetail.getCoverUrl(), width, -1));
                recommendDetail.setHeadUrl(QiniuImageUtils.compressImageAndSamePicTwo(recommendDetail.getHeadUrl(), 100, -1));
                recommendRe.add(recommendDetail);
            }
        }

        if(commonArticle!=null){
            for(CommonListDto commonDetail:commonArticle){
                List<ArticleDto> articleList = commonDetail.getArticleList();
                List<ArticleDto> articleListRe = new ArrayList <ArticleDto>();
                if(articleList!=null) {
                    for (ArticleDto articleDetail : articleList) {
                        articleDetail.setCoverUrl(QiniuImageUtils.compressImageAndSamePicTwo(articleDetail.getCoverUrl(), width, -1));
                        articleDetail.setHeadUrl(QiniuImageUtils.compressImageAndSamePicTwo(articleDetail.getHeadUrl(), 100, -1));
                        articleListRe.add(articleDetail);
                    }
                }
                commonDetail.setArticleList(articleListRe);
                commonArticleRe.add(commonDetail);
            }
        }

        LifeHomePage lifeHomePage = new LifeHomePage();
        lifeHomePage.setRecommendList(recommendRe);
        lifeHomePage.setBannerList(bannerRe);
        lifeHomePage.setCategoryList(categoryRe);
        lifeHomePage.setCommonArticleList(commonArticleRe);
        return lifeHomePage;
    }
}
