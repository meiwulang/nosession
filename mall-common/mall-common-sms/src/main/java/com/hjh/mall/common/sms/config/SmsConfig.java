package com.hjh.mall.common.sms.config;

import com.hjh.mall.common.core.util.StringUtil;

public class SmsConfig {
	

	public final String HTTP_PROXY_IP="http_proxy_ip";
	
	public final String HTTP_PROXY_PORT="http_proxy_port";
	
	private String url;
		
	private String appkey;
	
	private String secret;
	
	private String proxyHost;
	
	private String proxyPort;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = StringUtil.isBlank(proxyHost)?System.getenv(HTTP_PROXY_IP):proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = StringUtil.isBlank(proxyPort)?System.getenv(HTTP_PROXY_PORT):proxyPort;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsConfig [url=").append(url).append(", appkey=")
				.append(appkey).append(", secret=").append(secret)
				.append(", proxyHost=").append(proxyHost)
				.append(", proxyPort=").append(proxyPort).append("]");
		return builder.toString();
	}
	


	
	

}
