package com.spring.test;

import com.spring.demo.model.Person;
import com.spring.demo.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2018/8/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class TestDemo {


    @Autowired
    private PersonService personService;

    // 测试专用
    @Test
    public void selectByPrimaryKey() {
        System.out.println("测试方法开始");
        Person p = personService.selectByPrimaryKey(1L);
        System.out.println(p.getName());
        System.out.println("测试方法结束");
    }
}
