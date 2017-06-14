package com.tool.jms;

import javax.jms.Destination;

import com.tool.jms.impl.ProducerServiceImpl;

public class JmsUtils {

	private static ProducerServiceImpl producerService;
	private static Destination logQueueDestination;
	
	 public JmsUtils(ProducerServiceImpl producerService, Destination logQueueDestination) {
	    	JmsUtils.producerService = producerService;
	    	JmsUtils.logQueueDestination = logQueueDestination;
	    }
	
	public static void sendLogMsg(String msg) {
		producerService.sendMessage(logQueueDestination, msg);
	}
	
	
}
