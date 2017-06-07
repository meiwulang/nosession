/** * @author  csj
 * @date 创建时间：2017年3月14日 下午2:25:29 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.car.vo;

import java.io.Serializable;

public class AddCarParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface Add {
	};

	public interface Update {
	};

	// 参数名称
	private String car_params_name;

	// 参数值
	private String car_params_value;
	//
	private String car_params_id;

	public String getCar_params_name() {
		return car_params_name;
	}

	public void setCar_params_name(String car_params_name) {
		this.car_params_name = car_params_name;
	}

	public String getCar_params_value() {
		return car_params_value;
	}

	public void setCar_params_value(String car_params_value) {
		this.car_params_value = car_params_value;
	}

	public String getCar_params_id() {
		return car_params_id;
	}

	public void setCar_params_id(String car_params_id) {
		this.car_params_id = car_params_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddCarParams [car_params_name=");
		builder.append(car_params_name);
		builder.append(", car_params_value=");
		builder.append(car_params_value);
		builder.append(", car_params_id=");
		builder.append(car_params_id);
		builder.append("]");
		return builder.toString();
	}
}
