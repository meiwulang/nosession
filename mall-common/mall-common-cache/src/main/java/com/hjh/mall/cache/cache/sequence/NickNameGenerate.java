package com.hjh.mall.cache.cache.sequence;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.cache.cache.helper.CacheHelper;

/**
 * @Project: hjh-cache
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月6日
 * @version V1.0
 */
public class NickNameGenerate {

	public final static String NICK_NAME_PREFIX = "hjh";
	public final static String NICK_NAME_KEY = "hjh_default_nick_name_key";
	private long basicValue = 888;

	@Resource
	private CacheHelper cacheHelper;

	// 获取默认的全知道用户昵称
	public String getDefaultNickName() {
		StringBuilder builder = new StringBuilder();
		builder.append(NICK_NAME_PREFIX);
		String old_value = cacheHelper.get(NICK_NAME_KEY);
		if (StringUtils.isBlank(old_value)) {
			long new_value = basicValue + 1;
			cacheHelper.set(NICK_NAME_KEY, String.valueOf(new_value), -1);
			old_value = String.valueOf(basicValue);
		} else {
			long new_value = Long.parseLong(old_value) + 1;
			cacheHelper.set(NICK_NAME_KEY, String.valueOf(new_value), -1);

		}
		builder.append(old_value);
		return builder.toString();
	};
}
