package com.ihomefnt.o2o.service.proxy.agent;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.dto.CommissionRuleDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinCommissionServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.agent.AgentAladdinCommissionProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 经纪人  佣金系统
 * @author ZHAO
 */
@Service
public class AgentAladdinCommisionProxyImpl implements AgentAladdinCommissionProxy{
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	private static final Logger LOG = LoggerFactory.getLogger(AgentAladdinCommisionProxyImpl.class);

    @Override
    public ResponseVo bindAgentCustomerRelationship(UserDto userVo) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("customerUserId", userVo.getId());
        paramMap.put("customerMobile", userVo.getMobile());
        paramMap.put("customerName", userVo.getUsername());

        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinCommissionServiceNameConstants.AGENT_BIND_AGENT_CUSTOMER_RELATIONSHIP, paramMap, ResponseVo.class);
            return responseVo;
        } catch (Exception e) {
            return null;
        }
	}

	@Override
    //@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public CommissionRuleDto queryCommissionRule(Map<String, Object> param) {
		ResponseVo<CommissionRuleDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(AladdinCommissionServiceNameConstants.AGENT_QUERY_COMMISSION_RULE,
					param, new TypeReference<ResponseVo<CommissionRuleDto>>() {});
			if(responseVo != null && responseVo.isSuccess())
				return responseVo.getData();
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 检查是否是个人经纪人
	 * @param userId
	 * @return
	 */
	@Override
	public Boolean checkPersonalAgent(Integer userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId",userId);
		ResponseVo<Boolean> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(AladdinCommissionServiceNameConstants.CHECK_PERSONAL_AGENT,
					param, new TypeReference<ResponseVo<Boolean>>() {});
			if(responseVo != null && responseVo.isSuccess())
				return responseVo.getData();
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
