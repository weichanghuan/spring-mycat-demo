package com.base.util;

/**
 * 任务接口类
 */
public interface Task<A, R, T extends Throwable> {
    /**
     * 执行方法
     *
     * @param args
     * @return
     * @throws T
     */
    public R execute(A... args) throws T;
}
