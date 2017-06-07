package com.hjh.mall.common.core.context;

import java.util.HashMap;
import java.util.Map;

import com.hjh.mall.common.core.entity.SessionIdentity;

/**
 * 数据上下文，用于绑定线程上携带的信息
 * 
 * @author chengjia
 *
 */
public class DataContext {

	private static final String KEY_NAMESPACE = "hjy";

	private static final String KEY_CLIENT_ID = KEY_NAMESPACE + ".client_id";

	private static final String KEY_ACCESS_TOKEN = ".access_token";
	private static final String KEY_USER_TOKEN = ".user_token";

	public static final String KEY_SESSION_IDENTITY = KEY_NAMESPACE + ".sessionIdentity";

	public static final String KEY_REG_USER = KEY_NAMESPACE + ".user";

	public static final String KEY_SYS_OPERATOR = KEY_NAMESPACE + ".sysoperator";

	public static final String KEY_OPERATOR_ID = KEY_NAMESPACE + ".operatorId";

	private static ThreadLocal<DataContext> dataContext = new ThreadLocal<DataContext>() {
		protected DataContext initialValue() {
			return new DataContext();
		}
	};

	private Map<String, Object> store = new HashMap<String, Object>();

	// 获取线程上下文
	private static DataContext getDataContext() {
		return dataContext.get();
	}

	// 线程上下文存储容器
	private static Map<String, Object> getStore() {
		return getDataContext().store;
	}

	public static Object set(String key, Object value) {
		return getStore().put(key, value);
	}

	public static Object get(String key) {
		return getStore().get(key);
	}

	public static void setClientID(String client_id) {
		set(KEY_CLIENT_ID, client_id);
	}

	public static String getClientID() {
		return (String) get(KEY_CLIENT_ID);
	}

	public static String removeAccess_token() {
		return (String) remove(KEY_ACCESS_TOKEN);
	}

	public static void setAccess_token(String access_token) {
		set(KEY_ACCESS_TOKEN, access_token);
	}

	public static String getAccess_token() {
		return (String) get(KEY_ACCESS_TOKEN);
	};

	public static void setSessionIdentity(SessionIdentity identity) {
		set(KEY_SESSION_IDENTITY, identity);
	}

	public static SessionIdentity getSessionIdentity() {
		return (SessionIdentity) get(KEY_SESSION_IDENTITY);
	}

	public static void removetoken() {
		getStore().remove(KEY_ACCESS_TOKEN);
	}

	public static void removeSessionIdentity() {
		getStore().remove(KEY_SESSION_IDENTITY);
	}

	public static Object remove(String key) {
		return getStore().remove(key);
	}

	public static void clear() {
		getStore().clear();
	}

	public static String getUser_token() {
		return (String) get(KEY_USER_TOKEN);
	}

	public static String removeUser_token() {
		return (String) remove(KEY_USER_TOKEN);
	}

	public static void setUser_token(String userToken) {
		set(KEY_ACCESS_TOKEN, userToken);

	}

}
