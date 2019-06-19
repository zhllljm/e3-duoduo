package com.zhl.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class ActiveMqSpring {

    @Test
    public  void sendMessage() throws  Exception {
        //初始化spring容器
        ApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容器中获得JmsTemplate对象
        JmsTemplate jmsTemplate = application.getBean(JmsTemplate.class);
        //从容器中获得一个Destination对象
        Destination destination = (Destination) application.getBean("queueDestination");
        //发送消息
        jmsTemplate.send(String.valueOf(destination), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //创建一个消息对象并返回
                TextMessage textMessage = session.createTextMessage("spring activemq queue message");
                return textMessage;
            }
        });

    }
    }
