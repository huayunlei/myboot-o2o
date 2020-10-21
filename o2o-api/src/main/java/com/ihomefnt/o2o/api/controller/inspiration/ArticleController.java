/**
 * 
 */
package com.ihomefnt.o2o.api.controller.inspiration;


import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpArticleRequest;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.inspiration.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Administrator
 *
 */
@ApiIgnore
@Deprecated
@Api(tags = "灵感文章老API",hidden = true)
@RestController
@RequestMapping("/article")
public class ArticleController {
	
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/addRead", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addRead(@Json HttpArticleRequest request) {
        if (request == null || null == request.getArticleUrl()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        try{
        	articleService.addArticleRead(request.getArticleUrl());
        } catch(Exception e){
        	return HttpBaseResponse.fail(MessageConstant.FAILED);
        }
        return HttpBaseResponse.success();
    }

}
