package com.hjh.mall.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class SessionClientInfo implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected String access_token;
    
    protected String client_id;
    
    protected String auth_id;
    
    protected String user_token;
    
    public String toStringInfo() {
        return JSON.toJSONString(this);
    }
    
    public static SessionClientInfo fromStringInfo(String string) {
        if (StringUtils.isNotBlank(string)) {
            return JSON.parseObject(string, SessionClientInfo.class);
        }
        return null;
    }
    
    public String getAccess_token() {
        return access_token;
    }
    
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    
    public String getAuth_id() {
        return auth_id;
    }
    
    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }
    
    public String getUser_token() {
        return user_token;
    }
    
    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SessionClientInfo [access_token=").append(access_token).append(", client_id=")
                .append(client_id).append(", auth_id=").append(auth_id).append(", user_token=").append(user_token)
                .append(", getAccess_token()=").append(getAccess_token()).append(", getClient_id()=")
                .append(getClient_id()).append(", getAuth_id()=").append(getAuth_id()).append(", getUser_token()=")
                .append(getUser_token()).append("]");
        return builder.toString();
    }
    
}
