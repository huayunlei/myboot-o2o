package com.ihomefnt.o2o.intf.service.token;

import java.util.Map;

public interface TokenService {

	Map selSessionIdIsEffective(String accessToken);
}
