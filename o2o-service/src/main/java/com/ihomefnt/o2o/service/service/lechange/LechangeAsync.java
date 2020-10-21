package com.ihomefnt.o2o.service.service.lechange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.manager.util.common.http.HttpUtil;

import java.util.concurrent.Future;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-05-17 下午2:16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class LechangeAsync {

    private static final Logger LOG = LoggerFactory.getLogger(LechangeAsync.class);

    @Async
    public Future<String> getDeviceStatus(String combineParam){

        String url = "https://openapi.lechange.cn/openapi" + "/bindDeviceList";

        LOG.info("bindDeviceList param:{}", combineParam);
        String sendPost = HttpUtil.sendPost(url, combineParam);
        LOG.info("bindDevice result:{}", sendPost);

        Future future = new AsyncResult<>(sendPost);

        return future;
    }

}
