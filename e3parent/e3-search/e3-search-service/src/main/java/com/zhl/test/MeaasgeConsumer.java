package com.zhl.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeaasgeConsumer {

    public  void  msgConsumer() throws  Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");        //等待
        //等待
        System.in.read();
    }
}
