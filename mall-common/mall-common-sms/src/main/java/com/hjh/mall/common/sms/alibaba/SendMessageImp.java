package com.hjh.mall.common.sms.alibaba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.hjh.mall.common.core.util.EnumUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SendMessageImp implements SendMessage {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendMessageImp.class);
	private String appkey;
	private String secret;
	private String url;
	private String login_code;
	private String regis_code;
	private String activity_code;
	private String update_password_code;
	private String update_info_code;
	private String login_unusual_code;
	private String request_times;
	private String verify_failure;
	private String request_interval;
	private String valid_tim;
	private String request_tatla;
	private String request_time;
	private String sign;
	private String product;

	public String getAppkey() {
		return appkey;
	}

	@Value("${sms_app_key}")
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogin_code() {
		return login_code;
	}

	public void setLogin_code(String login_code) {
		this.login_code = login_code;
	}

	public String getRegis_code() {
		return regis_code;
	}

	public void setRegis_code(String regis_code) {
		this.regis_code = regis_code;
	}

	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	public String getUpdate_password_code() {
		return update_password_code;
	}

	public void setUpdate_password_code(String update_password_code) {
		this.update_password_code = update_password_code;
	}

	public String getUpdate_info_code() {
		return update_info_code;
	}

	public void setUpdate_info_code(String update_info_code) {
		this.update_info_code = update_info_code;
	}

	public String getLogin_unusual_code() {
		return login_unusual_code;
	}

	public void setLogin_unusual_code(String login_unusual_code) {
		this.login_unusual_code = login_unusual_code;
	}

	public String getRequest_times() {
		return request_times;
	}

	public void setRequest_times(String request_times) {
		this.request_times = request_times;
	}

	public String getVerify_failure() {
		return verify_failure;
	}

	public void setVerify_failure(String verify_failure) {
		this.verify_failure = verify_failure;
	}

	public String getRequest_interval() {
		return request_interval;
	}

	public void setRequest_interval(String request_interval) {
		this.request_interval = request_interval;
	}

	public String getValid_tim() {
		return valid_tim;
	}

	public void setValid_tim(String valid_tim) {
		this.valid_tim = valid_tim;
	}

	public String getRequest_tatla() {
		return request_tatla;
	}

	public void setRequest_tatla(String request_tatla) {
		this.request_tatla = request_tatla;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public void SendSmsMessage(String mobile, String verifyMsg, String type) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		JSONObject paramSms = new JSONObject();
		paramSms.put("code", verifyMsg);
		paramSms.put("product", "【好机惠】");
		req.setSmsParamString(paramSms.toJSONString());
		req.setRecNum(mobile);
		VerifyType verifyType = EnumUtil.valOf(type, VerifyType.class);

		switch (verifyType) {
		case REGIST:
			req.setSmsTemplateCode(regis_code);
			break;
		case LOGIN:
			req.setSmsTemplateCode(login_code);
			break;
		case UPDATEPWD:
			req.setSmsTemplateCode(update_password_code);
			break;
		case UPDATEINFO:
			req.setSmsTemplateCode(update_info_code);
			break;
		case IDENTITY_VERIFICATION:
			req.setSmsTemplateCode(login_unusual_code);
			break;
		}
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			LOGGER.info("mobile" + mobile + "阿里大鱼返回结果" + rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String a = "caea85216ba68cc5df8b14eafca20d65";
		TaobaoClient client = new DefaultTaobaoClient(
				"http://gw.api.taobao.com/router/rest", "23405018", a);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		JSONObject paramSms = new JSONObject();
		paramSms.put("code", "12345");
		req.setSmsParamString(paramSms.toJSONString());
		req.setRecNum("15381052501");
		req.setSmsTemplateCode("SMS_11545418");
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();	
		}
	}

	@Override
	public void SendTextMessage(String mobile, String value) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		JSONObject paramSms = new JSONObject();
		paramSms.put("value", value);
		req.setSmsParamString(paramSms.toJSONString());
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_12460804");
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void SendSmsMessageByName(String mobile, String verifyMsg,
			String type, String name) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		JSONObject paramSms = new JSONObject();
		paramSms.put("code", verifyMsg);
		paramSms.put("product", name);
		req.setSmsParamString(paramSms.toJSONString());
		req.setRecNum(mobile);
		VerifyType verifyType = EnumUtil.valOf(type, VerifyType.class);

		switch (verifyType) {
		case REGIST:
			req.setSmsTemplateCode(regis_code);
			break;
		case LOGIN:
			req.setSmsTemplateCode(login_code);
			break;
		case UPDATEPWD:
			req.setSmsTemplateCode(update_password_code);
			break;
		case UPDATEINFO:
			req.setSmsTemplateCode(update_info_code);
			break;
		case IDENTITY_VERIFICATION:
			req.setSmsTemplateCode(login_unusual_code);
			break;
		}
		try {
//			System.getProperties().setProperty("http.proxyHost", "192.168.1.2");
//			System.getProperties().setProperty("http.proxyPort", "82");
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			LOGGER.info("mobile" + mobile + "阿里大鱼返回结果" + rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void SendOrderMessage(String mobile, String orderId, String temp) {
		// TODO Auto-generated method stub
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		req.setRecNum(mobile);
		JSONObject paramSms = new JSONObject();
		paramSms.put("orderId", orderId);
		req.setSmsTemplateCode(temp);
		try {
//			System.getProperties().setProperty("http.proxyHost", "192.168.1.2");
//			System.getProperties().setProperty("http.proxyPort", "82");
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			LOGGER.info("mobile" + mobile + "阿里大鱼返回结果" + rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}

	}
}
