/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2017/3/13
 * Description:ConcurrentConstants.java
 */
package com.ihomefnt.o2o.intf.manager.concurrent;

/**
 * @author 闫辛未
 */
public interface ConcurrentConstants {
    /**
     * 线程池线程总数
     */
    int COMMON_THREAD_POOL_SIZE = 16;

    /**
     * 线程池核心线程数
     */
    int COMMON_THREAD_POOL_CORE_SIZE = 16;

    /**
     * 查询db单次in最大数量
     */
    int QUERY_DB_BATCH_SIZE = 200;

    /**
     * 查询DB并发数
     */
    int QUERY_DB_CONCURRENT_SIZE = 3;
}
