package com.hjh.mall.es.solr;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.WebPagedQueryVO;
import com.hjh.mall.es.base.SolrQueryBaseEntity;
import com.hjh.mall.es.server.ESBaseService;

public abstract class ESBaseServiceImpl<T, K> implements ESBaseService<T, K> {

	public abstract HttpSolrClient getEsClient();

	public Map<String, Object> save(T entity) throws Exception {
		DocumentObjectBinder binder = new DocumentObjectBinder();
		SolrInputDocument sDocument = binder.toSolrInputDocument(entity);
		getEsClient().add(sDocument);
		commit();
		return VOUtil.genSuccessResult();
	}

	public Map<String, Object> query(T entity) throws Exception {
		SolrQuery solrQuery = creatAllFiledSolrQuery(entity, null);

		Map<String, Object> result = VOUtil.genSuccessResult();
		try {
			Boolean isCounts = false;
			if (entity instanceof SolrQueryBaseEntity) {
				isCounts = ((SolrQueryBaseEntity) entity).getIscounts();
			}
			DocumentObjectBinder binder = new DocumentObjectBinder();
			QueryResponse response = getEsClient().query(solrQuery);
			SolrDocumentList reslList = response.getResults();
			result.put(BasicFields.RESULT_LIST, binder.getBeans(entity.getClass(), reslList));
			if (!isCounts) {
				return result;
			}
			result.put(BasicFields.COUNTS, reslList.getNumFound());
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, Object> batch(List<T> list) throws Exception {
		DocumentObjectBinder binder = new DocumentObjectBinder();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (T t : list) {
			SolrInputDocument solrDocument = binder.toSolrInputDocument(t);
			docs.add(solrDocument);
		}
		getEsClient().add(docs);
		commit();
		return VOUtil.genSuccessResult();
	}

	public Map<String, Object> update(T entity) throws Exception {
		Field[] fields = entity.getClass().getDeclaredFields();
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		for (Field field : fields) {
			if (null != field.get(entity)) {
				solrInputDocument.addField(field.getName(), field.get(entity));
			}
		}
		getEsClient().add(solrInputDocument);
		return VOUtil.genSuccessResult();
	}

	public Map<String, Object> delete(K key) throws Exception {
		getEsClient().deleteById(key.toString());
		commit();
		return VOUtil.genSuccessResult();
	}

	public QueryResponse querybyQueryString(SolrQuery solrQuery) throws SolrServerException {
		QueryResponse response = getEsClient().query(solrQuery);
		return response;
	}

	public void commit() throws SolrServerException, IOException {
		getEsClient().commit();
		optimize();
	}

	public Map<String, Object> allFiledQueryByOneKeyNoOder(T entity, K key) throws Exception {
		SolrQuery solrQuery = creatAllFiledSolrQuery(entity, key);
		Map<String, Object> result = VOUtil.genSuccessResult();
		try {
			Boolean isCounts = false;
			if (entity instanceof SolrQueryBaseEntity) {
				isCounts = ((SolrQueryBaseEntity) entity).getIscounts();
			}
			DocumentObjectBinder binder = new DocumentObjectBinder();
			QueryResponse response = getEsClient().query(solrQuery);
			SolrDocumentList reslList = response.getResults();
			result.put(BasicFields.RESULT_LIST, binder.getBeans(entity.getClass(), reslList));
			if (!isCounts) {
				return result;
			}
			result.put(BasicFields.COUNTS, reslList.getNumFound());
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;
	}

	public SolrQuery creatAllFiledSolrQuery(T entity, K key) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder stringBuilder = new StringBuilder();
		SolrQuery solrQuery = new SolrQuery();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getGenericType().toString().equals("class java.lang.Integer")
					|| field.getGenericType().toString().equals("class java.lang.Long")) {
				continue;
			}
			// 只对有注解的进行解析
			if (field.isAnnotationPresent(org.apache.solr.client.solrj.beans.Field.class)) {
				field.setAccessible(true);
				if (field.get(entity) != null) {
					stringBuilder.append(creatFieldMultipleValues(field.getName(), field.get(entity).toString()));
				}
			}
		}
		if (!StringUtil.isBlank((String) key)) {
			stringBuilder.append("hunterAll:" + key.toString());
		}

		if (entity instanceof WebPagedQueryVO) {
			solrQuery.setRows(((WebPagedQueryVO) entity).getLimit());
			solrQuery.setStart(((WebPagedQueryVO) entity).getLimit() * (((WebPagedQueryVO) entity).getPage() - 1));
		}
		solrQuery.setQuery(StringUtil.isBlank(stringBuilder.toString()) ? "*:*" : stringBuilder.toString());
		return solrQuery;
	}

	public String joint(String... strings) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : strings) {
			stringBuilder.append(string).append(" ");
		}
		return stringBuilder.toString();

	}

	public SolrQuery creatAllFiledSolrQuery(T entity) throws Exception {

		return null;
	}

	public StringBuilder creatFieldMultipleValues(String field, String values) {
		StringBuilder stringBuilder = new StringBuilder();
		if (values.split(",").length > 1) {
			stringBuilder.append("(");
			for (String string : values.split(",")) {
				stringBuilder.append(field).append(":").append(string).append(" OR ");
			}
			stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
			stringBuilder.append(")");
		} else {
			stringBuilder.append(field).append(":").append(values);
		}
		stringBuilder.append(" ");
		return stringBuilder;

	}

	public void optimize() {
		try {
			getEsClient().optimize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
