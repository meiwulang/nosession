package com.hjh.mall.es.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hjh.mall.es.solr.SolrConfig;
import com.hjh.mall.es.solr.SolrServer;

public class HJHBCSESClientFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(HJHBCSESClientFactory.class);

	public static SolrServer getSolrServer(SolrConfig solrConfig) {
		if (null == solrConfig) {
			LOGGER.error("SolrServer is null ");
			return null;
		}		
		return new SolrServer(solrConfig);
	}

}
