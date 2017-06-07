package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月15日
 * @version V1.0
 */
public class GetMetadataByName extends HJYVO {

	private static final long serialVersionUID = 1L;

	private String metadata_name;

	@NotNull
	private Integer type;

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
