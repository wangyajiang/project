package com.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQProducer {
	public final static String QUEUE_NAME = "company_mq";
	public final static String exchange= "";
    
	public static void main(String[] args)  throws IOException, TimeoutException {
		//创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setUsername("wangyj");
        factory.setPassword("123456");
        factory.setHost("192.168.1.190");
        factory.setVirtualHost("/");
        factory.setPort(5672);

    	//创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel =  connection.createChannel();
        
        System.out.println(connection.getChannelMax());
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World1";
        //发送消息到队列中
        channel.basicPublish(exchange, QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道和连接
        channel.close();
        connection.close();
	}

}
