package com.ihomefnt.o2o.intf.manager.concurrent;

import java.util.concurrent.Executor;

/**
 * 
 * 功能描述： ResizableExecutor
 * 
 * @author 作者12100579
 */
public interface ResizableExecutor extends Executor {

    /**
     * 功能描述 Returns the current number of threads in the pool.
     * 
     * @param 参数说明
     * @return the number of threads
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
    int getPoolSize();

    /**
     * 功能描述 Returns the current number of MaxThreads.
     * 
     * @param 参数说明
     * @return the number of threads
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
   int getMaxThreads();

    /**
     * 功能描述 Returns the current number of ActiveCount.
     * 
     * @param 参数说明
     * @return the number of ActiveCount
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
    int getActiveCount();

    /**
     * 功能描述 Returns the current number of resizePool.
     * 
     * @param 参数说明
     * @return the number of resizePool
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
    boolean resizePool(int corePoolSize, int maximumPoolSize);

    /**
     * 功能描述 Returns the current number of resizeQueue.
     * 
     * @param 参数说明
     * @return the number of resizeQueue
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
    boolean resizeQueue(int capacity);
}
