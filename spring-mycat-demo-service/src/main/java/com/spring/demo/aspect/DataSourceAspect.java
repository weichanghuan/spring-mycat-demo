package com.spring.demo.aspect;

import org.aspectj.lang.JoinPoint;


/**
 * Created by Administrator on 2018/8/3.
 */
public class DataSourceAspect {

    /**
     * 在进入Service方法之前执行 * * @param point 切面对象
     */
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // 标记为从库
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_SLAVER);
        } else {
            // 标记为主库
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_MASTER);
        }
    }

    /**
     * 判断是否为读库 * * @param methodName * @return
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return methodName.contains("select");
    }
}
