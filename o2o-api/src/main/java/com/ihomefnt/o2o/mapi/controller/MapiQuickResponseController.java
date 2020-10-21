package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.building.doo.Building;
import com.ihomefnt.o2o.intf.domain.house.dto.THouse;
import com.ihomefnt.o2o.intf.domain.suit.dto.TSuit;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.service.house.ModelHouseService;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Api(value="M站二维码扫码API",description="M站二维码扫码接口",tags = "【M-API】")
@Controller
public class MapiQuickResponseController {
			
	@Autowired
	ModelHouseService modelHouseService;
	
	/**
     * 线下二维码扫码看套装
     * @param model
     * @param httpSession
     * @return
	 * @throws IOException 
     */
    @RequestMapping(value="/showroom-{houseIdStr}",method = RequestMethod.GET)
    public String quickResponse(@PathVariable String houseIdStr,Model model,HttpServletResponse response,HttpServletRequest request,HttpSession httpSession) throws IOException{
	
    	String message ="FAILD";
        Integer code = 2;
        if(null == houseIdStr || !StringUtils.isNotBlank(houseIdStr)){	//判断传参是否为空
        	message = "传入参数为空";
    		model.addAttribute("message", message);
    		model.addAttribute("code", code);
    		return "/sampleroom/houseType.ftl";
        }
        Long houseId = (long)0;
        if(houseIdStr.equals("1")){
        	 houseId = (long) 26;
        }else if(houseIdStr.equals("2")){
//        	houseId = (long) 27;
            response.sendRedirect("http://weixin.qq.com/r/C0x9ZVLE-g_BrRYG9xkZ");
            return null;
        }else if(houseIdStr.equals("3")){
        	 houseId = (long) 27;
        }else if(houseIdStr.equals("4")){
        	 houseId = (long) 27;
        }else if(houseIdStr.equals("5")){
        	 houseId = (long) 28;
        }else if (houseIdStr.equals("6") 
        		|| houseIdStr.equals("7")
        		|| houseIdStr.equals("8")
        		|| houseIdStr.equals("9")
        		|| houseIdStr.equals("10")){
        	response.sendRedirect("http://m.ihomefnt.com/app/6001");
        	return null;
        }
    	Building building = modelHouseService.queryBuildingById(houseId);		//根据户型的ID反查的样板间信息
    		if(null!=building){
    			//查询结果不为空，进行下一步
    			model.addAttribute("building", building);
    			THouse house = modelHouseService.queryHouseById(houseId);		//根据户型ID查询样板间信息
    			if(null!=house){
//    				JSONArray jsonArray = JSONArray.fromObject(house.getPictureUrlOriginal());
//    	            List<String> graphs = (List<String>) JSONArray.toCollection(jsonArray);
//    	            if (graphs != null && graphs.size() > 0) {
//    	                for (String graph : graphs) {
//    	                    if (graph !=null && !graph.equals("")) {
//    	                    	house.setPictureUrlOriginal(graph);
//    	                    	break;
//    	                    }
//    	                }
//    	            }
    				model.addAttribute("house", house);
    				List<TSuit> suitList = modelHouseService.querySuitList(houseId);	//根据户型ID查询属于该户型下的套装的
    				if(null!=suitList){
    					for(TSuit list:suitList){
    						JSONArray jsonArrays = JSONArray.fromObject(list.getSuitImages());
    	    	            List<String> graph = (List<String>) JSONArray.toCollection(jsonArrays);
    	    	            if (graph != null && graph.size() > 0) {
    	    	                for (String graphss : graph) {
    	    	                    if (graphss !=null && !graphss.equals("")) {
    	    	                    	list.setSuitImages(graphss);
    	    	                    	break;
    	    	                    }
    	    	                }
    	    	            }
    					}
    					model.addAttribute("suitList", suitList);
    					model.addAttribute("enable", true);
    		            model.addAttribute("title1", "给你分享很棒的艾佳样板间");
    		            model.addAttribute("icon1", StaticResourceConstants.AIJIA_LOGO_ICON);
    		            model.addAttribute("desc", "艾佳生活样板间，线上、线下均可参观体验，小伙伴们火速围观吧！");
    				}else{
    					message = "查询结果为空";
                		model.addAttribute("message", message);
                		model.addAttribute("code", code);
    				}
    			}else{
    				message = "查询结果为空";
            		model.addAttribute("message", message);
            		model.addAttribute("code", code);
    			}
    		}else{
    			message = "查询结果为空";
        		model.addAttribute("message", message);
        		model.addAttribute("code", code);
    		}
    	return "/sampleroom/houseType.ftl";
    }
    
    
    
    /**
     * 线下二维码扫码看套装
     * @param model
     * @param httpSession
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="/housetype-{houseIdStr}",method = RequestMethod.GET)
    public String quickOfflineResponse(@PathVariable String houseIdStr,Model model,HttpServletResponse response,HttpServletRequest request,HttpSession httpSession) throws IOException{

        String message ="FAILD";
        Integer code = 2;
        if(null == houseIdStr || !StringUtils.isNotBlank(houseIdStr)){  //判断传参是否为空
            message = "传入参数为空";
            model.addAttribute("message", message);
            model.addAttribute("code", code);
            return "/sampleroom/houseType.ftl";
        }
       
        Long houseId = Long.parseLong(houseIdStr);
        Building building = modelHouseService.queryBuildingById(houseId);       //根据户型的ID反查的样板间信息
            if(null!=building){
                //查询结果不为空，进行下一步
                model.addAttribute("building", building);
                THouse house = modelHouseService.queryHouseById(houseId);       //根据户型ID查询样板间信息
                if(null!=house){
                    /*JSONArray jsonArray = JSONArray.fromObject(house.getPictureUrlOriginal());
                    List<String> graphs = (List<String>) JSONArray.toCollection(jsonArray);
                    if (graphs != null && graphs.size() > 0) {
                        for (String graph : graphs) {
                            if (graph !=null && !graph.equals("")) {
                                house.setPictureUrlOriginal(graph);
                                break;
                            }
                        }
                    }*/
                    model.addAttribute("house", house);
                    List<TSuit> suitList = modelHouseService.querySuitList(houseId);    //根据户型ID查询属于该户型下的套装的
                    if(null!=suitList){
                        for(TSuit list:suitList){
                            JSONArray jsonArrays = JSONArray.fromObject(list.getSuitImages());
                            List<String> graph = (List<String>) JSONArray.toCollection(jsonArrays);
                            if (graph != null && graph.size() > 0) {
                                for (String graphss : graph) {
                                    if (graphss !=null && !graphss.equals("")) {
                                        list.setSuitImages(graphss);
                                        break;
                                    }
                                }
                            }
                        }
                        model.addAttribute("suitList", suitList);
                        model.addAttribute("enable", true);
                        model.addAttribute("title1", "给你分享很棒的艾佳样板间");
                        model.addAttribute("icon1", StaticResourceConstants.AIJIA_LOGO_ICON);
                        model.addAttribute("desc", "艾佳生活样板间，线上、线下均可参观体验，小伙伴们火速围观吧！");
                    }else{
                        message = "查询结果为空";
                        model.addAttribute("message", message);
                        model.addAttribute("code", code);
                    }
                }else{
                    message = "查询结果为空";
                    model.addAttribute("message", message);
                    model.addAttribute("code", code);
                }
            }else{
                message = "查询结果为空";
                model.addAttribute("message", message);
                model.addAttribute("code", code);
            }
        return "/sampleroom/houseType.ftl";
    }
    
    
}
