package com.ihomefnt.o2o.service.dao.token;

import com.ihomefnt.o2o.intf.dao.token.TokenDao;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenDaoImpl implements TokenDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.token.TokenDao.";

	@Override
	public Map selSessionIdIsEffective(String accessToken) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(!StringUtil.isNullOrEmpty(accessToken)){
			paramMap.put("accessToken", accessToken);
		}
		return sqlSessionTemplate.selectOne(NAME_SPACE + "selSessionIdIsEffective", paramMap);
	}
}
