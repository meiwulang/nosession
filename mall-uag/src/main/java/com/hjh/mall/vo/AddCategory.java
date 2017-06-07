package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-businesscard-api
 * @Description 添加标签入参实体
 * @author 王斌
 * @date 2017年1月3日
 * @version V1.0
 */
public class AddCategory extends HJYVO {
	private static final long serialVersionUID = 1L;

	// 类目名称
	@NotBlank
	private String category_name;

	// 图标
	@NotBlank
	private String icon;

	private String remark;

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
