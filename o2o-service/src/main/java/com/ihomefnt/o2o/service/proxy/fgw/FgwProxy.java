package com.ihomefnt.o2o.service.proxy.fgw;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.agent.vo.request.B2cValidationInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.agent.vo.request.BankCheckRequest;
import com.ihomefnt.o2o.intf.domain.agent.vo.request.BankCnapParamsRequestVo;
import com.ihomefnt.o2o.intf.domain.agent.vo.request.PageSelectorInfos;
import com.ihomefnt.o2o.intf.domain.agent.vo.response.BankCheckResponse;
import com.ihomefnt.o2o.intf.domain.agent.vo.response.B2cValidationResult;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created 2017/9/1
 *
 * @author gaoxin
 */
@Service
public class FgwProxy {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FgwProxy.class);

    @Resource
    private ServiceCaller serviceCaller;

    private static final String B2C_PAY_VALIDATE_FACTOR_SERVICE_NAME = "fgw-web.b2cpay.validateFactor";

    private static final String COMMON_APP_API_BANK_INFOS_SERVICE_NAME = "fgw-web.common.api.bankInfos";

    private static final String COMMON_APP_API_PROVICE_INFOS_SERVICE_NAME = "fgw-web.common.api.provinceInfos";

    private static final String COMMON_APP_API_CITY_INFOS_SERVICE_NAME = "fgw-web.common.api.cityInfos";

    private static final String COMMON_APP_API_CITY_BANK_INFOS_SERVICE_NAME = "fgw-web.common.api.citybankInfos";

    /**
     * 银行四要素校验接口
     */
    private static final String COMMON_APP_API_BANK_INFO_CHECK_RECORD = "fgw-web.common.api.bankInfoCheckAndRecord";

    /**
     * 姓名身份证银行卡三要素检查
     * @param
     */
    public ResponseVo<B2cValidationResult> validateFactor(B2cValidationInfoRequestVo b2cValidationInfo) {
        LOGGER.info(B2C_PAY_VALIDATE_FACTOR_SERVICE_NAME + " params:{}", b2cValidationInfo);
        ResponseVo<B2cValidationResult> responseVo = null;
        try {
            responseVo = serviceCaller.post(B2C_PAY_VALIDATE_FACTOR_SERVICE_NAME, b2cValidationInfo, ResponseVo.class);
            LOGGER.info(B2C_PAY_VALIDATE_FACTOR_SERVICE_NAME + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(B2C_PAY_VALIDATE_FACTOR_SERVICE_NAME + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }

    public ResponseVo<List<PageSelectorInfos>> bankInfos(BankCnapParamsRequestVo.BankNameParam param) {
        LOGGER.info(COMMON_APP_API_BANK_INFOS_SERVICE_NAME + " params:{}", param);
        ResponseVo<List<PageSelectorInfos>> responseVo = null;
        try {
            responseVo = serviceCaller.post(COMMON_APP_API_BANK_INFOS_SERVICE_NAME, param, ResponseVo.class);
            LOGGER.info(COMMON_APP_API_BANK_INFOS_SERVICE_NAME + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(COMMON_APP_API_BANK_INFOS_SERVICE_NAME + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }

    public ResponseVo<List<PageSelectorInfos>> provinceInfos(BankCnapParamsRequestVo.ProvinceNameParam param) {
        LOGGER.info(COMMON_APP_API_PROVICE_INFOS_SERVICE_NAME + " params:{}", param);
        ResponseVo<List<PageSelectorInfos>> responseVo = null;
        try {
            responseVo = serviceCaller.post(COMMON_APP_API_PROVICE_INFOS_SERVICE_NAME, param, ResponseVo.class);
            LOGGER.info(COMMON_APP_API_PROVICE_INFOS_SERVICE_NAME + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(COMMON_APP_API_PROVICE_INFOS_SERVICE_NAME + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }

    public ResponseVo<List<PageSelectorInfos>> cityInfos(BankCnapParamsRequestVo.CityNameParam param) {
        LOGGER.info(COMMON_APP_API_CITY_INFOS_SERVICE_NAME + " params:{}", param);
        ResponseVo<List<PageSelectorInfos>> responseVo = null;
        try {
            responseVo = serviceCaller.post(COMMON_APP_API_CITY_INFOS_SERVICE_NAME, param, ResponseVo.class);
            LOGGER.info(COMMON_APP_API_CITY_INFOS_SERVICE_NAME + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(COMMON_APP_API_CITY_INFOS_SERVICE_NAME + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }

    public ResponseVo<List<PageSelectorInfos>> citybankInfos(BankCnapParamsRequestVo.CityBankNameParam param) {
        LOGGER.info(COMMON_APP_API_CITY_BANK_INFOS_SERVICE_NAME + " params:{}", param);
        ResponseVo<List<PageSelectorInfos>> responseVo = null;
        try {
            responseVo = serviceCaller.post(COMMON_APP_API_CITY_BANK_INFOS_SERVICE_NAME, param, ResponseVo.class);
            LOGGER.info(COMMON_APP_API_CITY_BANK_INFOS_SERVICE_NAME + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(COMMON_APP_API_CITY_BANK_INFOS_SERVICE_NAME + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }

    /**
     * 银行四要素校验
     * @param param
     * @return
     */
    public ResponseVo<BankCheckResponse> bankInfoCheckAndRecord(BankCheckRequest param) {
        LOGGER.info(COMMON_APP_API_BANK_INFO_CHECK_RECORD + " params:{}", param);
        ResponseVo<BankCheckResponse> responseVo = null;
        try {
            responseVo = serviceCaller.post(COMMON_APP_API_BANK_INFO_CHECK_RECORD, param, ResponseVo.class);
            LOGGER.info(COMMON_APP_API_BANK_INFO_CHECK_RECORD + " result :{}", JsonUtils.obj2json(responseVo));
        } catch (Exception e) {
            LOGGER.error(COMMON_APP_API_BANK_INFO_CHECK_RECORD + " ERROR:{}", e.getMessage());
        }
        return responseVo;
    }
}
