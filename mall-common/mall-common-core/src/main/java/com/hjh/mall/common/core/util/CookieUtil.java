package com.hjh.mall.common.core.util;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CookieUtil {
    
    public static String getCookie(HttpServletRequest request, String cookieKey) {
        if (null != request && StringUtils.isNotEmpty(cookieKey)) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (cookieKey.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }
    
    public static String[] getCookies(HttpServletRequest request, String... cookieKeys) {
        if (null != request && null != cookieKeys && 0 != cookieKeys.length) {
            List<String> cookieKeyList = Arrays.asList(cookieKeys);
            String[] result = new String[cookieKeys.length];
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    int idx = cookieKeyList.indexOf(cookie.getName());
                    if (-1 != idx) {
                        result[idx] = cookie.getValue();
                    }
                }
            }
            return result;
        }
        return null;
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue) {
        setCookie(response, cookieKey, cookieValue, null, null, null, null);
    }
    
    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue) {
        setCookie(response, cookieKey, cookieValue, null, null, null, true);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, int expiry) {
        setCookie(response, cookieKey, cookieValue, null, null, expiry, null);
    }
    
    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue,
            int expiry) {
        setCookie(response, cookieKey, cookieValue, null, null, expiry, true);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String path) {
        setCookie(response, cookieKey, cookieValue, null, path, null, null);
    }
    
    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue,
            String path) {
        setCookie(response, cookieKey, cookieValue, null, path, null, true);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain,
            String path) {
        setCookie(response, cookieKey, cookieValue, domain, path, null, null);
    }
    
    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue,
            String domain, String path) {
        setCookie(response, cookieKey, cookieValue, domain, path, null, true);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain,
            String path, Integer expiry) {
        setCookie(response, cookieKey, cookieValue, domain, path, expiry, null);
    }
    
    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue,
            String domain, String path, Integer expiry) {
        setCookie(response, cookieKey, cookieValue, domain, path, expiry, true);
    }
    
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain,
            String path, Integer expiry, Boolean isHttpOnly) {
        if (null != response && StringUtils.isNotEmpty(cookieKey)) {
            Cookie cookie = new Cookie(cookieKey, cookieValue);
            if (null != domain) {
                cookie.setDomain(domain);
            }
            if (null != path) {
                cookie.setPath(path);
            }
            if (null != expiry) {
                cookie.setMaxAge(expiry);
            }
//            if (null != isHttpOnly) {
//                cookie.setHttpOnly(isHttpOnly);
//            }
            response.addCookie(cookie);
        }
    }
    
}
