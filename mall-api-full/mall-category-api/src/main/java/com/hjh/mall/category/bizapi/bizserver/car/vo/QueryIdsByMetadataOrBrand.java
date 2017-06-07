package com.hjh.mall.category.bizapi.bizserver.car.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年4月10日
 * @version V1.0
 */
public class QueryIdsByMetadataOrBrand extends HJYVO {

	private static final long serialVersionUID = 1L;

	private String metadata_id;
	private String brand_id;

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuueryIdsByMetadataId [metadata_id=");
		builder.append(metadata_id);
		builder.append("]");
		return builder.toString();
	}

}
