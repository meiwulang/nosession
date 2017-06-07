package com.hjh.mall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hjh.mall.common.core.entity.Updatable;

public class OrderDetail extends Updatable implements Serializable {
	// 主键
	private String order_detail_id;

	private String prdt_name;

	// 必填规格
	private String standard_must;

	// 第一个选填规格
	private String optional_first;

	// 第二个选填规格
	private String optional_second;

	// 购买数量
	private Integer prdt_num;

	// 商品单价
	private BigDecimal prdt_price;
	// 商品单价
	private String standard_id;
	// 计量单位
	private String metadata_name;

	private static final long serialVersionUID = 1L;

	public String getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(String order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public String getPrdt_name() {
		return prdt_name;
	}

	public void setPrdt_name(String prdt_name) {
		this.prdt_name = prdt_name;
	}

	public String getStandard_must() {
		return standard_must;
	}

	public void setStandard_must(String standard_must) {
		this.standard_must = standard_must;
	}

	public String getOptional_first() {
		return optional_first;
	}

	public void setOptional_first(String optional_first) {
		this.optional_first = optional_first;
	}

	public String getOptional_second() {
		return optional_second;
	}

	public void setOptional_second(String optional_second) {
		this.optional_second = optional_second;
	}

	public Integer getPrdt_num() {
		return prdt_num;
	}

	public void setPrdt_num(Integer prdt_num) {
		this.prdt_num = prdt_num;
	}

	public BigDecimal getPrdt_price() {
		return prdt_price;
	}

	public void setPrdt_price(BigDecimal prdt_price) {
		this.prdt_price = prdt_price;
	}

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

}