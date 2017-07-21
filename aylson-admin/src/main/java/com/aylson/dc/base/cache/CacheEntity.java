/**   
 * @Title: CacheEntity.java 
 * @Description: 缓存实体
 * @date 2014年9月19日 下午6:45:54 
 * @version V1.0   
 */
package com.aylson.dc.base.cache;

/**
 * 
 *
 */
public class CacheEntity {
	private final int DEFUALT_VALIDITY_TIME = 300;// 默认过期时间 5分钟

	private String cacheKey;
	private Object cacheContext;
	private int validityTime; // 有效期时长，单位：秒
	private long timeoutStamp;// 过期时间戳

	private CacheEntity() {
		this.timeoutStamp = System.currentTimeMillis() + DEFUALT_VALIDITY_TIME
				* 1000;
		this.validityTime = DEFUALT_VALIDITY_TIME;
	}

	public CacheEntity(String cacheKey, Object cacheContext) {
		this();
		this.cacheKey = cacheKey;
		this.cacheContext = cacheContext;
	}

	public CacheEntity(String cacheKey, Object cacheContext, long timeoutStamp) {
		this(cacheKey, cacheContext);
		this.timeoutStamp = timeoutStamp;
	}

	public CacheEntity(String cacheKey, Object cacheContext, int validityTime) {
		this(cacheKey, cacheContext);
		this.validityTime = validityTime;
		this.timeoutStamp = System.currentTimeMillis() + validityTime * 1000;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public Object getCacheContext() {
		return cacheContext;
	}

	public void setCacheContext(Object cacheContext) {
		this.cacheContext = cacheContext;
	}

	public long getTimeoutStamp() {
		return timeoutStamp;
	}

	public void setTimeoutStamp(long timeoutStamp) {
		this.timeoutStamp = timeoutStamp;
	}

	public int getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(int validityTime) {
		this.validityTime = validityTime;
	}
}
