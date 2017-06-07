package com.hjh.mall.category.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: hjy-middle
 * @Description TODO
 * @author 陈士俊
 * @date 2017年2月7日
 * @version V1.0
 */
public class CarModel implements Serializable, Comparable<CarModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String car_models_id;

	// 型号名称
	private String car_models_name;

	private String brand_id;

	private String metadata_id;

	private String create_date;

	private String create_time;

	private String update_date;

	private String update_time;

	private String update_user_name;
	private String car_model_alias;
	// 参数集合
	private String car_params_ids;
	private String create_user;
	private String brand_name;
	private String metadata_name;

	public String getCar_models_id() {
		return car_models_id;
	}

	public void setCar_models_id(String car_models_id) {
		this.car_models_id = car_models_id;
	}

	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

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

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getCar_model_alias() {
		return car_model_alias;
	}

	public void setCar_model_alias(String car_model_alias) {
		this.car_model_alias = car_model_alias;
	}

	public String getCar_params_ids() {
		return car_params_ids;
	}

	public void setCar_params_ids(String car_params_ids) {
		this.car_params_ids = car_params_ids;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarModel [car_models_id=");
		builder.append(car_models_id);
		builder.append(", car_models_name=");
		builder.append(car_models_name);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", metadata_id=");
		builder.append(metadata_id);
		builder.append(", create_date=");
		builder.append(create_date);
		builder.append(", create_time=");
		builder.append(create_time);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", car_model_alias=");
		builder.append(car_model_alias);
		builder.append(", car_params_ids=");
		builder.append(car_params_ids);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", metadata_name=");
		builder.append(metadata_name);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(CarModel o) {
		if (o.getBrand_name().equals(brand_name)) {
			if (o.getMetadata_name().equals(metadata_name)) {
				if (o.getCar_models_name().equals(car_models_name)) {
					return this.car_models_name.compareTo(o.getCar_models_name());
				}
			} else {
				return this.metadata_name.compareTo(o.getMetadata_name());
			}
		} else {
			return this.brand_name.compareTo(o.getBrand_name());
		}
		return 0;
	}

	public static void main(String[] args) {
		CarModel carModel = new CarModel();
		List<CarModel> cList = new ArrayList<CarModel>();
		carModel.setBrand_name("卡特");
		carModel.setMetadata_name("挖掘机");
		carModel.setCar_models_name("AL856");
		cList.add(carModel);
		CarModel carModel1 = new CarModel();
		carModel1.setBrand_name("卡特");
		carModel1.setMetadata_name("挖掘机");
		carModel1.setCar_models_name("GL856");
		cList.add(carModel1);
		CarModel carModel2 = new CarModel();
		carModel2.setBrand_name("啊特");
		carModel2.setMetadata_name("挖掘机");
		carModel2.setCar_models_name("AL856");
		cList.add(carModel2);
		CarModel carModel3 = new CarModel();
		carModel3.setBrand_name("不特");
		carModel3.setMetadata_name("挖掘机");
		carModel3.setCar_models_name("AL856");
		cList.add(carModel3);
		CarModel carModel4 = new CarModel();
		carModel4.setBrand_name("陈特");
		carModel4.setMetadata_name("挖掘机");
		carModel4.setCar_models_name("AL856");
		cList.add(carModel4);
		System.out.println(cList);

	}
}
