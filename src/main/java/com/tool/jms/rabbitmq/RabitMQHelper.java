package com.tool.jms.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabitMQHelper {
	private static final Logger logger = LoggerFactory.getLogger(RabitMQHelper.class);
	
	private static ConcurrentHashMap<String, ConnectionFactory> factorySourceMap = new ConcurrentHashMap<String, ConnectionFactory>();
	ConnectionFactory factory = null;
    Connection connection = null;
	
	public RabitMQHelper() {
		initConnection(null);
	}
	
	public RabitMQHelper(String mqName) {
		initConnection(mqName);
	}
	
	public RabitMQHelper(String url, String username, String password, int port, String virtualHost) {
		StringBuilder key = new StringBuilder(url);
		key.append(port);
		key.append(username);
		key.append(password);
		key.append(virtualHost);
		if (!factorySourceMap.containsKey(key.toString())) {
			factory = new ConnectionFactory();
			factory.setHost(url);
	        factory.setUsername(username);
	        factory.setPassword(password);
	        factory.setPort(port);
	        factory.setVirtualHost(virtualHost);
	        factorySourceMap.put(key.toString(), factory);
		}
        initConnection(key.toString());
	}
	
	
	public void newInstance() {
	    	
	}
	 
	private void initConnection(String mqName) {
		if (factorySourceMap.isEmpty()) {
			System.out.println("未初始化BaseUtils");
		}
		factory = factorySourceMap.get(mqName);
		try {
			connection = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Channel createChannel() {
		try {
			return connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	};
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(final String queue, String message) throws Exception {
		//创建一个通道
        Channel channel = createChannel();
        //声明一个队列
        channel.queueDeclare(queue, true, false, false, null);
        //发送消息到队列中
        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道
        channel.close();
	}
	
	public void receive(final String queue) throws Exception {
		 //创建一个通道
        Channel channel = createChannel();
        //声明要关注的队列
        channel.queueDeclare(queue, true, false, false, null);
        System.out.println("Customer Waiting Received messages");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel)  {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
//                System.out.println("Customer Received '" + message + "'");
                showMessage(message);
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(queue, false, consumer);
	}
	
	public void showMessage(String msg) {
		System.out.println(msg);
	}
	
	public static void main(String[] args) {
		try {
			RabitMQHelper rabbitMQ = new RabitMQHelper("192.168.1.190", "wangyj", "123456", 5672, "/");
			rabbitMQ.receive("rabbitMQ.test1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
