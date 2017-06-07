package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class Operator extends Updatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String operator_id;

	private String operator_name;

	private String password;

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Operator [operator_id=");
		builder.append(operator_id);
		builder.append(", operator_name=");
		builder.append(operator_name);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param operator_name
	 */
	public Operator(String operator_name) {
		super();
		this.operator_name = operator_name;
	}

	/**
	 * 
	 */
	public Operator() {
		super();
	}

}
