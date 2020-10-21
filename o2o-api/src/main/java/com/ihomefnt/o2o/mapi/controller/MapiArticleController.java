/**
 * 
 */
package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.WeiXinArticle;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpArticleRequest;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.service.inspiration.ArticleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
@Api(value="M站文章老API",description="M站 文章老接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi/article")
public class MapiArticleController {
	
    private static final Logger LOG = LoggerFactory.getLogger(MapiArticleController.class);
    
    @Autowired
    ArticleService articleService;

//    /**
//     * article首页
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String homecard(Model model) {
//        LOG.info("homecard() interface start");
//        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        baseResponse.setCode(HttpResponseCode.FAILED);
//        baseResponse.setObj(null);
//        baseResponse.setExt(null);
//        baseResponse.setCode(HttpResponseCode.SUCCESS);
//        List<Article> articles = articleService.queryAllArticle(null);
//        List<WeiXinArticle> weiXinArticles = new ArrayList<WeiXinArticle>();
//        if (articles != null && !articles.isEmpty()) {
//            for (Article article : articles) {
//                String headFigure = article.getHeadFigure() + "?imageView2/1/w/160/h/120";
//                article.setHeadFigure(headFigure);
//                WeiXinArticle weiXinArticle = new WeiXinArticle(article);
//                weiXinArticles.add(weiXinArticle);
//            }
//        }
//        baseResponse.setObj(weiXinArticles);
//        model.addAttribute("baseResponse", baseResponse);
//        return "article/list.ftl";
//    }

    /**
     * article首页
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> home() {
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        List<Article> articles = articleService.queryAllArticle(null);
        List<WeiXinArticle> weiXinArticles = new ArrayList<WeiXinArticle>();
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
//                String headFigure = article.getHeadFigure() + "?imageView2/1/w/160/h/120";
            	String headFigure = QiniuImageUtils.compressImage(article.getHeadFigure(), 160, 120);
                article.setHeadFigure(headFigure);
                WeiXinArticle weiXinArticle = new WeiXinArticle(article);
                weiXinArticles.add(weiXinArticle);
            }
        }
        baseResponse.setObj(weiXinArticles);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/addArticleRead", method = RequestMethod.POST)
    public ResponseEntity<HttpBaseResponse> addArticleRead(@Json HttpArticleRequest article) {
		if (article != null) {
			LOG.info("MapiArticleController addArticleRead params:{}", JsonUtils.obj2json(article));
		}
        HttpBaseResponse baseResponse = new HttpBaseResponse();
//        Boolean addSuccess = articleService.addArticleRead(article.getArticleId());
//        if (addSuccess) {
//            baseResponse.setCode(HttpResponseCode.SUCCESS);
//            baseResponse.setObj(MessageConstant.SUCCESS);
//            baseResponse.setExt(null);
//        } else {
//            baseResponse.setCode(HttpResponseCode.FAILED);
//            baseResponse.setObj(MessageConstant.FAILED);
//            baseResponse.setExt(null);
//        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
}
