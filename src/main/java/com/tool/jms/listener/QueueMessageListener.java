package com.tool.jms.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueMessageListener implements MessageListener {

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;  
        try {  
            System.out.println("QueueMessageListener监听到了文本消息：\t"  + tm.getText());      
        } catch (Exception e){ 
        	e.printStackTrace();   
        }   
	}

}
