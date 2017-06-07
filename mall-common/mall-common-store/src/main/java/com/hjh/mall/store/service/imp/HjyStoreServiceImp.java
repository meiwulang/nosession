package com.hjh.mall.store.service.imp;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hjh.mall.store.config.AliYunOss;
import com.hjh.mall.store.config.FileFactory;
import com.hjh.mall.store.config.HjyStore;
import com.hjh.mall.store.service.HjyStoreService;

/**
 * @Project: hjy-store
 * @Description 文件云存储oss对面接口
 * @author 杨益桦
 * @date 2016年7月9日
 * @version V1.0
 */
@Component
public class HjyStoreServiceImp implements HjyStoreService {

	@Resource
	private AliYunOss aliYunOss;

	private String http_proxy_ip;// 代理

	private String http_proxy_port;// 代理

	public String getHttp_proxy_ip() {
		return http_proxy_ip;
	}

	public void setHttp_proxy_ip(String http_proxy_ip) {
		this.http_proxy_ip = http_proxy_ip;
	}

	public String getHttp_proxy_port() {
		return http_proxy_port;
	}

	public void setHttp_proxy_port(String http_proxy_port) {
		this.http_proxy_port = http_proxy_port;
	}

	@Override
	public void setProxy(String ip, String port) {
		setHttp_proxy_ip(ip);
		setHttp_proxy_port(port);
	}

	private HjyStore gertHjyStore() {
		FileFactory factory = new FileFactory(aliYunOss);
		HjyStore hjyStore = factory.crerate();
		hjyStore.setProxy(http_proxy_ip, http_proxy_port);
		return hjyStore;
	}

	@Override
	public void upload(String key, byte[] bs) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.upload(key, bs);

	}

	@Override
	public void upload(String key, String string) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.upload(key, string);
	}

	@Override
	public void upload(String key, URL url) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.upload(key, url);

	}

	@Override
	public void uploadFile(String key, File file) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.upload(key, file);
	}

	@Override
	public void uploadFile(String key, InputStream inputStream) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.upload(key, inputStream);
	}

	@Override
	public void breakPointUpload(String key, String path, int taskNum, int mbNum) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.breakPointUpload(key, path, taskNum, mbNum);
	}

	@Override
	public void downloadFile(String key, String path) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.downloadFile(key, path);
	}

	@Override
	public void downloadStream(String key, String path) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.downloadStream(key, path);
	}

	@Override
	public void downloadRange(String key, String path, int start, int end) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.downloadRange(key, path, start, end);

	}

	@Override
	public void breakPointDownLoad(String key, String path, int taskNum) {
		HjyStore hjyStore = gertHjyStore();
		hjyStore.breakPointDownLoad(key, path, taskNum);
	}

	@Override
	public boolean doesObjectExist(String key) {
		HjyStore hjyStore = gertHjyStore();
		return hjyStore.doesObjectExist(key);
	}

}
