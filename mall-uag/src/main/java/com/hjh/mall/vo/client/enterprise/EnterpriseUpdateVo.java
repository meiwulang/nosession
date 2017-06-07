package com.hjh.mall.vo.client.enterprise;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

public class EnterpriseUpdateVo extends HJYVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(max=16,min=16)
	@ApiModelProperty(name = "client_enterprise_id", value = "id",dataType="String",notes="id")
	private String client_enterprise_id;
	@NotBlank
	@Length(max=50,min=1)
	@ApiModelProperty(name = "enterprise_name", value = "企业名称",dataType="String",notes="企业名称")
	private String enterprise_name;// 企业名称
	@NotBlank
	@Length(max=10,min=1)
	@ApiModelProperty(name = "enterprise_province", value = "企业所在省",dataType="String",notes="企业所在省")
	private String enterprise_province;// 企业所在省
	@NotBlank
	@Length(max=10,min=1)
	@ApiModelProperty(name = "enterprise_city", value = "企业所在市",dataType="String",notes="企业所在市")
	private String enterprise_city;// 企业所在市
	@NotBlank
	@Length(max=10,min=1)
	@ApiModelProperty(name = "enterprise_area", value = "企业所在区",dataType="String",notes="企业所在区")
	private String enterprise_area;// 企业所在区
	@NotBlank
	@Length(max=50,min=1)
	@ApiModelProperty(name = "enterprise_street", value = "企业详情地址（不包含省市区）",dataType="String",notes="企业详情地址（不包含省市区）")
	private String enterprise_street;// 企业详情地址（不包含省市区）

	@NotBlank
	@Length(max=15,min=1)
	@ApiModelProperty(name = "enterprise_tel", value = "企业联系电话",dataType="String",notes="企业联系电话")
	private String enterprise_tel;// 企业联系电话
	@NotBlank
	@Length(max=10,min=1)
	@ApiModelProperty(name = "enterprise_linkman", value = "企业联系人",dataType="String",notes="企业联系人")
	private String enterprise_linkman;// 企业联系人
	@NotBlank
	@Length(max=100,min=1)
	@ApiModelProperty(name = "major_business", value = "主营",dataType="String",notes="主营，最大长度100")
	private String major_business;// 主营
	public String getClient_enterprise_id() {
		return client_enterprise_id;
	}
	public void setClient_enterprise_id(String client_enterprise_id) {
		this.client_enterprise_id = client_enterprise_id;
	}
	public String getEnterprise_name() {
		return enterprise_name;
	}
	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}
	public String getEnterprise_province() {
		return enterprise_province;
	}
	public void setEnterprise_province(String enterprise_province) {
		this.enterprise_province = enterprise_province;
	}
	public String getEnterprise_city() {
		return enterprise_city;
	}
	public void setEnterprise_city(String enterprise_city) {
		this.enterprise_city = enterprise_city;
	}
	public String getEnterprise_area() {
		return enterprise_area;
	}
	public void setEnterprise_area(String enterprise_area) {
		this.enterprise_area = enterprise_area;
	}
	public String getEnterprise_street() {
		return enterprise_street;
	}
	public void setEnterprise_street(String enterprise_street) {
		this.enterprise_street = enterprise_street;
	}
	public String getEnterprise_tel() {
		return enterprise_tel;
	}
	public void setEnterprise_tel(String enterprise_tel) {
		this.enterprise_tel = enterprise_tel;
	}
	public String getEnterprise_linkman() {
		return enterprise_linkman;
	}
	public void setEnterprise_linkman(String enterprise_linkman) {
		this.enterprise_linkman = enterprise_linkman;
	}
	public String getMajor_business() {
		return major_business;
	}
	public void setMajor_business(String major_business) {
		this.major_business = major_business;
	}
	
	
}
