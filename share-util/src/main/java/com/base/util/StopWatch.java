package com.base.util;

/**
 * 简单秒表实现类
 */
public class StopWatch {
    /**
     * 是否在运行
     */
    private boolean running = false;

    /**
     * 起始时间(单位:毫秒)
     */
    private long startTime = -1;

    /**
     * 停止时间(单位:毫秒)
     */
    private long stopTime = -1;

    /**
     * 标记时间(单位:毫秒)
     */
    private long markTime = -1;

    /**
     * 新建一个秒表类并触发start方法
     *
     * @return
     */
    public static StopWatch startNew() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        return stopWatch;
    }

    /**
     * 开始计时
     *
     * @return 当前时间(单位 : 毫秒)
     */
    public long start() {
        if (running == true) {
            throw new IllegalStateException("Stopwatch already started");
        }

        startTime = System.currentTimeMillis();
        markTime = -1;
        stopTime = -1;
        running = true;

        return startTime;
    }

    /**
     * 计时标记
     *
     * @return 返回上一个标记至本次标记的时差，如是第一次标记则返回与起始时间的时差(单位:毫秒)
     */
    public long mark() {
        if (running == false) {
            throw new IllegalStateException("Stopwatch hasn't been started");
        }

        if (markTime == -1) {
            markTime = startTime;
        }

        long now = System.currentTimeMillis();
        long duration = now - markTime;
        markTime = now;
        return duration;
    }

    /**
     * 计时停止
     *
     * @return 返回结束与起始时间的时差(单位 : 毫秒)
     */
    public long stop() {
        if (running == false) {
            throw new IllegalStateException("Stopwatch hasn't been started");
        }

        running = false;
        stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }

    /**
     * 获得起始时间
     *
     * @return 起始时间(单位 : 毫秒)
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 获得结束时间
     *
     * @return 结束时间(单位 : 毫秒)
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * 获得从开始至今的运行时间
     *
     * @return 运行时间(单位 : 毫秒)
     */
    public long getRunTime() {
        if (running == false) {
            throw new IllegalStateException("Stopwatch hasn't been started");
        }

        return System.currentTimeMillis() - startTime;
    }

    /**
     * 获得上次标记时间
     *
     * @return 上次标记时间(单位 : 毫秒)
     */
    public long getLastMarkTime() {
        return markTime;
    }

    /**
     * 是否正在运行
     *
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }
}
