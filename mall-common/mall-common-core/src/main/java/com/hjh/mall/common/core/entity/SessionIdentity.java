package com.hjh.mall.common.core.entity;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class SessionIdentity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected String clientId;
	protected String operatorName;

	protected String userType;

	protected Set<String> agentId;

	protected Set<String> permissions;

	protected String roleId;
	private String mobile_tel;
	protected String invite_code;
	private String enterprise_linkman;// 企业联系人; 

	public String getEnterprise_linkman() {
		return enterprise_linkman;
	}

	public void setEnterprise_linkman(String enterprise_linkman) {
		this.enterprise_linkman = enterprise_linkman;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String toStringInfo() {
		return JSON.toJSONString(this);
	}

	public static SessionIdentity fromStringInfo(String string) {
		if (StringUtils.isNotBlank(string)) {
			return JSON.parseObject(string, SessionIdentity.class);
		}
		return null;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public Set<String> getAgentId() {
		return agentId;
	}

	public void setAgentId(Set<String> agentId) {
		this.agentId = agentId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionIdentity [clientId=");
		builder.append(clientId);
		builder.append(", operatorName=");
		builder.append(operatorName);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", agentId=");
		builder.append(agentId);
		builder.append(", permissions=");
		builder.append(permissions);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", invite_code=");
		builder.append(invite_code);
		builder.append("]");
		return builder.toString();
	}

}
