package com.base.util;

/**
 * 获得目标类（用于Aop Proxy）。
 */
public interface TargetClassAware {
    Class<?> getTargetClass();
}
