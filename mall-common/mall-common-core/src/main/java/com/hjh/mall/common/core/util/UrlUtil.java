package com.hjh.mall.common.core.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Project: hjy-common
 * @Description 获取页面源代码
 * @author 杨益桦
 * @date 2016年9月27日
 * @version V1.0
 */
public class UrlUtil {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://hz-hjh-hjy.oss-cn-hangzhou.aliyuncs.com/NEWS/1474271074417175.html");
		String urlsource = getURLSource(url);
		String body = urlsource.split("<div id=\"pic\" class=\"newsContt\">")[1].replace(
				"</div></li></ul></div></div></body></html>", "");
		System.out.println(body);
	}

	/** */
	/**
	 * 通过网站域名URL获取该网站的源码
	 * 
	 * @param url
	 * @return String
	 * @throws Exception
	 */
	public static String getURLSource(URL url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream(); // 通过输入流获取html二进制数据
		byte[] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
		String htmlSource = new String(data, "utf-8");
		return htmlSource;
	}

	/** */
	/**
	 * 把二进制流转化为byte字节数组
	 * 
	 * @param instream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream instream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1204];
		int len = 0;
		while ((len = instream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		instream.close();
		return outStream.toByteArray();
	}
}
