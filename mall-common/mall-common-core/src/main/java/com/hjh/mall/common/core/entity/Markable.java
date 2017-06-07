package com.hjh.mall.common.core.entity;

import com.hjh.mall.common.core.entity.Pageable;

public abstract class Markable extends Pageable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected String remark;
	protected String status;
	
	protected String init_date;
	protected String init_time;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
	
	public String getInit_date() {
		return init_date;
	}

	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}

	public String getInit_time() {
		return init_time;
	}

	public void setInit_time(String init_time) {
		this.init_time = init_time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Markable [remark=");
		builder.append(remark);
		builder.append(", status=");
		builder.append(status);
		builder.append(", init_date=");
		builder.append(init_date);
		builder.append(", init_time=");
		builder.append(init_time);
		builder.append("]");
		return builder.toString();
	}

}