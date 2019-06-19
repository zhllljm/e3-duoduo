package com.zhl.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

public class ActiveMqTest {

    /**
     * 点到点形式发送消息
     *
     * @throws Exception
     */
    @Test
    public void testQueueProducer() throws Exception {
        //创建一个连接工厂对象，需要指定服务的IP及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //开启连接，调用connection对象的Start方法
        connection.start();
        //创建session对象
        //第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务false。
        //第二个参数：应答模式。一般自动应答或者手动应答。一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个Destination对象。
        //两种形式queue,topic,现在应该使用queue
        Queue queue = session.createQueue("test-queue");
        // 使用session对象创建一个Producer对象。
        MessageProducer producer = session.createProducer(queue);
        //创建一个message对象，可以使用TextMessage.
        /*  TextMessage textMessage = new ActiveMQTextMessage();*/
        /*textMessage.setText("Hello ActiveMq");*/
        TextMessage textMessage = session.createTextMessage("Hello ActiveMq");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
        // 创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象，queue对象
        Queue queue = session.createQueue("test-queue");
        //使用Session对象创建一个消费对象
        MessageConsumer consumer = session.createConsumer(queue);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    // 打印消息。
                    System.out.println(text);

                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
        //等待接收消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
        ;
    }

}
