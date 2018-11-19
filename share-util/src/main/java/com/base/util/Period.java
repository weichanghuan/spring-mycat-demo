package com.base.util;

/**
 * 周期描述类
 * 
 *
 *
 */
public class Period {
    /**
     * 周期域（参考Calendar类定义）
     */
    private int periodField;

    /**
     * 周期数值
     */
    private int periodCount;

    public int getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(int periodCount) {
        this.periodCount = periodCount;
    }

    public int getPeriodField() {
        return periodField;
    }

    public void setPeriodField(int periodField) {
        this.periodField = periodField;
    }

}

