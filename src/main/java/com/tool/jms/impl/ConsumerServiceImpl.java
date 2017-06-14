package com.tool.jms.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class ConsumerServiceImpl {

	@Autowired
    private JmsTemplate jmsTemplate;  
   
	/** 
	 * 接收消息 
	 */  
	public TextMessage receive(Destination destination) {  
	    TextMessage tm = (TextMessage) jmsTemplate.receive(destination);  
	    try {  
	        System.out.println("从队列" + destination.toString() + "收到了消息：\t"  
	                + tm.getText());  
	        tm.getText();  
	        System.out.println(tm.toString());  
	    } catch (JMSException e) {  
	        e.printStackTrace();  
	    }  
	    return tm;  
	      
	}  
}
