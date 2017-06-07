package com.hjh.mall.store.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @Project: hjy-store
 * @Description 文件云系统接口
 * @author 杨益桦
 * @date 2016年7月9日
 * @version V1.0
 */
public interface HjyStore {

	public void upload(String key, byte[] bs);

	public void upload(String key, String string);

	public void upload(String key, URL url);

	public void upload(String key, File file);

	public void upload(String key, InputStream inputStream);

	public void breakPointUpload(String key, String path, int taskNum, int mbNum);

	public void downloadFile(String key, String path);

	public void downloadStream(String key, String path);

	/**
	 * 获取0~1000字节范围内的数据，包括0和1000，共1001个字节的数据
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void downloadRange(String key, String path, int start, int end);

	public void breakPointDownLoad(String key, String path, int taskNum);

	public boolean doesObjectExist(String key);

	public void setProxy(String ip, String port);

}
