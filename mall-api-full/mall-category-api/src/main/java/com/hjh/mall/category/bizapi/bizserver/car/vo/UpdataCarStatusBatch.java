package com.hjh.mall.category.bizapi.bizserver.car.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * * @author csj:
 * 
 * @date 创建时间：2017年3月14日 下午2:14:25
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class UpdataCarStatusBatch extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 型号名称
	private String car_models_ids;

	private String status;

	public String getCar_models_ids() {
		return car_models_ids;
	}

	public void setCar_models_ids(String car_models_ids) {
		this.car_models_ids = car_models_ids;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdataCarStatusBatch [car_models_ids=");
		builder.append(car_models_ids);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
