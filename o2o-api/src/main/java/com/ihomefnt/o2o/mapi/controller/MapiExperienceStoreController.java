/**
 * 
 */
package com.ihomefnt.o2o.mapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResponse;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoresResponse;
import com.ihomefnt.o2o.intf.service.experiencestore.ExperienceStoreService;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import io.swagger.annotations.Api;

/**
 * @author Administrator
 *
 */
@Api(value="M站体验店API",description="M站体验店接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/experstore")
public class MapiExperienceStoreController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapiExperienceStoreController.class);
			
    @Autowired
    ExperienceStoreService experienceStoreService;
    /**
     * 体验店列表
     * 
     * @param resquest
     * @return
     */
    @RequestMapping(value = "/getExperStores", method = RequestMethod.GET)
    public String getExperStores(Model model, HttpServletRequest request) {
		
        HttpExperienceStoresResponse experienceStoreResponse = new HttpExperienceStoresResponse();
        if(null != request.getParameter("latitude")
        		&& null != request.getParameter("longitude")){
            //根据当前位置去取
            experienceStoreResponse = experienceStoreService.getExperStores(Double.valueOf(request.getParameter("latitude")),
            		Double.valueOf(request.getParameter("longitude")),null);
        } else {
            //获得默认的值
            experienceStoreResponse = experienceStoreService.getExperStores(null);
        }
        model.addAttribute("experStore", experienceStoreResponse);
        return "experstore/list.ftl";
    }

    /**
     * 
     * @param resquest
     * @return
     */
    @RequestMapping(value = "/getDSDetail/{esId}", method = RequestMethod.GET)
    public String getDSDetail(Model model,@PathVariable Long esId) {
    	   	
        HttpExperienceStoreDetailResponse experienceStoreInfo = experienceStoreService.getDSDetail(esId);
        model.addAttribute("experStoreDetail", experienceStoreInfo);
        List<HttpExperienceStoreDetailResponse> list =  experienceStoreService.getDSDetailById(esId);
        
		if(null != list && list.size() > 0){
			int amount = 0;//套装风格数量
			int suitAmount = list.size();////套装数量
			String name = list.get(0).getName();
			String images = "";
			List<String> strList = ImageUtil.removeEmptyStr(list.get(0).getImage());
			if(null != strList && strList.size() > 0){
//				images = strList.get(0) + "?imageView2/4/w/100/h/100";
				images = QiniuImageUtils.compressProductImage(strList.get(0), 100, 100);
			}
			Map<Long,Integer> map = new HashMap<Long,Integer>();
			for(HttpExperienceStoreDetailResponse res : list){
				if(null != res && null != res.getBuildingId() 
						&& null == map.get(res.getBuildingId())){
					amount = amount + 1;
					map.put(res.getBuildingId(), 1);
				}
			}
		}
        return "experstore/detail.ftl";
    }

}
