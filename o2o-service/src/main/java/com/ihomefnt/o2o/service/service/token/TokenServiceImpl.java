package com.ihomefnt.o2o.service.service.token;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.dao.token.TokenDao;
import com.ihomefnt.o2o.intf.service.token.TokenService;

@Service
public class TokenServiceImpl implements TokenService{

	@Autowired
	TokenDao tokenDao;

	@Override
	public Map selSessionIdIsEffective(String accessToken) {
		return tokenDao.selSessionIdIsEffective(accessToken);
	}
	
}
