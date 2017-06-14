package com.tool.cache;

import java.util.List;
import java.util.Map;

/**
 * 缓存接口，当前支持memcached，redis memcached有部分接口不能支持
 * 缓存操作不会抛出异常，只是打日志，可以根据返回值来判断是否成功，带cacheSource的会抛出Throwable，这个异常来自getValue操作。
 * 
 * @author wyj
 *
 */
public interface CacheService {
	/**
	 * 查询一个缓存（非计数,非hash），如果不存在就用存入value，并返回value 如果出现异常也会返回value
	 * 
	 * @param source
	 * @return
	 */
	public Object getCacheable(CacheSource source) throws Throwable;

	/**
	 * 查询一个带超时时间的缓存（非计数,非hash），如果不存在就用存入value，并返回value 如果expiry==0 等同于未设置过期时间
	 * 如果出现异常也会返回value
	 * 
	 * @param source
	 * @param expiry
	 *            多少毫秒后过期
	 * @return
	 */
	public Object getCacheable(CacheSource source, long expiry) throws Throwable;

	/**
	 * 查询一个带超时时间的缓存（非计数,非hash），并存入value，如果expiry==0 等同于未设置过期时间，返回缓存里的值
	 * 如果出现异常也会返回null
	 * 
	 * @param source
	 * @param expiry
	 *            多少毫秒后过期
	 * @return
	 */
	public Object getAndSet(CacheSource source, long expiry) throws Throwable;

	/**
	 * 设置一个缓存（非计数,非hash），不带过期时间 如果出现异常就是设置不成功
	 * 
	 * @param source
	 * @return
	 */
	public void setCacheable(CacheSource source) throws Throwable;

	/**
	 * 设置一个带超时时间的缓存（非计数,非hash） 如果expiry==0 等同于未设置过期时间， 如果出现异常就是设置不成功
	 * 
	 * @param source
	 * @return
	 */
	public void setCacheable(CacheSource source, long expiry) throws Throwable;

	/**
	 * 查询一个计数器，如果不存在就用存入value，并返回value 如果出现异常也会返回value
	 * 
	 * @param source
	 * @param expiry
	 *            多少毫秒后过期
	 * @return
	 */
	public long getCounterable(CacheSource source) throws Throwable;

	/**
	 * 用value的值累加一个计数器，如果不存在就用存入value，并返回value 如果出现异常也会返回value
	 * 
	 * @param source
	 * @return
	 */
	public long incCacheable(CacheSource source) throws Throwable;

	/**
	 * 替换一个缓存（非计数,非hash），如果不存在就什么都不做，存在的话就用value替换 如果出现异常也说明都不做
	 * 
	 * @param source
	 * @return
	 */
	public void replaceCacheable(CacheSource source) throws Throwable;

	/**
	 * 查询一个hash，如果不存在就返回null 如果出现异常也会返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return 一个包含所有field的map
	 */
	public Map<String, String> getHashAll(String key);
	
	/**
	 * 查询一个hash的某个field，如果不存在就返回null 如果出现异常也会返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return 一个包含所有field的map
	 */
	public String getHash(String key,String field);

	/**
	 * 累加一个hash的key，如果不存在就返回0 如果出现异常也会返回0,memcached不具备这个功能
	 * 
	 * @param key
	 * @param field
	 *            hash里的某个key
	 * @param increment
	 *            增量，如果是减就填负数
	 * @return
	 */
	public long incHash(String key, String field, long increment);

	/**
	 * 设置一个hash的某个field的值，这里目前只支持String,如果是整形也可以存到字符串里累加,memcached不具备这个功能
	 * 
	 * @param key
	 * @param field
	 * @param val
	 */
	public void setHash(String key, String field, String val);

	/**
	 * 查询一个对象缓存（非计数,非hash） 如果出现异常也返回null
	 * 
	 * @param key
	 * @return
	 */
	public Object getCache(String key);

