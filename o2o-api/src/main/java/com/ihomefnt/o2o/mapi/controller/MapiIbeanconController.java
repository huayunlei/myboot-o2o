package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProduct;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.IbeanconResponse;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.SuitModel;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hvk687 on 10/4/15.
 */
@Api(value="M站样板间API",description="M站样板间老接口",tags = "【M-API】")
@RequestMapping(value = "/mapi/ibeacon")
@Controller
public class MapiIbeanconController {

    @Autowired
    ProductService productService;
   

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
		
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        IbeanconResponse response = new IbeanconResponse();
        List<SuitModel> list = new LinkedList<SuitModel>();
        list.add(makeSuitModel(27L));
        list.add(makeSuitModel(26L));
        response.setList(list);
        baseResponse.setCode(HttpResponseCode.SUCCESS);
        baseResponse.setObj(response);
        baseResponse.setExt(null);
        model.addAttribute("baseResponse", baseResponse);
        /**
         * share related
         */
        model.addAttribute("enable", true);
        model.addAttribute("title1", "艾佳样板间");
        model.addAttribute("icon1", "http://pc2.img.ihomefnt.com/ibeaconlogo.png");
        model.addAttribute("desc", "你附近生活的家");
        model.addAttribute("mUrl","http://m.ihomefnt.com/ibeacon/homecard");
        return "topic/ibeacon.ftl";
    }

    SuitModel makeSuitModel(Long id) {
        CompositeProduct c1 = productService.queryCompositeProductById(id);
        SuitModel s1 = new SuitModel();
        s1.setName(c1.getName());
        s1.setId(id);
        if (StringUtils.isNotBlank(c1.getPictureUrlOriginal())
                && c1.getPictureUrlOriginal().contains("[")
                && c1.getPictureUrlOriginal().contains("]")) {
            JSONArray compositeJsonArray = JSONArray.fromObject(c1.getPictureUrlOriginal());
            List<String> compositeStrList = (List<String>) JSONArray.toList(compositeJsonArray, String.class);
            List<String> compositeStrResponseList = new ArrayList<String>();
            if (null != compositeStrList && compositeStrList.size() > 0) {
                for (String str : compositeStrList) {
                    if (null != str && !"".equals(str)) {
                        str += productService.appendImageMethod(ImageSize.SIZE_LARGE);
                        compositeStrResponseList.add(str);
                    }
                }
            }
            if (compositeStrResponseList.size() > 0) {
                s1.setImage(compositeStrResponseList.get(0));
            }
        }
        s1.setDesc(c1.getSummary());
        return s1;
    }
}
