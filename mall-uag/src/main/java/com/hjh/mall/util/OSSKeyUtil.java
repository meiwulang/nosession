/**
 * 
 */
package com.hjh.mall.util;

/**
 * @Project: hjy-middle
 * @Description 设置oss key
 * @author 曾繁林
 * @date 2016年7月11日
 * @version V1.0 
 */
public class OSSKeyUtil {
	public static String getClientAvatarKey(String client_id,String suffix){
		return "client/avatar"+client_id+suffix;
	}
	public static String getDeviceAvatarKey(String D){
		return null;
	}
}
