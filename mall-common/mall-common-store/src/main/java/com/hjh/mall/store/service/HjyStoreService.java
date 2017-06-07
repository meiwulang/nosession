package com.hjh.mall.store.service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @Project: hjy-store
 * @Description 文件云存储oss对面接口
 * @author 杨益桦
 * @date 2016年7月9日
 * @version V1.0
 */
public interface HjyStoreService {

	/**
	 * @date 2017年4月12日
	 * @Description: 设置代理,先拿环境变量
	 * @author 杨益桦
	 * @param ip
	 * @param port
	 *            void
	 */
	public void setProxy(String ip, String port);

	/**
	 * @date 2016年7月11日
	 * @Description: 上传byte[] 数组
	 * @param key
	 *            folderName/keyname 1、文件夹名字/文件名字 2、 文件夹不存在的时候，会新建 3、无后缀
	 * @param bs
	 * @return void
	 */
	public void upload(String key, byte[] bs);

	/**
	 * @date 2016年7月11日
	 * @Description: 上传字符串
	 * @param key
	 *            folderName/keyname 1、文件夹名字/文件名字 2、 文件夹不存在的时候，会新建 3、无后缀
	 * @param string
	 * @return void
	 */
	public void upload(String key, String string);

	/**
	 * @date 2016年7月11日
	 * @Description: 上传url
	 * @param key
	 *            folderName/keyname 1、文件夹名字/文件名字 2、 文件夹不存在的时候，会新建 3、无后缀
	 * @param url
	 * @return void
	 */
	public void upload(String key, URL url);

	/**
	 * @date 2016年7月11日
	 * @Description: 上传文件
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、 文件夹不存在的时候，会新建
	 *            3、后缀必填
	 * @param file
	 * @return void
	 */
	public void uploadFile(String key, File file);

	/**
	 * @date 2016年7月11日
	 * @Description: 上传文件
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、 文件夹不存在的时候，会新建
	 *            3、后缀必填
	 * @param inputStream
	 * @return void
	 */
	public void uploadFile(String key, InputStream inputStream);

	/**
	 * @date 2016年7月11日
	 * @Description: 断点续传 ps指定check文件还未做
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、后缀必填
	 * @param path
	 *            绝对路径，相对路径都可以
	 * @param taskNum
	 *            分片数量
	 * @param mbNum
	 *            每片大小，单位 ：M
	 * @return void
	 */
	public void breakPointUpload(String key, String path, int taskNum, int mbNum);

	/**
	 * @date 2016年7月11日
	 * @Description: 直接下载文件
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、后缀必填
	 * @param path
	 *            绝对路径，相对路径都可以
	 * @return void
	 */
	public void downloadFile(String key, String path);

	/**
	 * @date 2016年7月11日
	 * @Description: 流式下载文件，不支持图片
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、后缀必填
	 * @param path
	 *            绝对路径，相对路径都可以
	 * @return void
	 */
	public void downloadStream(String key, String path);

	/**
	 * @date 2016年7月11日
	 * @Description: 范围下载
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、后缀必填
	 * @param path
	 *            绝对路径，相对路径都可以
	 * @param start
	 *            获取0~1000字节范围内的数据，包括0和1000，共1001个字节的数据
	 * @param end
	 * @return void
	 */
	public void downloadRange(String key, String path, int start, int end);

	/**
	 * @date 2016年7月11日
	 * @Description: 断点续传下载
	 * @param key
	 *            folderName/filename.postfix 1、文件夹名字/文件名字.后缀 2、后缀必填
	 * @param path
	 *            绝对路径，相对路径都可以
	 * @param taskNum
	 *            分片数量
	 * @return void
	 */
	public void breakPointDownLoad(String key, String path, int taskNum);

	/**
	 * @date 2016年7月11日
	 * @Description: 判断key代表的文件是否存在
	 * @param key
	 * @return boolean
	 */
	public boolean doesObjectExist(String key);

}
