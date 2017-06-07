package com.hjh.mall.common.sms.alibaba;

public interface SendMessage {
	/**
	 * @date 2016年7月12日
	 * @Description: 验证码相关
	 * @param verify_msg
	 *            验证码
	 * @param verify_msg_id
	 *            验证码ID
	 * @param type
	 *            1 登录 2注册 3修改密码 4修改个人信息 5身份验证
	 */
	void SendSmsMessage(String mobile, String verifyMsg, String type);

	/**
	 * @date 2016年7月12日
	 * @Description: 验证码相关
	 * @param verify_msg
	 *            验证码
	 * @param verify_msg_id
	 *            验证码ID
	 * @param type
	 *            1 登录 2注册 3修改密码 4修改个人信息 5身份验证
	 */
	void SendSmsMessageByName(String mobile, String verifyMsg, String type, String name);

	/**
	 * @date 2016年7月18日
	 * @Description: 发送短信通知
	 * @param mobile
	 * @param value
	 * 自定义短信内容
	 */
	void SendTextMessage(String mobile, String value);
	
	/**
	 * @date 2016年7月18日
	 * @Description: 发送短信通知
	 * @param mobile
	 * @param value
	 * 自定义短信内容
	 */
	void SendOrderMessage(String mobile,String orderId,String temp);
}
