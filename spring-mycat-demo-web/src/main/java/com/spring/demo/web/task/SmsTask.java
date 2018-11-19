package com.spring.demo.web.task;


import com.spring.demo.web.interceptor.LoginInterceptor;
import com.spring.demo.web.utils.SmsDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmsTask {

    private final Logger logger = LoggerFactory.getLogger(SmsTask.class);

    //每分钟都执行
    @Scheduled(cron = "00 00 8 2 11 ?")
    public void test()throws Exception{
        logger.info("执行定时任务");
        SmsDemo.sendSmsTask("");
    }

    //每分钟都执行
    @Scheduled(cron = "0 58 18 1 11 ? ")
    public void test1(){
        logger.info("执行定时任务");
        System.out.println("执行");
    }
}
