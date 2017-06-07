package com.hjh.mall.vo.order;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

public class OrderDetailsVO extends HJYVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull	
	private String client_id;
	
	@NotNull
	private String orderId;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDetailsVO [client_id=").append(client_id)
				.append(", orderId=").append(orderId).append("]");
		return builder.toString();
	}
	
	

}
