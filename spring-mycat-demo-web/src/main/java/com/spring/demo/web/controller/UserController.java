package com.spring.demo.web.controller;

import com.spring.demo.model.Person;
import com.spring.demo.service.PersonService;
import com.spring.demo.web.utils.JSONUtil;
import com.spring.demo.web.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Administrator on 2018/8/5.
 */
@Controller
@Api(tags = "用户查询")
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonService personService;


    //解决response中文变成问号
    @RequestMapping(value = "/selectPerson.htm", produces = "text/html; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询方法", httpMethod = "GET")
    public String selectByPrimaryKey(UserVO userVO) {
        // Person person = personService.selectByPrimaryKey(userVO.getId());
        Person person = new Person();
        person.setId(1L);
        person.setAge(20);
        person.setName("危常焕");
        return JSONUtil.toJSonString(person);
    }

    //解决response中文变成问号
    @RequestMapping(value = "/testLongLink.htm", produces = "text/html; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "测试长链接", httpMethod = "GET")
    public void testLongLink(UserVO userVO, HttpServletResponse response) {
        try {
            log.info("连接已经建立:{}", Thread.currentThread().getName());
            if (userVO == null || userVO.getId() == null) {
                return;
            }
            response.setContentType("text/event-stream");
            response.setHeader("Cache-Control", "no-cache");//规定不对页面进行缓存
            response.setHeader("Connection", "keep-alive");//长连接
            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();
            while (true) {
                Random r = new Random();
                Thread.sleep(1000);
                System.out.println("执行ing");
                pw.write("data:Testing 1,2,3" + r.nextInt() + "\n\n");
                pw.flush();
                if (pw.checkError()) {
                    System.out.println("执行end");
                    return;
                }


            }
        } catch (Exception e) {
            log.error("异常");
        }
    }

}
