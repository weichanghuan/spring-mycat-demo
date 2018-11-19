#!/usr/bin/env bash
#编译+部署（项目）站点

#需要配置如下参数
# 项目路径，在Execute Shell 中配置项目路径，pwd就可以获取该项目路径
# export PROJ_PATH=这个jenkins任务在部署机器上的路径

#输入你的环境上tomcat的全路径
# export TOMCAT_APP_PATH=tomcat在部署机器上的路径

### base 函数
killTomcat()
{
        pid =`ps -ef|grep tomcat|grep java|awk '{print $2}'`
        echo "tomcat ID list :$pid "
        if [ "$pid" = ""]
        then
           echo "no tomcat pid alive"
         else
           kill -9 $pid
          fi
}
cd $PROJ_PATH/spring-temp-web/spring-mycat-demo-web
mvn clean install -Dmaven.test.skip=true

# 停tomcat
killTomcat

# 删除原有工程
rm -rf $TOMCAT_APP_PATH/webapps/ROOT
rm -f $TOMCAT_APP_PATH/webapps/ROOT.war
rm -f $TOMCAT_APP_PATH/webapps/spring-mycat-demo-web-1.0.0-SNAPSHOT.war

#复制新的工程
cp $PROJ_PATH/spring-temp-web/spring-mycat-demo-web/target/spring-mycat-demo-web-1.0.0-SNAPSHOT.war $TOMCAT_APP_PATH/webapps/

cd $TOMCAT_APP_PATH/webapps/
mv spring-mycat-demo-web-1.0.0-SNAPSHOT.war ROOT.war

#启动tomcat
cd $TOMCAT_APP_PATH/
sh bin/startup.sh
