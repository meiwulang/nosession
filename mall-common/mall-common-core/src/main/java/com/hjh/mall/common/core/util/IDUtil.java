package com.hjh.mall.common.core.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class IDUtil {

	public static int first = 0;

	public final static int last = 9999;

	public static String genUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String genUUIDAddPrefix(String prefix) {
		String uuid = genUUID();
		if (StringUtils.isNotBlank(prefix)) {
			uuid = prefix + uuid;
		}
		return uuid;
	}
	
	public static String genUUIDBySuffix(String filename) {
		String uuid = genUUID();
		if (StringUtils.isNotBlank(filename) && filename.indexOf(".")>0) {
			String suffix=filename.substring(filename.lastIndexOf(".")+1);
			uuid = uuid+"."+suffix;
		}
		return uuid;
	}
	public static void main(String[] args){
		System.out.println(genUUIDBySuffix("a.jpg"));
		
	}

	// 保证同一时间内拿到的id是唯一的
	public static synchronized String getOrdID16() {
		String ordNo;
		ordNo = String.format("%3d", (int) (Math.random() * 999)).replace(" ", "0");
		ordNo = String.valueOf(System.currentTimeMillis()) + ordNo;
		return ordNo;
	}

	// 保证同一时间内拿到的id是唯一的
//	public static synchronized String getMboileTelRandom11() {
//		String ordNo = "111";
//		ordNo = ordNo + String.format("%8d", (int) (Math.random() * 99999999)).replace(" ", "0");
//		return ordNo;
//	}
	

}
