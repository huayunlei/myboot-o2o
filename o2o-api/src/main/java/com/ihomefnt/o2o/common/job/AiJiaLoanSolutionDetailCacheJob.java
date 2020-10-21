package com.ihomefnt.o2o.common.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.intf.domain.program.dto.SolutionDetailResponseVo;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author liyonggang
 * @create 2020-01-14 4:07 下午
 */
@Slf4j
@Service
@JobHandler("aiJiaLoanSolutionDetailCacheJob")
public class AiJiaLoanSolutionDetailCacheJob extends IJobHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @NacosValue(value = "${aiJia.loan.solution.cetail.cache.key}", autoRefreshed = true)
    private String aiJiaLoanSolutionDetailCacheKey;

    /**
     * 为了对艾佳贷申请接口优化，创建此缓存，主动刷新方案缓存，接口流量不再调用dolly
     */

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        SolutionDetailResponseVo programDetailById = productProgramProxy.getProgramDetailById(3232);
        if (programDetailById != null) {
            stringRedisTemplate.opsForValue().set(aiJiaLoanSolutionDetailCacheKey, JSON.toJSONString(programDetailById), 1, TimeUnit.HOURS);
            XxlJobLogger.log("aijia loan solution reload");
            log.info("aijia loan solution reload");
        }
        return ReturnT.SUCCESS;
    }
}
