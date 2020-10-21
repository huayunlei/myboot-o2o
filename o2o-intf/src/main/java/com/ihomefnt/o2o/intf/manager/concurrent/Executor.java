/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2017/3/13
 * Description:DollyExecutor.java
 */
package com.ihomefnt.o2o.intf.manager.concurrent;

import com.ihomefnt.common.concurrent.DefaultTaskProcessFactory;
import com.ihomefnt.common.concurrent.TaskProcess;
import com.ihomefnt.common.concurrent.TaskProcessManager;

/**
 * @author 闫辛未
 */
public class Executor {
    /**
     * 调用外部接口域
     */
    private static final String INVOKE_OUTER_SERVICE_BUSINESS = "INVOKE_OUTER_SERVICE_EXECUTOR";

    /**
     * 查询内部db缓存域
     */
    private static final String QUERY_DB_CACHE_BUSINESS = "QUERY_DB_CACHE_EXECUTOR";

    /**
     * service层并发域
     */
    private static final String SERVICE_CONCURRENT_QUERY_BUSINESS = "SERVICE_CONCURRENT_QUERY_EXECUTOR";

    private static DefaultTaskProcessFactory invokeOuterServiceFactory = new DefaultTaskProcessFactory();

    private static DefaultTaskProcessFactory queryDBCacheFactory = new DefaultTaskProcessFactory();

    private static DefaultTaskProcessFactory serviceConcurrentQueryFactory = new DefaultTaskProcessFactory();

    static {
        invokeOuterServiceFactory.setCoreSize(ConcurrentConstants.COMMON_THREAD_POOL_CORE_SIZE);
        invokeOuterServiceFactory.setPoolSize(ConcurrentConstants.COMMON_THREAD_POOL_SIZE);

        queryDBCacheFactory.setCoreSize(ConcurrentConstants.COMMON_THREAD_POOL_CORE_SIZE);
        queryDBCacheFactory.setPoolSize(ConcurrentConstants.COMMON_THREAD_POOL_SIZE);

        serviceConcurrentQueryFactory.setCoreSize(ConcurrentConstants.COMMON_THREAD_POOL_CORE_SIZE);
        serviceConcurrentQueryFactory.setPoolSize(ConcurrentConstants.COMMON_THREAD_POOL_SIZE);

    }

    public static TaskProcess getInvokeOuterServiceFactory() {
        return TaskProcessManager.getTaskProcess(INVOKE_OUTER_SERVICE_BUSINESS, invokeOuterServiceFactory);
    }

    public static TaskProcess getQueryDBCacheFactory() {
        return TaskProcessManager.getTaskProcess(QUERY_DB_CACHE_BUSINESS, queryDBCacheFactory);
    }

    public static TaskProcess getServiceConcurrentQueryFactory() {
        return TaskProcessManager.getTaskProcess(SERVICE_CONCURRENT_QUERY_BUSINESS, serviceConcurrentQueryFactory);
    }
}
