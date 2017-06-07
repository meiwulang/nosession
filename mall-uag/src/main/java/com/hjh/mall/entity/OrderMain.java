package com.hjh.mall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hjh.mall.common.core.entity.Updatable;

public class OrderMain extends Updatable implements Serializable {
	// 主键
	private String order_main_id;

	// 订单号
	private String serialize_num;

	// 商品编号
	private String prdt_id;

	// 商品名称
	private String prdt_name;

	// 订单金额
	private BigDecimal total_money;

	// 买家用户名
	private String consignee;

	// 邀请码
	private String invite_code;

	// 收货联系方式
	private String consignee_tel;

	// 收货地址
	private String address_info;
	// 商家电话
	private String prdt_tel;

	// 规格编号集合，多个用逗号分隔
	private String standard_ids;
	// 用户编号
	private String client_id;
	// 买家留言
	private String leaved_word;
	// 买家昵称
	private String nick_name;

	private static final long serialVersionUID = 1L;
	// 买家注册电话
	private String regist_tel;
	// 买家注册电话
	private String prdt_img;
	// 商品品牌
	private String prdt_brand;
	// 商品类目
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

	public String getPrdt_img() {
		return prdt_img;
	}

	public void setPrdt_img(String prdt_img) {
		this.prdt_img = prdt_img;
	}

	public String getRegist_tel() {
		return regist_tel;
	}

	public void setRegist_tel(String regist_tel) {
		this.regist_tel = regist_tel;
	}

	public String getOrder_main_id() {
		return order_main_id;
	}

	public void setOrder_main_id(String order_main_id) {
		this.order_main_id = order_main_id;
	}

	public String getSerialize_num() {
		return serialize_num;
	}

	public void setSerialize_num(String serialize_num) {
		this.serialize_num = serialize_num;
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
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

	public String getStandard_ids() {
		return standard_ids;
	}

	public void setStandard_ids(String standard_ids) {
		this.standard_ids = standard_ids;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getLeaved_word() {
		return leaved_word;
	}

	public void setLeaved_word(String leaved_word) {
		this.leaved_word = leaved_word;
	}

	public OrderMain(String client_id) {
		super();
		this.client_id = client_id;
	}

	public OrderMain() {
		super();
	}

	public String getPrdt_tel() {
		return prdt_tel;
	}

	public void setPrdt_tel(String prdt_tel) {
		this.prdt_tel = prdt_tel;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

}