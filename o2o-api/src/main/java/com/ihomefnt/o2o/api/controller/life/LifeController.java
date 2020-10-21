package com.ihomefnt.o2o.api.controller.life;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.life.dto.LifeHomePage;
import com.ihomefnt.o2o.intf.domain.life.vo.request.CategoryRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.response.ArticleDetailResponse;
import com.ihomefnt.o2o.intf.domain.life.vo.response.CategoryDetailListResponse;
import com.ihomefnt.o2o.intf.service.life.LifeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "【生活板块API】")
@RestController
@RequestMapping("/life")
public class LifeController {

	@Autowired
	private LifeService lifeService;

	@ApiOperation(value = "首页内容信息", notes = "类目列表、banner区列表、推荐文章列表、通用文章列表")
	@RequestMapping(value = "/getLifeHomePage", method = RequestMethod.POST)
	public HttpBaseResponse<LifeHomePage> getLifeHomePage(@RequestBody HttpBaseRequest request) {
		LifeHomePage obj = lifeService.getLifeHomePage(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "类目详情页")
	@RequestMapping(value = "/getCategoryInfoList", method = RequestMethod.POST)
	public HttpBaseResponse<CategoryDetailListResponse> getCategoryInfoList(@RequestBody CategoryRequestVo request) {
		if(null == request || request.getCategoryId() == null) {
			return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
		}
		CategoryDetailListResponse obj =lifeService.getCategoryInfoList(request);
		return HttpBaseResponse.success(obj);
	}

	
	@ApiOperation(value = "文章详情页面")
	@RequestMapping(value = "/getArticleInfo", method = RequestMethod.POST)
	public HttpBaseResponse<ArticleDetailResponse> getArticleInfo(@RequestBody ArticleRequestVo request) {
		if (request == null || request.getArticleId()==null) {
			return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
		}
			
		ArticleDetailResponse obj =lifeService.getArticleInfo(request);
		return HttpBaseResponse.success(obj);
	}

	
	@ApiOperation(value = "浏览次数增加")
	@RequestMapping(value = "/addBrowse", method = RequestMethod.POST)
	public HttpBaseResponse<Integer> addBrowse(@RequestBody ArticleRequestVo request) {
		Integer obj = lifeService.addBrowse(request);
		return HttpBaseResponse.success(obj, "浏览次数增加");
	}

	
	@ApiOperation(value = "新增点赞")
	@RequestMapping(value = "/addArticlePraise", method = RequestMethod.POST)
	public HttpBaseResponse<Integer> addArticlePraise(@RequestBody ArticleRequestVo request) {
		Integer obj =lifeService.addArticlePraise(request);
		return HttpBaseResponse.success(obj, "点赞成功");
	}

	
	@ApiOperation(value = "添加转发数")
	@RequestMapping(value = "/addForward", method = RequestMethod.POST)
	public HttpBaseResponse<Integer> addForward(@RequestBody ArticleRequestVo request) {
		Integer obj =lifeService.addForward(request);
		return HttpBaseResponse.success(obj, "转发次数增加");
	}

	
}
