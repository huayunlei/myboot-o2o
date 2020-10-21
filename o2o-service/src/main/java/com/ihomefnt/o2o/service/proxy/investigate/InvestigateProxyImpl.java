package com.ihomefnt.o2o.service.proxy.investigate;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.investigate.dto.InvestigateCommitDto;
import com.ihomefnt.o2o.intf.domain.investigate.vo.response.InvestigateQueryResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.investigate.InvestigateProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/4 11:02 上午
 */
@Service
public class InvestigateProxyImpl implements InvestigateProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public InvestigateQueryResponse queryInvestigateInfo(Integer investigateId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("investigateId", investigateId);
        HttpBaseResponse<InvestigateQueryResponse> responseVo = strongSercviceCaller.post(
                    WcmWebServiceNameConstants.QUERY_INVESTIGATE_INFO, params,
                    new TypeReference<HttpBaseResponse<InvestigateQueryResponse>>() {
                    });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public void commitInvestigate(InvestigateCommitDto commitDto) {
        HttpBaseResponse responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.COMMIT_INVESTIGATE, commitDto, HttpBaseResponse.class);
        if (responseVo == null || !responseVo.isSuccess()) {
            throw new BusinessException(MessageConstant.FAIL_COMMIT_INVESTIGATE);
        }
    }
}
