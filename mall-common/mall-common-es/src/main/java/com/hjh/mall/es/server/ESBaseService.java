package com.hjh.mall.es.server;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public interface ESBaseService<T, K> {

	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 *             单条数据创建索引
	 */
	public Map<String, Object> save(T entity) throws Exception;

	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 *             根据列值数据查询
	 */
	public Map<String, Object> query(T entity) throws Exception;

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 *             提供自己创建的查询语句返回查询信息
	 */
	public QueryResponse querybyQueryString(SolrQuery solrQuery) throws Exception;

	/**
	 * @param entity
	 * @param key
	 * @return
	 * @throws Exception
	 *             全文索引不带排序
	 */
	public Map<String, Object> allFiledQueryByOneKeyNoOder(T entity, K key) throws Exception;

	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 *             更新索引
	 */
	public Map<String, Object> update(T entity) throws Exception;

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 *             删除索引
	 */
	public Map<String, Object> delete(K key) throws Exception;

	/**
	 * @param list
	 * @return
	 * @throws Exception
	 *             批量添加索引
	 */
	public Map<String, Object> batch(List<T> list) throws Exception;

	/**
	 * @param entity
	 * @param key
	 * @return
	 * @throws Exception
	 *             创建查询条件
	 */
	public SolrQuery creatAllFiledSolrQuery(T entity, K key) throws Exception;
	
	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public SolrQuery creatAllFiledSolrQuery(T entity) throws Exception;

}
