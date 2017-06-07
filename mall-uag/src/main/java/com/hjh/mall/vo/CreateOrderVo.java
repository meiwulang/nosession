package com.hjh.mall.vo;

import java.math.BigDecimal;
import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-mall
 * @Description 创建订单vo
 * @author 王斌
 * @date 2017年2月20日
 * @since 1.0
 */
public class CreateOrderVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 商品编号
	@NotBlank
	private String prdt_id;

	// 商品名称
	@NotBlank
	private String prdt_name;
	// 商品图片
	@NotBlank
	private String prdt_img;

	// 订单金额
	private BigDecimal total_money;
	// 收货联系方式
	@NotBlank
	private String consignee_tel;

	// 收货地址
	@NotBlank
	private String address_info;
	// 买家用户名
	@NotBlank
	private String consignee;
	// 买家留言
	private String leaved_word;
	// 商家电话
	@NotBlank
	private String prdt_tel;
	// 规格集合
	private List<OrderDetailVo> orderDetailVos;
	// 商品品牌
	@NotBlank
	private String prdt_brand;
	// 商品类目
	@NotBlank
	private String prdt_nav;

	public String getPrdt_brand() {
		return prdt_brand;
	}

	public void setPrdt_brand(String prdt_brand) {
		this.prdt_brand = prdt_brand;
	}

	public String getPrdt_nav() {
		return prdt_nav;
	}

	public void setPrdt_nav(String prdt_nav) {
		this.prdt_nav = prdt_nav;
	}

	public String getPrdt_id() {
		return prdt_id;
	}

	public void setPrdt_id(String prdt_id) {
		this.prdt_id = prdt_id;
	}

	public String getPrdt_name() {
		return prdt_name;
	}

	public void setPrdt_name(String prdt_name) {
		this.prdt_name = prdt_name;
	}

	public BigDecimal getTotal_money() {
		return total_money;
	}

	public void setTotal_money(BigDecimal total_money) {
		this.total_money = total_money;
	}

	public String getConsignee_tel() {
		return consignee_tel;
	}

	public void setConsignee_tel(String consignee_tel) {
		this.consignee_tel = consignee_tel;
	}

	public String getAddress_info() {
		return address_info;
	}

	public void setAddress_info(String address_info) {
		this.address_info = address_info;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public List<OrderDetailVo> getOrderDetailVos() {
		return orderDetailVos;
	}

	public void setOrderDetailVos(List<OrderDetailVo> orderDetailVos) {
		this.orderDetailVos = orderDetailVos;
	}

	public String getLeaved_word() {
		return leaved_word;
	}

	public void setLeaved_word(String leaved_word) {
		this.leaved_word = leaved_word;
	}

	public String getPrdt_img() {
		return prdt_img;
	}

	public void setPrdt_img(String prdt_img) {
		this.prdt_img = prdt_img;
	}

	public String getPrdt_tel() {
		return prdt_tel;
	}

	public void setPrdt_tel(String prdt_tel) {
		this.prdt_tel = prdt_tel;
	}
}
