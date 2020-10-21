package com.ihomefnt.o2o.service.dao.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.address.ChannelDao;
import com.ihomefnt.o2o.intf.domain.address.doo.CityConfigDo;
import com.ihomefnt.o2o.intf.domain.address.doo.CityDo;

@Repository
public class ChannelDaoImpl implements ChannelDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.address.ChannelDao.";
    
	@Override
	public List<CityDo> queryChannel() {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryChannel");
	}

    @Override
    public CityDo queryDefaultChannel() {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryDefaultChannel");
    }

    @Override
    public List<CityDo> locationCity(Double latitude, Double longitude) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        return sqlSessionTemplate.selectList(NAME_SPACE + "locationCity", map);
    }

	@Override
	public CityConfigDo getCityConfigByCode(String cityCode) {
		 return sqlSessionTemplate.selectOne(NAME_SPACE + "queryCityConfigByCode",cityCode);
	}
}
