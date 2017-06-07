package com.hjh.mall.cache.cache.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class CacheHelper {

	private CacheClient cacheClient;

	public CacheClient getCacheClient() {
		return cacheClient;
	}

	public void setCacheClient(CacheClient cacheClient) {
		this.cacheClient = cacheClient;
	}

	public void set(String key, String value, int expireSecs) {
		if (expireSecs <= 0) {
			cacheClient.set(key, value);
		} else {
			cacheClient.set(key, value, expireSecs);
		}

	}

	public String get(String key) {
		// 空白key直接返回null
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return cacheClient.get(key);
	}

	public boolean exists(String key) {
		return cacheClient.exists(key);
	}

	public boolean refresh(String key, int expireSecs) {
		if (0 > expireSecs) {
			return cacheClient.persist(key);
		} else {
			return cacheClient.expire(key, expireSecs);
		}
	}

	public void destroy(String key) {
		cacheClient.delete(key);
	}

	public boolean check(String key, String value) {
		String valueSaved = cacheClient.get(key);
		return null != valueSaved && valueSaved.equals(value);
	}

	public List<String> getList(String key, int start, int end) {
		return cacheClient.lrange(key, start, end);
	}

	public String lget(String key, long index) {
		return cacheClient.lindex(key, index);
	}

	public String lset(String key, int index, String value) {
		return cacheClient.lset(key, index, value);
	}

	public void lpush(String key, String... value) {
		cacheClient.lpush(key, value);
	}

	public long ladd(String key, String value) {
		return cacheClient.lpush(key, value);
	}

	/**
	 * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lrem(String key, long count, String value) {
		return cacheClient.lrem(key, count, value);
	}

	public long laddAll(String key, String... values) {
		return cacheClient.lpush(key, values);
	}

	public long laddAll(String key, List<String> values) {
		String[] arr = (String[]) values.toArray(new String[values.size()]);
		return cacheClient.lpush(key, arr);
	}

	public long llen(String key) {
		return cacheClient.llen(key);
	};

	public long lrem(String key, String value) {
		return cacheClient.lrem(key, 0, value);
	};

	/**
	 * 增加成员 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public long zdd(String key, double score, String member) {
		return cacheClient.zdd(key, score, member);
	}

	/**
	 * 批量增加成员
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public long zdd(String key, Map<String, Double> scoreMembers) {
		return cacheClient.zdd(key, scoreMembers);
	}

	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
	 * 
	 * @param key
	 * @return
	 */
	public long zcount(String key, int min, int max) {
		return cacheClient.zcount(key, min, max);
	}

	/**
	 * 返回有序集 key 中，所有的的成员的数量。
	 * 
	 * @param key
	 * @return
	 */
	public long zcount(String key) {
		return this.zcount(key, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * 获取一个排序的集合中的成员数量
	 * 
	 * @param key
	 * @return
	 */
	public long zcard(String key) {
		return cacheClient.zcard(key);
	}

	/**
	 * 增量的一名成员在排序设置的评分
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double score, String member) {
		return cacheClient.zincrby(key, score, member);
	}

	/**
	 * 获取集合中元素
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrangebyscore(String key, double min, double max, int offset, int count) {
		return cacheClient.zrangebyscore(key, min, max, offset, count);
	}

	/**
	 * 获取集合中元素
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrangebyscore(String key, double min, double max) {
		return cacheClient.zrangebyscore(key, min, max);
	}

	/**
	 * 查询成员在队列中的位置(从小到大)
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		return cacheClient.zrank(key, member);
	}

	/**
	 * 查询成员在队列中的位置(从大到小)
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		return cacheClient.zrevrank(key, member);
	}

	/**
	 * 返回有序集合key中，指定区间的成员(从小到大)
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Set<String> zrange(String key, long start, long stop) {
		return cacheClient.zrange(key, start, stop);
	}

	/**
	 * 返回有序集合key中，指定区间的成员(从大到小)
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long stop) {
		return cacheClient.zrevrange(key, start, stop);
	}

	/**
	 * 获取集合中元素(从大到小)
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrevrangebyscore(String key, double min, double max) {
		return cacheClient.zrevrangebyscore(key, min, max);
	}

	/**
	 * 返回的是从有序集合中删除的成员个数，不包括不存在的成员。
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long zrem(String key, String... members) {
		return cacheClient.zrem(key, members);
	}

	/**
	 * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, int start, int end) {
		return cacheClient.zremrangeByRank(key, start, end);
	}

	/**
	 * 移除有序集 key 中，指定排名(Score)内的所有成员。
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByScore(String key, double min, double max) {
		return cacheClient.zremrangeByScore(key, min, max);
	}

	/**
	 * 返回有序集key中，成员member的score值。
	 * 
	 * @param key
	 * @param memeber
	 * @return
	 */
	public Double zscore(String key, String member) {
		return cacheClient.zscore(key, member);
	}

	/**
	 * 添加一个或者多个元素到集合(set)里
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sdd(String key, String... members) {
		return cacheClient.sdd(key, members);
	}

	/**
	 * 获取集合里面的元素数量
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		return cacheClient.scard(key);
	}

	/**
	 * 从集合里删除一个或多个member
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key, String... members) {
		return cacheClient.srem(key, members);
	}

	/**
	 * 获取集合里面的所有元素
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		return cacheClient.smembers(key);
	}

	/**
	 * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中
	 * 
	 * @param key
	 * @return
	 */
	public boolean sismember(String key, String member) {
		return cacheClient.sismember(key, member);
	}

	/**
	 * 指定hash添加域对应的值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public String hget(String key, String field) {
		return cacheClient.hget(key, field);
	}

	/**
	 * 指定hash添加域对应的值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hset(String key, String field, String value) {
		return cacheClient.hset(key, field, value);
	}

	/**
	 * 设置 key 指定的哈希集中指定字段的值
	 * 
	 * @param key
	 * @param member
	 * @return String
	 */
	public String hmset(String key, Map<String, String> member) {
		return cacheClient.hmset(key, member);
	}

	/**
	 * 获取 key 指定member的值
	 * 
	 * @param key
	 * @param member
	 * @return String
	 */
	public List<String> hmget(String key, String... member) {
		List<String> list = cacheClient.hmget(key, member);
		if (list != null && list.size() > 0) {
			return list;
		}
		return new ArrayList<String>();

	}

	/**
	 * 获取 key 指定member的值
	 * 
	 * @param key
	 * @param member
	 * @return String
	 */
	public String hmget(String key, String member) {
		String result = null;
		List<String> list = cacheClient.hmget(key, member);
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	/**
	 * 获取 key对应的所有field及值
	 * 
	 * @param key
	 */
	public Map<String, String> hgetAll(String key) {
		return cacheClient.hgetAll(key);
	}

	public <T> void setList(String key, List<T> list, int expireSecs) {

		cacheClient.setList(key, list, expireSecs);
	}

	public <T> List<T> getList(String key) {
		return cacheClient.getList(key);
	}

	public long incrBb(String key, long by) {
		return cacheClient.incrBy(key, by);
	}

	public void rpush(String key, String... value) {
		cacheClient.rpush(key, value);
	}
}
