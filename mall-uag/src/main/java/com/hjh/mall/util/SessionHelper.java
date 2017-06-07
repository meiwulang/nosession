package com.hjh.mall.util;

import java.util.List;

import com.hjh.mall.cache.cache.helper.CacheHelper;

public abstract class SessionHelper {
    
    protected abstract CacheHelper getBusiCacheHelper();
    
    protected abstract int getSessionTimeout();
    
    protected abstract List<String> getSessionPrefixs();
    
    /**
     * 保存信息到会话
     * @param key
     * @param info 信息
     */
    public void setInfoForSession(String key, String info) {
        getBusiCacheHelper().set(key, info, getSessionTimeout());
    }
    
    /**
     * 刷新会话
     * @param access_token
     */
    public void refreshSession(String access_token) {
        int expireSecs = getSessionTimeout();
        CacheHelper cacheHelper = getBusiCacheHelper();
        List<String> sessionPrefixs = getSessionPrefixs();
        for (String sessionPrefix : sessionPrefixs) {
            cacheHelper.refresh(sessionPrefix + access_token, expireSecs);
        }
    }
    
    /**
     * 销毁会话
     * @param access_token
     */
    public void destroySession(String access_token) {
        CacheHelper cacheHelper = getBusiCacheHelper();
        List<String> sessionPrefixs = getSessionPrefixs();
        for (String sessionPrefix : sessionPrefixs) {
            cacheHelper.destroy(sessionPrefix + access_token);
        }
    }
    
    
    /**
     * 根据key获取保存的信息
     * @param key
     * @return 对应的信息或者null
     */
    public String fetchInfoByKey(String key) {
        return getBusiCacheHelper().get(key);
    }
    
}
