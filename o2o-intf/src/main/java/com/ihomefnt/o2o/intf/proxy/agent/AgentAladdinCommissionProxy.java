package com.ihomefnt.o2o.intf.proxy.agent;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.dto.CommissionRuleDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;

import java.util.Map;

/**
 * 经纪人  佣金
 * @author ZHAO
 */
public interface AgentAladdinCommissionProxy {

    ResponseVo bindAgentCustomerRelationship(UserDto userVo);

	CommissionRuleDto queryCommissionRule(Map<String, Object> ruleFact);

    Boolean checkPersonalAgent(Integer userId);

}
