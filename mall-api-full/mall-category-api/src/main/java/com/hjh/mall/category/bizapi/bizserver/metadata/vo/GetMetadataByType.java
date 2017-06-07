package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public class GetMetadataByType extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 类别（0：单位；1：车型类型）
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
