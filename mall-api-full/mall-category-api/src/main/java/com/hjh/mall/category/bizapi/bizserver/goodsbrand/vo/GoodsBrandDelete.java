package com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo;


import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class GoodsBrandDelete extends HJYVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(notes="id")
	private String brand_id;
    // 备注 
	@ApiModelProperty(notes="备注 ")
    private String remark;
    // 状态（0：禁用；1：启用）
	@ApiModelProperty(notes="状态（0：禁用；1：启用）")
    private String status;
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
