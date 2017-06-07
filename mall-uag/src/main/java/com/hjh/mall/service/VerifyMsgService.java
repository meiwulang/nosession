package com.hjh.mall.service;

import java.util.Map;

public interface VerifyMsgService {
	/**
	 * @date 2016年7月12日
	 * @Description: 获得验证码
	 * @param mobile
	 *            电话号
	 * @param type
	 *            1 登录 2注册 3修改密码 4修改个人信息 5身份验证
	 * @return Map
	 */
	public Map<String, Object> getValidate(String mobile, String type);

	/**
	 * @date 2016年7月12日
	 * @Description: 验证码验证
	 * @param verify_msg
	 *            验证码
	 * @param verify_msg_id
	 *            验证码ID
	 * @param type
	 *            1 登录 2注册 3修改密码 4修改个人信息 5身份验证
	 * @return Map
	 */
	public Map<String, Object> validataParam(String verify_msg, String verify_msg_id, String type, String mobile_tel);

	/**
	 * @date 2016年7月12日
	 * @Description: 验证码验证
	 * @param verify_msg
	 *            验证码
	 * @param verify_msg_id
	 *            验证码ID
	 * @param type
	 *            1 登录 2注册 3修改密码 4修改个人信息 5身份验证
	 * @return Map
	 */
	public void sendTextMessage(String value);

	/**
	 * @date 2016年12月8日
	 * @Description: 删除验证码
	 * 
	 */
	public void deleteAllMessage();

}
