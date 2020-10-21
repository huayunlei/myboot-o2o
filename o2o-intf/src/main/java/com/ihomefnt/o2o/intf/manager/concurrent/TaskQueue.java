package com.ihomefnt.o2o.intf.manager.concurrent;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 功能描述：TaskQueue
 * 
 * @author 作者12100579
 */
public class TaskQueue extends LinkedBlockingQueue<Runnable> {

    private static final long serialVersionUID = 1L;

    private ThreadPoolExecutor parent = null;

    /**
     * no need to be volatile, the one times when we change and read it occur in a single thread (the one that did stop
     * a context and fired listeners)
     */
    private Integer forcedRemainingCapacity = null;

    public TaskQueue() {
        super();
    }

    public TaskQueue(int capacity) {
        super(capacity);
    }

    public TaskQueue(Collection<? extends Runnable> c) {
        super(c);
    }

    public void setParent(ThreadPoolExecutor tp) {
        parent = tp;
    }

    public boolean force(Runnable o) {
        if (parent.isShutdown()) {
            throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
        }
        // forces the item onto the queue, to be used if the task is rejected
        return super.offer(o);
    }

    public boolean force(Runnable o, long timeout, TimeUnit unit) throws InterruptedException {
        if (parent.isShutdown()) {
            throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
        }
        // forces the item onto the queue,to be used if the task is rejected
        return super.offer(o, timeout, unit);
    }

    @Override
    public boolean offer(Runnable o) {
        // we can't do any checks
        if (parent == null) {
            return super.offer(o);
        }
        // we are maxed out on threads, simply queue the object
        if (parent.getPoolSize() == parent.getMaximumPoolSize()) {
            return super.offer(o);
        }
        // we have idle threads, just add it to the queue
        if (parent.getSubmittedCount() < (parent.getPoolSize())) {
            return super.offer(o);
        }
        // if we have less threads than maximum force creation of a new thread
        if (parent.getPoolSize() < parent.getMaximumPoolSize()) {
            return false;
        }
        // if we reached here, we need to add it to the queue
        return super.offer(o);
    }

    @Override
    public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
        Runnable runnable = super.poll(timeout, unit);
        if (runnable == null && parent != null) {
            // the poll timed out, it gives an opportunity to stop the current thread if needed to avoid memory leaks.
            parent.stopCurrentThreadIfNeeded();
        }
        return runnable;
    }

    @Override
    public Runnable take() throws InterruptedException {
        if (parent != null && parent.currentThreadShouldBeStopped()) {
            /**
             * yes, this may return null (in case of timeout) which normally does not occur with take() but the
             * ThreadPoolExecutor implementation allows this
             */
            return poll(parent.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
        }
        return super.take();
    }

    @Override
    public int remainingCapacity() {
        if (forcedRemainingCapacity != null) {
            /**
             * ThreadPoolExecutor.setCorePoolSize checks that remainingCapacity==0 to allow to interrupt idle threads I
             * don't see why, but this hack allows to conform to this "requirement"
             */
            return forcedRemainingCapacity.intValue();
        }
        return super.remainingCapacity();
    }

    public void setForcedRemainingCapacity(Integer forcedRemainingCapacity) {
        this.forcedRemainingCapacity = forcedRemainingCapacity;
    }
}
