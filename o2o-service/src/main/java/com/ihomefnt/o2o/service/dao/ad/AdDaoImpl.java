package com.ihomefnt.o2o.service.dao.ad;

import com.ihomefnt.o2o.intf.dao.ad.AdDao;
import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdDaoImpl implements AdDao {
	private static final Logger LOG = LoggerFactory.getLogger(AdDaoImpl.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.ad.AdDao";

	@Override
	public List<AdvertisementDto> querAdList(AdvertisementDto ad) {
		return null;
	}

	@Override
	public AdvertisementDto querAdById(Long id) {
		LOG.info("AdDao.querAdById() Start");
		return sqlSessionTemplate.selectOne(NAME_SPACE+".querAdById", id);
	}

	@Override
    public List<AdvertisementDto> queryAdvertisement(int count, int position,Integer type,String cityCode) {
		LOG.info("AdDao.queryAdvertisement() Start");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        map.put("position", position);
        map.put("cityCode", cityCode);
        if(null != type){
        	map.put("type", type);
        } else {
        	map.put("type", 1);
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryAdvertisement", map);
	}

	@Override
    public List<AdvertisementDto> queryAdFromProtocol(int count, String protocol, String cityCode) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("count", count);
        map.put("cityCode", cityCode);
		map.put("protocol", protocol+"%");
		return sqlSessionTemplate.selectList(NAME_SPACE+".queryAdFromProtocol", map);
	}

	@Override
	public List<AdvertisementDto> queryAdvertisement(Map<String, Object> paramMap) {
		LOG.info("AdDao.queryAdvertisement() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryAdvertisementnew", paramMap);
	}
	
	@Override
	public List<AdvertisementDto> queryAdvertisement1(Map<String, Object> paramMap) {
		LOG.info("AdDao.queryAdvertisement1() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryAdvertisement1", paramMap);
	}

	@Override
	public List<AdvertisementDto> queryAdvertisement295(Map<String, Object> paramMap) {
		LOG.info("AdDao.queryAdvertisement295() Start");
        return sqlSessionTemplate.selectList(NAME_SPACE + ".queryAdvertisement295", paramMap);
	}
	
	
}
