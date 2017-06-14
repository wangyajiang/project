package com.tool.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

import com.tool.cache.CacheService;
import com.tool.cache.CacheSource;
import com.tool.cache.redis.RedisPool;
import com.tool.cache.redis.SerializeUtil;
import com.tool.utils.ConvertUtils;

/**
 *  @author wyj
 */
public class RedisServiceImpl implements CacheService {
	private final static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
	private ShardedJedisPool pool;

	public ShardedJedisPool getPool() {
		return pool;
	}

	public RedisServiceImpl(RedisPool redisPool) {
		this.pool = redisPool.getPool();
	}

	public Object getCacheable(CacheSource source) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object v = null;
			try {
				v = SerializeUtil.unserialize(jedis.get(key.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
			if (null == v) {
				v = source.getValue();
				if (null != v) {
					try {
						jedis.set(key.getBytes(), SerializeUtil.serialize(v));
					} catch (Throwable e) {
						e.printStackTrace();
						logger.error(key, e);
						needReturn = false;
					}
				}
			}
			return v;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	public Object getCacheable(CacheSource source, long expiry) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object v = null;
			try {
				v = SerializeUtil.unserialize(jedis.get(key.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
			if (null == v) {
				v = source.getValue();
				if (null != v) {
					try {
						if (expiry == 0l)
							jedis.set(key.getBytes(), SerializeUtil.serialize(v));
						else
							jedis.setex(key.getBytes(), (int) (expiry / 1000l), SerializeUtil.serialize(v));
					} catch (Throwable e) {
						e.printStackTrace();
						logger.error(key, e);
						needReturn = false;
					}
				}
			}
			return v;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public Object getAndSet(CacheSource source, long expiry) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object v = null;
			try {
				v = SerializeUtil.unserialize(jedis.get(key.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
			if (null == v) {
				Object n = source.getValue();
				if (null != n) {
					try {
						if (expiry == 0l)
							jedis.set(key.getBytes(), SerializeUtil.serialize(n));
						else
							jedis.setex(key.getBytes(), (int) (expiry / 1000l), SerializeUtil.serialize(n));
					} catch (Throwable e) {
						e.printStackTrace();
						logger.error(key, e);
						needReturn = false;
					}
				}
			}
			return v;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setCacheable(CacheSource source) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object val = source.getValue();
			if (null != val) {
				try {
					jedis.set(key.getBytes(), SerializeUtil.serialize(val));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setCacheable(CacheSource source, long expiry) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object val = source.getValue();
			if (null != val) {
				try {
					if (expiry == 0l)
						jedis.set(key.getBytes(), SerializeUtil.serialize(val));
					else
						jedis.setex(key.getBytes(), (int) (expiry / 1000l), SerializeUtil.serialize(val));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public long getCounterable(CacheSource source) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			String vs = null;
			try {
				vs = jedis.get(key);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
			if (vs != null) {
				return Long.valueOf(vs);
			}

			Long val = (Long) source.getValue();
			if (val != null && val != -1L) {
				try {
					return jedis.incrBy(key, (Long) val);
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
			return val;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public long incCacheable(CacheSource source) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String key = source.getKey();
			Object val = source.getValue();
			if (null != val) {
				try {
					return jedis.incrBy(key, (Long) val);
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
			return (Long) val;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void deleteCache(String key) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			jedis.del(key.getBytes());
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	public void replaceCacheable(CacheSource source) throws Throwable {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		String key = source.getKey();
		try {
			if (jedis.exists(key.getBytes())) {
				Object val = source.getValue();
				if (null != val) {
					jedis.set(key.getBytes(), SerializeUtil.serialize(val));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public Map<String, String> getHashAll(String key) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			return jedis.hgetAll(key);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public String getHash(String key, String field) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			return jedis.hget(key, field);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public long incHash(String key, String field, long increment) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			return jedis.hincrBy(key, field, increment);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
		return 0l;
	}

	@Override
	public void setHash(String key, String field, String val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			jedis.hset(key, field, val);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public Object getCache(String key) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				return SerializeUtil.unserialize(jedis.get(key.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public void setCache(String key, Object val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					jedis.set(key.getBytes(), SerializeUtil.serialize(val));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setCache(String key, Object val, long expiry) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					if (expiry == 0l)
						jedis.set(key.getBytes(), SerializeUtil.serialize(val));
					else
						jedis.setex(key.getBytes(), (int) (expiry / 1000l), SerializeUtil.serialize(val));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public long incCache(String key, long increment) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			return jedis.incrBy(key, (Long) increment);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
		return 0l;
	}

	@Override
	public long getCounter(String key) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			String vs = null;
			try {
				vs = jedis.get(key);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
			if (vs != null) {
				return Long.valueOf(vs);
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return 0l;
	}

	@Override
	public void expire(String key, long expiry) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			jedis.expire(key, (int) (expiry / 1000l));
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(key, e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	public Map<String, String> batchGetString(List<String> keys) {
		Map<String, String> result = new HashMap<String, String>();
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		ShardedJedisPipeline p = jedis.pipelined();
		try {
			for (String key : keys) {
				p.get(key);
			}
			List<Object> objects = p.syncAndReturnAll();

			for (int i = 0; i < keys.size(); i++) {
				if (objects.get(i) != null) {
					result.put(keys.get(i), objects.get(i).toString());
				} else {
					result.put(keys.get(i), null);
				}
			}
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("", e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public void batchSetString(Map<String, String> kv, long expiry) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		ShardedJedisPipeline p = jedis.pipelined();
		try {
			for (String key : kv.keySet()) {
				if (expiry == 0l)
					p.set(key.getBytes(), kv.get(key).getBytes("utf-8"));
				else
					p.setex(key.getBytes(), (int) (expiry / 1000l), kv.get(key).getBytes("utf-8"));
			}
			p.sync();
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("", e);
			needReturn = false;
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setString(String key, String val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					jedis.set(key.getBytes(), val.getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setString(String key, String val, long expiry) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					if (expiry == 0l)
						jedis.set(key.getBytes(), val.getBytes("utf-8"));
					else
						jedis.setex(key.getBytes(), (int) (expiry / 1000l), val.getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public String getString(String key) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				byte[] v = jedis.get(key.getBytes());
				if (v != null)
					return new String(jedis.get(key.getBytes()), "utf-8");
				else
					return null;
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public void setObjectByJson(String key, Object val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					jedis.set(key.getBytes(), ConvertUtils.object2json(val).getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public void setObjectByJson(String key, Object val, long expiry) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					if (expiry == 0l)
						jedis.set(key.getBytes(), ConvertUtils.object2json(val).getBytes("utf-8"));
					else
						jedis.setex(key.getBytes(), (int) (expiry / 1000l), ConvertUtils.object2json(val).getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}

	@Override
	public <T> T getObjectByJson(String key, Class<T> clazz) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				byte[] v = jedis.get(key.getBytes());
				if (v != null)
					return ConvertUtils.json2object(new String(jedis.get(key.getBytes()), "utf-8"), clazz);
				else
					return null;
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public <T> List<T> getListByJson(String key, Class<T> clazz) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				byte[] v = jedis.get(key.getBytes());
				if (v != null)
					return ConvertUtils.json2list(new String(jedis.get(key.getBytes()), "utf-8"), clazz);
				else
					return null;
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	@Override
	public <K, V> Map<K, V> getMapByJson(String key, Class<K> kc, Class<V> vc) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				byte[] v = jedis.get(key.getBytes());
				if (v != null)
					return ConvertUtils.json2map(new String(jedis.get(key.getBytes()), "utf-8"), kc, vc);
				else
					return null;
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}

	public void lpushObjectByJson(String key, Object val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					jedis.lpush(key.getBytes(), ConvertUtils.object2json(val).getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}
	
	public void rpushObjectByJson(String key, Object val) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			if (null != val) {
				try {
					jedis.rpush(key.getBytes(), ConvertUtils.object2json(val).getBytes("utf-8"));
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(key, e);
					needReturn = false;
				}
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
	}
	
	public <T> T lpopObjectByJson(String key, Class<T> clazz) {
		ShardedJedis jedis = pool.getResource();
		boolean needReturn = true;
		try {
			try {
				byte[] v = jedis.lpop(key.getBytes());
				if (v != null)
					return ConvertUtils.json2object(new String(jedis.get(key.getBytes()), "utf-8"), clazz);
				else
					return null;
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(key, e);
				needReturn = false;
			}
		} finally {
			returnJedis(jedis, needReturn);
		}
		return null;
	}
	
	private void returnJedis(ShardedJedis jedis, boolean needReturn) {
		if (jedis != null) {
			if (needReturn)
				pool.returnResourceObject(jedis);
			else
				pool.returnBrokenResource(jedis);

		}
	}

	
	public static void main(String[] args) {
		RedisPool redisPool = new RedisPool();
		redisPool.setMaxIdle(1);
		redisPool.setMaxTotal(1);
		redisPool.setMaxWaitMillis(1000);
		redisPool.setConnectionTimeout(1000);
		redisPool.setSoTimeout(10);
		redisPool.setServer("127.0.0.1:6379:");
		redisPool.initialize();

		CacheService service = new RedisServiceImpl(redisPool);

		try {
//			service.deleteCache("focus_map_all");
//			System.out.println(service.getCache("focus_map_all"));
			long v = service.incHash("test_in_cachea", "asd", -3);
			System.out.println(v);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			redisPool.shutDown();
		}
	}


}
