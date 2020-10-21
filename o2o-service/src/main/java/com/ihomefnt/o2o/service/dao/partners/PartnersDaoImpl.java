package com.ihomefnt.o2o.service.dao.partners;

import com.ihomefnt.o2o.intf.dao.partners.PartnersDao;
import com.ihomefnt.o2o.intf.domain.partners.dto.Area;
import com.ihomefnt.o2o.intf.domain.partners.dto.TPartners;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PartnersDaoImpl implements PartnersDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.partners.PartnersDao.";

	@Override
	public Long enrollPartners(TPartners partners) {
        sqlSessionTemplate.insert(NAME_SPACE + "enrollPartners", partners);
        return partners.getId();
	}

	@Override
	public List<String> queryEnrollList() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryEnrollList");
	}

	@Override
	public String queryEnrollByMobile(String mobile) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryEnrollByMobile", mobile);
	}

	@Override
	public List<Area> queryAreaBuilding(Map<String,Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryAreaBuilding", params);
	}
}
