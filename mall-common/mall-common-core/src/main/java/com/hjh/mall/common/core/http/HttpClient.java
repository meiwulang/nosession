package com.hjh.mall.common.core.http;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hjh.mall.common.core.util.IOUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.ValueUtil;

public class HttpClient {

	protected CloseableHttpClient getHttpClient() {
		return getDefaultClientBuilder().build();
	}

	protected HttpClientBuilder getDefaultClientBuilder() {
		return HttpClients.custom();
	}

	protected CloseableHttpClient getHttpClient(String url) throws Exception {
		if ("https".equals(new URL(url).getProtocol())) {
			return getHttpsClient();
		} else {
			return getHttpClient();
		}
	}

	@SuppressWarnings("deprecation")
	public static CloseableHttpClient getHttpsClient() throws Exception {
		try {
			TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
						throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
						throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };
			SSLContext sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
			sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
			SSLContext.setDefault(sslContext);
			sslContext.init(null, trustManagers, null);
			SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory(connectionSocketFactory);
			clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
			CloseableHttpClient httpClient = clientBuilder.build();
			return httpClient;
		} catch (Exception e) {
			throw new Exception("http client 远程连接失败", e);
		}
	}

	protected static UrlEncodedFormEntity mapToFormEntity(Map<String, ?> param, String charset) {
		List<NameValuePair> nvPairs = new LinkedList<NameValuePair>();
		for (Entry<String, ?> entry : param.entrySet()) {
			String keyStr = StringUtil.nullToEmpty(entry.getKey());
			Object value = entry.getValue();
			String valueStr = StringUtil.nullToEmpty(value);
			nvPairs.add(new BasicNameValuePair(keyStr, valueStr));
		}
		try {
			return new UrlEncodedFormEntity(nvPairs, charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("unsupported encoding: " + charset, e);
		}
	}

	private static RequestPayloadHandler<Object> jsonParamRequestHandler = new RequestPayloadHandler<Object>() {
		@Override
		public void handle(HttpEntityEnclosingRequest request, Object param, String mimeType, String charset) {
			if (!ValueUtil.isEmpty(param)) {
				String jsonString = JSON.toJSONString(param);
				HttpEntity postBody = null;
				postBody = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
				request.setEntity(postBody);
			}
		}
	};
	private static RequestPayloadHandler<Map<String, ?>> formParamRequestHandler = new RequestPayloadHandler<Map<String, ?>>() {
		@Override
		public void handle(HttpEntityEnclosingRequest request, Map<String, ?> param, String mimeType, String charset) {
			if (!ValueUtil.isEmpty(param)) {
				HttpEntity postBody = mapToFormEntity(param, charset);
				request.setEntity(postBody);
			}
		}
	};

	protected RequestConfig getDefaultRequestConfig() {
		return RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(1000).build();
	}

	protected <T> String postFetchString(RequestPayloadHandler<T> requestPayloadHandler, String url, T param,
			String mimeType, String charset, Map<String, Object> header) throws ConnectTimeoutException,
			SocketTimeoutException {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		try {
			// LOGGER.info(genRequestLogHead(itfDesc) + param);

			client = getHttpClient(url);

			HttpPost request = new HttpPost(url);
			// 如果还有其他包头，组装包头
			if (!ValueUtil.isEmpty(header)) {
				for (Map.Entry<String, Object> enMap : header.entrySet()) {
					request.addHeader(enMap.getKey(), enMap.getValue().toString());
				}
			}
			requestPayloadHandler.handle(request, param, mimeType, charset);

			response = client.execute(request);

			HttpEntity responseEntity = response.getEntity();
			if (null != responseEntity) {
				String responseString = EntityUtils.toString(responseEntity);
				// LOGGER.info(genResponseLogHead(itfDesc) + responseString);
				// System.out.println(responseString);
				return responseString;
			} else {
				// LOGGER.error(genResponseLogHead(itfDesc) +
				// "doExecute failed, responseEntity is null");
			}
		} catch (ConnectTimeoutException e) {
			// LOGGER.error(genResponseLogHead(itfDesc) + "doExecute failed",
			// e);
			throw e;
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(response);
			IOUtil.close(client);
		}
		return null;
	}

	protected static interface RequestPayloadHandler<T> {
		public void handle(HttpEntityEnclosingRequest request, T param, String mimeType, String charset);

	}

	public JSONObject postFormFetchJSON(String url, Map<String, ?> param, String charset, Map<String, Object> Header)
			throws ConnectTimeoutException, SocketTimeoutException, JSONException {
		String responseString = postFetchString(jsonParamRequestHandler, url, param, null, charset, Header);
		if (StringUtils.isNotBlank(responseString)) {
			try {
				return JSON.parseObject(responseString);
			} catch (JSONException e) {
				throw e;
			}
		}
		return null;

	}

	public JSONObject postFormFetchForm(String url, Map<String, ?> param, String charset, Map<String, Object> Header)
			throws ConnectTimeoutException, SocketTimeoutException, JSONException {
		String responseString = postFetchString(formParamRequestHandler, url, param, null, charset, Header);
		if (StringUtils.isNotBlank(responseString)) {
			try {
				return JSON.parseObject(responseString);
			} catch (JSONException e) {
				throw e;
			}
		}
		return null;
	}

	// public static void main(String[] args) {
	// HttpClient c= new HttpClient();
	// c.postFormFetchForm(url, param, charset, Header)
	//
	//
	// }

}
