package com.ihomefnt.o2o.service.service.tkdm;

import com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao;
import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiao on 10/29/15.
 */
@Repository
public class TKDMDaoImpl implements TKDMDao {

	private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.tkdm.TKDMDao.";
	
	@Autowired
    SqlSessionTemplate sessionTemplate;
	
	@Override
	public TKDMSeo loadTKDM(String seoKey) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("seo_key", seoKey);
        return sessionTemplate.selectOne(NAME_SPACE + "queryTkdm", map);
	}

}
