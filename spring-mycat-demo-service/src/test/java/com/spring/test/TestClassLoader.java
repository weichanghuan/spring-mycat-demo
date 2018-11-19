package com.spring.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;

public class TestClassLoader {

    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    public void testClassLoader() {
        logger.info("initClient memcached");
        Properties config = new Properties();
        InputStream input = null;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoader cl1 = TestClassLoader.class.getClassLoader();
        try {
            input = cl.getResourceAsStream("memcache1.properties");
            cl.getResource("memcache1.properties");
            URL resource = cl1.getResource("memcache1.properties");
            Reader reader = new InputStreamReader(input, "UTF-8");
            config.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("memcache1.properties load failed!", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        String addresses = config.getProperty("addresses");
        String channel = config.getProperty("channel");
        logger.info("addresses={},channel={}",addresses,channel);
    }


    public static void main(String[] args) {
        TestClassLoader testClassLoader=new TestClassLoader();
        testClassLoader.testClassLoader();
    }



}
