package com.hjh.mall.cache.cache.sequence;

import java.util.Date;

import javax.annotation.Resource;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.common.core.util.DateTimeUtil;

public class KeyGenerate {

	/**
	 * 自增值 默认1
	 */
	private long by = 1;

	/**
	 * 失效时间 默认一天
	 */
	private int expireSecs = 86400;

	/**
	 * 序列号长度 默认16
	 */
	private int length = 16;

	/**
	 * key值长度
	 */
	private int keylength = 8;

	public long getBy() {
		return by;
	}

	public void setBy(long by) {
		this.by = by;
	}

	public int getExpireSecs() {
		return expireSecs;
	}

	public void setExpireSecs(int expireSecs) {
		this.expireSecs = expireSecs;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getKeylength() {
		return keylength;
	}

	public void setKeylength(int keylength) {
		this.keylength = keylength;
	}

	@Resource
	private CacheHelper cacheHelper;

	// 获取唯一序列号
	public String getKeyGenerate(String prefix) {
		StringBuilder builder = new StringBuilder();
		String table_key = DateTimeUtil.toString(new Date(), DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		builder.append(prefix);
		builder.append("_");
		builder.append(DateTimeUtil.toString(new Date(), table_key));
		long keyGenerate = cacheHelper.incrBb(builder.toString(), by);
		if (by == keyGenerate) {
			cacheHelper.set(builder.toString(), String.valueOf(by), expireSecs);
		}
		return table_key + String.format("%0" + (length - keylength) + "d", (int) (keyGenerate));
	};

}
