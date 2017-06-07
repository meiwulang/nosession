package com.hjh.mall.model;

import com.hjh.mall.common.core.annotation.ExcelResources;
import com.hjh.mall.common.core.annotation.Length;


/**
 * @Project: hjy-middle
 * @Description 
 * @author c
 * @date 2016年8月30日
 * @version V1.0
 */
public class CustomerModel {
	
	private int rownum;
	
	

	private String dictionary_name;// 客户类型名
	
//	private String dictionary_id; // 客户类型编号
	
//	private String customer_id; // id

	private String customer_name; // 客户名称
	
	private String mobile_tel; // 手机号
	
	private String address; // 联系地址
	
	private String latitude; // 维度
	
	private String longitude; // 经度
	
	private String street; // 街道
	
	private String status = "1"; // 审核状态
	
	private String client_enterprise_info;// 销售人员所属单位
	
	private String telephone;// 座机
	
	private String e_mail;// 邮箱
	
	private String website;// 网址
	
	private String enterprise_name;// 单位

	private String remark;
	
	private String brand_id;
	
	private String bussiness_scope_id;
	
	/**
	 * @return the rownum
	 */
	@ExcelResources(title="rownum")
	public int getRownum() {
		return rownum;
	}

	/**
	 * @param rownum the rownum to set
	 */
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	/**
	 * @return the brand_id
	 */
	@ExcelResources(title="brand_id")
	public String getBrand_id() {
		return brand_id;
	}

	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	/**
	 * @return the bussiness_scope_id
	 */
	@ExcelResources(title="bussiness_scope_id")
	public String getBussiness_scope_id() {
		return bussiness_scope_id;
	}

	/**
	 * @param bussiness_scope_id the bussiness_scope_id to set
	 */
	public void setBussiness_scope_id(String bussiness_scope_id) {
		this.bussiness_scope_id = bussiness_scope_id;
	}
	@ExcelResources(title="street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	@ExcelResources(title="dictionary_name")
	public String getDictionary_name() {
		return dictionary_name;
	}
	@ExcelResources(title="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDictionary_name(String dictionary_name) {
		this.dictionary_name = dictionary_name;
	}
	@ExcelResources(title="customer_name")
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	@ExcelResources(title="mobile_tel")
	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}
	@ExcelResources(title="address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@ExcelResources(title="latitude")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@ExcelResources(title="longitude")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@ExcelResources(title="telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@ExcelResources(title="e_mail")
	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	@ExcelResources(title="website")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	@ExcelResources(title="enterprise_name")
	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}
	@ExcelResources(title="client_enterprise_info")
	public String getClient_enterprise_info() {
		return client_enterprise_info;
	}

	public void setClient_enterprise_info(String client_enterprise_info) {
		this.client_enterprise_info = client_enterprise_info;
	}
	@ExcelResources(title="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerBaseVo [dictionary_name=");
		builder.append(dictionary_name);
		builder.append(", customer_name=");
		builder.append(customer_name);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", address=");
		builder.append(address);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", street=");
		builder.append(street);
		builder.append(", status=");
		builder.append(status);
		builder.append(", client_enterprise_info=");
		builder.append(client_enterprise_info);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", e_mail=");
		builder.append(e_mail);
		builder.append(", website=");
		builder.append(website);
		builder.append(", enterprise_name=");
		builder.append(enterprise_name);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");
		return builder.toString();
	}

}
