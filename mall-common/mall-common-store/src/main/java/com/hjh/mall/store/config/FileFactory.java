package com.hjh.mall.store.config;

/**
 * @Project: hjy-store
 * @Description 文件云系统工厂类
 * @author 杨益桦
 * @date 2016年7月9日
 * @version V1.0
 */
public class FileFactory {

	private HjyStore hjyStore;

	public FileFactory(HjyStore hjyStore) {
		this.hjyStore = hjyStore;
	}

	public HjyStore crerate() {
		return hjyStore;
	}
}
