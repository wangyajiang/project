package com.tool.cache.redis;

/**
 * auto:wyj
 */
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class RedisPool {
	private static final Logger log = LoggerFactory.getLogger(RedisPool.class);
	private int maxTotal = 10;
	private int maxIdle = 1;
	private int maxWaitMillis = 5 * 1000;// 从池里链接获取时间
	private int soTimeout = 3 * 1000;// 读取数据时间
	private int connectionTimeout = 3 * 1000;// 建立链接时间
	private String server;

	private ShardedJedisPool pool;

	public ShardedJedisPool getPool() {
		return pool;
	}

	public void initialize() {
		//池基本配置   
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		String[] servers = server.split(",");
		for (String server : servers) {
			String[] arr = server.split(":", 4);
			JedisShardInfo info = null;
			switch (arr.length) {
				case 2 :
					info = new JedisShardInfo(arr[0], Integer.valueOf(arr[1]));
					break;
				case 3 :
					info = new JedisShardInfo(arr[0], Integer.valueOf(arr[1]), arr[2]);
					break;
				case 4 :
					info = new JedisShardInfo(arr[0], Integer.valueOf(arr[1]), arr[2]);
					info.setPassword(arr[3]);
					break;
			}
			info.setTimeout(connectionTimeout);
			shards.add(info);
		}

		System.out.println("in redis----------");
		pool = new ShardedJedisPool(config, shards);
		log.debug("init redis pool {}", server);
	}

	public void shutDown() {
		System.out.println("close redis");
		log.debug("shutDown redis pool");
		if (pool != null) {
			pool.destroy();
			pool = null;
		}
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
