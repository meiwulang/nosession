package com.hjh.mall.cache.cache.sequence;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.cache.cache.helper.CacheHelper;

/**
 * @Project: hjy-cache
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月20日
 * @version V1.0
 */
public class ClientCodeGenerate {

	public final static String CLIENT_CODE_KEY = "client_code_key.";
	private long basicValue = 1;

	@Resource
	private CacheHelper cacheHelper;

	// 根据邀请码获取用户编号
	public String getClientCode(String invite_code) {
		StringBuilder builder = new StringBuilder();
		builder.append(invite_code);
		String old_value = cacheHelper.get(CLIENT_CODE_KEY + invite_code);
		if (StringUtils.isBlank(old_value)) {
			long new_value = basicValue + 1;
			cacheHelper.set(CLIENT_CODE_KEY + invite_code, String.valueOf(new_value), -1);
			old_value = String.valueOf(basicValue);
		} else {
			long new_value = Long.parseLong(old_value) + 1;
			cacheHelper.set(CLIENT_CODE_KEY + invite_code, String.valueOf(new_value), -1);

		}
		String value = String.format("%0" + 5 + "d", (int) (Long.parseLong(old_value)));
		builder.append(value);
		return builder.toString();
	};
}
