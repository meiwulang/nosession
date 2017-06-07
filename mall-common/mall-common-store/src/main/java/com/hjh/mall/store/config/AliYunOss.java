package com.hjh.mall.store.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.UploadFileRequest;

/**
 * @Project: hjy-store
 * @Description 阿里云oss的文件系统
 * @author 杨益桦
 * @date 2016年7月9日
 * @version V1.0
 */
public class AliYunOss implements HjyStore {
	private String endpoint;

	private String accessKeyId;

	private String accessKeySecret;

	private String bucketName;

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

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	@Override
	public void setProxy(String ip, String port) {
		// 先拿环境变量
		String http_proxy_ip = System.getenv("http_proxy_ip");
		String http_proxy_port = System.getenv("http_proxy_port");
		if (StringUtils.isNotBlank(http_proxy_ip) && StringUtils.isNotBlank(http_proxy_port)) {
			setHttp_proxy_ip(http_proxy_ip);
			setHttp_proxy_port(http_proxy_port);
		} else {
			setHttp_proxy_ip(ip);
			setHttp_proxy_port(port);
		}
	}

	private OSSClient getClient() {
		OSSClient ossClient = null;
		// 有代理服务器，则设置代理服务器
		if (StringUtils.isNotBlank(http_proxy_ip) && StringUtils.isNotBlank(http_proxy_port)) {
			ClientConfiguration conf = new ClientConfiguration();
			conf.setProxyHost(http_proxy_ip);
			conf.setProxyPort(Integer.parseInt(http_proxy_port));
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		} else {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		}
		return ossClient;
	}

	@Override
	public void upload(String key, byte[] bs) {
		OSSClient ossClient = getClient();
		ossClient.putObject(bucketName, key, new ByteArrayInputStream(bs));
		ossClient.shutdown();
	}

	@Override
	public void upload(String key, String string) {
		upload(key, string.getBytes());
	}

	@Override
	public void upload(String key, URL url) {
		InputStream inputStream = null;
		try {
			inputStream = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		upload(key, inputStream);
	}

	@Override
	public void upload(String key, File file) {
		OSSClient ossClient = getClient();
		ossClient.putObject(bucketName, key, file);
		ossClient.shutdown();
	}

	@Override
	public void upload(String key, InputStream inputStream) {
		OSSClient ossClient = getClient();
		ossClient.putObject(bucketName, key, inputStream);
		ossClient.shutdown();
	}

	@Override
	public void downloadFile(String key, String path) {
		OSSClient ossClient = getClient();
		ossClient.getObject(new GetObjectRequest(bucketName, key), new File(path));
		ossClient.shutdown();

	}

	@Override
	public void breakPointUpload(String key, String path, int taskNum, int mbNum) {
		OSSClient ossClient = getClient();
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
		uploadFileRequest.setUploadFile(path);
		uploadFileRequest.setTaskNum(taskNum);
		// uploadFileRequest.setCheckpointFile(checkpointFile);
		uploadFileRequest.setPartSize(mbNum * 1024 * 1024);
		uploadFileRequest.setEnableCheckpoint(true);
		try {
			ossClient.uploadFile(uploadFileRequest);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 关闭client
		ossClient.shutdown();
	}

	@Override
	public void downloadStream(String key, String path) {
		OSSClient ossClient = getClient();
		OSSObject ossObject = ossClient.getObject(bucketName, key);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(path));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				bufWriter.write(line);
				bufWriter.newLine();
				System.out.println("\n" + line);
				bufWriter.flush();
			}
			bufWriter.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ossClient.shutdown();
	}

	@Override
	public void downloadRange(String key, String path, int start, int end) {
		OSSClient ossClient = getClient();
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		// 获取0~1000字节范围内的数据，包括0和1000，共1001个字节的数据
		getObjectRequest.setRange(start, end);

		// 范围下载
		OSSObject ossObject = ossClient.getObject(getObjectRequest);

		InputStream in = ossObject.getObjectContent();
		try {
			// for (int n = 0; n != -1;) {
			// n = in.read(buf, 0, buf.length);
			// }
			writeToLocal(path, in);
			// InputStream数据读完成后，一定要close，否则会造成连接泄漏
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 关闭client
		ossClient.shutdown();
	}

	private static void writeToLocal(String destination, InputStream input) throws IOException {
		int index;
		byte[] bytes = new byte[1024];
		FileOutputStream downloadFile = new FileOutputStream(destination);
		while ((index = input.read(bytes)) != -1) {
			downloadFile.write(bytes, 0, index);
			downloadFile.flush();
		}
		downloadFile.close();
		input.close();
	}

	@Override
	public void breakPointDownLoad(String key, String path, int taskNum) {
		OSSClient ossClient = getClient();
		// 下载请求，10个任务并发下载，启动断点续传
		DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
		downloadFileRequest.setDownloadFile(path);
		downloadFileRequest.setTaskNum(taskNum);
		downloadFileRequest.setEnableCheckpoint(true);

		// 下载文件
		DownloadFileResult downloadRes = null;
		try {
			downloadRes = ossClient.downloadFile(downloadFileRequest);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 下载成功时，会返回文件的元信息
		downloadRes.getObjectMetadata();

		// 关闭client
		ossClient.shutdown();
	}

	@Override
	public boolean doesObjectExist(String key) {
		OSSClient ossClient = getClient();
		boolean result = ossClient.doesObjectExist(bucketName, key);
		ossClient.shutdown();
		return result;
	}

	public static void main(String[] args) {
		System.out.println(System.getenv("JAVA_HOME"));
	}

}
