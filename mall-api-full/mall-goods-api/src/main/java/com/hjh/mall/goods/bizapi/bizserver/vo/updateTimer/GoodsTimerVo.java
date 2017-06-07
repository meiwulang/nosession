package com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer;

import java.util.List;

import com.hjh.mall.common.core.vo.OperatorVo;

/**
 * @Project: mall-goods-api
 * @Description 刷新搜索引擎前的，更新数据库的vo
 * @author 杨益桦
 * @date 2017年4月1日
 * @version V1.0
 */
public class GoodsTimerVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	private List<String> ids;// 商品ids,逗号 分隔

	private String brand_id;// 品牌id

	private String brand_name;// 品牌名称

	private String unit_id;// 计量单位id
	private String unit_name;// 计量单位名称

	public interface FirstTimer {

	}

	private String first_category_id;// 第一级类目id

	private String first_category_name;// 第一级类目名称

	public interface SecondTimer {

	}

	private String second_category_id;// 第二级类目id

	private String second_category_name;// 第二级类目名称

	public interface ThirdTimer {

	}

	private String third_category_id;// 三级类目id,区分原有categor id

	private String third_category_name;// 第三级类目名称

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getFirst_category_id() {
		return first_category_id;
	}

	public void setFirst_category_id(String first_category_id) {
		this.first_category_id = first_category_id;
	}

	public String getFirst_category_name() {
		return first_category_name;
	}

	public void setFirst_category_name(String first_category_name) {
		this.first_category_name = first_category_name;
	}

	public String getSecond_category_id() {
		return second_category_id;
	}

	public void setSecond_category_id(String second_category_id) {
		this.second_category_id = second_category_id;
	}

	public String getSecond_category_name() {
		return second_category_name;
	}

	public void setSecond_category_name(String second_category_name) {
		this.second_category_name = second_category_name;
	}

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsTimerVo [ids=");
		builder.append(ids);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", unit_id=");
		builder.append(unit_id);
		builder.append(", unit_name=");
		builder.append(unit_name);
		builder.append(", first_category_id=");
		builder.append(first_category_id);
		builder.append(", first_category_name=");
		builder.append(first_category_name);
		builder.append(", second_category_id=");
		builder.append(second_category_id);
		builder.append(", second_category_name=");
		builder.append(second_category_name);
		builder.append(", third_category_id=");
		builder.append(third_category_id);
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
