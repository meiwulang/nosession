/** * @author  csj
 * @date 创建时间：2017年3月16日 下午8:30:52 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.entity;

import java.io.Serializable;

public class TypeNamesInSolr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String car_models_names;

	private String brand_names;

	public String getCar_models_names() {
		return car_models_names;
	}

	public void setCar_models_names(String car_models_names) {
		this.car_models_names = car_models_names;
	}

	public String getBrand_names() {
		return brand_names;
	}

	public void setBrand_names(String brand_names) {
		this.brand_names = brand_names;
	}

	public String getCar_types() {
		return car_types;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TypeNamesInSolr [car_models_names=");
		builder.append(car_models_names);
		builder.append(", brand_names=");
		builder.append(brand_names);
		builder.append(", car_types=");
		builder.append(car_types);
		builder.append("]");
		return builder.toString();
	}

	public void setCar_types(String car_types) {
		this.car_types = car_types;
	}

	private String car_types;

}
