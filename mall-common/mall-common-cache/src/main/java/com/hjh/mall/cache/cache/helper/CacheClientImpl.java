package com.hjh.mall.cache.cache.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hjh.mall.common.core.util.SerializationUtil;

public class CacheClientImpl implements CacheClient {
	@Value("${database}")
	private int database;
	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Jedis getJedisFromPool() {
		if (null == jedisPool) {

			throw new IllegalStateException("Please call init method first.");
		}
		Jedis jedis = jedisPool.getResource();
		jedis.select(database);
		return jedis;
	}

	public void destory() {
		if (null != jedisPool) {
			jedisPool.close();
		}
	}

	protected String processKey(String key) {
		return key;
	}

	@Override
	public void set(String key, String value) {
		Jedis jedis = getJedisFromPool();
		try {
			jedis.set(processKey(key), value);
		} finally {
			jedis.close();
		}
	}

	@Override
	public void set(String key, String value, int expireSecs) {
		Jedis jedis = getJedisFromPool();
		try {
			jedis.setex(processKey(key), expireSecs, value);
		} finally {
			jedis.close();
		}

	}

	@Override
	public String get(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.get(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean expire(String key, int expireSecs) {
		Jedis jedis = getJedisFromPool();
		try {
			return 1 == jedis.expire(processKey(key), expireSecs);
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean persist(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return 1 == jedis.persist(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean delete(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return 1 == jedis.del(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.exists(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public long incrBy(String key, long by) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.incrBy(processKey(key), by);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long incr(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.incr(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public long decrBy(String key, long by) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.decrBy(processKey(key), by);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long decr(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.decr(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public long llen(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.llen(processKey(key));
		} finally {
			jedis.close();
		}
	}

	@Override
	public long lpush(String key, String... values) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.lpush(processKey(key), values);
		} finally {
			jedis.close();
		}
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.lrange(processKey(key), start, end);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String lindex(String key, long index) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.lindex(key, index);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String lset(String key, int index, String value) {
		Jedis jedis = getJedisFromPool();
		try {

			return jedis.lset(key, index, value);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long lrem(String key, long count, String value) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.lrem(key, count, value);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String ltrim(String key, long start, long end) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.ltrim(key, start, end);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long zdd(String key, double score, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zadd(key, score, member);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long zdd(String key, Map<String, Double> scoreMembers) {

		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zadd(key, scoreMembers);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long zcount(String key, int min, int max) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zcount(key, min, max);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long zcard(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zcard(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zincrby(key, score, member);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> zrangebyscore(String key, double min, double max, int offset, int count) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrangeByScore(key, min, max, offset, count);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> zrangebyscore(String key, double min, double max) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrangeByScore(key, min, max);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zrank(String key, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zcard(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zrevrank(String key, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zcard(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long stop) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrange(key, start, stop);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long stop) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrevrange(key, start, stop);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> zrevrangebyscore(String key, double min, double max) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrevrangeByScore(key, max, min);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zrem(String key, String... members) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zrem(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zremrangeByRank(String key, int start, int end) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zremrangeByRank(key, start, end);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zremrangeByScore(String key, double min, double max) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zremrangeByScore(key, min, max);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Double zscore(String key, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zscore(key, member);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long sdd(String key, String... members) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.sadd(key, members);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long scard(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.zcard(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long srem(String key, String... members) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.srem(key, members);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.smembers(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean sismember(String key, String member) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.sismember(key, member);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hmset(key, hash);
		} finally {
			jedis.close();
		}
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hmget(key, fields);
		} finally {
			jedis.close();
		}
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hget(key, field);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hset(key, field, value);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Boolean hexists(String key, String field) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hexists(key, field);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long hdel(String key, String... fields) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hdel(key, fields);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long hlen(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.hlen(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = getJedisFromPool();

		try {
			return jedis.hgetAll(key);
		} finally {
			jedis.close();
		}
	}

	@Override
	public <T> void setList(String key, List<T> list, int expireSecs) {
		Jedis jedis = getJedisFromPool();
		try {
			jedis.set(key.getBytes(), SerializationUtil.serialize(list));
		} finally {
			jedis.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(String key) {
		Jedis jedis = getJedisFromPool();
		try {
			byte[] bs = jedis.get(key.getBytes());
			return (List<T>) SerializationUtil.deserialize(bs);
		} finally {
			jedis.close();
		}
	}

	@Override
	public long rpush(String key, String... values) {
		Jedis jedis = getJedisFromPool();
		try {
			return jedis.rpush(processKey(key), values);
		} finally {
			jedis.close();
		}
	}
}
