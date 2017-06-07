package com.hjh.mall.common.core.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.sms.SmsMessage;

/**
 * @author chenshijun 2016年4月18日
 */
@Service
public class SmsMessageImp implements SmsMessage {

	private String password;
	private String sign;
	private String url;
	private String name;

	public String getPassword() {
		return password;
	}

	@Value("${sms.request_password}")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUrl() {
		return url;
	}

	@Value("${sms.request_url}")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	@Value("${sms.request_name}")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 构造通用参数sign,content
	 * 
	 * @param sign
	 * @param verifyMsg
	 * @param Name
	 * @param password
	 * @return
	 */
	public static String createCommonParam(String name, String sign, String verifyMsg, String password) {
		String signKey = null;
		String content = "您的验证码为：" + verifyMsg + "，三十分钟内有效，如非本人操作请忽略。";
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

	@Override
	public String SendSmsMessage(String mobile, String verifyMsg, String stime) {
		String sign = "莱准智能";
		String body = createCommonParam(name, sign, verifyMsg, password) + "&mobile=" + mobile + "&stime=" + stime;
		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
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

	public static void main(String arg[]) {
	}
}
