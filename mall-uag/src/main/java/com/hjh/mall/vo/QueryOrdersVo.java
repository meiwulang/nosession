package com.hjh.mall.vo;

import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: hjh-mall
 * @Description 查询订单vo
 * @author 王斌
 * @date 2017年2月20日
 * @since 1.0
 */
public class QueryOrdersVo extends PageQueryVO {
	private static final long serialVersionUID = 1L;
	private String start_date;
	private String end_date;
	private String prdt_id;
	private String prdt_name;
	private String consignee_tel;
	private String order_invite_code_start;
	private String order_invite_code_end;
	private String regist_tel;
	private String serialize_num;
	private String prdt_brand;
	private String invite_code;

	public String getPrdt_brand() {
		return prdt_brand;
	}

	public void setPrdt_brand(String prdt_brand) {
		this.prdt_brand = prdt_brand;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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

	public String getConsignee_tel() {
		return consignee_tel;
	}

	public void setConsignee_tel(String consignee_tel) {
		this.consignee_tel = consignee_tel;
	}

	public String getOrder_invite_code_start() {
		return order_invite_code_start;
	}

	public void setOrder_invite_code_start(String order_invite_code_start) {
		this.order_invite_code_start = order_invite_code_start;
	}

	public String getOrder_invite_code_end() {
		return order_invite_code_end;
	}

	public void setOrder_invite_code_end(String order_invite_code_end) {
		this.order_invite_code_end = order_invite_code_end;
	}

	public String getRegist_tel() {
		return regist_tel;
	}

	public void setRegist_tel(String regist_tel) {
		this.regist_tel = regist_tel;
	}

	public String getSerialize_num() {
		return serialize_num;
	}

	public void setSerialize_num(String serialize_num) {
		this.serialize_num = serialize_num;
	}

}
