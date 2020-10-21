package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;
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

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by piweiwen on 15-1-26.
 */
@Api(value="M站首页API",description="M站首页接口",tags = "【M-API】")
@Controller
@RequestMapping("/mapi")
public class MapiDictionaryController {

	private static final Logger LOG = LoggerFactory.getLogger(MapiDictionaryController.class);
	
	@Autowired
	ArticleService articleService;
	/**
	 * 装修知识的首页
	 */
	@RequestMapping(value = "/knowledge", method = RequestMethod.GET)
	public String home(Model model) {    	

		return "dictionary/homecard.ftl";
	}

	/**
	 * 家居设计列表页
	 */
	@RequestMapping(value = "knowledge/design", method = RequestMethod.GET)
	public String list1(Model model) {

		HttpBaseResponse baseResponse = new HttpBaseResponse();
		List<Article> articles=articleService.queryArticle("创意家居");
		if(articles!=null&&!articles.isEmpty()){
			baseResponse.setCode(HttpResponseCode.SUCCESS);
	        baseResponse.setObj(articles);
	        baseResponse.setExt(null);
		}else{
			baseResponse.setCode(HttpResponseCode.SUCCESS);
	        baseResponse.setObj(null);
	        baseResponse.setExt(null);
		}
		model.addAttribute("baseResponse", baseResponse);
		return "dictionary/design.ftl";
	}
	
	/**
	 * 装修流程列表页
	 */
	@RequestMapping(value = "/knowledge/progress", method = RequestMethod.GET)
	public String list2(Model model) {

		return "dictionary/progress.ftl";
	}
	
	/**
	 * 选材秘籍列表页
	 */
	@RequestMapping(value = "/knowledge/material", method = RequestMethod.GET)
	public String list3(Model model) {

		return "dictionary/material.ftl";
	}
	
	/**
	 * 风水大全列表页
	 */
	@RequestMapping(value = "/knowledge/fengshui", method = RequestMethod.GET)
	public String detail(Model model) {

		return "dictionary/fengshui.ftl";
	}
	
	/**
	 * 装修知识详情页
	 */
	@RequestMapping(value = "/knowledge/detail/{filename:.*}", method = RequestMethod.GET)
	public String detail(HttpServletRequest request,Model model,
			@PathVariable String filename) {

		String basePath = request.getSession().getServletContext().getRealPath("/");
		String path=new StringBuffer(basePath).append("RES/data/").append(filename).toString();
		String str = null;
		// 1.获取源文件
		try (FileInputStream fin = new FileInputStream(path);){
			LOG.info("file path is {}", path);
			// 2.获取通道
			FileChannel fcin = fin.getChannel();
			//3.创建缓冲区
		    ByteBuffer buffer = ByteBuffer.allocate((int) fcin.size());
		    while (true) {
		    	// 4.clear方法重设缓冲区，使它可以接受读入的数据
		    	buffer.clear();
		    	 // 从输入通道中将数据读到缓冲区
		    	int r= fcin.read(buffer);
		    	// read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
		    	  if (r == -1) {
		    		  break;
		    	  }
		    }
		    str=new String(buffer.array());
		} catch (FileNotFoundException e) {
			LOG.error("knowledge.detail.FileNotFoundException o2o-exception , more info :{}",e.getMessage());
		}catch (IOException e) {
			LOG.error("knowledge.detail.IOException o2o-exception , more info :{}",e.getMessage());
		}
	    model.addAttribute("str", str);
		return "dictionary/knowledge.ftl";
	}
	@RequestMapping(value = "article/{articleId}", method = RequestMethod.GET)
	public String article(Model model,@PathVariable Long articleId) {

		HttpBaseResponse baseResponse = new HttpBaseResponse();
		Article article=articleService.queryArticleById(articleId);
		if(article!=null&&article.getArticleTitle()!=null){
			baseResponse.setCode(HttpResponseCode.SUCCESS);
	        baseResponse.setObj(article);
	        baseResponse.setExt(null);
		}else{
			baseResponse.setCode(HttpResponseCode.SUCCESS);
	        baseResponse.setObj(null);
	        baseResponse.setExt(null);
		}
		model.addAttribute("baseResponse", baseResponse);
		return "dictionary/article.ftl";
	}
}
