package com.ihomefnt.o2o.intf.dao.token;

import java.util.Map;

public interface TokenDao {
	Map selSessionIdIsEffective(String accessToken);
}
