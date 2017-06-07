package com.hjh.mall.es.solr;

import java.io.Serializable;

public class SolrConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String baseUrl;

	private Integer connectionTimeout = 6000;

	private Integer defaultMaxConnectionsPerHost = 100;

	private Integer maxTotalConnections = 200;
	
	private Integer soTimeout=6000;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getDefaultMaxConnectionsPerHost() {
		return defaultMaxConnectionsPerHost;
	}

	public void setDefaultMaxConnectionsPerHost(Integer defaultMaxConnectionsPerHost) {
		this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
	}

	public Integer getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(Integer maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public Integer getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SolrConfig [baseUrl=");
		builder.append(baseUrl);
		builder.append(", connectionTimeout=");
		builder.append(connectionTimeout);
		builder.append(", defaultMaxConnectionsPerHost=");
		builder.append(defaultMaxConnectionsPerHost);
		builder.append(", maxTotalConnections=");
		builder.append(maxTotalConnections);
		builder.append(", soTimeout=");
		builder.append(soTimeout);
		builder.append("]");
		return builder.toString();
	}
	
	

}
