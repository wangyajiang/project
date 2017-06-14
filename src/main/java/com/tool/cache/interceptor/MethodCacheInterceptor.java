package com.tool.cache.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tool.cache.CacheService;
import com.tool.cache.CacheSource;
import com.tool.cache.annotation.DelCache;
import com.tool.cache.annotation.GetCache;


public class MethodCacheInterceptor implements MethodInterceptor {
	Logger log = LoggerFactory.getLogger(MethodCacheInterceptor.class);
	CacheService cacheService;
	public static boolean cache = false;

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		long t1 = System.currentTimeMillis();
		final String methodName = invocation.getMethod().getDeclaringClass().getName() + "." +invocation.getMethod().getName();
		String logCacheKey = "";
		String logCacheDo = "";
		try {
			if (cache) {
				GetCache getCache = invocation.getMethod().getAnnotation(GetCache.class);
				DelCache delCache = invocation.getMethod().getAnnotation(DelCache.class);
				Object[] arguments = invocation.getArguments();

				if (getCache != null) {
					final String cacheKey = getCacheKey(getCache.name(), arguments, getCache.keyNum());
					logCacheKey = cacheKey;
					logCacheDo  = "getCacheable";
					
					return cacheService.getCacheable(new CacheSource() {
						public Object getValue() throws Throwable {
							log.debug("CACHE {}  no Cache {}", methodName, cacheKey);
							return invocation.proceed();
						}

						public String getKey() {
							return cacheKey;
						}
					}, getCache.expiry());
				} else if (delCache != null) {
					String[] names = delCache.names();
					for (String name : names) {
						String cacheKey = getCacheKey(name, arguments, delCache.keyNum());
						logCacheKey = cacheKey;
						logCacheDo  = "deleteCache";
						log.debug("CACHE {} deleteCache {}", methodName, cacheKey);
						cacheService.deleteCache(cacheKey);
					}
				}
			}
			return invocation.proceed();
		} finally {
			long t2 = System.currentTimeMillis();
			if(!logCacheDo.equals(""))
				log.debug("CACHE {} {} {} ({} milliseconds)", new Object[] { methodName, logCacheDo, logCacheKey, (t2 - t1)});
		}
	}

	private String getCacheKey(String prefix, Object[] keys, int keyNum) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix).append(".");
		if (keyNum <= keys.length) {
			for (int i = 0; i < keyNum; i++) {
				sb.append(keys[i]).append(".");
			}
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		MethodCacheInterceptor.cache = cache;
	}
}
