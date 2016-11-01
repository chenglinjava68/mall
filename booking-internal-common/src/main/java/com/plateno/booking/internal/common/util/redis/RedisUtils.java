package com.plateno.booking.internal.common.util.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtils {

	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	private ShardedJedisPool shardedJedisPool;

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public RedisUtils() {

	}

	/**
	 * key对应的Value加1 原子性操作
	 * 
	 * @param key
	 */
	public Long incr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.incr(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return null;
	}
	
	/**
	 * key对应的Value加1 原子性操作
	 * 
	 * @param key
	 */
	public Set<String> keys(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.getShard(key).keys(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
			shardedJedis.getShard(key).close();
		}
		return null;
	}

	/**
	 * key对应的Value减1 原子性操作
	 * 
	 * @param key
	 */
	public Long decr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.decr(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return null;
	}

	/**
	 * key对应的Value加 increment 原子性操作
	 * 
	 * @param key
	 */
	public Long incrBy(String key, long increment) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.incrBy(key, increment);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return null;
	}

	/**
	 * key对应的Value减decrement 原子性操作
	 * 
	 * @param key
	 */
	public Long decrBy(String key, long decrement) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.decrBy(key, decrement);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return null;
	}

	/**
	 * Hash 操作 HSET key field value将哈希表key中的域field的值设为value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hset(key, field, value);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * Hash 操作 HSET key field value将哈希表key中的域field的值设为value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param expire
	 *            有效期 单位是ms
	 */
	public void hset(String key, String field, String value, long expire) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hset(key, field, value);
			shardedJedis.expire(key, Long.valueOf(expire / 1000).intValue());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * HDEL key field [field ...]删除哈希表key中的一个或多个指定域。
	 * 
	 * @param key
	 * @param fields
	 */
	public void hdel(String key, String... fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hdel(key, fields);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * GET key field返回哈希表key中给定域field的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hget(key, field);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	public List<String> phget(List<String> keyList) {
		ShardedJedis shardedJedis = null;
		List<String> value = new ArrayList<String>();
		int count = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			ShardedJedisPipeline p = shardedJedis.pipelined();
			int i = 0;
			for (String key : keyList) {
				p.del(key);
				/*Response<Map<String, String>> ss1 =p.hgetAll(key);
				Map<String, String> ss = ss1.get();
				Set<String> sese = ss.keySet();
				for(String s : sese){
					shardedJedis.hset(key, s, "0");
				}*/
											   /*286132  2014-12-04
											   302111	2014-12-03
											   279450   2014-12-02
											   207426   2014-12-01
											   141240   2014-11-30	
				 							   146152   2014-11-29
				 							   159967   2014-11-28
				 							   113284   2014-11-27
				 							    92598   2014-11-26
				 							    66940   2014-11-25
				 							    28586   2014-11-24
				 							    6956    2014-11-23
				 							       4149     2014-11-22
				 							       7     2014-11-21
				 							    */
				System.out.println(i++);
			}
		    p.sync();
			/*List<Object> rs = p.syncAndReturnAll();

			for (Object r : rs) {
				if (null != r && Boolean.parseBoolean(r.toString())) {
					// value.add(r.toString());
					count++;
				}
			}*/
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		logger.info(count + "");
		return value;
	}

	/**
	 * 得到复杂对象的值
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		ShardedJedis shardedJedis = null;
		Map<String, String> value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hgetAll(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * GET key field返回哈希表key中给定域field的值
	 * 
	 * @param key
	 * @param field
	 * @param refreshSource
	 * @param expiry
	 *            有效期
	 * @return
	 */
	public String hget(String key, String field,
			Callable<String> refreshSource, long expiry) {
		String value = hget(key, field);
		if (null == value) {
			try {
				value = refreshSource.call();
				if (null != value) {
					hset(key, field, value, expiry);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value;
	}

	/**
	 * GET key field返回哈希表key中给定域field的值
	 * 
	 * @param key
	 * @param field
	 * @param refreshSource
	 * @return
	 */
	public String hget(String key, String field, Callable<String> refreshSource) {
		String value = hget(key, field);
		if (null == value) {
			try {
				value = refreshSource.call();
				if (null != value) {
					hset(key, field, value);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value;
	}

	/**
	 * 设值
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(key, value);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 设值
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            有效期 单位是ms
	 */
	public void set(String key, String value, long expire) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(key, value);
			shardedJedis.expire(key, Long.valueOf(expire / 1000).intValue());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 删除值。
	 * 
	 * @param key
	 */
	public void del(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.del(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 得到值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.get(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 得到值
	 * 
	 * @param key
	 * @param refreshSource
	 * @param expiry
	 *            有效期
	 * @return
	 */
	public String get(String key, Callable<String> refreshSource, long expiry) {
		String value = get(key);
		if (null == value) {
			try {
				value = refreshSource.call();
				if (null != value) {
					set(key, value, expiry);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value;
	}

	/**
	 * 得到值
	 * 
	 * @param key
	 * @param refreshSource
	 * @return
	 */
	public String get(String key, Callable<String> refreshSource) {
		String value = get(key);
		if (null == value) {
			try {
				value = refreshSource.call();
				if (null != value) {
					set(key, value);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value;
	}

	/**
	 * 在key对应list的头部添加字符串元素
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long lpush(String key, String... values) {
		ShardedJedis shardedJedis = null;
		Long value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.lpush(key, values);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 从list的头部删除元素，并返回删除元素
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.lpop(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 在key对应list的尾部添加字符串元素
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long rpush(String key, String... values) {
		ShardedJedis shardedJedis = null;
		Long value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.rpush(key, values);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 从list的尾部删除元素，并返回删除元素
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.rpop(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	
	/**
	 * 在key对应list下标的值
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public String lindex(String key, long index) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.lindex(key, index);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	
	/**
	 * 在key对应list下标的值
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long llen(String key) {
		ShardedJedis shardedJedis = null;
		Long value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.llen(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long sadd(String key, String... members) {
		ShardedJedis shardedJedis = null;
		Long value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.sadd(key, members);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long srem(String key, String... members) {
		ShardedJedis shardedJedis = null;
		Long value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.srem(key, members);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Set<String> smembers(String key) {
		ShardedJedis shardedJedis = null;
		Set<String> value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.smembers(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Boolean sismember(String key,String member) {
		ShardedJedis shardedJedis = null;
		Boolean value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.sismember(key, member);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (shardedJedis != null) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}
		return value;
	}
}
