package com.hjh.mall.common.core.model;

public class Model {
	
	private String error_info;
	private String error_no;
	private String data;
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getError_no() {
		return error_no;
	}
	public void setError_no(String error_no) {
		this.error_no = error_no;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Model [error_info=");
		builder.append(error_info);
		builder.append(", error_no=");
		builder.append(error_no);
		builder.append(", data=");
		builder.append(data);
		builder.append(", getError_info()=");
		builder.append(getError_info());
		builder.append(", getError_no()=");
		builder.append(getError_no());
		builder.append(", getData()=");
		builder.append(getData());
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
