package com.ihomefnt.o2o.intf.dao.ad;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;

public interface AdDao {

	/**
	 * 通用接口查询
	 * @param ad
	 * @return
	 */
	List<AdvertisementDto> querAdList(AdvertisementDto ad);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	AdvertisementDto querAdById(Long id);
	/**
	 * 
	 * @param count
	 * @return
	 */
    List<AdvertisementDto> queryAdvertisement(int count, int position,Integer type,String cityCode);
	
    /**
     * 根据请求协议查询
     * @param count
     * @param protocol
     * @param cityCode 
     * @return
     */
    List<AdvertisementDto> queryAdFromProtocol(int count, String protocol, String cityCode);
	/**
	 * 新版查询广告
	 * @param paramMap
	 * @return
	 */
	List<AdvertisementDto> queryAdvertisement(Map<String, Object> paramMap);
	
	/**
	 * 旧版查询广告
	 * @param paramMap
	 * @return
	 */
	List<AdvertisementDto> queryAdvertisement1(Map<String, Object> paramMap);
	
	/**
	 * 295版本以后查询广告
	 * @param paramMap
	 * @return
	 */
	List<AdvertisementDto> queryAdvertisement295(Map<String, Object> paramMap);
	
}
