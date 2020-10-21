package com.ihomefnt.o2o.service.service.ad;

import com.ihomefnt.o2o.intf.dao.ad.AdDao;
import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertDto;
import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertListDto;
import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;
import com.ihomefnt.o2o.intf.domain.ad.vo.request.AdStartPageRequestVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdStartPageItemResponseVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.ad.AdvertProxy;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdServiceImpl implements AdService {
	private static final Logger LOG = LoggerFactory.getLogger(AdServiceImpl.class);

	@Autowired
	AdDao adDao;
	
	@Autowired
	AdvertProxy advertProxy;
	
	@Override
	public AdvertisementDto querAdById(Long id) {
		LOG.info("AdService.querAdById() Start");
		return adDao.querAdById(id);
	}

	@Override
    public List<AdvertisementResponseVo> queryAdvertisement(int count, int position,String cityCode,Integer type) {
        List<AdvertisementDto> advertisements = adDao.queryAdvertisement(count, position,type,cityCode);
		List<AdvertisementResponseVo> list = new ArrayList<AdvertisementResponseVo>();
		String params = "";
		if(StringUtils.isNotBlank(cityCode)){
			params = "cityCode=" + cityCode;
		}
		String httpUrl = "";
		for (int i = 0; i < advertisements.size(); i++) {
			AdvertisementDto td = advertisements.get(i);
			if(null !=td){
				 httpUrl = td.getrHttpUrl();
				if(StringUtils.isNotBlank(httpUrl) && httpUrl.contains("http://")){
					
					if(StringUtils.isNotBlank(cityCode) && 1 == td.getIsCity()){
						if(httpUrl.contains("?")){
							httpUrl = httpUrl + "&" + params;
						} else {
							httpUrl = httpUrl + "?" + params;
						}
					}
					if(1 == td.getIsCity()) {
						if(httpUrl.contains("?")){
							td.setrHttpUrl(httpUrl + "&fromApp=1");
						} else {
							td.setrHttpUrl(httpUrl + "?fromApp=1");
						}
					}

				}
			}
			AdvertisementResponseVo advertisementResponse= new AdvertisementResponseVo(td);
			list.add(advertisementResponse);
		}
		return list;
	}

	@Override
	public List<AdvertisementResponseVo> queryAdFromProtocol(int count,
			String protocol,String cityCode) {
		LOG.info("AdService.queryAdFromProtocol() Start");
		List<AdvertisementResponseVo> list = new ArrayList<AdvertisementResponseVo>();
		String params = "";
		if(StringUtils.isNotBlank(cityCode)){
			params = "cityCode=" + cityCode;
		}else{
			params = "cityCode=" + "210000";
			cityCode = "210000";
		}
        List<AdvertisementDto> advertisements = adDao.queryAdFromProtocol(count, protocol, cityCode);
		String httpUrl = "";
		for (int i = 0; i < advertisements.size(); i++) {
			
			AdvertisementDto td = advertisements.get(i);
			if(null !=td && StringUtils.isNotBlank(cityCode) && 1 == td.getIsCity()){
				 httpUrl = td.getrHttpUrl();
				if(StringUtils.isNotBlank(httpUrl)){
					if(httpUrl.contains("?")){
						td.setrHttpUrl(httpUrl + "&" + params);
					} else {
						td.setrHttpUrl(httpUrl + "?" + params);
					}
				}
			}
			
			AdvertisementResponseVo advertisementResponse= new AdvertisementResponseVo(td);
			list.add(advertisementResponse);
		}
		return list;
	}

	@Override
	public List<AdvertisementResponseVo> queryAdvertisement(
			Map<String, Object> paramMap) {
		LOG.info("AdService.queryAdvertisement() Start");
		String cityCode=paramMap.get("cityCode").toString();
		String appVersionStr = paramMap.get("appVersion").toString();
		int appVersion = Integer.parseInt(appVersionStr);
		List<AdvertisementDto> advertisements;
		if(appVersion >= 295) {
			paramMap.put("appVersion", 293);
			advertisements = adDao.queryAdvertisement295(paramMap);
		} else {
			advertisements = adDao.queryAdvertisement(paramMap);
		}
		List<AdvertisementResponseVo> list = new ArrayList<AdvertisementResponseVo>();
		String params = "";
		if(StringUtils.isNotBlank(cityCode)){
			params = "cityCode=" + cityCode;
		}
		String httpUrl = "";
		for (int i = 0; i < advertisements.size(); i++) {
			AdvertisementDto td = advertisements.get(i);
			if(null !=td){
				 httpUrl = td.getrHttpUrl();
				if(StringUtils.isNotBlank(httpUrl) && httpUrl.contains("http://")){
					
					if(StringUtils.isNotBlank(cityCode) && 1 == td.getIsCity()){
						if(httpUrl.contains("?")){
							httpUrl = httpUrl + "&" + params;
						} else {
							httpUrl = httpUrl + "?" + params;
						}
					}
					if(1 == td.getIsCity()) {
						if(httpUrl.contains("?")){
							td.setrHttpUrl(httpUrl + "&fromApp=1");
						} else {
							td.setrHttpUrl(httpUrl + "?fromApp=1");
						}
					}

				}
			}
			AdvertisementResponseVo advertisementResponse= new AdvertisementResponseVo(td);
			list.add(advertisementResponse);
		}
		return list;
	}

	@Override
	public List<AdvertisementResponseVo> queryAdvertisement1(
			Map<String, Object> paramMap) {
		LOG.info("AdService.queryAdvertisement() Start");
		String cityCode=paramMap.get("cityCode").toString();
        List<AdvertisementDto> advertisements = adDao.queryAdvertisement1(paramMap);
		List<AdvertisementResponseVo> list = new ArrayList<AdvertisementResponseVo>();
		String params = "";
		if(StringUtils.isNotBlank(cityCode)){
			params = "cityCode=" + cityCode;
		}
		String httpUrl = "";
		for (int i = 0; i < advertisements.size(); i++) {
			AdvertisementDto td = advertisements.get(i);
			if(null !=td){
				 httpUrl = td.getrHttpUrl();
				if(StringUtils.isNotBlank(httpUrl) && httpUrl.contains("http://")){
					
					if(StringUtils.isNotBlank(cityCode) && 1 == td.getIsCity()){
						if(httpUrl.contains("?")){
							httpUrl = httpUrl + "&" + params;
						} else {
							httpUrl = httpUrl + "?" + params;
						}
					}
					if(1 == td.getIsCity()) {
						if(httpUrl.contains("?")){
							td.setrHttpUrl(httpUrl + "&fromApp=1");
						} else {
							td.setrHttpUrl(httpUrl + "?fromApp=1");
						}
					}

				}
			}
			AdvertisementResponseVo advertisementResponse= new AdvertisementResponseVo(td);
			list.add(advertisementResponse);
		}
		return list;
	}

	@Override
	public List<AdStartPageItemResponseVo> queryStartPageList(AdStartPageRequestVo request) {
		if(null == request) {
			throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		//20171107  版本控制 appVersion>3.1.8的版本启动页修改
		String appVersion = "";
		if(null == request.getOsType() || request.getOsType() == 3) {
			appVersion = "3.1.8";
		} else {
				appVersion = request.getAppVersion();
		}
		
		if(!VersionUtil.mustUpdate("3.1.8", appVersion)) {
			throw new BusinessException(HttpResponseCode.SUCCESS, MessageConstant.NO_PAGE_EXIST);
		}
		List<AdStartPageItemResponseVo> response = new ArrayList<AdStartPageItemResponseVo>();
		
		int width = 0;
		if(request.getWidth() != null){
			width = request.getWidth();
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(request.getPicId() != null && request.getPicId() > 0){
			paramMap.put("id", request.getPicId());
		}
		if(request.getGroupId() != null && request.getGroupId() > 0){
			paramMap.put("groupId", request.getGroupId());
		}
		if(request.getOsType() != null && request.getOsType() > 0){
			paramMap.put("osType", request.getOsType());
		}
		if(request.getWidth() != null && request.getWidth() > 0){
			paramMap.put("width", request.getWidth());
		}
		AdvertListDto listResponseVo = advertProxy.queryStartPageList(paramMap);
		if(listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getStartPageList())){
			List<AdvertDto> advertList = listResponseVo.getStartPageList();
			for (AdvertDto advertVo : advertList) {
				AdStartPageItemResponseVo startPageItem = new AdStartPageItemResponseVo();
				if(advertVo.getId() != null){
					startPageItem.setPicId(advertVo.getId().longValue());
				}
				if(StringUtils.isNotBlank(advertVo.getImage())){
					startPageItem.setImage(QiniuImageUtils.compressImageAndSamePic(advertVo.getImage(), width, -1));
				}
				if(StringUtils.isNotBlank(advertVo.getButtonUrl())){
					startPageItem.setButtonUrl(advertVo.getButtonUrl());
				}
				if(advertVo.getGroupId() != null){
					startPageItem.setGroupId(advertVo.getGroupId());
				}
				if(advertVo.getOsType() != null){
					startPageItem.setOsType(advertVo.getOsType());
				}
				if(advertVo.getStartTime() != null){
					startPageItem.setStartTime(advertVo.getStartTime().getTime());
				}
				if(advertVo.getEndTime() != null){
					startPageItem.setEndTime(advertVo.getEndTime().getTime());
				}
				if(advertVo.getUpdateTime() != null){
					startPageItem.setUpdateTime(advertVo.getUpdateTime().getTime());
				}
				response.add(startPageItem);
			}
		}
		
		return response;
	}
	
	
	
}
