package com.hjh.mall.cache.cache.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheClient {

	public void set(String key, String value);

	public void set(String key, String value, int expireSecs);

	public String get(String key);

	public boolean expire(String key, int expireSecs);

	public boolean persist(String key);

	public boolean delete(String key);

	public boolean exists(String key);

	public long incrBy(String key, long by);

	public long incr(String key);

	public long decrBy(String key, long by);

	public long decr(String key);

	/**
	 * 返回存储在 key 里的list的长度
	 * 
	 * @param key
	 * @return
	 */
	public long llen(String key);

	/**
	 * 将所有指定的值插入到存于 key 的列表的头部
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public long lpush(String key, String... values);

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 下标(index)参数 start 和 stop 都以 0
	 * 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 使用负数下标，以 -1 表示列表的最后一个元素， -2
	 * 表示列表的倒数第二个元素，
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, int start, int end);

	/**
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 执行命令 LTRIM list 0 2
	 * ，表示只保留列表 list 的前三个元素，其余元素全部删除。 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0
	 * 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
	 * 表示列表的倒数第二个元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String ltrim(String key, long start, long end);

	/**
	 * 返回列表里的元素的索引 index 存储在 key 里面。
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index);

	/**
	 * 设置 index 位置的list元素的值为 value
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public String lset(String key, int index, String value);

	/**
	 * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lrem(String key, long count, String value);

	/**
	 * 增加成员 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public long zdd(String key, double score, String member);

	/**
	 * 批量增加成员
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public long zdd(String key, Map<String, Double> scoreMembers);

	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
	 * 
	 * @param key
	 * @return
	 */
	public long zcount(String key, int min, int max);

	/**
	 * 获取一个排序的集合中的成员数量
	 * 
	 * @param key
	 * @return
	 */
	public long zcard(String key);

	/**
	 * 为有序集key的成员member的score值加上增量increment
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double score, String member);

	/**
	 * 获取集合中元素(从小到大) 所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrangebyscore(String key, double min, double max);

	/**
	 * 获取集合中元素(从小到大) 所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrangebyscore(String key, double min, double max, int offset, int count);

	/**
	 * 查询成员在队列中的位置(从小到大)
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member);

	/**
	 * 查询成员在队列中的位置(从大到小)
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member);

	/**
	 * 返回有序集合key中，指定区间的成员(从小到大)
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Set<String> zrange(String key, long start, long stop);

	/**
	 * 返回有序集合key中，指定区间的成员(从大到小)
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long stop);

	/**
	 * 返回的是从有序集合中删除的成员个数，不包括不存在的成员。
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long zrem(String key, String... members);

	/**
	 * 移除有序集 key 中，指定排名(rank)区间内的所有成员。 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop
	 * 在内。 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 * 使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, int start, int end);

	/**
	 * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Long zremrangeByScore(String key, double min, double max);

	/**
	 * 返回有序集key中，成员member的score值。
	 * 
	 * @param key
	 * @param memeber
	 * @return
	 */
	public Double zscore(String key, String member);

	/**
	 * 添加一个或者多个元素到集合(set)里
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sdd(String key, String... members);

	/**
	 * 获取集合里面的元素数量
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key);

	/**
	 * 从集合里删除一个或多个member
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key, String... members);

	/**
	 * 获取集合里面的所有元素
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key);

	/**
	 * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中
	 * 
	 * @param key
	 * @return
	 */
	public boolean sismember(String key, String member);

	/**
	 * 根据字段设置值。hmset用新值替换旧值。 如果key不存在，则创建一个新的key，并持有一个哈希。
	 * 
	 * @param key
	 * @param hash
	 * @return String
	 */
	public String hmset(String key, Map<String, String> hash);

	/**
	 * 检索指定的字段相关联的值。如果指定的字段不存在,返回零值。不存在键被认为表
	 * 
	 * @param key
	 * @param member
	 * @return List<String> 将相关的值存入list
	 */
	public List<String> hmget(String key, String... field);

	/**
	 * 如果key持有一个hash根据key检索指定的字段值。如果没有找到该领域或关键不存在,返回一个特殊的空值。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field);

	/**
	 * 根据字段设置相应的值,如果不存在该hash,根据key创建一个持有该key的hash
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hset(String key, String field, String value);

	/**
	 * 判断该字段是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hexists(String key, String field);

	/**
	 * 删除相应的字段
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(String key, String... fields);

	/**
	 * 返回该hash的条目数
	 * 
	 * @param key
	 * @return
	 */
	public Long hlen(String key);

	/**
	 * 获取集合中元素(从大到小)
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrevrangebyscore(String key, double min, double max);

	/**
	 * 获取 key对应的所有field及值
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key);

	/**
	 * 添加list
	 * 
	 * @param key
	 * @return
	 */
	public <T> void setList(String key, List<T> list, int expireSecs);

	/**
	 * 获取 key对应的所有list及值
	 * 
	 * @param key
	 * @return
	 */
	public <T> List<T> getList(String key);

	/**
	 * @date 2016年12月29日
	 * @Description: 将所有指定的值插入到存于 key 的列表的尾部
	 * @author：王斌
	 * @param key
	 * @param values
	 * @return long
	 */
	public long rpush(String key, String... values);
}
