package com.ihomefnt.o2o.intf.manager.util.common;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 调用api 封装
 *
 * @author jiangjun
 * @version 2.0, 2018-04-13 上午10:56
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class ServiceCallerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceCallerUtil.class);

    @Resource
    private ServiceCaller serviceCaller;

    public ResponseVo requestApiWithoutDataRetMsg(String url,Object param){
        return requestApiWithoutDataRetMsg(null, url,param);
    }

    public ResponseVo requestApiWithoutDataRetMsg(String method, String url,Object param){
        ResponseVo responseVo = requestApi(method, url, param);

        return responseVo;
    }

    public boolean requestApiWithoutData(String url,Object param){
        return requestApiWithoutData(null, url, param);
    }

    public boolean requestApiWithoutData(String method, String url,Object param){
        ResponseVo responseVo = requestApi(method, url, param);

        if (responseVo.isSuccess()) {
            return true;
        }

        return false;
    }

    public Object requestApiData(String url, Class clzz, Map param){
        return requestApiData(null,url,clzz,param);
    }

    public Object requestApiData(String method, String url, Class clzz, Map param){
        ResponseVo responseVo = requestApi(method, url, param);

        Object object = null;

        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                object = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), clzz);
            }
        }

        return object;
    }

    public List requestApiDatas(String url,Object obj, Map param){
        return requestApiDatas(null,url,obj,param);
    }

    public List requestApiDatas(String method, String url,Object obj, Object param){
        ResponseVo responseVo = requestApi(method, url, param);

        List ret = null;

        if (responseVo.isSuccess()) {
            if (responseVo.getData() != null) {
                ret = (List<Object>) JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), obj.getClass());
            }
        }

        return ret;
    }

    public ResponseVo requestApi(String method, String url, Object param){
        LOG.info(url + " params:{}", param);
        ResponseVo<?> responseVo;
        long t1 = System.currentTimeMillis();
        try {

            if(StringUtils.isNotBlank(method) && method.equals("get")){
                responseVo = serviceCaller.get(url, param, ResponseVo.class);
            }else{
                responseVo = serviceCaller.post(url, param, ResponseVo.class);
            }

            long t2 = System.currentTimeMillis();

            LOG.info("time :{}ms", (t2 - t1));

        } catch (Exception e) {
            long t3 = System.currentTimeMillis();
            LOG.info("error time :{}ms", (t3 - t1));
            LOG.error(url + " ERROR:{}", e.getMessage());
            return null;
        }
        LOG.info(url + " result :{}", JsonUtils.obj2json(responseVo));

        if (responseVo == null) {
            return null;
        }
        return responseVo;
    }
}
