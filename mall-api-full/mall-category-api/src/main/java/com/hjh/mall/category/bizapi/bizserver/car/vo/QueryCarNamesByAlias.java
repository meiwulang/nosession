/** * @author  csj
 * @date 创建时间：2017年3月18日 下午4:44:55 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.car.vo;

import com.hjh.mall.common.core.vo.HJYVO;

public class QueryCarNamesByAlias extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String car_model_alias;

	public String getCar_model_alias() {
		return car_model_alias;
	}

	public void setCar_model_alias(String car_model_alias) {
		this.car_model_alias = car_model_alias;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryCarNamesByAlias [car_model_alias=");
		builder.append(car_model_alias);
		builder.append("]");
		return builder.toString();
	}

}
