/** * @author  csj
 * @date 创建时间：2017年3月14日 下午2:29:11 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.car.vo;

import java.io.Serializable;

import com.hjh.mall.common.core.annotation.NotBlank;

public class QueryCarModelsApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String brand_id;
	@NotBlank
	private String metadata_id;

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
		builder.append("QueryCarModelsApp [brand_id=");
		builder.append(brand_id);
		builder.append(", metadata_id=");
		builder.append(metadata_id);
		builder.append("]");
		return builder.toString();
	}

}
