package com.hjh.mall.category.context;

import com.hjh.mall.category.entity.Client;
import com.hjh.mall.common.core.context.DataContext;
import com.hjh.mall.common.core.entity.SessionIdentity;

public class HJYBizDataContext {

	public static final String KEY_NAMESPACE = "mall.middle";

	public static final String KEY_USER = KEY_NAMESPACE + ".User";

	public static final String KEY_SYS_OPERATOR = KEY_NAMESPACE + ".sysOperator";

	public static final String KEY_OPERATOR_ID = KEY_NAMESPACE + ".operatorId";

	public static final String KEY_SESSION_IDENTITY = KEY_NAMESPACE + ".sessionIdentity";

	public static void setUser_token(String access_token) {
		DataContext.setUser_token(access_token);
	}

	public static String getUser_token() {
		return DataContext.getUser_token();
	}

	public static String removeAccess_token() {
		return DataContext.removeUser_token();
	}

	public static Client getRegUser() {
		return (Client) get(KEY_USER);
	}

	public static void setRegUser(Client User) {
		set(KEY_USER, User);
	}

	public static Client removeRegUser() {
		return (Client) remove(KEY_USER);
	}

	public static Long getOperatorId() {
		return (Long) get(KEY_OPERATOR_ID);
	}

	public static void setOperatorId(Long operatorId) {
		set(KEY_OPERATOR_ID, operatorId);
	}

	public static Long removeOperatorId() {
		return (Long) remove(KEY_OPERATOR_ID);
	}

	public static SessionIdentity getSessionIdentity() {
		return (SessionIdentity) get(KEY_SESSION_IDENTITY);
	}

	public static void setSessionIdentity(SessionIdentity operatorId) {
		set(KEY_SESSION_IDENTITY, operatorId);
	}

	public static SessionIdentity removeSessionIdentity() {
		return (SessionIdentity) remove(KEY_SESSION_IDENTITY);
	}

	public static Object get(String key) {
		return DataContext.get(key);
	}

	public static Object set(String key, Object value) {
		return DataContext.set(key, value);
	}

	public static Object remove(String key) {
		return DataContext.remove(key);
	}

	public static void clear() {
		DataContext.clear();
	}

}
