package com.hjh.mall.common.core.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public abstract class SmsMessageConfig {
	
	protected abstract String getUrl();
	
	protected abstract String getPassword();
	
	protected abstract String getSign();
	
	protected abstract String getName();
	
	protected abstract String getMode();
		
	protected String SendSmsMessage(String mobile, String verifyMsg, String stime) {
		
		String sign = "莱准智能";
		String body = createCommonParam(getName(), sign, verifyMsg, getPassword()) + "&mobile=" + mobile + "&stime=" + stime;
		System.out.println("url:" + getUrl() + body);
		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(getUrl());
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
		
	}
	
	protected  String createCommonParam(String name, String sign, String verifyMsg, String password) {
		String signKey = null;
		String content = getMode();
		String contentMessage = null;
		try {
			// 签名
			signKey = URLEncoder.encode(sign, "UTF-8");
			contentMessage = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "name=" + name + "&sign=" + signKey + "&content=" + contentMessage + "&pwd=" + password
				+ "&type=pt&extno=";
	}
	

}
