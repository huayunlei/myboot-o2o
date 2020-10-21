package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.designer.dto.*;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicBrowseRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicListRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerSuitListRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.response.*;
import com.ihomefnt.o2o.intf.domain.imagesview.dto.ImagesView;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.WeiXinArticle;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.designer.DesignerService;
import com.ihomefnt.o2o.intf.service.designer.DesignerViewService;
import com.ihomefnt.o2o.intf.service.imagesview.ImagesViewService;
import com.ihomefnt.o2o.intf.service.inspiration.ArticleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="M站设计师API",description="M站设计师接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiDesignController {

    private static final Logger LOG = LoggerFactory.getLogger(MapiDesignController.class);

    public static Integer count = 18;
    private static final Integer SCRENNWIDTH = 640;
    @Autowired
    DesignerService designerService;
    @Autowired
    DesignerViewService designerViewService;
    @Autowired
    ImagesViewService imagesViewService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserProxy userProxy;

    /**
     * 设计首页
     */
    @RequestMapping(value = "/design", method = RequestMethod.GET)
    public String home(
            @RequestParam(value = "screenWidth", required = false) Integer screenWidth, Model model,
            HttpServletRequest request) {

        HttpSession httpSession = request.getSession();
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        List<DesignerPic> designerPics = designerService.queryDesignerPic(map,
                screenWidth);
        DesignerPicsResponse designerPicResponse = new DesignerPicsResponse();
        designerPicResponse.setDesignerPicList(designerPics);
        List<Article> articles = articleService.queryAllArticle(4);
        List<WeiXinArticle> weiXinArticles = new ArrayList<WeiXinArticle>();
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
                String headFigure = QiniuImageUtils.compressImage(article.getHeadFigure(), 640, 360);
                article.setHeadFigure(headFigure);
                WeiXinArticle weiXinArticle = new WeiXinArticle(article);
                weiXinArticles.add(weiXinArticle);
            }
        }
        DesignHomeResponse designHomeResponse = new DesignHomeResponse();
        designHomeResponse.setArticles(weiXinArticles);
        designHomeResponse.setDesignerPicsResponse(designerPicResponse);
        baseResponse.setObj(designHomeResponse);
        if (screenWidth != null) {
            httpSession.setAttribute("screenWidth", screenWidth);
        }
        model.addAttribute("baseResponse", baseResponse);
        return "design/homecard.ftl";
    }

    @RequestMapping(value = "/design", method = RequestMethod.POST)
    public HttpBaseResponse<DesignHomeResponse> home(@RequestParam(value = "screenWidth", required = false) Integer screenWidth, HttpServletRequest request) {
        if (screenWidth == null || screenWidth < 0) {
            screenWidth = SCRENNWIDTH;
        }
        HttpSession httpSession = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        List<DesignerPic> designerPics = designerService.queryDesignerPic(map,
                screenWidth);
        DesignerPicsResponse designerPicResponse = new DesignerPicsResponse();
        designerPicResponse.setDesignerPicList(designerPics);
        List<Article> articles = articleService.queryAllArticle(4);
        List<WeiXinArticle> weiXinArticles = new ArrayList<WeiXinArticle>();
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
                String headFigure = QiniuImageUtils.compressImage(article.getHeadFigure(), 640, 360);
                article.setHeadFigure(headFigure);
                WeiXinArticle weiXinArticle = new WeiXinArticle(article);
                weiXinArticles.add(weiXinArticle);
            }
        }
        DesignHomeResponse designHomeResponse = new DesignHomeResponse();
        designHomeResponse.setArticles(weiXinArticles);
        designHomeResponse.setDesignerPicsResponse(designerPicResponse);

        httpSession.setAttribute("screenWidth", screenWidth);

        return HttpBaseResponse.success(designHomeResponse);
    }

    @RequestMapping(value = "/photo/{idtDesignerPic}", method = RequestMethod.POST)
    public HttpBaseResponse<HttpDesignerPicResponse> bigPicture(@PathVariable String idtDesignerPic,
                                                                @Json DesignerPicRequest designerPicRequest, HttpServletRequest request) {
        if (StringUtil.isNullOrEmpty(idtDesignerPic)) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        Integer screenWidth = null;
        if(null != designerPicRequest && null != designerPicRequest.getWidth()) {
            screenWidth = designerPicRequest.getWidth();
        }
        if (screenWidth == null || screenWidth < 0) {
            screenWidth = SCRENNWIDTH;
        }
        try {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("screenWidth", screenWidth);
        } catch (Exception e) {
            LOG.error("session生成异常===============", e);
        }

        ImagesView imagesView = new ImagesView();
        imagesView.setImageId(Long.parseLong(idtDesignerPic));

        if (null != designerPicRequest && designerPicRequest.getUserInfo() != null) {
            imagesView.setUserId(designerPicRequest.getUserInfo().getId().longValue());
        }
        imagesViewService.addImagesView(imagesView);
        HttpDesignerPicResponse designerPicsResponse = null;
        if (null == designerPicRequest) {
            designerPicRequest = new DesignerPicRequest();
        }
        designerPicRequest
                .setIdtDesignerPic(Long.parseLong(idtDesignerPic));
        List<DesignerPic> designerPics = designerService.queryDesignerPicsById(
                designerPicRequest, screenWidth);

        designerPicsResponse = new HttpDesignerPicResponse(designerPics, getUserList(designerPics));

        return HttpBaseResponse.success(designerPicsResponse);
    }

    private List<UserDo> getUserList(List<DesignerPic> designerPics) {
        List<UserDo> userList = new ArrayList<UserDo>();
        if (designerPics != null && !designerPics.isEmpty()) {
            for (int i = 0, l = designerPics.size(); i < l; i++) {
                UserDo user = new UserDo();
                UserDto userDtoPic = userProxy.getUserById(designerPics.get(i).getOwnerId().intValue());
                if (null != userDtoPic) {
                    user.setuId(userDtoPic.getId().longValue());
                    MemberDto member = userDtoPic.getMember();
                    if (null != member) {
                        user.setuImg(member.getuImg());
                        user.setNickName(member.getNickName());
                    }
                }

                if (user != null && (user.getuImg() == null || user.getuImg().equals(""))) {
                    user.setuImg(StaticResourceConstants.USER_AVATAR);
                }
                userList.add(user);
            }
        }
        return userList;
    }


    @RequestMapping(value = "/design/slideBigPic", method = RequestMethod.POST)
    public HttpBaseResponse<DesignerPicResponse> slideBigPic(
            @Json DesignerPicBrowseRequest designerPicRes,
            HttpSession httpSession) {
        if (designerPicRes == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        DesignerPicResponse designerPicResponse = null;
        ImagesView imagesView = new ImagesView();
        imagesView.setImageId(designerPicRes.getIdtDesignerPic());
        String sessionId = httpSession.getId();
        Object objeToken = httpSession.getAttribute(sessionId);
        if (objeToken != null) {
            UserDto userDto = userProxy.getUserByToken(objeToken.toString());
            imagesView.setUserId(userDto.getId().longValue());
        }
        imagesViewService.addImagesView(imagesView);
        if (designerPicRes.getIdtDesignerPic() != null) {
            Integer screenWidth = 0;
            Object screenWidthObj = httpSession.getAttribute("screenWidth");
            if (screenWidthObj != null) {
                screenWidth = Integer.parseInt(screenWidthObj.toString());
            }
            List<DesignerPic> designerPics = designerService.queryDesignerPic(
                    designerPicRes, screenWidth);
            UserDo user = new UserDo();

            if (designerPics != null && !designerPics.isEmpty()) {
                UserDto userDtoPic = userProxy.getUserById(designerPics.get(0).getOwnerId().intValue());
                if (null != userDtoPic) {
                    user.setuId(userDtoPic.getId().longValue());
                    MemberDto member = userDtoPic.getMember();
                    if (null != member) {
                        user.setuImg(member.getuImg());
                        user.setNickName(member.getNickName());
                    }
                }
                designerPicResponse = new DesignerPicResponse(
                        designerPics.get(0), user);
            }
        }

        return HttpBaseResponse.success(designerPicResponse);
    }


    @RequestMapping(value = "/designer/{designerId}", method = RequestMethod.POST)
    public HttpBaseResponse<DesignerHomeResponse> designerHome(@PathVariable Long designerId,
                                                               @RequestParam(value = "screenWidth", required = false) Integer screenWidth,
                                                               HttpSession httpSession) {

        if (screenWidth == null || screenWidth < 0) {
            screenWidth = SCRENNWIDTH;
        }

        /**
         * 1. add designer view statistic data to DB
         */
        DesignerView designerView = new DesignerView();
        designerView.setDesignerId(designerId);
        String sessionId = httpSession.getId();
        Object objToken = httpSession.getAttribute(sessionId);
        if (objToken != null) {
            UserDto userDto = userProxy.getUserByToken(objToken.toString());
            if (null != userDto) {
                designerView.setUserId(userDto.getId().longValue());
            }
        }
        designerViewService.addDesignerView(designerView);

        /**
         * edit response data
         */
        DesignerHomeResponse homeResponse = new DesignerHomeResponse();
        /**
         * load user information from data base;
         */
        UserDto designUser = userProxy.getUserById(designerId.intValue());
        if (designUser != null) {
            DesignerModel designerModel = designerService.loadDesignerInfo(designUser.getMobile());
            if (designerModel != null) {
                homeResponse.setMobile(designerModel.getMobile());
                homeResponse.setAvatar(designerModel.getAvatar());
                homeResponse.setDesignerName(designerModel.getName());
                homeResponse.setConcept(designerModel.getBrief());
                homeResponse.setDescription(designerModel.getDesignCase());
            }
        }

        homeResponse.setDesignerId(designerId);

        /**
         * load designer pic count, designer case count, designer bg picture
         *
         */
        homeResponse.setCaseCount(designerService.loadDesignerSuitCount(designerId));
        homeResponse.setPicCount(designerService.loadDesignerPicCount(designerId));
        homeResponse.setBgPic(designerService.loadDesignerSuitImg(designerId));
        /**
         * load suit list
         */
        List<DesignerSuit> suitList = designerService.loadDesignerSuit(designerId, 1, 1);
        homeResponse.setSuitList(suitList);
        /**
         * load picture list
         */
        List<DesignerPicture> picList = designerService.loadDesignerPicList(designerId, 1, 3);

        designerService.resizeDesignerPicture(picList, screenWidth);
        homeResponse.setPicList(picList);

        homeResponse.setEnable(true);
        homeResponse.setTitle1(homeResponse.getDesignerName() + "-艾佳设计师的主页");
        homeResponse.setIcon1(homeResponse.getAvatar());
        homeResponse.setDesc(homeResponse.getDescription());

        return HttpBaseResponse.success(homeResponse);
    }

    @RequestMapping(value = "/designer/pictures", method = RequestMethod.POST)
    public HttpBaseResponse<List<DesignerPicture>> ajaxFetchDesignerPictures(@Json DesignerPicListRequest request, Model model) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        try {
            List<DesignerPicture> picList = designerService.loadDesignerPicList(request.getDesignerId(), request.getPageNo(), request.getPageSize());
            return HttpBaseResponse.success(picList);
        } catch (Exception e) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
    }

    @RequestMapping(value = "/designer/suits", method = RequestMethod.POST)
    public HttpBaseResponse<List<DesignerSuit>> ajaxFetchDesignerSuits(@Json DesignerSuitListRequest request, Model model) {
        if (request == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        try {

            List<DesignerSuit> suitList = designerService.loadDesignerSuit(request.getDesignerId(), request.getPageNo(), request.getPageSize());
            return HttpBaseResponse.success(suitList);
        } catch (Exception e) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
    }

    @RequestMapping(value = "/design/ajaxLoadDesignerPic", method = RequestMethod.POST)
    public HttpBaseResponse<DesignerPicsResponse> ajaxLoadDesignerPic(
            @Json DesignerPicRequest designerPicRequest, HttpSession httpSession) {
        if (designerPicRequest == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setCode(HttpResponseCode.FAILED);
        baseResponse.setObj(null);
        baseResponse.setExt(null);

        Map<String, Object> map = new HashMap<String, Object>();
        if (designerPicRequest.getDesignerId() != null) {
            map.put("designerId", designerPicRequest.getDesignerId());
        }
        if (designerPicRequest.getCount() != null
                && designerPicRequest.getCount() > 0) {
            map.put("count", designerPicRequest.getCount());
        } else {
            map.put("count", count);
        }
        Integer screenWidth = 0;
        Object screenWidthObj = httpSession.getAttribute("screenWidth");
        if (screenWidthObj != null) {
            screenWidth = Integer.parseInt(screenWidthObj.toString());
        }
        map.put("picId", designerPicRequest.getIdtDesignerPic());
        List<DesignerPic> designerPics = designerService.queryDesignerPic(map, screenWidth);
        DesignerPicsResponse designerPicResponse = new DesignerPicsResponse();
        designerPicResponse.setDesignerPicList(designerPics);

        return HttpBaseResponse.success(designerPicResponse);
    }

}
