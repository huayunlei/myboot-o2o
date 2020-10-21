package com.ihomefnt.o2o.service.dao.culture;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.culture.CultureDao;

@Repository
public class CultureDaoImpl implements CultureDao{
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	private static String NAME_SPACE = "com.ihomefnt.o2o.service.dao.culture.";

	@Override
	public int queryProductCount(Integer userId,Integer productId) {
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("productId", productId);
		return sqlSessionTemplate.selectOne(NAME_SPACE+"queryProductCountByUserId", param);
	}

	@Override
	public boolean addUserPurchaseCultureRecord(Integer userId, Integer productId,Integer status) {
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("productId", productId);
		param.put("status", status);
		int insert = sqlSessionTemplate.insert(NAME_SPACE+"addUserPurchaseCultureRecord", param);
		if(insert == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserPurchaseCultureRecord(Integer userId, Integer productId, Integer status) {
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("productId", productId);
		param.put("status", 2);
		int update = sqlSessionTemplate.update(NAME_SPACE+"updateUserPurchaseCultureRecord", param);
		if (update > 0) {
			return true;
		}
		return false;
	}
	
	
	
	
}	
