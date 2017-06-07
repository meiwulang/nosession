package com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.OperatorVo;

/**
 * @Project: mall-goods-api
 * @Description 刷新搜索引擎前的，更新数据库的vo
 * @author 杨益桦
 * @date 2017年4月1日
 * @version V1.0
 */
public class UpdateBrandVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@NotBlank()
	@Length(min = 16, max = 16)
	private String brand_id;// 品牌id

	@NotBlank()
	@Length(max = 50)
	private String brand_name;// 品牌名称

	public interface UnitTimer {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateBrandVo [brand_id=");
		builder.append(brand_id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
