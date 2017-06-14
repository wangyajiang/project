package com.tool.event.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tool.event.IThreadsPool;

public abstract class AbstractLoopThread implements IThreadsPool {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private int threadpoolsize = 1;
	private String name;
	private ExecutorService[] threadPools;
	
	protected boolean closing = false;
	private boolean needlog  = true;
	
	private ThreadLocal<Object> bundles = new ThreadLocal<Object>();

	public AbstractLoopThread(String name) {
		this.name = name;
	}
	
	public AbstractLoopThread(String name,boolean needlog) {
		this.name = name;
		this.needlog = needlog;
	}

	public void start() {
		threadPools = new ExecutorService[threadpoolsize];
		for (int i = 0; i < threadpoolsize; i++) {
			final int threadNum = i;
			threadPools[i] = Executors.newFixedThreadPool(1, new ThreadFactory() {
				public Thread newThread(Runnable r) {
					return new Thread(r, name + "-" + threadNum);
				}
			});

			doWork(threadPools[i], i);
			if(needlog){
				logger.debug("started {} >>>>>", name + "-" + threadNum);
			}
		}
	}

	public void stop() {
		stopnow(false);
	}
	
	public void stopnow(boolean discart) {
		closing = true;
		if(!discart){
			destory();
		}
		for (int i = 0; i < threadpoolsize; i++) {
			threadPools[i].shutdown();
			if(needlog){
				logger.debug("stoping. {} <<<<<", name + "-" + i);
			}
		}
		try {
			for (int i = 0; i < threadpoolsize; i++) {

				if (threadPools[i].awaitTermination(3, TimeUnit.SECONDS)) {
					if(needlog){
						logger.debug("grase stoped. {} <<<<<", name + "-" + i);
					}
				} else {
					threadPools[i].shutdownNow();
					if (threadPools[i].awaitTermination(3, TimeUnit.SECONDS)){
						if(needlog){
							logger.debug("force stoped. {} <<<<<", name + "-" + i);
						}
					}else {
						if(needlog){
							logger.error("error can't stop {} <<<<<", name + "-" + i);
						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			if(needlog){
				logger.error("", e);
			}
			for (int i = 0; i < threadpoolsize; i++) {
				threadPools[i].shutdownNow();
			}
		}
	}

	public void doWork(final ExecutorService exec, final int order) {
		exec.submit(new Runnable() {
			public void run() {
				work(exec, order);
			}
		});
	}

	public void work(ExecutorService exec, int order) {
		try {
			work(order);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//��CPU�����л�
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doWork(exec, order);
		}
	}

	public abstract void work(int order);

	public abstract void destory();

	public Object getBundle(){
		return bundles.get();
	}
	
	public void setBundle(Object value){
		bundles.set(value);
	}
	
	public int getThreadpoolsize() {
		return threadpoolsize;
	}

	public void setThreadpoolsize(int threadpoolsize) {
		this.threadpoolsize = threadpoolsize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
