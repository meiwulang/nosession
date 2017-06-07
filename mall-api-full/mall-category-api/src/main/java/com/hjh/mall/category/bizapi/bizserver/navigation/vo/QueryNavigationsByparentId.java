package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 模糊查询
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
public class QueryNavigationsByparentId extends PageQueryVO {
	protected static final long serialVersionUID = 1L;

	@NotBlank
	@ApiModelProperty(value = "父级编号", required = true)
	private String fatherId;
	@EnumValue(enumClass = Status.class)
	@ApiModelProperty(value = "后台状态", required = false)
	private String status;
	@ApiModelProperty(value = "后台使用", example = "true", required = false)
	private boolean webUse;

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isWebUse() {
		return webUse;
	}

	public void setWebUse(boolean webUse) {
		this.webUse = webUse;
	}

}
