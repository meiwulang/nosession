package com.hjh.mall.es.solr;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SolrServer.class);

	private static HttpSolrClient solrClient;

	public HttpSolrClient getSolrClient() {
		return solrClient;
	}

	public SolrServer(SolrConfig solrConfig) {
		solrClient = new HttpSolrClient(solrConfig.getBaseUrl());
		solrClient.setDefaultMaxConnectionsPerHost(solrConfig
				.getDefaultMaxConnectionsPerHost());
		solrClient.setConnectionTimeout(solrConfig.getConnectionTimeout());
		solrClient.setMaxTotalConnections(solrConfig.getMaxTotalConnections());
		solrClient.setSoTimeout(solrConfig.getSoTimeout());
		solrClient.setFollowRedirects(true);
		solrClient.setMaxRetries(1);
	}

	public void commit() {
		try {
			solrClient.commit();
		} catch (SolrServerException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
