package com.ihomefnt.o2o.service.proxy.common;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.user.dto.AiMonitorDto;
import com.ihomefnt.o2o.intf.proxy.common.AiMonitorConfigProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiMonitorConfigProxyImpl implements AiMonitorConfigProxy {

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    @NacosValue(value = "${app.monitor.all}", autoRefreshed = true)
    private String appMonitorConfigAll;

    @NacosValue(value = "${app.monitor.default}", autoRefreshed = true)
    private String appMonitorConfigDefault;

    @NacosValue(value = "${app.monitor.default.switch}", autoRefreshed = true)
    private Boolean appMonitorConfigDefaultSwitch;

    @NacosValue(value = "${app.monitor.fail}", autoRefreshed = true)
    private String appMonitorConfigFail;

    @Override
    public List<AiMonitorDto> getMonitorByKey(String monitorKey) {

        List<AiMonitorDto> response = new ArrayList<>();
        try{
            if(null == appMonitorConfigAll || StringUtil.isNullOrEmpty(monitorKey) ){
               return response;
            }
            List<AiMonitorDto> appMonitorConfigAllList = JsonUtils.json2list(appMonitorConfigAll, AiMonitorDto.class);
            for(AiMonitorDto monitorConfigTmp : appMonitorConfigAllList){
                if(monitorKey.equals(monitorConfigTmp.getMonitorKey())){
                    response.add(monitorConfigTmp);
                }
            }
            if(appMonitorConfigDefaultSwitch && 0 == response.size()){
                response.add( JsonUtils.json2obj(appMonitorConfigDefault, AiMonitorDto.class));
            }
            return response;
        }catch (Exception e){
            AiMonitorDto appMonitorConfigDefaultDto = JsonUtils.json2obj(appMonitorConfigFail,AiMonitorDto.class);
            response.add(appMonitorConfigDefaultDto);
            return response;
        }

    }
}
