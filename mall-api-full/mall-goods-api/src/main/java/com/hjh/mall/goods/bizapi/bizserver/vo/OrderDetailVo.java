package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-mall
 * @Description 创建订单vo
 * @author 王斌
 * @date 2017年2月20日
 * @since 1.0
 */
public class OrderDetailVo extends HJYVO {

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

	public Double getPrdt_price() {
		return prdt_price;
	}

	public void setPrdt_price(Double prdt_price) {
		this.prdt_price = prdt_price;
	}

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}

	private static final long serialVersionUID = 1L;
	// 商品名称
	private String prdt_name;
	// 规格编号
	@NotBlank
	private String standard_id;

	// 必填规格
	@NotBlank
	private String standard_must;

	// 第一个选填规格
	private String optional_first;

	// 第二个选填规格
	private String optional_second;

	// 购买数量
	private Integer prdt_num;

	// 商品单价
	private Double prdt_price;

}
