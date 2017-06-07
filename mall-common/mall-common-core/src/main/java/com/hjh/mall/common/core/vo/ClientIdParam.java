package com.hjh.mall.common.core.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @author chengjia
 *
 */
public class ClientIdParam extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String client_id;

	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}

	/**
	 * @param client_id the client_id to set
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientIdParam [client_id=");
		builder.append(client_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append("]");
		return builder.toString();
	}
	
	

}
