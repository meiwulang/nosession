package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月15日
 * @version V1.0
 */
public class GetMetadataById extends HJYVO {

	private static final long serialVersionUID = 1L;
	private String metadata_id;

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}

}
