package com.ihomefnt.o2o.intf.service.ad;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;
import com.ihomefnt.o2o.intf.domain.ad.vo.request.AdStartPageRequestVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdStartPageItemResponseVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;


public interface AdService {
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	AdvertisementDto querAdById(Long id);

    List<AdvertisementResponseVo> queryAdvertisement(int i, int position,String cityCode,Integer type);

	List<AdvertisementResponseVo> queryAdFromProtocol(int i,String protocol,String cityCode);

	/**
	 * 新版
	 * @param paramMap
	 * @return
	 */
	List<AdvertisementResponseVo> queryAdvertisement(
			Map<String, Object> paramMap);
	
	/**
	 * 旧版
	 */
	List<AdvertisementResponseVo> queryAdvertisement1(
			Map<String, Object> paramMap);
	
	/**
	 * 查询APP启动页列表
	 * @param request
	 * @return
	 */
	List<AdStartPageItemResponseVo> queryStartPageList(AdStartPageRequestVo request);
	
}
