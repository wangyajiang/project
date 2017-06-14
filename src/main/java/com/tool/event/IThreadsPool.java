package com.tool.event;

public interface IThreadsPool {
	public void start();

	public void stop();

	public void stopnow(boolean discart);
}
