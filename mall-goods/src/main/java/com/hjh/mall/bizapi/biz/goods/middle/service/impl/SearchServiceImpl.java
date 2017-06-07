package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.stereotype.Service;

import com.hjh.mall.bizapi.biz.goods.middle.service.SearchService;
import com.hjh.mall.es.base.GoodsSorl;
import com.hjh.mall.es.solr.ESBaseServiceImpl;
import com.hjh.mall.es.solr.SolrServer;

/**
 * @Project: mall-goods
 * @Description
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
@Service
public class SearchServiceImpl extends ESBaseServiceImpl<GoodsSorl, String> implements SearchService {
	@Resource
	private SolrServer solrServer;

	@Override
	public HttpSolrClient getEsClient() {
		return solrServer.getSolrClient();
	}

}
