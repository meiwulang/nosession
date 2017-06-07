package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.MobileOrTel;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * * @author csj:
 *
 * @date 创建时间：2017年2月20日 上午11:06:33
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@ApiModel
public class UpdateClientAddress extends HJYVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty("地址编号")
	private String client_address_id;
	@ApiModelProperty("用户编号")
	private String client_id;
	@ApiModelProperty("省")
	private String provicne;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@Length(min = 5, max = 50)
	@ApiModelProperty("详细地址")
	private String detail_address;
	@ApiModelProperty("联系人")
	private String consignee;
	@MobileOrTel
	@ApiModelProperty("联系电话")
	private String consignee_tel;
	@ApiModelProperty("详细的收货地址（包括省市区）")
	private String address_info;// 详细的收货地址（包括省市区）
	@ApiModelProperty("是否默认 1为默认")
	private String is_default;

	public String getClient_id() {
		return client_id;
	}

	public String getClient_address_id() {
		return client_address_id;
	}

	public void setClient_address_id(String client_address_id) {
		this.client_address_id = client_address_id;
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
		this.address_info = address_info;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientAddress [client_address_id=");
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
