package com.hjh.mall.category.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class GoodsCarModels extends Updatable implements Serializable {
	// 商品适用车型编号
	private String goods_car_models_id;

	// 商品编号
	private String goods_id;

	// 车型编号
	private String car_models_id;
	private String create_user_name;

	private String create_date;

	private String create_time;
	private static final long serialVersionUID = 1L;

	public String getGoods_car_models_id() {
		return goods_car_models_id;
	}

	public void setGoods_car_models_id(String goods_car_models_id) {
		this.goods_car_models_id = goods_car_models_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getCar_models_id() {
		return car_models_id;
	}

	public void setCar_models_id(String car_models_id) {
		this.car_models_id = car_models_id;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_models_id == null) ? 0 : car_models_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoodsCarModels other = (GoodsCarModels) obj;
		if (car_models_id == null) {
			if (other.car_models_id != null)
				return false;
		} else if (!car_models_id.equals(other.car_models_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsCarModels [goods_car_models_id=");
		builder.append(goods_car_models_id);
		builder.append(", goods_id=");
		builder.append(goods_id);
		builder.append(", car_models_id=");
		builder.append(car_models_id);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", create_date=");
		builder.append(create_date);
		builder.append(", create_time=");
		builder.append(create_time);
		builder.append("]");
		return builder.toString();
	}

}