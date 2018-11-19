package com.spring.demo.web.controller;

import com.aliyuncs.exceptions.ClientException;
import com.spring.demo.model.Log;
import com.spring.demo.service.LogService;
import com.spring.demo.web.utils.CookieUtil;
import com.spring.demo.web.utils.JSONUtil;
import com.spring.demo.web.utils.SmsDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final String[] codes={"10520","11520","12520","13520","14520","15520","16520","17520","18520","19520"};

    private  static final String[] mobiles={"17621053900"};

    @Autowired
    private LogService logService;

    /**
     * chk请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/chk.htm")
    public String chk(HttpServletRequest httpServletRequest) {
        logger.info("进入chk");
        return "chk";
    }


    /**
     * 首页请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/index.htm")
    public String login(HttpServletRequest httpServletRequest) {
        logger.info("进入首页控制器");
        return "index_";
    }


    /**
     * 发送手机短信
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/code.htm", method = RequestMethod.GET,produces = "text/html; charset=utf-8")
    @ResponseBody
    public String code(String mobile) {
        logger.info("进入发送短信控制器");
        Map<String,String> map=new HashMap<>();
        if(null==mobile ||mobile.equals("")){
            map.put("result","请输入有效手机号(该系统,只限代安娜童鞋)");
            return JSONUtil.toJSonString(map);
        }

        if(mobile.equals(mobiles[0])||mobile.equals(mobiles[1])){
            Random r = new Random();
            int i = r.nextInt(10);
            try {
                SmsDemo.sendSms(mobile,codes[i]);
                map.put("result","已发送，请查收(只限代安娜童鞋),下一步的账号：anna，密码：anna");
                return JSONUtil.toJSonString(map);
            } catch (ClientException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        map.put("result","手机号有误，(该系统只限代安娜童鞋玩耍)");
        return JSONUtil.toJSonString(map);
    }


    /**
     * 验证手机和手机验证码页面，
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/mobile.htm")
    @ResponseBody
    public String mobile(HttpServletRequest httpServletRequest) {
        logger.info("进入首页控制器");
        Map<String,String> map=new HashMap<>();
        String mobile = httpServletRequest.getParameter("mobile");
        String code = httpServletRequest.getParameter("code");
        if(mobiles[0].equals(mobile) || mobiles[1].equals(mobile)){
            for (int i=0;i<codes.length;i++){
                if(codes[i].equals(code)){
                    map.put("result","1");
                    return JSONUtil.toJSonString(map);
                }
            }
        }
        map.put("result","0");
        return JSONUtil.toJSonString(map);
    }

    /**
     * 首页请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/index2.htm")
    public String index(HttpServletRequest httpServletRequest) {
        logger.info("进入首页控制器");
        return "index";
    }


    /**
     * 认证登陆
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET,produces = "text/html; charset=utf-8")
    @ResponseBody
    public String login(String name, String pwd, HttpServletResponse response) {
        logger.info("进入登陆控制器");
        Map<String,String> map=new HashMap<>();
        Log log=new Log();
        log.setCreated(new Date());
        log.setName(name);
        log.setPwd(pwd);


        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if("anna".equals(name) && "anna".equals(pwd)){
                map.put("result","1");
                log.setSuccess("1");
                logService.insertSelective(log);
                CookieUtil.setCookie(1, "name", name, 3600, "/", response);
                CookieUtil.setCookie(1, "pwd", pwd, 3600, "/", response);
                return JSONUtil.toJSonString(map);

        }
        map.put("result","该系统仅限代安娜童鞋玩耍");
        log.setSuccess("0");
        logService.insertSelective(log);
        return JSONUtil.toJSonString(map);
    }

    /**
     * 蛋糕页面请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/birthdayCake.htm")
    public String birthdayCake(HttpServletRequest httpServletRequest) {
        logger.info("进入蛋糕页面");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //if(date.equals("2018-11-02")){
            return "BirthdayCake";
        //}
    }


    /**
     * 蛋糕页面请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/img.htm")
    public String img(HttpServletRequest httpServletRequest) {
        logger.info("进入img页面");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            return "img";
    }


    /**
     * 文字页面请求跳转
     *
     * @param log
     * @return
     */
    @RequestMapping(value = "/memories.htm")
    public String memories(HttpServletRequest httpServletRequest) {
        logger.info("进入memories页面");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            return "Memories";
    }

}
