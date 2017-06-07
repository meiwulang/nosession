package com.hjh.mall.bizapi.biz.goods.middle.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * * @author csj:
 * 
 * @date 创建时间：2017年2月20日 上午10:16:55
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class ClientAddress extends Updatable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String client_address_id;
	private String client_id;
	private String provicne;
	private String city;
	private String area;
	private String detail_address;
	private String consignee;
	private String consignee_tel;
	private String address_info;
	private String is_default;

	public String getClient_address_id() {
		return client_address_id;
	}

	public void setClient_address_id(String client_address_id) {
		this.client_address_id = client_address_id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getProvicne() {
		return provicne;
	}

	public void setProvicne(String provicne) {
		this.provicne = provicne;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDetail_address() {
		return detail_address;
	}

	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
		this.address_info = provicne + city + area + detail_address;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientAddress [client_address_id=");
		builder.append(client_address_id);
		builder.append(", client_id=");
		builder.append(client_id);
		builder.append(", provicne=");
		builder.append(provicne);
		builder.append(", city=");
		builder.append(city);
		builder.append(", area=");
		builder.append(area);
		builder.append(", detail_address=");
		builder.append(detail_address);
		builder.append(", consignee=");
		builder.append(consignee);
		builder.append(", consignee_tel=");
		builder.append(consignee_tel);
		builder.append(", address_info=");
		builder.append(address_info);
		builder.append(", is_default=");
		builder.append(is_default);
		builder.append("]");
		return builder.toString();
	}
}
