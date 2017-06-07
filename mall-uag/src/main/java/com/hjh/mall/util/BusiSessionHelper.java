package com.hjh.mall.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.config.ConfigValues;
import com.hjh.mall.constants.CacheKeys;
import com.hjh.mall.constants.SysContants;
import com.hjh.mall.model.SessionClientInfo;

public class BusiSessionHelper extends SessionHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusiSessionHelper.class);

	private CacheHelper cacheHelper;

	public CacheHelper getCacheHelper() {
		return cacheHelper;
	}

	public void setCacheHelper(CacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}

	public void init() {
		this.sessionPrefixs.add(CacheKeys.ACCESS_TOKEN_PREFIX);
		this.sessionPrefixs.add(CacheKeys.SESSION_CLIENT_INFO_PREFIX);
	}

	private BusiSessionHelper() {
		this.init();
	}

	private List<String> sessionPrefixs = new ArrayList<String>();

	@Override
	protected int getSessionTimeout() {
		return ConfigValues.getSessionTimeout();
	}

	@Override
	public void setInfoForSession(String key, String info) {
		super.setInfoForSession(key, info);
	}

	@Override
	protected List<String> getSessionPrefixs() {
		return this.sessionPrefixs;
	}

	/**
	 * 更换会话令牌，生成一个新的会话令牌并保存会话身份对象
	 * 
	 * @param sessionIdentity
	 *            会话身份对象
	 * @return 新生成的会话令牌
	 */
	public String renewAccess_token(SessionIdentity sessionIdentity) {
		// 生成会话令牌
		String access_token = IDUtil.genUUID();
		// 获得会话令牌key
		String access_tokenKey = getAccess_tokenKey(access_token);
		// 生成字符串信息
		String stringInfo = sessionIdentity.toStringInfo();
		// 根据会话令牌保存信息到缓存
		setInfoForSession(access_tokenKey, stringInfo);
		return access_token;
	}

	/**
	 * 根据会话令牌获取保存的会话身份对象
	 * 
	 * @param access_token
	 *            请求携带的会话令牌
	 * @return 对应的会话身份对象或者null
	 */
	public SessionIdentity fetchSessionIdentityByAccess_token(String access_token) {
		try {
			// 获得会话令牌key
			String access_tokenKey = getAccess_tokenKey(access_token);
			// 从缓存获取会话令牌对应的字符串信息
			String stringInfo = fetchInfoByKey(access_tokenKey);
			// 解析会话身份信息
			return SessionIdentity.fromStringInfo(stringInfo);
		} catch (Exception e) {
			LOGGER.error("fetch SessionIdentity by access_token failed", e);
			return null;
		}
	}

	public void refreshSession(String access_token, SessionIdentity sessionIdentity) {
		super.refreshSession(access_token);
		String userType = sessionIdentity.getUserType();
		if (SysContants.SESSION_USER_TYPE_CLIENT.equals(userType)) {
			getCacheHelper().refresh(getClient_idKey(sessionIdentity.getClientId()), getSessionTimeout());
		} else if (SysContants.SESSION_USER_TYPE_OPERATOR.equals(userType)) {
			getCacheHelper().refresh(getOperator_idKey(sessionIdentity.getClientId()), getSessionTimeout());
		}
	}

	public void destroySession(String access_token, SessionIdentity sessionIdentity) {
		super.destroySession(access_token);
		if (null != sessionIdentity) {
			String userType = sessionIdentity.getUserType();
			if (SysContants.SESSION_USER_TYPE_CLIENT.equals(userType)) {
				getCacheHelper().destroy(getClient_idKey(sessionIdentity.getClientId()));
			} else if (SysContants.SESSION_USER_TYPE_OPERATOR.equals(userType)) {
				getCacheHelper().destroy(getOperator_idKey(sessionIdentity.getClientId()));
			}
		}
	}

	public String getSessionTokenByClientId(String client_id) {
		return fetchInfoByKey(getClient_idKey(client_id));
	}

	public String getSessionTokenByOperatorNo(String client_id) {
		return fetchInfoByKey(getOperator_idKey(client_id));
	}

	public String getAccess_tokenKey(String access_token) {
		return CacheKeys.ACCESS_TOKEN_PREFIX + access_token;
	}

	public String getClient_idKey(String client_id) {
		return CacheKeys.CLIENT_ID_PREFIX + client_id;
	}

	public String getOperator_idKey(String operator_id) {
		return CacheKeys.OPERATOR_ID_PREFIX + operator_id;
	}

	/**
	 * 使用会话令牌保存会话客户对象
	 * 
	 * @param access_token
	 *            会话令牌
	 * @param sessionClientInfo
	 *            会话客户对象
	 */
	public void saveSessionClientInfo(String access_token, SessionClientInfo sessionClientInfo) {
		// 获得会话客户key
		String sessionClientInfoKey = getSessionClientInfoKey(access_token);
		// 生成字符串信息
		String stringInfo = sessionClientInfo.toStringInfo();
		// 根据会话令牌保存信息到缓存
		setInfoForSession(sessionClientInfoKey, stringInfo);
	}

	/**
	 * 根据会话令牌获取保存的会话客户对象
	 * 
	 * @param access_token
	 *            会话令牌
	 * @return 对应的会话客户对象或者null
	 */
	public SessionClientInfo fetchSessionClientInfoByAccess_token(String access_token) {
		try {
			// 获得会话客户key
			String sessionClientInfoKey = getSessionClientInfoKey(access_token);
			// 从缓存获取会话客户key对应的字符串信息
			String stringInfo = fetchInfoByKey(sessionClientInfoKey);
			// 解析会话客户对象
			return SessionClientInfo.fromStringInfo(stringInfo);
		} catch (Exception e) {
			LOGGER.error("fetch SessionClientInfo by access_token failed", e);
			return null;
		}
	}

	private String getSessionClientInfoKey(String access_token) {
		return CacheKeys.SESSION_CLIENT_INFO_PREFIX + access_token;
	}

	@Override
	protected CacheHelper getBusiCacheHelper() {
		return cacheHelper;
	}

}