	/**
	 * 设置一个不带过期时间的 cache，如果出现异常就是设置不成功
	 * 
	 * @param key
	 * @param val
	 */
	public void setCache(String key, Object val);

	/**
	 * 设置一个带过期时间的 cache，如果expiry==0 等同于未设置过期时间，如果出现异常就是设置不成功
	 * 
	 * @param key
	 * @param val
	 * @param expiry
	 *            多少毫秒后过期
	 */
	public void setCache(String key, Object val, long expiry);

	/**
	 * 删除一个缓存 如果出现异常就是删除不成功
	 * 
	 * @param key
	 * @return
	 */
	public void deleteCache(String key);

	/**
	 * 计数，如果出现异常返回0
	 * 
	 * @param key
	 * @param increment
	 *            增量，如果是减就填负数
	 * @return
	 */
	public long incCache(String key, long increment);

	/**
	 * 查询一个计数器，如果出现异常返回0
	 * 
	 * @param key
	 * @return
	 */
	public long getCounter(String key);

	/**
	 * 设置key的过期时间,memcached不具备这个功能
	 * 
	 * @param key
	 * @param expiry
	 *            多少毫秒后过期
	 */
	public void expire(String key, long expiry);
	
	
	/**
	 * 批量查询一堆字符串，不要超过1W个 ,memcached不具备这个功能
	 * @param keys
	 * @return
	 */
	public Map<String, String> batchGetString(List<String> keys);
	
	
	/**
	 * 批量更新一堆字符串，不要超过1W个,memcached不具备这个功能
	 * @param kv
	 * @param expiry
	 */
	public void batchSetString(Map<String, String> kv, long expiry);
	
	
	/**
	 * 设置一个不带过期时间的 字符串，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 */
	public void setString(String key, String val);

	/**
	 * 设置一个带过期时间的 字符串，如果expiry==0 等同于未设置过期时间，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 * @param expiry
	 *            多少毫秒后过期
	 */
	public void setString(String key, String val, long expiry);
	

	/**
	 * 查询一个字符串  如果出现异常也返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);
	
	
	
	
	/**
	 * 设置一个不带过期时间的 对象，通过JSON存储，内存占用总体会比JAVA序列号少很多，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 */
	public void setObjectByJson(String key, Object val);

	/**
	 * 设置一个带过期时间的 对象，通过JSON存储，内存占用总体会比JAVA序列号少很多，如果expiry==0 等同于未设置过期时间，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 * @param expiry 多少毫秒后过期
	 */
	public void setObjectByJson(String key, Object val, long expiry);
	

	/**
	 * 查询一个通过JSON存储的对象， 如果出现异常也返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getObjectByJson(String key, Class<T> clazz);
	
	/**
	 * 查询一个通过JSON存储的List对象，  如果出现异常也返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return
	 */
	public <T> List<T> getListByJson(String key, Class<T> clazz);
	
	/**
	 * 查询一个通过JSON存储的Map对象，  如果出现异常也返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @param kc key的Class
	 * @param vc val的Class
	 * @return
	 */
	public <K, V> Map<K, V> getMapByJson(String key,Class<K> kc, Class<V> vc);
	
	
	//=====================================队列
	
	/**
	 * 队列-顺序存储
	 * 设置一个不带过期时间的 对象，通过JSON存储，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 */
	public void lpushObjectByJson(String key, Object val);
	
	/**
	 * 队列-反序存储
	 * 设置一个不带过期时间的 对象，通过JSON存储，如果出现异常就是设置不成功,memcached不具备这个功能
	 * 
	 * @param key
	 * @param val
	 */
	public void rpushObjectByJson(String key, Object val);
	
	/**
	 * 队列-取值
	 * 查询一个通过JSON存储的对象， 如果出现异常也返回null,memcached不具备这个功能
	 * 
	 * @param key
	 * @return
	 */
	public <T> T lpopObjectByJson(String key, Class<T> clazz);
}
